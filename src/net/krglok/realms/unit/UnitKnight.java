package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.Common.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitKnight extends AbstractUnit
{
	
	public UnitKnight(NpcData npcData)
	{
		super(npcData);
//		unitType = UnitType.KNIGHT;
		initData(this);
	}

	public static void initData(AbstractUnit unit)
	{
		
		unit.armor = 3;
		unit.speed = 2;
		unit.offense = 10;
		unit.defense = 10;
		unit.offenseRange = 1;
		unit.maxStorage  = 27;
		
		// required
		unit.requiredItems = getRequiredList();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = getConsumList();
		unit.consumCost  = 1.5;
		unit.consumTime  = 10;
		
	}


//	@Override
	public static ItemList getRequiredList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.CHAINMAIL_BOOTS.name(),1);
		subList.addItem(Material.CHAINMAIL_CHESTPLATE.name(),1);
		subList.addItem(Material.CHAINMAIL_HELMET.name(),1);
		subList.addItem(Material.CHAINMAIL_LEGGINGS.name(),1);
		subList.addItem(Material.DIAMOND_SWORD.name(),1);
		
		return subList;
	}

//	@Override
//	public ItemList initConsum()
//	{
//		return getConsumList();
//	}
	
	public static ItemList getConsumList()
	{
		ItemList subList = new ItemList();
		subList.addItem(Material.BREAD.name(),1);
		subList.addItem(Material.GRILLED_PORK.name(),1);
		return subList;
	}

}
