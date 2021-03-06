package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.tool.LogList;

import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlementTreasureTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	LocationData position  = new LocationData("SteamHaven",-519.5118200333327,68,-1415.4833680460988); 
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);

	@Test
	public void test()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getOwners();
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = testData.defaultRegionList();
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				position,
				settleType, 
				settleName, 
				0, 
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
//				logTest
				);

		int expected = 6;
		int actual = settle.getBuildingList().size(); 
		settle.initTreasureList();
		settle.expandTreasureList(settle.getBiome(), server);


		isOutput = true;
		if (isOutput)
		{
			System.out.println("==Settlement buildings =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion());
			}
			System.out.println("Treasure balance = "+settle.getBiome());
			for (Item item : settle.getTreasureList())
			{
				System.out.println(
						ConfigBasis.setStrleft(item.ItemRef(),13)+": "+
								settle.getWarehouse().getItemList().getValue(item.ItemRef())
						);
			}
		}
		assertEquals(expected, actual);
	}

}
