package net.krglok.realms.core;

import java.io.Serializable;
import java.util.HashMap;

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
		if (itemMax >= (itemCount+value))
		{
			itemList.depositItem(itemRef, value);
			itemCount = itemCount + value;
			return true;
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
}
