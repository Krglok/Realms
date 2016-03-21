package net.krglok.realms.command;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsGetItem extends RealmsCommand
{
	private int settleID;
	private String  itemRef;
	private int amount ;

	public CmdRealmsGetItem( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.GETITEM);
		description = new String[] {
				ChatColor.YELLOW+"/realms GETITEM [SettleID] [item] [amount] ",
				"Get the amount of item from the warehouse ",
		    	"of Settlement <ID> ",
		    	"This is an Admin & OP command ",
		    	"  "
		};
		requiredArgs = 3;
		this.itemRef = "";
		this.settleID = 0;
		this.amount = 0;  //default value
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
		return new String[] {int.class.getName(), String.class.getName(), int.class.getName()};	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		if (ConfigBasis.isMaterial(itemRef))
		{
			if (plugin.getData().getSettlements().getSettlement(settleID).getWarehouse().withdrawItemValue(itemRef, amount))
			{
				sender.sendMessage(ChatColor.RED+"Withdraw "+itemRef+" : "+amount);
			} else
			{
				sender.sendMessage(ChatColor.RED+"NOTHING done !");
			}
		} else
		{
			sender.sendMessage(ChatColor.RED+"NOT a valid Material name !");
		}
		this.itemRef = "";
		this.settleID = 0;
		this.amount = 0;  //default value
		
//		amount = amount * -1;
//		McmdDepositWarehouse cmd = new McmdDepositWarehouse(plugin.getRealmModel(), settleID, itemRef, amount);
//		plugin.getRealmModel().OnCommand(cmd);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (sender.isOp() == false)
			{
				errorMsg.add("You are not an OP ");
				errorMsg.add(" ");
				return false;
			}
			
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getWarehouse().getItemList().getValue(itemRef) < amount  )
				{
					errorMsg.add("Not enough items in warehouse ");
					errorMsg.add(" ");
					return false;
				}
				if (amount < 0)
				{
					errorMsg.add("The amount must be positive ");
					errorMsg.add("better use /realms SETITEM [ID] [item] [amount] ");
					return false;
					
				}
				if (ConfigBasis.isMaterial(itemRef) == false)
				{
					errorMsg.add("Item  not found !!!");
					errorMsg.add("A valid MaterialName must be used !");
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
