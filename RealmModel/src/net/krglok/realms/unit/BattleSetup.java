package net.krglok.realms.unit;


public class BattleSetup 
{
	private static int HUMAN_HEALTH = 20;

	private BattleStatus battleStatus;
	private BattlePlacement attackers;
	private BattlePlacement defenders;
	private boolean isNextAttack = false;
	private int attackPower;
	private int defendPower;
	private boolean isBattleEnd;
	
	
	public BattleSetup()
	{
		battleStatus = BattleStatus.NONE;
		attackers = new BattlePlacement();
		defenders = new BattlePlacement();
		attackPower = 0;
		defendPower = 0;
		setBattleEnd(false);
	}
	

	public BattlePlacement getAttacker() {
		return attackers;
	}


	public void setAttacker(BattlePlacement attacker) {
		this.attackers = attacker;
	}


	public BattlePlacement getDefender() {
		return defenders;
	}


	public void setDefender(BattlePlacement defender) {
		this.defenders = defender;
	}
	
	public BattleStatus getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(BattleStatus battleStatus) {
		this.battleStatus = battleStatus;
	}

	public int getAttackPower()
	{
		return attackPower;
	}


	public void setAttackPower(int attackPower)
	{
		this.attackPower = attackPower;
	}


	public int getDefendPower()
	{
		return defendPower;
	}


	public void setDefendPower(int defendPower)
	{
		this.defendPower = defendPower;
	}


	/**
	 * @return the isBattleEnd
	 */
	public boolean isBattleEnd()
	{
		return isBattleEnd;
	}


	/**
	 * @param isBattleEnd the isBattleEnd to set
	 */
	public void setBattleEnd(boolean isBattleEnd)
	{
		this.isBattleEnd = isBattleEnd;
	}


	/**
	 * 
	 * @return
	 */
	public boolean startBattle()
	{
		if (battleStatus == BattleStatus.NONE)
		{
			battleStatus = BattleStatus.PRE_BATTLE;
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * check is next attack possible for attacker
	 * if so, check is defendPower lower than attackPower
	 * if defendPower lower than the battle is win 
	 * the defender dont fight to death
	 * 
	 * @return
	 */
	public boolean winBattle()
	{
//		if (this.isNextAttack() == true)
//		{
			if(defendPower  < (attackPower *3/5))
			{
				return true;
			}
//		}
		return false;
	}
	
	public void run()
	{
		setNextAttack(false);
		battleStatus = BattleStatus.PRE_BATTLE;
		for (int i= 0; i < BattleStatus.values().length; i++)
		{
			switch(battleStatus)
			{
			case PRE_BATTLE :
				preBattle( attackers,  defenders);
				battleStatus = BattleStatus.DISTANT;
				break;
			case DISTANT:
				fightDistant( attackers,  defenders);
				fightDistant( defenders,  attackers);
				battleStatus = BattleStatus.SCOUT_ATTACK;
				break;
			case SCOUT_ATTACK:
				battleStatus = BattleStatus.KNIGHT_ATTACK;
				break;
			case KNIGHT_ATTACK:
				battleStatus = BattleStatus.CLOSE_ATTACK;
				break;
			case CLOSE_ATTACK:
				fightClose(attackers, defenders);
				battleStatus = BattleStatus.POST_BATTLE;
				break;
			case POST_BATTLE:
				postBattle( attackers,  defenders);
				battleStatus = BattleStatus.NONE;
				break;
			default:
				battleStatus = BattleStatus.NONE;
			}
		}
	}
	
	private void postBattle(BattlePlacement attacker, BattlePlacement defender)
	{
		for (BattleFieldPosition bPos : attacker.getUnitPlacement().keySet())
		{
			if ((attacker.getUnitPlacement().get(bPos) != null))
			{
				for (AbstractUnit unit : attacker.getUnitPlacement().get(bPos))
				{
					attackPower = attackPower + unit.getPower();
				}
			}			
		}
		for (BattleFieldPosition bPos : defender.getUnitPlacement().keySet())
		{
			if ((defender.getUnitPlacement().get(bPos) != null))
			{
				for (AbstractUnit unit : defender.getUnitPlacement().get(bPos))
				{
					defendPower = defendPower + unit.getPower();
				}
			}			
		}
		if(attackPower < defendPower)
		{
//			System.out.println("Attacker "+attackPower+ " :"+"DefendPower "+defendPower);
			setNextAttack(false);
		} else
		{
//			System.out.println("Attacker "+attackPower+ " :"+"DefendPower "+defendPower);
			setNextAttack(true);
		}
	}
	
	private void preBattle(BattlePlacement attacker, BattlePlacement defender)
	{
		for (BattleFieldPosition bPos : attacker.getUnitPlacement().keySet())
		{
			switch (bPos)
			{
			case CENTERBACK:
				if ((attacker.getUnitPlacement().get(BattleFieldPosition.CENTER) == null))
				{
					attacker.getUnitPlacement().put(BattleFieldPosition.CENTER, new UnitList());
				}
				if ((attacker.getUnitPlacement().get(BattleFieldPosition.CENTER).size() == 0))
				{
					for (AbstractUnit unit : attacker.getUnitPlacement().get(BattleFieldPosition.CENTERBACK))
					{
						attacker.getUnitPlacement().get(BattleFieldPosition.CENTER).add(unit);
					}
				}
			default:
				break;
			}
		}
		for (BattleFieldPosition bPos : defender.getUnitPlacement().keySet())
		{
			switch (bPos)
			{
			case CENTERBACK:
				if ((defender.getUnitPlacement().get(BattleFieldPosition.CENTER) == null))
				{
					defender.getUnitPlacement().put(BattleFieldPosition.CENTER, new UnitList());
				}
				if ((defender.getUnitPlacement().get(BattleFieldPosition.CENTER).size() == 0))
				{
					for (AbstractUnit unit : defender.getUnitPlacement().get(BattleFieldPosition.CENTERBACK))
					{
						defender.getUnitPlacement().get(BattleFieldPosition.CENTER).add(unit);
					}
					defender.getUnitPlacement().put(BattleFieldPosition.CENTERBACK,null);
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	private void fightDistant(BattlePlacement attacker, BattlePlacement defender)
	{
		BattleFieldPosition[] targetPos;
		for (BattleFieldPosition bPos : attacker.getUnitPlacement().keySet())
		{
			if (attacker.getUnitPlacement().get(bPos) != null)
			{
				if (attacker.getUnitPlacement().get(bPos).size() > 0)
				{
					if (attacker.getUnitPlacement().get(bPos).get(0).getUnitType() == UnitType.ARCHER)
					{
						targetPos = BattleFieldPosition.getDistanceTargetPos(bPos);
						calcDistant( attacker.getUnitPlacement().get(bPos),  defender, targetPos);
					}
				}
			}
		}
	}
	
	
	private void calcDistant(UnitList attacker, BattlePlacement defender, BattleFieldPosition[] targetPos)
	{
		// default damage
		int damage = 0;
		int attackRate = 1;
		for (int i= 0; i < targetPos.length; i++)
		{
			if (attackRate > 0)
			{
				if (defender.getUnitPlacement().get(targetPos[i]) != null )
				{
					attackRate--;
					damage = calcDamage( attacker,  defender.getUnitPlacement().get(targetPos[i]));
					damage = damage - calcDefendModifier(defender, targetPos[i]) + calcAttackModifier(attacker, BattleFieldPosition.LEFT);
					calcLoss(damage , defender.getUnitPlacement().get(targetPos[i]));
				}
			}
		}
		
	}


	private void fightClose(BattlePlacement attacker, BattlePlacement defender)
	{
		BattleFieldPosition[] targetPos;
		for (BattleFieldPosition bPos : attacker.getUnitPlacement().keySet())
		{
			if (attacker.getUnitPlacement().get(bPos) != null)
			{
				// Angreifer 
//				System.out.print("Attack  ");
				targetPos = BattleFieldPosition.getCloseTargetPos(bPos);
				calcClose( attacker.getUnitPlacement().get(bPos),  defender, targetPos);
				// Gegenschlag Verteidiger
//				System.out.print("Counter ");
				calcClose( defender.getUnitPlacement().get(bPos),  attacker, targetPos);

			}
		}
	}
	
	private int calcClose(UnitList attacker, BattlePlacement defender, BattleFieldPosition[] targetPos)
	{
		// default damage
		int damage = 0;
		int attackRate = 1;
		for (int i= 0; i < targetPos.length; i++)
		{
			if (attackRate > 0)
			{
				if (defender.getUnitPlacement().get(targetPos[i]) != null )
				{
					attackRate--;
//					System.out.print(targetPos[i]);
					damage = calcDamage( attacker,  defender.getUnitPlacement().get(targetPos[i]));
					damage = damage - calcDefendModifier(defender, targetPos[i]); // + calcAttackModifier(attacker, BattleFieldPosition.LEFT);
//					System.out.println(":"+damage);
					calcLoss(damage , defender.getUnitPlacement().get(targetPos[i]));
//					calcLoss(damage , attacker); //defender.getUnitPlacement().get(targetPos[i]));
				}
			}
		}
		return damage;
	}
	
	
	private int calcDamage(UnitList attackUnits, UnitList defendUnits)
	{
		int damage = 0;
		if (attackUnits.size()>0)
		{
			UnitFactory unitFactory = new UnitFactory();
			IUnit unitAttacker = unitFactory.erzeugeUnitConfig(attackUnits.get(0).getUnitType());
			int sumOffense = attackUnits.size() * unitAttacker.getOffense();
	
			IUnit unitDefender = unitFactory.erzeugeUnitConfig(attackUnits.get(0).getUnitType());
			int sumArmor = defendUnits.size() * unitDefender.getArmor();
	
//			damage = sumOffense - sumArmor;
			damage = (unitAttacker.getOffense() - unitDefender.getArmor())*attackUnits.size();
			
			if (unitDefender.getArmor() > unitAttacker.getOffense())
			{
				damage = 0;
			}
		}
		return damage;
	}
	
	private int  calcDefendModifier(BattlePlacement defender, BattleFieldPosition bPos)
	{
		int modifier = 0;
		modifier = defender.getDefendModifier() * defender.getUnitPlacement().get(bPos).size();
		
		return modifier;
	}

	
	private int  calcAttackModifier(UnitList attacker, BattleFieldPosition bPos)
	{
		int modifier = 0;
//		modifier = attacker.getAttackModifier() * attacker.getUnitPlacement().get(bPos).size();
		
		return modifier;
	}

	private void calcLoss(int damage , UnitList defendUnits)
	{
		if (damage == 0)
		{
			return;
		}
		for (int i = 0; i < defendUnits.size(); i++) 
		{
			if (damage > HUMAN_HEALTH)
			{
				defendUnits.get(i).addHealth(-HUMAN_HEALTH);
				damage = damage - HUMAN_HEALTH;
			} else
			{
				defendUnits.get(i).addHealth(-damage);
				damage = 0;
			}
		}
		for (int i = 0; i < defendUnits.size(); i++) 
		{
			if (defendUnits.get(i).getHealth() <= 0)
			{
				defendUnits.remove(i);
			}
		}
	}


	public boolean isNextAttack() {
		return isNextAttack;
	}


	public void setNextAttack(boolean isNextAttack) {
		this.isNextAttack = isNextAttack;
	}

}
