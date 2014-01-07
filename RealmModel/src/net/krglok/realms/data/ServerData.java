package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

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
	public HashMap<String, String> getSuperRegionList()
	{
		HashMap<String, String> regionList = new HashMap<String, String>();
		for (SuperRegion region : plugin.stronghold.getRegionManager().getSortedSuperRegions())
		{
			regionList.put(String.valueOf(region.getName()), region.getType());
		}
		return regionList;
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

	public ItemList getRegionChest(int id, String itemRef)
	{
		ItemList outList = new ItemList();
		// TODO Auto-generated
		
		return outList;
	}

	public void setRegionChest(int id, ItemList itemList)
	{
				
		//TODO Auto-generated
	}

	public ItemList getItemRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		MaterialData material = new MaterialData(Material.getMaterial(itemRef));
		ItemStack item = material.toItemStack(1);
		item.getItemMeta().getDisplayName();
		
		return itemList;
	}
	
	@Override
	public ItemList getRecipe(String itemRef)
	{
		return recipeData.getRecipe(itemRef);
	}

	@Override
	public int getRecipeFactor(String itemRef)
	{
		if (recipeData.getWeaponRecipe(itemRef).size() > 0)
		{
			return 1;
		}
		if (recipeData.getToolRecipe(itemRef).size() > 0)
		{
			return 16;
		}
		return 8;
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
	
	private Double getRecipePrice(String itemRef, ItemList ingredients)
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
			prodCost = 1.0;
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
	
}
