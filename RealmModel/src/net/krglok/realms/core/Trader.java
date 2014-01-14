package net.krglok.realms.core;

import java.util.HashMap;

/**
 * Der Trader ist kein Gebaeude, sondern ein Manager der Handel abwickelt. 
 * Das entsprechnde Gebaeude ist unter buildungs zu finden.
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
	private int buildingId;
	private boolean isEnabled;
	private boolean isActive;
	private TradeOrderList buyOrders;
//	private TradeOrderList sellOrders;
	private int OrderMax;
	
	
	
	public Trader()
	{
		caravanMax = 5;
		caravanCount = 0;
		buildingId = 0;
		isEnabled = false;
		isActive  = false;
		OrderMax = 5;
		buyOrders = new TradeOrderList();
//		sellOrders = new TradeOrderList();
		
	}



	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isEnabled, boolean isActive)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.buildingId = buildingId;
		this.isEnabled = isEnabled;
		this.isActive = isActive;
		OrderMax = 5;
		buyOrders = new TradeOrderList();
//		sellOrders = new TradeOrderList();
	}

	


	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isActive, TradeOrderList buyOrder,
			TradeOrderList sellOrder, int orderMax)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.buildingId = buildingId;
		this.isActive = isActive;
		this.buyOrders = buyOrder;
//		this.sellOrders = sellOrder;
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



	public int getBuildingId()
	{
		return buildingId;
	}



	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
		this.isEnabled = true;
		this.isActive = true;
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



	public TradeOrderList getBuyOrders()
	{
		return buyOrders;
	}



	public void setBuyOrders(TradeOrderList buyOrder)
	{
		this.buyOrders = buyOrder;
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
	 * Erzeugt eine TransportOrder mit gekaufter Menge und Preis
	 * berechne Menge und buche sie vom MarketOrder ab
	 * berechnet den Kaufpreis und bucht ihn vom kaeufer ab
	 * SettleId , ist derjenige, der das Geld erhaelt
	 * TargetId , ist derjenige der die Ware erhaelt
	 * errorHandling, wenn der Kaeufer nicht genug Geld hat passiert nix
	 * @param tmo
	 * @param foundOrder
	 * @param transport
	 * @param SettleId
	 */
	public void makeTransportOrder(TradeMarketOrder tmo, TradeOrder foundOrder, TradeTransport transport , Settlement settle)
	{
		int amount = 0;
		double cost = 0.0 ;
		if (tmo.value() >= foundOrder.value())
		{
			amount = foundOrder.value();
			cost = amount * tmo.getBasePrice();
			tmo.setValue(tmo.value() - amount);
			if (settle.getBank().getKonto() >= cost)
			{
				TradeMarketOrder tto = new TradeMarketOrder(
						tmo.getSettleID(),			// ID des Absenders
						tmo.getId(),				// Id der sellOrder
						TradeType.TRANSPORT, 
						foundOrder.ItemRef(), 		// Ware
						amount,						// gepkaufte Menge 
						tmo.getBasePrice(),  		// Kaufpreis
						ConfigBasis.GameDay, 		// Laufzeit des Transports
						0, 							// abgelaufene Transportzeit
						TradeStatus.STARTED, 		// automatischer Start des Transport
						tmo.getWorld(),				// ZielWelt
						settle.getId()					// ID des Ziel Settlement
						);			
				transport.addOrder(tto);
				settle.getBank().withdrawKonto(cost, "Trader "+settle.getId());
				tmo.setStatus(TradeStatus.WAIT);
			}	
		}
	}
	
	private TradeOrder checkBuyOrder(String itemRef, int offerValue, double offerPrice)
	{
		for (TradeOrder to : buyOrders.values())
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
	
	/**
	 * Versucht eine sellOrder zu finden fuer seine buyOrder
	 * prueft Anzahl der Caravan
	 * prueft Verkaufpreis <= Kaufpreis
	 * prueft VerkaufMenge >= Kaufmenge 
	 * @param tradeMarket
	 * @param tradeTransport
	 * @param settle
	 */
	public void checkMarket(TradeMarket tradeMarket, TradeTransport tradeTransport, Settlement settle )
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
					makeTransportOrder(tmo, foundOrder, tradeTransport, settle );
				} else
				{
					return;
				}
			}
		}
		
	}
	
	public void makeSellOrder(TradeMarket tradeMarket, int settleId, TradeOrder sellOrder)
	{
			sellOrder.setStatus(TradeStatus.STARTED);
			TradeMarketOrder tmo = new TradeMarketOrder(settleId, sellOrder);
			tradeMarket.addOrder(tmo);
	}

}
