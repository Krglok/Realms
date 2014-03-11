package net.krglok.realms.unit;

import java.util.HashMap;

/**
 * Die UnitList enthält die individuellen Units.
 * Jede Unit wird einzeln verwaltet, weil ansonsten keine visualisierung mit NPC
 * und keine individuelle Verfolgung bei Kämpfen möglich ist. 
 * @author Windu
 *
 */
public class UnitList extends  HashMap<Integer, Unit> 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2750568392824062736L;

	
	/**
	 * add unit to the list if in not contain in list
	 * otherwise return false
	 * @param unit
	 * @return true if added to list otherwise false
	 */
	public boolean addUnit(Unit unit)
	{
		if (this.containsKey(unit.getId()) == false)
		{
			this.put(unit.getId(), unit);
			return true;
		}
		return false;
	}

	
}
