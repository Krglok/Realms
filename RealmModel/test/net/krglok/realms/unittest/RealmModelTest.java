package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.ModelStatus;
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

		ServerTest server = new ServerTest();

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
		
		ServerTest server = new ServerTest();
		
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
				
		ServerTest server = new ServerTest();

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
				
		ServerTest server = new ServerTest();

		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null);
		
//		String command = RealmsCommandType.REALMS.name();
//		String subCommand = "version";
		McmdEnable modelCommand = new McmdEnable(rModel);
		
//		rModel.OnEnable();
		rModel.OnCommand(modelCommand);
		
		Boolean expected = rModel.isInit();
		Boolean actual = false; 

		actual =  (rModel.getcommandQueue().size() == 0);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getcommandQueue().size());
			System.out.println("CommandQueue  : "+rModel.getcommandQueue().size());

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

		ServerTest server = new ServerTest();
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
				if (building.getBuildingType() != BuildPlanType.HOME)
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
	public void testOnTick() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		ServerTest server = new ServerTest();
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message);
		
		McmdEnable modelCommand = new McmdEnable(rModel);
		
		Boolean expected = true; 
		Boolean actual = false;
		int i = 0;
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
//		rModel.OnEnable();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());

		rModel.OnProduction();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnCommand(modelCommand);
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("Test OnTick ");
			System.out.println("CommandQueue : "+rModel.getcommandQueue().size());
			System.out.println("ProdQueue    : "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().getBuildingList().values())
			{
				if (building.getBuildingType() != BuildPlanType.HOME)
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
