package net.krglok.realms.npc;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;

/**
 * <pre>
 * Das backpacjk ist eine variante von Itemlist.
 * Es ist kein Inventory !
 * Es kann jedes Item nur 1x aufnehmen mit einem MAX_ITEM_STACK.
 * Wird das MAX_ITEM_STACK überschritten, dann wird das Item nicht eingelagert !
 * 
 * @author Windu
 * </pre>
 */
public class BackpackList extends ItemList
{
	public static int MAX_BACKPACH_ITEM = 27;
	public static int MAX_ITEM_STACK = 64;
	
	public boolean isSpace()
	{
		if (this.size() < MAX_BACKPACH_ITEM)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean addItem(Item item)
	{
		if (isSpace())
		{
			if (item.value() <= MAX_ITEM_STACK)
			{
				return addItem(item.ItemRef(), item.value());
			}
		}
		return false;
	}
	
	@Override
	public boolean addItem(String itemRef, int iValue)
	{
		if (isSpace())
		{
			if (this.containsKey(itemRef) == false)
			{
				if (iValue <= MAX_ITEM_STACK)
				{
					this.put(itemRef, new Item(itemRef, iValue));
					this.itemCount = itemCount + iValue;
					return true;
				}
			} else
			{
				if ((this.get(itemRef).value()+iValue) <= MAX_ITEM_STACK)
				{
					this.put(itemRef, new Item(itemRef, iValue));
					this.itemCount = itemCount + iValue;
					return true;
				}
			}
			
		}
		return false;
	}

	
	public ItemList asItemList()
	{
		ItemList itemList = new ItemList();
		for (Item item : this.values())
		{
			itemList.put(item.ItemRef(), item);
		}
		return itemList;
	}
}
