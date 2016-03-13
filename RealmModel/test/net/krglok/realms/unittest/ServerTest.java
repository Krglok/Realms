package net.krglok.realms.unittest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.RegionConfig;
import net.krglok.realms.builder.RegionConfigList;
import net.krglok.realms.builder.SuperRegionConfig;
import net.krglok.realms.builder.SuperRegionConfigList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ConfigBiome;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.RecipeData;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.MaterialBuildPlanList;
import net.krglok.realms.tool.StrongholdTools;
import net.krglok.realms.tool.SuperRegionData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class ServerTest  implements ServerInterface // extends ServerData
{
	private static final double VERKAUF_FAKTOR = 1.25;

	
	ArrayList<String> playerNameList;
	ArrayList<String> offPlayerNameList;
	RegionConfigList regionConfigList = new RegionConfigList();
	SuperRegionConfigList superRegionConfigList = new SuperRegionConfigList();
	MaterialBuildPlanList materialBuildPlanList = new MaterialBuildPlanList();
	
	// ItemList = HashMap<String, Integer> 
	HashMap<Integer,ItemList> prodStore = new HashMap<Integer, ItemList>();
	
//	private ItemList defaultItems;
	
	private RecipeData recipeData;

	DataInterface data;
	
	public ItemList getDefaultItems()
	{
		return null; //defaultItems;
	}

	public ServerTest(DataInterface data)
	{
		this.data = data;
//		initItemList();
		playerNameList = getPlayerNameList();
		offPlayerNameList = getOffPlayerNameList();
		getBuildingList();
		getItemNameList();
		getPlayerNameList();
		recipeData = new RecipeData();
		initRegionConfig();
		initSuperRegionConfig();
		initMaterialBuildPlanList();
	}

	@Override
	public ArrayList<String> getPlayerNameList()
	{
		ArrayList<String> pList = new ArrayList<String>();
		pList.add("dradmin");
		return pList;
	}

	@Override
	public ArrayList<String> getOffPlayerNameList()
	{
		ArrayList<String> pList = new ArrayList<String>();
		pList.add("NPC0");
		pList.add("NPC1");
		pList.add("NPC2");
		pList.add("NPC3");
		pList.add("NPC4");
		pList.add("NPC5");
		return pList;
	}

	@Override
	public ArrayList<String> getItemNameList()
	{
		ArrayList<String> newList = new ArrayList<String>();
//		for (String s :	defaultItems.keySet())
//		{
//			newList.add(s);
//		}
		return newList;
	}

	@Override
	public HashMap<String, String> getBuildingList()
	{
		HashMap<String, String> rList = new HashMap<String, String>();
		
		rList.put("0", "WAREHOUSE");
		rList.put("1", "TAVERNE");
		rList.put("2", "HOME");
		rList.put("3", "HOME");
		rList.put("4", "GUARDHOUSE");
		rList.put("6", "HOME");
		rList.put("7", "HOME");
		rList.put("8", "TAVERNE");
		rList.put("9", "WAREHOUSE");
		rList.put("10", "HOME");
		rList.put("11", "HOME");
		rList.put("12", "HOME");
		rList.put("13", "HOME");
		rList.put("14", "HOME");
		rList.put("15", "HOUSE");
		rList.put("16", "WHEAT");
		rList.put("17", "TAVERNE");
		rList.put("18", "WHEAT");
		rList.put("19", "heiler_haus");
		rList.put("20", "WAREHOUSE");
		rList.put("21", "TAVERNE");
		rList.put("22", "TAVERNE");
		rList.put("23", "WAREHOUSE");
		rList.put("24", "HOME");
		rList.put("25", "HOME");
		rList.put("26", "heiler_haus");
		rList.put("27", "TAVERNE");
		rList.put("28", "HOME");
		rList.put("29", "HOME");
		rList.put("30", "HOME");

		rList.put("31", "FARMHOUSE");
		rList.put("32", "FARMHOUSE");
		rList.put("33", "FARMHOUSE");
		rList.put("34", "HALL");
		rList.put("35", "BIBLIOTHEK");
		rList.put("36", "CARPENTER");
		rList.put("37", "CABINETMAKER");
		rList.put("38", "SHEPHERD");
		rList.put("39", "HAOUSE");
		rList.put("41", "WORKSHOP");
		rList.put("42", "WORKSHOP");
		rList.put("43", "WORKSHOP");
		
		return rList;
	}


	@Override
	public String getSuperRegionType(String superRegionName)
	{
		switch(superRegionName)
		{
		case "Dunjar" : return "HAMLET";
		case "NewHaven" : return "CITY";
		case "Helnrau" : return "TOWN";
		default :
			return "";
		}
	}
	@Override
	public SuperRegion getSuperRegion(String superRegionName)
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
		ArrayList<SuperRegionData> sRegions =  StrongholdTools.getSuperRegionData(path);
		
		for(SuperRegionData sr : sRegions)
		{
			if (sr.getName().equalsIgnoreCase(superRegionName))
			{
				return null;
			}
		}
		
		return null;
	}
	
	public void destroySuperRegion(String superRegionName)
	{
		
	}


	@Override
	public int getSuperRegionRadius(String superRegionType)
	{
		switch (superRegionType)
		{
		case "HAMLET" : return 40;
		case "TOWN" : return 70;
		case "CITY" : return 100;
		
		default : return 0;
		}
		
	}
	
	
	@Override
	public int getSuperRegionPower(String superRegionName)
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        ArrayList<SuperRegionData> sRegionList = StrongholdTools.getSuperRegionData(path);

        for( SuperRegionData sRegion : sRegionList)
        {
        	if (sRegion.getName().equals(superRegionName))
        	{
        		return sRegion.getMaxPower();
        	}
        }
        return 0;
	}

	@Override
	public double getSuperRegionbank(String superRegionName)
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        ArrayList<SuperRegionData> sRegionList = StrongholdTools.getSuperRegionData(path);

        for( SuperRegionData sRegion : sRegionList)
        {
        	if (sRegion.getName().equals(superRegionName))
        	{
        		return sRegion.getBalance();
        	}
        }
        return 0.0;
	}

	@Override
	public ItemList getRegionOutput(String regionType)
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
//        String sRegionFile = regionType + ".yml";
//		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
		RegionConfig region = regionConfigList.get(BuildPlanType.getBuildPlanType(regionType));
		ItemList rList = new ItemList();
		if (region != null)
		{
			for (ItemStack itemStack :region.getOutput())
			{
				rList.addItem(itemStack.getType().name(), itemStack.getAmount());
			}
		}
		return rList;
	}

	@Override
	public ItemList getRegionUpkeep(String regionType)
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
//        String sRegionFile = regionType + ".yml";
//		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
		RegionConfig region = regionConfigList.get(BuildPlanType.getBuildPlanType(regionType));
		ItemList rList = new ItemList();
		if (region != null)
		{
//			System.out.print("Region Upkeep: "+region.getName());
			for (ItemStack itemStack :region.getUpkeep())
			{
//				System.out.print("|"+itemStack.getType()+":"+itemStack.getAmount());
				rList.addItem(itemStack.getType().name(), itemStack.getAmount());
			}
//			System.out.println("");
		}
		
		
		return rList;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
//        String sRegionFile = regionType + ".yml";
//		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
		RegionConfig region = regionConfigList.get(BuildPlanType.getBuildPlanType(regionType));
		if (region != null)
		{
			return region.getMoneyOutput();
		}
		return 0.0;
	}

	
	public HashMap<Integer,ItemList> getProdStore()
	{
		return prodStore;
	}


	
	@Override
	public double getRecipeFactor(String itemRef, Biome biome, int amount)
	{

		double prodFactor = (100.0 + (double) getBiomeFactor(biome, Material.getMaterial(itemRef)))/100.0 ;
		return prodFactor;
		
//		if (recipeData.getWeaponRecipe(itemRef).size() > 0)
//		{
//			return 1;
//		}
//		if (recipeData.getToolRecipe(itemRef).size() > 0)
//		{
//			return 16;
//		}
//		
//		return 8;
	}
	
	@Override
	public ItemList getRecipeProd(String itemRef, String hsRegionType)
	{
		ItemList items = new ItemList();
		items = getRegionUpkeep(hsRegionType);
		return items;
	}
	
	@Override
	public ItemList getRecipe(String itemRef)
	{
		return recipeData.getRecipe(itemRef);
	}

	@Override
	public ItemList getFoodRecipe(String itemRef)
	{
		return recipeData.getFoodRecipe(itemRef);
	}

	@Override
	public boolean checkRegionEnabled(int regionId)
	{
		return true;
	}

	@Override
	public Double getRecipePrice(String itemRef, ItemList ingredients)
	{
		Double prodCost = 0.0;
		Double price = 0.0;
		int amount = 1;
		for (String recipeRef : ingredients.keySet())
		{
			if (recipeRef.equals(itemRef) == false)
			{
//				System.out.println("ingredients: "+itemRef+":"+recipeRef);
				price =  data.getPriceList().getBasePrice(recipeRef);
				if (price == 0.0)
				{
					price =1.0;
				}
				prodCost = prodCost + (ingredients.getValue(recipeRef) * price) ; // * VERKAUF_FAKTOR);
//				System.out.println("cost: "+recipeRef+":"+price+" * "+ingredients.getValue(recipeRef)+"="+(ingredients.getValue(recipeRef) * price));
			} else
			{
				amount = ingredients.getValue(recipeRef);
				if (amount == 0)
				{
					amount = 1;
				}
			}
		}
		prodCost = prodCost / amount;
		if (prodCost == 0.0)
		{
			//prodCost = 1.0;
		}
		return prodCost;
	}

	@Override
	public ItemPriceList getProductionPrice(String itemRef)
	{
		ItemPriceList items = new ItemPriceList();
//		ItemList ingredients =  recipeData.getRecipe(itemRef);
//		Double prodCost = getRecipePrice(itemRef, ingredients);
//		items.add(itemRef, prodCost);
		return items;
	}

	@Override
	public Double getItemPrice(String itemRef)
	{
		ItemPriceList items = data.getPriceList();
		items.add("IRON_SWORD", 235.0);

		
		if (items.get(itemRef) != null)
		{
			return  items.get(itemRef).getBasePrice();
		} else
		{
			System.out.println("No ItemPrice :"+itemRef);
			return 0.9;
		}

	}

	@Override
	public ArrayList<Region> getRegionInSuperRegion(String superRegionName)
	{
		ArrayList<Region> rList = new ArrayList<Region>() ;
		Location loc = new Location(null, (double) 0.0, (double) 0.0, (double) 0.0);
		int id = 0;
		Region region;

			
		
		return rList;
	}
	
	private SuperRegionConfig getSuperRegionConfig(String regionName)
	{
        SuperRegionConfig region = null;
        if (SettleType.getSettleType(regionName) != SettleType.NONE)
        {
			String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
	        File regionFile = new File(path, "SuperRegionConfig\\"+regionName+".yml");
	        if (!regionFile.exists()) 
	        {
//	        	System.out.println("SuperRegionFile not found :"+regionName);
	            return region;
	        }
        	region= StrongholdTools.getSuperRegionConfig(path+"\\SuperRegionConfig", regionName+".yml");
        }
        return region;
	}
	
	public void initSuperRegionConfig()
	{
		for (SettleType bType : SettleType.values())
		{
			SuperRegionConfig rConfig = getSuperRegionConfig(bType.name());
			if (rConfig != null)
			{
				superRegionConfigList.put(bType, rConfig);
			}
		}
	}
	
	
	
	/**
	 * Liest die Region Config Datei ein.
	 * 
	 * @param regionName
	 * @return
	 */
	private RegionConfig getRegionConfig(String regionName)
	{
        RegionConfig region = null;
        if (BuildPlanType.getBuildPlanType(regionName)!=BuildPlanType.NONE)
        {
			String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
	        File regionFile = new File(path, "RegionConfig\\"+regionName+".yml");
	        if (!regionFile.exists()) 
	        {
//	        	System.out.println("RegionFile not found :"+regionName);
	            return region;
	        }
        	region= StrongholdTools.getRegionConfig(path+"\\RegionConfig", regionName+".yml");
        }
        return region;
	}

	public void initRegionConfig()
	{
		
		for (BuildPlanType bType : BuildPlanType.values())
		{
			RegionConfig rConfig = getRegionConfig(bType.name());
			if (rConfig != null)
			{
				regionConfigList.put(bType, rConfig);
			}
		}
	}
	

	@Override
	public String getRegionType(int id)
	{
		return getBuildingList().get(String.valueOf(id));
	}

	@Override
	public void setRegionChest(int id, ItemList itemList)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemList getRegionReagents(String regionType)
	{
		RegionConfig region = regionConfigList.get(regionType);
		return region.getReagentsItem();
	}

	@Override
	public Region getRegionAt(LocationData pos)
	{
		return null;
	}

	@Override
	public double getRegionTypeCost(String regionType)
	{
		RegionConfig region = regionConfigList.get(regionType);
		
		return region.getMoneyRequirement();
	}

	@Override
	public void initMaterialBuildPlanList() 
	{
		for (RegionConfig rConfig : regionConfigList.values())
		{
			for (ItemStack item : rConfig.getOutput())
			{
				materialBuildPlanList.addMaterialBuildPlan(item.getType().name(), BuildPlanType.getBuildPlanType(rConfig.getName()));
			}
		}
		
	}

	@Override
	public int getBiomeFactor(Biome biome, Material mat)
	{
		
		return ConfigBiome.getBiomeFactor(biome, mat);
	}

	@Override
	public ItemList getBiomeMaterial(Biome biome)
	{
		return ConfigBiome.getBiomeMaterial(biome);
	}

	@Override
	public ItemList getBiomeNeutralMaterial(Biome biome)
	{
		return ConfigBiome.getBiomeNeutralMaterial(biome);
	}
}
