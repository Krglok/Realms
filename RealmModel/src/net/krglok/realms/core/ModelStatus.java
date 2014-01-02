package net.krglok.realms.core;

public enum ModelStatus 
{
	MODEL_DISABLED,
	MODEL_ENABLED,
	MODEL_PRODUCTION,
	MODEL_TRAINING,
	MODEL_COMMAND,
	MODEL_TRADE,
	MODEL_MOVE,
	MODEL_BATTLE;
	
	ModelStatus()
	{
	
	}

	public static ModelStatus getModelStatus(String name)
	{
		for (ModelStatus mst : ModelStatus.values())
		{
			if (name.equalsIgnoreCase(mst.name()))
			{
				return mst;
			}
		}
		
		return MODEL_DISABLED;
	}
}
