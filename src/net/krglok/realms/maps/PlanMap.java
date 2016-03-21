package net.krglok.realms.maps;

import java.io.File;
import java.util.Map;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlanMap
{

	private static final String DEFAULT_PLAN_NAME = "Default";
	private static final int DEFAULT_PLAN_EDGE = 20;
	private String name ;
	private static int edge;
	private ScanResult[][] plan;
//	private plan[][] planSet;
	private boolean isInit;
	private int sektor;
	
	/**
	 * erstellt eine leere PlanMap
	 */
	public PlanMap()
	{
		this.name = DEFAULT_PLAN_NAME;
		this.edge = DEFAULT_PLAN_EDGE;
		isInit = false;
	}
	
	/**
	 * Erstellt eine PlanMap , prueft ob plan vorhanden
	 * wenn nicht wird ein leerer Plan erstellt  
	 * @param name
	 * @param radius
	 * @param plan
	 */
	public PlanMap(String name, int radius, ScanResult[][] plan, int sektor)
	{
		super();
		this.name = name;
		this.edge = radius;
		this.plan = plan;
		this.setSektor(sektor);
		if (plan == null)
		{
			plan = new ScanResult[edge][edge];
			isInit = false;
		} else
		{
			isInit = true;
		}
	}
	
	/**
	 * ersttelt eine PlanMap und liest die aus dem File 
	 * prueft ob plan vorhanden
	 * wenn nicht wird ein leerer Plan erstellt  
	 * @param name
	 * @param radius
	 */
	public PlanMap(String name)
	{
		super();
		this.name = name;
		if (plan == null)
		{
			plan = new ScanResult[edge][edge];
			isInit = false;
		} else
		{
			isInit = true;
		}
	}
	
	

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static int getEdge()
	{
		return edge;
	}


	public ScanResult[][] getPlan()
	{
		return plan;
	}

	public void setPlan(ScanResult[][] plan)
	{
		this.plan = plan;
	}

	public boolean isInit()
	{
		return isInit;
	}

	public void setInit(boolean isInit)
	{
		this.isInit = isInit;
	}


	
	public int getSektor()
	{
		return sektor;
	}

	public void setSektor(int sektor)
	{
		this.sektor = sektor;
	}

	public static void writePlanData(String name, int radius, byte[][] planMap, String path , int sektor ) 
	{
		try
		{
			//Program FilesBuckitTestpluginsRealms
            File DataFile = new File(path, "plan_"+name+".yml");
            if (!DataFile.exists()) 
            {
            	DataFile.createNewFile();
            	System.out.println("create PlanMap file : "+path+""+"plan+_"+name+".yml");
            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            String base = String.valueOf(sektor);
            ConfigurationSection headerSec = config.createSection("HEADER");
            config.set(MemorySection.createPath(headerSec, "radius"),radius);
            config.set(MemorySection.createPath(headerSec, "name"),name);
            ConfigurationSection planSec = config.createSection(base);
            for (int i = 0; i < edge; i++)
			{
            	config.set(MemorySection.createPath(planSec, String.valueOf(i)),planMap[i]);
            }
            config.save(DataFile);
			
		} catch (Exception e)
		{
             System.out.println("PlanMap Write Exception  "+e.getStackTrace());
			 StackTraceElement[] st = new Throwable().getStackTrace();
			 if (st.length > 0)
			 {
				 name = st[0].getClassName()+":"+st[0].getMethodName();
			 }
		}
	

	}
	

	private static int readPlanRadius(String name, String path, int sektor)  
	{
		try
		{
            File DataFile = new File(path+ "", "plan_"+name+".yml");
            if (!DataFile.exists()) 
            {
    			System.out.println("File not found  : "+name);
    			return 0;
            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection("HEADER"))
            {
            	int radius = config.getInt("HEADER"+"."+"radius",0);
	    		return radius;
            }
		} catch (Exception e)
		{
		}
		return  0;
	}
	
	private static ScanResult[][] readPlanData(String name, String path , int sektor)  
	{
//        String base = "PLAN";
        String base = String.valueOf(sektor);
//        byte[][] planData;
		try
		{
			//Program FilesBuckitTestpluginsRealms
            File DataFile = new File(path+ "", "plan_"+name+".yml");
            if (!DataFile.exists()) 
            {
    			System.out.println("File not found  : "+name);
    			return null;
            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection("HEADER"))
            {
            	int radius = config.getInt("HEADER"+"."+"radius",0);
//            	this.name = config.getString("HEADER"+"."+"name","");
            	
            	ScanResult[][] planMap = new ScanResult[edge][edge];
	            if (config.isConfigurationSection(base))
	            {
	    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
	            	for (String ref : buildings.keySet())
	            	{
	            		planMap[Integer.valueOf(ref)] = (ScanResult[]) config.get(base+"."+ref);
	            	}
	            }
	    		return planMap;
            }
		} catch (Exception e)
		{
		}
		return  null;
	}
	
	public static PlanMap readPlanMap(String path, String name, int sektor)
	{
		int radius = readPlanRadius(name, path, sektor);
		ScanResult[][] plan = readPlanData(name, path, sektor );
		return new PlanMap(name, radius,plan, sektor);
	}
	
}
