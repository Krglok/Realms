package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.BuildingType;

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


	@Test
	public void testGetRegionList()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		bList.addBuilding(building);
		int expected = 1;
		int actual = bList.getRegionArray().length;
//		actual = bList.getSuperRegionList().size();
		assertEquals(expected, actual);
	}


	@Test
	public void testGetSuperRegionList()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildingType.BUILDING_GOVERNMENT,"irgendwas",false);
		bList.addBuilding(building);
		int expected = 1;
		int actual = bList.getSuperRegionArray().length;
		assertEquals(expected, actual);
	}

	@Test
	public void testInitRegionList()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildingType.BUILDING_GOVERNMENT,"irgendwas",false);
		bList.addBuilding(building);
		building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		bList.addBuilding(building);
		bList.initRegionLists();
		int expected = 1;
		int actual = bList.getRegionArray().length;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testInitSuperRegionList()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildingType.BUILDING_HOME,"irgendwas",false);
		bList.addBuilding(building);
		building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		bList.addBuilding(building);
		bList.initRegionLists();
		int expected = 1;
		int actual = bList.getSuperRegionArray().length;
		assertEquals(expected, actual);
	}

	@Test
	public void testGetBuilding()
	{
		Building.initCounter(0);
		BuildingList bList = new BuildingList();
		Building building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
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
		Building building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		bList.addBuilding(building);
		building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		bList.addBuilding(building);
//		Integer a = bList.getRegionList().size();
		int index = bList.getRegionArray()[0];
		Building b = bList.getBuilding(index);
		int expected = 1;
		int actual = 0;
		if (b != null)
		{
			actual = b.getId();
		}
		assertEquals(expected, actual);
	}
	
}
