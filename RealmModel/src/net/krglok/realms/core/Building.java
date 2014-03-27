package net.krglok.realms.core;

import java.io.Serializable;

import org.bukkit.block.Biome;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.ConfigInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.unit.UnitMilitia;
import net.krglok.realms.unit.UnitType;

/**
 * <pre>
 * realize the building of a settlement and hold settlers and make production
 * the building have different types in the settlement
 * they include a region from herostronghold for the buildup requirements
 * and the production recipe
 * </pre>
 * @author oduda
 *
 */
public class Building  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -347474537928992879L;
	private static final int SETTLER_COUNT = 4; 
	private static double SETTLER_TAXE = 1.0;
	private static double TAVERNE_TAXE = 7.0;
	
	private static int COUNTER;


	private int id;
	private BuildPlanType buildingType;
	private int settler;
	private int workerNeeded;
	private int workerInstalled;
	private Boolean isRegion;
	private int hsRegion;
	private String hsRegionType;
	private String hsSuperRegion;
	private Boolean isEnabled;
	private boolean isActiv;
	private Item[] slots ;
	private Double sales;
	private boolean isSlot;
	private int storeCapacity;
	private LocationData position;
	private Biome biome;
	private UnitType trainType;
	private int trainCounter;
	private int trainTime;
	private int maxProduction;
	
	public Building()
	{
		COUNTER++;
		id			= COUNTER;
		this.buildingType = BuildPlanType.NONE;
		setSettlerDefault(buildingType);
		setWorkerDefault(buildingType);
		workerInstalled = 0;
		this.isRegion 		= true;
		hsRegion		= 0;
		hsRegionType	= "";
		hsSuperRegion	= "";
		isEnabled		= true;
		isActiv 	    = true;
		slots = new Item[5];
		setisSlot(false);
		sales = 0.0;
		storeCapacity   = getStoreCapacity(buildingType);
		position = new LocationData("", 0.0, 0.0, 0.0);
		biome = null;
		trainType = UnitType.NONE;
		trainTime = 1;
		maxProduction = 0;
	}
	
	public Building(BuildPlanType buildingType, String regionType, boolean isRegion)
	{
		COUNTER++;
		id			= COUNTER;
		this.buildingType = buildingType;
		setSettlerDefault(buildingType);
		setWorkerDefault(buildingType);
		workerInstalled = 0;
		this.isRegion 		= isRegion;
		this.hsRegionType	= regionType;
		hsRegion		= 0;
		hsSuperRegion	= "";
		isEnabled		= true;
		isActiv 	    = true;
		slots = new Item[5];
		sales = 0.0;
		storeCapacity   = getStoreCapacity(buildingType);
		position = new LocationData("", 0.0, 0.0, 0.0);
		biome = null;
		trainType = setDefaultTrainingType(buildingType);
		maxProduction = 0;
	}


	public Building(BuildPlanType buildingType,	int hsRegion, String hsRegionType, Boolean isRegion, LocationData position)
	{
		COUNTER++;
		id			= COUNTER;
		this.buildingType = buildingType;
		setSettlerDefault(buildingType);
		setWorkerDefault(buildingType);
		this.workerInstalled = 0;
		this.isRegion = isRegion;
		this.hsRegionType = hsRegionType;
		this.hsRegion = hsRegion;
		this.hsSuperRegion = "";
		this.isEnabled  = true;
		isActiv 	    = true;
		slots = new Item[5];
		setisSlot(false);
		sales = 0.0;
		storeCapacity   = getStoreCapacity(buildingType);
		this.position = position; //new LocationData("", 0.0, 0.0, 0.0);
		biome = null;
		trainType = setDefaultTrainingType(buildingType);
		maxProduction = 0;
	}
	
	public Building(int id, BuildPlanType buildingType, int settler,
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
		this.isRegion = isRegion;
		this.hsRegionType = hsRegionType;
		this.hsRegion = hsRegion;
		this.hsSuperRegion = hsSuperRegion;
		this.isEnabled = isEnabled;
		isActiv 	    = true;
		slots = new Item[5];
		setisSlot(false);
		sales = 0.0;
		storeCapacity   = getStoreCapacity(buildingType);
		position = new LocationData("", 0.0, 0.0, 0.0);
		biome = null;
		trainType = setDefaultTrainingType(buildingType);
		maxProduction = 0;
	}
	
	public Building(int id, BuildPlanType buildingType, int settler,
			int workerNeeded, int workerInstalled, Boolean isRegion,
			int hsRegion, String hsRegionType, String hsSuperRegion, 
			Boolean isEnabled, String slot1, String slot2, String slot3, 
			String slot4, String slot5,Double sales,
			LocationData position)
	{
		super();
		this.id = id;
		this.buildingType = buildingType;
		this.settler = settler;
		this.workerNeeded = workerNeeded;
		this.workerInstalled = workerInstalled;
		this.isRegion = isRegion;
		this.hsRegion = hsRegion;
		this.hsRegionType = hsRegionType;
		this.hsSuperRegion = hsSuperRegion;
		this.isEnabled = isEnabled;
		storeCapacity   = getStoreCapacity(buildingType);
		
		isActiv 	    = true;
		setisSlot(false);
		slots = new Item[5];
		if (slot1 != "")
		{
			slots[0] = new Item(slot1, 1);
			setisSlot(true);
		}
		if (slot2 != "")
		{
			slots[1] = new Item(slot2, 1);
			setisSlot(true);
		}
		if (slot3 != "")
		{
			slots[2] = new Item(slot3, 1);
			setisSlot(true);
		}
		if (slot4 != "")
		{
			slots[3] = new Item(slot4, 1);
			setisSlot(true);
		}
		if (slot5 != "")
		{
			slots[0] = new Item(slot5, 1);
			setisSlot(true);
		}
		this.sales = sales;
		this.position = position;
		biome = null;
		trainType = setDefaultTrainingType(buildingType);
		maxProduction = 0;

	}

	/**
	 * set the training parameter
	 * 
	 * @param buildingType
	 * @return
	 */
	private UnitType setDefaultTrainingType(BuildPlanType buildingType)
	{
		switch(buildingType)
		{
		case GUARDHOUSE : 
			trainTime = 5; // es sind 5 productionZyklen = 5 inGameTage gemeint
			return UnitType.MILITIA ;
		default :
			return UnitType.NONE;
		}
		
		
	}

	
	
	private void setWorkerDefault(BuildPlanType buildingType)
	{
		switch(buildingType)
		{
		case NONE : setWorkerNeeded(0);
		break;
		case HALL : setWorkerNeeded(0);
		break;
		case HOME : setWorkerNeeded(0);
		break;
		case WHEAT : setWorkerNeeded(1);
		break;
		case WOODCUTTER : setWorkerNeeded(1);
		break;
		case QUARRY : setWorkerNeeded(1);
		break;
		case CARPENTER : setWorkerNeeded(2);
		break;
		case CABINETMAKER : setWorkerNeeded(2);
		break;
		case AXESHOP : setWorkerNeeded(1);
		break;
		case BAKERY : setWorkerNeeded(1);
		break;
		case FARMHOUSE : setWorkerNeeded(2);
		break;
		case FARM : setWorkerNeeded(5);
		break;
		case BRICKWORK : setWorkerNeeded(2);
		break;
		case CHARBURNER : setWorkerNeeded(1);
		break;
		case CHICKENHOUSE : setWorkerNeeded(1);
		break;
		case COWSHED : setWorkerNeeded(1);
		break;
		case FISHERHOOD : setWorkerNeeded(1);
		break;
		case HOESHOP : setWorkerNeeded(1);
		break;
		case KNIFESHOP : setWorkerNeeded(1);
		break;
		case PICKAXESHOP : setWorkerNeeded(1);
		break;
		case PIGPEN : setWorkerNeeded(1);
		break;
		case SHEPHERD : setWorkerNeeded(1);
		break;
		case SMELTER : setWorkerNeeded(3);
		break;
		case SPADESHOP : setWorkerNeeded(1);
		break;
		case STONEMINE : setWorkerNeeded(3);
		break;
		case TAVERNE : setWorkerNeeded(2);
		break;
		case WORKSHOP : setWorkerNeeded(5);
		break;
		case TRADER : setWorkerNeeded(5);
		break;
		default :
			setWorkerNeeded(0);
			break;
		}
		 
	}
	

	/**
	 * 
	 * @return  Building instances Counter
	 */
	public static Integer getCounter()
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
	
	
	public static int getDefaultSettler(BuildPlanType buildingType)
	{
		switch(buildingType)
		{
		case NONE : return 0;
		case HOME : return SETTLER_COUNT;
		case HOUSE : return (2 * SETTLER_COUNT);
		case MANSION : return (3 * SETTLER_COUNT);
		case FARMHOUSE : return (2 * SETTLER_COUNT);
		case FARM : return(4 * SETTLER_COUNT);
		case COLONY : return 0;
		case LANE : return 0;
		case ROAD : return 0;
		case STEEPLE : return 0;
		case TAVERNE : return 0;
		case WALL : return 0;
		case PILLAR : return 0;
		default :
			return 0;
		}
		
	}

	
	/**
	 * Set the default amont of residents for building by type
	 * @param buildingType
	 */
	private void setSettlerDefault(BuildPlanType buildingType)
	{
		setSettler(getDefaultSettler(buildingType));
	}
	
	
	/**
	 * Setzt den Storage Factor fuer den BuidingType
	 * dieser wird bei der Verteilung der Worker auf die Building
	 * benutzt um festzustellen ob die Produktion sinnvoll ist.
	 * @param bType
	 * @return
	 */
	public static int getStoreCapacity(BuildPlanType bType)
	{
		switch(bType)
		{
		case WHEAT  : return 64;
		case MUSHROOM : return 64;
		case KITCHEN : return 64;
		case BAKERY : return 64;
		case FARMHOUSE:return 64;
		case FARM : return 64;
		case TRADER: return 27;
		case GUARDHOUSE : return 5;
		default:  return 32; 
		}
	}


	public Item[] getSlots()
	{
		return 	slots;
	}

	public void setSlots(ItemArray slots)
	{
		int index = 0;
		for (Item item : slots)
		{
			this.slots[index] = item;
			index ++;
		}
	}

	public boolean addSlot(int slot, String itemRef, ConfigInterface config)
	{
		int iValue = 16;
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

		slots[slot] = new Item(itemRef, iValue);
		return true;
	}
	
	/**
	 * 
	 * @return true if owner has to produce , false = no produce
	 */
	public Boolean isActive()
	{
		return isActiv;
	}
	
	/**
	 * the building could be disabled due to environment influences
	 * @return true if building is ready to produce, false = not ready to produce
	 */
	public Boolean isEnabled()
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
		case HOME : return true;
		case HOUSE : return true;
		case MANSION : return true;
		case FARMHOUSE : return true;
		case FARM : return true;
		default :
			return false;
		}
	}
	
	/**
	 * the buildingType define the function of the building
	 * @return the BuildingType
	 */
	public BuildPlanType getBuildingType()
	{
		return buildingType;
	}

	/**
	 * Only change the buildingType 
	 * if the actual BuildingType == BUILDING_NONE 
	 * @param buildingType
	 * @return true if type is changed
	 */
	public Boolean setBuildingType(BuildPlanType buildingType)
	{
		if (this.buildingType == BuildPlanType.NONE)
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
		return isRegion;
	}

	/**
	 * true  = building is a region
	 * false = building is a superRegion
	 * @param isRegion
	 */
	public void setIsRegion(Boolean isRegion)
	{
		this.isRegion = isRegion;
	}

	public int getHsRegion()
	{
		return hsRegion;
	}

	
	public void setHsRegion(int hsRegion)
	{
		if (isRegion == true)
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
		if (isRegion == false)
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
	
	public Double getSales()
	{
		return sales;
	}

	public void setSales(Double sales)
	{
		this.sales = sales;
	}

	public void addSales(Double value)
	{
		this.sales = this.sales + value;
	}

	
	/**
	 * @return the isSlot
	 */
	public boolean isSlot()
	{
		isSlot = false;
		for (Item item : slots)
		{
			if (item != null)
			{
				if(item.ItemRef() != "")
				{
					isSlot = true;
				}
			}
		}
		return isSlot;
	}

	/**
	 * @param isSlot the isSlot to set
	 */
	public void setisSlot(boolean isSlot)
	{
		this.isSlot = isSlot;
	}

	public int getStoreCapacity()
	{
		return storeCapacity;
	}

	public void setStoreCapacity(int storeCapacity)
	{
		this.storeCapacity = storeCapacity;
	}

	public static Building createRegionBuilding(String typeName, int regionId, String regionType, boolean isRegion)
	{
		BuildPlanType buildingType = BuildPlanType.getBuildPlanType(typeName);
		if (buildingType != BuildPlanType.NONE)
		{
			Building building = new Building(buildingType, regionType, isRegion);
			building.setHsRegion(regionId);
			building.setIsActive(true);
			return building;
		}
		
		return null;
	}
	
	public  ItemArray buildingProd(ServerInterface server, String regionType)
	{
		ItemList outValues = new ItemList();
		ItemArray items = new ItemArray(); 
		outValues = server.getRegionOutput(regionType);
		for (String itemRef : outValues.keySet())
		{
			items.addItem(itemRef, outValues.getValue(itemRef));
		}
		return items;
	}

		
	private  ItemArray werkstattProd(ServerInterface server, String regionType)
	{
		ItemList recipeList = new ItemList();
		ItemArray items = new ItemArray();
		int iValue = 0;
		double prodFactor = 1;
		for (Item item : slots)
		{
			if (item != null)
			{
				prodFactor = server.getRecipeFactor(item.ItemRef(), biome);
				recipeList = server.getRecipe(item.ItemRef());
				iValue = (int)((double)recipeList.getValue(item.ItemRef())*prodFactor);
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
		double prodFactor = 1.0;
		if (this.isSlot())
		{
			for (Item item : slots)
			{
				if (item != null)
				{
					if (item.ItemRef().equals("") == false)
					{
	//					System.out.println("RecipeFood "+item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef(),biome);
						recipeList = server.getFoodRecipe(item.ItemRef());
						iValue = (int)((double)recipeList.getValue(item.ItemRef())*prodFactor);
						items.addItem(item.ItemRef(), iValue);
						this.isSlot = true;
					}
	//					System.out.println(iValue);
				}
			}
		} else
		{
//			System.out.println("PROD");
			items = buildingProd(server, regionType);
			if (items.isEmpty() == false)
			{
				for (Item item : items)
				{
					prodFactor = server.getRecipeFactor(item.ItemRef(),biome);
					iValue = (int) ((double)item.value() *prodFactor);
					items.setItem(item.ItemRef(), iValue);
//					System.out.println("baeckerProd: "+item.ItemRef()+":"+item.value());
				}
			}
		}
//		for (Item item : items)
//		{
//			System.out.println(this.hsRegion+"-"+item.ItemRef()+":"+item.value()+":"+prodFactor);
//		}
		return items;
	}

//	private Double calcSales(ServerInterface server, ItemArray outValues)
//	{
//		Double BasePrice = 17.0;
//		Double sum = 0.0;
//		Double price = 1.0;
//		for (int i = 0; i < outValues.size(); i++)
//		{
//			price = (BasePrice - server.getRecipeFactor(outValues.get(i).ItemRef()));
//			sum = sum + (outValues.get(i).value()*price);
//		}
//		return sum;
//	}

	/**
	 * berechnet den gesamten Verkaufpreis eines Items
	 * ! the interest are calculated in the settlement !
	 * @param server
	 * @param outValue the produced Item
	 * @return amount * price
	 */
	public Double calcSales(ServerInterface server, Item item)
	{
		Double price = 1.0;
		price = server.getItemPrice(item.ItemRef());
		if (price == 0.0)
		{
			price = 1.0;
		}
//		System.out.println("calcSale"+sum+"/"+price);
		return price;
	}
	
	/**
	 * liefert die required items fuer eine militaryProduction
	 * 
	 * @return
	 */
	public ItemList militaryProduction()
	{
		ItemList outValues = new ItemList();
		switch (trainType)
		{
		case MILITIA:
			UnitMilitia militia = new UnitMilitia();
			for (Item item : militia.getRequiredItems().values())
			{
				outValues.put(item.ItemRef() ,new Item(item.ItemRef(),item.value()));
			}
			break;
		default :
		}
		return outValues;
	}
	
	/**
	 * get produced items from stronghold chest
	 * will calculate the amount that will be produced when enough stock is available
	 * dont set the production to storage, this will be done by settlement 
	 * after checking the stock 
	 * @param server for stronghold action
	 * @return List of (itemRef, int)
	 */
	public ItemArray produce(ServerInterface server)
	{
		ItemArray outValues = new ItemArray();
		switch(buildingType)
		{
		case WHEAT : 
			outValues = buildingProd(server,hsRegionType);
			break;
		case FARM :
			outValues = buildingProd(server,hsRegionType);
			break;
		case WORKSHOP :
			outValues = werkstattProd(server,hsRegionType);
			break;
		case BAKERY :
			outValues = baeckerProd(server,hsRegionType);
			break;
		default :
			if (BuildPlanType.getBuildGroup(buildingType) == 2)
			{
				outValues = buildingProd(server,hsRegionType);
			}
			break;
		}

		return outValues;
	}
	
//	/**
//	 * 
//	 * @param server for stronghold action
//	 * @param matList
//	 * @param id
//	 */
//	public void setConsume(ServerInterface server, ItemList matList, int id)
//	{
//		server.setRegionChest(id, matList);
//		
//	}
	

	/**
	 * calculate tax for building
	 * @param server for stronghold action
	 * @param id of building
	 * @return value
	 */
	public Double getTaxe(ServerInterface server, int SettlerCount)
	{
		double value = 0;
		switch(buildingType)
		{
		case TAVERNE :
			value = TAVERNE_TAXE;
			break;
		case FARM :
			value = SETTLER_TAXE * workerInstalled; 
			break;
		case WORKSHOP :
			value = SETTLER_TAXE * workerInstalled; 
			break;
		default :
			break;
		}
		return value;
	}

	/**
	 * @return the position
	 */
	public LocationData getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	/**
	 * @return the biome
	 */
	public Biome getBiome()
	{
		return biome;
	}

	/**
	 * @param biome the biome to set
	 */
	public void setBiome(Biome biome)
	{
		this.biome = biome;
	}

	public UnitType getTrainType()
	{
		return trainType;
	}

	public void setTrainType(UnitType trainType)
	{
		this.trainType = trainType;
	}

	public long getTrainCounter()
	{
		return trainCounter;
	}

	public void setTrainCounter(int trainCounter)
	{
		this.trainCounter = trainCounter;
	}

	public void addTrainCounter(int value)
	{
		this.trainCounter = this.trainCounter + value;
	}
	
	public boolean isTrainReady()
	{
		if (this.trainCounter >= this.trainTime)
		{
			return true;
		}
		return false;
	}

	public int getTrainTime()
	{
		return trainTime;
	}

	public void setTrainTime(int trainTime)
	{
		this.trainTime = trainTime;
	}

	/**
	 * set Max Units to train
	 * @param value
	 */
	public void setMaxTrain(int value)
	{
		maxProduction = value;
	}

	public void addMaxTrain(int value)
	{
		maxProduction = maxProduction + value;
	}

	public int getMaxTrain()
	{
		return maxProduction;
	}
	
}

