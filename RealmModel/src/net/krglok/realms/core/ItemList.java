package net.krglok.realms.core;

import java.util.HashMap;

/**
 * 
 * @author oduda
 *
 */
public class ItemList  extends  HashMap<String, Item>
{
/**
	 * 
	 */
	private static final long serialVersionUID = -3351533128792676311L;
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
	 * if exist values are  overwrite
	 * @param item
	 */
	public void addItem(Item item)
	{
		this.put(item.ItemRef(), item);
//		Item item = new Item(itemRef, iValue);
//		return item;
	}
	
	/**
	 * add item to the list
	 * if exist values are  overwrite
	 * @param itemRef
	 * @param iValue
	 * @return added item or null
	 */
	public void addItem(String itemRef, int iValue)
	{
		this.put(itemRef, new Item(itemRef, iValue));
		itemCount = itemCount + iValue;
//		Item item = new Item(itemRef, iValue);
//		return item;
	}
	
	/**
	 * add value to the item in the list
	 * if not exist add item
	 * @param itemRef
	 * @param iValue
	 * @return
	 */
	public Item putItem(String itemRef, int iValue)
	{
		Item item;
		if (this.containsKey(itemRef))
		{
			item = this.get(itemRef);
			item.setValue(item.value()+iValue);
			itemCount = itemCount + iValue;
			return item; 
		}
		item = new Item(itemRef,iValue);
		this.addItem(item);
		return item;
	}
	
	/**
	 * 
	 * @param itemRef
	 * @return
	 */
	public Item getItem(String itemRef)
	{
		return this.get(itemRef);
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
	public void depositItem(String itemRef, int iValue)
	{
		Item item;
		if (containsKey(itemRef))
		{
			item = this.get(itemRef);
//			System.out.println("deposit: "+iValue);
			item.setValue(item.value()+iValue);
//			put(itemRef, get(itemRef)+value);
		} else
		{
			this.addItem(itemRef, iValue);
		}
		itemCount = itemCount + iValue;
	}

	/**
	 * bucht value von item ab , 
	 * wenn bestand > 0
	 * @param itemRef
	 * @param value
	 * @return
	 */
	public Boolean withdrawItem(String itemRef, int value)
	{
		int actual = 0;
		if(this.containsKey(itemRef))
		{
			actual = this.get(itemRef).value();
		}
		if ((actual-value) >= 0)
		{
			value = value * -1;
//			System.out.println("withdraw: "+value);
			this.depositItem(itemRef, (value));
			return true;
		}
		
		return false;
	}
	
	public int getValue(String itemRef)
	{
		if (containsKey(itemRef))
		{
			return get(itemRef).value();
		} else
		{
			return 0;
		}
	}
	
	public void setValue(String itemRef, int value)
	{
		putItem(itemRef, value);
	}
	
//	public int size()
//	{
//		return itemList.size();
//	}
	
//	/**
//	 * search in the itemlist for subString of search
//	 * @param search
//	 * @return result list of item , null if not found 
//	 */
//	public ItemList getSubList(String search)
//	{
//		ItemList subList = new ItemList();
//		for (String ref : keySet())
//		{
//			if (ref.contains(search))
//			{
//				subList.addItem(ref, get(ref));
//			}
//		}
//		
//		return subList;
//	}

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
		for (Item item : this.values())
		{
			itemCount = itemCount + item.value();
		}
	}
	
	
}
