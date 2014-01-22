package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.PlanMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettlePlanTest
{


	private String setStoreString(byte[] planRow)
	{
		char[] charRow = new char[planRow.length];
		String sRow = "";
		for (int i = 0; i < charRow.length; i++)
		{
			charRow[i] = (char) planRow[i];
		}
		sRow = String.valueOf(charRow);
		return sRow;
	}
	
//	private void writePlanData(byte[][] planMap, int radius, String name) 
//	{
//		try
//		{
//			//\\Program Files\\BuckitTest\\plugins\\Realms
//            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\", "plan_"+name+".yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
////            System.out.println(DataFile.exists());
//            HashMap<String,String> values; // = new HashMap<String,String>();
//            
//            FileConfiguration config = new YamlConfiguration();
//            config.load(DataFile);
//            
//            String base = "PLAN";
//            ConfigurationSection headerSec = config.createSection("HEADER");
//            config.set(MemorySection.createPath(headerSec, "radius"),radius);
//            config.set(MemorySection.createPath(headerSec, "name"),name);
//            ConfigurationSection planSec = config.createSection(base);
//            for (int i = 0; i < setRegionLength(radius); i++)
//			{
//				
//            	config.set(MemorySection.createPath(planSec, String.valueOf(i)),planMap[i]);
//            }
//            config.save(DataFile);
//			
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//		}
//	
//
//	}
	
	private byte[] getStroreString(String sRow, int radius)
	{
		byte[] planRow = new byte[radius];
		char[] charRow = new char[radius];
		
		charRow = sRow.toCharArray();
		for (int i = 0; i < charRow.length; i++)
		{
			planRow[i] = (byte) charRow[i];
		}
		return planRow;
	}
	
//	private byte[][] readPlanData(String name)  
//	{
//        String base = "PLAN";
//		try
//		{
//			//\\Program Files\\BuckitTest\\plugins\\Realms
//            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\", "plan_"+name+".yml");
//            if (!DataFile.exists()) 
//            {
//    			System.out.println("File not found  : "+name);
//    			return null;
//            }
//            
//            FileConfiguration config = new YamlConfiguration();
//            config.load(DataFile);
//            
//            if (config.isConfigurationSection("HEADER"))
//            {
//            	int radius = config.getInt("HEADER"+"."+"radius",0);
//            	String planName = config.getString("HEADER"+"."+"name","");
//            	
//            	byte[][] planMap = new byte[setRegionLength(radius)][setRegionLength(radius)];
//	            if (config.isConfigurationSection(base))
//	            {
//	    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
//	            	for (String ref : buildings.keySet())
//	            	{
//	            		planMap[Integer.valueOf(ref)] = (byte[]) config.get(base+"."+ref);
////	            		String sRow = config.getString(base+"."+ref,"");
////	            		planMap[Integer.valueOf(ref)] = getStroreString(sRow, radius);
//	            	}
//	            }
//	    		return planMap;
//            }
//		} catch (Exception e)
//		{
//		}
//		return null;
//	}


	
	private int setRegionLength(int radius)
	{
		return (2 * radius);
	}
	
	private byte[][] setDefaultHaupthaus(byte[][] planMap, int radius)
	{
		int settleRadius = planMap.length / 2 +1;
		int regionRadius = 7;
		int offsetX = 2;
		int offsetY = 3;
//		System.out.println(SettleRadius);
		int posX = settleRadius + offsetX; // + setRegionLength(regionRadius);
		int posY = settleRadius + offsetY; // + setRegionLength(regionRadius);
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX+i][posY] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY + setRegionLength(regionRadius)-1;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX+i][posY] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX][posY+i] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX + setRegionLength(regionRadius)-1;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX][posY+i] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX +regionRadius-1 ;
		posY = settleRadius + offsetY +regionRadius-1 ;
		if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
		{
			planMap[posX][posY] = (byte) 255;
		}
		
		return planMap;
	}

	private byte[] makeDefaultRow(int radius)
	{
		int len = setRegionLength(radius);
		byte[] row = new byte[len];
		int Dice = 15;
		for (int i = 0; i < row.length; i++)
		{
			int wuerfel = (int) (Math.random()*Dice+1);
			byte b = (byte) wuerfel;

			row[i] = b;
		}
		return row;
	}
	
	private byte[][] makeDefaultMapPlan(int radius)
	{
		int line =  setRegionLength(radius);
		byte[][] planMap = new byte[line][line];
	
		for (int i = 0; i < line; i++)
		{
			if (i == radius)
			{
				byte[] center = makeDefaultRow(radius);
				center[radius] = (byte) 255;
				planMap[i] = center;
			} else
			{
				planMap[i] = makeDefaultRow(radius);
			}
		}
		
		return planMap;
	}
	
	private String showMapPlan (byte[] mapRow )
	{
		String charRow = "";
		for (int i = 0; i < mapRow.length; i++) 
		{
			switch (mapRow[i])
			{
			case 1 : charRow = charRow+'S';
			break;
			case 2 : charRow = charRow+'G';
			break;
			case 3 : charRow = charRow+'D';
			break;
			case 7 : charRow = charRow+'B';
			break;
			case 8 : charRow = charRow+'w';
			break;
			case 12 : charRow = charRow+'s';
			break;
			case 13 : charRow = charRow+'G';
			break;
			case 14 : charRow = charRow+'g';
			break;
			case 15 : charRow = charRow+'i';
			break;
			case 16 : charRow = charRow+'c';
			break;
			case 17 : charRow = charRow+'L';
			break;
			case 18 : charRow = charRow+'l';
			break;
			case 24 : charRow = charRow+'T';
			break;
			case 31 : charRow = charRow+'g';
			break;
			case 56 : charRow = charRow+'d';
			break;
			case 110 : charRow = charRow+'M';
			break;
			case 85 : charRow = charRow+'#';
			break;
			case (byte) 254: charRow = charRow+'.';
			break;
			case (byte) 255: charRow = charRow+'X';
			break;
			default :
				charRow = charRow+' ';
			}
		}
		
		return charRow;
	}
	
	@Test
	public void test()
	{
		int radius = 32; 
		int sektor = 2;
		String name = "NewHaven";
		byte[][] mapPlan = makeDefaultMapPlan(radius);
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		
		

		setDefaultHaupthaus(mapPlan, radius);
		for (int i = 0; i < setRegionLength(radius); i++)
		{
			System.out.println("|"+showMapPlan(mapPlan[i])+"|");
		}
		System.out.println("Write File : "+name);
		PlanMap.writePlanData(name, radius, mapPlan, path, sektor);
		System.out.println("Clear Plan Data : ");
		for (int i = 0; i < setRegionLength(radius); i++)
		{
			for (int j = 0; j < setRegionLength(radius); j++)
			{
				mapPlan[i][j] = 0;
			}
		}

		System.out.println("Read File : "+name);
		PlanMap planMap = PlanMap.readPlanMap(path, name, sektor);
		mapPlan = planMap.getPlan();
		if (mapPlan == null)
		{
			System.out.println("Error Read File : "+name);
		} else
		{
			for (int i = 0; i < setRegionLength(radius); i++)
			{
				System.out.println(i+" |"+ConfigBasis.showPlanValue(mapPlan[i])+"|");
			}
		}
	}

}
