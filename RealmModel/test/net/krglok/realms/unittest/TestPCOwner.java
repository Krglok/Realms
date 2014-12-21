package net.krglok.realms.unittest;
import static org.junit.Assert.*;

import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.LogList;
import net.krglok.realms.kingdom.Kingdom;

import org.junit.Test;


public class TestPCOwner
{

	@Test
	public void test1()
	{
		//  PC Owner
		Owner PCOwner = new Owner(0, NobleLevel.COMMONER, 0, "PC1", 0, false,"");
		boolean expected = false;
		boolean actual = PCOwner.isNPC();
		assertEquals("Owner ist ein PC ",expected,actual);
	}


	@Test
	public void testSettleOwner()
	{
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest);
		OwnerList ownerList = data.getOwners();
		System.out.println("");
		System.out.println("OwnerList "+"["+ownerList.size()+"]");
		for (Owner owner : ownerList.values())
		{
			System.out.print(owner.getId());
			System.out.print(" | ");
			System.out.print(owner.getPlayerName());
			System.out.print(" | ");
			System.out.print(owner.getUuid());
			System.out.print(" | ");
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Settle Owner List "+"["+" "+"]");
		for (Settlement settle :data.getSettlements().values())
		{
			System.out.print(settle.getId());
			System.out.print(" | ");
			System.out.print(settle.getName());
			System.out.print(" | ");
			System.out.print(settle.getOwnerId());
			System.out.print(" | ");
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Kingdom List "+"["+" "+"]");
		for (Kingdom kingdom :data.getKingdoms().values())
		{
			
			System.out.print(kingdom.getId());
			System.out.print(" | ");
			System.out.print(kingdom.getName());
			System.out.print(" | ");
			System.out.print(kingdom.getOwnerId());
			System.out.print(" | ");
			System.out.println("");
			for (Owner member : kingdom.getMembers().values())
			{
				System.out.print(" + ");
				System.out.print(member.getId());
				System.out.print(" | ");
				System.out.print(member.getPlayerName());
				System.out.println(" | ");
			}
		}
//		data.writeOwner(ownerList.getOwner(3));
//		data.writeOwner(ownerList.getOwner(4));
		
		
//		boolean expected = false;
//		boolean actual = true;
	}

}
