package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdOwnerList  extends RealmsCommand
{

	private int page;
	
	public CmdOwnerList()
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/owner LIST [page]  ",
		    	"List the registered Owners   ",
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
		msg.add("ID |  |Nobility  |Commonlevel  |   | Player      |  [ "+plugin.getData().getOwners().size()+" ]");
		for (Owner owner : plugin.getData().getOwners().values())
		{
			if (owner != null)
			{
				if (owner.isUser)
				{
		    		msg.add(owner.getId()
		    				+" | "+ChatColor.GREEN+owner.getKingdomId()
		    				+" | "+ChatColor.GOLD+owner.getNobleLevel()
		    				+" | "+ChatColor.YELLOW+owner.getCommonLevel()
		    				+" | "+owner.getPlayerName()
		    				);
				}
			}
		}
		for (Owner owner : plugin.getData().getOwners().values())
		{
			if (owner != null)
			{
				if (owner.isUser == false)
				{
		    		msg.add(owner.getId()
		    				+" | "+ChatColor.YELLOW+owner.getKingdomId()
		    				+" | "+ChatColor.GOLD+owner.getNobleLevel()
		    				+" | "+ChatColor.YELLOW+owner.getCommonLevel()
		    				+" | "+owner.getPlayerName()
		    				);
				}
			}
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
			writeBook(holdItem, msg, "Realms","Ownerlist");
			inventory.addItem(holdItem);
			((Player) sender).updateInventory();
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// always true, everyone can use this command
		return true;
	}

}
