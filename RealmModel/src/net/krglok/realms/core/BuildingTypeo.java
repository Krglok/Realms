package net.krglok.realms.core;

/**
 * <pre>
 * the buildingType are used for production and recipe calculation
 * the append value is a group number
 * </pre>
 * @author oduda
 *
 */
public enum BuildingTypeo
{
	BUILDING_NONE (0)
//	BUILDING_HALL (1000),
//	BUILDING_HOME (100),
//	BUILDING_PROD (200),
//	BUILDING_WHEAT (200),
//	BUILDING_BAUERNHOF (200),
//	BUILDING_BAECKER (200),
//	BUILDING_WERKSTATT (200),
//	BUILDING_WAREHOUSE (300),
//	BUILDING_TRADER (400),
//	BUILDING_MILITARY (500),
//	BUILDING_ENTERTAIN (600),
//	BUILDING_EDUCATION (700),
//	BUILDING_RELIGION (800),
//	BUILDING_KEEP (900),
//	BUILDING_GOVERNMENT (1000)
	;
	
	private final int value;

	BuildingTypeo(int value)
	{
		this.value = value;
	}
	
	int getValue()
	{
		return value;
	}
	
	public static BuildingTypeo getBuildingType(String name)
	{
		for (BuildingTypeo buildingType : BuildingTypeo.values())
		{
			if (buildingType.name().equals(name))
			{
				return buildingType;
			}
		}
		
		return BuildingTypeo.BUILDING_NONE;
	}
	
	public static BuildingTypeo getBuildingType(int ref)
	{
		for (BuildingTypeo buildingType : BuildingTypeo.values())
		{
			if (buildingType.getValue() == ref)
			{
				return buildingType;
			}
		}
		
		return BuildingTypeo.BUILDING_NONE;
	}
}
