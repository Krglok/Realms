package net.krglok.realms.gui;

import java.lang.reflect.Field;

import net.krglok.realms.core.ConfigBasis;

public class DataItemField 
{

	protected int id; 
	private String fieldName;
	private String valueString;
	private int    valueInt;
	private Long   valueLong;
	private double valueDouble;
	private boolean valueBoolean;
	private Object valueObject;
	private DataItemFieldDef fieldDef;
	private DataItemColumn column;
	
	public DataItemField()
	{
		this.id = -1;
		fieldName = "";
		valueString = "";
		valueInt = 0;
		valueDouble = 0.0;
		valueBoolean = false;
		valueObject = null;;
		fieldDef = new DataItemFieldDef(this.id,"",Object.class.getName());
	}

//	public DataItemField(String fieldName, DataItemFieldDef fieldDef, DataItemColumn column)
//	{
//		this.id = -1;
//		this.fieldName = fieldName;
//		this.valueString = "";
//		this.valueInt = 0;
//		this.valueDouble = 0.0;
//		this.valueBoolean = false;
//		this.valueObject = null;;
//		fieldDef.id = this.id;
//		this.fieldDef = fieldDef;
//		this.column = column;
//	}

	public DataItemField( String fieldName, String fieldTyp, String fieldTitel, int fieldWidth)
	{
		this.id = -1;
		this.fieldName = fieldName;
		this.valueString = "";
		this.valueInt = 0;
		this.valueDouble = 0.0;
		this.valueBoolean = false;
		this.valueObject = null;;
		this.fieldDef = new DataItemFieldDef(id,fieldName,fieldTyp);
		fieldDef.id = this.id;
		this.column = new DataItemColumn(id, fieldName, fieldTitel, fieldWidth);
	}

	
	public DataItemField(String fieldName, String fieldTyp, String fieldTitel, int fieldWidth, Object value)
	{
		this.id = -1;
		this.fieldName = fieldName;
		this.valueString = "";
		this.valueInt = 0;
		this.valueDouble = 0.0;
		this.valueBoolean = false;
		this.valueObject = null;;
		this.fieldDef = new DataItemFieldDef(id,fieldName,fieldTyp);
		this.column = new DataItemColumn(id, fieldName, fieldTitel, fieldWidth);
		setValue(value);
	}

	public DataItemField(String fieldName, String fieldTitel, int fieldWidth, Object value)
	{
		this.id = -1;
		this.fieldName = fieldName;
		this.valueString = "";
		this.valueInt = 0;
		this.valueDouble = 0.0;
		this.valueBoolean = false;
		this.valueObject = null;
		String fieldType ;
		if (value.getClass().isEnum())
		{
			fieldType = String.class.getSimpleName(); 
		} else
		{
			fieldType = value.getClass().getSimpleName();
		}
		this.fieldDef = new DataItemFieldDef(id,fieldName,fieldType);
		this.column = new DataItemColumn(id, fieldName, fieldTitel, fieldWidth);
		setValue(value);
	}
	

	public static DataItemField initField(String name, String titel, int width, Object value)
	{
		String fieldType = value.getClass().getSimpleName();
		DataItemField field = new DataItemField(name, fieldType , titel, width, value);
		return field;
	}

	public static DataItemField initField(String fieldName, String fieldTyp, String fieldTitel, int fieldWidth)
	{
		return new DataItemField( fieldName, fieldTyp, fieldTitel, fieldWidth);
	}

	public static DataItemField initField(String name, String titel,String fieldType, int width,  Object value)
	{
		return new DataItemField(name, fieldType, titel, width, value);
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public DataItemFieldDef getFieldDef()
	{
		return fieldDef;
	}
	
	public DataItemColumn getColumn()
	{
		return column;
	}
	
	public String asString()
	{
		if( String.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueString;
		} else
		if( int.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueInt);
		} else
		if( Integer.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueInt);
		} else
		if( long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueLong);
		} else
		if( Long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueLong);
		} else
		if( double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueDouble);
		} else
		if( Double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueInt);
		} else
		if(Boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueBoolean);
		} else
		if(boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return String.valueOf(valueBoolean);
		} else
		{
			if (valueObject == null)
			{
				return "null";
			}
		}
		return valueObject.getClass().getSimpleName();
		
	}

	public String asString(int width)
	{
		return ConfigBasis.setStrleft(asString(), width);
	}
	
	
	public int asInteger()
	{
		return valueInt; 
	}
	
	public Long asLong()
	{
		return valueLong;
	}
	
	public double asDouble()
	{
		return valueDouble;
	}
	
	public boolean asBoolean()
	{
		return valueBoolean;
	}
	
	public Object asObject ()
	{
		return valueObject;
	}
	
	public void asString(String value)
	{
		this.valueString = value;
		valueObject = value;
	}

	public void asInteger(int value)
	{
		this.valueInt= value;
		valueObject = value;
	}

	public void asLong(Long value)
	{
		this.valueLong = value;
		valueObject = value;
	}
	
	public void asDouble(Double value)
	{
		this.valueDouble = value;
		valueObject = value;
	}
	
	public void asBoolean(boolean value)
	{
		this.valueBoolean = value;
		valueObject = value;
	}
	
	public void asObject(Object obj)
	{
		this.valueObject = obj;
	}

	public void setValue(Object value)
	{
		if (value == null)
		{
			valueObject = null;
			return;
		}
		if( String.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueString = (String) value; 
			
		} else
		if( int.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueInt = (int) value;
		} else
		if( Integer.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueInt = (Integer) value;
		} else
		if( long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueLong = (Long) value;
		} else
		if( Long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueLong = (Long) value;
		} else
		if( double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueDouble = (double) value;
		} else
		if( Double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueDouble = (Double) value;
		} else
		if(Boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueBoolean = (Boolean) value;
		} else
		if(boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			valueBoolean = (boolean) value;
		}
		valueObject = value;
	}
	
	public Object getValue()
	{
		if( String.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return  valueString; 
			
		} else
		if( int.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueInt;
		} else
		if( Integer.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueInt;
		} 
		String longName = long.class.getSimpleName();
//		if( long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		if( longName.equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueLong;
		} else
//		if( Long.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		longName = Long.class.getSimpleName();
		if( longName.equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueLong;
		} else
		if( double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueDouble;
		} else
		if( Double.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueDouble;
		} else
		if(Boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueBoolean;
		} else
		if(boolean.class.getSimpleName().equalsIgnoreCase(fieldDef.fieldType))
		{
			return valueBoolean;
		}
		return valueObject;
	}
	
}
