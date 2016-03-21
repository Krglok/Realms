package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;

import org.junit.Test;

public class KingdomTest
{

//	@Test
//	public void testRealm()
//	{
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetId()
	{
		Kingdom realm = new Kingdom();
		int expected = 1;
		realm.setId(expected);
		int actual = realm.getId(); 
		assertEquals("ID test ",expected, actual);
	}

	@Test
	public void testSetId()
	{
		Kingdom realm = new Kingdom();
		int expected = 2;
		realm.setId(expected);
		int actual = realm.getId(); 
		assertEquals("ID test ",expected, actual);
	}

	@Test
	public void testGetName()
	{
		Kingdom realm = new Kingdom();
		String expected = "NewRealm";
		String actual = realm.getName(); 
		assertEquals("Name test ",expected, actual);
	}

	@Test
	public void testSetName()
	{
		Kingdom realm = new Kingdom();
		String expected = "MyRealm";
		realm.setName(expected);
		String actual = realm.getName(); 
		assertEquals("Name test ",expected, actual);
	}

	@Test
	public void testGetOwner()
	{
		Kingdom realm = new Kingdom();
		Owner expected = new Owner(0, NobleLevel.COMMONER, 0, "NPC1", 0, true,"");//null; 
		Owner actual = realm.getOwner(); 
		assertEquals("Owner test ",expected.getPlayerName(), actual.getPlayerName());
	}

	@Test
	public void testSetOwner()
	{
		Kingdom realm = new Kingdom();
		Owner expected = new Owner(0, NobleLevel.COMMONER, 0, "PC1", 0, false,"");
		realm.setOwner(expected);
		Owner actual = realm.getOwner(); 
		assertEquals("Owner test ",expected, actual);
	}




	@Test
	public void testIsNPCRealm()
	{
		Kingdom realm = new Kingdom();
		Boolean expected = true;
		Boolean actual = realm.isNPCkingdom(); 
		assertEquals("isNPCRealm test ",expected, actual);
	}

	@Test
	public void testSetIsNPCRealm()
	{
		Kingdom realm = new Kingdom();
		Boolean expected = false;
		realm.setIsNPCkingdom(expected);
		Boolean actual = realm.isNPCkingdom(); 
		assertEquals("isNPCRealm test ",expected, actual);
	}

}
