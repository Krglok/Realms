package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.ServerTest;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettlementBreedTest
{
	private Boolean isOutput = false; // set this to false to suppress println
	private boolean isMonth = false;
	private String sb = "";
	private boolean showSettler = true;
	private int dayCounter = 0;
	private int month = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	private double format2(double value)
	{
		int value100 = (int)(value * 100);
		return ((double)value100/100.0);
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
	
	private void BreedingLoop(
			Settlement settle,
			ServerTest server,
			int MaxLoop, 
			ItemPriceList priceList
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
				settle.doCalcTax();
				dayCounter = 0;
			}
			
			showSettler = true;	
			isMonth = false;
			
			if ((i %30) == 0)
			{
				isMonth = true;
				month++;
			} 
			if ((settle.getAge() % 360) == 0)
			{
//				makeSettleAnalysis(settle, month, priceList);
			}
			
			sb = ConfigBasis.setStrleft(showBalkenHappy(settle, isMonth,showSettler),17);
			if (isOutput)
			{
				if (isMonth)
				{
					System.out.println(
							ConfigBasis.setStrright(String.valueOf(month),3)+sb
//							+"/S:"+settle.getSettlerFactor()
//							+"/E:"+settle.getEntertainFactor()
							+"/D:"+settle.getResident().getDeathrate()
							+"/B:"+settle.getResident().getBirthrate()
//							+"/Wo:"+settle.getTownhall().getWorkerCount()
//							+"/Wn:"+settle.getTownhall().getWorkerNeeded()
//							+"/M:"+(int)settle.getBank().getKonto()
//							+"/W:"+settle.getWarehouse().getItemList().getValue("WHEAT")
							+"/R:"+settle.getRequiredProduction().size()+getReqList(settle)
							+"/F:"+format2(settle.getFoodFactor())
							+"/S:"+format2(settle.getSettlerFactor())
							+"/f:"+settle.getResident().getFertilityCounter()
							);
				} else
				{
					if ((i % 10) == 0)
					{
						System.out.println(
								ConfigBasis.setStrright(String.valueOf(month),3)+sb
	//							+"/ F:"+settle.getFoodFactor()
	//							+"/S:"+settle.getSettlerFactor()
	//							+"/E:"+settle.getEntertainFactor()
								+"/D:"+settle.getResident().getDeathrate()
								+"/B:"+settle.getResident().getBirthrate()
	//							+"/Wo:"+settle.getTownhall().getWorkerCount()
	//							+"/Wn:"+settle.getTownhall().getWorkerNeeded()
	//							+"/M:"+(int)settle.getBank().getKonto()
//								+"/G:"+settle.getWarehouse().getItemList().getValue("GOLD_NUGGET")
//								+"/W:"+settle.getWarehouse().getItemList().getValue("WHEAT")
								+"/R:"+settle.getRequiredProduction().size()+getReqList(settle)
								+"/F:"+format2(settle.getFoodFactor())
								+"/S:"+format2(settle.getSettlerFactor())
								+"/f:"+settle.getResident().getFertilityCounter()
								);
					}
				}
			}
		}
		
	}

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
	
	
	@Test
	public void testSettlementSettlerBreed()
	{
		
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ItemPriceList priceList = readPriceData(); 
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
//        ItemPriceList itemPrices = testPriceList();
		
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
//		regionTypes.put("62","taverne");
		regionTypes.put("65","WHEAT");
//		regionTypes.put("66","WHEAT");
//		regionTypes.put("69","markt");
		regionTypes.put("69","WOODCUTTER");
//		regionTypes.put("68","CARPENTER");
//		regionTypes.put("31","bauern_haus");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle =  
				Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

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
		settle.initTreasureList();
		settle.expandTreasureList(settle.getBiome(), server);
		settle.getWarehouse().setStoreCapacity();

		isOutput =   true; //(expected != actual);
//		String reqI = ""; 
		System.out.println("==Settlement Breed  : "+settle.getResident().getSettlerMax());
		BreedingLoop(settle, server, 10, priceList);
		
//		makeSettleAnalysis(settle, month, priceList);
		

		int hsRegion = 28;
		Building building;
		
		System.out.println(month+"=====Settler go away "+settle.getResident().getSettlerMax());
		hsRegion++;

//		settle.getResident().setSettlerCount(90);
		settle.setSettlerMax();
		BreedingLoop(settle, server, 510, priceList);

//		makeSettleAnalysis(settle, month, priceList);


		settle.setSettlerMax();
		System.out.println(month+"======New Houses "+settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_HOE",500);

		BreedingLoop(settle, server, 1110, priceList);

		hsRegion++;
		building =  new Building(BuildPlanType.WHEAT, hsRegion, "WHEAT", true,null);
		Settlement.addBuilding(building, settle);

		settle.setSettlerMax();
		System.out.println(month+"======New Production "+settle.getResident().getSettlerMax());

		isOutput =   true; //(expected != actual);
		BreedingLoop(settle, server, 1310, priceList);
//		makeSettleAnalysis(settle, month, priceList);

		
		settle.getResident().setSettlerCount(150);
		System.out.println(month+"====Now only 50 Settler in Settlement / Homes : "+settle.getResident().getSettlerCount());
		settle.setSettlerMax();
		BreedingLoop(settle, server, 1510, priceList);
//		makeSettleAnalysis(settle, month, priceList);
		
		settle.setSettlerMax();
		System.out.println(month+"==Last Run   "+settle.getResident().getSettlerMax());
		isOutput =   true; //(expected != actual);
		BreedingLoop(settle, server, 1310, priceList);

		
		System.out.println(" ");
		System.out.println("== Laufzeit "+month*30+" Tage ");
		makeSettleAnalysis(settle, month, priceList);
		int expected = 138;
		int actual = settle.getResident().getSettlerCount(); 
		isOutput = false; // (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("==Store Capacity ==");
			System.out.print("Item            "+" : "+"Stack");
			System.out.println("");
			for (String bItem : settle.getWarehouse().getTypeCapacityList().keySet())
			{
				System.out.print(ConfigBasis.setStrleft(bItem,16)+" : ");
				System.out.print(settle.getWarehouse().getTypeCapacityList().get(bItem).value()+ " | ");
				System.out.print("");
				System.out.println("");
				
			}
			System.out.println(" ");
//			System.out.println("==Settler Breed : "+settle.getResident().getSettlerCount());
			System.out.println("Warehouse");
			double price = 0.0;
			double balance = 0.0;
			for (Item item : settle.getWarehouse().getItemList().values())
			{
//				price = Math.round(priceList.getBasePrice(item.ItemRef()));
				price = priceList.getBasePrice(item.ItemRef());
				System.out.println(
						ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
						ConfigBasis.setStrright(String.valueOf(item.value()),7)+" = "+
						Math.round((item.value()*price)));
				balance = balance + (item.value()*price);
			}
			System.out.println("Item balance = "+Math.round(balance));
			System.out.println("=============================");
			System.out.println("Treasure balance = "+settle.getBiome());
			
			ItemList items = new ItemList();
			for (Item item : settle.getTreasureList())
			{
				items.addItem( new Item(item.ItemRef(),settle.getWarehouse().getItemList().getValue(item.ItemRef())));
			}
			for (Item item : items.values())
			{
				
//				price = Math.round(priceList.getBasePrice(item.ItemRef()));
				price = priceList.getBasePrice(item.ItemRef());
				System.out.println(
						ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
						item.value()
						);
				balance = balance + (item.value()*price);
			}
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
				int usedCapacity = settle.getWarehouse().getUsedCapacity();
				System.out.println("== Building Capacity used ["+usedCapacity+"/"+settle.getWarehouse().getItemMax()+"]");
			}
			
		}
		
		assertEquals(expected, actual);

	}
	
}
