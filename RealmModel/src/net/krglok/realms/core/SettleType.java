package net.krglok.realms.core;

/**
 * this are the types of settlements build in a hierarchical List of values
 * 
 * @author oduda
 *
 */
public enum SettleType
{
	NONE (0),
	HAMLET (10),
	TOWN (100),
	CITY (200),
	CASTLE (250),
	METROPOLIS (300),
	CAMP (500)
	;
	
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
		
		return SettleType.NONE;
		
	}

}
