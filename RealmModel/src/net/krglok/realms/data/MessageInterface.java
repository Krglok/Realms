package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.command.CommandSender;

public interface MessageInterface
{
	public Boolean isLogAll();
	
	public void setisLogAll(boolean value);
	
	/**
	 * send List<String> to player chat
	 * no formatting or counting will done
	 * a page has normally 18 lines inclusively header
	 * prepare pages before
	 * @param player
	 * @param msg
	 */
	public void sendPage(CommandSender sender, ArrayList<String> page);
	
	/**
	 * prepare output page for chat
	 * first line always repeated on each page, insert color FIRST_LINE_COLOUR
	 * a page contains 17 content lines  + 2 lines for header
	 * cut line length to 50 characters
	 * @param msg
	 * @return
	 */
	public HashMap<Integer,ArrayList<String>>  preparePage(ArrayList<String> msg);
	
	/**
	 * printout page of message list. 
	 * if pageNumber > max pages then last page will be print
	 * @param sender is the command sender
	 * @param msg , ArrayList<String> to print
	 * @param doPage , pagenumber to print
	 */
	public void printPage(CommandSender sender, ArrayList<String> msg, int pageNumber);
	
	/**
	 * generate Test page with number of lines
	 * @param lines
	 * @return list of messages
	 */
	public ArrayList<String> createTestPage(int lines);
	
	/**
	 * printout error message for no permissions
	 * @param sender
	 */
	public void errorPermission(CommandSender sender);

	public void errorArgs(CommandSender sender, RealmSubCommandType subCommand);

	public void errorArgWrong(CommandSender sender, RealmSubCommandType subCommand);

	public void errorSettleID(CommandSender sender, RealmSubCommandType subCommand);

	public void errorRegion(CommandSender sender, RealmSubCommandType subCommand);

	public void errorItem(CommandSender sender, RealmSubCommandType subCommand);
	
	public void errorFileIO(String name, Exception e);
	
	public void log(String msg);

}
