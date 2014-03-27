package net.krglok.realms.unit;

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
			unit.setItemList(iUnit.getRequiredItems());
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

		case SETTLER:
		default:
			iUnit = new UnitSettler();
			break;
		}
		return iUnit;
	}

}