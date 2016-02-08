package net.krglok.realms.gui;

public class DataItemFieldDef 
{

	protected int id; 
	protected String fieldName;
	protected String fieldType;
	
	/**
	 * 
	 */
	public DataItemFieldDef(int id)
	{
		this.id = id;
		this.fieldName = "";
		this.fieldType = "";
	}

	/**
	 * Zum synchronisieren von Field.id und FieldDef.id wird ie ID übergeben;
	 * 
	 * @param id
	 * @param fieldName
	 * @param fieldTyp
	 * @param fieldTitel
	 * @param fieldWidth
	 */
	public DataItemFieldDef(int id, String fieldName, String fieldTyp)
	{
		this.id = id;
		this.fieldName = fieldName;
		this.fieldType = fieldTyp;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) 
	{
		if( String.class.getName().equalsIgnoreCase(fieldType))
		{
			
		} else
		if( int.class.getName() == fieldType)
		{
			
		} else
		if( double.class.getName().equalsIgnoreCase(fieldType))
		{
			
		} else
		if(Boolean.class.getName().equalsIgnoreCase(fieldType))
		{
			
		} else
		{
			fieldType = Object.class.getName();
		}
		this.fieldType = fieldType;
	}

	public void setFieldTyp(Object obj)
	{
		this.fieldType = obj.getClass().getName();
	}
	
	
}
