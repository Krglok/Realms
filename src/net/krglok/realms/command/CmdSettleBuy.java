package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
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
				ChatColor.YELLOW+"/settle BUY [ID] [item] [amount] [prive] [days]",
				"Set an buy ordr for te amount of item to the trader ",
		    	"of Settlement <ID>, the decline after day(s) ",
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
    	ArrayList<String> msg = new ArrayList<String>();
		itemRef.toUpperCase();
		McmdBuyOrder cmd = new McmdBuyOrder(plugin.getRealmModel(), settleID, itemRef, amount, price, delayDays,plugin.getRealmModel().getSettlements().getSettlement(settleID).getSettleType());
		plugin.getRealmModel().OnCommand(cmd);
		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleID).getId()+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleID).getName());
    	msg.add(ChatColor.YELLOW+"Buy Item: "+ChatColor.GREEN+itemRef+":"+amount);
    	msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().containsID(settleID))
		{
			if (isOpOrAdmin(sender) == false)
			{
				if (isSettleOwner(plugin, sender, settleID)== false)
				{
					errorMsg.add("You are not the owner ! ");
					errorMsg.add(" ");
					return false;
					
				}
			}
		}
		
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (amount < 0)
				{
					errorMsg.add("The amount must be positive ");
					return false;
				}
				
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
