package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.command.CmdColonistCreate;
import net.krglok.realms.command.CmdColonistList;
import net.krglok.realms.command.CmdColonistMove;
import net.krglok.realms.command.CmdColonyBuild;
import net.krglok.realms.command.CmdColonyHelp;
import net.krglok.realms.command.CmdColonyWarehouse;
import net.krglok.realms.command.CmdRealmNone;
import net.krglok.realms.command.CmdRealmsActivate;
import net.krglok.realms.command.CmdRealmsBook;
import net.krglok.realms.command.CmdRealmsBookList;
import net.krglok.realms.command.CmdRealmsBookRead;
import net.krglok.realms.command.CmdRealmsBuildingList;
import net.krglok.realms.command.CmdRealmsCheck;
import net.krglok.realms.command.CmdRealmsDeactivate;
import net.krglok.realms.command.CmdRealmsGetItem;
import net.krglok.realms.command.CmdRealmsHelp;
import net.krglok.realms.command.CmdRealmsMap;
import net.krglok.realms.command.CmdRealmsPrice;
import net.krglok.realms.command.CmdRealmsPricelistInfo;
import net.krglok.realms.command.CmdRealmsProduce;
import net.krglok.realms.command.CmdRealmsSetItem;
import net.krglok.realms.command.CmdRealmsSettler;
import net.krglok.realms.command.CmdRealmsShop;
import net.krglok.realms.command.CmdRealmsSign;
import net.krglok.realms.command.CmdRealmsTest;
import net.krglok.realms.command.CmdRealmsVersion;
import net.krglok.realms.command.CmdRegimentCreate;
import net.krglok.realms.command.CmdRegimentHelp;
import net.krglok.realms.command.CmdRegimentInfo;
import net.krglok.realms.command.CmdRegimentList;
import net.krglok.realms.command.CmdRegimentMove;
import net.krglok.realms.command.CmdRegimentRaid;
import net.krglok.realms.command.CmdRegimentWarehouse;
import net.krglok.realms.command.CmdSettleAddBuilding;
import net.krglok.realms.command.CmdSettleAddMember;
import net.krglok.realms.command.CmdSettleBank;
import net.krglok.realms.command.CmdSettleBiome;
import net.krglok.realms.command.CmdSettleBuild;
import net.krglok.realms.command.CmdSettleBuildingList;
import net.krglok.realms.command.CmdSettleBuy;
import net.krglok.realms.command.CmdSettleCheck;
import net.krglok.realms.command.CmdSettleCreate;
import net.krglok.realms.command.CmdSettleCredit;
import net.krglok.realms.command.CmdSettleDeleteBuild;
import net.krglok.realms.command.CmdSettleEvolve;
import net.krglok.realms.command.CmdSettleGetItem;
import net.krglok.realms.command.CmdSettleHelp;
import net.krglok.realms.command.CmdSettleInfo;
import net.krglok.realms.command.CmdSettleList;
import net.krglok.realms.command.CmdSettleMarket;
import net.krglok.realms.command.CmdSettleNoSell;
import net.krglok.realms.command.CmdSettleOwner;
import net.krglok.realms.command.CmdSettleProduction;
import net.krglok.realms.command.CmdSettleSell;
import net.krglok.realms.command.CmdSettleSetItem;
import net.krglok.realms.command.CmdSettleTrader;
import net.krglok.realms.command.CmdSettleTrain;
import net.krglok.realms.command.CmdSettleWarehouse;
import net.krglok.realms.command.CmdSettleWorkshop;
import net.krglok.realms.command.CommandParser;
import net.krglok.realms.command.RealmsCommand;
import net.krglok.realms.command.RealmsCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * 
 * Command handling for the command registered to the plugin
 * every subCommand is a separate instance of abstract class  RealmsCommand
 * all active commands must be in the cmdList
 * Basic help for no SubCommand are handled in the parser.  
 * 
 * @author Windu
 *
 */
public class CommandRealms
{
	private Realms plugin;
	private RealmsCommand[] cmdList ;
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
			new CmdRealmsActivate(),
			new CmdRealmsBuildingList(),
			new CmdRealmsBook(),
			new CmdRealmsBookRead(),
			new CmdRealmsBookList(),
			new CmdRealmsCheck(),
			new CmdRealmsDeactivate(),
			new CmdRealmsGetItem(),
			new CmdRealmsHelp(),
			new CmdRealmsPricelistInfo(),
			new CmdRealmsMap(),
			new CmdRealmsProduce(),
			new CmdRealmsPrice(),
			new CmdRealmsSettler(),
			new CmdRealmsSetItem(),
			new CmdRealmsShop(),
			new CmdRealmsSign(),
			new CmdRealmsVersion(),
			new CmdRealmNone(),
			new CmdSettleAddBuilding(),
			new CmdSettleAddMember(),
			new CmdSettleBank(),
			new CmdSettleBiome(),
			new CmdSettleBuild(),
			new CmdSettleBuildingList(),
			new CmdSettleBuy(),
			new CmdSettleCheck(),
			new CmdSettleCreate(),
			new CmdSettleCredit(),
			new CmdSettleDeleteBuild(),
			new CmdSettleEvolve(),
			new CmdSettleGetItem(),
			new CmdSettleHelp(),
			new CmdSettleInfo(),
			new CmdSettleList(),
			new CmdSettleMarket(),
			new CmdSettleNoSell(),
			new CmdSettleOwner(),
			new CmdSettleProduction(),
			new CmdSettleSell(),
			new CmdSettleSetItem(),
			new CmdSettleTrader(),
			new CmdSettleTrain(),
			new CmdSettleWarehouse(),
			new CmdSettleWorkshop(),
			new CmdColonistCreate(),
			new CmdColonyBuild(),
			new CmdColonistList(),
			new CmdColonyHelp(),
			new CmdColonyWarehouse(),
			new CmdColonistMove(),
			new CmdRegimentCreate(),
			new CmdRegimentMove(),
			new CmdRegimentList(),
			new CmdRegimentWarehouse(),
			new CmdRegimentRaid(),
			new CmdRegimentInfo(),
			new CmdRegimentHelp(),
			new CmdRealmsTest()
			
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
