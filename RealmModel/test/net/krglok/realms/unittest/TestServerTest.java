package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
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
		for (String key : server.regionConfigList.keySet())
		{
			System.out.println(key);
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
		for (String key : server.superRegionConfigList.keySet())
		{
			System.out.println(key);
		}
		int expected = 138;
		int actual = server.superRegionConfigList.size(); 

		assertEquals(expected, actual);
	}
	
}
