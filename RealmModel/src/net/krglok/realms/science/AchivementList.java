package net.krglok.realms.science;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * 
 * @author Windu
 *
 */

public class AchivementList extends HashMap<String, Achivement>
{

	private static final long serialVersionUID = 7174481908548876082L;

	
	public void add(Achivement achiv)
	{
		this.put(achiv.getName(), achiv);
	}
	
	public void add(AchivementList addList)
	{
		for (Achivement achiv : addList.values())
		{
			this.put(achiv.getName(), achiv);
		}
	}
	
	
	public AchivementList getSubList(AchivementType aType)
	{
		AchivementList subList = new AchivementList();
		
		for (Achivement achiev : this.values())
		{
			if (achiev.getAchiveType() == aType)
			{
				subList.put(achiev.getName(), achiev);
			}
		}
		return subList;
	}

	public AchivementList getSubList(AchivementName value)
	{
		AchivementList subList = new AchivementList();
		
		for (Achivement achiev : this.values())
		{
			if (achiev.getAchiveName() == value)
			{
				subList.put(achiev.getName(), achiev);
			}
		}
		return subList;
	}
	
	/**
	 * check is the specific AchivementName &  AchievementType is true
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains (AchivementName value, AchivementType aType)
	{
		for (Achivement achiev : this.getSubList(value).values())
		{
			if (achiev.getAchiveName() == value)
			{
				if (achiev.getAchiveType() == aType)
				{
					return achiev.isEnaled();
				}
			}
		}
		return false;
	}
	
	/**
	 * check is any AchievementType is true
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains (AchivementName value)
	{
		for (Achivement achiev : this.getSubList(value).values())
		{
			if (achiev.getAchiveName() == value)
			{
				return achiev.isEnaled();
			}
		}
		return false;
	}
	
	
	public Achivement get(AchivementName value, AchivementType aType )
	{
		
		for (Achivement achiev : this.values())
		{
			if (achiev.getAchiveName().toString().equalsIgnoreCase(value.name()) )
			{
				if (achiev.getAchiveType() == aType)
				{
					return achiev;
				}
			}
		}
		return null;
		
	}
	
	public ArrayList<String> sortItems()
	{
		ArrayList<String> sortedItems = new ArrayList<String>();
		for (String s : this.keySet())
		{
			sortedItems.add(s);
		}
		if (sortedItems.size() > 1)
		{
			Collections.sort
			(sortedItems,  String.CASE_INSENSITIVE_ORDER);
		}
		return sortedItems;
	}
	
}
