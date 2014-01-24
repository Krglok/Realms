package net.krglok.realms;

import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.command.CommandSender;

public class CmdSettleBuy extends RealmsCommand
{
	private int settleID;
	private String  itemRef;
	private int amount ;
	private double price;
	private int delayDays;

	public CmdSettleBuy( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUY);
		description = new String[] {
		    	"/settle BUY [ID] [item] [amount] [prive] [days]",
				"Set an buy ordr for te amount of item to the trader ",
		    	"of Settlement <ID>, the decline after day(s) ",
		    	" ",
		    	" ",
		    	"  "
		};
		requiredArgs = 5;
		this.itemRef = "";
		this.settleID = 0;
		this.amount = 0;  //default value
		this.price = 0.0;
		this.delayDays = 1;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
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
		case 2 :
				amount = value;
			break;
		case 4:
			delayDays = value;
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
		switch (index)
		{
		case 3 :
				price = value;
			break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName()
				, String.class.getName()
				, int.class.getName()
				, double.class.getName()
				, int.class.getName()
				};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		McmdBuyOrder cmd = new McmdBuyOrder(plugin.getRealmModel(), settleID, itemRef, amount, price, delayDays);
		plugin.getRealmModel().OnCommand(cmd);
 
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (amount < 0)
				{
					errorMsg.add("The amount must be positive ");
					errorMsg.add("better use /settle GET [ID] [item] [amount] ");
					return false;
				}
				
//				if (hasItem(sender, itemRef, amount) == false)
//				{
//					errorMsg.add("Item  not found !!!");
//					return false;
//				}
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
