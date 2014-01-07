package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import net.krglok.realms.core.ItemList;

public class TestServer  implements ServerInterface // extends ServerData
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

	public TestServer()
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
		rList.put("5", "bibliothek");
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
		rList.put("36", "haus_einfach");
		rList.put("37", "haus_einfach");
		rList.put("38", "haus_einfach");
		rList.put("39", "haus_gross");
		rList.put("41", "werkstatt_haus");
		rList.put("42", "werkstatt_haus");
		rList.put("43", "werkstatt_haus");
		
		return rList;
	}

	@Override
	public HashMap<String, String> getSuperRegionList()
	{
		HashMap<String, String> rList = new HashMap<String, String>();
		
		rList.put("admin_tower", "Admin");
		rList.put("Aether_Spawn", "AdminSec");
		rList.put("Borum", "Stadt");
		rList.put("claim1", "Claim");
		rList.put("Clan_Moorhalle", "Claim");
		rList.put("Dujar", "Dorf");
		rList.put("Bauernhof1", "Bauernhof");
		rList.put("Bauernhof2", "Bauernhof");
		rList.put("Bauernhof3", "Bauernhof");
		rList.put("Werkstatt1", "Werkstatt");
		rList.put("Werkstatt2", "Werkstatt");
		rList.put("Werkstatt3", "Werkstatt");
//		rList.put("", "");
//		rList.put("", "");
		
		return rList;
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
			case "bibliothek":
				rList.put("PAPER", 1);
				break;
			case "kornfeld":
				rList.put("WHEAT", 32);
				break;
			case "heiler_haus":
				rList.put("", 0);
				break;
			case "prod_brett":
				rList.put("WOOD", 64);
				break;
			case "prod_stick":
				rList.put("STICK", 64);
				break;
			case "prod_waxe":
				rList.put("WOOD_AXE", 20);
				break;
			case "prod_whoe":
				rList.put("WOOD_HOE", 20);
				break;
			case "prod_wpaxe":
				rList.put("WOOD_PICKAXE", 20);
				break;
			case "prod_wsword":
				rList.put("WOOD_SWORD", 20);
				break;
			case "bauern_haus":
				rList.put("WHEAT", 128);
				break;
			case "werkstatt_haus":
				rList.put("STICK", 1);
				break;
			case "koehler":
				rList.put("COAL", 32);
				break;
			case "holzfaeller":
				rList.put("LOG", 15);
				break;
			case "haus_baecker":
				rList.put("BREAD", 32);
				break;
			case "schmelze":
				rList.put("IRON_INGOT", 32);
				break;
			case "prod_isword":
				rList.put(Material.IRON_SWORD.name(), 1);
				break;
			case "prod_ihelmet":
				rList.put(Material.IRON_HELMET.name(), 1);
				break;
			case "prod_ichest":
				rList.put(Material.IRON_CHESTPLATE.name(), 1);
				break;
			case "prod_ihose":
				rList.put(Material.IRON_LEGGINGS.name(), 1);
				break;
			case "prod_ishoe": 
				rList.put(Material.IRON_BOOTS.name(), 1);
				break;
			case "mine": 
				rList.put(Material.IRON_ORE.name(), 8);
				break;
			case "stone" :
				rList.put(Material.STONE.name(), 6);
//				rList.put(Material.COBBLESTONE.name(), 6);
//				rList.put(Material.COAL.name(), 1);
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
		case "bibliothek":
			break;
		case "kornfeld":
			rList.put("WOOD_HOE", 1);
			break;
		case "heiler_haus":
			break;
		case "holzfaeller":
			rList.put("BREAD", 1);
			rList.put("WOOD_AXE", 1);
			break;
		case "prod_brett":
			rList.put("BREAD", 1);
			rList.put("LOG", 16);
			break;
		case "prod_stick":
			rList.put("BREAD", 1);
			rList.put("WOOD", 10);
			break;
		case "prod_waxe":
			rList.put("BREAD", 1);
			rList.put("STICK", 40);
			rList.put("WOOD", 60);
			break;
		case "prod_whoe":
			rList.put("BREAD", 1);
			rList.put("STICK", 40);
			rList.put("WOOD", 40);
			break;
		case "prod_wpaxe":
			rList.put("BREAD", 1);
			rList.put("STICK", 40);
			rList.put("WOOD", 60);
			break;
		case "prod_wsword":
			rList.put("BREAD", 1);
			rList.put("STICK", 20);
			rList.put("WOOD", 60);
			break;
		case "werkstatt_haus":
			rList.put("BREAD", 4);
			break;
		case "bauern_haus":
			rList.put("WOOD_HOE", 1);
			break;
		case "koehler":
			rList.put("BREAD", 1);
			rList.put("LOG", 32);
			break;
		case "haus_baecker":
			rList.put("WHEAT", 64);
			break;
		case "schmelze":
			rList.put("BREAD", 1);
			rList.put("IRON_ORE", 32);
			rList.put("COAL", 5);
			break;
		case "prod_isword":
			rList.put("BREAD", 1);
			rList.put("IRON_INGOT", 2);
			rList.put("STICK", 1);
			break;
		case "prod_ihelmet":
			rList.put("BREAD", 1);
			rList.put("IRON_INGOT", 5);
			break;
		case "prod_ichest":
			rList.put("BREAD", 1);
			rList.put("IRON_INGOT", 8);
			break;
		case "prod_ihose":
			rList.put("BREAD", 1);
			rList.put("IRON_INGOT", 7);
			break;
		case "prod_ishoe":
			rList.put("BREAD", 1);
			rList.put("IRON_INGOT", 4);
			break;
		case "mine":
			rList.put("BREAD", 5);
			rList.put(Material.WOOD_PICKAXE.name(), 4);
			rList.put(Material.LOG.name(), 12);
			break;
		case "stone" :
//			rList.put(Material.STONE.name(), 6);
			rList.put(Material.COBBLESTONE.name(), 6);
			rList.put(Material.COAL.name(), 1);
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


	/**
	 * get all items off the chest
	 * 
	 * @param id  of building
	 * @param itemRef 
	 * @return value amount of items from chest
	 */
	public ItemList getRegionChest(int id, String itemRef)
	{
		ItemList outList = new ItemList();
		
		switch(itemRef)
		{
		case "LOG" :  outList.put("LOG",  64);
		break;
		case "WOOD" : outList.put("WOOD",  64);
		break;
		case "STICK" : outList.put("STICK",  64);
		break;
		case "WOOD_HOE" : outList.put("WOOD_HOE",  20);
		break;
		case "WOOD_AXE" : outList.put("WOOD_AXE",  20);
		break;
		case "WOOD_PICKAXE" : outList.put("WOOD_PICKAXE",  20);
		break;
		case "WOOD_SWORD" : outList.put("WOOD_SWORD",  20);
		break;
		case "WHEAT" : outList.put("WHEAT",  64);
		break;
		case "BREAD" : outList.put("BREAD",  32);
		break;
		
		default : 
		break;
		}
		// hier kann ein Zufallsgenerator für unschrfe sorgen
		return outList;
	}

	/**
	 * 
	 * @param id of building
	 * @param itemRef
	 * @param value  amount of items to set in chest
	 * @return  value of not set items to chest
	 */
	public void setRegionChest(int id, ItemList itemList)
	{
		// id wird im live System benoetigt um die Liste zu finden,
		// prodStore <id, ItemList>  die liste der Materialien zu id
		ItemList iList = prodStore.get(id);
		if (iList == null)
		{
			iList = new ItemList();
			prodStore.put(id, iList);
		}
		
		for (String matRef : itemList.keySet())
		{
			if (iList.get(matRef) != null)
			{
				//add material to chest
				iList.put(matRef,iList.get(matRef)+itemList.get(matRef));
			} else
			{
				// add new material to chest
				iList.put(matRef,itemList.get(matRef));
			}
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
}
