package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
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
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;

import org.bukkit.block.Biome;
import org.junit.Test;

public class TraderTest
{

	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	@Test
	public void testTrader()
	{
		Trader trader = new Trader();
//		TradeOrder sellOrder = new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(0, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
		trader.getBuyOrders().put(1, buyOrder);
		
		int expected = 1;
		int actual = trader.getBuyOrders().size();
		assertEquals(expected, actual);
		
	}

	
	private Settlement createSettlement()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("3","haus_einfach");
		regionTypes.put("4","haus_einfach");
		regionTypes.put("5","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("8","haus_einfach");
		regionTypes.put("9","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("13","haus_einfach");
		regionTypes.put("14","haus_einfach");
		regionTypes.put("15","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("18","haus_einfach");
		regionTypes.put("19","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("60","taverne");
		regionTypes.put("65","kornfeld");
		regionTypes.put("66","kornfeld");
		regionTypes.put("67","kornfeld");
		regionTypes.put("68","kornfeld");
		regionTypes.put("69","markt");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
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
//		ServerTest server = new ServerTest();
		Settlement sender = createSettlement();
		Settlement target = createSettlement();
		sender.setId(0);
		target.setId(1);
		target.getBank().depositKonto(10000.0, "Admin");
//		target.doProduce(server);
//		sender.doProduce(server);
		sender.getBank().depositKonto(10000.0, "Admin");
		
		SettlementList setteList = new SettlementList(0);
		setteList.addSettlement(sender);
		setteList.addSettlement(target);
		sender.setPosition(new LocationData("SteamHaven", -469.51819223615206, 72, -1236.6592548015324));
		target.setPosition(new LocationData("SteamHaven", -121.6704984377348, 103, -1320.300000011921));
		sender.getWarehouse().depositItemValue("WOOD", 1000);
		
		
		TradeOrder sellOrder = new TradeOrder(sender.getId(), TradeType.SELL, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
//		TradeOrder buyOrder = new TradeOrder(target.getId(), TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
//		target.getTrader().getBuyOrders().put(1, new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0.3 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
//		target.getTrader().getBuyOrders().put(2, new TradeOrder(2, TradeType.BUY, "WOOD", 64 , 0.4 , ConfigBasis.GameDay, 0L, TradeStatus.DECLINE, "",0));
		target.getTrader().getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.STARTED, "",0));

//		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 52);
//		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 120);
//		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 120);
		
		TradeTransport tpo = new TradeTransport();
		TradeMarket tm = new TradeMarket();
		System.out.println("Sender  : "+sender.getId());
		System.out.println("Target  : "+target.getId());
		System.out.println("Sender Bank : "+sender.getBank().getKonto());
		System.out.println("Target Bank : "+target.getBank().getKonto());
		System.out.println("Distance    : "+ (int)sender.getPosition().distance(setteList.getSettlement(1).getPosition()));
		System.out.println("Delay(ticks): "+ target.getTrader().getTransportDelay(sender.getPosition().distance((setteList.getSettlement(1).getPosition()))));
//		System.out.println((settlements.getSettlement(tmo.getSettleID()).getPosition());
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
		
		assertEquals(expected, actual);
	}
	
}
