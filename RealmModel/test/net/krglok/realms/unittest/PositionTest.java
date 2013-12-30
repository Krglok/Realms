package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Position;

import org.junit.Test;

public class PositionTest
{

	@Test
	public void testToString()
	{
		Position pos = new Position();
		String expected = "0.0,0.0,0.0";
		String actual = pos.toString();
		assertEquals(expected, actual);
	}

}
