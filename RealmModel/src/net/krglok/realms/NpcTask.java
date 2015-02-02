package net.krglok.realms;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Anchors;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcAction;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.SettlerTrait;
import net.krglok.realms.unit.UnitAction;
import net.krglok.realms.unit.UnitTrait;
import net.krglok.realms.unit.UnitType;

/**
 * Die schedule zeit ist absichtlich versetzt gegenüber dem realmSchedule.
 * Dies soll die überschneidung der aktivitäten minimieren.
 * Die Task läuft also ASYNCHRON zum RealmModel !!
 * bei schedule = 11 kommt es alle 11 sekunden zu einer synchronen aktivität.
 * 
 * @author Windu
 *
 */
public class NpcTask implements Runnable
{
	private static float SETTLER_RANGE = 40;
	
    private final Realms plugin;
	public long NPC_SCHEDULE =  1;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  15;  // 10 * 50 ms 
    
    private BlockFace lastPos = BlockFace.NORTH;
    private Iterator<NpcData> npcIterator;
    private boolean isNpcEnd = true;
    private boolean isNpcDead = false;
    private boolean isNpcAlive = false;
    
    public NpcTask(Realms plugin)
    {
    	this.plugin = plugin;
    	
    }
	@Override
	public void run()
	{
		// do nothing, when citizens not available
		if (plugin.npcManager.isEnabled() == false)
		{
			return;
		}
//		System.out.println("[REALMS] SpawnManager : "+plugin.npcManager.isSpawn());

		// make spanw the citizen npc
		if (plugin.npcManager.isSpawn() == false)
		{
			if (plugin.npcManager.isNpcInit())
			{
//				System.out.println("[REALMS] next Npc Spawn: "+plugin.npcManager.getSpawnList().size());
				if (plugin.npcManager.getSpawnList().size() > 0)
				{
					spawnNpc(plugin.npcManager);
				} else
				{
					System.out.println("[REALMS] Npc Spawn ENDED : "+plugin.npcManager.getSpawnList().size());
					plugin.npcManager.setSpawn(true);
				}
			}
			return;
		}
		
		// do other things :)
		if (plugin.npcManager.isSpawn() == true)
		{
			if (isNpcEnd)
			{
				// ???? wozu ???
				if (isNpcAlive == false)
				{
					npcIterator = plugin.getData().getNpcs().getAliveNpc().values().iterator();
					isNpcAlive = true;
					isNpcDead = false;
				} else
				{
					npcIterator = plugin.getData().getNpcs().getDeathNpc().values().iterator();
					isNpcAlive = false;
					isNpcDead = true;
				}
				isNpcEnd = false;
				return;
			}
			if (npcIterator.hasNext())
			{
//				System.out.println("[REALMS] next Npc Action: "+nextNpc+":"+plugin.getData().getNpcs().values().size());
				NpcData npcData = npcIterator.next();
				if (npcData != null)
				{
//					System.out.println("[REALMS] Npc Action  for: "+npcData.getId());
					if (npcData.isSpawned)
					{
						doAction(npcData);
					} else
					{
						if (npcData.isAlive())
						{
							if ((npcData.isAlive()) && (npcData.getUnitType() == UnitType.SETTLER))
							{
//								System.out.println("NPC Task reSpawn");
								npcData.setNpcAction(NpcAction.NONE);
								plugin.npcManager.getSpawnList().add(npcData.getId());
								spawnNpc(plugin.npcManager);
							}
						}
					}
				}
			} else
			{
				isNpcEnd = true;
			}
				
			
		}
		
	}
	
	/**
	 * do settler action based on status
	 * 
	 * @param npcData
	 */
	private void doAction(NpcData npcData)
	{
		if (npcData.isSpawned == false)
		{
			return;
		}
		if (npcData.getUnitType() != UnitType.SETTLER)
		{
			return;
		}

		NPC npc = CitizensAPI.getNPCRegistry().getById(npcData.spawnId);
		if (npc.isSpawned() == false) 
		{ 
			return; 
		}

		Location npcRefpos = plugin.makeLocation(npcData.getLocation());
		if (npcRefpos == null ) 
		{ 
			npcRefpos = npc.getEntity().getLocation();
		}
		if (npc.getTrait(SettlerTrait.class).isStuck == true) 
		{ 
			npc.getTrait(SettlerTrait.class).isStuck = false;
			npc.getNavigator().setPaused(false);
			npcData.setNpcAction(NpcAction.IDLE);
			return; 
		}

		switch (npcData.getNpcAction())
		{
		case NONE:
			npcData.setNpcAction(NpcAction.STARTUP);
			setHomePosition(npcData);
			break;
		case STARTUP:
			if (npc != null)
			{
				doTeleportHome(npc, npcData);				
				npcData.setNpcAction(NpcAction.IDLE);
			}
			break;
		case HOME:
		    if ((npcRefpos.getWorld().getTime() >= 0)
			    	&& (npcRefpos.getWorld().getTime() < 6000)
			    	)
		    {
				npcData.setNpcAction(NpcAction.IDLE);
		    }
			break;
		case WORKTAVERNE:
		    if (npcRefpos.getWorld().getTime() > 18000)
		    {
				npcData.setNpcAction(NpcAction.IDLE);
		    }			
			break;
		case WORK:
		    if (npcRefpos.getWorld().getTime() > 11000)
		    {
		    	npcData.setNpcAction(NpcAction.IDLE);
//		    	if (npcData.getNpcType() == NPCType.MANAGER)
//		    	{
//		    		doWorkManager(npc, npcData);
//		    		return;
//		    	}
//		    	
//		    	doWork(npc, npcData);
		    	return;
		    }
			break;
		case TREASURE :
			    if (npcRefpos.getWorld().getTime() > 10000)
			    {
			    	npcData.setNpcAction(NpcAction.IDLE);
//			    	doTreasure(npc, npcData);
			    }
			break;
		default :
			
			// Taverne Worker
			// 7:00 - 17:00  production Worker  
		    if ((npcRefpos.getWorld().getTime() >= 1000)
			    	&& (npcRefpos.getWorld().getTime() < 11000)
			    	)
		    {
		    	if (npcData.getNpcType() == NPCType.MANAGER)
		    	{
		    		setIdleManager(npc, npcData);
		    		return;
		    	}
		    	if (npcData.getWorkBuilding() > 0)
		    	{
		    		if (plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getBuildingType() == null)
		    		{
		    			npcData.setWorkBuilding(0);
		    			return;
		    		}
    				setIdleWorkStation(npc, npcData);
    				return;
		    	} else
		    	{
		    		setTreasue(npc, npcData);
		    		return;
		    	}
		    }
		    // 17:00 - 5:00  go Home 
		    if ((npcRefpos.getWorld().getTime() >= 11000)
			    	&& (npcRefpos.getWorld().getTime() < 23000)
			    )
		    {

		    	if ((npcData.getNpcAction() != NpcAction.WORKTAVERNE)
		    			&& (npcData.getNpcAction() != NpcAction.TAVERNE)
		    		)
		    	{
		    		return;
		    	} else
		    	{
		    		setHomePosition(npcData);
		    		setTarget(npc, npcData.getLocation());
		    	}
		    }
			break;
		}
	}
	
	private BlockFace getNextPos()
	{
		BlockFace nextFace = lastPos;
		switch(lastPos)
		{
		case NORTH:  return BlockFace.NORTH_NORTH_EAST;
		case NORTH_EAST: return BlockFace.EAST;
		case EAST: return BlockFace.SOUTH_EAST;
		case SOUTH_EAST: return BlockFace.SOUTH;
		case SOUTH: return BlockFace.SOUTH_WEST;
		case SOUTH_SOUTH_WEST: return BlockFace.WEST;
		case WEST: return BlockFace.NORTH_WEST;
		default:
			return BlockFace.NORTH;
		}
		
	}
	
	private void setTarget(NPC npc, LocationData position)
	{
		if (npc.getTrait(Waypoints.class).getCurrentProviderName().equalsIgnoreCase("wander"))
		{
			npc.getNavigator().cancelNavigation();
			npc.getTrait(Waypoints.class).setWaypointProvider("linear");
		}
		Location location = plugin.makeLocation(position);
		if (location != null)
		{
			npc.getTrait(UnitTrait.class).setTargetLocation(location);
			npc.getNavigator().cancelNavigation();
			npc.getNavigator().getLocalParameters().avoidWater(true);
			npc.getNavigator().getLocalParameters().range(SETTLER_RANGE);
			npc.getNavigator().setTarget(location);
			npc.getNavigator().setPaused(false);
		}
		
	}

	private void setHomePosition(NpcData npcData)
	{
		LocationData homePos;
		if (npcData.getHomeBuilding() > 0)
		{
			Building building = plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding());
			if (building != null)
			{
				homePos = building.getPosition();
				if (homePos.getWorld() != null)
				{
					npcData.setLocation(homePos);
					return;
				}
			}
		}
		homePos = plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition();
		if (homePos.getWorld() != null)
		{
			npcData.setLocation(homePos);
		}
	}
	
	/**
	 * spawn citizen the first time
	 * NO Child are spawned !!!
	 * @param npcManager
	 */
	private void spawnNpc(NpcManager npcManager)
	{
		NpcData npcData = plugin.getData().getNpcs().get(npcManager.getSpawnList().get(0));
		if ((npcData.isChild() == false)
			&& (npcData.isSpawned == false)
			)
		{
			if (npcData.getUnitType() == UnitType.SETTLER)
			{
				LocationData position = null;
	    		Block b = null;
		    	if (npcData.getHomeBuilding() > 0)
		    	{
		    		try
					{
		    			Location location = plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()).getPosition());
		    			if (location != null)
		    			{
		    				b = location.getBlock();
		    			}
						
					} catch (Exception e)
					{
						return;
					}
		    			
	    		} else
	    		{
	    			if (npcData.getSettleId() > 0)
	    			{
	    				try
						{
	        				Location location = plugin.makeLocation(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition());
	        				if (location != null)
	        				{
	        					b = location.getBlock();
	        				}
							
						} catch (Exception e)
						{
							return;
						}
	    			}
	    		}
	    		if (b != null)
	    		{
	    			// round robin for position
	    			lastPos = getNextPos();
	    			position = plugin.makeLocationData(b.getRelative(lastPos).getLocation());
	    			position.setZ(position.getZ()+1);
	
					try
					{
						if (position != null)
						{
		//					System.out.println("[REALMS] next Npc Spawn: "+npcManager.getSpawnList().get(0));
							npcManager.createNPC(npcData, position);
						} else
						{
							System.out.println("[REALMS) NPC  Spawn NO Position");
						}
						
					} catch (Exception e)
					{
						System.out.println("[REALMS] EXCEPTION  Npc Spawn: "+npcManager.getSpawnList().get(0));
						e.printStackTrace(System.out);
					}
	    		}
			}
		}
		npcManager.getSpawnList().remove(0);
	}

	private void setIdleManager(NPC npc, NpcData npcData)
	{
		if (npcData.getSettleId() > 0)
		{
			Location target = plugin.makeLocation(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition());
			if (target != null)
			{
//				System.out.println(target.toString());
    			Block b = target.getBlock();
    			lastPos = getNextPos();
    			Location taverne = b.getRelative(lastPos).getLocation();
    			taverne.setZ(taverne.getZ()+1);
    			npc.getNavigator().setTarget(taverne);
    			npc.getNavigator().setPaused(false);
//				npc.teleport(taverne, TeleportCause.PLUGIN);
				npcData.setNpcAction(NpcAction.WORK);
//				System.out.println(npc.getId()+" NPC "+npcData.getNpcType()+" walk to WORK ");
			}
		}
		
	}
	
	private void setIdleWorkStation(NPC npc, NpcData npcData)
	{
		Location target =  plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getPosition());
		if (target != null)
		{
//			System.out.println(target.toString());
			Block b = target.getBlock();
			lastPos = getNextPos();
			Location taverne = b.getRelative(lastPos).getLocation();
			taverne.setZ(taverne.getZ()+1);
			npc.getNavigator().cancelNavigation();
			npc.getNavigator().getLocalParameters().avoidWater(true);
			npc.getNavigator().getLocalParameters().range(SETTLER_RANGE);
			npc.getTrait(Waypoints.class).setWaypointProvider("linear");
			npc.getNavigator().setTarget(taverne);
			npc.getNavigator().setPaused(false);
//			npc.teleport(taverne, TeleportCause.PLUGIN);
			npcData.setNpcAction(NpcAction.WORK);
		}
	}
	
	private void setTreasue(NPC npc, NpcData npcData)
	{
		if (npc.getTrait(Waypoints.class).getCurrentProviderName().equalsIgnoreCase("linear"))
		{
			npc.getNavigator().cancelNavigation();
			npc.getTrait(SettlerTrait.class).setTargetLocation(npc.getEntity().getLocation());
			npc.getTrait(SettlerTrait.class).setNavi(false);
			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
			npc.getNavigator().setPaused(false);
	//		System.out.println(npc.getId()+" NPC "+npc.getName()+" set wander ");
		}
		npcData.setNpcAction(NpcAction.TREASURE);
	}
	
	private void doTeleportHome(NPC npc, NpcData npcData)
	{
		Location location = plugin.makeLocation(npcData.getLocation());
		if (location != null)
		{
			location.setZ(location.getZ()+1);
			npc.getTrait(Anchors.class).addAnchor("Home", location);
			npc.teleport(location, TeleportCause.PLUGIN);
		} else
		{
			System.out.println("[REALMS] Location for Npc Action: NULL");
		}
	}
	
	private void doWorkManager(NPC npc, NpcData npcData)
	{
		if (npcData.getSettleId() > 0)
		{
			Location target = plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()).getPosition());
			if (target != null)
			{
//				System.out.println(target.toString());
    			Block b = target.getBlock();
    			lastPos = getNextPos();
    			Location taverne = b.getRelative(lastPos).getLocation();
    			taverne.setZ(taverne.getZ()+1);
    			npc.getNavigator().setTarget(taverne);
    			npc.getNavigator().setPaused(false);
//				npc.teleport(taverne, TeleportCause.PLUGIN);
				npcData.setNpcAction(NpcAction.HOME);
//				System.out.println(npc.getId()+" NPC "+npcData.getNpcType()+" walk to HOME "+target.getWorld().getTime());
				return;
			}
		}
		
	}
	
	private void doWork(NPC npc, NpcData npcData)
	{
		Block b = null;
    	if (npcData.getHomeBuilding() > 0)
    	{
    			b = plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()).getPosition()).getBlock();
		} else
		{
			if (npcData.getSettleId() > 0)
			{
				b = plugin.makeLocation(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition()).getBlock();
			}
		}
		if (b != null)
		{
			lastPos = getNextPos();
			Location home = b.getRelative(lastPos).getLocation();
			home.setZ(home.getZ()+1);
			npc.getNavigator().setTarget(home);
			npc.getNavigator().setPaused(false);
//			npc.teleport(home, TeleportCause.PLUGIN);
			npcData.setNpcAction(NpcAction.IDLE);
		}
		
	}
	
	
	private void doTreasure(NPC npc, NpcData npcData)
	{
//    	if (npc.getTrait(SettlerTrait.class).isNavi() == false)
//    	{
			npc.getTrait(Waypoints.class).setWaypointProvider("linear");
			Location home = npc.getTrait(SettlerTrait.class).getTargetLocation();
			if (home != null)
			{
				npc.getNavigator().setTarget(home);
				npc.getNavigator().setPaused(false);
			} else
			{
				if (npcData.getSettleId() > 0)
				{
					home = plugin.makeLocation(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition());
					if (home != null)
					{
						npc.teleport(home, TeleportCause.PLUGIN);
						npc.getTrait(Waypoints.class).setWaypointProvider("wander");
					}
				}
			}
			npcData.setNpcAction(NpcAction.HOME);
//			System.out.println(npc.getId()+" NPC "+npc.getName()+" Treasure Home set linear ");
//    	}
		
	}
	
}
