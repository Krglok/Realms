package core;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author oduda
 *
 */
public class ItemList  //extends  HashMap<String, Integer>
{
	private Map<String, Integer> itemList;

	public ItemList()
	{
		setItemList(new HashMap<String, Integer>());
	}

	public Map<String, Integer> getItemList() {
		return itemList;
	}

	public void setItemList(Map<String, Integer> itemList) {
		this.itemList = itemList;
	}
	
	public Item addItem(String itemRef, int iValue)
	{
		Item item = new Item(itemRef, iValue);
		itemList.put(itemRef, iValue);
		return item;
	}
	
	public Item getItem(String itemRef)
	{
		Item item;
		if (itemList.containsKey(itemRef))
		{
			item = new Item(itemRef, itemList.get(itemRef));
		} else
		{
			item = null; 
		}
		return item;
	}
	
	public Boolean isEmpty()
	{
		return itemList.isEmpty();
	}

}
