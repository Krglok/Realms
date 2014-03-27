package net.krglok.realms.unit;

import java.util.ArrayList;

public class UnitList extends ArrayList<Unit>
{
	private static final long serialVersionUID = 8833127238708166553L;

	
	public UnitList getUnitTypeList(UnitType uType)
	{
		UnitList subList = new UnitList();
		for (Unit unit : this)
		{
			if (unit.getUnitType() == uType)
			{
				subList.add(unit);
			}
		}
		
		return subList;
	}
	
}
