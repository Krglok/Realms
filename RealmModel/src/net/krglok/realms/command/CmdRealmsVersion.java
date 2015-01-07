package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;

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
    	msg.add(ChatColor.YELLOW+"Buildings: "+ChatColor.GREEN+"["+plugin.getData().getBuildings().size()+"]");
    	msg.add(ChatColor.YELLOW+"Settlements: "+ChatColor.GREEN+"["+plugin.getRealmModel().getSettlements().count()+"]");
    	msg.add(ChatColor.YELLOW+"Regiments: "+ChatColor.GREEN+"["+plugin.getData().getRegiments().size()+"]");
    	msg.add(ChatColor.YELLOW+"Kingdoms: "+ChatColor.GREEN+"["+plugin.getData().getKingdoms().size()+"]");
    	msg.add(ChatColor.YELLOW+"Lehen: "+ChatColor.GREEN+"["+plugin.getData().getLehen().size()+"]");
    	msg.add(ChatColor.YELLOW+"Colonist: "+ChatColor.GREEN+"["+plugin.getRealmModel().getColonys().size()+"]");
    	
    	if (plugin.getRealmModel().getSettlements().getSettlement(1) != null)
    	{
    		long maxAge = plugin.getRealmModel().getSettlements().getSettlement(1).getAge();
    		long years = maxAge / 30 /12;
    		
    		msg.add(ChatColor.YELLOW+"Age of Realms: "+ChatColor.GREEN+"["+maxAge+"] Days or "+years+" Years");
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
