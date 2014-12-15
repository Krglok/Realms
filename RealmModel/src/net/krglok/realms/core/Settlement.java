package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
//<<<<<<< HEAD
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.MessageText;
//=======
//>>>>>>> origin/PHASE2
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.manager.TradeManager;
import net.krglok.realms.unit.BattleFieldPosition;
import net.krglok.realms.unit.BattlePlacement;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.Unit;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitList;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * <pre>
 * represent the whole settlement the central object
 * settlement based on the superregion and region of HeroStronghold
 * incorporate the the rules for production, fertility and money
 * simulation of residents and workers
 * make trading and train units for military
 * the production will be started from an external task, every ingame day 
 * there are some Managers incorporated to give the settlement the possibility to ruled by a NPC
 * 
 * hint: the settle dont have a kingdomId , only the owner has a kingdomId 
 * </pre>
 * @author Windu
 *
 */
public class Settlement //implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7534071936212709937L;
	private static final double MIN_FOODCONSUM_COUNTER = -5.0;
	private static final double TAVERNE_UNHAPPY_FACTOR = 2.0;
	private static final double BASE_TAX_FACTOR = 10;
	private static double TAVERNE_FREQUENT = 10.0;

	private static final String NEW_SETTLEMENT = "New Settlement";

	
	private static int COUNTER;
	
	private int id;
	private SettleType settleType = SettleType.NONE;
	private LocationData position;
	private String name;
	private String ownerId;
	private Owner owner;
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
	private long age;
	private BuildManager buildManager;
	private MapManager mapManager;
	private TradeManager tradeManager;
	private SettleManager settleManager;
	
	private ArrayList<Item> treasureList;
	
	private SignPosList signList;
	private LogList logList;
	
	/**
	 * instance empty settlement with
	 * - sequential ID
	 */
	public Settlement(LogList logList)
	{
		COUNTER++;
		id			= COUNTER;
		age         = 0;
		settleType 	= SettleType.NONE;
		position 	= new LocationData("", 0.0, 0.0, 0.0);
		name		= NEW_SETTLEMENT;
		ownerId 		= "";
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		barrack.setPowerMax(defaultPowerMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		bank		= new Bank(this.logList);
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
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager ();
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
	}

//<<<<<<< HEAD
	/**
	 * used by read from file
	 * @param priceList
	 */
	public Settlement(ItemPriceList priceList, LogList logList)
//=======
//	public Settlement(ItemPriceList priceList)
//>>>>>>> origin/PHASE2
	{
		COUNTER++;
		id			= COUNTER;
		age         = 0;
		settleType 	= SettleType.NONE;
		position 	= new LocationData("", 0.0, 0.0, 0.0);
		name		= NEW_SETTLEMENT;
		ownerId 		= "";
		isCapital	= false;
		this.logList = logList;
		barrack		= new Barrack(defaultUnitMax(settleType));
		barrack.setPowerMax(defaultPowerMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		bank		= new Bank(this.logList);
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
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager (priceList);
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
	}
	
	/**
	 * instances settlement with
	 * - with sequential ID
	 * - owner
	 * 
	 * @param Owner
	 */
	public Settlement(String owner, LocationData position, LogList logList)
	{
		COUNTER++;
		id			= COUNTER;
		age         = 0;
		settleType 	= SettleType.NONE;
		name		= NEW_SETTLEMENT;
		this.position 	= position;
		this.ownerId = owner;
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		barrack.setPowerMax(defaultPowerMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		this.logList = logList;
		bank		= new Bank(this.logList);
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
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager ();
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
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
	public Settlement(String owner, LocationData position, SettleType settleType, String name, Biome biome, LogList logList)
	{
		COUNTER++;
		age         = 0;
		id			= COUNTER;
		this.settleType = settleType;
		this.name		= name;
		this.position 	= position;
		this.ownerId = owner;
		isCapital	= false;
		barrack		= new Barrack(defaultUnitMax(settleType));
		barrack.setPowerMax(defaultPowerMax(settleType));
		warehouse	= new Warehouse(defaultItemMax(settleType));
		buildingList= new BuildingList();
		townhall	= new Townhall();
		this.logList = logList;
		bank		= new Bank(this.logList);
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
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager ();
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
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
			Resident resident, String world, Biome biome, long age,
			ItemPriceList priceList)
	{
		this.id = id;
		this.age        = age;
		this.settleType = settleType;
		this.name = name;
		this.ownerId = owner;
		this.isCapital = isCapital;
		this.position = position;
		this.barrack = barrack;
		this.barrack.setPowerMax(defaultPowerMax(settleType));
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
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager (priceList);
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
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
		case HAMLET : return 10 * ConfigBasis.CHEST_STORE;
		case TOWN   : return 10 * ConfigBasis.CHEST_STORE;
		case CITY   : return 4 * ConfigBasis.CHEST_STORE;
		case METROPOLIS  : return 4 * ConfigBasis.CHEST_STORE;
		case FORTRESS : return 4 * ConfigBasis.CHEST_STORE;
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
		case HAMLET : return 1 * ConfigBasis.HALL_Settler;
		case TOWN   : return 1 * ConfigBasis.HALL_Settler*2;
		case CITY   : return 2 * ConfigBasis.HALL_Settler*3;
		case METROPOLIS  : return 4 * ConfigBasis.HALL_Settler*4;
		case FORTRESS : return 4 * ConfigBasis.HALL_Settler;
		default :
			return ConfigBasis.HALL_Settler;
		}
	}

	private static int defaultPowerMax(SettleType settleType)
	{
		switch (settleType)
		{
		case HAMLET : return ConfigBasis.HALL_Power;
		case TOWN   : return ConfigBasis.TOWN_Power;
		case CITY   : return ConfigBasis.CITY_Power;
		case METROPOLIS  : return ConfigBasis.METROPOL_Power;
		case FORTRESS : return ConfigBasis.CASTLE_Power;
		default :
			return 100;
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

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
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

	/**
	 * give power of settlement = sum of barrack and units
	 * @return
	 */
	public int getPower()
	{
		int power = barrack.getPower();
		for (Unit unit : barrack.getUnitList())
		{
			power = power + unit.getPower();
		}
		
		return power;
	}
	
	/**
	 * setup a standard defender BattlePlacement
	 * @return
	 */
	public BattlePlacement getDefenders()
	{
		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();

		UnitList unitList = new UnitList();
		for (Unit unit : barrack.getUnitList())
		{
			unitList.add(unitFactory.erzeugeUnit(unit.getUnitType()));
		}
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
//		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		units.setPlaceUnit(BattleFieldPosition.CENTER, unitList);

		return units;
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
		for (Building b : newBuildingList.values())
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
		case WAREHOUSE : return   ConfigBasis.WAREHOUSE_CHEST_FACTOR * ConfigBasis.CHEST_STORE;
		case TRADER    : return   ConfigBasis.TRADER_CHEST_FACTOR * ConfigBasis.CHEST_STORE;
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
			case TRADER    : return ConfigBasis.TRADER_CHEST_FACTOR * ConfigBasis.CHEST_STORE;
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
			for (Building b : buildingList.values())
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
//		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
//		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
//		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
//		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
//		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
//		treasureList.add(new Item(Material.EMERALD.name(),1));
		treasureList.add(new Item(Material.SAND.name(),1));
		treasureList.add(new Item(Material.GOLD_NUGGET.name(),1));
		treasureList.add(new Item(Material.WOOD.name(),1));
		treasureList.add(new Item(Material.STICK.name(),1));
		treasureList.add(new Item(Material.IRON_ORE.name(),1));
		treasureList.add(new Item(Material.IRON_INGOT.name(),1));
		treasureList.add(new Item(Material.LOG.name(),1));
		treasureList.add(new Item(Material.SEEDS.name(),1));
		treasureList.add(new Item(Material.DIRT.name(),1));
//		treasureList.add(new Item(Material.CARROT.name(),1));
		treasureList.add(new Item(Material.BREAD.name(),1));
		treasureList.add(new Item(Material.WATER.name(),1));
		treasureList.add(new Item(Material.WOOL.name(),1));
//		treasureList.add(new Item(Material.EMERALD.name(),1));
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
					settlement.barrack.setUnitMax(settlement.barrack.getUnitMax() + building.getUnitSpace());
					break;
				case CASERN :
					settlement.barrack.setUnitMax(settlement.barrack.getUnitMax() + building.getUnitSpace());
					break;
				case GARRISON :
					settlement.barrack.setUnitMax(settlement.barrack.getUnitMax() + building.getUnitSpace());
					break;
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
	
	public LogList getLogList()
	{
		return logList;
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
											Biome biome,LogList logList)
	{
		if (settleType != SettleType.NONE)
		{
			Settlement settlement = new Settlement(owner,position, settleType, settleName,biome,logList);
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

	
	/**
	 * check amount in warehouse for take items
	 * if not set requiredItemList
	 * @param prodFactor
	 * @param items
	 * @return
	 */
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
	
	/**
	 * consum items from warehouse
	 * @param prodFactor
	 * @param items
	 */
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
		return warehouse.getUsedCapacity();
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
//		addTreasure2List(server, biome, Material.EMERALD );
		addTreasure2List(server, biome, Material.RED_MUSHROOM ); 
		addTreasure2List(server, biome, Material.BROWN_MUSHROOM ); 
		addTreasure2List(server, biome, Material.IRON_ORE );
		addTreasure2List(server, biome, Material.COAL_ORE );
		addTreasure2List(server, biome, Material.DIAMOND_ORE );
//		addTreasure2List(server, biome, Material.EMERALD_ORE );
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
		for (Building building : buildingList.values())
		{
			if (building.isEnabled())
			{
				taxSum = taxSum + building.getTaxe(server, this.resident.getSettlerCount());
			}
		}
		taxSum = taxSum + townhall.getWorkerCount() * ConfigBasis.SETTLER_TAXE;
//		taxSum = resident.getSettlerCount() * SETTLER_TAXE;
		System.out.println("Tax Sum : "+String.valueOf(taxSum));
		bank.addKonto(taxSum,"TAX", getId());
	}
	
	/**
	 * calculate settlerMax from buildings and set 
	 */
	public void setSettlerMax()
	{
		int settlerMax = 5;
		for (Building building : buildingList.values())
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
		int tavernNeeded = (resident.getSettlerCount() / ConfigBasis.ENTERTAIN_SETTLERS);
		int tavernCount = 0;
		double factor = 0.0;
		for (Building building : buildingList.values())
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
//		logList.addHappiness("CYCLE", getId(), sumDif, EntertainFactor, SettlerFactor, FoodFactor, "CraftManager", getAge());
		resident.setHappiness(sumDif);
		resident.settlerCalculation();
//		logList.addSettler("CYCLE", getId(), resident.getSettlerCount(), resident.getBirthrate(), resident.getDeathrate(), "CraftManager", getAge());
		UnitFactory unitFactory = new UnitFactory();
		for (Unit unit : barrack.getUnitList())
		{
			ItemList ingredients = unitFactory.militaryConsum(unit.getUnitType());
			double prodFactor  = 1.0;
			if (checkStock(prodFactor, ingredients))
			{
				consumStock(prodFactor, ingredients);
				if (unit.getHappiness() < 1.0)
				{
					unit.addHappiness(0.1);
				}
			} else
			{
				if (unit.getHappiness() > -1.0)
				{
					unit.addHappiness(-0.1);
				}
			}
		}
		int value = (int) (resident.getSettlerCount() * resident.getHappiness()); 
		barrack.addPower(value);
	}
	
	
	private double checkConsume(String foodItem , int amount, int required, double happyFactor)
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
			if (foodConsumCounter < 0.0)
			{
				if (resident.getHappiness() > MIN_FOODCONSUM_COUNTER)
				{
					factor = -0.1;
				} else
				{
					factor = happyFactor;
				}
			} else
			{
				if (resident.getHappiness() < 0.6)
				{
					if (resident.getSettlerMax() > resident.getSettlerCount())
					{
						factor = happyFactor;
					} else
					{
						factor = happyFactor/2;
					}
					
				} else
				{
					factor = happyFactor;
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
		// Fish consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = Material.COOKED_FISH.name();
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required, 0.3);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount, 0.3);
			}
		}
		// Mushroom Soup consume before wheat or mushroom consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "MUSHROOM_SOUP";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required,0.3);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount,0.3);
			}
		}
		// Mushroom consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "RED_MUSHROOM";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required, 0.0);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount, 0.0);
			}
		}
		// Mushroom consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "BROWN_MUSHROOM";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required, 0.0);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount, 0.0);
			}
		}
		// Bread consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "BREAD";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			if (amount > required)
			{
				factor = factor + checkConsume(foodItem, amount, required, 0.5);
				
			} else
			{
				required = required - amount;
				factor = factor + checkConsume(foodItem, amount, amount, 0.5);
			}
		}
		
		//  Wheat is the last consum item
		//  without wheat the residents are very unhappy
		foodItem = "WHEAT";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > required)
		{
			factor = factor + checkConsume(foodItem, amount, required,0.0);
			
		} else
		{
			factor = factor + checkConsume(foodItem, amount, required, 0.0);
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
		for (Building building : buildingList.values())
		{
			if (building.isEnabled())
			{
				workerSum = workerSum + building.getWorkerNeeded();
			}
		}
		townhall.setWorkerNeeded(workerSum);
	}

	/**
	 * calculate the actual stack size for the warehouse items
	 */
	private void setStoreCapacity()
	{
		warehouse.setStoreCapacity();			
	}
	
	/**
	 * check the storage capacity and the maxStorage for each item based on :
	 * - item store < maxSlots / 4
	 * - item store <=  item.capacity * 2;
	 * 
	 * @param server
	 * @param building
	 * @return
	 */
	private boolean checkStoreCapacity(ServerInterface server, Building building)
	{
		String itemRef = "";
		boolean isResult = true;
		ItemArray products = building.buildingProd(server, building.getHsRegionType());
		for (Item item : products)
		{
			itemRef = item.ItemRef();
			// check MaxStorage
			if ((warehouse.getItemList().getValue(itemRef)/64) < (warehouse.getItemMax() / 64 / 5))
			{
//				//check 
//				if ((warehouse.getItemList().getValue(itemRef)/64) >= (warehouse.getTypeCapacityList().getValue(itemRef)*2))
//					if ((warehouse.getItemList().getValue(itemRef)/64) >= (warehouse.getTypeCapacityList().getValue(itemRef)*2))
//				{
//					isResult = false;
//					System.out.println(getId()+" :TypCapacity "+itemRef+":"+(warehouse.getItemList().getValue(itemRef)/64)+"/"+(warehouse.getTypeCapacityList().getValue(itemRef)*2));
//				}
			} else
			{
//				System.out.println(getId()+" :MaxItems "+itemRef+":"+(warehouse.getItemList().getValue(itemRef)/64) +"/"+ (warehouse.getItemMax() / 64 / 4));
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
		for (Building building : buildingList.values())
		{
			if ((building.isEnabled()) &&(building.getBuildingType() == BuildPlanType.WHEAT))
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
		for (Building building : buildingList.values())
		{
			if ((building.isEnabled()) &&((building.getBuildingType() != BuildPlanType.WHEAT)))
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
		for (Building building : buildingList.values())
		{
			if (building.getHsRegionType() != null)
			{	
				if (building.isActive())
				{
					// Pruefe ob StorageCapacitaet des Types ausgelastet ist
					switch (BuildPlanType.getBuildGroup(building.getBuildingType()))
					{
						case 2 : // normal production
							if (checkStoreCapacity(server, building))
							{
								building.setIsEnabled(true);
							} else
							{
								building.setIsEnabled(false);
							}
							// pruefe ob Stronghold region enabled sind
							server.checkRegionEnabled(building.getHsRegion());
							break;
						case 5: //unit production
							if (building.getMaxTrain() > 0)
							{
								building.setIsEnabled(true);
							} else
							{
								building.setIsEnabled(false);
							}

							break;
						default:  // other buildings are not effected
						break;	
					}
				}
			} else
			{
				System.out.println("BuildRegionType null :"+building.getId()+":"+building.getBuildingType());
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
	 * each Building will separate calculate 
	 * @param server
	 */
	public void doProduce(ServerInterface server)
	{
		// increment age of the Setlement in production cycles
		age++;
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
		for (Building building : buildingList.values())
		{
			// setze defaultBiome auf Settlement Biome 
			if (building.getBiome() == null)
			{
				building.setBiome(biome);
			}
			building.setSales(0.0);
			if (building.isEnabled())
			{
				if ((BuildPlanType.getBuildGroup(building.getBuildingType())== 2)
					|| (BuildPlanType.getBuildGroup(building.getBuildingType())== 3))
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
//							System.out.println("WS " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
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
	//						System.out.println("doProd:"+building.getHsRegionType()+":"+BuildPlanType.getBuildGroup(building.getBuildingType()));
							ingredients = new ItemList();
							ingredients = server.getRecipeProd(item.ItemRef(),building.getHsRegionType());
							prodFactor = 1;
//								System.out.println(this.getId()+" :doProd:"+building.getHsRegionType()+":"+ingredients.size());
							prodFactor = server.getRecipeFactor(item.ItemRef(), this.biome);
							break;
						}
						
						if (checkStock(prodFactor, ingredients))
						{
//							System.out.println("Prod " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
	//						iValue = item.value();
							iValue = (int)((double) item.value() *prodFactor);
							// berechne Verkaufpreis der Produktion
							logList.addProduction(building.getBuildingType().name(), getId(), building.getId(), item.ItemRef(), iValue, "CraftManager",getAge());
							sale = building.calcSales(server,item);
							// berechne die MaterialKosten der Produktion
							
							cost = server.getRecipePrice(item.ItemRef(), ingredients);
							if ((sale - cost) > 0.0)
							{
							// setze Ertrag auf Building .. der Ertrag wird versteuert !!
								account = (sale-cost) * (double) iValue / 2;
//								logList.addProductionSale(building.getBuildingType().name(), getId(), building.getId(), account, "CraftManager",getAge());
							} else
							{
								account =  1.0 * (double) iValue;
//								logList.addProductionSale(building.getBuildingType().name(), getId(), building.getId(), account, "CraftManager",getAge());
							}
							building.addSales(account); //-cost);
							bank.depositKonto(account, "ProdSale ", getId());
//							System.out.println("ProdSale "+this.getId()+" : "+building.getHsRegionType() +" : "+account);
							consumStock(prodFactor, ingredients);
//							System.out.println("Product-"+item.ItemRef()+":"+iValue+"/"+item.value());
							warehouse.depositItemValue(item.ItemRef(),iValue);
							productionOverview.addCycleValue(item.ItemRef(), iValue);
						}
					}
//					building.addSales(sale);
				}
				
				// unit production
				if (BuildPlanType.getBuildGroup(building.getBuildingType())== 5)
				{
					if (building.isEnabled())
					{
						switch(building.getBuildingType())
						{
						case GUARDHOUSE:
							if (building.getTrainCounter() == 0)
							{
								if (resident.getSettlerCount() > townhall.getWorkerCount())
								{
									ingredients = building.militaryProduction();
									prodFactor  = 1.0;
									if (checkStock(prodFactor, ingredients))
									{
										// ausrüstung abbuchen
										consumStock(prodFactor, ingredients);
										// Siedler aus vorrat nehmen
										resident.depositSettler(-1);
										// Counter starten
										building.addTrainCounter(1);
									} else
									{
										System.out.println("No Traning Start due to Stock");
									}
								} else
								{
									System.out.println("No Traning Start due to Resident");
								}
		//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							} else
							{
								ingredients = building.militaryConsum();
								prodFactor  = 1.0;
								if (checkStock(prodFactor, ingredients))
								{
									consumStock(prodFactor, ingredients);
									building.addTrainCounter(1);
								} else
								{
									System.out.println("No Traning Consum");
								}
							}
							break;
						default:
							break;
						}
					}
				}
			} else
			{
//				System.out.println(this.getId()+" :doEnable:"+building.getHsRegionType()+":"+building.isEnabled());
			}
		}
		productionOverview.addCycle();
	}
	
	public void doCalcTax()
	{
		double taxSum = 0.0;
		double value = 0.0; 
//		for (Building building : buildingList.getBuildingList().values())
//		{
//			value = (building.getSales() * BASE_TAX_FACTOR/ 100.0);
////			System.out.println("doCalcTax"+building.getSales()+":"+value);
//			if (value > 0.0)
//			{
//				taxOverview.addCycleValue(building.getId()+"."+building.getHsRegionType() , value);
//			}
//			taxSum = taxSum + value;
//			// reset building.sale
//			building.setSales(0.0);
//		}
//		taxSum = taxSum + (townhall.getWorkerCount()  * ConfigBasis.SETTLER_TAXE);
		taxSum = taxSum + (resident.getSettlerCount() * ConfigBasis.SETTLER_TAXE);
		bank.depositKonto(taxSum, "TAX_COLLECTOR", getId());
		System.out.println("doCalcTax "+this.getId()+" : "+taxSum);
		//  Kingdom tax are an open item
		
	}
	
	/**
	 * give trained units to barrack
	 * @param unitFactory
	 */
	public void doUnitTrain(UnitFactory unitFactory)
	{
		for (Building building : buildingList.values())
		{
			// unit production
			if (BuildPlanType.getBuildGroup(building.getBuildingType())== 5)
			{
				if (building.isEnabled())
				{
					switch(building.getBuildingType())
					{
					case GUARDHOUSE:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							Unit unit = unitFactory.erzeugeUnit(building.getTrainType());
							unit.setSettleId(this.id);
							barrack.getUnitList().add(unit);
							building.addMaxTrain(-1);
							building.setIsEnabled(false);
							building.setTrainCounter(0);
						} else
						{
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

	/**
	 * @return the buildManager
	 */
	public BuildManager buildManager()
	{
		return buildManager;
	}

	public TradeManager tradeManager()
	{
		return tradeManager;
	}

	public SettleManager settleManager()
	{
		return settleManager;
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

	public long getAge()
	{
		return age;
	}

	public void setAge(long value)
	{
		this.age = value;
	}

	public SignPosList getSignList() 
	{
		return signList;
	}

	public void setSignList(SignPosList signList) 
	{
		this.signList = signList;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Owner owner)
	{
		this.owner = owner;
		this.setOwnerId(owner.getPlayerName());
	}

	public Owner getOwner()
	{
		return owner;
	}

}
