package net.krglok.realms.core;

import java.util.ArrayList;

public class ItemArray extends ArrayList<Item>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7213096475060542956L;

	public ItemArray()
	{
		
	}

	public Item addItem(String itemRef, int iValue)
	{
		Item item = new Item(itemRef, iValue);
		this.add(item);
		return item;
	}
	
	public Item getItem(String itemRef)
	{
		Item item ;
		for (int i = 0; i < this.size(); i++)
		{
			item = this.get(i); 
			if (item.ItemRef() == itemRef)
			{
				return item;
			}
		}
		return new Item("",0);
	}
	
}
