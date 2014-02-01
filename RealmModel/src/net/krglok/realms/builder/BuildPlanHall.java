package net.krglok.realms.builder;

import org.bukkit.Material;

public class BuildPlanHall extends BuildPlan
{

	public BuildPlanHall( )
	{
		super(BuildPlanType.HALL, 7, -2);
		setLine(0, "  [HALL]");
		this.cube = initCube(edge);
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		byte[][][] cube = new byte[edge][edge][edge];
		BuildPlan.fillLevel(cube[1], Material.COBBLESTONE);
		//Nordwand
		BuildPlan.fillRowPart(cube[2][0], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[3][0], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[4][0], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][0], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[6][0], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[7][0], Material.STONE, 1, 11);
		BuildPlan.fillRowPart(cube[8][1], Material.STONE, 1, 11);
		BuildPlan.fillRowPart(cube[9][2], Material.STONE, 1, 11);
		BuildPlan.fillRowPart(cube[7][6], Material.STONE, 1, 3);
		BuildPlan.fillRowPart(cube[7][6], Material.STONE, 9, 11);
		BuildPlan.fillRowPart(cube[8][5], Material.STONE, 1, 4);
		BuildPlan.fillRowPart(cube[8][5], Material.STONE, 8, 11);

		//Middle wand
		BuildPlan.fillRowPart(cube[2][6], Material.WOOD, 4, 10);
		BuildPlan.fillRowPart(cube[3][6], Material.WOOD, 4, 10);
		BuildPlan.fillRowPart(cube[4][6], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][6], Material.LOG, 2, 10);
		BuildPlan.fillRowPart(cube[5][6], Material.WOOD, 2, 10);
		// Holzdecke Hauptraum
		BuildPlan.fillRowPart(cube[5][7], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][8], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][9], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][10], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][11], Material.WOOD, 2, 10);

		//SuedWand aus Holz
		BuildPlan.fillRowPart(cube[2][12], Material.WOOD, 2, 4);
		BuildPlan.fillRowPart(cube[2][12], Material.WOOD, 8, 10);
		BuildPlan.fillRowPart(cube[3][12], Material.WOOD, 2, 4);
		BuildPlan.fillRowPart(cube[3][12], Material.WOOD, 8, 10);
		BuildPlan.fillRowPart(cube[4][12], Material.WOOD, 2, 10);
		BuildPlan.fillRowPart(cube[5][12], Material.LOG, 2, 10);
		BuildPlan.fillRowPart(cube[6][12], Material.WOOD, 3, 9);
		BuildPlan.fillRowPart(cube[7][12], Material.WOOD, 4, 8);
		BuildPlan.fillRowPart(cube[8][12], Material.WOOD, 5, 7);
		BuildPlan.fillRowPart(cube[9][12], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][11], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][10], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][9], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][8], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][7], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][6], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][5], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][4], Material.STONE, 5, 7);
		BuildPlan.fillRowPart(cube[9][3], Material.STONE, 1, 11);
		
		//Eckpfeiler
		BuildPlan.setHeight(cube, Material.LOG, 2, 0, 1, 5);
		BuildPlan.setHeight(cube, Material.LOG, 2, 6, 1, 5);
		BuildPlan.setHeight(cube, Material.WOOD,2, 6, 2, 3);
		BuildPlan.setHeight(cube, Material.LOG, 2,12, 1, 3);
		BuildPlan.setHeight(cube, Material.LOG, 2, 0,11, 5);
		BuildPlan.setHeight(cube, Material.LOG, 2, 6,11, 5);
		BuildPlan.setHeight(cube, Material.LOG, 2,12,11, 3);
		BuildPlan.setHeight(cube, Material.LOG, 2,12,5, 2);
		BuildPlan.setHeight(cube, Material.LOG, 2,12,7, 2);

		//Seitenwaende
		BuildPlan.fillColPart(cube[2], Material.WOOD, 1, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.WOOD, 11, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 1, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 11, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.WOOD, 1, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.WOOD, 11, 1, 5);
		BuildPlan.fillColPart(cube[6], Material.WOOD, 1, 1, 5);
		BuildPlan.fillColPart(cube[6], Material.WOOD, 11, 1, 5);
		BuildPlan.fillColPart(cube[7], Material.WOOD, 1, 1, 5);
		BuildPlan.fillColPart(cube[7], Material.WOOD, 11, 1, 5);
		BuildPlan.fillColPart(cube[8], Material.WOOD, 1, 2, 4);
		BuildPlan.fillColPart(cube[8], Material.WOOD, 11, 2, 4);
		BuildPlan.fillColPart(cube[2], Material.WOOD, 1, 7, 11);
		BuildPlan.fillColPart(cube[2], Material.WOOD, 11, 7, 11);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 1, 7, 11);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 11, 7, 11);
		BuildPlan.fillColPart(cube[4], Material.WOOD, 1, 7, 11);
		BuildPlan.fillColPart(cube[4], Material.WOOD, 11, 7, 11);
		//Dach hauptraum
		BuildPlan.fillColPart(cube[5], Material.STONE,  1, 7, 12);
		BuildPlan.fillColPart(cube[5], Material.STONE, 11, 7, 12);
		BuildPlan.fillColPart(cube[6], Material.STONE,  2, 7, 12);
		BuildPlan.fillColPart(cube[6], Material.STONE, 10, 7, 12);
		BuildPlan.fillColPart(cube[7], Material.STONE,  3, 7, 12);
		BuildPlan.fillColPart(cube[7], Material.STONE,  9, 7, 12);
		BuildPlan.fillColPart(cube[8], Material.STONE,  4, 6, 12);
		BuildPlan.fillColPart(cube[8], Material.STONE,  8, 6, 12);

		//Ausstattung
		BuildPlan.setPos(cube[2][12], Material.WOOD_DOOR, 6);
		BuildPlan.setPos(cube[2][6],  Material.WOOD_DOOR, 3);
		BuildPlan.setPos(cube[3][11], Material.WALL_SIGN, 5);
		BuildPlan.fillRowPart(cube[3][1], Material.CHEST, 3, 4);
		BuildPlan.fillRowPart(cube[3][1], Material.CHEST, 6, 7);
		BuildPlan.fillRowPart(cube[3][1], Material.CHEST, 9,10);
		BuildPlan.fillRowPart(cube[5][1], Material.CHEST, 3, 4);
		BuildPlan.fillRowPart(cube[5][1], Material.CHEST, 6, 7);
		BuildPlan.fillRowPart(cube[5][1], Material.CHEST, 9,10);
		BuildPlan.fillRowPart(cube[3][5], Material.CHEST, 5, 6);
		BuildPlan.fillRowPart(cube[3][5], Material.CHEST, 8, 9);
		BuildPlan.fillRowPart(cube[5][5], Material.CHEST, 5, 6);
		BuildPlan.fillRowPart(cube[5][5], Material.CHEST, 8, 9);
//		BuildPlan.setPos(cube[1][1], Material.SIGN, 1);
		BuildPlan.setPos(cube[4][11], Material.TORCH, 2);
		BuildPlan.setPos(cube[3][11], Material.BOOKSHELF, 2);
		BuildPlan.setPos(cube[2][11], Material.BOOKSHELF, 2);
		BuildPlan.setPos(cube[4][11], Material.TORCH, 10);
		BuildPlan.setPos(cube[3][11], Material.BOOKSHELF, 11);
		BuildPlan.setPos(cube[2][11], Material.BOOKSHELF, 11);
		
		return cube;
	}

}
