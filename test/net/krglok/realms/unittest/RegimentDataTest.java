package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.DataStoreRegiment;
import net.krglok.realms.data.RegimentData;
import net.krglok.realms.tool.LogList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;
import net.krglok.realms.unit.RegimentType;

import org.junit.Test;

/**
 * Test of Regiment Datta store on file.
 * test in cycle
 * - read regiment list 
 * - Write test regiments
 * - read test regiments
 * 
 * @author Windu
 *
 */
public class RegimentDataTest
{

	@Test
	public void test()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
	
		DataStoreRegiment regData = new DataStoreRegiment(path);
		LogList logList = new LogList(path);
		String world = ""; 
		LocationData position = new LocationData("", 0.0, 0.0, 0.0);
		RegimentList regiments = new RegimentList(1);
		ArrayList<String> regList = regData.readDataList();
		
//		regiments.createRegiment(RegimentType.RAIDER.name(), world, 0); //, logList);
//		regiments.createRegiment(RegimentType.RAIDER.name(), world, 0); //, logList);
		
//        System.out.println("WRITE  regimentList =============");
//		for (Regiment regiment : regiments.values())
//		{
////            System.out.println("REGIMENT: "+regiment.getId()+":"+regiment.getName());
//            regData.writeRegimentData(regiment);
//		}

		System.out.println("READ  regimentList : ["+regList.size()+"]");
		for (String ref : regList)
		{
			Regiment regiment = regData.readData(ref);
			if (regiment != null)
			{
            System.out.println("READ: "+regiment.getId()+":"+regiment.getName()+":"+regiment.getWarehouse().getItemList().size());
			}
		}
		
		fail("Not yet implemented");

	}

	
}
