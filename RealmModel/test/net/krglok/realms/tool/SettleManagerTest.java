package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.krglok.realms.admin.AdminStatus;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.junit.Test;

public class SettleManagerTest
{

	@Test
	public void testSettleMgr()
	{
		ServerTest server = new ServerTest();
		DataTest     data = new DataTest();
		ConfigTest config = new ConfigTest();
		MessageTest   msg = new MessageTest();
		RealmModel rModel = new RealmModel(0, 0, server, config, data, msg);
		// read Settlements form File
//		data.initSettleDate();
		//start RealmOdel
    	rModel.OnEnable();
		
		SettleManager settleMgr;
		AdminStatus expected = AdminStatus.NONE;
		AdminStatus actual = rModel.getSettlements().getSettlement(1).settleManager().getStatus();
		if (expected != actual)
		{
			System.out.println("");
			System.out.println("testSettleMgr");
			System.out.println("Anzahl Settlements : "+rModel.getSettlements().getSettlements().size());
			for (Settlement settle : rModel.getSettlements().getSettlements().values())
			{
				settleMgr = settle.settleManager();
				System.out.print("Settlement     : "+settle.getId()+" : "+settle.getName());
				System.out.print("  Manager Mode   : "+settleMgr.getAdminMode());
				System.out.print("  Status : "+settleMgr.getStatus());
				System.out.println("");
			}
		}
		assertEquals(expected, actual);
		
	}
	
	
	private void showMarket(RealmModel rModel)
	{
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (TradeMarketOrder order :rModel.getTradeMarket().values())
		{
			idList.add(order.getId());
		}
		Collections.sort(idList);
		for (Integer id : idList)
		{
			TradeMarketOrder order = rModel.getTradeMarket().get(id);
			System.out.print(""+ConfigBasis.setStrright(String.valueOf(order.getId()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getSettleID()),2));
			System.out.print("|"+ConfigBasis.setStrleft(order.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getBasePrice()),6));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(order.getMaxTicks()),6));
			System.out.println("|");
		}

	}
	
	private void showStock(RealmModel rModel, Settlement settle)
	{
		ArrayList<String> idList = new ArrayList<String>();
		for (Item item : settle.getWarehouse().getItemList().values())
		{
			idList.add(item.ItemRef());
		}
		Collections.sort(idList);
		for (String id : idList)
		{
			Item item = settle.getWarehouse().getItemList().get(id);
			System.out.print("|"+ConfigBasis.setStrleft(item.ItemRef(),12)+"");
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(item.value()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(rModel.getServer().getBioneFactor( settle.getBiome(), Material.getMaterial(item.ItemRef()))),5));
			System.out.print("|"+ConfigBasis.setStrright(
					String.valueOf(
							64 - 
							(64 * rModel.getServer().getBioneFactor
									( settle.getBiome(), Material.getMaterial
											(item.ItemRef())
											)/ 100)),5));
			System.out.println("|");
		}

	}
	
	
	@Test
	public void testSettleMgrModel()
	{
		ServerTest server = new ServerTest();
		DataTest     data = new DataTest();
		ConfigTest config = new ConfigTest();
		MessageTest   msg = new MessageTest();
		
		RealmModel rModel = new RealmModel(0, 0, server, config, data, msg);
    	rModel.OnEnable();

		int settleId = 1;

		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		double expected = settle.getBank().getKonto();
		double amount = 1000;
		String userName = "TestUser";
		McmdDepositeBank bankCommand = new McmdDepositeBank(rModel, settleId, amount , userName );
		
		BuildPlanType bType = BuildPlanType.HOME;
		LocationData position = new LocationData("SteamHaven", 0.0, 0.0, 0.0);
		Player player = null;
		McmdBuilder builderCommand    = new McmdBuilder(rModel, settleId, bType, position, player);

		String itemRef = "WHEAT";
		int value = 500;
		double price = data.getPriceList().getBasePrice(itemRef);
		int delayDays = 10;
		McmdSellOrder sellCommand = new McmdSellOrder(rModel, settleId, itemRef, value, price, delayDays);

		itemRef = "LOG";
		value = 500;
		price = data.getPriceList().getBasePrice(itemRef);
		delayDays = 10;
		McmdBuyOrder buyCommand = new McmdBuyOrder(rModel, settleId, itemRef, value, price, delayDays);

		itemRef = "WOOL";
		value = 500;
		price = data.getPriceList().getBasePrice(itemRef);
		delayDays = 10;
		McmdSellOrder sellNext = new McmdSellOrder(rModel, settleId, itemRef, value, price, delayDays);
		
		String name = "NewColonist";
		LocationData centerPos = new LocationData("SteamHaven", 0.0, 0.0, 0.0);
		String owner = "NPC1";
		
		McmdColonistCreate colonistCommand = new McmdColonistCreate(rModel, name, centerPos, owner);
		
		rModel.OnCommand(bankCommand);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnCommand(builderCommand);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
//		rModel.OnCommand(sellCommand);
//		rModel.OnCommand(buyCommand);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
//		rModel.OnCommand(sellNext);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnCommand(colonistCommand);
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();

		
		System.out.println("");
		System.out.println("testSettleMgrModel");
		System.out.println("Settlement     : "+settle.getId()+" : "+settle.getName());
		System.out.println("Bank       : "+settle.getBank().getKonto()+"/"+expected);
		System.out.println("Builder    :"+settle.buildManager().getActualBuild().getBuildingType());
		System.out.println("MgrBuyList :"+settle.settleManager().getCmdBuy().size());
		System.out.println("MgrSellList:"+settle.settleManager().getCmdSell().size());
		System.out.println("ModelCmd   :"+rModel.getcommandQueue().size());
		System.out.println("TradeMgr SellOrder:");
		if (settle.tradeManager().getSellOrder() != null)
		{
			System.out.print("|"+settle.tradeManager().getSellOrder().getItemRef());
			System.out.print("|"+settle.tradeManager().getSellOrder().getAmount());
			System.out.print("|"+settle.tradeManager().getSellOrder().getPrice());
			System.out.println("|");
		}
		System.out.println("Market SellOrders :"+rModel.getTradeMarket().size());
		showMarket(rModel);
		System.out.println("Settlement     : "+settle.getId()+" : "+settle.getName());
		System.out.println("Warehouse :"+settle.getWarehouse().getItemList().size());
		showStock(rModel, settle);
		System.out.println("");
		
	}

}
