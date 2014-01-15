package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandModel
{
	
	private Realms plugin;

	public CommandModel(Realms plugin)
	{
		this.plugin = plugin;
		
	}
	
	public boolean run(CommandSender sender, String[] args)
	{
		CommandArg commandArg = new CommandArg(args);
		RealmsSubCommandType subCommand = RealmsSubCommandType.getRealmSubCommandType(commandArg.get(0));
		commandArg.remove(0);
		switch (subCommand)
		{
		case ACTIVATE:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdActivate(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case DEACTIVATE:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdDeactivate(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case PRODUCTION:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdProduction(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case SET :
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				cmdSet(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case LIST :
			break;
		case VERSION :
			sender.sendMessage(ChatColor.GREEN+"== "+plugin.getConfigData().getPluginName()+" Vers.: "+plugin.getConfigData().getVersion()+" ==============");
			break;
		case TEST :
			if (commandArg.size() > 0)
			{
				cmdTest(sender, commandArg);
				return true;
			} else
			{
//				sender.sendMessage("Args Error !");
				argsError(sender,subCommand);
			}
			
			break;
		default :
			sender.sendMessage("["+args[0]+"] "+"SubCommand not found else "+RealmsSubCommandType.getRealmSubCommandType(args[0]));
			cmdHelp(sender, commandArg, subCommand);
			return false;
		}
		return true;
	}
	
	private boolean cmdTest(CommandSender sender, CommandArg commandArg)
	{
		int iPara = 0;
			iPara = CommandArg.argToInt(commandArg.get(0));
			sender.sendMessage("Args : "+iPara);
			ArrayList<String> msg = plugin.getMessageData().createTestPage(18);
			plugin.getMessageData().printPage(sender, msg, iPara);
		return true;
	}

	@SuppressWarnings("static-access")
	private boolean cmdSet(CommandSender sender, CommandArg commandArg)
	{
//		msg.add("/model set [AUTO] [true/false] ");
//		msg.add("/model set [COUNTER] [20-24000] ");
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		if (commandArg.size() < 2)
		{
			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.SET);
			return true;
		}

		String bName = commandArg.get(0);
		switch (bName)
		{
		case "counter" :
			int iValue = CommandArg.argToInt(commandArg.get(1));
			plugin.getTickTask().setProdCounter(iValue);
			msg.add("Model SET Production Counter to [ "+iValue+" ]");
			break;
		case "auto":
			boolean bValue = CommandArg.argToBool(commandArg.get(1));
			plugin.getTickTask().setProduction(bValue);
			plugin.getTaxTask().setTax(bValue);
			msg.add("Model SET auto production to [ "+bValue+" ]");
			msg.add("Production Cycles with  ["+plugin.getTickTask().getProdCounter()+"]");
			msg.add("Tax Cycles with  ["+plugin.getTaxTask().getTaxCounter()+"]");
			break;
		default :
			plugin.getMessageData().errorArgWrong(sender, RealmsSubCommandType.SET);
			return true;
		}
		msg.add("");
		msg.add("");
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		return true;
	}

	private boolean cmdActivate(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().OnEnable();
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			msg.add("[Realm Model] Enabled");
			msg.add(plugin.getRealmModel().getModelName()+" Vers.: "+plugin.getRealmModel().getModelVersion());
		} else
		{
			msg.add("[Realm Model] NOT Enabled");
			msg.add("Something unknown is wrong :(");
			plugin.getLog().info("[Realm Model] NOT Enabled. Something unknown is wrong :( ");
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}

	private boolean cmdDeactivate(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().OnDisable();
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			msg.add("[Realm Model] Disabled");
			msg.add(plugin.getRealmModel().getModelName()+" Vers.: "+plugin.getRealmModel().getModelVersion());
			msg.add("All Task are not executed !");
		} else
		{
			msg.add("[Realm Model] NOT Disabled");
			msg.add("Something unknown is wrong :(");
			plugin.getLog().info("[Realm Model] NOT Disabled. Something unknown is wrong :( ");
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}

	private boolean cmdProduction(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
//		msg.add("/model production ");
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		//  || (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED))
		{
			plugin.getRealmModel().OnProduction();
			msg.add("[Realm Model] Production");
			for (Settlement settle : plugin.getRealmModel().getSettlements().getSettlements().values())
			{
				msg.add(settle.getId()+" : "+settle.getName());
				msg.add("Storage  : "+settle.getWarehouse().getItemMax());
				msg.add("Capacity : "+settle.getResident().getSettlerMax());
				msg.add("Settlers : "+settle.getResident().getSettlerCount());
				msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
				msg.add("Happiness: "+settle.getResident().getHappiness());
				msg.add("Fertility: "+settle.getResident().getFertilityCounter());
				msg.add("Deathrate: "+settle.getResident().getDeathrate());
				msg.add("Required Items "+settle.getRequiredProduction().size());
				for (String itemRef : settle.getRequiredProduction().keySet())
				{
					Item item = settle.getRequiredProduction().getItem(itemRef);
					msg.add(item.ItemRef()+" : "+item.value());
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}
	
	private void argsError(CommandSender sender, RealmsSubCommandType subCommand)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add(ChatColor.RED+"Not enough arguments for "+RealmsCommandType.MODEL+" "+subCommand);
		switch (subCommand)
		{
		case TEST :	msg.add(ChatColor.YELLOW+"/model test pageNr , the pageNr is required as number");
			break;
		default :
			break;
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		
	}

	private boolean cmdHelp(CommandSender sender, CommandArg commandArg, RealmsSubCommandType subCommand)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1; //CommandArg.argToInt(commandArg.get(0));
		if (commandArg.size() > 0)
		{
			subCommand = RealmsSubCommandType.getRealmSubCommandType(commandArg.get(0));
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
	    	msg.add("Parameter  [] = required  {} = optional ");
			msg.add("/model activate");
			msg.add("/model deactivate");
			msg.add("/model set [AUTO] [true/false] ");
			msg.add("/model set [COUNTER] [20-24000] ");
			msg.add("/model production ");
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
