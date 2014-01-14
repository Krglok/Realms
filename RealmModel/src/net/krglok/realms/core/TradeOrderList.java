package net.krglok.realms.core;

import java.util.HashMap;

/**
 * 
 * @author Windu
 *
 */
public class TradeOrderList extends HashMap<Integer,TradeOrder>
{

	private int lastNumber;
	
	private static final long serialVersionUID = 1L;

	public TradeOrderList()
	{
		setLastNumber(0);
	}
	
	public void runTick()
	{
		for (TradeOrder to : this.values())
		{
			to.runTick();
		}
	}

	public void checkLastNumber()
	{
		int max = 0;
		for (TradeOrder to : this.values())
		{
			if (max < to.getId())
			{
				max = to.getId();
			}
		}
	}
	
	public int getLastNumber()
	{
		return lastNumber;
	}

	public void setLastNumber(int lastNumber)
	{
		this.lastNumber = lastNumber;
	}
	
	public TradeOrder addTradeOrder(TradeOrder tradeOrder)
	{
		if (tradeOrder.getId() <= lastNumber)
		{
			lastNumber++;
			tradeOrder.setId(lastNumber);
		}
		this.put(tradeOrder.getId(), tradeOrder);
		return tradeOrder;
	}
	
	public TradeOrder addTradeOrder(TradeType tradeType, 
			String itemRef , int value, double price, 
			long maxTicks, long tickCount,
			TradeStatus status, String world, int targetId)
	{
		int id = lastNumber++;
		TradeOrder to = new TradeOrder(id, tradeType, 
				itemRef , value, price, 
				maxTicks, tickCount,
				status, world, targetId);
		this.put(id, to);
		return to;
	}
	
}
