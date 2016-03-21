package net.krglok.realms.npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import net.krglok.realms.unit.UnitList;
import net.krglok.realms.unit.UnitType;

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
	
	/**
	 * Erstellt eine Sublist fuer alle NPC eines settlement
	 * @param settleId
	 * @return
	 */
	public NpcList getSubListSettle(int settleId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getSettleId() == settleId)
			{
				if (npc.getUnitType() == UnitType.SETTLER)
				{
					subList.putNpc(npc);
				}
			}
		}
		
		return subList;
	}

	/**
	 * erstellt eien Sublist fuer alle NPC eines Lehens
	 * 
	 * @param lehenId
	 * @return
	 */
	public NpcList getSubListLehen(int lehenId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getLehenId() == lehenId)
			{
				// achtung hier werden alle NPC genommen, auch di in einem Regiment etc.
				if (npc.getNpcType() != NPCType.MILITARY)
				{
					subList.putNpc(npc);
				}
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

	public NpcData getBuildingNpcFirst(int buildingId)
	{
		for (NpcData npc : this.values())
		{
			if (npc.getHomeBuilding() == buildingId)
			{
				return npc;
			}
		}
		
		return null;
	}
	
	
	public NpcList getBuildingWorker(int buildingId)
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

	
	public NpcList getSettleWorker()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive())
			{
				if (npc.getNpcType() != NPCType.CHILD)
				{
					if (npc.getNpcType() != NPCType.MANAGER)
					{
						subList.putNpc(npc);
					}
				}
			}
		}
		
		return subList;
	}
	
	public NpcList getTaxSettler()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive())
			{
				if (npc.getNpcType() != NPCType.CHILD)
				{
					if (npc.getNpcType() != NPCType.BEGGAR)
					{
						subList.putNpc(npc);
					}
				}
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

	
	public NpcData getHusbandNpc(int npcId)
	{
//		NpcList subList = new NpcList();
		NpcData npc = this.get(npcId);
		NpcData parent = null;
		if (npc != null)
		{
			if (npc.isAlive() == false)
			{
				return null;
			}
			if (npc.isMaried())
			{
				// hat einen pc husband
				if (npc.getPcHusband() > 0)
				{
					// ein pc verbraucht keine nahrung
				} else
				{
					// suchen nach npc husband
					if (npc.getNpcHusband() > 0)
					{
						parent = this.get(npc.getNpcHusband());
						if (parent.isAlive())
						{
							// wohnen im gleichen Huas
							if (npc.getHomeBuilding() == parent.getHomeBuilding())
							{
								return parent;
							}
						} 
					}
				}
			}
		}
		return parent;
	}
	
	
	public NpcList getChildNpc(int npcId)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			// kind des NPC
			if ((npc.getFather() == npcId)
				|| (npc.getMother() == npcId)
				)
			{
				// ist nicht tot
				if (npc.isAlive())
				{
					// lebt im gleichen Haus
					if (npc.getHomeBuilding() == this.get(npcId).getHomeBuilding())
					{
						subList.putNpc(npc);
					}
				}
			}
		}
		return subList;
	}
	
	/**
	 * add self, the children and the husband to the list
	 * 
	 * @param npcId
	 * @return
	 */
	public NpcList getFamily(int npcId)
	{
		NpcList subList = getChildNpc(npcId);
		subList.putNpc(this.get(npcId)); 
		NpcData husband = getHusbandNpc(npcId);
		if (husband != null)
		{
			for (NpcData npc : getChildNpc(husband.getId()).values())
			{
				if (subList.containsKey(npc.getId()) == false)
				{
					subList.putNpc(npc);
				}
			}
			subList.putNpc(husband);
		}
		return subList;
	}

	public NpcList getWoman()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			// kind des NPC
			if (npc.getGender() == GenderType.WOMAN)
			{
				// ist nicht tot
				if (npc.isAlive())
				{
					// ist kein kind
					if (npc.getNpcType() != NPCType.CHILD)
					{
						subList.putNpc(npc);
					}
				}
			}
		}
		return subList;
	}

	public NpcList getChild()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			// kind des NPC
			if (npc.isAlive())
			{
				// ist kein kind
				if (npc.getNpcType() == NPCType.CHILD)
				{
					subList.putNpc(npc);
				}
			}
		}
		return subList;
	}
	
	
	public NpcList getDeathNpc()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			// kind des NPC
			if (npc.isAlive() == false)
			{
				subList.putNpc(npc);
			}
		}
		return subList;
	}

	
	public NpcList getAliveNpc()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive() == true)
			{
				subList.putNpc(npc);
			}
		}
		return subList;
	}

	public NpcList getAdultNpc()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive() == true)
			{
				if (npc.isChild() == false)
				{
					subList.putNpc(npc);
				}
			}
		}
		return subList;
	}

	
	public NpcList getBeggarNpc()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive() == true)
			{
				if (npc.getNpcType() == NPCType.BEGGAR)
				{
					subList.putNpc(npc);
				}
			}
		}
		return subList;
	}

	
	public NpcData getNpcType(NPCType npcType)
	{
		for (NpcData npc : this.values())
		{
			if (npc.getNpcType() == npcType)
			{
				return npc;
			}
		}
			
		return null;
	}
	
	
	public NpcList getNoHomeNpc()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isAlive() == true)
			{
				if (npc.getHomeBuilding() == 0)
				{
					subList.putNpc(npc);
				}
			}
		}
		return subList;
	}

	public NpcList getGender(GenderType gender)
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.getGender() == gender)
			{
				subList.putNpc(npc);
			}
		}
			
		return subList;
	}

	public NpcList getSchwanger()
	{
		NpcList subList = new NpcList();
		for (NpcData npc : this.values())
		{
			if (npc.isSchwanger())
			{
				subList.putNpc(npc);
			}
		}
			
		return subList;
	}

	
	public NpcData getCitizenId(int citizenId)
	{
		for (NpcData npc : this.values())
		{
			if (npc.spawnId == citizenId)
			{
				return npc;
			}
		}
		return null;
	}

	/**
	 * get any NPC that is not UnitType SETTLER 
	 * 
	 * @return
	 */
	public UnitList getSubListUnits()
	{
		UnitList subList = new UnitList();
		for (NpcData npc : this.values())
		{
			if (npc.getUnitType() != UnitType.SETTLER)
			{
				subList.putUnit(npc);
			}
		}
		return subList;
	}

	/**
	 * get any NPC that is MILITARY and has SettleID
	 * 
	 * @param settleId
	 * @return
	 */
	public UnitList getSubListSettleUnits(int settleId)
	{
		UnitList subList = new UnitList();
		for (NpcData npc : this.values())
		{
			if (npc.getNpcType() == NPCType.MILITARY)
			{
				if ((npc.getSettleId() == settleId)
					&& (npc.getRegimentId() == 0)
					)
				{
					subList.putUnit(npc);
				}
			}
		}
		return subList;
	}
	

	/**
	 * get any NPC that is MILITARY and has lehenID
	 * @param lehenId
	 * @return
	 */
	public UnitList getSubListLehenUnits(int lehenId)
	{
		UnitList subList = new UnitList();
		for (NpcData npc : this.values())
		{
			if (npc.getNpcType() == NPCType.MILITARY)
			{
				if ((npc.getLehenId() == lehenId)
					&& (npc.getRegimentId() == 0)
					)
				{
					subList.putUnit(npc);
				}
			}
		}
		return subList;
	}
	
	/**
	 * get any NPC that has regimentID
	 * 
	 * @param regimentId
	 * @return
	 */
	public UnitList getSubListRegiment(int regimentId)
	{
		UnitList subList = new UnitList();
		for (NpcData npc : this.values())
		{
//			if (npc.getNpcType() == NPCType.MILITARY)
//			{
				if ((npc.getRegimentId() == regimentId)
					)
				{
					subList.putUnit(npc);
				}
//			}
		}
		return subList;
	}

	public ArrayList<Integer> sortIntegerList(Set<Integer> set)
	{
		ArrayList<Integer> sortedItems = new ArrayList<Integer>();
		for (Integer s : set)
		{
			sortedItems.add(s);
		} 
		if (sortedItems.size() > 1)
		{
			Collections.sort(sortedItems);
		}
		return sortedItems;
	}
	
}
