package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class KingdomListTest
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

	private void printSettleListRow(Settlement settle)
	{
			System.out.print(settle.getId());
			System.out.print(" | ");
			System.out.print(settle.getName());
			System.out.print(" | ");
			if (settle.getOwner() != null)
			{
				System.out.print(settle.getOwner().getPlayerName());
			} else
			{
				System.out.print("null");
			}
			System.out.print(" | ");
			if (settle.getOwner() != null)
			{
				System.out.print(settle.getOwner().getKingdomId());
			}
			System.out.print(" | ");
			System.out.println("");
	}
	
	@Test
	public void testKingdomList()
	{
		System.out.println("testKingdomList ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest();

		KingdomList kingdomList = data.getKingdoms();
		
		System.out.println("");
		System.out.println("OwnerList "+"["+data.getOwners().size()+"]");
		for (Owner owner : data.getOwners().values())
		{
			System.out.print(owner.getId());
			System.out.print(" | ");
			System.out.print(owner.getPlayerName());
			System.out.print(" | ");
			System.out.print(owner.getKingdomId());
			System.out.print(" | ");
			System.out.println("");
			
		}
		
		// Kindom List
		System.out.println("");
		System.out.println("KingdomList "+"["+kingdomList.size()+"]");
		SettlementList kSettles;
		OwnerList kMembers;
		for (Kingdom kingdom : kingdomList.values())
		{
			kSettles = data.getSettlements().getSubList(kingdom);
			System.out.print(kingdom.getId());
			System.out.print(" | ");
			System.out.print(kingdom.getName());
			System.out.print(" | ");
			System.out.print(kingdom.getOwner().getPlayerName());
			System.out.print(" | ");
			System.out.print(kSettles.size());
			System.out.print(" | ");
			System.out.println("");
//			kMembers = data.getOwners().getSubList(kingdom);
			System.out.println(" | MemberList "+"["+kingdom.getMembers().size()+"]");
			for (Owner member : kingdom.getMembers().values())
			{
				System.out.print(" | ");
				System.out.print(member.getId());
				System.out.print(" | ");
				System.out.print(member.getPlayerName());
				System.out.print(" | ");
				System.out.println("");
			}
			System.out.println("");
		}
		
		// print all settlements		
		System.out.println("");
		System.out.println("Settlement "+"["+data.getSettlements().size()+"]");
		for (Settlement settle : data.getSettlements().values())
		{
		  printSettleListRow(settle);
		}


		for (Owner owner1 : data.getOwners().values())
		{
			SettlementList oSettels = data.getSettlements().getSubList(owner1);
			System.out.println("");
			System.out.println("=="+owner1.getPlayerName()+" Settlements "+"["+oSettels.size()+"]");
			for (Settlement settle : oSettels.values())
			{
				  printSettleListRow(settle);
				
			}
		}
		
	}
}
