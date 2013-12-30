package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.core.ItemList;

/**
 * here will be the Data from the Server are transformed to RealData
 * Realm get data from server trough this interface
 * @author Windu
 *
 */
public class ServerData implements ServerInterface
{
	
	public ServerData()
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
	public HashMap<String, String> getSuperRegionList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getSuperRegionPower(String superRegionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getSuperRegionbank(String superRegionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getRegionOutput(String regionType)
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
	public HashMap<String, String> getRegionUpkeepMoney(String regionType)
	{
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public ItemList getRecipe(String itemRef)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecipeFactor(String itemRef)
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
	public boolean checkRegionEnabled(String regionId)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
