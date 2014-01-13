package net.krglok.realms.core;

import java.util.HashMap;

/**
 * 
 * @author oduda
 *
 * make the item storage and the processing for the storage
 * will be used in settlement and regiment
 * 
 */
public class Warehouse 
{
	private Boolean isEnabled;
	private int itemMax;
	private int itemCount;
	private ItemList itemList;
	private HashMap<BuildingType,Integer> typeCapacity;
	
	public Warehouse(int itemMax)
	{
		isEnabled = false;
		this.itemMax	  = itemMax;
		itemCount = 0;
		itemList  = new ItemList();
		typeCapacity = new HashMap<BuildingType,Integer>();
	}

	public void setTypeCapacity(BuildingType bType, int value)
	{
		if (typeCapacity.containsKey(bType))
		{
			typeCapacity.put(bType,typeCapacity.get(bType)+value);
		}else
		{
			typeCapacity.put(bType,value);
		}
	}
	
	public int getTypeCapacity(BuildingType bType)
	{
		if (typeCapacity.containsKey(bType))
		{
			return typeCapacity.get(bType);
		}
		return 0;
	}
	
	public HashMap<BuildingType,Integer> getTypeCapacityList()
	{
		return typeCapacity;
	}
	
	public void setTypeCapacityList(HashMap<BuildingType,Integer> capacityList)
	{
		this.typeCapacity = capacityList;
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
