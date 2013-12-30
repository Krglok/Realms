package core;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author oduda
 *
 */
public class UnitList 
{
	private Map<String,Unit> unitList; 
	
	public UnitList()
	{
		setUnitList(new HashMap<String,Unit>());
	}

	public Map<String,Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(Map<String,Unit> unitList) {
		this.unitList = unitList;
	}

	public Boolean isEmpty()
	{
		return unitList.isEmpty();		
	}
	
	public Unit addUnitType(UnitType uType)
	{
		Unit unit;
		unit = new Unit();
		unit.setUnitType(uType);
		unitList.put(uType.toString(), unit);
		return unit;
	}
}
