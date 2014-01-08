package net.krglok.realms.core;

import java.util.HashMap;

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

	
}
