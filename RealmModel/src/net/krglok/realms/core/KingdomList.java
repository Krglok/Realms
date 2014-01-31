package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

/**
 * realize a list of all kingdoms in the model
 * 
 * @author oduda
 *
 */
public class KingdomList
{
	private Map<String,Kingdom> kingdomList;
	
	public KingdomList()
	{
		this(0);
	}

	public KingdomList(int initCounter)
	{
		Kingdom.initID(initCounter);
		kingdomList = new HashMap<String,Kingdom>();
//		this.addRealm(new Realm());
	}

	public Map<String,Kingdom> getKingdoms()
	{
		return kingdomList;
	}

	public void setKingdoms(Map<String,Kingdom> kingdoms)
	{
		this.kingdomList = kingdoms;
	}
	
	/**
	 * realmId are unique in the realmList.
	 * @param kingdom
	 */
	public void addKingdom(Kingdom kingdom)
	{
		String key = String.valueOf(kingdom.getId());
		kingdomList.put(key, kingdom);
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
		Kingdom realm = kingdomList.get(key);
		realm.setOwner(owner);
	}
	
	
	public Kingdom getKingdom(int realmId)
	{
		return kingdomList.get(String.valueOf(realmId));
	}
	
	public int size()
	{
		return kingdomList.size();
	}

	public int getCounter()
	{
		return Kingdom.getID();
	}
}
