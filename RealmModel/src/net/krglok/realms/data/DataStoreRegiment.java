package net.krglok.realms.data;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;
import net.krglok.realms.unit.RegimentType;

import org.bukkit.configuration.ConfigurationSection;

public class DataStoreRegiment extends AbstractDataStore<Regiment>
{

	public DataStoreRegiment(String dataFolder)
	{
		super(dataFolder, "regiment", "REGIMENT", true, null);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Regiment dataObject)
	{
        section.set("id", dataObject.getId());
        section.set("regimentType", dataObject.getRegimentType().name());
        section.set("regStatus", dataObject.getRegStatus().name());
        section.set("position", LocationData.toString(dataObject.getPosition()));
        section.set("target", LocationData.toString(dataObject.getTarget()));
        section.set("name", dataObject.getName());
        section.set("owner", dataObject.getOwnerId());
        section.set("bank", dataObject.getBank().getKonto());
        section.set("isActiv", dataObject.getIsActive());
        section.set("settleId", dataObject.getSettleId());
        section.set("MaxUnit", dataObject.getBarrack().getUnitMax());
		HashMap<String,String> values; // = new HashMap<String,String>();
        //warehouse write
        values = new HashMap<String,String>();
    	values.put("itemMax", String.valueOf(dataObject.getWarehouse().getItemMax()));
    	values.put("itemCount", String.valueOf(dataObject.getWarehouse().getItemCount()));
    	values.put("isEnabled", dataObject.getWarehouse().getIsEnabled().toString());
        section.set("warehouse", values);
        //itemList Warehouse
        values = new HashMap<String,String>();
    	for (String itemref : dataObject.getWarehouse().getItemList().keySet())
    	{
    		values.put(itemref, String.valueOf(dataObject.getWarehouse().getItemList().getValue(itemref)) );
    	}
        section.set("itemList", values);
	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public Regiment initDataObject(ConfigurationSection data)
	{
		Regiment regiment = new Regiment(); //null);

    	regiment.setId(data.getInt("id",0));
    	String sType = data.getString("regimentType","NONE");
    	regiment.setRegimentType(RegimentType.valueOf(sType));
    	regiment.setPosition(LocationData.toLocation(data.getString( "position")));
    	regiment.setPosition(LocationData.toLocation(data.getString("target")));
    	regiment.setName(data.getString("name",""));
    	regiment.setOwnerId(data.getInt("owner",0));
    	regiment.setIsActive(data.getBoolean("isActive",true));
    	regiment.setSettleId(data.getInt("settleId",0));
    	regiment.getBarrack().setUnitMax(data.getInt("MaxUnit",0));
        regiment.setRegStatus(RegimentStatus.IDLE);

        regiment.getWarehouse().setItemMax(data.getInt("warehouse"+".itemMax",0));
        regiment.getWarehouse().setIsEnabled(data.getBoolean("warehouse"+".isEnable",false));
        
        if (data.contains("itemList"))
        {
            ItemList iList = new ItemList();
			Map<String,Object> itemList = data.getConfigurationSection("itemList").getValues(false);
			if (itemList != null)
			{
	        	for (String ref : itemList.keySet())
	        	{
	        		int value = config.getInt("itemList."+ref,0);
	//                System.out.println(ref+":"+value);
	                iList.addItem(ref, value);
	        	}    
	        	regiment.getWarehouse().setItemList(iList);
			}
        }
		return regiment;
	}

}
