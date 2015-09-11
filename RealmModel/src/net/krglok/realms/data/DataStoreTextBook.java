package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.krglok.realms.science.TextBook;

public class DataStoreTextBook extends AbstractDataStore<TextBook>
{

	private String dataFolder; 
    private FileConfiguration config = new YamlConfiguration();

    private static String sectionName = "TEXTBOOK"; 
    private static String fileName = "textbook";

	public DataStoreTextBook(String dataFolder, String fileName, String sectionName, boolean timeMessure,
			SQliteConnection sql)
	{
		super(dataFolder, fileName, sectionName, timeMessure, sql);
		// TODO Auto-generated constructor stub
	}

	public DataStoreTextBook(String dataFolder)
	{
		super(dataFolder, fileName, sectionName, false, null);
	}
	
	
	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, TextBook dataObject)
	{
        String base = getKey(String.valueOf(dataObject.getRefId()));
//        ConfigurationSection objectSection = config.createSection(base);
        
//        section = config.createSection(base);
    
        section.set("refId", dataObject.getRefId());
        section.set("group", dataObject.getGroup());
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
	public TextBook initDataObject(ConfigurationSection data)
	{
//		// 
		TextBook textBook = new TextBook();  // create a clean caseBook
        if (data != null)
        {
        	textBook.setRefId(data.getString("refId"));
        	textBook.setGroup(data.getString("group"));
        	textBook.setTitel(data.getString("titel"));
        	textBook.setAuthor(data.getString("author"));
        	textBook.setEnabled(data.getBoolean("isEnabled",true));
        
	        String liste = "pages";
	        if (data.isConfigurationSection(liste))
	        {
	            Map<String,Object> values = data.getConfigurationSection(liste).getValues(false);
				if (values != null)
				{
		        	for (String ref : values.keySet())
		        	{
		        		textBook.getPages().put(Integer.valueOf(ref), data.getString(liste+"."+ref,"0"));
		        	}    
				}
	        }
        }
		
		return textBook;
	}
	
}
