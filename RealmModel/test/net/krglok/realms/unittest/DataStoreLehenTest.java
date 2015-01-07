package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStoreLehen;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class DataStoreLehenTest
{

	private void initKingLehen(Lehen lehen)
	{
		lehen.setId(1);
		lehen.setKingdomId(1);
		lehen.setName("Freie Soeldner");
		lehen.setNobleLevel(NobleLevel.KING);
		lehen.setOwner(null);
		lehen.setOwnerId("");
		lehen.setParentId(0); // 0 = no parent
		lehen.setSettleType(SettleType.LEHEN_4);
		
	}
	
	private void initClaimCommoner(Lehen lehen)
	{
		lehen.setId(2);
		lehen.setKingdomId(1);
		lehen.setName("Steingau Mine");
		lehen.setNobleLevel(NobleLevel.COMMONER);
		lehen.setOwner(null);
		lehen.setOwnerId("");
		lehen.setParentId(0); // 0 = no parent
		lehen.setSettleType(SettleType.CLAIM);
		
	}

	private void initLehen3Lord(Lehen lehen)
	{
		lehen.setId(3);
		lehen.setKingdomId(1);
		lehen.setName("Steingau");
		lehen.setNobleLevel(NobleLevel.LORD);
		lehen.setOwner(null);
		lehen.setOwnerId("");
		lehen.setParentId(0); // 0 = no parent
		lehen.setSettleType(SettleType.LEHEN_3);
		
	}
	
	private void initLehen2Lord(Lehen lehen)
	{
		lehen.setId(4);
		lehen.setKingdomId(1);
		lehen.setName("Nordweg");
		lehen.setNobleLevel(NobleLevel.LORD);
		lehen.setOwner(null);
		lehen.setOwnerId("");
		lehen.setParentId(0); // 0 = no parent
		lehen.setSettleType(SettleType.LEHEN_2);
		
	}
	
	@Test
	public void testCreateLehen()
	{
		Lehen lehen = new Lehen();
		initClaimCommoner(lehen);
		
		int expected = 2;
		int actual = lehen.getId();
		assertEquals(expected, actual);
//		fail("Not yet implemented");
	}
	
	@Test
	public void testWriteLehen()
	{
		System.out.println("testWriteLehen ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		DataStoreLehen dataLehen = new DataStoreLehen(dataFolder);
		
		Lehen lehen = new Lehen();
		initKingLehen(lehen);
		dataLehen.writeData(lehen, String.valueOf(lehen.getId()));
		initClaimCommoner(lehen);
		dataLehen.writeData(lehen, String.valueOf(lehen.getId()));
		initLehen3Lord(lehen);
		dataLehen.writeData(lehen, String.valueOf(lehen.getId()));
		initLehen2Lord(lehen);
		dataLehen.writeData(lehen, String.valueOf(lehen.getId()));
		
	}

	@Test
	public void testReadLehen()
	{
		System.out.println("testReadLehen ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		DataStoreLehen dataLehen = new DataStoreLehen(dataFolder);
		dataLehen.readDataList();
		Lehen lehen = dataLehen.readData("1");
		int expected = 1;
		int actual = lehen.getId();
		assertEquals(expected, actual);
		
	}	
	
	private void printLehenNode(Lehen lehen)
	{
		System.out.print(lehen.getSettleType());
		System.out.print(" | ");
		System.out.print(lehen.getName());
		System.out.print(" | ");
		if (lehen.getOwner() != null)
		{
			System.out.print(lehen.getOwner().getPlayerName());
			System.out.print(" | ");
		}
		System.out.println("");
		
	}
	
	@Test
	public void testLehenList()
	{
		System.out.println("testLehenList ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest();

		LehenList lList = data.getLehen();

		lList.get(2).setParentId(3);
		lList.get(4).setParentId(3);
		lList.get(3).setParentId(1);
		
		System.out.println("");
		System.out.println("LehenList "+"["+data.getOwners().size()+"]");
		for (Lehen lehen : data.getLehen().values())
		{
			System.out.print(lehen.getId());
			System.out.print(" | ");
			System.out.print(lehen.getKingdomId());
			System.out.print(" | ");
			System.out.print(lehen.getParentId());
			System.out.print(" | ");
			System.out.print(lehen.getName());
			System.out.print(" | ");
			System.out.print(lehen.getSettleType());
			System.out.print(" | ");
			System.out.print(lehen.getOwnerId());
			System.out.print(" | ");
			System.out.println("");
			
		}

		System.out.println("");
		System.out.println("LehenTree "+"["+data.getOwners().size()+"]");
		for (Lehen lehen : data.getLehen().values())
		{
			if (lehen.getParentId() == 0)
			{
				printLehenNode(lehen);
				for (Lehen sub1 : data.getLehen().getChildList(lehen.getId()).values())
				{
					System.out.print(" + ");
					printLehenNode(sub1);
					for (Lehen sub2 : data.getLehen().getChildList(sub1.getId()).values())
					{
						System.out.print(" | ");
						System.out.print(" + ");
						printLehenNode(sub2);
					}
					for (Settlement settle : data.getSettlements().values())
					{
						if (sub1.getOwner() != null)
						{
							if (settle.getOwnerId() == sub1.getOwner().getPlayerName())
							{
								System.out.print(" | ");
								System.out.print(" + ");
								System.out.print(NobleLevel.COMMONER);
								System.out.print(" | ");
								System.out.print(settle.getId()+" | "+settle.getName());
								System.out.print(" | ");
								System.out.print(settle.getOwnerId());
								System.out.print(" | ");
								System.out.println("");
							}
						} else
						{
//							System.out.print(" | ");
//							System.out.print(" + ");
//							System.out.print("NULL");
//							System.out.println(" | ");
						}
					}
				}
			}
		}
		
	}
	
}
