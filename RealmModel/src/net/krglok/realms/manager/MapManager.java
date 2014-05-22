package net.krglok.realms.manager;

import java.util.ArrayList;
import net.krglok.realms.builder.BuildPosition;
import net.krglok.realms.builder.SettleSchema;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

/**
 * the scout manager realize the controller and manager for the position and location tasks
 * - settlement map
 * - resource map
 * - mining position
 * the manager can interact with the world and send commands and requests to other managers
 * 
 * @author oduda
 *
 */
public class MapManager
{
	private SettleSchema settleSchema;
	private ArrayList<BiomeLocation> biomeList;
	private ArrayList<BiomeLocation> biomeRequest;
	
	public MapManager(SettleType settleType, int radius, boolean markup)
	{
		this.settleSchema = new SettleSchema(settleType, radius,markup);
		this.biomeRequest = new ArrayList<BiomeLocation>();
	}

	public SettleSchema getSettleSchema()
	{
		return settleSchema;
	}

	public ArrayList<BiomeLocation> getBiomeRequest()
	{
		return biomeRequest;
	}
	
	public void initSettleSchema(Settlement settle)
	{
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
			settleSchema.getbPositions().add(new BuildPosition(building.getBuildingType(), building.getPosition()));
		}
	}
	
}
