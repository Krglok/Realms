package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleRoute extends RealmsCommand
{

	private int page;
	private int settleID;
	private int targetID;
	private String  itemRef;
	private int amount ;

	public CmdSettleRoute( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.ROUTE);
		description = new String[] {
				ChatColor.YELLOW+"/settle ROUTE [senderId] [TargetID] [item] [amount] ",
				"Transport the amount of item to the target settlement ",
		    	"You must be the owner of both settlement  ",
		    	"otherwise you must define a contract ",
		    	"  "
		};
		requiredArgs = 4;
		this.itemRef = "";
		this.settleID = 0;
		this.amount = 0;  //default value
		this.page = 1;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 2:
			itemRef = value;
			break;
		default:
			break;
		}
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
			targetID = value;
			break;
		case 3 :
				amount = value;
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
		return new String[] {int.class.getName(), int.class.getName(), String.class.getName(), int.class.getName()};	
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		
		Settlement settle = plugin.getData().getSettlements().getSettlement(settleID);
		Double price = plugin.getData().getPriceList().getBasePrice(itemRef);
		if (price < 0.1) { price = 0.1; }
		RouteOrder rOrder = new RouteOrder(1, targetID, itemRef, amount, price, true);
		if (settle.getTrader().getRouteOrders().contains(itemRef) == false)
		{
			if (settle.getTrader().getRouteOrders().size() < settle.getTrader().getOrderMax())
			{
				settle.getTrader().getRouteOrders().addRouteOrder(rOrder);
				msg.add("New Route added to ["+settle.getId()+"] "+settle.getName());
				msg.add(rOrder.getTargetId()
						+" | "+rOrder.ItemRef()
						+" | "+rOrder.value()
						+" | "+rOrder.getFormatedBasePrice()
						);
			} else
			{
				msg.add(ChatColor.RED+"Order Limit ["+settle.getId()+"] ["+settle.getTrader().getRouteOrders().size()+"]");
			}
		} else
		{
			if (amount == 0)
			{
				int id = settle.getTrader().getRouteOrders().getRouteOrder(itemRef).getId();
				settle.getTrader().getRouteOrders().remove(id);
				msg.add(ChatColor.RED+"Remove Route to ["+settle.getId()+"] "+settle.getName());
				
			} else
			{
				settle.getTrader().getRouteOrders().getRouteOrder(itemRef).setValue(amount);
				msg.add("Uppdate Route to ["+settle.getId()+"] "+settle.getName());
				msg.add(rOrder.getTargetId()
						+" | "+rOrder.ItemRef()
						+" | "+rOrder.value()
						+" | "+rOrder.getFormatedBasePrice()
						);
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		this.itemRef = "";
		this.settleID = 0;
		this.amount = 0;  //default value
		this.page = 1;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(targetID))
			{
				if (plugin.getRealmModel().getSettlements().containsID(settleID))
				{
					Settlement settle = plugin.getData().getSettlements().getSettlement(settleID);
					if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getWarehouse().getItemList().containsKey(itemRef) == false )
					{
						errorMsg.add("You have that Material not in Warehouse ");
						errorMsg.add("or the Materialname is wrong ");
						errorMsg.add(" ");
						return false;
					}
//					if (settle.getTrader().getRouteOrders().contains(itemRef) == false)
//					{
//						errorMsg.add("");
//						for (RouteOrder rOrder : settle.getTrader().getRouteOrders().values())
//						{
//							errorMsg.add(rOrder.getTargetId()
//									+" | "+rOrder.ItemRef()
//									+" | "+rOrder.getBasePrice()
//									+" | "+rOrder.getFormatedBasePrice()
//									);
//						}
//					}
					if (isOpOrAdmin(sender) == false)
					{
						Player player = (Player) sender;
						int owner1 = plugin.getData().getSettlements().getSettlement(settleID).getOwnerId();
						int owner2 = plugin.getData().getSettlements().getSettlement(targetID).getOwnerId();
						if (owner1 != owner2)
						{
							errorMsg.add("You are not the owner of target settlement ");
							errorMsg.add(" ");
							return false;
						}
						if (settle.getTrader().getRouteOrders().size() >= settle.getTrader().getOrderMax())
						{
							errorMsg.add("Max Order reached ");
							errorMsg.add("build a new TRADER ");
							return false;
						}
	
					}
				} else
				{
					errorMsg.add("Sender settlement not found");
					errorMsg.add(" ");
					return false;
				}
			} else
			{
				errorMsg.add("Target settlement not found");
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
