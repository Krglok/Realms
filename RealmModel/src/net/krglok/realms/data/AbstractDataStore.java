package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


/**
 * <pre>
 * define a abstract dataStore class for a given dataObject
 * hold the common parameter for datastorage
 * - dataFolder
 * - fileName
 * - sectionName of the root section
 * - timeMessure, true, write out a time messurement for file-io to console
 * 
 * define the needed methodes 
 * 
 * initDataSection, for setting data to dataSection of YML-file 
 * initDataObject, for init dataObject from DataSection of YML-file
 * 
 *  writeData, write dataSection to YML-file, with error handling
 *  readData, read dataSection from YML-file, with error handling
 * 
 * @author Krglok
 *
 * @param <T>
 * </pre>
 */
public abstract class AbstractDataStore<T> implements IDataStore<T>
{
	
	protected String dataFolder; 
    protected FileConfiguration config;  

    protected String sectionName ; 
    protected String fileName ;
    protected boolean isTimeMessure;

	public AbstractDataStore(String dataFolder, String fileName, String sectionName, boolean timeMessure)
	{
		this.dataFolder = dataFolder;
		this.fileName	= fileName;
		this.sectionName= sectionName;
		this.isTimeMessure = timeMessure;
		this.config = new YamlConfiguration();
	}
	
	/**
	 * set subsection with given sectionName and id
	 * 
	 * @param id
	 * @return
	 */
	protected String getKey(String id)
	{
    	
		return (sectionName.equals("") ? id : sectionName+"."+id);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, T dataObject)
	{
//		// TODO Auto-generated method stub

	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public T initDataObject(ConfigurationSection data)
	{
//		// TODO Auto-generated method stub
		
		return null;
	}

	/**
	 * write dataObject to file
	 * use the initDataSection for setup the real data structure
	 * ! initDataSection must be overwrite in the concrete  class
	 */
	public void writeData(T dataObject, String refId)
	{
		try
		{ 
			long time1 = System.nanoTime();
            File dataFile = new File(dataFolder, fileName+".yml");
            if (!dataFile.exists()) 
            {
            	System.out.println("Error Write : "+sectionName+":"+refId+"::"+dataFolder+":"+fileName+" not Exist !!!");
            	dataFile.createNewFile();
            	System.out.println("Create Datafile : "+dataFile.getPath());
            }
            dataFile.setWritable(true);
            String base = getKey(refId);
            
            ConfigurationSection objectSection = config.createSection(base);
            //write data
            initDataSection(objectSection,dataObject);
            try
			{
            	config.save(dataFile); // dataFolder+"settlement.yml");
			} catch (Exception e)
			{
	            System.out.println("ECXEPTION save "+objectSection+ ":"+dataFolder+":"+fileName);
			}
		    long time2 = System.nanoTime();
		    if (isTimeMessure)
		    {
		    	System.out.println("Write Time [ms]: "+(time2 - time1)/1000000);
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
			 System.out.println("Exception: "+name+" / "+e.getMessage());
		}
		
	}
	
	
	/**
	 *<pre> 
	 * read dataObject from file
	 * use the initDataObject for setup the real data structure
	 * ! initDataObject must be overwrite in the concrete  class
	 * 
	 * </pre>
	 */
	public T readData(String refId)
	{
		T dataObject = null;
		try
		{ 
	        String section = getKey(refId);
			long time1 = System.nanoTime();
            if (config.isConfigurationSection(section))
            {
//		    	System.out.println("Init DataObject :" +refId);
    	    	dataObject = initDataObject(config.getConfigurationSection(section));
            }

            long time2 = System.nanoTime();
		    if (isTimeMessure)
		    {
		    	System.out.println("Read " +refId+" Time [ms]: "+(time2 - time1)/1000000);
		    }
		    
            
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
    	
    	
    	return dataObject;
    }
	
//	@Override
	public ArrayList<String> readDataList()
	{
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File regFile = new File(dataFolder, fileName+".yml");
			System.out.println("Read DataStore: "+dataFolder+":"+fileName+".yml");
            if (regFile.exists() == false) 
            {
            	regFile.createNewFile();
    			System.out.println("NEW File: "+dataFolder+":"+fileName+".yml");
            }
            // load data from file
            config.load(regFile);
            
            if (config.isConfigurationSection(sectionName))
            {
    			Map<String,Object> settles = config.getConfigurationSection(sectionName).getValues(false);
            	for (String ref : settles.keySet())
            	{
            		msg.add(ref);
            	}
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
		}
    	return msg; 
	}

	
}
