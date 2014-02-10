package net.krglok.realms.tool;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.MessageText;
import net.krglok.realms.data.StrongholdTools;

import org.junit.Test;


public class StrongholdWorldTest
{
	public void showSuperRegionList(ArrayList<SuperRegionData> sList, String worldName, LocationData loc)
	{
        System.out.println("SuperRegions Distance in World "+worldName);
        for (SuperRegionData sData : sList)
        {
        	if (sData.getL().getWorld().equalsIgnoreCase(worldName) == true)
        	{
	        	String sName = StrongholdTools.setStrleft(sData.getName(), 20);
	    		System.out.println(sName+":"+sData.getL().getWorld()+" Dist: "+(int)sData.getL().distance(loc));
        	}
        }
	}
	
	public void showRegionStat(ArrayList<RegionData> rList, String name)
	{
        System.out.println("BuildingTypen in  "+name);
    	HashMap<String,Integer> buildingStat = new HashMap<String,Integer>();
        for (RegionData sData : rList)
        {
    		String bType = sData.getType();
    		if (buildingStat.containsKey(bType))
    		{
    			buildingStat.put(bType, (buildingStat.get(bType)+1));
    		}else
    		{
    			buildingStat.put(bType, 1);
    		}
        }
        for (String key : buildingStat.keySet())
        {
	    	String sName = StrongholdTools.setStrleft(key, 20);
			System.out.println(sName+":"+buildingStat.get(key));
        }
	
	}

	
	public void showSuperRegionStat(ArrayList<SuperRegionData> sList, String worldName)
	{
        System.out.println("SuperRegionTypen in World "+worldName);
    	HashMap<String,Integer> buildingStat = new HashMap<String,Integer>();
        for (SuperRegionData sData : sList)
        {
        	if (sData.getL().getWorld().equalsIgnoreCase(worldName) == true)
        	{
        		String bType = sData.getType();
        		if (buildingStat.containsKey(bType))
        		{
        			buildingStat.put(bType, (buildingStat.get(bType)+1));
        		}else
        		{
        			buildingStat.put(bType, 1);
        		}
        	}
        }
        for (String key : buildingStat.keySet())
        {
	    	String sName = StrongholdTools.setStrleft(key, 20);
			System.out.println(sName+":"+buildingStat.get(key));
        }

	}

	public void showBuildingInSettlement(ArrayList<RegionData> rList, double radius, LocationData loc, String name )
	{
        System.out.println("Building Distance for "+name+"   radius "+radius);
    	for (RegionData r : rList)
    	{
    		String sRef = StrongholdTools.setStrleft(String.valueOf(r.getId()),3);
  	    	String sName = StrongholdTools.setStrleft(r.getType(), 20);
    		if ((int)r.getLocation().distance2D(loc) < radius)
    		System.out.println(sRef+" : "+sName +" : "+(int)r.getLocation().distance2D(loc));
    	}
		
	}

	public void showBuildingsOutofSettlement(ArrayList<RegionData> rList, double radius, LocationData loc, String name )
	{
		System.out.println("Buldings"+name+" ausserhalb bis zu "+(radius*3));
    	for (RegionData r : rList)
    	{
    		if (((int)r.getLocation().distance2D(loc) > radius) 
    			&& ((int)r.getLocation().distance2D(loc) < (radius*3)))
    		System.out.println(r.getId()+":"+r.getType()+" : "+(int)r.getLocation().distance2D(loc));
    	}
		
	}

	public void showRegionOverlaps(ArrayList<RegionData> rList, double radius, LocationData loc, String name )
	{
		ArrayList<RegionData> tempList = new ArrayList<RegionData>();
    	tempList = RegionData.getContainingRegions(loc, rList, radius);
		System.out.println("=== Settlement  ERROR ["+tempList.size()+"] ");
    	for (RegionData r : tempList)
    	{
//    		if ((int)r.getLocation().distance2D(loc) < radius)
    		System.out.println(r.getId()+":"+r.getType()+" : "+(int)r.getLocation().deltaX(loc));
    	}

	}
	
	public void showRegionList(ArrayList<RegionData> rList,  String worldName)
	{
		System.out.println("Buildings in Region : "+worldName);
		for (RegionData rData : rList) 
        {
        	if (rData.getWorld().equalsIgnoreCase(worldName) == true)
        	{
        		System.out.println(rData.getId()+":"+rData.getWorld());
        	}
        }

	}
	
	public void showCreationAnalysis(ArrayList<RegionData> rList,  
			String name, 
			SettleType settleType, 
			ArrayList<SuperRegionData> sList, 
			String worldName, 
			LocationData loc)
	{
		System.out.println("Buildings in World : "+name);
    	HashMap<String,Integer> buildingStat = new HashMap<String,Integer>();
        for (RegionData sData : rList)
        {
    		String bType = sData.getType();
    		if (buildingStat.containsKey(bType))
    		{
    			buildingStat.put(bType, (buildingStat.get(bType)+1));
    		}else
    		{
    			buildingStat.put(bType, 1);
    		}
        }
        int MaxSettler = 0;
        int WheatField = 0;
		System.out.println("======================================");
		System.out.println("Founding Analysis of "+name+"  ");
		System.out.println("Residents valuation ");
        String key = "haus_einfach";
        if (buildingStat.get(key) != null)
        {
        	MaxSettler = buildingStat.get(key)* Building.getDefaultSettler(BuildPlanType.HOME);
        	
			System.out.println(ConfigBasis.setStrleft("Max  Places:",20)+MaxSettler);
			System.out.println(ConfigBasis.setStrleft("Start with:",20)+(MaxSettler/2));
			System.out.println("Estimate days to MaxSettlers : "+(MaxSettler/2*20));
        } else
        {
			System.out.println(ConfigBasis.setStrleft("Max  Places:",20)+"0");
			System.out.println(ConfigBasis.setStrleft("Start with:",20)+"0");
			System.out.println("Estimate days to MaxSettlers : "+"Never");
        }
		System.out.println("======================================");
		System.out.println("Supply valuation ");
        key = "kornfeld";
        if (buildingStat.get(key) != null)
        {
        	WheatField = buildingStat.get(key) * 16;
			System.out.println(ConfigBasis.setStrleft("Min. Wheat needed:",20)+(MaxSettler+5));
			System.out.println(ConfigBasis.setStrleft("You Supplied :",20)+WheatField);
			System.out.println("Be warned some Build need extra Wheat ! ");

        }else
        {
			System.out.println(ConfigBasis.setStrleft("Min. Wheat needed:",20)+(MaxSettler+5));
			System.out.println(ConfigBasis.setStrleft("You Supplied :",20)+ "0");
			System.out.println("The settlers all death in some days ! ");
        	
        }
		System.out.println("======================================");
		System.out.println("Storage valuation ");
        key = "markt";
        if (buildingStat.get(key) != null)
        {
        	int StorageBase = Settlement.defaultItemMax(settleType);
        	int StorageExpand = MessageText.WAREHOUSE_CHEST_FACTOR * MessageText.CHEST_STORE * buildingStat.get(key);
			System.out.println(ConfigBasis.setStrleft("Base Storage      :",20)+(StorageBase));
			System.out.println(ConfigBasis.setStrleft("Storage expansion :",20)+buildingStat.get(key)+"/"+StorageExpand);
			System.out.println(ConfigBasis.setStrleft("Storage Capacity  :",20)+(StorageExpand+StorageBase));

        }else
        {
        	int StorageBase = Settlement.defaultItemMax(settleType);
			System.out.println(ConfigBasis.setStrleft("Base Storage      :",20)+(StorageBase));
        	
        }
		System.out.println("======================================");
		System.out.println("Entertainment valuation ");
        key = "taverne";
        if (buildingStat.get(key) != null)
        {
			System.out.println(ConfigBasis.setStrleft("Basic Entertainment available :",20)+(buildingStat.get(key)));
			System.out.println(ConfigBasis.setStrleft("This make your settlers some more happy ",45));

        }else
        {
			System.out.println(ConfigBasis.setStrleft("NO Basic Entertainment available ",20));
			System.out.println(ConfigBasis.setStrleft("Build some taverne later on.",30));
        	
        }
		System.out.println("======================================");
		System.out.println("Trade valuation ");
        key = "haendler";
        if (buildingStat.get(key) != null)
        {
			System.out.println(ConfigBasis.setStrleft("Traders available :",20)+(buildingStat.get(key)));
			System.out.println(ConfigBasis.setStrleft("So you can trade with others  ",45));
        }else
        {
			System.out.println(ConfigBasis.setStrleft("NO traders available ",20));
			System.out.println(ConfigBasis.setStrleft("Build some traders later on.",30));
        }

        System.out.println("Settlements in range of 1 Day to trade ");
        for (SuperRegionData sData : sList)
        {
        	if (sData.getL().getWorld().equalsIgnoreCase(worldName) == true)
        	{
        		if (
        			(
        			(sData.getType().equalsIgnoreCase("Stadt"))
        			|| (sData.getType().equalsIgnoreCase("Dorf"))
        			|| (sData.getType().equalsIgnoreCase("Siedlung"))
        			|| (sData.getType().equalsIgnoreCase("Mine"))
        			)
        			&&(sData.getName().equals(name) == false)
        			&&(sData.getL().distance(loc) <= 1000)
        			)
        		{
		        	String sName = StrongholdTools.setStrleft(sData.getName(), 20);
		    		System.out.println(sName+":"+" Dist: "+(int)sData.getL().distance(loc));
        		}
        	}
        }
        System.out.println("Settlements in range of 3 Day to trade ");
        for (SuperRegionData sData : sList)
        {
        	if (sData.getL().getWorld().equalsIgnoreCase(worldName) == true)
        	{
        		if (
        			(
        			(sData.getType().equalsIgnoreCase("Stadt"))
        			|| (sData.getType().equalsIgnoreCase("Dorf"))
        			|| (sData.getType().equalsIgnoreCase("Siedlung"))
        			|| (sData.getType().equalsIgnoreCase("Mine"))
        			)
        			&&(sData.getName().equals(name) == false)
        			&&(sData.getL().distance(loc) <= 3000)
        			&&(sData.getL().distance(loc) > 1000)
        			)
        		{
		        	String sName = StrongholdTools.setStrleft(sData.getName(), 20);
		    		System.out.println(sName+":"+" Dist: "+(int)sData.getL().distance(loc));
        		}
        	}
        }

	}
	
	@Test
	public void checkStrongholdWorldRegion()
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
		String path = "\\GIT\\BukkitTest\\plugins\\HeroStronghold";
		//location: SteamHaven:-468.69999998807907:68:-1251.300000011921
		LocationData  loc = new LocationData("SteamHaven",-469.51,72,-1236.65);
		ArrayList<RegionData> rList = new ArrayList<RegionData>();
		ArrayList<RegionData> tempList = new ArrayList<RegionData>();
//		ArrayList<RegionConfig> rConfigList = new ArrayList<RegionConfig>();
		ArrayList<SuperRegionData> sList = new ArrayList<SuperRegionData>();
		String worldName = "SteamHaven";
		rList = StrongholdTools.getRegionData(path);
        sList = StrongholdTools.getSuperRegionData(path);
        int radius = 70;

        showRegionStat(rList, worldName);

//        showBuildingInSettlement(rList, radius, loc, "NewHaven");

        tempList = RegionData.getContainingRegions(loc, rList, radius);
        showRegionStat(tempList, "NewHaven");

        showCreationAnalysis(
        		tempList, 
        		"NewHaven", 
        		SettleType.SETTLE_CITY,
    			sList, 
    			worldName, 
    			loc
    			);
    	
//        showSuperRegionStat(sList, worldName);
//        
//        showSuperRegionList(sList, worldName, loc);
	}
}
