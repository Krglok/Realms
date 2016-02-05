package net.krglok.realms.gui;

public class DataItemField 
{
	private String fieldName;
	private String valueString;
	private int    valueInt;
	private double valueDouble;
	private boolean valueBoolean;
	private Object valueObject;
	private DataItemFieldDef fieldDef;
	
	public DataItemField()
	{
		fieldName = "";
		valueString = "";
		valueInt = 0;
		valueDouble = 0.0;
		valueBoolean = false;
		valueObject = null;;
		fieldDef = new DataItemFieldDef("","","Object",0);
	}

	public DataItemField(String fieldName, DataItemFieldDef fieldDef)
	{
		this.fieldName = fieldName;
		this.valueString = "";
		this.valueInt = 0;
		this.valueDouble = 0.0;
		this.valueBoolean = false;
		this.valueObject = null;;
		this.fieldDef = fieldDef;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String toString()
	{
		if( String.class.getName()==fieldDef.fieldType)
		{
			return valueString;
		} else
		if( int.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueInt);
		} else
		if( Integer.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueInt);
		} else
		if( double.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueDouble);
		} else
		if( Double.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueInt);
		} else
		if(Boolean.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueBoolean);
		} else
		if(boolean.class.getName() == fieldDef.fieldType)
		{
			return String.valueOf(valueBoolean);
		} else
		{
		}
		return "Object";
		
	}
	
	public int toInteger()
	{
		return valueInt; 
	}
	
	public double toDouble()
	{
		return valueDouble;
	}
	
	public boolean toBoolean()
	{
		return valueBoolean;
	}
	
	public void asString(String value)
	{
		this.valueString = value;
	}

	public void asInteger(int value)
	{
		this.valueInt= value;
	}

	public void asDouble(Double value)
	{
		this.valueDouble = value;
	}
	
	public void asBoolean(boolean value)
	{
		this.valueBoolean = value;
	}
	
	public void asObject(Object obj)
	{
		this.valueObject = obj;
	}

	
}
