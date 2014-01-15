package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.RealmsCommandType;

import org.junit.Test;

public class RealmCommandTypeTest {

	private Boolean isOutput = false; // set this to false to suppress println


	@Test
	public void test() {
		RealmsCommandType rct = RealmsCommandType.MODEL;
		
		Boolean expected = true;
		Boolean actual = false; 
		isOutput = (expected != actual); //true;
		if (isOutput)
		{
			System.out.println("RealmModelCommandType:");
			System.out.println("Command : "+rct.name());
			System.out.println("Command : "+rct.name());

		}
		assertEquals(expected, actual);

	}

}
