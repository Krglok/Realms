package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Bank;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class BankTest
{
	String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
	LogList logTest = new LogList(path);

	@Test
	public void testInitKonto()
	{
		Bank bank = new Bank(); //logTest);
		Double expected = Double.valueOf(33.3);
		bank.initKonto(expected,0);
		Double actual = bank.getKonto();
		logTest.run();
		assertEquals(expected, actual);
	}

	@Test
	public void testDepositKonto()
	{
		Bank bank = new Bank(); //logTest);
		Double expected = Double.valueOf(33.3);
		bank.depositKonto(expected,"Me",0);
		Double actual = bank.getKonto();
		logTest.run();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawKonto()
	{
		Bank bank = new Bank(); //logTest);
		Double expected = Double.valueOf(33.3);
		bank.initKonto(expected,0);
		expected = 13.3;
		bank.withdrawKonto(expected,"Me",0);
		expected = 33.3 - 13.3;
		Double actual = bank.getKonto();
		logTest.run();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawKontoNot()
	{
		Bank bank = new Bank(); //logTest);
		Double value = Double.valueOf(13.3);
		bank.initKonto(value,0);
		value = 33.3;
		Boolean expected = false;
		Boolean actual = bank.withdrawKonto(value,"Me",0);
		logTest.run();
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void testGetTransactionList()
//	{
//		Bank bank = new Bank(); //logTest);
//		Double value = Double.valueOf(33.3);
//		bank.initKonto(value,0);
//		value = 13.3;
//		bank.withdrawKonto(value,"Me",0);
//		int expected = 2;
//		int actual = bank.getTransactionList().size();
//		logTest.run();
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void testAddTransaction()
//	{
//		Bank bank = new Bank(); //logTest);
//		Double value = Double.valueOf(33.3);
//		bank.initKonto(value,0);
//		value = 13.3;
//		bank.withdrawKonto(value,"Me",0);
//		int expected = 2;
//		int actual = bank.getTransactionList().size();
//		logTest.run();
//		assertEquals(expected, actual);
//		
//	}

}
