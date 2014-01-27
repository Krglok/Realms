package net.krglok.realms.core;

public enum BuildingGroup
{
	NONE (0),
	HALL (10),
	HOME (100),
	PRODUCTION (200),
	WAREHOUSE (300),
	TRADER (400),
	MILITARY (500),
	ENTERTAIN (600),
	EDUCATION (700),
	RELIGION (800),
	KEEP (900),
	GOVERNMENT (1000);

	private final int value;

	BuildingGroup(int value)
	{
		this.value = value;
	}
	
	int getValue()
	{
		return value;
	}
	
}
