package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.data.DataStoreCaseBook;
import net.krglok.realms.data.LogList;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.science.CaseBookList;

import org.junit.Test;

public class CaseBookDataTest
{
	private Boolean isOutput = false; // set this to false to suppress println

	private CaseBook makeBook(int id)
	{
		CaseBook caseBook = new CaseBook();
		caseBook.setId(id);
		caseBook.setAuthor("Windu");
		caseBook.setTitel("TestBook "+id);
		HashMap<Integer,String> pages = new HashMap<Integer,String>();
		pages.put(1,"Seite 1            Dies ist das Testbuch");
		pages.put(2,"Seite 2            Es enthält ein paar Tesseiten");
		pages.put(3,"Seite 3            ");
		pages.put(4,"Seite 4            ");
		pages.put(5,"Seite 5            ");
		pages.put(6,"Seite 6            ");
		pages.put(7,"Seite 7            ");
		caseBook.setPages(pages);
		return caseBook;
	}
	
	
	
	@Test
	public void testCaseBookWrite()
	{
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest);
		DataStoreCaseBook caseBookData = new DataStoreCaseBook(dataFolder);
		CaseBookList bookList = data.initCaseBooks();
		int key = bookList.size()+1;
		bookList.addBook(makeBook(key));
		
		int expected = 10;
		int actual = bookList.size()-1;
		isOutput = true;
		if (isOutput == true)
		{
			for (CaseBook book : bookList.values())
			{
				System.out.println(book.getId()+" : "+book.getAuthor());
				System.out.println(book.getTitel()+ " : "+book.getPages().size());
				caseBookData.writeData(bookList.get(book.getId()),String.valueOf(book.getId()));
			}
			
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testCaseBookRead()
	{
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest);
		DataStoreCaseBook caseBookData = new DataStoreCaseBook(dataFolder);
		CaseBookList bookList = data.initCaseBooks();
		
		for (CaseBook caseBook : bookList.values())
		{
			System.out.println("Book List "+bookList.size());
			System.out.print(caseBook.getId());
			System.out.print(" | ");
			System.out.print(caseBook.getTitel());
			System.out.print(" | ");
			System.out.print(caseBook.getAuthor());
			System.out.print(" | ");
			System.out.print(caseBook.getPages().size());
			System.out.println("");
		}
	}
}
