package net.krglok.realms.data;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Kingdom;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.core.OwnerList;

/**
 * 
 * @author Windu
 *
 */
public class DataStorage implements DataInterface
{
	private static final String NPC_0 = "NPC0";
//	private static final String NPC_1 = "NPC1";
//	private static final String NPC_2 = "NPC2";
//	private static final String NPC_4 = "NPC4";
//	private static final String PC_3 = "NPC3";
//	private static final String PC_4 = "NPC4";
//	private static final String PC_5 = "NPC5";
	private static final String Realm_1_NPC = "Realm 1 NPC";

	private OwnerList owners ;
	private KingdomList kingdoms ;
	private SettlementList settlements;
	
	private PriceData priceData;
	private ItemPriceList priceList ;
	
	private SettlementData settleData;
	
	private Realms plugin;
	
	public DataStorage(Realms plugin)
	{
		this.plugin = plugin;
		settleData = new SettlementData(plugin.getDataFolder());
		owners = new OwnerList();
		kingdoms = new KingdomList();
		settlements = new SettlementList(0);
		priceData = new PriceData(plugin);
		priceList = new ItemPriceList();
	}
	
	public boolean initData()
	{
		Boolean isReady = false;
		priceList = priceData.readPriceData();
		npcOwners();
		npcRealms(owners.getOwner(NPC_0));
		ArrayList<String> settleInit = settleData.readSettleList();
		isReady = initSettlements(settleInit);
		return isReady;
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
	public KingdomList npcRealms(Owner owner)
	{
		kingdoms.addKingdom(new Kingdom(1, Realm_1_NPC, owner, new MemberList(), true));
		
		return kingdoms; 
	}
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	private boolean initSettlements(ArrayList<String> settleInit)
	{
		for (String settleId : settleInit)
		{
			plugin.getMessageData().log("SettleRead: "+settleId );
			settlements.addSettlement(readSettlement(Integer.valueOf(settleId)));
		}
		return true;
	}

	/**
	 * write settlement to dataFile
	 * @param settle
	 */
	public void writeSettlement(Settlement settle)
	{
		settleData.writeSettledata(settle);
	}

	/**
	 * Read Settlement from File
	 * normaly not used !!
	 * @param id
	 * @return
	 */
	public Settlement readSettlement(int id)
	{
		return settleData.readSettledata(id);
	}
	
//	private Settlement initSettlement()
//	{
//		Settlement settle = new Settlement();
//		settle = settleData.readSettledata(id)
//		return settle;
//	}

	
	@Override
	public KingdomList initKingdoms()
	{
		// TODO Auto-generated method stub
		return kingdoms;
	}

	@Override
	public OwnerList initOwners()
	{
		// TODO Auto-generated method stub
		return owners;
	}

	public ItemPriceList getPriceList()
	{
		return priceList;
	}

	public void addPrice(String itemRef, Double price)
	{
		priceList.add(itemRef, price);
		priceData.writePriceData(priceList);
	}

	@Override
	public SettlementList initSettlements()
	{
		// TODO Auto-generated method stub
		plugin.getMessageData().log("SettleInit: ");
		return settlements;
	}
	
}
