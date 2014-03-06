package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.StrongholdTools;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;

public class BuildPlanTest
{

	@Test
	public void testBuildPlan()
	{
		BuildPlanMap bHome = new BuildPlanMap(BuildPlanType.NONE, 4, 0);
		
		int expected = 7;
		int actual = bHome.getEdge();
		assertEquals(expected, actual);

	}

	@Test
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

	@Test
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

	@Test
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

	@Test
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

	@Test
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

//	@Test
//	public void testSerialize()
//	{
//		
//		OutputStream fos = null;
//		
//		try
//		{
//			String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
//			String filename = path+"\\BuildPlanHom.ser";
//		fos = new FileOutputStream( filename );
//		  ObjectOutputStream o = new ObjectOutputStream( fos );
//		  o.writeObject( "Today" );
//		  o.writeObject( new BuildPlanHome());
//		}
//		catch ( IOException e ) { System.err.println( e ); }
//		finally { try { fos.close(); } catch ( Exception e ) { e.printStackTrace(); } }
//	}
	
//	@Test
//	public void testSerializeJson()
//	{
//		
//		Gson gson = new Gson();
//		String jsonData = gson.toJson(new BuildPlanHome());
//		System.out.println(jsonData);
//		
//		BuildPlanHome newHome = new BuildPlanHome(); 
//		newHome =  gson.fromJson(jsonData, BuildPlanHome.class);
//		System.out.println("DERIALIZE===============================================");
//		System.out.println(newHome.getBuildingType()+":"+newHome.getRadius());
//		
//	}
	
	
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
	@Test
	public void testBuildPlanList()
	{
		ArrayList<String> tmxList = showTMXList();
		System.out.println("List of BuildPlanTypes ====================");
		for (BuildPlanType bType : BuildPlanType.values())
		{
			System.out.print( ConfigBasis.setStrright(bType.getValue(),3)+":"+ConfigBasis.setStrleft(bType.name(),14));
			if (tmxList.contains(bType.name()))
			{
				System.out.print(": "+tmxList.get(tmxList.indexOf(bType.name())));
			}
			System.out.println("");
		}
		byte expected = ConfigBasis.getMaterialId(Material.CHEST);
		byte actual = 2;
		assertEquals(expected, actual);
	}

}
