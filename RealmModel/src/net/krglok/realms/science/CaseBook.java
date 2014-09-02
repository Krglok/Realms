package net.krglok.realms.science;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * <pre>
 * do the managment for stored books.
 * Book can be read in  from BOON&QUIL
 * and write out to BOOK&QUIL
 * 
 * @author Windu
 * </pre>
 */

public class CaseBook
{
	
	private static int COUNTER;

	private int id;
	private String refId;
	private String titel;
	private String author;
	private HashMap<Integer,String> pages;
	private boolean isEnabled ;
	
	
	
	public CaseBook()
	{
		id = 0;
		refId = "0";
		titel = "";
		author = "";
		pages = new HashMap<Integer,String>();
		setEnabled(false);
	}

	public CaseBook(int id, String refId, String titel, String author, HashMap<Integer,String> pages)
	{
		this.id = id;
		this.refId = refId;
		this.titel = titel;
		this.author = author;
		this.pages = pages;
		this.setEnabled(true);
	}

	
	/**
	 * 
	 * @return  CaseBook instances Counter
	 */
	public static Integer getCounter()
	{
		return COUNTER;
	}

	/**
	 * Overwrite instances Counter
	 * @param Counter
	 */
	public static void initCounter(int Counter)
	{
		COUNTER = Counter;
	}
	
	public static int nextCounter()
	{
		COUNTER++;
		return COUNTER;
	}

	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the refID
	 */
	public String getRefId()
	{
		return refId;
	}

	/**
	 * @param refID the refID to set
	 */
	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public String getTitel()
	{
		return titel;
	}

	public void setTitel(String titel)
	{
		this.titel = titel;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public HashMap<Integer,String> getPages()
	{
		return pages;
	}

	public void setPages(HashMap<Integer,String> lines)
	{
		this.pages = lines;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled()
	{
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	/**
	 * create a caseBook from the given written book
	 * 
	 * @param book
	 * @param caseBook
	 * @return
	 */
	public static ArrayList<String> readBook(ItemStack book, CaseBook caseBook)
	{
		
		ArrayList<String> msg = new ArrayList<String>(); 
		final BookMeta bm = (BookMeta) book.getItemMeta();
		int maxPage  = bm.getPageCount();
		
		caseBook.setAuthor(bm.getAuthor());
		caseBook.setTitel(bm.getTitle());
		
		for (int i=1; i <= maxPage; i++)
		{
			caseBook.getPages().put(i,bm.getPage(i));
			String s = bm.getPage(i);
			
			msg.add(s.substring(0, 55));
		}
		caseBook.setEnabled(true);
		return msg;
	}
	
	/**
	 * create a written book item from the caseBook
	 * 
	 * @param book
	 * @param caseBook
	 * @return
	 */
	public static ItemStack writeBook(ItemStack book, CaseBook caseBook)
	{
		final BookMeta bm = (BookMeta) book.getItemMeta();
		String sPage = "";
		ArrayList<String> lore = new ArrayList<String>();
		for (int i=1; i <= caseBook.getPages().size(); i++)
		{
			bm.addPage(caseBook.getPages().get(i));
		}
		bm.setAuthor(caseBook.getAuthor());
		bm.setTitle(caseBook.getTitel());
		lore.add("ID="+String.valueOf(caseBook.getId()));
		lore.add("REFID="+String.valueOf(caseBook.getRefId()));
		bm.setLore(lore);
		book.setItemMeta(bm);

		return book;
	}


}
