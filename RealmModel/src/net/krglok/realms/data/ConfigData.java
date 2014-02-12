package net.krglok.realms.data;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.SettleType;

/**
 * read Data from YML file 
 * used for initialize the Plugin and the RealmModel with data
 * 
 * @author Windu
 *
 */
public class ConfigData extends ConfigBasis implements ConfigInterface
{

	// RegionTypeBuildingType
	private HashMap<String,String> regionBuildingTypes;
	// SuperRegionTypeBuildingType
	private HashMap<String,String> superBuildingTypes;
	// SuperRegionTypeSettlementType
	private HashMap<String,String> superSettleTypes;

	private HashMap<String, String> buildPlanRegions;
	
	private ItemList toolItems;
	private ItemList weaponItems;
	private ItemList armorItems;
	
	private int realmCounter ;
	private int settlementCounter ;
	private int buildingCounter;
	
	protected FileConfiguration configFile;
	
	public ConfigData(Realms plugin )
	{
		regionBuildingTypes = new HashMap<String, String>();
		superBuildingTypes = new HashMap<String, String>();
		superSettleTypes = new HashMap<String, String>();
		buildPlanRegions  = new HashMap<String, String>();
		
		setRealmCounter(0);
		setSettlementCounter(0);
		
		configFile = plugin.getConfig();
		String nameValue = configFile.getString(CONFIG_PLUGIN_NAME,"");
		if (!nameValue.equalsIgnoreCase(PLUGIN_NAME))
		{
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
			nameValue = configFile.getString(CONFIG_PLUGIN_NAME,"");
		}
		plugin.getLog().info("[realms] configname : "+nameValue);
		PLUGIN_VER = configFile.getString(CONFIG_PLUGIN_VER);
		realmCounter = configFile.getInt(CONFIG_REALM_COUNTER, 0);
		settlementCounter = configFile.getInt(CONFIG_SETTLEMENT_COUNTER, 0);
		setBuildingCounter(configFile.getInt("buildingCounter", 0));
//		configFile.options().copyDefaults(true);
	
	}

	@Override
	public Boolean initConfigData()
	{
		
		initRegionBuilding();
		initSuperSettleTypes();
		initBuildPlanRegion();
		initTool();
		initArmor();
		initWeapon();
		
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
	 * erzeugt eine List von superRegiontypen mit SettlementTypen
	 */
	private void initSuperSettleTypes()
	{
		superSettleTypes.put("Mine", SettleType.SETTLE_HAMLET.name());
		superSettleTypes.put("Burg", SettleType.SETTLE_CASTLE.name());
		superSettleTypes.put("Siedlung", SettleType.SETTLE_HAMLET.name());
		superSettleTypes.put("Dorf", SettleType.SETTLE_TOWN.name());
		superSettleTypes.put("Stadt", SettleType.SETTLE_CITY.name());
		superSettleTypes.put("Metropole", SettleType.SETTLE_METRO.name());
	}

	/**
	 * erzeugt eine Liste von RegionTypen zu BuildingTypen
	 */
	private void initRegionBuilding()
	{
//		regionBuildingTypes.put("haus_gross", BuildPlanType.HOUSE.name());
		regionBuildingTypes.put("bauern_hof",BuildPlanType.FARM.name());
		regionBuildingTypes.put("bauern_haus",BuildPlanType.FARMHOUSE.name());
		regionBuildingTypes.put("colony", BuildPlanType.COLONY.name());
		regionBuildingTypes.put("fischer", BuildPlanType.FISHERHOOD.name());
		regionBuildingTypes.put("haupthaus", BuildPlanType.HALL.name());
		regionBuildingTypes.put("haus_baecker",BuildPlanType.BAKERY.name());
		regionBuildingTypes.put("haus_einfach",BuildPlanType.HOME.name());
		regionBuildingTypes.put("haus_stadt", BuildPlanType.HOUSE.name());
		regionBuildingTypes.put("holzfaeller",BuildPlanType.WOODCUTTER.name());
		regionBuildingTypes.put("huehnerstall",BuildPlanType.CHICKENHOUSE.name());
		regionBuildingTypes.put("koehler", BuildPlanType.CHARBURNER.name());
		regionBuildingTypes.put("kornfeld", BuildPlanType.WHEAT.name());
		regionBuildingTypes.put("markt", BuildPlanType.WAREHOUSE.name());
		regionBuildingTypes.put("rathaus", BuildPlanType.HALL.name());
		regionBuildingTypes.put("rinderstall",BuildPlanType.COWSHED.name());
		regionBuildingTypes.put("schaefer", BuildPlanType.SHEPHERD.name());
		regionBuildingTypes.put("schmelze",BuildPlanType.SMELTER.name());
		regionBuildingTypes.put("schreiner", BuildPlanType.CARPENTER.name());
		regionBuildingTypes.put("schweinestall",BuildPlanType.PIGPEN.name());
		regionBuildingTypes.put("shop_waxe",BuildPlanType.AXESHOP.name());
		regionBuildingTypes.put("shop_whoe",BuildPlanType.HOESHOP.name());
		regionBuildingTypes.put("shop_wpaxe",BuildPlanType.PICKAXESHOP.name());
		regionBuildingTypes.put("shop_wspade",BuildPlanType.SPADESHOP.name());
		regionBuildingTypes.put("shop_wsword",BuildPlanType.KNIFESHOP.name());
		regionBuildingTypes.put("smith",BuildPlanType.BLACKSMITH.name());
		regionBuildingTypes.put("stadtwache",BuildPlanType.GUARDHOUSE.name());
		regionBuildingTypes.put("steinbruch", BuildPlanType.QUARRY.name());
		regionBuildingTypes.put("steinmine",BuildPlanType.STONEMINE.name());
		regionBuildingTypes.put("tanner",BuildPlanType.TANNARY.name());
		regionBuildingTypes.put("taverne", BuildPlanType.TAVERNE.name());
		regionBuildingTypes.put("tischler",BuildPlanType.CABINETMAKER.name());
		regionBuildingTypes.put("tower", BuildPlanType.TOWER.name());
		regionBuildingTypes.put("watchtower",BuildPlanType.WATCHTOWER.name());
		regionBuildingTypes.put("werkstatt",BuildPlanType.WORKSHOP.name());
		regionBuildingTypes.put("ziegelei",BuildPlanType.BRICKWORK.name());
		
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

//	@Override
//	public String getRegionType(BuildPlanType bType)
//	{
//		for (String key : regionBuildingTypes.keySet())
//		{
//			if (regionBuildingTypes.get(key).equalsIgnoreCase(bType.name()))
//			{
//				return key;
//			}
//		}
//		
//		return "";
//	}
	
	public HashMap<String, String> getBuildPlanRegions()
	{
		return buildPlanRegions;
	}

	@Override
	public String getRegionType(BuildPlanType bType)
	{
		for (String key : buildPlanRegions.keySet())
		{
			if (buildPlanRegions.get(key).equalsIgnoreCase(bType.name()))
			{
				return key;
			}
		}
		
		return "";
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
	public BuildPlanType superRegionToBuildingType(String superRegionTypeName)
	{
		String name = superBuildingTypes.get(superRegionTypeName);
		return BuildPlanType.getBuildPlanType(name);
	}

	/**
	 * Wandelt einen RegionTyp in einen BuildingTyp
	 * 
	 * @param regionTypeName
	 * @return Buildingtyp or BUILDING_NONE
	 */
	@Override
	public BuildPlanType regionToBuildingType(String regionTypeName)
	{
		String name = regionBuildingTypes.get(regionTypeName);

		return BuildPlanType.getBuildPlanType(name);
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
		BuildPlanType bType;
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

//	/**
//	 * make List of BuildingTypes for the List of superRegionTypes
//	 * 
//	 * @param collection
//	 * @return List <i, SettleTypes>
//	 */
//	public HashMap<String, String> makeSuperRegionBuildingTypes(HashMap<String, String> superRegions)
//	{
//		HashMap<String, String> regionBuildings = new HashMap<String, String>();
//		BuildPlanType bType;
//		String regionType;
//		for (String regionName :superRegions.keySet())
//		{
//			regionType = superRegions.get(regionName);
//			bType = superRegionToBuildingType(regionType);
//			regionBuildings.put(regionName, bType.name());
//		}
//		return regionBuildings;
//	}

	/**
	 * @return the settlementCounter
	 */
	public int getSettlementCounter()
	{
		return settlementCounter;
	}

	/**
	 * @param settlementCounter the settlementCounter to set
	 */
	public void setSettlementCounter(int settlementCounter)
	{
		this.settlementCounter = settlementCounter;
	}

	/**
	 * @return the realmCounter
	 */
	public int getRealmCounter()
	{
		return realmCounter;
	}

	/**
	 * @param realmCounter the realmCounter to set
	 */
	public void setRealmCounter(int realmCounter)
	{
		this.realmCounter = realmCounter;
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
	 * @return the buildingCounter
	 */
	public int getBuildingCounter()
	{
		return buildingCounter;
	}

	/**
	 * @param buildingCounter the buildingCounter to set
	 */
	public void setBuildingCounter(int buildingCounter)
	{
		this.buildingCounter = buildingCounter;
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

	public void initBuildPlanRegion()
	{
//		 = new HashMap<BuildPlanType,String>();
		buildPlanRegions.put("",BuildPlanType.NONE.name());
		buildPlanRegions.put("haus_einfach",BuildPlanType.HOME.name());
		buildPlanRegions.put("haus_gross", BuildPlanType.HOUSE.name());
		buildPlanRegions.put("haupthaus", BuildPlanType.HALL.name());
		buildPlanRegions.put("haus_stadt", BuildPlanType.HOUSE.name());
		buildPlanRegions.put("haus_hof",BuildPlanType.FARMHOUSE.name());
		buildPlanRegions.put("rathaus", BuildPlanType.HALL.name());
		buildPlanRegions.put("taverne", BuildPlanType.TAVERNE.name());
		buildPlanRegions.put("markt", BuildPlanType.WAREHOUSE.name());
		buildPlanRegions.put("kornfeld", BuildPlanType.WHEAT.name());
		buildPlanRegions.put("holzfaeller",BuildPlanType.WOODCUTTER.name());
		buildPlanRegions.put("koehler", BuildPlanType.CHARBURNER.name());
		buildPlanRegions.put("steinbruch", BuildPlanType.QUARRY.name());
		buildPlanRegions.put("schaefer", BuildPlanType.SHEPHERD.name());
		buildPlanRegions.put("bauern_haus",BuildPlanType.FARM.name());
		buildPlanRegions.put("haus_baecker",BuildPlanType.BAKERY.name());
		
	}

}
