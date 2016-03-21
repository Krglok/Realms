package net.krglok.realms.core;

import java.util.ArrayList;

import net.krglok.realms.Common.Item;

/**
 * list of items, can hold multiple items of the same type
 * @author Windu
 *
 */
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
	
	/**
	 * add all elements of newLIst to this list
	 * dont add existing ItemRef
	 * 
	 * @param newList
	 */
	public void addAll(ItemArray newList)
	{
		for (Item item : newList)
		{
			if (containItem(item) == false)
			{
				this.add(item);
			}
		}
	}
	
	/**
	 * check for ItemRef in List
	 * 
	 * @param item
	 * @return
	 */
	public boolean containItem(Item item)
	{
		for (Item existItem : this)
		{
			if (existItem.ItemRef().equalsIgnoreCase(item.ItemRef()))
			{
				return true;
			}
		}
		return false;
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
