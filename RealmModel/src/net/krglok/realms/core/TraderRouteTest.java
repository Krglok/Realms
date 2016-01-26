package net.krglok.realms.core;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.logging.Logger;

import net.krglok.realms.data.DataStorage;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.tool.LogList;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;

import org.bukkit.block.Biome;
import org.junit.Test;

public class TraderRouteTest {

	private Boolean isOutput = true; // set this to false to suppress println
	LocationData pos = new LocationData("SteamHaven",-469.51,72,-1236.65);
	private Logger logMarket = Logger.getLogger("Market"); 
	private String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
	DataStorage testData = new DataStorage(path);

	
	/**
	 * initialisiert ein Settlement mit Testdaten
	 * 
	 * @param settleName
	 * @return
	 */
	private Settlement createSettlement(String settleName)
	{
		LogList logTest = new LogList(path);
		OwnerList ownerList =  testData.getOwners();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("8","HOME");
		regionTypes.put("9","HOME");
		regionTypes.put("10","HOME");
		regionTypes.put("11","HOME");
		regionTypes.put("12","HOME");
		regionTypes.put("13","HOME");
		regionTypes.put("14","HOME");
		regionTypes.put("15","HOME");
		regionTypes.put("16","HOME");
		regionTypes.put("17","HOME");
		regionTypes.put("18","HOME");
		regionTypes.put("19","HOME");
		regionTypes.put("20","HOME");
		regionTypes.put("60","TAVERNE");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("67","WHEAT");
		regionTypes.put("68","WHEAT");
		regionTypes.put("69","WAREHOUSE");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.HAMLET;
		
		Settlement settle = Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				0,
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
//				logTest
				);

		settle.getWarehouse().depositItemValue("WHEAT",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("BREAD",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("WOOD_HOE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_AXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_PICKAXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("LOG",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("STICK",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("COBBLESTONE",settle.getResident().getSettlerMax());
		
		settle.getResident().setSettlerCount(25);
		settle.setSettlerMax();
		return settle;
	}
	
	

	@Test
	public void testcheckRouteOrder()
	{
		testData.initData();
		System.out.println("========================================");
		System.out.println("testcheckRouteOrder");
		Settlement sender = testData.getSettlements().getSettlement(1);
		Lehen target = testData.getLehen().getLehen(2);
		Settlement second = testData.getSettlements().getSettlement(3);
//		sender.setId(1);
//		target.setId(1);
//		second.setId(2);
		target.getBank().depositKonto(10000.0, "Admin",target.getId());
		sender.getBank().depositKonto(10000.0, "Admin",sender.getId());
		second.getBank().depositKonto(10000.0, "Admin",second.getId());
		
//		SettlementList settlements = new SettlementList(0);
//		settlements.addSettlement(sender);
//		settlements.addSettlement(target);
//		settlements.addSettlement(second);
		sender.setPosition(new LocationData("SteamHaven", -469.51819223615206, 72, -1236.6592548015324));
		target.setPosition(new LocationData("SteamHaven", -121.6704984377348, 103, -1320.300000011921));
		second.setPosition(new LocationData("SteamHaven", 121.6704984377348, 103, -132.300000011921));

		sender.getWarehouse().depositItemValue("WOOD", 1000);
		sender.getWarehouse().depositItemValue("WHEAT", 1000);
		sender.getWarehouse().depositItemValue("LOG", 1000);
		sender.getWarehouse().depositItemValue("BREAD", 100);
		
		second.getWarehouse().depositItemValue("WOOD", 1000);
		second.getWarehouse().depositItemValue("WHEAT", 1000);
		second.getWarehouse().depositItemValue("LOG", 1000);

		TradeTransport tradeTransport = new TradeTransport();
		TradeMarket tradeMarket = new TradeMarket();
		sender.getTrader().getRouteOrders().clear();
		target.getTrader().getRouteOrders().clear();
		int orderId = 1;
		int amount  = 100;
		String itemRef = "WOOD";
		RouteOrder routeOrder = new RouteOrder(1, target.getId(), itemRef, amount, 0.1, true,target.getSettleType());
		sender.getTrader().getRouteOrders().addRouteOrder(routeOrder);
		itemRef = "BREAD";
		amount  = 10;
		RouteOrder routeOrder1 = new RouteOrder(1, target.getId(), itemRef, amount, 1.0, true,target.getSettleType());
		sender.getTrader().getRouteOrders().addRouteOrder(routeOrder1);
		
		sender.getTrader().checkRoutes(tradeMarket, tradeTransport, sender, testData);
		System.out.println("Sender  : "+sender.getSettleType().name()+"|"+itemRef+":"+sender.getWarehouse().getItemList().getValue(itemRef));
		System.out.println("Route   : "+itemRef+":"+amount+" from "+sender.getId()+" >> "+target.getId()+ "|");
		System.out.println("Target  : "+target.getSettleType().name()+"|"+itemRef+":"+target.getWarehouse().getItemList().getValue(itemRef));
		System.out.println("DO : tradeTransport.runTick() :"+1195+" x");
		
		TradeStatus expected = TradeStatus.DECLINE;
		TradeStatus actual = tradeTransport.get(orderId).getStatus();
		isOutput = true;
		if (isOutput)
		{
			System.out.println("Expected: "+expected+" | "+"Actual: "+actual);
			System.out.println("---makeRouteOrder----["+sender.getTrader().getRouteOrders().size()+"] Size");
			System.out.println("---Transport     ----["+tradeTransport.size()+"] Size");

			for (int i = 0; i < 1195; i++)
			{
				tradeTransport.runTick();
			}
			
			for (int i = 0; i < tradeTransport.size(); i++)
			{
				tradeTransport.runTick();
				sender.getTrader().checkRoutes(tradeMarket, tradeTransport, sender, testData);
				System.out.println("| "+"Status:   "+tradeTransport.get(orderId).getStatus()+" | "+tradeTransport.get(orderId).getTickCount()+" : "+tradeTransport.get(orderId).getMaxTicks());
				tradeTransport.fullfillTarget(target);
				tradeTransport.fullfillSender(sender);
				if (tradeTransport.get(orderId).getStatus() == TradeStatus.NONE)
				{
					System.out.println("---makeRouteOrder----["+sender.getTrader().getRouteOrders().size()+"]  Size");
					System.out.println("---Transport     ----["+tradeTransport.size()+"]  Size");
					System.out.println("| "+"Fullfill: "+tradeTransport.get(orderId).getStatus()+" | "+tradeTransport.get(orderId).getTickCount()+" : "+tradeTransport.get(orderId).getMaxTicks()+" Ticks");
					System.out.println("| "+"Sender  : "+sender.getSettleType().name()+"|"+itemRef+":"+sender.getWarehouse().getItemList().getValue(itemRef));
					System.out.println("| "+"Target  : "+target.getSettleType().name()+"|"+itemRef+":"+target.getWarehouse().getItemList().getValue(itemRef));
				}
			}
		}
		System.out.println("========================================");
		System.out.println("Sender Messages");
		for (String s : sender.getMsg()) 
		{
			System.out.println(s);
		}
		System.out.println("========================================");
		System.out.println("Target Messages");
		for (String s : target.getMsg()) 
		{
			System.out.println(s);
		}
		System.out.println("========================================");
		actual = tradeTransport.get(orderId).getStatus();
		assertEquals(expected, actual);
	}

}
