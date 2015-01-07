package net.krglok.realms.kingdom;

import java.util.HashMap;

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
	/**
	 * new entry in list with a new id
	 * 
	 * @param lehen
	 */
	public void addLehen(Lehen lehen)
	{
		int key = lehen.getId();
		if (key > 0)
		{
			while (this.containsKey(key) == true)
			{
				key++;
			}
			Lehen.initID(key);
		} else
		{
			key = Lehen.nextID();
		}
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
	
	/**
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
	

}
