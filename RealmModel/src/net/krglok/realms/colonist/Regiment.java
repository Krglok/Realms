package net.krglok.realms.colonist;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.LogList;

/**
 * not yet implemented
 * 
 * @author Windu
 *
 */
public class Regiment extends Settlement
{

	
	public Regiment(String owner, LocationData position,  String name, LogList logList)
	{
		super(owner, position, SettleType.NONE, name, null,logList);
	}
}
