package net.krglok.realms.data;

import net.krglok.realms.core.RealmModel;

/**
 * definiert das Interace fuer den import von Daten
 * z.B: aus einer Testumgebung oder aus einem Datafile
 * @author Windu
 *
 */
public interface DataInterface
{

	/**
	 * must be done at first init for realmModel
	 */
	public void initOwners(RealmModel realmModel);
	
	
	/**
	 * must be done after initOwners
	 */
	public void initRealms(RealmModel realmModel);
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	public void initSettlements(RealmModel realmModel);

}
