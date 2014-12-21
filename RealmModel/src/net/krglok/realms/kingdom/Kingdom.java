package net.krglok.realms.kingdom;

import net.krglok.realms.core.NobleLevel;
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
	private Boolean isNPCkingdom;
	private MemberList members;
	
	public Kingdom()
	{
		ID++;
		id = ID;
		name = NEW_REALM;
		owner = new Owner(0, NobleLevel.COMMONER, 0, "NPC1", 0, true, ""); //null;  //new Owner());
		isNPCkingdom = owner.isNPC();
		members = new MemberList();
	}

	
	
	public Kingdom(int id, String name, Owner owner, Boolean isNPCkingdom)
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.isNPCkingdom = isNPCkingdom;
		members = new MemberList();
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

	public void initMembers(OwnerList owners)
	{
		for (Owner owner : owners.values())
		{
			if(this.id == owner.getKingdomId())
			{
				this.members.addMember(owner);
			}
		}
	}



	public MemberList getMembers()
	{
		return members;
	}

	
}
