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
	public class PriceTable
	{
		protected String tablename = "baseprice";
		protected String[] fieldnames = new String[] { "valuename, value" };
		protected String[] fieldtypes = new String[] { String.class.getName(), Double.class.getName() };
		protected String[] indexname = new String[] {"baseprice_idx1"  };
	}
	
	private Realms plugin;

	public PriceData(Realms plugin)
	{
		this.plugin = plugin;
	}

	private String getBaseKey()
	{
		return "BASEPRICE";
	}
	
	public void writePriceData(ItemPriceList Items) 
	{
		try
		{
            File DataFile = new File(plugin.getDataFolder(), "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	//DataFile.createNewFile();
//            }
            
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
			plugin.getMessageData().errorFileIO("writeSettledata", e);
		}
	

	}
	
	public ItemPriceList readPriceData() 
	{
        String base = getBaseKey();
        ItemPriceList items = new ItemPriceList();
		try
		{
            File DataFile = new File(plugin.getDataFolder(), "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            plugin.getMessageData().log("Read Pricelist");
            
            if (config.isConfigurationSection(base))
            {
    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
    			plugin.getMessageData().log("Read "+base+" : "+buildings.size());
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            		plugin.getMessageData().log(item.ItemRef());
            	}
            }
		} catch (Exception e)
		{
			plugin.getMessageData().errorFileIO("readSettledata", e);
		}
		return items;
	}
	
	
}
