package net.krglok.realms.manager;


public enum ReputationType
{
	NONE,
	BUILD,
	MANAGER,
	SETTLER,
	TRADE;
	
	public static ReputationType getBuildingType(String name)
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
