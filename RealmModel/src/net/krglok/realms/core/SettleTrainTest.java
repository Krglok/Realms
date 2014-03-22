package net.krglok.realms.core;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unittest.DataTest;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettleTrainTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	private boolean isMonth = false;
	private String sb = "";
	private boolean showSettler = true;
	private int dayCounter = 0;
	private int month = 0;
	private int maxResidents= 0;
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
		return sb+"#" +"   "+ settle.getResident().getHappiness();
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
		return sb+"#" +"   "+ settle.getResident().getSettlerCount();
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
			
			if (settle.getResident().getSettlerCount() > maxResidents)
			{
				maxResidents = settle.getResident().getSettlerCount();
			}
			
			sb = ConfigBasis.setStrleft(showBalkenHappy(settle, isMonth,showSettler),20);
			if (isOutput)
			{
				if (isMonth)
				{
					System.out.println(
							ConfigBasis.setStrright(String.valueOf(settle.getAge()),4)+sb
//							+"/S:"+settle.getSettlerFactor()
//							+"/E:"+settle.getEntertainFactor()
							+"|D:"+settle.getResident().getDeathrate()
							+"|B:"+settle.getResident().getBirthrate()
//							+"/Wo:"+settle.getTownhall().getWorkerCount()
//							+"/Wn:"+settle.getTownhall().getWorkerNeeded()
//							+"/M:"+(int)settle.getBank().getKonto()
//							+"/R:"+settle.getRequiredProduction().size()+getReqList(settle)
							+"|Fo:"+format2(settle.getFoodFactor())
							+"|Se:"+format2(settle.getSettlerFactor())
//							+"|fe:"+settle.getResident().getFertilityCounter()
							+"|W:"+settle.getWarehouse().getItemList().getValue("WHEAT")
							);
				} else
				{
//					if ((i % 10) == 0)
					{
						System.out.println(
								ConfigBasis.setStrright(String.valueOf(settle.getAge()),4)+sb
	//							+"/ F:"+settle.getFoodFactor()
	//							+"/S:"+settle.getSettlerFactor()
	//							+"/E:"+settle.getEntertainFactor()
								+"|D:"+settle.getResident().getDeathrate()
								+"|B:"+settle.getResident().getBirthrate()
	//							+"/Wo:"+settle.getTownhall().getWorkerCount()
	//							+"/Wn:"+settle.getTownhall().getWorkerNeeded()
	//							+"/M:"+(int)settle.getBank().getKonto()
//								+"/G:"+settle.getWarehouse().getItemList().getValue("GOLD_NUGGET")
//								+"/R:"+settle.getRequiredProduction().size()+getReqList(settle)
								+"|Fo:"+format2(settle.getFoodFactor())
								+"|Se:"+format2(settle.getSettlerFactor())
//								+"|fe:"+settle.getResident().getFertilityCounter()
								+"|W:"+settle.getWarehouse().getItemList().getValue("WHEAT")
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
		msg.add("Sieldungstatus  ========= ");
		msg.add("Biome         :"+settle.getBiome());
		msg.add("Age           : "+settle.getAge()+" Tage  ca. " + (settle.getAge()/30/12)+" Jahre ");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount()+"  :"+maxResidents+" Maximum");
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
	public void testSettlementTrain()
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
		regionTypes.put("6","HOME");
//		regionTypes.put("7","HOME");
//		regionTypes.put("8","HOME");
//		regionTypes.put("9","HOME");
//		regionTypes.put("10","HOME");
//		regionTypes.put("11","HOME");
//		regionTypes.put("12","HOME");
//		regionTypes.put("13","HOME");
//		regionTypes.put("14","HOME");
		regionTypes.put("65","WHEAT");
		regionTypes.put("66","WHEAT");
		regionTypes.put("67","BAKERY");
		regionTypes.put("68","BAKERY");
		regionTypes.put("69","WHEAT");
		regionTypes.put("70","WHEAT");
		regionTypes.put("71","WHEAT");
		regionTypes.put("72","WHEAT");
		regionTypes.put("80","WOODCUTTER");
		regionTypes.put("90","QUARRY");
		regionTypes.put("100","GUARDHOUSE");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.HAMLET;
		String settleName = "Hochweg";
		
		Settlement settle =  
				Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.HELL
				);

		settle.getWarehouse().depositItemValue("WHEAT",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("BREAD",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("WOOD_HOE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_AXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_PICKAXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("LOG",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue(Material.STICK.name(),200);
		settle.getWarehouse().depositItemValue(Material.WOOD.name(),200);
		settle.getWarehouse().depositItemValue(Material.COBBLESTONE.name(),settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue(Material.STONE_SWORD.name(),100);
		
		settle.getResident().setSettlerCount(5);
		settle.setSettlerMax();
		settle.initTreasureList();
		settle.expandTreasureList(settle.getBiome(), server);
		settle.getWarehouse().setStoreCapacity();

		isOutput =   false; //(expected != actual);

		if (isOutput)
		{
			System.out.println("==Settlement Breed  Start: "+settle.getResident().getSettlerCount());
		}

		BreedingLoop(settle, server, 1500, priceList);

//		BreedingLoop(settle, server, 410, priceList);

		int expected = 1;
		int actual = settle.getResident().getSettlerCount(); 

		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("== Laufzeit "+settle.getAge()+" Tage ");
			makeSettleAnalysis(settle, month, priceList);
			System.out.println(" ");

			boolean isTrainList = true;
			if (isTrainList)
			{
				System.out.println(" ");
				System.out.println("Train balance = ");
				ItemList items = new ItemList();
				for (Building building : settle.getBuildingList().getBuildingList().values())
				{
					if (building.getBuildingType() == BuildPlanType.GUARDHOUSE)
					{
					System.out.println(
							ConfigBasis.setStrleft(building.getBuildingType().name(),13)
							+": "+building.getUnitSpace()
							+": "+building.getTrainType()
							+": "+building.getTrainCounter()
							);
					}
				}
			}
			boolean isBarackList = true;
			if (isBarackList)
			{
				System.out.println("Warehouse");
				double price = 0.0;
				double balance = 0.0;
				for (Item item : settle.getWarehouse().getItemList().values())
				{
					price = Math.round(priceList.getBasePrice(item.ItemRef()));
					System.out.println(
							ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
							ConfigBasis.setStrright(String.valueOf(item.value()),3)+" = "+
							(item.value()*price));
					balance = balance + (item.value()*price);
				}
				System.out.println("Warehouse balance = "+balance);
				System.out.println("=============================");
			}
			boolean isCapacityList = false;
			if (isCapacityList)
			{
				System.out.println("==Barack Capacity ==");
				System.out.print("UnitType         "+" : "+"Stack");
				System.out.println("");
				for (IUnit uItem : settle.getBarrack().get)
				{
					System.out.print(ConfigBasis.setStrleft(uItem.getUnitType().name(),10)
							+" : "+uItem.
					+ " | "
					+""
					System.out.println("");
					
				}
			}
			
		}
		
		assertEquals(expected, actual);

	}

}
