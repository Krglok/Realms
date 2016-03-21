package net.krglok.realms.science;

import net.krglok.realms.Common.ItemList;

public class EvolveRequirement
{
	private EvolveRequirementType evolveRequirementType;
	private String valueType;
	private String valueName;
	private String value;
	private ItemList valueList;
	private Boolean isList;
	
	
	
	public EvolveRequirement()
	{
		this.evolveRequirementType = EvolveRequirementType.NONE;
		valueType = "";
		valueName = "";
		value	  = "";
		valueList = new ItemList();
		isList	  = false;
	}

	public EvolveRequirement(EvolveRequirementType evolveRequirementType,
			String valueType,
			String valueName,
			String value,
			ItemList valueList,
			Boolean isList
	)
	{
		this.evolveRequirementType = evolveRequirementType;
		this.valueType = valueType;
		this.valueName = valueName;
		this.value	  = value;
		this.valueList = valueList;
		this.isList	  = isList;
	}
	


	public EvolveRequirementType getEvolveRequirementType()
	{
		return evolveRequirementType;
	}



	public void setEvolveRequirementType(EvolveRequirementType evolveRequirementType)
	{
		this.evolveRequirementType = evolveRequirementType;
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
