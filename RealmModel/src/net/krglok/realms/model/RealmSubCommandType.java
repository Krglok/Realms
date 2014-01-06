package net.krglok.realms.model;

public enum RealmSubCommandType 
{
	NONE ,
	VERSION ,
	CREATE ,
	DESTROY ,
	PRODUCTION ,
	BUILDING,
	SETTLEMENT,
	ADD ,
	DELETE ,
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
	
		
	public static RealmSubCommandType getRealmSubCommandType(String name)
	{
		for (RealmSubCommandType rsc : RealmSubCommandType.values())
		{
			if (name.equalsIgnoreCase(rsc.name()))
			{
				return rsc;
			}
		}
		return NONE;
	}
	
}
