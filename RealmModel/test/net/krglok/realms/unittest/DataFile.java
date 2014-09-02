package net.krglok.realms.unittest;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import net.krglok.realms.science.CaseBook;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataFile
{
    private FileConfiguration config = new YamlConfiguration();

	private String dataFolder; 
    private String section ; 
    private String fileName ;

	public DataFile(String dataFolder, String fileName, String section)
	{
		this.dataFolder = dataFolder; //+"Realms";
		this.section = section;
		this.fileName = fileName;
	}

    private String getKey(int id)
	{
		return section+"."+String.valueOf(id);
	}
	
    /**
     * read the available dataList from section from File fileName 
     * 
     * @return list of elements as String
     */
    public  ArrayList<String> readDataList()
    {
		ArrayList<String> msg = new ArrayList<String>();
		try
		{
            File dataFile = new File(dataFolder, fileName+".yml");
            if (dataFile.exists() == false) 
            {
            	dataFile.createNewFile();
    			System.out.println("NEW File: "+dataFolder+":"+fileName);
            }
            
            config.load(dataFile);
            System.out.println(dataFile.getName()+":"+dataFile.length());
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
				 name = st[0].getClassName()+":"+st[0].getMethodName()+": FileName"+fileName;
			 }
			 System.out.println(name);
			 System.out.println(e.getMessage());
		}
		
    	return msg; 
    }
    
    /**
     * write the data of the object to file 
     * the fileName is specified in constructor 
     * @param <T>
     * 
     * @param caseBook
     */
    public void writeCaseBook(Object obj)
    {
		try
		{ 
			long time1 = System.nanoTime();
            File dataFile = new File(dataFolder, fileName+".yml");
            if (dataFile.exists() == false) 
            {
            	System.out.println("WRITE File: "+dataFolder+":"+fileName+" not Exist !!!");
    			System.out.println("NEW File  : "+dataFolder+":"+fileName);
            	dataFile.createNewFile();
	            return;
            }
            dataFile.setWritable(true);
            
            String base = getKey(1); // hier id einsetzen
            
            
//            ConfigurationSection section = config.createSection(base);
////            System.out.println("SECTION: "+caseBook.getId()+": "+base);
//        
//            config.set(MemorySection.createPath(section, "id"), caseBook.getId());
//            config.set(MemorySection.createPath(section, "refId"), caseBook.getRefId());
//            config.set(MemorySection.createPath(section, "titel"), caseBook.getTitel());
//            config.set(MemorySection.createPath(section, "author"), caseBook.getAuthor());
//            config.set(MemorySection.createPath(section, "isEnalbled"), caseBook.isEnabled());
//            //write pages		
//			HashMap<String,String> values; // = new HashMap<String,String>();
//            values = new HashMap<String,String>();
//            int index = 0;
//        	for (String page : caseBook.getPages())
//        	{
////                System.out.println("UNITLIST: "+index+": "+unit.getUnitType().name());
//        		values.put(String.valueOf(index), page);
//        		index++;
//        	}
//            config.set(MemorySection.createPath(section,"pages"), values);

		} catch (Exception e)
		{
			 @SuppressWarnings("unused")
			String name = "" ;
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName()+": FileName"+fileName;
			 }
			 System.out.println("Exception: "+name+" / "+e.getMessage());
		}
    	
    }
    
    public void readCaseBook(int id)
    {
		try
		{ 
	        String section = getKey(id);
			long time1 = System.nanoTime();
//            if (config.isConfigurationSection(section))
//            {
//	    		CaseBook caseBook = new CaseBook();  // create a clean caseBook
//            	caseBook.setId(config.getInt(section+".id"));
//            	caseBook.setRefId(config.getInt(section+".refId"));
//            	caseBook.setTitel(config.getString(section+".titel"));
//            	caseBook.setAuthor(config.getString(section+".author"));
//            	caseBook.setEnabled(config.getBoolean(section+".isEnabled",false));
//            }
//            
//            if (config.isConfigurationSection(section+".pages"))
//            {
//	            ArrayList<String> iList = new ArrayList<String>();
//				Map<String,Object> values = config.getConfigurationSection(section+".itemList").getValues(false);
//				if (values != null)
//				{
//		        	for (String ref : values.keySet())
//		        	{
//		                iList.add(config.getString(section+".pages."+ref,"0"));
//		        	}    
//		        	caseBook.setPages(iList);
//				}
//            }

            
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
//			return null;
		}
    	
    	
//    	return caseBook;
    }


}
