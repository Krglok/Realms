package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdFeudalWarehouse extends RealmsCommand
{

	private int page;
	private int lehenId;
	
	public CmdFeudalWarehouse()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.WAREHOUSE);
		description = new String[] {
				ChatColor.YELLOW+"/feudal WAREHOUSE [lehenId] [page]  ",
		    	"List Lehen warehouse  items",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
			lehenId = 0;
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
		case 0:
			lehenId = value;
			break;
		case 1:
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
		ArrayList<String> msg = new ArrayList<String>();
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
	    if (lehen != null)
	    {
			msg.add("["+lehen.getId()+"] : "+lehen.getName()+"\n");
			
			msg.add("Store [ "+lehen.getWarehouse().getUsedCapacity() +" ]/["+lehen.getWarehouse().getFreeCapacity()+"]"+"\n");
		    for (String itemref : lehen.getWarehouse().sortItems())
		    {
		    	Item item = lehen.getWarehouse().getItemList().get(itemref);
		    	if (item.value() > 0)
		    	{
		    		msg.add(ConfigBasis.setStrleft(item.ItemRef()+"__________",12)+":"+ConfigBasis.setStrright(item.value(),5)+"\n");
		    	}
		    }
			msg.add(ChatColor.ITALIC+"Required Items : "+lehen.getrequiredItems().size());
			for (String itemRef : lehen.getrequiredItems().keySet())
			{
				Item item = lehen.getrequiredItems().getItem(itemRef);
				msg.add(ChatColor.ITALIC+" -"+item.ItemRef()+" : "+item.value());
			}
	    	if (sender instanceof Player)
			{
	        	PlayerInventory inventory = ((Player) sender).getInventory();
	    		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
				writeBook(book, msg, lehen.getName(),"Warehouse");
				inventory.addItem(book);
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
		if (plugin.getData().getLehen().getLehen(lehenId) == null)
		{
			errorMsg.add(ChatColor.RED+"The lehenId is wrong !");
			return false;
		}
		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
		if (isOpOrAdmin(sender) == false)
		{
			if (plugin.getData().getLehen().getLehen(lehenId).getOwnerId() != owner.getId())
			{
				errorMsg.add(ChatColor.RED+"You are not the owner !");
				return false;
			}
		}
			
		return true;
	}

}
