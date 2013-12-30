package net.krglok.realms.core;

/**
 * 
 * @author oduda
 * 
 */
public class Barrack
{
	private Boolean isEnabled;
	private int unitMax;
	private UnitList unitList;

	public Barrack(int unitMax)
	{
		isEnabled=false;
		this.unitMax  = unitMax;
		unitList = new UnitList();
	}

	public Boolean getIsEnabled()
	{
		return isEnabled;
	}

	/**
	 * 
	 * Set the enabled Status , no check for conditions
	 * 
	 * @param isEnabled
	 */
	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public int getUnitMax()
	{
		return unitMax;
	}

	public void setUnitMax(int unitMax)
	{
		this.unitMax = unitMax;
	}

	public UnitList getUnitList()
	{
		return unitList;
	}

	public void setUnitList(UnitList unitList)
	{
		this.unitList = unitList;
	}

	public void addUnit(Unit unit)
	{
		unitList.put(unit.getUnitType().name(),unit);
	}

	public Boolean setUnitCount(UnitType unitType, int value)
	{
		return unitList.addUnitCount(unitType, value);
	}
}
