package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.Settlement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class CommandRealms
{
	private Realms plugin;
	RealmsCommand[] cmdList ;
	CommandParser parser ;
	
	public CommandRealms(Realms plugin)
	{
		this.plugin = plugin;
		cmdList = makeCommandList();
		parser = new CommandParser(cmdList);
	}

	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
			new cmdRealmNone(),
			new CmdRealmsVersion(),
			new CmdRealmsHelp(),
			new cmdRealmsInfoPricelist(),
			new cmdRealmsTest()
			
		};
		return commandList;
	}
	
	public boolean run(CommandSender sender, String[] args)
	{
		RealmsCommand cmd = parser.getRealmsCommand(args);
		if (cmd != null)
		{
			if (cmd.canExecute(plugin, sender))
			{
				cmd.execute(plugin, sender);
			}
		}
		return true;
	}
	
	//org.bukkit.material.MaterialData.getData(),
	private boolean cmdTest(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.DEBUG);
		}
		String itemRef = commandArg.get(0);
		if (Material.getMaterial(itemRef) != null)
		{
			ItemStack  itemStack = new ItemStack(Material.getMaterial(itemRef));
			sender.sendMessage("Item: "+itemStack.getType().name());
			ItemList items = plugin.getServerData().getDefaultRecipe(itemRef);
			for (String ref :items.keySet())
			{
				System.out.println("- "+ref+":"+items.get(ref));
			}
		}
		return true;
	}

	private boolean cmdInfo(CommandSender sender, RealmsSubCommandType subCommand, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() < 2)
		{
			plugin.getMessageData().errorArgs(sender, subCommand);
			return true;
		}
		String ListRef = commandArg.get(0);
		if (ListRef.equalsIgnoreCase("PRICE"))
		{
			String itemRef = commandArg.get(1);
			Material material = Material.getMaterial(itemRef);
			if (material == null)
			{
				plugin.getMessageData().errorItem(sender, subCommand);
			}
			msg.add("== Produktion price :"+itemRef);
			for (ItemPrice itemPrice : plugin.getServerData().getProductionPrice(itemRef).values())
			{
				msg.add("your price : "+String.valueOf(itemPrice.getBasePrice()*1.25));
				msg.add("base price : "+itemPrice.getBasePrice());
				msg.add("==");
			}
			plugin.getMessageData().printPage(sender, msg, 1);
		}
		return true;
	}
	
	private boolean cmdSet(CommandSender sender, RealmsSubCommandType subCommand, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() < 3)
		{
			plugin.getMessageData().errorArgs(sender, subCommand);
			return true;
		}
		String ListRef = commandArg.get(0);
		if (ListRef.equalsIgnoreCase("PRICE"))
		{
			String itemRef = commandArg.get(1);
			Material material = Material.getMaterial(itemRef);
			if (material == null)
			{
				plugin.getMessageData().errorItem(sender, subCommand);
			}
			Double price = CommandArg.argToDouble(commandArg.get(2));
			plugin.getData().addPrice(itemRef, price);
			sender.sendMessage(itemRef+":"+price);
		}
		return true;
	}
	
	private boolean cmdWrite(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.DEBUG);
			return true;
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
			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.DEBUG);
			return true;
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
			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.DEBUG);
			return true;
		}
		boolean value = CommandArg.argToBool(commandArg.get(0));
		plugin.getMessageData().setisLogAll(value);
		msg.add("[Realm Model] Debug  "+plugin.getMessageData().isLogAll());
		msg.add("printout debug messages to server console !");
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}

	private boolean cmdHelp(CommandSender sender, CommandArg commandArg, RealmsSubCommandType subCommand)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1; //CommandArg.argToInt(commandArg.get(0));
		if (commandArg.size() > 0)
		{
			subCommand = RealmsSubCommandType.getRealmSubCommandType(commandArg.get(0));
			return true;
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
