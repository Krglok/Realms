package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

/**
 * list of members in a kingdom
 * key = uuid of the owner (player)
 * 
 * @author Windu
 *
 */
public class MemberList extends HashMap<String, Owner>
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7743487409334558577L;

	/**
	 * insert member in List. members are unique based on playerName
	 * @param member
	 */
	public void addMember(Owner member)
	{
		String key = member.getUuid();
		if (this.containsKey(key) == false)
		{
			this.put(key, member);
		}
	}
	
	public Owner getMember(String uuid)
	{
		Owner member = null;
		if (this.containsKey(uuid) == false)
		{
			member = this.get(uuid);
		}
		return member;
	}
	
	public Boolean contains(String playerName)
	{
		return this.containsKey(playerName);
	}
}
