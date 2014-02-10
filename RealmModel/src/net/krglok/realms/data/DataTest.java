package net.krglok.realms.data;

import java.util.HashMap;

import org.bukkit.block.Biome;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
//import net.krglok.realms.core.Position;
import net.krglok.realms.core.Kingdom;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Warehouse;

/**
 * Simuliert die eingelesenen des Realms
 * im der live umgebung werden die daten aus einer Datenbank (YML) gelesen
 * @author Windu
 *
 */
public class DataTest implements DataInterface
{
	private static final String NPC_0 = "NPC0";
	private static final String NPC_1 = "NPC1";
	private static final String NPC_2 = "NPC2";
//	private static final String NPC_4 = "NPC4";
	private static final String PC_3 = "NPC3";
	private static final String PC_4 = "NPC4";
	private static final String PC_5 = "NPC5";
	@SuppressWarnings("unused")
	private static final String Realm_1_NPC = "Realm 1 NPC";
	
	private OwnerList testOwners ;
	private KingdomList testRealms ;
	private SettlementList testSettlements;
	private BuildingList testBuildings; 
	
	public DataTest()
	{
		initTestData();
	}

	public void initTestData()
	{
		initOwnerList();
		initRealmList();
		initBuildingList();
		initSettlementList ();
		
	}
	
	/**
	 * erzeugt testdaten mit 6 Owner,  3 NPC und 3 PC
	 * @return
	 */
	private void initOwnerList()
	{
		testOwners = new OwnerList();
		testOwners.addOwner(new Owner(0, MemberLevel.MEMBER_NONE, 0, NPC_0, 1, true));
		testOwners.addOwner(new Owner(1, MemberLevel.MEMBER_NONE, 0, NPC_1, 0, true));
		testOwners.addOwner(new Owner(2, MemberLevel.MEMBER_NONE, 0, NPC_2, 0, true));
		testOwners.addOwner(new Owner(3, MemberLevel.MEMBER_NONE, 0, PC_3, 0, false));
		testOwners.addOwner(new Owner(4, MemberLevel.MEMBER_NONE, 0, PC_4, 0, false));
		testOwners.addOwner(new Owner(5, MemberLevel.MEMBER_NONE, 0, PC_5, 0, false));
		
	}
	

	/**
	 * Erzeugt testdaten fuer eine Realm List mit
	 * - NPC Realm, NPC Owner , nur ein Member
	 * @return
	 */
	private void initRealmList()
	{
		testRealms = new KingdomList();
		Owner owner ;
		if (testOwners == null)
		{
			owner = new Owner(6, MemberLevel.MEMBER_NONE, 0, "NPC4", 0, true);
		} else
		{
			owner = testOwners.getOwner(NPC_0);
		}
		testRealms.addKingdom(new Kingdom(1, "Realm 1 NPC", owner, new MemberList(), true));
	}
	
	/**
	 * erzeugt testdaten fuer eine SettlementList mit 
	 * - 1 Settlement 
	 * - buildingList fuer Settlement   
	 */
	private void initSettlementList ()
	{
		LocationData position = new LocationData("",0.0,0.0,0.0);
		testSettlements = new SettlementList(1);
		testSettlements.addSettlement(createSettlement(1, position));
	}

	private Settlement createSettlement(int id, LocationData position)
	{
		
//		Position position = position; //new Position(0.0, 0.0, 0.0);
		Owner owner = testOwners.getOwner(NPC_0);
		Barrack barrack = new Barrack(5);
		Warehouse warehouse = new Warehouse(6912);
		BuildingList buildingList = new BuildingList(); 
		Townhall townhall = new Townhall(true);
		Bank bank = new Bank();
		Resident resident = new Resident();
		resident.setSettlerCount(13);
		Settlement settle =  new Settlement(
				id, 
				SettleType.SETTLE_HAMLET, 
				"TestSiedlung", 
				position, 
				owner.getPlayerName(),
				true, 
				barrack, 
				warehouse,
				buildingList, 
				townhall, 
				bank,
				resident,
				"",
				Biome.PLAINS
				);
		
		for (Building b : testBuildings.getBuildingList().values())
		{
			Settlement.addBuilding(b, settle);
		}
		
		return settle;
	}
	
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
						true, 
						regionId,
						"haus_einfach",
						"", 
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
						true, 
						regionId, 
						"kornfeld",
						"", 
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
						true, 
						regionId, 
						"holzfaeller",
						"", 
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
						true, 
						regionId, 
						"haupthaus",
						"", 
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
						true, 
						regionId, 
						"taverne",
						"", 
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
						true, 
						regionId, 
						"stadtwache",
						"", 
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
						true, 
						regionId, 
						"markt",
						"", 
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
						true, 
						regionId, 
						"bauern_haus",
						"", 
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
						true, 
						regionId, 
						"werkstatt_haus",
						"", 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5,
						0.0,
						new LocationData("SteamHaven", 0.0, 0.0, 0.0)
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
						true, 
						regionId, 
						"haus_baecker",
						"", 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5,
						0.0,
						new LocationData("SteamHaven", 0.0, 0.0, 0.0)
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
						true, 
						regionId, 
						"handelshaus",
						"", 
						true
						);
		
	}
	
	
	private void initBuildingList()
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
		Settlement plot = new Settlement();
		
		plot.setOwner(testOwners.getOwner(NPC_0).getPlayerName());
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
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}
	
	public HashMap<String,String> defaultSuperregionList()
	{
		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("NewHaven","Siedlung");
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}

	@Override
	public OwnerList initOwners()
	{
	
		return testOwners;
	}

	public KingdomList initRealms()
	{
		return testRealms;
		
	}

	@Override
	public SettlementList initSettlements()
	{
		return testSettlements;
	}

	public OwnerList getTestOwners()
	{
		return testOwners;
	}

	public KingdomList getTestRealms()
	{
		return testRealms;
	}

	public SettlementList getTestSettlements()
	{
		return testSettlements;
	}

	@Override
	public KingdomList initKingdoms()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeSettlement(Settlement settle)
	{
		// TODO Auto-generated method stub
		
	}
	
}
