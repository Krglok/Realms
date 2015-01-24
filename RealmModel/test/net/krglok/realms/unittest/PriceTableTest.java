package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.data.DataStorePrice;
import net.krglok.realms.data.PriceTable;
import net.krglok.realms.data.SQliteConnection;

import org.bukkit.Material;
import org.junit.Test;

public class PriceTableTest
{

	@Test
	public void test()
	{
	       String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
	       String basekey = "BASEPRICE";
	       String fileName = "pricetest";
	       Logger logger = Logger.getAnonymousLogger();
//			SQLite(Logger logger, String prefix, String directory, String filename) 
	       DataStorePrice dPrice = new DataStorePrice(path, fileName, basekey, true); 
			
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
	       dPrice.writeData(items, "");

	       items.clear();
	       dPrice.readDataList();
	       items = dPrice.readData("");
		   System.out.print(ConfigBasis.setStrleft("ItenName",13));
		   System.out.print(":"+ConfigBasis.setStrright("Value",5));
		   System.out.println();
		   for (ItemPrice item : items.values())
		   {
    		   System.out.print(ConfigBasis.setStrleft(item.ItemRef(),13));
    		   System.out.print(":"+ConfigBasis.setStrright(item.getBasePrice(),7));
    		   System.out.println();
		   }
	       System.out.print("ENDE");
	}

}
