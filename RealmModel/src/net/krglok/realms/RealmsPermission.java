package net.krglok.realms;

public enum RealmsPermission
{
	ADMIN ("realms.admin"),
	USER ("realms.user"),
    SETTLE ("realms.settle"),
    REALM ("realms.realm");
	
	private final String value;
	
	RealmsPermission(String value)
	{
		this.value = value;
	}
	
	String getValue()
	{
		return this.value;
	}
}

