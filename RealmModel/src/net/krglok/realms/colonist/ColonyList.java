package net.krglok.realms.colonist;

import java.util.HashMap;
import java.util.Map;

public class ColonyList extends HashMap<Integer, Colony>
{
	private static final long serialVersionUID = -6403462913762486430L;
	

	public ColonyList(int initCounter)
	{
		super();
		Colony.setCOUNTER(initCounter);
	}
	

	public void addColony(Colony colony)
	{
		Colony.setCOUNTER(Colony.getCOUNTER()+1);
		put(colony.getId(), colony);
	}

}
