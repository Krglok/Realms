package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.ServerTest;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlementBaeckerTest
{

	private Boolean isOutput = true; // set this to false to suppress println
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	@Test
	public void testSettlementBaecker()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		DataTest testData = new DataTest(logTest);
		OwnerList ownerList =  testData.getTestOwners();
		ServerTest server = new ServerTest();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>();   //testData.defaultRegionList();
		regionTypes.put("1","HALL");
		regionTypes.put("2","WAREHOUSE");
		regionTypes.put("3","HOME");
		regionTypes.put("4","HOME");
		regionTypes.put("5","HOME");
		regionTypes.put("6","HOME");
		regionTypes.put("7","HOME");
		regionTypes.put("10","WHEAT");
		regionTypes.put("11","WHEAT");
		regionTypes.put("12","WHEAT");
		regionTypes.put("13","WHEAT");
		regionTypes.put("14","WHEAT");
		regionTypes.put("15","WHEAT");
		regionTypes.put("16","WHEAT");
		regionTypes.put("20","BAKERY");
		regionTypes.put("30","WOODCUTTER");
		regionTypes.put("31","WOODCUTTER");
		regionTypes.put("32","WOODCUTTER");
		regionTypes.put("33","WOODCUTTER");
		regionTypes.put("34","WOODCUTTER");
		regionTypes.put("35","CARPENTER");
		regionTypes.put("40","PICKAXESHOP");
		regionTypes.put("50","COALMINE");
		regionTypes.put("51","IRONMINE");
		regionTypes.put("52","SMELTER");
		
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
				Biome.FOREST,
				logTest
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
		settle.doProduce(server);
		settle.doProduce(server);
		settle.doProduce(server);
		settle.doProduce(server);
		settle.doProduce(server);
		
		int expected = 2;
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
				System.out.print(ConfigBasis.setStrleft(bItem.getName(),12));
				System.out.print("|"+ConfigBasis.setStrright(bItem.getLastValue(),9));
				System.out.print("|"+ConfigBasis.setStrright(bItem.getCycleSum(),9));
				System.out.print("|"+ConfigBasis.setStrright(settle.getWarehouse().getItemList().getValue(bItem.getName()),7));
				System.out.println("");
			}
		}
		
		assertEquals(expected, actual);

	}

}
