package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.logging.Logger;

import net.krglok.realms.RealmsSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageTest implements MessageInterface
{
	public MessageTest()
	{
	}

	public Boolean isLogAll()
	{
		return MessageText.isLogAll;
	}
	
	public void setisLogAll(boolean value)
	{
		MessageText.isLogAll = value;
	}
	
	/**
	 * send List<String> to player chat
	 * no formatting or counting will done
	 * a page has normally 18 lines inclusively header
	 * prepare pages before
	 * @param player
	 * @param msg
	 */
	public void sendPage(  ArrayList<String> page)
	{
		if (page != null)
		{
			for (String line : page)
			{
				System.out.println(line);
				if (MessageText.isLogAll)
				{
					System.out.println(line);
				}
			}
		} else
		{
			System.out.println(MessageText.NO_PAGE);
			System.out.println(MessageText.NO_PAGE);
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
	public HashMap<Integer,ArrayList<String>>  preparePage(ArrayList<String> msg)
	{
		HashMap<Integer,ArrayList<String>> pages = new HashMap<Integer,ArrayList<String>>();
		if (msg.size() == 0)
		{
			return pages;
		}
		String header =  msg.get(0);
		if (header.length() > 50)
		{
			header = String.copyValueOf(header.toCharArray(), 1, 50);
		}
		int pageMax = ((msg.size()-1)/MessageText.pageLines)+1;
		int pageNr = 1;
		int lineCount = 0;
		ArrayList<String> pageLine = new ArrayList<String>();
		
		for (int i = 1; i < msg.size(); i++)
		{

			// add header to page
			if (lineCount == 0)
			{
				pageLine.add(MessageText.FIRST_LINE_COLOUR+MessageText.FIRST_LINE+" ["+pageNr+"/"+pageMax+"]");
				pageLine.add(header);
			}
			lineCount++;
			String sLine = msg.get(i);
			if (sLine.length() > 50)
			{
				sLine = String.copyValueOf(sLine.toCharArray(), 1, 50);
			}
			pageLine.add(sLine);
			if (lineCount == MessageText.pageLines)
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
	public void printPage(  ArrayList<String> msg, int pageNumber)
	{
		if (msg.size() > 0)
		{
			HashMap<Integer,ArrayList<String>> pages = preparePage(msg);
			if (pageNumber > pages.size())
			{
				pageNumber = pages.size()-1;
			}
			if (pageNumber < 1)
			{
				pageNumber = 1;
			}
			ArrayList<String> page = pages.get(pageNumber);
			sendPage(page);
		} else
		{
			System.out.println("NO data in message !");
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
		sender.sendMessage(ChatColor.RED+MessageText.NO_PERMISSION);
		System.out.println(MessageText.NO_PERMISSION);
	}

	public void errorArgs(RealmsSubCommandType subCommand)
	{
		System.out.println(ChatColor.RED+MessageText.NOT_ENOUGH_ARGUMENTS_FOR+" "+subCommand);
	}

	public void errorArgWrong(  RealmsSubCommandType subCommand)
	{
		System.out.println(ChatColor.RED+MessageText.WRONG_ARGUMENTS+" "+subCommand);
	}

	public void errorSettleID(  RealmsSubCommandType subCommand)
	{
		System.out.println(ChatColor.RED+MessageText.WRONG_SETTLEMNET_ID+" "+subCommand);
		
	}

	public void errorRegion(  RealmsSubCommandType subCommand)
	{
		System.out.println(ChatColor.RED+MessageText.REGION_NOT_FOUND+" "+subCommand);
	}

	public void errorItem(  RealmsSubCommandType subCommand)
	{
		System.out.println(ChatColor.RED+MessageText.WRONG_ITEMNAME+" "+subCommand);
	}
	
	public void errorFileIO(String name, Exception e)
	{
		System.out.println(name+ MessageText.FILE_IO_ERROR);
		System.out.println(e.getMessage());
	}
	
	public void log(String msg)
	{
		if (MessageText.isLogAll)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void sendPage(CommandSender sender, ArrayList<String> page)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int printPage(CommandSender sender, ArrayList<String> msg,
			Integer pageNumber)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void errorArgs(CommandSender sender, RealmsSubCommandType subCommand)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorArgWrong(CommandSender sender,
			RealmsSubCommandType subCommand)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorSettleID(CommandSender sender,
			RealmsSubCommandType subCommand)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorRegion(CommandSender sender, RealmsSubCommandType subCommand)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorItem(CommandSender sender, RealmsSubCommandType subCommand)
	{
		// TODO Auto-generated method stub
		
	}

}
