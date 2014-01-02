package net.krglok.realms.settlemanager;

public enum NpcItemType 
{
	NONE,
	SUPPLY,
	TOOL,
	DEFEND;
	
	public static NpcItemType getNpcItemType(String name)
	{
		for (NpcItemType nit : NpcItemType.values())
		{
			if(name.equalsIgnoreCase(nit.name()))
			{
				return nit;
			}
		}
		return NONE;
	}

}
