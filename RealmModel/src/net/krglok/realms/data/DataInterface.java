package net.krglok.realms.data;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.core.Settlement;
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
	public KingdomList initKingdoms();
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	public SettlementList initSettlements();
	
	public void writeSettlement(Settlement settle);

	public BuildPlanMap readTMXBuildPlan(BuildPlanType bType, int radius, int offSet, String path);


}
