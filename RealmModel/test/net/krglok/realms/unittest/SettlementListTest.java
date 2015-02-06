package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.tool.LogList;

import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlementListTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	@Test
	public void SetOwnerCapitalTest()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		SettlementList sList = new SettlementList(1);
		Owner owner = new Owner();
		owner.setId(5);
		owner.setPlayerName("playerName");
		Settlement settlement = new Settlement(); //, logTest);
		sList.addSettlement(settlement);
		sList.setOwnerCapital(owner, settlement.getId());
		int expected = settlement.getId();
		int actual = owner.getCapital();
		assertEquals(expected, actual);
	}

	@Test
	public void UpdateOwnerCapitalTest()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);

		SettlementList sList = new SettlementList(1);
		Owner owner = new Owner();
		owner.setId(5);
		owner.setPlayerName("playerName");
		
		Settlement settlement = new Settlement(); //,logTest);
		sList.addSettlement(settlement);
		settlement = new Settlement(); //, logTest);
		sList.addSettlement(settlement);
		sList.updateOwnerCapital(owner, settlement.getId());
		int expected = settlement.getId();
		int actual = owner.getCapital();
		assertEquals(expected, actual);
	}

	@Test
	public void  CreateSettlementTest()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		HashMap<String,String> regionTypes = testData.defaultRegionList();
		HashMap<String,String> superRegionTypes = testData.defaultSuperregionList();
	
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();

		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);
		HashMap<String,String> superRegionSettles = config.makeSuperRegionSettleTypes(superRegionTypes);
		
		SettlementList sList = new SettlementList(1);
		Owner owner = new Owner();
		
//		SettlementList.createSettlement_0(null, superRegionTypes, superRegionTypes, regionBuildings, owner, biome, logList);
		
		sList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,testData.getPriceList());
		
		int expected = 1;
		int actual = sList.count();
		assertEquals(expected, actual);
	}

	@Test
	public void addSettlementsTest()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		HashMap<String,String> regionTypes = testData.defaultRegionList();
		HashMap<String,String> superRegionTypes = testData.defaultSuperregionList();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();

		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);
		HashMap<String,String> superRegionSettles = config.makeSuperRegionSettleTypes(superRegionTypes);
		
		SettlementList sList = new SettlementList(1);
		Owner owner = new Owner();
		
//		Settlement settlement = new Settlement(owner);
//		sList.addSettlement(settlement);
//		settlement = new Settlement(owner);
//		sList.addSettlement(settlement);
		
		SettlementList newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,testData.getPriceList());
		sList.addSettlements(newList);

		newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,testData.getPriceList());
		sList.addSettlements(newList);

		newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,testData.getPriceList());
		sList.addSettlements(newList);

		int expected = 3;
		int actual = sList.count();

		//isOutput = true;
		if (isOutput)
		{
			System.out.println("==Settlements :"+sList.count());
			for (Settlement settle : sList.values())
			{
				System.out.println(settle.getId()+":"+settle.getName()+":"+settle.getBuildingList().size());
				for (Building building : settle.getBuildingList().values())
				{
					System.out.println("  "+building.getBuildingType().name()+":"+building.getHsRegion());
				}
			}
		}

		assertEquals(expected, actual);
	}

	@Test
	public void addSettlementsProduktion()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testDat = new DataTest();
		HashMap<String,String> regionTypes = testDat.defaultRegionList();
		HashMap<String,String> superRegionTypes = testDat.defaultSuperregionList();
		ServerTest server = new ServerTest(data);
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();

		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);
		HashMap<String,String> superRegionSettles = config.makeSuperRegionSettleTypes(superRegionTypes);
		
		SettlementList sList = new SettlementList(1);
		Owner owner = new Owner();
		
//		Settlement settlement = new Settlement(owner);
//		sList.addSettlement(settlement);
//		settlement = new Settlement(owner);
//		sList.addSettlement(settlement);
		
		SettlementList newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,data.getPriceList());
		sList.addSettlements(newList);

		newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,data.getPriceList());
		sList.addSettlements(newList);

		newList = SettlementList.createSettlement(pos, superRegionSettles, regionTypes, regionBuildings, owner.getId(),Biome.PLAINS,data.getPriceList());
		sList.addSettlements(newList);

		int expected = 3;
		int actual = sList.count();
		int dayCounter = 0;

		for (Settlement settle : sList.values())
		{
			dayCounter++;
			settle.doProduce(server,data);
			settle.doHappiness(data);
			if (dayCounter == 30)
			{
				settle.getTaxe(server);
				dayCounter = 0;
			}
		}
		//isOutput = true;
		if (isOutput)
		{
			System.out.println("==Settlements :"+sList.count());
			for (Settlement settle : sList.values())
			{
				System.out.println(settle.getId()+":"+settle.getName()+":"+settle.getBuildingList().size());
				for (Building building : settle.getBuildingList().values())
				{
					System.out.println("  "+building.getBuildingType().name()+":"+building.getHsRegion());
				}
			}
		}

		assertEquals(expected, actual);
	}
	
}
