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
				"command not found this will help you ",
		    	"/settle CHECK [SuperRegionName] ",
		    	"/setlle CREATE [SuperRegionName]  ",
		    	"/settle  "
			};
			requiredArgs = 0;
			page = 1;
	}

	@Override
	public void setPara(int index, String value)
	{

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
		return new String[] { int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+ConfigBasis.setStrleft(ConfigBasis.LINE,30));
    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+" ");
		msg.addAll(getDescriptionString());
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
