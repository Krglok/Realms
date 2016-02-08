package net.krglok.realms.gui;

import net.krglok.realms.core.ConfigBasis;

public class DataItemColumn 
{
	protected int id;
	protected String fieldName;
	protected String fieldTitel;
	protected int fieldWidth;

	
	public DataItemColumn()
	{
		this.id = 0;
		this.fieldName = "";
		this.fieldTitel= "";
		this.fieldWidth= 0;
	}

	public DataItemColumn(int id, String fieldName, String fieldTitel, int fieldWidth)
	{
		this.id = id;
		this.fieldName = fieldName;
		this.fieldTitel= fieldTitel;
		this.fieldWidth= fieldWidth;
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


	public String getFieldTitel() {
		return fieldTitel;
	}

	public void setFieldTitel(String fieldTitel) {
		this.fieldTitel = fieldTitel;
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public String asString(int width)
	{
		return ConfigBasis.setStrleft(fieldTitel, width);
	}

}
