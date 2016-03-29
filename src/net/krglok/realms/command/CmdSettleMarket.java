package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleMarket extends aRealmsCommand
{

	private int page;
	
	public CmdSettleMarket()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.MARKET);
		description = new String[] {
				ChatColor.YELLOW+"/settle MARKET [page] ",
				"Show the trademarket orders ",
		    	"of all settlement ",
		    	"  "
		};
		requiredArgs = 0;
		this.page = 1;  //default value
	}

	public int getPage()
	{
		return page;
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
		return new String[] { int.class.getName(), String.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			msg.add("SellOrder: ["+plugin.getRealmModel().getTradeMarket().size()+"]");
			for (TradeMarketOrder order : plugin.getRealmModel().getTradeMarket().values())
			{
				
				msg.add(""+ ConfigBasis.setStrright(String.valueOf(order.getId()),4)
						+":"+ChatColor.GREEN+ ConfigBasis.setStrright(String.valueOf(order.getSettleID()),2)
						+" "+ChatColor.YELLOW+ConfigBasis.setStrleft(order.ItemRef()+"___________",12)
						+":"+ChatColor.YELLOW+ConfigBasis.setStrright(order.value(),3)
						+" ["+order.getStatus()+"]"
						+" "+ChatColor.YELLOW+plugin.getData().getSettlements().getSettlement(order.getSettleID()).getName()
						);
			}
		} else
		{
			errorMsg.add("[Realm Model] NOT enabled or too busy");
			errorMsg.add("Try later again");
		}
		page = plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			return true;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
