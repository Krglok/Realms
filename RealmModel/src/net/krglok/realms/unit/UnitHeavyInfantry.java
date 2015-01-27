package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;

import org.bukkit.Material;

public class UnitHeavyInfantry  extends AbstractUnit
{

	public UnitHeavyInfantry()
	{
		super();
		unitType = UnitType.HEAVY_INFANTRY;
		armor = 3;
		speed = 2;
		offense = 6;
		defense = 10;
		offenseRange = 1;
		maxStorage = 27;
		
		// required
		requiredItems = initRequired();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitList();
		
		//consum
		consumItems = initConsum();
		consumCost  = 0.8;
		consumTime  = 10;
	}

	@Override
	public ItemList initRequired()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.IRON_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.IRON_SWORD.name(),1);
		
		return subList;
	}

	@Override
	public ItemList initConsum()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		subList.addItem(Material.COOKED_BEEF.name(),1);
		
		return subList;
	}
		
	
}
