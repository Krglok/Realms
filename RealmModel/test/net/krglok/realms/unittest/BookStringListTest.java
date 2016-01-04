package net.krglok.realms.unittest;
import static org.junit.Assert.*;

import org.junit.Test;

import net.krglok.realms.data.BookStringList;

/**
 * Test eine spezielle ArrayList fuer die Darstellung von Booktexten.
 * die methode add erezugt eine automatische Formatierung
 * 
 * @author Krglok
 * 
 *
 */

public class BookStringListTest
{

	@Test
	public void testBookStringList()
	{
		BookStringList bookList = new BookStringList();
		
		if (bookList != null)
		{
			bookList.add("Test");
		}
		int expected = 1;
		int actual = bookList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddString()
	{
		BookStringList bookList = new BookStringList();
		
		if (bookList != null)
		{
			bookList.add("Test");
			bookList.add("[1] : DrachenhorstLeh");
			bookList.add("Store [ 27]/[243]");
			bookList.add("erweiterte Zeile mit mehr als 19 Zeichen pro Zeile und ");
		}
		System.out.println("|1234567890123456789");
		for (int i = 0; i < bookList.size(); i++)
		{
			System.out.println("|"+bookList.get(i)+"|");
		}
		int expected = 5;
		int actual = bookList.size();
		assertEquals(expected, actual);
	}

}
