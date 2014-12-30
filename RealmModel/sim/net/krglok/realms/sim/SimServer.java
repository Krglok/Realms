package net.krglok.realms.sim;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.ServerInterface;

public class SimServer implements ServerInterface
{
	private String path;
	private DataStorage dataStorage;
	private LogList logList = new LogList(path); 
	
	public SimServer(String path)
	{
		this.path = path; 
		dataStorage = new DataStorage(path,logList);
	}

	/**
	 * @return the dataStorage
	 */
	public DataStorage getDataStorage()
	{
		return dataStorage;
	}

	/**
	 * @param dataStorage the dataStorage to set
	 */
	public void setDataStorage(DataStorage dataStorage)
	{
		this.dataStorage = dataStorage;
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
