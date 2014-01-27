package net.krglok.realms.tool;

import static org.junit.Assert.*;

import net.krglok.realms.builder.BuildPlanHome;
import net.krglok.realms.core.ConfigBasis;

import org.bukkit.Material;
import org.junit.Test;

public class BuildPlanHomeTest
{

	@Test
	public void testInitCube()
	{
		BuildPlanHome bHome = new BuildPlanHome();
		int edge = bHome.getEdge();
		
		bHome.setCube(bHome.initCube(edge));
		byte expected = ConfigBasis.getMaterialId(Material.LOG);
		byte actual = bHome.getCube()[3][1][1];
		if (expected != actual)
		{
			System.out.println("Height Test");
			for (int i = 0; i < bHome.getEdge(); i++)
			{
				for (int j = 0; j < bHome.getEdge(); j++)
				{
					System.out.println(i+":"+j+" |"+ConfigBasis.showPlanValue(bHome.getCube()[i][j])+"|");
					
				}
			}
		}
		assertEquals(expected, actual);
	}

}
