package net.krglok.realms.science;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.core.ItemArray;

public class EvolveNode
{
	private EvolveType evolveType;
	private String valueType;
	private String valueName;
	private ItemArray valueList;
	
	
	public EvolveNode()
	{
		this.evolveType = EvolveType.NONE;
		valueType = "";
		valueName = "";
		valueList = new ItemArray();
		
	}

	public EvolveNode(EvolveType evolveType,
			String valueType,
			String valueName,
			ItemList valueList
			)
	{
		this.evolveType = evolveType;
		valueType = valueType;
		valueName = valueName;
		valueList = valueList;
		
	}

	
	public EvolveType getEvolveType()
	{
		return evolveType;
	}


	public void setEvolveType(EvolveType evolveType)
	{
		this.evolveType = evolveType;
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
		if (valueList.size() > 0)
		{
			return valueList.get(0).ItemRef();
		}
		return "";
	}


	public void setValue(String value)
	{
		
		Item item = valueList.get(0);
		item.setItemRef(value);
		this.valueList.set(0,item );
	}


	public ItemArray getValueList()
	{
		return valueList;
	}


	public void setValueList(ItemArray valueList)
	{
		this.valueList = valueList;
	}


	
	
}
