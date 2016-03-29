package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.BookStringList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdSettleList extends aRealmsCommand
{
	private int page;
	
	public CmdSettleList( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/settle LIST [page] ",
				"List all Settlements ",
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
		SettlementList  rList = plugin.getRealmModel().getSettlements();
	    if (rList.count() > 0)
	    {
			msg.add("ID|Settlement | Type | Owner     |  [ "+rList.size()+" ]");
		    for (Settlement settle : rList.values())
		    {
	    		msg.add(settle.getId()
	    				+" |"+ChatColor.YELLOW+ConfigBasis.setStrleft(settle.getName()+"___________",12)
	    				+" |"+ChatColor.GREEN+ConfigBasis.setStrleft(settle.getSettleType().name(),4)
	    				+ChatColor.YELLOW+"| "+ConfigBasis.setStrright(settle.getOwnerId(),3)
	    				+ChatColor.YELLOW+"| "+ChatColor.DARK_PURPLE+settle.getKingdomId()
	    				+ChatColor.YELLOW+" in "+settle.getPosition().getWorld());
	    		msg1.add(settle.getId()
	    				+" |"+ConfigBasis.setStrleft(settle.getName()+"___________",12)
	    				+" |"+ConfigBasis.setStrleft(settle.getSettleType().name(),4)
	    				+"| "+settle.getKingdomId()
	    				);
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
				writeBook(holdItem, msg1, "Realms","Settlements");
				((Player) sender).updateInventory();
			}
	    } else
	    {
			msg.add("ID |Settlement | TYPE | Owner [ World ]");
	    	msg.add("/settle LIST [page] ");
	    	msg.add("NO settlements found !!");
	    }
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
