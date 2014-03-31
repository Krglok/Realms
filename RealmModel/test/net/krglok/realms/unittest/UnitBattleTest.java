package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.unit.BattleFieldPosition;
import net.krglok.realms.unit.BattlePlacement;
import net.krglok.realms.unit.BattleSetup;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitList;
import net.krglok.realms.unit.UnitType;

import org.junit.Test;

public class UnitBattleTest {

	
	private void showBattleStatus(BattleSetup battle)
	{
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getAttacker().getUnitPlacement().get(bPos) != null)
			{
				System.out.print("|"+ConfigBasis.setStrright(battle.getAttacker().getUnitPlacement().get(bPos).size(), 10));
			} else
			{
				System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
			}
		}
		System.out.println("|");
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getDefender().getUnitPlacement().get(bPos) != null)
			{
				System.out.print("|"+ConfigBasis.setStrright(battle.getDefender().getUnitPlacement().get(bPos).size(),10));
			} else
			{
				System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
			}
 
		}
		System.out.println("|");
		System.out.println("-");
	}

	private void showBattleHeader(BattleSetup battle)
	{
		System.out.println("");
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			System.out.print("|"+ConfigBasis.setStrright(bPos.name(), 10));
		}
		System.out.println("|");
	}

	private BattlePlacement getAttackers()
	{
		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();
	
		
		UnitList unitList = new UnitList();
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		units.setPlaceUnit(BattleFieldPosition.CENTER, unitList);
		
		return units;
	}

	
	private BattlePlacement getDefenders()
	{
		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();
	
		
		UnitList unitList = new UnitList();
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		units.setPlaceUnit(BattleFieldPosition.CENTER, unitList);
		
		return units;
	}

	
	@Test
	public void testBattle() 
	{
		BattleSetup battle = new BattleSetup();
		battle.setAttacker(getAttackers());
		battle.setDefender(getDefenders());
		System.out.print("BattleStatus");
		showBattleHeader(battle);
//		battle.startBattle();
		showBattleStatus(battle);
		battle.run();
		showBattleStatus(battle);
//		battle.startBattle();
		battle.run();
		showBattleStatus(battle);
		battle.run();
		showBattleStatus(battle);
		battle.run();
		showBattleStatus(battle);
		battle.run();
		showBattleStatus(battle);
		battle.run();
		showBattleStatus(battle);
		
		fail("Not yet implemented");
	}

}
