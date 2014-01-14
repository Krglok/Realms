package net.krglok.realms.core;

import static org.junit.Assert.*;

import org.junit.Test;

import sun.awt.geom.AreaOp.AddOp;

public class TraderTest
{

	@Test
	public void testTrader()
	{
		Trader trader = new Trader();
		TradeOrder sellOrder = new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(0, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
		trader.getBuyOrders().put(1, buyOrder);
		
		int expected = 1;
		int actual = trader.getBuyOrders().size();
		assertEquals(expected, actual);
		
	}

	@Test
	public void testcheckBuyOrder()
	{
		Trader trader = new Trader();
		TradeOrder sellOrder = new TradeOrder(2, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
		trader.getBuyOrders().put(1, new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0.3 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(2, new TradeOrder(2, TradeType.BUY, "WOOD", 64 , 0.6 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		
		TradeMarketOrder mOrder = new TradeMarketOrder(1,new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));

		TradeMarket tm = new TradeMarket();
		tm.addOrder(mOrder);
		
		String itemRef = "WOOD";
		int offerValue = 64;
		double offerPrice = 0.4;
		trader.checkMarket(tm); // checkBuyOrder(itemRef, offerValue, offerPrice); 
		
		int expected = 1;
		int actual = 0;
		actual = trader.getTransportOrders().size();
		if (expected != actual)
		{
			System.out.println("---checkBuyOrder-----");
			System.out.println(itemRef+": "+offerValue+" : "+offerPrice);
			System.out.println("---Orders to Transport ---");
			for (TradeOrder to : trader.getTransportOrders().values())
			{
				System.out.println(to.ItemRef()+": "+to.value()+" : "+to.getBasePrice()+" -:"+to.getMaxTicks());
			}
		}
		
		assertEquals(expected, actual);
		
	}

	
	@Test
	public void testTransportBuyOrder()
	{
		Trader trader = new Trader();
		TradeOrder sellOrder = new TradeOrder(2, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
		trader.getBuyOrders().put(1, new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0.3 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(2, new TradeOrder(2, TradeType.BUY, "WOOD", 64 , 0.6 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		
		TradeMarketOrder mOrder = new TradeMarketOrder(1,new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));

		TradeMarket tm = new TradeMarket();
		tm.addOrder(mOrder);
		String itemRef = "WOOD";
		int offerValue = 64;
		double offerPrice = 0.4;
		trader.checkMarket(tm); // checkBuyOrder(itemRef, offerValue, offerPrice);
		for (int i = 0; i < 1200; i++)
		{
			trader.runTick();
		}
		TradeStatus expected = TradeStatus.DECLINE;
		TradeStatus actual = trader.getTransportOrders().get(0).getStatus();
		if (expected != actual)
		{
			System.out.println("---Orders to Transport ---");
			for (TradeOrder to : trader.getTransportOrders().values())
			{
				System.out.println(to.getStatus()+"|"+to.ItemRef()+": "+to.value()+" : "+to.getBasePrice()+" -:"+to.getTickCount()+"/"+to.getMaxTicks());
			}
		}
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testcheckSellOrder()
	{
		Trader trader = new Trader();
		TradeOrder sellOrder = new TradeOrder(2, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		TradeOrder buyOrder = new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0);
		
		trader.getBuyOrders().put(1, new TradeOrder(1, TradeType.BUY, "WHEAT", 64 , 0.3 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(2, new TradeOrder(2, TradeType.BUY, "WOOD", 64 , 0.6 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		trader.getBuyOrders().put(3, new TradeOrder(3, TradeType.BUY, "WOOD", 64 , 0.5 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		
		TradeMarket tm = new TradeMarket();
		TradeMarketOrder mOrder = new TradeMarketOrder(1,new TradeOrder(0, TradeType.SELL, "WOOD", 64 , 0.2 , ConfigBasis.GameDay, 0L, TradeStatus.NONE, "",0));
		tm.addOrder(mOrder);
		mOrder = new TradeMarketOrder(2,sellOrder);
		tm.addOrder(mOrder);
		int settleId = 10;
		trader.makeSellOrder(tm, settleId, sellOrder);

//		trader.checkMarket(tm); 
		for (int i = 0; i < 1200; i++)
		{
			trader.runTick();
		}
		TradeStatus expected = TradeStatus.FULFILL;
		TradeStatus actual = TradeStatus.NONE;
		TradeMarket foundSell = tm.getSettleOrders(settleId);
		if (foundSell != null)
		{
			actual = foundSell.get(1).getStatus();
		}
		if (expected != actual)
		{
			System.out.println("---makeSellOrder---------");
			for (TradeMarketOrder tmol : tm.values())
			{
				System.out.println(tmol.getId() +"|"+tmol.getStatus()+"|"+tmol.ItemRef()+": "+tmol.value()+" : "+tmol.getBasePrice()+" -:"+tmol.getTickCount()+"/"+tmol.getMaxTicks());
			}
			System.out.println("---Orders in SellList ---");
			for (TradeMarketOrder to : foundSell.values())
			{
				System.out.println(to.getId() +"|"+to.getStatus()+"|"+to.ItemRef()+": "+to.value()+" : "+to.getBasePrice()+" -:"+to.getTickCount()+"/"+to.getMaxTicks());
			}
		}
		
		assertEquals(expected, actual);
	}
	
}
