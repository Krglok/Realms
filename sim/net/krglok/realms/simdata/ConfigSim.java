package net.krglok.realms.simdata;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.krglok.realms.Common.ItemPrice;
import net.krglok.realms.unittest.DataFile;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Verwaltung der Simulations konstanten
 * diese Config Datei steht normalerweise im verzeichnis der Application
 * die default daten sind als static xx hinterlegt
 *  
 * @author Windu
 *
 */

public class ConfigSim
{
	public static String defaultPluginPath = "\\GIT\\OwnPlugins\\Realms\\plugins"; 
	public static String defaultPath = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
	public static String defaultDataPath = defaultPath;
	public static String defaultStrongholdPath = "\\GIT\\OwnPlugins\\Realms\\plugins\\HeroStronghold";
	public static int defaultSequenceStep = 10;
	
	protected static String base = "SIMULATION";
	protected static String fileName = "configsim.yml";
	protected String path;
	protected FileConfiguration config = new YamlConfiguration();

	public String dataPath = defaultDataPath;
	public String strongholdPath = defaultStrongholdPath;
	public boolean singleStep = false;
	public int sequenceStep = defaultSequenceStep;
	public boolean isInit = false;
	
	public ConfigSim(String path)
	{
		if (path == "")
		{
			this.path = defaultPath;
		} else
		{
			this.path = path;
		}
		readConfig();
	}
	

	
	public String getPath()
	{
		return path;
	}



	public void setPath(String path)
	{
		this.path = path;
	}



	public String getDataPath()
	{
		return dataPath;
	}



	public void setDataPath(String dataPath)
	{
		this.dataPath = dataPath;
	}



	public String getStrongholdPath()
	{
		return strongholdPath;
	}

	


	public void setStrongholdPath(String strongholdPath)
	{
		this.strongholdPath = strongholdPath;
	}



	public boolean isSingleStep()
	{
		return singleStep;
	}



	public void setSingleStep(boolean singleStep)
	{
		this.singleStep = singleStep;
	}



	public int getSequenceStep()
	{
		return sequenceStep;
	}

	public String getSequenceStepString()
	{
		return String.valueOf(sequenceStep);
	}


	public void setSequenceStep(int sequenceStep)
	{
		this.sequenceStep = sequenceStep;
	}

	public void setSequenceStep(String sequenceStep)
	{
		try
		{
			this.sequenceStep = Integer.valueOf(sequenceStep);
			
		} catch (NumberFormatException e)
		{
			this.sequenceStep = 0;
			System.out.println("Falsches Integerformat: sequenceStep");
		}
	}


	public boolean isInit()
	{
		return isInit;
	}



	public void setInit(boolean isInit)
	{
		this.isInit = isInit;
	}



	private void initReadData(FileConfiguration config)
	{
        if (config.isConfigurationSection(base))
        {
    		this.dataPath = config.getString(base+"."+"dataPath");
    		this.strongholdPath = config.getString(base+"."+"strongholdPath");
    		this.singleStep = config.getBoolean(base+"."+"singleStep",false);
    		this.sequenceStep = config.getInt(base+"."+"sequenceStep",0);
        }
	
	}
	
	public String readConfig()
	{
		try
		{
            File dataFile = new File(this.path, fileName);
            if (!dataFile.exists()) 
            {
            	dataFile.createNewFile();
            	writeConfig();
            	System.out.println("Write default config file");
            	return "File not found : write default !";
            }
            
            config.load(dataFile);
            initReadData(config);
            System.out.println("Read "+fileName);
            return "Read : true";
            
		} catch (IOException e)
		{
			e.printStackTrace();
			return " writeConfigSim : "+e.getMessage();
		} catch (InvalidConfigurationException e)
		{
			e.printStackTrace();
			return " writeConfigSim : "+e.getMessage();
		} catch (Exception e)
		{
			System.out.println("read "+fileName+" :: "+ e.getMessage());
			return " writeConfigSim : "+e.getMessage();
		}
		
	}
	

	private void initWriteData(FileConfiguration config, ConfigurationSection baseSec)
	{
        	config.set(MemorySection.createPath(baseSec,"dataPath"),this.dataPath);
        	config.set(MemorySection.createPath(baseSec,"strongholdPath"),this.strongholdPath);
        	config.set(MemorySection.createPath(baseSec,"singleStep"),this.singleStep);
        	config.set(MemorySection.createPath(baseSec,"sequenceStep"),this.sequenceStep);
	}
	
	public String writeConfig()
	{
		try
		{
            File dataFile = new File(this.path, fileName);
            if (!dataFile.exists()) 
            {
            	dataFile.createNewFile();
            }
			config.load(dataFile);
            ConfigurationSection baseSec = config.createSection(base);
            initWriteData(config, baseSec);
			config.save(dataFile);
			return "Write : true";
		} catch (IOException e)
		{
			System.out.println("Exception "+this.path+fileName);
			e.printStackTrace();
			return " writeConfigSim : "+e.getMessage();
		} catch (InvalidConfigurationException e)
		{
			e.printStackTrace();
			return " writeConfigSim : "+e.getMessage();
		} catch (Exception e)
		{
			System.out.println("write "+fileName+ e.getMessage());
			return " writeConfigSim : "+e.getMessage();
		}
	
	}
	
}
