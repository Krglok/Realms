package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.CmdColonistCreate;
import net.krglok.realms.CmdColonyBuild;
import net.krglok.realms.CmdColonyHelp;
import net.krglok.realms.CmdRealmNone;
import net.krglok.realms.CmdRealmsActivate;
import net.krglok.realms.CmdRealmsCheck;
import net.krglok.realms.CmdRealmsDeactivate;
import net.krglok.realms.CmdRealmsGetItem;
import net.krglok.realms.CmdRealmsHelp;
import net.krglok.realms.CmdRealmsInfoPricelist;
import net.krglok.realms.CmdRealmsProduce;
import net.krglok.realms.CmdRealmsSetItem;
import net.krglok.realms.CmdRealmsMap;
import net.krglok.realms.CmdRealmsVersion;
import net.krglok.realms.CmdSettleAddBuilding;
import net.krglok.realms.CmdSettleBank;
import net.krglok.realms.CmdSettleBuy;
import net.krglok.realms.CmdSettleCheck;
import net.krglok.realms.CmdSettleCreate;
import net.krglok.realms.CmdSettleGetItem;
import net.krglok.realms.CmdSettleHelp;
import net.krglok.realms.CmdSettleInfo;
import net.krglok.realms.CmdSettleList;
import net.krglok.realms.CmdSettleSell;
import net.krglok.realms.CmdSettleSetItem;
import net.krglok.realms.CmdSettleBuild;
import net.krglok.realms.CmdSettleTrader;
import net.krglok.realms.CmdSettleWarehouse;
import net.krglok.realms.RealmsCommand;

import org.junit.Test;

public class CmdRealmsHelpTest
{

	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
				new CmdRealmNone(),
				new CmdRealmsVersion(),
				new CmdRealmsHelp(),
				new CmdRealmsInfoPricelist(),
				new CmdRealmsActivate(),
				new CmdRealmsDeactivate(),
				new CmdRealmsProduce(),
				new CmdRealmsCheck(),
				new CmdRealmsSetItem(),
				new CmdRealmsGetItem(),
				new CmdRealmsMap(),
				new CmdRealmNone(),
				new CmdSettleCheck(),
				new CmdSettleCreate(),
				new CmdSettleHelp(),
				new CmdSettleList(),
				new CmdSettleInfo(),
				new CmdSettleWarehouse(),
				new CmdSettleBank(),
				new CmdSettleBuy(),
				new CmdSettleSell(),
				new CmdSettleSetItem(),
				new CmdSettleGetItem(),
				new CmdSettleTrader(),
				new CmdSettleBuild(),
				new CmdColonistCreate(),
				new CmdColonyBuild(),
				new CmdSettleAddBuilding(),
				new CmdColonyHelp()
//				new CmdRealmsTest()
			
		};
		return commandList;
	}

	
	@Test
	public void testHelpCommand()
	{
		CmdRealmsHelp cmdHelp = new CmdRealmsHelp();

		ArrayList<String> msg = new ArrayList<String>();
		
		msg = cmdHelp.makeHelpPage(makeCommandList(),  msg, "" );

		CmdSettleHelp cmdSetleHelp = new CmdSettleHelp();
		msg = cmdSetleHelp.makeHelpPage(makeCommandList(),  msg, "" );

		CmdColonyHelp cmdColonyHelp = new CmdColonyHelp();
		msg = cmdColonyHelp.makeHelpPage(makeCommandList(),  msg, "" );
		
		for (String s : msg)
		{
			System.out.println(s);
		}
		
		fail("Not yet implemented");
	}

}
