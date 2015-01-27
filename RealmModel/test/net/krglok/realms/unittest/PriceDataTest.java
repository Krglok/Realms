package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.data.PriceData;
import net.krglok.realms.data.PriceTable;
import net.krglok.realms.data.SQliteConnection;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.junit.Test;

public class PriceDataTest {

	private void getRecipe(String itemRef)
	{
		ItemStack item = new ItemStack(Material.valueOf(itemRef));
		ShapedRecipe recipe = new ShapedRecipe(item);
		Map<Character, ItemStack> ingredientMap = recipe.getIngredientMap();
        System.out.print(itemRef+"|");
        for (ItemStack ingred  : ingredientMap.values()) 
        {
            System.out.print(ingred.getType().name()+"|");
        }
        System.out.print(itemRef+"|");
	}
	
	@Test
	public void testWritePriceData()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		String basekey = "BASEPRICE";
		Logger logger = Logger.getAnonymousLogger();
		//		SQLite(Logger logger, String prefix, String directory, String filename) 
		 PriceData priceData = new PriceData(path);
		
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
       long time1 = System.nanoTime();
       priceData.writePriceData(items);
//       priceData.replaceItemValue(basekey, "WHEAT", 0.20);
//       priceTable.replaceItemValue(basekey, "LOG", 0.50);
       long time2 = System.nanoTime();
       System.out.println("Update Time [ms]: "+(time2 - time1)/1000000);

       time1 = System.nanoTime();
       ItemPriceList result = priceData.readPriceData();
       time2 = System.nanoTime();
       System.out.println("Select Time [us]: "+(time2 - time1)/1000);
       int row = 1;
       for (ItemPrice item : result.values())
	   {
		   System.out.print(ConfigBasis.setStrright(row,2)+":");
		   System.out.print(ConfigBasis.setStrleft(item.ItemRef(),13));
		   System.out.print(":"+ConfigBasis.setStrright(item.getBasePrice(),7));
		   System.out.println();
	   }
		
       System.out.println("Recipe: ");
       getRecipe(Material.STONE_AXE.name());
	}


}
