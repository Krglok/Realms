package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Position;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettlementTest
{

	private Boolean isOutput = false; // set this to false to suppress println

	
	
	@Test
	public void testInitID()
	{
		Settlement.initCounter(3);
		int expected = 3;
		int actual = Settlement.getCounter();
		assertEquals( expected, actual);
		
	}
	
	@Test
	public void testSettlement()
	{
		Settlement settlement = new Settlement();
		String expected = new Owner().getPlayerName();
		String actual = settlement.getOwner();
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementOwner()
	{
		Owner owner = new Owner();
		owner.setId(5);
		String expected =  "";
		Settlement settlement = new Settlement(owner.getPlayerName());
		String actual = settlement.getOwner();
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementPara()
	{
		Owner owner = new Owner();
		owner.setId(5);
		
		int id = 99;
		String name 		= "Test Para Hamlet";
		Boolean isCapital 	= true;
		SettleType settletype = SettleType.SETTLE_HAMLET;
		Barrack barrack 	= new Barrack(10);
		Warehouse warehouse = new Warehouse(1000);
		BuildingList buildingList = new BuildingList();
		Townhall townhall 	= new Townhall();
		Bank bank 			= new Bank();
		Resident resident	= new Resident();
		Position position  = new Position(); 
		
		Settlement settlement = new Settlement(
				id, settletype, name, position,  
				owner.getPlayerName(), isCapital, barrack, warehouse,
				buildingList, townhall, bank,
				resident);
		int expected =  99;
		int actual = settlement.getId();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSettlementCreate()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();

		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = testData.defaultRegionList();
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(), regionTypes, regionBuildings);

		int expected = 6;
		int actual = settle.getBuildingList().size(); 


		//isOutput = true;
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion());
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementItemMax()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();

		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();


		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(), regionTypes, regionBuildings);

		int expected = 22464;
		int actual = settle.getWarehouse().getItemMax(); 

		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Storage =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion());
			}
			System.out.println("Storage : "+settle.getWarehouse().getItemMax());
			
		}
		assertEquals(expected, actual);
	}


	@Test
	public void testSettlementProduce()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 64);
		
		settle.doProduce(server);
		
		int expected = 32;
		int actual = settle.getWarehouse().getItemList().getValue("WHEAT"); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Produce =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}
	

	
	@Test
	public void testSettlementSettlerBreed()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("21","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("30","haus_einfach");
		regionTypes.put("31","haus_einfach");
		regionTypes.put("32","haus_einfach");
		regionTypes.put("26","haus_einfach");
		regionTypes.put("27","haus_einfach");
		regionTypes.put("40","haus_einfach");
		regionTypes.put("41","haus_einfach");
		regionTypes.put("42","haus_einfach");
		regionTypes.put("46","haus_einfach");
		regionTypes.put("47","haus_einfach");
		regionTypes.put("50","haus_einfach");
		regionTypes.put("51","haus_einfach");
		regionTypes.put("52","haus_einfach");
		regionTypes.put("56","haus_einfach");
		regionTypes.put("57","haus_einfach");
		regionTypes.put("60","taverne");
		regionTypes.put("61","taverne");
		regionTypes.put("62","taverne");
		regionTypes.put("65","kornfeld");
		regionTypes.put("66","kornfeld");
		regionTypes.put("67","kornfeld");
		regionTypes.put("68","kornfeld");
		regionTypes.put("69","markt");
		regionTypes.put("31","bauern_haus");
//		regionTypes.put("70","haus_einfach");
//		regionTypes.put("71","haus_einfach");
//		regionTypes.put("72","haus_einfach");
//		regionTypes.put("73","haus_einfach");
//		regionTypes.put("75","haus_einfach");
//		regionTypes.put("76","haus_einfach");
//		regionTypes.put("77","haus_einfach");
//		regionTypes.put("78","haus_einfach");
//		regionTypes.put("79","haus_einfach");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 1512);
		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 1512);
		
		settle.getResident().setSettlerCount(30);
		settle.setSettlerMax();

//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();

		
		isOutput =   false; //(expected != actual);
		int dayCounter = 0;
		if (isOutput)
		{
		  System.out.println("==Settlement Breed  : "+settle.getResident().getSettlerMax());
		}
		for (int i = 0; i < 198; i++)
		{
			dayCounter++;
			settle.doProduce(server);
			settle.setHappiness();
			if (dayCounter == 30)
			{
				settle.getTaxe(server);
				dayCounter = 0;
			}
			
			if (isOutput)
			{
			System.out.println(i+" Settler Set : "+settle.getResident().getSettlerCount()+
					" B: "+settle.getResident().getBirthrate()+
					" D: "+settle.getResident().getDeathrate()+
					" H: "+settle.getResident().getHappiness()
					);
			}
		}
		settle.getResident().setSettlerCount(100);
		if (isOutput)
		{
			System.out.println("=Settler Set : "+settle.getResident().getSettlerCount());
		}
		for (int i = 0; i < 100; i++)
		{
			settle.doProduce(server);
			settle.setHappiness();
			settle.getTaxe(server);
			if (isOutput)
			{
			System.out.println(i+" Settlers : "+settle.getResident().getSettlerCount()+
					" B: "+settle.getResident().getBirthrate()+
					" D: "+settle.getResident().getDeathrate()+
					" H: "+settle.getResident().getHappiness()
					);
			}
		}
		settle.getResident().setSettlerCount(30);
		if (isOutput)
		{
			System.out.println("=Settler Set : "+settle.getResident().getSettlerCount());
		}
		for (int i = 0; i < 300; i++)
		{
			settle.doProduce(server);
			settle.setHappiness();
			settle.getTaxe(server);
			if (isOutput)
			{
			System.out.println(i+" Settlers : "+settle.getResident().getSettlerCount()+
					" B: "+settle.getResident().getBirthrate()+
					" D: "+settle.getResident().getDeathrate()+
					" H: "+settle.getResident().getHappiness()
					);
			}
		}
		int expected = 138;
		int actual = settle.getResident().getSettlerCount(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("=Settle Count : "+settle.getResident().getSettlerCount());
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testSettlementSettlerWorkerSupply()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("21","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("30","haus_einfach");
		regionTypes.put("31","haus_einfach");
		regionTypes.put("32","haus_einfach");
		regionTypes.put("26","haus_einfach");
		regionTypes.put("27","haus_einfach");
		regionTypes.put("40","haus_einfach");
		regionTypes.put("41","haus_einfach");
		regionTypes.put("42","haus_einfach");
		regionTypes.put("46","haus_einfach");
		regionTypes.put("47","haus_einfach");
		regionTypes.put("50","haus_einfach");
		regionTypes.put("51","haus_einfach");
		regionTypes.put("52","haus_einfach");
		regionTypes.put("56","haus_einfach");
		regionTypes.put("57","haus_einfach");
		regionTypes.put("60","markt");
//		regionTypes.put("63","taverne");
//		regionTypes.put("64","taverne");
		regionTypes.put("65","taverne");
//		regionTypes.put("66","kornfeld");
//		regionTypes.put("67","kornfeld");
//		regionTypes.put("68","kornfeld");
//		regionTypes.put("69","kornfeld");
		
		int id = 80;
		for (int i = 0; i < 0; i++)
		{
			regionTypes.put(String.valueOf(id+i),"haus_einfach");
		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getResident().setSettlerCount(30);
		settle.setSettlerMax();

//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();

		isOutput =  false; //(expected != actual);
		int dayCount = 0;
		if (isOutput)
		{
		  System.out.println("==Settlement Worker Supply : "+settle.getResident().getSettlerMax());
		}
		for (int i = 0; i < 198; i++)
		{
			settle.doProduce(server);
			settle.setHappiness();
			dayCount++;
			if (dayCount == 30)
			{
				settle.getTaxe(server);
				dayCount = 0;
			}
			
			if (isOutput)
			{
			System.out.println(i+" Settlers : "+settle.getResident().getSettlerCount()+
					" D: "+settle.getResident().getDeathrate()+
					" B: "+settle.getResident().getBirthrate()+
					" K: "+settle.getBank().getKonto()+
					" W: "+settle.getWarehouse().getItemList().getValue("WHEAT")+
					" H: "+settle.getResident().getHappiness()
					);
			}
		}
		
		int expected = 4;
		int actual = settle.getResident().getSettlerCount(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			
			System.out.println("==Settlement Settler Max : "+settle.getResident().getSettlerMax());
			System.out.println("=Supply : "+settle.getResident().getSettlerCount());
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementSettlerNeeded()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("21","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("30","haus_einfach");
		regionTypes.put("31","haus_einfach");
		regionTypes.put("32","haus_einfach");
		regionTypes.put("26","haus_einfach");
		regionTypes.put("27","haus_einfach");
		regionTypes.put("40","haus_einfach");
		regionTypes.put("41","haus_einfach");
		regionTypes.put("42","haus_einfach");
		regionTypes.put("46","haus_einfach");
		regionTypes.put("47","haus_einfach");
		regionTypes.put("50","haus_einfach");
		regionTypes.put("51","haus_einfach");
		regionTypes.put("52","haus_einfach");
		regionTypes.put("56","haus_einfach");
		regionTypes.put("57","haus_einfach");
		regionTypes.put("60","markt");
		regionTypes.put("63","taverne");
//		regionTypes.put("64","taverne");
//		regionTypes.put("65","taverne");
		regionTypes.put("66","kornfeld");
		regionTypes.put("67","kornfeld");
		regionTypes.put("68","kornfeld");
//		regionTypes.put("69","kornfeld");
		regionTypes.put("70","holzfaeller");
		regionTypes.put("71","koehler");
		regionTypes.put("72","steinbruch");
		regionTypes.put("73","rindermast");
		regionTypes.put("74","schweinenast");
		regionTypes.put("75","schaefer");
		
//		int id = 80;
//		for (int i = 0; i < 0; i++)
//		{
//			regionTypes.put(String.valueOf(id+i),"haus_einfach");
//		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getResident().setSettlerCount(50);
		settle.setSettlerMax();
		settle.setWorkerNeeded();


		isOutput = false; //true; //(expected != actual);
		if (isOutput)
		{
		  System.out.println("==Settlement Needed Settler : "+settle.getTownhall().getWorkerNeeded());
		}
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
			
			if (isOutput)
			{
				if (building.getBuildingType() != BuildingType.BUILDING_HOME)
				{
					System.out.println(
					building.getHsRegionType()+" : "+building.getWorkerNeeded()+" : "+building.getWorkerInstalled()
					);
				}
			}
		}
		
		int expected = 15;
		int actual = settle.getTownhall().getWorkerNeeded(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("==Settlement Settler : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementSettlerWorked()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("21","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("30","haus_einfach");
		regionTypes.put("31","haus_einfach");
		regionTypes.put("32","haus_einfach");
		regionTypes.put("26","haus_einfach");
		regionTypes.put("27","haus_einfach");
		regionTypes.put("40","haus_einfach");
		regionTypes.put("41","haus_einfach");
		regionTypes.put("42","haus_einfach");
		regionTypes.put("46","haus_einfach");
		regionTypes.put("47","haus_einfach");
		regionTypes.put("50","haus_einfach");
		regionTypes.put("51","haus_einfach");
		regionTypes.put("52","haus_einfach");
		regionTypes.put("56","haus_einfach");
		regionTypes.put("57","haus_einfach");
		regionTypes.put("60","markt");
		regionTypes.put("61", "bibliothek");
		regionTypes.put("63","taverne");
//		regionTypes.put("64","taverne");
//		regionTypes.put("65","taverne");
		regionTypes.put("66","kornfeld");
		regionTypes.put("67","kornfeld");
		regionTypes.put("68","kornfeld");
//		regionTypes.put("69","kornfeld");
		regionTypes.put("70","holzfaeller");
		regionTypes.put("71","koehler");
		regionTypes.put("72","steinbruch");
		regionTypes.put("73","rindermast");
		regionTypes.put("74","schweinenast");
		regionTypes.put("75","schaefer");
		regionTypes.put("81", "bauern_haus");
		regionTypes.put("82", "bauern_haus");
		regionTypes.put("83", "bauern_haus");
		regionTypes.put("91", "werkstatt_haus");
		regionTypes.put("92", "werkstatt_haus");
		regionTypes.put("93", "werkstatt_haus");
		
//		int id = 80;
//		for (int i = 0; i < 0; i++)
//		{
//			regionTypes.put(String.valueOf(id+i),"haus_einfach");
//		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getResident().setSettlerCount(50);
		settle.setSettlerMax();
		settle.setWorkerNeeded();
		int freeSettler = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());

		isOutput =  false; //true; //(expected != actual);
		if (isOutput)
		{
		  System.out.println("==Settlement Settler : "+settle.getResident().getSettlerCount());
		}
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
			
			if (isOutput)
			{
				if (building.getBuildingType() != BuildingType.BUILDING_HOME)
				{
					System.out.println(
					building.getHsRegionType()+" : "+building.getWorkerNeeded()+" : "+building.getWorkerInstalled()
					);
				}
			}
		}
		
		int expected = 45;
		int actual = settle.getTownhall().getWorkerNeeded(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("==Settlement WorkerNeeded : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
			System.out.println("=Settler free : "+freeSettler);
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementTax()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("8","haus_einfach");
		regionTypes.put("9","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("9","markt");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getResident().setSettlerCount(30);
		settle.setWorkerNeeded();
		int freeSettler = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
		settle.doProduce(server);
		settle.setHappiness();
		settle.getTaxe(server);

		settle.getResident().setSettlerCount(30);
		settle.setWorkerNeeded();
		freeSettler = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
		settle.doProduce(server);
		settle.setHappiness();
		settle.getTaxe(server);
		
		Integer expected = 12;
		Integer actual = (int) (settle.getBank().getKonto()*1); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println("==Settlement Tax Settler : "+settle.getTownhall().getWorkerCount());
			System.out.println("=Settlers     : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
			System.out.println("=Settler free : "+freeSettler);
			System.out.println("=Settlement Konto : "+settle.getBank().getKonto());
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testSettlementBauernhof()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("31","bauern_haus");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 64);
		
		settle.doProduce(server);
		
		int expected = 160;
		int actual = settle.getWarehouse().getItemList().getValue("WHEAT"); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testSettlementWerkstatt()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("31","bauern_haus");
		regionTypes.put("41","werkstatt_haus");
		regionTypes.put("42","werkstatt_haus");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			if (b.getHsRegion() == 41)
			{
				b.addSlot("WOOD_PICKAXE",config);
				b.addSlot("WOOD_AXE",config);
				b.addSlot("WOOD_HOE",config);
				b.addSlot(Material.IRON_SWORD.name(),config);
				b.addSlot(Material.LEATHER_CHESTPLATE.name(),config);
			}
			if (b.getHsRegion() == 42)
			{
				b.addSlot("STICK",config);
				b.addSlot("WOOD",config);
				b.addSlot("FENCE",config);
				b.addSlot(Material.STICK.name(),config);
				b.addSlot(Material.WOOD.name(),config);
			}
		}
		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
		settle.getWarehouse().depositItemValue(Material.WOOD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.IRON_INGOT.name(), 32);
		settle.getWarehouse().depositItemValue(Material.LEATHER.name(), 32);
		
		settle.doProduce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
		
		int expected = 32;
		int actual = settle.getWarehouse().getItemList().getValue(Material.FENCE.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}


	@Test
	public void testSettlementBaecker()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
//		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
//		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","haus_baecker");
//		regionTypes.put("52","haus_baecker");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			if (b.getHsRegion() == 51)
			{
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
		}
		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 512);
		
		settle.doProduce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
		
		int expected = 96;
		int actual = settle.getWarehouse().getItemList().getValue(Material.BREAD.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testSettlementSchmelze()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
//		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
//		regionTypes.put("31","bauern_haus");
//		regionTypes.put("51","haus_baecker");
//		regionTypes.put("52","haus_baecker");
		regionTypes.put("61","schmelze");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			if (b.getHsRegion() == 51)
			{
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
		}
		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 512);
		settle.getWarehouse().depositItemValue(Material.COAL.name(), 512);
		settle.getWarehouse().depositItemValue(Material.IRON_ORE.name(), 512);
		
		settle.doProduce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
		
		int expected = 32;
		int actual = settle.getWarehouse().getItemList().getValue(Material.IRON_INGOT.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testBuildingEnabled()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","haus_baecker");
		regionTypes.put("52","haus_baecker");
		regionTypes.put("61","schmelze");
		regionTypes.put("41","werkstatt_haus");
		regionTypes.put("42","werkstatt_haus");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			b.setIsActive(true);
			if (b.getHsRegion() == 51)
			{
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
				b.setIsActive(false);
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 41)
			{
				b.setIsActive(false);
				b.addSlot("WOOD_AXE",config);
				b.addSlot("WOOD_HOE",config);
				b.addSlot(Material.WOOD_PICKAXE.name(),config);
				b.addSlot(Material.IRON_SWORD.name(),config);
				b.addSlot(Material.LEATHER_CHESTPLATE.name(),config);
			}
			if (b.getHsRegion() == 42)
			{
				b.setIsActive(true);
				b.addSlot("STICK",config);
				b.addSlot("WOOD",config);
				b.addSlot("WOOD",config);
				b.addSlot(Material.STICK.name(),config);
				b.addSlot(Material.WOOD.name(),config);
			}
		}
		
		settle.getWarehouse().depositItemValue(Material.WOOD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 512);
		settle.getWarehouse().depositItemValue(Material.COAL.name(), 512);
		settle.getWarehouse().depositItemValue(Material.IRON_ORE.name(), 512);
		
		settle.checkBuildingsEnabled(server);
		settle.doProduce(server);
		settle.checkBuildingsEnabled(server);
		settle.doProduce(server);
		settle.checkBuildingsEnabled(server);
		settle.doProduce(server);
		
		int expected = 2;
		int actual = 0; 
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
			if (building.isActive() == false)
			{
				actual++;
			}
		}

		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Active =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				if (building.isActive() == false)
				{
					actual++;
					System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
				}
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}
	
	@Test
	public void testRequiredProduction()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","haus_baecker");
		regionTypes.put("52","haus_baecker");
		regionTypes.put("61","schmelze");
		regionTypes.put("41","werkstatt_haus");
		regionTypes.put("42","werkstatt_haus");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			b.setIsActive(true);
			if (b.getHsRegion() == 51)
			{
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
				b.setIsActive(false);
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 41)
			{
				b.setIsActive(false);
				b.addSlot("WOOD_AXE",config);
				b.addSlot("WOOD_HOE",config);
				b.addSlot(Material.WOOD_PICKAXE.name(),config);
				b.addSlot(Material.IRON_SWORD.name(),config);
				b.addSlot(Material.LEATHER_CHESTPLATE.name(),config);
			}
			if (b.getHsRegion() == 42)
			{
				b.setIsActive(true);
				b.addSlot("STICK",config);
				b.addSlot("WOOD",config);
				b.addSlot("WOOD",config);
				b.addSlot(Material.STICK.name(),config);
				b.addSlot(Material.WOOD.name(),config);
			}
		}
		
//		settle.getWarehouse().depositItemValue(Material.WOOD.name(), 64);
//		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
//		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
//		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
//		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 512);
//		settle.getWarehouse().depositItemValue(Material.COAL.name(), 512);
//		settle.getWarehouse().depositItemValue(Material.IRON_ORE.name(), 512);
		
		settle.checkBuildingsEnabled(server);
		settle.doProduce(server);
//		settle.checkBuildingsEnabled(server);
//		settle.produce(server);
//		settle.checkBuildingsEnabled(server);
//		settle.produce(server);
		
		int expected = 7;
		int actual = settle.getRequiredProduction().size(); 

		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Required Production =="+settle.getRequiredProduction().size());
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getRequiredProduction().keySet())
			{
				System.out.println(itemRef+":"+settle.getRequiredProduction().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}
	
}
