package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.science.CaseBook;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsBookList extends RealmsCommand
{

	private int page = 1;
	
	public CmdRealmsBookList()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.BOOKLIST);
		description = new String[] {
				ChatColor.YELLOW+"/realms BOOKLIST {page} , list the books in sektion 0 ",
		    	"The section 0 nbooks are not linked to an settlement  ",
		    	"For settlement books use the book list of a ",
		    	"bibliothek building ",
		    	" "
			};
			requiredArgs = 0;
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
				this.page = value;
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
    	if (plugin.getRealmModel().getCaseBooks().size() == 0)
    	{
    		msg.add("No books in the list");
    		msg.add("");
    	} else
    	{
	    	for (CaseBook book : plugin.getRealmModel().getCaseBooks().values())
	    	{
	    		msg.add(book.getId()+" : "+book.getAuthor()+" | "+book.getTitel());
	    	}
    		msg.add("Counter: "+CaseBook.getCounter());
    		msg.add("");
    	}
		plugin.getMessageData().printPage(sender, msg, page);
		helpPage = "";
		page = 1;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
