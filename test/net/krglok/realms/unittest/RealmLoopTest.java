package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.Common.Item;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.tool.LogList;

import org.bukkit.block.Biome;
import org.junit.Test;

public class RealmLoopTest
{

	private Boolean isOutput = true; // set this to false to suppress println
	int days = 0;
	int loopCount = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
	
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
		System.out.println("== Buildings "+settle.getBuildingList().size());
		for (Building buildg : settle.getBuildingList().values())
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
		msg.add("Sieldungstatus === ["+settle.getId()+"] "+settle.getName());
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount());
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
//		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
//		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
//		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
//		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());

//		msg.add("!  ");
//		msg.add("Bevölkerungsanalyse  ");
//		if (settle.getResident().getSettlerCount() > settle.getResident().getSettlerMax())
//		{
//			msg.add("!  Sie haben Überbevölkerung in der Siedlung. Dies macht die Siedler unglücklich auf lange Sicht!");
//		}
//		if (settle.getResident().getHappiness() < 0)
//		{
//			msg.add("!  Ihre Siedler sind unglücklich. Das wird sie veranlassen zu verschwinden!");
//		}
//		msg.add("  ");
		msg.add("Wirtschaftsanalyse  ");
		msg.add("!  Ihre Siedler haben "+(int)(settle.getBank().getKonto())+" Thaler erarbeitet.  Herzlichen Glückwunsch.");
		
//		if (settle.getTownhall().getWorkerCount() < settle.getTownhall().getWorkerNeeded())
//		{
//			msg.add("!  Es fehlen Arbeiter. Deshalb produzieren einige Gebäude nichts!");
//		}
//		if (settle.getResident().getSettlerCount() < settle.getTownhall().getWorkerNeeded())
//		{
//			msg.add("!  Es fehlen Siedler. Deshalb produzieren einige Gebäude nichts!");
//		}
//		if (settle.getResident().getSettlerCount() > settle.getTownhall().getWorkerNeeded())
//		{
//			msg.add("!  Sie haben "+(settle.getResident().getSettlerCount() -settle.getTownhall().getWorkerNeeded())+" Siedler ohne Arbeit. Sie könnten neue Arbeitsgebäude bauen !");
//		}
//
//		if (settle.getRequiredProduction().size() > 0)
//		{
//			msg.add("!  Es fehlen "+settle.getRequiredProduction().size()+" verschiedene Rohstoffe zur Produktion.");
//		}
//		
//		if ((settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()) < 512)
//		{
//			msg.add("!  Die Lagerkapazität ist knapp !  Freie Kapazitäte nur "+(settle.getWarehouse().getItemMax()-settle.getWarehouse().getItemCount()));
//		}
		
//		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
	}
	
	private void doCleanRequest(RealmModel rModel)
	{
		for (Settlement settle : rModel.getSettlements().values())
		{
			settle.buildManager().getCleanRequest().clear();
		}
	}
	
	private void doBuildRequest(RealmModel rModel)
	{
		for (Settlement settle : rModel.getSettlements().values())
		{
			settle.buildManager().getBuildRequest().clear();
		}
	}

	private void doRegionRequest(RealmModel rModel)
	{
		for (Settlement settle : rModel.getSettlements().values())
		{
			settle.buildManager().getRegionRequest().clear();
		}
	}

	private void doChestRequest(RealmModel rModel)
	{
		for (Settlement settle : rModel.getSettlements().values())
		{
			settle.buildManager().getChestSetRequest().clear();
		}
	}
	
	
	/**
	 * simuliert die TickTask und ruft OnTick des Model auf 
	 * reduzierte Berechnung auf Welt SteamHaven , DRASKORIA
	 * OHNE Tax calculation
	 * 
	 * @param rMOdel
	 * @param simDays, alzahl der durchlauf tage
	 */
	private void simTick(RealmModel rModel, int simDays)
	{
		int maxLoop = (int) ConfigBasis.GameDay * simDays ;
		maxLoop = maxLoop + 100;
		int count = 0;
		int dayCount = 0;
		for (int i = 0; i < maxLoop; i++)
		{
			count++;
			rModel.OnTick();
			if (count ==  (int) ConfigBasis.GameDay)
			{
				dayCount++;
				System.out.println("Day Production ==================="+dayCount);
				count = 0;
				rModel.OnProduction("SteamHaven");
				rModel.OnTrade("SteamHaven");
				rModel.OnProduction("DRASKORIA");
				rModel.OnTrade("DRASKORIA");
				
			}
			doCleanRequest(rModel);
			doBuildRequest(rModel);
			doRegionRequest(rModel);
			doChestRequest(rModel);
		}
	}

//	/**
//	 * einfacher Loop 
//	 * @param loopMax
//	 * @param rModel
//	 */
//	private void doLoop(int loopMax, RealmModel rModel)
//	{
//		loopMax = loopMax - 2;
//		int dayCount = (int) ConfigBasis.GameDay;
//		for (int i = 0; i < loopMax; i++)
//		{
////			System.out.println("Tick");
//			loopCount++;
//			rModel.OnTick();
//			if ((loopCount % dayCount) == 0)
//			{
////				System.out.println("Day");
//				rModel.OnProduction("SteamHaven");
//				rModel.OnTrade("SteamHaven");
//				rModel.OnProduction("DRASKORIA");
//				rModel.OnTrade("DRASKORIA");
//			} 
//			doCleanRequest(rModel);
//			doBuildRequest(rModel);
//			doRegionRequest(rModel);
//			doChestRequest(rModel);
//		}
//	}
	
	@Test
	public void testRealmModelLoop()
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		LogList logTest = new LogList(dataFolder);
		MessageTest message = new MessageTest();
		data.initData();
		
		RealmModel rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				data,
				message 
//				logTest
				);
//		String command = RealmsCommandType.REALMS.name();
//		String subCommand = "version";
//		aRealmsCommand realmCommand = new CmdRealmsVersion();
		
		Boolean expected = true; 
		Boolean actual = false; 
		rModel.OnEnable();
		int settleId = 1;
		rModel.getSettlements().getSettlement(settleId).getWarehouse().depositItemValue("LOG", 32);

		int loopMax = (int) ConfigBasis.GameDay * 2;
//		doLoop(loopMax, rModel);
//		McmdBuilder modelCommand = new McmdBuilder(rModel, settleId, BuildPlanType.HOME, new LocationData("SteamHaven", -456, 68, -1287), null);
//		rModel.OnCommand(modelCommand);

//		loopMax = (int) ConfigBasis.GameDay * 2;
//		doLoop(loopMax, rModel);
//		
//		loopMax = (int) ConfigBasis.GameDay * 2;
//		doLoop(loopMax, rModel);
		days = 5;
		simTick(rModel, 5);
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);

//		if (expected != actual)
		{
//			days = (int) (loopCount / ConfigBasis.GameDay);
			int month = days / 30;
			System.out.println(" ");
			System.out.println("== Laufzeit "+days+" Tage ");
			for (Settlement settle : rModel.getSettlements().values())
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
			System.out.println("Command    Queue "+rModel.getcommandQueue().size());
			System.out.println("Production Queue "+rModel.getProductionQueue().size());
		}
		assertEquals(expected, actual);
	}

}
