package net.krglok.realms.data;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import net.krglok.realms.Common.AbstractDataStore;
import net.krglok.realms.Common.ItemPrice;
import net.krglok.realms.Common.ItemPriceList;

public class DataStorePrice extends AbstractDataStore<ItemPriceList>
{
	

	public DataStorePrice(String dataFolder, String fileName,
			String sectionName, boolean timeMessure)
	{
		super(dataFolder, fileName, sectionName, true, null);
	}

	public DataStorePrice(String dataFolder, boolean timeMessure)
	{
		super(dataFolder, "baseprice", "BASEPRICE", timeMessure, null);
	}

	public DataStorePrice(String dataFolder)
	{
		super(dataFolder, "baseprice", "BASEPRICE", true, null);
	}
	
	@Override
	public void initDataSection(ConfigurationSection section, ItemPriceList dataObject)
	{
//		            for (ItemPrice item : Items.values())
        String base = getKey("");
//        ConfigurationSection objectSection = config.createSection(base);
        for (ItemPrice item : dataObject.values())
        {
        	config.set(MemorySection.createPath(section, item.ItemRef()),item.getBasePrice());
        }
// 

	}

	@Override
	public ItemPriceList initDataObject(ConfigurationSection data)
	{
//		// 
        ItemPriceList items = new ItemPriceList();
        String base = getKey("");
        
		Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
//		System.out.println("Read "+base+" : "+buildings.size());
    	for (String ref : buildings.keySet())
    	{
    		Double price = config.getDouble(base+"."+ref,0.0);
    		ItemPrice item = new ItemPrice(ref, price);
    		items.add(item);
//    		System.out.println(item.ItemRef());
    	}
		
		return items;
	}
	
}
