package net.krglok.realms.unittest;

import java.io.File;
import java.util.Map;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class PriceDataTest
{

	@Test
	public void testWritePriceData()
	{
		ItemPriceList items = new ItemPriceList();
		for (Material mat : Material.values())
		{
			if (mat.name().contains("IRON"))
			{
				items.add(mat.name(), 1.0);
			}
		}
		items.add("WHEAT", 0.30);
		items.add("LOG", 0.5);
		items.add("COBBLESTONE", 0.5);
		items.add("SAND", 0.5);
		items.add("STONE", 1.7);
		items.add("IRON_INGOT", 56.0);
		items.add("GOLD_INGOT", 400.0);
		items.add("WOOD", 0.1666);
		items.add("STICK", 0.0555);
		items.add("WOOD_AXE", 1.25);
		items.add("WOOD_PICKAXE", 1.25);
		items.add("WOOD_HOE", 1.00);
		items.add("WOOD_SWORD", 0.6);
		items.add("BREAD", 1.0);
		items.add("COAL", 3.0);
		items.add("IRON_ORE", 15.0);
		items.add("IRON_SWORD", 235.0);
	//	writePriceData(items);
		
	}

	@Test
	public void testReadPriceData()
	{
		ItemPriceList items = new ItemPriceList();
		items = readPriceData();
		for (ItemPrice item : items.values())
		{
			if (Material.getMaterial(item.ItemRef()) == Material.WOOD)
			{
				Material mat = Material.getMaterial(item.ItemRef());
				ItemStack  itemStack = new ItemStack(mat);
				System.out.println("=="+itemStack.getType().name());
			}
			System.out.println(Material.getMaterial(item.ItemRef()).name()+" : "+item.getBasePrice());
		}
	}

	
	
	private String getBaseKey()
	{
		return "BASEPRICE";
	}
	
	public void writePriceData(ItemPriceList Items) 
	{
		try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\plugins\\Realms", "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
            System.out.println(DataFile.exists());
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            String base = getBaseKey();
            ConfigurationSection settleSec = config.createSection(base);
            for (ItemPrice item : Items.values())
            {
            	config.set(MemorySection.createPath(settleSec, item.ItemRef()),item.getBasePrice());
            }
            config.save(DataFile);
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	

	}
	
	public ItemPriceList readPriceData() 
	{
        String base = getBaseKey();
        ItemPriceList items = new ItemPriceList();
		try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms", "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection(base))
            {
            	
    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            	}
            }
		} catch (Exception e)
		{
		}
		return items;
	}

	@Test
	public void testRecipeCost()
	{
		ItemPriceList itemPrices = new ItemPriceList();
		itemPrices = readPriceData();
		
//		recipe.getRecipe(itemRef);
		System.out.println("=====================");
		
//		for (ItemPrice item : itemPrices.values())
//		{
//			System.out.println(item.ItemRef()+" : "+item.getBasePrice());
//		}
		
		ServerTest server = new ServerTest();
		
		ItemList  ingredients = new ItemList();
		
		String regionType = "prod_waxe";
		ingredients = server.getRegionUpkeep(regionType);
		
		double prodCost = 0.0;
		double basePrice = 0.0;
		System.out.println("upkeep: "+regionType+":"+ingredients.size());
		for (String itemRef : ingredients.keySet())
		{
			prodCost = prodCost + (ingredients.getValue(itemRef) * itemPrices.getBasePrice(itemRef) * 1.25);
			System.out.println(itemRef+": "+ingredients.getValue(itemRef)+" : "+itemPrices.getBasePrice(itemRef)*1.25);
			
		}
		ingredients.clear();
		ingredients = server.getRegionOutput(regionType);
		System.out.println("output: "+regionType+":"+ingredients.size());
		for (String itemRef : ingredients.keySet())
		{
			
			basePrice = (prodCost / ingredients.getValue(itemRef)/0.75);
			System.out.println(itemRef+": "+ingredients.getValue(itemRef)+" : "+(prodCost / ingredients.getValue(itemRef))+" : "+basePrice);
			System.out.println("Ertrag : "+(basePrice*ingredients.getValue(itemRef)-prodCost)+ " : "+(basePrice*ingredients.getValue(itemRef)-prodCost)*30);
			System.out.println("Steuer : "+(basePrice*ingredients.getValue(itemRef)-prodCost)*10.0/100.0*30);
		
		}
//		System.out.println("Pricelist =================");
//		for (ItemPrice ip : itemPrices.values())
//		{
//			System.out.println("-"+ip.ItemRef()+" : "+ip.getBasePrice());
//		}
	}
	
}
