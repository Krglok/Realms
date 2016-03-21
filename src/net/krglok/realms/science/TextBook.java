package net.krglok.realms.science;

import java.util.HashMap;

/**
 * <pre>
 * 
 * </pre>
 * @author olaf.duda
 * created : 23.07.2015
 *
 */

public class TextBook
{

	private static int COUNTER;

	private int id;
	private String refId;
	private String group;
	private String titel; 
	private String author;
	private HashMap<Integer,String> pages;
	private boolean isEnabled ;
	
	public TextBook()
	{
		refId = "0";
		group = "";
		titel = "";
		author = "";
		pages = new HashMap<Integer,String>();
		setEnabled(false);
		
	}

	public TextBook(int id)
	{
		this.id = id; 
		refId = "";
		group = "";
		titel = "";
		author = "";
		pages = new HashMap<Integer,String>();
		setEnabled(false);
		
	}
	
	
	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	public String getRefId()
	{
		return refId;
	}

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

	public HashMap<Integer, String> getPages()
	{
		return pages;
	}

	public void setPages(HashMap<Integer, String> pages)
	{
		this.pages = pages;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the group
	 */
	public String getGroup()
	{
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group)
	{
		this.group = group;
	}

	
	
}
