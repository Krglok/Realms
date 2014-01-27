package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.BuildPlan;
import net.krglok.realms.data.BuildPlanHome;

import org.bukkit.Material;
import org.junit.Test;

public class BuildPlanTest
{

	@Test
	public void testBuildPlan()
	{
		BuildPlanHome bHome = new BuildPlanHome(4,0);
		
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

}
