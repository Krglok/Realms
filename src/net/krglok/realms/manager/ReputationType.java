package net.krglok.realms.manager;


public enum ReputationType
{
	NONE (0),
	DONATION (20),
	REQUIRED (30),
	VALUABLE (20),
	FOOD(30),
	MONEY (30),
	TRADE (50),
	MEMBER (50)
	;
	
	private final int value;

	ReputationType(int maxValue)
	{
		this.value = maxValue;
	}
	
	public int getValue()
	{
		return value;
	}

	
	public static ReputationType getReputationType(String name)
	{
		for (ReputationType repType : ReputationType.values())
		{
			if (repType.name().equals(name))
			{
				return repType;
			}
		}
		return NONE;
	}
	
}
