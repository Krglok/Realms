package net.krglok.realms.colonist;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

/**
 * portable settlement 
 * - can build stationary settlement
 * - has no settlers
 * - has no happiness
 * - has no buildinglist
 * 
 * - has a warehouse
 * - has a buildManager
 * - has a limited production
 * - has a bank 
 * - has a trader
 * 
 * @author oduda
 *
 */
public class Colonist extends Settlement
{

	public Colonist(String owner, LocationData position,  String name)
	{
		super(owner, position, SettleType.SETTLE_NONE, name);
	}
	
	public void initWoodcutter()
	{
//		Building build = new Building(buildingType, hsRegion, hsRegionType., false);
		
	}
	
	
}

