package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;

public class UnitMilitia extends AbstractUnit
{


	public UnitMilitia()
	{
		super();
		unitType = UnitType.MILITIA;
		armor = 3;
		speed = 4;
		offense = 6;
		defense = 3;
		offenseRange = 1;
		maxStorage = 27;
		
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
