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
    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getDescription().getVersion()+" ");
    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+"["+plugin.getRealmModel().getModelStatus()+"]");
    	msg.add(ChatColor.YELLOW+"Settlements: "+ChatColor.GREEN+"["+plugin.getRealmModel().getSettlements().count()+"]");
    	msg.add(ChatColor.YELLOW+"Colonist: "+ChatColor.GREEN+"["+plugin.getRealmModel().getColonys().size()+"]");
    	if (plugin.getRealmModel().getSettlements().getSettlement(0) != null)
    	{
    	msg.add(ChatColor.YELLOW+"Age of Realms: "+ChatColor.GREEN+"["+plugin.getRealmModel().getSettlements().getSettlement(0).getAge()+"] Days");
    	}
		msg.add(" ");
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
