package net.krglok.realms.data;

import java.util.HashMap;

import org.bukkit.Material;

import net.krglok.realms.builder.BuildPlanType;
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
		regionBuildingTypes.put("tanner",BuildPlanType.TANNARY.name());
		
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
		subList.addItem(Material.WOOD_SWORD.name(),0);

		this.toolItems = subList;
	}

	public void initBuildMaterial()
	{
		ItemList subList = new ItemList();

		subList.addItem(Material.COBBLESTONE.name(),0);
		subList.addItem(Material.LOG.name(),0);
		subList.addItem(Material.WOOD.name(),0);
		subList.addItem(Material.STONE.name(),0);
		subList.addItem(Material.FENCE.name(),0);
		subList.addItem(Material.FENCE_GATE.name(),0);
		subList.addItem(Material.STONE.name(),0);
		subList.addItem(Material.BRICK.name(),0);
		subList.addItem(Material.NETHER_BRICK.name(),0);
		subList.addItem(Material.STAINED_GLASS_PANE.name(),0);
		subList.addItem(Material.WOOD_DOOR.name(),0);
		subList.addItem(Material.CHEST.name(),0);
		subList.addItem(Material.WORKBENCH.name(),0);
		subList.addItem(Material.FURNACE.name(),0);
		subList.addItem(Material.BED_BLOCK.name(),0);
		subList.addItem(Material.BED.name(),0);
		subList.addItem(Material.TORCH.name(),0);
		subList.addItem(Material.WOOL.name(),0);
		subList.addItem(Material.ANVIL.name(),0);
		subList.addItem(Material.BOOKSHELF.name(),0);
		subList.addItem(Material.WOOD_STEP.name(),0);
		subList.addItem(Material.STEP.name(),0);
		subList.addItem(Material.WALL_SIGN.name(),0);
		subList.addItem(Material.SIGN.name(),0);
		subList.addItem(Material.SIGN_POST.name(),0);
		subList.addItem(Material.DIRT.name(),0);
		subList.addItem(Material.GRASS.name(),0);
		subList.addItem(Material.WATER.name(),0);
		subList.addItem(Material.NETHERRACK.name(),0);
		subList.addItem(Material.WHEAT.name(),0);
		subList.addItem(Material.RED_MUSHROOM.name(),0);
		subList.addItem(Material.BROWN_MUSHROOM.name(),0);
//		subList.addItem(Material..name(),0);
		
		this.buildItems = subList;
	}

	public void initMaterial()
	{
		ItemList subList = new ItemList();

		subList.addItem(Material.COAL.name(),0);
		subList.addItem(Material.WOOD.name(),0);
		subList.addItem(Material.STICK.name(),0);
		subList.addItem(Material.WOOL.name(),0);
		subList.addItem(Material.COBBLESTONE.name(),0);
		subList.addItem(Material.LOG.name(),0);
		subList.addItem(Material.SEEDS.name(),0);
		subList.addItem(Material.NETHERRACK.name(),0);
//		subList.addItem(Material..name(),0);
		
		this.materialItems = subList;
	}

	public void initOre()
	{
		ItemList subList = new ItemList();

		subList.addItem(Material.COAL_ORE.name(),0);
		subList.addItem(Material.IRON_ORE.name(),0);
		subList.addItem(Material.GOLD_ORE.name(),0);
		subList.addItem(Material.DIAMOND_ORE.name(),0);
		subList.addItem(Material.REDSTONE_ORE.name(),0);
		subList.addItem(Material.EMERALD_ORE.name(),0);
		subList.addItem(Material.LAPIS_ORE.name(),0);
		subList.addItem(Material.QUARTZ_ORE.name(),0);
//		subList.addItem(Material..name(),0);
			
		this.oreItems = subList;
	}
	
	public void initValuables()
	{
		ItemList subList = new ItemList();

		subList.addItem(Material.GOLD_NUGGET.name(),0);
		subList.addItem(Material.EMERALD.name(),0);
		subList.addItem(Material.DIAMOND.name(),0);
		subList.addItem(Material.GOLD_INGOT.name(),0);
		subList.addItem(Material.IRON_INGOT.name(),0);
//		subList.addItem(Material..name(),0);
			
		this.valuableItems = subList;
	}
	
	public void initRawMaterial()
	{
		ItemList subList = new ItemList();

		subList.addItem(Material.DIRT.name(),0);
		subList.addItem(Material.STONE.name(),0);
		subList.addItem(Material.GRASS.name(),0);
		subList.addItem(Material.NETHERRACK.name(),0);
		subList.addItem(Material.LOG.name(),0);
		subList.addItem(Material.GRAVEL.name(),0);
		subList.addItem(Material.WATER.name(),0);
		subList.addItem(Material.WOOL.name(),0);
		subList.addItem(Material.CLAY.name(),0);
		subList.addItem(Material.SAND.name(),0);
		subList.addItem(Material.LAVA.name(),0);
		subList.addItem(Material.LEAVES.name(),0);
		subList.addItem(Material.LEAVES_2.name(),0);
		subList.addItem(Material.SANDSTONE.name(),0);
		subList.addItem(Material.SNOW.name(),0);
		subList.addItem(Material.ICE.name(),0);
		subList.addItem(Material.CACTUS.name(),0);
		subList.addItem(Material.SUGAR_CANE.name(),0);
		subList.addItem(Material.PUMPKIN.name(),0);
		subList.addItem(Material.SEEDS.name(),0);
		subList.addItem(Material.MELON.name(),0);
		subList.addItem(Material.VINE.name(),0);
		subList.addItem(Material.MYCEL.name(),0);
		subList.addItem(Material.HUGE_MUSHROOM_1.name(),0);
		subList.addItem(Material.HUGE_MUSHROOM_2.name(),0);
		subList.addItem(Material.MOSSY_COBBLESTONE.name(),0);
		subList.addItem(Material.SAPLING.name(),0);
//		subList.addItem(Material..name(),0);
			
		this.rawItems = subList;
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

}
