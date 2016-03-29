package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdOwnerHelp extends aRealmsCommand
{
	private int page;

	public CmdOwnerHelp( )
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.HELP);
		description = new String[] {
				ChatColor.YELLOW+"/owner HELP [page] {WORD} ",
				ChatColor.GREEN+"  ",
			};
			requiredArgs = 0;
			page = 1;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1 :
				this.helpPage = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
				page = value;
			break;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg = makeHelpPage(plugin.getCommandRealms().getCmdList(), msg, this.helpPage);
		plugin.getMessageData().printPage(sender, msg, page);
		helpPage = "";
		page = 1;
		msg.clear();

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
