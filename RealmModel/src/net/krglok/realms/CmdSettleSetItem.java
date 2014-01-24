package net.krglok.realms;

import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.ModelStatus;

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
		    	"/settle SETITEM [SettleID] [item] [amount] ",
				"Set the amount of item into the warehouse ",
		    	"of Settlement <ID> ",
		    	"The amount must available in your inventory ! ",
		    	" ",
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
		// remove item / amount from player inventory
		Player player = (Player) sender;
		ItemStack item = new ItemStack(Material.getMaterial(itemRef), amount);
		player.getInventory().remove(item);
		// set itemRef / amount in warehopuse
		McmdDepositWarehouse cmd = new McmdDepositWarehouse(plugin.getRealmModel(), settleID, itemRef, amount);
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
