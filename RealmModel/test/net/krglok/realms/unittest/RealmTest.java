package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Realm;

import org.junit.Test;

public class RealmTest
{

//	@Test
//	public void testRealm()
//	{
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetId()
	{
		Realm realm = new Realm();
		int expected = 1;
		realm.setId(expected);
		int actual = realm.getId(); 
		assertEquals("ID test ",expected, actual);
	}

	@Test
	public void testSetId()
	{
		Realm realm = new Realm();
		int expected = 2;
		realm.setId(expected);
		int actual = realm.getId(); 
		assertEquals("ID test ",expected, actual);
	}

	@Test
	public void testGetName()
	{
		Realm realm = new Realm();
		String expected = "NewRealm";
		String actual = realm.getName(); 
		assertEquals("Name test ",expected, actual);
	}

	@Test
	public void testSetName()
	{
		Realm realm = new Realm();
		String expected = "MyRealm";
		realm.setName(expected);
		String actual = realm.getName(); 
		assertEquals("Name test ",expected, actual);
	}

	@Test
	public void testGetOwner()
	{
		Realm realm = new Realm();
		Owner expected = new Owner(0, MemberLevel.MEMBER_NONE, 0, "NPC1", 0, true);//null; 
		Owner actual = realm.getOwner(); 
		assertEquals("Owner test ",expected.getPlayerName(), actual.getPlayerName());
	}

	@Test
	public void testSetOwner()
	{
		Realm realm = new Realm();
		Owner expected = new Owner(0, MemberLevel.MEMBER_NONE, 0, "PC1", 0, false);
		realm.setOwner(expected);
		Owner actual = realm.getOwner(); 
		assertEquals("Owner test ",expected, actual);
	}

	@Test
	public void testGetMemberList()
	{
		Realm realm = new Realm();
		Owner member = new Owner(0, MemberLevel.MEMBER_NONE, 0, "MEMBER1", 0, false);
		int expected = 1;
		realm.addMember(member);	// insert 1
		realm.addMember(member);    // insert 1 again fails and only 1 is in list because List is unique 
		int actual = realm.getMemberList().size(); 
		assertEquals("MemberList test ",expected, actual);
	}

	@Test
	public void testSetMemberList()
	{
		Realm realm = new Realm();
		Owner member = new Owner(0, MemberLevel.MEMBER_NONE, 0, "MEMBER1", 0, false);
		int expected = 1;
		realm.addMember(member);
		int actual = realm.getMemberList().size(); 
		assertEquals("MemberList test ",expected, actual);
	}

	@Test
	public void testAddMember()
	{
		Realm realm = new Realm();
		Owner member = new Owner(0, MemberLevel.MEMBER_NONE, 0, "MEMBER1", 0, false);
		int expected = 1;
		realm.addMember(member);	// insert 1
		int actual = realm.getMemberList().size(); 
		assertEquals("MemberList test ",expected, actual);
	}

	@Test
	public void testIsNPCRealm()
	{
		Realm realm = new Realm();
		Boolean expected = true;
		Boolean actual = realm.isNPCRealm(); 
		assertEquals("isNPCRealm test ",expected, actual);
	}

	@Test
	public void testSetIsNPCRealm()
	{
		Realm realm = new Realm();
		Boolean expected = false;
		realm.setIsNPCRealm(expected);
		Boolean actual = realm.isNPCRealm(); 
		assertEquals("isNPCRealm test ",expected, actual);
	}

}
