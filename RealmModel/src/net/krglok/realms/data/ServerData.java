package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;

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
	public SuperRegion getSuperRegion(String SuperregionName)
	{
		return plugin.stronghold.getRegionManager().getSuperRegion(SuperregionName);
	}

	@Override
	public int getSuperRegionPower(String superRegionName)
	{
		
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

	@Override
	public ItemList getRegionReagents(String regionType)
	{
		ItemList rList = new ItemList();
		if ( plugin.stronghold.getRegionManager().getRegionType(regionType) != null)
		{
			for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getReagents())
			{
				rList.addItem(item.getData().getItemType().name(), item.getAmount());
			}
		}
		return rList;
	}
	
	
	@Override
	public ItemList getRegionOutput(String regionType)
	{
		ItemList rList = new ItemList();
		for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getOutput())
		{
			rList.addItem(item.getData().getItemType().name(), item.getAmount());
		}
		return rList;
	}

	@Override
	public ItemList getRegionUpkeep(String regionType)
	{
		ItemList rList = new ItemList();
		for (ItemStack item : plugin.stronghold.getRegionManager().getRegionType(regionType).getUpkeep())
		{
			rList.addItem(item.getData().getItemType().name(), item.getAmount());
		}
		return rList;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
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

	private int plainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_PP;
		case SEEDS : return FAKTOR_PP;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_PP;
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
		default :
			return  FAKTOR_0;
		}
	}

	private int mountainFactor(Material mat)
	{
		switch (mat)
		{
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
		default :
			return  FAKTOR_0;
		}
	}

	private int hillFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_P;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_0;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_0;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int swampFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MMM;
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
		default :
			return  FAKTOR_0;
		}
	}

	private int oceanFactor(Material mat)
	{
		switch (mat)
		{
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
		default :
			return  FAKTOR_0;
		}
	}
	
	private int forestFactor(Material mat)
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
		default :
			return  FAKTOR_0;
		}
	}
	
	private int desertFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
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
		case LAPIS_ORE : return FAKTOR_0;
		case GOLD_ORE : return FAKTOR_0;
		default :
			return  FAKTOR_0;
		}
	}

	private int extremeFactor(Material mat)
	{
		switch (mat)
		{
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
		default :
			return  FAKTOR_0;
		}
	}
	
	@Override
	public int getBioneFactor(Biome biome, Material mat)
	{
		int factor = 0;
		if (biome.name().contains("PLAIN"))
		{
			factor = factor + plainFactor(mat);
		}
		if (biome.name().contains("SWAMP"))
		{
			factor = factor + swampFactor(mat);
		}
		if (biome.name().contains("MOUNTAIN"))
		{
			factor = factor + mountainFactor(mat);
		}
		if (biome.name().contains("OCEAN"))
		{
			factor = factor + oceanFactor(mat);
		}
		if (biome.name().contains("FOREST"))
		{
			factor = factor + forestFactor(mat);
		}
		if (biome.name().contains("DESERT"))
		{
			factor = factor + desertFactor(mat);
		}
		if (biome.name().contains("EXTREME"))
		{
			factor = factor + extremeFactor(mat);
		}
		return factor;
	}

	
	@Override
	public double getRecipeFactor(String itemRef, Biome biome)
	{
		double prodFactor = (100.0 + (double) getBioneFactor(biome, Material.getMaterial(itemRef)))/100.0 ;
		return prodFactor;
//		if (recipeData.getWeaponRecipe(itemRef).size() > 0)
//		{
//			return 1;
//		}
//		if (recipeData.getToolRecipe(itemRef).size() > 0)
//		{
//			return 16;
//		}
//		return 8;
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
}
