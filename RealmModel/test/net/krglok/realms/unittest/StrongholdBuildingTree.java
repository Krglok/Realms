package net.krglok.realms.unittest;


import java.io.File;

import org.junit.Test;


public class StrongholdBuildingTree
{

	@SuppressWarnings("unused")
	@Test
	public void getStrongholdConstructionMaterial()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        File regionFolder = new File(path, "RegionConfig");
        if (!regionFolder.exists()) 
        {
        	System.out.println("Folder not found !");
            return;
        }
        
//        RegionType[] Region = new RegionType[100];
        System.out.println("[Stronghold] Building cost" );
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	
//        	sRegionFile = RegionFile.getName();
//            if ( isInList(sRegionFile,sList))
//            {
//	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
//	            System.out.println(sRegionFile.replace(".yml", "")+"  Cost : "+region.getMoneyRequirement());
//
//	            for (ItemStack item : region.getRequirements())
//	            {
//	            	required.put(item.getType().name(), 0);
//	            }
//	            for (ItemStack item : region.getReagents())
//	            {
//	            	reagent.put(item.getType().name(), 0);
//	            }
//	            for (String item : region.getSuperRegions())
//	            {
//	            	superRef.put(item, 0);
//	            }
//	            for (ItemStack item : region.getUpkeep())
//	            {
//	            	ingredient.put(item.getType().name(), 0);
//	            }
//	            for (ItemStack item : region.getOutput())
//	            {
//	            	product.put(item.getType().name(), 0);
//	            }
//            }
        }
	}
}
