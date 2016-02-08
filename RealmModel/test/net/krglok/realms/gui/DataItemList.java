package net.krglok.realms.gui;

import java.util.HashMap;

/**
 * Die Liste wird von 0 bis n gefüllt. Der Index wird automatisch vergeben.
 * 
 * @author Windu
 *
 */
public class DataItemList  extends  HashMap<Integer, DataItem> 
{
	private static int COUNTER;

	private static final long serialVersionUID = 1L;

    protected DataItemFieldDefList fieldDefs;
    protected DataItemColumnList columns;
	
	public DataItemList()
	{
		COUNTER = 0;
	}
	
	public DataItem getItem(int index)
	{
		if (index < this.size())
		{
			return this.get(index);
		}
		
		return null;
	}
	
	public int addItem(DataItem item)
	{
		item.id = COUNTER;
		COUNTER++;
		this.put(item.id, item);
		if (this.fieldDefs == null)
		{
			this.fieldDefs = item.getFieldDefs();
		}
		if (this.columns == null)
		{
			this.columns = item.getColumns();
		}
		
		return item.getId();
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
	
	public DataItemFieldDefList getFieldDefs()
	{
		return this.fieldDefs;
	}
	
	public void setFieldDefs(DataItemFieldDefList fieldDefs)
	{
		this.fieldDefs = fieldDefs;
	}
	
	public DataItemColumnList getColumns()
	{
		return columns;
	}

	public void setColumns(DataItemColumnList columns)
	{
		this.columns = columns;
	}
	
	
}
