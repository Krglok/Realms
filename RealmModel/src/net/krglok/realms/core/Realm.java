package net.krglok.realms.core;

/**
 * 
 * @author krglok
 *
 * The Realm Class represents the  hierarchical top of the feudal system.
 * The settlements are the Plots of the  feudal system.
 * The members of the realms are owners of settlements.
 * The owners are players.
 *  
 */

public class Realm 
{
	private static final String NEW_REALM = "NewRealm";
	private static int ID ;
	private int id;
	private String name;
	private Owner owner;
	private MemberList memberList;
	private Boolean isNPCRealm;
	
	public Realm()
	{
		ID++;
		id = ID;
		name = NEW_REALM;
		memberList = new MemberList();
		owner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "NPC1", 0, true); //null;  //new Owner());
		isNPCRealm = owner.isNPC();
	}

	
	
	public Realm(int id, String name, Owner owner, MemberList memberList,
			Boolean isNPCRealm)
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.memberList = memberList;
		this.isNPCRealm = isNPCRealm;
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
	
	

	public Boolean isNPCRealm() {
		return isNPCRealm;
	}

	public void setIsNPCRealm(Boolean isNPCRealm) {
		this.isNPCRealm = isNPCRealm;
	}
	
	
}
