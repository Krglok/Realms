package net.krglok.realms.gui;

import net.krglok.realms.core.ConfigBasis;

public class DataItem
{
	protected int id;
    protected DataItemFieldList fields;
    protected DataItemColumnList columns;
    protected DataItemFieldDefList fieldDefs;
    protected Object itemObject;
//    protected 

    public DataItem() 
    {
    	this.id = 0;
        this.fields = new DataItemFieldList();
        fieldDefs = new DataItemFieldDefList();
        columns = new DataItemColumnList();
    }
    
    public DataItemFieldDefList getFieldDefs()
    {
    	return fieldDefs;
    }
    
    public DataItemColumnList getColumns()
    {
    	return columns;
    }
    
    public int getId()
    {
    	return id;
    }

    public void setItemObject(Object object)
    {
    	itemObject = object;
    }
    
    public Object getItemObject()
    {
    	return itemObject;
    }
    
	public int addField(DataItemField field)
	{
		this.fields.addField(field);
		this.fieldDefs.addFieldDef(field.getFieldDef());
		this.columns.addColumn(field.getColumn());
		return field.id;
	}
    
    public DataItemField getFieldByName(String fieldName)
    {
    	return fields.getFieldByName(fieldName);
    }
    
    public DataItemField getField(int index)
    {
    	if (index < fields.size())
    	{
    		return fields.get(index);
    	}
    	return null;
    }

    public DataItemColumn getColumnByName(String fieldName)
    {
    	return columns.getColumnByName(fieldName);
    }
    
    public DataItemFieldList fields()
    {
    	return fields;
    }
    
    public DataItemFieldDefList fieldDefs()
    {
    	return fieldDefs;
    }
    
    public DataItemColumnList columns()
    {
    	return columns;
    }

}
