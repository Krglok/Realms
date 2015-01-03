package net.krglok.realms.unittest;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataStoreSettlement;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class DataStoreSettlementTest
{

	private void printBuildingRow(Building building)
	{
		System.out.print("| ");
		System.out.print(building.getId());
		System.out.print(" | ");
		System.out.print(building.getSettleId());
		System.out.print(" | ");
		System.out.print(building.getOwnerId());
		System.out.print(" | ");
		System.out.print(building.getHsRegion());
		System.out.print(" | ");
		System.out.print(building.getBuildingType());
		System.out.print(" | ");
		System.out.println("");
	
	}

	private void printSettleRow(Settlement settle)
	{
		System.out.print(settle.getId());
		System.out.print(" | ");
		System.out.print(settle.getOwnerId());
		System.out.print(" | ");
		System.out.print(settle.getSettleType());
		System.out.print(" | ");
		System.out.print(settle.getName());
		System.out.print(" | ");
		System.out.println("");
	
	}

	private void printBuildingList(BuildingList bList)
	{
		System.out.println("BuildingList  ["+bList.size()+"] ");
		for (Building build : bList.values())
		{
			printBuildingRow(build);
		}
		
	}
	
	@Test
	public void testWriteData()
	{
		System.out.println("testSettleConvert ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(); 
		DataStoreSettlement settleData = new DataStoreSettlement(dataFolder);
		settleData.readDataList();
		
		SettlementList sList = data.getSettlements();
		System.out.println("Write SettleList : ["+sList.size()+"]");
		for (Settlement settle : sList.values())
		{
				printSettleRow(settle);
//				settleData.writeData(settle, String.valueOf(settle.getId()));
		}
		fail("Not yet implemented");
	}

	@Test
	public void testReadData()
	{
		System.out.println("testSettleConvert ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(); 
		DataStoreSettlement settleData = new DataStoreSettlement(dataFolder);
		ArrayList<String> refList = settleData.readDataList();
		SettlementList sList = new SettlementList();
		Settlement settlement = null;
		for (String refId : refList)
		{
			settlement = settleData.readData(refId);
			 sList.putSettlement(settlement);
		}
		
		System.out.println("Read SettleList : ["+sList.size()+"]");
		for (Settlement settle : sList.values())
		{
				printSettleRow(settle);
		}
		
		fail("Not yet implemented");
	}

	@Test
	public void testStoreData()
	{
		System.out.println("testSettleConvert ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(); 
		SettlementList sList = data.getSettlements();
		Settlement settlement = null;
		
		System.out.println("DataStore SettleList : ["+sList.size()+"]");
		for (Settlement settle : sList.values())
		{
				printSettleRow(settle);
				printBuildingList(settle.getBuildingList());
		}
		
		fail("Not yet implemented");
	}
	
}
