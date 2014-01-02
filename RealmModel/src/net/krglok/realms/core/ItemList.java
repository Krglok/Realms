package net.krglok.realms.core;

import java.util.HashMap;

/**
 * 
 * @author oduda
 *
 */
public class ItemList  extends  HashMap<String, Integer>
{
//	private Map<String, Integer> itemList;
	private int itemCount;

	public ItemList()
	{
//		itemList = new HashMap<String, Integer>();
		itemCount = 0;
	}

//	public Map<String, Integer> getItemList() {
//		return itemList;
//	}
//
//	public void setItemList(Map<String, Integer> itemList) {
//		this.itemList = itemList;
//	}
	
	/**
	 * add item to the list
	 * if exist values are not overwrite
	 * @param itemRef
	 * @param iValue
	 * @return added item or null
	 */
	public Item addItem(String itemRef, int iValue)
	{
		this.put(itemRef, iValue);
		Item item = new Item(itemRef, iValue);
		return item;
	}
	
	
	/**
	 * 
	 * @param itemRef
	 * @return
	 */
	public Item getItem(String itemRef)
	{
		Item item;
		if (containsKey(itemRef))
		{
			item = new Item(itemRef, get(itemRef));
		} else
		{
			item = null; 
		}
		return item;
	}
		
//	public Boolean isEmpty()
//	{
//		return itemList.isEmpty();
//	}

	/**
	 * add value to item value
	 * if item not exist in list it will be created
	 * @param itemRef
	 * @param value
	 */
	public void depositItem(String itemRef, int value)
	{
		if (containsKey(itemRef))
		{
			put(itemRef, get(itemRef)+value);
		} else
		{
			put(itemRef, value);
		}
		itemCount = itemCount + value;
	}

	public Boolean withdrawItem(String itemRef, int value)
	{
		int actual = 0;
		if(this.containsKey(itemRef))
		{
			actual = this.get(itemRef);
		}
		if ((actual-value) >= 0)
		{
			this.put(itemRef, (actual - value));
			return true;
		}
		
		return false;
	}
	
	public int getValue(String itemRef)
	{
		if (containsKey(itemRef))
		{
			return get(itemRef);
		} else
		{
			return 0;
		}
	}
	
	public void setValue(String itemRef, int value)
	{
		put(itemRef, value);
	}
	
//	public int size()
//	{
//		return itemList.size();
//	}
	
	/**
	 * search in the itemlist for subString of search
	 * @param search
	 * @return result list of item , null if not found 
	 */
	public ItemList getSubList(String search)
	{
		ItemList subList = new ItemList();
		for (String ref : keySet())
		{
			if (ref.contains(search))
			{
				subList.addItem(ref, get(ref));
			}
		}
		
		return subList;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	/**
	 * calculate item count new form item values
	 */
	public void setItemCount()
	{
		itemCount = 0;
		for (Integer value : values())
		{
			itemCount = itemCount + value;
		}
	}
	
	
}
