package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.data.DataStoreOwner;
import net.krglok.realms.data.LogList;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.junit.Test;

public class DataStoreOwnerTest
{

	@Test
	public void testDataWriteOwner()
	{
		String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
		String fileName    = "owner";
		String sectionName = "OWNER";
		boolean timeMessure= false;
		DataStoreOwner dataOwner = new DataStoreOwner(dataFolder, fileName, sectionName, timeMessure);
		
//		private int id;
//		private String uuid;
//		private NobleLevel nobleLevel;
//		private CommonLevel commonLevel;
//		private int capital;
//		private String playerName;
//		private int realmID;
//		private Boolean isNPC;
//		private ArrayList<String> msg;  	// only temporrayr data
//		private SettlementList settleList;	// dynamic data initialize as subList from global settlelemntList
//		private LehenList lehenList;		// dynamic data initialize as subList from global lehenList

		Owner owner = new Owner();
		owner.setId(2);
		owner.setUuid("51bde8b9-bad8-4461-a375-7b0438c98271");
		owner.setPlayerName("drAdmin");
		owner.setNobleLevel(NobleLevel.COMMONER);
		owner.setCommonLevel(CommonLevel.SENATOR);
		owner.setCapital(0);
		owner.setKingdomId(0);
		owner.setIsNPC(false);

		Owner ownerNPC = new Owner();
		ownerNPC.setId(1);
		ownerNPC.setUuid("NPC1");
		ownerNPC.setPlayerName("NPC1");
		ownerNPC.setNobleLevel(NobleLevel.COMMONER);
		ownerNPC.setCommonLevel(CommonLevel.COUNCILOR);
		ownerNPC.setCapital(0);
		ownerNPC.setKingdomId(0);
		ownerNPC.setIsNPC(true);
		Achivement achiv = new Achivement(AchivementType.BOOK, AchivementName.HOME, true);
		ownerNPC.getAchivList().put(achiv.getName(), achiv);

		dataOwner.writeData(owner, String.valueOf(owner.getId()));
		dataOwner.writeData(ownerNPC, String.valueOf(ownerNPC.getId()));
			
		int expected = 2;
		int actual = owner.getId();
		if (expected != actual)
		{
			System.out.println("testDataStoreOwner "+"Expected: "+expected+" | "+actual);
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testDataReadOwner()
	{
		String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
		String fileName    = "owner";
		String sectionName = "OWNER";
		boolean timeMessure= false;
		DataStoreOwner dataOwner = new DataStoreOwner(dataFolder, fileName, sectionName, timeMessure);
		dataOwner.readDataList();
		Owner owner = dataOwner.readData("1");
		
		int expected = 1;
		int actual = owner.getId();
		if (expected != actual)
		{
			System.out.println("testDataReadOwner "+"Expected: "+expected+" | "+actual);
		}
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testDataReadOwnerList()
	{
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest);
		
		OwnerList ownerList = data.getOwners();

		int expected = 3;
		int actual = ownerList.size();
		
		if (expected != actual)
		{
			System.out.println("OwnerList "+"["+ownerList.size()+"]");
			for (Owner owner : ownerList.values())
			{
				System.out.print(owner.getId());
				System.out.print(" | ");
				System.out.println(owner.getPlayerName());
				System.out.print(" | ");
				System.out.println(owner.getUuid());
				System.out.print(" | ");
				System.out.println("");
			}
		}
		assertEquals(expected, actual);

	}
	
	
//	@Test
//	public void testDataWriteOwnerList()
//	{
//		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
//		LogList logTest = new LogList(dataFolder);
//		DataTest data = new DataTest(logTest);
//		
//		OwnerList ownerList = data.initOwners();
//		for (Owner owner : ownerList.values())
//		{
//			System.out.print("Owner Write Test ");
//			data.writeOwner(owner);
//		}
//	}
}
