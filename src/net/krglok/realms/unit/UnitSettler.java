package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.Common.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitSettler extends AbstractUnit
{


	public UnitSettler(NpcData npcData)
	{
		super(npcData);
//		unitType = UnitType.SETTLER;
		initData(this);
	}

	public static void initData(AbstractUnit unit)
	{
		unit.armor = 1;
		unit.speed = 5;
		unit.offense = 2;
		unit.defense = 3;
		unit.offenseRange = 1;
		unit.maxStorage = 27;
		
		// required
		unit.requiredItems = new ItemList();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = new ItemList();
		unit.consumCost  = 0.0;
		unit.consumTime  = 10;
	}


//	@Override
//	public ItemList initRequired() 
//	{
//		ItemList subList = new ItemList();
//		
//		return subList;
//	}
//
//	@Override
//	public ItemList initConsum() 
//	{
//		return new ItemList();
//	}

//	public static ItemList getConsumList()
//	{
//		ItemList subList = new ItemList();
//		subList.addItem(Material.BREAD.name(),1);
//		return subList;
//	}

}
