package net.krglok.realms.core;

public enum RealmSubCommandType 
{
	NONE,
	VERSION,
	CREATE ,
	DESTROY ,
	PRODUCTION ,
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
	CAMP 
	;
	
	
//	 RealmSubCommandType(String value) {
//		 this.value =  value;
//	}
	
//	 String value()
//	 {
//		 return this.value;
//	 }
	
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
	
//	public static String getValue(RealmSubCommandType rct)
//	{
//		return rct.value;
//	}
}
