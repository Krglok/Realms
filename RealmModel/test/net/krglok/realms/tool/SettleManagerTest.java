package net.krglok.realms.tool;

import java.util.ArrayList;
import java.util.Collections;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.data.LogList;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.MessageTest;
import net.krglok.realms.unittest.ServerTest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.junit.Test;

public class SettleManagerTest
{
	private int dayCounter = 0;
	private int month;
	
	@Test
//	public void testSettleMgr()
//	{
//		ServerTest server = new ServerTest();
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
//		LogList logTest = new LogList(path);
//		DataTest testData = new DataTest(logTest);
//		ConfigTest config = new ConfigTest();
//		MessageTest   msg = new MessageTest();
//		RealmModel rModel = new RealmModel(0, 0, server, config, testData, msg, logTest);
//		// read Settlements form File
////		data.initSettleDate();
//		//start RealmOdel
//    	rModel.OnEnable();
//		
//		SettleManager settleMgr;
////		AdminStatus expected = AdminStatus.NONE;
////		AdminStatus actual = rModel.getSettlements().getSettlement(1).settleManager().getStatus();
//		if (expected != actual)
//		{
//			System.out.println("");
//			System.out.println("testSettleMgr");
//			System.out.println("Anzahl Settlements : "+rModel.getSettlements().getSettlements().size());
//			for (Settlement settle : rModel.getSettlements().getSettlements().values())
//			{
//				settleMgr = settle.settleManager();
//				System.out.print("Settlement     : "+settle.getId()+" : "+settle.getName());
//				System.out.print("  Manager Mode   : "+settleMgr.getAdminMode());
//				System.out.print("  Status : "+settleMgr.getStatus());
//				System.out.println("");
//			}
//		}
//		assertEquals(expected, actual);
//		
//	}
	

	private void makeSettleAnalysis(Settlement settle, ItemPriceList priceList)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Sieldungstatus  ========= "+settle.getBiome());
		msg.add("Age           : "+settle.getAge()+" Tage  ca. " + (settle.getAge()/30/12)+" Jahre ");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount());
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());
		msg.add("!  ");
		msg.add("Bevölkerungsanalyse  ");
		if (settle.getResident().getSettlerCount() > settle.getResident().getSettlerMax())
		{
			msg.add("!  Sie haben Überbevölkerung in der Siedlung. Dies macht die Siedler unglücklich auf lange Sicht!");
		}
		if (settle.getResident().getHappiness() < 0)
		{
			msg.add("!  Ihre Siedler sind unglücklich. ");
		}
		if (settle.getFoodFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler leiden Hunger. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getSettlerFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler haben keinen Wohnraum. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getEntertainFactor() < 0.9)
		{
			msg.add("!  Ihre Siedler haben wenig Unterhaltung. Etwas mehr Unterhltung macht sie glücklicher!");
		}
		if ((settle.getFoodFactor() < 0.0) && (settle.getResident().getSettlerCount() < 8))
		{
			msg.add("!  Ihre Siedler sind verhungert. Sie haben als Verwalter versagt!");
			msg.add("!  Es würde mich nicht wundern, wenn eine Revolte ausbricht!!");
		}

		msg.add("  ");
		msg.add("Wirtschaftsanalyse  ");
		msg.add("!  Ihre Siedler haben "+(int)(settle.getBank().getKonto())+" Thaler erarbeitet.  Herzlichen Glückwunsch.");

		double price = 0.0;
		double balance = 0.0;
		for (Item item : settle.getWarehouse().getItemList().values())
		{
			price = Math.round(priceList.getBasePrice(item.ItemRef()));
			balance = balance + (item.value()*price);
		}
		msg.add("!  Das Warenlager hat einen Wert von:  "+balance);
		
		if (settle.getTownhall().getWorkerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Arbeiter. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Siedler. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() > settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Sie haben "+(settle.getResident().getSettlerCount() -settle.getTownhall().getWorkerNeeded())+" Siedler ohne Arbeit. Sie könnten neue Arbeitsgebäude bauen !");
		}

		if (settle.getRequiredProduction().size() > 0)
		{
			msg.add("!  Es fehlen "+settle.getRequiredProduction().size()+" verschiedene Rohstoffe zur Produktion.");
		}
		
		if ((settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()) < 512)
		{
			msg.add("!  Die Lagerkapazität ist knapp !  Freie Kapazitäte nur "+(settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()));
		}
		
		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
		
		System.out.println("==ProductionOverview ==");
		System.out.print("Item            "+" : "+"   Last"+" | "+"  Monat"+" | "+"   Jahr"+"  Store");
		System.out.println("");
		ArrayList<String> idList = new ArrayList<String>();
		for (BoardItem item : settle.getProductionOverview().values())
		{
			idList.add(item.getName());
		}
		Collections.sort(idList);
		for (String id : idList)
		{

//		for (BoardItem bItem : settle.getProductionOverview().values())
//		{
			BoardItem bItem = settle.getProductionOverview().get(id);
			System.out.print(ConfigBasis.setStrleft(bItem.getName(),16)+" : ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getLastValue()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getCycleSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getPeriodSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemList().getValue(bItem.getName()) ) ,7)+ " | ");
			System.out.print("");
			System.out.println("");
			
		}
	}
	
	private void showMarket(RealmModel rModel)
	{
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (TradeMarketOrder order :rModel.getTradeMarket().values())
		{
			idList.add(order.getId());
		}
		Collections.sort(idList);
		for (Integer id : idList)
		{
			TradeMarketOrder order = rModel.getTradeMarket().get(id);
			System.out.print(""+ConfigBasis.setStrright(String.valueOf(order.getId()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getSettleID()),2));
			System.out.print("|"+ConfigBasis.setStrleft(order.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getBasePrice()),6));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getTickCount()),6));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getMaxTicks()),6));
			System.out.print("|"+ConfigBasis.setStrleft(order.getStatus().name(),12)+"");
			System.out.println("|");
		}

	}

	private void showTransport(RealmModel rModel)
	{
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (TradeMarketOrder order :rModel.getTradeTransport().values())
		{
			idList.add(order.getId());
		}
		Collections.sort(idList);
		for (Integer id : idList)
		{
			TradeMarketOrder order = rModel.getTradeTransport().get(id);
			System.out.print(""+ConfigBasis.setStrright(String.valueOf(order.getId()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getSettleID()),2));
			System.out.print(">>"+ConfigBasis.setStrright(String.valueOf(order.getTargetId()),2));
			System.out.print("|"+ConfigBasis.setStrleft(order.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getBasePrice()),6));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getTickCount()),6));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getMaxTicks()),6));
			System.out.print("|"+ConfigBasis.setStrleft(order.getStatus().name(),12)+"");
			System.out.println("|");
		}

	}
	
	private void showStock(RealmModel rModel, Settlement settle)
	{
		ArrayList<String> idList = new ArrayList<String>();
		for (Item item : settle.getWarehouse().getItemList().values())
		{
			idList.add(item.ItemRef());
		}
		Collections.sort(idList);
		for (String id : idList)
		{
			Item item = settle.getWarehouse().getItemList().get(id);
			System.out.print("|"+ConfigBasis.setStrleft(item.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(item.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(rModel.getServer().getBioneFactor( settle.getBiome(), Material.getMaterial(item.ItemRef()))),5));
			System.out.print("|"+ConfigBasis.setStrright(
					String.valueOf(
							64 - 
							(64 * rModel.getServer().getBioneFactor
									( settle.getBiome(), Material.getMaterial
											(item.ItemRef())
											)/ 100)),5));
			System.out.println("|");
		}

	}
	
	private void showBuyList(Settlement settle)
	{
//		ArrayList<String> idList = new ArrayList<String>();
//		for (Item item : settle.getTrader().getBuyOrders())
//		{
//			idList.add(item.ItemRef());
//		}
//		Collections.sort(idList);
		for (TradeOrder order :  settle.getTrader().getBuyOrders().values())
		{
			System.out.print("|"+ConfigBasis.setStrleft(String.valueOf(order.getId()),3)+"");
			System.out.print("|"+ConfigBasis.setStrleft(order.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getBasePrice()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getTickCount()),5));
			System.out.print("|"+ConfigBasis.setStrleft(order.getStatus().name(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getMaxTicks()),5));
			System.out.println("|");
		}

		for (Item item : settle.settleManager().getBuyList().values())
		{
			System.out.print("|"+ConfigBasis.setStrleft(item.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(item.value()),5));
			System.out.println("|");
			
		}
		System.out.println("|");
	}

	
	private void showBuyCmd(Settlement settle)
	{
		for (McmdBuyOrder order :  settle.settleManager().getCmdBuy())
		{
			System.out.print("|"+ConfigBasis.setStrleft(order.getItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getAmount()),5));
			System.out.println("|");
		}

	}

	private void doDayLoop(RealmModel rModel)
	{
		for (int i = 0; i < 100; i++)
		{
			rModel.OnTick();
			
		}
		rModel.OnProduction("SteamHaven");
		dayCounter++;
		if ((dayCounter % 30) == 0)
		{
			rModel.OnTax();
			System.out.println(":");
		}
		
	}

	private void doLoop(RealmModel rModel, int maxDays)
	{
		for (int i = 0; i <= maxDays; i++)
		{
			doDayLoop( rModel);
			System.out.print("=");
		}
	}
	
	@Test
	public void testSettleMgrModel()
	{
		ServerTest server = new ServerTest();
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest(logTest);
		ConfigTest config = new ConfigTest();
		MessageTest   msg = new MessageTest();
		
		RealmModel rModel = new RealmModel(0, 0, server, config, testData, msg, logTest);
    	rModel.OnEnable();

		int settleId = 1;

		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		double expected = settle.getBank().getKonto();
		double amount = 1000;
		String userName = "TestUser";
//		McmdDepositeBank bankCommand = new McmdDepositeBank(rModel, settleId, amount , userName );
		
		BuildPlanType bType = BuildPlanType.HOME;
		LocationData position = new LocationData("SteamHaven", 0.0, 0.0, 0.0);
		Player player = null;
		McmdBuilder builderCommand    = new McmdBuilder(rModel, settleId, bType, position, player);

		String itemRef = "WHEAT";
		int value = 500;
		double price = testData.getPriceList().getBasePrice(itemRef);
		int delayDays = 10;
//		McmdSellOrder sellCommand = new McmdSellOrder(rModel, settleId, itemRef, value, price, delayDays);

		itemRef = "LOG";
		value = 500;
		price = testData.getPriceList().getBasePrice(itemRef);
		delayDays = 10;
//		McmdBuyOrder buyCommand = new McmdBuyOrder(rModel, settleId, itemRef, value, price, delayDays);

		itemRef = "WOOL";
		value = 500;
		price = testData.getPriceList().getBasePrice(itemRef);
		delayDays = 10;
//		McmdSellOrder sellNext = new McmdSellOrder(rModel, settleId, itemRef, value, price, delayDays);
		
		String name = "NewColonist";
//		LocationData centerPos = new LocationData("SteamHaven", 0.0, 0.0, 0.0);
		String owner = "NPC1";
		
//		McmdColonistCreate colonistCommand = new McmdColonistCreate(rModel, name, centerPos, owner);
		
//		doLoop(rModel, 5);
//		rModel.OnCommand(bankCommand);
//		doLoop(rModel, 5);
		rModel.OnCommand(builderCommand);
		doLoop(rModel, 1);
//		rModel.OnCommand(buyCommand);
//		doLoop(rModel, 5);
//		rModel.OnCommand(colonistCommand);
//		doLoop(rModel, 35);

		
		System.out.println("");
		System.out.println("testSettleMgrModel");
		System.out.println("Settlement     : "+settle.getId()+" : "+settle.getName());
		System.out.println("Bank       : "+ConfigBasis.format2(settle.getBank().getKonto())+"/"+expected);
		if (settle.buildManager().getActualBuild() != null)
		{
			System.out.println("Builder    :"+settle.buildManager().getActualBuild().getBuildingType());
		} else
		{
			System.out.println("Builder    :"+"NONE");
		}
		System.out.println("MgrBuyList :"+settle.settleManager().getCmdBuy().size());
		System.out.println("MgrSellList:"+settle.settleManager().getCmdSell().size());
		System.out.println("ModelCmd   :"+rModel.getcommandQueue().size());
		System.out.println("Market SellOrders :"+rModel.getTradeMarket().size());
		showMarket(rModel);
		System.out.println("Market Transports :"+rModel.getTradeTransport().size());
		showTransport(rModel);
		for (Settlement settl : rModel.getSettlements().values())
		{			
			System.out.println("");
			System.out.println("Settlement     : "+settl.getId()+" : "+settl.getName());
			System.out.println("Caravans  :"+settl.getTrader().getCaravanCount());
			System.out.println("BuyOrders :"+settl.getTrader().getBuyOrders().size());
			showBuyList(settl);
		}
		makeSettleAnalysis( settle, rModel.getData().getPriceList());
		System.out.println("Warehouse :"+settle.getWarehouse().getItemList().size());
		showStock(rModel, settle);
		System.out.println("BuyCmd :"+settle.getTrader().getBuyOrders().size());
		showBuyCmd(settle);
		System.out.println("");
		
	}

}
