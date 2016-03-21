package net.krglok.realms.manager;

public enum RaiderCycle
{
	DAY1,
	DAY2,
	DAY3,
	DAY4,
	DAY5,
	DAY6,
	DAY7;
	
	
	public static RaiderCycle nextCyle(RaiderCycle value)
	{
		switch(value)
		{
		case DAY1 : 
			return DAY2;
		case DAY2 : 
			return DAY3;
		case DAY3:
			return DAY4;
		case DAY4:
			return DAY5;
		case DAY5:
			return DAY6;
		case DAY6:
			return DAY7;
		case DAY7:
			return DAY1;
		default :
			return DAY1;
		}

	}

}
