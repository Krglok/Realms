package net.krglok.realms.tool;

import java.util.ArrayList;

import net.krglok.realms.unittest.RegionConfig;

import org.junit.Test;


public class StrongholdWorldTest
{

	
	@Test
	public void checkStrongholdWorldRegion()
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
		String path = "\\GIT\\BukkitTest\\plugins\\HeroStronghold";
		//location: SteamHaven:-468.69999998807907:68:-1251.300000011921
		LocationData  loc = new LocationData("SteamHaven",-469.51,72,-1236.65);
		ArrayList<RegionData> rList = new ArrayList<RegionData>();
		ArrayList<RegionData> tempList = new ArrayList<RegionData>();
		ArrayList<RegionConfig> rConfigList = new ArrayList<RegionConfig>();
		ArrayList<SuperRegionData> sList = new ArrayList<SuperRegionData>();
		
		rList = StrongholdTools.getRegionData(path);
        for (RegionData rData : rList) 
        {
        	if (rData.getWorld().equalsIgnoreCase("SteamHaven") == false)
        	{
        		System.out.println(rData.getId()+":"+rData.getWorld());
        	}
//        	if (rData.getWorld().equalsIgnoreCase("SteamHaven") == true)
//        	{
//        		System.out.println(rData.getId()+":"+rData.getType());
//        	}
        }
        System.out.println("Dist.: "+loc.distance(loc));
        
        sList = StrongholdTools.getSuperRegionData(path);
        int radius = 70;
        for (SuperRegionData sData : sList)
        {
        	String sName = StrongholdTools.setStrleft(sData.getName(), 20);
    		System.out.println(sName+":"+sData.getL().getWorld()+" Dist: "+(int)sData.getL().distance(loc)+" / "+(int)sData.getL().distance2D(loc));
        }
		System.out.println("=== Settlement  radius "+radius);
    	for (RegionData r : rList)
    	{
    		if ((int)r.getLocation().distance2D(loc) < radius)
    		System.out.println(r.getId()+":"+r.getType()+" : "+(int)r.getLocation().distance2D(loc));
    	}
		System.out.println("=== ausserhalb bis zu "+(radius*3));
    	for (RegionData r : rList)
    	{
    		if (((int)r.getLocation().distance2D(loc) > radius) 
    			&& ((int)r.getLocation().distance2D(loc) < (radius*3)))
    		System.out.println(r.getId()+":"+r.getType()+" : "+(int)r.getLocation().distance2D(loc));
    	}
    	
    	tempList = RegionData.getContainingRegions(loc, rList, radius);
    	rConfigList = StrongholdTools.getRegionConfigList(path);
		System.out.println("=== Settlement  TempList ["+tempList.size()+"] ");
    	for (RegionData r : tempList)
    	{
//    		if ((int)r.getLocation().distance2D(loc) < radius)
    		System.out.println(r.getId()+":"+r.getType()+" : "+(int)r.getLocation().deltaX(loc));
    	}

    	
	}
}
