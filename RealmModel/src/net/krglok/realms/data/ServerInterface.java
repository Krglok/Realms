package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;

/**
 * references of HeroSTronghold RegionManager
 *  private Map<Location, Region> liveRegions = new HashMap<Location, Region>();
    private Map<Integer, Region> idRegions = new HashMap<Integer, Region>();
    private ArrayList<Region> sortedRegions = new ArrayList<Region>();
    private Map<String, SuperRegion> liveSuperRegions = new HashMap<String, SuperRegion>();
    private ArrayList<SuperRegion> sortedSuperRegions = new ArrayList<SuperRegion>();
    private Map<String, RegionType> regionTypes = new HashMap<String, RegionType>();
    private Map<String, SuperRegionType> superRegionTypes = new HashMap<String, SuperRegionType>();
    private HeroStronghold plugin;
    private final FileConfiguration config;
    private FileConfiguration dataConfig;
    private final ConfigManager configManager;
    private HashMap<SuperRegion, HashSet<SuperRegion>> wars = new HashMap<SuperRegion, HashSet<SuperRegion>>();
    private HashMap<String, PermSet> permSets = new HashMap<String, PermSet>();
    private HashSet<String> possiblePermSets = new HashSet<String>();
    private ArrayList<Region> sortedBuildRegions = new ArrayList<Region>();

 * @author Windu
 *
 */
public interface ServerInterface
{

	public ArrayList<String> getPlayerNameList();

	public ArrayList<String> getOffPlayerNameList();
	
	public ArrayList<String> getItemNameList();
	
	public HashMap<String,String> getBuildingList();
	
	public String getSuperRegionType(String superRegionName);
	
	public ArrayList<Region> getRegionInSuperRegion(String superRegionName);

	public SuperRegion getSuperRegion(String SuperregionName);
	
	public void destroySuperRegion(String superRegionName);

	public int getSuperRegionPower(String superRegionName);
	
	public double getSuperRegionbank(String superRegionName);
	
	public ItemList getRegionOutput(String regionType);
	
	public ItemList getRegionReagents(String regionType);

	public ItemList getRegionUpkeep(String regionType);

	public double getRegionUpkeepMoney(String regionType);

	public String getRegionType(int id);
	
	public double getRegionTypeCost(String regionType);
	
	public void setRegionChest(int id, ItemList itemList);

	public ItemList getRecipe(String itemRef);
	
	public int getBiomeFactor(Biome biome, Material mat);

	public double getRecipeFactor(String itemRef, Biome biome, int amount);

	public ItemList getFoodRecipe(String itemRef);

	public ItemList getRecipeProd(String itemRef, String hsRegionType);

	public boolean checkRegionEnabled(int i);

	public Double getRecipePrice(String itemRef, ItemList ingredients);
	
	public ItemPriceList getProductionPrice(String itemRef);

	public Double getItemPrice(String itemRef);

	int getSuperRegionRadius(String superRegionName);
	
	public Region getRegionAt(LocationData pos);
	
	public void initRegionConfig();
	
	public void initSuperRegionConfig();

	public void initMaterialBuildPlanList();

	public ItemList getBiomeMaterial(Biome biome);

	public ItemList getBiomeNeutralMaterial(Biome biome);

}
