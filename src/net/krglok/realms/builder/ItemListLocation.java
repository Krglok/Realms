package net.krglok.realms.builder;
import java.util.ArrayList;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.LocationData;

/**
 * hold the items for a setChest Request
 * @author Windu
 *
 */
public class ItemListLocation 
{
	private ArrayList<Item> itemList;
	private LocationData position;
	
	public ItemListLocation(ArrayList<Item> itemList, LocationData position)
	{
		super();
		this.itemList = itemList;
		this.position = position;
	}

	public ItemListLocation(ItemList items, LocationData position)
	{
		itemList = new ArrayList<Item>();
		for (Item item : items.values())
		{
			itemList.add(item);
		}
		this.position = position;
	}
	
	public ArrayList<Item> getItemList()
	{
		return itemList;
	}


	public void setItemList(ArrayList<Item> itemList)
	{
		this.itemList = itemList;
	}


	public LocationData position()
	{
		return position;
	}


	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	/**
	 * append itemlist with item
	 * itemref are NOT unique
	 * @param item
	 */
	public void addItem (Item item)
	{
		itemList.add(item);
	}
	
	/**
	 * 
	 * @param itemRef
	 * @return
	 */
	public Item getItem(String itemRef)
	{
		for (Item item : itemList)
		{
			if (item.ItemRef().equalsIgnoreCase(itemRef))
			{
				return item;
			}
		}
		return null;
	}
	
}
