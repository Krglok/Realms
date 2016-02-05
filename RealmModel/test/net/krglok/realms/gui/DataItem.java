package net.krglok.realms.gui;

import net.krglok.realms.core.ConfigBasis;

public class DataItem
{
	protected int id;
    protected DataItemFieldList fields;
    protected DataItemFieldDefList fieldDefs;
//    protected 

    public DataItem() 
    {
    	this.id = 0;
        this.fields = new DataItemFieldList();
        fieldDefs = new DataItemFieldDefList();
    }

    public DataItemField fieldByName(String fieldName)
    {
    	for (DataItemField field : fields.values())
    	{
    		if (field.getFieldName().equalsIgnoreCase(fieldName))
    		{
    			return field;
    		}
    	}
    	return null;
    }
    
    public DataItemField getField(int index)
    {
    	if (index < fields.size())
    	{
    		return fields.get(index);
    	}
    	return null;
    }
    
}
