package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;

public class UnitKnight extends AbstractUnit
{
	
	public UnitKnight()
	{
		super();
		unitType = UnitType.KNIGHT;
		armor = 3;
		speed = 2;
		offense = 10;
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
		consumCost  = 1.5;
		consumTime  = 10;
		
	}


	@Override
	public ItemList initRequired()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.CHAINMAIL_BOOTS.name(),1);
		subList.addItem(Material.CHAINMAIL_CHESTPLATE.name(),1);
		subList.addItem(Material.CHAINMAIL_HELMET.name(),1);
		subList.addItem(Material.CHAINMAIL_LEGGINGS.name(),1);
		subList.addItem(Material.DIAMOND_SWORD.name(),1);
		
		return subList;
	}

	@Override
	public ItemList initConsum()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		subList.addItem(Material.GRILLED_PORK.name(),1);
		
		return subList;
	}

}
