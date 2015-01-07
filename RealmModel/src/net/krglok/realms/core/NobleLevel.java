package net.krglok.realms.core;

/**
 * <pre>
 * these are the noble level of owners.
 * noble level can only rankup within a kingdom. 
 * All beginner has the level COMMONER, this is a none noble.
 * 
 * @author oduda
 * </pre>
 */
public enum NobleLevel
{
	/**
	 * kein level
	 */
	COMMONER(0),
	KNIGHT(10),
	EARL(20),
	LORD(50),
	KING(100);

	private final int value;

	NobleLevel(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	NobleLevel levelUp()
	{
		switch (this)
		{
		case COMMONER:
			return KNIGHT;
		case KNIGHT:
			return EARL;
		case EARL:
			return LORD;
		case LORD:
			return KING;
		default:
			return this;
		}
	}
	
	NobleLevel levelDown()
	{
		switch (this)
		{
		case KNIGHT:
			return COMMONER;
		case EARL:
			return KNIGHT;
		case LORD:
			return EARL;
		case KING:
			return LORD;
		default:
			return this;
		}
	}

	public static NobleLevel valueOf(int level)
	{
		switch (level)
		{
		case 1: return KNIGHT;
		case 2: return EARL;
		case 3: return LORD;
		case 4: return KING;
		
		default:
			return COMMONER;
		}
	}
}
