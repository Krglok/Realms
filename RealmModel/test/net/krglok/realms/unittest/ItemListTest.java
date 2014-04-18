package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.ItemList;
import org.junit.Test;

public class ItemListTest
{
	@SuppressWarnings("unused")
	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void testItemListAdd()
	{
		ItemList itemList = new ItemList();
		String itemRef = "STONE";
		int iValue = 5;
		itemList.addItem(itemRef, iValue);
		int expected = 5;
//		itemList.depositItem(itemRef, expected);
		int actual = itemList.getValue(itemRef);
		assertEquals(expected, actual);
		
	}

	@Test
	public void testItemListDeposit()
	{
		ItemList itemList = new ItemList();
		String itemRef = "STONE";
		int expected = 5;
		itemList.depositItem(itemRef, expected);
		int actual = itemList.getValue(itemRef);
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testItemListWithdraw()
	{
		ItemList itemList = new ItemList();
		String itemRef = "STONE";
		int iValue = 5;
		itemList.addItem(itemRef, iValue);
		itemList.withdrawItem(itemRef, 3);
		int expected = 2;
		int actual = itemList.getValue(itemRef);
		assertEquals(expected, actual);
		
	}

	@Test
	public void testItemListWithdrawNot()
	{
		ItemList itemList = new ItemList();
		String itemRef = "STONE";
		int iValue = 3;
		itemList.addItem(itemRef, iValue);
		Boolean expected = false;
		Boolean actual = itemList.withdrawItem(itemRef, 5);
		assertEquals(expected, actual);
		
	}
	

	@Test
	public void testItemListCount()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		int i = 0;
		int value = 0;
		for (String itemRef : iList.keySet())
		{
			i++;
			value = value + i;
			iList.depositItem(itemRef, i);
		}
		int expected = value;
		int actual = iList.getItemCount();
		assertEquals(expected, actual);
	}

	
}
