package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

/**
 * list of members in a kingdom
 * @author Windu
 *
 */
public class MemberList 
{
	private Map<String, Owner> memberList;
	
	
	public MemberList()
	{
		setMemberList(new HashMap<String, Owner>());
	}

	public Map<String, Owner> getMemberList() {
		return memberList;
	}

	public void setMemberList(Map<String, Owner> memberList) {
		this.memberList = memberList;
		
	}

	public int size()
	{
		return this.memberList.size();
	}
	
	public Boolean isEmpty()
	{
		return this.memberList.isEmpty();
	}
	
	/**
	 * insert member in List. members are unique based on playerName
	 * @param member
	 */
	public void addMember(Owner member)
	{
		String key = member.getPlayerName();
		if (!memberList.containsKey(key))
		{
			memberList.put(key, member);
		}
	}
	
	public Owner getMember(String playerName)
	{
		Owner member = null;
		if (!memberList.containsKey(playerName))
		{
			member = memberList.get(playerName);
		}
		return member;
	}
	
	public Boolean contains(String playerName)
	{
		return memberList.containsKey(playerName);
	}
}
