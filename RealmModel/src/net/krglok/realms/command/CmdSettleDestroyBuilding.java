package net.krglok.realms.command;

import net.krglok.realms.Realms;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleDestroyBuilding extends RealmsCommand
{
	private int settleId;
	private int regionId;

	public CmdSettleDestroyBuilding( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.DESTROY);
		description = new String[] {
				ChatColor.YELLOW+"/settle DESTROY [SettleID] [RegionID] ",
				"Destroy a building and region off a Settlement  ",
				"You must NOT be the Owner of the region ! ",
				"You must  be the Owner of the Settlement ! ",
				"The region must exist",
		    	"  ",
		};
		this.regionId = 0;
		this.settleId = 0;
		requiredArgs = 2;
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
		sender.sendMessage(ChatColor.RED+"Not implemented yet !!");
		return false;
	}

}
