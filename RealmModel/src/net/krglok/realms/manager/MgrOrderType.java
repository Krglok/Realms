package net.krglok.realms.manager;


public enum MgrOrderType
{
	NONE_MGR,
	SETTLE_MGR,
	TRADE_MGR,
	BUILD_MGR,
	CRAFT_MGR,
	MAP_MGR;

	/**
	 * transformation from string to MgrOrderType
	 * if name is wrong NONE_MGR are returned; 
	 * @param name
	 * @return  managerType equivalent to name
	 */
	public static MgrOrderType getSettleType(String name)
	{
		for (MgrOrderType mType : MgrOrderType.values())
		{
			if (mType.name().equals(name))
			{
				return mType;
			}
		}
		return NONE_MGR;
	}

}
