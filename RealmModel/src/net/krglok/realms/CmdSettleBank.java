package net.krglok.realms;

import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.command.CommandSender;

public class CmdSettleBank extends RealmsCommand
{
	private int settleID;
	private double amount ;

	public CmdSettleBank( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BANK);
		description = new String[] {
		    	"/settle BANK [SettleID] [amount] ",
				"Set the amount of item into the warehouse ",
		    	"of Settlement <ID> ",
		    	"If amount (+) deposit  to bank",
		    	"If amount (-) withdraw from bank",
		    	"  "
		};
		requiredArgs = 2;
		this.settleID = 0;
		this.amount = 0.0;  //default value
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
		case 1 :
				amount = value;
			break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { int.class.getName(), double.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		McmdDepositeBank bank = new McmdDepositeBank(plugin.getRealmModel(), settleID, amount, sender.getName());
		plugin.getRealmModel().OnCommand(bank);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (sender.isOp())
				{
					return true;
				}
				if (hasMoney(plugin, sender, amount) == false)
				{
					errorMsg.add("You have not enough  money! ");
					return false;
					
				}
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
