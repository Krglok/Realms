package net.krglok.realms.unittest;

import java.util.HashMap;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.TestServer;
import net.krglok.realms.settlemanager.NpcManager;
import net.krglok.realms.settlemanager.ProductBuilding;

import org.bukkit.Material;
import org.junit.Test;

public class NpcManagerTest {

	@Test
	public void testSetProductList() 
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
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
		regionTypes.put("31","bauern_haus");
		regionTypes.put("51","haus_baecker");
		regionTypes.put("52","haus_baecker");
		regionTypes.put("61","schmelze");
		regionTypes.put("41","werkstatt_haus");
		regionTypes.put("42","werkstatt_haus");
		
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(settleType, settleName, ownerList.getOwner("NPC0").getPlayerName(),regionTypes, regionBuildings);

		for (Building b : settle.getBuildingList().getBuildingList().values())
		{
			b.setIsActive(true);
			if (b.getHsRegion() == 51)
			{
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 52)
			{
				b.setIsActive(false);
				b.addSlot(Material.BREAD.name(),config);
				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
//				b.addSlot(Material.BREAD.name(),config);
			}
			if (b.getHsRegion() == 41)
			{
				b.setIsActive(false);
				b.addSlot("WOOD_AXE",config);
				b.addSlot("WOOD_HOE",config);
				b.addSlot(Material.WOOD_PICKAXE.name(),config);
				b.addSlot(Material.IRON_SWORD.name(),config);
				b.addSlot(Material.LEATHER_CHESTPLATE.name(),config);
			}
			if (b.getHsRegion() == 42)
			{
				b.setIsActive(true);
				b.addSlot("STICK",config);
				b.addSlot("WOOD",config);
				b.addSlot("WOOD",config);
				b.addSlot(Material.STICK.name(),config);
				b.addSlot(Material.WOOD.name(),config);
			}
		}
		NpcManager npc = new NpcManager(settle);
		
		npc.setProductList(server);
		
		System.out.println("NPC Manager ProduktBuilding"+" :"+npc.getProductList().size());
		for (ProductBuilding pb : npc.getProductList())
		{
			System.out.print(npc.getProductList().indexOf(pb)+":");
			System.out.println(pb.getItemRef()+":"+pb.getBuildingType()+":"+pb.getBuildingId());
		}
	}

}
