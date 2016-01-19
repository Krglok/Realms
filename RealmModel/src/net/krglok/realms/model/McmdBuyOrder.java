package net.krglok.realms.model;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;

public class McmdBuyOrder implements iModelCommand
{
	

	private ModelCommandType commandType = ModelCommandType.BUYITEM;
	private RealmModel rModel;
	private int settleId;
	private String itemRef;
	private int amount;
	private double price;
	private int delayDays;
	

	
	public McmdBuyOrder(RealmModel rModel, int settleId, String itemRef,
			int amount, double price, int delayDays)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.itemRef = itemRef;
		this.amount = amount;
		this.price = price;
		this.delayDays = delayDays;
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

	public double getPrice()
	{
		return price;
	}

	public int getDelayDays()
	{
		return delayDays;
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
		;
		//SellOrder(rModel, settleId, itemRef, amount, delayDays);
//		int id = rModel.getTradeMarket().getLastNumber()+1;
		int id = rModel.getTradeMarket().nextLastNumber();
		if (delayDays < 1)
		{
			delayDays = 1;
		}
		long maxTicks = ConfigBasis.GameDay * delayDays;
		TradeOrder buyOrder = new TradeOrder(id , TradeType.SELL, itemRef, amount, price, maxTicks, 0L, TradeStatus.STARTED, settle.getPosition().getWorld(), 0,SettleType.NONE);
		settle.getTrader().makeBuyOrder(buyOrder);
	}

	@Override
	public boolean canExecute()
	{
		if (rModel.getSettlements().getSettlement(settleId) != null)
		{
			return true;
		}
return false;
	}

}
