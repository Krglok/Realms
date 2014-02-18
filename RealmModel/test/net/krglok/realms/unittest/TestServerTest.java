package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.data.ServerTest;

import org.junit.Test;

public class TestServerTest
{

	@Test
	public void testGetRegionUpkeep()
	{
		
		ServerTest server = new ServerTest();
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
		
		ServerTest server = new ServerTest();
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
	
}
