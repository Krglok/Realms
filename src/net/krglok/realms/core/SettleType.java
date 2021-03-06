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
	SHIP_0(11),
	ENCLAVE(20),
	BERTH(30),
	VILLAGE(105),
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

	public static boolean isLehen(SettleType settleType)
	{
		if (settleType == LEHEN_1) return true;
		if (settleType == LEHEN_2) return true;
		if (settleType == LEHEN_3) return true;
		if (settleType == LEHEN_4) return true;
		return false;
	}

	public static boolean isRegiment(SettleType settleType)
	{
		if (settleType == CAMP) return true;
		return false;
	}

	public static boolean isFortress(SettleType settleType)
	{
		if (settleType == FORTRESS) return true;
		return false;
	}
	
}
