package net.krglok.realms.core;

import java.util.HashMap;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import multitallented.redcastlemedia.bukkit.herostronghold.checkregiontask.CheckPlayerInRegionThread;

/**
 * Hier werden zentral die Angebote der Trader gespeichert und verwaltet.
 * Die Instanz ist zentral angeordnet und wird an die entsprechenden Nutzer uebergeben.
 * Es wird von den Nutzern verändert und verwaltet.
 * 
 * @author oduda
 *
 */
public class TradeMarket extends HashMap<Integer,TradeMarketOrder>
{
	
	private static final long serialVersionUID = 1L;

	private static int lastNumber;


	public TradeMarket()
	{
		super();
		lastNumber = 0;
	}

	public static int getLastNumber()
	{
		return TradeMarket.lastNumber;
	}

	public static int nextLastNumber()
	{
		lastNumber++;
		return lastNumber;
	}

	public static void setLastNumber(int lastNumber)
	{
		TradeMarket.lastNumber = lastNumber;
	}

	
	public void runTick()
	{
		for (TradeMarketOrder to : this.values())
		{
			to.runTick();
		}
	}
	
	public void checkLastNumber()
	{
		int max = 0;
		for (TradeMarketOrder to : this.values())
		{
			if (max < to.getId())
			{
				max = to.getId();
			}
		}
	}

	public void removeOrder(int orderId)
	{
		this.remove(orderId);
	}
	
	public void cancelOrder(int orderId)
	{
		this.get(orderId).setStatus(TradeStatus.READY);
	}
	
	
	private boolean checkPrice(TradeMarketOrder sellOrder, TradeMarketOrder buyOrder)
	{
		if (sellOrder.getBasePrice() <= buyOrder.getBasePrice())
		{
			return true;
		}
		
		return false;
	}
	
	public void setOrderFullfill(TradeMarketOrder sellOrder)
	{
		sellOrder.setStatus(TradeStatus.FULFILL);
	}
	
	public TradeMarket find (TradeMarketOrder buyOrder)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder sellOrder : this.values())
		{
			if ((sellOrder.getSettleID() != buyOrder.getSettleID())
				&& (sellOrder.getStatus() == TradeStatus.STARTED)
				&& (sellOrder.getWorld().equalsIgnoreCase(buyOrder.getWorld()))
				)
			{
				if (sellOrder.ItemRef().equalsIgnoreCase(buyOrder.ItemRef()))
				{
					if (checkPrice(sellOrder, buyOrder))
					{
						TradeMarket.lastNumber++;
						subList.put(TradeMarket.lastNumber++,sellOrder);
					}
				}
			}
		}
		return subList;
	}
	
	
	public TradeMarketOrder putOrder(TradeMarketOrder tmo)
	{
		if (this.containsKey(tmo.getId()))
		{
			this.put(tmo.getId(), tmo);
			return tmo;
		}

		return addOrder(tmo);
	}
	
	public TradeMarketOrder getOrder(int orderId)
	{
		return this.get(orderId);
	}
	
	public TradeMarketOrder addOrder(TradeMarketOrder tmo)
	{
		lastNumber++;
		tmo.setId(lastNumber);
		this.put(lastNumber, tmo);
		return tmo;
	}
	
	public TradeMarket getSettleOrders(int settleId)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (tmo.getSettleID() == settleId)
			{
				TradeMarket.lastNumber++;
				subList.put(TradeMarket.lastNumber++,tmo);
			}
		}
		
		return subList;
	}
	
	public TradeMarket getWorldOrders(String world)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (tmo.getWorld().equalsIgnoreCase(world))
			{
				TradeMarket.lastNumber++;
				subList.put(TradeMarket.lastNumber++,tmo);
			}
		}
		
		return subList;
	}

	public TradeMarket getOtherWorldOrders(String world)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (!tmo.getWorld().equalsIgnoreCase(world))
			{
				TradeMarket.lastNumber++;
				subList.put(TradeMarket.lastNumber++,tmo);
			}
		}
		
		return subList;
	}
	
	
}
