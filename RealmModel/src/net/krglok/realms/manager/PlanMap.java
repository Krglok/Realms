package net.krglok.realms.manager;

import java.io.File;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlanMap
{

	private static final String DEFAULT_PLAN_NAME = "Default";
	private static final int DEFAULT_PLAN_RADIUS = 32;
	private String name ;
	private int radius;
	private byte[][] plan;
	private boolean isInit;
	private int sektor;
	
	/**
	 * erstellt eine leere PlanMap
	 */
	public PlanMap()
	{
		this.name = DEFAULT_PLAN_NAME;
		this.radius = DEFAULT_PLAN_RADIUS;
		isInit = false;
	}
	
	/**
	 * Erstellt eine PlanMap , prueft ob plan vorhanden
	 * wenn nicht wird ein leerer Plan erstellt  
	 * @param name
	 * @param radius
	 * @param plan
	 */
	public PlanMap(String name, int radius, byte[][] plan, int sektor)
	{
		super();
		this.name = name;
		this.radius = radius;
		this.plan = plan;
		this.setSektor(sektor);
		if (plan == null)
		{
			plan = new byte[PlanMap.getPlanSize(radius)][PlanMap.getPlanSize(radius)];
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
	public PlanMap(String name, int radius)
	{
		super();
		this.name = name;
		this.radius = radius;
		if (plan == null)
		{
			plan = new byte[PlanMap.getPlanSize(radius)][PlanMap.getPlanSize(radius)];
			isInit = false;
		} else
		{
			isInit = true;
		}
	}
	
	
	public static int getPlanSize(int radius)
	{
		return (2 * radius);
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	public byte[][] getPlan()
	{
		return plan;
	}

	public void setPlan(byte[][] plan)
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
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File(path, "plan_"+name+".yml");
            if (!DataFile.exists()) 
            {
            	DataFile.createNewFile();
            	System.out.println("create PlanMap file : "+path+"\\"+"plan+_"+name+".yml");
            }
            
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            String base = String.valueOf(sektor);
            ConfigurationSection headerSec = config.createSection("HEADER");
            config.set(MemorySection.createPath(headerSec, "radius"),radius);
            config.set(MemorySection.createPath(headerSec, "name"),name);
            ConfigurationSection planSec = config.createSection(base);
            for (int i = 0; i < getPlanSize(radius); i++)
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
	
//	private byte[] getStroreString(String sRow, int radius)
//	{
//		byte[] planRow = new byte[radius];
//		char[] charRow = new char[radius];
//		
//		charRow = sRow.toCharArray();
//		for (int i = 0; i < charRow.length; i++)
//		{
//			planRow[i] = (byte) charRow[i];
//		}
//		return planRow;
//	}

	private static int readPlanRadius(String name, String path, int sektor)  
	{
		try
		{
            File DataFile = new File(path+ "\\", "plan_"+name+".yml");
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
	
	private static byte[][] readPlanData(String name, String path , int sektor)  
	{
//        String base = "PLAN";
        String base = String.valueOf(sektor);
//        byte[][] planData;
		try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File(path+ "\\", "plan_"+name+".yml");
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
            	
            	byte[][] planMap = new byte[getPlanSize(radius)][getPlanSize(radius)];
	            if (config.isConfigurationSection(base))
	            {
	    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
	            	for (String ref : buildings.keySet())
	            	{
	            		planMap[Integer.valueOf(ref)] = (byte[]) config.get(base+"."+ref);
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
		byte[][] plan = readPlanData(name, path, sektor );
		if (radius == 0)
		{
			radius = DEFAULT_PLAN_RADIUS;
		}
		return new PlanMap(name, radius,plan, sektor);
	}
	
}
