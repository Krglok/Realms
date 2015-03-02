package net.krglok.realms.data;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.PositionFace;
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
        section.set("age", dataObject.getAge());
        section.set("regimentType", dataObject.getRegimentType().name());
        section.set("regStatus", dataObject.getRegStatus().name());
        section.set("position", LocationData.toString(dataObject.getPosition()));
        section.set("target", LocationData.toString(dataObject.getTarget()));
        section.set("home", LocationData.toString(dataObject.getHomePosition()));
        section.set("name", dataObject.getName());
        section.set("owner", dataObject.getOwnerId());
        section.set("bank", dataObject.getBank().getKonto());
        section.set("isActiv", dataObject.isActive());
        section.set("isPlayer", dataObject.isPlayer());
        section.set("settleId", dataObject.getSettleId());
        section.set("supportId", dataObject.getSupportId());
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
    		if (dataObject.getWarehouse().getItemList().getValue(itemref) > 0)
    		{
    			values.put(itemref, String.valueOf(dataObject.getWarehouse().getItemList().getValue(itemref)) );
    		}
    	}
        section.set("itemList", values);

        values = new HashMap<String,String>();
        for (Integer campPos : dataObject.getCampList().keySet())
        {
        	
        	values.put(String.valueOf(campPos),dataObject.getCampList().get(campPos).name());
        }
        section.set("camppos", values);
        
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
    	regiment.setAge(data.getInt("age",0));
    	String sType = data.getString("regimentType","NONE");
    	regiment.setRegimentType(RegimentType.valueOf(sType));
    	regiment.setPosition(LocationData.toLocation(data.getString( "position")));
    	regiment.setTarget(LocationData.toLocation(data.getString("target")));
    	regiment.setHomePosition(LocationData.toLocation(data.getString("home")));
    	regiment.setName(data.getString("name",""));
    	regiment.setOwnerId(data.getInt("owner",0));
    	regiment.setIsActive(data.getBoolean("isActive",true));
    	regiment.setIsPlayer(data.getBoolean("isPlayer",false));
    	regiment.setSettleId(data.getInt("settleId",0));
    	regiment.setSupportId(data.getInt("supportId",0));
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
	        		int value = Integer.valueOf(data.getString("itemList."+ref,"0"));
	                System.out.println(ref+":"+value);
	                iList.addItem(ref, value);
	        	}    
	        	regiment.getWarehouse().setItemList(iList);
			}
        }
        if (data.contains("camppos"))
        {
			Map<String,Object> campList = data.getConfigurationSection("camppos").getValues(false);
			if (campList != null)
			{
	        	for (String ref : campList.keySet())
	        	{
	        		PositionFace value = PositionFace.valueOf(data.getString("camppos."+ref,"NORTH"));
	        		regiment.getCampList().put(Integer.valueOf(ref), value);
	        	}
			}
        }
		return regiment;
	}

}
