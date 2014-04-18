package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.ItemList;

import org.junit.Test;

public class BuildingTest
{
	private Boolean isOutput = false; // set this to false to suppress println


	@Test
	public void testBuildingBuildingTypeStringBoolean()
	{
		Building.initCounter(0);
		BuildPlanType buildingType = BuildPlanType.HOME;
		String regionType 	= "HOME";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);
		
		int expected = 1;
		int actual 	 = building.getId();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingBuildingDefaultSettler()
	{
		BuildPlanType buildingType = BuildPlanType.HOME;
		String regionType 	= "HOME";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);
		
		int expected = 4;
		int actual 	 = building.getSettler();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingBuildingDefaultWorker()
	{
		BuildPlanType buildingType = BuildPlanType.WHEAT;
		String regionType 	= "WHEAT";
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
		BuildPlanType buildingType = BuildPlanType.WHEAT;
		String regionType 	= "WHEAT";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		outValues = building.produce(server);
		
		//isOutput = true;
		
		int expected = 16;
		int actual 	 = outValues.getItem("WHEAT").value();
		if (expected != actual)
		{
			System.out.println("==Produce == : "+outValues.size());
			for (Item item : outValues)
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildingTax()
	{
		ServerTest server = new ServerTest();
		ItemArray outValues = new ItemArray(); 
		BuildPlanType buildingType = BuildPlanType.WHEAT;
		String regionType 	= "WHEAT";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		outValues = building.produce(server);
		double sale = 0.0;
		for (Item item : outValues)
		{
			sale = building.calcSales(server,item)*item.value();
		}
		building.setSales(sale);
		Double expected = 4.8;
		Double actual 	 = building.getSales();
//		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("==testBuildingTax == : "+outValues.size());
			for (Item item : outValues)
			{
				System.out.println(item.ItemRef()+":"+item.value());
				System.out.println(item.ItemRef()+":"+building.calcSales(server, item));
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
		BuildPlanType buildingType = BuildPlanType.CARPENTER;
		String regionType 	= "CARPENTER";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, regionType, isRegion);

		ItemList matList = new ItemList();
		
		matList = server.getRegionUpkeep(regionType);
		
//		building.setConsume(server, matList, 18);  // die methode wurde gelöscht !!!
		
		outValues = building.produce(server);
		
		
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

	@Test
	public void testBuildingProduceGroup()
	{
		ServerTest server = new ServerTest();
		@SuppressWarnings("unused")
		ItemArray outValues = new ItemArray(); 
		BuildPlanType buildingType = BuildPlanType.CARPENTER;
		String hsRegionType 	= "CARPENTER";
		boolean isRegion 	= true;
		Building building = new Building(buildingType, hsRegionType, isRegion);

		ItemList matList = new ItemList();
		
		matList = server.getRegionUpkeep(hsRegionType);
		
//		building.setConsume(server, matList, 18);  // die methode wurde gelöscht !!!
		
		outValues = building.produce(server);
		

//		isOutput = true;
		if (isOutput)
		{
			System.out.println("==Matlist == : "+matList.size());
			for (Item item : matList.values())
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
			System.out.println("==Outlist == Type : "+BuildPlanType.getBuildGroup(buildingType));
			if (BuildPlanType.getBuildGroup(buildingType) == 2)
			{
				outValues.clear();
				outValues = building.buildingProd(server,hsRegionType);
			}
			for (Item item : outValues)
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
			
		}
		
		int expected = 32;
		int actual 	 = matList.getValue("LOG");
		assertEquals(expected, actual);
	}
	

}
