package net.krglok.realms.unit;


public enum RegimentStatus {
	NONE,
	CAMP,
	UNCAMP,
	MOVE,
	BATTLE,
	RAID,
	HIDE,
	WAIT;
	
	
	public static RegimentStatus getSettleType(String name)
	{
		for (RegimentStatus regType : RegimentStatus.values())
		{
			if (regType.name().equals(name))
			{
				return regType;
			}
		}
		
		return RegimentStatus.NONE;
		
	}
	

}
