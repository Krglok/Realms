package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanQuarry extends BuildPlan
{

	public BuildPlanQuarry( )
	{
		super(BuildPlanType.QUARRY, 4, -1);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.fillLevel(cube[0], Material.DIRT);
		BuildPlan.fillRowPart(cube[1][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[1][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[1][2], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[2][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[2][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[2][2], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[3][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[3][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[3][2], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[4][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[4][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[4][2], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[5][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[5][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[5][2], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[6][0], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[6][1], Material.STONE, 0, 6);
		BuildPlan.fillRowPart(cube[6][2], Material.STONE, 0, 6);
	
		BuildPlan.fillRowPart(cube[2][3], Material.LOG, 0, 6);
		BuildPlan.setPos(cube[1][3], Material.LOG, 0);
		BuildPlan.setPos(cube[1][3], Material.LOG, 6);
		BuildPlan.setPos(cube[1][4], Material.LOG, 1);
		BuildPlan.setPos(cube[1][4], Material.CHEST, 5);
		BuildPlan.setPos(cube[1][5], Material.CHEST, 5);
		BuildPlan.setPos(cube[1][4], Material.WALL_SIGN, 3);
		return cube;
	}

}
