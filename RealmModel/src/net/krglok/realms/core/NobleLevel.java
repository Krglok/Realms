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

	int getValue()
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

}
