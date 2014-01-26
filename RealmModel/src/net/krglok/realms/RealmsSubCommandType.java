package net.krglok.realms;

public enum RealmsSubCommandType 
{
	NONE ,
	ACTIVATE ,
	ADD ,
	BANK,
	BUILDING,
	BUILD,
	BUY,
	CREATE ,
	CHECK,
	DEACTIVATE ,
	DELETE ,
	DEPOSIT ,
	DESTROY ,
	GETITEM,
	GET,
	INFO ,
	LIST ,
	MAP,
	PRICE,
	PRICELIST,
	PRODUCTION ,
	READ,
	SET ,
	SETITEM,
	SELL,
	SETTLEMENT,
	TAX,
	TEST ,
	TRADER,
	WAREHOUSE,
	WRITE,
	DEBUG,
	UNSET ,
	VERSION ,
	WITHDRAW ,
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
