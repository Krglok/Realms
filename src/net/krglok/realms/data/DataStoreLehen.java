package net.krglok.realms.data;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.Common.AbstractDataStore;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.configuration.ConfigurationSection;




public class DataStoreLehen extends AbstractDataStore<Lehen>
{

	public DataStoreLehen(String dataFolder)
	{
		super(dataFolder, "lehen", "LEHEN", false, null);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param Lehen dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Lehen dataObject)
	{
//		private int id;
//		private String name;
//		private NobleLevel nobleLevel;
//		private SettleType settleType;
//		private Owner owner;
//		private int KingdomId;
//		private int parentId;

		HashMap<String,String> values; // = new HashMap<String,String>();

		section.set("id", dataObject.getId());
		section.set("name", dataObject.getName());
		section.set("nobleLevel", dataObject.getNobleLevel().name());
		section.set("settleType", dataObject.getSettleType().name());
		section.set("owner", dataObject.getOwner().getPlayerName());
		section.set("kingdomId", dataObject.getKingdomId());
		section.set("parentId", dataObject.getParentId());
		section.set("supportId", dataObject.getSupportId());
        section.set( "bank", dataObject.getBank().getKonto());
    	section.set("position", LocationData.toString(dataObject.getPosition()));
    	section.set("isEnabled", dataObject.isEnabled());

        values = new HashMap<String,String>();
    	values.put("itemMax", String.valueOf(dataObject.getWarehouse().getItemMax()));
    	values.put("itemCount", String.valueOf(dataObject.getWarehouse().getItemCount()));
    	values.put("isEnabled", dataObject.getWarehouse().getIsEnabled().toString());
        section.set("warehouse", values);

        values = new HashMap<String,String>();
    	for (String itemref : dataObject.getWarehouse().getItemList().keySet())
    	{
    		if (dataObject.getWarehouse().getItemList().getValue(itemref) > 0)
    		{
    			values.put(itemref, String.valueOf(dataObject.getWarehouse().getItemList().getValue(itemref)) );
    		}
    	}
        section.set("itemList", values);
    	
	}


	/**
	 * Override this for the concrete class

	 * @return Lehen , real data Class
	 */
	@Override
	public Lehen initDataObject(ConfigurationSection data)
	{
//		// 
		Lehen lehen = new Lehen();
		
		lehen.setId(data.getInt("id"));
		lehen.setName(data.getString("name"));
		String ref = data.getString("nobleLevel");
		lehen.setNobleLevel(NobleLevel.valueOf(ref));
		ref = data.getString("settleType");
		lehen.setSettleType(SettleType.valueOf(ref));
		lehen.setOwnerId(data.getInt("owner"));
		lehen.setKingdomId(data.getInt("kingdomId", 0));
		lehen.setParentId(data.getInt("parentId",0));
		lehen.setSupportId(data.getInt("supportId",0));
		lehen.getBank().addKonto(data.getDouble( "bank",0.0),"SettleRead",lehen.getId());
		LocationData position = LocationData.toLocation(data.getString("position"));
		lehen.setPosition(position);
		lehen.setIsEnabled(Boolean.valueOf(data.getString( "isEnable","true")));
//		System.out.println("Lehen read Warehouse");
		lehen.getWarehouse().setItemMax(Integer.valueOf(data.getString( "warehouse"+".itemMax","0")));
		lehen.getWarehouse().setIsEnabled(Boolean.valueOf(data.getString( "warehouse"+".isEnable","true")));
	    
//		System.out.println("Lehen read Itemlist");
		if (data.isConfigurationSection("itemList"))
		{
		    ItemList iList = new ItemList();
			Map<String,Object> itemList = data.getConfigurationSection( "itemList").getValues(false);
			if (itemList != null)
			{
		    	for (String refKey : itemList.keySet())
		    	{
		    		int value = Integer.valueOf(data.getString( "itemList."+refKey,"0"));
	//	                System.out.println(ref+":"+value);
		            iList.addItem(refKey, value);
		    	}    
		    	lehen.getWarehouse().setItemList(iList);
			}
		}
		return lehen;
	}
	
}
