package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdSettleCredit extends RealmsCommand
{
	private int settleID;
	private int amount ;
	private String itemRef;

	public CmdSettleCredit()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.CREDIT);
		description = new String[] {
				ChatColor.YELLOW+"/settle CREDIT [SettleID] [item] [amount] ",
				"Set the price  of item into Bank ",
		    	"of Settlement <ID> ",
		    	"the item must be a valuable ",
		    	"EMERALD|GOLD_NUGGET|GOLD_INGOT|DIAMOND",
		    	"the amount must be in your inventory",
		    	"  "
		};
		requiredArgs = 3;
		this.settleID = 0;
		this.itemRef = "";
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
		return new String[] {int.class.getName(), String.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		
		itemRef.toUpperCase();
		ItemStack item = new ItemStack(Material.getMaterial(itemRef), amount);
		player.getInventory().remove(item);
		ItemList iList = new ItemList();
		iList.addItem(new Item(itemRef,amount));
		msg = plugin.getRealmModel().getSettlements().getSettlement(settleID).getBank().setKredit(itemRef, amount, plugin.getData().getPriceList(), iList, settleID);
//		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleID).getId()+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleID).getName());
//    	msg.add(ChatColor.YELLOW+"Bank  "+ChatColor.GREEN+"deposit : "+amount);
    	msg.add("");
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
					// dont check for items or money
					return true;
				}
				if (hasItem(sender, itemRef, amount) == false)
				{
					errorMsg.add("You have not enough  Items! ");
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
