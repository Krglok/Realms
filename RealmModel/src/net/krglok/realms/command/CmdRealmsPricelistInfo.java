package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

public class CmdRealmsPricelistInfo extends RealmsCommand
{
	private int page;

	public CmdRealmsPricelistInfo()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.PRICELIST);
		description = new String[] {
				ChatColor.YELLOW+"/realms PRICELIST {page}, show the pricelist  ",
		    	"All items are listed in the central pricelist  ",
		    	"The list has many pages. ",
		    	" "
			};
			requiredArgs = 1;
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
		msg.add("== Pricelist size :"+plugin.getData().getPriceList().size());
		Player player = (Player) sender;
		PlayerInventory inventory = player.getInventory();
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

		msg.add(ConfigBasis.setStrleft(" Pricelist for ", 19));
		msg.add(ConfigBasis.setStrleft(" Settlements : §0", 19));
		for (String itemRef : plugin.getData().getPriceList().sortItems())
		{
			ItemPrice item = plugin.getData().getPriceList().get(itemRef);
			String sLine = "";
			sLine = ConfigBasis.setStrleft(item.ItemRef()+"_________",10)+":§a "+item.getFormatedBasePrice()+"§0 ";
			msg.add(sLine);
			
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		if (sender instanceof Player)
		{
			writeBook(book, msg, "Realm Admin","The Pricelist");
			inventory.addItem(book);
		}
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			return false;
		}
		return true;
	}

}
