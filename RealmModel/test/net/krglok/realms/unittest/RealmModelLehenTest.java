package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.model.McmdHireSettler;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.tool.LogList;

import org.bukkit.ChatColor;
import org.junit.Test;

public class RealmModelLehenTest {

	
	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);
	
	
	private void printWarehouse(AbstractSettle aSettle)
	{
		System.out.println("============================================================");
		System.out.println(aSettle.getSettleType()+" : "+aSettle.getId()+" "+aSettle.getName());
		System.out.println("Warehouse : "+aSettle.getWarehouse().getItemMax());
		for (String itemRef : aSettle.getWarehouse().getItemList().keySet())
		{
			System.out.println(itemRef+" : "+aSettle.getWarehouse().getItemList().getValue(itemRef));
		}
	}
	
	private void printBuildingMsg(AbstractSettle aSettle)
	{
		System.out.println("============================================================");
		System.out.println(aSettle.getSettleType()+": "+aSettle.getId()+" "+aSettle.getName());
		System.out.println("printBuildingMsg");
		for (Building building :aSettle.getBuildingList().values())
		{
			if (building.getMsg().size() > 0)
			{
				System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getWorkerInstalled()+":"+building.isEnabled());
				for (String s : building.getMsg())
				{
					System.out.println(s);
				}
			}
		}

	}

	private void printMilitaryBuildingList(RealmModel rModel)
	{
		System.out.println("============================================================");
		int count = 0;
		for (Settlement settle : rModel.getData().getSettlements().values())
		{
			System.out.println(settle.getSettleType()+": "+settle.getId()+" "+settle.getName());
			for (Building building :settle.getBuildingList().values())
			{
				if ((BuildPlanType.getBuildGroup(building.getBuildingType()) == 500))
				{
					count++;
					System.out.print( building.getId()+":"+ConfigBasis.setStrleft(building.getBuildingType().name(),15));
					System.out.print(":"+building.getMaxTrain()); 
					System.out.println(":" +building.getWorkerInstalled()+":"+building.isEnabled());
				}
			}
		}
		System.out.println("Anzah [" +count+"]");

	}
	
	private void printProductionView(AbstractSettle aSettle)
	{
		System.out.println("============================================================");
		System.out.println("Production Messages");
		for (String s : aSettle.getMsg())
		{
			System.out.println(s);
		}
	}

	private void printTrainingStatus(AbstractSettle aSettle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("============================================================");
		msg.add("TrainingStatus");
		msg.add(aSettle.getSettleType()+" ["+aSettle.getId()+"] : "+aSettle.getName());
		msg.add("Building       |ct| NPC                  |  ");
		for (Building building : aSettle.getBuildingList().values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500)
			{
				String name = building.getId()+":"+ConfigBasis.setStrleft(building.getBuildingType().name()+"__________", 15);
				String last = ConfigBasis.setStrright(building.getMaxTrain(), 2)+":"+ConfigBasis.setStrright(building.getTrainCounter(), 4);
				NpcData recrute = aSettle.getBarrack().getUnitList().getBuildingRecrute(building.getId());
				String cycle = "                 ";
				if (recrute  != null)
				{
					cycle = ":"+recrute.getId()+":"+recrute.getName();
				}
				String period = building.isEnabled().toString(); 
				msg.add(name +"|"+last+"|"+cycle+"|"+period+"|");
			}
		}
		msg.add("");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}

	private void printLehenNpc(AbstractSettle aSettle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("======================================================");
		msg.add("NPC List");
		msg.add(aSettle.getSettleType()+" ["+aSettle.getId()+"] : "+aSettle.getName());
		msg.add("|NPC                  |  ");
		for (NpcData npc : aSettle.getResident().getNpcList().values())
		{
				String name = ConfigBasis.setStrright(npc.getId(),4)+":"
						+ConfigBasis.setStrleft(npc.getName()+"__________", 16);
				String last = ":"+npc.getNpcType()+":"+npc.getUnitType();  
						
				String cycle = " W:"+npc.getWorkBuilding()+" H:"+npc.getHomeBuilding();
				Building building = aSettle.getBuildingList().getBuilding(npc.getHomeBuilding());
				if (building != null)
				{
					cycle = cycle+":"+building.getBuildingType().name();
				}
				msg.add(name +"|"+last+"|"+cycle+"|");
		}
		msg.add("");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	

	private void printLehenUnit(AbstractSettle aSettle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("======================================================");
		msg.add("Unit List");
		msg.add(aSettle.getSettleType()+" ["+aSettle.getId()+"] : "+aSettle.getName());
		msg.add("|NPC                  |  ");
		for (NpcData npc : aSettle.getBarrack().getUnitList())
		{
				String name = ConfigBasis.setStrright(npc.getId(),4)+":"
						+ConfigBasis.setStrleft(npc.getName()+"__________", 16);
				String last = ":"+npc.getNpcType()+":"+npc.getUnitType();  
						
				String cycle = " W:"+npc.getWorkBuilding();
				Building building = aSettle.getBuildingList().getBuilding(npc.getWorkBuilding());
				if (building != null)
				{
					cycle = cycle+":"+building.getBuildingType().name();
				}
				msg.add(name +"|"+last+"|"+cycle+"|");
		}
		msg.add("");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	
	private void printTradeOrders(RealmModel rModel, AbstractSettle aSettle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("======================================================");
		msg.add("Trade Orders ");
		msg.add(aSettle.getSettleType()+" ["+aSettle.getId()+"] : "+aSettle.getName());
		msg.add("|SELL Order                 |  ");
		for (TradeMarketOrder order : rModel.getTradeMarket().values())
		{
			if (order.getSettleType() == aSettle.getSettleType())
			{
				if (order.getSettleID() == aSettle.getId())
				{
					String name = order.getSettleID()+":"+aSettle.getName()+":"+order.ItemRef()+":"+order.value();
					msg.add(name);
				}
			}
		}
		msg.add("|BUY Order                 |  ");
		for (TradeOrder order : aSettle.getTrader().getBuyOrders().values())
		{
				String name = order.getTargetId()+":"+aSettle.getName()+":"+order.ItemRef()+":"+order.value();
				msg.add(name);
		}
		msg.add("|TRANSPORT Order                 |  ");
		for (TradeMarketOrder order : rModel.getTradeMarket().values())
		{
			if (order.getSettleID() == aSettle.getId())
			{
				String name = order.getSettleID()+":"+aSettle.getName()+":"+order.ItemRef()+":"+order.value();
				msg.add(name);
			}
		}
		for (TradeMarketOrder order : rModel.getTradeTransport().values())
		{
			if (order.getSettleID() == aSettle.getId())
			{
				String name = order.getSettleID()+":"+aSettle.getName()+":"+order.ItemRef()+":"+order.value()+">>"+order.getTargetId();
				msg.add(name);
			}
		}
		msg.add("");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	
	private void simDayCycle(String world, RealmModel rModel)
	{
		int maxTick = 1200;
		System.out.println("simStart========================");
		// es sind 1200 cyclen pro Tag
		int l = 0;
		for (int i = 0; i < maxTick; i++) 
		{
		  // es ist 18:00 und produktionsberechnung
		  if (i == 900)
		  {
			  rModel.OnProduction(world);
		  } else
		  {
			  rModel.OnTick();
		  }
		  if (rModel.getData().writeCache.size() > 0)
		  {
			  rModel.getData().writeCache.run();
		  }
		  System.out.print(">");
		  l++;
		  if (l > 80) { l= 0; System.out.println(""); }
		}
		// der Cache muss geleert werden
		while (rModel.getData().writeCache.size() > 0)
		{
			  System.out.print(".");
			  rModel.getData().writeCache.run();
			  l++;
			  if (l > 80) { l= 0; System.out.println(""); }
		}
		System.out.println("");
		System.out.println("simEnd  after "+maxTick+" cycles");
	}
	
	@Test
	public void testOnProduction() 
	{
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
		Lehen lehen = rModel.getData().getLehen().getLehen(2);
		
		settle.getWarehouse().depositItemValue("LOG", 32);
		settle.getWarehouse().depositItemValue("LEATHER_BOOTS", 32);
		settle.getWarehouse().depositItemValue("LEATHER_LEGGINGS", 32);
		settle.getWarehouse().depositItemValue("LEATHER_CHESTPLATE", 32);
		settle.getWarehouse().depositItemValue("LEATHER_HELMET", 32);
		settle.getWarehouse().depositItemValue("STONE_SWORD", 32);
		settle.getWarehouse().depositItemValue("BREAD", 200);
		
		TradeOrder sellOrder = new TradeOrder(0, TradeType.SELL, "LEATHER_BOOTS" , 5, 22, 500, 0, TradeStatus.NONE, "Draskoria", 0, SettleType.CITY);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		sellOrder = new TradeOrder(0, TradeType.SELL, "LEATHER_LEGGINGS" , 5, 22, 500, 0, TradeStatus.NONE, "Draskoria", 0, SettleType.CITY);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		sellOrder = new TradeOrder(0, TradeType.SELL, "LEATHER_CHESTPLATE" , 5, 22, 500, 0, TradeStatus.NONE, "Draskoria", 0, SettleType.CITY);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		sellOrder = new TradeOrder(0, TradeType.SELL, "LEATHER_HELMET" , 5, 22, 500, 0, TradeStatus.NONE, "Draskoria", 0, SettleType.CITY);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		sellOrder = new TradeOrder(0, TradeType.SELL, "STONE_SWORD" , 5, 22, 500, 0, TradeStatus.NONE, "Draskoria", 0, SettleType.CITY);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		
		rModel.getBuildings().getBuilding(766).setMaxTrain(1);
		rModel.getBuildings().getBuilding(54).setMaxTrain(1);
		
		McmdHireSettler hire = new McmdHireSettler(rModel, 4, 2, 1000);
		rModel.getcommandQueue().add(hire);
		
		rModel.getSettlements().getSettlement(4).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(6).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(7).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(8).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(9).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(11).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(15).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(17).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(18).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(19).getWarehouse().depositItemValue("WHEAT", 620);		
		rModel.getSettlements().getSettlement(23).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(25).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(26).getWarehouse().depositItemValue("WHEAT", 320);		
		rModel.getSettlements().getSettlement(27).getWarehouse().depositItemValue("WHEAT", 320);		
		
		for (Building building : rModel.getData().getBuildings().values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500)
			{
				building.setIsEnabled(true);
			}
		}
		
		rModel.getData().getBuildings().getBuilding(801).setMaxTrain(1);
//		rModel.getData().getBuildings().getBuilding(801).setTrainCounter(3);
		String world = "DRASKORIA";
		// Eine Tagessimulation
		simDayCycle(world, rModel);
		simDayCycle(world, rModel);
		simDayCycle(world, rModel);
		simDayCycle(world, rModel);
		simDayCycle(world, rModel);
//		simDayCycle(world, rModel);
//		simDayCycle(world, rModel);
//		simDayCycle(world, rModel);
//		simDayCycle(world, rModel);
		
		
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_PRODUCTION);

		isOutput = true; //(expected != actual); //true;
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

			printProductionView(lehen);
			printLehenNpc(lehen);
			printLehenUnit(lehen);
			printBuildingMsg(lehen);
//			printTradeOrders(rModel, lehen);
//			printProductionView(settle);
//			printBuildingMsg(settle);
//			printLehenUnit(settle);
//			printTradeOrders(rModel, settle);
		}
		assertEquals(expected, actual);
	}


}
