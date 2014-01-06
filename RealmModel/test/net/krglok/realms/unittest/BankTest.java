package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Bank;
import net.krglok.realms.model.LogList;

import org.junit.Test;

public class BankTest
{

	@Test
	public void testInitKonto()
	{
		LogList log = new LogList();
		Bank bank = new Bank(log);
		Double expected = Double.valueOf(33.3);
		bank.initKonto(expected);
		Double actual = bank.getKonto();
		assertEquals(expected, actual);
	}

	@Test
	public void testDepositKonto()
	{
		LogList log = new LogList();
		Bank bank = new Bank(log);
		Double expected = Double.valueOf(33.3);
		bank.depositKonto(expected,"Me");
		Double actual = bank.getKonto();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawKonto()
	{
		LogList log = new LogList();
		Bank bank = new Bank(log);
		Double expected = Double.valueOf(33.3);
		bank.initKonto(expected);
		expected = 13.3;
		bank.withdrawKonto(expected,"Me");
		expected = 33.3 - 13.3;
		Double actual = bank.getKonto();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithdrawKontoNot()
	{
		LogList log = new LogList();
		Bank bank = new Bank(log);
		Double value = Double.valueOf(13.3);
		bank.initKonto(value);
		value = 33.3;
		Boolean expected = false;
		Boolean actual = bank.withdrawKonto(value,"Me");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTransactionList()
	{
		LogList logList = new LogList();
		Bank bank = new Bank(logList);
		Double value = Double.valueOf(33.3);
		bank.initKonto(value);
		value = 13.3;
		bank.withdrawKonto(value,"Me");
		int expected = 2;
		int actual = bank.getTransactionList().size();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddTransaction()
	{
		LogList logList = new LogList();
		Bank bank = new Bank(logList);
		Double value = Double.valueOf(33.3);
		bank.initKonto(value);
		value = 13.3;
		bank.withdrawKonto(value,"Me");
		int expected = 2;
		int actual = bank.getTransactionList().size();
		assertEquals(expected, actual);
		
//		for (String wert : bank.getTransactionList().getLogList())
//		{
//			System.out.println(wert);
//			
//		}
	}

}
