package net.krglok.realms.npc;

public enum NPCType
{
	BEGGAR,
	CHILD ,
	SETTLER,
	CITIZEN,
	FARMER,
	MANAGER,
	TRADER,
	BUILDER,
	CRAFTSMAN,
	MAPMAKER,
	NOBLE,
	MILITARY;

	public boolean contains(String value)
	{
		for (NPCType nType : NPCType.values())
		{
			if (nType.name().equals(value))
			{
				return true;
			}
		}
		return false;
	}
	
}
