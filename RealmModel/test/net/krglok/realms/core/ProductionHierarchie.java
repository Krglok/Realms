package net.krglok.realms.core;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionType;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class ProductionHierarchie
{
    private ArrayList<ItemStack> processItemStackList(List<String> input, String filename) {
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
	
	
	private RegionConfig getRegionConfig(String pathName, String sRegionFile)
	{
        try {
//        		sRegionFile = sRegionFile;
        		File currentRegionFile = new File(pathName,sRegionFile);
        		if (currentRegionFile == null)
        		{
        			System.out.println(pathName+"\\"+sRegionFile);
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
                System.out.println("[Stronghold] failed to load " + sRegionFile);
                e.printStackTrace();
            }
        return null;
	}
	

//	@Test
//	public void getStrongholdBuild()
//	{
//		String pathName = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold\\RegionConfig";
//		String  sRegionFile = "haus_einfach" + ".yml";
//		RegionConfig region = getRegionConfig(pathName, sRegionFile); 
//		
//        System.out.println("[Stronghold] Config" + sRegionFile);
//        System.out.println("Radius    : " + region.getRawRadius());
//        System.out.println("Construct===== ");
//        for(ItemStack required : region.getRequirements())
//        {
//        	System.out.println(required.getType().name()+":"+required.getAmount());
//        }
//        System.out.println("Build========= ");
//        for(ItemStack required : region.getReagents())
//        {
//        	System.out.println(required.getType().name()+":"+required.getAmount());
//        }
//        System.out.println("Production==== ");
//        System.out.println("Ingredients=== ");
//        for(ItemStack required : region.getReagents())
//        {
//        	System.out.println(required.getType().name()+":"+required.getAmount());
//        }
//        System.out.println("Product======= ");
//        for(ItemStack required : region.getReagents())
//        {
//        	System.out.println(required.getType().name()+":"+required.getAmount());
//        }
//		
//	}

	private void showRegionConfig(RegionConfig region)
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
	
	private void showBuildingList( 
			HashMap<String,Integer> required, 
			String[] sList,
			File regionFolder,
			String path
			)
	{
		int OFFSET1 = 21;
		int OFFSET2 = OFFSET1 * 2;
		int OFFSET3 = OFFSET1 * 3;
		
		String materials = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        required.clear();
        required.put("INPUT",0);
        required.put("OUTPUT",0);
        System.out.println(" ");
        System.out.println(setStrleft("Ebene 0 : ",15)+"IN   "+"OUT");
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	if (region.getUpkeep().size() == 0)
	        	{
	            	required.put("INPUT",region.getUpkeep().size());
	            	required.put("OUTPUT",region.getOutput().size());
	            	
//			        System.out.println(setStrleft(+materials );
			        System.out.println(setStrleft(sRegionFile.replace(".yml", ""),15)+setStrleft(String.valueOf(required.get("INPUT"))  ,5)+setStrleft(String.valueOf(required.get("OUTPUT"))  ,6) );
	        	}
		        
            }
        }
        System.out.println(setStrleft(" ",OFFSET1)+setStrleft("Ebene 1 : ",15)+"IN   "+"OUT");
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	if (region.getUpkeep().size() == 1)
	        	{
	            	required.put("INPUT",region.getUpkeep().size());
	            	required.put("OUTPUT",region.getOutput().size());
	            	
			        System.out.println(setStrleft(" ",OFFSET1)+
			        		setStrleft(sRegionFile.replace(".yml", ""),15)+
			        		setStrleft(String.valueOf(required.get("INPUT"))  ,5)+
			        		setStrleft(String.valueOf(required.get("OUTPUT"))  ,6) );
	        	}
		        
            }
        }
        System.out.println(setStrleft(" ",OFFSET2)+setStrleft("Ebene 2 : ",15)+"IN   "+"OUT");
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	if (region.getUpkeep().size() == 2)
	        	{
	            	required.put("INPUT",region.getUpkeep().size());
	            	required.put("OUTPUT",region.getOutput().size());
	            	
			        System.out.println(setStrleft(" ",OFFSET2)+
			        		setStrleft(sRegionFile.replace(".yml", ""),15)+
			        		setStrleft(String.valueOf(required.get("INPUT"))  ,5)+
			        		setStrleft(String.valueOf(required.get("OUTPUT"))  ,6) );
	        	}
		        
            }
        }
        System.out.println(setStrleft(" ",OFFSET3)+setStrleft("Ebene 3 : ",15)+"IN   "+"OUT");
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	if (region.getUpkeep().size() > 2)
	        	{
	            	required.put("INPUT",region.getUpkeep().size());
	            	required.put("OUTPUT",region.getOutput().size());
	            	
			        System.out.println(setStrleft(" ",OFFSET3)+
			        		setStrleft(sRegionFile.replace(".yml", ""),15)+
			        		setStrleft(String.valueOf(required.get("INPUT"))  ,5)+
			        		setStrleft(String.valueOf(required.get("OUTPUT"))  ,6) );
	        	}
		        
            }
        }

		
	}


	private void showProductList( 
			HashMap<String,Integer> product, 
			String[] sList,
			File regionFolder,
			String path
			)
	{
		String products = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        // REAGENTS LIST
        System.out.println(" ");
        System.out.println("[Production] Products : " + product.size());
        for (String sName : product.keySet())
        {
    	products = products +""+sName+" ";
        }
        System.out.println(setStrleft(" ",15)+products);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : product.keySet())
	            {
	            	product.put(itemRef, 0);
	            }
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getOutput())
	            {
	            	product.put(item.getType().name(), item.getAmount());
	            }
	            products = "";
		        for (String sName : product.keySet())
		        {
		        	int value = product.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
//						sValue = sValue + " ";
						sValue = setStrleft(sValue,sName.length());
					}
		        	products = products +""+sValue+" ";
		        }
		        System.out.println(setStrleft(sRegionFile.replace(".yml", ""),15)+products );
		        
            }
        }
		
	}
	
	private boolean isInList(String name, String[] sList)
	{
		for (int i = 0; i < sList.length; i++)
		{
			if (name.contains(sList[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	private String[] setBasisList()
	{
		return new String[] 
        		{"haupthaus",
				"markt",
				"taverne",
        		"haus_einfach", 
        		"kornfeld", 
        		"holzfaeller",
        		"steinbruch",
        		"schaefer",
        		"schreiner",
        		"tischler"
        		};
	}

	private String[] setErweitertList()
	{
		return new String[] 
        		{"prod_waxe",
        		"prod_whoe", 
        		"prod_wpaxe", 
        		"prod_wsword",
        		"prod_wspade",
        		"prod_steinziegel",
        		"prod_netherziegel",
        		"bauern_haus",
        		"haus_baecker",
        		"koehler",
        		"rinderstall",
        		"huehnerstall",
        		"fischer"
        		};
	}

	private String[] setEnhancedList()
	{
		return new String[] 
        		{"werkstatt",
				"steinmine",
				"schmelze",
				"bauernhof",
				"schweinestall"
        		};
	}
	
	private String[] setBuildingList()
	{
		return new String[] 
        		{
        		"kornfeld", 
        		"holzfaeller",
        		"steinbruch",
        		"schaefer",
        		"schreiner",
        		"tischler",
        		"prod_waxe",
        		"prod_whoe", 
        		"prod_wpaxe", 
        		"prod_wsword",
        		"prod_wspade",
        		"prod_steinziegel",
        		"prod_netherziegel",
        		"bauern_haus",
        		"haus_baecker",
        		"koehler",
        		"rinderstall",
        		"huehnerstall",
        		"fischer",
        		"werkstatt",
				"steinmine",
				"schmelze",
				"bauernhof",
				"schweinestall"
        		};
	}
	
	public String setStrleft(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < zw.length; i++)
			{
				out[i] = zw[i]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < out.length; i++)
			{
				out[i] = zw[i]; 
			}
			
		}
		return String.valueOf(out);
	}

	public String setStrright(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			for (int i = 0; i < zw.length; i++)
			{
				out[len-i-1] = zw[zwl-i-1]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			for (int i = 0; i < out.length; i++)
			{
				out[len-i] = zw[zwl-i]; 
//				out[i] = zw[i]; 
			}
			
		}
		return String.valueOf(out);
	}
	
	@Test
	public void getStrongholdConstructionMaterial()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        File regionFolder = new File(path, "RegionConfig");
        if (!regionFolder.exists()) {
        	System.out.println("Folder not found !");
            return;
        }
        HashMap<String,Integer> required = new HashMap<String,Integer>();
        HashMap<String,Integer> reagent = new HashMap<String,Integer>();
        HashMap<String,Integer> superRef = new HashMap<String,Integer>();
        HashMap<String,Integer> ingredient = new HashMap<String,Integer>();
        HashMap<String,Integer> product = new HashMap<String,Integer>();
        superRef.put("Anywhere",0);
        String sRegionFile = "";
        
        RegionConfig region;
        String[] sList ;
//        sList = setBasisList();
//        sList = setErweitertList();
//        sList = setEnhancedList();
        sList = setBuildingList();
        System.out.println("[Stronghold] Building  Hierarchie" );
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	
        	sRegionFile = RegionFile.getName();
            if ( isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);

	            for (ItemStack item : region.getRequirements())
	            {
	            	required.put(item.getType().name(), 0);
	            }
	            for (ItemStack item : region.getReagents())
	            {
	            	reagent.put(item.getType().name(), 0);
	            }
	            for (String item : region.getSuperRegions())
	            {
	            	superRef.put(item, 0);
	            }
	            for (ItemStack item : region.getUpkeep())
	            {
	            	ingredient.put(item.getType().name(), 0);
	            }
	            for (ItemStack item : region.getOutput())
	            {
	            	product.put(item.getType().name(), 0);
	            }
            }
        }

        showBuildingList( ingredient, sList, regionFolder, path);        
        
//        showReagentList( reagent, sList, regionFolder, path);
//        
//        showBuildingAllowed(superRef, sList, regionFolder, path );
//
//        showIngredientList(ingredient, sList, regionFolder, path );
//        
//        showProductList(product, sList, regionFolder, path );
        
	}

}
