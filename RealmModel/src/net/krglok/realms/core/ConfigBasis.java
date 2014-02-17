package net.krglok.realms.core;

import org.bukkit.Material;
import org.bukkit.block.Block;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.ConfigInterface;

/**
 * class for define static values (constants) 
 * 
 * @author Windu
 *
 */
public class ConfigBasis implements ConfigInterface
{
	protected static final String CONFIG_SETTLEMENT_COUNTER = "settlementCounter";
	protected static final String CONFIG_REALM_COUNTER = "realmCounter";
	protected static final String CONFIG_PLUGIN_VER = "plugin_ver";
	protected static final String CONFIG_PLUGIN_NAME = "plugin_name";
	protected static final String PLUGIN_NAME = "Realms";
	protected static String PLUGIN_VER = "0.2.0";

	public final static long dayNight = 2400; //0 ; // serverTicks 
	public final static long RealmTick = 20L; 
	public final static long DelayTick = 20L; 
	public static long GameDay = ConfigBasis.dayNight / ConfigBasis.RealmTick; // 40; //
	
	public static double DISTANCE_1_DAY = 1000.0;
	
	public static final String LINE = "=============================== ";

	public static byte getBlockId(Block block)
	{
		return getMaterialId(block.getType());
	}
	
	public static byte getMaterialId(Material mat)
	{
		switch (mat)
		{
		case STONE : return 1;
		case GRASS : return 2;
		case DIRT  : return 3;
		case COBBLESTONE : return 4;
		case WOOD  : return 5;
		case SAPLING : return 6;
		case BEDROCK : return 7;
		case WATER: return 8;
		case OBSIDIAN : return 49;
		case ICE : return 79;
		case MYCEL : return 110;
		case LAVA  : return 10;
		case GRAVEL: return 13;
		case LOG : return 17;
		case LOG_2: return 17;
		case SAND : return 12;
		case SANDSTONE : return 24;
		case CLAY: return 82;
		case MOSSY_COBBLESTONE : return 48;
		case IRON_BARDING: return 101;
		case FENCE: return 85;
		case WOOD_STAIRS: return 53;
		case COBBLESTONE_STAIRS: return 67;
		case BRICK: return 98;
		case WHEAT: return 59;
		case SANDSTONE_STAIRS: return (byte) 128;
		case CHEST : return (byte) 54;
		case WORKBENCH : return 58;
		case WOOD_DOOR: return 64;
		case SIGN: return 63;
		case WALL_SIGN : return 68;
		case BED_BLOCK : return 26;
		case BOOKSHELF : return 47;
		case TORCH : return 50;
		case WOOL : return 35;
		case SEEDS : return 59;
		case SOIL : return 60;
		case NETHER_WARTS : return 115;
		case SOUL_SAND : return 88;
		case NETHERRACK : return 87;
		case NETHER_BRICK: return 112;
		case NETHER_BRICK_STAIRS : return  114;
		case WOOD_STEP : return 126;
		case STEP : return 44;
		case MELON_BLOCK : return 103;
		case QUARTZ_ORE : return (byte) 153 ;
		case QUARTZ_BLOCK : return (byte) 155 ;
		
		default:
			return 0;
		}
	}

	
	public static char planValueToChar(byte value)
	{
		switch (value)
		{
		case 1 : return 'S';
		case 2 : return'G';
		case 3 : return'D';
		case 4 : return'C';
		case 5 : return 'W';
		case 6 : return 'i';
		case 7 : return'B';
		case 8 : return'w';
		case 12 : return's';
		case 13 : return'G';
		case 14 : return'g';
		case 15 : return'i';
		case 16 : return'c';
		case 17 : return'L';
		case 18 : return'l';
		case 24 : return'T';
		case 31 : return'g';
		case 35 : return 'O';
		case 56 : return'd';
		case 59 : return 'i';
		case 60 : return 's';
		case 110 : return'M';
		case 85 : return'#';
		case (byte) 254: return'.';
		case (byte) 255: return'X';
		case 54 : return '*';
		case 58 : return'*';
		case 64 : return '-';
		case 63 : return '-';
		case 68 : return '-';
		case 26 : return '=';
		case 47 : return '*';
		case 50 : return '*';
		default :
			return' ';
		}
		
	}

	public static Material getPlanMaterial(byte value)
	{
		switch (value)
		{
		case 1 : return Material.STONE;
		case 2 : return Material.GRASS;
		case 3 : return Material.DIRT;
		case 4 : return Material.COBBLESTONE;
		case 5 : return Material.WOOD;
		case 6 : return Material.SAPLING;
		case 7: return Material.BEDROCK;
		case 8 : return Material.WATER;
		case 12 : return Material.SAND;
		case 13 : return Material.GRAVEL;
		case 14 : return Material.GOLD_ORE;
		case 15 : return Material.IRON_ORE;
		case 16 : return Material.COAL_ORE;
		case 17 : return Material.LOG;
		case 18 : return Material.LEAVES;
		case 24 : return Material.SANDSTONE;
		case 31 : return Material.GRASS;
		case 56 : return Material.DIAMOND_ORE;
		case 59 : return Material.WHEAT;
		case 110 : return Material.MYCEL;
		case 85 : return Material.FENCE;
		case (byte) 254: return Material.AIR;
		case (byte) 255: return Material.AIR;
		case 54 : return Material.CHEST;
		case 58 : return Material.WORKBENCH;
		case 60 : return Material.SOIL;
		case 64 : return Material.WOOD_DOOR;
		case 63 : return Material.SIGN;
		case 68 : return Material.WALL_SIGN;
		case 26 : return Material.BED_BLOCK;
		case 47 : return Material.BOOKSHELF;
		case 50 : return Material.TORCH;
		case 35 : return Material.WOOL;
		case 115 : return Material.NETHER_WARTS ;
		case 88 : return Material.SOUL_SAND;
		case 87 : return Material.NETHERRACK ;
		case 112 :return Material.NETHER_BRICK;
		case 114 : return Material.NETHER_BRICK_STAIRS ;
		case 126 : return Material.WOOD_STEP ;
		case 44 : return Material.STEP ;
		case 103 : return Material.MELON_BLOCK;
		case (byte) 153 : return Material.QUARTZ_ORE;
		case (byte) 155 : return Material.QUARTZ_BLOCK;
		
		default :
			return Material.AIR;
		}
		
	}
	
	public static String showPlanValue (byte[] mapRow )
	{
		String charRow = "";
		for (int i = 0; i < mapRow.length; i++) 
		{
			charRow = charRow + planValueToChar(mapRow[i]);
		}
		return charRow;
	}

	
	@Override
	public Boolean initConfigData()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPluginName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemList getToolItems()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemList getWeaponItems()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemList getArmorItems()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BuildPlanType regionToBuildingType(String regionTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static String setStrleft(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < zw.length; i++)
			{
				out[i] = zw[i]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < out.length; i++)
			{
				out[i] = zw[i]; 
			}
		}
		return String.valueOf(out);
	}

	public static double format2(double value)
	{
		int value100 = (int)(value * 100);
		return ((double)value100/100.0);
	}

	public static String setStrright(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			for (int i = 0; i < zw.length; i++)
			{
				out[len-i-1] = zw[zwl-i-1]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			if (zw.length <= out.length)
			{
				for (int i = 0; i < out.length; i++)
				{
					out[len-i] = zw[zwl-i]; 
				}
			} else
			{
				out[0] = '?';
				out[1] = '?';
			}
		}
		return String.valueOf(out);
	}

	@Override
	public BuildPlanType superRegionToBuildingType(String superRegionTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRegionType(BuildPlanType bType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	
}
