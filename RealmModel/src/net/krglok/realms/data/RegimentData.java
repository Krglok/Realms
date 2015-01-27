package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;
import net.krglok.realms.unit.RegimentType;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * <pre>
 * Read the regiment Data from file with  YML Format
 * the Config file are loaded with the regimentList and will used for read and write operations.
 * This will minimize the read and write time for the file
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 *</pre>
 */

public class RegimentData
{
	private String dataFolder; 
    private FileConfiguration config = new YamlConfiguration();

    private static String section = "REGIMENT"; 
    private static String fileName = "regiment";

    public RegimentData(String dataFolder)
    {
		this.dataFolder = dataFolder; //+"Realms";
    }

    private String getRegimentKey(int id)
	{
		return section +"."+String.valueOf(id);
	}

    /**
     * <pre>
     * write the given regiment object to datafile
     * use the global config definition.
     * make no file handling !
     * @param regiment
     * </pre>
     */
	public void writeRegimentData(Regiment regiment) 
	{
		try
		{ 
			long time1 = System.nanoTime();
            File regFile = new File(dataFolder, fileName+".yml");
            if (!regFile.exists()) 
            {
            	System.out.println("WRITE :  "+regiment.getId()+":"+dataFolder+""+" not Exist !!!");
            	regFile.createNewFile();
	            return;
            }
            regFile.setWritable(true);
            String base = getRegimentKey(regiment.getId());
            
            ConfigurationSection section = config.createSection(base);
//            System.out.println("SECTION: "+regiment.getId()+": "+base);
        
            config.set(MemorySection.createPath(section, "id"), regiment.getId());
            config.set(MemorySection.createPath(section, "regimentType"), regiment.getRegimentType().name());
            config.set(MemorySection.createPath(section, "regStatus"), regiment.getRegStatus().name());
            config.set(MemorySection.createPath(section, "position"), LocationData.toString(regiment.getPosition()));
            config.set(MemorySection.createPath(section, "target"), LocationData.toString(regiment.getTarget()));
            config.set(MemorySection.createPath(section, "name"), regiment.getName());
            config.set(MemorySection.createPath(section, "owner"), regiment.getOwnerId());
            config.set(MemorySection.createPath(section, "bank"), regiment.getBank().getKonto());
            config.set(MemorySection.createPath(section, "isActiv"), regiment.getIsActive());
            config.set(MemorySection.createPath(section, "settleId"), regiment.getSettleId());
            config.set(MemorySection.createPath(section, "MaxUnit"), regiment.getBarrack().getUnitMax());
            //Unitlist write		
			HashMap<String,String> values; // = new HashMap<String,String>();
//            values = new HashMap<String,String>();
//            int index = 0;
//        	for (Unit unit : regiment.getBarrack().getUnitList())
//        	{
////                System.out.println("UNITLIST: "+index+": "+unit.getUnitType().name());
//        		values.put(String.valueOf(index), unit.getUnitType().name());
//        		index++;
//        	}
//            config.set(MemorySection.createPath(section,"unitlist"), values);
            //warehouse write
            values = new HashMap<String,String>();
        	values.put("itemMax", String.valueOf(regiment.getWarehouse().getItemMax()));
        	values.put("itemCount", String.valueOf(regiment.getWarehouse().getItemCount()));
        	values.put("isEnabled", regiment.getWarehouse().getIsEnabled().toString());
            config.set(MemorySection.createPath(section,"warehouse"), values);

            values = new HashMap<String,String>();
        	for (String itemref : regiment.getWarehouse().getItemList().keySet())
        	{
        		values.put(itemref, String.valueOf(regiment.getWarehouse().getItemList().getValue(itemref)) );
        	}
            config.set(MemorySection.createPath(section,"itemList"), values);

            try
			{
//	            System.out.println("SAVE: "+regiment.getId()+": "+dataFolder+""+"regiment.yml");
            	config.save(regFile); // dataFolder+"settlement.yml");
			} catch (Exception e)
			{
	            System.out.println("ECXEPTION : "+regiment.getId()+": "+dataFolder+""+"regiment.yml");
			}
		    long time2 = System.nanoTime();
		    System.out.println("Write Time [ms]: "+(time2 - time1)/1000000);


		} catch (Exception e)
		{
			 @SuppressWarnings("unused")
			String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
			 System.out.println("Exception: "+name+" / "+e.getMessage());
		}
	}
	
	
	/**
	 * read the regiment data from file with the given id
     * use the global config definition.
     * make no file handling !
	 *  
	 * @param id
	 * @param logList
	 * @return  regiment object, should be add to the regiment list
	 */
	public Regiment readRegimentData(int id) 
	{
		Regiment regiment = new Regiment(); //null);
		try
		{ 
	        String section = getRegimentKey(id);
			long time1 = System.nanoTime();
            if (config.isConfigurationSection(section))
            {
            	regiment.setId(config.getInt(section+".id"));
            	regiment.setRegimentType(RegimentType.valueOf(config.getString(section+".regimentType")));
            	regiment.setPosition(LocationData.toLocation(config.getString(section+".position")));
            	regiment.setPosition(LocationData.toLocation(config.getString(section+".target")));
            	regiment.setName(config.getString(section+".name"));
            	regiment.setOwnerId(Integer.valueOf(config.getString(section+".owner","0")));
            	regiment.setIsActive(config.getBoolean(section+".isActive"));
            	regiment.setSettleId(config.getInt(section+".settleId"));
            	regiment.getBarrack().setUnitMax(config.getInt(section+".MaxUnit"));
            }
            regiment.setRegStatus(RegimentStatus.IDLE);
            
//        	UnitFactory uFactory = new UnitFactory();
//			Map<String,Object> uList = config.getConfigurationSection(section+".unitlist").getValues(false);
//			if (uList != null)
//			{
//	        	for (Object ref : uList.values())
//	        	{
//	        		String uType = (String) ref;
//	        		regiment.getBarrack().getUnitList().add(uFactory.erzeugeUnit(UnitType.getBuildPlanType(uType)));
//	//                System.out.println(ref+":");
//	        	}
//			}

            
            regiment.getWarehouse().setItemMax(Integer.valueOf(config.getString(section+".warehouse"+".itemMax","0")));
            regiment.getWarehouse().setIsEnabled(Boolean.valueOf(config.getString(section+".warehouse"+".isEnable","false")));
            
            if (config.isConfigurationSection(section+".itemList"))
            {
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
		        	regiment.getWarehouse().setItemList(iList);
				}
            }
		    long time2 = System.nanoTime();
//		    System.out.println("Regiment "+id+": Time [ms]: "+(time2 - time1)/1000000);
		    
            
		}catch (Exception e)
		{
			@SuppressWarnings("unused")
			String name = "" ;
			StackTraceElement[] st = new Throwable().getStackTrace();
			if (st.length > 0)
			{
				name = st[0].getClassName()+":"+st[0].getMethodName();
			}
			System.out.println("Exception: "+name+" / "+e.getMessage());
			return null;
		}
	    return regiment; 
	}
	
	/**
	 * read the regiment id from datafile  
     * use the global config definition. 
     * make the file handling ! 
     * MUST be run before readRegiment or writeRegiment!
	 * 
	 * @return list of regiment id as string number
	 */
	public ArrayList<String> readRegimentList() 
	{
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File regFile = new File(dataFolder, fileName+".yml");
            if (!regFile.exists()) 
            {
            	regFile.createNewFile();
    			System.out.println("NEW RegimentData: "+dataFolder);
            }
            
            config.load(regFile);
            System.out.println(regFile.getName()+":"+regFile.length());
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
