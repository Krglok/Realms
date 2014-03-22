package net.krglok.realms.data;

import java.util.HashMap;

import org.bukkit.Material;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
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
	private ItemList buildItems;
	private ItemList materialItems;
	private ItemList oreItems;
	private ItemList valuableItems;
	private ItemList rawItems;
	private ItemList foodItems;
	
	
	int realmCounter ;
	int settlementCounter ;
	
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

	
	public ConfigTest()
	{
		regionBuildingTypes = new HashMap<String, String>();
		superBuildingTypes = new HashMap<String, String>();
		superSettleTypes = new HashMap<String, String>();
		realmCounter = 0;
		settlementCounter = 0;
		armorItems = ConfigBasis.initArmor();
		weaponItems = ConfigBasis.initWeapon();
		toolItems = ConfigBasis.initTool();
		buildItems  = ConfigBasis.initBuildMaterial();
		materialItems = ConfigBasis.initMaterial();
		oreItems = ConfigBasis.initOre();
		valuableItems = ConfigBasis.initValuables();
		rawItems = ConfigBasis.initRawMaterial();
		foodItems  = ConfigBasis.initFoodMaterial();
		
	}

	@Override
	public Boolean initConfigData()
	{
		realmCounter = 0;
		settlementCounter = 0;
		initRegionBuilding();
		initSuperSettleTypes();
		armorItems = ConfigBasis.initArmor();
		weaponItems = ConfigBasis.initWeapon();
		toolItems = ConfigBasis.initTool();
		buildItems  = ConfigBasis.initBuildMaterial();
		materialItems = ConfigBasis.initMaterial();
		oreItems = ConfigBasis.initOre();
		valuableItems = ConfigBasis.initValuables();
		rawItems = ConfigBasis.initRawMaterial();
		foodItems  = ConfigBasis.initFoodMaterial();
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
	
	@Override
	public ItemList getBuildMaterialItems()
	{
		return buildItems;
	}
	
	@Override
	public ItemList getMaterialItems()
	{
		return materialItems;
	}
	
	@Override
	public ItemList getOreItems()
	{
		return oreItems;
	}
	
	@Override
	public ItemList getValuables()
	{
		return valuableItems;
	}
	
	@Override
	public ItemList getRawItems()
	{
		return rawItems;
	}
	
	@Override
	public ItemList getFoodItems()
	{
		return foodItems;
	}
	
	/**
	 * erzeugt eine Liste von RegionTypen zu BuildingTypen
	 */
	public void initRegionBuilding()
	{
		regionBuildingTypes.put("haus_einfach",BuildPlanType.HOME.name());
//		regionBuildingTypes.put("haus_gross", BuildPlanType.HOUSE.name());
		regionBuildingTypes.put("haupthaus", BuildPlanType.HALL.name());
		regionBuildingTypes.put("haus_stadt", BuildPlanType.HOUSE.name());
		regionBuildingTypes.put("haus_hof",BuildPlanType.FARMHOUSE.name());
		regionBuildingTypes.put("taverne", BuildPlanType.TAVERNE.name());
		regionBuildingTypes.put("markt", BuildPlanType.WAREHOUSE.name());
		regionBuildingTypes.put("kornfeld", BuildPlanType.WHEAT.name());
		regionBuildingTypes.put("holzfaeller",BuildPlanType.WOODCUTTER.name());
		regionBuildingTypes.put("koehler", BuildPlanType.CHARBURNER.name());
		regionBuildingTypes.put("schreiner", BuildPlanType.CARPENTER.name());
		regionBuildingTypes.put("tischler",BuildPlanType.CABINETMAKER.name());
		regionBuildingTypes.put("steinbruch", BuildPlanType.QUARRY.name());
		regionBuildingTypes.put("schweinestall",BuildPlanType.PIGPEN.name());
		regionBuildingTypes.put("rinderstall",BuildPlanType.COWSHED.name());
		regionBuildingTypes.put("schaefer", BuildPlanType.SHEPHERD.name());
		regionBuildingTypes.put("bauern_haus",BuildPlanType.FARM.name());
		regionBuildingTypes.put("werkstatt_haus",BuildPlanType.WORKSHOP.name());
		regionBuildingTypes.put("schmelze",BuildPlanType.SMELTER.name());
		regionBuildingTypes.put("haus_baecker",BuildPlanType.BAKERY.name());
		regionBuildingTypes.put("shop_axe",BuildPlanType.AXESHOP.name());
		regionBuildingTypes.put("shop_hoe",BuildPlanType.HOESHOP.name());
		regionBuildingTypes.put("shop_pickaxe",BuildPlanType.PICKAXESHOP.name());
		regionBuildingTypes.put("shop_knife",BuildPlanType.KNIFESHOP.name());
		regionBuildingTypes.put("shop_spade",BuildPlanType.SPADESHOP.name());
		regionBuildingTypes.put("steinmine",BuildPlanType.STONEMINE.name());
		regionBuildingTypes.put("tower", BuildPlanType.TOWER.name());
		regionBuildingTypes.put("watchtower",BuildPlanType.WATCHTOWER.name());
		regionBuildingTypes.put("stadtwache",BuildPlanType.GUARDHOUSE.name());
		regionBuildingTypes.put("smith",BuildPlanType.BLACKSMITH.name());
		regionBuildingTypes.put("tanner",BuildPlanType.TANNERY.name());
		
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
		superSettleTypes.put("Mine", SettleType.HAMLET.name());
		superSettleTypes.put("Burg", SettleType.CASTLE.name());
		superSettleTypes.put("Siedlung", SettleType.HAMLET.name());
		superSettleTypes.put("Dorf", SettleType.TOWN.name());
		superSettleTypes.put("Stadt", SettleType.CITY.name());
		superSettleTypes.put("Metropole", SettleType.METROPOLIS.name());
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

//	/**
//	 * Wandelt einen superRegionTyp in einen BuildingTyp
//	 * 
//	 * @param superRegionTypeName
//	 * @return Buildingtyp or BUILDING_NONE
//	 */
//	public BuildPlanType superRegionToBuildingType(String superRegionTypeName)
//	{
//		String name = superBuildingTypes.get(superRegionTypeName);
//		return BuildPlanType.getBuildPlanType(name);
//	}

	/**
	 * Wandelt einen RegionTyp in einen BuildingTyp
	 * 
	 * @param regionTypeName
	 * @return Buildingtyp or BUILDING_NONE
	 */
	public BuildPlanType regionToBuildingType(String regionTypeName)
	{
		if (BuildPlanType.getBuildPlanType(regionTypeName) == BuildPlanType.NONE)
		{
			String name = regionBuildingTypes.get(regionTypeName);
			return BuildPlanType.getBuildPlanType(name);
		} else
		{
			return BuildPlanType.getBuildPlanType(regionTypeName);
		}
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
//			bType = superRegionToSettleType(regionType);
			regionBuildings.put(regionName, regionType);
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
		BuildPlanType bType;
		String regionType;
		for (String regionName :superRegions.keySet())
		{
			regionType = superRegions.get(regionName);
			bType = superRegionToBuildingType(regionType);
			regionBuildings.put(regionName, bType.name());
		}
		return regionBuildings;
	}


	
	@Override
	public String getRegionType(BuildPlanType bType)
	{
		return bType.name();
	}

	@Override
	public BuildPlanType superRegionToBuildingType(String superRegionTypeName)
	{
		return BuildPlanType.getBuildPlanType(superRegionTypeName);
	}

	@Override
	public boolean isUpdateCheck()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAutoUpdate()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
