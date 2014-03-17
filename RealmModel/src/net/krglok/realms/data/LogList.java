package net.krglok.realms.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.krglok.realms.core.LocationData;

/**
 * <pre>
 * log file for internal transactions.
 * the data are stored as CSV to make it possible to read external
 * all data stored as string.
 * timestamp, type user,type,data1, data2, ....datax
 * every TransaType has a special dataformat and a special ad  method
 * 
 * if new File false all writes are redirected to console
 * </pre>
 * @author Windu
 *
 */
public class LogList
{
	private ArrayList<String> logList;
    File logFile;
    FileWriter logWrite;

	public LogList(String path )
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HH");
		String formattedDate = sdf.format(date);
		logList = new ArrayList<String>();
	    File logFile = new File(path, "protocol_"+formattedDate+".csv");
	    if (logFile.exists()== false) 
	    {
			System.out.println("NEW protocol.csv : "+path+":"+"protocol_"+formattedDate+".csv");
	    	try
			{
				logFile.createNewFile();
			    logWrite = new FileWriter(logFile);
			} catch (IOException e)
			{
				System.out.println("Exeption LogList : "+path+":"+"protocol_"+formattedDate+".csv");
				e.printStackTrace();
				logFile = null;
				logWrite = null;
			}
	    } else
	    {
		    try
			{
				logWrite = new FileWriter(logFile,true);
			} catch (IOException e)
			{
				e.printStackTrace();
				logWrite = null;
			}
	    }

	}

	public ArrayList<String> getLogList()
	{
		return logList;
	}

	public void setLogList(ArrayList<String> logList)
	{
		this.logList = logList;
	}

	/**
	 * Set a transaction text, there are automatic add datetime
	 * date / time / user / text
	 * @param text
	 */
	public void addText( String user, String text)
	{
		String DataType = "TEXT";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate + ";"+DataType+ ";" + user + ";" + text;
		this.logList.add(transaction);
	}

	public void addBank(String user, String text, int SettleId,double value)
	{
		String DataType = "BANK";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate 
				+ ";"+DataType
				+ ";" + user 
				+ ";" + text
				+";" + String.valueOf(SettleId) 
				+ ";" + String.valueOf(value);
		this.logList.add(transaction);
	}

	public void addProduction(String text,int SettleId, int buildingId, String itemRef, int value , String user, long age)
	{
		String DataType = "PRODUCTION";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(buildingId) 
				+";" + itemRef 
				+";" + String.valueOf(value)
				+";" + String.valueOf(age)
				;
		this.logList.add(transaction);
	}

	public void addProductionSale(String text,int SettleId, int buildingId,  double value , String user, long age)
	{
		String DataType = "SALE";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(buildingId) 
				+";" + String.valueOf(value)
				+";" + String.valueOf(age)
				;
		this.logList.add(transaction);
	}

	public void addHappiness(String text,int SettleId, double happiness, double entertainFactor, double settlerFactor, double foodFactor , String user, long age)
	{
		String DataType = "HAPPINESS";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(happiness) 
				+";" + String.valueOf(entertainFactor)
				+";" + String.valueOf(settlerFactor)
				+";" + String.valueOf(foodFactor)
				+";" + String.valueOf(age)
				;
		this.logList.add(transaction);
	}
	public void addSettler(String text,int SettleId, int settlerCount, int settlerBirthrate, int settlerDeathrate, String user, long age)
	{
//		settlerCount + settlerBirthrate - settlerDeathrate;

		String DataType = "SETTLER";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(settlerCount) 
				+";" + String.valueOf(settlerBirthrate)
				+";" + String.valueOf(settlerDeathrate)
				+";" + String.valueOf(age)
				;
		this.logList.add(transaction);
	}
	
	public void addOrder(String text,int SettleId, int buildingId, String itemRef, int value , double price, int timeOut ,String user)
	{
		String DataType = "ORDER";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(buildingId) 
				+";" + itemRef 
				+";" + String.valueOf(value) 
				+";" + String.valueOf(price) 
				+";" + String.valueOf(timeOut)
				;
		this.logList.add(transaction);
	}
	
	public void addLocation(String text,int SettleId, LocationData position , String user)
	{
		String DataType = "POSITION";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + position.getWorld() 
				+";" + String.valueOf(position.getX()) 
				+";" + String.valueOf(position.getY()) 
				+";" + String.valueOf(position.getZ())
				;
		this.logList.add(transaction);
	}
	
	
	public int size()
	{
		return logList.size();
	}
	
	/**
	 * Write LogDat to File
	 * normally used by onTick;
	 */
	public void run ()
	{
		for (String line : logList)
		{
			try
			{
				logWrite.append(line);
				logWrite.append("\n");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		logList.clear();
		try
		{
			logWrite.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
