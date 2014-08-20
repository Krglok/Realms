package net.krglok.realms.kingdom;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;

/**
 * <pre>
 * The Realm Class represents the  hierarchical top of the feudal system.
 * The settlements are the Plots of the  feudal system.
 * The members of the realms are owners of settlements.
 * The owners are players.
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
	private Owner owner;
	private MemberList memberList;
	private Boolean isNPCkingdom;
	
	public Kingdom()
	{
		ID++;
		id = ID;
		name = NEW_REALM;
		memberList = new MemberList();
		owner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "NPC1", 0, true); //null;  //new Owner());
		isNPCkingdom = owner.isNPC();
	}

	
	
	public Kingdom(int id, String name, Owner owner, MemberList memberList,
			Boolean isNPCkingdom)
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.memberList = memberList;
		this.isNPCkingdom = isNPCkingdom;
	}



	/**
	 * Klassenmethode zum setzen der laufenden Nummer der Klasseninstanz 
	 * @param value
	 */
	static void initID(int value)
	{
		ID = value;
	}
	
	static int getID()
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
		this.owner.setLevel(MemberLevel.MEMBER_KING);
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
	
	
}
