package net.krglok.realms.colonist;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

public class Regiment extends Settlement
{

	
	public Regiment(String owner, LocationData position,  String name)
	{
		super(owner, position, SettleType.SETTLE_NONE, name);
	}
}
