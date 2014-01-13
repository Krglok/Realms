package net.krglok.realms.core;

/**
 * Die TraderTransaction ist die Basis fuer das Handelssystem.
 * Es enthaelt das Item, die Menge und den Preis, 
 * sowie einen AblaufStatus und AblaufSteuerung
 * @author Windu
 *
 */
public class TradeTransaction
{
	private TradeType tradeType;
	private ItemPrice itemPrice;
	private int maxTicks; 
	private int tickCount;
	private boolean isStarted;
	private boolean isReady;
	private boolean isDecline;
	
	
	public TradeTransaction()
	{
		tradeType = TradeType.NONE;
		itemPrice = new ItemPrice("", 0.0);
		maxTicks  = 0;
		isStarted = false;
		isReady   = false;
		isDecline = false;
	}


	public TradeTransaction(TradeType tradeType, ItemPrice itemPrice,
			int maxTicks, int tickCount)
	{
		super();
		this.tradeType = tradeType;
		this.itemPrice = itemPrice;
		this.maxTicks = maxTicks;
		this.tickCount = tickCount;
		isStarted = false;
		isReady   = false;
		isDecline = false;
	}
	

	public TradeTransaction(TradeType tradeType, ItemPrice itemPrice,
			int maxTicks, int tickCount, boolean isStarted)
	{
		super();
		this.tradeType = tradeType;
		this.itemPrice = itemPrice;
		this.maxTicks = maxTicks;
		this.tickCount = tickCount;
		this.isStarted = isStarted;
		this.isReady = false;
		this.isDecline = false;
	}


	public void runTick(int value)
	{
		this.tickCount = this.tickCount + value;
		if (isRise())
		{
			this.isReady = true;
		}
		if (isDecline())
		{
			this.isDecline = true;
		}
	}
	
	public void runTick()
	{
		if (isStarted)
		{
			this.tickCount++;
		}
	}
	
	public boolean isRise()
	{	
		if (isStarted)
		{
			return (tickCount == maxTicks);
		} else
		{
			return false;
		}
	}
	
	public boolean isDecline()
	{
		if (isStarted)
		{
			return (tickCount > maxTicks);
		} else
		{
			return false;
		}
	}
	
	public boolean isStarted()
	{
		if ((this.isReady == false) && (isDecline == false) )
		{
			return isStarted;
		} else
		{
			return false;
		}
	}
	
}
