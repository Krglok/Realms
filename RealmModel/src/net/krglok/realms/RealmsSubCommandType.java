package net.krglok.realms;

/**
 * <pre>
 * Enum for the possible subCommands of the plugin
 * this are keywords for the command interpreter
 * @author oduda
 *</pre>
 */
public enum RealmsSubCommandType 
{
	NONE ,
	ACTIVATE ,
	ADD ,
	BANK,
	BIOME,
	BOOK,
	BOOKLIST,
	BUILDING,
	BUILDINGLIST,
	BUILD,
	BUY,
	CREATE ,
	CHECK,
	CREDIT,
	DEACTIVATE ,
	DELETE ,
	DEPOSIT ,
	DESTROY ,
	EVOLVE,
	GETITEM,
	GET,
	INFO ,
	LIST ,
	MAP,
	MARKET,
	MEMBER,
	MOVE,
	NOSELL,
	OWNER,
	PRICE,
	PRICELIST,
	PRODUCTION ,
	RAID,
	READ,
	REQUIRE,
	SET ,
	SETITEM,
	SELL,
	SETTLEMENT,
	SIGN,
	TAX,
	TEST ,
	TRADER,
	TRAIN,
	TRAINING,
	WAREHOUSE,
	WRITE,
	DEBUG,
	UNSET ,
	VERSION ,
	WITHDRAW ,
	WORKSHOP,
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
