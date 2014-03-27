package net.krglok.realms.core;

//<<<<<<< HEAD
//=======
import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.UnitTypeList;
//>>>>>>> origin/PHASE2

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
//<<<<<<< HEAD
//	private UnitList unitList;
//=======
	private UnitTypeList unitList;
//>>>>>>> origin/PHASE2

	public Barrack(int unitMax)
	{
		isEnabled=false;
		this.unitMax  = unitMax;
//<<<<<<< HEAD
//		unitList = new UnitList();
//=======
		unitList = new UnitTypeList();
//>>>>>>> origin/PHASE2
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

//<<<<<<< HEAD
//	public UnitList getUnitList()
//	{
//		return unitList;
//	}
//
//	public void setUnitList(UnitList unitList)
//	{
//		this.unitList = unitList;
//	}
//
//	public void addUnit(UnitItem unitItem)
//	{
//		unitList.put(unitItem.getID(),unitItem);
//	}
//=======
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
//>>>>>>> origin/PHASE2

}
