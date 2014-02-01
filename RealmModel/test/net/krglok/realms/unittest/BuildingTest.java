package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.data.ServerTest;

import org.junit.Test;

public class BuildingTest
{
	private Boolean isOutput = false; // set this to false to suppress println


	@Test
	public void testBuildingBuildingTypeStringBoolean()
	{
		Building.initCounter(0);
		BuildingType buildingType = BuildingType.BUILDING_HOME;
		String regionType 	= "haus_einfach";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);
		
		int expected = 1;
		int actual 	 = building.getId();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingBuildingDefaultSettler()
	{
		BuildingType buildingType = BuildingType.BUILDING_HOME;
		String regionType 	= "haus_einfach";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);
		
		int expected = 4;
		int actual 	 = building.getSettler();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingBuildingDefaultWorker()
	{
		BuildingType buildingType = BuildingType.BUILDING_PROD;
		String regionType 	= "kornfeld";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);
		
		int expected = 1;
		int actual 	 = building.getWorkerNeeded();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildingProduce()
	{
		ServerTest server = new ServerTest();
		ItemArray outValues = new ItemArray(); 
		BuildingType buildingType = BuildingType.BUILDING_PROD;
		String regionType 	= "kornfeld";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		outValues = building.produce(server);
		
		//isOutput = true;
		if (isOutput)
		{
			System.out.println("==Produce == : "+outValues.size());
			for (Item item : outValues)
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
		}
		
		int expected = 16;
		int actual 	 = outValues.getItem("WHEAT").value();
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingTax()
	{
		ServerTest server = new ServerTest();
		ItemArray outValues = new ItemArray(); 
		BuildingType buildingType = BuildingType.BUILDING_PROD;
		String regionType 	= "kornfeld";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		outValues = building.produce(server);
		
		Double expected = 30.0;
		Double actual 	 = building.getSales();
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("==Produce == : "+outValues.size());
			for (Item item : outValues)
			{
				System.out.println(item.ItemRef()+":"+item.value());
				System.out.println(item.ItemRef()+":"+building.calcSales(server, item.ItemRef()));
			}
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildingIntBuildingConsume()
	{
		ServerTest server = new ServerTest();
		@SuppressWarnings("unused")
		ItemArray outValues = new ItemArray(); 
		BuildingType buildingType = BuildingType.BUILDING_PROD;
		String regionType 	= "schreiner";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		ItemList matList = new ItemList();
		
		matList = server.getRegionUpkeep(regionType);
		
//		building.setConsume(server, matList, 18);  // die methode wurde gelöscht !!!
		
		outValues = building.produce(server);
		
		
		isOutput = true;
		if (isOutput)
		{
			System.out.println("==Matlist == : "+matList.size());
			for (Item item : matList.values())
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
			
		}
		
		int expected = 32;
		int actual 	 = matList.getValue("LOG");
		assertEquals(expected, actual);
	}
	

}
