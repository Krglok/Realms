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
	private HashMap<String,Integer> typeCapacity;
	
	public Warehouse(int itemMax)
	{
		isEnabled = false;
		this.itemMax	  = itemMax;
		itemCount = 0;
		itemList  = new ItemList();
		typeCapacity = new HashMap<String,Integer>();
	}

	public void setTypeCapacity(String itemRef, int value)
	{
		if (typeCapacity.containsKey(itemRef))
		{
			typeCapacity.put(itemRef,typeCapacity.get(itemRef)+value);
		}else
		{
			typeCapacity.put(itemRef,value);
		}
	}
	
	public int getUsedCapacity()
	{
		int value = 0;
		for (String key :  typeCapacity.keySet())
		{
			value = value + typeCapacity.get(key);
		}
		return value;
	}
	
	public void setStoreCapacity()
	{
		getTypeCapacityList().clear();
		for (Item item : itemList.values())
		{
//			int value = (item.value() / 64) + 1;
//			System.out.println("Value : "+value);
			setTypeCapacity(item.ItemRef(),((item.value() / 64) + 1));			
		}
	}

	
	public HashMap<String,Integer> getTypeCapacityList()
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
	
}
