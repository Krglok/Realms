package net.krglok.realms.tool.BreedTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.LogList;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.MessageTest;
import net.krglok.realms.unittest.ServerTest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettlementBreedTest
{
	private Boolean isOutput = false; // set this to false to suppress println
	private boolean isMonth = false;
	private String sb = "";
//	private boolean showSettler = true;
	int dayCounter = 0;
	private int month = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);
	private ServerTest server;
	private DataTest     data;
	private ConfigTest config;
	private MessageTest   msg;
	Settlement settle;
	RealmModel rModel;
	
	public SettlementBreedTest()
	{
		server = new ServerTest();
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest(logTest);
		config = new ConfigTest();
		msg = new MessageTest();
		rModel = new RealmModel(0, 0, server, config, testData, msg, logTest);
    	rModel.OnEnable();
		settle = rModel.getSettlements().getSettlement(1);
		
	}
	
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


	@SuppressWarnings("unused")
	private String showBalkenHappy(Settlement settle, boolean isDay, boolean isSettler)
	{
		if (isSettler)
		{
			return showBalkenSettler(settle, isDay);
		}
//		int rs = settle.getResident().getSettlerCount();
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
	
	/**
	 * make a normal Breed for ALL Settlements
	 * call OnTick 
	 * @param days
	 */
	public void doBreeding()
	{
			dayCounter++;
			rModel.OnTick();
			if ((dayCounter % 40) == 0)
			{
				rModel.OnProduction("SteamHaven");

			}
	}
	
	/**
	 * make a normal Production for the given Settlement
	 * is equivalent to 1 day
	 * @param settle
	 * @param server
	 * @param MaxLoop
	 * @param priceList
	 */
	public void BreedingLoop(
			Settlement settle
			)
	{

			dayCounter++;
			settle.setSettlerMax();
			settle.checkBuildingsEnabled(server);
			settle.setWorkerNeeded();
			settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
			settle.setHappiness();
			settle.doProduce(server);
			if ((dayCounter % 30) == 0)
			{
				settle.doCalcTax();
			}
			
	}

	@SuppressWarnings("unused")
	private void makeSettleAnalysis(Settlement settle, int moth, ItemPriceList priceList)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Sieldungstatus  ========= "+settle.getBiome());
		msg.add("Age           : "+settle.getAge()+" Tage  ca. " + (settle.getAge()/30/12)+" Jahre ");
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
		
		System.out.println("==ProductionOverview ==");
		System.out.print("Item            "+" : "+"   Last"+" | "+"  Monat"+" | "+"   Jahr"+"  Store");
		System.out.println("");
		for (BoardItem bItem : settle.getProductionOverview().values())
		{
			System.out.print(ConfigBasis.setStrleft(bItem.getName(),16)+" : ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getLastValue()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getCycleSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getPeriodSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemList().getValue(bItem.getName()) ) ,7)+ " | ");
			System.out.print("");
			System.out.println("");
		}
	}
	
	

	/**
	 * make a short start Loop of 10 Ticks
	 * @param settleId
	 */
	public void SettlerBreedInit(int settleId)
	{
//		System.out.println("==Settlement Breed  : "+settle.getResident().getSettlerMax());
//		BreedingLoop(settle, 10);
//
//		return;
//	}
//		
//
//	public void testSettlementSettlerBreed(int settleId, Biome biome)
//	{
//		int hsRegion = 28;
//		Building building;
//
//		isOutput =   true; //(expected != actual);
//		ItemPriceList priceList = rModel.getData().getPriceList();
//		settle = rModel.getSettlements().getSettlement(settleId);
//		settle.setBiome(biome);
//		System.out.println(month+"=====Settler go away "+settle.getResident().getSettlerMax());
//		hsRegion++;
//
//		settle.setSettlerMax();
//		BreedingLoop(settle,  510);
//
//
//		settle.setSettlerMax();
//		System.out.println(month+"======New Houses "+settle.getResident().getSettlerMax());
//		settle.getWarehouse().depositItemValue("WOOD_HOE",500);
//
//		BreedingLoop(settle, 1110);
//
//		hsRegion++;
//		building =  new Building(BuildPlanType.WHEAT, hsRegion, "WHEAT", true,null);
//		Settlement.addBuilding(building, settle);
//
//		settle.setSettlerMax();
//		System.out.println(month+"======New Production "+settle.getResident().getSettlerMax());
//
//		isOutput =   true; //(expected != actual);
//		BreedingLoop(settle, 1310);
//
//		
//		settle.getResident().setSettlerCount(150);
//		System.out.println(month+"====Now only 50 Settler in Settlement / Homes : "+settle.getResident().getSettlerCount());
//		settle.setSettlerMax();
//		BreedingLoop(settle, 1510);
//		
//		settle.setSettlerMax();
//		System.out.println(month+"==Last Run   "+settle.getResident().getSettlerMax());
//		isOutput =   true; //(expected != actual);
//		BreedingLoop(settle, 1310);
//
//		
//		System.out.println(" ");
//		System.out.println("== Laufzeit "+month*30+" Tage ");
//		makeSettleAnalysis(settle, month, priceList);
//		isOutput = false; // (expected != actual);
//		if (isOutput)
//		{
//			System.out.println(" ");
//			System.out.println("==Store Capacity ==");
//			System.out.print("Item            "+" : "+"Stack");
//			System.out.println("");
//			for (String bItem : settle.getWarehouse().getTypeCapacityList().keySet())
//			{
//				System.out.print(ConfigBasis.setStrleft(bItem,16)+" : ");
//				System.out.print(settle.getWarehouse().getTypeCapacityList().get(bItem).value()+ " | ");
//				System.out.print("");
//				System.out.println("");
//				
//			}
//			System.out.println(" ");
//			System.out.println("Warehouse");
//			double price = 0.0;
//			double balance = 0.0;
//			for (Item item : settle.getWarehouse().getItemList().values())
//			{
////				price = Math.round(priceList.getBasePrice(item.ItemRef()));
//				price = priceList.getBasePrice(item.ItemRef());
//				System.out.println(
//						ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
//						ConfigBasis.setStrright(String.valueOf(item.value()),7)+" = "+
//						Math.round((item.value()*price)));
//				balance = balance + (item.value()*price);
//			}
//			System.out.println("Item balance = "+Math.round(balance));
//			System.out.println("=============================");
//			System.out.println("Treasure balance = "+settle.getBiome());
//			
//			ItemList items = new ItemList();
//			for (Item item : settle.getTreasureList())
//			{
//				items.addItem( new Item(item.ItemRef(),settle.getWarehouse().getItemList().getValue(item.ItemRef())));
//			}
//			for (Item item : items.values())
//			{
//				
////				price = Math.round(priceList.getBasePrice(item.ItemRef()));
//				price = priceList.getBasePrice(item.ItemRef());
//				System.out.println(
//						ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
//						item.value()
//						);
//				balance = balance + (item.value()*price);
//			}
//			boolean isBuildingList = false;
//			if (isBuildingList)
//			{
//				System.out.println("== Buildings "+settle.getBuildingList().getBuildingList().size());
//				for (Building buildg : settle.getBuildingList().getBuildingList().values())
//				{
//					System.out.println("- "+buildg.getHsRegion()+" : "+buildg.getHsRegionType()+" :W "+buildg.getWorkerInstalled()+" :E "+buildg.isEnabled());
//				}
//			}
//			boolean isWarehouseList = false;
//			if (isWarehouseList)
//			{
//				System.out.println("== Warehouse ["+settle.getWarehouse().getItemCount()+"/"+settle.getWarehouse().getItemMax()+"]");
//				for (Item item : settle.getWarehouse().getItemList().values())
//				{
//					System.out.println("- "+item.ItemRef()+" : "+item.value());
//				}
//			}
//			boolean isCapacityList = false;
//			if (isCapacityList)
//			{
//				System.out.println("== Building Capacity List");
//				int usedCapacity = settle.getWarehouse().getUsedCapacity();
//				System.out.println("== Building Capacity used ["+usedCapacity+"/"+settle.getWarehouse().getItemMax()+"]");
//			}
//			
//		}
		
	}
	
}
