package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanColony;
import net.krglok.realms.builder.BuildPlanHall;
import net.krglok.realms.builder.BuildPlanHome;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanQuarry;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildPlanWheat;
import net.krglok.realms.builder.BuildPlanWoodCutter;
import net.krglok.realms.core.ConfigBasis;

import org.bukkit.Material;
import org.junit.Test;

import tiled.core.*;
import tiled.io.TMXMapReader;

public class BuildPlanHomeTest
{
	/**
	 * not all tile ids will converted
	 * not found id return 0 = AIR 
	 * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * @param id
	 * @return
	 */
	private Material tmxToMaterial(int id)
	{
		switch (id)
		{
		case 1: return Material.GRASS;
		case 2: return Material.STONE;
		case 3: return Material.DIRT;
		case 4: return Material.GRASS;
		case 5: return Material.WOOD;
		case 6: return Material.DOUBLE_STEP;
		case 71: return Material.CLAY;
		case 8: return Material.BRICK;
		case 9: return Material.TNT;
		case 12: return Material.WEB;
		case 13: return Material.RED_ROSE;
		case 14: return Material.YELLOW_FLOWER;
		case 16: return Material.SAPLING;
		case 20: return Material.BED_BLOCK;
		case 25: return Material.COBBLESTONE;
		case 26: return Material.BEDROCK;
		case 27: return Material.SAND;
		case 28: return Material.GRAVEL;
		case 29: return Material.LOG;
		case 30: return Material.LOG;
		case 31: return Material.WOOL;
		case 32: return Material.GOLD_BLOCK;
		case 33: return Material.DIAMOND_BLOCK;
		case 34: return Material.EMERALD_BLOCK;
		case 35: return Material.REDSTONE_BLOCK;
		case 37: return Material.RED_MUSHROOM;
		case 38: return Material.BROWN_MUSHROOM;
		case 44: return Material.CAKE_BLOCK;
		case 45: return Material.PORTAL;
		case 46: return Material.FENCE;
		case 47: return Material.NETHER_FENCE;
		case 49: return Material.GOLD_ORE;
		case 50: return Material.IRON_ORE;
		case 51: return Material.COAL_ORE;
		case 52: return Material.BOOKSHELF;
		case 53: return Material.MOSSY_COBBLESTONE;
		case 54: return Material.OBSIDIAN;
		case 57: return Material.LONG_GRASS;
		case 60: return Material.WORKBENCH;
		case 61: return Material.FURNACE;
		case 62: return Material.FURNACE;
		case 63: return Material.DISPENSER;
		case 64: return Material.DISPENSER;
		case 65: return Material.REDSTONE_TORCH_ON;
		case 67: return Material.DIODE_BLOCK_OFF;
		case 68: return Material.WOOD_BUTTON;
		case 69: return Material.TORCH;
		case 70: return Material.LEVER;
		case 72: return Material.DIODE_BLOCK_OFF;
		case 73: return Material.SPONGE;
		case 74: return Material.GLASS;
		case 75: return Material.DIAMOND_ORE;
		case 76: return Material.REDSTONE_ORE;
		case 77: return Material.LEAVES;
		case 78: return Material.LEAVES_2;
		case 82: return Material.DAYLIGHT_DETECTOR;
		case 84: return Material.WORKBENCH;
		case 85: return Material.WORKBENCH;
		case 86: return Material.FURNACE;
		case 87: return Material.FURNACE;
		case 88: return Material.SAPLING;
		case 91: return Material.WALL_SIGN;
		case 92: return Material.FIRE;
		case 94: return Material.LAVA;
		case 95: return Material.WATER;
		case 97: return Material.WOOL;
		case 99: return Material.SNOW_BLOCK;
		case 100: return Material.ICE;
		case 101: return Material.SOIL;
		case 102: return Material.CACTUS;
		case 103: return Material.CACTUS;
		case 105: return Material.CLAY;
		case 106: return Material.SUGAR_CANE_BLOCK;
		case 107: return Material.NOTE_BLOCK;
		case 108: return Material.NOTE_BLOCK;
		case 109: return Material.WATER_LILY;
		case 110: return Material.MYCEL;
		case 111: return Material.MYCEL;
		case 118: return Material.WEB;
		case 121: return Material.TORCH;
		case 122: return Material.WOOD_DOOR;
		case 123: return Material.IRON_DOOR_BLOCK;
		case 124: return Material.LADDER;
		case 125: return Material.TRAP_DOOR;
		case 126: return Material.IRON_BARDING;
		case 129: return Material.WHEAT;
		case 130: return Material.WHEAT;
		case 131: return Material.WHEAT;
		case 132: return Material.WHEAT;
		case 133: return Material.WHEAT;
		case 134: return Material.WHEAT;
		case 135: return Material.WHEAT;
		case 136: return Material.WHEAT;
		case 137: return Material.BREWING_STAND;
		case 139: return Material.FENCE_GATE;
		case 140: return Material.LEAVES;
		case 141: return Material.LEAVES_2;
		case 142: return Material.CHEST;
		case 143: return Material.ENDER_CHEST;
		case 145: return Material.LEVER;
		case 146: return Material.WOOD_DOOR;
		case 147: return Material.IRON_DOOR_BLOCK;
		case 151: return Material.PUMPKIN;
		case 152: return Material.NETHERRACK;
		case 153: return Material.SOUL_SAND;
		case 154: return Material.GLOWSTONE;
		case 161: return Material.COCOA;
		case 162: return Material.CHEST;
		case 163: return Material.CHEST;
		case 164: return Material.CHEST;
		case 165: return Material.CHEST;
		case 166: return Material.CHEST;
		case 167: return Material.CHEST;
		case 168: return Material.CHEST;

		
		default : return Material.AIR;
		}
		
	}

    private int getGid(Tile tile) {
        TileSet tileset = tile.getTileSet();
        if (tileset != null) {
            return tile.getId() + 1; //getFirstGidForTileset(tileset);
        }
        return tile.getId();
    }

    /**
     * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * the tsx and the image file must stay in the some 
	 * directory as the tmx files 
	 * errorHandling: if file not exist, empty map returned
     * @param bType
     * @param radius
     * @param offSet
     * @param path
     * @return
     */
	private BuildPlanMap readTMXBuildPlan(BuildPlanType bType, int radius, int offSet, String path)
	{
		BuildPlanMap buildPlan = new BuildPlanMap(bType,radius , offSet);
		TMXMapReader mapReader = new TMXMapReader();
		String filename =  path+"\\"+bType.name()+".tmx";
		Map tmxMap = null;
		try
		{
			tmxMap =  mapReader.readMap(filename);
			System.out.println(tmxMap.getFilename()+":"+tmxMap.getHeight()+":"+tmxMap.getWidth());
			radius = (tmxMap.getWidth()+1) / 2; 
			buildPlan.setRadius(radius);
			byte [][][] newCube = buildPlan.initCube(tmxMap.getWidth());
			int level = 0;
	 		for (MapLayer layer :tmxMap.getLayers())
			{
	 			
				System.out.println(layer.getName()+":"+level+":"+layer.getHeight()+":"+layer.getWidth());
		        Rectangle bounds = layer.getBounds();
				TileLayer tl = (TileLayer) layer;
	            for (int y = 0; y < layer.getHeight(); y++) 
	            {
					System.out.print("|");
	                for (int x = 0; x < layer.getWidth(); x++) 
	                {
	                    Tile tile = tl.getTileAt(x + bounds.x, y + bounds.y);
	                    int gid = 0;
	
	                    if (tile != null) 
	                    {
	                        gid = getGid(tile);
	                    }
	                    System.out.print(gid+"|");
	                    newCube[level][y][x] = ConfigBasis.getMaterialId(tmxToMaterial(gid));
	//                    System.out.println("tile"+"gid: "+ gid);
	                }
	                System.out.println("");
	            }
	            level++;
				
			}
			buildPlan.setCube(newCube);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return buildPlan;
	}

	@Test
	public void testInitCube()
	{
		BuildPlanMap bHome;
//		BuildPlanColony bHome = new BuildPlanColony();
//		BuildPlanHall bHome = new BuildPlanHall();
//		BuildPlanWheat bHome = new BuildPlanWheat();
//		BuildPlanWoodCutter bHome = new BuildPlanWoodCutter();
//		BuildPlanQuarry bHome = new BuildPlanQuarry();
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\buildplan";

		bHome = readTMXBuildPlan(BuildPlanType.STEEPLE, 7, -3, path);

		byte expected = ConfigBasis.getMaterialId(Material.LOG);
		byte actual = bHome.getCube()[3][1][1];
		if (expected != actual)
		{
			System.out.println("=====================================");
			System.out.println("Map Test  "+bHome.getBuildingType()+" Offset: "+bHome.getOffsetY());
			System.out.println("Map Radius "+bHome.getRadius()+" Edge: "+bHome.getEdge());
			for (int i = 0; i < bHome.getEdge(); i++)
			{
                System.out.println("Level:"+i);
				for (int j = 0; j < bHome.getEdge(); j++)
				{
					System.out.print("|");
					for (int j2 = 0; j2 < bHome.getEdge(); j2++)
					{
						System.out.print(ConfigBasis.planValueToChar(bHome.getCube()[i][j][j2])+"|");
					}
					System.out.println("");
					
				}
			}
		}
		assertEquals(expected, actual);
	}

}
