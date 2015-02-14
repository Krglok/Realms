package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;

/**
 * here will be the Data from the Server are transformed to RealData
 * Realm get data from server trough this interface
 * @author Windu
 *
 */
public class ServerData implements ServerInterface
{
	private static final double VERKAUF_FAKTOR = 1.25;
	private Realms plugin;
	private RecipeData recipeData;
	
	public static final int FAKTOR_0 = 0;
	public static final int FAKTOR_M = -25;
	public static final int FAKTOR_MM = -75;
	public static final int FAKTOR_MMM = -100;
	public static final int FAKTOR_P = 25;
	public static final int FAKTOR_PP = 50;
	public static final int FAKTOR_PPP = 75;
	
	public ServerData(Realms plugin)
	{
		this.plugin = plugin; 
		recipeData = new RecipeData();
	}

	public RecipeData getRecipeData()
	{
		return recipeData;
	}
	
	@Override
	public ArrayList<String> getPlayerNameList()
	{
		ArrayList<String> players = new ArrayList<String>();
		for (Player player : plugin.getServer().getOnlinePlayers())
		{
			players.add(player.getName());
		}
		return players;
	}

	@Override
	public ArrayList<String> getOffPlayerNameList()
	{
		ArrayList<String> players = new ArrayList<String>();
		for (OfflinePlayer player : plugin.getServer().getOfflinePlayers())
		{
			players.add(player.getName());
		}
		return players;
	}

	@Override
	public ArrayList<String> getItemNameList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getBuildingList()
	{
		HashMap<String, String> regionList = new HashMap<String, String>();
		for (Region region : plugin.stronghold.getRegionManager().getSortedBuildRegions())
		{
			regionList.put(String.valueOf(region.getID()), region.getType());
		}
		return regionList;
	}

	@Override
	public SuperRegion getSuperRegion(String superregionName)
	{
		return plugin.stronghold.getRegionManager().getSuperRegion(superregionName);
	}

	@Override
	public int getSuperRegionRadius(String superRegionName)
	{
		Integer radius = plugin.stronghold.getRegionManager().getSuperRegionType(superRegionName).getRadius();
		if (radius != null)
		{ 
			return radius;
		} else
		{
			return 0;
		}
	}
	
	public void createSuperRegion(World world, RegionLocation rLoc)
	{
		Location currentLocation = new Location (
				world,
				rLoc.getPosition().getX(),
				rLoc.getPosition().getY(),
				rLoc.getPosition().getZ()
				);
		String arg2 = rLoc.getRegionType();
		String arg0 = rLoc.getName();
		ArrayList<String> arg3= new ArrayList<String>();
		arg3.add(rLoc.getOwner());
		Map<String,List<String>> arg4= new HashMap<String,List<String>>();
//public boolean addSuperRegion(String name, Location loc, String type, List<String> owners, Map<String, List<String>> members, int power, double balance) {
		int arg5 = 10;
		double arg6 = 10000.0;
		if (plugin.stronghold.getRegionManager().addSuperRegion(arg0, currentLocation, arg2, arg3, arg4,arg5 , arg6))
		{
			System.out.println("create SuperRegion"+arg0+" at : "+
				 (int)currentLocation.getX()+":"+
				 (int)currentLocation.getY()+":"+
				 (int)currentLocation.getZ()
				 );
			
		} else
		{
			System.out.println("Error on Create SuperRegion "+arg0);
		}
	}
	
	public void destroySuperRegion(String superRegionName)
	{
		plugin.stronghold.getRegionManager().destroySuperRegion(superRegionName, true);
	}
	
	@Override
	public int getSuperRegionPower(String superRegionName)
	{
		if (plugin.stronghold.getRegionManager().getSuperRegion(superRegionName) == null)
		{
			return 0;
		}
		Integer power = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName).getPower();
		if (power != null)
		{ 
			return power;
		} else
		{
			return 0;
		}
	}

	@Override
	public double getSuperRegionbank(String superRegionName)
	{
		Double bank = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName).getBalance();
		if (bank != null)
		{ 
			return bank;
		} else
		{
			return 0;
		}
	}
	
	public HashMap<String, Integer> getSuperTypeRequirements(String regionType)
	{
		HashMap<String, Integer> required = new HashMap<String, Integer>();
		
		for (Entry<String, Integer> entry : plugin.stronghold.getRegionManager().getSuperRegionType(regionType).getRequirements().entrySet())
		{
			required.put(entry.getKey(), entry.getValue());
		}
		return required;
	}
	
	public double getSuperTypeCost(String regionType)
	{
		return plugin.stronghold.getRegionManager().getSuperRegionType(regionType).getMoneyRequirement();
	}

	public double getSuperTypeExp(String regionType)
	{
		return plugin.stronghold.getRegionManager().getSuperRegionType(regionType).getExp();
	}
	
	@Override
	public ItemList getRegionReagents(String regionType)
	{
		ItemList rList = new ItemList();
		if (regionType == null)
		{
			return rList;
		}

		if ( plugin.stronghold.getRegionManager().getRegionType(regionType) != null)
		{
			for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getReagents())
			{
				rList.addItem(item.getData().getItemType().name(), item.getAmount());
			}
			System.out.println(regionType+":"+rList.size());
		}
		return rList;
	}
	
	public ItemList getRegionRequirements(String regionType)
	{
		ItemList rList = new ItemList();
		if (regionType == null)
		{
			return rList;
		}

		if ( plugin.stronghold.getRegionManager().getRegionType(regionType) != null)
		{
			for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getReagents())
			{
				rList.addItem(item.getData().getItemType().name(), item.getAmount());
			}
			System.out.println(regionType+":"+rList.size());
		}
		return rList;
	}
	
	
	
	@Override
	public ItemList getRegionOutput(String regionType)
	{
		ItemList rList = new ItemList();
		if (regionType == null)
		{
			return rList;
		}
		if (plugin.stronghold.getRegionManager().getRegionType(regionType) != null)
		{
			if (plugin.stronghold.getRegionManager().getRegionType(regionType).getOutput() != null)
			{
				for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getOutput())
				{
					rList.addItem(item.getData().getItemType().name(), item.getAmount());
				}
			}
	//			System.out.println("Out: "+regionType+":"+rList.size());
		}
		return rList;
	}

	@Override
	public ItemList getRegionUpkeep(String regionType)
	{
		ItemList rList = new ItemList();
		if (regionType == null)
		{
			return rList;
		}
		if (plugin.stronghold.getRegionManager().getRegionType(regionType) != null)
		{
			for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getUpkeep())
			{
				rList.addItem(item.getData().getItemType().name(), item.getAmount());
			}
//			System.out.println("Up : "+regionType+":"+rList.size());
		}
		return rList;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
		if (regionType == null)
		{
			return 0.0;
		}

		Double money = plugin.stronghold.getRegionManager().getRegionType(regionType).getMoneyOutput();
		if (money != null)
		{ 
			return money;
		} else
		{
			return 0;
		}
	}


	/**
	 * unused !!!!
	 */
	public void setRegionChest(int id, ItemList itemList)
	{
				
	}

	public ItemList getDefaultRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		ItemStack  itemStack = new ItemStack(Material.getMaterial(itemRef));
		for (Recipe recipe :plugin.getServer().getRecipesFor(itemStack))
		{
			if (recipe instanceof ShapelessRecipe)
			{
				ShapelessRecipe sr = (ShapelessRecipe) recipe;
				for (ItemStack item : sr.getIngredientList())
				{
					itemList.putItem(item.getType().name(),item.getAmount());
				}
			}
			if (recipe instanceof ShapedRecipe)
			{
				ShapedRecipe sr = (ShapedRecipe) recipe;
				for (ItemStack item : sr.getIngredientMap().values())
				{
					if (item != null)
					{
						itemList.putItem(item.getType().name(),item.getAmount());
					}
				}
			}
		}
		return itemList;
	}
	
	public ItemList findRecipe(String itemRef)
	{
		ItemList items = new ItemList();
		items = recipeData.getRecipe(itemRef);
		if (items.size() == 0 )
		{
			items = recipeData.getWeaponRecipe(itemRef);
		}
		if (items.size() == 0 )
		{
			items = recipeData.getToolRecipe(itemRef);
		}
		if (items.size() == 0 )
		{
			items = recipeData.getFoodRecipe(itemRef);
		}
		if (items.size() == 0 )
		{
			items = getDefaultRecipe(itemRef);
		}
		return items;
	}
	
	@Override
	public ItemList getRecipe(String itemRef)
	{
		return recipeData.getRecipe(itemRef);
	}

	private int biomePlainFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_0;
		case WHEAT : return FAKTOR_PP;
		case SEEDS : return FAKTOR_PP;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}

	private int biomeMountainFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_MM;
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}

//	private int biomeHillFactor(Material mat)
//	{
//		switch (mat)
//		{
//		case WATER : return FAKTOR_M;
//		case WHEAT : return FAKTOR_M;
//		case SEEDS : return FAKTOR_M;
//		case COBBLESTONE: return FAKTOR_P;
//		case LOG: return FAKTOR_M;
//		case WOOL : return FAKTOR_0;
//		case GOLD_NUGGET: return FAKTOR_0;
//		case LEATHER : return FAKTOR_0;
//		case RAW_BEEF : return FAKTOR_0;
//		case PORK : return FAKTOR_0;
//		case RAW_CHICKEN : return FAKTOR_0;
//		case FEATHER : return FAKTOR_0;
//		case RAW_FISH : return FAKTOR_MM;
//		case EMERALD : return FAKTOR_0;
//		case RED_MUSHROOM : return FAKTOR_P; 
//		case BROWN_MUSHROOM : return FAKTOR_P; 
//		case IRON_ORE : return FAKTOR_P;
//		case COAL_ORE : return FAKTOR_P;
//		case DIAMOND_ORE : return FAKTOR_P;
//		case EMERALD_ORE : return FAKTOR_P;
//		case REDSTONE_ORE : return FAKTOR_P;
//		case GOLD_ORE : return FAKTOR_P;
//		case LAPIS_ORE : return FAKTOR_P;
//		case GHAST_TEAR : return FAKTOR_MMM;
//		case MAGMA_CREAM : return FAKTOR_MMM;
//		default :
//			return  FAKTOR_0;
//		}
//	}
	
	private int biomeSwampFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_P;
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case STRING : return FAKTOR_PPP;
		case SPIDER_EYE: return FAKTOR_PPP;
		case SPONGE: return FAKTOR_P;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_0;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PP; 
		case BROWN_MUSHROOM : return FAKTOR_PP; 
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}

	private int biomeOceanFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_PP;
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_PPP;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_MMM;
		case COAL_ORE : return FAKTOR_MMM;
		case DIAMOND_ORE : return FAKTOR_MMM;
		case EMERALD_ORE : return FAKTOR_MMM;
		case LAPIS_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_MMM;
		case GOLD_ORE : return FAKTOR_MMM;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int biomeForestFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_0;
		case LOG: return FAKTOR_PPP;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_M;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_0;
		case COAL_ORE : return FAKTOR_0;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_0;
		case LAPIS_ORE : return FAKTOR_0;
		case GOLD_ORE : return FAKTOR_0;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int biomeDesertFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_MMM;
		case SAND : return FAKTOR_PPP;
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_P;
		case GOLD_NUGGET: return FAKTOR_M;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_M;
		case EMERALD : return FAKTOR_P;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_M;
		case COAL_ORE : return FAKTOR_M;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_M;
		case LAPIS_ORE : return FAKTOR_M;
		case GOLD_ORE : return FAKTOR_M;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_MM;
		}
	}

	private int biomeExtremeFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_MM;
		case WHEAT : return FAKTOR_MMM;
		case SEEDS : return FAKTOR_MMM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_MMM;
		case EMERALD : return FAKTOR_PP;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_PP;
		case COAL_ORE : return FAKTOR_PP;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_0;
		case GHAST_TEAR : return FAKTOR_MMM;
		case MAGMA_CREAM : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}

	private int biomeHellFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_MMM;
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PPP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_MM;
		case FEATHER : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PPP; 
		case BROWN_MUSHROOM : return FAKTOR_PPP;
		case LAVA : return FAKTOR_PPP;
		case NETHERRACK : return FAKTOR_PPP;
		case SOUL_SAND : return FAKTOR_PPP;
		case GHAST_TEAR : return FAKTOR_PPP;
		case MAGMA_CREAM : return FAKTOR_PPP;
		
		default :
			return  FAKTOR_MMM;
		}
	}
	
	private int biomeJungleFactor(Material mat)
	{
		switch (mat)
		{
		case WATER : return FAKTOR_P;
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_P;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RED_MUSHROOM : return FAKTOR_PP; 
		case BROWN_MUSHROOM : return FAKTOR_PP;
		case LAVA : return FAKTOR_MM;
		case POTATO : return FAKTOR_PP;
		case COCOA : return FAKTOR_PPP;
		
		default :
			return  FAKTOR_MMM;
		}
	}
	
	@Override
	public int getBioneFactor(Biome biome, Material mat)
	{
		int factor = 0;
		if (biome == null)
		{
			return factor;
		}
		if (biome.name().contains("PLAIN"))
		{
			factor = factor + biomePlainFactor(mat);
		}
		if (biome.name().contains("SWAMP"))
		{
			factor = factor + biomeSwampFactor(mat);
		}
		if (biome.name().contains("MOUNTAIN"))
		{
			factor = factor + biomeMountainFactor(mat);
		}
		if (biome.name().contains("OCEAN"))
		{
			factor = factor + biomeOceanFactor(mat);
		}
		if (biome.name().contains("FOREST"))
		{
			factor = factor + biomeForestFactor(mat);
		}
		if (biome.name().contains("DESERT"))
		{
			factor = factor + biomeDesertFactor(mat);
		}
		if (biome.name().contains("EXTREME"))
		{
			factor = factor + biomeExtremeFactor(mat);
		}
		if (biome.name().contains("HELL"))
		{
			factor = factor + biomeHellFactor(mat);
		}
		if (biome.name().contains("JUNGLE"))
		{
			factor = factor + biomeJungleFactor(mat);
		}
		return factor;
	}

	
	@Override
	public double getRecipeFactor(String itemRef, Biome biome, int amount)
	{
		if (amount >= 8)
		{
			double prodFactor = (100.0 + (double) getBioneFactor(biome, Material.getMaterial(itemRef)))/100.0 ;
			return prodFactor;
		} else
		{
			return 1;
		}
	}

	@Override
	public ItemList getFoodRecipe(String itemRef)
	{
		return recipeData.getFoodRecipe(itemRef);
	}

	@Override
	public ItemList getRecipeProd(String itemRef, String hsRegionType)
	{

		ItemList items = new ItemList();
		if (hsRegionType == null)
		{
			return items;
		}
		items = getRegionUpkeep(hsRegionType);
		return items;
	}


	@Override
	public boolean checkRegionEnabled(int regionId)
	{
		if (plugin.stronghold.getRegionManager().getRegionByID(regionId)  != null)
		{
			return true;
		}
		return false;
	}
	
	public Double getRecipePrice(String itemRef, ItemList ingredients)
	{
		Double prodCost = 0.0;
		Double price = 0.0;
		int amount = 1;
		for (String recipeRef : ingredients.keySet())
		{
			if (!recipeRef.equals(itemRef))
			{
				price =  plugin.getData().getPriceList().getBasePrice(itemRef);
				if (price == 0.0)
				{
					price =1.0;
				}
				prodCost = prodCost + (ingredients.getValue(itemRef) * price * VERKAUF_FAKTOR);
			} else
			{
				amount = ingredients.getValue(recipeRef);
				if (amount == 0)
				{
					amount = 1;
				}
			}
		}
		prodCost = prodCost / amount;
		if (prodCost == 0.0)
		{
			//prodCost = 1.0;
		}
		return prodCost;
	}

	
	
	public ItemPriceList getProductionPrice(String itemRef)
	{
		ItemPriceList items = new ItemPriceList();
		ItemList ingredients =  recipeData.getRecipe(itemRef);
		Double prodCost = getRecipePrice(itemRef, ingredients);
		items.add(itemRef, prodCost);
		return items;
	}
	
	public Double getItemPrice(String itemRef)
	{
		return plugin.getData().getPriceList().getBasePrice(itemRef);
	}
	
	public void getBiome()
	{
//		Biome biome = plugin.getServer().getWorld("SteamHaven").getBiome(arg0, arg1);
//		Biome.
	}

	@Override
	public ArrayList<Region> getRegionInSuperRegion(String superRegionName)
	{
		ArrayList<Region> rList = new ArrayList<Region>();
		SuperRegion superReg = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(superReg))
		{
			rList.add(region);
		}

		return rList;
	}

	@Override
	public String getSuperRegionType(String superRegionName)
	{
		for (SuperRegion region : plugin.stronghold.getRegionManager().getSortedSuperRegions())
		{
			if (region.getName().equalsIgnoreCase(superRegionName))
			{
				return region.getType();
			}
		}
		return "";
	}

	@Override
	public String getRegionType(int id)
	{
		return plugin.stronghold.getRegionManager().getRegionByID(id).getType();
	}

	@Override
	public double getRegionTypeCost(String regionType)
	{
		return plugin.stronghold.getRegionManager().getRegionType(regionType).getMoneyRequirement();
	}
	
	@Override
	public Region getRegionAt(LocationData pos)
	{
		World world = plugin.getServer().getWorld(pos.getWorld());
		return plugin.stronghold.getRegionManager().getRegion(new Location (world, pos.getX(),pos.getY(),pos.getZ())); 
	}

	
}
