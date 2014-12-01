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
public class DataStoreCaseBook extends DataStore<CaseBook>
{
	private String dataFolder; 
    private FileConfiguration config = new YamlConfiguration();

    private static String sectionName = "CASEBOOK"; 
    private static String fileName = "casebook";


	public DataStoreCaseBook(String dataFolder, String fileName,
			String sectionName, boolean timeMessure)
	{
		super(dataFolder, fileName, sectionName, timeMessure);
		// TODO Auto-generated constructor stub
	}

    public DataStoreCaseBook(String dataFolder)
    {
    	
		super(dataFolder, fileName, sectionName, true);
    }
    
	@Override
	public void initDataSection(ConfigurationSection section, CaseBook dataObject)
	{
        String base = getKey(String.valueOf(dataObject.getId()));
//        ConfigurationSection objectSection = config.createSection(base);
        
//        section = config.createSection(base);
    
        config.set(MemorySection.createPath(section, "id"), dataObject.getId());
        config.set(MemorySection.createPath(section, "refId"), dataObject.getRefId());
        config.set(MemorySection.createPath(section, "titel"), dataObject.getTitel());
        config.set(MemorySection.createPath(section, "author"), dataObject.getAuthor());
        config.set(MemorySection.createPath(section, "isEnabled"), dataObject.isEnabled());
        //write pages		
		HashMap<String,String> values; // = new HashMap<String,String>();
        values = new HashMap<String,String>();
    	for (int i = 1; i <= dataObject.getPages().size(); i++  )
    	{
//            System.out.println("Pages: "+i+": "+dataObject.getPages().get(i));
    		values.put(String.valueOf(i), dataObject.getPages().get(i));
    	}
        config.set(MemorySection.createPath(section,"pages"), values);
	}

	@Override
	public CaseBook initDataObject(ConfigurationSection data)
	{
//		// 
    	CaseBook caseBook = new CaseBook();  // create a clean caseBook
        if (data != null)
        {
        	caseBook.setId(config.getInt(sectionName+".id"));
        	caseBook.setRefId(config.getString(sectionName+".refId"));
        	caseBook.setTitel(config.getString(sectionName+".titel"));
        	caseBook.setAuthor(config.getString(sectionName+".author"));
        	caseBook.setEnabled(config.getBoolean(sectionName+".isEnabled",true));
        
	        String liste = sectionName+".pages";
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
        }
		
		return caseBook;
	}

    
}
