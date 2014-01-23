package net.krglok.realms.data;

import java.awt.geom.CubicCurve2D;

import net.krglok.realms.core.BuildingType;

import org.bukkit.Material;


public class BuildPlanHome extends BuildPlan
{

	
	
	public BuildPlanHome( int radius, int offsetY)
	{
		super(BuildingType.BUILDING_HOME, radius, offsetY);
		setLine(0, "  [HOME]");
		this.cube = initCube(edge);
		
	}

	@Override
	public byte[][][] initCube(int edge)
	{
		byte[][][] cube = new byte[edge][edge][edge];
		// Grundplatte Cobblestone
		BuildPlan.fillLevel(cube[0], Material.COBBLESTONE);
		//Eckpfosten Log 1 , 4 hoch
		BuildPlan.setHeight(cube, Material.LOG, 1, 0, 0, 3);
		BuildPlan.setHeight(cube, Material.LOG, 1, 6, 0, 3);
		BuildPlan.setHeight(cube, Material.LOG, 1, 0, 6, 3);
		BuildPlan.setHeight(cube, Material.LOG, 1, 6, 6, 3);
		// Querbalken Stirnwaende
		BuildPlan.fillRowPart(cube[4][0], Material.LOG, 1, 5);
		BuildPlan.fillRowPart(cube[4][6], Material.LOG, 1, 5);
		//Giebel Nord
		BuildPlan.fillRowPart(cube[5][0], Material.LOG, 2, 4);
		BuildPlan.fillRowPart(cube[6][0], Material.STONE, 3, 3);
		//Giebel Sued
		BuildPlan.fillRowPart(cube[5][6], Material.LOG, 2, 4);
		BuildPlan.fillRowPart(cube[6][6], Material.STONE, 3, 3);
		
		//NordWand aus Holz
		BuildPlan.fillRowPart(cube[1][0], Material.WOOD, 1, 2);
		BuildPlan.fillRowPart(cube[1][0], Material.WOOD, 4, 5);
		BuildPlan.fillRowPart(cube[2][0], Material.WOOD, 1, 2);
		BuildPlan.fillRowPart(cube[2][0], Material.WOOD, 4, 5);
		BuildPlan.fillRowPart(cube[3][0], Material.WOOD, 1, 5);
		//SuedWand aus Holz
		BuildPlan.fillRowPart(cube[1][6], Material.WOOD, 1, 5);
		BuildPlan.fillRowPart(cube[2][6], Material.WOOD, 1, 5);
		BuildPlan.fillRowPart(cube[3][6], Material.WOOD, 1, 5);
		//WestWand aus Holz
		BuildPlan.fillColPart(cube[1], Material.WOOD, 0, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.WOOD, 0, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 0, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.STONE, 0, 0, 6);
		//OstWand aus Holz
		BuildPlan.fillColPart(cube[1], Material.WOOD, 6, 1, 5);
		BuildPlan.fillColPart(cube[2], Material.WOOD, 6, 1, 5);
		BuildPlan.fillColPart(cube[3], Material.WOOD, 6, 1, 5);
		BuildPlan.fillColPart(cube[4], Material.STONE, 6, 0, 6);
		//Dach
		BuildPlan.fillColPart(cube[5], Material.STONE, 1, 0, 6);
		BuildPlan.fillColPart(cube[5], Material.STONE, 5, 0, 6);
		BuildPlan.fillColPart(cube[6], Material.STONE, 2, 0, 6);
		BuildPlan.fillColPart(cube[6], Material.STONE, 4, 0, 6);
		BuildPlan.fillColPart(cube[6], Material.STONE, 3, 0, 6);

		BuildPlan.fillRowPart(cube[1][0], Material.WOOD_DOOR, 3, 3);
		BuildPlan.fillRowPart(cube[2][1], Material.WALL_SIGN, 4, 4);
//		BuildPlan.fillRowPart(cube[1][5], Material.BED_BLOCK, 3, 3);
		BuildPlan.fillRowPart(cube[1][4], Material.BED_BLOCK, 3, 3);
		BuildPlan.fillRowPart(cube[1][5], Material.CHEST, 5, 5);
		BuildPlan.fillRowPart(cube[1][5], Material.WORKBENCH, 1, 1);
//		BuildPlan.setPos(cube[1][1], Material.SIGN, 1);
		BuildPlan.setPos(cube[2][3], Material.TORCH, 1);
		BuildPlan.setPos(cube[2][3], Material.TORCH, 5);
		


		
		return cube;
	}


}
