package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.command.CmdColonistCreate;
import net.krglok.realms.command.CmdColonyBuild;
import net.krglok.realms.command.CmdColonyHelp;
import net.krglok.realms.command.CmdRealmNone;
import net.krglok.realms.command.CmdRealmsActivate;
import net.krglok.realms.command.CmdRealmsCheck;
import net.krglok.realms.command.CmdRealmsDeactivate;
import net.krglok.realms.command.CmdRealmsGetItem;
import net.krglok.realms.command.CmdRealmsHelp;
import net.krglok.realms.command.CmdRealmsMap;
import net.krglok.realms.command.CmdRealmsPricelistInfo;
import net.krglok.realms.command.CmdRealmsProduce;
import net.krglok.realms.command.CmdRealmsSetItem;
import net.krglok.realms.command.CmdRealmsVersion;
import net.krglok.realms.command.CmdSettleAddBuilding;
import net.krglok.realms.command.CmdSettleBank;
import net.krglok.realms.command.CmdSettleBuild;
import net.krglok.realms.command.CmdSettleBuy;
import net.krglok.realms.command.CmdSettleCheck;
import net.krglok.realms.command.CmdSettleCreate;
import net.krglok.realms.command.CmdSettleGetItem;
import net.krglok.realms.command.CmdSettleHelp;
import net.krglok.realms.command.CmdSettleInfo;
import net.krglok.realms.command.CmdSettleList;
import net.krglok.realms.command.CmdSettleSell;
import net.krglok.realms.command.CmdSettleSetItem;
import net.krglok.realms.command.CmdSettleTrader;
import net.krglok.realms.command.CmdSettleWarehouse;
import net.krglok.realms.command.RealmsCommand;

import org.junit.Test;

public class CmdRealmsHelpTest
{

	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
				new CmdRealmNone(),
				new CmdRealmsVersion(),
				new CmdRealmsHelp(),
				new CmdRealmsPricelistInfo(),
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
