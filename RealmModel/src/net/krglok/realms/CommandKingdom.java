package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionManager;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Kingdom;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandKingdom
{
	
	private Realms plugin;

	public CommandKingdom(Realms plugin)
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
		case CREATE:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				sender.sendMessage("Command not implemented !");
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case INFO :
			break;
		case LIST :
			if ((sender.hasPermission(RealmsPermission.USER.name())) ) 
			{
				if (commandArg.size() > 0)
				{
					cmdList(sender, commandArg);
					return true;
				} else
				{
					commandArg.add("1");
					cmdList(sender, commandArg);
					return true;
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
		case HELP:
			if ((sender.hasPermission(RealmsPermission.USER.name())) ) 
			{
				cmdHelp(sender, commandArg,subCommand);
				return true;
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
		default :
			cmdHelp(sender, commandArg, subCommand);
			return false;
		}
		return true;
	}


	private boolean cmdList(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("ID |RegionType | BuildingType");
	    KingdomList  rList = plugin.getRealmModel().getRealms();
	    for (Kingdom realm : rList.getKingdoms().values())
	    {
    		msg.add(realm.getId()+" : "+ChatColor.YELLOW+realm.getName()+" : "+ChatColor.GOLD+realm.isNPCkingdom()+" Owner: "+realm.getOwner().getPlayerName());
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	private boolean cmdInfo (CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("ID |RegionType | BuildingType");
		
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}

	
	
	
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
		case CREATE:
			msg.add("[Realms] Command: kingdom                  ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/kingdom create [kingdomName] {ownerName} ");
			msg.add("creates a new kingdom. the kingdomName must be");
			msg.add("set. Ownername (the king) is optional ");
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
		case INFO :
			msg.add("[Realms] Command: kingdom                  ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/kingdom info [kingdomName] ");
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
		case LIST :
			msg.add("[Realms] Command: kingdom                  ");
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
		case ADD :
			msg.add("[Realms] Command: kingdom                  ");
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
		case DELETE:
			msg.add("[Realms] Command: kingdom                  ");
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
		case SET :
			msg.add("[Realms] Command: kingdom                  ");
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
			msg.add("[Realms] Command: kingdom                  ");
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
		
		default :
			msg.add("[Realms] Command: kingdom                  ");
	    	msg.add("Parameter  [] = required  {} = optional ");
			msg.add("/kingdom create ......");
			msg.add("/kingdom info [RealmID] ,show overview realm");
			msg.add("/kingdom list {page} , show list of realms");
			msg.add("/kingdom help {SubCommand} , show help ");
			msg.add("for SubCommand");
			msg.add("/kingdom add [PlayerName] {level}, add ");
			msg.add("playername to memberlist");
			msg.add("/kingdom delete [PlayerName] , delete ");
			msg.add("playername from memberlist");
			msg.add("/kingdom set {PlayerName} , set playername ");
			msg.add("to owner of realm");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		}

		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
}