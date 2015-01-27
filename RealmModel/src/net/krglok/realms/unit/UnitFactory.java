package net.krglok.realms.unit;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.npc.NpcData;

public class UnitFactory
{

	private static int HUMAN_HEALTH = 20;
	public UnitFactory()
	{
	}

	public AbstractUnit erzeugeUnit(UnitType uType)
	{
//		IUnit iUnit = null;
		AbstractUnit unit = null;

		switch (uType)
		{
		case MILITIA:
			unit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.setPower(10);
			unit.getBackPack().addAll(unit.getRequiredItems());
			break;
		case ARCHER:
			unit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.setPower(10);
			unit.getBackPack().addAll(unit.getRequiredItems());
			break;
		case COMMANDER:
			
			break;
		case SETTLER:
		default:
			unit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.getBackPack().addAll(unit.getRequiredItems());
			break;
		}
		return unit;
	}

	public AbstractUnit erzeugeUnitConfig(UnitType uType)
	{
		AbstractUnit iUnit = null;

		switch (uType)
		{
		case MILITIA:
			iUnit = new UnitMilitia();
			break;
		case ARCHER:
			iUnit = new UnitArcher();
			break;
		case COMMANDER :
			iUnit = new UnitCommander();
		case SETTLER:
		default:
			iUnit = new UnitSettler();
			break;
		}
		return iUnit;
	}
	
	/**
	 * liefert die consum Items für die Unit
	 * @return
	 */
	public ItemList militaryConsum(UnitType uType)
	{
		ItemList outValues = new ItemList();
		switch (uType)
		{
		case MILITIA:
			UnitMilitia militia = new UnitMilitia();
			for (Item item : militia.getConsumItems().values())
			{
				outValues.put(item.ItemRef() ,new Item(item.ItemRef(),item.value()));
			}
			break;
		default :
		}
		return outValues;
	}

	

}