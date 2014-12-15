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
public class DataStoreCaseBook extends AbstractDataStore<CaseBook>
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
    
        section.set("id", dataObject.getId());
        section.set("refId", dataObject.getRefId());
        section.set("titel", dataObject.getTitel());
        section.set("author", dataObject.getAuthor());
        section.set("isEnabled", dataObject.isEnabled());
        //write pages		
		HashMap<String,String> values; // = new HashMap<String,String>();
        values = new HashMap<String,String>();
    	for (int i = 1; i <= dataObject.getPages().size(); i++  )
    	{
//            System.out.println("Pages: "+i+": "+dataObject.getPages().get(i));
    		values.put(String.valueOf(i), dataObject.getPages().get(i));
    	}
    	section.set("pages", values);
	}

	@Override
	public CaseBook initDataObject(ConfigurationSection data)
	{
//		// 
    	CaseBook caseBook = new CaseBook();  // create a clean caseBook
        if (data != null)
        {
        	caseBook.setId(data.getInt("id"));
        	caseBook.setRefId(data.getString("refId"));
        	caseBook.setTitel(data.getString("titel"));
        	caseBook.setAuthor(data.getString("author"));
        	caseBook.setEnabled(data.getBoolean("isEnabled",true));
        
	        String liste = "pages";
	        if (data.isConfigurationSection(liste))
	        {
	            ArrayList<String> iList = new ArrayList<String>();
				Map<String,Object> values = data.getConfigurationSection(liste).getValues(false);
				if (values != null)
				{
		        	for (String ref : values.keySet())
		        	{
		        		caseBook.getPages().put(Integer.valueOf(ref), data.getString(liste+"."+ref,"0"));
		        	}    
				}
	        }
        }
		
		return caseBook;
	}

    
}
