package net.krglok.realms.data;

import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.RealmList;
import net.krglok.realms.core.SettlementList;

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
	public OwnerList initOwners();
	
	
	/**
	 * must be done after initOwners
	 */
	public RealmList initRealms();
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	public SettlementList initSettlements();

}
