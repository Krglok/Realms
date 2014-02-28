package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmNone extends RealmsCommand
{
	

	public CmdRealmNone()
	{
		super(RealmsCommandType.REALMS ,  RealmsSubCommandType.NONE);
		description = new String[] {
				ChatColor.RED+"command not found , use one of the following ",
//				ChatColor.YELLOW+"/realms [HELP] [page] {String} ",
				ChatColor.GREEN+"/realms [HELP] only for ops to control plugin & model",
				ChatColor.GREEN+"/owner [HELP] only for ops to managing owners",
				ChatColor.GREEN+"/kingdom [HELP]  player command for kingdom management",
				ChatColor.GREEN+"/settle [HELP] player command for settlement management",
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
    	msg.add(ChatColor.YELLOW+"Status: "+"["+plugin.getRealmModel().getModelStatus()+"]");
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
