package net.krglok.realms.science;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.SettleType;


/**
 * key = AchivementName 
 * 
 * @author Windu
 *
 */
public class KnowledgeList extends HashMap<String, KnowledgeNode>
{

	private static final long serialVersionUID = -3494057194452450377L;

	public KnowledgeNode get(int level, KnowledgeType techType)
	{
		String key =  KnowledgeNode.makeTechId(techType.name(),level);
		for (KnowledgeNode kNode : this.values())
		{
			if (kNode.getTechId().equalsIgnoreCase(key))
			{
				return kNode;
			}
		}
		return null;
	}

	public KnowledgeNode get(AchivementName achivementName)
	{
		if (this.containsKey(achivementName))
		{
			return this.get(achivementName);
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

	/**
	 * return a list of build permissions
	 * 
	 * @param refList
	 * @return the Build permissions based on techlevel
	 */
	public ArrayList<BuildPlanType> getPermissions(AchivementList refList)
	{
		ArrayList<BuildPlanType> resultList = new ArrayList<BuildPlanType>();
		for (KnowledgeNode kNode : this.values())
		{
			if (refList.contains(kNode.getAchievName()))
			{
				for (BuildPlanType bType : kNode.getBuildPermission())
				{
					resultList.add(bType);
				}
			}
		}
		return resultList;
	}
	
	public ArrayList<SettleType> getSettlePermission(AchivementList refList)
	{
		ArrayList<SettleType> result = new ArrayList<SettleType>();
		for (KnowledgeNode kNode : this.values())
		{
			if (refList.contains(kNode.getAchievName()))
			{
				for (SettleType sType : kNode.getSettlePermission())
				{
					result .add(sType);
				}
			}
		}
		return result;
	}
	
	/**
	 * return the fulllist of achivement based on techlevel and the requirements
	 * 
	 * @param refList the reference list , normally the owner achivement list
	 * @return list of Achivements
	 */
	public AchivementList getFullList(AchivementList refList)
	{
		AchivementList fullList = new AchivementList();
		fullList.add(refList);
		for (KnowledgeNode kNode : this.values())
		{
			if (refList.contains(kNode.getAchievName())==true)
			{
				for (AchivementName aName : kNode.getRequirements())
				{
					if (fullList.contains(aName) == false)
					{
						fullList.add(new Achivement(AchivementType.BOOK, aName));
					}
				}
			}
		}
		return fullList;
	}
	
	/**
	 * check requirements for next techlevel
	 *  
	 * @param refList the reference list , normally the owner achivement list
	 * @return nextTechlevel as AchivementName or NONE
	 */
	public AchivementName checkNextRank(AchivementList refList)
	{
		AchivementName result = AchivementName.NONE;
		ArrayList<BuildPlanType> fullList = getPermissions(refList);
		for (KnowledgeNode kNode : this.values())
		{
			if (refList.contains(kNode.getAchievName())==false)
			{
				boolean fullfil = true;
				for (AchivementName require : kNode.getRequirements())
				{
					BuildPlanType rbType = BuildPlanType.valueOf(require.name());
					if (fullList.contains(rbType) == false)
					{
						fullfil = false;
					}
				}
				if (fullfil)
				{
					return kNode.getAchievName();
				}
			}
		}
		return result;
	}
}
