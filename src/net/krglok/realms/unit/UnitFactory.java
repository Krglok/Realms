package net.krglok.realms.unit;

import net.krglok.realms.npc.NpcData;

public class UnitFactory
{

	private static int HUMAN_HEALTH = 20;
	public UnitFactory()
	{
	}

	public AbstractUnit erzeugeUnit(UnitType uType, NpcData npcData)
	{
//		IUnit iUnit = null;
		AbstractUnit unit = null;

		switch (uType)
		{
		case MILITIA:
			unit = erzeugeUnitConfig(uType, npcData);
			npcData.setHealth(HUMAN_HEALTH);
			npcData.setPower(10);
			npcData.getBackPack().addAll(unit.getRequiredItems());
			break;
		case ARCHER:
			unit = erzeugeUnitConfig(uType, npcData);
			npcData.setHealth(HUMAN_HEALTH);
			npcData.setPower(10);
			npcData.getBackPack().addAll(unit.getRequiredItems());
			break;
		case COMMANDER:
			
			break;
		case SETTLER:
		default:
			unit = erzeugeUnitConfig(uType,npcData);
			npcData.setHealth(HUMAN_HEALTH);
			npcData.getBackPack().addAll(unit.getRequiredItems());
			break;
		}
		return unit;
	}

	public AbstractUnit erzeugeUnitConfig(UnitType uType, NpcData npcData)
	{
		AbstractUnit iUnit = null;

		switch (uType)
		{
		case MILITIA:
			iUnit = new UnitMilitia(npcData);
			break;
		case ARCHER:
			iUnit = new UnitArcher(npcData);
			break;
		case COMMANDER :
			iUnit = new UnitCommander(npcData);
		case SETTLER:
		default:
			iUnit = new UnitSettler(npcData);
			break;
		}
		return iUnit;
	}
	
	
	public void setConfig(UnitType uType, AbstractUnit unit)
	{
		switch (uType)
		{
		case MILITIA:
			UnitMilitia.initData(unit);
			break;
		case ARCHER:
			UnitArcher.initData(unit);
			break;
		case LIGHT_INFANTRY:
			UnitLightInfantry.initData(unit);
			break;
		case HEAVY_INFANTRY:
			UnitHeavyInfantry.initData(unit);
			break;
		case KNIGHT:
			UnitKnight.initData(unit);
			break;
		case COMMANDER :
			UnitCommander.initData(unit);
		case SETTLER:
		default:
			UnitSettler.initData(unit);
			break;
		}
		
	}
	
	

}