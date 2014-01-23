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
//				cmdActivate(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case DEACTIVATE:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
//				cmdDeactivate(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case PRODUCTION:
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
//				cmdProduction(sender, commandArg);
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
//				argsError(sender,subCommand);
			}
			
			break;
		default :
			sender.sendMessage("["+args[0]+"] "+"SubCommand not found else "+RealmsSubCommandType.getRealmSubCommandType(args[0]));
//			cmdHelp(sender, commandArg, subCommand);
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



}
