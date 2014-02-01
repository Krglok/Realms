package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanPillar extends BuildPlan
{

	public BuildPlanPillar()
	{
		super(BuildPlanType.PILLAR, 2, 0);
		setLine(0, "  [PILLAR]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		BuildPlan.setPos(cube[0][1], Material.COBBLESTONE, 1);
		BuildPlan.setPos(cube[1][1], Material.COBBLESTONE, 1);
		BuildPlan.setPos(cube[2][1], Material.TORCH, 1);

		return cube;
	}

}
