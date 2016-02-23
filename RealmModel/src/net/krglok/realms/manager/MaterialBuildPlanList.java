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
		if (this.containsKey(materialName))
		{
			return;
		}
		BuildPlanList bList; 
		if (this.containsKey(materialName))
		{
			bList = this.get(materialName);
			bList.add(buildPlan);
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
