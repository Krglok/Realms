package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;

public class UnitMilitia extends UnitData
{

	private UnitType unitType;
	
	public UnitMilitia()
	{
		super();
		this.unitType = UnitType.MILITIA;
		armor = 1;
		speed = 3;
		offense = 6;
		defense = 3;
		offenseRange = 1;
		maxStorage = 27;
		
		armor  = 0;
		// required
		requiredItems = new ItemList();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitTypeList();
		
		//consum
		consumItems = new ItemList();
		consumCost  = 0.0;
		consumTime  = 10;
		
	}
	
	public ItemList initRequired()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.LEATHER_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.STONE_SWORD.name(),1);
		
		return subList;
	}
	
	public UnitTypeList initUnits()
	{
		UnitTypeList units = new UnitTypeList();
		
		units.put(UnitType.SETTLER, 1);
		
		return units;
	}
	
	public ItemList initConsum()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),0);
		
		return subList;
	}
}
