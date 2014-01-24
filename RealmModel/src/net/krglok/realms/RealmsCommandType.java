package net.krglok.realms;

public enum RealmsCommandType 
{

	NONE ,
	KINGDOM ,
	SETTLE ,
	OWNER,
	REALMS
	;
	
//	 RealmCommandType() 
//	{
//
//	}
	
	 
	public static RealmsCommandType getRealmCommandType(String name)
	{
		for (RealmsCommandType rct : RealmsCommandType.values())
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
