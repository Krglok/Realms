package net.krglok.realms.kingdom;

import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettlementList;

/**
 * <pre>
 * The Realm Class represents the  hierarchical top of the feudal system.
 * The settlements are the Plots of the  feudal system.
 * The members of the realms are owners of settlements.
 * The owners are players or NPC.
 * 
 * 
 * </pre>
 * @author krglok
 *
 *  
 */

public class Kingdom 
{
	private static final String NEW_REALM = "NewRealm";
	private static int ID ;
	
	private int id;
	private String name;
	private int ownerId; 
	private Owner owner;
	private MemberList memberList;
	private SettlementList settlements;
	private Boolean isNPCkingdom;
	private LehenList lehenList;
	
	public Kingdom()
	{
		ID++;
		id = ID;
		name = NEW_REALM;
		memberList = new MemberList();
		settlements = new SettlementList();
		lehenList = new LehenList();
		owner = new Owner(0, NobleLevel.COMMONER, 0, "NPC1", 0, true, ""); //null;  //new Owner());
		isNPCkingdom = owner.isNPC();
	}

	
	
	public Kingdom(int id, String name, Owner owner, Boolean isNPCkingdom)
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		memberList = new MemberList();
		settlements = new SettlementList();
		lehenList = new LehenList();
		this.isNPCkingdom = isNPCkingdom;
	}

	public static Kingdom initDefaultKingdom(OwnerList owners)
	{
		int id = 0; 
		String name = "Freie Siedler"; 
		Owner owner = owners.getOwner(0);
		boolean isNPCkingdom = true;
		
		return new Kingdom( id,  name,  owner,  isNPCkingdom);
	}


	/**
	 * Klassenmethode zum setzen der laufenden Nummer der Klasseninstanz 
	 * @param value
	 */
	public static void initID(int value)
	{
		ID = value;
	}
	
	public static int getID()
	{
		return ID;
	}
	

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Owner getOwner() 
	{
		return owner;
	}

	public void setOwner(Owner owner) 
	{
		this.owner = owner;
		this.owner.setLevel(NobleLevel.KING);
		if (!memberList.contains(this.owner.getPlayerName()))
		{
			memberList.addMember(this.owner);
		}
	}

	public MemberList getMemberList() 
	{
		return memberList;
	}

	public void setMemberList(MemberList arrayList) 
	{
		this.memberList = arrayList;
	}

	public void addMember(Owner member)
	{
		memberList.addMember(member);
	}
	
	

	public Boolean isNPCkingdom() {
		return isNPCkingdom;
	}

	public void setIsNPCkingdom(Boolean isNPCRealm) {
		this.isNPCkingdom = isNPCRealm;
	}



	/**
	 * @return the ownerId
	 */
	public int getOwnerId()
	{
		return ownerId;
	}



	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}



	/**
	 * @return the settlements
	 */
	public SettlementList getSettlements()
	{
		return settlements;
	}



	/**
	 * 
	 * @param the settlementList for this kingdom 
	 */
	public void setSettlements(SettlementList settlements)
	{
		this.settlements = settlements;
	}

	/**
	 * normally the global SettlementList are given as source
	 * hint: only useful is memberList are set
	 * 
	 * @param source
	 */
	public void initSettleList(SettlementList source)
	{
		settlements.clear();
		if (this.owner != null)
		{
			settlements .addSettlements(source.getSubList(this.owner));
		}
		for (Owner member : memberList.values())
		{
			settlements.addSettlements(source.getSubList(member));
		}
		
	}
	
	/**
	 * 
	 * @param settleId
	 * @return
	 */
	public boolean containSettlement(int settleId)
	{
		return getSettlements().containsID(settleId);
	}
	
}
