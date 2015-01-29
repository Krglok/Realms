package net.krglok.realms.data;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import net.krglok.realms.npc.NpcData;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
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
	protected SQliteConnection sql;
	protected String dataFolder; 
    public FileConfiguration config;  

    protected String sectionName ; 
    protected String fileName ;
    protected boolean isTimeMessure;
    protected boolean isSql;
    private TableYml tableYml;

	public AbstractDataStore(String dataFolder, String fileName, String sectionName, boolean timeMessure, SQliteConnection sql)
	{
		this.sql = sql;
		if (sql == null)
		{
			this.isSql = false;
			tableYml = null;
		} else
		{
			this.isSql = true;
			tableYml = new TableYml(sql, fileName);
		}
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
		if (isSql == true)
		{
			System.out.println("Sql access: "+fileName+" / String not valid as PRIMARY key !");
			return ;
		}

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

	public String  makeValue(ConfigurationSection section)
	{
		String value = "";
		for (String key :section.getKeys(false))
		{
			value = value + key+": "+section.getString(key)+"\n";
		}
		return value;
	}

	public void writeData(T dataObject, int Id)
	{
		if (isSql == true)
		{
			ConfigurationSection section = new MemoryConfiguration();
			initDataSection(section, dataObject);
			String value = makeValue(section);
			tableYml.writeObject(Id, value);			
		} else
		{
			String refId = String.valueOf(Id);
			writeData(dataObject, refId);
		}
		
	}	
	
	/**
	 * write sectionName without  objectSection
	 * @param dataObject
	 */
	public void writeData(T dataObject)
	{
		try
		{ 
			long time1 = System.nanoTime();
            File dataFile = new File(dataFolder, fileName+".yml");
            if (!dataFile.exists()) 
            {
            	System.out.println("Error Write : "+sectionName+":"+dataFolder+":"+fileName+" not Exist !!!");
            	dataFile.createNewFile();
            	System.out.println("Create Datafile : "+dataFile.getPath());
            }
            dataFile.setWritable(true);
            
            ConfigurationSection objectSection = config.createSection(sectionName);
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
		if (isSql == true)
		{
			System.out.println("Sql access: "+fileName+" / String not valid as PRIMARY key !");
			return null;
		}
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

	public T readData(int Id)
	{
		T dataObject = null;
		if (isSql)
		{
			ResultSet result = tableYml.readObject(Id);
			try
			{
				config.loadFromString(result.getString(2));
				ConfigurationSection section = config.getRoot();
				dataObject = initDataObject(section);
			} catch (InvalidConfigurationException | SQLException e) 
			{
				e.printStackTrace();
			}
			
		} else
		{
			String refId = String.valueOf(Id);
			dataObject = readData(refId);
		}
		return dataObject; 
	}
	
	
	public T readData()
	{
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
		
		T dataObject = null;
		try
		{ 
			long time1 = System.nanoTime();
            if (config.isConfigurationSection(sectionName))
            {
//		    	System.out.println("Init DataObject :" +refId);
            	ConfigurationSection section = config.getConfigurationSection(sectionName); 
    	    	dataObject = initDataObject(section);
            }

            long time2 = System.nanoTime();
		    if (isTimeMessure)
		    {
		    	System.out.println("Read " +sectionName+" Time [ms]: "+(time2 - time1)/1000000);
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
	/**
	 * read the complete sectionName
	 */
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

	/*
	 * read all records from table
	 */
	public ResultSet readDataList(int startId)
	{
		String objectName = String.valueOf(startId);
		String selectQuery = "SELECT * "+" FROM "+fileName+ " WHERE "+tableYml.fieldnames[0]+">="+TableYml.makeSqlString(objectName);		
		try
		{
			return this.sql.query(selectQuery);
//			if (result.next())
//			{
//				return result;
//			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return  null;
	}

	
}
