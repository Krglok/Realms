package net.krglok.realms.core;

public enum ItemGroup 
{
	NONE (0),
	ARMOR (100),
	BUILDMATERIAL(200),
	MATERIAL (200),
	FOOD (300),
	ORE (400),
	RAW (500),
	TOOL (600),
	VALUABLE (700),
	WEAPON (800),
	IGNORE (9000);
	
	private final int value;
	
	ItemGroup(int value)
	{
		this.value = value;
	}
	
	public int getValue(ItemGroup iGroup)
	{
		return value; 
	}
	
	public ItemGroup getItemGroup(String name)
	{
		for (ItemGroup iGroup : ItemGroup.values())
		{
			if (iGroup.name().equalsIgnoreCase(name))
			{
				return iGroup;
			}
		}
		return ItemGroup.NONE;
	}
}
