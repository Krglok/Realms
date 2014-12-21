package net.krglok.realms.data;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.science.CaseBookList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;

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
	public OwnerList getOwners();
	
	public BuildingList getBuildings();
	
	public LehenList getLehen();
	
	
	/**
	 * must be done after initOwners
	 */
	public KingdomList getKingdoms();
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 * @return SettlementList from datafile
	 */
	public SettlementList getSettlements();
	
	/**
	 * initialize the RegimentList
	 * must be done after initSettlements
	 * @return RegimentList from datafile
	 */
	public RegimentList getRegiments();
	
	public CaseBookList getCaseBooks();
	
	public void writeSettlement(Settlement settle);
	
	public void writeRegiment(Regiment regiment);
	
	public void writeCaseBook(CaseBook caseBook);
	
	public void writeBuilding(Building building);
	
	public void writeKingdom(Kingdom kingdom);
	
	public void writeLehen(Lehen lehen);

	public void writeOwner(Owner owner);
	
	public BuildPlanMap readTMXBuildPlan(BuildPlanType bType, int radius, int offSet);
	
	public ItemPriceList getPriceList();

	void addPrice(String itemRef, Double price);



}
