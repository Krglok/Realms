package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.ItemPrice;
import net.krglok.realms.Common.ItemPriceList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.DataStoreSettlement;
import net.krglok.realms.data.SettlementData;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.tool.LogList;
import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class LehenTrainTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	private boolean isMonth = false;
	private String sb = "";
	private boolean showSettler = true;
	private int dayCounter = 0;
	private int month = 0;
	private int maxResidents= 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
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
			Lehen lehen,
			ServerTest server,
			int MaxLoop, 
			ItemPriceList priceList
			)
	{
		UnitFactory unitFactory = new UnitFactory();
		for (int i = 0; i < MaxLoop; i++)
		{
			dayCounter++;
			if (dayCounter > 6)
			{
				dayCounter = 0;
			}
			lehen.doResident(data);
			lehen.doProduce(server,data,dayCounter);
			lehen.doUnitTrain(unitFactory,data);
			
			showSettler = true;	
			isMonth = false;

			System.out.println("STEP========");

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
		msg.add("Power         : "+ settle.getPower());
		msg.add("Anzahl Units  : "+settle.getBarrack().getUnitList().size()+ ":"+settle.getBarrack().getUnitMax());
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
//		if (settle.getFoodFactor() < 0.0)
//		{
//			msg.add("!  Ihre Siedler leiden Hunger. Das ist wohl der Grund warum sie unglücklich sind!");
//		}
//		if (settle.getSettlerFactor() < 0.0)
//		{
//			msg.add("!  Ihre Siedler haben keinen Wohnraum. Das ist wohl der Grund warum sie unglücklich sind!");
//		}
//		if (settle.getEntertainFactor() < 0.9)
//		{
//			msg.add("!  Ihre Siedler haben wenig Unterhaltung. Etwas mehr Unterhaltung macht sie glücklicher!");
//		}
//		if ((settle.getFoodFactor() < 0.0) && (settle.getResident().getSettlerCount() < 8))
//		{
//			msg.add("!  Ihre Siedler sind verhungert. Sie haben als Verwalter versagt!");
//			msg.add("!  Es würde mich nicht wundern, wenn eine Revolte ausbricht!!");
//		}

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
	
	private  void printProduction(AbstractSettle settle)
	{
		System.out.println("==ProductionOverview ==");
		System.out.print("Item            "+" : "+"   Last"+" | "+"  Monat"+" | "+"   Jahr"+"  Store");
		System.out.println("");
		for (BoardItem bItem : settle.getProductionOverview().values())
		{
			System.out.print(ConfigBasis.setStrleft(bItem.getName(),16)+" : ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getInputValue()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getInputSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(bItem.getPeriodSum()) ,7)+ " | ");
			System.out.print(ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemList().getValue(bItem.getName()) ) ,7)+ " | ");
			System.out.print("");
			System.out.println("");
			
		}
	}
	

	private void printWarehouseBalance(AbstractSettle settle,ItemPriceList priceList)
	{
			System.out.println("Warehouse" );
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
	
	private void printTrainBalance(AbstractSettle settle, UnitFactory unitFactory)
	{
		System.out.println(" ");
		System.out.println("Train balance = ");
		ItemList items = new ItemList();
		for (Building building : settle.getBuildingList().values())
		{
			if (building.getBuildingType() == BuildPlanType.GUARDHOUSE)
			{
				IUnit iUnit = unitFactory.erzeugeUnitConfig(building.getTrainType(),new NpcData());
			System.out.println(
					ConfigBasis.setStrright(building.getId(),3)
					+": "+ConfigBasis.setStrleft(building.getBuildingType().name(),13)
					+": "+building.getUnitSpace()
					+": "+building.getTrainType()
					+": "+building.getTrainCounter()
					+": "+building.getTrainTime()
					+"| "+BuildPlanType.getBuildGroup(building.getBuildingType())
			+"| "+building.getMaxTrain()
			);
			}
		}
	}

	private void printBarrackBalance(AbstractSettle settle)
	{
		System.out.println("==Barack Capacity ==");
		System.out.print("UnitType  "+" :"+"Health"+" : "+"Power");
		System.out.println("");
		for (NpcData unit : settle.getBarrack().getUnitList().getUnitTypeList(UnitType.MILITIA))
		{
			System.out.print(ConfigBasis.setStrleft(unit.getUnitType().name(),10)
					+" : "+unit.getHealth()
					+"    : "+unit.getPower()
			);
		
			System.out.println("");
			
		}
		
	}
	
	@Test
	public void testLehenTrain()
	{
		
		LogList logList = new LogList(dataFolder);
		data.initData();
		OwnerList ownerList =  data.getOwners();
		UnitFactory unitFactory = new UnitFactory();
		ItemPriceList priceList = readPriceData(); 
		
		ConfigTest config = new ConfigTest();
		int lehenId = 2;
		Settlement settleS = data.getSettlements().getSettlement(1);
		// Settlement zum support fuer lehen machen
		settleS.setTributId(lehenId);
		Lehen lehen = data.getLehen().getLehen(lehenId);
		// Building in Lehen verschieben
		Building building = data.getBuildings().getBuilding(26);
		building.setSettleId(0);
		building.setLehenId(lehenId);
		lehen.getBuildingList().addBuilding(building);
		
		lehen.getBuildingList().getBuilding(26).addMaxTrain(1);
		lehen.getBuildingList().getBuilding(26).setIsEnabled(true);
		
		lehen.getWarehouse().depositItemValue("LEATHER_HELMET", 1);
		lehen.getWarehouse().depositItemValue("COBBLESTONE",85);
		lehen.getWarehouse().depositItemValue("LEATHER_BOOTS",1);
		lehen.getWarehouse().depositItemValue("LOG",378);
		lehen.getWarehouse().depositItemValue("LEATHER_LEGGINGS",1);
		lehen.getWarehouse().depositItemValue("STONE_SWORD",1);
		lehen.getWarehouse().depositItemValue("WHEAT",152);
		lehen.getWarehouse().depositItemValue("LEATHER_CHESTPLATE",1);
		
		lehen.getBank().depositKonto(10000.0, "Test", 1);

		NpcData npc = data.getNpcs().get(1);
		npc.setNpcType(NPCType.SETTLER);
		npc.setNoble(NobleLevel.COMMONER);
		npc.setUnitType(UnitType.SETTLER);
		npc.setSettleId(0);
		npc.setLehenId(lehenId);
		lehen.getResident().setNpcList(data.getNpcs().getSubListLehen(lehenId));
		
		isOutput =   false; //(expected != actual);

		int loopCount = 12;
		System.out.println("START LOOP =======================");

		BreedingLoop(lehen, server, loopCount, priceList);

//		BreedingLoop(settle, server, 410, priceList);

		int expected = 1;
		int actual = lehen.getResident().getSettlerCount(); 
		System.out.println("END LOOP =="+loopCount);

		isOutput = (expected != actual);
		if (isOutput)
		{
			System.out.println(" ");
			System.out.println("== Laufzeit "+lehen.getAge()+" Tage ");
			boolean isAnalysis = true;
			if (isAnalysis)
			{
//				makeSettleAnalysis(settle, month, priceList);
			}
			boolean isProduction= false;
			if (isProduction)
			{
				printProduction(lehen);
			}
			System.out.println(" ");

			boolean isTrainList = true;
			if (isTrainList)
			{
				printTrainBalance(lehen,unitFactory);			
			}
			boolean isWarehouseList = false;
			if (isWarehouseList)
			{
				printWarehouseBalance(lehen,priceList);
			}
			boolean isCapacityList = true;
			if (isCapacityList)
			{
				printBarrackBalance(lehen);
			}
			
		}
		
		assertEquals(expected, actual);

	}

}
