package net.krglok.realms.core;

import java.util.HashMap;

import net.krglok.realms.kingdom.Kingdom;

/**
 * <pre>
 * realize a list of all owners in the model.
 * there are different type of owners players and NPC
 * only the playerName are relevant for the model
 * NPC names are generated automaticly and most are symbolic names
 * </pre>
 * @author oduda
 *
 */
public class OwnerList extends HashMap<Integer,Owner>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163612884108422109L;


	public OwnerList()
	{
		
	}
	
	public int checkID(int ref)
	{
		while (this.containsKey(ref))
		{
			ref++;
		}
		Owner.initID(ref);
		return Owner.getID();
	}
	
	public boolean containUuid(String uuid)
	{
		for (Owner owner : this.values())
		{
			if (owner.getUuid().equalsIgnoreCase(uuid))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * owner are unique in the ownerlist.
	 * give a new ID
	 * @param owner
	 */
	public void addOwner(Owner owner)
	{
		int key = checkID(owner.getId());
		owner.setId(key);
		this.put(key,owner);
	}
	
	/**
	 * add a new owner to the list
	 * 
	 * @param playerName
	 * @param level
	 * @param isNPC
	 * @param uuid
	 * @return
	 */
	public Owner addOwner(String playerName, NobleLevel level, Boolean isNPC, String uuid, int kingdomId)
	{
		Owner owner = new Owner();
		owner.setId(checkID(0));
		owner.setLevel(level);
		owner.setIsNPC(isNPC);
		owner.setUuid(uuid);
		owner.setKingdomId(kingdomId);
		owner.setNobleLevel(NobleLevel.COMMONER);
		owner.setCommonLevel(CommonLevel.COLONIST);
		this.put(owner.getId(), owner);
		return owner;
	}
	
	public Owner getOwner(int id)
	{
		return this.get(id);
	}

	public Owner getOwner(String uuid)
	{
		for (Owner owner : this.values())
		{
			if (owner.getUuid().equals(uuid))
			{
				return owner;
			}
		}
		return null;
	}

	public Owner getOwnerName(String value)
	{
		for (Owner owner : this.values())
		{
			if (owner.getPlayerName().equalsIgnoreCase(value))
			{
				return owner;
			}
		}
		return null;
	}
	

	/**
	 * Set the realm reference of the owner
	 * @param realmId
	 */
	public void setKingdom(String uuid, int kingdomId)
	{
		String key = uuid;
		Owner owner = this.get(key);
		owner.setKingdomId(kingdomId);
	}
	
	/**
	 * find playername in ownerlist
	 * @param value
	 * @return  null if not found
	 */
	public Owner findPlayername(String value)
	{
		for (Owner owner : this.values())
		{
			if (owner.getPlayerName().equalsIgnoreCase(value))
			{
				return owner;
			}
		}
		
		return null;
	}
	
	
	public OwnerList getSubList(Kingdom kingdom)
	{
		OwnerList subList = new OwnerList();
		for (Owner owner : this.values())
		{
			if (owner.getKingdomId() == kingdom.getId())
			{
				subList.put(owner.getId(), owner);
			}
		}
		return subList;
	}
}
