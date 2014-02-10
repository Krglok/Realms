package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.MessageText;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;

/**
 * <pre>
 * represent the whole settlement the central object
 * settlement based on the superregion and region of HeroStronghold
 * incorporate the the rules for production, fertility and money
 * simulation of residents and workers
 * make trading and train units for military
 * the production will be started from an external task, every ingame day 
 * there are some Managers incorporated to give the settlement the possibility to ruled by a NPC
 * </pre>
 * @author Windu
 *
 */
public class Settlement
{
	private static final double MIN_FOODCONSUM_COUNTER = -5.0;
	private static final double TAVERNE_UNHAPPY_FACTOR = 2.0;
	private static final double BASE_TAX_FACTOR = 10;
	private static double TAVERNE_FREQUENT = 10.0;

	private static final String NEW_SETTLEMENT = "New Settlement";

	
	private static int COUNTER;
	
	private int id;
	private SettleType settleType = SettleType.SETTLE_NONE;
	private LocationData position;
	private String name;
	private String owner;
	private Boolean isCapital;
	private Barrack barrack ;
	private Warehouse warehouse ;
	private BuildingList buildingList;
	private Townhall townhall;
	private Bank bank;
	private Resident resident;
	private Trader trader;
//  private Headquarter headquarter;
	private ItemList requiredProduction;
	
	private Boolean isEnabled;
	private Boolean isActive;
	
	private double hungerCounter = 0.0;
	private double foodConsumCounter;
	private Double buildingTax ;
	
	private BoardItemList productionOverview;
	private BoardItemList taxOverview;

	private double EntertainFactor = 0.0;
	private double FoodFactor = 0.0;
	private double SettlerFactor = 0.0;

	private String world;
	private Biome biome;

	private BuildManager buildManager;
	private MapManager mapManager;
	
	private ArrayList<Item> treasureList;
	
	/**
	 * instance empty settlement with
	 * - sequential ID
	 */
	public Settlement()
	{
		COUNTER++;
		id			= COUNTER;
		settleType 	= SettleType.SETTLE_NONE;
		position 	= new LocationData("", 0.0, 0.0, 0.0);
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
		setBuildingTax(BASE_TAX_FACTOR);
		productionOverview = new BoardItemList();
		taxOverview = new BoardItemList();
		world = "";
		setBiome(Biome.SKY);
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70);
		treasureList =  new ArrayList<Item>();
}

	/**
	 * instances settlement with
	 * - with sequential ID
	 * - owner
	 * 
	 * @param Owner
	 */
	public Settlement(String owner, LocationData position)
	{
		COUNTER++;
		id			= COUNTER;
		settleType 	= SettleType.SETTLE_NONE;
		name		= NEW_SETTLEMENT;
		this.position 	= position;
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
		setBuildingTax(BASE_TAX_FACTOR);
		productionOverview = new BoardItemList();
		taxOverview = new BoardItemList();
		world = "";
		trader = new Trader();
		setBiome(Biome.SKY);
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70);
		treasureList =  new ArrayList<Item>();
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
	public Settlement(String owner, LocationData position, SettleType settleType, String name, Biome biome)
	{
		COUNTER++;
		id			= COUNTER;
		this.settleType = settleType;
		this.name		= name;
		this.position 	= position;
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
		setBuildingTax(BASE_TAX_FACTOR);
		productionOverview = new BoardItemList();
		taxOverview = new BoardItemList();
		world = "";
		this.biome = biome;
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70);
		treasureList =  new ArrayList<Item>();
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
			LocationData position, String owner,
			Boolean isCapital, Barrack barrack, Warehouse warehouse,
			BuildingList buildingList, Townhall townhall, Bank bank,
			Resident resident, String world, Biome biome)
	{
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
		setBuildingTax(BASE_TAX_FACTOR);
		productionOverview = new BoardItemList();
		taxOverview = new BoardItemList();
		this.world = world;
		this.setBiome(biome);
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70);
		treasureList =  new ArrayList<Item>();
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
	 * 
	 * @param settleType
	 * @return number of items 
	 */
	public static int defaultItemMax(SettleType settleType)
	{
		switch (settleType)
		{
		case SETTLE_HAMLET : return 4 * MessageText.CHEST_STORE;
		case SETTLE_TOWN   : return 4 * MessageText.CHEST_STORE;
		case SETTLE_CITY   : return 4 * MessageText.CHEST_STORE;
		case SETTLE_METRO  : return 4 * MessageText.CHEST_STORE;
		case SETTLE_CASTLE : return 4 * MessageText.CHEST_STORE;
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
		case SETTLE_HAMLET : return 1 * MessageText.Haupthaus_Settler;
		case SETTLE_TOWN   : return 1 * MessageText.Haupthaus_Settler;
		case SETTLE_CITY   : return 2 * MessageText.Haupthaus_Settler;
		case SETTLE_METRO  : return 4 * MessageText.Haupthaus_Settler;
		case SETTLE_CASTLE : return 4 * MessageText.Haupthaus_Settler;
		default :
			return 0;
		}
	}
	
	public void initSettlement()
	{
		calcItemMax( buildingList,  warehouse,  settleType);
		setSettlerMax();
		setWorkerNeeded();
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
		case WAREHOUSE : return   MessageText.WAREHOUSE_CHEST_FACTOR * MessageText.CHEST_STORE;
		case TRADER    : return   MessageText.TRADER_CHEST_FACTOR * MessageText.CHEST_STORE;
		case WORKSHOP : return   0; //WerkstattChestFactor * Chest_Store;
		case FARM : return   0; //BauernhofChestFactor * Chest_Store;
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
			case TRADER    : return MessageText.TRADER_CHEST_FACTOR * MessageText.CHEST_STORE;
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
					case WAREHOUSE :
						value = value + getWarehouseItemMax(b);
						break;
					case TRADER :
						value = value + getTraderItemMax(b);
						break;
					case HALL :
						value = value + defaultItemMax(settleType);
						break;
					default :
						break;
				}
			}
		}
		return value;
	}
	
	public void initTreasureList()
	{
		treasureList.clear();
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
	}
	
	
	/**
	 * Add building to buildingList and recalculate settlement parameter
	 * @param building
	 * @param settlement
	 * @return
	 */
	public static Boolean addBuilding(Building building, Settlement settlement)
	{
		if (settlement != null)
		{
			if(settlement.buildingList.addBuilding(building))
			{
				switch(building.getBuildingType())
				{
				case HALL: 
					settlement.townhall.setIsEnabled(true);
					settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
					break;
				case WAREHOUSE :
					settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
					break;
				case TRADER :
					settlement.trader.setActive(true);
					settlement.trader.setEnabled(true);
					settlement.warehouse.setItemMax(calcItemMax(settlement.buildingList, settlement.warehouse, settlement.getSettleType()));
					settlement.trader.setOrderMax(settlement.trader.getOrderMax()+5);
					break;
				case GUARDHOUSE :
				case WATCHTOWER :
				case DEFENSETOWER :
				case BARRACK :
				case TOWER :
				case HEADQUARTER :
				case KEEP :
					settlement.barrack.setUnitMax(settlement.barrack.getUnitMax() + building.getUnitSpace());
					break;
				default :
					break;
				}
				return true;
			}
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

	public LocationData getPosition()
	{
		return position;
	}

	public void setPosition(LocationData position)
	{
		this.position = position;
	}
	
	public Double getBuildingTax()
	{
		return buildingTax;
	}

	public void setBuildingTax(Double buildingTax)
	{
		this.buildingTax = buildingTax;
	}

	public double getFoodConsumCounter()
	{
		return foodConsumCounter;
	}

	public double getEntertainFactor()
	{
		return EntertainFactor;
	}

	public double getFoodFactor()
	{
		return FoodFactor;
	}

	public double getSettlerFactor()
	{
		return SettlerFactor;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}

	public Trader getTrader()
	{
		return trader;
	}

	public void setTrader(Trader trader)
	{
		this.trader = trader;
	}

	public BoardItemList getProductionOverview()
	{
		return productionOverview;
	}

	public BoardItemList getTaxOverview()
	{
		return taxOverview;
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
	public static Settlement createSettlement(LocationData position, SettleType 
											settleType, String settleName, String owner, 
											HashMap<String,String> regionTypes, 
											HashMap<String,String> regionBuildings,
											Biome biome)
	{
		if (settleType != SettleType.SETTLE_NONE)
		{
			Settlement settlement = new Settlement(owner,position, settleType, settleName,biome);
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
			settlement.setStoreCapacity();

//			settlement.setBuildingList(buildingList);
			return settlement;
		}
		return null;
	}

	

	public boolean checkStock(double prodFactor, ItemList items)
	{
		int iValue = 0;
		// Check amount in warehouse
		boolean isStock = true;
		for (String itemRef : items.keySet())
		{
			iValue = (int) (items.getValue(itemRef)*prodFactor);
			if (this.warehouse.getItemList().getValue(itemRef) < iValue)
			{
				isStock = false;
				if (requiredProduction.containsKey(itemRef))
				{
					requiredProduction.depositItem(itemRef, iValue);
				} else
				{
					requiredProduction.depositItem(itemRef, iValue);
//					requiredProduction.addItem(itemRef, iValue);
				}

			}
		}
		return isStock;
	}
	
	public void consumStock(double prodFactor, ItemList items)
	{
		int iValue = 0;
		for (Item item : items.values())
		{
			iValue = (int)((double) item.value() *prodFactor);
			this.getWarehouse().withdrawItemValue(item.ItemRef(), iValue);
//			System.out.println("Withdraw-"+item.ItemRef()+":"+iValue+":"+prodFactor);
		}
	}
	
	@SuppressWarnings("unused")
	private void checkDecay()
	{
		int wheat = warehouse.getItemList().getValue("WHEAT");
		// berechnet mindestBestand
		wheat = wheat - (resident.getSettlerMax()*5);
		if (wheat > 0)
		{
			int decay = wheat / 100;
			warehouse.withdrawItemValue("WHEAT", decay);
		}
	}
	
	public int getUsedBuildingCapacity()
	{
		int usedCapacity = 0;
		for (BuildPlanType bType : warehouse.getTypeCapacityList().keySet())
		{
			usedCapacity = usedCapacity + warehouse.getTypeCapacity(bType);
		}
		return usedCapacity;
	}
	
	private int getFoundCapacity()
	{
		int usedBuildCap = getUsedBuildingCapacity();
		if ( usedBuildCap > warehouse.getItemCount())
		{
			return warehouse.getItemMax() - usedBuildCap - 512;
		} else
		{
			return warehouse.getItemMax() - warehouse.getItemCount() - 512;
		}
	}

	private void addTreasure2List(ServerInterface server, Biome biome, Material mat)
	{
		int matFactor  = 0;
		matFactor = server.getBioneFactor( biome, mat);
		if (matFactor > 0)
		{
			int anz = matFactor / 25;
			for(int i=0; i < anz; i++)
			{
				treasureList.add(new Item(mat.name(), 1));
			}
		}
		if (matFactor < 0)
		{
			int anz = matFactor / -25;
			for(int i=0; i < anz; i++)
			{
				int index = -1;
				for (int j=0; j < treasureList.size(); j++)
				{
					if (treasureList.get(j).ItemRef()== mat.name())
					{
						index = j;
					}
				}
				if (index > -1)
				{
					Item item = treasureList.get(index);
					treasureList.remove(item);
				}
				
			}
		}
		
	}
	
	public void expandTreasureList(Biome biome, ServerInterface server)
	{
		addTreasure2List(server, biome, Material.WHEAT);
		addTreasure2List(server, biome, Material.SEEDS);
		addTreasure2List(server, biome, Material.COBBLESTONE);
		addTreasure2List(server, biome, Material.LOG);
		addTreasure2List(server, biome, Material.WOOL);
		addTreasure2List(server, biome, Material.GOLD_NUGGET);
		addTreasure2List(server, biome, Material.LEATHER);
		addTreasure2List(server, biome, Material.RAW_BEEF );
		addTreasure2List(server, biome, Material.PORK );
		addTreasure2List(server, biome, Material.RAW_CHICKEN );
		addTreasure2List(server, biome, Material.FEATHER );
		addTreasure2List(server, biome, Material.RAW_FISH );
		addTreasure2List(server, biome, Material.EMERALD );
		addTreasure2List(server, biome, Material.RED_MUSHROOM ); 
		addTreasure2List(server, biome, Material.BROWN_MUSHROOM ); 
		addTreasure2List(server, biome, Material.IRON_ORE );
		addTreasure2List(server, biome, Material.COAL_ORE );
		addTreasure2List(server, biome, Material.DIAMOND_ORE );
		addTreasure2List(server, biome, Material.EMERALD_ORE );
		addTreasure2List(server, biome, Material.REDSTONE_ORE );
		addTreasure2List(server, biome, Material.LAPIS_ORE );
		addTreasure2List(server, biome, Material.GOLD_ORE );
	}

	
	private String getFoundItem()
	{
		int Dice = treasureList.size()-1;
		int wuerfel = (int) (Math.random()*Dice+1);
		return treasureList.get(wuerfel).ItemRef();
//		switch (wuerfel)
//		{
//		case 1 : return Material.WOOD.name();
//		case 2 : return Material.STICK.name();
//		case 3 : return Material.IRON_ORE.name();
//		case 4 : return Material.IRON_INGOT.name();
//		case 5 : return Material.LOG.name();
//		case 6 : return Material.SEEDS.name();
//		case 7 : return Material.WOOD.name();
//		case 8 : return Material.DIRT.name();
//		case 9 : return Material.SEEDS.name();
//		case 10 : return Material.CARROT.name();
//		case 11 : return Material.BREAD.name();
//		case 12 : return Material.WATER.name();
//		case 13 : return Material.WOOL.name();
//		case 14 : return Material.EMERALD.name();
//		case 15 : return Material.DIRT.name();
//		case 16 : return Material.SAND.name();
//		case 20 : return Material.GOLD_NUGGET.name();
//		default :
//			return Material.AIR.name();
//		}
	}
	
	private void checkFoundItems(ServerInterface server)
	{
		if (getFoundCapacity() < resident.getSettlerCount()-townhall.getWorkerCount())
		{
			return;
		}
		int notWorker = resident.getSettlerCount()-townhall.getWorkerCount();
		int Dice = 100;
		int wuerfel = 0;
		String foundItem = "";
		for (int i = 0; i < notWorker; i++)
		{
			wuerfel = (int) (Math.random()*Dice+1);
			if (wuerfel < 3)
			{
				foundItem = getFoundItem();
				if (foundItem != Material.AIR.name())
				{
					if (getFoundCapacity() > 1)
					{
						warehouse.depositItemValue(foundItem, 1);
						productionOverview.addCycleValue(foundItem, 1);
					}
				}
			}
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
		taxSum = taxSum + townhall.getWorkerCount() * MessageText.SETTLER_TAXE;
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
		int tavernNeeded = (resident.getSettlerCount() / MessageText.ENTERTAIN_SETTLERS);
		int tavernCount = 0;
		double factor = 0.0;
		for (Building building : buildingList.getBuildingList().values())
		{
			if (building.isEnabled())
			{
				if (building.getBuildingType() == BuildPlanType.TAVERNE)
				{
					tavernCount++;
				}
			}
		}
		if (tavernCount > 0)
		{
			if (tavernNeeded >= tavernCount)
			{
			  factor = ((double) tavernCount  / (double)tavernNeeded );
			} else
			{
				factor = 0.5;
			}
		}
		
		return factor;
	}
	
	/**
	 * calculate the whole happines for the different influences 
	 */
	public void setHappiness()
	{
		double sumDif = 0.0;
//		double resiDif = 0.0;
		EntertainFactor = calcEntertainment();
		SettlerFactor = resident.calcResidentHappiness(SettlerFactor); //resident.getHappiness());
		FoodFactor = consumeFood(); //SettlerFactor);
		sumDif = EntertainFactor + SettlerFactor + FoodFactor;
		resident.setHappiness(sumDif);
		resident.settlerCount();

	}
	
	
	private double checkConsume(String foodItem , int amount, int required)
	{
		double factor = 0.0; 
		if (required > amount)
		{	
			// keine Versorgung
			if (resident.getSettlerCount() > 5)
			{
				
				factor = hungerCounter + ((double)required / (double)resident.getSettlerMax()) * -1.0;
				if (foodConsumCounter > MIN_FOODCONSUM_COUNTER)
				{
					foodConsumCounter = foodConsumCounter + factor;
				}
				requiredProduction.depositItem(foodItem, required);
				hungerCounter = factor ; // hungerCounter + factor;
				if (resident.getHappiness() < MIN_FOODCONSUM_COUNTER)
				{
					factor = 0.0;
				}
			}
		} else
		{
			hungerCounter = 0.0;
			warehouse.withdrawItemValue(foodItem, required);
//			System.out.println(foodItem+":"+required);
			productionOverview.addCycleValue(foodItem, (required* -1));
			if (foodConsumCounter > MIN_FOODCONSUM_COUNTER)
			{
				foodConsumCounter = foodConsumCounter + ((double)resident.getSettlerCount() / 20.0);
			}
			if (foodConsumCounter < 1)
			{
				if (resident.getHappiness() > MIN_FOODCONSUM_COUNTER)
				{
					factor = -0.1;
				} else
				{
					factor = 0.0;
				}
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
	 * calculate happines for the food supply of the settlers
	 * - no influence if fodd supply is guarantee 
	 * - haevy influence if food supply too low.
	 * the settlers are all supplied or none  
	 * @param oldFactor
	 * @return happiness factor of food supply 
	 */
	private double consumeFood() //double oldFactor)
	{
		double factor = 0.0; 
		int required = resident.getSettlerCount();
		String foodItem = "";
		int amount = 0;
		// Bread consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "BREAD";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount);
			}
		}
		foodItem = "WHEAT";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > required)
		{
			factor = factor + checkConsume(foodItem, amount, required);
			
		} else
		{
			factor = factor + checkConsume(foodItem, amount, required);
		}
//		
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

	private void setStoreCapacity()
	{
		warehouse.getTypeCapacityList().clear();
		for (Building building : buildingList.getBuildingList().values())
		{
			warehouse.setTypeCapacity(building.getBuildingType(), building.getStoreCapacity());
		}
	}
	
	private boolean checkStoreCapacity(ServerInterface server, Building building)
	{
		String itemRef = "";
		boolean isResult = true;
		ItemArray products = building.buildingProd(server, building.getHsRegionType());
		for (Item item : products)
		{
			itemRef = item.ItemRef();
			if (warehouse.getItemList().getValue(itemRef) > warehouse.getTypeCapacity(building.getBuildingType()))
			{
				isResult = false;
			}
		}
		return isResult;
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
			if ((building.isEnabled()) &&(building.getHsRegionType().equalsIgnoreCase("kornfeld")))
			{
				if (workerSum >= workerCount + building.getWorkerNeeded())
				{
					workerCount = workerCount + building.getWorkerNeeded();
					building.setWorkerInstalled(building.getWorkerNeeded());
				} else
				{
//					building.setIsEnabled(false);
				}
			}
		}
		for (Building building : buildingList.getBuildingList().values())
		{
			if ((building.isEnabled()) &&(!building.getHsRegionType().equalsIgnoreCase("kornfeld")))
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
				// Pruefe ob StorageCapacitaet des Types ausgelastet ist
				if (checkStoreCapacity(server, building))
				{
					building.setIsEnabled(true);
				} else
				{
					building.setIsEnabled(false);
				}
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

	
	/**
	 * get production get from producer buildings in the settlement
	 * @param server
	 */
	public void doProduce(ServerInterface server)
	{
		double prodFactor = 1;
		int iValue = 0;
		double sale = 0.0;
		double cost = 0.0;
		double account = 0.0; 
		ItemArray products;
		ItemList ingredients;
		requiredProduction.clear();
		productionOverview.resetLastAll();
		setStoreCapacity();
		initTreasureList();
		expandTreasureList(getBiome(), server);
//		checkDecay();
		checkFoundItems(server);
		for (Building building : buildingList.getBuildingList().values())
		{
			// setze defaultBiome auf Settlement Biome 
			if (building.getBiome() == null)
			{
				building.setBiome(biome);
			}
			if (building.isEnabled())
			{
				sale = 0.0;
				products = building.produce(server);
				for (Item item : products)
				{
					
					switch(building.getBuildingType())
					{
					case WORKSHOP:
						ingredients = server.getRecipe(item.ItemRef());
						ingredients.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef(),this.biome);
						break;
					case BAKERY:
						if (building.isSlot())
						{
//							System.out.println("SLOT "+item.ItemRef());
							ingredients = server.getRecipe(item.ItemRef());
							ingredients.remove(item.ItemRef());
						} else
						{
							ingredients = server.getRegionUpkeep(building.getHsRegionType());
						}
						prodFactor = server.getRecipeFactor(item.ItemRef(), this.biome);
						break;
					case TAVERNE:
						if (resident.getHappiness() > Resident.getBaseHappines())
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness();
						} else
						{
							if (resident.getHappiness() > 0.0)
							{
								sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness()*TAVERNE_UNHAPPY_FACTOR;
							}
						}
						if (resident.getDeathrate() > 0)
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * TAVERNE_UNHAPPY_FACTOR;
						}
						building.setSales(sale);	
						ingredients = new ItemList();
						break;
					default :
						if (BuildPlanType.getBuildGroup(building.getBuildingType())== 200)
						{
							ingredients = server.getRecipeProd(item.ItemRef(),building.getHsRegionType());
							prodFactor = 1;
						}
						ingredients = new ItemList();
						prodFactor = server.getRecipeFactor(item.ItemRef(), this.biome);
						break;
					}
					
					if (checkStock(prodFactor, ingredients))
					{
//						iValue = item.value();
						iValue = (int)((double) item.value() *prodFactor);
						// berechne Umsatz der Produktion
						sale = building.calcSales(server,item.ItemRef());
						// berechne Kosten der Produktion
						cost = server.getRecipePrice(item.ItemRef(), ingredients);
						if ((sale - cost) > 0.0)
						{
						// setze Ertrag auf Building .. der Ertrag wird versteuert !!
							account = (sale-cost) * (double) iValue;
							building.addSales(account); //-cost);
						} else
						{
							account =  1.0 * (double) iValue;
							building.addSales(account); //-cost);
						}
						consumStock(prodFactor, ingredients);
//						System.out.println("Product-"+item.ItemRef()+":"+iValue+"/"+item.value());
						warehouse.depositItemValue(item.ItemRef(),iValue);
						productionOverview.addCycleValue(item.ItemRef(), iValue);
					}
				}
				building.addSales(sale);
			}
		}
		productionOverview.addCycle();
	}
	
	public void doCalcTax()
	{
		double taxSum = 0.0;
		double value = 0.0; 
		for (Building building : buildingList.getBuildingList().values())
		{
//			System.out.println("doCalcTax"+building.getSales());
			value = (building.getSales() * BASE_TAX_FACTOR/ 100.0);
			if (value > 0.0)
			{
				taxOverview.addCycleValue(building.getId()+"."+building.getHsRegionType() , value);
			}
			taxSum = taxSum + value;
				// pruefe ob Stronghold region enabled sind
		}
		bank.depositKonto(taxSum, "TAX_COLLECTOR");
		//  Kingdom tax are an open item
		
	}

	/**
	 * @return the buildManager
	 */
	public BuildManager buildManager()
	{
		return buildManager;
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

	/**
	 * @return the mapManager
	 */
	public MapManager getMapManager()
	{
		return mapManager;
	}

	/**
	 * @return the treasureList
	 */
	public ArrayList<Item> getTreasureList()
	{
		return treasureList;
	}

	/**
	 * @param treasureList the treasureList to set
	 */
	public void setTreasureList(ArrayList<Item> treasureList)
	{
		this.treasureList = treasureList;
	}

}
