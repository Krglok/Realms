package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.data.PriceTable;

import org.bukkit.Material;
import org.junit.Test;

public class PriceDataTest {

	@Test
	public void testWritePriceData()
	{
       String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
       String basekey = "BASEPRICE";
       Logger logger = Logger.getAnonymousLogger();
//		SQLite(Logger logger, String prefix, String directory, String filename) 
       Database sql = new SQLite(logger, 
	             "[Realms] ", 
	             path, 
	             "realms" 
	             );
       if (sql.open() == false)
       {
           System.out.println("Database NOT open !");
    	   return;
       }
       System.out.println("Database open !");
       PriceTable priceTable = new PriceTable(sql);
       System.out.println("Table open !");
		
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
//       priceTable.replaceItemValue(basekey, "WHEAT", 0.20);
//       priceTable.replaceItemValue(basekey, "LOG", 0.50);
       ResultSet result = priceTable.selectPricedata();
       try 
       {
    	   if (result.getRow() == 0)
    	   {
		       System.out.println("ResulSet empty");
    	   } else
    	   {
    		   System.out.println("ResulSet "+result.getRow());
    	   }
       } catch (SQLException e) {
			e.printStackTrace();
       }
       System.out.print("ENDE");
		
	}


}
