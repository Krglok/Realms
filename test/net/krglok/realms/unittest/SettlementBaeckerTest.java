package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.tool.LogList;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlementBaeckerTest
{

	private Boolean isOutput = true; // set this to false to suppress println
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	private void showSettleInfo(Settlement settle)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Sieldungstatus  ========= ");
		msg.add("Biome         :"+settle.getBiome());
		msg.add("Age           : "+settle.getAge()+" Tage  ca. " + (settle.getAge()/30/12)+" Jahre ");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount()+"  :"+settle.getResident().getSettlerMax()+" Maximum");
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());
		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
	
	}

	
	@Test
	public void testSettlementBaecker()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
//		DataTest testData = new DataTest();
		OwnerList ownerList =  data.getOwners();
		ServerTest server = new ServerTest(data);
		
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
		regionTypes.put("36","CARPENTER");
		regionTypes.put("40","PICKAXESHOP");
		regionTypes.put("41","PICKAXESHOP");
		regionTypes.put("50","COALMINE");
		regionTypes.put("51","IRONMINE");
		regionTypes.put("52","SMELTER");
		regionTypes.put("60","COWSHED");
		regionTypes.put("61","TANNERY");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				0,
				regionTypes, 
				regionBuildings,
				Biome.FOREST
//				logTest
				);

		settle.getResident().setSettlerCount(25);
		for (Building b : settle.getBuildingList().values())
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
		settle.getWarehouse().depositItemValue(Material.LEATHER.name(), 50);
		
       long time1 = System.nanoTime();
		settle.doProduce(server,data,1);
       long time2 = System.nanoTime();
       System.out.println("Update Time [ms]: "+(time2 - time1)/1000000);
		settle.doProduce(server,data,2);
		settle.doProduce(server,data,3);
		settle.doProduce(server,data,4);
		settle.doProduce(server,data,5);
		settle.doProduce(server,data,6);
		
		int expected = 2;
		int actual = settle.getWarehouse().getItemList().getValue(Material.BREAD.name()); 
		isOutput = (expected !=  actual);
		if (isOutput)
		{
			showSettleInfo(settle);
			
			System.out.println("==Settlement Baecker =="+settle.getBuildingList().size());
			for (Building building : settle.getBuildingList().values())
			{
				System.out.print("id:"+ConfigBasis.setStrright(building.getHsRegion(),3));
				System.out.print(" : "+ConfigBasis.setStrleft(building.getBuildingType().name(),12));
				System.out.print(" : "+ConfigBasis.setStrleft(building.isEnabled().toString(),12));
				System.out.println();
			}
			
			System.out.println("=Warehouse ="+settle.getWarehouse().getItemMax()+":"+settle.getWarehouse().getItemCount());
			for (String itemRef : settle.getWarehouse().getItemList().sortItems())
			{
				System.out.print(ConfigBasis.setStrleft(itemRef,12));
				System.out.print(":"+ConfigBasis.setStrright(settle.getWarehouse().getItemList().getValue(itemRef),5));
				System.out.println();
			}
			System.out.println("=Production Overview =");
			for (String ref : settle.getProductionOverview().sortItems())
			{
				BoardItem bItem = settle.getProductionOverview().get(ref);
				System.out.print(ConfigBasis.setStrleft(bItem.getName(),12));
				System.out.print("|"+ConfigBasis.setStrright(bItem.getInputValue(),9));
				System.out.print("|"+ConfigBasis.setStrright(bItem.getInputSum(),9));
				System.out.print("|"+ConfigBasis.setStrright(settle.getWarehouse().getItemList().getValue(bItem.getName()),7));
				System.out.println("");
			}
		}
		
		assertEquals(expected, actual);

	}

}
