package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdColonyBuild;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

/**
 * <pre>
 * Test for Model Funktions.
 * Needs many predefined Data, so a Snapshot of the real serverdata should be used.
 * Event will be tested
 * - testOnTick
 * 
 * </pre>
 * @author olaf.duda
 *
 */

public class RealmModelTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);



	@Test
	public void testOnTick() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		ServerTest server = new ServerTest(data);
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
//		LogList logTest = new LogList(path);
//		DataStorage testData = new DataStorage(path);
		MessageTest message = new MessageTest();
		
		data.initData();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				data,
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
