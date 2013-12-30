package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.RealmList;
import net.krglok.realms.core.RealmModel;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;

import org.junit.Test;

public class RealmModelTest
{

	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void ReamModelTest()
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();
		
		TestServer tServer = new TestServer();
		
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		RealmList realmList =  testData.getTestRealms();
		SettlementList settleList = testData.getTestSettlements();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
		RealmModel rModel = new RealmModel(realmCounter, settlementCounter);
		
		rModel.setOwners(ownerList);
		rModel.setRealms(realmList);
		rModel.setSettlements(settleList);

		int expected = 3000;
		int actual = rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax(); 
		
		//isOutput = true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getModelVersion());
			System.out.println("Realm  : "+rModel.getRealms().getRealm(1).getId()+":"+rModel.getRealms().getRealm(1).getName());

			SettlementList sList = rModel.getSettlements();
			System.out.println("==Settlements : "+sList.count());
			for (Settlement settle : sList.getSettlements().values())
			{
				System.out.println(settle.getId()+":"+settle.getName()+":"+settle.getBuildingList().size());
				System.out.println(" Owner  : "+sList.getSettlement(1).getOwner().getPlayerName());
				System.out.println(" Realm  : "+rModel.getRealms().getRealm(sList.getSettlement(1).getOwner().getRealmID()).getName());
				for (Building building : settle.getBuildingList().getBuildingList().values())
				{
					System.out.println("  "+building.getBuildingType().name()+":"+building.getHsRegion());
				}
			}
		}
		assertEquals(expected, actual);
		
	}

	@Test
	public void RealmModelWarehouse()
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();
		
		TestServer tServer = new TestServer();
		
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		RealmList realmList =  testData.getTestRealms();
		SettlementList settleList = testData.getTestSettlements();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
		RealmModel rModel = new RealmModel(realmCounter, settlementCounter);
		
		rModel.setOwners(ownerList);
		rModel.setRealms(realmList);
		rModel.setSettlements(settleList);

		int value = 0;
		Warehouse w = rModel.getSettlements().getSettlement(1).getWarehouse();
		for (String itemRef : w.getItemList().keySet())
		{
			value = 64;
			w.depositItemValue(itemRef, value);
		}
		
		String itemRef = "LOG";
		value	   = 123;
		
		rModel.getSettlements().getSettlement(1).getWarehouse().depositItemValue(itemRef, value);
		int expected = 2000;
		int actual = rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax(); 
		
		isOutput = true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getModelVersion());
			System.out.println("Realm  : "+rModel.getRealms().getRealm(1).getId()+":"+rModel.getRealms().getRealm(1).getName());

			SettlementList sList = rModel.getSettlements();
			System.out.println("==Warehouse : "+sList.getSettlement(1).getWarehouse().getItemMax()+" : "+sList.getSettlement(1).getWarehouse().getItemCount());
			int i = 0;
			for (String itemName : sList.getSettlement(1).getWarehouse().getItemList().keySet())
			{
				i++;
				Item item = sList.getSettlement(1).getWarehouse().getItemList().getItem(itemName);
				System.out.println(i+":"+item.ItemRef()+":"+item.value());
			}
		}
		assertEquals(expected, actual);
		
	}
	
}
