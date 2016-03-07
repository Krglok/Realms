package net.krglok.realms.core;

import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * Kapselt die Biome Defintion umd Material Listen
 * 
 * @author Windu
 *
 */


public class ConfigBiome
{

	public static final int FAKTOR_0 = 0;
	public static final int FAKTOR_N = 0;
	public static final int FAKTOR_M = -25;
	public static final int FAKTOR_MM = -75;
	public static final int FAKTOR_MMM = -100;
	public static final int FAKTOR_P = 25;
	public static final int FAKTOR_PP = 50;
	public static final int FAKTOR_PPP = 75;

	
	public ConfigBiome()
	{
		
	}
	
	private static int plainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_PP;
		case SEEDS : return FAKTOR_PP;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}

	private static int mountainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_P;
		case RAW_BEEF : return FAKTOR_P;
		case PORK : return FAKTOR_P;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}

	private int hillFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_P;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_0;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_0;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_P;
		case COAL_ORE : return FAKTOR_P;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_0;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}
	
	private static int swampFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case COOKED_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case GRILLED_PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_PP;
		case COOKED_CHICKEN : return FAKTOR_PP;
		case FEATHER : return FAKTOR_PP;
		case RAW_FISH : return FAKTOR_P;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PP; 
		case BROWN_MUSHROOM : return FAKTOR_PP; 
		case IRON_ORE : return FAKTOR_MM;
		case COAL_ORE : return FAKTOR_MM;
		case DIAMOND_ORE : return FAKTOR_MM;
		case EMERALD_ORE : return FAKTOR_MM;
		case REDSTONE_ORE : return FAKTOR_MM;
		case GOLD_ORE : return FAKTOR_MM;
		case LAPIS_ORE : return FAKTOR_MM;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}

	private static int oceanFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MMM;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
//		case RAW_FISH : return FAKTOR_PPP;
		case COOKED_FISH : return FAKTOR_PPP;
		case EMERALD : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_MMM;
		case COAL_ORE : return FAKTOR_MMM;
		case DIAMOND_ORE : return FAKTOR_MMM;
		case EMERALD_ORE : return FAKTOR_MMM;
		case LAPIS_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_MMM;
		case GOLD_ORE : return FAKTOR_MMM;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}
	
	private static int forestFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_0;
		case LOG: return FAKTOR_PPP;
		case WOOD: return FAKTOR_PPP;
		case STICK: return FAKTOR_PPP;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_MM;
		case EMERALD : return FAKTOR_M;
		case RED_MUSHROOM : return FAKTOR_P; 
		case BROWN_MUSHROOM : return FAKTOR_P; 
		case IRON_ORE : return FAKTOR_0;
		case COAL_ORE : return FAKTOR_0;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_0;
		case LAPIS_ORE : return FAKTOR_0;
		case GOLD_ORE : return FAKTOR_0;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}
	
	private static int desertFactor(Material mat)
	{
		switch (mat)
		{
		case SAND : return FAKTOR_PPP;
		case WHEAT : return FAKTOR_M;
		case SEEDS : return FAKTOR_M;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_P;
		case GOLD_NUGGET: return FAKTOR_M;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_P;
		case FEATHER : return FAKTOR_P;
		case RAW_FISH : return FAKTOR_M;
		case EMERALD : return FAKTOR_P;
		case RED_MUSHROOM : return FAKTOR_MM; 
		case BROWN_MUSHROOM : return FAKTOR_MM; 
		case IRON_ORE : return FAKTOR_M;
		case COAL_ORE : return FAKTOR_M;
		case DIAMOND_ORE : return FAKTOR_0;
		case EMERALD_ORE : return FAKTOR_0;
		case REDSTONE_ORE : return FAKTOR_M;
		case LAPIS_ORE : return FAKTOR_M;
		case GOLD_ORE : return FAKTOR_M;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}

	private static int extremeFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MMM;
		case SEEDS : return FAKTOR_MMM;
		case COBBLESTONE: return FAKTOR_PP;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_MMM;
		case EMERALD : return FAKTOR_PP;
		case RED_MUSHROOM : return FAKTOR_0; 
		case BROWN_MUSHROOM : return FAKTOR_0; 
		case IRON_ORE : return FAKTOR_PP;
		case COAL_ORE : return FAKTOR_PP;
		case DIAMOND_ORE : return FAKTOR_P;
		case EMERALD_ORE : return FAKTOR_P;
		case REDSTONE_ORE : return FAKTOR_0;
		case LAPIS_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_0;
		default :
			return  FAKTOR_N; //FAKTOR_0;
		}
	}

	private static int hellFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_MM;
		case SEEDS : return FAKTOR_MM;
		case WOOL : return FAKTOR_MM;
		case GOLD_NUGGET: return FAKTOR_PPP;
		case LEATHER : return FAKTOR_MM;
		case RAW_BEEF : return FAKTOR_MM;
		case PORK : return FAKTOR_MM;
		case RAW_CHICKEN : return FAKTOR_MM;
		case FEATHER : return FAKTOR_MM;
		case RED_MUSHROOM : return FAKTOR_PPP; 
		case BROWN_MUSHROOM : return FAKTOR_PPP;
		case LAVA : return FAKTOR_PPP;
		case NETHERRACK : return FAKTOR_PPP;
		case SOUL_SAND : return FAKTOR_PPP;
		case GHAST_TEAR : return FAKTOR_PPP;
		case MAGMA_CREAM : return FAKTOR_PPP;
		
		default :
			return  FAKTOR_M; //FAKTOR_0;
		}
	}
	
	public static int getBiomeFactor(Biome biome, Material mat)
	{
		if (mat == null)
		{
			mat = Material.AIR;
		}
		int factor = FAKTOR_0;
;
		if (biome == null)
		{
			return factor;
		}
		if (biome.name().contains("PLAIN"))
		{
			factor = factor + plainFactor(mat);
		}
		if (biome.name().contains("SWAMP"))
		{
			factor = factor + swampFactor(mat);
		}
		if (biome.name().contains("MOUNTAIN"))
		{
			factor = factor + mountainFactor(mat);
		}
		if (biome.name().contains("OCEAN"))
		{
			factor = factor + oceanFactor(mat);
		}
		if (biome.name().contains("FOREST"))
		{
			factor = factor + forestFactor(mat);
		}
		if (biome.name().contains("DESERT"))
		{
			factor = factor + desertFactor(mat);
		}
		if (biome.name().contains("EXTREME"))
		{
			factor = factor + extremeFactor(mat);
		}
		if (biome.name().contains("HELL"))
		{
			factor = factor + hellFactor(mat);
		}
		return factor;
	}
	
	public static ItemList getBiomeMaterial(Biome biome)
	{
		ItemList refList = ConfigBasis.initArmor();
		refList.addAll(ConfigBasis.initBuildMaterial());
		refList.addAll(ConfigBasis.initFoodMaterial());
		refList.addAll(ConfigBasis.initMaterial());
		refList.addAll(ConfigBasis.initOre() );
		refList.addAll(ConfigBasis.initRawMaterial() );
		refList.addAll(ConfigBasis.initTool() );
		refList.addAll(ConfigBasis.initValuables());
		refList.addAll(ConfigBasis.initWeapon());
		ItemList itemList = new ItemList();
		for (Item item : refList.values())
		{
			int factor = getBiomeFactor(biome,Material.getMaterial(item.ItemRef()));
			if (factor != 0)
			{
				itemList.addItem(item.ItemRef(), factor);
			}
		}
		return itemList;
	}
	
	public static ItemList getBiomeNeutralMaterial(Biome biome)
	{
		ItemList refList = ConfigBasis.initArmor();
		refList.addAll(ConfigBasis.initBuildMaterial());
		refList.addAll(ConfigBasis.initFoodMaterial());
		refList.addAll(ConfigBasis.initMaterial());
		refList.addAll(ConfigBasis.initOre() );
		refList.addAll(ConfigBasis.initRawMaterial() );
		refList.addAll(ConfigBasis.initTool() );
		refList.addAll(ConfigBasis.initValuables());
		refList.addAll(ConfigBasis.initWeapon());
		ItemList itemList = new ItemList();
		for (Item item : refList.values())
		{
			int factor = getBiomeFactor(biome,Material.getMaterial(item.ItemRef()));
			if (factor == 0)
			{
				itemList.addItem(item.ItemRef(), factor);
			}
		}
		return itemList;
	}
	
}
