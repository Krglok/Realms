package net.krglok.realms.gui;

import java.util.HashMap;

public class DataItemFieldDefList  extends  HashMap<Integer, DataItemFieldDef>   
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataItemFieldDefList()
	{
		
	}
	

	public void addFieldDef(DataItemFieldDef fieldDef)
	{
		this.put(fieldDef.getId(), fieldDef);
	}
	
	public void putFieldDef(DataItemFieldDef fieldDef)
	{
		this.put(fieldDef.getId(), fieldDef);
		
	}

	public DataItemFieldDef getFieldDef(int index)
	{
		if (index < this.size())
		{
			return this.get(index);
		}
		return null;
	}
	
	public DataItemFieldDef getFieldDefByName(String fieldName)
	{
		for (DataItemFieldDef fieldDef : this.values())
		{
			if (fieldDef.getFieldName().equalsIgnoreCase(fieldName))
			{
				return fieldDef;
			}
		}
		
		return null;
	}
}
