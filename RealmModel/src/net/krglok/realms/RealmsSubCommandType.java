package net.krglok.realms;

public enum RealmsSubCommandType 
{
	NONE ,
	VERSION ,
	CREATE ,
	DESTROY ,
	PRODUCTION ,
	TAX,
	BUILDING,
	SETTLEMENT,
	ADD ,
	DELETE ,
	PRICE,
	PRICELIST,
	DISBAND ,
	ACTIVATE ,
	DEACTIVATE ,
	TRAIN ,
	RANKUP ,
	RANKDOWN ,
	DEPOSIT ,
	WITHDRAW ,
	LIST ,
	INFO ,
	SET ,
	UNSET ,
	MOVE ,
	TRANSFER ,
	ATTACK ,
	CAMP ,
	TEST ,
	CONFIG,
	READ,
	WRITE,
	DEBUG,
	HELP
	;
	
		
	public static RealmsSubCommandType getRealmSubCommandType(String name)
	{
		for (RealmsSubCommandType rsc : RealmsSubCommandType.values())
		{
			if (name.equalsIgnoreCase(rsc.name()))
			{
				return rsc;
			}
		}
		return NONE;
	}
	
}
