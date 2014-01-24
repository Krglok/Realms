package net.krglok.realms;

public enum RealmsPermission
{
	ADMIN ("realms.admin"),
	USER ("realms.user"),
    SETTLE ("realms.settle"),
    REALM ("realms.realm"),
    HAMLET ("realms.hamlet"),
    TOWN ("realms.town"),
    CITY ("realms.city"),
    METROPOL ("realms.metropol"),
    CASTLE ("realms.castle")
    ;
	
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

