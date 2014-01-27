package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleBank extends RealmsCommand
{
	private int settleID;
	private double amount ;

	public CmdSettleBank( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BANK);
		description = new String[] {
				ChatColor.YELLOW+"/settle BANK [SettleID] [amount] ",
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
    	ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		
		Realms.economy.withdrawPlayer(player.getName(),  amount);
		
		McmdDepositeBank bank = new McmdDepositeBank(plugin.getRealmModel(), settleID, amount, sender.getName());
		plugin.getRealmModel().OnCommand(bank);
    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
    	msg.add(ChatColor.YELLOW+"Bank : "+ChatColor.GREEN+"dposit : "+amount);
		msg.addAll(getDescriptionString());
		plugin.getMessageData().printPage(sender, msg, 1);
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
					if (isSettleOwner(plugin, sender, settleID)== false)
					{
						errorMsg.add("You are not the owner ! ");
						errorMsg.add(" ");
						return false;
						
					}
				}
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
