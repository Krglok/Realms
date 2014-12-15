package net.krglok.realms.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class StrongholdTools
{
	
	public StrongholdTools()
	{
		
	}

//	public static String setStrleft(String in, int len)
//	{
//		char[] out = new char[len];
//		for (int i = 0; i < out.length; i++)
//		{
//			out[i] = ' ';
//		}
//		if (len >= in.length())
//		{
//			char[] zw  = in.toCharArray();
//			for (int i = 0; i < zw.length; i++)
//			{
//				out[i] = zw[i]; 
//			}
//		} else
//		{
//			char[] zw  = in.toCharArray();
//			for (int i = 0; i < out.length; i++)
//			{
//				out[i] = zw[i]; 
//			}
//			
//		}
//		return String.valueOf(out);
//	}
//
//	public static String setStrright(String in, int len)
//	{
//		char[] out = new char[len];
//		for (int i = 0; i < out.length; i++)
//		{
//			out[i] = ' ';
//		}
//		if (len >= in.length())
//		{
//			char[] zw  = in.toCharArray();
//			int zwl = zw.length;
//			for (int i = 0; i < zw.length; i++)
//			{
//				out[len-i-1] = zw[zwl-i-1]; 
//			}
//		} else
//		{
//			char[] zw  = in.toCharArray();
//			int zwl = zw.length;
//			for (int i = 0; i < out.length; i++)
//			{
//				out[len-i] = zw[zwl-i]; 
////				out[i] = zw[i]; 
//			}
//			
//		}
//		return String.valueOf(out);
//	}
	
    public static ArrayList<ItemStack> processItemStackList(List<String> input, String filename) {
        ArrayList<ItemStack> returnList = new ArrayList<ItemStack>();
        for (String current : input) {
            String[] params = current.split("\\.");
            if (Material.getMaterial(params[0]) != null) {
                ItemStack is;
                if (params.length < 3) {
                    is = new ItemStack(Material.getMaterial(params[0]),Integer.parseInt(params[1]));
                } else {
                    is = new ItemStack(Material.getMaterial(params[0]),Integer.parseInt(params[1]), Short.parseShort(params[2]));
                }
                returnList.add(is);
            } else {
            	System.out.println("[Stronghold] could not find item " + params[0] + " in " + filename);
            }
        }
        return returnList;
    }
	
	
	@SuppressWarnings("unused")
	public static RegionConfig getRegionConfig(String pathName, String sRegionFile)
	{
        try {
//        		sRegionFile = sRegionFile;
        		File currentRegionFile = new File(pathName,sRegionFile);
        		if (currentRegionFile == null)
        		{
        			System.out.println(pathName+"\\RegionConfig"+sRegionFile);
        		}
        		
                FileConfiguration rConfig = new YamlConfiguration();
                rConfig.load(currentRegionFile);
                String regionName = currentRegionFile.getName().replace(".yml", "");
                RegionConfig regionConfig = new RegionConfig(regionName,
                        rConfig.getString("group", regionName),
                        (ArrayList<String>) rConfig.getStringList("friendly-classes"),
                        (ArrayList<String>) rConfig.getStringList("enemy-classes"),
                        (ArrayList<String>) rConfig.getStringList("effects"),
                        (int) Math.pow(rConfig.getInt("radius"), 2),
                        (int) Math.pow(rConfig.getInt("build-radius", rConfig.getInt("radius", 2)), 2),
                        processItemStackList(rConfig.getStringList("requirements"), currentRegionFile.getName()),
                        rConfig.getStringList("super-regions"),
                        processItemStackList(rConfig.getStringList("reagents"), currentRegionFile.getName()),
                        processItemStackList(rConfig.getStringList("upkeep"), currentRegionFile.getName()),
                        processItemStackList(rConfig.getStringList("output"), currentRegionFile.getName()),
                        rConfig.getDouble("upkeep-chance"),
                        rConfig.getDouble("money-requirement"),
                        rConfig.getDouble("upkeep-money-output"),
                        rConfig.getDouble("exp"),
                        rConfig.getString("description"),
                        rConfig.getInt("power-drain", 0),
                        rConfig.getInt("housing", 0),
                        rConfig.getStringList("biome"));
                return regionConfig;
            } catch (Exception e) {
                System.out.println("[Realms] failed to load RegionConfig " + sRegionFile);
//                e.printStackTrace();
            }
        return null;
	}

	public static void showRegionConfig(RegionConfig region)
	{
        System.out.println("Radius    : " + region.getRawRadius());
        System.out.println("Construct===== ");
        for(ItemStack required : region.getRequirements())
        {
        	System.out.println(required.getType().name()+":"+required.getAmount());
        }
        System.out.println("Build========= ");
        for(ItemStack required : region.getReagents())
        {
        	System.out.println(required.getType().name()+":"+required.getAmount());
        }
        System.out.println("Production==== ");
        System.out.println("Ingredients=== ");
        for(ItemStack required : region.getReagents())
        {
        	System.out.println(required.getType().name()+":"+required.getAmount());
        }
        System.out.println("Product======= ");
        for(ItemStack required : region.getReagents())
        {
        	System.out.println(required.getType().name()+":"+required.getAmount());
        }
		
	}

	public static ArrayList<RegionData> getRegionData(String path)
	{
		ArrayList<RegionData> rList = new ArrayList<RegionData>();
		FileConfiguration dataConfig;
	    File playerFolder = new File(path, "data"); // Setup the Data Folder if it doesn't already exist
        playerFolder.mkdirs();
        for (File regionFile : playerFolder.listFiles()) 
        {
            try 
            {
                //Load saved region data
                dataConfig = new YamlConfiguration();
                dataConfig.load(regionFile);
                String locationString = dataConfig.getString("location");
                if (locationString != null) 
                {
                	String type = dataConfig.getString("type");
                    ArrayList<String> owners = (ArrayList<String>) dataConfig.getStringList("owners");
                    ArrayList<String> members = (ArrayList<String>) dataConfig.getStringList("members");
                    if (owners == null) {
                        owners = new ArrayList<String>();
                    }
                    if (members == null) {
                        members = new ArrayList<String>();
                    }
                  new Region(Integer.parseInt(regionFile.getName().replace(".yml", "")), null, type, owners, members);

                    if (type != null) 
                    {
                        try 
                        {
                           rList.add(new RegionData(Integer.parseInt(regionFile.getName().replace(".yml", "")), locationString, type, owners, members));

                        } catch (NullPointerException npe) 
                        {
                            System.out.println("[HeroStronghold] failed to load data from " + regionFile.getName());
                        }
                    }
                }
            } catch (Exception e) 
            {
                System.out.println("[Realms] failed to load data from " + regionFile.getName());
//                System.out.println(e.getStackTrace());
            }
        }
		return rList;
	}
	
	public static ArrayList<SuperRegionData> getSuperRegionData(String path)
	{
		ArrayList<SuperRegionData> rList = new ArrayList<SuperRegionData>();

        File sRegionFolder = new File(path, "superregions"); // Setup the Data Folder if it doesn't already exist
        sRegionFolder.mkdirs();
        
        for (File sRegionFile : sRegionFolder.listFiles()) 
        {
            try 
            {
                //Load saved region data
                FileConfiguration sRegionDataConfig = new YamlConfiguration();
                sRegionDataConfig.load(sRegionFile);
                String name = sRegionFile.getName().replace(".yml", "");
                String locationString = sRegionDataConfig.getString("location", "0:64:0");
                if (locationString != null) {
                    LocationData location = null;
                    if (locationString != null) {
                        String[] params = locationString.split(":");
                        location = new LocationData(params[0], Double.parseDouble(params[1]),Double.parseDouble(params[2]),Double.parseDouble(params[3]));
                    }
                    String type = sRegionDataConfig.getString("type", "shack");
                    ArrayList<String> owners = (ArrayList<String>) sRegionDataConfig.getStringList("owners");
                    ConfigurationSection configMembers = sRegionDataConfig.getConfigurationSection("members");
                    Map<String, List<String>> members = new HashMap<String, List<String>>();
                    for (String s : configMembers.getKeys(false)) {
                        List<String> perm = configMembers.getStringList(s);
                        if (perm.contains("member")) {
                            members.put(s, configMembers.getStringList(s));
                        }
                    }
                    int power = sRegionDataConfig.getInt("power", 10);
                    double taxes = sRegionDataConfig.getDouble("taxes", 0.0);
                    double balance = sRegionDataConfig.getDouble("balance", 0.0);
                    List<Double> taxRevenue1 = sRegionDataConfig.getDoubleList("tax-revenue");
                    LinkedList<Double> taxRevenue = new LinkedList<Double>();
                    int maxPower = sRegionDataConfig.getInt("max-power", 0);
                    if (taxRevenue1 != null) {
                        for (double d : taxRevenue1) {
                            taxRevenue.add(d);
                        }
                    }
                    if (location != null && type != null) {
                    	rList.add(new SuperRegionData(name, location, type, owners, members, power, taxes, balance, taxRevenue, maxPower));
                        
                    }
                }
            } catch (Exception e) {
                System.out.println("[Realms] failed to load superregions from " + sRegionFile.getName());
                e.printStackTrace();
            }
        }
		return rList;
	}

	public static ArrayList<RegionConfig> getRegionConfigList(String path)
	{
		ArrayList<RegionConfig> rList = new ArrayList<RegionConfig>();
        File regionFolder = new File(path, "RegionConfig");
        for (File currentRegionFile : regionFolder.listFiles()) {
            try {
                FileConfiguration rConfig = new YamlConfiguration();
                rConfig.load(currentRegionFile);
                String regionName = currentRegionFile.getName();
                
                rList.add(getRegionConfig(path+"\\RegionConfig", regionName));
            } catch (Exception e) {
            	System.out.println("[Realms] failed to load " + currentRegionFile.getName());
                e.printStackTrace();
            }
        }
		return rList;
	}
}
