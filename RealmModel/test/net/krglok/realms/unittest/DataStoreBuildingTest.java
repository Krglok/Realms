package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataStoreBuilding;
import net.krglok.realms.data.LogList;

import org.junit.Test;

public class DataStoreBuildingTest
{

	private void printBuildingRow(Building building)
	{
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
	
	
	@Test
	public void testBuildingConvert()
	{
		System.out.println("testBuildingConvert ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest); 
		DataStoreBuilding buildingData = new DataStoreBuilding(dataFolder);
		buildingData.readDataList();
		
		SettlementList sList = data.getSettlements();
		for (Settlement settle : sList.values())
		{
			System.out.println("Settle :"+settle.getId()+" | "+settle.getName());
			for (Building building : settle.getBuildingList().values())
			{
				building.setSettleId(settle.getId());
				building.setOwnerId(settle.getOwnerId());
//				printBuildingRow(building);
//				buildingData.writeData(building, String.valueOf(building.getId()));
			}
		}
		
		fail("Not yet implemented");
	}
	
	@Test
	public void testBuildingRead()
	{
		System.out.println("testBuildingRead ============================================");
		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(dataFolder);
		DataTest data = new DataTest(logTest); 
		DataStoreBuilding buildingData = new DataStoreBuilding(dataFolder);
		ArrayList<String> refList = buildingData.readDataList();
		BuildingList bList = new BuildingList();
		Building building = null;
		System.out.println("BuildingList : ["+refList.size()+"] ");
		for (String refId : refList)
		{
			building = buildingData.readData(refId);
//			printBuildingRow(building);
			bList.putBuilding(building);
		}
		System.out.println("BuildingList Settle: ["+"1"+"] ");
		for (Building build : bList.getSubList(1).values())
		{
			printBuildingRow(build);
		}
		System.out.println("BuildingList Settle: ["+"1 | HOME"+"] ");
		for (Building build : bList.getSubList(1,BuildPlanType.HOME).values())
		{
			printBuildingRow(build);
		}
		
	}

}
