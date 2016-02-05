package net.krglok.realms.gui;

public class DataItemFieldDef 
{
	protected String fieldName;
	protected String fieldType;
	
	/**
	 * 
	 */
	public DataItemFieldDef()
	{
		this.fieldName = "";
		this.fieldType = "";
	}

	/**
	 * 
	 * @param fieldName
	 * @param fieldTyp
	 * @param fieldTitel
	 * @param fieldWidth
	 */
	public DataItemFieldDef(String fieldName, String fieldTyp, String fieldTitel, int fieldWidth)
	{
		this.fieldName = fieldName;
		this.fieldType = fieldTyp;
	}
	
	/**
	 * 
	 * @param fieldName
	 * @param fieldTyp
	 * @param fieldTitel
	 */
	public DataItemFieldDef(String fieldName, String fieldTyp, String fieldTitel)
	{
		this.fieldName = fieldName;
		this.fieldType = fieldTyp;
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
		if( String.class.getName()==fieldType)
		{
			
		} else
		if( int.class.getName() == fieldType)
		{
			
		} else
		if( double.class.getName() == fieldType)
		{
			
		} else
		if(Boolean.class.getName() == fieldType)
		{
			
		} else
		{
			fieldType = Object.class.getName();
		}
		this.fieldType = fieldType;
	}

	
	
}
