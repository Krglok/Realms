package net.krglok.realms.core;


/**
 * <pre>
 * Der Trader ist kein Gebaeude, sondern ein Manager der Handel abwickelt. 
 * Das entsprechende Gebaeude ist unter buildungs zu finden.
 * Der Trader wickelt die Handelsaktionen ab.
 * Hierzu benutzt er Karawanen. Jede Karawane hat einen Esel zum Transport der Warenkiste
 * Jeder Trader hat 5 Handelskisten. Fuer jede Handelskiste kann eine Handelstransaktion ausgeführt werden . 
 * Dies begrenzt auch gleichzeitig den Umfang der Transaktion (max 1762 Items). 
 * Die tatsaechliche Anzahl der Items ist von der Stacksize abhaengig. 
 * tradeOrders , sind die Transporte mit den Karawanen
 * buyOrders , sind die Einkaufsaktionen des Settlement
 * sellOrders, sind die Verkaufsaktionen des Settlements
 * routeOrder, sind die regelmässigen Warentransporte zwischen zwei settlement des gleichen Owner
 * 
 * Das Target ist immer ein Settlement, kein AbstractSelltement !
 * </pre>
 * @author Windu
 *
 */

public class Trader  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1502593484415028676L;
	private int caravanMax;
	private int caravanCount;
	private int buildingId;
	private boolean isEnabled;
	private boolean isActive;
	private TradeOrderList buyOrders;
	private RouteOrderList routeOrders;
	private int orderMax;
	private int orderCount;
	
	
	
	public Trader()
	{
		caravanMax = 5;
		caravanCount = 0;
		buildingId = 0;
		isEnabled = false;
		isActive  = false;
		orderMax = 20;
		buyOrders = new TradeOrderList();
		routeOrders = new RouteOrderList();
		
	}



	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isEnabled, boolean isActive, int orderMax)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.buildingId = buildingId;
		this.isEnabled = isEnabled;
		this.isActive = isActive;
		this.orderMax = orderMax;
		buyOrders = new TradeOrderList();
		routeOrders = new RouteOrderList();
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
		routeOrders = new RouteOrderList();
//		this.sellOrders = sellOrder;
		this.orderMax = orderMax;
	}

	//public 

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
		if (this.caravanCount < 0)
		{
			this.caravanCount= 0;
		}
	}


	public boolean isFreeSellOrder()
	{
		return (5 >= orderCount);
	}
	
	public boolean isFreeBuyOrder()
	{
//		if (orderMax >= buyOrders.size())
//		{
//			return true;
//		}
		return true;
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
		return orderMax;
	}


	public void setOrderMax(int orderMax)
	{
		this.orderMax = orderMax;
	}
	
	public int getOrderCount()
	{
		return orderCount;
	}



	public void setOrderCount(int orderCount)
	{
		this.orderCount = orderCount;
		if (this.orderCount< 0)
		{
			this.orderCount = 0;
		}
	}



	public RouteOrderList getRouteOrders()
	{
		return routeOrders;
	}



	public void setRouteOrders(RouteOrderList routeOrders)
	{
		this.routeOrders = routeOrders;
	}



	public static long  getTransportDelay(double distance)
	{
		
		if (distance > ConfigBasis.DISTANCE_1_DAY )
		{
			long way = (long) (distance / ConfigBasis.DISTANCE_1_DAY * ConfigBasis.GameDay);
			return way; 
		}
		return ConfigBasis.GameDay;
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
	public void makeTransportOrder(TradeMarketOrder tmo, TradeOrder foundOrder, TradeTransport transport , AbstractSettle settle, double distance)
	{
		int amount = 0;
		double cost = 0.0 ;
		if (tmo.value() >= foundOrder.value())
		{
			amount = foundOrder.value();
		} else
		{
			amount = tmo.value();
		}
		if (amount > 0)
		{
			cost = amount * tmo.getBasePrice();
			tmo.setValue(tmo.value() - amount);
			long travelTime = getTransportDelay(distance);
			if (tmo.getWorld().equalsIgnoreCase(settle.getPosition().getWorld()) == false)
			{
				travelTime = travelTime + (10 * ConfigBasis.GameDay);
			}
			if (settle.getBank().getKonto() >= cost)
			{
				if (transport.countSender(settle.getId(),settle.getSettleType()) < caravanMax)
				{
					TradeMarketOrder tto = new TradeMarketOrder(
							tmo.getSettleID(),			// ID des Absenders
							settle.getSettleType(),
							tmo.getId(),				// Id der sellOrder
							TradeType.TRANSPORT, 
							foundOrder.ItemRef(), 		// Ware
							amount,						// gepkaufte Menge 
							tmo.getBasePrice(),  		// Kaufpreis
							travelTime, // Laufzeit des Transports
							0, 							// abgelaufene Transportzeit
							TradeStatus.STARTED, 		// automatischer Start des Transport
							tmo.getWorld(),				// ZielWelt
							settle.getId(),					// ID des Ziel Settlement
							settle.getSettleType()
							);			
					transport.addOrder(tto);
					settle.getTrader().setCaravanCount(settle.getTrader().getCaravanCount() +1);
					settle.getBank().withdrawKonto(cost, "Trader "+settle.getId(),settle.getId());
					tmo.setStatus(TradeStatus.WAIT);
					foundOrder.setStatus(TradeStatus.NONE);
//					System.out.println("[REALMS] TRANSPORT "+settle.getId()+":"+foundOrder.ItemRef()+":"+amount+" =====");
				}
			}	
		}
	}
	
	
	private TradeOrder checkBuyOrder(String itemRef, int offerValue, double offerPrice)
	{
//		System.out.println("checkBuyOrder "+itemRef+"/");
		for (TradeOrder to : buyOrders.values())
		{
			if (to.getStatus() == TradeStatus.STARTED)
			{
	//			System.out.println("checkBuyOrder "+itemRef+"/"+to.ItemRef());
				if (to.ItemRef().equalsIgnoreCase(itemRef))
				{
	//				System.out.println("checkBuyOrder "+offerPrice+"<="+to.getBasePrice());
					if (offerPrice <= to.getBasePrice())
					{
	//					System.out.println("return "+to.ItemRef());
						return to;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Versucht eine sellOrder zu finden fuer seine buyOrder
	 * hierbei wird die Distanz als Sort kriterium genommen, so dass die nächsten
	 * Settlements als erste berücksichtigt werden.
	 * prueft Anzahl der Caravan
	 * prueft Verkaufpreis <= Kaufpreis
	 * prueft VerkaufMenge >= Kaufmenge
	 * scliesst andere welten aus !! 
	 * @param tradeMarket
	 * @param tradeTransport
	 * @param settle
	 */
	public void checkMarket(TradeMarket tm, TradeTransport tradeTransport, AbstractSettle settle,SettlementList targets )
	{
		double distance = 0.0;
		TradeMarket tradeMarket = tm.getDistantOrders(targets, settle.getId());
		TradeOrder foundOrder = null;
		if (tradeMarket.isEmpty())
		{
			return;
		}
		for (String sRef : tradeMarket.sortItems())
		{
			TradeMarketOrder tmo = tradeMarket.get(Integer.valueOf(sRef));
			foundOrder = checkBuyOrder(tmo.ItemRef(), tmo.value(), tmo.getBasePrice());
			if ( foundOrder != null)
			{
				if (settle.getPosition().getWorld().equalsIgnoreCase(foundOrder.getWorld())==true)
				{
//				System.out.println(foundOrder.getId()+":"+foundOrder.ItemRef());
					if (tradeTransport.countSender(settle.getId(),settle.getSettleType()) < caravanMax)
					{
						distance = settle.getPosition().distance2D(targets.getSettlement(tmo.getSettleID()).getPosition());
						makeTransportOrder(tmo, foundOrder, tradeTransport, settle, distance );
					} else
					{
						return;
					}
				}
			}
		}
		
	}
	
	/**
	 * erstellt eine SellOrder
	 * prueft ob genug Ware im Lager ist.
	 * und startet die TradeMarketOrder d.h. schickt die Ware los.
	 * 
	 * @param tradeMarket
	 * @param settle
	 * @param sellOrder
	 */
	public void makeSellOrder(TradeMarket tradeMarket, AbstractSettle settle, TradeOrder sellOrder)
	{
		if (settle.getWarehouse().getItemList().getValue(sellOrder.ItemRef())>= sellOrder.value())
		{
			settle.getWarehouse().withdrawItemValue(sellOrder.ItemRef(), sellOrder.value());
			sellOrder.setStatus(TradeStatus.STARTED);
			TradeMarketOrder tmo = new TradeMarketOrder(settle.getId(), settle.getSettleType(), sellOrder);
			tradeMarket.addOrder(tmo);
			orderCount++;
		}
	}

	/**
	 * erstellt eine BuyOrder und startet sie
	 * die BuyOrder laufen nach einer bestimmten Zeit ab und werden geloescht.
	 * 
	 * @param buyOrder
	 */
	public void makeBuyOrder(TradeOrder buyOrder)
	{
		buyOrders.addTradeOrder(buyOrder);
	}
	
	
	/**
	 * prueft ob eine routeOrder erstellt werden kann
	 * @param settle
	 * @param settlements
	 */
	public void checkRoutes(TradeMarket tradeMarket, TradeTransport tradeTransport, AbstractSettle settle, SettlementList targets)
	{
		// es wird eine zusätzliche Caravan erzeugt, damit der TRansport nicht vollstaendig unterdrueckt wird 
		if (tradeTransport.countSender(settle.getId(),settle.getSettleType()) <= caravanMax)
		{
			for (RouteOrder rOrder : routeOrders.values())
			{
				if (tradeTransport.checkRoute(settle.getId(), rOrder.getTargetId(), rOrder.ItemRef()) == false)
				{
					if (settle.getWarehouse().getItemList().containsKey(rOrder.ItemRef()))
					{
						// enough items in warehouse of sender
						if (settle.getWarehouse().getItemList().getValue(rOrder.ItemRef()) >= rOrder.value())
						{
							// target exist
							if (targets.containsKey(rOrder.getTargetId()))
							{
								// target has enough space in warehouse
								if (targets.getSettlement(rOrder.getTargetId()).getWarehouse().getFreeCapacity() > ConfigBasis.WAREHOUSE_SPARE_STORE)
								{
//									System.out.println("[REALMS] Make route "+settle.getId()+" to "+rOrder.getTargetId()+" : "+ rOrder.ItemRef());
									makeRouteOrder(tradeMarket, rOrder, tradeTransport, settle, targets);
								}
							}
						}
					}
				}
			}
//		} else
//		{
//			System.out.println("[REALMS] Routes  MaxCaravan :"+settle.getId());
		}
		
	}

	/**
	 * calculate the travel time in ticks for route transport order
	 * 
	 * @param distance
	 * @return
	 */
	public static long getRouteDelay(double distance)
	{
		long way = (long) distance;
		if (distance < (ConfigBasis.DISTANCE_1_DAY / 2 ) )
		{
			way = (long) (ConfigBasis.DISTANCE_1_DAY / 2);
			return way; 
		} else 	if (distance > (ConfigBasis.DISTANCE_1_DAY ) )
		{
			way = (long) (ConfigBasis.DISTANCE_1_DAY  * 1.5);
			return way; 
		} else
		{
			return (long) way;
		}
		
	}
	
	/**
	 * make a RouteOrder for AbstractSettle to Settlement
	 * RouteOrder is a imperative tradeOrder with a predifined target
	 *  
	 * @param tradeMarket
	 * @param rOrder
	 * @param settle
	 * @param settlements
	 */
	public void makeRouteOrder(TradeMarket tradeMarket, RouteOrder rOrder,  TradeTransport transport, AbstractSettle settle, SettlementList targets)
	{
		Settlement targetSettle = targets.getSettlement(rOrder.getTargetId());
		
		double distance = settle.getPosition().distance2D(targetSettle.getPosition());

		// setze next free MarketNumber
		String itemRef = rOrder.ItemRef();
//		long tickCount = 0;
//		TradeStatus status = TradeStatus.STARTED;
		String targetWorld = targetSettle.getPosition().getWorld();
//		int targetId = rOrder.getTargetId();

		int amount = rOrder.value();
		double cost = 0.0 ;

		if (amount > 0)
		{
			cost = amount * rOrder.getBasePrice();
			long travelTime = getRouteDelay(distance); // getTransportDelay(distance);
			if (targetWorld.equalsIgnoreCase(settle.getPosition().getWorld()) == false)
			{
				travelTime = travelTime + (10 * ConfigBasis.GameDay);
			}
			if (settle.getBank().getKonto() >= cost)
			{
				if (caravanCount < caravanMax)
				{
					TradeMarketOrder tto = new TradeMarketOrder(
							settle.getId(),			// ID des Absenders
							settle.getSettleType(),
							0 ,				// Id der sellOrder
							TradeType.TRANSPORT, 
							rOrder.ItemRef(), 		// Ware
							amount,						// gepkaufte Menge 
							rOrder.getBasePrice(),  		// Kaufpreis
							travelTime, // Laufzeit des Transports
							0, 							// abgelaufene Transportzeit
							TradeStatus.STARTED, 		// automatischer Start des Transport
							targetWorld,				// ZielWelt
							targetSettle.getId(),					// ID des Ziel Settlement
							targetSettle.getSettleType()
							);			
					transport.addOrder(tto);
					settle.getTrader().setCaravanCount(settle.getTrader().getCaravanCount() +1);
					settle.getWarehouse().withdrawItemValue(itemRef, amount);
					targetSettle.getBank().withdrawKonto(cost, "Trader "+settle.getId(),settle.getId());
//					System.out.println("[REALMS] ROUTE   "+settle.getId()+">"+targetSettle.getId()+":"+rOrder.ItemRef()+":"+amount+"="+ConfigBasis.setStrformat2(cost, 11));
				}
			}	
		}
		
	}
}
