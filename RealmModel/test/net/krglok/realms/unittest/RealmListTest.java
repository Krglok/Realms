package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.LogList;
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
		int expected = Kingdom.getID()+1;
		realmList.addKingdom(new Kingdom());
		int actual = Kingdom.getID();
		assertEquals(expected, actual);

	}
	
//	@Test
//	public void testKingdomWrite()
//	{
//		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
//		LogList logTest = new LogList(dataFolder);
//		DataTest data = new DataTest(logTest);
//		OwnerList ownerList = data.initOwners();
//
//		KingdomList kingdomList = data.initKingdoms();
//
//		Kingdom kingdom = new Kingdom();
//		kingdom.setName("Clan Freie Soeldner");
//		kingdom.setOwner(data.initOwners().getOwner(1));
//		kingdom.setIsNPCkingdom(true);
//		
//		kingdomList.addKingdom(kingdom);
//		for (Kingdom found : kingdomList.values())
//		{
//			data.writeKingdom(found);
//		}
//	}

	@Test
	public void testKingdomList()
	{
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest);
		OwnerList ownerList = data.initOwners();

		KingdomList kingdomList = data.initKingdoms();
		
		System.out.println("OwnerList "+"["+data.initOwners().size()+"]");
		for (Owner owner : data.initOwners().values())
		{
			System.out.print(owner.getId());
			System.out.print(" | ");
			System.out.print(owner.getPlayerName());
			System.out.print(" | ");
			System.out.print(" | ");
			System.out.println("");
			
		}
		
		System.out.println("KingdomList "+"["+kingdomList.size()+"]");
		for (Kingdom kingdom : kingdomList.values())
		{
			System.out.print(kingdom.getId());
			System.out.print(" | ");
			System.out.print(kingdom.getName());
			System.out.print(" | ");
			System.out.print(kingdom.getOwnerId());
			System.out.print(" | ");
			System.out.println(kingdom.getOwner().getPlayerName());
		}
				
		System.out.println("Settlement "+"["+kingdomList.size()+"]");
		for (Settlement settle : data.initSettlements().values())
		{
			System.out.print(settle.getId());
			System.out.print(" | ");
			System.out.print(settle.getName());
			System.out.print(" | ");
			System.out.print(settle.getOwnerId());
			System.out.print(" | ");
			if (settle.getOwner() != null)
			{
				System.out.print(settle.getOwner().getPlayerName());
			} else
			{
				System.out.print("null");
			}
			System.out.print(" | ");
			System.out.print(settle.getOwner().getKingdomId());
			System.out.print(" | ");
			System.out.println("");
			
		}
	}
}
