package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandRealms
{
	private Realms plugin;
	
	public CommandRealms(Realms plugin)
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
		case DEBUG:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdDebug(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case WRITE :
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdWrite(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case READ :
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdRead(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
		case LIST :
			break;
		case VERSION :
			sender.sendMessage(ChatColor.GREEN+"== "+plugin.getConfigData().getPluginName()+" Vers.: "+plugin.getConfigData().getVersion()+" ==============");
			break;
		case TEST :
			if (commandArg.size() > 0)
			{
//				cmdTest(sender, commandArg);
				return true;
			} else
			{
//				sender.sendMessage("Args Error !");
				plugin.getMessageData().errorArgs(sender, subCommand);
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

	private boolean cmdWrite(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.DEBUG);
		}
		int value = CommandArg.argToInt(commandArg.get(0));
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(value);
		if (settle != null)
		{
			plugin.getData().writeSettlement(settle);
			msg.add("[Realms] WRITE  Settlement "+value);
			msg.add("save settlement data to file ");
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}

	private boolean cmdRead(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.DEBUG);
		}
		int value = CommandArg.argToInt(commandArg.get(0));
		Settlement settle = plugin.getData().readSettlement(value);
		if (settle != null)
		{
			plugin.getRealmModel().getSettlements().addSettlement(settle);
			msg.add("[Realms] READ  Settlement "+value);
			msg.add(settle.getId()+" : "+settle.getName());
			msg.add("Storage  : "+settle.getWarehouse().getItemMax());
			msg.add("Capacity : "+settle.getResident().getSettlerMax());
			msg.add("Settlers : "+settle.getResident().getSettlerCount());
			msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
			msg.add("Happiness: "+settle.getResident().getHappiness());
			msg.add("Fertility: "+settle.getResident().getFertilityCounter());
			msg.add("Deathrate: "+settle.getResident().getDeathrate());
			msg.add("Building : "+settle.getBuildingList().size());
			msg.add("Bank     : "+settle.getBank().getKonto());
			msg.add("Food : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
			msg.add("Required Items "+settle.getRequiredProduction().size());
			for (String itemRef : settle.getRequiredProduction().keySet())
			{
				Item item = settle.getRequiredProduction().getItem(itemRef);
				msg.add(item.ItemRef()+" : "+item.value());
			}
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}
	
	
	private boolean cmdDebug(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.DEBUG);
		}
		boolean value = CommandArg.argToBool(commandArg.get(0));
		plugin.getMessageData().setisLogAll(value);
		msg.add("[Realm Model] Debug  "+plugin.getMessageData().isLogAll());
		msg.add("printout debug messages to server console !");
		plugin.getMessageData().printPage(sender, msg, 1);
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
		case DEBUG :
			msg.add("[Realms] Command: Realms                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/realms debug");
			msg.add("activate the debug mode and listing");
			msg.add("of programmsteps to console");
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
			msg.add("[Realms] Command: Realms                 ");
	    	msg.add("Parameter  [] = required  {} = optional ");
			msg.add("/realms debug");
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
