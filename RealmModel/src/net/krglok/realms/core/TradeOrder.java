package net.krglok.realms.core;

/**
 * <pre>
 * Die TraderTransaction ist die Basis fuer das Handelssystem.
 * Es enthaelt das Item, die Menge und den Preis, 
 * sowie einen AblaufStatus und AblaufSteuerung
 * isStarted zeigt an , dass der Auftrag aktiv ist
 * 
 * der Parameter world kontrolliert den Multiworld zugriff
 * 
 * </pre>
 * @author Windu
 *
 */
public class TradeOrder extends ItemPrice
{
	private int id;
	private TradeType tradeType;
	private long maxTicks; 
	private long tickCount;
	private TradeStatus status;
	private String world;
	private int targetId;
	
	
	public TradeOrder()
	{
		super("", 0, 0.0);
		setId(0);
		setTradeType(TradeType.NONE);
		maxTicks  = 0;
		tickCount = 0;
		status = TradeStatus.NONE;
		world ="";
		targetId = 0;
	}



	public TradeOrder(int id, TradeType tradeType, String itemRef , int value, double price, 
			long maxTicks, long tickCount,
			TradeStatus status, String world, int targetId)
	{
		super(itemRef, value, price);
		this.setId(id);
		this.setTradeType(tradeType);
		this.maxTicks = maxTicks;
		this.tickCount = tickCount;
		this.status = status;
		this.world =world;
		this.targetId = targetId;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}


	public TradeType getTradeType()
	{
		return tradeType;
	}


	public void setTradeType(TradeType tradeType)
	{
		this.tradeType = tradeType;
	}

	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public TradeStatus getStatus()
	{
		return status;
	}


	public void setStatus(TradeStatus status)
	{
		this.status = status;
	}


	public long getMaxTicks()
	{
		return maxTicks;
	}



	public void setMaxTicks(long maxTicks)
	{
		this.maxTicks = maxTicks;
	}



	public long getTickCount()
	{
		return tickCount;
	}



	public void setTickCount(long tickCount)
	{
		this.tickCount = tickCount;
	}



	public int getTargetId()
	{
		return targetId;
	}



	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}



	public void runTick(int value)
	{
		this.tickCount = this.tickCount + value;
		if (isDecline())
		{
			status = TradeStatus.DECLINE;
		}
	}
	
	public void runTick()
	{
		if (status == TradeStatus.STARTED)
		{
			this.tickCount++;
			if (this.tickCount >= this.maxTicks)
			{
				status = TradeStatus.DECLINE;
			}
		}
	}
	
	public boolean isDecline()
	{
		if (status == TradeStatus.STARTED)
		{
			return (tickCount > maxTicks);
		} 
		return false;
	}
	
	public boolean isStarted()
	{
		if (status == TradeStatus.STARTED )
		{
			return true;
		} 
		return false;
	}
	
	public long getRemainPeriod()
	{
		return (maxTicks - tickCount);
		
	}
	
	public static int calcMaxTickforDay(int day)
	{
		return (int) ConfigBasis.GameDay;
	}
}
