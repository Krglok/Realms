package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.science.CaseBook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdRealmsBook extends RealmsCommand
{

	private int Id;
	
	public CmdRealmsBook()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.BOOK);
		description = new String[] {
				ChatColor.YELLOW+"/realms BOOK [ID], create a new Book from BookList  ",
		    	"The stored Text are stored into the new Book  ",
		    	"The ID is the BookId in the Booklist "
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
				this.Id = value;
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
		return new String[] { int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	if (Id == 0)
    	{
    		msg.add("Id not valid !  ");
    		msg.add("See /realms booklist   for appropriate values");
    		msg.add("");
    	} else
    	{
	    	CaseBook caseBook = plugin.getRealmModel().getCaseBooks().get(Id);
	    	if (caseBook != null)
	    	{
	    		Player player = (Player) sender;
	    		PlayerInventory inventory = player.getInventory();
	    		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
	    		CaseBook.writeBook(book, caseBook);
	    		msg.add("Create Book "+caseBook.getId()+" : "+caseBook.getAuthor()+" | "+caseBook.getTitel());
				inventory.addItem(book);
	    	}
    		msg.add("");
    	}
		plugin.getMessageData().printPage(sender, msg, 1);
		Id = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
