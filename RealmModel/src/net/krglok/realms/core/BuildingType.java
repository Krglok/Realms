package net.krglok.realms.core;

public enum BuildingType
{
	BUILDING_NONE (0),
	BUILDING_HOME (100),
	BUILDING_PROD (200),
	BUILDING_BAUERNHOF (210),
	BUILDING_WERKSTATT (220),
	BUILDING_BAECKER (230),
	BUILDING_WAREHOUSE (300),
	BUILDING_TRADER (400),
	BUILDING_MILITARY (500),
	BUILDING_HALL (600),
	BUILDING_ENTERTAIN (610),
	BUILDING_EDUCATION (620),
	BUILDING_RELIGION (630),
	BUILDING_KEEP (700),
	BUILDING_GOVERNMENT (800);
	
	private final int value;

	BuildingType(int value)
	{
		this.value = value;
	}
	
	int getValue()
	{
		return value;
	}
	
	public static BuildingType getBuildingType(String name)
	{
		for (BuildingType buildingType : BuildingType.values())
		{
			if (buildingType.name().equals(name))
			{
				return buildingType;
			}
		}
		
		return BuildingType.BUILDING_NONE;
	}
}
