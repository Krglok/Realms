package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.krglok.realms.Common.Bank;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.ItemPriceList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.manager.ReputationList;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.manager.TradeManager;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.unit.BattleFieldPosition;
import net.krglok.realms.unit.BattlePlacement;
import net.krglok.realms.unit.UnitArcher;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitHeavyInfantry;
import net.krglok.realms.unit.UnitKnight;
import net.krglok.realms.unit.UnitLightInfantry;
import net.krglok.realms.unit.UnitList;
import net.krglok.realms.unit.UnitMilitia;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Material;
import org.bukkit.block.Biome;
/**
 * <pre>
 * Represent the urban settlement  
 * Settlement based on the superregion of HeroStronghold
 * Simulation of residents and workers
 * Use the inherited managers for 
 * - build up building, 
 * - make trading 
 * - train units for military
 * Has a settleManager for controlling and managing the settlement
 * The production will be started from an external task, every ingame day 
 * The settlement pay tax to his liege lord
 * The objects of this class are stored persistent 
 * 
 * hint: the settlement dont direct link a kingdomId , only the owner has a kingdomId
 *  
 * </pre>
 * @author Windu
 *
 */
public class Settlement extends AbstractSettle //implements Serializable
{
	/**
	 * 
	 */
	private static final double BASE_TAX_FACTOR = ConfigBasis.SALES_TAX;


	
	private static int COUNTER;
	
	private Boolean isCapital;

	private Owner owner;
	private Townhall townhall;
//  private Headquarter headquarter;

	private Double buildingTax ;
	
	private BoardItemList taxOverview;

//	private double EntertainFactor = 0.0;
//	private double FoodFactor = 0.0;
	private double SettlerFactor = 0.0;

	private Biome biome;
	
	private BuildManager buildManager;
	private MapManager mapManager;
	private TradeManager tradeManager;
	private SettleManager settleManager;
	
	private ArrayList<Item> treasureList;
	
	private SignPosList signList;

	private double sales;
	private double taxSum;
	
	/**
	 * instance empty settlement with
	 * - sequential ID
	 */
	public Settlement() //LogList logList)
	{
		super();
		COUNTER++;
		this.id			= COUNTER;
		position 	= new LocationData("", 0.0, 0.0, 0.0);
		isCapital	= false;
		townhall	= new Townhall();
		sales = 0.0;
		taxSum = 0.0;
		setBuildingTax(BASE_TAX_FACTOR);
		taxOverview = new BoardItemList();
		setBiome(Biome.SKY);
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager ();
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		setSignList(new SignPosList());
	}

	/**
	 * used by read from file DataStore
	 * @param priceList
	 */
	public Settlement(ItemPriceList priceList) //, LogList logList)
	{
		COUNTER++;
		id			= COUNTER;
		position 	= new LocationData("", 0.0, 0.0, 0.0);
		isCapital	= false;
		townhall	= new Townhall();
		sales = 0.0;
		taxSum = 0.0;
		setBuildingTax(BASE_TAX_FACTOR);
		taxOverview = new BoardItemList();
		setBiome(Biome.SKY);
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager (priceList);
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		reputations = new ReputationList();
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
	public Settlement(int ownerId, LocationData position, SettleType settleType, String name, Biome biome) //, LogList logList)
	{
		COUNTER++;
		id			= COUNTER;
		this.settleType = settleType;
		this.name		= name;
		this.position 	= position;
		this.ownerId = ownerId;
		isCapital	= false;
		townhall	= new Townhall();
		sales = 0.0;
		taxSum = 0.0;
		setBuildingTax(BASE_TAX_FACTOR);
		taxOverview = new BoardItemList();
		this.biome = biome;
		trader = new Trader();
		buildManager = new BuildManager();
		mapManager  = new MapManager(settleType,70,true);
		tradeManager = new TradeManager ();
		settleManager = new SettleManager ();
		treasureList =  new ArrayList<Item>();
		reputations = new ReputationList();
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
			LocationData position, int ownerId,
			Boolean isCapital, Barrack barrack, Warehouse warehouse,
			BuildingList buildingList, Townhall townhall, Bank bank,
			Resident resident, String world, Biome biome, long age,
			ItemPriceList priceList,
			int kingdomId,
			int lehenId
			)
	{
		this.id = id;
		this.age        = age;
		this.settleType = settleType;
		this.name = name;
		this.ownerId = ownerId;
		this.tributId = lehenId;
		this.isCapital = isCapital;
		this.position = position;
		this.barrack = barrack;
		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));
		this.warehouse = warehouse;
		this.buildingList = buildingList;
		this.townhall = townhall;
		this.bank = bank;
		this.resident = resident;
		sales = 0.0;
		taxSum = 0.0;
		setBuildingTax(BASE_TAX_FACTOR);
		taxOverview = new BoardItemList();
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
	
	
//	public void setLogList(LogList logList)
//	{
//		this.logList = logList;
//	}
	
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
	

	
	public void initSettlement(ItemPriceList priceList)
	{
		initSettlement();
		tradeManager.setPriceList(priceList);
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
		this.warehouse.setItemMax(ConfigBasis.defaultItemMax(settleType));
		this.barrack.setUnitMax(ConfigBasis.defaultUnitMax(settleType));
		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));

	}

//	public String getName()
//	{
//		return name;
//	}
//
//	public void setName(String name)
//	{
//		this.name = name;
//	}

	public int getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(int ownerId)
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

//	public void setBarrack(Barrack barrack)
//	{
//		this.barrack = barrack;
//	}

	/**
	 * give power of settlement = sum of barrack and units
	 * @return
	 */
	public int getPower()
	{
		int power = barrack.getPower();
		for (NpcData unit : barrack.getUnitList())
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
//		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();

		UnitList unitList = new UnitList();
		for (NpcData unit : barrack.getUnitList())
		{
			unitList.add(unit);
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
		this.checkBuildingType();

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
		this.checkBuildingType();
	}
	
	
	/**
	 * Initialisiert die globale Treasure List
	 * Achtung: Die Liste ist nicht vom Biome abhaengig
	 */
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
	 * recalculate settlement parameter based on buildings
	 * - Warehouse.maxItem from actual buildingList with  ConfigBasis.getWarehouseItemMax
	 * - Barrack.maxUnit
	 * - Trader.maxOrder
	 */
	public void checkBuildingType()
	{
		this.barrack.setUnitMax(buildingList.getMaxUnit());
		this.warehouse.setItemMax(buildingList.getMaxStorage());
		this.trader.setOrderMax(buildingList.getMaxOrder());
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

	
	public Double getBuildingTax()
	{
		return buildingTax;
	}

	public void setBuildingTax(Double buildingTax)
	{
		this.buildingTax = buildingTax;
	}

//	public double getFoodConsumCounter()
//	{
//		return foodConsumCounter;
//	}

//	public double getEntertainFactor()
//	{
//		return EntertainFactor;
//	}

//	public double getFoodFactor()
//	{
//		return FoodFactor;
//	}

	public double getSettlerFactor()
	{
		return SettlerFactor;
	}

	public String getWorld()
	{
		return position.getWorld();
	}

//	public void setWorld(String world)
//	{
//		this.world = position.getWorld();
//	}



	public BoardItemList getTaxOverview()
	{
		return taxOverview;
	}
	
//	public LogList getLogList()
//	{
//		return logList;
//	}

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
											settleType, String settleName, int ownerId, 
											HashMap<String,String> regionTypes, 
											HashMap<String,String> regionBuildings,
											Biome biome) //,LogList logList)
	{
		if (settleType != SettleType.NONE)
		{
			Settlement settlement = new Settlement(ownerId,position, settleType, settleName,biome); //,logList);
//			BuildingList buildingList = new BuildingList();
			int regionId = 0;
			String BuildingTypeName = "";
			String regionType = "";
			boolean isRegion = false;
			BuildingList bList = new BuildingList();
			for (String region : regionTypes.keySet())
			{
				regionId = Integer.valueOf(region);
				
				regionType = regionTypes.get(region);
				
				BuildingTypeName   = regionBuildings.get(region);
				isRegion = true;
				bList.addBuilding(Building.createRegionBuilding(BuildingTypeName, regionId, regionType, isRegion));
			}
			settlement.setBuildingList(bList);
			settlement.checkBuildingType();
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
//				System.out.println("miss: "+itemRef+":"+iValue);
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
	
//	/**
//	 * consum items from warehouse
//	 * @param prodFactor
//	 * @param items
//	 */
//	public boolean consumStock(double prodFactor, ItemList items)
//	{
//		int iValue = 0;
//		for (Item item : items.values())
//		{
//			iValue = (int)((double) item.value() *prodFactor);
//			if (this.getWarehouse().withdrawItemValue(item.ItemRef(), iValue);
////			System.out.println("Withdraw-"+item.ItemRef()+":"+iValue+":"+prodFactor);
//		}
//	}
	
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
		matFactor = server.getBiomeFactor( biome, mat);
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

	/**
	 * Ermittelt das gefundeneItem bei einer Schatzsuche fuer Bettler
	 * 
	 * @return  String, Materialname des gefundenen Item
	 */
	private String getFoundItem()
	{
		int Dice = treasureList.size()-1;
		int wuerfel = (int) (Math.random()*Dice+1);
		return treasureList.get(wuerfel).ItemRef();
	}
	
	/**
	 * Wuerfelt fuer einen NPC ob er ein Schatzitem findet.
	 * @param server
	 * @param npc
	 */
	private void getTreasue(ServerInterface server, NpcData npc)
	{
		int Dice = 100;
		int wuerfel = 0;
		String foundItem = "";

		wuerfel = (int) (Math.random()*Dice+1);
//		if (wuerfel < 8)
		//  Wahrscheinlichkeit stark erhoeht um verhungern zu verhindern
		//  wenn zuwenig Arbeit da ist.
		if (wuerfel < 75)
		{
			foundItem = getFoundItem();
			if (foundItem != Material.AIR.name())
			{
//				System.out.println("Treasure: "+foundItem);
				if (getFoundCapacity() > 1)
				{
					npc.depositMoney(server.getItemPrice(foundItem));
					warehouse.depositItemValue(foundItem, 1);
					productionOverview.addCycleValue(foundItem, 1);
				}
			}
		}
	}
	
	/**
	 * check for treasure and give child money when hungry
	 *  
	 * @param server
	 */
	private void checkFoundItems(ServerInterface server)
	{
		if (getFoundCapacity() < resident.getSettlerCount()-townhall.getWorkerCount())
		{
			return;
		}
		int notWorker = resident.getSettlerCount()-townhall.getWorkerCount();
		NpcList homeNpc = resident.getNpcList(); //.getSettleWorker();
		NpcList treasureNpc = new NpcList();
//		Iterator<NpcData> npcIterator = homeNpc.values().iterator();
		for (NpcData npc : homeNpc.values())
		{
			if (npc.getWorkBuilding() == 0)
			{
				treasureNpc.putNpc(npc);
			}
			// give hungry chil money from settlement
			if (npc.isChild())
			{
				if (npc.getMoney() <= 1.0)
				{
					if (npc.hungerCounter < ConfigBasis.HUNGER_BEGGAR)
					{
						if (bank.getKonto() > resident.getSettlerCount())
						{
//							System.out.println("ChildBeggar"+npc.getId());
							bank.withdrawKonto(1.0, "ChildBeggar", this.id);
							npc.depositMoney(2.0);
						}
					}
				}
			}
		}

		for (NpcData npc : treasureNpc.values())
		{
			getTreasue(server, npc);
			if (npc.getNpcType() == NPCType.BEGGAR)
			{
				getTreasue(server, npc);
			}

		}
		
	}
	
	/**
	 * get Taxe from buildings and deposit in bank
	 * @param server
	 */
	public double getTaxe(ServerInterface server)
	{
		double taxSum = 0.0;
		double taxSettler = 0.0;
//		for (Building building : buildingList.values())
//		{
//			if (building.isEnabled())
//			{
//				taxSum = taxSum + building.getTaxe(server, this.resident.getSettlerCount());
//			}
//		}
		for (NpcData npc : getResident().getNpcList().getTaxSettler().values())
		{
			if (npc.getMoney() > ConfigBasis.SETTLER_TAXE)
			{
				taxSettler = taxSettler + ConfigBasis.SETTLER_TAXE;
				npc.depositMoney((ConfigBasis.SETTLER_TAXE * -1.0));
			} else
			{
				npc.setMoney(0.0);
				this.getBank().withdrawKonto(ConfigBasis.SETTLER_TAXE, npc.getName(), this.id);
				taxSettler = taxSettler + ConfigBasis.SETTLER_TAXE;
			}
		}
		taxSum = taxSum + taxSettler;
//		taxSum = resident.getSettlerCount() * SETTLER_TAXE;
		System.out.println("Tax Sum : "+String.valueOf(taxSum));
//		bank.addKonto(taxSum,"TAX", getId());
		return taxSum;
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
		boolean isMulti = false;
		ItemArray products = building.buildingProd(server, building.getHsRegionType());
		for (Item item : products)
		{
			itemRef = item.ItemRef();
			// check MaxStorage
			if ((warehouse.getItemList().getValue(itemRef)/64) < (warehouse.getItemMax() / 64 / 5))
			{
				// check if multi items produce 
				if (products.size() > 1)
				{
					isMulti = true;
				}
			} else
			{
//				System.out.println(getId()+" :MaxItems "+itemRef+":"+(warehouse.getItemList().getValue(itemRef)/64) +"/"+ (warehouse.getItemMax() / 64 / 4));
				isResult = false;
			}
		}
		// check if one of multi items can be produce
		if (isMulti)
		{
			isResult = isMulti;
		}
		return isResult;
	}
	
	
	private void resetWorkerBuild(NpcList homeNpc)
	{
		Iterator<NpcData> npcIterator = homeNpc.values().iterator();
		while (npcIterator.hasNext())
		{
			npcIterator.next().setWorkBuilding(0); 
		}
	}
	
	/**
	 * set workers to buildings. no priority
	 * @param workerSum
	 * @return workers without workingplace
	 */
	public int setWorkerToBuilding(int workerSum)
	{
//		Iterator<Building> buildingIterator = buildingList.values().iterator(); 
		NpcList homeNpc = resident.getNpcList().getSettleWorker();
		Iterator<NpcData> npcIterator = homeNpc.values().iterator();
		resetWorkerBuild(homeNpc);
//		System.out.println(" hasNext: "+npcIterator.hasNext());
		for (Building building : buildingList.getSubList(BuildPlanType.WHEAT).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getUnitType() == UnitType.SETTLER)
				{
					if ((npc.getNpcType() != NPCType.MANAGER)
						&& (npc.getNpcType() != NPCType.BUILDER)
						&& (npc.getNpcType() != NPCType.TRADER)
						&& (npc.getNpcType() != NPCType.MAPMAKER)
						)
					{
						npc.setWorkBuilding(building.getId());
		//				System.out.println(building.getBuildingType()+" : "+npc.getId());
						installed++;
					}
				}
			}
		}

		for (Building building : buildingList.getGroupSubList(ConfigBasis.BUILDPLAN_GROUP_PRODUCTION).values())
		{
			int installed = 0;
			if (building.getBuildingType() != BuildPlanType.WHEAT)
			{
				while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
				{
					NpcData npc = npcIterator.next();
					if (npc.getNpcType() != NPCType.MANAGER)
					{
						npc.setWorkBuilding(building.getId());
		//				System.out.println(building.getBuildingType()+" : "+npc.getId());
						installed++;
					}
				}
			}
		}
		
		for (Building building : buildingList.getSubList(BuildPlanType.FARMHOUSE).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getNpcType() != NPCType.MANAGER)
				{
					npc.setWorkBuilding(building.getId());
	//				System.out.println(building.getBuildingType()+" : "+npc.getId());
					installed++;
				}
			}
		}
		
		for (Building building : buildingList.getSubList(BuildPlanType.FARM).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getNpcType() != NPCType.MANAGER)
				{
					npc.setWorkBuilding(building.getId());
	//				System.out.println(building.getBuildingType()+" : "+npc.getId());
					installed++;
				}
			}
		}


		for (Building building : buildingList.getGroupSubList(ConfigBasis.BUILDPLAN_GROUP_EQUIPMENT).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getNpcType() != NPCType.MANAGER)
				{
					npc.setWorkBuilding(building.getId());
	//				System.out.println(building.getBuildingType()+" : "+npc.getId());
					installed++;
				}
			}
		}

		for (Building building : buildingList.getGroupSubList(ConfigBasis.BUILDPLAN_GROUP_ENTERTAIN).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getNpcType() != NPCType.MANAGER)
				{
					npc.setWorkBuilding(building.getId());
	//				System.out.println(building.getBuildingType()+" : "+npc.getId());
					installed++;
				}
			}
		}

		for (Building building : buildingList.getGroupSubList(ConfigBasis.BUILDPLAN_GROUP_TRADE).values())
		{
			int installed = 0;
			while ((installed < building.getWorkerNeeded()) && npcIterator.hasNext())
			{
				NpcData npc = npcIterator.next();
				if (npc.getNpcType() != NPCType.MANAGER)
				{
					npc.setWorkBuilding(building.getId());
	//				System.out.println(building.getBuildingType()+" : "+npc.getId());
					installed++;
				}
			}
		}
		// reset all other npc
//		System.out.println(" ClearNext: "+npcIterator.hasNext());
		while (npcIterator.hasNext())
		{
			npcIterator.next().setWorkBuilding(0);
		}
		
		int workerCount = 0;
		for (NpcData  npc : resident.getNpcList().values())
		{
			if (npc.getWorkBuilding() > 0)
			{
				workerCount++;
			}
		}
		townhall.setWorkerCount(workerCount);
		return workerSum-workerCount;
	}

	/**
	 * check if production is necessary for a building
	 * 
	 * @param server
	 */
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
						case ConfigBasis.BUILDPLAN_GROUP_EQUIPMENT : 
						case ConfigBasis.BUILDPLAN_GROUP_PRODUCTION : // normal production
							if (checkStoreCapacity(server, building))
							{
								building.setIsEnabled(true);
							} else
							{
								building.setIsEnabled(false);
								if (building.getBuildingType() == BuildPlanType.CABINETMAKER)
								{
									building.initIdle(5);
								}
							}
							// pruefe ob Stronghold region enabled sind
							server.checkRegionEnabled(building.getHsRegion());
							break;
						case ConfigBasis.BUILDPLAN_GROUP_MILITARY: //unit production
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


	public void setWorkerSale(Building building, double account)
	{
		NpcList homeNpc = resident.getNpcList().getBuildingWorker(building.getId());
		if (homeNpc.size() > 0)
		{
			double value = account / homeNpc.size();
			for (NpcData npc : homeNpc.values())
			{
				if (value > 0.0)
				{
					npc.depositMoney(value);
				}
			}
		} else
		{
//			System.out.println("No NPC");
		}
	}
	
	public void doResident(DataInterface data)
	{
		this.doHappiness(data);
	}
	
	/**
	 * get production get from producer buildings in the settlement
	 * each Building will separate calculate 
	 * @param server
	 */
	public void doProduce(ServerInterface server, DataInterface data, int day)
	{
		// increment age of the Setlement in production cycles
		age++;
		// is not Sunday
		if (day != 0)  
		{
			// redude required list by production cycle 
			requiredProduction.reduceRequired(); 
			productionOverview.resetLastAll();
			prodAnalyse.clear();

			this.msg.add("Day "+day);
			this.msg.add("setStoreCapacity");
			setStoreCapacity();
			this.msg.add("initTreasureList");
			initTreasureList();
			this.msg.add("expandTreasureList");
			expandTreasureList(getBiome(), server);
	//		checkDecay();
			this.msg.add("checkFoundItems");
			checkFoundItems(server);
			this.msg.add("StartBuildingProduction");
			for (Building building : buildingList.values())
			{
				if ((BuildPlanType.getBuildGroup(building.getBuildingType())!= ConfigBasis.BUILDPLAN_GROUP_MILITARY)
					&& (BuildPlanType.getBuildGroup(building.getBuildingType())> ConfigBasis.BUILDPLAN_GROUP_HOME)
					)
				{
					this.msg.add(building.getBuildingType().name()+":"+building.getId()+" MaxTrain : "+building.getMaxTrain());
					// loesche letzte Message
					building.getMsg().clear();
					// setze defaultBiome auf Settlement Biome 
					if (building.getBiome() == null)
					{
						building.setBiome(biome);
					}
					// doProduction on Building
					doProduction(server, data, building, biome);
					doService(server, data, building, biome);
				}
				// unit production
				if (BuildPlanType.getBuildGroup(building.getBuildingType())== ConfigBasis.BUILDPLAN_GROUP_MILITARY)
				{
					this.msg.add("Train check : "+building.getMaxTrain());
	
//					if (building.isEnabled())
					{
						building.setIsEnabled(true);
						doTrainStart(data, building);
//					} else
//					{
//						System.out.println("Train not enabled : "+building.getBuildingType()+" in "+this.id+" "+this.name);
					}
				}
			}
			productionOverview.addCycle();
		} else
		{
			this.msg.add("Day 0 = No Production");
		}
	}

	/**
	 * berechnet tax von Bevölkerung 
	 * vom Umsatz der Gebäude
	 * 
	 */
	public void doCalcTax()
	{
//		double taxSum = 0.0;
		double value = 0.0; 
		for (Building building : buildingList.values())
		{
			value = (building.getSales() * BASE_TAX_FACTOR/ 100.0);
			if (value > 0.0)
			{
				taxOverview.addCycleValue(building.getId()+"."+building.getHsRegionType() , value);
			}
			sales = sales + value;
			// reset building.sale
			building.setSales(0.0);
		}
//		taxSum = townhall.getWorkerCount()  * ConfigBasis.SETTLER_TAXE;
		taxSum = taxSum + (resident.getSettlerCount() * ConfigBasis.SETTLER_TAXE);
		bank.depositKonto(sales, "TAX_COLLECTOR", getId());
//		System.out.println("doCalcTax "+this.getId()+" : "+ConfigBasis.setStrformat2(sales,7)+"/"+ConfigBasis.setStrformat2(taxSum,7));
		//  Kingdom tax calculated in RealmModel
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
			if (BuildPlanType.getBuildGroup(building.getBuildingType())== 500)
			{
				if (building.isEnabled())
				{
					switch(building.getBuildingType())
					{
					case GUARDHOUSE:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.MILITIA);
								UnitMilitia.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(true);
								building.setTrainCounter(0);
							} else
							{
								System.out.println("[REALMS] Guardhouse Train Recrute not found !");
							}
						} else
						{
						}
						break;
					case ARCHERY:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.ARCHER);
								UnitArcher.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(true);
								building.setTrainCounter(0);
							}
						} else
						{
						}
						break;
					case BARRACK:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.LIGHT_INFANTRY);
								UnitLightInfantry.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(true);
								building.setTrainCounter(0);
							}
						} else
						{
						}
						break;
					case CASERN:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.HEAVY_INFANTRY);
								UnitHeavyInfantry.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(true);
								building.setTrainCounter(0);
							}
						} else
						{
						}
						break;
					case TOWER:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.KNIGHT);
								UnitKnight.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(true);
								building.setTrainCounter(0);
							}
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
	public void setOwner(Owner nOwner)
	{
		if (nOwner != null)
		{
			this.owner = nOwner;
			this.ownerId = nOwner.getId();
			this.ownerName = nOwner.getPlayerName();
		}
	}
		

	public Owner getOwner()
	{
		return owner;
	}


	/**
	 * @return the kingdomId
	 */
	public int getKingdomId()
	{
		if (owner != null)
		{
			return owner.getKingdomId();
		}
		return 0;
	}


	/**
	 * @return the lehenId
	 */
	public int getTributId()
	{
		return tributId;
	}

	/**
	 * @param lehenId the lehenId to set
	 */
	public void setTributId(int lehenId)
	{
		this.tributId = lehenId;
	}

	/**
	 * @return the sales
	 */
	public double getSales()
	{
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(double sales)
	{
		this.sales = sales;
	}

	/**
	 * @return the taxSum
	 */
	public double getTaxSum()
	{
		return taxSum;
	}

	/**
	 * @param taxSum the taxSum to set
	 */
	public void setTaxSum(double taxSum)
	{
		this.taxSum = taxSum;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName()
	{
		return ownerName;
	}

	/**
	 * @param ownerName the ownerName to set
	 */
	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}
	
}
