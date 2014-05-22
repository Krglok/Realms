package net.krglok.realms;

/**
 * <pre>
 * Enum for the reistered commands
 * this are keywords for the command interpreter
 * 
 * @author oduda
 *</pre>
 */
public enum RealmsCommandType 
{

	NONE ,
	KINGDOM ,
	SETTLE ,
	COLONIST,
	OWNER,
	REALMS,
	REGIMENT
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
