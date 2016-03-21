package net.krglok.realms.npc;

public enum GenderType
{
	MAN,
	WOMAN;
	
	public boolean contains(String value)
	{
		for (GenderType gType : GenderType.values())
		{
			if (gType.name().equals(value))
			{
				return true;
			}
		}
		return false;
	}
}
