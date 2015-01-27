package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;

public class UnitArcher extends AbstractUnit 
{

	
	
	public UnitArcher() 
	{
		super();
		unitType = UnitType.ARCHER ;
		armor = 3;
		speed = 4;
		offense = 9;
		defense = 3;
		offenseRange = 2;
		maxStorage = 27;
		
		// required
		requiredItems = initRequired();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitList();
		
		//consum
		consumItems = initConsum();
		consumCost  = 0.1;
		consumTime  = 10;
	}


	@Override
	public ItemList initRequired() {
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.LEATHER_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.BOW.name(),1);
		subList.addItem(Material.ARROW.name(),32);
		
		return subList;
	}

	@Override
	public ItemList initConsum() {
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}

}
