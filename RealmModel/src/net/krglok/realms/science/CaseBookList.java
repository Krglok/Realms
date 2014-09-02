package net.krglok.realms.science;

import java.util.HashMap;

import net.krglok.realms.core.Item;

public class CaseBookList extends  HashMap<Integer, CaseBook>  
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 852931374765097814L;

	public CaseBookList()
	{
		super();
	}
	
	public void addBook(CaseBook book)
	{
		int key = book.getId();
		if (book.getId() == 0)
		{
			key = CaseBook.getCounter()+1;
			CaseBook.initCounter(key);
		}
		this.put(key, book);
		
	}
	
	
	
	public HashMap<Integer, CaseBook> getBookSection(String refId)
	{
		HashMap<Integer, CaseBook> bookList = new HashMap<Integer, CaseBook>();
		if (refId == "")
		{
			refId = "0";
		}
		for (CaseBook cBook : this.values())
		{
			if (cBook.getRefId().equalsIgnoreCase(refId))
			{
				bookList.put(cBook.getId(), cBook);
			}
		}
		
		return bookList;
	}
}
