package net.krglok.realms.manager;

import java.util.HashMap;

import net.krglok.realms.builder.SettleSchema;

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
public class ScoutManager
{
	HashMap<String, SettleSchema> settleSchema;

	
	public ScoutManager()
	{
		
	}
}
