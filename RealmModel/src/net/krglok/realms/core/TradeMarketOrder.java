package net.krglok.realms.core;

/**
 * <pre>
 * this are the Sell orders in the trademarket.
 * the orders have a sourceID, targetId and a runtime.
 * they decline automatically 
 * </pre>
 * @author Windu
 *
 */
public class TradeMarketOrder extends TradeOrder
{

	protected Integer settleID;
	protected SettleType settleType;
	
	public TradeMarketOrder()
	{
		super();
		this.setSettleID(0);
	}

	public TradeMarketOrder(int settleId, SettleType settleType, TradeOrder order)
	{
		super();
		this.setSettleID(settleId);
		this.settleType = settleType;
		this.setId(order.getId());
		this.setItemRef(order.ItemRef());
		this.setValue(order.value());
		this.setBasePrice(order.getBasePrice());
		this.setMaxTicks(order.getMaxTicks());
		this.setTickCount(0);
		this.setStatus(order.getStatus());
		this.setWorld(order.getWorld());
		this.setTargetId(order.getTargetId());
		this.setTargetType(order.getTargetType());
	}
	
	
	
	public TradeMarketOrder(int settleID, SettleType settleType, int id, TradeType tradeType, String itemRef , int value, double price, 
			long maxTicks, long tickCount,
			TradeStatus status, String world, int targetId, SettleType targetType)
	{
		super();
		this.setSettleID(settleID);
		this.settleType = settleType;
		this.setId(id);
		this.setItemRef(itemRef);
		this.setValue(value);
		this.setBasePrice(price);
		this.setMaxTicks(maxTicks);
		this.setTickCount(0);
		this.setStatus(status);
		this.setWorld(world);
		this.setTargetId(targetId);
		this.setTargetType(targetType);
	}


	public int getSettleID()
	{
		return settleID;
	}

	public void setSettleID(int settleID)
	{
		this.settleID = settleID;
	}
	
	public SettleType getSettleType()
	{
		return settleType;
	}
	
	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	
	
}
