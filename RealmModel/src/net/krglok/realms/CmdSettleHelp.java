package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleHelp extends RealmsCommand
{

	private int page;
	
	public CmdSettleHelp( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.HELP);
		description = new String[] {
				ChatColor.YELLOW+"/settle HELP [page] {WORD} ",
		    	" "
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
    	msg = makeHelpPage(plugin.getCommandRealms().cmdList, msg, this.helpPage);

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