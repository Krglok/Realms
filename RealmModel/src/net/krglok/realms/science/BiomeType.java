package net.krglok.realms.science;

public enum BiomeType
{
	NONE,
	DESERT,
	EXTREME,
	FOREST,
	HELL,
	JUNGLE,
	MOUNTAIN,
	OCEAN,
	PLAIN,
	SWAMNP;
	
	
	public static BiomeType getBiomeType(String name)
	{
		for (BiomeType bType : BiomeType.values())
		{
			if (bType.name().equalsIgnoreCase(name))
			{
				return bType;
			}
		}
		return NONE;
	}
}
