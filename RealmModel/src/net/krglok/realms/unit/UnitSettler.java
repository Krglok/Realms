package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;

public class UnitSettler extends AbstractUnit
{


	public UnitSettler()
	{
		super();
		unitType = UnitType.SETTLER;
		armor = 1;
		speed = 5;
		offense = 2;
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
	public ItemList initRequired() 
	{
		ItemList subList = new ItemList();
		
		return subList;
	}

	@Override
	public ItemList initConsum() 
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}

}