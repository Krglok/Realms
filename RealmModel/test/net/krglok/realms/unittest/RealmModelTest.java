package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdColonyBuild;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class RealmModelTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);


	@Test
	public void testRealmModel() 
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();


		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		ServerTest server = new ServerTest(data);
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null
//				logTest
				);
		
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
		
		ServerTest server = new ServerTest(data);
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null
//				logTest
				);
		
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
		config.initSuperSettleTypes();
		
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();
				
		ServerTest server = new ServerTest(data);

		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null
//				logTest
				);
				
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
				
		ServerTest server = new ServerTest(data);

		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				null
//				logTest
				);
		
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

		ServerTest server = new ServerTest(data);
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message
//				logTest
				);
		
		Boolean expected = true; 
		Boolean actual = false; 
		rModel.OnEnable();
		rModel.getSettlements().getSettlement(1).getWarehouse().depositItemValue("LOG", 32);

		rModel.OnProduction("SteamHaven");
		if (rModel.getProductionQueue().size() > 0)
		{
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
			rModel.OnProduction("SteamHaven");
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
			for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().values())
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

		ServerTest server = new ServerTest(data);
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message
//				logTest
				);
		
		rModel.OnEnable();
		
		String name = "Newhome";
		LocationData centerPos = new LocationData("Test", 0.0, 0.0, 0.0);
		String owner = "drAdmin";
		
		McmdColonistCreate colonist1 = new McmdColonistCreate(rModel, name, centerPos, owner);
		
		boolean isCleanUp = true;
		int colonyId = 1;
		McmdColonyBuild colonist2 = new McmdColonyBuild(rModel, colonyId, isCleanUp);
		
		Boolean expected = true; 
		Boolean actual = false;
		int i = 0;

		rModel.OnProduction("SteamHaven");
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnCommand(colonist1);
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
//		System.out.println((i++)+" OnTick "+rModel.getModelStatus());
		rModel.OnCommand(colonist2);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
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
			for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().values())
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
