package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsVersion extends RealmsCommand implements iRealmsCommand
{

	public CmdRealmsVersion()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.VERSION);
		description = new String[] {
			ChatColor.YELLOW+"/realms VERSION ",
			"Show the plugin and model Version",		
		" "
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
    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+"["+plugin.getRealmModel().getModelStatus()+"]");
	}



	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}



	@Override
	public void setPara(int index, String value)
	{
		return;
	}



	@Override
	public void setPara(int index, int value)
	{
		return;
	}



	@Override
	public void setPara(int index, boolean value)
	{
		return;
	}



	@Override
	public void setPara(int index, double value)
	{
		return;
	}


	
}
