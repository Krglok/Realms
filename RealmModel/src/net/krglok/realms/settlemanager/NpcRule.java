package net.krglok.realms.settlemanager;

import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Settlement;

public class NpcRule 
{
	private String itemRef;
	private int maxValue;
	private int minValue;
	private BuildingType buildingType;
	
	public NpcRule()
	{
		itemRef = "";
		maxValue = 0;
		minValue = 0;
		buildingType = BuildingType.BUILDING_NONE;
	}

	public NpcRule(String itemRef, int maxValue, int minValue,
			BuildingType buildingType) 
	{
		super();
		this.itemRef = itemRef;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.buildingType = buildingType;
	}


	public String getItemRef() {
		return itemRef;
	}

	public void setItemRef(String itemRef) {
		this.itemRef = itemRef;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
	}

	
	public boolean checkMax(Settlement settle)
	{
		if (settle.getWarehouse().getItemList().getItem("WHEAT").value() > maxValue)
		{
			return true;
		}
		return false;
	}

	public boolean checkMin(Settlement settle)
	{
		if (settle.getWarehouse().getItemList().getItem(itemRef).value() < minValue)
		{
			return true;
		}
		return false;
	}

}
