package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanSteeple extends BuildPlan
{

	public BuildPlanSteeple()
	{
		super(BuildPlanType.STEEPLE, 4, 0);
		setLine(0, "  [STEEPLE]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		// nordwand
		BuildPlan.fillRowPart(cube[0][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[1][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[0][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[1][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[2][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[3][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[4][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[5][0], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[6][0], Material.COBBLESTONE, 1, 5);
		//suedwand
		BuildPlan.fillRowPart(cube[0][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[1][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[2][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[3][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[4][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[5][6], Material.COBBLESTONE, 0, 6);
		BuildPlan.fillRowPart(cube[6][6], Material.COBBLESTONE, 1, 5);
		//west wand
		BuildPlan.fillColPart(cube[0], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[1], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[5], Material.COBBLESTONE, 0, 1, 5);
		BuildPlan.fillColPart(cube[6], Material.COBBLESTONE, 0, 1, 5);
		//ost wand
		BuildPlan.fillColPart(cube[0], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[1], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[5], Material.COBBLESTONE, 6, 1, 5);
		BuildPlan.fillColPart(cube[6], Material.COBBLESTONE, 6, 1, 5);
		// dach
		BuildPlan.fillRowPart(cube[5][1], Material.WOOD, 1, 5);
		BuildPlan.fillRowPart(cube[5][2], Material.WOOD, 1, 5);
		BuildPlan.fillRowPart(cube[5][3], Material.WOOD, 1, 4);
		BuildPlan.fillRowPart(cube[5][4], Material.WOOD, 1, 5);
		BuildPlan.fillRowPart(cube[5][5], Material.WOOD, 1, 5);
		// ausstattung
		BuildPlan.setPos(cube[6][0], Material.TORCH, 0);
		BuildPlan.setPos(cube[6][0], Material.TORCH, 6);
		BuildPlan.setPos(cube[6][6], Material.TORCH, 0);
		BuildPlan.setPos(cube[6][6], Material.TORCH, 6);

		return cube;
	}

}
