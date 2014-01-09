package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionManager;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStronghold
{

	private Realms plugin;

	public CommandStronghold(Realms plugin)
	{
		this.plugin = plugin;
		
	}

	public boolean run(CommandSender sender, String[] args)
	{
		CommandArg commandArg = new CommandArg(args);
		RealmSubCommandType subCommand = RealmSubCommandType.getRealmSubCommandType(commandArg.get(0));
		commandArg.remove(0);
		switch (subCommand)
		{
		case INFO :
			break;
		case LIST :
			break;
		case BUILDING:
			if (commandArg.size() > 0)
			{
				cmdBuilding(sender, commandArg);
				return true;
			} else
			{
				commandArg.add("1");
				cmdBuilding(sender, commandArg);
				return true;
			}
		case SETTLEMENT:
			if (commandArg.size() > 0)
			{
				cmdSettlement(sender, commandArg);
				return true;
			} else
			{
				commandArg.add("1");
				cmdSettlement(sender, commandArg);
				return true;
			}
		case TEST :
			if (commandArg.size() > 0)
			{
				cmdTest(sender, commandArg);
				return true;
			} else
			{
//				sender.sendMessage("Args Error !");
				plugin.getMessageData().errorArgs(sender,subCommand);
			}
			
			break;
		default :
			sender.sendMessage("["+args[0]+"] "+"SubCommand not found else "+RealmSubCommandType.getRealmSubCommandType(args[0]));
			for (RealmSubCommandType rsc : RealmSubCommandType.values())
			{
				sender.sendMessage(rsc.name());
			}
			return false;
		}
		return true;
	}
	
	private boolean cmdBuilding(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("ID |RegionType | BuildingType");
	    RegionManager rManager = plugin.stronghold.getRegionManager();
	    for (Region region : rManager.getSortedRegions())
	    {
	    	BuildingType buildingType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (buildingType != BuildingType.BUILDING_NONE)
	    	{
	    		msg.add(region.getID()+" : "+ChatColor.YELLOW+region.getType()+" : "+ChatColor.GOLD+buildingType);
	    	}
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}

//	private boolean cmdSettlement(CommandSender sender, CommandArg commandArg)
//	{
//		ArrayList<String> msg = new ArrayList<String>();
//		int page = CommandArg.argToInt(commandArg.get(0));
//		msg.add("ID |RegionType | BuildingType");
//	    RegionManager rManager = plugin.stronghold.getRegionManager();
//	    SettleType buildingType;
//	    for (SuperRegion region : rManager.getSortedSuperRegions())
//	    {
//	    	buildingType = plugin.getConfigData().superRegionToSettleType(region.getType());
//	    	if (buildingType != SettleType.SETTLE_NONE)
//	    	{
//	    		msg.add(region.getName()+" : "+ChatColor.YELLOW+region.getType()+" : "+ChatColor.GOLD+buildingType);
//	    	}
//	    }
//		plugin.getMessageData().printPage(sender, msg, page);
//		return true;
//	}
	
	private boolean cmdSettlement(CommandSender sender, CommandArg commandArg)
	{
		Player player = (Player) sender;
		Location position = player.getLocation();
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("Stronghold List [ "+" ]");
		
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
    		msg.add(sRegion.getType()+" : "+ChatColor.YELLOW+sRegion.getName()+" : "+" Owner: "+sRegion.getOwners());
    		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
    		{
        		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		}
    		msg.add("==");
	    }
	    if (msg.size() == 1)
	    {
	    	msg.add("No Superregions found !");
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}

	private boolean cmdTest(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("ID |RegionType | BuildingType");
	    RegionManager rManager = plugin.stronghold.getRegionManager();
//	    int range = 0;
	    SettleType settleType;
	    for (SuperRegion sRegion : rManager.getSortedSuperRegions())
	    {
	    	settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
	    	if (settleType != SettleType.SETTLE_NONE)
	    	{
		    	msg.add(sRegion.getName()+" : "+ChatColor.YELLOW+sRegion.getType()+" : "+ChatColor.GOLD+settleType);
		    	
			    for (Region region : rManager.getContainedRegions(sRegion))
			    {
			    	BuildingType buildingType = plugin.getConfigData().regionToBuildingType(region.getType());
			    	if (buildingType != BuildingType.BUILDING_NONE)
			    	{
			    		msg.add(" -"+region.getID()+" : "+ChatColor.YELLOW+region.getType()+" : "+ChatColor.GOLD+buildingType);
			    	}
			    }
	    	}
			    
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	@SuppressWarnings("unused")
	private boolean cmdHelp(CommandSender sender, CommandArg commandArg, RealmSubCommandType subCommand)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1; //CommandArg.argToInt(commandArg.get(0));
		if (commandArg.size() > 0)
		{
			subCommand = RealmSubCommandType.getRealmSubCommandType(commandArg.get(0));
		}
		switch (subCommand)
		{
		case LIST :
			msg.add("[Realms] Command: Model                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		case HELP:
		
		default :
			msg.add("[Realms] Command: Model                 ");
			msg.add("SubCommands are case insensitive ");
			msg.add("/model ......");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		}

		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
}
