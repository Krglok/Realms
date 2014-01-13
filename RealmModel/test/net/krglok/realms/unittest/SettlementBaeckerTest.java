package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;

import org.bukkit.Material;
import org.junit.Test;

public class SettlementBaeckerTest
{

	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void testSettlementBaecker()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		TestServer server = new TestServer();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
//		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
//		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","haus_baecker");
//		regionTypes.put("52","haus_baecker");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			if (b.getHsRegion() == 51)
			{
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
		}
//		settle.getWarehouse().depositItemValue(Material.LOG.name(), 64);
//		settle.getWarehouse().depositItemValue(Material.STICK.name(), 128);
		settle.getWarehouse().depositItemValue(Material.WHEAT.name(), 256);
		
		settle.doProduce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
//		settle.produce(server);
		
		int expected = 64;
		int actual = settle.getWarehouse().getItemList().getValue(Material.BREAD.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Baecker =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+building.getHsRegionType());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
		}
		
		assertEquals(expected, actual);

	}

}
