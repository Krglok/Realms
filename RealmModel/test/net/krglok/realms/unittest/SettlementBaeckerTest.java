package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.ServerTest;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlementBaeckerTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	@Test
	public void testSettlementBaecker()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("16","WHEAT");
		regionTypes.put("9","WAREHOUSE");
//		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","BAKERY");
//		regionTypes.put("52","haus_baecker");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.FOREST
				);

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
				b.addSlot(0, Material.BREAD.name(),config);
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
//		settle.doProduce(server);
//		settle.doProduce(server);
		
		int expected = 21;
		int actual = settle.getWarehouse().getItemList().getValue(Material.BREAD.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			System.out.println("==Settlement Baecker =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				System.out.println(building.getBuildingType().name()+"  id:"+building.getHsRegion());
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().keySet())
			{
				System.out.println(itemRef+":"+settle.getWarehouse().getItemList().getValue(itemRef));
			}
			System.out.println("=Production Overview =");
			for (BoardItem bItem : settle.getProductionOverview().values())
			{
				System.out.println(bItem.getName()+":"+bItem.getLastValue());
			}
		}
		
		assertEquals(expected, actual);

	}

}
