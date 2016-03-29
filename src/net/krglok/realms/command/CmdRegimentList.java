package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.BookStringList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdRegimentList extends aRealmsCommand
{

	private int page;
	
	public CmdRegimentList()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/regiment LIST [page] ",
				"List all regiments ",
		    	"  ",
		};
		requiredArgs = 0;
		page = 1;
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
		return new String[] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		BookStringList msg1 = new BookStringList();
		RegimentList  rList = plugin.getRealmModel().getRegiments();
	    if (rList.size() > 0)
	    {
			msg.add("ID |Regiment | Status | Owner [ "+rList.size()+" ]");
		    for (Regiment regiment : rList.values())
		    {
	    		msg.add(regiment.getId()
	    				+" : "+ChatColor.YELLOW+regiment.getName()
	    				+" : "+ChatColor.GOLD+regiment.getRegStatus().name()
	    				+" Owner: "+regiment.getOwner()
	    				+" in "+regiment.getPosition().getWorld()
	    				);
	    		msg1.add(regiment.getId()
	    				+" : "+regiment.getName()
	    				);
		    }
	    } else
	    {
			msg.add("ID |Settlement | Active | Owner [ null ]");
	    	msg.add("/regiment LIST [page] ");
	    	msg.add("NO regiments found !!");
	    }
    	if (sender instanceof Player)
		{
    		Player player = ((Player) sender);
        	PlayerInventory inventory = player.getInventory();
        	ItemStack holdItem = player.getItemInHand();
        	if (holdItem.getData().getItemType() != Material.WRITTEN_BOOK)
        	{
    		 holdItem  = new ItemStack(Material.WRITTEN_BOOK, 1);
        	}
			writeBook(holdItem, msg1, "Realms","Regimentlist");
			inventory.addItem(holdItem);
			((Player) sender).updateInventory();
		}
	    
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
