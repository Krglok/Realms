package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.Common.CommandParser;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.command.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * 
 * Command handling for the command registered to the plugin
 * every subCommand is a separate instance of abstract class  aRealmsCommand
 * all active commands must be in the cmdList
 * Basic help for no SubCommand are handled in the parser.  
 * 
 * @author Windu
 *
 */
public class CommandList
{
	private Realms plugin;
	private aRealmsCommand[] cmdList ;
	CommandParser parser ;
	
	public CommandList(Realms plugin)
	{
		this.plugin = plugin;
		cmdList = makeCommandList();
		parser = new CommandParser(cmdList);
	}

	private aRealmsCommand[] makeCommandList()
	{
		aRealmsCommand[] commandList = new aRealmsCommand[] {
			new CmdRealmNone(),
			new CmdRealmsActivate(),
			new CmdRealmsBuildingList(),
			new CmdRealmsBuilding(),
			new CmdRealmsBook(),
			new CmdRealmsBookRead(),
			new CmdRealmsBookList(),
			new CmdRealmsCheck(),
			new CmdRealmsDeactivate(),
			new CmdRealmsGetItem(),
			new CmdRealmsHelp(),
			new CmdRealmsPricelistInfo(),
			new CmdRealmsMap(),
			new CmdRealmsMove(),
			new CmdRealmsProduce(),
			new CmdRealmsPrice(),
			new CmdRealmsPlayer(),
			new CmdRealmsRecipeList(),
			new CmdRealmsSettler(),
			new CmdRealmsSetItem(),
			new CmdRealmsSettlement(),
			new CmdRealmsSign(),
			new CmdRealmsTax(),
			new CmdRealmsTech(),
			new CmdWallSign(),
			new CmdRealmsVersion(),
			new CmdRealmNone(),
			new CmdSettleAddBuilding(),
			new CmdSettleAddMember(),
			new CmdSettleAssume(),
			new CmdSettleBank(),
			new CmdSettleBiome(),
			new CmdSettleBuild(),
			new CmdSettleBuilding(),
			new CmdSettleBuildingList(),
			new CmdSettleBuy(),
			new CmdSettleCheck(),
			new CmdSettleCreate(),
			new CmdSettleCredit(),
			new CmdSettleDelete(),
			new CmdSettleDestroyBuilding(),
			new CmdSettleEvolve(),
			new CmdSettleGetItem(),
			new CmdSettleHelp(),
			new CmdSettleInfo(),
			new CmdSettleJoin(),
			new CmdSettleList(),
			new CmdSettleMarket(),
			new CmdSettleNoSell(),
			new CmdSettleOwner(),
			new CmdSettleProduction(),
			new CmdSettleReputation(),
			new CmdSettleRequired(),
			new CmdSettleRoute(),
			new CmdSettleRouteList(),
			new CmdSettleSell(),
			new CmdSettleSetItem(),
			new CmdSettleSettler(),
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
			new CmdOwnerCreate(),
			new CmdOwnerHelp(),
			new CmdOwnerInfo(),
			new CmdOwnerList(),
			new CmdOwnerSet(),
			new CmdOwnerSettlement(),
			new CmdFeudalBank(),
			new CmdFeudalBuild(),
			new CmdFeudalFollow(),
			new CmdFeudalHelp(),
			new CmdFeudalInfo(),
			new CmdFeudalList(),
			new CmdFeudalCreate(),
			new CmdFeudalWarehouse(),
			new CmdFeudalOwner(),
			new CmdFeudalHire(),
			new CmdKingdomList(),
			new CmdKingdomCreate(),
			new CmdKingdomGive(),
			new CmdKingdomHelp(),
			new CmdKingdomInfo(),
			new CmdKingdomJoin(),
			new CmdKingdomMember(),
			new CmdKingdomOwner(),
			new CmdKingdomRequest(),
			new CmdKingdomRelease(),
			new CmdKingdomStructure(),
			new CmdRegimentCreate(),
			new CmdRegimentHome(),
			new CmdRegimentList(),
			new CmdRegimentMove(),
			new CmdRegimentWarehouse(),
			new CmdRegimentRaid(),
			new CmdRegimentInfo(),
			new CmdRegimentHelp(),
			new CmdRealmsTest()
			
		};
		return commandList;
	}
	
	public aRealmsCommand[] getCmdList()
	{
		return cmdList;
	}

	public boolean run(CommandSender sender, Command command, String[] args)
	{
		
		RealmsCommandType cmdType = RealmsCommandType.getRealmCommandType(command.getName()); 
		aRealmsCommand cmd = parser.getRealmsCommand(cmdType, args);
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
				msg.add(ChatColor.YELLOW + "use /"+command.getName()+" [HELP] for more information");
		    	msg.addAll(cmd.getErrorMsg());
				plugin.getMessageData().printPage(sender, msg, 1);
				
			}
		} else
		{
	    	ArrayList<String> msg = new ArrayList<String>();
	    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
	    	msg.add(ChatColor.RED+"OOPS Realms Plugin dont find a Command ! ");
			msg.add(ChatColor.YELLOW + "use /"+command.getName()+" [HELP] for more information");
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
