package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdSettleGetItem extends RealmsCommand
{
	private int settleID;
	private String  itemRef;
	private int amount ;

	public CmdSettleGetItem( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.GETITEM);
		description = new String[] {
				ChatColor.YELLOW+"/setlle GETITEM [SettleID] [item] [amount] ",
				"Get the amount of item from the warehouse ",
		    	"of the Settlement  ",
		    	"Put the item in your inventory ",
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
		
		Player player = (Player) sender;
		ItemStack item = new ItemStack(Material.getMaterial(itemRef), amount);
		player.getInventory().addItem(item);
		amount = amount * -1;
		McmdDepositWarehouse cmd = new McmdDepositWarehouse(plugin.getRealmModel(), settleID, itemRef, amount);
		plugin.getRealmModel().OnCommand(cmd);
		
    	msg.add(ChatColor.YELLOW+"Get Item: "+ChatColor.GREEN+itemRef+":"+amount);
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
				if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getWarehouse().getItemList().getValue(itemRef) < amount  )
				{
					errorMsg.add("Not enough items in warehouse ");
					errorMsg.add(" ");
					return false;
				}
				if (amount < 0)
				{
					errorMsg.add("The amount must be positive ");
					errorMsg.add("better use /settle SET [ID] [item] [amount] ");
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
