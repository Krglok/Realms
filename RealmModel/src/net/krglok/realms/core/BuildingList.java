package net.krglok.realms.core;


import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.builder.BuildPlanType;

/**
 * <pre>
 * Verwaltet eine Liste von Buildings
 * der key ist die Building id
 * zusätzlich wird eine liste der buildings vom typ region
 * und eine Liste der buildings von typ superregion 
 * geführt
 * </pre>
 * @author Windu
 *
 */
public class BuildingList  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 259384614415396799L;
	private Map<String,Building> buildingList;
//	private ArrayList<Integer> regionList; 
//	private ArrayList<Integer> superRegionList; 
	private HashMap<BuildPlanType,Integer> buildTypeList;
	private boolean isHall;
	
	public BuildingList()
	{
		buildingList = new HashMap<String,Building>();
		buildTypeList = new HashMap<BuildPlanType,Integer>();
	}

	/**
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
//		this.regionList.clear();
//		this.superRegionList.clear();
	}
	
	public int checkId(int ref)
	{
		while (buildingList.containsKey(String.valueOf(ref)))
		{
			ref++;
		}
		Building.initCounter(ref);
		return Building.getCounter();
	}
	
	/**
	 * 
	 * @return  true if no building in the list
	 */
	public Boolean isEmpty()
	{
		return buildingList.isEmpty();
	}
	
	public boolean containRegion(int regionId)
	{
		for (Building building : buildingList.values())
		{
			if (building.getHsRegion() == regionId)
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * !!! Normally used by the automatic. USE instead settlement.addBuilding()
	 * store the building in the List
	 * if building = null then a default building with buildingType = None 
	 * @param building
	 * @return true if building successful build
	 */
	public Boolean addBuilding(Building building)
	{
		Boolean isBuild = false;
		if (building != null)
		{
			if(containRegion(building.getHsRegion()) == false)
			{
				int newId = checkId(building.getId());
				building.setId(newId);
				buildingList.put(String.valueOf(newId),building);
				if (building.getBuildingType() == BuildPlanType.HALL)
				{
					isHall = true;
				}
				isBuild = true;
				addBuildTypeList(building.getBuildingType());
			}
		}
		return isBuild;
	}
	
	private void addBuildTypeList(BuildPlanType bType)
	{
		if (buildTypeList.containsKey(bType))
		{
			buildTypeList.put(bType,buildTypeList.get(bType)+1);
		} else
		{
			buildTypeList.put(bType,1);
		}
		
	}

	/**
	 * initialize regionList and superRegionList
	 */
	public void initBuildPlanList()
	{
		this.buildTypeList.clear();
		for (Building building  : buildingList.values())
		{
			addBuildTypeList(building.getBuildingType());
		}
	}

	/**
	 * 
	 * @return  list of Building_id as array of integer
	 */
//	public Integer[] getRegionArray()
//	{
//		return regionList.toArray(new Integer[regionList.size()]);
//	}

	/**
	 * 
	 * @return  list of Building_id as array of integer
	 */
//	public Integer[] getSuperRegionArray()
//	{
//		return superRegionList.toArray(new Integer[superRegionList.size()]);
//	}

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
			if (building.getBuildingType() == BuildPlanType.HALL)
			{
				isHall = true;
			}
		}
		this.isHall = isHall;
	}

	public HashMap<BuildPlanType,Integer> getBuildTypeList()
	{
		return buildTypeList;
	}

	public Building getBuildingByRegion(int regionId)
	{
		Building building = null;
		for (Building b : buildingList.values())
		{
			if (b.getHsRegion() == regionId)
			{
				return b;
			}
		}
		return building;
	}
}
