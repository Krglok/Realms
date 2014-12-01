package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleProduction extends RealmsCommand
{
	private int page;
	private int settleId;

	public CmdSettleProduction( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.PRODUCTION);
		description = new String[] {
				ChatColor.YELLOW+"/settle PRODUCTION [page] ",
				"List Production Overview of the settlelment ",
		    	"  ",
		};
		requiredArgs = 1;
		page = 1;
		settleId = 0;
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
		case 0 :
			settleId = value;
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
		return new String[] {int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getId()
				+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleId).getName());
		msg.add("Item              |    Day  |    Month |  Store ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().getPeriodCount()+"]");
		for (String ref : plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().sortItems())
		{
			BoardItem bItem = plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().get(ref);
			String name = ConfigBasis.setStrleft(bItem.getName()+"__________", 12);
			String last = ConfigBasis.setStrright(String.valueOf((int)bItem.getCycleSum()), 9);
			String cycle = ConfigBasis.setStrright(String.valueOf((int)bItem.getPeriodSum()), 9);
			String period = ConfigBasis.setStrright(String.valueOf((int)  plugin.getRealmModel().getSettlements().getSettlement(settleId).getWarehouse().getItemList().getValue(bItem.getName())), 6);
			msg.add(name +"|"+last+"|"+cycle+"|"+period+"|");
		}
		msg.add("");
		page = plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
			{
				errorMsg.add("Settlement not found !!!");
				errorMsg.add("The ID is wrong or not a number ?");
				return false;
			}
			return true;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
