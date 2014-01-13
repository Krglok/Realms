package net.krglok.realms.core;

import java.util.HashMap;

/**
 * Der Rrder ist kein Gebaeude. Das entsprechnde Gebaeude ist unter buildungs zu finden.
 * Der Trader wickelt die Handelsaktionen ab.
 * Hierzu benutzt er Karawanen. Jede Karawane hat einen Esel zum Transport der Warenkiste
 * Jeder Trader hat 5 Handelskisten. Fuer jede Handelskiste kann eine Handelstransaktion ausgeführt werden . 
 * Dies begrenzt auch gleichzeitig den Umfang der Transaktion (max 1762 Items). 
 * Die tatsaechliche Anzahl der Items ist von der Stacksize abhaengig. 
 * tradeOrders , sind die Transporte mit den Karawanen
 * buyOrders , sind die Einkaufsaktionen des Settlement
 * sellOrders, sind die Verkaufsaktionen des Settlements
 *
 * @author Windu
 *
 */

public class Trader
{
	private int caravanMax;
	private int caravanCount;
	private TradeOrderList tradeOrders;
	private int buildingId;
	private boolean isEnabled;
	private boolean isActive;
	private TradeOrderList buyOrder;
	private TradeOrderList sellOrder;
	private int OrderMax;
	
	
	
	public Trader()
	{
		caravanMax = 5;
		caravanCount = 0;
		tradeOrders = new TradeOrderList();
		buildingId = 0;
		isEnabled = false;
		isActive  = false;
		OrderMax = 5;
		buyOrder = new TradeOrderList();
		sellOrder = new TradeOrderList();
		
	}



	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isEnabled, boolean isActive)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.tradeOrders = tradeOrders;
		this.buildingId = buildingId;
		this.isEnabled = isEnabled;
		this.isActive = isActive;
		OrderMax = 5;
		buyOrder = new TradeOrderList();
		sellOrder = new TradeOrderList();
	}

	


	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isActive, TradeOrderList buyOrder,
			TradeOrderList sellOrder, int orderMax)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.tradeOrders = tradeOrders;
		this.buildingId = buildingId;
		this.isActive = isActive;
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		OrderMax = orderMax;
	}



	public int getCaravanMax()
	{
		return caravanMax;
	}



	public void setCaravanMax(int caravanMax)
	{
		this.caravanMax = caravanMax;
	}



	public int getCaravanCount()
	{
		return caravanCount;
	}



	public void setCaravanCount(int caravanCount)
	{
		this.caravanCount = caravanCount;
	}



	public TradeOrderList getTradeTasks()
	{
		return tradeOrders;
	}



	public void setTradeTasks(TradeOrderList tradeTasks)
	{
		this.tradeOrders = tradeTasks;
	}



	public int getBuildingId()
	{
		return buildingId;
	}



	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
	}



	public boolean isEnabled()
	{
		return isEnabled;
	}



	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}



	public boolean isActive()
	{
		return isActive;
	}



	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}



	public TradeOrderList getBuyOrder()
	{
		return buyOrder;
	}



	public void setBuyOrder(TradeOrderList buyOrder)
	{
		this.buyOrder = buyOrder;
	}



	public HashMap<Integer, TradeOrder> getSellOrder()
	{
		return sellOrder;
	}



	public void setSellOrder(TradeOrderList sellOrder)
	{
		this.sellOrder = sellOrder;
	}



	public int getOrderMax()
	{
		return OrderMax;
	}


	public void setOrderMax(int orderMax)
	{
		OrderMax = orderMax;
	}
	
	/**
	 * berechne Menge und buche sie vom MarketOrder ab
	 * verschiebe buyOrder in tradeOrder 
	 * @param tmo
	 * @param foundOrder
	 */
	public void makeTradeOrder(TradeMarketOrder tmo, TradeOrder foundOrder )
	{
		int amount = 0;
		if (tmo.value() > foundOrder.value())
		{
			amount = foundOrder.value();
			tmo.setValue(tmo.value() - amount);
		}
	}
	
	public TradeOrder checkBuyOrder(String itemRef, int offerValue, double offerPrice)
	{
		for (TradeOrder to : buyOrder.values())
		{
			if (to.ItemRef().equalsIgnoreCase(itemRef))
			{
				if (offerPrice <= to.getBasePrice())
				{
					return to;
				}
			}
		}
		return null;
	}
	
	public void checkMarket(TradeMarket tradeMarket)
	{
		TradeOrder foundOrder = null;
		if (tradeMarket.isEmpty())
		{
			return;
		}
		for (TradeMarketOrder tmo : tradeMarket.values())
		{
			foundOrder = checkBuyOrder(tmo.ItemRef(), tmo.value(), tmo.getBasePrice());
			if ( foundOrder != null)
			{
				if (caravanCount < caravanMax)
				{
					makeTradeOrder(tmo, foundOrder );
					if (tmo.value() == 0)
					{
						tradeMarket.remove(tmo.getId());
					}
				} else
				{
					return;
				}
			}
		}
		
	}
	
	
	
}
