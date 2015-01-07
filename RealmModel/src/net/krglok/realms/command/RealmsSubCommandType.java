package net.krglok.realms.command;

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
	ADD,
	AQUIRE,
	ASSUME,
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
	FOUNDING,
	FOLLOW,
	GETITEM,
//	GET,
	GIVE,
	INFO ,
	JOIN,
//	LIMIT,
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
	RECIPE,
	RECIPES,
	RELEASE,
	REQUEST,
	REQUIRE,
	REPUTATION,
	ROUTE,
	ROUTES,
//	ROUTESTOP,
	SETTLER,
	SET ,
	SETITEM,
	SELL,
	SETTLEMENT,
	SHOP,
	SIGN,
	STRUCTURE,
	TAX,
	TECH,
	TEST ,
	TRADER,
	TRAIN,
	TRAINING,
	WAREHOUSE,
	WRITE,
	DEBUG,
//	UNSET ,
	VERSION ,
	WALLSIGN,
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
