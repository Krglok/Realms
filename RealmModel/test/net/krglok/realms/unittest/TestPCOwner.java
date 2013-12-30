package net.krglok.realms.unittest;
import static org.junit.Assert.*;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;

import org.junit.Test;


public class TestPCOwner
{

	@Test
	public void test1()
	{
		//  PC Owner
		Owner PCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "PC1", 0, false);
		boolean expected = false;
		boolean actual = PCOwner.isNPC();
		assertEquals("Owner ist ein PC ",expected,actual);
	}

	
}
