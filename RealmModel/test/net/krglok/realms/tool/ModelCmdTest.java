package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageData;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.model.McmdAddBuilding;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdCreateSettle;
import net.krglok.realms.model.McmdDepositWarehouse;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;

import org.bukkit.block.Biome;
import org.junit.Test;

public class ModelCmdTest 
{

	private Boolean isOutput = false; // set this to false to suppress println
	int steps = 0;

	
	private String getReqList(Settlement settle)
	{
		System.out.println("Building List : "+settle.getBuildingList().getBuildTypeList().size());
		for (BuildPlanType building :settle.getBuildingList().getBuildTypeList().keySet())
		{
				System.out.println(ConfigBasis.setStrleft(building.name(),15)+":"+settle.getBuildingList().getBuildTypeList().get(building));
		}
		System.out.println(" ");
		System.out.println("Warehouse : "+settle.getWarehouse().getItemMax());
		for (String itemRef : settle.getWarehouse().getItemList().keySet())
		{
			System.out.println(ConfigBasis.setStrleft(itemRef,15)+" : "+settle.getWarehouse().getItemList().getValue(itemRef));
		}
		System.out.println("Market Buy Orders");
		for (TradeOrder tmo : settle.getTrader().getBuyOrders().values())
		{
			System.out.println("- for : "+settle.getId()+":"+tmo.ItemRef()+":"+tmo.value()+":"+tmo.getBasePrice()+" :"+tmo.getStatus());
		}
		String reqI = "-";
		for (String itemRef : settle.getRequiredProduction().keySet())
		{
//			Item item = settle.getRequiredProduction().getItem(itemRef);
			reqI = reqI+itemRef+":"+settle.getRequiredProduction().getItem(itemRef).value()+"-";
		}
		System.out.println("=====================================");
	
		return reqI;
		
	}
	
	private void makeSettleAnalysis(Settlement settle, int moth)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Settlement : "+settle.getId()+" : "+settle.getName());
		msg.add("Sieldungstatus  ========================");
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
			msg.add("!  Ihre Siedler sind unglücklich. Das wird sie veranlassen zu verschwinden!");
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
	}
	
	
	private void someLoops(int maxLoop, RealmModel rModel)
	{
		for (int i=0; i < 5; i++)
		{
			for (int j = 0; j < ConfigBasis.GameDay/2; j++)
			{
				rModel.OnTick();
				steps++;
			}
			rModel.OnProduction();
			steps++;
			for (int j = 0; j < ConfigBasis.GameDay/2; j++)
			{
				rModel.OnTick();
				steps++;
			}
		}
		
	}
	
	
	@Test
	public void testLoop() 
	{
		Logger log = Logger.getLogger("Minecraft"); 
		
		MessageData messageData = new MessageData(log);

		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
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
				messageData);

		McmdEnable modelEnable = new McmdEnable(rModel);
		
		Boolean expected = true; 
		Boolean actual = false; 

		rModel.OnCommand(modelEnable);

		steps++;
		someLoops(5, rModel);
		McmdDepositWarehouse deposit = new McmdDepositWarehouse(rModel, 1, "WOOD_AXE", 32);
		rModel.OnCommand(deposit);
		McmdDepositeBank setKonto = new McmdDepositeBank(rModel, 1, 870.0, "Admin");
		rModel.OnCommand(setKonto);
		deposit = new McmdDepositWarehouse(rModel, 1, "WOOD_PICKAXE", 32);
		rModel.OnCommand(deposit);
		someLoops(5, rModel);
		
		
		McmdCreateSettle createSettle = new McmdCreateSettle(rModel, "Erebor", "NPC1", SettleType.SETTLE_HAMLET,Biome.PLAINS);
		rModel.OnCommand(createSettle);
		someLoops(5, rModel);
		
		McmdSellOrder sellOrder = new McmdSellOrder(rModel, 1, "WOOL", 5, 1.0 , 2);
		rModel.OnCommand(sellOrder);
		someLoops(5, rModel);
		

		McmdBuyOrder buyOrder = new McmdBuyOrder(rModel, 2, "WOOD_AXE", 5, 1.0, 2);
		rModel.OnCommand(buyOrder);
		someLoops(25, rModel);
		
		sellOrder = new McmdSellOrder(rModel, 1, "WOOD_AXE", 5, 1.0 , 2);
		rModel.OnCommand(sellOrder);
		someLoops(5, rModel);		
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

		isOutput = true; // (expected != actual); //true;
		if (isOutput)
		{
			System.out.println("LOOP TEST:"+steps);
			System.out.println("OnTick ");
			System.out.println("CommandQueue : "+rModel.getcommandQueue().size());
			System.out.println("ProdQueue    : "+rModel.getProductionQueue().size());
			System.out.println("SellOrders   : "+rModel.getTradeMarket().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			for (Settlement settle : rModel.getSettlements().getSettlements().values())
			{
				makeSettleAnalysis(settle, 1);
				getReqList(settle);
			}
			System.out.println("=====================================");
			System.out.println("Zentral Market Sell Orders");
			for (TradeMarketOrder tmo : rModel.getTradeMarket().values())
			{
				System.out.println("- from: "+tmo.getSettleID()+":"+tmo.ItemRef()+":"+tmo.value()+":"+tmo.getBasePrice()+" :"+tmo.getStatus());
			}
		}
		assertEquals(expected, actual);
	}

}
