package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import org.junit.Test;

public class BuildingListTest
{

	@Test
	public void testBuildingList()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building();
		bList.addBuilding(building);
		int expected = 1;
		int actual = bList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddBuildingnull()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = null;
		bList.addBuilding(building);
		int expected = 0;
		int actual = bList.size();
		assertEquals(expected, actual);
	}


//	@Test
//	public void testGetRegionList()
//	{
//		Building.initCounter(0);
//		BuildingList bList = new BuildingList();
//		Building building = new Building(BuildPlanType.HOME,"haus_einfach",true);
//		bList.addBuilding(building);
//		int expected = 1;
////		int actual = bList.getRegionArray().length;
////		actual = bList.getSuperRegionList().size();
//		assertEquals(expected, actual);
//	}


//	@Test
//	public void testInitRegionList()
//	{
//		Building.initCounter(0);
//		BuildingList bList = new BuildingList();
//		Building building = new Building(BuildPlanType.BAKERY,"irgendwas",false);
//		bList.addBuilding(building);
//		building = new Building(BuildPlanType.HOME,"haus_einfach",true);
//		bList.addBuilding(building);
//		bList.initRegionLists();
//		int expected = 1;
////		int actual = bList.getRegionArray().length;
//		assertEquals(expected, actual);
//	}
	
//	@Test
//	public void testInitSuperRegionList()
//	{
//		Building.initCounter(0);
//		BuildingList bList = new BuildingList();
//		Building building = new Building(BuildPlanType.BAKERY,"irgendwas",false);
//		bList.addBuilding(building);
//		building = new Building(BuildPlanType.HOME,"haus_einfach",true);
//		bList.addBuilding(building);
//		bList.initRegionLists();
//		int expected = 1;
//		int actual = bList.getSuperRegionArray().length;
//		assertEquals(expected, actual);
//	}

	@Test
	public void testGetBuilding()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildPlanType.HOME);
		building = new Building(BuildPlanType.HOME);
		bList.addBuilding(building);
		Building b = bList.getBuilding(2);
		int expected = 2;
		int actual = 0;
		if (b != null)
		{
			actual = b.getId();
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetRegionBuilding()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildPlanType.HOME);
		bList.addBuilding(building);
		building = new Building(BuildPlanType.HOME);
		bList.addBuilding(building);
		int expected = 2;
		int actual = bList.getBuildTypeList().get(BuildPlanType.HOME);
		if (expected != actual)
		{
			System.out.println("== Test BuildTypeList ==");
			for (BuildPlanType key : bList.getBuildTypeList().keySet())
			{
				System.out.println(key+":"+bList.getBuildTypeList().get(key));
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testCheckId()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildPlanType.HOME);
		for (int i = 0; i < 10; i++)
		{
			bList.put(String.valueOf(i),building);
		}
		
		int expected = 10;
		int actual = bList.checkId(5);
//		if (b != null)
//		{
//			actual = b.getId();
//		}
		assertEquals(expected, actual);
	}
	
}


