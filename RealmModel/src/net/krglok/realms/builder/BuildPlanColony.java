package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanColony extends BuildPlan
{

	
	public BuildPlanColony()
	{
		super(BuildPlanType.COLONY, 4, -1);
		setLine(0, "  [COLONY]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
//		byte[][][] cube = new byte[edge][edge][edge];
		//Center of Settlement/Colony
		BuildPlan.setPos(cube[0][3], Material.BEDROCK, 3);
		//NordWand aus Holz
		BuildPlan.fillRowPart(cube[1][0], Material.WOOL, 1, 2);
		BuildPlan.fillRowPart(cube[1][0], Material.WOOL, 4, 5);
		BuildPlan.fillRowPart(cube[2][0], Material.WOOL, 1, 2);
		BuildPlan.fillRowPart(cube[2][0], Material.WOOL, 4, 5);
		BuildPlan.fillRowPart(cube[3][0], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[4][0], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[5][1], Material.WOOL, 1, 5);
		//SuedWand aus Holz
		BuildPlan.fillRowPart(cube[1][6], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[2][6], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[3][6], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[4][6], Material.WOOL, 1, 5);
		BuildPlan.fillRowPart(cube[5][5], Material.WOOL, 1, 5);
		//WestWand aus Holz
		BuildPlan.fillColPart(cube[1], Material.WOOL, 0, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.WOOL, 0, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOL, 0, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.WOOL, 0, 1, 5);
		BuildPlan.fillColPart(cube[5], Material.WOOL, 1, 2, 4);
		//OstWand aus Holz
		BuildPlan.fillColPart(cube[1], Material.WOOL, 6, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.WOOL, 6, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOL, 6, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.WOOL, 6, 1, 5);
		BuildPlan.fillColPart(cube[5], Material.WOOL, 5, 2, 4);
		//Dach
		BuildPlan.fillRowPart(cube[6][2], Material.WOOL, 2, 4);
		BuildPlan.fillRowPart(cube[6][3], Material.WOOL, 2, 4);
		BuildPlan.fillRowPart(cube[6][4], Material.WOOL, 2, 4);
		//Inventory
		BuildPlan.fillRowPart(cube[1][0], Material.WOOD_DOOR, 3, 3);
		BuildPlan.fillRowPart(cube[2][1], Material.WALL_SIGN, 4, 4);
		BuildPlan.fillRowPart(cube[1][1], Material.CHEST, 1, 2);
		BuildPlan.fillRowPart(cube[1][1], Material.CHEST, 4, 5);
		BuildPlan.fillRowPart(cube[1][5], Material.CHEST, 1, 2);
		BuildPlan.fillRowPart(cube[1][5], Material.CHEST, 4, 5);
//		BuildPlan.setPos(cube[1][1], Material.SIGN, 1);
		BuildPlan.setPos(cube[2][3], Material.TORCH, 1);
		return cube;
	}

}
