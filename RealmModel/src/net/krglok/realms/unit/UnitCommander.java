package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;

public class UnitCommander extends AbstractUnit {

	
	
	public UnitCommander() {
		super();
		unitType = UnitType.COMMANDER;
		armor = 6;
		speed = 4;
		offense = 7;
		defense = 6;
		offenseRange = 1;
		maxStorage = 27;
		
		// required
		requiredItems = initRequired();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitList();
		
		//consum
		consumItems = initConsum();
		consumCost  = 0.0;
		consumTime  = 10;
	}

	@Override
	public ItemList initRequired() {
		ItemList subList = new ItemList();
		
		subList.addItem(Material.GOLD_BOOTS.name(),1);
		subList.addItem(Material.GOLD_CHESTPLATE.name(),1);
		subList.addItem(Material.GOLD_HELMET.name(),1);
		subList.addItem(Material.GOLD_LEGGINGS.name(),1);
		subList.addItem(Material.DIAMOND_SWORD.name(),1);
		
		return subList;
	}

	@Override
	public ItemList initConsum() {
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}

}
