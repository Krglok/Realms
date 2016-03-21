package net.krglok.realms.core;

/**
 * define the type of trade orders
 * 
 * @author Windu
 *
 */
public enum TradeType
{
	NONE,
	BUY,
	SELL,
	TRANSPORT;
	
	public static TradeType getTradeTaskType(String name)
	{
		for (TradeType t : TradeType.values())
		{
			if (name.equalsIgnoreCase(t.name()))
			{
				return t;
			}
		}
		
		return NONE; 
	}
}
