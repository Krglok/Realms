package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.model.LogList;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.junit.Test;
//import net.krglok.realms.core.Position;

public class SettlementTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	LocationData position  = new LocationData("SteamHaven",-519.5118200333327,68,-1415.4833680460988); 

	
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
		Settlement settlement = new Settlement(owner.getPlayerName(), position);
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
		
		Settlement settlement = new Settlement(
				id, settletype, name, position,  
				owner.getPlayerName(), isCapital, barrack, warehouse,
				buildingList, townhall, bank,
				resident,"",Biome.PLAINS,0);
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
		
		Settlement settle = Settlement.createSettlement(
				position,
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(), 
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		int expected = 6;
		int actual = settle.getBuildingList().size(); 


		//isOutput = true;
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementCreate");
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
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position,
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(), 
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		int expected = 32832;
		int actual = settle.getWarehouse().getItemMax(); 

		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementItemMax");
			System.out.println("==Building List =="+settle.getBuildingList().size());
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
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position,
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 64);
		
		settle.doProduce(server);
		
		int expected = 48; // weil Biome Plains
		int actual = settle.getWarehouse().getItemList().getValue("WHEAT"); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementProduce");
			System.out.println("==Settlement Produce =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getHsRegion()+":"+building.getBuildingType().name());
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
	public void testSettlementSettlerWorkerSupply()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("8","HOME");
		regionTypes.put("9","HOME");
		regionTypes.put("10","HOME");
		regionTypes.put("11","HOME");
		regionTypes.put("12","HOME");
		regionTypes.put("13","HOME");
		regionTypes.put("14","HOME");
		regionTypes.put("15","HOME");
		regionTypes.put("16","HOME");
		regionTypes.put("17","HOME");
		regionTypes.put("18","HOME");
		regionTypes.put("19","HOME");
		regionTypes.put("20","HOME");
		regionTypes.put("60","TAVERNE");
//		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		
		int id = 80;
		for (int i = 0; i < 0; i++)
		{
			regionTypes.put(String.valueOf(id+i),"HOME");
		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);
		settle.initTreasureList();
		settle.expandTreasureList(settle.getBiome(), server);

		int startWith = 30;
		settle.getResident().setSettlerCount(startWith);
		settle.setSettlerMax();

//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();

		isOutput =  false; //(expected != actual);
		int dayCount = 0;
		if (isOutput)
		{
		  System.out.println("==testSettlementSettlerWorkerSupply : "+settle.getResident().getSettlerMax());
		}
		for (int i = 0; i < 51; i++)
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
//			System.out.println(i+" Settlers : "+settle.getResident().getSettlerCount()+
//					" D: "+settle.getResident().getDeathrate()+
//					" B: "+settle.getResident().getBirthrate()+
//					" K: "+settle.getBank().getKonto()+
//					" W: "+settle.getWarehouse().getItemList().getValue("WHEAT")+
//					" H: "+settle.getResident().getHappiness()
//					);
				int rs = settle.getResident().getSettlerCount();
				int as = 0;
//				int hs = (int) (settle.getResident().getHappiness()*10);
//				as = 30 + hs;
				as = rs;
				String sb = "";
				if ((i == 30) || (i == 60))
				{
					for (int j = 0; j < as+1; j++)
					{
						sb = sb + "-";
					}
				} else
				{
					for (int j = 0; j < as+1; j++)
					{
						sb = sb + " ";
					}
				}
				if (isOutput)
				{
					sb = sb+"#"+"    | "+rs+"|"+ ConfigBasis.format2(settle.getResident().getHappiness())
							+ "|"+ ConfigBasis.format2(settle.getFoodFactor())
							+ "|"+ ConfigBasis.format2(settle.getSettlerFactor())
							+ "|"+ ConfigBasis.format2(settle.getEntertainFactor())
							;
					System.out.println(
							ConfigBasis.setStrright(String.valueOf(i),4)
							+sb
							+"|WHEAT:"+settle.getWarehouse().getItemList().getValue("WHEAT")+"BREAD:"+settle.getWarehouse().getItemList().getValue("BREAD"));
				}
			}
			
		}
		
		int expected = 25;
		int actual = settle.getResident().getSettlerCount(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("==testSettlementSettlerWorkerSupply : "+settle.getResident().getSettlerMax());
			System.out.println("==Settler Supply Max : "+settle.getResident().getSettlerMax());
			System.out.println("=StartWith      : "+startWith);
			System.out.println("=Expectedt      : "+expected);
			System.out.println("=Actual Settler : "+settle.getResident().getSettlerCount());
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementWorkerNeeded()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
//		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("8","HOME");
		regionTypes.put("9","HOME");
		regionTypes.put("10","HOME");
		regionTypes.put("11","HOME");
		regionTypes.put("12","HOME");
		regionTypes.put("13","HOME");
		regionTypes.put("14","HOME");
		regionTypes.put("15","HOME");
		regionTypes.put("16","HOME");
		regionTypes.put("17","HOME");
		regionTypes.put("18","HOME");
		regionTypes.put("19","HOME");
		regionTypes.put("20","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("67","WHEAT");
		regionTypes.put("68","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("70","WOODCUTTER");
		regionTypes.put("71","CHARBURNER");
		regionTypes.put("72","QUARRY");
		regionTypes.put("75","SHEPHERD");
		
//		int id = 80;
//		for (int i = 0; i < 0; i++)
//		{
//			regionTypes.put(String.valueOf(id+i),"haus_einfach");
//		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		settle.getResident().setSettlerCount(50);
		settle.setSettlerMax();
		settle.setWorkerNeeded();
		
		int expected = 10;
		int actual = settle.getTownhall().getWorkerNeeded(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementWorkerNeeded");
			System.out.println("==Settlement Settler : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
			System.out.println("==Settlement Needed Settler : "+settle.getTownhall().getWorkerNeeded());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				
				if (isOutput)
				{
					if (building.getBuildingType() != BuildPlanType.HOME)
					{
						System.out.println(
						building.getHsRegionType()+" : "+building.getWorkerNeeded()+" : "+building.getWorkerInstalled()
						);
					}
				}
			}
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementSettlerWorked()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
//		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("8","HOME");
		regionTypes.put("9","HOME");
		regionTypes.put("10","HOME");
		regionTypes.put("11","HOME");
		regionTypes.put("12","HOME");
		regionTypes.put("13","HOME");
		regionTypes.put("14","HOME");
		regionTypes.put("15","HOME");
		regionTypes.put("16","HOME");
		regionTypes.put("17","HOME");
		regionTypes.put("18","HOME");
		regionTypes.put("19","HOME");
		regionTypes.put("20","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("67","WHEAT");
		regionTypes.put("68","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("70","WOODCUTTER");
		regionTypes.put("71","CHARBURNER");
		regionTypes.put("72","QUARRY");
		regionTypes.put("75","SHEPHERD");
		regionTypes.put("81", "FARMHOUSE");
		regionTypes.put("82", "FARMHOUSE");
		regionTypes.put("83", "FARMHOUSE");
		regionTypes.put("91", "WORKSHOP");
		regionTypes.put("92", "WORKSHOP");
		regionTypes.put("93", "WORKSHOP");
		
//		int id = 80;
//		for (int i = 0; i < 0; i++)
//		{
//			regionTypes.put(String.valueOf(id+i),"haus_einfach");
//		}
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		settle.getResident().setSettlerCount(50);
		settle.setSettlerMax();
		settle.setWorkerNeeded();
		int freeSettler = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());

		
		int expected = 31;
		int actual = settle.getTownhall().getWorkerNeeded(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementSettlerWorked");
			System.out.println("==Settlement WorkerNeeded : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
			System.out.println("=Settler free : "+freeSettler);
			System.out.println("==Settlement Settler : "+settle.getResident().getSettlerCount());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
			
				if (building.getBuildingType() != BuildPlanType.HOME)
				{
					System.out.println(ConfigBasis.setStrleft(building.getHsRegionType(), 15)
					+" : "+building.getWorkerNeeded()+" : "+building.getWorkerInstalled()
					);
				}
			}
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSettlementTax()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("69","WAREHOUSE");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);
		double value = settle.getBank().getKonto() * -1.0;
		settle.getBank().addKonto(value, "Reset");
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
		
		Integer expected = 20;
		Integer actual = (int) (settle.getBank().getKonto()); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementTax ");
			System.out.println("==Settlement Tax Settler : "+settle.getTownhall().getWorkerCount());
			System.out.println("=Settlers     : "+settle.getResident().getSettlerCount());
			System.out.println("=WorkerNeeded : "+settle.getTownhall().getWorkerNeeded());
			System.out.println("=Settler free : "+freeSettler);
			System.out.println("=Settlement Konto : "+settle.getBank().getKonto());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
			
				if (building.getBuildingType() != BuildPlanType.HOME)
				{
					System.out.println(ConfigBasis.setStrleft(building.getHsRegionType(), 15)
					+" : "+building.getWorkerNeeded()+" : "+building.getTaxe(server, settle.setWorkerToBuilding(settle.getResident().getSettlerCount()))
					);
				}
			}
			System.out.println("=Bank Transaction : "+settle.getBank().getTransactionList().size());
			for (String log :settle.getBank().getTransactionList().getLogList())
			{
				System.out.println(log);
			}
			
		}
		
		assertEquals(expected, actual);

	}

	@Test
	public void testSettlementBauernhof()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
//		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("31","FARMHOUSE");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 64);
		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 64);
		
		settle.doProduce(server);
		
		int expected = 96;	// wegen Biome PLAINS 
		int actual = settle.getWarehouse().getItemList().getValue("WHEAT"); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementBauernhof");
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
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("31","FARMHOUSE");
		regionTypes.put("41","WORKSHOP");
		regionTypes.put("42","WORKSHOP");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

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
		
		int expected = 2;
		int actual = settle.getWarehouse().getItemList().getValue(Material.FENCE.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("testSettlementWerkstatt");
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
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("31","FARMHOUSE");
		regionTypes.put("61","SMELTER");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

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
//		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
//		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
//		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 512);
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
			System.out.println(" ");
			System.out.println("testSettlementSchmelze");
			System.out.println("==Settlement Schmelze =="+settle.getBuildingList().size());
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
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("31","FARMHOUSE");
		regionTypes.put("41","WORKSHOP");
		regionTypes.put("42","WORKSHOP");
		regionTypes.put("51","BAKERY");
		regionTypes.put("52","BAKERY");
		regionTypes.put("61","SMELTER");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

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
			System.out.println(" ");
			System.out.println("testBuildingEnabled");
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
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		regionTypes.put("31","FARMHOUSE");
		regionTypes.put("41","WORKSHOP");
		regionTypes.put("42","WORKSHOP");
		regionTypes.put("51","BAKERY");
		regionTypes.put("52","BAKERY");
		regionTypes.put("61","SMELTER");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			b.setIsActive(true);
			if (b.getHsRegion() == 51)
			{
				b.setIsActive(true);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
				b.setIsActive(false);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 41)
			{
				b.setIsActive(true);
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
//		settle.doProduce(server);
//		settle.checkBuildingsEnabled(server);
//		settle.doProduce(server);
		
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
			for (BoardItem b : settle.getProductionOverview().values())
			{
				System.out.println(b.getName()+": "+b.getLastValue()+ " | "+settle.getProductionOverview().getCycleCount()+" | "+b.getCycleSum()+" | "+settle.getProductionOverview().getPeriodCount()+" | "+b.getPeriodSum());
				
			}
		}
		assertEquals(expected, actual);

	}
	
}
