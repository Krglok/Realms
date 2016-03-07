package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.ItemList;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

public class BiomeLocationTest
{

	
	private static final int FAKTOR_0 = 0;
	private static final int FAKTOR_M = -25;
	private static final int FAKTOR_MM = -50;
	private static final int FAKTOR_MMM = -75;
	private static final int FAKTOR_P = 25;
	private static final int FAKTOR_PP = 50;
	private static final int FAKTOR_PPP = 75;

	private void showBiomeGroup(String group)
	{
		int anz = 0;
		int counter = 0;
		System.out.println("==== Biome Group === "+group);
		for (Biome biome : Biome.values())
		{
			counter++;
			if (biome.name().contains(group) == true)
			{
				anz ++;
				System.out.println(counter+":"+anz+":"+biome);
			}
		}
		
	}

	private void showBiomeBasic()
	{
		int anz = 0;
		int counter = 0;
		System.out.println("==== Biome Bsic === ");
		for (Biome biome : Biome.values())
		{
			counter++;
			if (biome.name().contains("_") == false)
			{
				anz ++;
				System.out.println(counter+":"+anz+":"+biome);
			}
		}
		
	}
	
	private int plainFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_PP;
		case SEEDS : return FAKTOR_PP;
		case COBBLESTONE: return FAKTOR_M;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_PP;
		case GOLD_NUGGET: return FAKTOR_MM;
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
			return  FAKTOR_0;
		}
	}

	private int mountainFactor(Material mat)
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
			return  FAKTOR_0;
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
		case REDSTONE_ORE : return FAKTOR_P;
		case GOLD_ORE : return FAKTOR_P;
		case LAPIS_ORE : return FAKTOR_P;
		default :
			return  FAKTOR_0;
		}
	}
	
	private int swampFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_M;
		case WOOL : return FAKTOR_0;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_0;
		case RAW_BEEF : return FAKTOR_0;
		case PORK : return FAKTOR_0;
		case RAW_CHICKEN : return FAKTOR_0;
		case FEATHER : return FAKTOR_0;
		case RAW_FISH : return FAKTOR_0;
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
			return  FAKTOR_0;
		}
	}

	private int oceanFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_MM;
		case LOG: return FAKTOR_MM;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_MM;
		case LEATHER : return FAKTOR_M;
		case RAW_BEEF : return FAKTOR_M;
		case PORK : return FAKTOR_M;
		case RAW_CHICKEN : return FAKTOR_M;
		case FEATHER : return FAKTOR_M;
		case RAW_FISH : return FAKTOR_PPP;
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
			return  FAKTOR_0;
		}
	}
	
	private int forestFactor(Material mat)
	{
		switch (mat)
		{
		case WHEAT : return FAKTOR_0;
		case SEEDS : return FAKTOR_0;
		case COBBLESTONE: return FAKTOR_0;
		case LOG: return FAKTOR_PPP;
		case WOOL : return FAKTOR_M;
		case GOLD_NUGGET: return FAKTOR_M;
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
			return  FAKTOR_0;
		}
	}
	
	private int getBioneFactor(Biome biome, Material mat)
	{
		int factor = 0;
		if (biome.name().contains("PLAIN"))
		{
			factor = factor + plainFactor(mat);
//			if (factor != 0) System.out.println("PLAIN"+":"+mat+":"+factor);
		}
		if (biome.name().contains("SWAMP"))
		{
			factor = factor + swampFactor(mat);
//			if (factor != 0) System.out.println("SWAMP"+":"+mat+":"+factor);
		}
		if (biome.name().contains("MOUNTAIN"))
		{
			factor = factor + mountainFactor(mat);
//			if (factor != 0) System.out.println("MOUNTAIN"+":"+mat+":"+factor);
		}
		if (biome.name().contains("OCEAN"))
		{
			factor = factor + oceanFactor(mat);
//			if (factor != 0) System.out.println("OCEAN"+":"+mat+":"+factor);
		}
		if (biome.name().contains("FOREST"))
		{
			factor = factor + forestFactor(mat);
//			if (factor != 0) System.out.println("FOREST"+":"+mat+":"+factor);
		}
		return factor;
	}

	private ItemArray getModifiedMaterial(Biome biome)
	{
		ItemArray items = new ItemArray();
		for (Material mat : Material.values())
		{
			
			int factor = getBioneFactor(biome, mat);
			if (factor != 0)
			{
				items.add(new Item(mat.name(),factor));
			}
		}
		return items;
	}
	
	@Test
	public void testBiomeGroups()
	{
//		showBiomeBasic();
//		showBiomeGroup("FOREST");
//		showBiomeGroup("HILL");
//		showBiomeGroup("MOUNTAIN");
//		showBiomeGroup("EXTREME");
//		showBiomeGroup("BEACH");
//		showBiomeGroup("PLAIN");
			
	}

	private void showModifiedItems(Biome biome)
	{
		ItemArray items = getModifiedMaterial(biome);
		
		System.out.println("=============================");
		System.out.println("Modified Items for "+biome);
		for (Item item : items)
		{
			System.out.println(item.ItemRef()+":"+item.value()+" %");
		}
	}
	
	@Test
	public void testBiomeModified()
	{
		showModifiedItems(Biome.PLAINS);
		showModifiedItems(Biome.OCEAN);
		showModifiedItems(Biome.SMALLER_EXTREME_HILLS);
		showModifiedItems(Biome.FOREST);
		showModifiedItems(Biome.BIRCH_FOREST_HILLS);

	}
	
//	@Test
//	public void testGetBiome()
//	{
//		System.out.println("==================");
//		System.out.println("List of Biomes ===");
//		int counter = 0;
//		for (Biome biome : Biome.values())
//		{
//			double factor = 1.0;
//			counter ++;
//			if(biome.name().contentEquals("ORE"))
//			{
//				factor = 0.5;
//			}
//			switch (biome)
//			{
//			case PLAINS :
//				System.out.println(counter+":"+biome+": "+factor+" :"+Material.WHEAT);
//				break;
//			case DESERT :
//				System.out.println(counter+":"+biome+": "+factor+" :"+Material.WHEAT);
//				break;
//			case HELL :
//				factor = 0.1;
//				System.out.println(counter+":"+biome+": "+factor+" :"+Material.WHEAT);
//				break;
//			default :
//				System.out.println(counter+":"+biome+": "+factor+" :"+"");
//				break;
//			}
//		}
//		
//	}

}
