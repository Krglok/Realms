package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.RealmCommand;
import net.krglok.realms.core.RealmCommandType;
import net.krglok.realms.core.RealmModel;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;

import org.junit.Test;

public class ModelLoopTest 
{

	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void testLoop() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		TestServer server = new TestServer();

		DataTest testData = new DataTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData);
		String command = RealmCommandType.MODEL.name();
		String subCommand = "version";
		RealmCommand realmCommand = new RealmCommand(command, subCommand);
		
		Boolean expected = true; 
		Boolean actual = false; 
		int steps = 0;
		rModel.OnEnable();
		rModel.getSettlements().getSettlement(1).getWarehouse().depositItemValue("LOG", 32);
//		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(41).setIsActive(false);
//		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(42).setIsActive(false);
//		rModel.getSettlements().getSettlement(1).getBuildingList().getBuilding(43).setIsActive(false);

		rModel.OnProduction();
		steps++;
		if (rModel.getProductionQueue().size() > 0)
		{
			rModel.OnCommand(realmCommand);
			steps++;
			rModel.OnTick();
			steps++;
			for (int i = 0; i < 100; i++) 
			{
				rModel.OnProduction();
				rModel.OnTick();
				steps++;
			
			}
		}
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

		isOutput = true; // (expected != actual); //true;
		if (isOutput)
		{
			System.out.println("LOOP TEST:"+steps);
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
			System.out.println("Settler Max    : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerMax());
			System.out.println("Settler Count  : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerCount());
			System.out.println("Warehouse : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax());
			for (String itemRef : rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+" : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().getValue(itemRef));
			}
		}
		assertEquals(expected, actual);
	}

}
