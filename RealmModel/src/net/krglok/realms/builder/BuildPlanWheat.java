package net.krglok.realms.builder;

import org.bukkit.Material;

/**
 * 
 * @author Windu
 *
 */
public class BuildPlanWheat extends BuildPlan
{

	public BuildPlanWheat( )
	{
		super(BuildPlanType.WHEAT, 4, 0);
		setLine(0, "  [WHEAT]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		byte[][][] cube = new byte[edge][edge][edge];
		// aussenrand Nord und Sued
		BuildPlan.fillRowPart(cube[0][0], Material.LOG, 0, 6);
		BuildPlan.fillRowPart(cube[0][6], Material.LOG, 0, 6);
		BuildPlan.fillColPart(cube[0], Material.LOG, 0, 1, 5);
		BuildPlan.fillColPart(cube[0], Material.LOG, 6, 1, 5);
		BuildPlan.setPos(cube[1][0], Material.SIGN_POST, 3);
		// Feld dirt
		BuildPlan.fillRowPart(cube[0][1], Material.DIRT, 1, 5);
		BuildPlan.fillRowPart(cube[0][2], Material.DIRT, 1, 5);
		BuildPlan.fillRowPart(cube[0][3], Material.DIRT, 1, 2);
		BuildPlan.fillRowPart(cube[0][3], Material.DIRT, 4, 5);
		BuildPlan.fillRowPart(cube[0][4], Material.DIRT, 1, 5);
		BuildPlan.fillRowPart(cube[0][5], Material.DIRT, 1, 5);
		// Water in Center
		BuildPlan.setPos(cube[0][3], Material.WATER, 3);
		
		
		return cube;
	}

}
