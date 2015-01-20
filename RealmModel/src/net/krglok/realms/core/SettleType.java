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
	CLAIM(10),
	ENCLAVE(20),
	HAMLET (110),
	TOWN (120),
	CITY (130),
	METROPOLIS (140),
	CAMP (210),
	FORTRESS (310),
	LEHEN_1(510),
	LEHEN_2(520),
	LEHEN_3(530),
	LEHEN_4(540)
	;
	
	private final int value;
	
	SettleType(int value)
	{
		this.value = value;
	}
	
	public int getValue()
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
