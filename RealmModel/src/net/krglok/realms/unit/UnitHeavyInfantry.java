package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.npc.NpcData;

import org.bukkit.Material;

public class UnitHeavyInfantry  extends AbstractUnit
{

	public UnitHeavyInfantry(NpcData npcData)
	{
		super(npcData);
//		npcData.setUnitType(UnitType.HEAVY_INFANTRY);
		initData(this);
		
	}
	
	public static void initData(AbstractUnit unit)
	{
		unit.armor = 3;
		unit.speed = 2;
		unit.offense = 6;
		unit.defense = 10;
		unit.offenseRange = 1;
		unit.maxStorage = 27;
		
		// required
		unit.requiredItems = unit.initRequired();
		unit.requiredCost = 0.0;
		unit.requiredTime = 10;
		unit.requiredUnits = new UnitList();
		
		//consum
		unit.consumItems = unit.initConsum();
		unit.consumCost  = 0.8;
		unit.consumTime  = 10;
	}

	@Override
	public ItemList initRequired()
	{
		ItemList subList = new ItemList();
		
		subList.addItem(Material.LEATHER_BOOTS.name(),1);
		subList.addItem(Material.IRON_CHESTPLATE.name(),1);
		subList.addItem(Material.LEATHER_HELMET.name(),1);
		subList.addItem(Material.LEATHER_LEGGINGS.name(),1);
		subList.addItem(Material.IRON_SWORD.name(),1);
		
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
