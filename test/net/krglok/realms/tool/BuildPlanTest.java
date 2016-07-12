package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.unittest.DataTest;

import org.bukkit.Material;
import org.junit.Test;

public class BuildPlanTest
{

	private void getBuildPlanTypeHeader(BuildPlanType bType, int group)
	{
			//BUILDING_HOME (100),
			//BUILDING_PROD (200),
			//BUILDING_PRODMILITARY (300),
			//BUILDING_WAREHOUSE (300),
			//BUILDING_TRADER (400),
			//BUILDING_MILITARY (500),
			//BUILDING_ENTERTAIN (600),
			//BUILDING_EDUCATION (700),
			//BUILDING_RELIGION (800),
			//BUILDING_KEEP (900),
			//BUILDING_GOVERNMENT (1000)
			if (BuildPlanType.getBuildGroup(bType)==1)
			{
				System.out.println("===:Structure");
			}
			if (BuildPlanType.getBuildGroup(bType)==10)
			{
				System.out.println("===:Management");
			}
			if (BuildPlanType.getBuildGroup(bType)==100)
			{
				System.out.println("===:Homes");
			}
			if (BuildPlanType.getBuildGroup(bType)==200)
			{
				System.out.println("===:Production");
			}
			if (BuildPlanType.getBuildGroup(bType)==300)
			{
				System.out.println("===:Military Production");
			}
			if (BuildPlanType.getBuildGroup(bType)==400)
			{
				System.out.println("===:Trader");
			}
			if (BuildPlanType.getBuildGroup(bType)==500)
			{
				System.out.println("===:Military ");
			}
			if (BuildPlanType.getBuildGroup(bType)==600)
			{
				System.out.println("===:Entertain");
			}
			if (BuildPlanType.getBuildGroup(bType)==700)
			{
				System.out.println("===:Education");
			}
			if (BuildPlanType.getBuildGroup(bType)==800)
			{
				System.out.println("===:Religion");
			}
			if (BuildPlanType.getBuildGroup(bType)==900)
			{
				System.out.println("===:Feudal");
			}
			if (BuildPlanType.getBuildGroup(bType)==1000)
			{
				System.out.println("===:Government");
			}
	}
	
//	@Test
	public void testBuildPlan()
	{
		BuildPlanMap bHome = new BuildPlanMap(BuildPlanType.NONE, 4, 0);
		
		int expected = 7;
		int actual = bHome.getEdge();
		assertEquals(expected, actual);

	}

//	@Test
	public void testFillCol()
	{
		int edge = 7;
		Material mat = Material.COBBLESTONE;
		byte[] column = new byte[edge];
		
		column = BuildPlan.fillRow(column, mat);
		
		byte expected = ConfigBasis.getMaterialId(Material.COBBLESTONE);
		byte actual = column[0];
		if (expected != actual)
		{
			System.out.println("Fill Col Test");
			System.out.println("|"+ConfigBasis.showPlanValue(column)+"|");
		}
		assertEquals(expected, actual);
	}

//	@Test
	public void testFillLevel()
	{
		int edge = 7;
		Material mat = Material.COBBLESTONE;
		byte[][] level = new byte[edge][edge];
		
		level = BuildPlan.fillLevel(level, mat);

		byte expected = ConfigBasis.getMaterialId(Material.COBBLESTONE);
		byte actual = level[0][edge-1];
		if (expected != actual)
		{
			for (int i = 0; i < level.length; i++)
			{
				System.out.println("Fill Level Test");
				System.out.println(i+" |"+ConfigBasis.showPlanValue(level[i])+"|");
			}
		}
		assertEquals(expected, actual);
	}

//	@Test
	public void testSetColPart()
	{
		int edge = 7;
		Material mat = Material.COBBLESTONE;
		byte[] column = new byte[edge];
		column = BuildPlan.fillRow(column, mat);
		// set chest in middle Position
		mat = Material.CHEST;
		column = BuildPlan.fillRowPart(column, mat, 3, 3);
		
		byte expected = ConfigBasis.getMaterialId(Material.CHEST);
		byte actual = column[3];
//		if (expected != actual)
		{
			System.out.println("Set Part Test");
			System.out.println("|"+ConfigBasis.showPlanValue(column)+"|");
		}
		assertEquals(expected, actual);
	}

//	@Test
	public void testSetPos()
	{
		int edge = 7;
		Material mat = Material.AIR;
		byte[] column = new byte[edge];
		column = BuildPlan.fillRow(column, mat);
		// set chest in middle Position
		mat = Material.CHEST;
		column = BuildPlan.setPos(column, mat, 3);
		
		byte expected = ConfigBasis.getMaterialId(Material.CHEST);
		byte actual = column[3];
		if (expected != actual)
		{
			System.out.println("Set Pos Test");
			System.out.println("|"+ConfigBasis.showPlanValue(column)+"|");
		}
		assertEquals(expected, actual);
	}

//	@Test
	public void testSetHeight()
	{
		int edge = 7;
		Material mat = Material.AIR;
		byte[][][] cube = new byte[edge][edge][edge];
		cube = BuildPlan.clearCube(cube); 
		// set chest in middle Position
		mat = Material.LOG;
		cube = BuildPlan.setHeight(cube, mat, 3, 1, 1, 3);
		
		byte expected = ConfigBasis.getMaterialId(Material.LOG);
		byte actual = cube[3][1][1];
		if (expected != actual)
		{
			System.out.println("Height Test");
			for (int i = 0; i < edge; i++)
			{
				for (int j = 0; j < edge; j++)
				{
					System.out.println(i+":"+j+" |"+ConfigBasis.showPlanValue(cube[i][j])+"|");
					
				}
			}
		}
		assertEquals(expected, actual);
	}

	
	
	private ArrayList<String> showTMXList()
	{
		ArrayList<String> tmxList = new ArrayList<String>();
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
        File regionFolder = new File(path, "buildplan");
        if (!regionFolder.exists()) {
        	System.out.println("Folder not found !");
            return tmxList;
        }

        for (File TMXFile : regionFolder.listFiles()) 
        {
        	String sRegionFile = TMXFile.getName();
        	if (sRegionFile.contains("tmx"))
        	{
	        	sRegionFile = sRegionFile.replace(".tmx", "");
	        	tmxList.add(sRegionFile);
        	}
//        	System.out.println(sRegionFile);
        }
        return tmxList;
	}
//	@Test
	public void testBuildPlanList()
	{
		ArrayList<String> tmxList = showTMXList();
		int group = 0;
		System.out.println("List of BuildPlanTypes with BuildBlueprint====================");
		for (BuildPlanType bType : BuildPlanType.values())
		{
			if (BuildPlanType.getBuildGroup(bType) != group)
			{
				getBuildPlanTypeHeader(bType, group);
				group = BuildPlanType.getBuildGroup(bType);
				System.out.println(" id:BuildPlan     : Blueprint(tmx)");
			}
			System.out.print( ConfigBasis.setStrright(bType.getValue(),3)+":"+ConfigBasis.setStrleft(bType.name(),14));
			if (tmxList.contains(bType.name()))
			{
				System.out.print(": "+tmxList.get(tmxList.indexOf(bType.name())));
			}
			System.out.println("");
		}
		byte expected = ConfigBasis.getMaterialId(Material.CHEST);
		byte actual = 54;
		assertEquals(expected, actual);
	}
	
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
        System.out.println(ConfigBasis.setStrleft(" ",15)+reagents);
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
						sValue = ConfigBasis.setStrleft(sValue,sName.length());
					}
		        	reagents = reagents +""+sValue+" ";
		        }
		        System.out.println(ConfigBasis.setStrleft(sRegionFile.replace(".yml", ""),15) + reagents );
		        
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
				sValue = ConfigBasis.setStrleft(sValue,sName.length());
			}
        	reagents = reagents +""+sValue+" ";
        }
        System.out.println(ConfigBasis.setStrleft( "  Summe : ",15) + reagents );
		
	}
	
	@Test
	public void getBuildPlanConstructionMaterial()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest(); //logTest);

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
//        String sTypeName;

        System.out.println("[Stronghold] Building   Configuration" );
        System.out.println("" );
        System.out.println("123456789012345678901234567890123456789012345678901234567890" );
        System.out.println("============================================================" );
        System.out.println("" );
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
