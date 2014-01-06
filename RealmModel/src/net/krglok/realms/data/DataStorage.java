package net.krglok.realms.data;

import net.krglok.realms.Realms;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Realm;
import net.krglok.realms.core.Settlement;
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
	private static final String NPC_0 = "NPC0";
	private static final String NPC_1 = "NPC1";
	private static final String NPC_2 = "NPC2";
	private static final String NPC_4 = "NPC4";
	private static final String PC_3 = "NPC3";
	private static final String PC_4 = "NPC4";
	private static final String PC_5 = "NPC5";
	private static final String Realm_1_NPC = "Realm 1 NPC";

	private OwnerList owners ;
	private RealmList realms ;
	private SettlementList settlements;
	private BuildingList buildings; 
	
	private SettlementData settleData;
	
	private Realms plugin;
	
	public DataStorage(Realms plugin)
	{
		this.plugin = plugin;
		settleData = new SettlementData(plugin);
		owners = new OwnerList();
		realms = new RealmList();
		settlements = new SettlementList(0);
		npcOwners();
		npcRealms(owners.getOwner(NPC_0));
	}
	
	/**
	 * must be done at first init for realmModel
	 */
	public OwnerList npcOwners()
	{
		
		owners.addOwner(new Owner(0, MemberLevel.MEMBER_NONE, 0, NPC_0, 1, true));
//		owners.addOwner(new Owner(1, MemberLevel.MEMBER_NONE, 0, NPC_1, 0, true));
//		owners.addOwner(new Owner(2, MemberLevel.MEMBER_NONE, 0, NPC_2, 0, true));
//		owners.addOwner(new Owner(3, MemberLevel.MEMBER_NONE, 0, PC_3, 0, false));
//		owners.addOwner(new Owner(4, MemberLevel.MEMBER_NONE, 0, PC_4, 0, false));
//		owners.addOwner(new Owner(5, MemberLevel.MEMBER_NONE, 0, PC_5, 0, false));
		
		return owners; 
	}
	
	
	/**
	 * must be done after initOwners
	 */
	public RealmList npcRealms(Owner owner)
	{
		realms.addRealm(new Realm(1, Realm_1_NPC, owner, new MemberList(), true));
		
		return realms; 
	}
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	public SettlementList initSettlements()
	{
		return settlements;
	}

	/**
	 * write settlement to dataFile
	 * @param settle
	 */
	public void writeSettlement(Settlement settle)
	{
		settleData.writeSettledata(settle);
	}
	
	private Settlement initSettlement()
	{
		Settlement settle = new Settlement();
		
		
		settle.setBuildingList(initBuildings(settle));
		return settle;
	}

	
	private BuildingList initBuildings(Settlement settle)
	{
		BuildingList buildingList = new BuildingList();
		
		return buildingList;
	}

	@Override
	public RealmList initRealms()
	{
		// TODO Auto-generated method stub
		return realms;
	}

	@Override
	public OwnerList initOwners()
	{
		// TODO Auto-generated method stub
		return owners;
	}

	
}