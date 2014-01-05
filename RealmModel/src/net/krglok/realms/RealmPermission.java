package net.krglok.realms;

public enum RealmPermission
{
	ADMIN ("realms.admin"),
	USER ("realms.user"),
    SETTLE ("realms.settle"),
    REALM ("realms.realm");
	
	private final String value;
	
	RealmPermission(String value)
	{
		this.value = value;
	}
	
	String getValue()
	{
		return this.value;
	}
}

