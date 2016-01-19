package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleTrader extends RealmsCommand
{
	int settleID;
	int page ;

	public CmdSettleTrader( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.TRADER);
		description = new String[] {
				ChatColor.YELLOW+"/settle TRADER [SettleID] [page] ",
				"Show Infomation about the trade orders ",
		    	"of the settlement ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleID = 0;
		this.page = 1;  //default value
	}

	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0:
			settleID = value;
			break;
		case 1 :
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
		return new String[] { int.class.getName(), int.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			if (settle != null)
			{
				msg.add("Trade Order ["+settle.getId()+"] : "+settle.getName());
				msg.add("BuyOrder : ["+settle.getTrader().getBuyOrders().size()+"]");
				for (TradeOrder order : settle.getTrader().getBuyOrders().values())
				{
					msg.add("Buy : "+ChatColor.GREEN+order.getId()
							+" "+ChatColor.YELLOW+order.ItemRef()
							+":"+ChatColor.YELLOW+order.value()
							+":"+ChatColor.YELLOW+order.getBasePrice()
							+" ["+order.getStatus()+"]"
							);
				}
				msg.add("SellOrder: ["+plugin.getRealmModel().getTradeMarket().getSettleOrders(settleID, settle.getSettleType()).size()+"/"+settle.getTrader().getOrderMax()+"]");
				for (TradeMarketOrder order : plugin.getRealmModel().getTradeMarket().getSettleOrders(settleID, settle.getSettleType()).values())
				{
					msg.add(""+ ConfigBasis.setStrright(String.valueOf(order.getId()),4)
							+":"+ChatColor.GREEN+ ConfigBasis.setStrright(String.valueOf(order.getSettleID()),2)
							+" "+ChatColor.YELLOW+ConfigBasis.setStrleft(order.ItemRef()+"___________",15)
							+":"+ChatColor.YELLOW+order.value()
//							+":"+ChatColor.YELLOW+order.getBasePrice()
							+" ["+order.getStatus()+"]"
							);
				}
				int caravanCount = plugin.getRealmModel().getTradeTransport().countSender(settle.getId(),settle.getSettleType());
				msg.add("Transport: ["+caravanCount+"/"+settle.getTrader().getCaravanMax()+"]");
				for (TradeMarketOrder order : plugin.getRealmModel().getTradeTransport().getSubList(settle.getId()).values())
				{
					msg.add("GO : "+ChatColor.GREEN+order.getSettleID()+" >> "+order.getTargetId()
							+" "+ChatColor.YELLOW+order.ItemRef()
							+":"+ChatColor.YELLOW+order.value()
							+":"+ChatColor.YELLOW+order.getBasePrice()
							+" ["+order.getStatus()+"]"
							+" : "+order.getTickCount()+"/"+order.getMaxTicks()
							);
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if(isOpOrAdmin(sender) == false)
			{
				if (isSettleOwner(plugin, sender, settleID) == false)
				{
					errorMsg.add("You are not the Owner !");
					errorMsg.add(" ");
					return false;
				}
			}
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				return true;
			}
			errorMsg.add("Settlement not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
