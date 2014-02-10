package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.model.McmdColonyBuild;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdColonyBuild extends RealmsCommand
{
	private int colonyId;
	private boolean isCleanUp;
	

	public CmdColonyBuild( )
	{
		super(RealmsCommandType.COLONIST, RealmsSubCommandType.BUILD);
		description = new String[] {
				ChatColor.YELLOW+"/colonist BUILD [colonyID] [clean true/false]",
				"Startup Building of Hamlet ",
		    	"clean true clean up build area with R=21  ",
		    	"clean false no clean start with colony house  ",
		};
		requiredArgs = 2;
		colonyId = 0;
		isCleanUp = true;
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
		switch (index)
		{
		case 1 :
			isCleanUp = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName(), boolean.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().OnCommand(new McmdColonyBuild(plugin.getRealmModel(), colonyId,isCleanUp));
    	msg.add("Start Colony Build for "+colonyId);
    	msg.add("Clean Up  "+isCleanUp);
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
