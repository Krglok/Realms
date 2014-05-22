package net.krglok.realms.unit;

import java.util.HashMap;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.LogList;
import net.krglok.realms.model.RealmModel;



public class RegimentList extends HashMap <Integer, Regiment>
{
	private static final long serialVersionUID = -1778527507904573869L;

	private  int lastNumber;

	public RegimentList()
	{
		super();
		lastNumber = 0;
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

	public void createRegiment(String typ, String name, int settleId, LogList logList)
	{
		Regiment regiment;
		switch (typ)
		{
		
		default :
			regiment = Regiment.makeRaider(logList);
			break;
		}
		int key = nextLastNumber();
		this.put(key, regiment);
	}
	
	public void regimentMove(int regId, LocationData pos)
	{
		Regiment regiment = this.get(regId);
		if (regiment != null)
		{
			regiment.setTarget(pos);
			regiment.startMove();
		}
		
	}
	
}
