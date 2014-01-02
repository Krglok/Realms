package net.krglok.realms.core;

import java.util.ArrayList;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.settlemanager.ProductBuilding;

/**
 * Der NPC Manager übernimmt die automatische Verwaltung von NPC settlements.
 * Der Manager hat kein Gedächtnis und arbeitet nach dem Ergebnis der Ausertung.
 * Er kann Produktions buildings ein/ausschalten. Hierzu benutzt er hysterese
 * Schalter für die verschiedenen Items
 * 
 * @author Windu  01.01.2014
 * 
 */
public class NpcManager 
{
	private ArrayList<ProductBuilding> productList;
	private ArrayList<String> npcItems;
	
	public NpcManager() 
	{
		productList = new ArrayList<ProductBuilding>();
		npcItems    = initNpcItems();
	}

	public ArrayList<ProductBuilding> getProductList() 
	{
		return productList;
	}

	public void setProductList(
			BuildingList buildingList,
			ServerInterface server) 
	{
		ItemArray items ;
		
		for (Building building : buildingList.getBuildingList().values())
		{
			items = building.produce(server);
			for (Item item : items)
			{
				productList.add(new ProductBuilding(item.ItemRef(), building.getBuildingType(), building.getId()));
			}
		}
	}

	public ArrayList<String> getNpcItems() 
	{
		return npcItems;
	}

	public void setNpcItems(ArrayList<String> npcItems) 
	{
		this.npcItems = npcItems;
	}

	private ArrayList<String> initNpcItems()
	{
		ArrayList<String> defaultItems = new ArrayList<String>();
		defaultItems.add("WHEAT");
		defaultItems.add("BREAD");
		defaultItems.add("LOG");
		defaultItems.add("WOOD");
		defaultItems.add("STICK");
		defaultItems.add("WOOD_HOE");
		defaultItems.add("WOOD_AXE");
		defaultItems.add("WOOD_PICKAXE");
		defaultItems.add("WOOD_SWORD");
		defaultItems.add("LEATHER");
		defaultItems.add("COAL");
		defaultItems.add("IRON_ORE");
		defaultItems.add("IRON_INGOT");
		defaultItems.add("");
		defaultItems.add("");
		defaultItems.add("");
		defaultItems.add("");
		defaultItems.add("");
		defaultItems.add("");
		return defaultItems;
	}
	
}
