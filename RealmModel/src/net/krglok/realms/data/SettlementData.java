package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.Unit;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

/**
 * read Data from YML file 
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 */
public class SettlementData
{

//	private Realms plugin;
	private String dataFolder; 
//	private ItemList requiredProduction;
//
	
	public SettlementData(String dataFolder)		//Realms plugin)
	{
//		this.plugin = plugin;
		this.dataFolder = dataFolder; //+"Realms";
//		System.out.println("SettlementData: "+dataFolder.getName());
	}

	private String getSettleKey(int id)
	{
		return "SETTLEMENT."+String.valueOf(id);
	}
	
	public void writeSettledata(Settlement settle) 
	{
		try
		{ 
	            File settleFile = new File(dataFolder, "settlement.yml");
	            if (!settleFile.exists()) 
	            {
	            	System.out.println("WRITE :  "+settle.getId()+":"+dataFolder+""+" not Exist !!!");
		            return;
	            }
//            	settleFile.setWritable(true);
//	            
	            System.out.println("WRITE :  "+settle.getId()+":"+dataFolder+":"+"settlement.yml");
				HashMap<String,String> values; // = new HashMap<String,String>();
	            FileConfiguration config = new YamlConfiguration();
//	            String fileName = dataFolder+"//settlement.yml";
	            config.load(settleFile);
//	            System.out.println("LOAD : "+settle.getId()+": "+fileName);
            
	            String base = getSettleKey(settle.getId());
	            
	            ConfigurationSection settleSec = config.createSection(base);
            
	            config.set(MemorySection.createPath(settleSec, "id"), settle.getId());
	            config.set(MemorySection.createPath(settleSec, "settleType"), settle.getSettleType().name());
	            config.set(MemorySection.createPath(settleSec, "position"), LocationData.toString(settle.getPosition()));
	            config.set(MemorySection.createPath(settleSec, "biome"), settle.getBiome().name());
	            config.set(MemorySection.createPath(settleSec, "age"), settle.getAge());
	            config.set(MemorySection.createPath(settleSec, "name"), settle.getName());
	            config.set(MemorySection.createPath(settleSec, "owner"), settle.getOwner());
	            config.set(MemorySection.createPath(settleSec, "isCapital"), settle.getIsCapital());
//	            config.set(MemorySection.createPath(settleSec, "isEnabled"), settle.isEnabled());
	            config.set(MemorySection.createPath(settleSec, "isActive"), settle.isActive());
	            config.set(MemorySection.createPath(settleSec, "bank"), settle.getBank().getKonto());

//	            System.out.println("SAVE : 1");
//
	            values = new HashMap<String,String>();
	            values.put("isEnabled",settle.getTownhall().getIsEnabled().toString());
	            values.put("workerNeeded",String.valueOf(settle.getTownhall().getWorkerNeeded()));
	            values.put( "workerCount",String.valueOf(settle.getTownhall().getWorkerCount()));
	            config.set(MemorySection.createPath(settleSec, "townhall"), values);

//	            System.out.println("SAVE : 2");

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

//	            System.out.println("SAVE : 3");
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
	            	values.put("position", LocationData.toString(building.getPosition()));
	            	for (int i = 0; i < building.getSlots().length; i++)
					{
	            		if (building.getSlots()[i] != null)
	            		{
	            			if (building.getSlots()[i].ItemRef() != "")
	            			{
	            				values.put("slot"+i, building.getSlots()[i].ItemRef());
	            			}
	            		}
					}
	            	buildings.put(String.valueOf(building.getId()), values ); 
		            config.set((MemorySection.createPath(settleSec,"buildinglist")), buildings);
	            }

//	            System.out.println("SAVE : 4");

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
//	            System.out.println("SAVE : "+settle.getId()+": "+fileName);
	            values = new HashMap<String,String>();
	            int index = 0;
            	for (Unit unit : settle.getBarrack().getUnitList())
            	{
            		values.put(String.valueOf(index), unit.getUnitType().name());
            		index++;
            	}
	            config.set(MemorySection.createPath(settleSec,"unitlist"), values);

	            try
				{
	            	config.save(settleFile); // dataFolder+"settlement.yml");
				} catch (Exception e)
				{
		            System.out.println("ECXEPTION : "+settle.getId()+": "+dataFolder+""+"settlement.yml");
				}
//	            System.out.println("WRITTEN : "+settle.getId()+": "+dataFolder+""+"settlement.yml");
			
		} catch (Exception e)
		{
			 @SuppressWarnings("unused")
			String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
		}
	

	}
	
	public Settlement readSettledata(int id, ItemPriceList priceList, LogList logList) 
	{
		Settlement settle = new Settlement(priceList, logList);
        String settleSec = getSettleKey(id);
		try
		{
            File settleFile = new File( dataFolder, "settlement.yml");
            FileConfiguration config = new YamlConfiguration();
            config.load(settleFile);
//            System.out.println(settleFile.getName()+":"+settleFile.length());
            System.out.println("READ : "+id+":"+dataFolder+":"+"settlement.yml");
            if (config.isConfigurationSection(settleSec))
            {
//                System.out.println(settleSec);
            	settle.setId(config.getInt(settleSec+".id"));
            	settle.setSettleType(SettleType.valueOf(config.getString(settleSec+".settleType")));
            	settle.setPosition(LocationData.toLocation(config.getString(settleSec+".position")));
            	settle.setBiome(Biome.valueOf(config.getString(settleSec+".biome","SKY")));
            	settle.setAge(config.getLong(settleSec+".age",0));

            	//Biome.valueOf(settle.getBiome()));
            	settle.setName(config.getString(settleSec+".name"));
            	settle.setOwner(config.getString(settleSec+".owner"));
            	settle.setIsCapital(config.getBoolean(settleSec+".isCapital"));
            	settle.setIsActive(config.getBoolean(settleSec+".isActive"));
            	settle.getBank().addKonto(config.getDouble(settleSec+".bank",0.0),"SettleRead",settle.getId());
            	
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
            	
//                System.out.println(settleSec+".buildinglist");
    			Map<String,Object> buildings = config.getConfigurationSection(settleSec+".buildinglist").getValues(false);
            	for (String ref : buildings.keySet())
            	{
//                    System.out.println(ref);
            		int buildingId = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".id","0"));
            		BuildPlanType buildingType = BuildPlanType.getBuildPlanType(config.getString(settleSec+".buildinglist."+ref+".buildingType","None"));
            		int settler = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".settler","0"));
            		int workerNeeded = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerNeeded","0"));
            		int workerInstalled = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerInstalled","0"));
            		Boolean isRegion = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isRegion","false"));
            		int hsRegion = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".hsRegion","0"));
        			String hsRegionType = config.getString(settleSec+".buildinglist."+ref+".hsRegionType","");
        			String hsSuperRegion = config.getString(settleSec+".buildinglist."+ref+".hsSuperRegion","");
        			Boolean isEnabled = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isEnabled","false"));
        			Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isActiv","false"));
        			String slot1 = "";
        			String slot2 = "";
        			String slot3 = "";
        			String slot4 = "";
        			String slot5 = "";
        			slot1 = config.getString(settleSec+".buildinglist."+ref+".slot1","");
        			slot2 = config.getString(settleSec+".buildinglist."+ref+".slot2","");
        			slot3 = config.getString(settleSec+".buildinglist."+ref+".slot3","");
        			slot4 = config.getString(settleSec+".buildinglist."+ref+".slot4","");
        			slot5 = config.getString(settleSec+".buildinglist."+ref+".slot5","");
        			LocationData position = LocationData.toLocation(config.getString(settleSec+".position"));
        			Double sale = Double.valueOf(config.getString(settleSec+".buildinglist."+ref+".sale","0.0"));
        			
//        			System.out.println(buildingId+" : "+buildingType);
        			
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
        							slot5,
        							sale,
        							position
        							), 
        					settle
        					);
            	}
            }
            settle.getWarehouse().setItemMax(Integer.valueOf(config.getString(settleSec+".warehouse"+".itemMax","0")));
            settle.getWarehouse().setIsEnabled(Boolean.valueOf(config.getString(settleSec+".warehouse"+".isEnable","false")));
            
            ItemList iList = new ItemList();
			Map<String,Object> itemList = config.getConfigurationSection(settleSec+".itemList").getValues(false);
        	for (String ref : itemList.keySet())
        	{
        		int value = Integer.valueOf(config.getString(settleSec+".itemList."+ref,"0"));
//                System.out.println(ref+":"+value);
                iList.addItem(ref, value);
        	}    
        	settle.getWarehouse().setItemList(iList);
        	
        	UnitFactory uFactory = new UnitFactory();
			Map<String,Object> uList = config.getConfigurationSection(settleSec+".unitlist").getValues(false);
        	for (Object ref : uList.values())
        	{
        		String uType = (String) ref;
        		settle.getBarrack().getUnitList().add(uFactory.erzeugeUnit(UnitType.getBuildPlanType(uType)));
                System.out.println(ref+":");
        	}    
		} catch (Exception e)
		{
			 String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
			 System.out.println(name);
			 System.out.println(e.getMessage());
// 			plugin.getMessageData().errorFileIO(name, e);
		}
		return settle;
	}
	
	public ArrayList<String> readSettleList(Realms plugin) 
	{
		String path = "";
		if (plugin == null)
		{
			path = dataFolder;
		} else
		{
			path = plugin.getDataFolder().getPath(); //+"buildplan";
			dataFolder = path;
		}
        String settleSec = "SETTLEMENT"; // getSettleKey(settle.getId());
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File settleFile = new File(path, "settlement.yml");
            if (!settleFile.exists()) 
            {
            	settleFile.createNewFile();
    			System.out.println("NEW SettlementData: "+dataFolder);
            }
//            System.out.println("READLIST : "+dataFolder+":"+"settlement.yml");
            
            FileConfiguration config = new YamlConfiguration();
            config.load(settleFile);
//            System.out.println(settleFile.getName()+":"+settleFile.length());
//            System.out.println(settleSec);
            
            if (config.isConfigurationSection(settleSec))
            {
            	
//                System.out.println(settleSec);
   			
    			Map<String,Object> settles = config.getConfigurationSection(settleSec).getValues(false);
            	for (String ref : settles.keySet())
            	{
            		msg.add(ref);
//            		System.out.println("SettleId: "+ref);
//            		plugin.getMessageData().log(ref);
            	}
            }
		} catch (Exception e)
		{
			 @SuppressWarnings("unused")
  			 String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
			 System.out.println(name);
			 System.out.println(e.getMessage());
	}
		return msg;
	}
	
}
