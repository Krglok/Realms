package net.krglok.realms.unittest;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.SettlementData;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class RegimentManagerTest {

	@Test
	public void testNewPosition() 
	{
		final double MIN_MONEY_FACTOR = 50.0; 
//		ConfigTest config = new ConfigTest();
		
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		
//		ItemPriceList priceList = testData.getPriceList();

//		File DataFile = new File(path, "Realms");
		SettlementData sData = new SettlementData(path);
		SettlementList settleList = new SettlementList(0);

		System.out.println("==Read Settlement from File ==");
		ArrayList<String> sList = sData.readSettleList();
		for (String sName : sList)
		{
			settleList.addSettlement(sData.readSettledata(Integer.valueOf(sName),testData.getPriceList())); //,logTest));
		}
		System.out.println("Settle Overview ");
		System.out.print("id"+"|Name        ");
		System.out.print(" |"+"Setler");
		System.out.print("|"+" Bank");
		System.out.print(" |"+" Stock");
//		System.out.print(" | "+"HiPrice   ");
//		System.out.print(" | "+"HiValue   ");
//		System.out.print(" |"+"Gold");
//		System.out.print(" |"+"Emer.");
		System.out.println(" ");
		for (Settlement settle : settleList.values())
		{
			System.out.print(settle.getId());
			System.out.print(" | "+ConfigBasis.setStrleft(settle.getName(),12));
			System.out.print(" | "+ConfigBasis.setStrleft(String.valueOf(settle.getResident().getSettlerCount()),2));
			System.out.print(" | "+ConfigBasis.setStrright(String.valueOf((int)settle.getBank().getKonto()),5));
//			System.out.print(" | "+ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemList().getValue(Material.GOLD_NUGGET.name())),3));
//			System.out.print(" | "+ConfigBasis.setStrright(String.valueOf(settle.getWarehouse().getItemList().getValue(Material.EMERALD.name())),2));
			System.out.println(" ");
		}


		fail("Not yet implemented");
	}

}
