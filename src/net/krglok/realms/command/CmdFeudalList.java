package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdFeudalList extends aRealmsCommand
{

	private int page;
	
	public CmdFeudalList()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/feudal LIST [page]  ",
		    	"List the registered Lehen   ",
		    	"only Admins can create Lehen ",
		    	"or the owner of a kingdom ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
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
		return new String[] { int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("ID |kingdom|Name        |Owner   |Lord|  [ "+plugin.getData().getLehen().size()+" ]");
		for (Lehen lehen : plugin.getData().getLehen().values())
		{
    		msg.add(lehen.getId()
    				+" | "+ChatColor.GOLD+lehen.getKingdomId()
//    				+" | "+ChatColor.GOLD+lehen.getNobleLevel()
    				+" | "+ChatColor.YELLOW+lehen.getName()
    				+" | "+ChatColor.YELLOW+lehen.getOwnerId()
    				+" | "+ChatColor.GREEN+lehen.getParentId()
    				);
		}
    	if (sender instanceof Player)
		{
    		Player player = ((Player) sender);
        	PlayerInventory inventory = player.getInventory();
        	ItemStack holdItem = player.getItemInHand();
        	if (holdItem.getData().getItemType() != Material.BOOK_AND_QUILL)
        	{
    		 holdItem  = new ItemStack(Material.WRITTEN_BOOK, 1);
        	}
			writeBook(holdItem, msg, "Realms","Lehenlist");
			inventory.addItem(holdItem);
			((Player) sender).updateInventory();
		}
		
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		//Anyone can use this command
		return true;
	}

}
