package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeMarketOrder;

import org.junit.Test;

public class TradeManagerTest
{

	private TradeMarket getSettleOrders(int settleId, TradeMarket tradeMarket)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : tradeMarket.values())
		{
			if (tmo.getSettleID() == settleId)
			{
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
			}
		}
		
		return subList;
	}

	
	@Test
	public void test()
	{
		fail("Not yet implemented");
	}

}
