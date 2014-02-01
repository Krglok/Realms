package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanLane extends BuildPlan
{

	public BuildPlanLane()
	{
		super(BuildPlanType.LANE, 2, 0);
		setLine(0, "  [LANE]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.fillLevel(cube[0], Material.GRAVEL);
		return cube;
	}

}
