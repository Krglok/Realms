package net.krglok.realms.core;

import java.util.HashMap;

import net.krglok.realms.data.ServerInterface;

/**
 * represent the whole settlement and the rules for production, fertility and money
 * 
 *    
 * @author Windu
 *
 */
public class Settlement
{
	private static final String NEW_SETTLEMENT = "New Settlement";

	private static double SETTLER_TAXE = 1.0;
	private static double TRADER_TAXE = 5.0;
	private static double TAVERNE_TAXE = 7.0;

	private static final int ENTERTAIN_SETTLERS = 50;

	private static final int WarehouseChestFactor = 9;
	private static final int TraderChestFactor = 4;
	private static final int Chest_Store = 1728;
	
	private static final int Haupthaus_Settler = 5; 
	
	private static int COUNTER;
	
	private int id;
	private SettleType settleType = SettleType.SETTLE_NONE;
	private Position position;
	private String name;
	private String owner;
	private Boolean isCapital;
	private Barrack barrack ;
	private Warehouse warehouse ;
	private BuildingList buildingList;
	private Townhall townhall;
	private Bank bank;
	private Resident resident;
//	private Wellfare wellfare;
//  private Trader trader;
//  private Headquarter headquarter;
	private ItemList requiredProduction;

	private Boolean isEnabled;
	private Boolean isActive;
	
	private double foodConsumCounter;
	
	/**
	 * instance empty settlement with
	 * - sequential ID
	 */
	public Settlement()
	{
		COUNTER++;
		id			= COUNTER;
		settleType 	= SettleType.SETTLE_NONE;
		position 	= new Position();
		name		= NEW_SETTLEMENT;
		owner 		= "";
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		bank		= new Bank();
		resident	= new Resident();
		isEnabled   = true;
		isActive    = true;
		foodConsumCounter = 0.0;
		requiredProduction = new ItemList();
	}

	/**
	 * instances settlement with
	 * - with sequential ID
	 * - owner
	 * 
	 * @param Owner
	 */
	public Settlement(String owner)
	{
		COUNTER++;
		id			= COUNTER;
		settleType 	= SettleType.SETTLE_NONE;
		name		= NEW_SETTLEMENT;
		position 	= new Position();
		this.owner = owner;
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		bank		= new Bank();
		resident	= new Resident();
		isEnabled   = true;
		isActive    = true;
		foodConsumCounter = 0.0;
		requiredProduction = new ItemList();
	}

	/**
	 * instances settlement with
	 * - with sequential ID
	 * - owner
	 * - name of stellemnet
	 * @param Owner
	 * @param settleType
	 * @param name
	 */
	public Settlement(String owner, SettleType settleType, String name)
	{
		COUNTER++;
		id			= COUNTER;
		this.settleType = settleType;
		this.name		= name;
		position 	= new Position();
		this.owner = owner;
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		bank		= new Bank();
		resident	= new Resident();
		isEnabled   = true;
		isActive    = true;
		foodConsumCounter = 0.0;
		requiredProduction = new ItemList();
	}
	
	
	/**
	 * 
	 * instances settlement with
	 * - without sequential ID
	 * @param id
	 * @param settleType
	 * @param name
	 * @param position
	 * @param owner
	 * @param isCapital
	 * @param barrack
	 * @param warehouse
	 * @param buildingList
	 * @param townhall
	 * @param bank
	 * @param resident
	 */
	public Settlement(int id, SettleType settleType, String name, 
			Position position, String owner,
			Boolean isCapital, Barrack barrack, Warehouse warehouse,
			BuildingList buildingList, Townhall townhall, Bank bank,
			Resident resident)
	{
		super();
		this.id = id;
		this.settleType = settleType;
		this.name = name;
		this.owner = owner;
		this.isCapital = isCapital;
		this.barrack = barrack;
		this.warehouse = warehouse;
		this.buildingList = buildingList;
		this.townhall = townhall;
		this.bank = bank;
		this.resident = resident;
		isEnabled   = true;
		isActive    = true;
		foodConsumCounter = 0.0;
		requiredProduction = new ItemList();
	}

	/**
	 * Klassenmethode zum auslesen des Instanzen counter
	 * @return
	 */
	public static int getCounter()
	{
		return COUNTER;
	}

	/**
	 * Klassenmethode zum setzen des Instanzen counter
	 * sequential instances counter
	 * @param iD
	 */
	public static void initCounter(int iD)
	{
		COUNTER = iD;
	}
	
	public Boolean isEnabled()
	{
		return isEnabled;
	}
	
	public Boolean isActive()
	{
		return isActive;
	}
	
	public void setIsActive(boolean value)
	{
		this.isActive = value;
	}
	
	/**
	 * charge number of items in storage of townhall without any storage buildings
	 * @param settleType
	 * @return number of items 
	 */
	private static int defaultItemMax(SettleType settleType)
	{
		switch (settleType)
		{
		case SETTLE_HAMLET : return 4 * Chest_Store;
		case SETTLE_TOWN   : return 4 * Chest_Store;
		case SETTLE_CITY   : return 4 * Chest_Store;
		case SETTLE_METRO  : return 4 * Chest_Store;
		case SETTLE_CASTLE : return 4 * Chest_Store;
		default :
			return 0;
		}
	}

	/**
	 * charge number of unit in settlement without any special military buildings
	 * @param settleType
	 * @return number of unit 
	 */
	private static int defaultUnitMax(SettleType settleType)
	{
		switch (settleType)
		{
		case SETTLE_HAMLET : return 1 * Haupthaus_Settler;
		case SETTLE_TOWN   : return 1 * Haupthaus_Settler;
		case SETTLE_CITY   : return 2 * Haupthaus_Settler;
		case SETTLE_METRO  : return 4 * Haupthaus_Settler;
		case SETTLE_CASTLE : return 4 * Haupthaus_Settler;
		default :
			return 0;
		}
	}
	
	
	/**
	 * actual number of the settlement
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Set the actual number of the settlement
	 * only be useful to initialize a stored settlement
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	public SettleType getSettleType()
	{
		return settleType;
	}

	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public Boolean getIsCapital()
	{
		return isCapital;
	}

	public void setIsCapital(Boolean isCapital)
	{
		this.isCapital = isCapital;
	}

	public Barrack getBarrack()
	{
		return barrack;
	}

	public void setBarrack(Barrack barrack)
	{
		this.barrack = barrack;
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse)
	{
		this.warehouse = warehouse;
	}

	public BuildingList getBuildingList()
	{
		return buildingList;
	}

	/**
	 * 
	 * @param buildingList
	 */
	public void setBuildingList(BuildingList buildingList)
	{
		this.buildingList = buildingList;
	}

	/**
	 * expand buildingList with values of newBuildingList
	 * instances buildingList if not present
	 * @param newBuildingList
	 */
	public void addBuildingList(BuildingList newBuildingList)
	{
		for (Building b : newBuildingList.getBuildingList().values())
		{
			buildingList.addBuilding(b);
		}
	}
	
	/**
	 * calculate extend for ItemMax for warehouse building
	 * @param building
	 * @param value  old ItemMax
	 * @return new ItemMax
	 */
	private static int getWarehouseItemMax(Building building)
	{
		switch(building.getBuildingType())
		{
		case BUILDING_WAREHOUSE : return   WarehouseChestFactor * Chest_Store;
		case BUILDING_TRADER    : return   TraderChestFactor * Chest_Store;
		case BUILDING_WERKSTATT : return   0; //WerkstattChestFactor * Chest_Store;
		case BUILDING_BAUERNHOF : return   0; //BauernhofChestFactor * Chest_Store;
		default :
			return 0 ;
			
		}
		 //value + (building.getWorkerNeeded()*WarehouseItemMaxFactor);
	}

	/**
	 * calculate extend for ItemMax for trader building
	 * @param building
	 * @param value  old ItemMax
	 * @return new ItemMax
	 */
	private static int getTraderItemMax(Building building)
	{
		switch(building.getBuildingType())
		{
			case BUILDING_TRADER    : return TraderChestFactor * Chest_Store;
			default :
				return 0 ;
		}
	}
	
	/**
	 * calculte ItemMax for the whole settlement
	 * @return ItemMax
	 */
	private static int calcItemMax(BuildingList buildingList, Warehouse warehouse, SettleType settleType)
	{
		int value = 0;
		if (buildingList != null)
		{
			for (Building b : buildingList.getBuildingList().values())
			{
				switch (b.getBuildingType()) 
				{
					case BUILDING_WAREHOUSE :
						value = value + getWarehouseItemMax(b);
						break;
					case BUILDING_TRADER :
						value = value + getTraderItemMax(b);
						break;
					case BUILDING_HALL :
						value = value + defaultItemMax(settleType);
						break;
					default :
						break;
				}
			}
		}
		return value;
	}
	/**
	 * Add building to buildingList and recalculate settlement parameter
	 * @param building
	 * @param settlement
	 * @return
	 */
	public static Boolean addBuilding(Building building, Settlement settlement)
	{
		if(settlement.buildingList.addBuilding(building))
		{
			int value = settlement.getWarehouse().getItemMax();
			switch(building.getBuildingType())
			{
			case BUILDING_HALL: 
				settlement.townhall.setIsEnabled(true);
				settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
//				settlement.warehouse.setItemMax(getTraderItemMax(building,settlement.warehouse.getItemMax()));
				break;
			case BUILDING_WAREHOUSE :
				settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
//				settlement.warehouse.setItemMax(getTraderItemMax(building,settlement.warehouse.getItemMax()));
				break;
			case BUILDING_TRADER :
				settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
//				settlement.warehouse.setItemMax(getTraderItemMax(building,settlement.warehouse.getItemMax()));
				break;
			case BUILDING_MILITARY :
				settlement.barrack.setUnitMax(settlement.barrack.getUnitMax() + building.getUnitSpace());
				break;
			default :
				break;
			}
			return true;
		}
		return false;
	}
	

	public Townhall getTownhall()
	{
		return townhall;
	}

	public void setTownhall(Townhall townhall)
	{
		this.townhall = townhall;
	}

	public Bank getBank()
	{
		return bank;
	}

	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	public Resident getResident()
	{
		return resident;
	}

	public void setResident(Resident resident)
	{
		this.resident = resident;
	}

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	/**
	 * Create a new settlement by SettleType 
	 * and regionTypes List <String, String>  for building list
	 * 
	 * @param settleType
	 * @param settleName
	 * @param owner
	 * @param regionTypes
	 * @return
	 */
	public static Settlement createSettlement(SettleType settleType, String settleName, String owner, 
											HashMap<String,String> regionTypes, 
											HashMap<String,String> regionBuildings)
	{
		if (settleType != SettleType.SETTLE_NONE)
		{
			Settlement settlement = new Settlement(owner,settleType, settleName);
//			BuildingList buildingList = new BuildingList();
			int regionId = 0;
			String BuildingTypeName = "";
			String regionType = "";
			boolean isRegion = false;
			for (String region : regionTypes.keySet())
			{
				regionId = Integer.valueOf(region);
				
				regionType = regionTypes.get(region);
				
				BuildingTypeName   = regionBuildings.get(region);
				isRegion = true;
				addBuilding(Building.createRegionBuilding(BuildingTypeName, regionId, regionType, isRegion),settlement);
			}
//			settlement.setBuildingList(buildingList);
			return settlement;
		}
		return null;
	}

	/**
	 * get production get from producer buildings in the settlement
	 * @param server
	 */
	public void produce(ServerInterface server)
	{
		int prodFactor = 1;
		int iValue = 0;
		ItemArray items;
		ItemList recipeList;
		requiredProduction.clear();

		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				items = building.produce(server);
				for (Item item : items)
				{
					switch(building.getBuildingType())
					{
					case BUILDING_PROD :
						recipeList = server.getRecipeProd(item.ItemRef(),building.getHsRegionType());
						prodFactor = 1;
						break;
					case BUILDING_BAUERNHOF:
						recipeList = server.getRecipe(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef());
						break;
					case BUILDING_WERKSTATT:
						recipeList = server.getRecipe(item.ItemRef());
						recipeList.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef());
						break;
					case BUILDING_BAECKER:
						recipeList = server.getRecipe(item.ItemRef());
						recipeList.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef());
						break;
					default :
						recipeList = new ItemList();
						prodFactor = 1;
						break;
					}
					
					if (checkStock(prodFactor, recipeList))
					{
						consumStock(prodFactor, recipeList);
						iValue = item.value();
						warehouse.depositItemValue(item.ItemRef(),iValue);
					}
				}
			}
		}
	}
	

	public boolean checkStock(int prodFactor, ItemList items)
	{
		int iValue = 0;
		// Check amount in warehouse
		boolean isStock = true;
		for (String itemRef : items.keySet())
		{
			iValue = items.get(itemRef)*prodFactor;
			if (this.warehouse.getItemList().getValue(itemRef) < iValue)
			{
				isStock = false;
				if (requiredProduction.containsKey(itemRef))
				{
					requiredProduction.depositItem(itemRef, iValue);
				} else
				{
					requiredProduction.addItem(itemRef, iValue);
				}

			}
		}
		return isStock;
	}
	
	public void consumStock(int prodFactor, ItemList items)
	{
		int iValue = 0;
		for (String itemRef : items.keySet())
		{
			iValue = items.get(itemRef)*prodFactor;
			this.getWarehouse().withdrawItemValue(itemRef, iValue);
		}
	}
	
	/**
	 * get Taxe from buildings and deposit in bank
	 * @param server
	 */
	public void getTaxe(ServerInterface server)
	{
		double taxSum = 0;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				taxSum = taxSum + building.getTaxe(server, building.getId());
			}
		}
		taxSum = taxSum + townhall.getWorkerCount() * SETTLER_TAXE;
//		taxSum = resident.getSettlerCount() * SETTLER_TAXE;
		bank.addKonto(taxSum);
	}
	
	/**
	 * calculate settlerMax from buildings and set 
	 */
	public void setSettlerMax()
	{
		int settlerMax = 5;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				settlerMax = settlerMax + building.getSettler();
			}
		}
		resident.setSettlerMax(settlerMax);
	}
	
	/**
	 * calculate happines for entertaiment
	 * @return happiness
	 */
	private double calcEntertainment()
	{
		int tavernNeeded = (resident.getSettlerCount() / ENTERTAIN_SETTLERS)+1;
		int tavernCount = 0;
		double factor = 0.0;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				if (building.getBuildingType() == BuildingType.BUILDING_ENTERTAIN)
				{
					tavernCount++;
				}
			}
		}
		if (tavernCount > 0)
		{
		  factor = ((tavernCount * 100.0) / (tavernNeeded * 100.0)) / 100 * 0.2;
		}
		
		return factor;
	}
	
	/**
	 * calculate the whole happines for the different influences 
	 */
	public void setHappiness()
	{
		double sumDif = 0.0;
		sumDif = calcEntertainment();
		sumDif = sumDif + consumeFood(resident.calcResidentHappiness(resident.getHappiness()));
		sumDif = sumDif + resident.calcResidentHappiness(resident.getHappiness());
		resident.setHappiness(sumDif);
		resident.settlerCount();

	}
	
	/**
	 * calculate happines for the food supply of the settlers
	 * - no influence if fodd supply is guarantee 
	 * - haevy influence if food supply too low.
	 * the settlers are all supplied or none  
	 * @param oldFactor
	 * @return happiness factor of food supply 
	 */
	private double consumeFood(double oldFactor)
	{
		double factor = 0.0; 
		int value = resident.getSettlerCount();
		Integer bread = warehouse.getItemList().get("BREAD");
//		if (bread != null) 
//		{ 
//			if (value <= bread)
//			{
//				warehouse.withdrawItemValue("BREAD", value);
//				foodConsumCounter = foodConsumCounter + (resident.getSettlerCount() / 10.0);
//				factor = 0.0;
//				if (resident.getHappiness() < 0.8)
//				{
//					factor = 0.2;
//				}
//				if (resident.getHappiness() < 0.8)
//				{
//					factor = 0.2;
//				}
//				foodConsumCounter = 0;
//				return factor;
//			}
//			
//		} else { bread = 0;}

		Integer wheat = warehouse.getItemList().get("WHEAT");
		if (wheat == null) { wheat = 0;}
		if (value > wheat)
		{		
			factor = (resident.getSettlerCount() / -10.0);
			foodConsumCounter = foodConsumCounter + factor;
		} else
		{
			warehouse.withdrawItemValue("WHEAT", value);
			foodConsumCounter = foodConsumCounter + (resident.getSettlerCount() / 20.0);
			if (foodConsumCounter < 1)
			{
				factor = -0.1;
			} else
			{
				if (resident.getHappiness() < 0.6)
				{
					if (resident.getSettlerMax() > resident.getSettlerCount())
					{
						factor = 0.1;
					} else
					{
						factor = 0.0;
					}
					
				} else
				{
					factor = 0.0;
				}
				foodConsumCounter = 0;
			}
		}
		
		return factor;
	}
	
	/**
	 * calculte needed worker for buildings
	 */
	public void setWorkerNeeded()
	{
		int workerSum = 0;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				workerSum = workerSum + building.getWorkerNeeded();
			}
		}
		townhall.setWorkerNeeded(workerSum);
	}
	
	/**
	 * set workers to buildings. no priority
	 * @param workerSum
	 * @return workers without workingplace
	 */
	public int setWorkerToBuilding(int workerSum)
	{
		int workerCount = 0;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				if (workerSum >= workerCount + building.getWorkerNeeded())
				{
					workerCount = workerCount + building.getWorkerNeeded();
					building.setWorkerInstalled(building.getWorkerNeeded());
				} else
				{
					building.setIsEnabled(false);
				}
			}
		}
		townhall.setWorkerCount(workerCount);
		return workerSum-workerCount;
	}

	public void checkBuildingsEnabled(ServerInterface server)
	{
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isActive())
			{
				building.setIsEnabled(true);
				// pruefe ob Stronghold region enabled sind
				server.checkRegionEnabled(building.getHsRegion());
			} else
			{
				building.setIsEnabled(false);
			}
		}
	}

	/**
	 * 
	 * @return list of required items from last production cycle
	 */
	public ItemList getRequiredProduction()
	{
		return requiredProduction;
	}
	
}
