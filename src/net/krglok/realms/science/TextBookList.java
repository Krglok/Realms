package net.krglok.realms.science;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * <pre>
 * 
 * </pre>
 * @author olaf.duda
 * created : 23.07.2015

 */

public class TextBookList extends  HashMap<Integer,TextBook>
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextBookList()
	{
		super();
		
	}

	/**
	 * insert textBook onbject into list.
	 * overwrite if id if exists
	 * 
	 * @param textBook
	 */
	public void addTextBook(TextBook textBook)
	{
		int key = textBook.getId();
		this.put(key, textBook);
	}
	
	
	/**
	 * The subList make no copy of the TextBook objects
	 * 
	 * @param refGroup
	 * @return list of TextBook with refGroup 
	 */
	public TextBookList getGroupList(String refGroup)
	{
		TextBookList  subList = new TextBookList();
		for (TextBook item : this.values())
		{
			if (item.getGroup().equalsIgnoreCase(refGroup))
			{
				subList.addTextBook(item);
			}
		}
		return subList;
	}
	
	/**
	 * get a 18 char substring from a String
	 * 
	 * @param in
	 * @param offset
	 * @return
	 */
	private String getSubString(String in, int offset)
	{
		String out = "";
		
		if (in.length() < 19)
		{
			out = in;
		} else
		{
			int beginIndex = offset;
			int endIndex = offset+17;
			out = in.substring(beginIndex, endIndex);
		}
		
		return out;
	}
	
	/**
	 * split a text line into parts of 18 char
	 * 
	 * @param page
	 * @return
	 */
	private ArrayList<String> getLines(String page)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int max = page.length();
		if (max < 19)
		{
			msg.add(page);
		} else
		{
			int index = 0;
			while (index < max)
			{
				String sub = getSubString(page, index)+" ";
				msg.add(sub);
				index = index + 18;
			}
		}
		return msg;
	}

	/**
	 * Write a multi line text into a BookAndQuill item
	 * the lines are split into 18 char parts and store as line
	 * in the BookAndQuill item.
	 * There are no special format rules, simple cut off lines 
	 * 
	 * @param book
	 * @param textBook
	 * @return
	 */
	public ItemStack writeBook(ItemStack book, TextBook textBook)
	{
//		ArrayList<String> msg, String author, String title
		final BookMeta bm = (BookMeta) book.getItemMeta();
		String sPage = "";
		int line = 0;
		int bookPage = 0;
		ArrayList<String> msg = new ArrayList<String>();
		// get pages from
		for (int i = 0; i < textBook.getPages().size(); i++)
		{
			// splitt page into lines of 18 char
			msg = getLines(textBook.getPages().get(i));
			for (int j = 0; j < msg.size(); j++)
			{
				// add lines to bookPage
				line++;
				sPage = sPage + msg.get(j);
				if ((line > 11) && (bookPage < 50))
				{
					bm.addPage(sPage);
					sPage = "";
					line = 0;
					bookPage++;
				}
			}
		}
		if ((sPage != "") && (bookPage < 50))
		{
			bm.addPage(sPage);
		}
		bm.setAuthor(textBook.getAuthor());
		bm.setTitle(textBook.getTitel());
		book.setItemMeta(bm);

		return book;
	}
	
}
