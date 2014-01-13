package net.krglok.realms.core;

public class TradeMarketOrder extends TradeOrder
{

	private int settleID;
	
	public TradeMarketOrder()
	{
		super();
		this.setSettleID(0);
	}

	public int getSettleID()
	{
		return settleID;
	}

	public void setSettleID(int settleID)
	{
		this.settleID = settleID;
	}
	
	
}
