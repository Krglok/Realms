package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

/**
 * realize a list of all realms in the model
 * 
 * @author oduda
 *
 */
public class RealmList
{
	private Map<String,Realm> realmList;
	
	public RealmList()
	{
		this(0);
	}

	public RealmList(int initCounter)
	{
		Realm.initID(initCounter);
		setRealms(new HashMap<String,Realm>());
//		this.addRealm(new Realm());
	}

	public Map<String,Realm> getRealms()
	{
		return realmList;
	}

	public void setRealms(Map<String,Realm> realms)
	{
		this.realmList = realms;
	}
	
	/**
	 * realmId are unique in the realmList.
	 * @param realm
	 */
	public void addRealm(Realm realm)
	{
		String key = String.valueOf(realm.getId());
		realmList.put(key, realm);
	}
	
	/**
	 * set the owner of the realm
	 * set owner to level King
	 * check if owner in memberList of the realm
	 * @param owner
	 * @param realmId
	 */
	public void setOwner(Owner owner, int realmId)
	{
		String key = String.valueOf(realmId);
		Realm realm = realmList.get(key);
		realm.setOwner(owner);
	}
	
	
	public Realm getRealm(int realmId)
	{
		return realmList.get(String.valueOf(realmId));
	}
	
	public int size()
	{
		return realmList.size();
	}

	public int getCounter()
	{
		return Realm.getID();
	}
}
