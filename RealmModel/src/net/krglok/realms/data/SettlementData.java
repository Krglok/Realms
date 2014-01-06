package net.krglok.realms.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Position;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.model.LogList;

/**
 * read Data from YML file 
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 */
public class SettlementData
{

	private Realms plugin;
	
//	private int id;
//	private SettleType settleType = SettleType.SETTLE_NONE;
//	private Position position;
//	private String name;
//	private Owner owner;
//	private Boolean isCapital;
//	private Boolean isEnabled;
//	private Boolean isActive;
//	private Barrack barrack ;
//	private Warehouse warehouse ;
//		private Boolean isEnabled;
//		private int itemMax;
//		private int itemCount;
//		private ItemList itemList;
//
//	private BuildingList buildingList;
//		private Map<String,Building> buildingList;
//			private int id;
//			private BuildingType buildingType;
//			private int settler;
//			private int workerNeeded;
//			private int workerInstalled;
//			private Boolean iSRegion;
//			private int hsRegion;
//			private String hsRegionType;
//			private String hsSuperRegion;
//			private Boolean isEnabled;
//			private boolean isActiv;
//	
//	private Townhall townhall;
//		private Boolean isEnabled;
//		private int workerNeeded;
//		private int workerCount;
//
//	private Bank bank;
//		private Boolean isEnabled;
//		private Double konto;
//		private LogList transactionList;
//	
//	private Resident resident;
//		private int settlerMax;
//		private int settlerBirthrate;
//		private int settlerDeathrate;
//		private double fertilityCounter ;
//		private int settlerCount;
//		private int workerCount;
//		private int cowCount;
//		private int horseCount;
//		private double happiness ;
//		private ItemArray slots ;
	
//	private Wellfare wellfare;
//  private Trader trader;
//  private Headquarter headquarter;
	
//	private ItemList requiredProduction;
//
	
	public SettlementData(Realms plugin)
	{
		this.plugin = plugin;
	}

	private String getSettleKey(int id)
	{
		return "SETTLEMENT."+String.valueOf(id);
	}
	
	public void writeSettledata(Settlement settle) 
	{
		try
		{
	            File settleFile = new File(plugin.getDataFolder(), "settlement.yml");
	            if (! settleFile.exists()) 
	            {
	            	settleFile.createNewFile();
	            }
	            HashMap<String,String> values; // = new HashMap<String,String>();
	            
	            FileConfiguration config = new YamlConfiguration();
	            config.load(settleFile);
	            
	            String base = getSettleKey(settle.getId());
	            
	            ConfigurationSection settleSec = config.createSection(base);
	            config.set(MemorySection.createPath(settleSec, "id"), settle.getId());
	            config.set(MemorySection.createPath(settleSec, "settleType"), settle.getSettleType().name());
	            config.set(MemorySection.createPath(settleSec, "position"), settle.getPosition().toString());
	            config.set(MemorySection.createPath(settleSec, "name"), settle.getName());
	            config.set(MemorySection.createPath(settleSec, "owner"), settle.getOwner());
	            config.set(MemorySection.createPath(settleSec, "isCapital"), settle.getIsCapital());
//	            config.set(MemorySection.createPath(settleSec, "isEnabled"), settle.isEnabled());
	            config.set(MemorySection.createPath(settleSec, "isActive"), settle.isActive());

	            values = new HashMap<String,String>();
	            values.put("isEnabled",settle.getTownhall().getIsEnabled().toString());
	            values.put("workerNeeded",String.valueOf(settle.getTownhall().getWorkerNeeded()));
	            values.put( "workerCount",String.valueOf(settle.getTownhall().getWorkerCount()));
	            config.set(MemorySection.createPath(settleSec, "townhall"), values);

//	            ConfigurationSection residentSec = config.createSection(base+".resident");
	            values = new HashMap<String,String>();
	            values.put("settlerMax",String.valueOf(settle.getResident().getSettlerMax()));
	            values.put("settlerCount",String.valueOf(settle.getResident().getSettlerCount()));
	            values.put("fertilityCounter",String.valueOf(settle.getResident().getFertilityCounter()));
	            values.put("happiness",String.valueOf(settle.getResident().getHappiness()));
	            values.put("workerCount","0");
	            values.put("cowCount",String.valueOf(settle.getResident().getHorseCount()));
	            values.put("horseCount",String.valueOf(settle.getResident().getCowCount()));
	            config.set(MemorySection.createPath(settleSec,"resident"), values);

//	            ConfigurationSection buildingSec = config.createSection(base+".buildinglist");
	            HashMap<String,HashMap<String,String>> buildings = new HashMap<String,HashMap<String,String>>();
	            for (Building building : settle.getBuildingList().getBuildingList().values())
	            {
		            values = new HashMap<String,String>();
		            
	            	values.put("id", String.valueOf(building.getId()));
	            	values.put("buildingType", building.getBuildingType().name());
	            	values.put("settler", String.valueOf(building.getSettler()));
	            	values.put("workerNeeded", String.valueOf(building.getWorkerNeeded()));
	            	values.put("workerInstalled", String.valueOf(building.getWorkerInstalled()));
	            	values.put("isRegion", building.isRegion().toString());
	            	values.put("hsRegion", String.valueOf(building.getHsRegion()));
	            	values.put("hsRegionType", building.getHsRegionType());
	            	values.put("hsSuperRegion", building.getHsSuperRegion());
	            	values.put("isEnabled", building.isEnabled().toString());
	            	values.put("isActiv", building.isActive().toString());
	            	for (int i = 0; i < building.getSlot1().size(); i++)
					{
	            		values.put("slot"+i, building.getSlot1().get(i).ItemRef());
					}
	            	buildings.put(String.valueOf(building.getId()), values ); 
		            config.set((MemorySection.createPath(settleSec,"buildinglist")), buildings);
	            }
////	        	private Warehouse warehouse ;
////	    			private Boolean isEnabled;
////	    			private int itemMax;
////	    			private int itemCount;
////	    			private ItemList itemList;
	            values = new HashMap<String,String>();
            	values.put("itemMax", String.valueOf(settle.getWarehouse().getItemMax()));
            	values.put("itemCount", String.valueOf(settle.getWarehouse().getItemCount()));
            	values.put("isEnabled", settle.getWarehouse().getIsEnabled().toString());
	            config.set(MemorySection.createPath(settleSec,"warehouse"), values);

	            values = new HashMap<String,String>();
            	for (String itemref : settle.getWarehouse().getItemList().keySet())
            	{
            		values.put(itemref, String.valueOf(settle.getWarehouse().getItemList().getValue(itemref)) );
            	}
	            config.set(MemorySection.createPath(settleSec,"itemList"), values);
////	            config.set("", settle);
            	config.save(settleFile);
			
		} catch (Exception e)
		{
			// TODO: handle exception
			plugin.getMessageData().errorFileIO("writeSettledata", e);
		}
	

	}
	
	public Settlement readSettledata(int id) 
	{
		Settlement settle = new Settlement();
        String settleSec = getSettleKey(settle.getId());
		try
		{
            File settleFile = new File(plugin.getDataFolder(), "settlement.yml");
            if (! settleFile.exists()) 
            {
            	settleFile.createNewFile();
            }
            HashMap<String,String> values; // = new HashMap<String,String>();
            
            FileConfiguration config = new YamlConfiguration();
            config.load(settleFile);
            
            if (config.isConfigurationSection(settleSec))
            {
            	settle.setId(config.getInt(settleSec+".id"));
            	settle.setSettleType(SettleType.valueOf(config.getString(settleSec+".settleType")));
            	//settle.setposition()
            	settle.setName(config.getString(settleSec+".name"));
            	settle.setOwner(null);
            	settle.setIsCapital(config.getBoolean(settleSec+".isCapital"));
            	settle.setIsActive(config.getBoolean(settleSec+".isActive"));
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getTownhall().setIsEnabled(Boolean.valueOf(config.getString(settleSec+".townhall"+".isEnable")));
            	settle.getTownhall().setWorkerNeeded(Integer.valueOf(config.getString(settleSec+".townhall"+".workerNeeded")));
            	settle.getTownhall().setWorkerCount(Integer.valueOf(config.getString(settleSec+".townhall"+".workerCount")));
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getResident().setSettlerMax(Integer.valueOf(config.getString(settleSec+".resident"+".settlerMax")));
            	settle.getResident().setSettlerCount(Integer.valueOf(config.getString(settleSec+".resident"+".settlerCount")));
            	settle.getResident().setFeritilityCounter(Double.valueOf(config.getString(settleSec+".resident"+".fertilityCounter")));
            	settle.getResident().setHappiness(Double.valueOf(config.getString(settleSec+".resident"+".happiness")));
            	settle.getResident().setCowCount(Integer.valueOf(config.getString(settleSec+".resident"+".cowCount")));
            	settle.getResident().setHorseCount(Integer.valueOf(config.getString(settleSec+".resident"+".horseCount")));
            	
    			ArrayList<String> msg = new ArrayList<String>();
    			
    			Map<String,Object> buildings = config.getConfigurationSection(settleSec+".buildinglist").getValues(false);
            	for (String ref : buildings.keySet())
            	{
                	System.out.println("Part 5 "+ref);

            		int buildingId = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".id"));
            		BuildingType buildingType = BuildingType.getBuildingType(config.getString(settleSec+".buildinglist."+ref+".buildingType"));
            		int settler = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".settler"));
            		int workerNeeded = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerNeeded"));
            		int workerInstalled = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerInstalled"));
            		Boolean isRegion = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isRegion"));
            		int hsRegion = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".hsRegion"));
        			String hsRegionType = config.getString(settleSec+".buildinglist."+ref+".hsRegionType");
        			String hsSuperRegion = config.getString(settleSec+".buildinglist."+ref+".hsSuperRegion");
        			Boolean isEnabled = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isEnabled"));
        			boolean isActiv = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isActiv"));
        			String slot1 = config.getString(settleSec+".buildinglist."+ref+".slot1","");
        			String slot2 = config.getString(settleSec+".buildinglist."+ref+".slot2","");
        			String slot3 = config.getString(settleSec+".buildinglist."+ref+".slot3","");
        			String slot4 = config.getString(settleSec+".buildinglist."+ref+".slot4","");
        			String slot5 = config.getString(settleSec+".buildinglist."+ref+".slot5","");
        			
        			Settlement.addBuilding(
        					new Building(
        							buildingId, 
        							buildingType, 
        							settler, 
        							workerNeeded, 
        							workerInstalled, 
        							isRegion, 
        							hsRegion, 
        							hsRegionType, 
        							hsSuperRegion, 
        							isEnabled, 
        							slot1, 
        							slot2, 
        							slot3, 
        							slot4, 
        							slot5), 
        					settle
        					);
            	}
            }
		} catch (Exception e)
		{
			// TODO: handle exception
			plugin.getMessageData().errorFileIO("readSettledata", e);
		}
		return settle;
	}
}
