package net.krglok.realms.core;

/**
 * Die TraderTransaction ist die Basis fuer das Handelssystem.
 * Es enthaelt das Item, die Menge und den Preis, 
 * sowie einen AblaufStatus und AblaufSteuerung
 * isStarted zeigt an , dass der Auftrag aktiv ist
 * 
 * der Parameter world kontrolliert den Multiworld zugriff
 * 
 * @author Windu
 *
 */
public class TradeOrder extends ItemPrice
{
	protected int id;
	protected TradeType tradeType;
	protected int maxTicks; 
	protected int tickCount;
	protected TradeStatus status;
	protected String world;
	
	
	public TradeOrder()
	{
		super("", 0, 0.0);
		setId(0);
		setTradeType(TradeType.NONE);
		maxTicks  = 0;
		tickCount = 0;
		status = TradeStatus.NONE;
		world ="";
	}

	
	


	public TradeOrder(int id, TradeType tradeType, String itemRef , int value, double price, 
			int maxTicks, int tickCount,
			TradeStatus status, String world)
	{
		super(itemRef, value, price);
		this.setId(id);
		this.setTradeType(tradeType);
		this.maxTicks = maxTicks;
		this.tickCount = tickCount;
		this.status = status;
		this.world =world;
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
	
	public int getRemainPeriod()
	{
		return (maxTicks - tickCount);
		
	}
	
	public static int calcMaxTickforDay(int day)
	{
		return (int) ConfigBasis.GameDay;
	}
}
