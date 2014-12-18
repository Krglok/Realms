package net.krglok.realms.science;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.SettleType;

/**
 * <pre>
 * the KnowledgeNode builds a group used as a level of knowledge
 * this level is defined by
 * - requirements , based on achiveents
 * - building permissions, based on BuildPlanTypes
 * - settlement permission, based on SettleTypes
 *   
 * @author Windu
 * </pre>
 */

public class KnowledgeNode
{
	private int level;
	private KnowledgeType knowledgeType;
	private String description;
	private ArrayList<AchivementName> requirements;
	private ArrayList<BuildPlanType> buildPermission;
	private ArrayList<SettleType> settlePermission;
	private AchivementName achievName;
	
	public KnowledgeNode(int level, KnowledgeType techType)
	{
		this.level = level;
		this.knowledgeType = techType;
		requirements = new ArrayList<AchivementName>();
		buildPermission = new ArrayList<BuildPlanType>();
		settlePermission = new ArrayList<SettleType>();
	}
	
	public static String makeTechId(String name, int level)
	{
		return name+"_"+level;
	}
	
	public String getTechId()
	{
		return makeTechId(knowledgeType.name(),level);
	}
	
	public void adRequirement(AchivementName aName)
	{
		requirements.add(aName);
	}
	
	public void addBuildPlan(BuildPlanType bType)
	{
		buildPermission.add(bType);
	}
	
	public void addSettleType(SettleType sType)
	{
		settlePermission.add(sType);
	}
	
	public boolean checkRequirements(AchivementList achiveList)
	{
		boolean found = false;
		for (AchivementName aName : requirements)
		{
			AchivementList subList = achiveList.getSubList(aName);
			for (Achivement achive : subList.values())
			{
				if (achive.isEnaled())
				{
					found = true;
				}
			}
		}
		return found;
	}
	

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public KnowledgeType getKnowledgeType()
	{
		return knowledgeType;
	}

	public void setKnowledgeType(KnowledgeType knowledgeType)
	{
		this.knowledgeType = knowledgeType;
	}

	public AchivementName getAchievName()
	{
		return achievName;
	}

	public void setAchievName(AchivementName achievName)
	{
		this.achievName = achievName;
	}

	public ArrayList<BuildPlanType> getBuildPermission()
	{
		return buildPermission;
	}

	public ArrayList<SettleType> getSettlePermission()
	{
		return settlePermission;
	}

	public ArrayList<AchivementName> getRequirements()
	{
		return requirements;
	}
	
}
