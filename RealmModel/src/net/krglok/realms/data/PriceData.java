package net.krglok.realms.data;

import java.io.File;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;

public class PriceData
{

	private String path;

	public PriceData(String path)
	{
		this.path = path;
	}

	private String getBaseKey()
	{
		return "BASEPRICE";
	}
	
	public void writePriceData(ItemPriceList Items) 
	{
		try
		{
            File DataFile = new File(this.path, "baseprice.yml");
            if (!DataFile.exists()) 
            {
            	DataFile.createNewFile();
            }
            
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
			System.out.println("[REALMS] writePricedata"+ e.getMessage());
		}
	

	}
	
	public ItemPriceList readPriceData() 
	{
        String base = getBaseKey();
        ItemPriceList items = new ItemPriceList();
		try
		{
            File DataFile = new File(this.path, "baseprice.yml");
            if (!DataFile.exists()) 
            {
            	DataFile.createNewFile();
            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            System.out.println("[REALMS] Read Pricelist");
            
            if (config.isConfigurationSection(base))
            {
    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
//    			System.out.println("Read "+base+" : "+buildings.size());
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
//            		System.out.println(item.ItemRef());
            	}
            }
		} catch (Exception e)
		{
			System.out.println("[REALMS] readSettledata"+ e.getMessage());
		}
		return items;
	}
	
	
}
