package net.krglok.realms.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.science.CaseBook;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Write the content of Casebooks to file
 * Read the content of CaseBooks from file
 * 
 *  
 * @author Windu
 *
 */
public class CaseBookData
{

	private String dataFolder; 
    private FileConfiguration config = new YamlConfiguration();

    private static String section = "CASEBOOK"; 
    private static String fileName = "casebook";

    
    public CaseBookData(String dataFolder)
    {
		this.dataFolder = dataFolder; //+"Realms";
   	
    }
    
    private String getKey(int id)
	{
		return section+"."+String.valueOf(id);
	}

    public void writeCaseBook(CaseBook caseBook)
    {
		try
		{ 
			long time1 = System.nanoTime();
            File regFile = new File(dataFolder, fileName+".yml");
            if (!regFile.exists()) 
            {
            	System.out.println("WRITE :  "+caseBook.getId()+":"+dataFolder+":"+fileName+" not Exist !!!");
            	System.out.println("WRITE :  "+regFile.getPath());
            	regFile.createNewFile();
            }
            regFile.setWritable(true);
            String base = getKey(caseBook.getId());
            
            ConfigurationSection section = config.createSection(base);
//            System.out.println("SECTION: "+caseBook.getId()+": "+base);
        
            config.set(MemorySection.createPath(section, "id"), caseBook.getId());
            config.set(MemorySection.createPath(section, "refId"), caseBook.getRefId());
            config.set(MemorySection.createPath(section, "titel"), caseBook.getTitel());
            config.set(MemorySection.createPath(section, "author"), caseBook.getAuthor());
            config.set(MemorySection.createPath(section, "isEnabled"), caseBook.isEnabled());
            //write pages		
			HashMap<String,String> values; // = new HashMap<String,String>();
            values = new HashMap<String,String>();
        	for (int i = 1; i <= caseBook.getPages().size(); i++  )
        	{
                System.out.println("UNITLIST: "+i+": "+caseBook.getPages().get(i));
        		values.put(String.valueOf(i), caseBook.getPages().get(i));
        	}
            config.set(MemorySection.createPath(section,"pages"), values);
            try
			{
//	            System.out.println("SAVE: "+regiment.getId()+": "+dataFolder+""+"regiment.yml");
            	config.save(regFile); // dataFolder+"settlement.yml");
			} catch (Exception e)
			{
	            System.out.println("ECXEPTION : "+caseBook.getId()+": "+dataFolder+""+"regiment.yml");
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
 
    public CaseBook readCaseBook(int id)
    {
    	CaseBook caseBook = new CaseBook();  // create a clean caseBook
		try
		{ 
	        String section = getKey(id);
			long time1 = System.nanoTime();
            if (config.isConfigurationSection(section))
            {
            	caseBook.setId(config.getInt(section+".id"));
            	caseBook.setRefId(config.getString(section+".refId"));
            	caseBook.setTitel(config.getString(section+".titel"));
            	caseBook.setAuthor(config.getString(section+".author"));
            	caseBook.setEnabled(config.getBoolean(section+".isEnabled",true));
            }
            
            String liste = section+".pages";
            if (config.isConfigurationSection(liste))
            {
	            ArrayList<String> iList = new ArrayList<String>();
				Map<String,Object> values = config.getConfigurationSection(liste).getValues(false);
				if (values != null)
				{
		        	for (String ref : values.keySet())
		        	{
		        		caseBook.getPages().put(Integer.valueOf(ref), config.getString(liste+"."+ref,"0"));
		        	}    
				}
            }

            
		    long time2 = System.nanoTime();
		    System.out.println("caseBook "+id+": Time [ms]: "+(time2 - time1)/1000000);
		    
            
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
    	
    	
    	return caseBook;
    }

    
    /**
     * read the list of available CaseBooks from dataFile
     * 
     * @return
     */
    public  ArrayList<String> readCaseBookList()
    {
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File regFile = new File(dataFolder, fileName+".yml");
            if (regFile.exists() == false) 
            {
            	regFile.createNewFile();
    			System.out.println("NEW CaseBookData: "+dataFolder);
            }
            
            config.load(regFile);
            System.out.println("[Realms] "+regFile.getName()+":"+regFile.length());
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
