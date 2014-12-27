package net.krglok.realms.science;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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

}
