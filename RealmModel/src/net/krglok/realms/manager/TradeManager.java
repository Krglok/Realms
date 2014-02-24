package net.krglok.realms.manager;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.model.McmdSellOrder;
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
	private McmdSellOrder sellOrder;
	
	private int sellDelay;
	private int buyDelay;
	
	private double sellFactor;
	private double buyFactor;
	
	private boolean isSellActiv;
	private boolean isBuyActiv;
	
	private ItemPriceList priceList;
	
	public TradeManager()
	{
		this.buyOrder  = new TradeOrder();
		this.sellOrder = null;
		this.sellDelay = SELL_DELAY;
		this.buyDelay  = BUY_DELAY;
		this.sellFactor= 1.0;
		this.buyFactor = 1.0;
		this.isSellActiv = false;
		this.isBuyActiv  = false;
		priceList = new ItemPriceList();
	}

	public TradeManager(ItemPriceList priceList)
	{
		this.buyOrder  = new TradeOrder();
		this.sellOrder = null;
		this.sellDelay = SELL_DELAY;
		this.buyDelay  = BUY_DELAY;
		this.sellFactor= 1.0;
		this.buyFactor = 1.0;
		this.isSellActiv = false;
		this.isBuyActiv  = false;
		this.priceList = priceList;
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



	public McmdSellOrder getSellOrder()
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


	public void getPriceList(ItemPriceList  priceList)
	{
		this.priceList = priceList ;
	}



	public void newSellOrder(McmdSellOrder sellOrder)
	{
		this.sellOrder = sellOrder; 
//				
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
		
		if (sellOrder != null)
		{
//			System.out.println("SellOrder amount "+sellOrder.getAmount());
			if (sellOrder.getAmount() > 0 )
			{
				int restAmount = sellValuePrice(rModel, settle, sellOrder);
//				System.out.println("SellOrder rest "+restAmount);
				if (restAmount <= 0)
				{
					sellOrder.setAmount(0);
				}
			}
		}
		for (TradeOrder order : settle.getTrader().getBuyOrders().values())
		{
			if (order.getStatus() == TradeStatus.NONE)
			{
//				settle.getTrader().getBuyOrders().remove(order);
			}
		}

		for (TradeMarketOrder mOrder : rModel.getTradeMarket().getSettleOrders(settle.getId()).values())
		{
			if (mOrder.isDecline())
			{
//				sellOrder.setAmount(sellOrder.getAmount()-mOrder.value());
			}
			if (mOrder.getStatus() == TradeStatus.FULFILL)
			{
				sellOrder.setReached(sellOrder.getReached()+mOrder.value());
			}
		}
	}

	private int sellValuePrice(RealmModel rModel, Settlement settle, McmdSellOrder sellOrder)
	{
//		boolean isRest = true;
		int sellAmount = sellOrder.getAmount();
		int amount = settle.getWarehouse().getItemList().getValue(sellOrder.getItemRef());
		double sellPrice = priceList.getBasePrice(sellOrder.getItemRef());
		if (rModel.getConfig().getValuables().containsKey(sellOrder.getItemRef()))
		{
			sellPrice = sellPrice * 1.25;
		}

//		sellOrder.setBasePrice(sellPrice);
		if (sellAmount > amount)
		{
			sellAmount = amount;
		}
		
		if (sellAmount > MAX_AMOUNT)
		{
			sellAmount = MAX_AMOUNT;
		} 
		if(Math.round(sellAmount * sellPrice) > MAX_VALUE)
		{
			sellAmount = (int) (MAX_VALUE / sellPrice);
		}
		sellOrder.setAmount(sellOrder.getAmount()-sellAmount);

		int id = rModel.getTradeMarket().nextLastNumber();
//		System.out.println("Market id : "+id);
		TradeOrder order = new TradeOrder(id, TradeType.SELL, sellOrder.getItemRef(), sellAmount, sellPrice, SELL_DELAY, 0, TradeStatus.READY, settle.getPosition().getWorld(), 0);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, order);
		sellOrder.setTarget(sellOrder.getTarget()+sellAmount);
		if ((amount-sellAmount) > 0)
		{
			return sellOrder.getAmount();
		} else
		{
			return 0;
		}
	}
	
	
}
