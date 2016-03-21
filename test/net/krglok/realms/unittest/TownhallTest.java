package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Townhall;

import org.junit.Test;

public class TownhallTest
{

	@Test
	public void testTownhall()
	{
		Townhall townhall = new Townhall();
		int expected = 0;
		int actual = townhall.getWorkerCount();
		assertEquals(expected, actual);
	}

}
