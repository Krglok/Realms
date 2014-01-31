package net.krglok.realms.settlemanager;

import net.krglok.realms.core.BuildingType;

/**
 * 
 * @author Windu
 *
 */
public class ProductBuilding 
{
	private String itemRef;
	private BuildingType buildingType;
	private int buildingId;
	
	public ProductBuilding()
	{
		itemRef = "";
		buildingType = BuildingType.BUILDING_NONE;
		buildingId = 0;
	}

	public ProductBuilding(
			String itemRef, 
			BuildingType buildingType,
			int buildingId) 
	{
		super();
		this.itemRef = itemRef;
		this.buildingType = buildingType;
		this.buildingId = buildingId;
	}

	public String getItemRef() {
		return itemRef;
	}

	public void setItemRef(String itemRef) {
		this.itemRef = itemRef;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	
	
}
