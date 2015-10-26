package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdColonyBuild;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.tool.LogList;

import org.junit.Test;

/**
 * <pre>
 * Test for Model Funktions.
 * Needs many predefined Data, so a Snapshot of the real serverdata should be used.
 * Event will be tested
 * - testOnTick
 * - ohne Colonist
 * 
 * </pre>
 * @author olaf.duda
 *
 */

public class RealmModelTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);


	private void printProductionOverview(Settlement settle)
	{
		System.out.println("=Production Overview =");
		System.out.print(ConfigBasis.setStrleft("Name",12));
		System.out.print("|"+ConfigBasis.setStrright("Input",9));
		System.out.print("|"+ConfigBasis.setStrright("InSum",9));
		System.out.print("|"+ConfigBasis.setStrright("OutSum",9));
		System.out.print("|"+ConfigBasis.setStrright("Store",7));
		System.out.println("|");
		for (String ref : settle.getProductionOverview().sortItems())
		{
			BoardItem bItem = settle.getProductionOverview().get(ref);
			System.out.print(ConfigBasis.setStrleft(bItem.getName(),12));
			System.out.print("|"+ConfigBasis.setStrright(bItem.getInputValue(),9));
			System.out.print("|"+ConfigBasis.setStrright(bItem.getInputSum(),9));
			System.out.print("|"+ConfigBasis.setStrright(bItem.getOutputSum(),9));
			System.out.print("|"+ConfigBasis.setStrright(settle.getWarehouse().getItemList().getValue(bItem.getName()),7));
			System.out.println("|");
		}
	}
	
	private void printProductionBuilding(Settlement settle)
	{
		for (Building building :settle.getBuildingList().values())
		{
			if (building.getBuildingType() != BuildPlanType.HOME)
			{
				System.out.println( building.getId()+":"+building.getBuildingType() +":" +building.getHsRegionType()+" : "+building.getWorkerInstalled());
			}
		}
		
	}
	
	private void printWarehouse(Settlement settle)
	{
		System.out.println("Warehouse : "+settle.getWarehouse().getItemMax());
		for (String itemRef : settle.getWarehouse().getItemList().keySet())
		{
			System.out.println(itemRef+" : "+settle.getWarehouse().getItemList().getValue(itemRef));
		}
		
	}
	
	
	private ArrayList<String> getNscStartwerte(DataStorage data, int settleId)
	{
		ArrayList<String> msg = new ArrayList<String>();
		String s = ConfigBasis.setStrleft("Name",12)
				+"|"+ConfigBasis.setStrright("Npc", 5)
				+"|"+ConfigBasis.setStrright("Work", 5)
				+"|"+ConfigBasis.setStrright("Money", 9)
				;
		msg.add(s);
		for (NpcData npc : data.getNpcs().getSubListSettle(settleId).values())
		{
			if ((npc.getSettleId() == settleId)
				&& (npc.isAlive())
				)
			{
				s = ConfigBasis.setStrleft(npc.getName(),12)
						+"|"+ConfigBasis.setStrright(npc.getId(), 5)
						+"|"+ConfigBasis.setStrright(npc.getWorkBuilding(), 5)
						+"|"+ConfigBasis.setStrformat2(npc.getMoney(), 9)
						;
				msg.add(s);
			}
		}
		
		return msg;
	}
	

	/**
	 * Ausgabe einer Stringliste zur Console
	 * Die String mueessen bereits formatiert sein
	 * 
	 * @param msg
	 */
	public void printConsole(ArrayList<String> msg)
	{
		for (int i = 0; i < msg.size(); i++)
		{
			System.out.println(msg.get(i));
		}
	}
	
	@Test
	public void testOnTick() {
		ConfigTest config = new ConfigTest();
		config.initConfigData();
		config.initRegionBuilding();
		config.initSuperSettleTypes();
		int realmCounter = config.getRealmCounter();
		int settlementCounter = config.getSettlementCounter();

		ServerTest server = new ServerTest(data);
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
//		LogList logTest = new LogList(path);
//		DataStorage testData = new DataStorage(path);
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
		
		rModel.OnEnable();
		
		String name = "Newhome";
		LocationData centerPos = new LocationData("Test", 0.0, 0.0, 0.0);
		String owner = "drAdmin";
		
//		McmdColonistCreate colonist1 = new McmdColonistCreate(rModel, name, centerPos, owner);
		
		boolean isCleanUp = true;
		int colonyId = 1;
//		McmdColonyBuild colonist2 = new McmdColonyBuild(rModel, colonyId, isCleanUp);
		
		Boolean expected = true; 
		Boolean actual = false;
		double startKonto = rModel.getSettlements().getSettlement(6).getBank().getKonto();
		ArrayList<String> startNsc = getNscStartwerte(data,6);
		
		for (int j = 0; j < 7; j++)
		{
			rModel.OnProduction("SteamHaven");
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
			
			rModel.OnProduction("DRASKORIA"); //"SteamHaven");
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
		}
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);
		double endKonto = rModel.getSettlements().getSettlement(6).getBank().getKonto();
		ArrayList<String> endNsc = getNscStartwerte(data,6);

		isOutput = true; // (expected != actual); //true;
		if (isOutput)
		{
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("Test OnTick ");
			System.out.println("CommandQueue : "+rModel.getcommandQueue().size());
			System.out.println("ProdQueue    : "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			
			Settlement settle = rModel.getData().getSettlements().getSettlement(6);
			System.out.println("Settle Income : "+ConfigBasis.setStrformat2(startKonto,13)+" : "+ConfigBasis.setStrformat2(endKonto,13)+":"+ConfigBasis.setStrformat2(endKonto-startKonto,13));
			printProductionOverview(settle);
			System.out.println("Start NPC : ");
			printConsole(startNsc);
			System.out.println("End NPC : ");
			printConsole(endNsc);
		}
		assertEquals(expected, actual);
	}
	
	
	
}
