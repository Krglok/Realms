package net.krglok.realms.command;
import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.science.CaseBook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;



public class CmdRealmsBookRead extends RealmsCommand
{

	public CmdRealmsBookRead()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.READ);
		description = new String[] {
				ChatColor.YELLOW+"/realms READ , read the book in your hand ",
		    	"The text will stored into the BookList  ",
		    	"With this command you read books into the Global Library ",
		    	"that is the RefId = 0 , means no assotiated settlement",
		    	"the BookList is stored in the database"
			};
			requiredArgs = 0;
	}

	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{

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
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		msg.add("== Read Book ==");
		Player player = (Player) sender;
    	if (player.getItemInHand().getType() == Material.WRITTEN_BOOK)
    	{
    		ItemStack book = player.getItemInHand();
    		final BookMeta bm = (BookMeta) book.getItemMeta();
    		if (bm.getAuthor() == "")
    		{
    			msg.add(ChatColor.RED+"The author is required for read in Booklist !");
    			msg.add("");
    			plugin.getMessageData().printPage(sender, msg, 1);
    			return;
    		}
    		if (bm.getTitle() == "")
    		{
    			msg.add(ChatColor.RED+"The title is required for read in Booklist !");
    			msg.add("");
    			plugin.getMessageData().printPage(sender, msg, 1);
    			return;
    		}
    		int id  = CaseBook.nextCounter();
			String refId = "0"; // Kein setlement
			
			CaseBook caseBook = new CaseBook(id, refId, "", "", new HashMap<Integer,String>());
			msg.add(ChatColor.GREEN+"Title : "+bm.getTitle());
			msg.add(ChatColor.YELLOW+"Author: "+bm.getAuthor());
			msg.addAll(CaseBook.readBook(book, caseBook));
			
			plugin.getRealmModel().getCaseBooks().addBook(caseBook);
			plugin.getRealmModel().getData().writeCaseBook(caseBook);
    	} else
    	{
			msg.add(ChatColor.RED+"You must hold a written book in hand!");
			msg.add("");
    		
    	}
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		if (sender.isOp() == false)
		{
			msg.add(ChatColor.RED+"You must be OP to use this command!");
			msg.add("");
			plugin.getMessageData().printPage(sender, msg, 1);
			return false;
		}
		return true;
	}

}
