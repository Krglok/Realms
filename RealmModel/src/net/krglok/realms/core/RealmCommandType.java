package net.krglok.realms.core;

public enum RealmCommandType 
{

	NONE ,
	MODEL,
	REALM ,
	SETTLE ,
	OWNER ;
	
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
