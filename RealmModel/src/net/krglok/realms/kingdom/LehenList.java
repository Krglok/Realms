package net.krglok.realms.kingdom;

import java.util.HashMap;
import java.util.Iterator;

import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

public class LehenList extends HashMap<Integer,Lehen>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4448871082715576355L;
	
	public LehenList()
	{
		
	}
	
	
	public Lehen getLehen(int lehenId)
	{
		if (this.containsKey(lehenId))
		{
			return this.get(lehenId);
		}
		return null;
	}
	
	public Lehen getLehen(String lehenName)
	{
		for (Lehen lehen : this.values())
		{
			if (lehen.getName().equalsIgnoreCase(lehenName))
			{
				return lehen;
			}
		}
		return null;
	}
	
	public int checkID(int ref)
	{
		if (ref == 0) { ref = 1;}
		while (this.containsKey(ref))
		{
			ref++;
		}
		Lehen.initLfdID(ref);
		return ref;
	}
	
	/**
	 * new entry in list with a new id
	 * 
	 * @param lehen
	 */
	public void addLehen(Lehen lehen)
	{
		int key = checkID(lehen.getId());
		lehen.setId(key);
		this.put(key,lehen);
	}

	public void addLehenList(LehenList newList)
	{
		for (Lehen lehen : newList.values())
		{
			put(lehen.getId(), lehen);
		}
	}

	/**
	 * put lehen in list
	 * overwrite existing lehen with same id
	 * 
	 * @param lehen
	 */
	public void putLehen(Lehen lehen)
	{
		this.put(lehen.getId(),lehen);
	}
	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	public LehenList getChildList(Lehen parent)
	{
		LehenList memberList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getParentId() == lehen.getId())
			{
				memberList.put(lehen.getId(), lehen);
			}
		}
		
		return memberList;
	}
	
	/**
	 * 
	 * @param kingdomId
	 * @return
	 */
	public LehenList getKingdomLehen(int kingdomId)
	{
		LehenList memberList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getKingdomId() == kingdomId)
			{
				memberList.put(lehen.getId(), lehen);
			}
		}
		
		return memberList;
	}
	
	/**
	 * 
	 * @param kingdomId
	 * @return
	 */
	public Lehen getKingdomRoot(int kingdomId)
	{
		if (kingdomId > 0)
		{
			for (Lehen lehen : this.values())
			{
				if (lehen.getKingdomId() == kingdomId)
				{
					if (lehen.getNobleLevel() == NobleLevel.KING)
					{
						return lehen;
					}
				}
			}
		}
		return null;
	}

	public LehenList getParentList(int parent)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getParentId() == parent)
			{
				subList.put(lehen.getId(), lehen);
			}
		}
		
		return subList;
	}
	
	/**
	 * check for uuid of Owner for lehen list 
	 * 
	 * @param owner
	 * @return
	 */
	public LehenList getSubList(Owner owner)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getOwner().getUuid().equals(owner.getUuid()))
			{
				subList.put(lehen.getId(), lehen);
			}
		}
		
		return subList;
	}

	public LehenList getSubList(String ownerId)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getOwner().getPlayerName().equalsIgnoreCase(ownerId))
			{
				subList.put(lehen.getId(), lehen);
			}
		}
		
		return subList;
	}
	

	public LehenList getSubList(int kingdomId)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getKingdomId() == kingdomId)
			{
				subList.put(lehen.getId(), lehen);
			}
		}
		
		return subList;
	}
	
	public LehenList getChildList(int Id)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getParentId() == Id)
			{
				subList.put(lehen.getId(), lehen);
			}
		}
		
		return subList;
	}
	
	public LehenList getAllChild(int parentId)
	{
		LehenList subList = new LehenList();
		for (Lehen lehen : this.values())
		{
			if (lehen.getParentId() == parentId)
			{
				subList.put(lehen.getId(), lehen);
				subList.addLehenList(getChildList(lehen.getId()));
			}
		}
		return subList;
		
	}
	
	public Boolean containsName(String lehenName)
	{
		for (Lehen lehen : this.values())
		{
			if (lehen.getName().equalsIgnoreCase(lehenName))
			{
				return true;
			}
		}
		
		return false;
	}

	public Lehen getHighLevel()
	{
		Lehen result = null;
		if (this.size() > 0)
		{
			NobleLevel level = NobleLevel.COMMONER;
			Iterator<Lehen> Entries = this.values().iterator();
			while (Entries.hasNext())
			{
				Lehen lehen = Entries.next();
				if (lehen.getNobleLevel().getValue() > level.getValue())
				{
					result = lehen;
				}
			}
		}
		return result;
	}
}
