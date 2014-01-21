package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.krglok.realms.core.ConfigBasis;

public class CmdRealmNone extends RealmsCommand
{
	

	public CmdRealmNone()
	{
		super(RealmsCommandType.REALMS ,  RealmsSubCommandType.NONE);
		description = new String[] {
				"Subcommand not found , use /realms help ",
		    	"/realms  only for ops to control plugin & model",
		    	"/owner only for ops for managing owners",
		    	"/kingdom  player command for kingdom management",
		    	"/settle player command for settlement management",
		    	"/stronghold plyer command for Stronghold Data"
		};
		requiredArgs = 0;
	}

	@Override
	public String[] getParaTypes()
	{
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+" ");
		msg.addAll(getDescriptionString());
		plugin.getMessageData().printPage(sender, msg, 1);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

	@Override
	public void setPara(int index, String value)
	{
		
	}

	@Override
	public void setPara(int index, int value)
	{
		
	}

	@Override
	public void setPara(int index, boolean value)
	{
		
	}

	@Override
	public void setPara(int index, double value)
	{
		
	}

}
