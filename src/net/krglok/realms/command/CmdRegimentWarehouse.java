package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.BookStringList;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdRegimentWarehouse extends RealmsCommand
{
	int regID;
	Integer page ;

	public CmdRegimentWarehouse()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.WAREHOUSE);
		description = new String[] {
				ChatColor.YELLOW+"/regiment WAREHOUSE [RegD] [page] ",
		    	"List all Items in the Warehouse ",
		    	"  "
			};
			requiredArgs = 1;
			this.regID = 0;
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
			regID = value;
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
	    Regiment regiment = plugin.getRealmModel().getRegiments().get(regID);
	    if (regiment != null)
	    {
			msg.add("Regiment ["+regiment.getId()+"] : "+regiment.getName());
			msg.add(regiment.getName()+" Warehouse  [ "+regiment.getWarehouse().getUsedCapacity() +" ]");
		    for (String itemref : regiment.getWarehouse().sortItems())
		    {
		    	Item item = regiment.getWarehouse().getItemList().get(itemref);
	    		msg.add(ConfigBasis.setStrleft(item.ItemRef()+"__________",15)+" : "+ChatColor.YELLOW+item.value());
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
				writeBook(holdItem, msg,regiment.getName() ,"Warehouse");
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
			if (plugin.getRealmModel().getRegiments().get(regID) != null)
			{
				if (isOpOrAdmin(sender) == false)
				{
					if (isRegimentOwner(plugin, sender, regID)== false)
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
			}
			errorMsg.add("Regiment not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
