package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.data.LogList;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class BuildPlanTools
{

	private void showReagentList( 
			HashMap<String,Integer> reagent, 
			String[] sList,
			DataTest  data
			)
	{
		String reagents = "";
//        String[] outLines ;
        ItemList summe = new ItemList();

        // REAGENTS LIST
        System.out.println(" ");
        for (String sName : reagent.keySet())
        {
        	reagents = reagents +""+sName+" ";
        	summe.put(sName, new Item(sName,0));
        }
        System.out.println(StrongholdTools.setStrleft(" ",15)+reagents);
        for (String sRegionFile : sList) 
        {
	            for (String itemRef : reagent.keySet())
	            {
	            	reagent.put(itemRef, 0);
	            }
	            reagents = "";
        		BuildPlanType bType = BuildPlanType.getBuildPlanType(sRegionFile);
        		BuildPlanMap buildPLan = data.readTMXBuildPlan(bType, 4, -1);
	            ItemList matItems = BuildManager.makeMaterialList(buildPLan);
	            for (Item item : matItems.values())
	            {
	            	reagent.put(item.ItemRef(), item.value());
	            }
		        for (String sName : reagent.keySet())
		        {
		        	int value = reagent.get(sName);
		        	summe.putItem(sName, value);
		        	String sValue = String.valueOf(value);
		        	for (int i = 0; i < (sName.length()-1); i++)
					{
//						sValue = sValue + " ";
						sValue = StrongholdTools.setStrleft(sValue,sName.length());
					}
		        	reagents = reagents +""+sValue+" ";
		        }
		        System.out.println(StrongholdTools.setStrleft(sRegionFile.replace(".yml", ""),15) + reagents );
		        
        }
        reagents = "";
        for (String sName : summe.keySet())
        {
        	int value = summe.getValue(sName);
        	summe.putItem(sName, value);
        	String sValue = String.valueOf(value);
        	for (int i = 0; i < (sName.length()-1); i++)
			{
//				sValue = sValue + " ";
				sValue = StrongholdTools.setStrleft(sValue,sName.length());
			}
        	reagents = reagents +""+sValue+" ";
        }
        System.out.println(StrongholdTools.setStrleft( "  Summe : ",15) + reagents );
		
	}
	


	
	private boolean isInList(String name, String[] sList)
	{
		for (int i = 0; i < sList.length; i++)
		{
			if (name.contains(sList[i]))
			{
				return true;
			}
		}
		return false;
	}

	private String[] setStandardList()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"WHEAT",
        		"WOODCUTTER",
        		"QUARRY",
        		};
	}

	private String[] setNetherList()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"MUSHROOM",
        		"KITCHEN",
        		"NETHERQUARRY"
        		};
	}

	private String[] setBasisList()
	{
		return new String[] 
        		{
        		"FARMHOUSE",
        		"SHEPHERD",
        		"CARPENTER",
        		"CABINETMAKER",
        		"BAKERY"
        		};
	}

	@SuppressWarnings("unused")
	private String[] setErweitertList()
	{
		return new String[] 
        		{
        		"BRICKWORK",
        		"CHARBURNER",
				"AXESHOP",
        		"HOESHOP", 
        		"PICKAXESHOP", 
        		"KNIFESHOP",
        		"SPADESHOP",
        		"COWSHED",
        		"CHICKENHOUSE",
        		"FISHERHOOD"
        		};
	}
	

	@SuppressWarnings("unused")
	private String[] setEnhancedList()
	{
		return new String[] 
        		{
				"WORKSHOP",
				"STONEMINE",
				"SMELTER",
				"FARM",
				"PIGPEN",
        		"NETHER_BRICKWORK",
				"WARHOUSE",
				"TAVERNE"
        		};
	}

	private String[] setEbene1()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"WHEAT",
        		"WOODCUTTER",
        		"QUARRY",
        		"FARMHOUSE",
        		"SHEPHERD",
        		"CARPENTER",
        		"CABINETMAKER",
        		"BAKERY",
        		"BRICKWORK",
        		"CHARBURNER",
				"AXESHOP",
        		"HOESHOP", 
        		"PICKAXESHOP", 
        		"KNIFESHOP",
        		"SPADESHOP",
        		"COWSHED",
        		"CHICKENHOUSE",
        		"FISHERHOOD",
				"WORKSHOP",
				"STONEMINE",
				"SMELTER",
//				"FARM",
				"PIGPEN",
        		"NETHER_BRICKWORK",
				"WARHOUSE",
//				"TAVERNE"
        		
        		};
		
	}


	@Test
	public void getBuildPlanConstructionMaterial()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest(logTest);

		path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\buildplan";
		BuildPlanMap buildPLan;

		HashMap<String,Integer> required = new HashMap<String,Integer>();
        HashMap<String,Integer> reagent = new HashMap<String,Integer>();
        HashMap<String,Integer> superRef = new HashMap<String,Integer>();
        HashMap<String,Integer> ingredient = new HashMap<String,Integer>();
        HashMap<String,Integer> product = new HashMap<String,Integer>();
        superRef.put("Anywhere",0);
        String sRegionFile = "";
        
        String[] sList;
//        sList = setStandardList();
//        sList = setNetherList();
//        sList = setBasisList();
//        sList = setErweitertList();
//        sList = setEbene1();
        sList = new String[]
        		{
        		"WOODCUTTER",
        		};

        System.out.println("[Stronghold] Building              cost" );
        for (String sType : sList) 
        {
        		BuildPlanType bType = BuildPlanType.getBuildPlanType(sType);
        		buildPLan = testData.readTMXBuildPlan(bType, 4, -1);
	            ItemList matItems = BuildManager.makeMaterialList(buildPLan);
	            for (Item item : matItems.values())
	            {
	            	required.put(item.ItemRef(), 0);
	            }
        }
        for (String sType : sList) 
        {
        		BuildPlanType bType = BuildPlanType.getBuildPlanType(sType);
        		buildPLan = testData.readTMXBuildPlan(bType, 4, -1);
	            ItemList matItems = BuildManager.makeMaterialList(buildPLan);
	            for (Item item : matItems.values())
	            {
	            	required.put(item.ItemRef(), 0);
	            }
        }

        showReagentList( required, sList,testData);
        
		byte expected = ConfigBasis.getMaterialId(Material.CHEST);
		byte actual = 2;
		assertEquals(expected, actual);
	}

}
