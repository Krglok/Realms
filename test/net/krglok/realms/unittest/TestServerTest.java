package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.data.DataStorage;

import org.junit.Test;

public class TestServerTest
{
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);

	@Test
	public void testGetRegionUpkeep()
	{
		
		ItemList rList = new ItemList();
		String regionType = BuildPlanType.AXESHOP.name();
		
		rList = server.getRegionUpkeep(regionType); 
		
		System.out.println(" ");
		System.out.println("== UPKEEP :");
		for (Item item : rList.values())
		{
			System.out.println(item.ItemRef()+":"+item.value());
		}
		int expected = 138;
		int actual = rList.size(); 

		assertEquals(expected, actual);
	}

	@Test
	public void testGetRegionOutput()
	{
		
		ItemList rList = new ItemList();
		String regionType = BuildPlanType.AXESHOP.name();
		
		rList = server.getRegionOutput(regionType); 
		
		System.out.println(" ");
		System.out.println("== OUTLIST :");
		for (Item item : rList.values())
		{
			System.out.println(item.ItemRef()+":"+item.value());
		}
		int expected = 138;
		int actual = rList.size(); 

		assertEquals(expected, actual);
	}

	@Test
	public void testGetRegionConfigList()
	{
		
		System.out.println(" ");
		System.out.println("== REGION CONFIG :"+server.regionConfigList.size());
		for (BuildPlanType key : server.regionConfigList.keySet())
		{
			System.out.println(key.name());
		}
		int expected = 138;
		int actual = server.regionConfigList.size(); 

		assertEquals(expected, actual);
	}

	@Test
	public void testGetSuperRegionConfigList()
	{
		
		System.out.println(" ");
		System.out.println("== SUPERREGION CONFIG :"+server.superRegionConfigList.size());
		for (SettleType key : server.superRegionConfigList.keySet())
		{
			System.out.println(key.name());
		}
		int expected = 138;
		int actual = server.superRegionConfigList.size(); 

		assertEquals(expected, actual);
	}

	
	public ArrayList<String> sortItems()
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (String s : server.materialBuildPlanList.keySet())
		{
			sortedItems.add(s);
		}
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  String.CASE_INSENSITIVE_ORDER);
		}
		return sortedItems;
	}
	
	@Test
	public void testMatrialBuildPlanList()
	{
		
		System.out.println(" ");
		System.out.println("== MATERIAL BUILDPLAN :"+server.materialBuildPlanList.size());
		for (String key : sortItems())
		{
			for (BuildPlanType bType : server.materialBuildPlanList.get(key))
			System.out.println(key+":"+bType.name());
		}
		int expected = 138;
		int actual = server.superRegionConfigList.size(); 

		assertEquals(expected, actual);
	}
	
	
}
