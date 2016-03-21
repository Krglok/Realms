package net.krglok.realms.gui;

import java.util.HashMap;

/**
 * Die Liste wird von 0 bis n gefüllt. Der Index wird automatisch vergeben.
 * 
 * @author Windu
 *
 */

public class DataItemFieldList extends  HashMap<Integer, DataItemField>  
{
	private static int COUNTER = 0;
	private static final long serialVersionUID = 1L;
	
	public DataItemFieldList()
	{
		COUNTER = 0;
	}
	
	public int getCounter()
	{
		return COUNTER;
	}

	public int addField(DataItemField field)
	{
		field.id = COUNTER;
		field.getColumn().id = COUNTER;
		field.getFieldDef().id = COUNTER;
		this.put(field.id, field);
		COUNTER++;
		return field.id;
	}

	public void putField(DataItemField field)
	{
		this.put(field.id, field);
	}
	
	public DataItemField getField(int index)
	{
		if (index < this.size())
		{
			return this.get(index);
		}
		return null;
	}
	
    public DataItemField getFieldByName(String fieldName)
    {
    	for (DataItemField field : this.values())
    	{
    		if (field.getFieldName().equalsIgnoreCase(fieldName))
    		{
    			return field;
    		}
    	}
    	return null;
    }

	public String[] getFieldNames()
	{
		String[] fieldNames = new String[this.values().size()];
		for (Integer key : this.keySet())
		{
			DataItemField field = this.get(key);
			fieldNames[key] = field.getFieldName();
		}
		return fieldNames;
	}
	
	public Object[] getFieldValues()
	{
		Object[] fieldValues = new Object[this.values().size()];
		for (Integer key : this.keySet())
		{
			DataItemField field = this.get(key);
			fieldValues[key] = field.getValue();
		}
		return fieldValues;
	}
    
}
