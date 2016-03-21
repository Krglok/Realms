package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.util.Set;

import net.krglok.realms.Common.CommandParser;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
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

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.junit.Test;

public class CommandParserTest
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
				new CmdSettleBuild()
			
		};
		return commandList;
	}
	
	
	
	@Test
	public void testGetRealmsCommand()
	{
		class TestSender implements CommandSender
		{

			@Override
			public PermissionAttachment addAttachment(Plugin arg0)
			{
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1)
			{
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1,
					boolean arg2)
			{
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1,
					boolean arg2, int arg3)
			{
				return null;
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions()
			{
				return null;
			}

			@Override
			public boolean hasPermission(String arg0)
			{
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0)
			{
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0)
			{
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0)
			{
				return true;
			}

			@Override
			public void recalculatePermissions()
			{
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0)
			{
				
			}

			@Override
			public boolean isOp()
			{
				return true;
			}

			@Override
			public void setOp(boolean arg0)
			{
				
			}

			@Override
			public String getName()
			{
				return "Test User";
			}

			@Override
			public Server getServer()
			{
				return null;
			}

			@Override
			public void sendMessage(String arg0)
			{
				System.out.println(arg0);
			}

			@Override
			public void sendMessage(String[] arg0)
			{
				for (int i = 0; i < arg0.length; i++)
				{
					System.out.println(arg0[i]);
				}
			}
			
		}
		
		
		RealmsCommand[] cmdList = makeCommandList();
		CommandParser parser = new CommandParser(cmdList);
		String[] args = new String[] {
			"help",	
			"1"	,
			""
		};
		String s = "INPUT: realms";
		for (int i = 0; i < args.length; i++)
		{
			s = s + " "+args[i];
		}
		System.out.println(s);
		System.out.println("OUTPUT:");
		RealmsCommand cmd = parser.getRealmsCommand(RealmsCommandType.REALMS, args);
		CommandSender sender = new TestSender();
		if (cmd != null)
		{
//			System.out.println("Command found "+cmd.command()+" "+cmd.subCommand());
			String [] msg = cmd.getDescription();
			for (int i = 0; i < msg.length; i++)
			{
				System.out.println(msg[i]);
			}
			if (cmd.canExecute(null, sender))
			{
				System.out.println("");
				System.out.println("Command will be Executed !");
			}
		}else
		{
			System.out.println("NO Command found ");
		}
		
		fail("Not yet implemented");
	}

}
