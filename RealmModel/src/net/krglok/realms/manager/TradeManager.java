package net.krglok.realms.manager;

import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;

/**
 * the trade manager realize the trading task for the settlement
 * - sell order
 * - buy order
 * - route order
 * the manager can interact with the world and send commands and requests to other managers
 * the trade manager take trading requests from the settlemanager 
 * 
 * @author oduda
 *
 */
public class TradeManager
{
	private final double MAX_VALUE  = 2000.0;
	private final int    MAX_AMOUNT = 2000;
	private final int  SELL_DELAY   = 1200;
	private final int  BUY_DELAY    = 1200;
	private final int  maxCounter = 30;
	
	private TradeOrder buyOrder;
	private McmdSellOrder sellOrder;
	
	private int sellDelay;
	private int buyDelay;
	
	private double sellFactor;
	private double buyFactor;
	
	private boolean isSellActiv;
	private boolean isBuyActiv;
	
	private int sellCounter;
	private int buyCounter;
	
	private ItemPriceList priceList;
	
	private int delayRoutes = 0;
	private int delaySell = 0;
	
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
		sellCounter = 0;
		buyCounter = 0;
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
		sellCounter = 0;
		buyCounter = 0;
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
	
	


	public void setPriceList(ItemPriceList  priceList)
	{
		this.priceList = priceList ;
	}



	public void newSellOrder(McmdSellOrder sellOrder)
	{
		this.sellOrder = sellOrder; 
//				
	}

	public void newBuyOrder(AbstractSettle aSettle, String itemRef, int amount)
	{
		buyOrder = new TradeOrder(aSettle.getId(),TradeType.BUY, itemRef, amount, 0.0, BUY_DELAY, 0, TradeStatus.READY, aSettle.getPosition().getWorld(), 0,aSettle.getSettleType());
		
	}
	
	/**
	 * handle the trading order and generate new orders if needed
	 * 
	 * @param rModel
	 * @param settle
	 */
	public void run(RealmModel rModel, AbstractSettle settle)
	{
//		System.out.println("TradeManager "+settle.getSettleType()+":["+settle.getId()+"] ");
		// check for RouteOrders
		if (delayRoutes > (SELL_DELAY / 20))
		{
//			settle.getMsg().add("checkRoutes");
			settle.getTrader().checkRoutes(rModel.getTradeMarket(), rModel.getTradeTransport(), settle, rModel.getData());
			delayRoutes = 0;
		} else
		{
			delayRoutes++;
		}
		// check for BuyOrders to fulfill
		if (delaySell > (SELL_DELAY / 20))
		{
//			settle.getMsg().add("checkMarket");
			settle.getTrader().checkMarket(rModel.getTradeMarket(), rModel.getTradeTransport(), settle, rModel.getSettlements());
			delaySell = 0;
		} else
		{
			delaySell++;
		}
		// check for Transport to fulfill
//		if (settle.getSettleType().isLehen(settle.getSettleType()))
//		{
////			settle.getMsg().add("checkTransport Lehen");
//			rModel.getLehenTransport().fullfillTarget(settle);
//			rModel.getLehenTransport().fullfillSender(settle);
//		} else
//		{
//			settle.getMsg().add("checkTransport Settlement");
			rModel.getTradeTransport().fullfillTarget(settle);
			rModel.getTradeTransport().fullfillSender(settle);
//		}
		// work on Sell Command
		if (sellOrder != null)
		{
			if (sellCounter > maxCounter)
			{
				if (sellOrder.getAmount() > 0 )
				{
					settle.getMsg().add("SellOrder amount "+sellOrder.getAmount());
					boolean isSellOrder = false;
					for (TradeMarketOrder tOrder : rModel.getTradeMarket().getSettleOrders(settle.getId(), settle.getSettleType()).values())
					{
						if (tOrder.ItemRef().equalsIgnoreCase(sellOrder.getItemRef()))
						{
							isSellOrder = true;
						}
					}
					if (isSellOrder == false)
					{
						// make new SellOrder and give return rest 
						int restAmount = sellValuePrice(rModel, settle, sellOrder);
			//				System.out.println("SellOrder rest "+restAmount);
						if (restAmount <= 0)
						{
							sellOrder.setAmount(0);
						}
					}
				}
				sellCounter = 0;
			} else
			{
				sellCounter++;

			}
		}
		// work on Buy Command
		if (buyOrder != null)
		{
			if (buyCounter > maxCounter)
			{
				if (checkBuyValid(rModel, settle))
				{
					if (buyOrder.value() > 0)
					{
						isBuyActiv = true;
						buyValuePrice(rModel, settle, buyOrder);
					} else
					{
						isBuyActiv = false;
					}
				} else
				{
					isBuyActiv = false;
				}
				buyCounter = 0;
			} else
			{
				buyCounter++;
			}
		}
		
		// Clean order Queues
		Integer[] keyArray = new Integer[settle.getTrader().getBuyOrders().size()];
		int index = 0;
		for (TradeOrder order : settle.getTrader().getBuyOrders().values())
		{
			if ((order.getStatus() == TradeStatus.NONE) || (order.getStatus() == TradeStatus.DECLINE))
			{
				keyArray[index] = order.getId();
				index++;
			} else
			{
				order.runTick();
			}
		}
		for (Integer id : keyArray)
		{
			settle.getTrader().getBuyOrders().remove(id);
		}

		// clean 
		for (TradeMarketOrder order : rModel.getTradeMarket().values())
		{
			if (order.getSettleID() == settle.getId())
			{
				if (order.getStatus() == TradeStatus.WAIT)
				{
					if (order.value() > 0)
					{
						settle.getWarehouse().depositItemValue(order.ItemRef(), order.value());
					}
					order.setStatus(TradeStatus.DECLINE);
					
				}
			}
		}
		
	}

	private int sellValuePrice(RealmModel rModel, AbstractSettle settle, McmdSellOrder sellOrder)
	{
//		boolean isRest = true;
		if (sellOrder != null)
		{
			int sellAmount = sellOrder.getAmount();
			int amount = settle.getWarehouse().getItemList().getValue(sellOrder.getItemRef());
			double sellPrice = priceList.getBasePrice(sellOrder.getItemRef());
			//check valuables price
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
				
				sellAmount = (int) (MAX_VALUE / sellPrice);    //((MAX_VALUE-Math.round(sellAmount * sellPrice)) / sellPrice);
			}
			sellOrder.setAmount(sellOrder.getAmount()-sellAmount);
	
			int id = rModel.getTradeMarket().nextLastNumber();
			settle.getMsg().add("SellOrder id : "+id+": "+sellOrder.getItemRef()+":"+sellAmount+":"+ConfigBasis.setStrformat2(sellPrice, 7));
			TradeOrder order = new TradeOrder(id, TradeType.SELL, sellOrder.getItemRef(), sellAmount, sellPrice, SELL_DELAY, 0, TradeStatus.READY, settle.getPosition().getWorld(), 0,SettleType.NONE);
			settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, order);
			sellOrder.setTarget(sellOrder.getTarget()+sellAmount);
			if ((amount-sellAmount) > 0)
			{
				return sellOrder.getAmount();
			} else
			{
				return 0;
			}
		} else
		{
			return 0;
		}
	}
	
	
	private void buyValuePrice(RealmModel rModel, AbstractSettle settle, TradeOrder buyOrder)
	{
		double buyPrice = priceList.getBasePrice(buyOrder.ItemRef());
		int buyAmount = buyOrder.value();
		if (buyAmount > MAX_AMOUNT)
		{
			buyAmount = MAX_AMOUNT;
		} 
		if(Math.round(buyAmount * buyPrice) > MAX_VALUE)
		{
			buyAmount = (int) (MAX_VALUE / buyPrice);
		}
		buyOrder.setBasePrice(buyPrice);
		if (buyOrder.value() > buyAmount)
		{
			buyOrder.setValue(buyOrder.value() - buyAmount);
		} else
		{
			buyOrder.setValue(0);
		}
		TradeOrder order = new TradeOrder(-1, TradeType.BUY, buyOrder.ItemRef(), buyAmount, buyPrice, BUY_DELAY, 0, TradeStatus.READY, settle.getPosition().getWorld(), 0,SettleType.NONE);
		settle.getTrader().makeBuyOrder(order);
		settle.getMsg().add("Trader Make BuyOrder "+buyOrder.ItemRef());
	}
	
	/**
	 * check if already equal buyOrder in progress
	 * @param rModel
	 * @param settle
	 * @return true if itemRef in Trader buyOrdr list
	 */
	private boolean checkBuyValid(RealmModel rModel, AbstractSettle settle)
	{
		for (TradeOrder order : settle.getTrader().getBuyOrders().values())
		{
			if (order.ItemRef().equalsIgnoreCase(buyOrder.ItemRef()))
			{
				return false;
			}
		}
		
		return true;
	}
}
