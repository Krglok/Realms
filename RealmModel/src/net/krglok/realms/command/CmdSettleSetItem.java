package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdSettleSetItem extends RealmsCommand
{
	private int settleID;
	private String  itemRef;
	private int amount ;

	public CmdSettleSetItem( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.SETITEM);
		description = new String[] {
				ChatColor.YELLOW+"/settle SETITEM [SettleID] [item] [amount] ",
				"Set the amount of item into the warehouse ",
		    	"of the Settlement  ",
		    	"The amount must available in your inventory ! ",
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
		return new String[] {int.class.getName(), String.class.getName(), int.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		// remove item / amount from player inventory
		Player player = (Player) sender;
		itemRef.toUpperCase();
		ItemStack item = new ItemStack(Material.getMaterial(itemRef), amount);
		if (player.getInventory().contains(item) == true)
		{
			player.getInventory().remove(item);
			player.updateInventory();
			// set itemRef / amount in warehopuse
			McmdDepositWarehouse cmd = new McmdDepositWarehouse(plugin.getRealmModel(), settleID, itemRef, amount);
			plugin.getRealmModel().OnCommand(cmd);
			msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleID).getId()+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleID).getName());
	    	msg.add(ChatColor.YELLOW+"Set Item: "+ChatColor.GREEN+itemRef+":"+amount);
	    	msg.add("");
		} else
		{
			msg.add("You have not enough items !");
			msg.add(" ");
		}
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (this.isOpOrAdmin(sender) == false)
				{
					Player player = (Player) sender;
					String uuid = player.getUniqueId().toString();
					if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getOwner().getUuid().equals(uuid) == false)
					{
						errorMsg.add("You are not the owner ");
						errorMsg.add(" ");
						return false;
					}
				}
				if (amount < 0)
				{
					errorMsg.add("The amount must be positive ");
					errorMsg.add("better use /settle GET [ID] [item] [amount] ");
					return false;
				}
				itemRef.toUpperCase();
				if (hasItem(sender, itemRef, amount) == false)
				{
					errorMsg.add("Item  not found !!!");
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
