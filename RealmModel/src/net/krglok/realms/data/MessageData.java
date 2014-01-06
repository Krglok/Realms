package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import net.krglok.realms.model.RealmCommandType;
import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class PageList extends HashMap<Integer,ArrayList<String>>
{
	public PageList()
	{
	}
}

public class MessageData
{
	private static final String FILE_IO_ERROR = ": File IO error !";
	private static final String REGION_NOT_FOUND = "Region not found!";
	private static final String WRONG_SETTLEMNET_ID = "Wrong Settlemnet ID ";
	private static final String WRONG_ARGUMENTS = "Wrong argument";
	private static final String NOT_ENOUGH_ARGUMENTS_FOR = "Not enough arguments for ";
	private static final String FIRST_LINE = "==============================";
	private static final String NO_PAGE = "No page found !";
	private static final ChatColor FIRST_LINE_COLOUR = ChatColor.GREEN;
	private static final String NO_PERMISSION = "You have not the right permissions";
	
	private static int pageLines = 17;

	private Logger log;
	private boolean isLogAll = false;
	
	public MessageData(Logger logger)
	{
		this.log = logger;
	}

	public Boolean isLogAll()
	{
		return this.isLogAll;
	}
	
	public void setisLogAll(boolean value)
	{
		this.isLogAll = value;
	}
	
	/**
	 * send List<String> to player chat
	 * no formatting or counting will done
	 * a page has normally 18 lines inclusively header
	 * prepare pages before
	 * @param player
	 * @param msg
	 */
	public void sendPage(CommandSender sender, ArrayList<String> page)
	{
		if (page != null)
		{
			for (String line : page)
			{
				sender.sendMessage(line);
				if (isLogAll)
				{
					log.info(line);
				}
			}
		} else
		{
			sender.sendMessage(NO_PAGE);
			log.info(NO_PAGE);
		}
	}
	
	/**
	 * prepare output page for chat
	 * first line always repeated on each page, insert color FIRST_LINE_COLOUR
	 * a page contains 17 content lines  + 2 lines for header
	 * cut line length to 50 characters
	 * @param msg
	 * @return
	 */
	public PageList  preparePage(ArrayList<String> msg)
	{
		PageList pages = new PageList();
		if (msg.size() == 0)
		{
			return pages;
		}
		String header =  msg.get(0);
		if (header.length() > 50)
		{
			header = String.copyValueOf(header.toCharArray(), 1, 50);
		}
		int pageMax = ((msg.size()-1)/pageLines)+1;
		int pageNr = 1;
		int lineCount = 0;
		ArrayList<String> pageLine = new ArrayList<String>();
		
		for (int i = 1; i < msg.size(); i++)
		{

			// add header to page
			if (lineCount == 0)
			{
				pageLine.add(FIRST_LINE_COLOUR+FIRST_LINE+" ["+pageNr+"/"+pageMax+"]");
				pageLine.add(header);
			}
			lineCount++;
			String sLine = msg.get(i);
			if (sLine.length() > 50)
			{
				sLine = String.copyValueOf(sLine.toCharArray(), 1, 50);
			}
			pageLine.add(sLine);
			if (lineCount == pageLines)
			{
				pages.put(pageNr, pageLine);
				lineCount = 0;
				pageNr++;
				pageLine = new ArrayList<String>();
			}
		}
		pages.put(pageNr, pageLine);
		
		return pages;
	}
	
	/**
	 * printout page of message list. 
	 * if pageNumber > max pages then last page will be print
	 * @param sender is the command sender
	 * @param msg , ArrayList<String> to print
	 * @param doPage , pagenumber to print
	 */
	public void printPage(CommandSender sender, ArrayList<String> msg, int pageNumber)
	{
		if (msg.size() > 0)
		{
			PageList pages = preparePage(msg);
			if (pageNumber > pages.size())
			{
				pageNumber = pages.size()-1;
			}
			if (pageNumber < 1)
			{
				pageNumber = 1;
			}
			ArrayList<String> page = pages.get(pageNumber);
			sendPage(sender, page);
		} else
		{
			sender.sendMessage("NO data in message !");
		}
	}
	
	/**
	 * generate Test page with number of lines
	 * @param lines
	 * @return list of messages
	 */
	public ArrayList<String> createTestPage(int lines)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Test Page for Page Output");
		for (int i = 1; i < 37; i++)
		{
			msg.add("Message "+i);
		}
		return msg;
	}
	
	/**
	 * printout error message for no permissions
	 * @param sender
	 */
	public void errorPermission(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED+NO_PERMISSION);
		log.info(NO_PERMISSION);
	}

	public void errorArgs(CommandSender sender, RealmSubCommandType subCommand)
	{
		sender.sendMessage(ChatColor.RED+NOT_ENOUGH_ARGUMENTS_FOR+" "+subCommand);
	}

	public void errorArgWrong(CommandSender sender, RealmSubCommandType subCommand)
	{
		sender.sendMessage(ChatColor.RED+WRONG_ARGUMENTS+" "+subCommand);
	}

	public void errorSettleID(CommandSender sender, RealmSubCommandType subCommand)
	{
		sender.sendMessage(ChatColor.RED+WRONG_SETTLEMNET_ID+" "+subCommand);
		
	}

	public void errorRegion(CommandSender sender, RealmSubCommandType subCommand)
	{
		sender.sendMessage(ChatColor.RED+REGION_NOT_FOUND+" "+subCommand);
	}

	public void errorFileIO(String name, Exception e)
	{
		log.info(name+ FILE_IO_ERROR);
		log.info(e.getMessage());
	}
	
	public void log(String msg)
	{
		if (isLogAll)
		{
			log.info(msg);
		}
	}
}
