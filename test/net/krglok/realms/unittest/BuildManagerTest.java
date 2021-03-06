package net.krglok.realms.unittest;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class BuildManagerTest
{

	
	@Test
	public void testBuildManager()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
//		BuildManager bManager = new BuildManager();
//		LocationData centerPos = new LocationData("SteamHaven", 0.0, 0.0, 0.0);
		BuildPlanMap buildPlan = testData.readTMXBuildPlan(BuildPlanType.HALL, 4, -1);
//		bManager.newBuild(buildPlan, centerPos);

		ItemList matList = BuildManager.makeMaterialList(buildPlan);
		System.out.println("Bauterial for : "+buildPlan.getBuildingType().name());
		for (Item item : matList.values())
		{
			System.out.println(item.ItemRef()+":"+item.value());
		}
		System.out.println(" ");
			
	}

	private ItemList getMatListfor(BuildPlan bPlan, ItemList gesamtList)
	{
		ItemList matList = BuildManager.makeMaterialList(bPlan);
		for (Item item : matList.values())
		{
			gesamtList.depositItem(item.ItemRef(), item.value());
		}
		return gesamtList;
	}
	
	@Test
	public void testMakeMaterialList()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
		ItemList gesamtList = new ItemList();
		
		BuildPlan bPlan =  testData.readTMXBuildPlan(BuildPlanType.HALL, 4, -1);
		gesamtList = getMatListfor( bPlan, gesamtList);
		bPlan =  testData.readTMXBuildPlan(BuildPlanType.HOME, 4, -1);
		gesamtList = getMatListfor( bPlan, gesamtList);
		gesamtList = getMatListfor( bPlan, gesamtList);
		gesamtList = getMatListfor( bPlan, gesamtList);
		gesamtList = getMatListfor( bPlan, gesamtList);
		bPlan =  testData.readTMXBuildPlan(BuildPlanType.WHEAT, 4, -1);
		gesamtList = getMatListfor( bPlan, gesamtList);
		gesamtList = getMatListfor( bPlan, gesamtList);
		bPlan =  testData.readTMXBuildPlan(BuildPlanType.WOODCUTTER, 4, -1);
		gesamtList = getMatListfor( bPlan, gesamtList);
		bPlan =  testData.readTMXBuildPlan(BuildPlanType.QUARRY, 4, -1);
		gesamtList = getMatListfor( bPlan, gesamtList);
		
		System.out.println("Bauterial for ALL ");
		for (Item item : gesamtList.values())
		{
			System.out.println(item.ItemRef()+":"+item.value());
		}
		System.out.println(" ");
		
	}

}
