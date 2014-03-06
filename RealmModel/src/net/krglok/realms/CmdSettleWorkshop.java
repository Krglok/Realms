package net.krglok.realms;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleWorkshop extends RealmsCommand
{
	private int settleID;
	private Integer buildingId ;
	private int slot;
	private String itemRef;

	public CmdSettleWorkshop()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.WORKSHOP);
		description = new String[] {
				ChatColor.YELLOW+"/settle WORKSHOP [SettleID] [Building] [slot] [item]",
		    	"Set the item in production slot (0..4)  ",
		    	" of the Building Id of settlement "
			};
			requiredArgs = 4;
			this.settleID = 0;
			this.buildingId = 1;  
			this.slot = 0;
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
