package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitMilitia extends AbstractUnit
{


	public UnitMilitia(NpcData npcData)
	{
		super(npcData);
//		this.npcData.setUnitType(UnitType.MILITIA);
		initData(this);
		
	}
	
	public static void initData(AbstractUnit unit)
	{
		unit.armor = 3;
		unit.speed = 4;
		unit.offense = 6;
		unit.defense = 3;
		unit.offenseRange = 1;
		unit.maxStorage = 27;
		
		// required
		unit.requiredItems = unit.initRequired();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = unit.initConsum();
		unit.consumCost  = 0.1;
		unit.consumTime  = 10;
		
	}
	
	@Override
	public ItemList initRequired()
	{
		return getRequiredList();
	}
	
	
	public static ItemList getRequiredList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.LEATHER_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.STONE_SWORD.name(),1);
		
		return subList;
		
	}
	
	@Override
	public ItemList initConsum()
	{
		return getConsumList();
	}

	public static ItemList getConsumList()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.BREAD.name(),1);
		
		return subList;
	}


}
