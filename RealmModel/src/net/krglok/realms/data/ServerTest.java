package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.tool.SuperRegionData;

public class ServerTest  implements ServerInterface // extends ServerData
{
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
//						new SuperRegion(
//						name, 
//						l, 
//						type, 
//						owner, 
//						members, 
//						power, 
//						taxes, 
//						balance, 
//						taxRevenue
//						);
			}
		}
//		rList.put("admin_tower", "Admin");
//		rList.put("Aether_Spawn", "AdminSec");
//		rList.put("Borum", "Stadt");
//		rList.put("claim1", "Claim");
//		rList.put("Clan_Moorhalle", "Claim");
//		rList.put("Bauernhof1", "Bauernhof");
//		rList.put("Bauernhof2", "Bauernhof");
//		rList.put("Bauernhof3", "Bauernhof");
//		rList.put("Werkstatt1", "Werkstatt");
//		rList.put("Werkstatt2", "Werkstatt");
//		rList.put("Werkstatt3", "Werkstatt");
//		rList.put("", "");
//		rList.put("", "");
		
		return null;
	}

	@Override
	public int getSuperRegionPower(String superRegionName)
	{

		switch (superRegionName)
		{
		case "admin_tower": return 99999999;
		case "Aether_Spawn": return 99999999;
		case "New Haven": return 10000;
		case "claim1": return 100;
		case "Clan_Moorhalle":  return 100;
		case "Dujar": return 1500;
		default :
			return 0;
		}
	}

	@Override
	public double getSuperRegionbank(String superRegionName)
	{
		switch (superRegionName)
		{
		case "admin_tower": return 99999999;
		case "Aether_Spawn": return 99999999;
		case "New Haven": return 10000;
		case "claim1": return 100;
		case "Clan_Moorhalle":  return 100;
		case "Dujar": return 1500;
		default :
			return 0;
		}
	}

	@Override
	public ItemList getRegionOutput(String regionType)
	{
		ItemList rList = new ItemList();
		if (regionType != null)
		{
			switch (regionType)
			{
			case "kornfeld":
				rList.putItem("WHEAT", 16);
				break;
			case "schreiner":
				rList.putItem("WOOD", 68);
				rList.putItem("STICK", 60);
				break;
			case "steinbruch":
				rList.putItem("COBBLESTONE", 30);
				break;
			case "schaefer":
				rList.putItem("WOOL", 1);
				break;
			case "holzfaeller":
				rList.putItem("LOG", 32);
				break;
			case "tischler":
				rList.putItem("TORCH", 16);
				break;
			case "prod_waxe":
				rList.putItem("WOOD_AXE", 20);
				break;
			case "prod_whoe":
				rList.putItem("WOOD_HOE", 20);
				break;
			case "prod_wpaxe":
				rList.putItem("WOOD_PICKAXE", 20);
				break;
			case "prod_wsword":
				rList.putItem("WOOD_SWORD", 20);
				break;
			case "bauern_haus":
				rList.putItem("WHEAT", 32);
				break;
			case "werkstatt_haus":
				rList.putItem("STICK", 1);
				break;
			case "koehler":
				rList.putItem("COAL", 32);
				break;
			case "haus_baecker":
				rList.putItem("BREAD", 8);
				break;
			case "schmelze":
				rList.putItem("IRON_INGOT", 32);
				break;
			case "prod_isword":
				rList.putItem(Material.IRON_SWORD.name(), 1);
				break;
			case "prod_ihelmet":
				rList.putItem(Material.IRON_HELMET.name(), 1);
				break;
			case "prod_ichest":
				rList.putItem(Material.IRON_CHESTPLATE.name(), 1);
				break;
			case "prod_ihose":
				rList.putItem(Material.IRON_LEGGINGS.name(), 1);
				break;
			case "prod_ishoe": 
				rList.putItem(Material.IRON_BOOTS.name(), 1);
				break;
			case "huehnerstall": 
				rList.putItem(Material.FEATHER.name(), 1);
				rList.putItem(Material.RAW_CHICKEN.name(), 1);
				break;
			case "rinderstall": 
				rList.putItem(Material.RAW_BEEF.name(), 2);
				rList.putItem(Material.LEATHER.name(), 1);
				break;
			case "steinmine": 
				rList.putItem(Material.IRON_ORE.name(), 1);
				rList.putItem(Material.COBBLESTONE.name(), 16);
				rList.putItem(Material.GRAVEL.name(), 8);
				rList.putItem(Material.FLINT.name(), 3);
				break;
			case "stone" :
				rList.putItem(Material.STONE.name(), 6);
//				rList.put(Material.COBBLESTONE.name(), 6);
//				rList.put(Material.COAL.name(), 1);
				break;
			case "bibliothek":
				rList.putItem("PAPER", 1);
				break;
			case "heiler_haus":
				rList.putItem("", 0);
				break;
			default:
				break;
			}
		}
		return rList;
	}

	@Override
	public ItemList getRegionUpkeep(String regionType)
	{
		ItemList rList = new ItemList();
		
		switch (regionType)
		{
		case "schreiner":
//			rList.putItem("WHEAT", 1);
			rList.putItem("LOG", 32);
			break;
		case "holzfaeller":
//			rList.putItem("WHEAT", 2);
			rList.putItem("WOOD_AXE", 1);
			break;
		case "tischler":
			rList.putItem("LOG", 8);
			break;
		case "steinbruch":
			rList.putItem("WHEAT", 2);
			rList.putItem("WOOD_PICKAXE", 30);
			break;
		case "schaefer":
			rList.putItem("WHEAT", 1);
			break;
		case "koehler":
			rList.putItem("WHEAT", 1);
			rList.putItem("LOG", 32);
			break;
		case "prod_waxe":
//			rList.putItem("BREAD", 1);
			rList.putItem("WHEAT", 2);
			rList.putItem("STICK", 40);
			rList.putItem("WOOD", 60);
			break;
		case "prod_whoe":
//			rList.putItem("BREAD", 1);
			rList.putItem("WHEAT", 1);
			rList.putItem("STICK", 40);
			rList.putItem("WOOD", 5);
			break;
		case "prod_wpaxe":
//			rList.putItem("BREAD", 1);
			rList.putItem("WHEAT", 1);
			rList.putItem("STICK", 40);
			rList.putItem("WOOD", 5);
			break;
		case "prod_wsword":
//			rList.putItem("BREAD", 1);
			rList.putItem("WHEAT", 1);
			rList.putItem("STICK", 20);
			rList.putItem("WOOD", 5);
			break;
		case "werkstatt_haus":
			rList.putItem("BREAD", 5);
			break;
		case "bauern_haus":
			rList.putItem("WOOD_HOE", 1);
			break;
		case "haus_baecker":
			rList.putItem("WHEAT", 24);
			break;
		case "schmelze":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_ORE", 32);
			rList.putItem("COAL", 5);
			break;
		case "prod_isword":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_INGOT", 2);
			rList.putItem("STICK", 1);
			break;
		case "prod_ihelmet":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_INGOT", 5);
			break;
		case "prod_ichest":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_INGOT", 8);
			break;
		case "prod_ihose":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_INGOT", 7);
			break;
		case "prod_ishoe":
			rList.putItem("BREAD", 1);
			rList.putItem("IRON_INGOT", 4);
			break;
		case "huehnerstall": 
			rList.putItem(Material.SEEDS.name(), 1);
			break;
		case "rinderstall": 
			rList.putItem(Material.WHEAT.name(), 2);
			break;
		case "haus_trader":
			rList.putItem("WHEAT", 1);
			break;
		case "stone" :
//			rList.put(Material.STONE.name(), 6);
			rList.putItem(Material.COBBLESTONE.name(), 6);
			rList.putItem(Material.COAL.name(), 1);
			break;
		case "heiler_haus":
			break;
		default:
			break;
		}
		
		return rList;
	}

	@Override
	public double getRegionUpkeepMoney(String regionType)
	{
		switch (regionType)
		{
		case "taverne": return 7;
		case "markt": return 5;
		case "haus_einfach": return 1;
		case "haus_gross": return 2;
		case "haus_stadt":  return 4;
		default :
			return 0;
		}
	}

	
	public HashMap<Integer,ItemList> getProdStore()
	{
		return prodStore;
	}

	@Override
	public int getRecipeFactor(String itemRef)
	{
		if (recipeData.getWeaponRecipe(itemRef).size() > 0)
		{
			return 1;
		}
		if (recipeData.getToolRecipe(itemRef).size() > 0)
		{
			return 16;
		}
		
		return 8;
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
				items.add(mat.name(), 1.0);
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
}
