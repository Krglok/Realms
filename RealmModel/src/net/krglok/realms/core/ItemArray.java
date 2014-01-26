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
		for (int i = 0; i < this.size(); i++)
		{
			if (this.get(i).ItemRef().equals(itemRef))
			{
				return this.get(i);
			}
		}
		return new Item("",0);
	}
	
	@SuppressWarnings("unused")
	public void putItem(String itemRef, int iValue)
	{
		for (int i = 0; i < this.size(); i++)
		{
			if (this.get(i).ItemRef().equals(itemRef))
			{
				this.getItem(itemRef).setValue(this.getItem(itemRef).value()+iValue);
			}
			return;
		}
		addItem( itemRef,  iValue);
	}

	@SuppressWarnings("unused")
	public void setItem(String itemRef, int iValue)
	{
		for (int i = 0; i < this.size(); i++)
		{
			if (this.get(i).ItemRef().equals(itemRef))
			{
				this.getItem(itemRef).setValue(iValue);
			}
			return;
		}
		addItem( itemRef,  iValue);
	}
}
