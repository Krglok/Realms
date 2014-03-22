package net.krglok.realms.core;

import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.UnitTypeList;

/**
 * not yet implemented
 * @author oduda
 * 
 */
public class Barrack 
{
	private static final long serialVersionUID = 3574102060452319954L;
	private Boolean isEnabled;
	private int unitMax;
	private UnitTypeList unitList;

	public Barrack(int unitMax)
	{
		isEnabled=false;
		this.unitMax  = unitMax;
		unitList = new UnitTypeList();
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

	public UnitTypeList getUnitList()
	{
		return unitList;
	}

	public void setUnitList(UnitTypeList unitList)
	{
		this.unitList = unitList;
	}

	public void addUnit(IUnit unit)
	{
		unitList.add(unit);
	}

}
