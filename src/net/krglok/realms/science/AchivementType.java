package net.krglok.realms.science;


/**
 * the type of achievement
 * 
 * @author Windu
 *
 */
public enum AchivementType
{
	NONE,
	BUILD,
	BOOK,
	OWN,
	MEMBER
	;
	
	public boolean contains(String value)
	{
		for (AchivementType aName : AchivementType.values())
		{
			if (aName.name().equalsIgnoreCase(value))
			{
				return true;
			}
		}
		return false;
	}

}
