package net.krglok.realms.manager;

import java.util.HashMap;

public class CampPositionList  extends HashMap<Integer,CampPosition>
{
	private static final long serialVersionUID = 9140109752906606753L;

	
	public CampPositionList()
	{
		
	}
	
	
	public void initID(int value)
	{
		CampPosition.COUNTER = value;
	}
	
	
	public int checkId(int ref)
	{
		if (ref == 0) { ref = 1; }
		while (this.containsKey(ref))
		{
			ref++;
		}
		CampPosition.initCOUNTER(ref);
		return CampPosition.getCOUNTER();
	}

	
	public void addCampPosition(CampPosition campPos)
	{
		int key = checkId(campPos.getId());
		campPos.setId(key);
		put(key,campPos);
	}
	
	
	public void putCampPos(CampPosition campPos)
	{
		put(campPos.getId(),campPos);
	}
	
	public CampPosition getCampPosition(int id)
	{
		if (this.containsKey(id))
		{
			return get(id);
		}
		return null;
	}
	
	public CampPositionList getSubList(int settleId)
	{
		CampPositionList subList = new CampPositionList();
		for (CampPosition camp : this.values())
		{
			if (camp.getSettleId() == settleId)
			{
				subList.put(camp.getId(), camp);
			}
		}
		return subList;
	}
	
	
	public CampPositionList getSubList(String world)
	{
		CampPositionList subList = new CampPositionList();
		for (CampPosition camp : this.values())
		{
			if (camp.getPosition().getWorld().equalsIgnoreCase(world))
			{
				subList.put(camp.getId(), camp);
			}
		}
		return subList;
	}
	
	
}
