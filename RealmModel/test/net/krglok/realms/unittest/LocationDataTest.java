package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.LocationData;

import org.junit.Test;

public class LocationDataTest
{

	@Test
	public void testToStringLocationData()
	{
		String world = "SteamHaven";
		double posX = -473.747444;
		double posY = 68;
		double posZ = -1418.4211224333;
		LocationData position = new LocationData(world, posX, posY, posZ);
		
		String expected = world;
		String actual = position.getWorld();
		
		if (expected != actual)
		{
			System.out.println(LocationData.toString(position));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testToLocation()
	{
		String sPosition = "SteamHaven:-473.747444:68.0:-1418.4211224333";
		String world = "SteamHaven";
		double posX = -473.747444;
		double posY = 68;
		double posZ = -1418.4211224333;
		LocationData position = LocationData.toLocation(sPosition);
		Double expected = new LocationData(world, posX, posY, posZ).getX();
		Double actual =  position.getX();
		if (expected != actual)
		{
			System.out.println(position.getX());
		}
		assertEquals(expected, actual);
		
	}

}
