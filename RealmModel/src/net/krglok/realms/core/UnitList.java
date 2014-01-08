package net.krglok.realms.core;

import java.util.HashMap;

/**
 * 
 * @author oduda
 *
 */
public class UnitList extends HashMap<String,Unit>
{
//	private Map<String,Unit> unitList;
//	private int unitCount;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 574604357282114718L;

	public UnitList()
	{
		initUnitList();
	}

	public void initUnitList() 
	{
		this.put(UnitType.UNIT_WORKER.name(),new Unit(UnitType.UNIT_WORKER));
		this.put(UnitType.UNIT_COW.name(),new Unit(UnitType.UNIT_COW));
		this.put(UnitType.UNIT_HORSE.name(),new Unit(UnitType.UNIT_HORSE));
		this.put(UnitType.UNIT_WAGON.name(),new Unit(UnitType.UNIT_WAGON));
		this.put(UnitType.UNIT_MILITIA.name(),new Unit(UnitType.UNIT_MILITIA));
		this.put(UnitType.UNIT_SCOUT.name(),new Unit(UnitType.UNIT_SCOUT));
		this.put(UnitType.UNIT_ARCHER.name(),new Unit(UnitType.UNIT_ARCHER));
		this.put(UnitType.UNIT_LINF.name(),new Unit(UnitType.UNIT_LINF));
		this.put(UnitType.UNIT_HINF.name(),new Unit(UnitType.UNIT_HINF));
		this.put(UnitType.UNIT_KNIGHT.name(),new Unit(UnitType.UNIT_KNIGHT));
		this.put(UnitType.UNIT_COMMANDER.name(),new Unit(UnitType.UNIT_COMMANDER));
	}

	
	public Unit getUnit(UnitType unitType)
	{
		for(Unit unit : this.values())
		{
			if (unit.getUnitType() == unitType)
			{
				return unit;
			}
		}
		return null;
	}
	
	/**
	 * adds unit to the list , check no conditions
	 * overwrite existing unit 
	 * @param unit
	 * @return 
	 */
	public void addUnit(Unit unit)
	{
		String key = unit.getUnitType().name();
		if (!this.containsKey(key))
		{
			this.put(unit.getUnitType().name(), unit);
		}
	}
	
//	/**
//	 * update actual unit with unit, replace
//	 * @param unit
//	 */
//	public Boolean updateUnit(Unit unit)
//	{
//		String key = unit.getUnitType().name();
//		if (this.containsKey(key))
//		{
//			this.put(unit.getUnitType().name(), unit);
//			return true;
//		}
//		return false;
//	}
	
	/**
	 * add value to unit.count
	 * unitType are unique in the list
	 * @param unitType
	 * @param value
	 * @return 
	 */
	public Boolean addUnitCount(UnitType unitType, int value)
	{
		String key = unitType.name();
		if (this.containsKey(key))
		{
			Unit unit = this.get(key);
			unit.addUnitCount(value);
			this.put(key, unit);
			return true;
		}
		return false;
		
	}

	/**
	 *  
	 * @return sum of all units in List
	 */
	public int unitSum()
	{
		int value = 0;
		for (Unit unit : this.values())
		{
			value = value + unit.getUnitCount();
		}	
		return value;
	}
}
