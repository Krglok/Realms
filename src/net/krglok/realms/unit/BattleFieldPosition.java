package net.krglok.realms.unit;

public enum BattleFieldPosition {
	
	LEFTBACK,
	LEFT,
	CENTERBACK,
	CENTER,
	RIGHTBACK,
	RIGHT;

	/**
	 * die targetpos werden als array zurückgegeben
	 * Die prioritaetem sind die aufsteigende reihenfolge
	 * @param bPos
	 * @return
	 */
	public static BattleFieldPosition[] getDistanceTargetPos(BattleFieldPosition bPos)
	{
		BattleFieldPosition[] targetPos = new BattleFieldPosition[0];
		switch(bPos)
		{
		case LEFTBACK:
			targetPos = new BattleFieldPosition[] {RIGHT, CENTER };
			break;
		case CENTERBACK:
			targetPos = new BattleFieldPosition[] {LEFT, RIGHT, CENTER };
			break;
		case RIGHTBACK:
			targetPos = new BattleFieldPosition[] {LEFT, CENTER };
			break;
		case LEFT:
			targetPos = new BattleFieldPosition[] {RIGHT, CENTER, RIGHTBACK, CENTERBACK };
			break;
		case CENTER:
			targetPos = new BattleFieldPosition[] {LEFT, CENTER, RIGHT, LEFTBACK, CENTERBACK, RIGHTBACK };
			break;
		case RIGHT:
			targetPos = new BattleFieldPosition[] {LEFT, CENTER, LEFTBACK, CENTERBACK };
			break;
		}
		return targetPos;
	}

	
	public static BattleFieldPosition[] getCloseTargetPos(BattleFieldPosition bPos)
	{
		BattleFieldPosition[] targetPos = new BattleFieldPosition[0];
		switch(bPos)
		{
		case LEFT:
			targetPos = new BattleFieldPosition[] {RIGHT, CENTER };
			break;
		case CENTER:
			targetPos = new BattleFieldPosition[] {LEFT, CENTER, RIGHT };
			break;
		case RIGHT:
			targetPos = new BattleFieldPosition[] {LEFT, CENTER };
			break;
		default:
			break;
		}
		return targetPos;
	}

}
