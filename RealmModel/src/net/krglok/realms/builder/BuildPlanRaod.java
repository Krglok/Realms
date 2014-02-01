package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanRaod extends BuildPlan
{
	

	public BuildPlanRaod( )
	{
		super(BuildPlanType.ROAD, 2, 0);
		setLine(0, "  [ROAD]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.fillLevel(cube[0], Material.COBBLESTONE);
		return cube;
	}

}
