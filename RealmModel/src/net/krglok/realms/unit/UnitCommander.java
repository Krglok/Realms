package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitCommander extends AbstractUnit {

	
	
	public UnitCommander(NpcData npcData) 
	{
		super(npcData);
//		npcData.setUnitType(UnitType.COMMANDER);
		initData(this);

	}
	
	public static void initData(AbstractUnit unit)
	{
		unit.armor = 6;
		unit.speed = 4;
		unit.offense = 7;
		unit.defense = 8;
		unit.offenseRange = 1;
		unit.maxStorage = 27;
		
		// required
		unit.requiredItems = getRequiredList();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = getConsumList();
		unit.consumCost  = 1.0;
		unit.consumTime  = 10;

	}


//	@Override
	public static ItemList getRequiredList() 
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.GOLD_BOOTS.name(),1);
		subList.addItem(Material.GOLD_CHESTPLATE.name(),1);
		subList.addItem(Material.GOLD_HELMET.name(),1);
		subList.addItem(Material.GOLD_LEGGINGS.name(),1);
		subList.addItem(Material.DIAMOND_SWORD.name(),1);
		
		return subList;
	}

//	@Override
//	public ItemList initConsum() {
//		return getConsumList();
//	}

	public static ItemList getConsumList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}

}
