package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanWoodCutter extends BuildPlan
{

	public BuildPlanWoodCutter( )
	{
		super(BuildPlanType.WOODCUTTER, 4, 0);
		setLine(0, "[WOOD_CUTTER]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.fillRowPart(cube[0][0], Material.LOG, 0, 6);
		BuildPlan.fillRowPart(cube[0][1], Material.LOG, 0, 6);
		BuildPlan.fillRowPart(cube[1][0], Material.LOG, 1, 5);
		BuildPlan.fillRowPart(cube[1][1], Material.LOG, 1, 5);
		BuildPlan.fillRowPart(cube[2][0], Material.LOG, 2, 4);
		BuildPlan.fillRowPart(cube[2][1], Material.LOG, 2, 4);
		BuildPlan.setPos(cube[0][1], Material.WORKBENCH, 3);
		BuildPlan.setPos(cube[0][1], Material.SIGN_POST, 5);
return null;
	}

}
