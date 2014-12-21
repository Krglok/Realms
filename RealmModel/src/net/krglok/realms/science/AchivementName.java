package net.krglok.realms.science;

/**
 * <pre>
 * this are the names of objects the player can reach
 * the names will be combined with a type for a individual achievement
 * not all are necessary for techlevel rankup
 * 
 * Hint: the underscore <_> is not allowed in AchivementName
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
	TECH0,
	TECH1,
	TECH2,
	TECH3,
	TECH4,
	TECH5,
	TECH6,
	TECH7,
	NOBLE0,
	NOBLE1,
	NOBLE2,
	NOBLE3,
	NOBLE4
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
