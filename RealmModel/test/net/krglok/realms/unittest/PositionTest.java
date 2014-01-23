package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.LocationData;
//import net.krglok.realms.core.Position;

import org.junit.Test;

public class PositionTest
{

	@Test
	public void testToString()
	{
		LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);
		String expected = "SteamHaven"; //:-469.51819223615206:72:-1236.6592548015324";
		String actual = pos.getWorld();
		assertEquals(expected, actual);
	}

}
