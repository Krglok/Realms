package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.model.McmdDepositeBank;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFeudalBank extends RealmsCommand
{
	private int lehenId;
	private double amount ;

	public CmdFeudalBank( )
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.BANK);
		description = new String[] {
				ChatColor.YELLOW+"/feudal BANK [lehenID] [amount] ",
				"Set the amount of money into the bank of the Lehen",
		    	"If amount (+) deposit  to bank",
		    	"If amount (-) withdraw from bank",
		    	"  "
		};
		requiredArgs = 2;
		this.lehenId = 0;
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
			lehenId = value;
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
		if (amount > 0)
		{
			if (plugin.economy.has(player.getName(), amount))
			{
				plugin.economy.withdrawPlayer(player.getName(),  amount);
				plugin.getData().getLehen().getLehen(lehenId).getBank().depositKonto(amount, "owner", 0);
			}
		} else
		{
			if (plugin.getData().getLehen().getLehen(lehenId).getBank().getKonto() > amount)
			{
				amount = amount * -1;
				plugin.economy.depositPlayer(player.getName(),  amount);
				plugin.getData().getLehen().getLehen(lehenId).getBank().withdrawKonto(amount, "owner", 0);
			}
		}
		msg.add("Lehen ["+plugin.getData().getLehen().getLehen(lehenId).getId()+"] : "+ChatColor.YELLOW+plugin.getData().getLehen().getLehen(lehenId).getName());
    	msg.add(ChatColor.YELLOW+"Bank  "+ChatColor.GREEN+"deposit : "+amount);
    	msg.add("");
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
		if ( lehen == null)
		{
			errorMsg.add(ChatColor.RED+"Lehen not exist !");
		}
		Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"Owner not exist !");
		}
		if (isOpOrAdmin(sender) == false)
		{
			if (lehen.getOwnerId()== owner.getId() == false)
			{
				errorMsg.add(ChatColor.RED+"You are not the Owner  !");
			}
		}
		if (amount > 0)
		{
			if (plugin.economy.has(player.getName(), amount))
			{
				errorMsg.add(ChatColor.RED+"You have not enough money  !");
			}
		} else
		{
			if (amount < 0.0)
			{	
				if (lehen.getBank().getKonto() > amount)
				{
					errorMsg.add(ChatColor.RED+"The lehen has not enough money !");
				}
			} else
			{
				errorMsg.add(ChatColor.RED+"Amount is wrong !");
				return false;
			}
		}
		
		return true;
	}

}
