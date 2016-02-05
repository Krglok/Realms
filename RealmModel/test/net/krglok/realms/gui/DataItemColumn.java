package net.krglok.realms.gui;

public class DataItemColumn 
{
	protected String fieldName;
	protected String fieldTitel;
	protected int fieldWidth;

	
	public DataItemColumn()
	{
		this.fieldName = "";
		this.fieldTitel= "";
		this.fieldWidth= 0;
		this.fieldName = fieldName;
		this.fieldTitel= fieldTitel;
		this.fieldWidth= fieldWidth;
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

	
}
