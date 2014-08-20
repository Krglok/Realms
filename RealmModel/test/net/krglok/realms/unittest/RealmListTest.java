package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;

import org.junit.Test;

public class RealmListTest
{

	@Test
	public void testRealmList()
	{
		KingdomList realmList = new KingdomList(0);
		Kingdom realm = new Kingdom();
		realmList.addKingdom(realm);
		realmList.addKingdom(new Kingdom());
		realmList.addKingdom(new Kingdom());
		realmList.addKingdom(new Kingdom());
		int expected = 4;
		int actual = realmList.size();
		assertEquals(expected, actual);

	}

	@Test
	public void testGetRealms()
	{
		KingdomList realmList = new KingdomList(0);
		realmList.addKingdom(new Kingdom());
		realmList.addKingdom(new Kingdom());
		realmList.addKingdom(new Kingdom());
		int expected = 3;
		int actual = realmList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testRealmInit()
	{
		KingdomList realmList = new KingdomList(5);
		int expected = realmList.getCounter()+1;
		realmList.addKingdom(new Kingdom());
		int actual = realmList.getCounter();
		assertEquals(expected, actual);

	}
	
}
