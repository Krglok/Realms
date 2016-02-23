package net.krglok.realms.manager;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;


public class MaterialBuildPlanList extends HashMap<String, BuildPlanList> 
{

	
	public MaterialBuildPlanList()
	{
		
	}
	
	public void addMaterialBuildPlan(String materialName, BuildPlanType buildPlan)
	{
		BuildPlanList bList; 
		bList = this.get(materialName);
		if (bList != null)
		{
			bList.add(buildPlan);
//			this.put(materialName, bList);
		} else
		{
			bList = new BuildPlanList();
			bList.add(buildPlan);
			this.put(materialName, bList);
		}
	}

	public BuildPlanList getBuildPlan(String MaterialName)
	{
		if (this.containsKey(MaterialName))
		{
			return this.get(MaterialName);
		}
		
		return new BuildPlanList();
	}
	
}
