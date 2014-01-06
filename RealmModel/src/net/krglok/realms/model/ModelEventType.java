package net.krglok.realms.model;

public enum ModelEventType {

	ON_NONE,
	ON_ENABLE,
	ON_DISABLE,
	ON_COMMAND,
	ON_TICK,
	ON_MOVE,
	ON_TRADE,
	ON_BATTLE,
	ON_PRODUCTION,
	ON_TRAIN;
	
	ModelEventType()
	{
		
	}
	
	public static ModelEventType getModelEventType(String name)
	{
		for (ModelEventType met : ModelEventType.values())
		{
			if (name.equalsIgnoreCase(met.name()))
			{
				return met;
			}
		}
		
		return ON_NONE;
	}
}
