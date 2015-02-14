package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleRouteList extends RealmsCommand
{
	private int page;
	private int settleID;
	
	public CmdSettleRouteList( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.ROUTES);
		description = new String[] {
				ChatColor.YELLOW+"/settle ROUTES [settleID] {page} ",
				"List of Route Order in settlement ",
		    	"  ",
		};
		requiredArgs = 1;
		settleID = 0;
		page = 1;
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
		case 1:
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
		
		Settlement settle = plugin.getData().getSettlements().getSettlement(settleID);
		msg.add("List of Route Order ["+settle.getId()+"] "+settle.getName()+"["+settle.getTrader().getRouteOrders().size()+"]");
		for (RouteOrder rOrder : settle.getTrader().getRouteOrders().values())
		{
			msg.add(rOrder.getTargetId()
					+" | "+rOrder.ItemRef()
					+" | "+rOrder.value()
					+" | "+rOrder.getFormatedBasePrice()
					);
		}
		msg.add(" ");
//		plugin.getData().writeSettlement(settle);
		if (page == 0) { page = 1; }
		plugin.getMessageData().printPage(sender, msg, page);
		settleID = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (isOpOrAdmin(sender) == false)
				{
					return false;
				}
			} else
			{
				errorMsg.add("Settlement not found");
				errorMsg.add(" ");
				return false;
			}
		} else
		{
			errorMsg.add("[Realm Model] NOT enabled or too busy");
			errorMsg.add("Try later again");
			return false;
		}
		return true;
	}

}
