package net.krglok.realms.model;

public enum RealmCommandType 
{

	NONE ,
	MODEL,
	REALM ,
	SETTLE ,
	OWNER,
	REALMS,
	STRONGHOLD;
	
//	 RealmCommandType() 
//	{
//
//	}
	
	 
	public static RealmCommandType getRealmCommandType(String name)
	{
		for (RealmCommandType rct : RealmCommandType.values())
		{
			if(name.equalsIgnoreCase(rct.name()))
			{
				return rct;
			}
		}
		return NONE;
	}
	
	
//	public static String getValue(RealmCommandType rct)
//	{
//		return rct.value;
//	}
}
