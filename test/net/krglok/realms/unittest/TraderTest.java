package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.core.Trader;
import net.krglok.realms.tool.LogList;

import org.bukkit.block.Biome;
import org.junit.Test;

public class TraderTest
{

	private Boolean isOutput = true; // set this to false to suppress println
	LocationData pos = new LocationData("SteamHaven",-469.51,72,-1236.65);
	private Logger logMarket = Logger.getLogger("Market"); 
	private String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
	DataTest testData = new DataTest();

	@Test
	public void testTrader() throws SecurityException, IOException
	{
		System.out.println("========================================");
		System.out.println("testTrader");
		Trader trader = new Trader();
//		TradeOrder sellOrder = new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(0, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "WORLD",0, SettleType.NONE);
		String msg = "0,BUY, WHEAT, 64 , 0 , 1, 0L, NONE,WORLD,0";
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		Handler handler = new FileHandler(path+"\\trader.log");
		logMarket.addHandler(handler );
		logMarket.info(msg);
		trader.getBuyOrders().put(1, buyOrder);
		
		int expected = 1;
		int actual = trader.getBuyOrders().size();
		
		assertEquals(expected, actual);
		System.out.println("========================================");
		
	}
	
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
	public void testcheckSellOrder()
	{
		System.out.println("========================================");
		System.out.println("testcheckSellOrder");
//		ServerTest server = new ServerTest();
		Settlement sender = createSettlement("Sender");
		Settlement target = createSettlement("Target");
		sender.setId(0);
		target.setId(1);
		target.getBank().depositKonto(10000.0, "Admin",target.getId());
//		target.doProduce(server);
//		sender.doProduce(server);
		sender.getBank().depositKonto(10000.0, "Admin",sender.getId());
		
		SettlementList setteList = new SettlementList(0);
		setteList.addSettlement(sender);
		setteList.addSettlement(target);
		sender.setPosition(new LocationData("SteamHaven", -469.51819223615206, 72, -1236.6592548015324));
		target.setPosition(new LocationData("SteamHaven", -121.6704984377348, 103, -1320.300000011921));
		sender.getWarehouse().depositItemValue("WOOD", 1000);
		
		
		TradeOrder sellOrder = new TradeOrder(1, TradeType.SELL, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
//		TradeOrder buyOrder = new TradeOrder(target.getId(), TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
//		target.getTrader().getBuyOrders().put(1, new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0.3 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
//		target.getTrader().getBuyOrders().put(2, new TradeOrder(2, TradeType.BUY, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.DECLINE, "",0));
		target.getTrader().getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.STARTED, "",0,SettleType.NONE));

//		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 52);
//		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 120);
//		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 120);
		
		TradeTransport tpo = new TradeTransport();
		TradeMarket tm = new TradeMarket();
		if (isOutput)
		{
			System.out.println("Sender  : "+sender.getId()+" : "+sender.getName());
			System.out.println("Target  : "+target.getId()+" : "+target.getName());
			System.out.println("Sender Bank : "+sender.getBank().getKonto());
			System.out.println("Target Bank : "+target.getBank().getKonto());
			System.out.println("Distance    : "+ (int)sender.getPosition().distance(setteList.getSettlement(1).getPosition()));
			target.getTrader();
			System.out.println("Delay(ticks): "+ Trader.getTransportDelay(sender.getPosition().distance((setteList.getSettlement(1).getPosition()))));
	//		System.out.println((settlements.getSettlement(tmo.getSettleID()).getPosition());
		}
		sender.getTrader().makeSellOrder(tm, sender, sellOrder);

		TradeStatus expected = TradeStatus.NONE;
		TradeStatus actual = TradeStatus.NONE;
		
		
		target.getTrader().checkMarket(tm, tpo, target, setteList);

		for (int i = 0; i < 1205; i++)
		{
//			System.out.println("Tick");
			tpo.runTick();
			tm.runTick();
			tpo.fullfillSender(sender);
			tpo.fullfillTarget(sender);
			tpo.fullfillSender(target);
			tpo.fullfillTarget(target);
		}
		tm.runTick();
		if (expected != actual)
		{
			System.out.println("---makeSellOrder---------");
			for (TradeMarketOrder tmo : tm.values())
			{
				System.out.println(tmo.getId() +"|"+tmo.getStatus()+"|"+tmo.ItemRef()+": "+tmo.value()+" : "+tmo.getBasePrice()+" -:"+tmo.getTickCount()+"/"+tmo.getMaxTicks());
			}
			System.out.println("---Orders in BuyList ---");
			for (TradeOrder to : target.getTrader().getBuyOrders().values())
			{
				System.out.println(to.getId() +"|"+to.getStatus()+"|"+to.ItemRef()+": "+to.value()+" : "+to.getBasePrice()+" -:"+to.getTickCount()+"/"+to.getMaxTicks());
			}
			System.out.println("---Orders in TransportList ---");
			for (TradeOrder to : tpo.values())
			{
				System.out.println(to.getId() +"|"+to.getStatus()+"|"+to.ItemRef()+": "+to.value()+" : "+to.getBasePrice()+" -:"+to.getTickCount()+"/"+to.getMaxTicks()+"|"+to.ItemRef()+": "+to.getTargetId());
			}
			System.out.println("-------------------------------");
			System.out.println("Sender Bank : "+sender.getBank().getKonto());
			for (Item item : sender.getWarehouse().getItemList().values())
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
			System.out.println("-------------------------------");
			System.out.println("Target Bank : "+target.getBank().getKonto());
			for (Item item : target.getWarehouse().getItemList().values())
			{
				System.out.println(item.ItemRef()+":"+item.value());
			}
		}
		System.out.println("========================================");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testcheckDistanceOrder()
	{
		System.out.println("========================================");
		System.out.println("testcheckDistanceOrder");
		Settlement sender = createSettlement("Sender");
		Settlement target = createSettlement("Target");
		Settlement second = createSettlement("Second");
		sender.setId(0);
		target.setId(1);
		second.setId(2);
		target.getBank().depositKonto(10000.0, "Admin",target.getId());
		sender.getBank().depositKonto(10000.0, "Admin",sender.getId());
		second.getBank().depositKonto(10000.0, "Admin",second.getId());
		
		SettlementList setteList = new SettlementList(0);
		setteList.addSettlement(sender);
		setteList.addSettlement(target);
		setteList.addSettlement(second);
		sender.setPosition(new LocationData("SteamHaven", -469.51819223615206, 72, -1236.6592548015324));
		target.setPosition(new LocationData("SteamHaven", -121.6704984377348, 103, -1320.300000011921));
		second.setPosition(new LocationData("SteamHaven", 121.6704984377348, 103, -132.300000011921));

		sender.getWarehouse().depositItemValue("WOOD", 1000);
		sender.getWarehouse().depositItemValue("WHEAT", 1000);
		sender.getWarehouse().depositItemValue("LOG", 1000);
		
		second.getWarehouse().depositItemValue("WOOD", 1000);
		second.getWarehouse().depositItemValue("WHEAT", 1000);
		second.getWarehouse().depositItemValue("LOG", 1000);
		
		TradeTransport tpo = new TradeTransport();
		TradeMarket tm = new TradeMarket();
		int orderId = -1;
		TradeOrder sellOrder = new TradeOrder(orderId, TradeType.SELL, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		sender.getTrader().makeSellOrder(tm, sender, sellOrder);
		TradeOrder sellOrder1 = new TradeOrder(orderId, TradeType.SELL, "WHEAT", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		sender.getTrader().makeSellOrder(tm, sender, sellOrder1);
		TradeOrder sellOrder2 = new TradeOrder(orderId, TradeType.SELL, "LOG", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		sender.getTrader().makeSellOrder(tm, sender, sellOrder2);

		TradeOrder sellOrder4 = new TradeOrder(orderId, TradeType.SELL, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		second.getTrader().makeSellOrder(tm, second, sellOrder4);
		TradeOrder sellOrder5 = new TradeOrder(orderId, TradeType.SELL, "WHEAT", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		second.getTrader().makeSellOrder(tm, second, sellOrder5);
		TradeOrder sellOrder6 = new TradeOrder(orderId, TradeType.SELL, "LOG", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0, SettleType.NONE);
		second.getTrader().makeSellOrder(tm, second, sellOrder6);

//		target.getTrader().getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.STARTED, "",0));

		TradeStatus expected = TradeStatus.NONE;
		TradeStatus actual = TradeStatus.DECLINE;
		
		
		if (expected != actual)
		{
			System.out.println("---makeDistantOrder----["+tm.size()+"]");
			for (int ref : tm.keySet())
			{
				TradeMarketOrder tmo = tm.get(ref);
				System.out.println(ref+"|"+tmo.getSettleID() +"|"+tmo.getStatus()+"|"+tmo.ItemRef()+": "+tmo.value()+" : "+tmo.getBasePrice()+" -:"+tmo.getTickCount()+"/"+tmo.getMaxTicks());
			}

			TradeMarket distTm = tm.getDistantOrders(setteList, target.getId());

		
			System.out.println("---makeDistantOrder----["+distTm.size()+"]");
			for (String sRef : distTm.sortItems())
			{
				int ref = Integer.valueOf(sRef);
				TradeMarketOrder tmo = distTm.get(ref);
				System.out.println(ref+"|"+tmo.getSettleID() +"|"+tmo.getStatus()+"|"+tmo.ItemRef()+": "+tmo.value()+" : "+tmo.getBasePrice()+" -:"+tmo.getTickCount()+"/"+tmo.getMaxTicks());
			}
		
			
		}
		System.out.println("========================================");
		assertEquals(expected, actual);
	}


}
