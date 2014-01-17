package net.krglok.realms.model;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;

public class SellOrder implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.SELLITEM;
	private RealmModel rModel;
	private int settleId;
	private String itemRef;
	private int amount;
	private double price;
	private int delayDays;

	
	
	public SellOrder(RealmModel rModel, int settleId, String itemRef, int amount, double price, int delayDays)
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

	@Override
	public String[] getParaTypes()
	{
		return new String[] { RealmModel.class.getName() , int.class.getName() , String.class.getName() , int.class.getName() , int.class.getName()  };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		rModel.getTradeMarket();
		//SellOrder(rModel, settleId, itemRef, amount, delayDays);
		int id = TradeMarket.getLastNumber()+1;
		if (delayDays < 1)
		{
			delayDays = 1;
		}
		long maxTicks = ConfigBasis.GameDay * delayDays;
		TradeOrder sellOrder = new TradeOrder(id , TradeType.SELL, itemRef, amount, price, maxTicks, 0L, TradeStatus.STARTED, "", 0);
		settle.getTrader().makeSellOrder(rModel.getTradeMarket(), settleId, sellOrder);
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
