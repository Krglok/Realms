package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

public class CmdRealmsInfoPricelist extends RealmsCommand
{
	private int page;

	public CmdRealmsInfoPricelist()
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
		for (ItemPrice item : plugin.getData().getPriceList().values())
		{
			msg.add(item.ItemRef()+ " : "+item.getFormatedBasePrice());
		}
		if (msg.size() < 2)
		{
			msg.add("== ");
		}
		Player player = (Player) sender;
		PlayerInventory inventory = player.getInventory();
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

		final BookMeta bm = (BookMeta) book.getItemMeta();
		msg.add(ConfigBasis.setStrleft("Trade  Pricelist ", 19));
		msg.add(ConfigBasis.setStrleft("of the Settlements ", 19));
		for (ItemPrice item : plugin.getData().getPriceList().values())
		{
			String sLine = "";
			sLine = ConfigBasis.setStrleft(item.ItemRef()+"________",9)+":§a "+item.getFormatedBasePrice()+"§0 ";
			msg.add(sLine);
			
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
		if (sender instanceof Player)
		{
			String sPage = "";
			int line = 0;
			int bookPage = 0;
			for (int i=0; i < msg.size(); i++)
			{
				line++;
				sPage = sPage+msg.get(i);
				if ((line > 12) && (bookPage < 50))
				{
					bm.addPage(sPage);
					sPage = "";
					line = 0;
					bookPage++;
				}
			}
			if ((sPage != "") && (bookPage < 50))
			{
				bm.addPage(sPage);
			}
			bm.setAuthor("Realm Admin");
			bm.setTitle("The Pricelist");
			book.setItemMeta(bm);
			inventory.addItem(book);
		}
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
