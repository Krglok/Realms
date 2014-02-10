package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.RealmModel;

import org.bukkit.block.Biome;
import org.junit.Test;

public class RealmLoopTest
{

	private Boolean isOutput = true; // set this to false to suppress println
	int days = 0;
	int loopCount = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	@SuppressWarnings("unused")
	private Settlement createSettlement()
	{
		DataTest testData = new DataTest();
		OwnerList ownerList =  testData.getTestOwners();
		
		ConfigTest config = new ConfigTest();
		config.initRegionBuilding();
	
		HashMap<String,String> regionTypes = new HashMap<String,String>(); // testData.defaultRegionList();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("3","haus_einfach");
		regionTypes.put("4","haus_einfach");
		regionTypes.put("5","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("8","haus_einfach");
		regionTypes.put("9","haus_einfach");
		regionTypes.put("10","haus_einfach");
		regionTypes.put("11","haus_einfach");
		regionTypes.put("12","haus_einfach");
		regionTypes.put("13","haus_einfach");
		regionTypes.put("14","haus_einfach");
		regionTypes.put("15","haus_einfach");
		regionTypes.put("16","haus_einfach");
		regionTypes.put("17","haus_einfach");
		regionTypes.put("18","haus_einfach");
		regionTypes.put("19","haus_einfach");
		regionTypes.put("20","haus_einfach");
		regionTypes.put("60","taverne");
		regionTypes.put("65","kornfeld");
		regionTypes.put("66","kornfeld");
		regionTypes.put("67","kornfeld");
		regionTypes.put("68","kornfeld");
		regionTypes.put("69","markt");
		HashMap<String,String> regionBuildings = config. makeRegionBuildingTypes(regionTypes);

		SettleType settleType = SettleType.SETTLE_HAMLET;
		String settleName = "New Haven";
		
		Settlement settle = Settlement.createSettlement(
				pos, 
				settleType, 
				settleName, 
				ownerList.getOwner("NPC0").getPlayerName(),
				regionTypes, 
				regionBuildings,
				Biome.PLAINS
				);

		settle.getWarehouse().depositItemValue("WHEAT",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("BREAD",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("WOOD_HOE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_AXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_PICKAXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("LOG",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("STICK",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("COBBLESTONE",settle.getResident().getSettlerMax());
		
		settle.getResident().setSettlerCount(25);
		settle.setSettlerMax();
		return settle;
	}
	
	@SuppressWarnings("unused")
	private String showBalkenSettler(Settlement settle, boolean isDay)
	{
		int rs = settle.getResident().getSettlerCount();
		rs = (rs / 5) + 1; 
		int as = 0;
		as = rs;
		String sb = "";
		if (isDay)
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + "-";
			}
		} else
		{
			for (int j = 0; j < as+1; j++)
			{
				sb = sb + " ";
			}
		}
		return sb+"#" +"     "+ settle.getResident().getSettlerCount();
	}

	@SuppressWarnings("unused")
	private void showBuildings(Settlement settle)
	{
		System.out.println("== Buildings "+settle.getBuildingList().getBuildingList().size());
		for (Building buildg : settle.getBuildingList().getBuildingList().values())
		{
			System.out.println("- "+buildg.getHsRegion()+" : "+buildg.getHsRegionType()+" :W "+buildg.getWorkerInstalled()+" :E "+buildg.isEnabled());
		}
		
	}

	@SuppressWarnings("unused")
	private void showWarehouse(Settlement settle)
	{
		System.out.println("== Warehouse ["+settle.getWarehouse().getItemCount()+"/"+settle.getWarehouse().getItemMax()+"]");
		for (Item item : settle.getWarehouse().getItemList().values())
		{
			System.out.println("- "+item.ItemRef()+" : "+item.value());
		}
	}
	
	private void showReqList(Settlement settle)
	{
		String reqI = "Requsted Item ["+settle.getRequiredProduction().size()+"] "+"-";
		for (String itemRef : settle.getRequiredProduction().keySet())
		{
			reqI = reqI+itemRef+":"+settle.getRequiredProduction().getItem(itemRef).value()+"-";
		}
		System.out.println(reqI);
	}
	
	private void makeSettleAnalysis(Settlement settle, int moth)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add(" ");
		msg.add("Sieldungstatus  ================= ["+settle.getId()+"]");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount());
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());

		msg.add("!  ");
		msg.add("Bevölkerungsanalyse  ");
		if (settle.getResident().getSettlerCount() > settle.getResident().getSettlerMax())
		{
			msg.add("!  Sie haben Überbevölkerung in der Siedlung. Dies macht die Siedler unglücklich auf lange Sicht!");
		}
		if (settle.getResident().getHappiness() < 0)
		{
			msg.add("!  Ihre Siedler sind unglücklich. Das wird sie veranlassen zu verschwinden!");
		}
		if (settle.getFoodFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler leiden Hunger. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getSettlerFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler haben keinen Wohnraum. Das ist wohl der Grund warum sie unglücklich sind!");
		}
		if (settle.getEntertainFactor() < 0.9)
		{
			msg.add("!  Ihre Siedler haben wenig Unterhaltung. Etwas mehr Unterhltung macht sie glücklicher!");
		}
		if ((settle.getFoodFactor() < 0.0) && (settle.getResident().getSettlerCount() < 8))
		{
			msg.add("!  Ihre Siedler sind verhungert. Sie haben als Verwalter versagt!");
			msg.add("!  Es würde mich nicht wundern, wenn eine Revolte ausbricht!!");
		}

		msg.add("  ");
		msg.add("Wirtschaftsanalyse  ");
		msg.add("!  Ihre Siedler haben "+(int)(settle.getBank().getKonto())+" Thaler erarbeitet.  Herzlichen Glückwunsch.");
		
		if (settle.getTownhall().getWorkerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Arbeiter. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() < settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Es fehlen Siedler. Deshalb produzieren einige Gebäude nichts!");
		}
		if (settle.getResident().getSettlerCount() > settle.getTownhall().getWorkerNeeded())
		{
			msg.add("!  Sie haben "+(settle.getResident().getSettlerCount() -settle.getTownhall().getWorkerNeeded())+" Siedler ohne Arbeit. Sie könnten neue Arbeitsgebäude bauen !");
		}

		if (settle.getRequiredProduction().size() > 0)
		{
			msg.add("!  Es fehlen "+settle.getRequiredProduction().size()+" verschiedene Rohstoffe zur Produktion.");
		}
		
		if ((settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()) < 512)
		{
			msg.add("!  Die Lagerkapazität ist knapp !  Freie Kapazitäte nur "+(settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()));
		}
		
		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	private void doLoop(int loopMax, RealmModel rModel)
	{
		int dayCount = (int) ConfigBasis.GameDay;
		for (int i = 0; i < loopMax; i++)
		{
			loopCount++;
			rModel.OnTick();
			if ((loopCount % dayCount) == 0)
			{
				rModel.OnProduction();
				rModel.OnTrade();
			} 
		}
	}
	
	@Test
	public void testRealmModelLoop()
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		ServerTest server = new ServerTest();
		DataTest testData = new DataTest();
		MessageTest message = new MessageTest();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				testData,
				message);
//		String command = RealmsCommandType.REALMS.name();
//		String subCommand = "version";
//		RealmsCommand realmCommand = new CmdRealmsVersion();
		
		Boolean expected = true; 
		Boolean actual = false; 
		rModel.OnEnable();
		int settleId = 1;
		rModel.getSettlements().getSettlement(settleId).getWarehouse().depositItemValue("LOG", 32);

		int loopMax = (int) ConfigBasis.GameDay * 2;
		doLoop(loopMax, rModel);
		McmdBuilder modelCommand = new McmdBuilder(rModel, settleId, BuildPlanType.HOME, new LocationData("SteamHaven", -456, 68, -1287));
		rModel.OnCommand(modelCommand);

		loopMax = (int) ConfigBasis.GameDay * 10;
		doLoop(loopMax, rModel);
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

		if (isOutput)
		{
			days = (int) (loopCount / ConfigBasis.GameDay);
			int month = days / 30;
			System.out.println(" ");
			System.out.println("== Laufzeit "+days+" Tage ");
			for (Settlement settle : rModel.getSettlements().getSettlements().values())
			{
				makeSettleAnalysis(settle, month);
//				showBuildings(settle);
//				showWarehouse(settle);
				showReqList(settle);
			}
			int i = 0;
			for (ItemLocation iLoc : rModel.getSettlements().getSettlement(settleId).buildManager().getBuildRequest())
			{
				System.out.println(i+" > "+iLoc.itemRef().name()+":"+(int)iLoc.position().getX()+":"+(int)iLoc.position().getY()+":"+ (int)iLoc.position().getZ());
				i++;
			}
			i = 0;
			for (ItemLocation iLoc : rModel.getSettlements().getSettlement(settleId).buildManager().getCleanRequest())
			{
				System.out.println(i+"<< "+iLoc.itemRef().name()+":"+(int)iLoc.position().getX()+":"+(int)iLoc.position().getY()+":"+ (int)iLoc.position().getZ());
				i++;
			}
		}
		assertEquals(expected, actual);
	}

}
