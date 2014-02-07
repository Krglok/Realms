package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.model.McmdColonyBuild;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdColonyBuild extends RealmsCommand
{
	private int colonyId;
	
	

	public CmdColonyBuild( )
	{
		super(RealmsCommandType.COLONIST, RealmsSubCommandType.BUILD);
		description = new String[] {
				ChatColor.YELLOW+"/colonist BUILD [colonyID] ",
				"Startup Building of Hamlet ",
		    	"  "
		};
		requiredArgs = 1;
		colonyId = 0;
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
		case 0 :
				colonyId = value;
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
		return new String[] {int.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().OnCommand(new McmdColonyBuild(plugin.getRealmModel(), colonyId));
    	msg.add("Start Colony Build for "+colonyId);
    	msg.add(" ");
    	plugin.getMessageData().printPage(sender, msg, 1);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		for (Colony colony : plugin.getRealmModel().getColonys().values())
		{
			if (colony.getId() == colonyId)
			{
				return true;
			}
		}
		errorMsg.add("Colony ID not found !");
		errorMsg.add(" ");
		return false;
	}

}
