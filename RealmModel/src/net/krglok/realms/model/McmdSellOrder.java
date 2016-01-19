package net.krglok.realms.model;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;

public class McmdSellOrder implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.SELLITEM;
	private RealmModel rModel;
	private int settleId;
	private String itemRef;
	private int amount;
	private int target;
	private int reached;
	private double price;
	private int delayDays;
	private TradeStatus tradeStatus;

	
	
	public McmdSellOrder(RealmModel rModel, int settleId, String itemRef, int amount, double price, int delayDays)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		if (itemRef == null)
		{ 
			this.itemRef = "AIR";
		} else
		{
			this.itemRef = itemRef;
		}
		this.amount = amount;
		this.price = price;
		this.delayDays = delayDays;
		this.setTradeStatus(TradeStatus.READY);
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}
	

	public int getSettleId()
	{
		return settleId;
	}

	public String getItemRef()
	{
		return itemRef;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int value)
	{
		this.amount = value;
	}
	
	public double getPrice()
	{
		return price;
	}

	public int getDelayDays()
	{
		return delayDays;
	}

	public TradeStatus getTradeStatus()
	{
		return tradeStatus;
	}

	public void setTradeStatus(TradeStatus tradeStatus)
	{
		this.tradeStatus = tradeStatus;
	}

	public int getTarget()
	{
		return target;
	}

	public void setTarget(int target)
	{
		this.target = target;
	}

	public int getReached()
	{
		return reached;
	}

	public void setReached(int reached)
	{
		this.reached = reached;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { RealmModel.class.getName() , int.class.getName() , String.class.getName() , int.class.getName() , int.class.getName()  };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		//SellOrder(rModel, settleId, itemRef, amount, delayDays);
		int id = rModel.getTradeMarket().nextLastNumber();
		if (delayDays < 1)
		{
			delayDays = 1;
		}
		long maxTicks = ConfigBasis.GameDay * delayDays;
		TradeOrder sellOrder = new TradeOrder(id , TradeType.SELL, itemRef, amount, price, maxTicks, 0L, TradeStatus.STARTED, settle.getPosition().getWorld(), 0,SettleType.NONE);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settle, sellOrder);
	}

	@Override
	public boolean canExecute()
	{
		if (rModel.getSettlements().getSettlement(settleId) != null)
		{
			if (rModel.getSettlements().getSettlement(settleId).getTrader().isFreeSellOrder())
			{
				return true;				
			}
		}
		return false;
	}

}
