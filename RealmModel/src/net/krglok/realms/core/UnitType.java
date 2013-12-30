package net.krglok.realms.core;

/**
 * 
 * @author oduda
 * 
 */
public enum UnitType
{
	UNIT_NONE(0),
	UNIT_WORKER(10),
	UNIT_COW(20),
	UNIT_HORSE(30),
	UNIT_WAGON(40),
	UNIT_MILITIA(100),
	UNIT_SCOUT(110),
	UNIT_ARCHER(120),
	UNIT_LINF(130),
	UNIT_HINF(140),
	UNIT_KNIGHT(150),
	UNIT_COMMANDER(200);

	private final int value;

	UnitType(int value)
	{
		this.value = value;
	}

	/**
	 * Get the actual Value of the UnitType
	 * 
	 * @return integer
	 */
	int getValue()
	{
		return value;
	}

	/**
	 * @return the next Unittype in the hierarchie 
	 */
	UnitType rankUp()
	{
		switch (this)
		{
		case UNIT_NONE:
			return UNIT_WORKER;
		case UNIT_WORKER:
			return UNIT_MILITIA;
		case UNIT_MILITIA:
			return UNIT_LINF;
		case UNIT_LINF:
			return UNIT_HINF;
		case UNIT_HINF:
			return UNIT_KNIGHT;
		default:
			return this;
		}
	}


}
