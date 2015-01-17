package net.krglok.realms;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Anchors;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.manager.NpcManager;
import net.krglok.realms.npc.NpcAction;
import net.krglok.realms.npc.NpcData;

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
	
    private final Realms plugin;
	public long NPC_SCHEDULE =  3;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  5;  // 10 * 50 ms 
    private static int counter = 0;
    
    private int nextNpc = 0;

    private BlockFace lastPos = BlockFace.NORTH;
    private int nextPos = 0;
    
    public NpcTask(Realms plugin)
    {
    	this.plugin = plugin;
    	counter = 0;
    }

	@Override
	public void run()
	{
		// do nothing, when citizens not available
		if (plugin.npcManager.isEnabled() == false)
		{
			return;
		}

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
					plugin.npcManager.setSpawn(true);
				}
			}
			return;
		}
		
		// do other things :)
		if (plugin.npcManager.isSpawn() == true)
		{
			if (nextNpc < plugin.getData().getNpcs().values().size())
			{
//				System.out.println("[REALMS] next Npc Action: "+nextNpc+":"+plugin.getData().getNpcs().values().size());
				NpcData npcData = null;
				int index = 0;
				Iterator<NpcData> npcIterator = plugin.getData().getNpcs().values().iterator();
				while (index <= nextNpc)
				{
					if (npcIterator.hasNext())
					{
						npcData = npcIterator.next();
						index++;
					} else
					{
						index = nextNpc +1;
					}
				}
				if (npcData != null)
				{
//					System.out.println("[REALMS] Npc Action  for: "+npcData.getId());
					doAction(npcData);
				}
				nextNpc++;
			} else
			{
				nextNpc = 0;
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
		NPC npc = CitizensAPI.getNPCRegistry().getById(npcData.spawnId);
		if (npc.isSpawned() == false) 
		{ 
//			System.out.println("[REALMS] Npc Action: Citizen not spawned "+npc.getId());
			return; 
		}

		Location npcRefpos = null;
		switch (npcData.getNpcAction())
		{
		case NONE:
//			if (npcData.getId() < 10)
//			{
//				System.out.println("[REALMS] next Npc Action: "+npcData.getNpcAction());
//			}
			npcData.setNpcAction(NpcAction.STARTUP);
			setHomePosition(npcData);
			break;
		case STARTUP:
//			if (npcData.getId() < 10)
//			{
//				System.out.println("[REALMS] next Npc Action: "+npcData.getNpcAction());
//			}
			if (npc != null)
			{
				Location location = plugin.makeLocation(npcData.getLocation());
				if (location != null)
				{
//					if (npcData.getId() < 10)
//					{
//						System.out.println("[REALMS] next Npc Location: "+LocationData.toString(npcData.getLocation()));
//					}
					npc.getTrait(Anchors.class).addAnchor("Home", location);
					npc.teleport(location, TeleportCause.PLUGIN);
				} else
				{
					System.out.println("[REALMS] Location for Npc Action: NULL");
				}
				
				npcData.setNpcAction(NpcAction.IDLE);
			}
			break;
		case WORKTAVERNE:
			npcRefpos = plugin.makeLocation(npcData.getLocation());
			if (npcRefpos == null ) { return; }
		    if (npcRefpos.getWorld().getTime() > 18000)
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
	    			npc.getNavigator().setTarget(home);
//						npc.teleport(home, TeleportCause.PLUGIN);
					npcData.setNpcAction(NpcAction.IDLE);
	    		}
		    }			
			break;
		case WORK:
			npcRefpos = plugin.makeLocation(npcData.getLocation());
			if (npcRefpos == null ) { return; }
		    if (npcRefpos.getWorld().getTime() > 18000)
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
	    			npc.getNavigator().setTarget(home);
//						npc.teleport(home, TeleportCause.PLUGIN);
					npcData.setNpcAction(NpcAction.IDLE);
	    		}
		    }			
			break;
		default :
			npcRefpos = plugin.makeLocation(npcData.getLocation());
			if (npcRefpos == null ) { return; }
		    if ((npcRefpos.getWorld().getTime() >= 10000)
		    	&& (npcRefpos.getWorld().getTime() < 18000)
		    	)
		    {

		    	if (npcData.getWorkBuilding() > 0)
		    	{
		    		if (plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getBuildingType() == BuildPlanType.TAVERNE)
		    		{
		    			Block b = plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getPosition()).getBlock();
		    			lastPos = getNextPos();
		    			Location taverne = b.getRelative(lastPos).getLocation();
		    			npc.getNavigator().setTarget(taverne);
//						npc.teleport(taverne, TeleportCause.PLUGIN);
						npcData.setNpcAction(NpcAction.WORKTAVERNE);
		    		}
		    	}
		    }
		    if ((npcRefpos.getWorld().getTime() >= 1000)
			    	&& (npcRefpos.getWorld().getTime() < 10000)
			    	)
			    {

			    	if (npcData.getWorkBuilding() > 0)
			    	{
			    		if (plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getBuildingType() != BuildPlanType.TAVERNE)
			    		{
			    			Block b = plugin.makeLocation(plugin.getData().getBuildings().getBuilding(npcData.getWorkBuilding()).getPosition()).getBlock();
			    			lastPos = getNextPos();
			    			Location taverne = b.getRelative(lastPos).getLocation();
			    			npc.getNavigator().setTarget(taverne);
//							npc.teleport(taverne, TeleportCause.PLUGIN);
							npcData.setNpcAction(NpcAction.WORK);
			    		}
			    	}
			    }
//			npc.set
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
	
	private void setHomePosition(NpcData npcData)
	{
		LocationData homePos;
		if (npcData.getHomeBuilding() > 0)
		{
			Building building = plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding());
			if (building != null)
			{
//				if (npcData.getId() < 10)
//				{
//					System.out.println("[REALMS] next Npc Building Location: "+LocationData.toString(building.getPosition()));
//				}
				homePos = building.getPosition();
				if (homePos.getWorld() != null)
				{
					npcData.setLocation(homePos);
				} else
				{
					npcData.setLocation(null);
				}
			}
		}
	}
	
	private void spawnNpc(NpcManager npcManager)
	{
		NpcData npcData = plugin.getData().getNpcs().get(npcManager.getSpawnList().get(0));
		Settlement settle;
		if (npcData.isSpawned == false)
		{
			LocationData position = null;
			switch (npcData.getNpcType())
			{
			case BEGGAR:
//				System.out.println("[REALMS] next Npc Settlement: "+npcData.getSettleId());
				settle = plugin.getData().getSettlements().get(npcData.getSettleId());
				if (settle != null)
				{
					position = settle.getPosition();
				}
				break;
			default:
//				System.out.println("[REALMS] next Npc Building: "+npcData.getHomeBuilding());
				Building building = plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding());
				if (building != null)
				{
					if (building.getPosition().getX() > 0.1)
					{
						position = building.getPosition();
					} else
					{
//						System.out.println("[REALMS] next Npc Settlement and not Building : "+npcData.getSettleId());
						settle = plugin.getData().getSettlements().get(npcData.getSettleId());
						if (settle != null)
						{
							position = settle.getPosition();
						}
					}
				}
				break;
			}
			try
			{
				if (position != null)
				{
//					System.out.println("[REALMS] next Npc Spawn: "+npcManager.getSpawnList().get(0));
					npcManager.createNPC(npcData, position);
				}
				
			} catch (Exception e)
			{
				System.out.println("[REALMS] EXCEPTION  Npc Spawn: "+npcManager.getSpawnList().get(0));
				e.printStackTrace(System.out);
			}
			npcManager.getSpawnList().remove(0);
		}
	}

}
