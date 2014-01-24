package net.krglok.realms;

public enum RealmsSubCommandType 
{
	NONE ,
	VERSION ,
	CREATE ,
	CHECK,
	DESTROY ,
	PRODUCTION ,
	WAREHOUSE,
	SETITEM,
	GETITEM,
	BUY,
	SELL,
	BANK,
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
	DEPOSIT ,
	WITHDRAW ,
	LIST ,
	INFO ,
	SET ,
	GET,
	UNSET ,
	TEST ,
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
