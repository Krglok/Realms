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
    	if (building != null)
    	{
    		if (checkBuildingSpace(settle, building))
    		{
		    	NpcList residents = settle.getResident().getNpcList().getBuildingNpc(building.getId());
		    	if (building.getBuildingType() == BuildPlanType.HALL)
		    	{
		    		if (residents.getNpcType(NPCType.MANAGER) == null)
		    		{
		    			return NPCType.MANAGER;
		    		}
		    		if (residents.getNpcType(NPCType.FARMER) == null)
		    		{
		    			return NPCType.FARMER;
		    		}
		    		if (residents.getNpcType(NPCType.BUILDER) == null)
		    		{
		    			return NPCType.BUILDER;
		    		}
		    		if (residents.getNpcType(NPCType.CRAFTSMAN) == null)
		    		{
		    			return NPCType.CRAFTSMAN;
		    		}
		    		if (residents.getNpcType(NPCType.MAPMAKER) == null)
		    		{
		    			return NPCType.MAPMAKER;
		    		}
		    	} else
		    	{
		    		return NPCType.SETTLER;
		    	}
    		}
    	}
    	return NPCType.BEGGAR;
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
    
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Spawn Settler ");
    	ArrayList<String> msg = new ArrayList<String>();
		int radius = 5;
		int edge = radius * 2 -1;
		Player player = (Player) sender;
		Entity npc;
		NpcData settleNpc = null;
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
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
				Region region = findRegionAtPosition(plugin, player.getTargetBlock(null, 6).getLocation());
				if (region != null)
				{
					Building building = settle.getBuildingList().getBuildingByRegion(region.getID());
					NPCType nextNpc = nextFreeNpcType(settle, building);
					if (building == null)
					{
						nextNpc = NPCType.BEGGAR;
					}
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
						
				}
			} else
			{
		    	msg.add(ChatColor.RED+"Sorry settlement not found: "+sRegion.getName());
			}
		} else
		{
	    	msg.add(ChatColor.RED+"Sorry No settlement "+sRegion.getName());
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
