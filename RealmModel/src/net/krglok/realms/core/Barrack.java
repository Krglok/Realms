package net.krglok.realms.core;

import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.UnitList;

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
	private UnitList unitList;
	private int power;
	private int powerMax;
	private int powerPool;

	public Barrack(int unitMax)
	{
		isEnabled=false;
		this.unitMax  = unitMax;
		unitList = new UnitList();
		setPower(10);
		setPowerMax(100);
		setPowerPool(0);
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

	public void addUnitList(UnitList addList)
	{
		for (NpcData unit : addList)
		{
			this.unitList.add(unit); 
		}
	}

	public boolean addUnit(NpcData unit)
	{
		if (unitList.size()+1 < unitMax)
		{
			unitList.add(unit);
			return true;
		}
		return false;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) 
	{
		this.power = power;
	}

	public void addPower(int value) 
	{
		if (power < powerMax)
		{
			this.power = this.power + value;
		}
	}

	public int getPowerMax() {
		return powerMax;
	}

	public void setPowerMax(int powerMax) 
	{
		this.powerMax = powerMax;
	}

	public int getPowerPool() {
		return powerPool;
	}

	public void setPowerPool(int powerPool) 
	{
		this.powerPool = powerPool;
	}

	public void addPowerPool(int value) 
	{
		this.powerPool = this.powerPool + value;
	}

}
