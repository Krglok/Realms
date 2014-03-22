package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;

public class UnitSettler extends AbstractUnit
{


	public UnitSettler()
	{
		super();
		unitType = UnitType.SETTLER;
		armor = 1;
		speed = 5;
		offense = 1;
		defense = 3;
		offenseRange = 1;
		maxStorage = 27;
		
		// required
		requiredItems = new ItemList();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitList();
		
		//consum
		consumItems = new ItemList();
		consumCost  = 0.0;
		consumTime  = 10;
	}

	@Override
	public void train()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attack()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void defend()
	{
		// TODO Auto-generated method stub

	}

}
