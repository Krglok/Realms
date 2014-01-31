package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

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
public class OwnerList
{

	private Map<String,Owner> ownerList;

	public OwnerList()
	{
		setOwners(new HashMap<String,Owner>());
	}

	public Map<String,Owner> getOwners()
	{
		return ownerList;
	}

	public void setOwners(Map<String,Owner> owners)
	{
		this.ownerList = owners;
	}
	
	/**
	 * owner are unique in the ownerlist.
	 * @param owner
	 */
	public void addOwner(Owner owner)
	{
		String key = owner.getPlayerName();
		ownerList.put(key,owner);
	}
	
	public Owner addOwner(String playerName, MemberLevel level, Boolean isNPC)
	{
		Owner owner = new Owner();
		owner.setLevel(level);
		owner.setIsNPC(isNPC);
		ownerList.put(playerName, owner);
		return owner;
	}
	
	public Owner getOwner(String playerName)
	{
		return ownerList.get(playerName);
	}
	
	public int size()
	{
		return ownerList.size();
	}
	
	/**
	 * Set the realm reference of the owner
	 * @param realmId
	 */
	public void setRealm(String playerName, int realmId)
	{
		String key = playerName;
		Owner owner = ownerList.get(key);
		owner.setRealmID(realmId);
	}
	
}
