package net.krglok.realms;

import java.util.Iterator;
import java.util.List;

import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitAction;
import net.krglok.realms.unit.UnitTrait;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class UnitTask implements Runnable
{

	private static float MILITEA_RANGE = 40;
	private static float WANDER_RANGE = 10;
	private static float ARCHCHER_ATTACK_RANGE = 30;
	
    private final Realms plugin;
	public long UNIT_SCHEDULE =  5;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  15;  // 10 * 50 ms 
    private static int counter = 0;
    
    private BlockFace lastPos = BlockFace.NORTH;

    private Iterator<NpcData> npcIterator;
    private boolean isNpcEnd = true;
    private boolean isNpcAlive = true;
//    private boolean isNpcDead = false;
    
    public UnitTask(Realms plugin)
    {
    	this.plugin = plugin;
    }
	
	@Override
	public void run()
	{
		// do nothing, when citizens not available
		if (plugin.unitManager.isEnabled() == false)
		{
			return;
		}

		// make spanw the citizen npc
		if (plugin.unitManager.isSpawn() == false)
		{
			if (plugin.unitManager.isNpcInit())
			{
				System.out.println("[REALMS] next Unit Spawn: "+plugin.unitManager.getSpawnList().size());
				if (plugin.unitManager.getSpawnList().size() > 0)
				{
					spawnUnit(plugin.unitManager);
				} else
				{
					System.out.println("[REALMS] Unit Spawn ENDED : "+plugin.unitManager.getSpawnList().size());
					plugin.unitManager.setSpawn(true);
				}
			}
			return;
		}

		//do other things
		if (plugin.unitManager.isSpawn() == true)
		{
			if (isNpcEnd)
			{
				npcIterator = plugin.getData().getNpcs().getSubListUnits().iterator();
				isNpcEnd = false;
				return;
			}
			if (npcIterator.hasNext())
			{
//				System.out.println("[REALMS] next Npc Action: "+nextNpc+":"+plugin.getData().getNpcs().values().size());
				NpcData npcData = npcIterator.next();
				if (npcData != null)
				{
					if (npcData.isSpawned)
					{
						doAction(npcData);
					} else
					{
						if ((npcData.isAlive()) && (npcData.getNpcType() == NPCType.MILITARY))
						{
//							System.out.println("Unit Task reSpawn");
							npcData.setUnitAction(UnitAction.NONE);
							plugin.unitManager.getSpawnList().add(npcData.getId());
							spawnUnit(plugin.unitManager);
						}
					}
				}
			} else
			{
				isNpcEnd = true;
			}
				

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
	
	public double distance2D(Location loc, Location target)
	{
		double x1 = Math.abs(loc.getX() - target.getX());; //loc.posX - this.posX;
		double z1 = Math.abs(loc.getZ() - target.getZ()); //loc.posZ - this.posZ;
		double d2 = Math.sqrt((x1*x1)+(z1*z1));
		return  d2;
	}

	
	/**
	 * Daily cycle for MILITIA in a Settlement
	 * 
	 * im default werden die zeiten abgefragt und die actionen verteilt
	 * IDLE wird im default abgehandelt. Daher werden die neuen action nicht direkt vergeben,
	 * sondern über default bzw IDLE
	 * @param npcData
	 */
	private void doMilitiaSettle(NpcData npcData, NPC npc)
	{
		Location npcRefpos = plugin.makeLocation(npcData.getLocation());
//		if (npcData.getId() == 136)
//		{
//			System.out.println("[REALMS] Unit GuardPOs  for: "+npcData.getId()+":"+npcData.getUnitAction()+":"+npcData.guardPos);
//		}
		switch (npcData.getUnitAction())
		{
		case NONE:
			setHomePosition(npcData,npc);
			npcData.setUnitAction(UnitAction.IDLE);
			break;
		case HOME: // ab 6:00 gehen sie auf GUARD
		    if ((npcRefpos.getWorld().getTime() >= 0) && (npcRefpos.getWorld().getTime() < 11000))
			{
				npcData.setUnitAction(UnitAction.IDLE);
			}
			break;
		case GUARD: // bis 22:00 sind sie auf GUARD
		    if ((npcRefpos.getWorld().getTime() >= 11000) && (npcRefpos.getWorld().getTime() < 16000))
			{
				npcData.setUnitAction(UnitAction.IDLE);
			}
//			if (npcData.getId() == 136)
//			{
//				System.out.println("[REALMS] Unit GuardPos : "+npcData.getId()+":"+npcData.getUnitAction()+":"+npcData.guardPos+":"+distance2D(npc.getEntity().getLocation(),npc.getTrait(UnitTrait.class).getTargetLocation()));
//			}
		    if (distance2D(npc.getEntity().getLocation(),npc.getTrait(UnitTrait.class).getTargetLocation()) <= 2.5)
		    {
		    	LocationData newPos = getNextGuardPosition(npcData, npc);
		    	if (newPos != null)
		    	{
		    		setTarget(npc,newPos);
		    	}
		    } else
		    {
    			npc.getNavigator().setPaused(false);
		    }
		    EntityType scanTarget = scanEnemy(npc);
		    if (scanTarget != null)
		    {
		    	plugin.getServer().broadcastMessage("Militia see enemy :"+scanTarget.name());
		    	scanTarget = null;
		    }
			
			break;
		case NIGHTWATCH:
			npcData.setUnitAction(UnitAction.IDLE);
			break;
		default:
			// 7:00 bis 17:00 GUARD für MILITIA
		    if ((npcRefpos.getWorld().getTime() >= 500) && (npcRefpos.getWorld().getTime() < 11000))
			{
				npcData.setUnitAction(UnitAction.GUARD);
			}
		    // 17:00 bis 6:00 go home
		    if ((npcRefpos.getWorld().getTime() > 11000) && (npcRefpos.getWorld().getTime() < 23999))
			{
				npcData.setUnitAction(UnitAction.HOME);
				doHome(npcData,npc);
			}
			break;
		}
		
	}
	
	private LocationData findHighest(LocationData position)
	{
		Block b;
		Location location = plugin.makeLocation(position);
		if (location != null)
		{
			b = location.getBlock().getLocation().getWorld().getHighestBlockAt(location);
			return plugin.makeLocationData(b.getRelative(BlockFace.UP).getLocation());
		}
		return null;
	}

	/**
	 * Daily cycle of the Archer
	 *  
	 * im default werden die zeiten abgefragt und die actionen verteilet
	 * IDLÖE wird im default abgehandelt. Daher werden die neuen action nicht direkt vergeben,
	 * sondern über default bzw IDLE
	 * @param npcData
	 * @param npc
	 */
	private void doArcherSettle(NpcData npcData, NPC npc)
	{
		Location npcRefpos = plugin.makeLocation(npcData.getLocation());
		switch (npcData.getUnitAction())
		{
		case NONE:
			setHomePosition(npcData,npc);
			npcData.setUnitAction(UnitAction.IDLE);
			break;
		case HOME:
		    if ((npcRefpos.getWorld().getTime() > 11000))
			{
				npcData.setUnitAction(UnitAction.IDLE);
			}
			break;
		case GUARD:
			npcData.setUnitAction(UnitAction.IDLE);
			break;
		case NIGHTWATCH:
		    if ((npcRefpos.getWorld().getTime() >= 1000) && (npcRefpos.getWorld().getTime() < 11000))
			{
				npcData.setUnitAction(UnitAction.IDLE);
			}
//			if (npcData.getId() == 138)
//			{
//				System.out.println("[REALMS] Unit GuardPos : "+npcData.getId()+":"+npcData.getUnitAction()+":"+npcData.guardPos+":"+distance2D(npc.getEntity().getLocation(),npc.getTrait(UnitTrait.class).getTargetLocation()));
//			}
		    if (distance2D(npc.getEntity().getLocation(),npc.getTrait(UnitTrait.class).getTargetLocation()) <= 2.5)
		    {
		    	if (npcData.guardPos > 0)
		    	{
			    	LocationData newPos = npcData.getLocation();
			    	newPos.setY(newPos.getY()+1);
			    	newPos = findHighest(newPos);
			    	if (newPos != null)
			    	{
						npc.teleport(plugin.makeLocation(newPos), TeleportCause.PLUGIN);
			    		setTarget(npc,newPos);
			    		npcData.guardPos = 0;
			    	}
		    	}
		    }
		    EntityType scanTarget = scanEnemy(npc);
		    if (scanTarget != null)
		    {
//		    	plugin.getServer().broadcastMessage("Archer see enemy :"+scanTarget.name());
		    }
//		    attackEnemy(npc);
			break;
		default:
		    if ((npcRefpos.getWorld().getTime() >= 500) && (npcRefpos.getWorld().getTime() < 11000))
			{
				npcData.setUnitAction(UnitAction.HOME);
				doHome(npcData,npc);
			}

			// 7:00 bis 17:00 GUARD für MILITIA
		    if ((npcRefpos.getWorld().getTime() >= 11000) && (npcRefpos.getWorld().getTime() < 23999))
			{
		    	LocationData position = getNightWatchStart(npcData);
		    	if (position != null)
		    	{
		    		npcData.guardPos = 1;
		    		npcData.setLocation(position);
		    		setTarget(npc, position);
		    		setEnemy(npcData,npc);
					npcData.setUnitAction(UnitAction.NIGHTWATCH);
		    	}
			}
			break;
		}
		
	}
	
	
	private void setEnemy(NpcData npcData, NPC npc)
	{
		if (npcData.getUnitType() == UnitType.ARCHER)
		{
			npc.getNavigator().getLocalParameters().attackRange(ARCHCHER_ATTACK_RANGE);
			npc.getTrait(UnitTrait.class).addEnemyEntity(EntityType.SPIDER);
			npc.getTrait(UnitTrait.class).addEnemyEntity(EntityType.ZOMBIE);
			npc.getTrait(UnitTrait.class).addEnemyEntity(EntityType.SKELETON);
			npc.getTrait(UnitTrait.class).addEnemyEntity(EntityType.CREEPER);
			if (npc.getTrait(SentryTrait.class).getInstance().validTargets.contains(EntityType.SPIDER.name()) == false)
			{
				npc.getTrait(SentryTrait.class).getInstance().validTargets.add(EntityType.SPIDER.name());
			}
			if (npc.getTrait(SentryTrait.class).getInstance().validTargets.contains(EntityType.ZOMBIE.name()) == false)
			{
				npc.getTrait(SentryTrait.class).getInstance().validTargets.add(EntityType.ZOMBIE.name());
			}
			if (npc.getTrait(SentryTrait.class).getInstance().validTargets.contains(EntityType.SKELETON.name()) == false)
			{
				npc.getTrait(SentryTrait.class).getInstance().validTargets.add(EntityType.SKELETON.name());
			}
			if (npc.getTrait(SentryTrait.class).getInstance().validTargets.contains(EntityType.CREEPER.name()) == false)
			{
				npc.getTrait(SentryTrait.class).getInstance().validTargets.add(EntityType.CREEPER.name());
			}
			npc.getTrait(SentryTrait.class).getInstance().UpdateWeapon();
			npc.getTrait(SentryTrait.class).getInstance().setHealth(10);
			npc.setProtected(false);
			npc.getTrait(SentryTrait.class).getInstance().Targetable = true;
			npc.getTrait(SentryTrait.class).getInstance().sentryRange = 30;
			npc.getTrait(SentryTrait.class).getInstance().IgnoreLOS = true;
			npc.getTrait(SentryTrait.class).getInstance().NightVision = 16;
		}
		
	}
	
	private EntityType scanEnemy(NPC npc)
	{
		Location pos = npc.getEntity().getLocation();
		List<Entity> targets = pos.getWorld().getEntities();
		double dist = 0.0;
		double foundDist = ARCHCHER_ATTACK_RANGE;
		EntityType foundType = null;
		for (Entity target : targets)
		{
			if (target.getEntityId() != npc.getEntity().getEntityId())
			{
				dist = target.getLocation().distance(pos);
				if ( dist<= ARCHCHER_ATTACK_RANGE)
				{
					if (npc.getTrait(UnitTrait.class).getEnemyPlayer().contains(target.getType().name()))
					{
						if (dist < foundDist)
						{
							foundDist = dist;
							foundType  = target.getType();
						}
					}
				}
			}
		}
		return foundType;
	}
	
	private LocationData getNightWatchStart(NpcData npcData)
	{
		if (npcData.getSettleId() > 0)
		{
			Settlement settle = plugin.getData().getSettlements().getSettlement(npcData.getSettleId());
			BuildingList buildings = settle.getBuildingList().getSubList(BuildPlanType.GATE);
			Iterator<Building> iterator = buildings.values().iterator();
			if (iterator.hasNext())
			{
				Building building = iterator.next();
				if (building.getWorkerInstalled() == 0)
				{
					building.setWorkerInstalled(1);
					return building.getPosition();
				}
			}
			buildings = settle.getBuildingList().getSubList(BuildPlanType.ARCHERY);
			while (iterator.hasNext())
			{
				Building building = iterator.next();
				if (building.getId() == npcData.getHomeBuilding())
				{
					return building.getPosition();
				}
			}
			return settle.getPosition();
			
		}
		return null;
	}

	/**
	 * get the start Position for Guard Work, settlement center 
	 * @param npcData
	 * @return
	 */
	private void doGuardStart(NpcData npcData, NPC npc)
	{
		LocationData pos = null;
		if (npcData.getRegimentId() > 0)
		{
			
		} else
		{
			if (npcData.getSettleId() > 0)
			{
				pos = plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition();
		    	if (pos != null)
		    	{
		    		npcData.setLocation(pos);
		    		setTarget(npc, pos);
					npcData.guardPos = 7; // count to 0 zero
		    	}
			}
		}
	}

	/**
	 * set a new target location and start the linear Navigator
	 * @param npc
	 * @param position
	 */
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
			npc.getNavigator().getLocalParameters().range(MILITEA_RANGE);
			npc.getNavigator().setTarget(location);
			npc.getNavigator().setPaused(false);
		}
		
	}
	
	private void doWander(NpcData npcData, NPC npc)
	{
		if (npc.getTrait(Waypoints.class).getCurrentProviderName().equalsIgnoreCase("linear"))
		{
			npc.getNavigator().cancelNavigation();
			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
			npc.getNavigator().getLocalParameters().range(WANDER_RANGE);
			npc.getNavigator().setPaused(false);
		}
	}
	
	
	private void doHome(NpcData npcData, NPC npc)
	{
    	setHomePosition(npcData,npc);
    	if (npcData.getLocation() != null)
    	{
			npcData.setUnitAction(UnitAction.HOME);
    		setTarget(npc,npcData.getLocation());
    	}

	}
	
	private void doAction(NpcData npcData)
	{
		if (npcData.isSpawned == false)
		{
			return;
		}
		if (npcData.getNpcType() != NPCType.MILITARY)
		{
			return;
		}

		NPC npc = CitizensAPI.getNPCRegistry().getById(npcData.spawnId);
		if (npc.isSpawned() == false) 
		{ 
			return; 
		}
		if (npc.getTrait(UnitTrait.class).isStuck == true) 
		{ 
			npc.getTrait(UnitTrait.class).isStuck = false;
			npc.getNavigator().setPaused(false);
			npcData.setUnitAction(UnitAction.IDLE);
			return; 
		}
		
			switch(npcData.getUnitType())
			{
			case MILITIA:
				if (npcData.getRegimentId() > 0)
				{
					
				} else
				{
					if (npcData.getSettleId() > 0)
					{
						doMilitiaSettle(npcData, npc);
						return;
					}
				}
				doWander(npcData,npc);
				break;
			case ARCHER:
				if (npcData.getRegimentId() > 0)
				{
					
				} else
				{
					if (npcData.getSettleId() > 0)
					{
						doArcherSettle(npcData, npc);
						return;
					}
				}
				break;
			default:
				break;
			}
			return; 
//		} else
//		{
////			System.out.println("[REALMS] Unit Action  for: "+npcData.getId()+":"+npcData.getUnitAction()+":");
//			
//		}


	}

	private LocationData getSettleGuardPos(Settlement settle, int guardPos)
	{
		LocationData pos = null;
		LocationData center = settle.getPosition();
		switch(guardPos)
		{
		case 7: // North = Z - delta
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ()-20 );
			break;
		case 6: // back to center
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ());
			break;
		case 5: // South = Z + delta
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ()+20 );
			break;
		case 4: // backk to center
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ() );
			break;
		case 3: // East = X + delta
			pos = new LocationData(center.getWorld(),center.getX()+20,center.getY(),center.getZ());
			break;
		case 2: // back to center
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ());
			break;
		case 1: // west = X - delta
			pos = new LocationData(center.getWorld(),center.getX()-20,center.getY(),center.getZ());
			break;
		
		default :
			pos = new LocationData(center.getWorld(),center.getX(),center.getY(),center.getZ());
			break;
		}
		
		return pos;
	}
	
	private LocationData getNextGuardPosition(NpcData npcData, NPC npc)
	{
		LocationData pos = null;
		if (npcData.getRegimentId() > 0)
		{
			
		} else
		{
			// settlement guard
			if (npcData.guardPos < 1)
			{
				if (npcData.getSettleId() > 0)
				{
					pos = plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition();
					npcData.guardPos = 8;
				}
			} else
			{
				pos = getSettleGuardPos(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()), npcData.guardPos);
			}
			npcData.guardPos--;
		}
		if (pos != null)
		{
			npcData.setLocation(pos);
			npc.getTrait(UnitTrait.class).setTargetLocation(plugin.makeLocation(pos));
		}
		return pos;
	}
	
	private void setHomePosition(NpcData npcData, NPC npc)
	{
		LocationData homePos = null;
		if (npcData.getRegimentId() > 0)
		{
			
		} else
		{
			if (npcData.getSettleId() > 0)
			{
				if (npcData.getHomeBuilding() > 0)
				{
					Building building = plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding());
					if (building != null)
					{
						homePos = building.getPosition();
					} else
					{
						npcData.setHomeBuilding(0);
						homePos = plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition();
					}
				} else
				{
					homePos = plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition();
				}
			}
		}
		if (homePos != null)
		{
			npcData.setLocation(homePos);
			npc.getTrait(UnitTrait.class).setTargetLocation(plugin.makeLocation(homePos));
//		} else
//		{
//			npcData.setLocation(null);
		}
	}
	

	
	/**
	 * calculate the basic location and spawn the npc unit
	 * 
	 * @param unitManager
	 */
	private void spawnUnit(UnitManager unitManager)
	{
		NpcData npcData = plugin.getData().getNpcs().get(unitManager.getSpawnList().get(0));
		if ((npcData.isChild() == false)
			&& (npcData.isSpawned == false)
			)
		{
			if (npcData.getUnitType() != UnitType.SETTLER)
			{
				LocationData position = null;
	    		Block b = null;
		    	if (npcData.getHomeBuilding() > 0)
		    	{
		    		if (plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()) != null)
		    		{
		    			b = plugin.getLocationBlock(plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()).getPosition());
		    		} else
		    		{
		    			npcData.setHomeBuilding(0);
		    		}
	    		} else
	    		{
	    			if (npcData.getSettleId() > 0)
	    			{
	    				if (plugin.getData().getSettlements().getSettlement(npcData.getSettleId()) != null)
	    				{
	    					b = plugin.getLocationBlock(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition());
	    				} else
	    				{
	    					npcData.setSettleId(0);
	    				}
	    			}
	    			if (npcData.getRegimentId() > 0)
	    			{
	    				if (plugin.getData().getRegiments().getRegiment(npcData.getRegimentId()) != null)
	    				{	
	    					b = plugin.getLocationBlock(plugin.getData().getRegiments().getRegiment(npcData.getRegimentId()).getPosition());
	    				} else
	    				{
	    					npcData.setRegimentId(0);
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
//						System.out.println("[REALMS] next Unit Spawn: "+unitManager.getSpawnList().get(0));
						npcData.setLocation(position);
						npcData.setUnitAction(UnitAction.NONE);
						unitManager.createUnit(npcData, position);
						
					} catch (Exception e)
					{
						System.out.println("[REALMS] EXCEPTION  Npc Spawn: "+unitManager.getSpawnList().get(0));
						e.printStackTrace(System.out);
					}
	    		}
			}
		}
		unitManager.getSpawnList().remove(0);
	}
}
