package net.krglok.realms.data;

import java.util.Collection;
import java.util.HashMap;

import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.SettleType;

public class ConfigTest implements ConfigInterface
{
	private static final String PLUGIN_NAME = "Realms";
	private static final String PLUGIN_VER = "0.1.0";

	// RegionTypeBuildingType
	private HashMap<String, String> regionBuildingTypes;
	// SuperRegionTypeBuildingType
	private HashMap<String, String> superBuildingTypes;
	// SuperRegionTypeSettlementType
	private HashMap<String, String> superSettleTypes;

	private ItemList toolItems;
	private ItemList weaponItems;
	private ItemList armorItems;
	
	
	int realmCounter ;
	public int getRealmCounter()
	{
		return realmCounter;
	}

	public void setRealmCounter(int realmCounter)
	{
		this.realmCounter = realmCounter;
		//write to ConfigFile !
	}

	public int getSettlementCounter()
	{
		return settlementCounter;
	}

	public void setSettlementCounter(int settlementCounter)
	{
		this.settlementCounter = settlementCounter;
	}

	int settlementCounter ;
	
	public ConfigTest()
	{
		regionBuildingTypes = new HashMap<String, String>();
		superBuildingTypes = new HashMap<String, String>();
		superSettleTypes = new HashMap<String, String>();
		realmCounter = 0;
		settlementCounter = 0;
		initArmor();
		initWeapon();
		initTool();
		
	}

	@Override
	public Boolean initConfigData()
	{
		realmCounter = 0;
		settlementCounter = 0;
		initRegionBuilding();
		initSuperSettleTypes();
		initArmor();
		initWeapon();
		initTool();
		return true;
	}

	@Override
	public String getVersion()
	{
		return PLUGIN_VER;
	}

	@Override
	public String getPluginName()
	{
		return PLUGIN_NAME;
	}

	@Override
	public ItemList getToolItems()
	{
		return toolItems;
	}

	@Override
	public ItemList getWeaponItems()
	{
		return weaponItems;
	}

	@Override
	public ItemList getArmorItems()
	{
		return armorItems;
	}
	
	/**
	 * erzeugt eine Liste von RegionTypen zu BuildingTypen
	 */
	public void initRegionBuilding()
	{
		regionBuildingTypes.put("haus_einfach",BuildingType.BUILDING_HOME.name());
		regionBuildingTypes.put("haus_gross", BuildingType.BUILDING_HOME.name());
		regionBuildingTypes.put("haupthaus", BuildingType.BUILDING_HALL.name());
		regionBuildingTypes.put("haus_stadt", BuildingType.BUILDING_HOME.name());
		regionBuildingTypes.put("rathaus", BuildingType.BUILDING_HALL.name());
		regionBuildingTypes.put("taverne", BuildingType.BUILDING_ENTERTAIN.name());
		regionBuildingTypes.put("markt", BuildingType.BUILDING_WAREHOUSE.name());
		regionBuildingTypes.put("kornfeld", BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("holzfaeller",BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("koehler", BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("prod_stick", BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("steinbruch", BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("schweinemast",BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("rindermast",BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("schaefer", BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("tower", BuildingType.BUILDING_MILITARY.name());
		regionBuildingTypes.put("waffenkammer",BuildingType.BUILDING_MILITARY.name());
		regionBuildingTypes.put("stadtwache",BuildingType.BUILDING_MILITARY.name());
		regionBuildingTypes.put("bauern_haus",BuildingType.BUILDING_BAUERNHOF.name());
		regionBuildingTypes.put("werkstatt_haus",BuildingType.BUILDING_WERKSTATT.name());
		regionBuildingTypes.put("schmelze",BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("koehler",BuildingType.BUILDING_PROD.name());
		regionBuildingTypes.put("haus_baecker",BuildingType.BUILDING_BAECKER.name());
		
	}

//	public void initSuperBuilding()
//	{
//		superBuildingTypes.put("Bauernhof", BuildingType.BUILDING_HOME.name());
//		superBuildingTypes.put("Werkstatt", BuildingType.BUILDING_HOME.name());
//
//	}

	/**
	 * erzeugt eine List von superRegiontypen mit SettlementTypen
	 */
	public void initSuperSettleTypes()
	{
		superSettleTypes.put("Mine", SettleType.SETTLE_HAMLET.name());
		superSettleTypes.put("Burg", SettleType.SETTLE_CASTLE.name());
		superSettleTypes.put("Siedlung", SettleType.SETTLE_HAMLET.name());
		superSettleTypes.put("Dorf", SettleType.SETTLE_TOWN.name());
		superSettleTypes.put("Stadt", SettleType.SETTLE_CITY.name());
		superSettleTypes.put("Metropole", SettleType.SETTLE_METRO.name());
	}

	/**
	 * 
	 * @return list of <superRegionTypeName,SettleTypName>
	 */
	public HashMap<String, String> getSuperSettleTypes()
	{
		return superSettleTypes;
	}

	/**
	 * Set list to internal strongholdAreas
	 * 
	 * @param strongholdAreas
	 *            <superRegionTypeName,SettleTypName>
	 */
	public void setSuperSettleTypes(HashMap<String, String> strongholdAreas)
	{
		this.superSettleTypes = strongholdAreas;
	}

	/**
	 * 
	 * @return <superRegionTypeName,buildingTypeName>
	 */
	public HashMap<String, String> getSuperBuildingTypes()
	{
		return superBuildingTypes;
	}

	/**
	 * 
	 * @param superBuildingTypes
	 */
	public void setSuperBuildingTypes(HashMap<String, String> superBuildingTypes)
	{
		this.superBuildingTypes = superBuildingTypes;
	}

	/**
	 * 
	 * @return List of <regionTypeName,buildingTypName>
	 */
	public HashMap<String, String> getRegionBuildingTypes()
	{
		return regionBuildingTypes;
	}

	/**
	 * set the list to strongholdTypes
	 * 
	 * @param regionBuildings
	 *            <regionTypeName,buildingTypName>
	 */
	public void setRegionBuildingTypes(HashMap<String, String> regionBuildings)
	{
		regionBuildingTypes = regionBuildings;
	}

	/**
	 * Wandelt einen superRegionTyp in einen BuildingTyp
	 * 
	 * @param superRegionTypeName
	 * @return Buildingtyp or BUILDING_NONE
	 */
	public BuildingType superRegionToBuildingType(String superRegionTypeName)
	{
		String name = superBuildingTypes.get(superRegionTypeName);
		return BuildingType.getBuildingType(name);
	}

	/**
	 * Wandelt einen RegionTyp in einen BuildingTyp
	 * 
	 * @param regionTypeName
	 * @return Buildingtyp or BUILDING_NONE
	 */
	public BuildingType regionToBuildingType(String regionTypeName)
	{
		String name = regionBuildingTypes.get(regionTypeName);

		return BuildingType.getBuildingType(name);
	}

	/**
	 * Wandelt einen superRegionType in einen SettlementTyp based on
	 * superSettleTypes
	 * 
	 * @param ref
	 * @return
	 */
	public SettleType superRegionToSettleType(String superRegionTypeName)
	{
		String name = superSettleTypes.get(superRegionTypeName);
		return SettleType.getSettleType(name);
	}
	
	/**
	 * make List of buildingTypes for the List of regionTypes
	 * 
	 * @param collection <regionTypeName>
	 * @return List of <i,BuildingType>
	 */
	public HashMap<String, String> makeRegionBuildingTypes(HashMap<String, String> regions)
	{
		HashMap<String, String> regionBuildings = new HashMap<String, String>();
		BuildingType bType;
		String regionType;
		for (String regionName :regions.keySet())
		{
			regionType = regions.get(regionName);
			bType = regionToBuildingType(regionType);
			regionBuildings.put(regionName, bType.name());
		}
		return regionBuildings;
	}

	/**
	 * make List of settleTypes for the List of superRegionTypes
	 * 
	 * @param collection
	 * @return List <i, SettleTypes>
	 */
	public HashMap<String, String> makeSuperRegionSettleTypes(HashMap<String, String> superRegions)
	{
		HashMap<String, String> regionBuildings = new HashMap<String, String>();
		SettleType bType;
		String regionType;
		for (String regionName :superRegions.keySet())
		{
			regionType = superRegions.get(regionName);
			bType = superRegionToSettleType(regionType);
			regionBuildings.put(regionName, bType.name());
		}
		return regionBuildings;
	}

	/**
	 * make List of BuildingTypes for the List of superRegionTypes
	 * 
	 * @param collection
	 * @return List <i, SettleTypes>
	 */
	public HashMap<String, String> makeSuperRegionBuildingTypes(HashMap<String, String> superRegions)
	{
		HashMap<String, String> regionBuildings = new HashMap<String, String>();
		BuildingType bType;
		String regionType;
		for (String regionName :superRegions.keySet())
		{
			regionType = superRegions.get(regionName);
			bType = superRegionToBuildingType(regionType);
			regionBuildings.put(regionName, bType.name());
		}
		return regionBuildings;
	}

	/**
	 * 
	 * @return default weapon items
	 */
	public void initWeapon()
	{
		ItemList subList = new ItemList();

		subList.addItem("BOW",0);
		subList.addItem("DIAMOND_SWORD",0);
		subList.addItem("GOLD_SWORD",0);
		subList.addItem("IRON_SWORD",0);
		subList.addItem("STONE_SWORD",0);
		subList.addItem("WOOD_SWORD",0);
		
		this.weaponItems = subList;
	}
	
	/**
	 * 
	 * @return default armor items
	 */
	public void initArmor()
	{
		ItemList subList = new ItemList();
		
		subList.addItem("LEATHER_BOOTS",0);
		subList.addItem("LEATHER_CHESTPLATE",0);
		subList.addItem("LEATHER_HELMET",0);
		subList.addItem("LEATHER_LEGGINGS",0);

		subList.addItem("DIAMOND_BOOTS",0);
		subList.addItem("DIAMOND_CHESTPLATE",0);
		subList.addItem("DIAMOND_HELMET",0);
		subList.addItem("DIAMOND_LEGGINGS",0);
		
		subList.addItem("GOLD_BOOTS",0);
		subList.addItem("GOLD_CHESTPLATE",0);
		subList.addItem("GOLD_HELMET",0);
		subList.addItem("GOLD_LEGGINGS",0);
		
		subList.addItem("IRON_BOOTS",0);
		subList.addItem("IRON_CHESTPLATE",0);
		subList.addItem("IRON_HELMET",0);
		subList.addItem("IRON_LEGGINGS",0);

		subList.addItem("CHAINMAIL_BOOTS",0);
		subList.addItem("CHAINMAIL_CHESTPLATE",0);
		subList.addItem("CHAINMAIL_HELMET",0);
		subList.addItem("CHAINMAIL_LEGGINGS",0);
		
		this.armorItems = subList;
	}
	
	/**
	 * 
	 * @return default tool items
	 */
	public void  initTool()
	{
		ItemList subList = new ItemList();

		subList.addItem("FISHING_ROD",0);
		subList.addItem("FLINT_AND_STEEL",0);
		subList.addItem("SHEARS",0);
		subList.addItem("ARROW",0);
		
		subList.addItem("DIAMOND_AXE",0);
		subList.addItem("DIAMOND_HOE",0);
		subList.addItem("DIAMOND_PICKAXE",0);
		subList.addItem("DIAMOND_SPADE",0);

		subList.addItem("GOLD_AXE",0);
		subList.addItem("GOLD_HOE",0);
		subList.addItem("GOLD_PICKAXE",0);
		subList.addItem("GOLD_SPADE",0);

		subList.addItem("IRON_AXE",0);
		subList.addItem("IRON_HOE",0);
		subList.addItem("IRON_PICKAXE",0);
		subList.addItem("IRON_SPADE",0);

		subList.addItem("STONE_AXE",0);
		subList.addItem("STONE_HOE",0);
		subList.addItem("STONE_PICKAXE",0);
		subList.addItem("STONE_SPADE",0);

		subList.addItem("WOOD_AXE",0);
		subList.addItem("WOOD_HOE",0);
		subList.addItem("WOOD_PICKAXE",0);
		subList.addItem("WOOD_SPADE",0);

		this.toolItems = subList;
	}

}
