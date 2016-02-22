package net.krglok.realms.science;

import net.krglok.realms.core.ItemList;

public class EvolveAction
{

	private EvolveActionType evolveActionType;
	private String valueType;
	private String valueName;
	private String value;
	private ItemList valueList;
	private Boolean isList;
	
	public EvolveAction()
	{
		evolveActionType = EvolveActionType.NONE;
		valueType = "";
		valueName = "";
		value	  = "";
		valueList = new ItemList();
		isList	  = false;
		
	}


	public EvolveAction(EvolveActionType evolveActionType,
						String valueType,
						String valueName,
						String value,
						ItemList valueList,
						Boolean isList
				)
	{
		this.evolveActionType = evolveActionType;
		this.valueType = valueType;
		this.valueName = valueName;
		this.value	  = value;
		this.valueList = valueList;
		this.isList	  = isList;
	}
	
	public EvolveActionType getEvolveActionType()
	{
		return evolveActionType;
	}

	public void setEvolveActionType(EvolveActionType evolveActionType)
	{
		this.evolveActionType = evolveActionType;
	}

	public String getValueType()
	{
		return valueType;
	}

	public void setValueType(String valueType)
	{
		this.valueType = valueType;
	}

	public String getValueName()
	{
		return valueName;
	}

	public void setValueName(String valueName)
	{
		this.valueName = valueName;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public ItemList getValueList()
	{
		return valueList;
	}

	public void setValueList(ItemList valueList)
	{
		this.valueList = valueList;
	}

	public Boolean getIsList()
	{
		return isList;
	}

	public void setIsList(Boolean isList)
	{
		this.isList = isList;
	}


	
}
