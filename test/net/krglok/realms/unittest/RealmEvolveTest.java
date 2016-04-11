package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdColonyBuild;
import net.krglok.realms.model.McmdEnable;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.tool.LogList;

import org.bukkit.block.Biome;
import org.junit.Test;

/**
 * <pre>
 * Test for Model Funktions.
 * Needs many predefined Data, so a Snapshot of the real serverdata should be used.
 * Event will be tested
 * - testOnTick
 * 
 * </pre>
 * @author olaf.duda
 *
 */

public class RealmEvolveTest
{

	private Boolean isOutput = false; // set this to false to suppress println
	
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	/**
	 * Erzeugt eine Liste der Settlements von Draskoria
	 * @param settleList
	 * @return
	 */
	private ArrayList<String> printSettleInfo(SettlementList settleList)
	{
		String sRow;
		ArrayList<String> msg = new ArrayList<String>();
		sRow = ConfigBasis.setStrleft("ID", 3) 
			  +"|"+ConfigBasis.setStrleft("Name", 10)
			  +"|"+ConfigBasis.setStrleft("Biome", 10)
			  +"|"+ConfigBasis.setStrright("Build", 5)
			  +"|"+ConfigBasis.setStrright("Type", 7)
			  ;
		msg.add(sRow );
		for (Settlement settle : settleList.values()) 
		{
			if (settle.getPosition().getWorld().equalsIgnoreCase("DRASKORIA"))
			{
				sRow = ConfigBasis.setStrright(settle.getId(), 3) 
					  +"|"+ConfigBasis.setStrleft(settle.getName(), 10)
					  +"|"+ConfigBasis.setStrleft(settle.getBiome().name(), 10)
					  +"|"+ConfigBasis.setStrright(settle.getBuildingList().size(),5)
					  +"|"+ConfigBasis.setStrleft(settle.getSettleType().name(),7)
					  ;
				msg.add(sRow );
			}
		}
//		Biome.EXTREME_HILLS_MOUNTAINS SMALL_MOUNTAINS
		return msg;
	}

	
	private void printMsg(ArrayList<String> msg)
	{
		for (String s : msg)
		{
			System.out.println(s);
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
		ArrayList<String> msg = new ArrayList<String>();

		ServerTest server = new ServerTest(data);
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
		
		McmdColonistCreate colonist1 = new McmdColonistCreate(rModel, name, centerPos, owner);
		
		boolean isCleanUp = true;
		
		Boolean expected = true; 
		Boolean actual = false;
		int i = 0;

//		rModel.OnProduction("DRASKORIA");
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
//		rModel.OnTick();
		
		actual =  (rModel.getcommandQueue().size() == 0) & (rModel.getProductionQueue().size() == 0);
		msg = printSettleInfo(rModel.getSettlements());	
		isOutput = true; //(expected != actual); //true;
		if (isOutput)
		{
			System.out.println("============================================================");
			System.out.println(rModel.getModelName()+":"+rModel.getProductionQueue().size());
			System.out.println("Test OnTick ");
			System.out.println("CommandQueue : "+rModel.getcommandQueue().size());
			System.out.println("ProdQueue    : "+rModel.getProductionQueue().size());
			System.out.println("ModelStatus  : "+rModel.getModelStatus().name());
			System.out.println("Settlements  : "+rModel.getData().getSettlements().size());
			System.out.println("Buildings    : "+rModel.getData().getBuildings().size());
			System.out.println("NPC          : "+rModel.getData().getNpcs().size());
			System.out.println("============================================================");
			printMsg(msg);
		}
		assertEquals(expected, actual);
	}
	
	
	
}
