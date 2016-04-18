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
	NONE,
	HOME,
	WHEAT,
	WOODCUTTER,
	SHEPHERD,
	QUARRY,
	HALL,
	CARPENTER,
	CABINETMAKER,
	BAKERY,
	HOESHOP,
	KNIFESHOP,
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
	KEEP,
	CASTLE,
	STRONGHOLD,
	PALACE,
	NOBLE0,
	NOBLE1,
	NOBLE2,
	NOBLE3,
	NOBLE4,
	VTECH0,
	VTECH1,
	VTECH2,
	CABIN,
	FIELD,
	CHURCH,
	WELL,
	SMITH,
	BUTCHER,
	SMALLIBRARY,
	LARGEHOUSE,
	VILLAGE,
	MONEYBANK,
	XPBANK
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
