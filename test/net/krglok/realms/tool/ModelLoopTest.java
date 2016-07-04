package net.krglok.realms.tool;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import net.krglok.realms.Common.Item;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.MessageTest;
import net.krglok.realms.unittest.ServerTest;

import org.junit.Test;

public class ModelLoopTest
{
	@SuppressWarnings("unused")
	private Boolean isOutput = false; // set this to false to suppress println
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
	LogList logTest = new LogList(dataFolder);
	private MessageTest message = new MessageTest();
	private RealmModel rModel;

	int hsRegion = 100;		// laufende BuildingID
	int days = 0;			// Tageszaehler, nach 30 tagen kommt eine TaxTask
	int month = 0; 			// Monnatzaehler 
	
	/**
	 * Instanziert das Model und mach die Grundkonfiguration
	 * @return
	 */
	private RealmModel initModel()
	{
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		
		rModel = new RealmModel(
				realmCounter, 
				settlementCounter,
				server,
				config,
				data,
				message
//				logTest
				);

		return rModel;
	}
	
	/**
	 * maximal 6 settlement werden verarbeitet !!
	 */
	private void makeProductionTick()
	{
		days++;
		rModel.OnProduction("SteamHaven");
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		rModel.OnTick();
		if (days >= 30)
		{
			days=0;
			month++;
			rModel.OnTax();
			rModel.OnTick();
			rModel.OnTick();
			rModel.OnTick();
			rModel.OnTick();
			rModel.OnTick();
			rModel.OnTick();
			rModel.OnTick();
		}
	}
	
	private void makeSettleAnalysis(Settlement settle, int moth)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add("Es sind "+month+" Monate  und "+days+"  Tage  vergangen, dies sind "+((month*30+days)/72)+"++ real Tage ");
		msg.add("Sieldungstatus  ========================");
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
		int foodRange = settle.getWarehouse().getItemList().getValue("WHEAT")/settle.getResident().getSettlerCount();
		msg.add("!  Die Nahrung reicht für weitere "+foodRange+" Tage");
		
		if (settle.getResident().getSettlerCount() > settle.getResident().getSettlerMax())
		{
			msg.add("!  Sie haben Überbevölkerung in der Siedlung. Dies macht die Siedler unglücklich auf lange Sicht!");
		}
		if (settle.getResident().getHappiness() < 0)
		{
			msg.add("!  Ihre Siedler sind unglücklich. Das wird sie veranlassen zu verschwinden!");
		}
//		if (settle.getFoodFactor() < 0.0)
//		{
//			msg.add("!  Ihre Siedler leiden Hunger. Das ist wohl der Grund warum sie unglücklich sind!");
//		}
		if (settle.getSettlerFactor() < 0.0)
		{
			msg.add("!  Ihre Siedler haben keinen Wohnraum. Das ist wohl der Grund warum sie unglücklich sind!");
		}
//		if (settle.getEntertainFactor() < 0.9)
//		{
//			msg.add("!  Ihre Siedler haben wenig Unterhaltung. Etwas mehr Unterhltung macht sie glücklicher!");
//		}
//		if ((settle.getFoodFactor() < 0.0) && (settle.getResident().getSettlerCount() < 8))
//		{
//			msg.add("!  Ihre Siedler sind verhungert. Sie haben als Verwalter versagt!");
//			msg.add("!  Es würde mich nicht wundern, wenn eine Revolte ausbricht!!");
//		}

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

	private void showResult()
	{
		System.out.println("=====================================");
		System.out.println("=====================================");
		System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
		System.out.println("Settlement List: "+rModel.getSettlements().count());
		System.out.println("Settler Max    : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerMax());
		System.out.println("Settler Count  : "+rModel.getSettlements().getSettlement(1).getResident().getSettlerCount());
		System.out.println("OnProduction Queue: "+rModel.getProductionQueue().size());
		System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
//		System.out.println("FoodFactor:  "+rModel.getSettlements().getSettlement(1).getFoodFactor());
		System.out.println("SettlerFactor: "+rModel.getSettlements().getSettlement(1).getSettlerFactor());
//		System.out.println("Entertain    : "+rModel.getSettlements().getSettlement(1).getEntertainFactor());
		System.out.println("=========================== "+Settlement.getCounter());
		for (Settlement settle : rModel.getSettlements().values())
		{
			System.out.println("Settlement : "+settle.getId()+" : "+settle.getName());
		}
		
		System.out.println("Building List : "+rModel.getSettlements().getSettlement(1).getBuildingList().getBuildTypeList().size());
		for (String building :rModel.getSettlements().getSettlement(1).getBuildingList().getBuildTypeList().keySet())
		{
				System.out.println(ConfigBasis.setStrleft(building,15)+":"+rModel.getSettlements().getSettlement(1).getBuildingList().getBuildTypeList().get(building));
		}
		System.out.println(" ");
		System.out.println("Warehouse : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax());
		for (String itemRef : rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().keySet())
		{
			System.out.println(itemRef+" : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().getValue(itemRef));
		}
		System.out.println(" ");
		System.out.println("Warehouse Capacity: "+rModel.getSettlements().getSettlement(1).getWarehouse().getUsedCapacity()+"/"+(rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax()/64));
		for (Item item : rModel.getSettlements().getSettlement(1).getWarehouse().getTypeCapacityList().values())
		{
			System.out.println(item.ItemRef()+" Slot: "+item.value());
		}

		makeSettleAnalysis(rModel.getSettlements().getSettlement(1), days);

	}
	
	private void setDefaultWarehouse(int sID)
	{
		Settlement settle = rModel.getSettlements().getSettlement(sID);
		settle.getWarehouse().depositItemValue("WHEAT",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("BREAD",settle.getResident().getSettlerMax()*2 );
		settle.getWarehouse().depositItemValue("WOOD_HOE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_AXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD_PICKAXE",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("LOG",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("WOOD",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("STICK",settle.getResident().getSettlerMax());
		settle.getWarehouse().depositItemValue("COBBLESTONE",settle.getResident().getSettlerMax());
		
	}
	

	@SuppressWarnings("unused")
	private void cmdAddHolzfaeller(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.WOODCUTTER, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddProdWaxe(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.AXESHOP, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddProdWhoe(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.HOESHOP, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddSchreiner(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.CARPENTER, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddKornfeld(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.WHEAT, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}
	
	private void cmdAddBauernhaus(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.FARMHOUSE, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddBaecker(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.BAKERY, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddHome(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.HOME, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}

	private void cmdAddSchaefer(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildPlanType.SHEPHERD, hsRegion, null,sID,0);
		rModel.getBuildings().addBuilding(newbuilding);
		rModel.getSettlements().getSettlement(sID).setBuildingList(rModel.getBuildings().getSubList(sID));
	}
	
	
	@Test
	public void test()
	{
		
		RealmModel rModel = initModel();
		int sID = 1;

		// This is the first aktion in the Model !!!!!!!!!!!!!!!!!!
		rModel.OnEnable();

		setDefaultWarehouse(sID);
		
		cmdAddSchreiner(sID);

		int rounds = 12;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddKornfeld(sID);
		cmdAddKornfeld(sID);
		cmdAddProdWaxe(sID);
		cmdAddBauernhaus(sID);
		cmdAddBaecker(sID);

		rounds = 5;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddSchreiner(sID);
		cmdAddProdWhoe(sID);

		rounds = 5;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddSchreiner(sID);
		cmdAddProdWhoe(sID);

		rounds = 5;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddHome(sID);

		rounds = 5;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddSchaefer(sID);

		rounds = 5;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		cmdAddSchreiner(sID);
		cmdAddProdWhoe(sID);
		cmdAddBauernhaus(sID);
		
		rounds = 150;
		for(int i=0; i<rounds+1; i++) makeProductionTick();
		
		showResult();
		
		Boolean expected = true; 
		Boolean actual = false; 
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_PRODUCTION);
		assertEquals(expected, actual);
	}

}
