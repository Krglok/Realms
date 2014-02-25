package net.krglok.realms.core;

import java.util.HashMap;

/**
 * <pre>
 * hold a list of trade orders
 * will be used from traders in settlement
 * </pre>
 * @author Windu
 *
 */
public class TradeOrderList extends HashMap<Integer,TradeOrder>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4902227609327886827L;
	
	private int lastNumber;
	

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
		tradeOrder.setStatus(TradeStatus.STARTED);
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
