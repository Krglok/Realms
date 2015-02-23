package net.krglok.realms;

import java.util.Iterator;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.kingdom.NobleAction;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcAction;
import net.krglok.realms.npc.NpcData;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class NobleTask implements Runnable
{
	private static float NOBLE_RANGE = 40;
	
    private final Realms plugin;
	public long NOBLE_SCHEDULE =  1;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  15;  // 10 * 50 ms 
    
    private BlockFace lastPos = BlockFace.NORTH;
    private Iterator<NpcData> npcIterator;
    private boolean isNpcEnd = true;
    private boolean isNpcDead = false;
    private boolean isNpcAlive = false;

    
    public NobleTask (Realms plugin)
    {
    	this.plugin = plugin;
    }


	@Override
	public void run()
	{
//		
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			return;
		}

		if (plugin.nobleManager.isEnabled() == false)
		{
			return;
		}
//		System.out.println("[REALMS] SpawnManager : "+plugin.nobleManager.isSpawn());

		// make spanw the citizen npc
		if (plugin.nobleManager.isSpawn() == false)
		{
			if (plugin.nobleManager.isNpcInit())
			{
//				System.out.println("[REALMS] next Npc Spawn: "+plugin.nobleManager.getSpawnList().size());
				if (plugin.nobleManager.getSpawnList().size() > 0)
				{
					spawnNoble(plugin.nobleManager);
				} else
				{
					System.out.println("[REALMS] Noble Spawn ENDED : "+plugin.nobleManager.getSpawnList().size());
					plugin.nobleManager.setSpawn(true);
				}
			}
			return;
		}
		
		// do other things :)
		if (plugin.nobleManager.isSpawn() == true)
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
					if ((npcData.isSpawned) && (npcData.getNpcType() == NPCType.NOBLE))
					{
//						doAction(npcData);
					} else
					{
						if (npcData.isAlive())
						{
							if ((npcData.isAlive()) && (npcData.getNpcType() == NPCType.NOBLE))
							{
//								System.out.println("NPC Task reSpawn");
								npcData.setNpcAction(NpcAction.NONE);
								npcData.setNobleAction(NobleAction.NONE);
								plugin.nobleManager.getSpawnList().add(npcData.getId());
								spawnNoble(plugin.nobleManager);
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
		
	private void spawnNoble(NobleManager nobleManager)
	{
		NpcData npcData = plugin.getData().getNpcs().get(nobleManager.getSpawnList().get(0));
		if ((npcData.isChild() == false)
			&& (npcData.isSpawned == false)
			)
		{
			if (npcData.getNpcType() == NPCType.NOBLE)
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
	    			if (npcData.getLehenId() > 0)
	    			{
	    				try
						{
	        				Location location = plugin.makeLocation(plugin.getData().getLehen().getLehen(npcData.getLehenId()).getPosition());
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
	    		}
	    		if (b != null)
	    		{
	    			// round robin for position
	    			lastPos = getNextPos();
	    			position = plugin.makeLocationData(b.getRelative(lastPos).getLocation());
	    			position.setZ(position.getZ()+1);
	
					try
					{
		//					System.out.println("[REALMS] next Npc Spawn: "+nobleManager.getSpawnList().get(0));
						nobleManager.createNoble(npcData, position);
						
					} catch (Exception e)
					{
						System.out.println("[REALMS] EXCEPTION  Noble Spawn: "+nobleManager.getSpawnList().get(0));
						e.printStackTrace(System.out);
					}
	    		} else
	    		{
	    			System.out.println("No regular Location for Noble found "+npcData.getId());
	    		}
			}
		}
		nobleManager.getSpawnList().remove(0);
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

}
