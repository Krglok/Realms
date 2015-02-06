package net.krglok.realms.unit;


public enum UnitType
{
	NONE,
	SETTLER,
	RAG_TAG,
	SLAVE,
//	COW,
//	WAGON,
//	HORSE,
	ROOKIE,
	MILITIA,
	ARCHER,
	SCOUT,
	LIGHT_INFANTRY,
	HEAVY_INFANTRY,
	KNIGHT,
	CAVALRY,
	COMMANDER;
	
	/**
	 * 
	 * @param name
	 * @return  unitType or NONE
	 */
	public static UnitType getBuildPlanType(String name)
	{
		for (UnitType uType : UnitType.values())
		{
			if (uType.name().equals(name))
			{
				return uType;
			}
		}
		
		return UnitType.NONE;
	}

	public boolean contains(String value)
	{
		for (UnitType uType : UnitType.values())
		{
			if (uType.name().equals(value))
			{
				return true;
			}
		}
		return false;
	}
}
