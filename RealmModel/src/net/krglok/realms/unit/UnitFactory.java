package net.krglok.realms.unit;

public class UnitFactory
{

	public UnitFactory()
	{
		// TODO Auto-generated constructor stub
	}

	public IUnit erzeugeAbstractUnit(UnitType uType)
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