package net.krglok.realms.npc;

import java.util.HashMap;

public class NpcList extends HashMap<Integer, NpcData>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2636459492773906871L;

	public int checkId(int ref)
	{
		if (ref == 0) { ref = 1; }
		while (this.containsKey(ref))
		{
			ref++;
		}
		NpcData.initCOUNTER(ref);
		return NpcData.getCOUNTER();
	}
	
	public void add(NpcData npc)
	{
		int key = checkId(npc.getId());
		npc.setId(key);
		put(key,npc);
	}
	
	public void putNpc(NpcData npc)
	{
		if (npc != null)
		{
			put(npc.getId(), npc);
		}
	}
	
	public NpcData getNpc(String name)
	{
		for (NpcData npc : this.values())
		{
			if (npc.getName().equalsIgnoreCase(name))
			{
				return npc;
			}
		}
			
		return null;
	}
	
	public NpcList getSubList(int settleId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getSettleId() == settleId)
			{
				subList.putNpc(npc);
			}
		}
		
		return subList;
	}
	
	public NpcList getBuildingNpc(int buildingId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getHomeBuilding() == buildingId)
			{
				subList.putNpc(npc);
			}
		}
		
		return subList;
	}
	
	public NpcList getWorkerNpc(int buildingId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getWorkBuilding() == buildingId)
			{
				subList.putNpc(npc);
			}
		}
		
		return subList;
	}

	public NpcList getParentNpc(int npcId)
	{
		NpcList subList = new NpcList();
		NpcData npc = this.get(npcId);
		if (npc != null)
		{
			NpcData parent = get(npc.getFather());
			if (parent != null)
			{
				subList.putNpc(npc);
			}
			parent = get(npc.getMother());
			if (parent != null)
			{
				subList.putNpc(npc);
			}
		}
		return subList;
	}
	
	public NpcList getFamilyNpc(int npcId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getFather() == npcId)
			{
				subList.putNpc(npc);
			}
		}
		return subList;
	}

}
