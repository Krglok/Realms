package net.krglok.realms.core;

/**
 * this are the types of settlements build in a hierarchical List of values
 * @author oduda
 *
 */
public enum SettleType
{
	SETTLE_NONE (0),
	SETTLE_HAMLET (10),
	SETTLE_TOWN (100),
	SETTLE_CITY (200),
	SETTLE_CASTLE (250),
	SETTLE_METRO (300);
	
	private final int value;
	
	SettleType(int value)
	{
		this.value = value;
	}
	
	int getValue()
	{
		return value;
	}
	
	public static SettleType getSettleType(String name)
	{
		for (SettleType settleType : SettleType.values())
		{
			if (settleType.name().equals(name))
			{
				return settleType;
			}
		}
		
		return SettleType.SETTLE_NONE;
		
	}

}
