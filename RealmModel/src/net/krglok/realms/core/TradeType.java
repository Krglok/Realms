package net.krglok.realms.core;

public enum TradeType
{
	NONE,
	PURCHASE,
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
