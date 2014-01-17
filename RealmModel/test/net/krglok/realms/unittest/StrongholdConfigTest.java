package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class StrongholdConfigTest
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
		String materials = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        System.out.println("[Construction] Resources : " + required.size());
        for (String sName : required.keySet())
        {
    	materials = materials +""+sName+" ";
        }
        System.out.println(materials);
        
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : required.keySet())
	            {
	            	required.put(itemRef, 0);
	            }
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getRequirements())
	            {
	            	required.put(item.getType().name(), item.getAmount());
	            }
		        materials = "  ";
		        for (String sName : required.keySet())
		        {
		        	int value = required.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = sValue + " ";
					}
		        	materials = materials +""+sValue+" ";
		        }
		        System.out.println(materials + sRegionFile.replace(".yml", ""));
		        
            }
        }

		
	}

	private void showReagentList( 
			HashMap<String,Integer> reagent, 
			String[] sList,
			File regionFolder,
			String path
			)
	{
		String reagents = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        // REAGENTS LIST
        for (String sName : reagent.keySet())
        {
    	reagents = reagents +""+sName+" ";
        }
        System.out.println(reagents);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : reagent.keySet())
	            {
	            	reagent.put(itemRef, 0);
	            }
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getReagents())
	            {
	            	reagent.put(item.getType().name(), item.getAmount());
	            }
	            reagents = "  ";
		        for (String sName : reagent.keySet())
		        {
		        	int value = reagent.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = sValue + " ";
					}
		        	reagents = reagents +""+sValue+" ";
		        }
		        System.out.println(reagents + sRegionFile.replace(".yml", ""));
		        
            }
        }
		
	}
	

	private void showBuildingAllowed( 
			HashMap<String,Integer> superRef, 
			String[] sList,
			File regionFolder,
			String path
			)
	{
		String superRefs = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        System.out.println("[Construction] Build allowed : " + superRef.size());
        for (String sName : superRef.keySet())
        {
        	superRefs = superRefs +""+sName+" ";
        }
        System.out.println(superRefs);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : superRef.keySet())
	            {
	            	superRef.put(itemRef, 0);
	            }
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (String item : region.getSuperRegions())
	            {
	            	superRef.put(item, 1);
	            }
            	if (region.getSuperRegions().size() == 0)
            	{
            		superRef.put("Anywhere", 1);
            	}
	            superRefs = "  ";
		        for (String sName : superRef.keySet())
		        {
		        	int value = superRef.get(sName);
		        	
		        	String sValue = "";
		        	if (value == 0)
		        	{
		        		sValue = " ";
		        	} else
		        	{
		        		sValue = String.valueOf(value);		        		
		        	}
		        	
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = sValue + " ";
					}
		        	superRefs = superRefs +""+sValue+" ";
		        }
		        System.out.println(superRefs + sRegionFile.replace(".yml", ""));
		        
            }
        }
		
	}

	private void showIngredientList( 
			HashMap<String,Integer> ingredient, 
			String[] sList,
			File regionFolder,
			String path
			)
	{
		String ingredients = "";
        String sRegionFile = "";
        RegionConfig region ;
//        String[] outLines ;

        // REAGENTS LIST
        for (String sName : ingredient.keySet())
        {
    	ingredients = ingredients +""+sName+" ";
        }
        System.out.println(ingredients);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : ingredient.keySet())
	            {
	            	ingredient.put(itemRef, 0);
	            }
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getUpkeep())
	            {
	            	ingredient.put(item.getType().name(), item.getAmount());
	            }
	            ingredients = "  ";
		        for (String sName : ingredient.keySet())
		        {
		        	int value = ingredient.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = sValue + " ";
					}
		        	ingredients = ingredients +""+sValue+" ";
		        }
		        System.out.println(ingredients + sRegionFile.replace(".yml", ""));
		        
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
        for (String sName : product.keySet())
        {
    	products = products +""+sName+" ";
        }
        System.out.println(products);
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
	            products = "  ";
		        for (String sName : product.keySet())
		        {
		        	int value = product.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = sValue + " ";
					}
		        	products = products +""+sValue+" ";
		        }
		        System.out.println(products + sRegionFile.replace(".yml", ""));
		        
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
        String materials = "  ";
        String reagents = "  ";
        String superRefs = "";
        String[] sList ;
        sList = setBasisList();
//        sList = setErweitertList();
//        sList = setEnhancedList();
        System.out.println("[Stronghold] Building cost" );
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	
        	sRegionFile = RegionFile.getName();
            if ( isInList(sRegionFile,sList))
            {
	        	region= getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            System.out.println(sRegionFile.replace(".yml", "")+"  Cost : "+region.getMoneyRequirement());

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

        showBuildingList( required, sList, regionFolder, path);        
        
        showReagentList( reagent, sList, regionFolder, path);
        
        showBuildingAllowed(superRef, sList, regionFolder, path );

        showIngredientList(ingredient, sList, regionFolder, path );
        
        showProductList(product, sList, regionFolder, path );
        
//        Queue<RegionType> myQueue = new Queue<RegionType>();
		{
		};
	}
}
