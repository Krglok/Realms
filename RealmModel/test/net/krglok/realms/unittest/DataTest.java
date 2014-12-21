package net.krglok.realms.unittest;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.DataStoreBuilding;
import net.krglok.realms.data.DataStoreCaseBook;
import net.krglok.realms.data.DataStoreKingdom;
import net.krglok.realms.data.DataStoreLehen;
import net.krglok.realms.data.DataStoreOwner;
import net.krglok.realms.data.DataStoreSettlement;
import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.RegimentData;
import net.krglok.realms.data.SettlementData;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.science.CaseBookList;
import net.krglok.realms.science.RealmPermission;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;
//import java.util.Map;
//import net.krglok.realms.core.Position;

/**
 * Simuliert die eingelesenen des Realms
 * im der live umgebung werden die daten aus einer Datenbank (YML) gelesen
 * @author Windu
 *
 */
public class DataTest implements DataInterface
{
	
//	private static final String NPC_1 = "NPC1";
//	private static final String NPC_2 = "NPC2";
//	private static final String NPC_4 = "NPC4";
//	private static final String PC_3 = "NPC3";
//	private static final String PC_4 = "NPC4";
//	private static final String PC_5 = "NPC5";
	@SuppressWarnings("unused")
	private static final String Realm_1_NPC = "Realm 1 NPC";
	
	private OwnerList testOwners ;
	private SettlementList settlementTest;
	private BuildingList testBuildings; 
	private RegimentList regiments; 		// data readed from file
	private CaseBookList caseBooks;
    private ItemPriceList priceList ;
    private LehenList lehenList;
    private BuildingList buildings;
	private SettlementList settlements;
	private KingdomList testKingdoms ;
//	private RealmPermission rPerm; // = new RealmPermission();
//	private KnowledgeData knowledgeData; // = new KnowledgeData();

	private SettlementData settleTestData;
	private LogList logList;
	private RegimentData regData;
	private DataStoreCaseBook caseBookData;
	private DataStoreOwner ownerData;
	private DataStoreKingdom kingdomData;
	private DataStoreLehen lehenData;
	private DataStoreBuilding buildingData;
	private DataStoreSettlement settleData;
	
	private String dataFolder;

	public DataTest(LogList logList)
	{
		this.logList = logList;
		dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
//		rPerm = new RealmPermission();
//		knowledgeData = new KnowledgeData();
		
		initTestData();

	}

	public void initTestData()
	{
		System.out.println("DataTest.initTestData()");
		this.priceList = getPriceList();
		caseBookData = new DataStoreCaseBook(dataFolder);
		ownerData = new DataStoreOwner(dataFolder);
		kingdomData = new DataStoreKingdom(dataFolder); 
		settleTestData = new SettlementData(dataFolder);
		regData = new RegimentData(dataFolder);
		lehenData = new DataStoreLehen(dataFolder);
		buildingData = new DataStoreBuilding(dataFolder);
		settleData = new DataStoreSettlement(dataFolder);
		
		initOwnerList();		// read dta from data file
		initBuildingList();
		initSettleTest(testOwners);		// read Settlements from data file
		initSettlementList();
		initKingdomList(testOwners);		// read data from data file
		initBuildingTest();		// set constant list
		initLehenList(testOwners);
		
		this.caseBooks = initCaseBookData();	// read data from data file
	}
	
	
	
//	/**
//	 * erzeugt testdaten fuer eine SettlementList mit 
//	 * - 1 Settlement 
//	 * - buildingList fuer Settlement   
//	 */
//	private void initSettlementList ()
//	{
//		LocationData position = new LocationData("SteamHaven",1000.0,64.0,1000.0);
//		testSettlements = new SettlementList(1);
//		testSettlements.addSettlement(createSettlement(1, position));
//	}
	
	public void initSettleTest(OwnerList owners)
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins";
//        File DataFile = new File(path, "Realms");
		settlementTest = new SettlementList(0);
//		Settlement settle = null;
		Owner owner = null;
//		System.out.println("==Read Settlement from File ==");
		ArrayList<String> sList = settleTestData.readSettleList();
		for (String sName : sList)
		{
			Settlement settle = settleTestData.readSettledata(Integer.valueOf(sName),this.getPriceList(),this.logList);
			String ref = settle.getOwnerId();
			if ((ref == null))
			{
				System.out.println("read Settle"+settle.getId()+" OwnerId "+"NPC_0");
				owner = owners.findPlayername(ConfigBasis.NPC_0);
				settle.setOwner(owner);
			} else
			{
				owner = owners.findPlayername(ref);
				if (owner == null)
				{
					// make a default Owner
					owner = Owner.initDefaultOwner();
					owner.setOwnerPlayer(ref, "");
					owner.initColonist();
					owners.addOwner(owner);
				}
				settle.setOwner(owner);
//				System.out.println("read Settle"+settle.getId()+" OwnerId "+ref);
			}
			settlementTest.addSettlement(settle);
//			System.out.println(settle.getOwner().getPlayerName());
		}
//		this.testSettlements= settlements; 
		
	}

//	private Settlement createSettlement(int id, LocationData position)
//	{
//		
////		Position position = position; //new Position(0.0, 0.0, 0.0);
//		this.priceList = getPriceList();
//		Owner owner = testOwners.getOwner(ConfigBasis.NPC_0);
//		Barrack barrack = new Barrack(5);
//		Warehouse warehouse = new Warehouse(6912);
//		BuildingList buildingList = new BuildingList(); 
//		Townhall townhall = new Townhall(true);
//		Bank bank = new Bank(this.logList);
//		Resident resident = new Resident();
//		resident.setSettlerCount(13);
//		Settlement settle =  new Settlement(
//				id, 
//				SettleType.HAMLET, 
//				"TestSiedlung", 
//				position, 
//				owner.getPlayerName(),
//				true, 
//				barrack, 
//				warehouse,
//				buildingList, 
//				townhall, 
//				bank,
//				resident,
//				"",
//				Biome.PLAINS,
//				0,
//				priceList
//				);
//		
//		for (Building b : testBuildings.values())
//		{
//			Settlement.addBuilding(b, settle);
//		}
//		
//		return settle;
//	}
	
	/**
	 * create a test Home, id and settler are configurable
	 * @param id
	 * @param settler
	 * @param regionId
	 * @return configured building
	 */
	public Building createBuildingHome(int id, int settler, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.HOME, 
						settler, 
						0, 
						0, 
						regionId,
						true
						);
		
	}
	
	/**
	 * create a test Production, id and workerNeeded are configurable 
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingKornfeld(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.WHEAT, 
						0,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}

	public Building createBuildingHolzfaeller(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.WOODCUTTER, 
						0,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}
	
	/**
	 * create a test townhall, id and settler are configurable
	 * @param id
	 * @param residentHome
	 * @return configured building
	 */
	
	public Building createBuildingHall(int id, int settler, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.HALL, 
						settler,
						0,
						0, 
						regionId, 
						true
						);
		
	}

	public Building createBuildingTaverne(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.TAVERNE, 
						0,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}
	
	/**
	 * create a test millitary building , id and unitSpace are configurable
	 * 
	 * @param id
	 * @param unitSpace
	 * @return configured building
	 */
	public Building createBuildingMilitary(int id, int unitSpace, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.GUARDHOUSE, 
						unitSpace,
						0,
						0, 
						regionId, 
						true
						);
		
	}

	/**
	 * create a test warehouse building , id and workerNeeded are configurable
	 * the itemMax are calculated = workerNeeded * factor
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingWarehouse(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.WAREHOUSE, 
						0,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}

	public Building createBuildingBauernhof(int id, int settler, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.FARMHOUSE, 
						settler,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}

	public Building createBuildingWerkstatt(int id, int settler, int workerNeeded, int regionId, 
			String slot1, String slot2, String slot3, String slot4, String slot5)
	{
		return  new	Building(
						id, 
						BuildPlanType.WORKSHOP, 
						settler,
						workerNeeded,
						0, 
						regionId, 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5,
						0.0,
						new LocationData("SteamHaven", 0.0, 0.0, 0.0),
//						UnitType.NONE,
						0,
						0,
						0,
						0,
						""
						);
		
	}
	
	public Building createBuildingBaecker(int id, int settler, int workerNeeded, int regionId, 
			String slot1, String slot2, String slot3, String slot4, String slot5)
	{
		return  new	Building(
						id, 
						BuildPlanType.BAKERY, 
						settler,
						workerNeeded,
						0, 
						regionId, 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5,
						0.0,
						new LocationData("SteamHaven", 0.0, 0.0, 0.0),
//						UnitType.NONE,
						0,
						0,
						0,
						0,
						""

						);
		
	}
	
	/**
	 * create a test trade building , id , settler and workerNeeded are configurable
	 * the itemMax are calculated = workerNeeded * factor
	 * the tradelineMax are calculated = settler * factor 
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingTrader(int id, int settler, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildPlanType.TRADER, 
						settler,
						workerNeeded,
						0, 
						regionId, 
						true
						);
		
	}
	
	
	private void initBuildingTest()
	{
		testBuildings = new BuildingList();
		testBuildings.addBuilding(createBuildingHome(2, 4,2));
		testBuildings.addBuilding(createBuildingHome(6, 4,6));
		testBuildings.addBuilding(createBuildingHome(7, 4,7));
		testBuildings.addBuilding(createBuildingTaverne(8, 2,8));
		testBuildings.addBuilding(createBuildingWarehouse(9, 5, 9));
		testBuildings.addBuilding(createBuildingKornfeld(16, 1, 16));
		testBuildings.addBuilding(createBuildingKornfeld(18, 1, 18));
		testBuildings.addBuilding(createBuildingHolzfaeller(19, 1, 18));
		testBuildings.addBuilding(createBuildingHome(28, 4,28));
		testBuildings.addBuilding(createBuildingHome(29, 4,29));
//		testBuildings.addBuilding(createBuildingBauernhof(31, 8, 5, 31));
//		testBuildings.addBuilding(createBuildingBauernhof(32, 8, 5, 32));
//		testBuildings.addBuilding(createBuildingBauernhof(33, 8, 5, 33));
		testBuildings.addBuilding(createBuildingHall(34, 2, 34));
//		testBuildings.addBuilding(createBuildingWerkstatt(41, 8, 5, 41, "WOOD_AXE", "STICK", "WOOD", "", ""));
//		testBuildings.addBuilding(createBuildingWerkstatt(42, 8, 5, 42, "IRON_SWORD", "BOW", "ARROW", "ARROW", "ARROW"));
//		testBuildings.addBuilding(createBuildingWerkstatt(43, 8, 5, 42, "IRON_HELMET", "IRON_CHESTPLATE", "IRON_LEGGINGS", "IRON_BOOTS", ""));
//		testBuildings.addBuilding(createBuildingBaecker(51, 8, 5, 51, "BREAD", "", "", "", ""));
//		testBuildings.addBuilding(createBuildingBaecker(52, 8, 5, 52, "BREAD", "BREAD", "BREAD", "BREAD", "BREAD"));
//		testBuildings.addBuilding(createBuildingBaecker(53, 8, 5, 53, "BREAD", "BREAD", "BREAD", "BREAD", "BREAD"));
	}
	
	

	/**
	 * 
	 * @return default list of items
	 */
	public ItemList defaultWarehouseItems()
	{
		ItemList subList = new ItemList();
		subList.addItem("BUCKET",0);
		subList.addItem("COAL",0);
		subList.addItem("COBBLESTONE",0);
		subList.addItem("COBBLESTONE_STAIRS",0);
		subList.addItem("FEATHER",0);
		subList.addItem("FENCE",0);
		subList.addItem("FENCE_GATE",0);
		subList.addItem("FLINT",0);
		subList.addItem("FURNACE",0);
		subList.addItem("GRAVEL",0);
		subList.addItem("LADDER",0);
		subList.addItem("LEATHER",0);
		subList.addItem("LOG",0);
		subList.addItem("SADDLE",0);
		subList.addItem("SAND",0);
		subList.addItem("SANDSTONE",0);
		subList.addItem("SAPLING",0);
		subList.addItem("STICK",0);
		subList.addItem("STONE",0);
		subList.addItem("STRING",0);
		subList.addItem("TORCH",0);
		subList.addItem("WALL_SIGN",0);
		subList.addItem("WATER_BUCKET",0);
		subList.addItem("WOOD",0);
		subList.addItem("WOODEN_DOOR",0); // (Block only)
		subList.addItem("WOOL",0);
		subList.addItem("WORKBENCH",0);

		return subList;
	}
	
	/**
	 * 
	 * @return list of default Food Items
	 */
	public ItemList defaultFoodItems()
	{
		ItemList subList = new ItemList();
		subList.addItem("BREAD",0);
		subList.addItem("CAKE",0);
		subList.addItem("COOKED_BEEF",0);
		subList.addItem("COOKED_CHICKEN",0);
		subList.addItem("COOKED_FISH",0);
		subList.addItem("COOKIE",0);
		subList.addItem("CROPS",0);
		subList.addItem("EGG",0);
		subList.addItem("GRILLED_PORK",0);
		subList.addItem("MELON",0);
		subList.addItem("MELON_SEEDS",0);
		subList.addItem("MILK_BUCKET",0);
		subList.addItem("PORK",0);
		subList.addItem("PUMPKIN",0);
		subList.addItem("PUMPKIN_SEEDS",0);
		subList.addItem("RAW_BEEF",0);
		subList.addItem("RAW_CHICKEN",0);
		subList.addItem("RAW_FISH",0);
		subList.addItem("SEEDS",0);
		subList.addItem("SUGAR",0);
		subList.addItem("SUGAR_CANE",0);
		subList.addItem("WHEAT",0);

		return subList;
	}

	/**
	 * prerun initOwnerList, initRealmList, defaultFoodList, defaultWarehouseItems, 
	 *  	  initTool, initArmor, initWeapon
	 * default Plot 
	 * - with out buildings
	 * - with NPC owner
	 * - with default warehouse
	 * @return a configured Settlement of a default Plot 
	 */
	public Settlement defaultPlot()
	{
		Settlement plot = new Settlement(this.logList);
		
		plot.setOwnerId(testOwners.getOwner(ConfigBasis.NPC_0).getPlayerName());
		plot.setOwner(testOwners.getOwner(ConfigBasis.NPC_0));
		plot.getWarehouse().setItemList(defaultWarehouseItems());
		return plot;
	}

	
//	public HashMap<String,String> setSiedlungAtPoition(Position pos)
//	{
//		HashMap<String,String> testData = new HashMap<String,String>();
//		HashMap<String,String> regionTypes = new HashMap<String,String>();
//		regionTypes.put("1","haupthaus");
//		regionTypes.put("2","haus_einfach");
//		regionTypes.put("3","haus_einfach");
//		regionTypes.put("4","haus_einfach");
//		regionTypes.put("5","taverne");
//		regionTypes.put("NewHaven","Siedlung");
//				
//		//this.strongholdAPI.getRegionAtPotion(pos, testData);
//		return testData;
//	}
	
	public HashMap<String,String> defaultRegionList()
	{
		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("16","WHEAT");
		regionTypes.put("9","WAREHOUSE");
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}
	
	public HashMap<String,String> defaultSuperregionList()
	{
		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("NewHaven",SettleType.HAMLET.name());
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}

	@Override
	public OwnerList getOwners()
	{
	
		return testOwners;
	}


	@Override
	public SettlementList getSettlements()
	{
		return settlements;
	}

	public SettlementList getTestSettlements()
	{
		return settlementTest;
	}

	@Override
	public KingdomList getKingdoms()
	{
		return testKingdoms;
	}
	
	public LehenList getLehen()
	{
		return lehenList;
	}

	@Override
	public void writeSettlement(Settlement settle)
	{
		settleData.writeData(settle, String.valueOf(settle.getId()));
	}
	
	public Settlement readSettlementTest(int id, ItemPriceList priceList, LogList logList)
	{
		return settleTestData.readSettledata(id, priceList,logList);
	}
	
	/**
	 * not all tile ids will converted
	 * not found id return 0 = AIR 
	 * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * @param id
	 * @return
	 */
	private Material tmxToMaterial(int id)
	{
		switch (id)
		{
		case 1: return Material.GRASS;
		case 2: return Material.STONE;
		case 3: return Material.DIRT;
		case 4: return Material.GRASS;
		case 5: return Material.WOOD;
		case 6: return Material.DOUBLE_STEP;
		case 71: return Material.CLAY;
		case 8: return Material.BRICK;
		case 9: return Material.TNT;
		case 12: return Material.WEB;
		case 13: return Material.RED_ROSE;
		case 14: return Material.YELLOW_FLOWER;
		case 16: return Material.SAPLING;
		case 20: return Material.BED_BLOCK;
		case 25: return Material.COBBLESTONE;
		case 26: return Material.BEDROCK;
		case 27: return Material.SAND;
		case 28: return Material.GRAVEL;
		case 29: return Material.LOG;
		case 30: return Material.LOG;
		case 31: return Material.WOOL;
		case 32: return Material.GOLD_BLOCK;
		case 33: return Material.DIAMOND_BLOCK;
		case 34: return Material.EMERALD_BLOCK;
		case 35: return Material.REDSTONE_BLOCK;
		case 37: return Material.RED_MUSHROOM;
		case 38: return Material.BROWN_MUSHROOM;
		case 44: return Material.CAKE_BLOCK;
		case 45: return Material.PORTAL;
		case 46: return Material.FENCE;
		case 47: return Material.NETHER_FENCE;
		case 49: return Material.GOLD_ORE;
		case 50: return Material.IRON_ORE;
		case 51: return Material.COAL_ORE;
		case 52: return Material.BOOKSHELF;
		case 53: return Material.MOSSY_COBBLESTONE;
		case 54: return Material.OBSIDIAN;
		case 57: return Material.LONG_GRASS;
		case 60: return Material.WORKBENCH;
		case 61: return Material.FURNACE;
		case 62: return Material.FURNACE;
		case 63: return Material.DISPENSER;
		case 64: return Material.DISPENSER;
		case 65: return Material.REDSTONE_TORCH_ON;
		case 67: return Material.DIODE_BLOCK_OFF;
		case 68: return Material.WOOD_BUTTON;
		case 69: return Material.TORCH;
		case 70: return Material.LEVER;
		case 72: return Material.DIODE_BLOCK_OFF;
		case 73: return Material.SPONGE;
		case 74: return Material.GLASS;
		case 75: return Material.DIAMOND_ORE;
		case 76: return Material.REDSTONE_ORE;
		case 77: return Material.LEAVES;
		case 78: return Material.LEAVES_2;
		case 82: return Material.DAYLIGHT_DETECTOR;
		case 84: return Material.WORKBENCH;
		case 85: return Material.WORKBENCH;
		case 86: return Material.FURNACE;
		case 87: return Material.FURNACE;
		case 88: return Material.SAPLING;
		case 91: return Material.WALL_SIGN;
		case 92: return Material.FIRE;
		case 94: return Material.LAVA;
		case 95: return Material.WATER;
		case 97: return Material.WOOL;
		case 99: return Material.SNOW_BLOCK;
		case 100: return Material.ICE;
		case 101: return Material.SOIL;
		case 102: return Material.CACTUS;
		case 103: return Material.CACTUS;
		case 105: return Material.CLAY;
		case 106: return Material.SUGAR_CANE_BLOCK;
		case 107: return Material.NOTE_BLOCK;
		case 108: return Material.NOTE_BLOCK;
		case 109: return Material.WATER_LILY;
		case 110: return Material.MYCEL;
		case 111: return Material.MYCEL;
		case 118: return Material.WEB;
		case 121: return Material.TORCH;
		case 122: return Material.WOOD_DOOR;
		case 123: return Material.IRON_DOOR_BLOCK;
		case 124: return Material.LADDER;
		case 125: return Material.TRAP_DOOR;
		case 126: return Material.IRON_BARDING;
		case 129: return Material.WHEAT;
		case 130: return Material.WHEAT;
		case 131: return Material.WHEAT;
		case 132: return Material.WHEAT;
		case 133: return Material.WHEAT;
		case 134: return Material.WHEAT;
		case 135: return Material.WHEAT;
		case 136: return Material.WHEAT;
		case 137: return Material.BREWING_STAND;
		case 139: return Material.FENCE_GATE;
		case 140: return Material.LEAVES;
		case 141: return Material.LEAVES_2;
		case 142: return Material.CHEST;
		case 143: return Material.ENDER_CHEST;
		case 145: return Material.LEVER;
		case 146: return Material.WOOD_DOOR;
		case 147: return Material.IRON_DOOR_BLOCK;
		case 151: return Material.PUMPKIN;
		case 152: return Material.NETHERRACK;
		case 153: return Material.SOUL_SAND;
		case 154: return Material.GLOWSTONE;
		case 161: return Material.COCOA;
		case 162: return Material.CHEST;
		case 163: return Material.CHEST;
		case 164: return Material.CHEST;
		case 165: return Material.CHEST;
		case 166: return Material.CHEST;
		case 167: return Material.CHEST;
		case 168: return Material.CHEST;

		
		default : return Material.AIR;
		}
		
	}

    private int getGid(Tile tile) {
        TileSet tileset = tile.getTileSet();
        if (tileset != null) {
            return tile.getId() + 1; //getFirstGidForTileset(tileset);
        }
        return tile.getId();
    }

    /**
     * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * the tsx and the image file must stay in the some 
	 * directory as the tmx files 
	 * errorHandling: if file not exist, empty map returned
     * @param bType
     * @param radius
     * @param offSet
     * @param path
     * @return
     */
	public BuildPlanMap readTMXBuildPlan(BuildPlanType bType, int radius, int offSet)
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\buildplan";
		BuildPlanMap buildPlan = new BuildPlanMap(bType,radius , offSet);
		TMXMapReader mapReader = new TMXMapReader();
		String filename =  path+"\\"+bType.name()+".tmx";
		Map tmxMap = null;
		try
		{
			tmxMap =  mapReader.readMap(filename);
//			System.out.println(tmxMap.getFilename()+":"+tmxMap.getHeight()+":"+tmxMap.getWidth());
			radius = (tmxMap.getWidth()+1) / 2; 
			buildPlan.setRadius(radius);
			byte [][][] newCube = buildPlan.initCube(tmxMap.getWidth());
			int level = 0;
	 		for (MapLayer layer :tmxMap.getLayers())
			{
	 			
//				System.out.println(layer.getName()+":"+level+":"+layer.getHeight()+":"+layer.getWidth());
		        Rectangle bounds = layer.getBounds();
				TileLayer tl = (TileLayer) layer;
	            for (int y = 0; y < layer.getHeight(); y++) 
	            {
//					System.out.print("|");
	                for (int x = 0; x < layer.getWidth(); x++) 
	                {
	                    Tile tile = tl.getTileAt(x + bounds.x, y + bounds.y);
	                    int gid = 0;
	
	                    if (tile != null) 
	                    {
	                        gid = getGid(tile);
	                    }
//	                    System.out.print(gid+"|");
	                    newCube[level][y][x] = ConfigBasis.getMaterialId(tmxToMaterial(gid));
	//                    System.out.println("tile"+"gid: "+ gid);
	                }
//	                System.out.println("");
	            }
	            level++;
				
			}
			buildPlan.setCube(newCube);
		} catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println("TMX File not "+filename);
		}
		return buildPlan;
	}

	@Override
	public ItemPriceList getPriceList()
	{
        String base = "BASEPRICE";
        ItemPriceList items = new ItemPriceList();
        try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms", "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection(base))
            {
            	java.util.Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            	}
            	priceList = items;
            }
		} catch (Exception e)
		{
		}
		return priceList;
	}

	@Override
	public void addPrice(String itemRef, Double price)
	{
		try
		{
		
			priceList.add(itemRef, price);
	
			File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms", "baseprice.yml");
	//      if (!DataFile.exists()) 
	//      {
	//      	//DataFile.createNewFile();
	//      }
	      
			FileConfiguration config = new YamlConfiguration();
			config.load(DataFile);
			  
			String base = "BASEPRICE";
			ConfigurationSection settleSec = config.createSection(base);
			for (ItemPrice item : priceList.values())
			{
				config.set(MemorySection.createPath(settleSec, item.ItemRef()),item.getBasePrice());
			}
	  		config.save(DataFile);
			
		} catch (Exception e)
		{
			System.out.println("writePriclist");
			System.out.println(e.getStackTrace());
		}
	
	}

	@Override
	public RegimentList getRegiments()
	{
		return regiments;
	}

	@Override
	public void writeRegiment(Regiment regiment)
	{
		regData.writeRegimentData(regiment);
	}

	@Override
	public CaseBookList getCaseBooks()
	{
		return caseBooks;
	}

	@Override
	public void writeCaseBook(CaseBook caseBook)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the caseBookData
	 */
	public CaseBookList initCaseBookData()
	{
		CaseBookList caseBooks = new CaseBookList(); 
		ArrayList<String> sList = caseBookData.readDataList();
		for (String refId : sList)
		{
			CaseBook caseBook = caseBookData.readData(refId);
			caseBooks.addBook(caseBook); 
		}
		return caseBooks;
	}

	public void writeOwner(Owner owner)
	{
		String refId = String.valueOf(owner.getId());
		ownerData.writeData(owner, refId);
	}
	
	
	/**
	 * erzeugt testdaten mit 6 Owner,  3 NPC und 3 PC
	 * @return
	 */
	public void initOwnerList()
	{
		testOwners = new OwnerList();
		Owner owner ;
		
		ArrayList<String> refList = ownerData.readDataList();
		
		for (String ref : refList)
		{
			owner = ownerData.readData(ref);
			testOwners.addOwner(owner);
		}
		
		if (testOwners.getOwner(ConfigBasis.NPC_0) == null)
		{
			owner = Owner.initDefaultOwner();
			owner.initMayor();
			testOwners.addOwner(owner);
		}
		
//		testOwners.addOwner(new Owner(0, NobleLevel.COMMONER, 0, NPC_0, 1, true,""));
//		testOwners.addOwner(new Owner(1, NobleLevel.COMMONER, 0, NPC_1, 0, true,""));
//		testOwners.addOwner(new Owner(2, NobleLevel.COMMONER, 0, NPC_2, 0, true,""));
//		testOwners.addOwner(new Owner(3, NobleLevel.COMMONER, 0, PC_3, 0, false,""));
//		testOwners.addOwner(new Owner(4, NobleLevel.COMMONER, 0, PC_4, 0, false,""));
//		testOwners.addOwner(new Owner(5, NobleLevel.COMMONER, 0, PC_5, 0, false,""));
		
	}

	public void writeKingdom(Kingdom kingdom)
	{
		String refId = String.valueOf(kingdom.getId());
		kingdomData.writeData(kingdom, refId);
	}

	/**
	 * Erzeugt testdaten fuer eine Realm List mit
	 * - NPC Realm, NPC Owner , nur ein Member
	 * @return
	 */
	private void initKingdomList(OwnerList owners)
	{
		testKingdoms = new KingdomList();
		Kingdom kingdom;
		
		ArrayList<String> refList = kingdomData.readDataList();
		
		for (String ref : refList)
		{
			kingdom = kingdomData.readData(ref);
			kingdom.setOwner(owners.getOwner(kingdom.getOwnerId()));
			kingdom.initMembers(owners);
			testKingdoms.addKingdom(kingdom);
		}
		
		if (testKingdoms.getKingdom(0) == null)
		{
			testKingdoms.addKingdom(Kingdom.initDefaultKingdom(testOwners));
		}
	}

	public void writeLehen(Lehen lehen)
	{
		String refId = String.valueOf(lehen.getId());
		lehenData.writeData(lehen, refId);
	}
	
	
	public void initLehenList(OwnerList owners)
	{
		lehenList = new LehenList();
		Lehen lehen;
		ArrayList<String> refList = lehenData.readDataList();
		for (String ref : refList)
		{
			lehen = lehenData.readData(ref);
			lehen.setOwner(owners.getOwner(lehen.getOwnerId()));
			lehenList.addLehen(lehen);
		}
	}

	public void writeBuilding(Building building)
	{
		buildingData.writeData(building, String.valueOf(building.getId()));
	}
	
	public void initBuildingList()
	{
		buildings = new BuildingList();
		ArrayList<String> refList = buildingData.readDataList();
		Building building;
		for (String ref : refList)
		{
			building = buildingData.readData(ref);
			buildings.putBuilding(building);
		}
		
	}
	
	public void initSettlementList()
	{
		settlements = new SettlementList();
		ArrayList<String> refList = settleData.readDataList();
		Settlement settle;
		for (String ref : refList)
		{
			settle = settleData.readData(ref);
			settlements.putSettlement(settle);
			settle.setBuildingList(buildings.getSubList(settle.getId()));
		}
		
	}

	@Override
	public BuildingList getBuildings()
	{
		return buildings;
	}
	
}
