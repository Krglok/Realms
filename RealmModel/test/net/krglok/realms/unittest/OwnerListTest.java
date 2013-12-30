package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;

import org.junit.Test;

public class OwnerListTest
{

	@Test
	public void testOwnerList()
	{
		OwnerList oList = new OwnerList();
		int expected = 0;
		int actual = oList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddOwnerOwner()
	{
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "PC1", 0, false);
		oList.addOwner(PCOwner);
		int expected = 1;
		int actual = oList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetOwner()
	{
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "PC1", 0, false);
		oList.addOwner(PCOwner);
		int expected = 0;
		int actual = oList.getOwner("PC1").getId();
		assertEquals(expected, actual);
	}

	@Test
	public void testSetRealm()
	{
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "PC1", 0, false);
		oList.addOwner(PCOwner);
		oList.getOwner("PC1").setRealmID(99);
		int expected = 99;
		int actual = oList.getOwner("PC1").getRealmID();
		assertEquals(expected, actual);
	}

}
