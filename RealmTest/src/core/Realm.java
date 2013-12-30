package core;

import java.util.ArrayList;

/**
 * 
 * @author oduda
 *
 * The Realm Class represents the overal Container for thesettlements.
 * The members of the realms are owners of settlements.
 * The owners are players.
 * The Realm will be referenced in the settlements of the owners 
 */

public class Realm 
{
	private int id;
	private String name;
	private Member owner;
	private ArrayList<Member> memberList; 
	
	public Realm()
	{
		setId(0);
		setName("NewRealm");
		setOwner(new Member());
		setMemberList(new ArrayList<Member>());
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

	public Member getOwner() 
	{
		return owner;
	}

	public void setOwner(Member owner) 
	{
		this.owner = owner;
	}

	public ArrayList<Member> getMemberList() 
	{
		return memberList;
	}

	public void setMemberList(ArrayList<Member> arrayList) 
	{
		this.memberList = arrayList;
	}

	
	
}
