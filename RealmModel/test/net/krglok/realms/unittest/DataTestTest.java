package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;

import org.junit.Test;

/**
 * 
 * @author Windu
 *
 */
public class DataTestTest
{
	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void testInitOwnerList()
	{
		DataTest data = new DataTest();
		OwnerList oList = data.getTestOwners();
		int expected = 6; 
		int actual = oList.size();
		assertEquals(expected, actual);
		Owner owner = oList.getOwner("NPC0");

		if (isOutput)
		{	
			System.out.println("===Owner Test ====");
			System.out.println(owner.getPlayerName());
			System.out.println(owner.getLevel().name());
		}
	}

	@Test
	public void testInitRealmList()
	{
		DataTest data = new DataTest();
		KingdomList rList = data.getTestRealms();
		int expected = 1; 
		int actual = rList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Realm Test 1 ====");
			System.out.println(rList.getKingdom(1).getName());
			System.out.println(rList.getKingdom(1).getOwner().getPlayerName());
		}
	}

	@Test
	public void testInitRealmListOwner()
	{
		DataTest data = new DataTest();
		KingdomList rList = data.getTestRealms();
		int expected = 1; 
		int actual = rList.size();
		assertEquals(expected, actual);
		
		if (isOutput)
		{	
			System.out.println("===Realm Test 2 ====");
			System.out.println(rList.getKingdom(1).getName());
			System.out.println(rList.getKingdom(1).getOwner().getPlayerName());
		}
	}
	
	@Test
	public void testInitItemList()
	{
		TestServer server = new TestServer();
		ItemList iList = new ItemList();
		iList.depositItem("AIR",5);
		int expected = 5;
		int actual = iList.getValue("AIR");
		assertEquals(expected, actual);
		if (isOutput)
		{	
			System.out.println("===Item Test 1 ====");
			System.out.println(iList.size());
		}
	}
	
	@Test
	public void testInitItemWeaponList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getWeaponItems();
		int expected = 6;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 2 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("SWORD"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			System.out.println(i);
		}
	}

	@Test
	public void testInitItemArmorList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getArmorItems();
		int expected = 20;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 3 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("CHAIN"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			
			System.out.println(i);
		}
	}

	@Test
	public void testInitItemToolList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		int expected = 24;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 4 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("STONE"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			
			System.out.println(i);
		}
	}
	
}
