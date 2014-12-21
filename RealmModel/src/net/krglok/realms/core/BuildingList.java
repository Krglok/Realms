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
public class BuildingList  extends HashMap<String,Building>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 259384614415396799L;
//	private Map<String,Building> buildingList;
//	private ArrayList<Integer> regionList; 
//	private ArrayList<Integer> superRegionList; 
//	private HashMap<BuildPlanType,Integer> buildTypeList;
	private boolean isHall;
	
	public BuildingList()
	{
//		buildingList = new HashMap<String,Building>();
//		buildTypeList = new HashMap<BuildPlanType,Integer>();
	}

//	/**
//	 * default buildings is of Type BUILDING_NONE   
//	 * @return  List of Buildings
//	 */
//	public Map<String,Building> getBuildingList()
//	{
//		return buildingList;
//	}

//	/**
//	 * Set the BuildingList without check or 
//	 * @param buildingList
//	 */
//	public void setBuildingList(Map<String,Building> buildingList)
//	{
//		this.buildingList = buildingList;
//	}
	
	
	public int checkId(int ref)
	{
		while (this.containsKey(String.valueOf(ref)))
		{
			ref++;
		}
		Building.initCounter(ref);
		return Building.getCounter();
	}
	
	
	public boolean containRegion(int regionId)
	{
		for (Building building : this.values())
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
				this.put(String.valueOf(newId),building);
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
	
	public void putBuilding(Building building)
	{
		this.put(String.valueOf(building.getId()),building);
	}
	
	private void addBuildTypeList(BuildPlanType bType)
	{
		
	}

	/**
	 * initialize regionList and superRegionList
	 */
	public HashMap<BuildPlanType,Integer> initBuildPlanList()
	{
		HashMap<BuildPlanType,Integer> subList = new HashMap<BuildPlanType,Integer>();
		BuildPlanType bType ;
		for (Building building  : this.values())
		{
			bType = building.getBuildingType();
			if (subList.containsKey(bType))
			{
				subList.put(bType,subList.get(bType)+1);
			} else
			{
				subList.put(bType,1);
			}
		}
		return subList;
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
	 * @param id
	 * @return  Building with the id  or null 
	 */
	public Building getBuilding(int id)
	{
		return this.get(String.valueOf(id)); 
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
		for (Building building : this.values())
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
		
		return initBuildPlanList();
	}

	public Building getBuildingByRegion(int regionId)
	{
		Building building = null;
		for (Building b : this.values())
		{
			if (b.getHsRegion() == regionId)
			{
				return b;
			}
		}
		return building;
	}
	
	/**
	 * get Sublist for Settlement
	 * 
	 * @param settleId
	 * @return
	 */
	public BuildingList getSubList(int settleId)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getSettleId() == settleId)
			{
				subList.put(String.valueOf(building.getId()),building);
			}
		}
		
		return subList;
	}
	
	/**
	 * get Sublist for ownerId (playerName)
	 * @param ownerId
	 * @return
	 */
	public BuildingList getSubList(String ownerId)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getOwnerId().equalsIgnoreCase(ownerId))
			{
				subList.put(String.valueOf(building.getId()),building);
			}
		}
		
		return subList;
	}
	
	public BuildingList getSubList(BuildPlanType bType)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getBuildingType() == bType)
			{
				subList.put(String.valueOf(building.getId()),building);
			}
		}
		
		return subList;
	}

	public BuildingList getSubList(int settleId, BuildPlanType bType)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getSettleId() == settleId)
			{
				if (building.getBuildingType() == bType)
				{
					subList.put(String.valueOf(building.getId()),building);
				}
			}
		}
		
		return subList;
	}
	
}
