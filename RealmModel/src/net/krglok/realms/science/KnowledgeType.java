package net.krglok.realms.science;


public enum KnowledgeType
{
	TECH,
	NOBLE;
	
	public boolean contains(String value)
	{
		for (KnowledgeType aName : KnowledgeType.values())
		{
			if (aName.name().equalsIgnoreCase(value))
			{
				return true;
			}
		}
		return false;
	}

}
