package net.krglok.realms.command;

import net.krglok.realms.Realms;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleAquire extends RealmsCommand
{

	int settleID;
	int page ;

	public CmdSettleAquire( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.AQUIRE);
		description = new String[] {
				ChatColor.YELLOW+"/settle AQUIRE [SettleID] [page] ",
				"Show Infomation about the Settlement ",
		    	"and show the analysis report ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleID = 0;
		this.page = 1;  //default value
	}

	@Override
	public void setPara(int index, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, boolean value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, double value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getParaTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
