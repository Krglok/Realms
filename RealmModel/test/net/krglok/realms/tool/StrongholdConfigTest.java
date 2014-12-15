package net.krglok.realms.tool;

import java.io.File;
import java.util.HashMap;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.unittest.RegionConfig;


import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class StrongholdConfigTest
{
	
	

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

        System.out.println(" ");
        System.out.println("[Construction] Resources : " + required.size());
        for (String sName : required.keySet())
        {
        	materials = materials +""+sName+" ";
        }
        System.out.println(ConfigBasis.setStrleft(" ",15)+materials);
        
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : required.keySet())
	            {
	            	required.put(itemRef, 0);
	            }
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getRequirements())
	            {
	            	required.put(item.getType().name(), item.getAmount());
	            }
		        materials = "";
		        for (String sName : required.keySet())
		        {
		        	int value = required.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
						sValue = ConfigBasis.setStrleft(sValue,sName.length());
					}
		        	materials = materials +""+sValue+" ";
		        }
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15)+materials );
		        
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
        System.out.println(" ");
        for (String sName : reagent.keySet())
        {
    	reagents = reagents +""+sName+" ";
        }
        System.out.println(ConfigBasis.setStrleft(" ",15)+reagents);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : reagent.keySet())
	            {
	            	reagent.put(itemRef, 0);
	            }
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getReagents())
	            {
	            	reagent.put(item.getType().name(), item.getAmount());
	            }
	            reagents = "";
		        for (String sName : reagent.keySet())
		        {
		        	int value = reagent.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
//						sValue = sValue + " ";
						sValue = ConfigBasis.setStrleft(sValue,sName.length());
					}
		        	reagents = reagents +""+sValue+" ";
		        }
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15) + reagents );
		        
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

        System.out.println(" ");
        System.out.println("[Construction] Build allowed : " + superRef.size());
        for (String sName : superRef.keySet())
        {
        	superRefs = superRefs +""+sName+" ";
        }
        System.out.println(ConfigBasis.setStrleft("",15)+superRefs);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : superRef.keySet())
	            {
	            	superRef.put(itemRef, 0);
	            }
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
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
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15)+superRefs );
		        
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
        System.out.println(" ");
        System.out.println("[Production] Ingredients : " + ingredient.size());
        for (String sName : ingredient.keySet())
        {
    	ingredients = ingredients +""+sName+" ";
        }
        System.out.println(ConfigBasis.setStrleft(" ",15)+ingredients);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : ingredient.keySet())
	            {
	            	ingredient.put(itemRef, 0);
	            }
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
	            for (ItemStack item : region.getUpkeep())
	            {
	            	ingredient.put(item.getType().name(), item.getAmount());
	            }
	            ingredients = "";
		        for (String sName : ingredient.keySet())
		        {
		        	int value = ingredient.get(sName);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
//						sValue = sValue + " ";
						sValue = ConfigBasis.setStrleft(sValue,sName.length());
}
		        	ingredients = ingredients +""+sValue+" ";
		        }
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15)+ingredients );
		        
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
        System.out.println(ConfigBasis.setStrleft(" ",15)+products);
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
            if (isInList(sRegionFile,sList))
            {
	            for (String itemRef : product.keySet())
	            {
	            	product.put(itemRef, 0);
	            }
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
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
						sValue = ConfigBasis.setStrleft(sValue,sName.length());
					}
		        	products = products +""+sValue+" ";
		        }
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15)+products );
		        
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
	

	@SuppressWarnings("unused")
	private String[] setStandardList()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"WHEAT",
        		"WOODCUTTER",
        		"QUARRY",
        		};
	}

	private String[] setNetherList()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"MUSHROOM",
        		"KITCHEN",
        		"NETHERQUARRY"
        		};
	}
	
	private String[] setBasisList()
	{
		return new String[] 
        		{
        		"FARMHOUSE",
        		"SHEPHERD",
        		"CARPENTER",
        		"CABINETMAKER",
        		"BAKERY"
        		};
	}

	@SuppressWarnings("unused")
	private String[] setErweitertList()
	{
		return new String[] 
        		{
        		"BRICKWORK",
        		"CHARBURNER",
				"AXESHOP",
        		"HOESHOP", 
        		"PICKAXESHOP", 
        		"KNIFESHOP",
        		"SPADESHOP",
        		"COWSHED",
        		"CHICKENHOUSE",
        		"FISHERHOOD"
        		};
	}

	@SuppressWarnings("unused")
	private String[] setEnhancedList()
	{
		return new String[] 
        		{"WORKSHOP",
				"STONEMINE",
				"SMELTER",
				"FARM",
				"PIGPEN",
        		"NETHER_BRICKWORK",
				"WARHOUSE",
				"TAVERNE"
        		};
	}
	
	
	@Test
	public void getStrongholdConstructionMaterial()
	{
//		StrongholdTools shTools = new StrongholdTools();
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
//        sList = setStandardList();
//        sList = setNetherList();
//        sList = setBasisList();
//        sList = setErweitertList();
//        sList = setEnhancedList();
        sList = new String[]
        		{
        		"MUSHROOM",
        		};

        System.out.println("[Stronghold] Building              cost" );
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	
        	sRegionFile = RegionFile.getName();
            if ( isInList(sRegionFile,sList))
            {
	        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	
	            System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),20)+"  Cost : "+ ConfigBasis.setStrright(String.valueOf(region.getMoneyRequirement()),10));

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
