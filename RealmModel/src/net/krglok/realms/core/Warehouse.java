package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.Collections;

import net.krglok.realms.builder.BuildPlanType;


/**
 * <pre>
 * make the item storage and the processing for the storage
 * will be used in settlement and regiment
 * </pre>
 * @author oduda
 *
 * 
 */
public class Warehouse  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5203174911445774008L;
	private Boolean isEnabled;
	private int itemMax;
	private int itemCount;
	private ItemList itemList;
	private ItemList typeCapacity;
	
	public Warehouse(int itemMax)
	{
		isEnabled = false;
		this.itemMax	  = itemMax;
		itemCount = 0;
		itemList  = new ItemList();
		typeCapacity = new ItemList();
		typeCapacity.addItem(new Item("TRADER",27));
	}

	public void setTypeCapacity(String itemRef, int value)
	{
		if (typeCapacity.containsKey(itemRef))
		{
			Item item = typeCapacity.getItem(itemRef);
			item.setValue(item.value()+ value);
		}else
		{
			typeCapacity.put(itemRef, new Item(itemRef, value));
		}
	}
	
	public int getUsedCapacity()
	{
		int value = 0;
		for (String key :  typeCapacity.keySet())
		{
			value = value + typeCapacity.getValue(key);
		}
		return value;
	}
	
	public int getFreeCapacity()
	{
		int used = getUsedCapacity();
		int max  = itemMax / 64;
		return max - used;
	}
	
	public void setStoreCapacity()
	{
		getTypeCapacityList().clear();
		typeCapacity.addItem(new Item("TRADER",27));
		for (Item item : itemList.values())
		{
			setTypeCapacity(item.ItemRef(),((item.value() / 64) + 1));			
		}
	}

	
	public ItemList getTypeCapacityList()
	{
		return typeCapacity;
	}
	
	
	/**
	 * 
	 * @return  Boolean 
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * Set the enabled Status , no check for conditions
	 * @param isEnabled
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 
	 * @return  number of max allowed sum of item values
	 */
	public int getItemMax() {
		return itemMax;
	}

	/**
	 * 
	 * @param itemMax , set max allowed sum of item values 
	 */
	public void setItemMax(int itemMax) {
		if (itemMax == 0)
		{
			itemMax = ConfigBasis.getWarehouseItemMax(BuildPlanType.HALL);
		} 
		this.itemMax = itemMax;
	}

	/**
	 * 
	 * @return itemList
	 */
	public ItemList getItemList() {
		return itemList;
	}

	/**
	 * 
	 * @param itemList overwrite actual itemList
	 */
	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
		this.itemCount = itemList.getItemCount();
	}
		
	/**
	 * 
	 * @return sum of actual stored item values
	 */
	public int getItemCount()
	{
		return itemList.getItemCount();
	}

	/**
	 * add value to item value 
	 * if itemMax not reached
	 * @param itemRef
	 * @param value
	 * @return true if deposit done otherwise false
	 */
	public Boolean depositItemValue(String itemRef, int value)
	{
		if (getFreeCapacity() > (value/64))
		{
			if ((getFreeCapacity()*64) >= (itemCount+value))
			{
				itemRef = ConfigBasis.checkItemRefBuild(itemRef);
				itemList.depositItem(itemRef, value);
				itemCount = itemCount + value;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * decrement item amount with value in warehouse
	 * if item not found false return
	 * @param itemRef
	 * @param value
	 * @return true if value are withdrawn from amount
	 */
	public Boolean withdrawItemValue(String itemRef, int value)
	{
		int actual = itemList.getValue(itemRef);
		if ((actual-value) >= 0)
		{
			itemList.withdrawItem(itemRef, value);
			itemCount = itemCount - value;
			return true;
		}
		
		return false;
	}
	
	/**
	 * search for ItemList in warehouse 
	 * dont check for amount
	 * @param searchList
	 * @return  found Items in Warehouse with amount
	 */
	public ItemList findItemsInWarehouse(ItemList searchList)
	{
		ItemList resultList = new ItemList();
		for (Item search : searchList.values())
		{
			for (Item item :  this.getItemList().values())
			{
				if (item.ItemRef().equalsIgnoreCase(search.ItemRef()))
				{
					resultList.addItem(new Item(item.ItemRef(), item.value()) );
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * search items in Warehouse and check requested amount
	 *  
	 * @param searchList
	 * @return all items form searchList that NOT in warehouse
	 */
	public ItemList searchItemsNotInWarehouse(ItemList searchList)
	{
		ItemList resultList = new ItemList();
		for (Item search : searchList.values())
		{
//			System.out.println("SearchInList "+search.ItemRef());
			String itemRef = ConfigBasis.checkItemRefBuild(search.ItemRef());
			if (itemList.getValue(itemRef) < search.value())
			{
				resultList.addItem(new Item(search.ItemRef(), search.value()) );
			}
		}
		
		return resultList;
	}
	
	public ArrayList<String> sortItems()
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (String s : this.itemList.keySet())
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
	
	public ItemList searchItemsInWarehouse(ItemList searchList)
	{
		ItemList resultList = new ItemList();
		for (Item search : searchList.values())
		{
//			System.out.println("SearchInList "+search.ItemRef());
			if (itemList.getValue(search.ItemRef()) > search.value())
			{
				resultList.addItem(new Item(search.ItemRef(), itemList.getValue(search.ItemRef())) );
			}
		}
		
		return resultList;
	}

	

}
