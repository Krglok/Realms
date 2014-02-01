package net.krglok.realms.builder;

import org.bukkit.Material;

/**
 * Buildplan for a Woodcutter
 * 
 * @author Windu
 *
 */
public class BuildPlanWoodCutter extends BuildPlan
{

	public BuildPlanWoodCutter( )
	{
		super(BuildPlanType.WOODCUTTER, 4, -1);
		setLine(0, "[WOOD_CUTTER]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.fillLevel(cube[0], Material.DIRT);
		BuildPlan.fillRowPart(cube[1][0], Material.LOG, 0, 6);
		BuildPlan.fillRowPart(cube[1][1], Material.LOG, 0, 6);
		BuildPlan.fillRowPart(cube[2][0], Material.LOG, 1, 5);
		BuildPlan.fillRowPart(cube[2][1], Material.LOG, 1, 5);
		BuildPlan.fillRowPart(cube[3][0], Material.LOG, 2, 4);
		BuildPlan.fillRowPart(cube[3][1], Material.LOG, 2, 4);
		BuildPlan.fillRowPart(cube[4][0], Material.LOG, 3, 3);
		BuildPlan.fillRowPart(cube[4][1], Material.LOG, 3, 3);
		BuildPlan.setPos(cube[1][3], Material.CHEST, 5);
		BuildPlan.setPos(cube[1][4], Material.WORKBENCH, 5);
		BuildPlan.setPos(cube[2][2], Material.WALL_SIGN, 3);
		return cube;
	}

}
