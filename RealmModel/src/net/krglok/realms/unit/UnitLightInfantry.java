package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;

import org.bukkit.Material;

public class UnitLightInfantry extends AbstractUnit
{
	
	public UnitLightInfantry()
	{
		super();
		unitType = UnitType.LIGHT_INFANTRY;
		armor = 3;
		speed = 3;
		offense = 6;
		defense = 5;
		offenseRange = 1;
		maxStorage = 27;
		
		// required
		requiredItems = initRequired();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitList();
		
		//consum
		consumItems = initConsum();
		consumCost  = 0.5;
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
		subList.addItem(Material.STONE_SWORD.name(),1);
		
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
