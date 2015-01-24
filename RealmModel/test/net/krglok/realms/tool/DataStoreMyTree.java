package net.krglok.realms.tool;

import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.data.AbstractDataStore;
import net.krglok.realms.data.SQliteConnection;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;


public class DataStoreMyTree extends AbstractDataStore<MyTree>
{

	public DataStoreMyTree(String dataFolder, String fileName,
			String sectionName, boolean timeMessure,SQliteConnection sql)
	{
		super(dataFolder, fileName, sectionName, timeMessure, sql);
		// TODO Auto-generated constructor stub
	}

	private void makeChildList(String base ,MyTree.Node<String> node)
	{
		ConfigurationSection nodeSection = config.createSection(base+"."+node.getKey());
		if (node.getChildren().size() > 0)
		{
	        for (MyTree.Node<String> child : node.getChildren())
	        {
	        	config.set(MemorySection.createPath(nodeSection, child.getKey()),"");
	        	makeChildList(base, node); 
	        }
		}
	}
	
	@Override
	public void initDataSection(ConfigurationSection section,MyTree dataObject)
	{
        String base = getKey("0");
//        ConfigurationSection objectSection = config.createSection(base);
        
        for (MyTree.Node<String> node : dataObject.getRoots())
        {
        	config.set(MemorySection.createPath(section, node.getKey()),"");
        	makeChildList(base, node); 
        }
	}

	@Override
	public MyTree initDataObject(ConfigurationSection data)
	{
//		// TODO Auto-generated method stub
		
		return null;
	}

}
