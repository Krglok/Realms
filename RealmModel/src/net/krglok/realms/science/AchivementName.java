package net.krglok.realms.science;

/**
 * <pre>
 * this are the names of objects the player can reach
 * the names will be combined with a type for a individual achievement
 * not all are necessary for techlevel rankup
 * 
 * @author Windu
 * </pre>
 */

public enum AchivementName
{
	HOME,
	WHEAT,
	WOODCUTTER,
	SHEPHERD,
	QUARRY,
	HALL,
	CARPENTER,
	CABINETMAKER,
	BAKERY,
	SHOP,
	WORKSHOP,
	TANNERY,
	BLACKSMITH,
	GUARDHOUSE,
	TOWNHALL,
	SMELTER,
	BARRACK,
	TOWER,
	HAMLET,
	TOWN,
	CITY,
	METROPOL,
	HEADQUARTER,
	KINGDOM,
	TECH_0,
	TECH_1,
	TECH_2,
	TECH_3,
	TECH_4,
	TECH_5,
	TECH_6,
	TECH_7,
	NOBLE_0,
	NOBLE_1,
	NOBLE_2,
	NOBLE_3,
	NOBLE_4
	;
	
	public static boolean contains(String value)
	{
		for (AchivementName aName : AchivementName.values())
		{
			if (aName.name().equalsIgnoreCase(value))
			{
				return true;
			}
		}
		return false;
	}
}
