package net.krglok.realms.builder;

import java.util.ArrayList;

import org.bukkit.Material;


/**
 * <pre>
 * This are the Types of BuildPlan the player can build
 * For some be are standard BuildPlan defined
 * </pre>
 * @author Windu
 *
 */

//BUILDING_HALL (50),
//BUILDING_HOME (100),
//BUILDING_PROD (200),
//BUILDING_PRODMILITARY (300),
//BUILDING_WAREHOUSE (300),
//BUILDING_TRADER (400),
//BUILDING_MILITARY (500),
//BUILDING_ENTERTAIN (600),
//BUILDING_EDUCATION (700),
//BUILDING_RELIGION (800),
//BUILDING_KEEP (900),
//BUILDING_GOVERNMENT (1000)

public enum BuildPlanType
{
	NONE (0),
	COLONY (1),
	PILLAR (2),
	WALL (3),
	LANE (4),
	ROAD (5),
	STEEPLE (6),
	FORT(7),
	SHIP_0 (20),
	BOATYARD(40),
	HALL (51),
	WAREHOUSE (52),
	TOWNHALL(53),
	GATE(60),
	CITYGATE(61),
	HOME (101),
	HOUSE (110),
	MANSION (120),
	CABIN (130),
	COTTAGE(131),
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
	FARMHOUSE (224),
	PIGPEN (225),
	FARM (226),
	DUSTCUTTER (227),
	NETHERQUARRY (228),
	MUSHROOM (229),
	KITCHEN (230),
	COALMINE(231),
	IRONMINE(232),
	EMERALDMINE (233),
	GOLDMINE (234),
	GOLDSMELTER (235),
	HUNTER (236),
	TAMER (237),
	SPIDERSHED (238),
	DIAMONDMINE (239),
	STONEYARD(240),
	BLACKSMITH (301),
	TANNERY (302),
	BOWMAKER (310),
	FLETCHER (311),
	HORSEBARN (320),
	WEAPONSMIH (330),
	ARMOURER (331),
	CHAINMAKER (340),
	TRADER (401),
	GUARDHOUSE (501),
	WATCHTOWER (502),
	DEFENSETOWER(503),
	ARCHERY (504),
	BARRACK (510),
	TOWER (520),
	CASERN (530),
	GARRISON (531),
	TAVERNE(601),
	BIBLIOTHEK(602),
	LIBRARY(603),
	KEEP (901),
	CASTLE(902),
	STRONGHOLD(903),
	PALACE(904),
	HEADQUARTER(910)
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

	/**
	 * check for groups to build automatically
	 * 
	 * @param buildPlanType
	 * @return
	 */
	public static int getBuildGroup(BuildPlanType buildPlanType)
	{
		int group = buildPlanType.getValue();
		if (group == 0) return 0;
		if (group < 10)  return 1;
		if (group  < 100) return 10;
		return group / 100 * 100;
	}
}
