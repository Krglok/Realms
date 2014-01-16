package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.CommandArg;
import net.krglok.realms.RealmsSubCommandType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;

import org.bukkit.command.CommandSender;
import org.junit.Test;

public class ModelLoopTest
{
	private Boolean isOutput = false; // set this to false to suppress println
	private ServerTest server = new ServerTest();
	private DataTest testData = new DataTest();
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
				testData,
				message);

		return rModel;
	}
	
	private MessageTest getMessageData()
	{
		return this.message;
	}
	
	private RealmModel getRealmModel()
	{
		return this.rModel;
	}
	
	@SuppressWarnings("static-access")
	private boolean cmdSet( CommandArg commandArg)
	{
//		msg.add("/model set [AUTO] [true/false] ");
//		msg.add("/model set [COUNTER] [20-24000] ");
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		if (commandArg.size() < 2)
		{
			getMessageData().errorArgs(  RealmsSubCommandType.SET);
			return true;
		}

		String bName = commandArg.get(0);
		switch (bName)
		{
		case "counter" :
			int iValue = CommandArg.argToInt(commandArg.get(1));
			msg.add("Model SET Production Counter to [ "+iValue+" ]");
			break;
		case "auto":
			boolean bValue = CommandArg.argToBool(commandArg.get(1));
			msg.add("Model SET auto production to [ "+bValue+" ]");
			msg.add("Production Cycles with  [30]");
			msg.add("Tax Cycles with  [1]");
			break;
		default :
			getMessageData().errorArgWrong(  RealmsSubCommandType.SET);
			return true;
		}
		msg.add("");
		msg.add("");
		
		getMessageData().printPage(  msg, page);
		
		return true;
	}

	private boolean cmdActivate( CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		getRealmModel().OnEnable();
		if (getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			msg.add("[Realm Model] Enabled");
			msg.add(getRealmModel().getModelName()+" Vers.: "+getRealmModel().getModelVersion());
		} else
		{
			msg.add("[Realm Model] NOT Enabled");
			msg.add("Something unknown is wrong :(");
			getMessageData().log("[Realm Model] NOT Enabled. Something unknown is wrong :( ");
		}
		getMessageData().printPage(  msg, 1);
		return true;
	}

	private boolean cmdDeactivate(  CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		getRealmModel().OnDisable();
		if (getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			msg.add("[Realm Model] Disabled");
			msg.add(getRealmModel().getModelName()+" Vers.: "+getRealmModel().getModelVersion());
			msg.add("All Task are not executed !");
		} else
		{
			msg.add("[Realm Model] NOT Disabled");
			msg.add("Something unknown is wrong :(");
			getMessageData().log("[Realm Model] NOT Disabled. Something unknown is wrong :( ");
		}
		getMessageData().printPage(  msg, 1);
		return true;
	}

	private boolean cmdProduction(CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
//		msg.add("/model production ");
		if (getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		//  || (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED))
		{
			getRealmModel().OnProduction();
			msg.add("[Realm Model] Production");
			for (Settlement settle : getRealmModel().getSettlements().getSettlements().values())
			{
				msg.add(settle.getId()+" : "+settle.getName());
				msg.add("Storage  : "+settle.getWarehouse().getItemMax());
				msg.add("Capacity : "+settle.getResident().getSettlerMax());
				msg.add("Settlers : "+settle.getResident().getSettlerCount());
				msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
				msg.add("Happiness: "+settle.getResident().getHappiness());
				msg.add("Fertility: "+settle.getResident().getFertilityCounter());
				msg.add("Deathrate: "+settle.getResident().getDeathrate());
				msg.add("Required Items "+settle.getRequiredProduction().size());
				for (String itemRef : settle.getRequiredProduction().keySet())
				{
					Item item = settle.getRequiredProduction().getItem(itemRef);
					msg.add(item.ItemRef()+" : "+item.value());
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		getMessageData().printPage(  msg, 1);
		return true;
	}
	

	
	private boolean cmdInfo(RealmsSubCommandType subCommand, CommandArg commandArg)
	{
		// /settle info {page} {ID}
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		int id = 0;
		if (commandArg.size() < 1)
		{
			getMessageData().errorArgs(  subCommand);
			return true;
		}
		if (commandArg.size()>1)
		{
			page = CommandArg.argToInt(commandArg.get(0));
			id = CommandArg.argToInt(commandArg.get(1));
		} else
		{
			page = CommandArg.argToInt(commandArg.get(0));
		}
		
		if (getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			if (id == 0)
			{
				for (Settlement settle : getRealmModel().getSettlements().getSettlements().values())
				{
					msg.add("Settlement Info "+settle.getId()+" : "+settle.getName());
					msg.add("Storage  : "+settle.getWarehouse().getItemMax());
					msg.add("Residence: "+settle.getResident().getSettlerMax());
					msg.add("Settlers : "+settle.getResident().getSettlerCount());
					msg.add("Needed   : "+settle.getTownhall().getWorkerNeeded());
					msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
					msg.add("Building : "+settle.getBuildingList().size());
					msg.add("Bank     : "+((int) settle.getBank().getKonto()));
					msg.add("Food : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
					msg.add("Required Items "+settle.getRequiredProduction().size());
					msg.add("====================== ");
				}
			} else
			{
				Settlement settle = getRealmModel().getSettlements().getSettlement(id);
				if (settle != null)
				{
					msg.add("Settlement Info "+settle.getId()+" : "+settle.getName());
					msg.add("Residence : "+settle.getResident().getSettlerMax());
					msg.add("Settlers  : "+settle.getResident().getSettlerCount());
					msg.add("Needed    : "+settle.getTownhall().getWorkerNeeded());
					msg.add("Workers   : "+settle.getTownhall().getWorkerCount());
					msg.add("Happiness : "+settle.getResident().getHappiness());
					msg.add("Fertility : "+settle.getResident().getFertilityCounter());
					msg.add("Deathrate : "+settle.getResident().getDeathrate());
					msg.add("Bank      : "+((int) settle.getBank().getKonto()));
					msg.add("Storage   : "+settle.getWarehouse().getItemMax());
					msg.add("Building  : "+settle.getBuildingList().size());
					msg.add("Food      : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
					msg.add("====================== ");
					msg.add("Required Items : "+settle.getRequiredProduction().size());
					for (String itemRef : settle.getRequiredProduction().keySet())
					{
						Item item = settle.getRequiredProduction().getItem(itemRef);
						msg.add(" -"+item.ItemRef()+" : "+item.value());
					}
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		getMessageData().printPage(  msg, page);
		return true;
	}
	
	/**
	 * maximal 6 settlement werden verarbeitet !!
	 */
	private void makeProductionTick()
	{
		days++;
		rModel.OnProduction();
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
		System.out.println("FoodFactor:  "+rModel.getSettlements().getSettlement(1).getFoodFactor());
		System.out.println("SettlerFactor: "+rModel.getSettlements().getSettlement(1).getSettlerFactor());
		System.out.println("Entertain    : "+rModel.getSettlements().getSettlement(1).getEntertainFactor());
		System.out.println("=========================== "+Settlement.getCounter());
		for (Settlement settle : rModel.getSettlements().getSettlements().values())
		{
			System.out.println("Settlement : "+settle.getId()+" : "+settle.getName());
		}
		
		for (Building building :rModel.getSettlements().getSettlement(1).getBuildingList().getBuildingList().values())
		{
			if (building.getBuildingType() != BuildingType.BUILDING_HOME)
			{
				System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getHsRegionType()+" : "+building.getWorkerInstalled());
			}
		}
		System.out.println("Warehouse : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemMax());
		for (String itemRef : rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().keySet())
		{
			System.out.println(itemRef+" : "+rModel.getSettlements().getSettlement(1).getWarehouse().getItemList().getValue(itemRef));
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
	

	private void cmdAddHolzfaeller(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "holzfaeller", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddProdWaxe(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_waxe", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddProdWhoe(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "prod_whoe", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddSchreiner(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "schreiner", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddKornfeld(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "kornfeld", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}
	
	private void cmdAddBauernhaus(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_BAUERNHOF, hsRegion, "bauern_haus", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddBaecker(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_BAECKER, hsRegion, "haus_baecker", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddHome(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_HOME, hsRegion, "haus_einfach", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}

	private void cmdAddSchaefer(int sID)
	{
		Building newbuilding ;
		hsRegion++;
		newbuilding =  new Building(BuildingType.BUILDING_PROD, hsRegion, "schaefer", true);
		if (!Settlement.addBuilding(newbuilding,rModel.getSettlements().getSettlement(sID)))
		{
			System.out.println("Settlemen not found id = "+sID);
		}
	}
	
	private void cmdSettleInfo(String SettleId)
	{
		CommandArg commandArg = new CommandArg();
		commandArg.add(SettleId);
		commandArg.add(SettleId);
		RealmsSubCommandType subCommand = RealmsSubCommandType.INFO;
		
		cmdInfo(subCommand , commandArg);
		
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
		
		cmdSettleInfo("1");
		showResult();
		
		Boolean expected = true; 
		Boolean actual = false; 
		actual =  (rModel.getModelStatus() == ModelStatus.MODEL_PRODUCTION);
		assertEquals(expected, actual);
	}

}
