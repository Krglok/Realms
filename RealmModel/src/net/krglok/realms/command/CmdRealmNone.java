package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;

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
				ChatColor.GREEN+"/settle [HELP] player command for settlement management",
				ChatColor.GREEN+"/colonist [HELP] op/player for colonist management",
				ChatColor.GREEN+"/regiment [HELP] op/player for regiment management",
				ChatColor.GREEN+"/owner [HELP] only for ops to managing owners",
				ChatColor.GREEN+"/kingdom [HELP]  player command for kingdom management",
				ChatColor.GREEN+"/feudal [HELP]  player command for Lehen management",
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
