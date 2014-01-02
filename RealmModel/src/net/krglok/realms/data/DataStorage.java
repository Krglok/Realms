package net.krglok.realms.data;

import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.RealmList;
import net.krglok.realms.core.OwnerList;

/**
 * 
 * @author Windu
 *
 */
public class DataStorage implements DataInterface
{

	public DataStorage()
	{
		
	}
	
	/**
	 * must be done at first init for realmModel
	 */
	public OwnerList initOwners()
	{
		return new OwnerList();
	}
	
	
	/**
	 * must be done after initOwners
	 */
	public RealmList initRealms()
	{
		return new RealmList();
	}
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	public SettlementList initSettlements()
	{
		return new SettlementList(0);
	}
	
}
