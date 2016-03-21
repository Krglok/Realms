package net.krglok.realms.data;

import org.bukkit.ChatColor;

public class MessageText
{
	public static final String WRONG_ITEMNAME = "Wrong itemname !";
	public static final String REGION_NOT_FOUND = "Region not found!";
	public static final String WRONG_SETTLEMNET_ID = "Wrong Settlemnet ID ";
	public static final String WRONG_ARGUMENTS = "Wrong argument";
	public static final String NOT_ENOUGH_ARGUMENTS_FOR = "Not enough arguments for ";
	public static final String FIRST_LINE = "==============================";
	public static final String NO_PAGE = "No page found !";
	public static final String NO_PERMISSION = "You have not the right permissions";
	public static final String FILE_IO_ERROR = ": File IO error !";
	
	public static final ChatColor FIRST_LINE_COLOUR = ChatColor.GREEN;
	
	public static int pageLines = 17;

	public static boolean isLogAll = false;

	/**
	 * hold the constants for textmesaegs in plugin and Model
	 */
	public MessageText()
	{
		
	}
	
	public void loadMessagesFromFile()
	{
		System.out.println(FILE_IO_ERROR);
	}
}
