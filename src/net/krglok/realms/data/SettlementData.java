package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.ItemPriceList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * <pre>
 * Read the regiment Data from file with  YML Format
 * the Config file are loaded with the regimentList and the used for read andwrite operations.
 * This will minimize the read and write time for the file
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 *</pre>
 */
public class SettlementData
{

//	private Realms plugin;
	private String dataFolder; 
    private FileConfiguration config = new YamlConfiguration();
	
	public SettlementData(String dataFolder)	
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
				long time1 = System.nanoTime();

	            File settleFile = new File(dataFolder, "settlement.yml");
	            if (!settleFile.exists()) 
	            {
	            	System.out.println("WRITE :  "+settle.getId()+":"+dataFolder+""+" not Exist !!!");
		            return;
	            }
				HashMap<String,String> values; // = new HashMap<String,String>();
            
	            String base = getSettleKey(settle.getId());
	            
	            ConfigurationSection section = config.createSection(base);
            
	            config.set(MemorySection.createPath(section, "id"), settle.getId());
	            config.set(MemorySection.createPath(section, "settleType"), settle.getSettleType().name());
	            config.set(MemorySection.createPath(section, "position"), LocationData.toString(settle.getPosition()));
	            config.set(MemorySection.createPath(section, "biome"), settle.getBiome().name());
	            config.set(MemorySection.createPath(section, "age"), settle.getAge());
	            config.set(MemorySection.createPath(section, "name"), settle.getName());
	            config.set(MemorySection.createPath(section, "owner"), settle.getOwnerId());
	            config.set(MemorySection.createPath(section, "isCapital"), settle.getIsCapital());
//	            config.set(MemorySection.createPath(settleSec, "isEnabled"), settle.isEnabled());
	            config.set(MemorySection.createPath(section, "isActive"), settle.isActive());
	            config.set(MemorySection.createPath(section, "bank"), settle.getBank().getKonto());

//	            System.out.println("SAVE : 1");
//
	            values = new HashMap<String,String>();
	            values.put("isEnabled",settle.getTownhall().getIsEnabled().toString());
	            values.put("workerNeeded",String.valueOf(settle.getTownhall().getWorkerNeeded()));
	            values.put( "workerCount",String.valueOf(settle.getTownhall().getWorkerCount()));
	            config.set(MemorySection.createPath(section, "townhall"), values);

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
	            config.set(MemorySection.createPath(section,"resident"), values);

//	            System.out.println("SAVE : 3");
//	            ConfigurationSection buildingSec = config.createSection(base+".buildinglist");
	            HashMap<String,HashMap<String,String>> buildings = new HashMap<String,HashMap<String,String>>();
	            for (Building building : settle.getBuildingList().values())
	            {
		            values = new HashMap<String,String>();
		            
	            	values.put("id", String.valueOf(building.getId()));
	            	values.put("buildingType", building.getBuildingType().name());
	            	values.put("settler", String.valueOf(building.getSettler()));
	            	values.put("workerNeeded", String.valueOf(building.getWorkerNeeded()));
	            	values.put("workerInstalled", String.valueOf(building.getWorkerInstalled()));
//	            	values.put("isRegion", building.isRegion().toString());
	            	values.put("hsRegion", String.valueOf(building.getHsRegion()));
	            	values.put("hsRegionType", building.getHsRegionType());
//	            	values.put("hsSuperRegion", building.getHsSuperRegion());
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
//	            	values.put("trainType", building.getTrainType().name());
	            	values.put("trainCounter", String.valueOf(building.getTrainCounter()));
	            	values.put("trainTime", String.valueOf(building.getTrainTime()));
	            	values.put("maxProduction", String.valueOf(building.getMaxTrain()));

	            	buildings.put(String.valueOf(building.getId()), values ); 
		            config.set((MemorySection.createPath(section,"buildinglist")), buildings);
	            }

//	            System.out.println("SAVE : 4");

	            values = new HashMap<String,String>();
            	values.put("itemMax", String.valueOf(settle.getWarehouse().getItemMax()));
            	values.put("itemCount", String.valueOf(settle.getWarehouse().getItemCount()));
            	values.put("isEnabled", settle.getWarehouse().getIsEnabled().toString());
	            config.set(MemorySection.createPath(section,"warehouse"), values);

	            values = new HashMap<String,String>();
            	for (String itemref : settle.getWarehouse().getItemList().keySet())
            	{
            		values.put(itemref, String.valueOf(settle.getWarehouse().getItemList().getValue(itemref)) );
            	}
	            config.set(MemorySection.createPath(section,"itemList"), values);
////	            config.set("", settle);
//	            System.out.println("SAVE : "+settle.getId()+": "+fileName);
//	            values = new HashMap<String,String>();
//	            int index = 0;
//            	for (Unit unit : settle.getBarrack().getUnitList())
//            	{
//            		values.put(String.valueOf(index), unit.getUnitType().name());
//            		index++;
//            	}
//	            config.set(MemorySection.createPath(section,"unitlist"), values);

	            try
				{
	            	config.save(settleFile); // dataFolder+"settlement.yml");
				} catch (Exception e)
				{
		            System.out.println("ECXEPTION : "+settle.getId()+": "+dataFolder+""+"settlement.yml");
				}
			    long time2 = System.nanoTime();
			    System.out.println("Write Time [ms]: "+(time2 - time1)/1000000);

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
	
	public Settlement readSettledata(int id, ItemPriceList priceList, BuildingList buildingList) 
	{
		Settlement settle = new Settlement(priceList);
        String section = getSettleKey(id);
		try
		{
//            System.out.println("READ : "+id+":"+dataFolder+" : "+"settlement.yml");
            if (config.isConfigurationSection(section))
            {
//                System.out.println(settleSec);
            	settle.setId(config.getInt(section+".id"));
            	settle.setSettleType(SettleType.valueOf(config.getString(section+".settleType")));
            	settle.setPosition(LocationData.toLocation(config.getString(section+".position")));
            	settle.setBiome(Biome.valueOf(config.getString(section+".biome","SKY")));
            	settle.setAge(config.getLong(section+".age",0));

            	//Biome.valueOf(settle.getBiome()));
            	settle.setName(config.getString(section+".name"));
            	settle.setOwnerId(config.getInt(section+".owner",0));
            	settle.setIsCapital(config.getBoolean(section+".isCapital",false));
            	settle.setIsActive(config.getBoolean(section+".isActive"));
            	settle.getBank().addKonto(config.getDouble(section+".bank",0.0),"SettleRead",settle.getId());
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getTownhall().setIsEnabled(Boolean.valueOf(config.getString(section+".townhall"+".isEnable")));
            	settle.getTownhall().setWorkerNeeded(Integer.valueOf(config.getString(section+".townhall"+".workerNeeded")));
            	settle.getTownhall().setWorkerCount(Integer.valueOf(config.getString(section+".townhall"+".workerCount")));
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getResident().setSettlerMax(Integer.valueOf(config.getString(section+".resident"+".settlerMax")));
            	settle.getResident().setSettlerCount(Integer.valueOf(config.getString(section+".resident"+".settlerCount")));
            	settle.getResident().oldPopulation = Integer.valueOf(config.getString(section+".resident"+".settlerCount"));
            	settle.getResident().setFeritilityCounter(Double.valueOf(config.getString(section+".resident"+".fertilityCounter")));
//            	settle.getResident().setHappiness(Double.valueOf(config.getString(section+".resident"+".happiness")));
            	settle.getResident().setCowCount(Integer.valueOf(config.getString(section+".resident"+".cowCount")));
            	settle.getResident().setHorseCount(Integer.valueOf(config.getString(section+".resident"+".horseCount")));
            	
//                System.out.println(settleSec+".buildinglist");
    			Map<String,Object> buildings = config.getConfigurationSection(section+".buildinglist").getValues(false);
    			BuildingList bList = new BuildingList();
    			Building building ;
            	for (String ref : buildings.keySet())
            	{
//                    System.out.println(ref);
            		int buildingId = Integer.valueOf(config.getString(section+".buildinglist."+ref+".id","0"));
            		BuildPlanType buildingType = BuildPlanType.getBuildPlanType(config.getString(section+".buildinglist."+ref+".buildingType","None"));
            		int settler = Integer.valueOf(config.getString(section+".buildinglist."+ref+".settler","0"));
            		int workerNeeded = Integer.valueOf(config.getString(section+".buildinglist."+ref+".workerNeeded","0"));
            		int workerInstalled = Integer.valueOf(config.getString(section+".buildinglist."+ref+".workerInstalled","0"));
            		Boolean isRegion = Boolean.valueOf(config.getString(section+".buildinglist."+ref+".isRegion","false"));
            		int hsRegion = Integer.valueOf(config.getString(section+".buildinglist."+ref+".hsRegion","0"));
        			String hsRegionType = config.getString(section+".buildinglist."+ref+".hsRegionType","");
        			String hsSuperRegion = config.getString(section+".buildinglist."+ref+".hsSuperRegion","");
        			Boolean isEnabled = Boolean.valueOf(config.getString(section+".buildinglist."+ref+".isEnabled","false"));
        			Boolean.valueOf(config.getString(section+".buildinglist."+ref+".isActiv","false"));

//        			UnitType trainType= UnitType.getBuildPlanType(config.getString(settleSec+".buildinglist."+ref+".trainType", "NONE"));
        			int trainCounter = Integer.valueOf(config.getString(section+".buildinglist."+ref+".trainCounter", "0"));
        			int trainTime = Integer.valueOf(config.getString(section+".buildinglist."+ref+".trainTime", "0"));
        			int maxProduction = Integer.valueOf(config.getString(section+".buildinglist."+ref+".maxProduction", "0"));
        			String slot1 = "";
        			String slot2 = "";
        			String slot3 = "";
        			String slot4 = "";
        			String slot5 = "";
        			slot1 = config.getString(section+".buildinglist."+ref+".slot1","");
        			slot2 = config.getString(section+".buildinglist."+ref+".slot2","");
        			slot3 = config.getString(section+".buildinglist."+ref+".slot3","");
        			slot4 = config.getString(section+".buildinglist."+ref+".slot4","");
        			slot5 = config.getString(section+".buildinglist."+ref+".slot5","");
        			LocationData position = LocationData.toLocation(config.getString(section+".position"));
        			Double sale = Double.valueOf(config.getString(section+".buildinglist."+ref+".sale","0.0"));
        			
//        			System.out.println(buildingId+" : "+buildingType);
        			
        			building = new Building(
        							buildingId, 
        							buildingType, 
        							settler, 
        							workerNeeded, 
        							workerInstalled, 
        							hsRegion, 
        							isEnabled, 
        							slot1, 
        							slot2, 
        							slot3, 
        							slot4, 
        							slot5,
        							sale,
        							position,
//        							trainType,
        							trainCounter,
        							trainTime,
        							maxProduction,
        							settle.getId(),
        							0
        							); 
        		     buildingList.putBuilding(building);
//        		     buildingData.writeData(building, String.valueOf(building.getId()));
            	}
            	settle.setBuildingList(buildingList.getSubList(settle.getId())); 
            }
            settle.getWarehouse().setItemMax(Integer.valueOf(config.getString(section+".warehouse"+".itemMax","0")));
            settle.getWarehouse().setIsEnabled(Boolean.valueOf(config.getString(section+".warehouse"+".isEnable","false")));
            
            ItemList iList = new ItemList();
			Map<String,Object> itemList = config.getConfigurationSection(section+".itemList").getValues(false);
			if (itemList != null)
			{
	        	for (String ref : itemList.keySet())
	        	{
	        		int value = Integer.valueOf(config.getString(section+".itemList."+ref,"0"));
	//                System.out.println(ref+":"+value);
	                iList.addItem(ref, value);
	        	}    
	        	settle.getWarehouse().setItemList(iList);
			}
//        	UnitFactory uFactory = new UnitFactory();
//			Map<String,Object> uList = config.getConfigurationSection(section+".unitlist").getValues(false);
//			if (uList != null)
//			{
//	        	for (Object ref : uList.values())
//	        	{
//	        		String uType = (String) ref;
//	        		settle.getBarrack().getUnitList().add(uFactory.erzeugeUnit(UnitType.getBuildPlanType(uType)));
//	//                System.out.println(ref+":");
//	        	}
//			}
		} catch (Exception e)
		{
			 String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
			 System.out.println(name);
			 System.out.println("Exception "+e.getMessage());
// 			plugin.getMessageData().errorFileIO(name, e);
		}
		return settle;
	}
	
	public ArrayList<String> readSettleList() 
	{
//		String path = "";
//		if (plugin == null)
//		{
//			path = dataFolder;
//		} else
//		{
//			path = plugin.getDataFolder().getPath(); //+"buildplan";
//			dataFolder = path;
//		}
        String section = "SETTLEMENT"; // getSettleKey(settle.getId());
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File settleFile = new File(dataFolder, "settlement.yml");
            if (!settleFile.exists()) 
            {
            	settleFile.createNewFile();
    			System.out.println("NEW SettlementData: "+dataFolder);
            }
//            System.out.println("READLIST : "+dataFolder+":"+"settlement.yml");
            
            config.load(settleFile);
//            System.out.println(settleFile.getName()+":"+settleFile.length());
//            System.out.println(settleSec);
            
            if (config.isConfigurationSection(section))
            {
            	
//                System.out.println(settleSec);
   			
    			Map<String,Object> settles = config.getConfigurationSection(section).getValues(false);
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
