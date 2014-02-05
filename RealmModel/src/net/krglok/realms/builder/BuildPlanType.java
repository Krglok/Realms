package net.krglok.realms.builder;

import java.util.ArrayList;


/**
 * <pre>
 * This are the Types of BuildPlan the player can build
 * For some be are standard BuildPlan defined
 * </pre>
 * @author Windu
 *
 */
public enum BuildPlanType
{
	NONE (0),
	COLONY (1),
	PILLAR (2),
	WALL (3),
	LANE (4),
	ROAD (5),
	STEEPLE (6),
	HALL (11),
	WAREHOUSE (12),
	HOME (100),
	HOUSE (110),
	MANSION (120),
	WHEAT(202),
	WOODCUTTER (203),
	QUARRY (204),
	SHEPHERD (205),
	CARPENTER (206),
	CABINETMAKER (207),
	FISHERHOOD (208),
	AXESHOP (209),
	PICKAXESHOP (210),
	HOESHOP (211),
	KNIFESHOP (212),
	SPADESHOP (213),
	BAKERY (216),
	CHARBURNER (217),
	COWSHED (218),
	CHICKENHOUSE (219),
	BRICKWORK (220),
	STONEMINE (221),
	SMELTER (222),
	WORKSHOP (223),
	FARM (224),
	PIGPEN (225),
	FARMHOUSE (310),
	BAUERNHOF (320),
	TRADER (401),
	TAVERNE(601)
	;
	
	private final int value;

	BuildPlanType(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}

	public static BuildPlanType getBuildPlanType(String name)
	{
		for (BuildPlanType buildingType : BuildPlanType.values())
		{
			if (buildingType.name().equals(name))
			{
				return buildingType;
			}
		}
		
		return BuildPlanType.NONE;
	}

	/**
	 * give a List of all BuildPlanType 
	 * the list is sorted by number ascending
	 * @return  list<BulspPlanType>
	 */
	public static ArrayList<BuildPlanType> getBuildPlanOrder()
	{
		ArrayList<BuildPlanType>  buildPlanTypes = new ArrayList<BuildPlanType>();
		for (int i = 0; i < 1000; i++)
		{
			for (BuildPlanType bType : BuildPlanType.values())
			{
				if (bType.getValue() == i)
				{
					buildPlanTypes.add(bType);
				}
			}
		}
		
		return buildPlanTypes;
	}

	/**
	 * give a List of BuildPlanType for the group
	 * the list is sorted by number ascending
	 * @param group
	 * @return  list<BulspPlanType> or null
	 */
	public static ArrayList<BuildPlanType> getBuildPlanGroup(int group)
	{
		ArrayList<BuildPlanType>  buildPlanTypes = new ArrayList<BuildPlanType>();
		for (int i = 0; i < 1000; i++)
		{
			for (BuildPlanType bType : BuildPlanType.values())
			{
				if ((bType.getValue() == i) && ((bType.getValue() / 100) == (group/100) ))
				{
					buildPlanTypes.add(bType);
				}
			}
		}
		return buildPlanTypes;
	}
	
}
