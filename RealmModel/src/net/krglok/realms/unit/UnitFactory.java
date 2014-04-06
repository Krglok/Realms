package net.krglok.realms.unit;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;

public class UnitFactory
{

	private static int HUMAN_HEALTH = 20;
	public UnitFactory()
	{
		// TODO Auto-generated constructor stub
	}

	public Unit erzeugeUnit(UnitType uType)
	{
		IUnit iUnit = null;
		Unit unit = new Unit(uType);

		switch (uType)
		{
		case MILITIA:
			iUnit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.setPower(10);
			unit.setItemList(iUnit.getRequiredItems());
			break;
		case ARCHER:
			iUnit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.setPower(10);
			unit.setItemList(iUnit.getRequiredItems());
			break;
		case COMMANDER:
			
			break;
		case SETTLER:
		default:
			iUnit = erzeugeUnitConfig(uType);
			unit.setHealth(HUMAN_HEALTH);
			unit.setItemList(iUnit.getRequiredItems());
			break;
		}
		return unit;
	}

	public IUnit erzeugeUnitConfig(UnitType uType)
	{
		IUnit iUnit = null;

		switch (uType)
		{
		case MILITIA:
			iUnit = new UnitMilitia();
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