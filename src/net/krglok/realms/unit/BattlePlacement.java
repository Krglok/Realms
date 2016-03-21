package net.krglok.realms.unit;

import java.util.HashMap;

/**
 * <pre>
 * Container for fighting attacker and defender
 * who is the aggressor (attacker) must be decide before setup battlefield
 * the run method make the progress.
 * the fight always go thru all steps.
 * one step per tick. (normally 1 sec) 
 * - a settlement is always a defender
 * </pre>
 * @author Windu
 *
 */
public class BattlePlacement {
	
	private int attackModifier;
	private int defendModifier;
	private HashMap<BattleFieldPosition,UnitList> unitPlacement;
	
	public BattlePlacement()
	{

		unitPlacement = new HashMap<BattleFieldPosition,UnitList>();
		attackModifier = 0;
		defendModifier = 0;
	}

	public HashMap<BattleFieldPosition, UnitList> getUnitPlacement() {
		return unitPlacement;
	}

	public void setUnitPlacement(
			HashMap<BattleFieldPosition, UnitList> unitPlacement) {
		this.unitPlacement = unitPlacement;
	}

	public UnitList getPlaceUnit(BattleFieldPosition bPos) 
	{
		return unitPlacement.get(bPos);
	}

	public void setPlaceUnit(BattleFieldPosition bPos, UnitList units) {
		unitPlacement.put(bPos, units);
	}


	public int getAttackModifier() {
		return attackModifier;
	}

	public void setAttackModifier(int attackModifier) {
		this.attackModifier = attackModifier;
	}

	public int getDefendModifier() {
		return defendModifier;
	}

	public void setDefendModifier(int defendModifier) {
		this.defendModifier = defendModifier;
	}

	

}
