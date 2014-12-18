package net.krglok.realms.science;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;


/**
 * the list only iherite permission that are true.
 * when a permision will go invalid , you must delete it.
 * 
 * @author Windu
 *
 */

public class RealmPermission extends ArrayList<String>
{
	


	public RealmPermission()
	{
	}


	public boolean hasPermission(String permName)
	{
		if (this.contains(permName))
		{
			return true;
		}
		
		return false;
	}
	
	public void initKnowledgeNode(KnowledgeNode kNode)
	{
		if (kNode != null)
		{
			for (BuildPlanType bType: kNode.getBuildPermission())
			{
				this.add(bType.name());
			}
			for (SettleType sType : kNode.getSettlePermission())
			{
				this.add(sType.name());
			}
		}
	}
	
	public void initPermission(Owner owner, KnowledgeList knowledgeList)
	{
		for (Achivement achive : owner.getAchivList().values())
		{
			this.add(achive.getAchiveName().name());
			initKnowledgeNode(knowledgeList.get(achive.getAchiveName().name()));
		}
		
		
	}
	

	
}
