package net.krglok.realms.kingdom;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.tool.LogList;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.MessageTest;
import net.krglok.realms.unittest.ServerTest;

import org.bukkit.ChatColor;
import org.junit.Test;

public class RealmModelLehenTest {

	
	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);
	
	
	private void printWarehouse(AbstractSettle settle)
	{
		System.out.println("============================================================");
		System.out.println("Settlement : "+settle.getId()+" "+settle.getName());
		System.out.println("Warehouse : "+settle.getWarehouse().getItemMax());
		for (String itemRef : settle.getWarehouse().getItemList().keySet())
		{
			System.out.println(itemRef+" : "+settle.getWarehouse().getItemList().getValue(itemRef));
		}
	}
	
	private void printBuildingList(Settlement settle)
	{
		System.out.println("============================================================");
		System.out.println("Settlement : "+settle.getId()+" "+settle.getName());
		for (Building building :settle.getBuildingList().values())
		{
			if ((building.getBuildingType() != BuildPlanType.HOME) && (building.getBuildingType() != BuildPlanType.HOUSE))
			{
				System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getWorkerInstalled()+":"+building.isEnabled());
			}
		}

	}

	private void printProductionView(Settlement settle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Settlement ["+settle.getId()+"] : "+settle.getName());
		msg.add("Item              |    Day  |    Month |  Store ["+settle.getProductionOverview().getPeriodCount()+"]");
		for (String ref : settle.getProductionOverview().sortItems())
		{
			BoardItem bItem = settle.getProductionOverview().get(ref);
			String name = ConfigBasis.setStrleft(bItem.getName()+"__________", 12);
			String last = ConfigBasis.setStrright(String.valueOf((int)bItem.getLastValue()), 9);
			String cycle = ConfigBasis.setStrright(String.valueOf((int)bItem.getCycleSum()), 9);
			String period = ConfigBasis.setStrright(String.valueOf((int)  settle.getWarehouse().getItemList().getValue(bItem.getName())), 6);
			msg.add(name +"|"+last+"|"+cycle+"|"+period+"|");
		}
		msg.add("");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	private void simProductioncycle(String world, RealmModel rModel)
	{
		
		rModel.OnProduction(world);
		for (int i = 0; i < 100; i++) 
		{
		  rModel.OnTick();	
		}
	}
	
	@Test
	public void testOnProduction() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		DataStorage testData = new DataStorage(dataFolder);
		MessageTest message = new MessageTest();
		ServerTest server = new ServerTest(testData);
//		String path = "D:\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		
		testData.initData();
		
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

		Settlement settle = rModel.getSettlements().getSettlement(4);
		Lehen lehen = rModel.getData().getLehen().getLehen(1);
		
		settle.getWarehouse().depositItemValue("LOG", 32);
		
		String world = "DRASKORIA";
		
		simProductioncycle(world, rModel);
		simProductioncycle(world, rModel);
		
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_PRODUCTION);

		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println("Lehen Test   =================================================");
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("Lehen List: "+rModel.getData().getLehen().size());
			System.out.println("Settlement List: "+rModel.getSettlements().count());
			System.out.println("Settler Max    : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerMax());
			System.out.println("Settler Count  : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerCount());
			System.out.println("Production Queue: "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());

			printBuildingList(settle);
			printProductionView(settle);
			printWarehouse(lehen);
		}
		assertEquals(expected, actual);
	}


}
