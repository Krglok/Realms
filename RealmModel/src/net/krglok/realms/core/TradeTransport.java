package net.krglok.realms.core;

import java.util.HashMap;

/**
 * <pre>
 * Hier werden zentral die Transporte der Trader abgewickelt.
 * Die Instanz ist zentral angeordnet und wird an die entsprechenden Nutzer uebergeben.
 * Es wird von den Nutzern verändert und verwaltet.
 * </pre>
 * @author Windu
 *
 */
public class TradeTransport extends HashMap<Integer,TradeMarketOrder>
{

	private static final long serialVersionUID = 1L;
	
	private static int lastNumber;

	public TradeTransport()
	{
		super();
		lastNumber = 0;
	}
	
	public static int getLastNumber()
	{
		return TradeTransport.lastNumber;
	}

	public static int nextLastNumber()
	{
		lastNumber++;
		return lastNumber;
	}

	public static void setLastNumber(int lastNumber)
	{
		TradeTransport.lastNumber = lastNumber;
	}

	
	public void runTick()
	{
		for (TradeMarketOrder to : this.values())
		{
//			System.out.println("Trans:"+ to.getTradeType()+":"+to.getStatus()+":"+to.gtetTickCount()+":"+to.getMaxTicks());
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
	
	
	public void setOrderFullfill(TradeMarketOrder sellOrder)
	{
		sellOrder.setStatus(TradeStatus.FULFILL);
	}

	public TradeMarketOrder addOrder(TradeMarketOrder tmo)
	{
		lastNumber++;
		tmo.setId(lastNumber);
		this.put(lastNumber, tmo);
		return tmo;
	}
	
	/**
	 * Erledigt eine TransportOrder mit gekaufter Menge und Preis
	 * berechnet Kaufpreis und bucht das Geld auf das Settlement
	 * SettleId , ist derjenige, der das Geld erhaelt
	 * TargetId , ist derjenige der die Ware erhaelt
	 * 
	 * @param settle der Absender der Ware 
	 */
	public void fullfillSender(Settlement settle)
	{
		double cost = 0.0;
		for (TradeMarketOrder to : this.values())
		{
			if (to.getStatus() == TradeStatus.DECLINE)
			{
				if (to.getSettleID() == settle.getId())
				{
					cost = to.value() * to.getBasePrice();
					settle.getBank().depositKonto(cost, "Trader "+to.getTargetId(),settle.getId());
					settle.getTrader().setOrderCount(settle.getTrader().getOrderCount()+1);
//					settle.getTrader().setCaravanCount(settle.getTrader().getCaravanCount()-1);
				}
				if (to.getTargetId() == 0)
				{
					to.setStatus(TradeStatus.NONE);
				}
			}
		}
	}
	
	public void fullfillTarget(Settlement settle)
	{
		for (TradeMarketOrder to : this.values())
		{
			if (to.getStatus() == TradeStatus.DECLINE)
			{
				if (to.getTargetId() == settle.getId())
				{
					settle.getWarehouse().depositItemValue(to.ItemRef(), to.value());
					settle.getTrader().setOrderCount(settle.getTrader().getOrderCount()-1);
					settle.getTrader().setCaravanCount(settle.getTrader().getCaravanCount() -1);

					to.setTargetId(0);
//					to.setStatus(TradeStatus.NONE);
				}
			}
		}
		
	}
	
}
