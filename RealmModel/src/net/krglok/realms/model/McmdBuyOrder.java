package net.krglok.realms.model;

import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.kingdom.Lehen;

public class McmdBuyOrder implements iModelCommand
{
	

	private ModelCommandType commandType = ModelCommandType.BUYITEM;
	private RealmModel rModel;
	private int settleId;
	private SettleType settleType;
	private String itemRef;
	private int amount;
	private double price;
	private int delayDays;
	

	
	public McmdBuyOrder(RealmModel rModel, int settleId, 
			String itemRef,
			int amount, double price, int delayDays, SettleType settleType)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.settleType = settleType;
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
	
	public SettleType getSettleType()
	{
	  return this.settleType;	
	}
	
	
	@Override
	public String[] getParaTypes()
	{
		return new String[] { RealmModel.class.getName() , int.class.getName() , String.class.getName() , int.class.getName() , int.class.getName(), SettleType.class.getName()  };
	}

	@Override
	public void execute()
	{
		AbstractSettle aSettle = null;
		if (SettleType.isLehen(settleType) == true)
		{
			aSettle = rModel.getData().getLehen().getLehen(settleId);
		} else
		{
			aSettle = rModel.getSettlements().getSettlement(settleId);
		}
		
		if (aSettle != null)
		{
			int id = rModel.getTradeMarket().nextLastNumber();
			if (delayDays < 1)
			{
				delayDays = 1;
			}
			long maxTicks = ConfigBasis.GameDay * delayDays;
			TradeOrder buyOrder = new TradeOrder(id , TradeType.SELL, itemRef, amount, price, maxTicks, 0L, TradeStatus.STARTED, aSettle.getPosition().getWorld(), 0,settleType);
			aSettle.getTrader().makeBuyOrder(buyOrder);
		}
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
