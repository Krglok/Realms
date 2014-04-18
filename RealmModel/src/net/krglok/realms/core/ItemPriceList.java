package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * list of items with prices , used as central pricelist  
 * @author Windu
 *
 */
public class ItemPriceList extends HashMap<String,ItemPrice>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -970781088522071013L;

	public ItemPriceList()
	{
		
	}
	
	/**
	 * create new Item and add to List
	 * overwrite existing data
	 * @param itemRef
	 * @param price
	 */
	public void add(String itemRef, Double price)
	{
		ItemPrice item = new ItemPrice(itemRef, price);
		this.put(item.ItemRef(), item);
	}
	
	/**
	 * add Item to List
	 * overwrite existing data
	 * @param item
	 */
	public void add(ItemPrice item)
	{
		this.put(item.ItemRef(), item);
	}
	
	/**
	 * 
	 * @param itemRef
	 * @return basePrice
	 */
	public Double getBasePrice(String itemRef)
	{
		ItemPrice item = this.get(itemRef);
		if (item != null)
		{
			return item.getBasePrice();
		}
		return 0.0;
	}

	public double getPriceOfList(ItemList items)
	{
		double sum = 0.0;
		for (Item item : items.values())
		{
			sum = sum + (this.getBasePrice(item.ItemRef()) * item.value());
		}
		
		return sum;
	}
	
	public ArrayList<String> sortItems()
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (String s : this.keySet())
		{
			sortedItems.add(s);
		}
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  String.CASE_INSENSITIVE_ORDER);
		}
		return sortedItems;
	}

}
