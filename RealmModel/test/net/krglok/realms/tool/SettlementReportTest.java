package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.RegionConfig;
import net.krglok.realms.unittest.ServerTest;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class SettlementReportTest
{

	/**
	 * Count the production buildings in List. 
	 * Every building with needWorker > 0 is for production
	 * 
	 * @param buildingList
	 * @return
	 */
	public int getProdBuildCount(BuildingList buildingList)
	{
		int value = 0;
		for (Building building : buildingList.values())
		{
			if (building.getWorkerNeeded() > 0)
			{
				value++;
			}
		}
		return value;
	}

	/**
	 * 
	 */
	public  ArrayList<RegionConfig> getBuildingTypList( String HeroStrongholdPath )
	{
        String sRegionFile = "";
		RegionConfig region ;
        ArrayList<RegionConfig> regionList = new ArrayList<RegionConfig>();
        
        File regionFolder = new File(HeroStrongholdPath, "RegionConfig");
        if (!regionFolder.exists()) 
        {
        	System.out.println("Folder not found !");
            return null;
        }
        
        for (File RegionFile : regionFolder.listFiles()) 
        {
        	sRegionFile = RegionFile.getName();
        	region= StrongholdTools.getRegionConfig(HeroStrongholdPath+"\\RegionConfig", sRegionFile);
        	regionList.add(region);
        }
        
		return regionList;
	}

	/**
	 * Holt die  regionConfiguration zu dem gesuchten String aus der Liste 
	 * 
	 * @param regionList
	 * @param regionTyp
	 * @return RegionConfig oder null
	 */
	public RegionConfig getRegionConfig(ArrayList<RegionConfig> regionList  , String regionTyp)
	{
		RegionConfig regionConfig = null;
		for (RegionConfig rConfig : regionList)
		{
			if (rConfig.getName().equalsIgnoreCase(regionTyp))
			{
				return rConfig;
			}
		}
		return regionConfig;
	}

	
	public ItemList getRegionOutput(ArrayList<RegionConfig> regionList  ,String regionType)
	{
		ItemList rList = new ItemList();
		for (RegionConfig regionConfig : regionList)
		{
			if (regionConfig.getName().equalsIgnoreCase(regionType))
			{
				for (ItemStack itemStack :regionConfig.getOutput())
				{
					rList.addItem(itemStack.getType().name(), itemStack.getAmount());
				}
			}
		}
		return rList;
	}
	
	
	public ItemList getRegionUpkeep(ArrayList<RegionConfig> regionList  ,String regionType)
	{
		ItemList rList = new ItemList();
		for (RegionConfig regionConfig : regionList)
		{
			if (regionConfig.getName().equalsIgnoreCase(regionType))
			{
				for (ItemStack itemStack :regionConfig.getUpkeep())
				{
					rList.addItem(itemStack.getType().name(), itemStack.getAmount());
				}
			}
		}
		return rList;
	}
	
	
	public int getRegionWorker(ArrayList<RegionConfig> regionList  ,String regionType)
	{
		return Building.getDefaultWorker(BuildPlanType.getBuildPlanType(regionType)); 
	}
	
	/**
	 * Zaehlt die Productions Buildings
	 * 
	 * @param buildingList
	 * @return
	 */
	public void getProdBuildingType(Building building, HashMap<String,Integer> resultType)
	{
		int value = 0;
		for (String bTyp : resultType.keySet())
		{
			if (bTyp.equalsIgnoreCase(building.getBuildingType().name()))
			{
				resultType.put(building.getBuildingType().name(),resultType.get(building.getBuildingType().name())+1);
				return;
			}
		}
		resultType.put(building.getBuildingType().name(),1);		
	}
	

	public ArrayList<String> BuildingReport(SettlementList settleList, ArrayList<RegionConfig> regionList)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	HashMap<String,Integer> resultType = new HashMap<String,Integer>();
    	// Auswertung BuildingList
		for (Settlement settle :  settleList.values())
		{
	    	// headerline
	    	msg.add("==============================================================");
			msg.add("Building Overview ");
			msg.add("id "+String.valueOf(settle.getId())+" | "+settle.getName() +" | "+settle.getBiome()+" | "+settle.getSettleType()
					);
			msg.add("BuildingTyp    "
			+" |"+"Count"
			+" |"+"Worker"
			+" |"+"Product   "
			+" |"+"Amount"
			);
			resultType.clear();
			for (Building building : settle.getBuildingList().values())
			{
				if (building.getWorkerNeeded() > 0)
				{
					getProdBuildingType(building,resultType);
//					RegionConfig regionConfig = getRegionConfig(regionList, building.getBuildingType().name()); 
				
				}
			}
			// zusammensetzen der Datenzeile aus BuildingType, Output items, Items mit Amount*AnzahlBuildings 
			for (String rType :resultType.keySet())
			{
				String output = "";
				ItemList itemList = getRegionOutput(regionList, rType);
				for (String item : itemList.keySet())
				{
					output = output+"|"+ConfigBasis.setStrleft(item, 10)+"|"+ConfigBasis.setStrright(String.valueOf(itemList.getItem(item).value()*resultType.get(rType)),5);
				}
				msg.add(ConfigBasis.setStrleft(rType, 15)
						+" |"+ConfigBasis.setStrright(String.valueOf(resultType.get(rType)),5)
						+" |"+ConfigBasis.setStrright(String.valueOf(getRegionWorker(regionList, rType)),6)
						+output
						+"|"
						);
			}
			msg.add("");
		} 
		
		return msg;
	}
	
	/**
	 *   Es wird eine Liste mit den wichtigsten Kennzahlen des Settlement 
	 *   erstellt. Die Liste wird in eine Stringlist  geschrieben.
	 *   
	 * @return  Ausgabetext  
	 */
	public ArrayList<String> SettlementReport(SettlementList settleList)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	// headerline
    	msg.add("==============================================================");
		msg.add("Settle Overview ");
		msg.add("id "+"|Name         "
		+"|"+"Settle"
		+"|"+"Worker"
		+" |"+"Bank    "
		+" |"+"Stock     "
		+" |"+"Build "
		+" |"+"Prod  "
		+" |"+"Biome"
		+" ");
		// data lines
		for (Settlement settle : settleList.values())
		{
			msg.add(ConfigBasis.setStrleft(String.valueOf(settle.getId()),2)
			+" | "+ConfigBasis.setStrleft(settle.getName(),12)
			+"| "+ConfigBasis.setStrleft(String.valueOf(settle.getResident().getPopulation()),5)
			+"| "+ConfigBasis.setStrleft(String.valueOf(settle.getTownhall().getWorkerNeeded()),5)
			+" | "+ConfigBasis.setStrright(String.valueOf((int)settle.getBank().getKonto()),7)
			+" | "+ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemCount()),9)
			+" | "+ConfigBasis.setStrright(String.valueOf(settle.getBuildingList().size()),5)
			+" | "+ConfigBasis.setStrright(String.valueOf(getProdBuildCount(settle.getBuildingList())),5)
			+" | "+ settle.getBiome()
			+" ");
		}
		
    	return msg;
	}

	public  ArrayList<String> ProductBuildingList( ArrayList<RegionConfig> regionList)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	// headerline
    	msg.add("==============================================================");
		msg.add("Building Create Overview ");
		msg.add("id "+"|Name         "
		+"|"+"Wor"
		+ "|"+"Cost   "
		+" |"+"Upkeep   "
		+" |"+"Anz"
		+" ");

		for (BuildPlanType bType : BuildPlanType.getBuildPlanGroup(200))
		{
			RegionConfig rConfig = getRegionConfig(regionList, bType.name());
			String output = "";
			ItemList itemList = getRegionUpkeep(regionList, bType.name());
			for (String item : itemList.keySet())
			{
				output = output+"|"+ConfigBasis.setStrleft(item, 10)+"|"+ConfigBasis.setStrright(String.valueOf(itemList.getItem(item).value()),3);
			}
			msg.add(ConfigBasis.setStrleft(String.valueOf(bType.getValue()), 3)
					+"|"+ ConfigBasis.setStrleft(bType.name(), 13)
					+"|"+ConfigBasis.setStrright(String.valueOf(getRegionWorker(regionList, bType.name())),3)
					+"|"+ConfigBasis.setStrright(String.valueOf(rConfig.getMoneyRequirement()),8)
					+output
					+"|"
					);
		}
		for (BuildPlanType bType : BuildPlanType.getBuildPlanGroup(300))
		{
			RegionConfig rConfig = getRegionConfig(regionList, bType.name());
			if (rConfig != null)
			{
				String output = "";
				ItemList itemList = getRegionUpkeep(regionList, bType.name());
				for (String item : itemList.keySet())
				{
					output = output+"|"+ConfigBasis.setStrleft(item, 10)+"|"+ConfigBasis.setStrright(String.valueOf(itemList.getItem(item).value()),3);
				}
				msg.add(ConfigBasis.setStrleft(String.valueOf(bType.getValue()), 3)
						+"|"+ ConfigBasis.setStrleft(bType.name(), 13)
						+"|"+ConfigBasis.setStrright(String.valueOf(getRegionWorker(regionList, bType.name())),3)
						+"|"+ConfigBasis.setStrright(String.valueOf(rConfig.getMoneyRequirement()),8)
						+output
						+"|"
						);
			}
		}
		for (BuildPlanType bType : BuildPlanType.getBuildPlanGroup(400))
		{
			RegionConfig rConfig = getRegionConfig(regionList, bType.name());
			if (rConfig != null)
			{
				String output = "";
				ItemList itemList = getRegionUpkeep(regionList, bType.name());
				for (String item : itemList.keySet())
				{
					output = output+"|"+ConfigBasis.setStrleft(item, 10)+"|"+ConfigBasis.setStrright(String.valueOf(itemList.getItem(item).value()),3);
				}
				msg.add(ConfigBasis.setStrleft(String.valueOf(bType.getValue()), 3)
						+"|"+ ConfigBasis.setStrleft(bType.name(), 13)
						+"|"+ConfigBasis.setStrright(String.valueOf(getRegionWorker(regionList, bType.name())),3)
						+"|"+ConfigBasis.setStrright(String.valueOf(rConfig.getMoneyRequirement()),8)
						+output
						+"|"
						);
			}
		}
		return msg;
	}

	/**
	 * Erstellt eine Liste der Item mit Plusfaktor fuer das Biome
	 * 
	 * @param biome
	 * @param materialList
	 * @param server
	 * @return
	 */
	private ItemList getBiomePlus(Biome biome, ItemList materialList, ServerTest server)
	{
		ItemList subList = new ItemList();
		for (Item item : materialList.values())
		{
			int faktor = server.getBioneFactor(biome , Material. getMaterial(item.ItemRef()));
			if (faktor > server.FAKTOR_0)
			{
	    		subList.addItem(item.ItemRef(), faktor);
			}
		}
		return subList;
	}

	private ItemList getBiomePlusList(Biome biome, ServerTest server)
	{
		ItemList plusList = new ItemList();
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initBuildMaterial(), server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initRawMaterial() , server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initOre() , server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initMaterial() , server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initFoodMaterial() , server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initTool() , server));
		plusList.addAll(getBiomePlus(biome, ConfigBasis.initWeapon() , server));
		return plusList;
	}
	
	public ArrayList<String> BiomePlus(SettlementList settleList, ServerTest server)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	
		ItemList plusList = new ItemList();

    	msg.add("==============================================================");
		msg.add("Settle Biome Plus Production ");
		msg.add("id "+"|Name         "
		+"|"+"Biome     "
		+"|"+"item      "
		+"|"+"Fak"
		+" ");
    	for (Settlement settle : settleList.values())
    	{
    		// liste erstellen
    		Biome biome = settle.getBiome();
    		plusList.clear();
    		plusList = getBiomePlusList(biome, server);
    		// Ausgabe formatieren
			String output = "";
	    	for (Item item : plusList.values())
	    	{
	    		output = output+"|"+ConfigBasis.setStrleft(item.ItemRef(),10)+"|"+ConfigBasis.setStrright(item.value(), 3);
	    	}
	    	output=output+"|";
			msg.add(ConfigBasis.setStrleft(String.valueOf(settle.getId()),2)
			+"|"+ConfigBasis.setStrleft(settle.getName(),10)
			+"|"+ConfigBasis.setStrleft(settle.getBiome().name(),10)
			+output
			+" ");
    	}
    	
    	return msg;
	}
	
	/**
	 * Erstellt eine Liste der Item mit Plusfaktor fuer das Biome
	 * 
	 * @param biome
	 * @param materialList
	 * @param server
	 * @return
	 */
	private ItemList getBiomeNormal(Biome biome, ItemList materialList, ServerTest server)
	{
		ItemList subList = new ItemList();
		for (Item item : materialList.values())
		{
			int faktor = server.getBioneFactor(biome , Material. getMaterial(item.ItemRef()));
			if (faktor == server.FAKTOR_0)
			{
	    		subList.addItem(item.ItemRef(), faktor);
			}
		}
		return subList;
	}

	private ItemList getBiomeNormalList(Biome biome, ServerTest server)
	{
		ItemList plusList = new ItemList();
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initBuildMaterial(), server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initRawMaterial() , server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initOre() , server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initMaterial() , server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initFoodMaterial() , server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initTool() , server));
		plusList.addAll(getBiomeNormal(biome, ConfigBasis.initWeapon() , server));
		return plusList;
	}
	
	public ArrayList<String> BiomeNormal(SettlementList settleList, ServerTest server)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	
		ItemList plusList = new ItemList();

    	msg.add("==============================================================");
		msg.add("Settle Biome Normal Production ");
		msg.add("id "+"|Name         "
		+"|"+"Biome     "
		+"|"+"item      "
		+"|"+"Fak"
		+" ");
    	for (Settlement settle : settleList.values())
    	{
    		// liste erstellen
    		Biome biome = settle.getBiome();
    		plusList.clear();
    		plusList = getBiomeNormalList(biome, server);
    		// Ausgabe formatieren
			String output = "";
	    	for (Item item : plusList.values())
	    	{
	    		output = output+"|"+ConfigBasis.setStrleft(item.ItemRef(),10)+"|"+ConfigBasis.setStrright(item.value(), 3);
	    	}
	    	output=output+"|";
			msg.add(ConfigBasis.setStrleft(String.valueOf(settle.getId()),2)
			+"|"+ConfigBasis.setStrleft(settle.getName(),10)
			+"|"+ConfigBasis.setStrleft(settle.getBiome().name(),10)
			+output
			+" ");
    	}
    	
    	return msg;
	}

	/**
	 * Erstellt eine Liste der Item mit Plusfaktor fuer das Biome
	 * 
	 * @param biome
	 * @param materialList
	 * @param server
	 * @return
	 */
	private ItemList getBiomeMinus(Biome biome, ItemList materialList, ServerTest server)
	{
		ItemList subList = new ItemList();
		for (Item item : materialList.values())
		{
			int faktor = server.getBioneFactor(biome , Material. getMaterial(item.ItemRef()));
			if (faktor < server.FAKTOR_0)
			{
	    		subList.addItem(item.ItemRef(), faktor);
			}
		}
		return subList;
	}

	private ItemList getBiomeMinusList(Biome biome, ServerTest server)
	{
		ItemList plusList = new ItemList();
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initBuildMaterial(), server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initRawMaterial() , server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initOre() , server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initMaterial() , server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initFoodMaterial() , server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initTool() , server));
		plusList.addAll(getBiomeMinus(biome, ConfigBasis.initWeapon() , server));
		return plusList;
	}
	
	
	public ArrayList<String> BiomeMinus(SettlementList settleList, ServerTest server)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	
		ItemList plusList = new ItemList();

    	msg.add("==============================================================");
		msg.add("Settle Biome Minus Production ");
		msg.add("id "+"|Name     "
		+"|"+"Biome     "
		+"|"+"item      "
		+"|"+"Fak"
		+" ");
    	for (Settlement settle : settleList.values())
    	{
    		plusList.clear();
    		// liste erstellen
    		Biome biome = settle.getBiome();
    		// Ausgabe formatieren
			String output = "";
	    	for (Item item : plusList.values())
	    	{
	    		output = output+"|"+ConfigBasis.setStrleft(item.ItemRef(),10)+"|"+ConfigBasis.setStrright(item.value(), 3);
	    	}
	    	output=output+"|";
			msg.add(ConfigBasis.setStrleft(String.valueOf(settle.getId()),2)
			+"|"+ConfigBasis.setStrleft(settle.getName(),10)
			+"|"+ConfigBasis.setStrleft(settle.getBiome().name(),10)
			+output
			+" ");
    	}
    	
    	return msg;
	}
	
	
	private String getProductBuilding(String mat, ArrayList<RegionConfig> regionList)
	{
		for (RegionConfig rConfig : regionList)
		{
			ItemList itemList = getRegionOutput(regionList, rConfig.getName());
			for (Item item : itemList.values())
			{
				if (item.ItemRef().equalsIgnoreCase(mat))
				{
					return rConfig.getName();
				}
			}
		}
		return "";
	}
	
	private RegionConfig getRegionType(String bType, ArrayList<RegionConfig> regionList)
	{
		for (RegionConfig rConfig : regionList)
		{
		   if (rConfig.getName().equalsIgnoreCase(bType))
		   {
			   return rConfig;
		   }
		}
		return null;
	}
	
	/**
	 * Pruefe ob die Resourcen zum bauen des BuildingType vorhanden sind.
	 * Es wird negative selektion verwendet. Eine nicht erfuellte Bedingung 
	 * fuehrt zu negativen Result
	 * Es werden keine Aktionen ausgeloest.
	 * 
	 * @param settle
	 * @param bType true = Resourcen vorhanden 
	 */
	private boolean checkBuildingResources(Settlement settle, String bType, ArrayList<RegionConfig> regionList)
	{
		Boolean result = true;
		RegionConfig rConfig = getRegionType(bType, regionList);
		// pruefe Money
		if (settle.getBank().getKonto() < rConfig.getMoneyRequirement()) { result = false; }
		// erzeuge required ItemList
		ItemList required = rConfig.getRequirementsItem();
		if (settle.getWarehouse().searchItemsNotInWarehouse(required).size() > 0) { result = false; }
		// Pruefe Reagents
		ItemList reagents = rConfig.getReagentsItem();
		if (settle.getWarehouse().searchItemsNotInWarehouse(reagents).size() > 0) { result = false; }
		
		return result;
	}
	
	/**
	 * vereinfachte Upkeep kontrolle ueber den aktuellen Lagerbestand.
	 * Richtiger waere eien kontrolle ueber die Productionmenge
	 * 
	 * @param settle
	 * @param bType
	 * @param regionList
	 * @return treu = upkeep vorhanden
	 */
	private boolean checkUpkeep(Settlement settle, String bType, ArrayList<RegionConfig> regionList)
	{
		Boolean result = true;
		RegionConfig rConfig = getRegionType(bType, regionList);
		// upkeep material
		ItemList upkeep = rConfig.getUpkeepItem();
		if (settle.getWarehouse().searchItemsNotInWarehouse(upkeep).size() > 0) { result = false; }
		return result;
	}
	
	
	
	public ArrayList<String> getStartWertBuilding(SettlementList settleList, ArrayList<RegionConfig> regionList, ServerTest server)
	{
    	ArrayList<String> msg = new ArrayList<String>();

    	ItemList plusList = new ItemList();
		int maxPlus = 0;
		int start = 0;
		String bType ="";

    	msg.add("==============================================================");
		msg.add("Settle Biome Start Production ");
		msg.add("id "+"|Name     "
		+"|"+"Biome     "
		+"|"+"Max"
		+"|"+"Start"
		+"|"+"Item      "
		+"|"+"Building  "
		+"|"+"Create"
		+"|"+"Upkeep"
		+" ");
		
		for (Settlement settle : settleList.values())
    	{
			bType ="";
    		Biome biome = settle.getBiome();
    		plusList = getBiomePlusList(biome, server);
    		maxPlus = plusList.size();
    		start = 0;
    		int ref   = settle.getId(); 
    		String output = "";
    		Boolean hasResources = false;
    		Boolean hasUpkeep = false;
			if (maxPlus > 0)
			{
	    		if (ref > maxPlus)
	    		{
		    		if ((ref % maxPlus) != 0)
		    		{
		    			start = ref - maxPlus *(ref / maxPlus);
		    			output = plusList.keySet().toArray()[start].toString();
		    		} else
		    		{
		    				start = 0; //99; //ref-1;
		        			output = plusList.keySet().toArray()[start].toString();
		    		}
	    		} else
	    		{
	    				start = ref-1;
	        			output = plusList.keySet().toArray()[start].toString();
	    		}
	    		bType = getProductBuilding(output, regionList);
	    		if (bType != "")
	    		{
	    			hasResources= checkBuildingResources(settle, bType, regionList);
	    			hasUpkeep = checkUpkeep(settle, bType, regionList);
	    		}
    	    }
    		msg.add(ConfigBasis.setStrleft(String.valueOf(settle.getId()),2)
    				+"|"+ConfigBasis.setStrleft(settle.getName(),10)
    				+"|"+ConfigBasis.setStrleft(settle.getBiome().name(),10)
    				+"|"+ConfigBasis.setStrright(maxPlus, 3)
    				+"|"+ConfigBasis.setStrright(start, 5)
    				+"|"+ConfigBasis.setStrleft(output,10)
    				+"|"+ConfigBasis.setStrleft(bType,10)
    				+"|"+ConfigBasis.setStrleft(hasResources.toString(),6)
    				+"|"+ConfigBasis.setStrleft(hasUpkeep.toString(),6)
    				+" ");
    	}
    	
		
		return msg;
	}
	
	/**
	 * Ausgabe einer Stringliste zur Console
	 * Die String mueessen bereits formatiert sein
	 * 
	 * @param msg
	 */
	public void printConsole(ArrayList<String> msg)
	{
		for (int i = 0; i < msg.size(); i++)
		{
			System.out.println(msg.get(i));
		}
	}

	@Test
	public void testGesamt()
	{
		String strongholdPath = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
        ArrayList<RegionConfig> regionList = getBuildingTypList( strongholdPath);
        
		// Initialize DataStorage
		ConfigTest config = new ConfigTest();
		// Data Path set as constant in DataTest
		DataTest data     = new DataTest();
        // Server Interface
        ServerTest server = new ServerTest(data);
		// Output Stringlist
		ArrayList<String> msg = new ArrayList<String>();
		
		SettlementList settleList = data.getSettlements();
		// Settlement Overview
    	msg = SettlementReport(settleList);
    	printConsole(msg);
    	// BuildingReport 
//    	msg = BuildingReport(settleList, regionList);
//    	printConsole(msg);
    	// Building Produkt Overview
    	msg = ProductBuildingList( regionList);
    	printConsole(msg);
    	// BiomePlusList
    	msg = BiomePlus(settleList, server);
    	printConsole(msg);
    	// BiomePlusList
//    	msg = BiomeMinus(settleList, server);
//    	printConsole(msg);
    	// getStratwert
    	msg = getStartWertBuilding(settleList, regionList, server);
    	printConsole(msg);

    	
	}

}
