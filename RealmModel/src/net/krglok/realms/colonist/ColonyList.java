package net.krglok.realms.colonist;

import java.util.HashMap;

public class ColonyList extends HashMap<Integer, Colony>
{
	private static final long serialVersionUID = -6403462913762486430L;
	

	public ColonyList(int initCounter)
	{
		super();
		Colony.initCOUNTER(initCounter);
	}
	
	public int checkId(int ref)
	{
		while (this.containsKey(ref))
		{
			ref++;
		}
		Colony.initCOUNTER(ref);
		return Colony.getCOUNTER();
	}

	public void addColony(Colony colony)
	{
		colony.setId(checkId(colony.getId()));
		put(colony.getId(), colony);
	}

}
