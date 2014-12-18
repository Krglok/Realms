package net.krglok.realms.kingdom;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

/**
 * realize a list of all kingdoms in the model
 * 
 * @author oduda
 *
 */
public class KingdomList extends HashMap<Integer,Kingdom>
{
	
	private static final long serialVersionUID = -6434347912718528865L;

//	private Map<String,Kingdom> kingdomList;
	
	public KingdomList()
	{
		
	}

	public KingdomList(int initCounter)
	{
		Kingdom.initID(initCounter);
//		this.addRealm(new Realm());
	}

	public int checkID(int ref)
	{
		while (this.containsKey(ref))
		{
			ref++;
		}
		Kingdom.initID(ref);
		return Kingdom.getID();
	}
	
	/**
	 * realmId are unique in the realmList.
	 * give a new Id
	 * @param kingdom
	 */
	public void addKingdom(Kingdom kingdom)
	{
		Integer key = checkID(kingdom.getId());
		kingdom.setId(key);
		this.put(key, kingdom);
	}
	
	/**
	 * add owner to member list, if not already a member 
	 * @param kingdomId
	 * @param owner
	 * @return false if not added (wrong kingdom, already member)
	 */
	public boolean addMember(int kingdomId, Owner owner)
	{
		Kingdom kingdom = this.get(kingdomId);
		if (kingdom != null)
		{
			owner.setKingdomId(kingdomId);
		}
		return false;
	}
	
	/**
	 * set the owner of the realm
	 * set owner to level King
	 * check if owner in memberList of the realm
	 * @param owner
	 * @param kingdomId
	 */
	public void setOwner(Owner owner, int kingdomId)
	{
		String key = String.valueOf(kingdomId);
		Kingdom kingdom = this.get(key);
		kingdom.setOwner(owner);
	}
	
	/**
	 * get kingdom by number
	 * @param kingdomId
	 * @return null if not contains
	 */
	public Kingdom getKingdom(int kingdomId)
	{
		if (this.containsKey(kingdomId))
		{
			return this.get(kingdomId);
		} else
		{
			return null;
		}
	}
	
	/**
	 * check for value is kingdom.name
	 * @param value
	 * @return null if not contains
	 */
	public Kingdom findKingdom(String value)
	{
		for (Kingdom kingdom : this.values())
		{
			if (kingdom.getName().equalsIgnoreCase(value))
			{
				return kingdom;
			}
			
		}
		return null;
	}
	
	/**
	 * check for uuid of owner and kingdom.owner
	 * @param owner
	 * @return null if not contains
	 */
	public Kingdom findKingdom(Owner owner)
	{
		for (Kingdom kingdom : this.values())
		{
			if (kingdom.getOwner().getUuid().equals(owner.getUuid()))
			{
				return kingdom;
			}
			
		}
		return null;
	}
	
	/**
	 * scan for owner or member in KingdomList. use uuid for check
	 * 
	 * @param owner
	 * @return
	 */
	public Kingdom findKingdomOfMember(Owner owner)
	{
		Kingdom found = null;
		found = findKingdom(owner);
		if (found == null)
		{
			for (Kingdom kingdom : this.values())
			{
				if (kingdom.getOwner().getKingdomId() == kingdom.getId())
				{
					return kingdom;
				}
			}
		}
		return found;
	}
	
	/**
	 * scan member list for owner. the owner of the kingdom is also a member.
	 * 
	 * @param owner
	 * @param kingdom
	 * @return
	 */
	public boolean isMember(Owner owner, Kingdom kingdom)
	{
		if (owner == null) { return false; }
		if (kingdom == null) { return false; }
		if (kingdom.getId() == owner.getKingdomId())
		{
			return true;
		}
		return false;
	}
		

}
