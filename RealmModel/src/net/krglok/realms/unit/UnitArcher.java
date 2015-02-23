package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitArcher extends AbstractUnit 
{

	
	
	public UnitArcher(NpcData npcData) 
	{
		super(npcData);
//		npcData.setUnitType(UnitType.ARCHER) ;
		initData(this);
	}

	public static void initData(AbstractUnit unit)
	{
		unit.armor = 3;
		unit.speed = 4;
		unit.offense = 9;
		unit.defense = 3;
		unit.offenseRange = 2;
		unit.maxStorage = 27;
		
		// required
		unit.requiredItems = getRequiredList();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = getConsumList();
		unit.consumCost  = 0.1;
		unit.consumTime  = 10;
	}


//	@Override
//	public ItemList initRequired() {
//		
//		return getRequiredList();
//	}
	
	public static ItemList getRequiredList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.LEATHER_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.BOW.name(),1);
		subList.addItem(Material.ARROW.name(),32);
		
		return subList;
		
	}


//	@Override
//	public ItemList initConsum() {
//		ItemList subList = new ItemList();
//		
//		subList.addItem(Material.BREAD.name(),1);
//		
//		return subList;
//	}

	public static ItemList getConsumList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}

}
