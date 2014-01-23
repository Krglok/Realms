package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.ServerTest;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettlementBreedTest
{
	private Boolean isOutput = false; // set this to false to suppress println
	private boolean isDay = false;
	private String sb = "";
	private boolean showSettler = true;
	private int dayCounter = 0;
	private int month = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	public ItemPriceList testPriceList()
	{
		ItemPriceList items = new ItemPriceList();
//		for (Material mat : Material.values())
//		{
//			if (mat.name().contains("IRON"))
//			{
//				items.add(mat.name(), 1.0);
//			}
//		}
		items.add("WHEAT", 0.30);
		items.add("LOG", 0.5);
		items.add("COBBLESTONE", 0.1);
		items.add("SAND", 0.2);
		items.add("DIRT", 0.2);
		items.add("GRASS", 0.5);
		items.add("STONE", 1.7);
		items.add("COAL", 3.0);
		items.add("WOOL", 0.5);
		items.add("IRON_ORE", 15.0);
		items.add("SEEDS", 0.2);
		items.add("GRAVEL", 0.5);
		items.add("FLINT", 1.0);
		items.add("FEATHER", 0.5);
		items.add("GOLD_NUGGET", 44.0);
		
		return items;
	}

	public ItemPriceList readPriceData() 
	{
        String base = "BASEPRICE";
        ItemPriceList items = new ItemPriceList();
		try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms", "baseprice.yml");
            if (!DataFile.exists()) 
            {
            	items = testPriceList();
            	DataFile.createNewFile();
            	return items;
            }
            HashMap<String,String> values; // = new HashMap<String,String>();
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection(base))
            {
            	
    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            	}
            }
		} catch (Exception e)
		{
		}
		return items;
	}


	private String showBalkenHappy(Settlement settle, boolean isDay, boolean isSettler)
	{
		if (isSettler)
		{
			return showBalkenSettler(settle, isDay);
		}
		int rs = settle.getResident().getSettlerCount();
		int as = 0;
		int hs = (int) (settle.getResident().getHappiness()*20);
		as = 10 + hs;
		String sb = "";
		if (isDay)
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + "-";
			}
		} else
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + " ";
			}
		}
		return sb+"#" +"     "+ settle.getResident().getHappiness();
	}

	private String showBalkenSettler(Settlement settle, boolean isDay)
	{
		int rs = settle.getResident().getSettlerCount();
//		if (rs > 60)
//		{
//			rs = rs -50;
//		}
		rs = (rs / 5) + 1; 
		int as = 0;
		as = rs;
		String sb = "";
		if (isDay)
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + "-";
			}
		} else
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + " ";
			}
		}
		return sb+"#" +"     "+ settle.getResident().getSettlerCount();
	}


	private String getReqList(Settlement settle)
	{
		String reqI = "-";
		for (String itemRef : settle.getRequiredProduction().keySet())
		{
//			Item item = settle.getRequiredProduction().getItem(itemRef);
			reqI = reqI+itemRef+":"+settle.getRequiredProduction().getItem(itemRef).value()+"-";
		}
		
		return reqI;
		
	}
	
	private void BreedingLoop(
			Settlement settle,
			ServerTest server,
			int MaxLoop
			)
	{
		for (int i = 0; i < MaxLoop; i++)
		{
			dayCounter++;
			settle.setSettlerMax();
			settle.checkBuildingsEnabled(server);
			settle.setWorkerNeeded();
			settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
			settle.setHappiness();
			settle.doProduce(server);
			if (dayCounter == 30)
			{
				settle.getTaxe(server);
				dayCounter = 0;
			}
			
			showSettler = true;	
			isDay = false;
			
			if ((i %30) == 0)
			{
				isDay = true;
				month++;
			} 
			
			sb = showBalkenHappy(settle, isDay,showSettler);
			if (isOutput)
			{
				if ((i % 10) == 0)	
					System.out.println(
							month+sb
//							+"/ F:"+settle.getFoodFactor()
//							+"/S:"+settle.getSettlerFactor()
//							+"/E:"+settle.getEntertainFactor()
							+"/D:"+settle.getResident().getDeathrate()
//							+"/B:"+settle.getResident().getBirthrate()
							+"/Wo:"+settle.getTownhall().getWorkerCount()
							+"/Wn:"+settle.getTownhall().getWorkerNeeded()
							+"/M:"+(int)settle.getBank().getKonto()
							+"/W:"+settle.getWarehouse().getItemList().getValue("WHEAT")
							+"/R:"+settle.getRequiredProduction().size()+getReqList(settle)
							+"/ F:"+settle.getFoodFactor()
							+"/S:"+settle.getSettlerFactor()
							);
			}
		}
		
	}

	private void makeSettleAnalysis(Settlement settle, int moth, ItemPriceList priceList)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Sieldungstatus  ========================");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount());
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());
		msg.add(getReqList(settle));
		msg.add("!  ");
		msg.add("Bevölkerungsanalyse  ");
		if (settle.getResident().getSettlerCount() > settle.getResident().getSettlerMax())
		{
			msg.add("!  Sie haben Überbevölkerung in der Siedlung. Dies macht die Siedler unglücklich auf lange Sicht!");
		}
		if (settle.getResident().getHappiness() < 0)
		{
			msg.add("!  Ihre Siedler sind unglücklich. Das wird sie veranlassen zu verschwinden!");
		}
		if (settle.getFoodFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler leiden Hunger. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getSettlerFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler haben keinen Wohnraum. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getEntertainFactor() < 0.9)
		{
			msg.add("!  Ihre Siedler haben wenig Unterhaltung. Etwas mehr Unterhltung macht sie glücklicher!");
		}
		if ((settle.getFoodFactor() < 0.0) && (settle.getResident().getSettlerCount() < 8))
		{
			msg.add("!  Ihre Siedler sind verhungert. Sie haben als Verwalter versagt!");
			msg.add("!  Es würde mich nicht wundern, wenn eine Revolte ausbricht!!");
		}

		msg.add("  ");
		msg.add("Wirtschaftsanalyse  ");
		msg.add("!  Ihre Siedler haben "+(int)(settle.getBank().getKonto())+" Thaler erarbeitet.  Herzlichen Glückwunsch.");

		double price = 0.0;
		double balance = 0.0;
		for (Item item : settle.getWarehouse().getItemList().values())
		{
			price = Math.round(priceList.getBasePrice(item.ItemRef()));
			balance = balance + (item.value()*price);
		}
		msg.add("!  Das Warenlager hat einen Wert von:  "+balance);
		
		if (settle.getTownhall().getWorkerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Arbeiter. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Siedler. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() > settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Sie haben "+(settle.getResident().getSettlerCount() -settle.getTownhall().getWorkerNeeded())+" Siedler ohne Arbeit. Sie könnten neue Arbeitsgebäude bauen !");
		}

		if (settle.getRequiredProduction().size() > 0)
		{
			msg.add("!  Es fehlen "+settle.getRequiredProduction().size()+" verschiedene Rohstoffe zur Produktion.");
		}
		
		if ((settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()) < 512)
		{
			msg.add("!  Die Lagerkapazität ist knapp !  Freie Kapazitäte nur "+(settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()));
		}
		
		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	
	@Test
	public void testSettlementSettlerBreed()
	{
		
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ItemPriceList priceList = readPriceData(); 
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
        ItemPriceList itemPrices = testPriceList();
		
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("3","haus_einfach");
		regionTypes.put("4","haus_einfach");
		regionTypes.put("5","haus_einfach");
//		regionTypes.put("6","haus_einfach");
//		regionTypes.put("7","haus_einfach");
//		regionTypes.put("8","haus_einfach");
//		regionTypes.put("9","haus_einfach");
//		regionTypes.put("10","haus_einfach");
//		regionTypes.put("11","haus_einfach");
//		regionTypes.put("12","haus_einfach");
//		regionTypes.put("13","haus_einfach");
//		regionTypes.put("14","haus_einfach");
//		regionTypes.put("15","haus_einfach");
//		regionTypes.put("16","haus_einfach");
//		regionTypes.put("17","haus_einfach");
//		regionTypes.put("18","haus_einfach");
//		regionTypes.put("19","haus_einfach");
//		regionTypes.put("20","haus_einfach");
//		regionTypes.put("60","taverne");
//		regionTypes.put("61","taverne");
//		regionTypes.put("62","taverne");
		regionTypes.put("65","kornfeld");
		regionTypes.put("66","kornfeld");
//		regionTypes.put("67","kornfeld");
//		regionTypes.put("68","kornfeld");
//		regionTypes.put("69","markt");
		regionTypes.put("69","holzfaeller");
		regionTypes.put("68","schreiner");
//		regionTypes.put("31","bauern_haus");
//		regionTypes.put("70","haus_einfach");
//		regionTypes.put("71","haus_einfach");
//		regionTypes.put("72","haus_einfach");
//		regionTypes.put("73","haus_einfach");
//		regionTypes.put("75","haus_einfach");
//		regionTypes.put("76","haus_einfach");
//		regionTypes.put("77","haus_einfach");
//		regionTypes.put("78","haus_einfach");
//		regionTypes.put("79","haus_einfach");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(pos, settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

//		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 52);
//		settle.getWarehouse().depositItemValue(Material.BREAD.name(), 120);
//		settle.getWarehouse().depositItemValue(Material.WOOD_HOE.name(), 120);
		settle.getWarehouse().depositItemValue("WHEAT",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("BREAD",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("WOOD_HOE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_AXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_PICKAXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("LOG",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("STICK",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("COBBLESTONE",settle.getResident().getSettlerMax());
		
		settle.getResident().setSettlerCount(5);
		settle.setSettlerMax();

//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();
//		settle.getResident().settlerCount();

		
		isOutput =   false; //(expected != actual);
		String reqI = ""; 
		System.out.println("==Settlement Breed  : "+settle.getResident().getSettlerMax());
		BreedingLoop(settle, server, 3080);
		
		makeSettleAnalysis(settle, month, priceList);
		

		int hsRegion = 28;
		Building building;
		
		System.out.println(month+"=====Settler go away "+settle.getResident().getSettlerMax());
		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);

//		settle.getResident().setSettlerCount(90);
		settle.setSettlerMax();
		BreedingLoop(settle, server, 510);

		makeSettleAnalysis(settle, month, priceList);

//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_HOME, hsRegion, "haus_einfach", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_HOME, hsRegion, "haus_einfach", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_HOME, hsRegion, "haus_einfach", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_BAUERNHOF, hsRegion, "bauern_haus", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);

		settle.setSettlerMax();
		System.out.println(month+"======New Houses "+settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_HOE",500);

		BreedingLoop(settle, server, 110);

		makeSettleAnalysis(settle, month, priceList);

//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "schreiner", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "schreiner", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_BAECKER, hsRegion, "haus_baecker", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "schreiner", true);
		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_PROD, hsRegion, "tischler", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "kornfeld", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_WHEAT, hsRegion, "holzfaeller", true);
//		Settlement.addBuilding(building, settle);
//		hsRegion++;
//		building =  new Building(BuildingType.BUILDING_HOME, hsRegion, "haus_einfach", true);
//		Settlement.addBuilding(building, settle);

		settle.setSettlerMax();
		System.out.println(month+"======New Production "+settle.getResident().getSettlerMax());

		isOutput =   false; //(expected != actual);
		BreedingLoop(settle, server, 1310);
		makeSettleAnalysis(settle, month, priceList);

		
		settle.getResident().setSettlerCount(50);
		settle.setSettlerMax();
		System.out.println(month+"====Now only 50 Settler in Settlement / Homes : "+settle.getResident().getSettlerMax());
		BreedingLoop(settle, server, 510);
		makeSettleAnalysis(settle, month, priceList);
		
		settle.setSettlerMax();
		System.out.println(month+"==Last Run   "+settle.getResident().getSettlerMax());
		isOutput =   false; //(expected != actual);
		BreedingLoop(settle, server, 1310);

		boolean isBuildingList = false;
		if (isBuildingList)
		{
			System.out.println("== Buildings "+settle.getBuildingList().getBuildingList().size());
			for (Building buildg : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println("- "+buildg.getHsRegion()+" : "+buildg.getHsRegionType()+" :W "+buildg.getWorkerInstalled()+" :E "+buildg.isEnabled());
			}
		}
		boolean isWarehouseList = false;
		if (isWarehouseList)
		{
			System.out.println("== Warehouse ["+settle.getWarehouse().getItemCount()+"/"+settle.getWarehouse().getItemMax()+"]");
			for (Item item : settle.getWarehouse().getItemList().values())
			{
				System.out.println("- "+item.ItemRef()+" : "+item.value());
			}
		}
		boolean isCapacityList = false;
		if (isCapacityList)
		{
			System.out.println("== Building Capacity List");
			int usedCapacity = 0;
			for (BuildingType bType : settle.getWarehouse().getTypeCapacityList().keySet())
			{
				System.out.println("- "+bType.name()+" : "+settle.getWarehouse().getTypeCapacity(bType));
				usedCapacity = usedCapacity + settle.getWarehouse().getTypeCapacity(bType);
			}
			System.out.println("== Building Capacity used ["+usedCapacity+"/"+settle.getWarehouse().getItemMax()+"]");
		}
		
		System.out.println(" ");
		System.out.println("== Laufzeit "+month*30+" Tage ");
		makeSettleAnalysis(settle, month, priceList);
		int expected = 138;
		int actual = settle.getResident().getSettlerCount(); 
		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("==Settler Breed : "+settle.getResident().getSettlerCount());
			System.out.println("Warehouse");
			double price = 0.0;
			double balance = 0.0;
			for (Item item : settle.getWarehouse().getItemList().values())
			{
				price = Math.round(priceList.getBasePrice(item.ItemRef()));
				System.out.println(item.ItemRef()+": "+item.value()+" = "+(item.value()*price));
				balance = balance + (item.value()*price);
			}
			System.out.println("Item balance = "+balance);
		}
		
		assertEquals(expected, actual);

	}
	
}
