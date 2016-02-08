package net.krglok.realms.gui;

import java.util.HashMap;

public class DataItemColumnList extends  HashMap<Integer, DataItemColumn>  
{

	private static int COUNTER = 0;
	private static final long serialVersionUID = -3299724806305770392L;

	public DataItemColumnList()
	{
		COUNTER = 0;
	}

	public int getCounter()
	{
		return COUNTER;
	}
	
	
	public int addColumn(DataItemColumn column)
	{
		this.put(column.id, column);
		return column.id; 
	}
	
	public DataItemColumn getColumnByName(String fieldName)
	{
		for (DataItemColumn column : this.values())
		{
			if (column.fieldName.equalsIgnoreCase(fieldName))
			{
				return column;
			}
		}
		return null;
	}

	public DataItemColumn getColumn(int index)
	{
		if (index < this.size())
		{
			return this.get(index);
		}
		
		return null;
	}
}
