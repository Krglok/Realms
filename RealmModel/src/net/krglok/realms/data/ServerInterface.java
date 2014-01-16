package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegionType;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;

/**
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

	public HashMap<String,String> getSuperRegionList();
	
	public int getSuperRegionPower(String superRegionName);
	
	public double getSuperRegionbank(String superRegionName);
	
	public ItemList getRegionOutput(String regionType);

	public ItemList getRegionUpkeep(String regionType);

	public double getRegionUpkeepMoney(String regionType);

	public String getRegionType(int id);
	
	public void setRegionChest(int id, ItemList itemList);

	public ItemList getRecipe(String itemRef);
	
	public int getRecipeFactor(String itemRef);

	public ItemList getFoodRecipe(String itemRef);

	public ItemList getRecipeProd(String itemRef, String hsRegionType);

	public boolean checkRegionEnabled(int i);

	public Double getRecipePrice(String itemRef, ItemList ingredients);
	
	public ItemPriceList getProductionPrice(String itemRef);

	public Double getItemPrice(String itemRef);
	
}
