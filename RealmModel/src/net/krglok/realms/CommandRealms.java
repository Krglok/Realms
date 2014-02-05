package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * 
 * Command handling for the command registered to the plugin
 * every subCommand is a separate instance of abstract class  RealmsCommand
 * allactive commands must be in the cmdList
 * Basic help for no SubCommand are handled in the parser.  
 * 
 * @author Windu
 *
 */
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
			new CmdRealmNone(),
			new CmdRealmsVersion(),
			new CmdRealmsHelp(),
			new CmdRealmsInfoPricelist(),
			new CmdRealmsActivate(),
			new CmdRealmsDeactivate(),
			new CmdRealmsProduce(),
			new CmdRealmsCheck(),
			new CmdRealmsSetItem(),
			new CmdRealmsGetItem(),
			new CmdRealmsMap(),
			new CmdRealmNone(),
			new CmdSettleCheck(),
			new CmdSettleCreate(),
			new CmdSettleHelp(),
			new CmdSettleList(),
			new CmdSettleInfo(),
			new CmdSettleWarehouse(),
			new CmdSettleBank(),
			new CmdSettleBuy(),
			new CmdSettleSell(),
			new CmdSettleSetItem(),
			new CmdSettleGetItem(),
			new CmdSettleTrader(),
			new CmdSettleBuild(),
			new CmdColonistCreate(),
			new CmdColonyBuild(),
			new CmdSettleAddBuilding(),
			new CmdColonyHelp()
//			new CmdRealmsTest()
			
		};
		return commandList;
	}
	
	public RealmsCommand[] getCmdList()
	{
		return cmdList;
	}

	public boolean run(CommandSender sender, Command command, String[] args)
	{
		
		RealmsCommandType cmdType = RealmsCommandType.getRealmCommandType(command.getName()); 
		RealmsCommand cmd = parser.getRealmsCommand(cmdType, args);
		if (cmd != null)
		{
			if (cmd.isParserError() == false)
			{
				if (cmd.canExecute(plugin, sender))
				{
					cmd.execute(plugin, sender);
				} else
				{
			    	ArrayList<String> msg = new ArrayList<String>();
			    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
			    	msg.addAll(cmd.getErrorMsg());
					plugin.getMessageData().printPage(sender, msg, 1);
				}
			} else
			{
		    	ArrayList<String> msg = new ArrayList<String>();
		    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
		    	msg.add(ChatColor.RED+"Error in command sysntax ");
		    	msg.addAll(cmd.getErrorMsg());
				plugin.getMessageData().printPage(sender, msg, 1);
				
			}
		} else
		{
	    	ArrayList<String> msg = new ArrayList<String>();
	    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
	    	msg.add(ChatColor.RED+"OOPS Realms Plugin dont find a Command ! ");
			plugin.getMessageData().printPage(sender, msg, 1);
			
		}
		return true;
	}
	

//	private boolean cmdInfo(CommandSender sender, RealmsSubCommandType subCommand, CommandArg commandArg)
//	{
//		ArrayList<String> msg = new ArrayList<String>();
//		if (commandArg.size() < 2)
//		{
//			plugin.getMessageData().errorArgs(sender, subCommand);
//			return true;
//		}
//		String ListRef = commandArg.get(0);
//		if (ListRef.equalsIgnoreCase("PRICE"))
//		{
//			String itemRef = commandArg.get(1);
//			Material material = Material.getMaterial(itemRef);
//			if (material == null)
//			{
//				plugin.getMessageData().errorItem(sender, subCommand);
//			}
//			msg.add("== Produktion price :"+itemRef);
//			for (ItemPrice itemPrice : plugin.getServerData().getProductionPrice(itemRef).values())
//			{
//				msg.add("your price : "+String.valueOf(itemPrice.getBasePrice()*1.25));
//				msg.add("base price : "+itemPrice.getBasePrice());
//				msg.add("==");
//			}
//			plugin.getMessageData().printPage(sender, msg, 1);
//		}
//		return true;
//	}
	
	
}
