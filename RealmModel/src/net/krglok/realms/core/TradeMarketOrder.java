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

	private int settleID;
	
	public TradeMarketOrder()
	{
		super();
		this.setSettleID(0);
	}

	public TradeMarketOrder(int settleId, TradeOrder order)
	{
		super();
		this.setSettleID(settleId);
		this.setId(order.getId());
		this.setItemRef(order.ItemRef());
		this.setValue(order.value());
		this.setBasePrice(order.getBasePrice());
		this.setMaxTicks(order.getMaxTicks());
		this.setTickCount(0);
		this.setStatus(order.getStatus());
		this.setWorld(order.getWorld());
		this.setTargetId(order.getTargetId());
	}
	
	
	
	public TradeMarketOrder(int settleID, int id, TradeType tradeType, String itemRef , int value, double price, 
			long maxTicks, long tickCount,
			TradeStatus status, String world, int targetId)
	{
		super();
		this.setSettleID(settleID);
		this.setId(id);
		this.setItemRef(itemRef);
		this.setValue(value);
		this.setBasePrice(price);
		this.setMaxTicks(maxTicks);
		this.setTickCount(0);
		this.setStatus(status);
		this.setWorld(world);
		this.setTargetId(targetId);
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
