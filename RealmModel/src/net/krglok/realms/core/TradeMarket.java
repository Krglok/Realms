package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * <pre>
 * Hier werden zentral die Angebote der Trader gespeichert und verwaltet.
 * Die Instanz ist zentral angeordnet und wird an die entsprechenden Nutzer uebergeben.
 * Es wird von den Nutzern verändert und verwaltet.
 * </pre>
 * @author oduda
 *
 */
public class TradeMarket extends HashMap<Integer,TradeMarketOrder>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5194184061229844880L;
	private  int lastNumber;


	public TradeMarket()
	{
		super();
		lastNumber = 0;
	}

	public  int getLastNumber()
	{
		return lastNumber;
	}

	public  int nextLastNumber()
	{
		lastNumber++;
		return lastNumber;
	}

	public  void setLastNumber(int lastNumber)
	{
		this.lastNumber = lastNumber;
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
//						this.lastNumber++;
						subList.put(sellOrder.getId(),sellOrder);
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
		if (tmo.getId() < 1) 
		{
			lastNumber++;
			tmo.setId(lastNumber);
		}
		if (lastNumber > 9000)
		{
			lastNumber = 1;
		}
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
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
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
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
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
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
			}
		}
		
		return subList;
	}
	
	public ArrayList<String> sortItems()
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (Integer i : this.keySet())
		{
			String s = ConfigBasis.set0right(i, 5);
			sortedItems.add(s);
//			System.out.print(s+"|");
		}
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  String.CASE_INSENSITIVE_ORDER);
		}
		return sortedItems;
	}

	
	
	public ArrayList<Integer> sortInteger()
	{
		ArrayList<Integer> sortedItems = new ArrayList<Integer>();
		Comparator<Integer> iComp = new Comparator<Integer>() 
		{

            @Override
            public int compare(Integer o1, Integer o2) 
            {
            	if (o1 == o2)
            	{
            		return 0;
            	}
            	if (o1 < o2)
            	{
            		return +1;
            	} else
            	{
            		return -1;
            	}
            }
		};
		for (Integer s : this.keySet())
		{
			sortedItems.add(s);
		}
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  iComp);
		}
		return sortedItems;
	}

	public  TradeMarket getDistantOrders(SettlementList settleList, int settleId)
	{
		TradeMarket subList = new TradeMarket();
		
		Settlement settlement = settleList.getSettlement(settleId);
		LocationData sourceLoc = settlement.getPosition();
		int index = 0;
		
		for (TradeMarketOrder tradeOrder : this.values())
		{
			int targetId = tradeOrder.getSettleID();
			Settlement settle = settleList.get(targetId);
			if (settleId != targetId)
			{
				LocationData targetLoc = settle.getPosition();
				double dist = sourceLoc.distance(targetLoc);
				if (sourceLoc.getWorld().equalsIgnoreCase(targetLoc.getWorld()) != true)
				{
					dist = dist + (10 * ConfigBasis.DISTANCE_1_DAY);
				}
				Integer iKey = (int) dist;
				if(subList.containsKey(iKey) == false)
				{
					index ++;
					dist = dist + index;
				}
				iKey = (int) dist;
				subList.put(iKey, tradeOrder);
			}
		}
		TradeMarket sortList = new TradeMarket();
		for (String sRef :subList.sortItems())
		{
			int ref = Integer.valueOf(sRef);
			sortList.put(ref, subList.get(ref));
		}
		return sortList;
	}
}
