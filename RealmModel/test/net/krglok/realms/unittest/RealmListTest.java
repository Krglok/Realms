package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Realm;
import net.krglok.realms.core.RealmList;

import org.junit.Test;

public class RealmListTest
{

	@Test
	public void testRealmList()
	{
		RealmList realmList = new RealmList(0);
		Realm realm = new Realm();
		realmList.addRealm(realm);
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		int expected = 4;
		int actual = realmList.size();
		assertEquals(expected, actual);

	}

	@Test
	public void testGetRealms()
	{
		RealmList realmList = new RealmList(0);
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		int expected = 3;
		int actual = realmList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testRealmInit()
	{
		RealmList realmList = new RealmList(5);
		int expected = realmList.getCounter()+1;
		realmList.addRealm(new Realm());
		int actual = realmList.getCounter();
		assertEquals(expected, actual);

	}
	
}
