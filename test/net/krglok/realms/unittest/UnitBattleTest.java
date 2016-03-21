package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.BattleFieldPosition;
import net.krglok.realms.unit.BattlePlacement;
import net.krglok.realms.unit.BattleSetup;
import net.krglok.realms.unit.UnitArcher;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitList;
import net.krglok.realms.unit.UnitMilitia;
import net.krglok.realms.unit.UnitType;

import org.junit.Test;

public class UnitBattleTest {

	
	private void showBattleStatus(BattleSetup battle)
	{
		System.out.print("|"+ConfigBasis.setStrleft("Attacker", 10));
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getAttacker().getUnitPlacement().get(bPos) != null)
			{
				if (battle.getAttacker().getUnitPlacement().get(bPos).size() > 0)
				{
					System.out.print("|"+ConfigBasis.setStrleft(battle.getAttacker().getUnitPlacement().get(bPos).get(0).getUnitType().name(),8));
					System.out.print(ConfigBasis.setStrright(battle.getAttacker().getUnitPlacement().get(bPos).size(), 2));
				} else
				{
					System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
				}
			} else
			{
				System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
			}
		}
		System.out.println("|");
		System.out.print("|"+ConfigBasis.setStrleft("Defender", 10));
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getDefender().getUnitPlacement().get(bPos) != null)
			{
				if (battle.getDefender().getUnitPlacement().get(bPos).size() > 0)
				{
					System.out.print("|"+ConfigBasis.setStrleft(battle.getDefender().getUnitPlacement().get(bPos).get(0).getUnitType().name(),8));
					System.out.print(ConfigBasis.setStrright(battle.getDefender().getUnitPlacement().get(bPos).size(),2));
				} else
				{
					System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
				}
			} else
			{
				System.out.print("|"+ConfigBasis.setStrleft(" ", 10));
			}
 
		}
		System.out.println("|");
//		if (battle.isNextAttack() == false)
//		{
//			System.out.println("|> ESCAPE  |");
//		} else
//		{
//			System.out.println("|> ATTACK  |");
//		}
		System.out.println("------------------------------------------------------------------------------");
	}

	private void showBattleHeader(BattleSetup battle)
	{
		System.out.println("");
		System.out.print("|"+ConfigBasis.setStrleft("Position", 10));
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			System.out.print("|"+ConfigBasis.setStrright(bPos.name(), 10));
		}
		System.out.println("|");
		System.out.print("|"+ConfigBasis.setStrleft("Attacker", 10));
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
		System.out.print("|"+ConfigBasis.setStrleft("Defender", 10));
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
		System.out.println(">  "+"--------------------------------------------------------------------------");
	}

	private BattlePlacement getAttackers()
	{
		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();
	
		
		NpcData npc;
		UnitList unitList = new UnitList();
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		
		units.setPlaceUnit(BattleFieldPosition.CENTER, unitList);

		UnitList unitDist = new UnitList();

		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);

		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);

		units.setPlaceUnit(BattleFieldPosition.CENTERBACK, unitDist);
		
		return units;
	}

	
	private BattlePlacement getDefenders()
	{
		UnitFactory unitFactory = new UnitFactory();
		BattlePlacement units = new BattlePlacement();
	
		
		NpcData npc;
		UnitList unitList = new UnitList();
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.MILITIA);
		UnitMilitia.initData(npc.getUnit());
		unitList.add(npc);

		units.setPlaceUnit(BattleFieldPosition.CENTER, unitList);
		
		UnitList unitDist = new UnitList();
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);
		npc = new NpcData();
		npc.setUnitType(UnitType.ARCHER);
		UnitArcher.initData(npc.getUnit());
		unitDist.add(npc);

		units.setPlaceUnit(BattleFieldPosition.CENTERBACK, unitDist);

		return units;
	}

	
	@Test
	public void testBattle() 
	{
		int firstWave = 5;
		int secondWave = 5;
		BattleSetup battle = new BattleSetup();
		battle.setAttacker(getAttackers());
		battle.setDefender(getDefenders());
		System.out.print("BattleStatus");
		showBattleHeader(battle);
		battle.startBattle();
		battle.setNextAttack(true);
		int i = 0;
		while ((i < firstWave) && (battle.winBattle() == false))
		{
				if (battle.isNextAttack())
				{
					battle.run();
					showBattleStatus(battle);
				} else
				{
					battle.setBattleEnd(true);
				}
			i++;
		}
		if (battle.winBattle() == true)
		{
			System.out.print("Battle WIN defeat Defender");
			battle.setBattleEnd(true);
		}  else
		{
			if (battle.isBattleEnd() == true)
			{
				System.out.print("Battle LOST defeat Attacker");
			} else
			{
				System.out.print("Battle SecondWave");
			}				
		}

		System.out.println("");
		System.out.println("SecondWave");
		i = 0;
		if (battle.winBattle() == false)
		{
			while(i < secondWave)
			{
				if (battle.isNextAttack())
				{
					battle.run();
					showBattleStatus(battle);
				} else
				{
					battle.setBattleEnd(true);
				}
				i++;
			}		
			if (battle.winBattle() == true)
			{
				System.out.print("Battle WIN defeat Defender");
				battle.setBattleEnd(true);
			}  else
			{
				if (battle.isBattleEnd() == true)
				{
					System.out.print("Battle LOST defeat Attacker");
				} else
				{
					System.out.print("Battle LOST defeat Attacker");
				}				

			}
		}
		fail("Not yet implemented");
	}

}
