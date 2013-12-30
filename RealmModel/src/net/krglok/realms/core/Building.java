package net.krglok.realms.core;

import java.util.HashMap;

import net.krglok.realms.data.ConfigInterface;
import net.krglok.realms.data.ServerData;
import net.krglok.realms.data.ServerInterface;

/**
 * 
 * @author oduda
 *
 */
public class Building
{
	private static final int SETTLER_COUNT = 4; 
	private static double SETTLER_TAXE = 1.0;
	private static double TRADER_TAXE = 5.0;
	private static double TAVERNE_TAXE = 7.0;
	
	private static int COUNTER;


	private int id;
	private BuildingType buildingType;
	private int settler;
	private int workerNeeded;
	private int workerInstalled;
	private Boolean iSRegion;
	private int hsRegion;
	private String hsRegionType;
	private String hsSuperRegion;
	private Boolean isEnabled;
	private boolean isActiv;


	private ItemArray slots ;
	
	
	public Building()
	{
		COUNTER++;
		id			= COUNTER;
		this.buildingType = BuildingType.BUILDING_NONE;
		setSettlerDefault(buildingType);
		setWorkerDefault(buildingType);
		workerInstalled = 0;
		iSRegion 		= true;
		hsRegion		= 0;
		hsRegionType	= "";
		hsSuperRegion	= "";
		isEnabled		= true;
		isActiv 	    = false;
		slots = new ItemArray();
	}
	
	public Building(BuildingType buildingType, String regionType, boolean isRegion)
	{
		COUNTER++;
		id			= COUNTER;
		this.buildingType = buildingType;
		setSettlerDefault(buildingType);
		setWorkerDefault(buildingType);
		workerInstalled = 0;
		iSRegion 		= isRegion;
		this.hsRegionType	= regionType;
		hsRegion		= 0;
		hsSuperRegion	= "";
		isEnabled		= true;
		isActiv 	    = false;
		slots = new ItemArray();
	}

	
	public Building(int id, BuildingType buildingType, int settler,
			int workerNeeded, int workerInstalled, Boolean isRegion,
			int hsRegion, String hsRegionType, String hsSuperRegion, 
			Boolean isEnabled)
	{
		super();
		this.id = id;
		this.buildingType = buildingType;
		this.settler = settler;
		this.workerNeeded = workerNeeded;
		this.workerInstalled = workerInstalled;
		this.iSRegion = isRegion;
		this.hsRegion = hsRegion;
		this.hsSuperRegion = hsSuperRegion;
		this.isEnabled = isEnabled;
		isActiv 	    = false;
		slots = new ItemArray();
	}
	
	public Building(int id, BuildingType buildingType, int settler,
			int workerNeeded, int workerInstalled, Boolean isRegion,
			int hsRegion, String hsRegionType, String hsSuperRegion, 
			Boolean isEnabled, String slot1, String slot2, String slot3, 
			String slot4, String slot5)
	{
		super();
		this.id = id;
		this.buildingType = buildingType;
		this.settler = settler;
		this.workerNeeded = workerNeeded;
		this.workerInstalled = workerInstalled;
		this.iSRegion = isRegion;
		this.hsRegion = hsRegion;
		this.hsSuperRegion = hsSuperRegion;
		this.isEnabled = isEnabled;
		isActiv 	    = false;
		slots = new ItemArray();
	}

	private void setWorkerDefault(BuildingType buildingType)
	{
		switch(buildingType)
		{
		case BUILDING_NONE : setWorkerNeeded(0);
		break;
		case BUILDING_HALL : setWorkerNeeded(0);
		break;
		case BUILDING_HOME : setWorkerNeeded(0);
		break;
		case BUILDING_PROD : setWorkerNeeded(1);
		break;
		case BUILDING_BAUERNHOF : setWorkerNeeded(5);
		break;
		case BUILDING_WERKSTATT : setWorkerNeeded(5);
		break;
		case BUILDING_MILITARY : setWorkerNeeded(5);
		break;
		case BUILDING_WAREHOUSE : setWorkerNeeded(5);
		break;
		case BUILDING_TRADER : setWorkerNeeded(5);
		break;
		case BUILDING_GOVERNMENT : setWorkerNeeded(1);
		break;
		case BUILDING_ENTERTAIN : setWorkerNeeded(2);
		break;
		case BUILDING_EDUCATION : setWorkerNeeded(2);
		break;
		case BUILDING_KEEP : setWorkerNeeded(0);
		default :
			setWorkerNeeded(0);
			break;
		}
		 
	}
	

	/**
	 * 
	 * @return  Building instances Counter
	 */
	public static int getCounter()
	{
		return COUNTER;
	}

	/**
	 * Overwrite instances Counter
	 * @param Counter
	 */
	public static void initCounter(int Counter)
	{
		COUNTER = Counter;
	}
	
	
	/**
	 * Set the default amont of residents for building by type
	 * @param buildingType
	 */
	private void setSettlerDefault(BuildingType buildingType)
	{
		switch(buildingType)
		{
		case BUILDING_NONE : setSettler(0);
			break;
		case BUILDING_HOME : setSettler(SETTLER_COUNT);
			break;
		case BUILDING_BAUERNHOF : setSettler(2 * SETTLER_COUNT);
			break;
		case BUILDING_WERKSTATT : setSettler(2 * SETTLER_COUNT);
			break;
		default :
			setSettler(0);
			break;
		}
	}

	public ItemArray getSlot1()
	{
		return 	slots;
	}

	public void setSlot1(ItemArray slots)
	{
		this.slots = slots;
	}

	public boolean addSlot(String itemRef, ConfigInterface config)
	{
		int iValue = 16;
//		if (slots.contains(itemRef) == false)
//		{
			if (config.getToolItems().containsKey(itemRef))
			{
				iValue = 32;
			}
			if (config.getWeaponItems().containsKey(itemRef))
			{
				iValue = 1;
			}
			if (config.getArmorItems().containsKey(itemRef))
			{
				iValue = 1;
			}

			slots.addItem(itemRef, iValue);
//		}
		return false;
	}
	
	/**
	 * 
	 * @return true if owner has to produce , false = no produce
	 */
	public boolean isActive()
	{
		return isActiv;
	}
	
	/**
	 * the building could be disabled due to environment influences
	 * @return true if building is ready to produce, false = not ready to produce
	 */
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
	
	/**
	 * only Homes or Home equivalent has residents and build workers 
	 * @return  true if the building is a home and has residents
	 */
	public Boolean isHome()
	{
		switch(buildingType)
		{
		case BUILDING_HOME : return true;
		default :
			return false;
		}
	}
	
	/**
	 * the buildingType define the function of the building
	 * @return the BuildingType
	 */
	public BuildingType getBuildingType()
	{
		return buildingType;
	}

	/**
	 * Only change the buildingType 
	 * if the actual BuildingType == BUILDING_NONE 
	 * @param buildingType
	 * @return true if type is changed
	 */
	public Boolean setBuildingType(BuildingType buildingType)
	{
		if (this.buildingType == BuildingType.BUILDING_NONE)
		{
			this.buildingType = buildingType;
			return true;
		}
		return false;
	}
	
	/**
	 * @return amount of residents in the building
	 */
	public int getSettler()
	{
		return settler;
	}

	/**
	 * Set the amount of residents in building
	 * the residents are producing workers and breeding
	 * @param residentHome
	 */
	public void setSettler(int residentHome)
	{
		this.settler = residentHome;
	}

	/**
	 * 
	 * @return Worker needed for producing or functioning
	 */
	public int getWorkerNeeded()
	{
		return workerNeeded;
	}

	/**
	 * only possible for unitStore buildings (military, stables etc.)
	 * @param unitSpace
	 */
	public void setUnitSpace(int unitSpace)
	{
		this.settler = unitSpace;
	}

	/**
	 * only possible for unitStore buildings (military, stables etc.)
	 * @return unitSpace for Units
	 */
	public int getUnitSpace()
	{
		return settler;
	}

	/**
	 * the building will only be enabled and producing 
	 * if workerInstalled = workerNeeded
	 * @param workerNeeded
	 */
	public void setWorkerNeeded(int workerNeeded)
	{
		this.workerNeeded = workerNeeded;
	}
	
	/**
	 * regions are producer / breeder
	 * superregions are power collectors and managment buildings
	 * @return 	true = building is a region
	 */
	public Boolean isRegion()
	{
		return iSRegion;
	}

	/**
	 * true  = building is a region
	 * false = building is a superRegion
	 * @param isRegion
	 */
	public void setIsRegion(Boolean isRegion)
	{
		this.iSRegion = isRegion;
	}

	public int getHsRegion()
	{
		return hsRegion;
	}

	
	public void setHsRegion(int hsRegion)
	{
		if (iSRegion == true)
		{
			this.hsRegion = hsRegion;
		}
	}

	/**
	 * Only valid if isRegion = false
	 * @return the reference for Superregion (name of superregion)
	 */
	public String getHsSuperRegion()
	{
		return hsSuperRegion;
	}

	/**
	 * Only valid if isRegion = false
	 * set the reference for Superregion (name of superregion)
	 * @param hsSuperRegion
	 */
	public void setHsSuperRegion(String hsSuperRegion)
	{
		if (iSRegion == false)
		{
			this.hsSuperRegion = hsSuperRegion;
		}
	}

	/**
	 * show if building working correctly
	 * @return
	 */
	public Boolean getIsEnabled()
	{
		return isEnabled;
	}

	/**
	 * Set true for building working correctly
	 * Set the enabled Status , no check for conditions
	 * @param isEnabled
	 */
	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	/**
	 * Set building to producing isActive
	 * normally set automatically;
	 * @param isActive
	 */
	public void setIsActive(boolean isActive)
	{
		this.isActiv = isActive;
	}
	
	/**
	 * 
	 * @return worker installed in this building
	 */
	public int getWorkerInstalled()
	{
		return workerInstalled;
	}

	/**
	 * Check given worker against needed worker
	 * set needed worker if worker >= needed
	 * reduce worker by needed worker 
	 * @param worker 
	 * @return worker reduced by needed worker
	 */
	public int setWorkerInstalled(int worker)
	{
		if (worker >= workerNeeded)
		{
			this.workerInstalled = worker;
			worker = worker - workerInstalled;
		} else
		{
			this.workerInstalled = 0;
		}
		return worker;  
	}

	/**
	 * @return  reference of the building
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Set the reference Id of the building 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	public String getHsRegionType()
	{
		return hsRegionType;
	}
	
	public static Building createRegionBuilding(String typeName, int regionId, String regionType, boolean isRegion)
	{
		BuildingType buildingType = BuildingType.getBuildingType(typeName);
		if (buildingType != BuildingType.BUILDING_NONE)
		{
			Building building = new Building(buildingType, regionType, isRegion);
			building.setHsRegion(regionId);
			building.setIsActive(true);
			return building;
		}
		
		return null;
	}
	
	private  ItemArray buildingProd(ServerInterface server, String regionType)
	{
		ItemList outValues = new ItemList();
		ItemArray items = new ItemArray(); 
		outValues = server.getRegionOutput(regionType);
		for (String itemRef : outValues.keySet())
		{
			items.addItem(itemRef, Integer.valueOf(outValues.get(itemRef)));
		}
		return items;
	}

		
	private  ItemArray werkstattProd(ServerInterface server, String regionType)
	{
		ItemList recipeList = new ItemList();
		ItemArray items = new ItemArray();
		int iValue = 0;
		int prodFactor = 1;
		if (slots.isEmpty() == false)
		{
			for (Item item : slots)
			{
				prodFactor = server.getRecipeFactor(item.ItemRef());
				recipeList = server.getRecipe(item.ItemRef());
				iValue = recipeList.getValue(item.ItemRef())*prodFactor;
				items.addItem(item.ItemRef(), iValue);
			}
		}
		return items;
	}

	private  ItemArray baeckerProd(ServerInterface server, String regionType)
	{
		ItemList recipeList = new ItemList();
		ItemArray items = new ItemArray();
		int iValue = 0;
		int prodFactor = 1;
		if (slots.isEmpty() == false)
		{
			for (Item item : slots)
			{
				prodFactor = server.getRecipeFactor(item.ItemRef());
				recipeList = server.getFoodRecipe(item.ItemRef());
				iValue = recipeList.getValue(item.ItemRef())*prodFactor;
				items.addItem(item.ItemRef(), iValue);
			}
		}
		return items;
	}

	
	/**
	 * get produced items from stronghold chest
	 * @param server for stronghold action
	 * @return List of (itemRef, int)
	 */
	public ItemArray produce(ServerInterface server)
	{
		ItemArray outValues = new ItemArray();
		switch(buildingType)
		{
		case BUILDING_PROD : 
			outValues = buildingProd(server,hsRegionType);
			break;
		case BUILDING_BAUERNHOF :
			outValues = buildingProd(server,hsRegionType);
			break;
		case BUILDING_WERKSTATT :
			outValues = werkstattProd(server,hsRegionType);
			break;
		case BUILDING_BAECKER :
			outValues = baeckerProd(server,hsRegionType);
			break;
		default :
			break;
		}
		
		return outValues;
	}
	
	/**
	 * 
	 * @param server for stronghold action
	 * @param matList
	 * @param id
	 */
	public void setConsume(ServerInterface server, ItemList matList, int id)
	{
		server.setRegionChest(id, matList);
		
	}
	

	/**
	 * calculate tax for building
	 * @param server for stronghold action
	 * @param id of building
	 * @return value
	 */
	public Double getTaxe(ServerInterface server, int id)
	{
		double value = 0;
		switch(buildingType)
		{
		case BUILDING_ENTERTAIN :
			value = TAVERNE_TAXE;
			break;
		case BUILDING_BAUERNHOF :
			value = SETTLER_TAXE * workerInstalled; 
			break;
		case BUILDING_WERKSTATT :
			value = SETTLER_TAXE * workerInstalled; 
			break;
		default :
			break;
		}
		return value;
	}

	
}
