package net.krglok.realms.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.UnitType;

public class CmdRealmsSettler extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsSettler( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.SETTLER);
		description = new String[] {
				ChatColor.YELLOW+"/realms SETTLER   ",
		    	" Create a Citizen NPC at the  ",
		    	" you look at. ",
		    	" The settlerType is chosen by regionType ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			page = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName()  };
	}


    public boolean checkBuildingSpace(Settlement settle, Building building )
    {
    	int resident = settle.getResident().getNpcList().getBuildingNpc(building.getId()).size();
    	if (building.getBuildingType() == BuildPlanType.HALL)
    	{
        	if (resident < 5)
        	{
        		return true;
        	}
    	}
    	if (resident < building.getSettler())
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public NPCType nextFreeNpcType(Settlement settle, Building building)
    {
    	NPCType result = null; //NPCType.BEGGAR;
    	if (building != null)
    	{
    		// Check if space in Building
    		if (checkBuildingSpace(settle, building))
    		{
    			// check for resident NpcType 
		    	NpcList residents = settle.getResident().getNpcList().getBuildingNpc(building.getId());
		    	switch (building.getBuildingType())
		    	{
		    	case HALL:
		    		// only one of each type allowed
		    		if (residents.getNpcType(NPCType.MANAGER) == null)
		    		{
		    			result = NPCType.MANAGER;
		    		}
		    		if (residents.getNpcType(NPCType.FARMER) == null)
		    		{
		    			result = NPCType.FARMER;
		    		}
		    		if (residents.getNpcType(NPCType.BUILDER) == null)
		    		{
		    			result = NPCType.BUILDER;
		    		}
		    		if (residents.getNpcType(NPCType.CRAFTSMAN) == null)
		    		{
		    			result = NPCType.CRAFTSMAN;
		    		}
		    		if (residents.getNpcType(NPCType.MAPMAKER) == null)
		    		{
		    			result = NPCType.MAPMAKER;
		    		}
		    		break;
		    	case HOME:
		    	case HOUSE:
		    	case MANSION:
		    	case FARMHOUSE:
		    	case FARM:
		    		result = NPCType.SETTLER;
		    		break;
		    	default :
		    		result = NPCType.BEGGAR;
		    		break;
		    	}
    		}
    	}
    	return result; //NPCType.BEGGAR;
    }

    public UnitType nextFreeUnitType(Settlement settle, Building building, Regiment regiment)
    {
    	UnitType result = null; //NPCType.BEGGAR;
    	if (building != null)
    	{
    		if (settle != null)
    		{
	    		if (checkBuildingSpace(settle, building) == false)
	    		{
	    			return result;
	    		}
    		}
    		if (regiment != null)
    		{
    			return result;
    		}
			// check for resident NpcType 
	    	NpcList residents = settle.getResident().getNpcList().getBuildingNpc(building.getId());
	    	switch (building.getBuildingType())
	    	{
	    	case GUARDHOUSE:
	    		result = UnitType.MILITIA;
	    		break;
	    	case ARCHERY:
	    		result = UnitType.ARCHER;
	    		break;
	    	case BARRACK:
	    		result = UnitType.LIGHT_INFANTRY;
	    		break;
	    	case CASERN:
	    		result = UnitType.HEAVY_INFANTRY;
	    		break;
	    	case TOWER:
	    		result = UnitType.CAVALRY;
	    		break;
	    	case HEADQUARTER:
	    		result = UnitType.COMMANDER;
	    		break;
	    	default :
	    		break;
	    	}
    		
    	} else
    	{
    		
    	}
    	return result; 
    }
    
    private boolean checkFullSize(Location pos)
    {
    	if (pos.getBlock().getType() == Material.AIR)
    	{
	    	if (pos.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
	    	{
	    		return true;
	    	}
    	}
    	return false;
    }
    
    private Location getNearFree(Location pos)
    {
    	Location spawnPos = new Location(pos.getWorld(),pos.getX(),pos.getY(),pos.getZ());
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.NORTH).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.NORTH).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.EAST).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.EAST).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.SOUTH).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.SOUTH).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.WEST).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.WEST).getLocation();
    	}

    	return spawnPos;
    }

    
	private Location getFreeTarget(Location pos, Location eyelocation)
	{
		Vector vec = pos.getDirection();
		Location frontlocation = eyelocation.add(vec);
		return frontlocation.getWorld().getHighestBlockAt(frontlocation).getLocation(); //.getRelative(BlockFace.UP).getLocation();
	}
	
	private ArrayList<String> makeSettler(Realms plugin, NPCType nextNpc, Building building, Settlement settle, Player player)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	NpcData settleNpc = null;
		if (nextNpc == NPCType.BEGGAR)
		{
			settleNpc = new NpcData();
			settleNpc.setGender(NpcData.findGender());
			String npcName = plugin.getData().getNpcName().findName(settleNpc.getGender());
			settleNpc.setName(npcName);
			settleNpc.setNpcType(nextNpc);
			settleNpc.setSettleId(building.getSettleId());
			settleNpc.setHomeBuilding(0);
			settleNpc.setAge(21);
			settleNpc.setMoney(10.0);
			plugin.getData().getNpcs().add(settleNpc);
			settle.getResident().setNpcList(plugin.getData().getNpcs().getSubList(settle.getId())); 
			plugin.getData().writeNpc(settleNpc);
			plugin.npcManager.createNPC(settleNpc, plugin.makeLocationData(player.getLocation()));
			
	    	msg.add(ChatColor.GREEN+"NPC Beggar "+nextNpc+ ": "+settleNpc.getGender()+":"+settleNpc.getName());
		} else
			if (nextNpc == NPCType.SETTLER)
			{
				settleNpc = new NpcData();
				settleNpc.setGender(NpcData.findGender());
				String npcName = plugin.getData().getNpcName().findName(settleNpc.getGender());
				settleNpc.setName(npcName);
				settleNpc.setNpcType(nextNpc);
				settleNpc.setSettleId(building.getSettleId());
				settleNpc.setHomeBuilding(building.getId());
				settleNpc.setAge(21);
				settleNpc.setMoney(10.0);
				plugin.getData().getNpcs().add(settleNpc);
				settle.getResident().setNpcList(plugin.getData().getNpcs().getSubList(settle.getId())); 
				plugin.getData().writeNpc(settleNpc);
				plugin.npcManager.createNPC(settleNpc, plugin.makeLocationData(player.getLocation()));
					
		    	msg.add(ChatColor.GREEN+"NPC Citizen  "+nextNpc+ ": "+settleNpc.getGender()+":"+settleNpc.getName());
			} else
			{
				settleNpc = new NpcData();
				settleNpc.setGender(NpcData.findGender());
				String npcName = plugin.getData().getNpcName().findName(settleNpc.getGender());
				if (nextNpc == NPCType.MANAGER)
				{
					npcName = "Elder "+npcName;
				}
				settleNpc.setName(npcName);
				settleNpc.setNpcType(nextNpc);
				settleNpc.setSettleId(building.getSettleId());
				settleNpc.setHomeBuilding(building.getId());
				settleNpc.setAge(31);
				settleNpc.setMoney(10.0);
				settleNpc.setImmortal(true);
				plugin.getData().getNpcs().add(settleNpc);
				settle.getResident().setNpcList(plugin.getData().getNpcs().getSubList(settle.getId())); 
				plugin.getData().writeNpc(settleNpc);
				plugin.npcManager.createNPC(settleNpc, plugin.makeLocationData(player.getLocation()));
				
		    	msg.add(ChatColor.GREEN+"NPC Manager "+nextNpc+ ": "+settleNpc.getGender()+":"+settleNpc.getName());
			} 
		return msg;
	}

	private ArrayList<String> makeUnit(Realms plugin, UnitType nextUnit, Building building, Settlement settle, Regiment regiment, Player player)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	NpcData settleNpc = null;
    	String npcName = "";
    	switch(nextUnit)
    	{
    	case MILITIA:
			settleNpc = new NpcData();
			settleNpc.setGender(GenderType.MAN);
			npcName = plugin.getData().getNpcName().findName(settleNpc.getGender());
			settleNpc.setName(npcName);
			settleNpc.setNpcType(NPCType.MILITARY);
			settleNpc.setUnitType(nextUnit);
			settleNpc.setSettleId(building.getSettleId());
			settleNpc.setHomeBuilding(building.getId());
			settleNpc.setAge(21);
			settleNpc.setMoney(10.0);
			plugin.getData().getNpcs().add(settleNpc);
			settle.getBarrack().setUnitList(plugin.getData().getNpcs().getSubListUnits(settle.getId()));
			settle.getResident().setNpcList(plugin.getData().getNpcs().getSubList(settle.getId())); 
			plugin.getData().writeNpc(settleNpc);
			plugin.unitManager.createUnit(settleNpc, plugin.makeLocationData(player.getLocation()));
    		break;
    	case ARCHER:
			settleNpc = new NpcData();
			settleNpc.setGender(GenderType.MAN);
			npcName = plugin.getData().getNpcName().findName(settleNpc.getGender());
			settleNpc.setName(npcName);
			settleNpc.setNpcType(NPCType.MILITARY);
			settleNpc.setUnitType(nextUnit);
			settleNpc.setSettleId(building.getSettleId());
			settleNpc.setHomeBuilding(building.getId());
			settleNpc.setAge(21);
			settleNpc.setMoney(10.0);
			plugin.getData().getNpcs().add(settleNpc);
			settle.getBarrack().setUnitList(plugin.getData().getNpcs().getSubListUnits(settle.getId()));
			settle.getResident().setNpcList(plugin.getData().getNpcs().getSubList(settle.getId())); 
			plugin.getData().writeNpc(settleNpc);
			plugin.unitManager.createUnit(settleNpc, plugin.makeLocationData(player.getLocation()));
    		break;
    	default:
    		break;
    	}
		return msg;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Spawn Settler ");
    	ArrayList<String> msg = new ArrayList<String>();
//		int radius = 5;
//		int edge = radius * 2 -1;
		Player player = (Player) sender;
//		Entity npc;
		Location spawnPos;
		spawnPos = getNearFree(player.getLocation());
			
		if (spawnPos.getBlock().getType() != Material.AIR)
		{
	    	msg.add(ChatColor.RED+"Sorry there is no free space for spawn ");
			plugin.getMessageData().printPage(sender, msg, page);
	    	return;
		} else
		{
			// in die mitte des Blocks verschieben
			spawnPos.setX(spawnPos.getX()+0.5);
			spawnPos.setZ(spawnPos.getZ()+0.5);
		}
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, player.getTargetBlock(null, 6).getLocation());
		if (sRegion != null)
		{
			if (sRegion.getType() != "CAMP")
			{
				Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
				if (settle != null)
				{
					Region region = findRegionAtPosition(plugin, player.getTargetBlock(null, 6).getLocation());
					if (region != null)
					{
						Building building = settle.getBuildingList().getBuildingByRegion(region.getID());
						if (building != null)
						{
							if ((BuildPlanType.getBuildGroup(building.getBuildingType()) == ConfigBasis.BUILDPLAN_GROUP_CONSTRUCT)
								|| (BuildPlanType.getBuildGroup(building.getBuildingType()) == ConfigBasis.BUILDPLAN_GROUP_HOME)
								)
							{
								NPCType nextNpc = nextFreeNpcType(settle, building);
								if (nextNpc != null)
								{
									msg.addAll(makeSettler(plugin, nextNpc, building, settle, player));
									return;
								} else
								{
									
									return;

								}
							}
							if ((BuildPlanType.getBuildGroup(building.getBuildingType()) == ConfigBasis.BUILDPLAN_GROUP_MILITARY)
								)
							{
								
								UnitType nextUnit = nextFreeUnitType(settle, building, null);
								if (nextUnit != null)
								{
									makeUnit(plugin, nextUnit, building, settle, null, player);
								} else
								{
									
									return;

								}
							}
						}else
						{
					    	msg.add(ChatColor.RED+"Sorry Building not found region: "+region.getID());
						}
					} else
					{
				    	msg.add(ChatColor.RED+"Sorry Region not found ");
					}
				} else
				{
			    	msg.add(ChatColor.RED+"Sorry settlement not found: "+sRegion.getName());
				}
			} else
			{
		    	msg.add(ChatColor.RED+"Sorry regiment not found: "+sRegion.getName());
			}
		} else
		{
	    	msg.add(ChatColor.RED+"Sorry No soperregion found ");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add(ChatColor.RED+"Only for Ops and Admins !  ");
			return false;
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			errorMsg.add(ChatColor.RED+"The Model is busy or disabled !  ");
			return false;
		}
		return true;
	}


}
