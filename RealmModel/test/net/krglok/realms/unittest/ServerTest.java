package net.krglok.realms.unittest;

import java.util.ArrayList;
import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.RecipeData;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.data.StrongholdTools;
import net.krglok.realms.tool.SuperRegionData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

public class ServerTest  implements ServerInterface // extends ServerData
{

	public static final int FAKTOR_0 = 0;
	public static final int FAKTOR_M = -25;
	public static final int FAKTOR_MM = -75;
	public static final int FAKTOR_MMM = -100;
	public static final int FAKTOR_P = 25;
	public static final int FAKTOR_PP = 50;
	public static final int FAKTOR_PPP = 75;
	
	ArrayList<String> playerNameList;
	ArrayList<String> offPlayerNameList;
	
	// ItemList = HashMap<String, Integer> 
	HashMap<Integer,ItemList> prodStore = new HashMap<Integer, ItemList>();
	
//	private ItemList defaultItems;
	
	private RecipeData recipeData;
	
	public ItemList getDefaultItems()
	{
		return null; //defaultItems;
	}

	public ServerTest()
	{
//		initItemList();
		playerNameList = getPlayerNameList();
		offPlayerNameList = getOffPlayerNameList();
		getBuildingList();
		getItemNameList();
		getPlayerNameList();
		recipeData = new RecipeData();
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
		
		rList.put("0", "markt");
		rList.put("1", "taverne");
		rList.put("2", "haus_einfach");
		rList.put("3", "arrowturret1");
		rList.put("4", "stadtwache");
		rList.put("5", "haendler");
		rList.put("6", "haus_einfach");
		rList.put("7", "haus_einfach");
		rList.put("8", "taverne");
		rList.put("9", "markt");
		rList.put("10", "haus_einfach");
		rList.put("11", "haus_einfach");
		rList.put("12", "haus_einfach");
		rList.put("13", "haus_einfach");
		rList.put("14", "haus_einfach");
		rList.put("15", "haus_stadt");
		rList.put("16", "kornfeld");
		rList.put("17", "taverne");
		rList.put("18", "kornfeld");
		rList.put("19", "heiler_haus");
		rList.put("20", "markt");
		rList.put("21", "taverne");
		rList.put("22", "taverne");
		rList.put("23", "markt");
		rList.put("24", "haus_einfach");
		rList.put("25", "haus_einfach");
		rList.put("26", "heiler_haus");
		rList.put("27", "taverne");
		rList.put("28", "haus_einfach");
		rList.put("29", "haus_einfach");
		rList.put("30", "haus_einfach");

		rList.put("31", "bauern_haus");
		rList.put("32", "bauern_haus");
		rList.put("33", "bauern_haus");
		rList.put("34", "haupthaus");
		rList.put("35", "bibliothek");
		rList.put("36", "schreiner");
		rList.put("37", "tischler");
		rList.put("38", "schaefer");
		rList.put("39", "haus_gross");
		rList.put("41", "werkstatt_haus");
		rList.put("42", "werkstatt_haus");
		rList.put("43", "werkstatt_haus");
		
		return rList;
	}


	@Override
	public String getSuperRegionType(String superRegionName)
	{
		switch(superRegionName)
		{
		case "Dunjar" : return "Siedlung";
		case "NewHaven" : return "Stadt";
		case "Helnrau" : return "Dorf";
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
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        String sRegionFile = regionType + ".yml";
		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
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
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        String sRegionFile = regionType + ".yml";
		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
		ItemList rList = new ItemList();
		if (region != null)
		{
			for (ItemStack itemStack :region.getUpkeep())
			{
				rList.addItem(itemStack.getType().name(), itemStack.getAmount());
			}
		}
		
		
		return rList;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        String sRegionFile = regionType + ".yml";
		RegionConfig region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile );
		
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

	private int plainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_PP;
		case SEEDS : return FAKTOR_PP;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		default :
			return  FAKTOR_0;
		}
	}

	private int mountainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_0;
		}
	}

	private int hillFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_P;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_0;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_0;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int swampFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_0;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PP; 
		case BROWN_MUSHROOM : return FAKTOR_PP; 
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		default :
			return  FAKTOR_0;
		}
	}

	private int oceanFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_PPP;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_MMM;
		case COAL_ORE : return FAKTOR_MMM;
		case DIAMOND_ORE : return FAKTOR_MMM;
		case EMERALD_ORE : return FAKTOR_MMM;
		case LAPIS_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_MMM;
		case GOLD_ORE : return FAKTOR_MMM;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int forestFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_0;
		case LOG: return FAKTOR_PPP;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_M;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_0;
		case COAL_ORE : return FAKTOR_0;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_0;
		case LAPIS_ORE : return FAKTOR_0;
		case GOLD_ORE : return FAKTOR_0;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int desertFactor(Material mat)
	{
		switch (mat)
		{
		case SAND : return FAKTOR_PPP;
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_P;
		case GOLD_NUGGET: return FAKTOR_M;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_M;
		case EMERALD : return FAKTOR_P;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_M;
		case COAL_ORE : return FAKTOR_M;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_M;
		case LAPIS_ORE : return FAKTOR_M;
		case GOLD_ORE : return FAKTOR_M;
		default :
			return  FAKTOR_MM;
		}
	}

	private int extremeFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MMM;
		case SEEDS : return FAKTOR_MMM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_MMM;
		case EMERALD : return FAKTOR_PP;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_PP;
		case COAL_ORE : return FAKTOR_PP;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_0;
		default :
			return  FAKTOR_0;
		}
	}

	private int hellFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PPP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_MM;
		case FEATHER : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PPP; 
		case BROWN_MUSHROOM : return FAKTOR_PPP;
		case LAVA : return FAKTOR_PPP;
		case NETHERRACK : return FAKTOR_PPP;
		case SOUL_SAND : return FAKTOR_PPP;
		case GHAST_TEAR : return FAKTOR_PPP;
		case MAGMA_CREAM : return FAKTOR_PPP;
		
		default :
			return  FAKTOR_MMM;
		}
	}
	
	@Override
	public int getBioneFactor(Biome biome, Material mat)
	{
		int factor = 0;
		if (biome == null)
		{
			return factor;
		}
		if (biome.name().contains("PLAIN"))
		{
			factor = factor + plainFactor(mat);
		}
		if (biome.name().contains("SWAMP"))
		{
			factor = factor + swampFactor(mat);
		}
		if (biome.name().contains("MOUNTAIN"))
		{
			factor = factor + mountainFactor(mat);
		}
		if (biome.name().contains("OCEAN"))
		{
			factor = factor + oceanFactor(mat);
		}
		if (biome.name().contains("FOREST"))
		{
			factor = factor + forestFactor(mat);
		}
		if (biome.name().contains("DESERT"))
		{
			factor = factor + desertFactor(mat);
		}
		if (biome.name().contains("EXTREME"))
		{
			factor = factor + extremeFactor(mat);
		}
		if (biome.name().contains("HELL"))
		{
			factor = factor + hellFactor(mat);
		}
		return factor;
	}

	
	@Override
	public double getRecipeFactor(String itemRef, Biome biome)
	{

		double prodFactor = (100.0 + (double) getBioneFactor(biome, Material.getMaterial(itemRef)))/100.0 ;
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
		return 0.0;
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
		ItemPriceList items = new ItemPriceList();
		for (Material mat : Material.values())
		{
			if (mat.name().contains("IRON"))
			{
				items.add(mat.name(), 25.0);
			}
		}
		items.add("WHEAT", 0.30);
		items.add("LOG", 0.5);
		items.add("COBBLESTONE", 0.5);
		items.add("SAND", 0.5);
		items.add("STONE", 1.7);
		items.add("IRON_INGOT", 56.0);
		items.add("GOLD_INGOT", 400.0);
		items.add("WOOD", 0.1666);
		items.add("STICK", 0.0555);
		items.add("WOOD_AXE", 1.25);
		items.add("WOOD_PICKAXE", 1.25);
		items.add("WOOD_HOE", 1.00);
		items.add("WOOD_SWORD", 0.6);
		items.add("BREAD", 1.0);
		items.add("COAL", 3.0);
		items.add("IRON_ORE", 15.0);
		items.add("IRON_SWORD", 235.0);
	
		if (items.get(itemRef) != null)
		{
			return  items.get(itemRef).getBasePrice();
		} else
		{
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
		if (superRegionName.equalsIgnoreCase("Dunjar"))
		{
			id++;
			region = new Region(id, loc, "haupthaus", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "holzfaeller", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "steinbruch", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "schreiner", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "tischler", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "schaefer", null, null);
			rList.add(region);
		}		

		if (superRegionName.equalsIgnoreCase("NewHaven"))
		{
			id++;
			region = new Region(id, loc, "haupthaus", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "markt", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "taverne", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "holzfaeller", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "steinbruch", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "schreiner", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "tischler", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "schaefer", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "bauern_haus", null, null);
			rList.add(region);
		} else
		{
			id++;
			region = new Region(id, loc, "haupthaus", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "haus_einfach", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "kornfeld", null, null);
			rList.add(region);
			id++;
			region = new Region(id, loc, "schaefer", null, null);
			rList.add(region);
		}		
			
		
		return rList;
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
		ItemList items = new ItemList();
		items.addItem(new Item("GOLD_NUGGET",1 ));
		return items;
	}

	@Override
	public Region getRegionAt(LocationData pos)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
