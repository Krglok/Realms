package net.krglok.realms.unit;


public enum UnitType
{
	NONE,
	SETTLER,
	COW,
	WAGON,
	HORSE,
	MILITIA,
	ARCHER,
	SCOUT,
	LIGHT_INFANTRY,
	HEAVY_INFANTRY,
	KNIGHT,
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
	
}
