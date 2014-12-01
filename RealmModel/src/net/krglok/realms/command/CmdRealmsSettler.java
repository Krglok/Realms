package net.krglok.realms.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NPCType;

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
			requiredArgs = 1;
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

	private void checkRegionChest(Realms plugin, ArrayList<String> msg)
	{
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			Block bs = region.getLocation().getBlock();
			if (bs.getType() != Material.CHEST)
			{
				String x = ConfigBasis.setStrformat2(region.getLocation().getX(),7);
				String y = ConfigBasis.setStrformat2(region.getLocation().getY(),7);
				String z = ConfigBasis.setStrformat2(region.getLocation().getZ(),7);
				String type = ConfigBasis.setStrleft(region.getType(), 12);
				msg.add(region.getID()+":"+ type +":"+ x + ":"+ y +":"+ z);
			}
		}
	}
	
	private static SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if (sRegion != null)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}

	public static Region findRegionAtPosition(Realms plugin, Location position)
	{
	    for (Region region : plugin.stronghold.getRegionManager().getContainingRegions(position))
	    {
	    	if (region != null)
	    	{
	    		return region;
	    	}
	    }
		return null;
	}
	
    private void cmdSignShop(Realms plugin, Block b, Player player)
    {
    	Location pos = b.getLocation();
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
    	    	System.out.println("Realms setShop");
    	    	plugin.setShopPrice(b.getLocation());
//    	    	plugin.setShop(player,b.getLocation(), settle);
//    	    	plugin.setShopPrice(pos);
    	    }
		}
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
//		Location pos = new Location(player.getLocation().getWorld(), player.getLocation().getX()-radius, player.getLocation().getY(), player.getLocation().getZ()-radius);
		Block lookAt =  player.getTargetBlock(null, 6);  
		
		// location where you look at
		Location pos = player.getTargetBlock(null, 6).getLocation();
		pos.setY(pos.getY()+1);
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, pos);
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			Region region = findRegionAtPosition(plugin, pos);
			Building building = settle.getBuildingList().getBuildingByRegion(region.getID());
			if (region != null)
			{
				switch (region.getType())
				{
				case "HALL":
					plugin.npcManager.createNPC("Verwalter", NPCType.MANAGER, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.GREEN+"NPC Citizen");
			    	msg.add("");
					break;
				case "HOME":
					plugin.npcManager.createNPC("Siedler", NPCType.SETTLER, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.GREEN+"NPC Citizen");
			    	msg.add("");
					break;
				case "WHEAT":
					plugin.npcManager.createNPC("Farmer", NPCType.FARMER, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.GREEN+"NPC Citizen");
			    	msg.add("");
					break;
				case "FARMHOUSE":
					plugin.npcManager.createNPC("Farmer", NPCType.FARMER, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.GREEN+"NPC Citizen");
			    	msg.add("");
					break;
				case "WOODCUTTER":
					plugin.npcManager.createNPC("Farmer", NPCType.ARTISAN, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.GREEN+"NPC Citizen");
			    	msg.add("");
					break;
				default:
					plugin.npcManager.createNPC("Bettler", NPCType.BEGGAR, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,building.getId());
			    	msg.add(ChatColor.YELLOW+"NPC Citizen Bettler");
			    	msg.add(ChatColor.RED+"No regular Settler");
			    	msg.add("");
				}
			} else
			{
				plugin.npcManager.createNPC("Kind", NPCType.CHILD, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),settle,0);
		    	msg.add(ChatColor.YELLOW+"NPC Citizen Bettler");
		    	msg.add(ChatColor.RED+"No regular Settler");
		    	msg.add("");
			}
		} else
		{
			plugin.npcManager.createNPC("Bettler", NPCType.BEGGAR, new LocationData(pos.getWorld().getName(),pos.getX(),pos.getY(),pos.getZ()),null,0);
	    	msg.add(ChatColor.YELLOW+"NPC Citizen Bettler");
	    	msg.add(ChatColor.RED+"No regular Settler");
	    	msg.add("");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			return true;
		}
		errorMsg.add("The Model is always Enabled !  ");
		return false;
	}


}
