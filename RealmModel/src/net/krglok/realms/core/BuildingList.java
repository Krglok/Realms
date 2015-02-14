package net.krglok.realms.core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.kingdom.Lehen;

/**
 * <pre>
 * Verwaltet eine Liste von Buildings
 * der key ist die Building id
 * Die BuildingList macht auswertungen für die verschiedenen BuildingTypen
 * Die Auswertung erfolgt anhand der TypeValue des BuildingType bzw. der sich daraus ergebenden Gruppenummer
 * - MaxHome fuer anzahl der Bette für Siedler (100)
 * - MaxWorker fuer anzahl der Arneitsplätze (200)
 * - Max Capacity fuer den Storage (300)
 * - Max Order fuer TRADER
 * - MaxUnit  fuer Anzahl der Unitplätze (500)
 * - MaxRegiment, fuer Anzahl der Regimenter von Lehen (900)
 * 
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
	}

	public int checkId(int ref)
	{
		if (ref == 0) { ref = 1; }
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
			}
		}
		return isBuild;
	}
	
	public void putBuilding(Building building)
	{
		this.put(String.valueOf(building.getId()),building);
	}
	

	/**
	 * initialize regionList and superRegionList
	 */
	public HashMap<String,Integer> initBuildPlanList()
	{
		HashMap<String,Integer> subList = new HashMap<String,Integer>();
		BuildPlanType bType ;
		for (Building building  : this.values())
		{
			bType = building.getBuildingType();
			if (subList.containsKey(bType.name()))
			{
				subList.put(bType.name(),subList.get(bType)+1);
			} else
			{
				subList.put(bType.name(),1);
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

	public Building removeBuilding(Building building)
	{
		return this.remove(String.valueOf(building.getId())); 
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

	public HashMap<String,Integer> getBuildTypeList()
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
	 * get Sublist for Lehen. check for lehenId
	 * 
	 * @param lehen
	 * @return
	 */
	public BuildingList getSubList(Lehen lehen)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getLehenId() == lehen.getId())
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
	public BuildingList getSubList(Owner owner)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (building.getOwnerId() == owner.getId())
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

	public BuildingList getGroupSubList(int group)
	{
		BuildingList subList = new BuildingList();
		for (Building building : this.values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType()) == group)
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

	/**
	 * check for BuildingGroup 100
	 * @return  max amount of beds for settler 
	 */
	public int getMaxHome()
	{
		int result = 0;
		for (Building building : this.values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 100)
			{
				if (building.isEnabled())
				{
				result = result + building.getSettler();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * check for BuildingGroup 200
	 * @return  max amount of beds for settler 
	 */
	public int getMaxWorker()
	{
		int result = 0;
		for (Building building : this.values())
		{
			if ((BuildPlanType.getBuildGroup(building.getBuildingType()) == 200)
				|| (BuildPlanType.getBuildGroup(building.getBuildingType()) == 300)
					)
			{
				if (building.isEnabled())
				{
				result = result + building.getWorkerNeeded();
				}
			}
		}
		
		return result;
	}

	public int getMaxStorage()
	{
		int result = 0;
		for (Building building : this.values())
		{
			if (building.isEnabled())
			{
				result = result + ConfigBasis.getWarehouseItemMax(building.getBuildingType());
			}
		}
		
		return result;
	}
	
	public int getMaxOrder()
	{
		int result = 0;
		for (Building building : this.values())
		{
			if (building.isEnabled())
			{
				result = result + ConfigBasis.getOrderMax(building);
			}
		}
		return result;
	}

	public int getMaxUnit()
	{
		int result = 0;
		for (Building building : this.values())
		{
			if ((BuildPlanType.getBuildGroup(building.getBuildingType()) == 500)
					)
			{
				if (building.isEnabled())
				{
				result = result + building.getUnitSpace();
				}
			}
		}
		return result;
	}

	public ArrayList<String> sortStringList(Set<String> set)
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (String s : set)
		{
			sortedItems.add(s);
		} 
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  String.CASE_INSENSITIVE_ORDER);
		}
		return sortedItems;
	}
	
}
