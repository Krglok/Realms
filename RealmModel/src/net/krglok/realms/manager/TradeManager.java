package net.krglok.realms.manager;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.model.RealmModel;

/**
 * the trade mnager realize the trading task for the settlement
 * - sell order
 * - buy order
 * the manager can interact with the world and send commands and requests to other managers
 * the trade manager take trading requests from the settlemanager 
 * 
 * @author oduda
 *
 */
public class TradeManager
{
	private final double MAX_VALUE  = 100.0;
	private final int    MAX_AMOUNT = 100;
	private final int  SELL_DELAY   = 1200;
	private final int  BUY_DELAY    = 1200;
	
	private TradeOrder buyOrder;
	private TradeOrder sellOrder;
	
	private int sellDelay;
	private int buyDelay;
	
	private double sellFactor;
	private double buyFactor;
	
	private boolean isSellActiv;
	private boolean isBuyActiv;
	
	private ItemPriceList priceList;
	
	public TradeManager(DataInterface data)
	{
		this.buyOrder  = new TradeOrder();
		this.sellOrder = new TradeOrder();
		this.sellDelay = SELL_DELAY;
		this.buyDelay  = BUY_DELAY;
		this.sellFactor= 1.0;
		this.buyFactor = 1.0;
		this.isSellActiv = false;
		this.isBuyActiv  = false;
		priceList = data.getPriceList();
	}
	
	
	
	public double getSellFactor()
	{
		return sellFactor;
	}



	public void setSellFactor(double sellFactor)
	{
		this.sellFactor = sellFactor;
	}



	public double getBuyFactor()
	{
		return buyFactor;
	}



	public void setBuyFactor(double buyFactor)
	{
		this.buyFactor = buyFactor;
	}



	public TradeOrder getBuyOrder()
	{
		return buyOrder;
	}



	public TradeOrder getSellOrder()
	{
		return sellOrder;
	}



	public int getSellDelay()
	{
		return sellDelay;
	}



	public int getBuyDelay()
	{
		return buyDelay;
	}



	public boolean isSellActiv()
	{
		return isSellActiv;
	}



	public boolean isBuyActiv()
	{
		return isBuyActiv;
	}



	public ItemPriceList getPriceList()
	{
		return priceList;
	}



	public void newSellOrder(Settlement settle, String itemRef, int amount)
	{
		sellOrder = new TradeOrder(settle.getId(), TradeType.SELL, itemRef, amount, 0.0, SELL_DELAY, 0, TradeStatus.READY, settle.getPosition().getWorld(), 0);
	}

	public void newBuyOrder(Settlement settle, String itemRef, int amount)
	{
		buyOrder = new TradeOrder(settle.getId(), TradeType.BUY, itemRef, amount, 0.0, BUY_DELAY, 0, TradeStatus.READY, settle.getPosition().getWorld(), 0);
		
	}
	
	public void run(RealmModel rModel, Settlement settle)
	{
		// check for BuyOrders to fulfill
		settle.getTrader().checkMarket(rModel.getTradeMarket(), rModel.getTradeTransport(), settle, rModel.getSettlements());
		// check for Transport to fulfill
		rModel.getTradeTransport().fullfillTarget(settle);
		rModel.getTradeTransport().fullfillSender(settle);
		if ((sellOrder.getStatus() == TradeStatus.READY) || (sellOrder.getStatus() == TradeStatus.STARTED))
		{
			int restAmount = sellValuePrice(rModel, settle, sellOrder);
			if (restAmount <= 0)
			{
				sellOrder.setValue(0);
				sellOrder.setStatus(TradeStatus.WAIT);
			}
		}
		if ((sellOrder.getStatus() == TradeStatus.WAIT) )
		{
			// check for sellOrder is Fulfilled
		}
	}

	private int sellValuePrice(RealmModel rModel, Settlement settle, TradeOrder sellOrder)
	{
		
		int sellAmount = sellOrder.value();
		int amount = settle.getWarehouse().getItemList().getValue(sellOrder.ItemRef());
		double sellPrice = priceList.getBasePrice(sellOrder.ItemRef());
		sellOrder.setBasePrice(sellPrice);
		
		if (sellAmount > MAX_AMOUNT)
		{
			sellAmount = MAX_AMOUNT;
		} 
		if(Math.round(sellAmount * sellPrice) > MAX_VALUE)
		{
			sellAmount = (int) (MAX_VALUE / sellPrice);
		}
		sellOrder.setValue(sellOrder.value()-sellAmount);
		sellOrder.setStatus(TradeStatus.STARTED);
		
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
		return (amount - sellAmount);
	}
	
	
}
