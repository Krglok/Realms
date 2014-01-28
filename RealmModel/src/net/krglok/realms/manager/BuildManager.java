package net.krglok.realms.manager;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.TradeStatus;

/**
 * the build manager realize the Controller and Manager of the building process
 * for the Settlement.
 * - buildup the standard hamlet
 * - buildup the standard buildings
 * - buildup the mine(s)
 * the manager can interact with the world and send commands and requests to other managers
 * the build manager receive a BuildingType and position to buildup a building
 * 
 * @author oduda
 *
 */
public class BuildManager
{
	private enum BuildStatus
	{

		NONE,
		PREBUILD,
		STARTED,
		POSTBUILD,
		DECLINE,
		WAIT;
		
	}

	
	private HashMap<String, BuildPlan> buildPlanList = new HashMap<String, BuildPlan>();
	private BuildPlan actualBuild;
	private LocationData actualPosition;
	private int level;
	private int row;
	private int col;
	private ItemList requiredItems;
	private ItemList buildStore;
	private int workerNeeded;
	private int workerInstalled;
	private ArrayList<ItemLocation> buildRequest;
	private ArrayList<Item> itemRequest;


	public BuildManager()
	{
		this.requiredItems = new ItemList();
		this.buildStore    = new ItemList();
		this.buildRequest  = new ArrayList<ItemLocation>();
		this.itemRequest   = new ArrayList<Item>();
	}
	
	private void addBuildRequest(String itemRef, int x, int y, int z)
	{
		// make block position
	}
	
	public ArrayList<ItemLocation> getBuildRequest()
	{
		return buildRequest;
	}


	public ArrayList<Item> getItemRequest()
	{
		return itemRequest;
	}
	
	public HashMap<String, BuildPlan> getBuildPlanList()
	{
		return buildPlanList;
	}

	public void setBuildPlanList(HashMap<String, BuildPlan> buildPlanList)
	{
		this.buildPlanList = buildPlanList;
	}

	public BuildPlan getActualBuild()
	{
		return actualBuild;
	}

	public void setActualBuild(BuildPlan actualBuild)
	{
		this.actualBuild = actualBuild;
	}

	public ItemList getRequiredItems()
	{
		return requiredItems;
	}

	public void setRequiredItems(ItemList requiredItems)
	{
		this.requiredItems = requiredItems;
	}



	public ItemList getBuildStore()
	{
		return buildStore;
	}



	public void setBuildStore(ItemList buildStore)
	{
		this.buildStore = buildStore;
	}



	public int getWorkerNeeded()
	{
		return workerNeeded;
	}



	public void setWorkerNeeded(int workerNeeded)
	{
		this.workerNeeded = workerNeeded;
	}



	public int getWorkerInstalled()
	{
		return workerInstalled;
	}



	public void setWorkerInstalled(int workerInstalled)
	{
		this.workerInstalled = workerInstalled;
	}






	public LocationData getActualPosition()
	{
		return actualPosition;
	}



	public void setActualPosition(LocationData actualPosition)
	{
		this.actualPosition = actualPosition;
	}
	
}
