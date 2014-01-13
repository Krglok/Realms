package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.MessageText;
import net.krglok.realms.data.TestServer;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmCommand;
import net.krglok.realms.model.RealmCommandType;
import net.krglok.realms.model.RealmModel;

import org.junit.Test;

public class RealmModelTest
{

	private Boolean isOutput = false; // set this to false to suppress println


	@Test
	public void testRealmModel() 
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();

		TestServer server = new TestServer();

		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null);
		
//		rModel.setOwners(ownerList);
//		rModel.setRealms(realmList);
//		rModel.setSettlements(settleList);

		Boolean expected = false;
		Boolean actual = rModel.isInit(); 
		
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getModelVersion());

		}
		assertEquals(expected, actual);
		
	}

	@Test
	public void testOnEnable() {
		ConfigTest config = new ConfigTest();
//		config.initConfigData();
//		config.initRegionBuilding();
////		config.initSuperBuilding();
//		config.initSuperSettleTypes();
		
		TestServer server = new TestServer();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		DataTest testData = new DataTest();
//		OwnerList ownerList =  testData.getTestOwners();
//		RealmList realmList =  testData.getTestRealms();
//		SettlementList settleList = testData.getTestSettlements();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null);
		
//		rModel.setOwners(ownerList);
//		rModel.setRealms(realmList);
//		rModel.setSettlements(settleList);

		Boolean expected = true; //rModel.isInit();
		Boolean actual = false; 
		
		rModel.OnEnable();
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_ENABLED);
		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getModelVersion());
			System.out.println("onEnable  : "+rModel.getModelStatus());

		}
		assertEquals(expected, actual);
	}

	@Test
	public void testOnDisable() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		TestServer server = new TestServer();

		DataTest testData = new DataTest();
//		OwnerList ownerList =  testData.getTestOwners();
//		RealmList realmList =  testData.getTestRealms();
//		SettlementList settleList = testData.getTestSettlements();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null);
				
		
//		rModel.setOwners(ownerList);
//		rModel.setRealms(realmList);
//		rModel.setSettlements(settleList);

		Boolean expected = true; //rModel.isInit();
		Boolean actual = false; 
		
		rModel.OnDisable();
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_DISABLED);
		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getModelVersion());
			System.out.println("onDisable  : "+rModel.getModelStatus());

		}
		assertEquals(expected, actual);
	}

	@Test
	public void testOnCommand() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
//		config.initSuperBuilding();
		config.initSuperSettleTypes();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		TestServer server = new TestServer();

		DataTest testData = new DataTest();
//		OwnerList ownerList =  testData.getTestOwners();
//		RealmList realmList =  testData.getTestRealms();
//		SettlementList settleList = testData.getTestSettlements();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null);
		
//		rModel.setOwners(ownerList);
//		rModel.setRealms(realmList);
//		rModel.setSettlements(settleList);

		
		String command = RealmCommandType.MODEL.name();
		String subCommand = "version";
		RealmCommand realmCommand = new RealmCommand(command, subCommand);
		
		rModel.OnEnable();
		rModel.OnCommand(realmCommand);
		
		Boolean expected = rModel.isInit();
		Boolean actual = false; 

//		actual =  (rModel.getcommandQueue().get(0).command() == RealmCommandType.MODEL);
		actual =  (rModel.getcommandQueue().size() == 0);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getcommandQueue().size());
			System.out.println("CommandQueue  : "+rModel.getcommandQueue().size());
//			System.out.println("Command    : "+rModel.getcommandQueue().get(0).command());
//			System.out.println("SubCommand : "+rModel.getcommandQueue().get(0).subCommand().name());

		}
		assertEquals(expected, actual);
	}

	@Test
	public void testOnProduction() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		TestServer server = new TestServer();
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message);
		
		Boolean expected = true; 
		Boolean actual = false; 
		rModel.OnEnable();
		rModel.getSettlements().getSettlement(1).getWarehouse().depositItemValue("LOG", 32);
//		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(41).setIsActive(false);
		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(42).setIsActive(false);
		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(43).setIsActive(false);

		rModel.OnProduction();
		if (rModel.getProductionQueue().size() > 0)
		{
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
			rModel.OnProduction();
//			rModel.OnProduction();
		}
		
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_PRODUCTION);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("Settlement List: "+rModel.getSettlements().count());
			System.out.println("Settler Max    : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerMax());
			System.out.println("Settler Count  : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerCount());
			System.out.println("OnProduction Queue: "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().getBuildingList().values())
			{
				if (building.getBuildingType() != BuildingType.BUILDING_HOME)
				{
					System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getHsRegionType()+" : "+building.getWorkerInstalled());
				}
			}
			System.out.println("Warehouse : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax());
			for (String itemRef : rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+" : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().getValue(itemRef));
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testOnMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnAttack() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnTrain() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnTick() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		TestServer server = new TestServer();
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message);
		String command = RealmCommandType.MODEL.name();
		String subCommand = "version";
		RealmCommand realmCommand = new RealmCommand(command, subCommand);
		
		Boolean expected = true; 
		Boolean actual = false; 
		rModel.OnEnable();
		rModel.getSettlements().getSettlement(1).getWarehouse().depositItemValue("LOG", 32);
//		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(41).setIsActive(false);
		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(42).setIsActive(false);
		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(43).setIsActive(false);

		rModel.OnProduction();
		if (rModel.getProductionQueue().size() > 0)
		{
			rModel.OnCommand(realmCommand);
			rModel.OnTick();
			rModel.OnTick();
//			rModel.OnTick();
//			rModel.OnTick();
//			rModel.OnTick();
		}
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("OnTick ");
			System.out.println("CommandQueue : "+rModel.getcommandQueue().size());
			System.out.println("ProdQueue    : "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().getBuildingList().values())
			{
				if (building.getBuildingType() != BuildingType.BUILDING_HOME)
				{
					System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getHsRegionType()+" : "+building.getWorkerInstalled());
				}
			}
			System.out.println("Warehouse : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax());
			for (String itemRef : rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+" : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().getValue(itemRef));
			}
		}
		assertEquals(expected, actual);
	}
	
	
	
}
