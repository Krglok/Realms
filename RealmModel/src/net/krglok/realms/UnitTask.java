package net.krglok.realms;

import java.util.Iterator;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.npc.NpcAction;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class UnitTask implements Runnable
{

    private final Realms plugin;
	public long UNIT_SCHEDULE =  5;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  15;  // 10 * 50 ms 
    private static int counter = 0;
    
    private BlockFace lastPos = BlockFace.NORTH;

    private Iterator<NpcData> npcIterator;
    private boolean isNpcEnd = true;
    private boolean isNpcAlive = false;
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
//				System.out.println("[REALMS] next Unit Spawn: "+plugin.unitManager.getSpawnList().size());
				if (plugin.unitManager.getSpawnList().size() > 0)
				{
					spawnUnit(plugin.unitManager);
				} else
				{
//					System.out.println("[REALMS] Npc Spawn ENDED : "+plugin.unitManager.getSpawnList().size());
					plugin.unitManager.setSpawn(true);
				}
			}
			return;
		}

		//do other things
		if (plugin.unitManager.isSpawn() == false)
		{
			if (isNpcEnd)
			{
				if (isNpcAlive == false)
				{
					npcIterator = plugin.getData().getNpcs().getAliveNpc().values().iterator();
					isNpcAlive = true;
//					isNpcDead = false;
				} else
				{
					npcIterator = plugin.getData().getNpcs().getDeathNpc().values().iterator();
					isNpcAlive = false;
//					isNpcDead = true;
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
//					System.out.println("[REALMS] Unit Action  for: "+npcData.getId());
					if (npcData.isSpawned)
					{
						doAction(npcData);
					} else
					{
						npcData.setNpcAction(NpcAction.NONE);
						plugin.npcManager.getSpawnList().add(npcData.getId());
						spawnUnit(plugin.unitManager);
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
		if (npcRefpos != null)
		{
			
		} else
		{
			
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
		    		b = plugin.getLocationBlock(plugin.getData().getBuildings().getBuilding(npcData.getHomeBuilding()).getPosition());
	    		} else
	    		{
	    			if (npcData.getSettleId() > 0)
	    			{
	    				b = plugin.getLocationBlock(plugin.getData().getSettlements().getSettlement(npcData.getSettleId()).getPosition());
	    			}
	    			if (npcData.getRegimentId() > 0)
	    			{
	    				b = plugin.getLocationBlock(plugin.getData().getRegiments().getRegiment(npcData.getRegimentId()).getPosition());
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
						System.out.println("[REALMS] next Unit Spawn: "+unitManager.getSpawnList().get(0));
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
