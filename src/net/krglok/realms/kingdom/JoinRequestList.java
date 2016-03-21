package net.krglok.realms.kingdom;

import java.util.HashMap;


public class JoinRequestList extends HashMap<Integer, Request>
{

	private static final long serialVersionUID = 2541903339818408329L;
	
	public int checkID(int ref)
	{
		if (ref == 0) { ref = 1;}
		while (this.containsKey(ref))
		{
			ref++;
		}
		Request.initID(ref);
		return Request.getID();
	}
	
	public void addRequest(Request request)
	{
		int key = checkID(1);
		request.setId(key);
		this.put(key, request);
	}

	public JoinRequestList getSubList(int status)
	{
		JoinRequestList subList = new JoinRequestList();
		
		for (Request request : this.values())
		{
			if (request.getStatus() == status)
			{
				subList.put(request.getId(), request);
			}
		}
		
		return subList;
	}
	

}
