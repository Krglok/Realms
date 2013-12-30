package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Resident;

import org.junit.Test;

public class ResidentTest
{

	@Test
	public void testResident()
	{
		Resident resident = new Resident();
		resident.setSettlerCount(5);
		int expected = 5;
		int actual = resident.getSettlerCount();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawSettler()
	{
		Resident resident = new Resident();
		resident.setSettlerCount(5);
		resident.withdrawSettler(2);
		int expected = 3;
		int actual = resident.getSettlerCount();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawSettlerNot()
	{
		Resident resident = new Resident();
		resident.setSettlerCount(2);
		boolean expected = false;
		boolean actual = resident.withdrawSettler(5);
		assertEquals(expected, actual);
	}
	

	
	@Test
	public void testWithdrawCow()
	{
		Resident resident = new Resident();
		resident.setCowCount(5);
		resident.withdrawCow(2);
		int expected = 3;
		int actual = resident.getCowCount();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawCowNot()
	{
		Resident resident = new Resident();
		resident.setCowCount(2);
		boolean expected = false;
		boolean actual = resident.withdrawCow(5);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWithdrawHorse()
	{
		Resident resident = new Resident();
		resident.setHorseCount(5);
		resident.withdrawHorse(2);
		int expected = 3;
		int actual = resident.getHorseCount();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawHorseNot()
	{
		Resident resident = new Resident();
		resident.setHorseCount(2);
		boolean expected = false;
		boolean actual = resident.withdrawHorse(5);
		assertEquals(expected, actual);
	}
	
}
