package net.krglok.realms.core;

/**
 * enum
 * 
 * @author oduda
 * 
 */
public enum MemberLevel
{
	/**
	 * kein level
	 */
	MEMBER_NONE(0),
	MEMBER_SETTLER(10),
	MEMBER_KNIGHT(20),
	MEMBER_LOWLORD(30),
	MEMBER_LORD(40),
	MEMBER_KING(50);

	private final int value;

	MemberLevel(int value)
	{
		this.value = value;
	}

	int getValue()
	{
		return value;
	}

	MemberLevel levelUp()
	{
		switch (this)
		{
		case MEMBER_NONE:
			return MEMBER_SETTLER;
		case MEMBER_SETTLER:
			return MEMBER_KNIGHT;
		case MEMBER_KNIGHT:
			return MEMBER_LOWLORD;
		case MEMBER_LOWLORD:
			return MEMBER_LORD;
		case MEMBER_LORD:
			return MEMBER_KING;
		default:
			return this;
		}
	}
	
	MemberLevel levelDown()
	{
		switch (this)
		{
		case MEMBER_SETTLER:
			return MEMBER_NONE;
		case MEMBER_KNIGHT:
			return MEMBER_SETTLER;
		case MEMBER_LOWLORD:
			return MEMBER_KNIGHT;
		case MEMBER_LORD:
			return MEMBER_LOWLORD;
		case MEMBER_KING:
			return MEMBER_LORD;
		default:
			return this;
		}
	}

}
