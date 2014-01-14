package net.krglok.realms.core;

public enum TradeStatus
{

	NONE,
	READY,
	STARTED,
	FULFILL,
	DECLINE,
	WAIT;
	
	public static TradeStatus getTradeStatus(String name)
	{
		for (TradeStatus t : TradeStatus.values())
		{
			if (name.equalsIgnoreCase(name))
			{
				return t;
			}
		}
		
		return NONE;
	}
}
