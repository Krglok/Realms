package net.krglok.realms.tool;

import static org.junit.Assert.fail;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPosition;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.model.McmdColonyBuild;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.DataTest;
import net.krglok.realms.unittest.MessageTest;
import net.krglok.realms.unittest.ServerTest;

import org.junit.Test;

public class ColonyTest
{
//	private Boolean isOutput = false; // set this to false to suppress println
//	private String sb = "";
	int dayCounter = 0;
//	private int month = 0;
	LocationData pos = new LocationData("SteamHaven",-469.51819223615206,72,-1236.6592548015324);
//	private DataTest     data;
	private ConfigTest config;
	private MessageTest   msg;
	Settlement settle;
	RealmModel rModel;

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
	public ColonyTest()
	{
	
	}

	public void initTestEnvironment()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		LogList logTest = new LogList(path);
		DataStorage testData = new DataStorage(path);
		config = new ConfigTest();
		msg = new MessageTest();

		rModel = new RealmModel(0, 0, server, config, testData, msg); //, logTest);
    	rModel.OnEnable();
		settle = rModel.getSettlements().getSettlement(1);
		
	}

	/**
	 * make a normal Breed for ALL Settlements
	 * call OnTick 
	 * @param days
	 */
	public void doBreeding()
	{
		String colonyStatus = rModel.getColonys().get(1).getStatus();
			dayCounter++;
			rModel.OnTick();
			if ((dayCounter % 40) == 0)
			{
				rModel.OnProduction("SteamHaven");
			}
		String nextStatus = rModel.getColonys().get(1).getStatus();
		if (colonyStatus != nextStatus)
		{
			showColonyStatus(rModel);
		}
	}

	public void doDayLoop(int days)
	{
		int maxLoop = (days * 40) + 1;
		for (int i = 0; i < maxLoop; i++)
		{
			doBreeding();
		}
	}
	
	public void showBuildPos(BuildManager builder)
	{
		System.out.println("");
		System.out.print("|"+ConfigBasis.setStrleft(builder.getActualBuild().getBuildingType().name(),11));
		showPosition(builder.getActualPosition());
		System.out.println("");
		
	}
	
	public void showSchemaPos(Colony colony)
	{
		for (BuildPosition bPos : colony.getSettleSchema().getbPositions())
		{
			System.out.print("|"+ConfigBasis.setStrleft(bPos.getbType().name(),11));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(bPos.getPosition().getX()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(bPos.getPosition().getY()),5));
			System.out.print("|"+ConfigBasis.setStrright(String.valueOf(bPos.getPosition().getZ()),5));
			System.out.print("|");
			System.out.println("");
			
		}
	}
	
	public void showPosition(LocationData pos)
	{
		System.out.print("|"+pos.getX());
		System.out.print("|"+pos.getY());
		System.out.print("|"+pos.getZ());
		System.out.print("|");
		
	}

	
	public void showColonyStatus(RealmModel rModel)
	{
		for (Colony colony : rModel.getColonys().values())
		{
			System.out.print("--------------------------------------------------");
			System.out.println("");
			System.out.print("|"+colony.getId());
			System.out.print("|"+ConfigBasis.setStrleft(colony.getName(),17));
			System.out.print("|"+ConfigBasis.setStrleft(colony.getStatus(),10));
			showPosition(colony.getPosition());
			System.out.println("");
		}
	}

	public void showResultStatus(RealmModel rModel)
	{
		for (Colony colony : rModel.getColonys().values())
		{
			showColonyStatus(rModel);
			System.out.print("--------------------------------------------------");
			System.out.println("");
			showSchemaPos(colony);
			System.out.print("--");
			showBuildPos(colony.buildManager());
			System.out.print("--");
			System.out.println("");
		}
	}
	
	@Test
	public void test()
	{
		initTestEnvironment();
		LocationData centerPos = new LocationData("World", -260.0, 77.0, 102.0);
		McmdColonistCreate cmdColCreate = new McmdColonistCreate(rModel, "TestHome", centerPos, "Me");
		rModel.OnCommand(cmdColCreate);
		showColonyStatus(rModel);
		System.out.print("--------------------------------------------------");
		System.out.println("");
		doBreeding();
		doBreeding();
		McmdColonyBuild cmdBuild = new McmdColonyBuild(rModel, 1, false);
		rModel.OnCommand(cmdBuild);
		doBreeding();
		doBreeding();
		showResultStatus(rModel);
		
		fail("Not yet implemented");
	}

}
