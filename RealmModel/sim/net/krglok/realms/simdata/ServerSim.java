package net.krglok.realms.simdata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import multitallented.redcastlemedia.bukkit.herostronghold.region.DefaultRegions;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.RecipeData;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.tool.RegionData;
import net.krglok.realms.tool.SuperRegionData;

public class ServerSim implements ServerInterface
{

	
	public ServerSim(String pluginPath)
	{
	}

	@Override
	public ArrayList<String> getPlayerNameList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getOffPlayerNameList()
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSuperRegionType(String superRegionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Region> getRegionInSuperRegion(String superRegionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuperRegion getSuperRegion(String SuperregionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroySuperRegion(String superRegionName)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSuperRegionPower(String superRegionName)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSuperRegionbank(String superRegionName)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemList getRegionOutput(String regionType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getRegionReagents(String regionType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getRegionUpkeep(String regionType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRegionType(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRegionChest(int id, ItemList itemList)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemList getRecipe(String itemRef)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBioneFactor(Biome biome, Material mat)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRecipeFactor(String itemRef, Biome biome, int amount)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemList getFoodRecipe(String itemRef)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getRecipeProd(String itemRef, String hsRegionType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkRegionEnabled(int i)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Double getRecipePrice(String itemRef, ItemList ingredients)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemPriceList getProductionPrice(String itemRef)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getItemPrice(String itemRef)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSuperRegionRadius(String superRegionName)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Region getRegionAt(LocationData pos)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRegionTypeCost(String regionType)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}