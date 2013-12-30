package net.krglok.realms.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Verwaltet eine Liste von Buildings
 * der key ist die Building id
 * zusätzlich wird eine liste der buildings vom typ region
 * und eine Liste der buildings von typ superregion 
 * geführt
 * 
 * @author Windu
 *
 */
public class BuildingList
{
	private Map<String,Building> buildingList;
	private ArrayList<Integer> regionList; 
	private ArrayList<Integer> superRegionList; 
	
	private boolean isHall;
	
	public BuildingList()
	{
		buildingList = new HashMap<String,Building>();
		regionList = new ArrayList<Integer>();
		superRegionList = new ArrayList<Integer>();
	}

	/**
	 * the inlude NO null objects
	 * default buildings is of Type BUILDING_NONE   
	 * @return  List of Buildings
	 */
	public Map<String,Building> getBuildingList()
	{
		return buildingList;
	}

	/**
	 * Set the BuildingList without check or 
	 * @param buildingList
	 */
	public void setBuildingList(Map<String,Building> buildingList)
	{
		this.buildingList = buildingList;
	}
	
	/**
	 * Clear the buildingList without checks 
	 * After clear the List is Empty
	 * clear implicit the regionList 
	 */
	public void clearBuildingList()
	{
		this.buildingList.clear();
		this.regionList.clear();
		this.superRegionList.clear();
	}
	
	/**
	 * 
	 * @return  true if no building in the list
	 */
	public Boolean isEmpty()
	{
		return buildingList.isEmpty();
	}
	
	/**
	 * store the building in the List
	 * if building = null then a default building with buildingType = None 
	 * id build by buildingList.size()+10000;
	 * @param building
	 * @return true if building successful build
	 */
	public Boolean addBuilding(Building building)
	{
		Boolean isBuild = false;
		if (building != null)
		{
			buildingList.put(String.valueOf(building.getId()),building);
			if (building.isRegion())
			{
				regionList.add(building.getId());
			}else
			{
				superRegionList.add(building.getId());
			}
			if (building.getBuildingType() == BuildingType.BUILDING_HALL)
			{
				isHall = true;
			}
			isBuild = true;
		}
		return isBuild;
	}

	/**
	 * initialize regionList and superRegionList
	 */
	public void initRegionLists()
	{
		this.regionList.clear();
		this.superRegionList.clear();
		for (Building building  : buildingList.values())
		{
			if (building.isRegion())
			{
				regionList.add(building.getId());
			}else
			{
				superRegionList.add(building.getId());
			}
		}
	}

	/**
	 * 
	 * @return  list of Building_id as array of integer
	 */
	public Integer[] getRegionArray()
	{
		return regionList.toArray(new Integer[regionList.size()]);
	}

	/**
	 * 
	 * @return  list of Building_id as array of integer
	 */
	public Integer[] getSuperRegionArray()
	{
		return superRegionList.toArray(new Integer[superRegionList.size()]);
	}

	/**
	 * 
	 * @return  number of buildings in List
	 */
	public int size()
	{
		return buildingList.size();
	}
	
	/**
	 * 
	 * @param id
	 * @return  Building with the id  or null 
	 */
	public Building getBuilding(int id)
	{
		Building b = buildingList.get(String.valueOf(id)); 
		return b;
		
	}

	public static BuildingList createRegionBuildings(HashMap<String,String> regionTypes, BuildingList buildings)
	{
		
		
		return buildings;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHall()
	{
		return isHall;
	}

	/**
	 * set flag isHall , check if BUILDING_HALL in buildingList
	 * @param isHall
	 */
	public void setHall(boolean isHall)
	{
		for (Building building : buildingList.values())
		{
			if (building.getBuildingType() == BuildingType.BUILDING_HALL)
			{
				isHall = true;
			}
		}
		this.isHall = isHall;
	}
}
