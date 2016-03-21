package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsPlayer extends RealmsCommand
{
	private int page;
	
	public CmdRealmsPlayer()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/realms OWNER [page]  ",
		    	"List the registered Owners  and Players ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
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
		case 0:
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
		msg.add("ID | Player      |  [ "+plugin.getData().getOwners().size()+" ]");
		for (Owner owner : plugin.getData().getOwners().values())
		{
			if (owner.isNPC()==false)
			{
	    		msg.add(owner.getId()
	    				+" | "+owner.getPlayerName()
	    				+" | "+owner.firstLogin
	    				+" | "+owner.lastLogOff
	    				);
			}
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// always true, everyone can use this command
		return true;
	}

}
