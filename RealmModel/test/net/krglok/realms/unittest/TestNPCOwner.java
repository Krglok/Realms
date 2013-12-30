package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;

import org.junit.Test;

public class TestNPCOwner
{

	@Test
	public void testOwnerNPC()
	{
		//  NPC Owner
		Owner NPCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "NPC1", 0, true);
		boolean expected = true;
		boolean actual = NPCOwner.isNPC();
		assertEquals("Owner ist ein NPC ",expected,actual);
	}

}
