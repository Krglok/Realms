package net.krglok.realms.unit;

import java.util.HashMap;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.RealmModel;

/**
 * <pre>
 * the regimentList hold all regiments of the Model,
 * the regiments are the basic for battles
 * based on the list the regiments run on the ModelTicks 
 * 
 * @author Windu
 *
 *</pre>
 */

public class RegimentList extends HashMap <Integer, Regiment>
{
	private static final long serialVersionUID = -1778527507904573869L;

	private  int lastNumber;

	public RegimentList(int initCounter )
	{
		super();
		lastNumber = 0;
		Regiment.initLfdID(initCounter);
	}
	
	
	public  int getLastNumber()
	{
		return lastNumber;
	}

	public  int nextLastNumber()
	{
		lastNumber++;
		return lastNumber;
	}

	public  void setLastNumber(int lastNumber)
	{
		this.lastNumber = lastNumber;
	}

	public void runTick(RealmModel rModel)
	{
		for (Regiment regiment : this.values())
		{
			regiment.run(rModel);
		}
	}
	
	/**
	 * add regiment to the list 
	 * set lastNumber of the list to highest regimentId
	 * @param regiment
	 */
	public void addRegiment(Regiment regiment)
	{
		int key = regiment.getId();
		this.put(key, regiment);
		if (this.lastNumber < key)
		{
			lastNumber = key;
			Regiment.initLfdID(key);
		}
	}

	/**
	 * <pre>
	 * create new Regiment of the given type. the regiment is initialized when it is a NPC type 
	 * if type not found a Raider will create as default
	 *  
	 * @param typ , RegimentType
	 * @param name , name of regiment must not unique
	 * @param settleId , reference to owner settlement
	 * @param logList  
	 * </pre>
	 */
	public Regiment createRegiment(String typ, String name, int settleId) //, LogList logList)
	{
		Regiment regiment;
		switch (typ)
		{
		
		default :
			regiment = Regiment.makeRaider(); //logList);
			regiment.setName(name);
			break;
		}
		int key = nextLastNumber();
		this.put(key, regiment);
		return regiment;
	}
	
	/**
	 * set the target postion and start move action
	 * @param regId
	 * @param pos , new position
	 */
	public void regimentMove(int regId, LocationData pos)
	{
		Regiment regiment = this.get(regId);
		if (regiment != null)
		{
			regiment.setTarget(pos);
			regiment.startMove();
		}
		
	}
	
	public Regiment getRegiment(int regimentId)
	{
		if (this.containsKey(regimentId))
		{
			return this.get(regimentId);
		}
		return null;
	}
}
