package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.command.*;

import org.junit.Test;

public class CmdRealmsHelpTest
{

	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
				new CmdRealmNone(),
				new CmdRealmsActivate(),
				new CmdRealmsBuildingList(),
				new CmdRealmsBuilding(),
				new CmdRealmsBook(),
				new CmdRealmsBookRead(),
				new CmdRealmsBookList(),
				new CmdRealmsCheck(),
				new CmdRealmsDeactivate(),
				new CmdRealmsGetItem(),
				new CmdRealmsHelp(),
				new CmdRealmsPricelistInfo(),
				new CmdRealmsMap(),
				new CmdRealmsMove(),
				new CmdRealmsProduce(),
				new CmdRealmsPrice(),
				new CmdRealmsPlayer(),
				new CmdRealmsRecipeList(),
				new CmdRealmsSettler(),
				new CmdRealmsSetItem(),
				new CmdRealmsSettlement(),
				new CmdRealmsSign(),
				new CmdRealmsTax(),
				new CmdRealmsTech(),
				new CmdWallSign(),
				new CmdRealmsVersion(),
				new CmdRealmNone(),
				new CmdSettleAddBuilding(),
				new CmdSettleAddMember(),
				new CmdSettleAssume(),
				new CmdSettleBank(),
				new CmdSettleBiome(),
				new CmdSettleBuild(),
				new CmdSettleBuilding(),
				new CmdSettleBuildingList(),
				new CmdSettleBuy(),
				new CmdSettleCheck(),
				new CmdSettleCreate(),
				new CmdSettleCredit(),
				new CmdSettleDelete(),
				new CmdSettleDestroyBuilding(),
				new CmdSettleEvolve(),
				new CmdSettleGetItem(),
				new CmdSettleHelp(),
				new CmdSettleInfo(),
				new CmdSettleJoin(),
				new CmdSettleList(),
				new CmdSettleMarket(),
				new CmdSettleNoSell(),
				new CmdSettleOwner(),
				new CmdSettleProduction(),
				new CmdSettleReputation(),
				new CmdSettleRequired(),
				new CmdSettleRoute(),
				new CmdSettleRouteList(),
				new CmdSettleSell(),
				new CmdSettleSetItem(),
				new CmdSettleSettler(),
				new CmdSettleTrader(),
				new CmdSettleTrain(),
				new CmdSettleWarehouse(),
				new CmdSettleWorkshop(),
				new CmdColonistCreate(),
				new CmdColonyBuild(),
				new CmdColonistList(),
				new CmdColonyHelp(),
				new CmdColonyWarehouse(),
				new CmdColonistMove(),
				new CmdOwnerCreate(),
				new CmdOwnerHelp(),
				new CmdOwnerInfo(),
				new CmdOwnerList(),
				new CmdOwnerSet(),
				new CmdOwnerSettlement(),
				new CmdFeudalBank(),
				new CmdFeudalFollow(),
				new CmdFeudalHelp(),
				new CmdFeudalInfo(),
				new CmdFeudalList(),
				new CmdFeudalCreate(),
				new CmdFeudalOwner(),
				new CmdKingdomList(),
				new CmdKingdomCreate(),
				new CmdKingdomGive(),
				new CmdKingdomHelp(),
				new CmdKingdomInfo(),
				new CmdKingdomJoin(),
				new CmdKingdomMember(),
				new CmdKingdomOwner(),
				new CmdKingdomRequest(),
				new CmdKingdomRelease(),
				new CmdKingdomStructure(),
				new CmdRegimentCreate(),
				new CmdRegimentHome(),
				new CmdRegimentList(),
				new CmdRegimentMove(),
				new CmdRegimentWarehouse(),
				new CmdRegimentRaid(),
				new CmdRegimentInfo(),
				new CmdRegimentHelp(),
//				new CmdRealmsTest()
			
		};
		return commandList;
	}

	
	@Test
	public void testHelpCommand()
	{
		ArrayList<String> msg = new ArrayList<String>();

		CmdOwnerHelp cmdOHelp = new CmdOwnerHelp();
		msg = cmdOHelp.makeHelpPage(makeCommandList(),  msg, "" );
		
		CmdRegimentHelp cmdRHelp = new CmdRegimentHelp();
		msg = cmdRHelp.makeHelpPage(makeCommandList(),  msg, "" );

		CmdFeudalHelp cmdFHelp = new CmdFeudalHelp();
		msg = cmdFHelp.makeHelpPage(makeCommandList(),  msg, "" );

		CmdKingdomHelp cmdKHelp = new CmdKingdomHelp();
		msg = cmdKHelp.makeHelpPage(makeCommandList(),  msg, "" );

//		CmdRealmsHelp cmdHelp = new CmdRealmsHelp();
//		msg = cmdHelp.makeHelpPage(makeCommandList(),  msg, "" );
//		
//		CmdSettleHelp cmdSetleHelp = new CmdSettleHelp();
//		msg = cmdSetleHelp.makeHelpPage(makeCommandList(),  msg, "" );
//
//		CmdColonyHelp cmdColonyHelp = new CmdColonyHelp();
//		msg = cmdColonyHelp.makeHelpPage(makeCommandList(),  msg, "" );
		
		for (String s : msg)
		{
			System.out.println(s);
		}
		
		fail("Not yet implemented");
	}

}
