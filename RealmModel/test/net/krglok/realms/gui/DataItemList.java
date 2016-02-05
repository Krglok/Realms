package net.krglok.realms.gui;

import java.util.HashMap;

public class DataItemList  extends  HashMap<Integer, DataItem> 
{
	private static int COUNTER;

	private static final long serialVersionUID = 1L;

	public DataItemList()
	{
	}
	
	public DataItem getItem(int index)
	{
		if (index < this.size())
		{
			return this.get(index);
		}
		
		return null;
	}
	
	public void addItem(DataItem item)
	{
		if (item.id < 1)
		{
			item.id = COUNTER++;
		}
		this.put(item.id, item);
	}
	
	public void putItem(DataItem item)
	{
		if (item.id < 1)
		{
			addItem(item);
		}
		this.put(item.id, item);
	}
	
	public void removeItem(int index)
	{
		if (this.get(index)!=null)
		{
			this.remove(index);
		}
	}
	
	public void removeList(int[] indexList)
	{
		for (int index : indexList)
		{
			if (this.get(index)!=null)
			{
				this.remove(index);
			}
		}
	}
}
