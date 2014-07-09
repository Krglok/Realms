package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.data.LogList;
import net.krglok.realms.unit.RegimentList;
import net.krglok.realms.unit.RegimentType;

import org.junit.Test;

public class RegimentListTest
{

	@Test
	public void test()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logList = new LogList(path);
		String world = ""; 
		LocationData position = new LocationData("", 0.0, 0.0, 0.0);
		RegimentList regiments = new RegimentList(1);
		
		regiments.createRegiment(RegimentType.RAIDER.name(), world, 0, logList);
		
		fail("Not yet implemented");
	}

}
