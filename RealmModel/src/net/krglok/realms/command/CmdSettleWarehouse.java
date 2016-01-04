package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.BookStringList;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdSettleWarehouse extends RealmsCommand
{
	int settleID;
	Integer page ;

	public CmdSettleWarehouse( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.WAREHOUSE);
		description = new String[] {
				ChatColor.YELLOW+"/settle WAREHOUSE [SettleID] [page] ",
		    	"List all Items in the Warehouse ",
		    	"  "
			};
			requiredArgs = 1;
			this.settleID = 0;
			this.page = 0;  //default value
	}

	public int getPage()
	{
		return page;
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
		case 0 :
			settleID = value;
		break;
		case 1 :
				page = value;
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
		return new String[] { int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		BookStringList msg = new BookStringList();
	    Settlement  settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
	    if (settle != null)
	    {
			msg.add("["+settle.getId()+"] : "+settle.getName()+"\n");
			
			msg.add("Store [ "+settle.getWarehouse().getUsedCapacity() +" ]/["+settle.getWarehouse().getFreeCapacity()+"]"+"\n");
		    for (String itemref : settle.getWarehouse().sortItems())
		    {
		    	Item item = settle.getWarehouse().getItemList().get(itemref);
		    	if (item.value() > 0)
		    	{
		    		msg.add(ConfigBasis.setStrleft(item.ItemRef()+"__________",12)+":"+ConfigBasis.setStrright(item.value(),5)+"\n");
		    	}
		    }
	    	if (sender instanceof Player)
			{
	    		Player player = ((Player) sender);
	        	PlayerInventory inventory = player.getInventory();
	        	ItemStack holdItem = player.getItemInHand();
	        	if (holdItem.getData().getItemType() != Material.WRITTEN_BOOK)
	        	{
	        		holdItem  = new ItemStack(Material.WRITTEN_BOOK, 1);
					inventory.addItem(holdItem);
	        	}
				writeBook(holdItem, msg, settle.getName(),"Warehouse");
    			((Player) sender).updateInventory();
			}
	    }
	    if (page == 0)
	    {
	    	page = 1;
	    }
	    page = plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().getSettlement(settleID) != null)
			{
				if (isOpOrAdmin(sender) == false)
				{
					if (isSettleOwner(plugin, sender, settleID) == false)
					{
						errorMsg.add("You are not the Owner !");
						errorMsg.add(" ");
						return false;
					}
					
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
