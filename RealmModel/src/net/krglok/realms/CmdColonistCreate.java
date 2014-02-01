package net.krglok.realms;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.McmdColonistCreate;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdColonistCreate extends RealmsCommand
{
	private String name;
	LocationData position;
	private String owner;

	public CmdColonistCreate( )
	{
		super(RealmsCommandType.COLONIST, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/colonist CREATE [Name] [X] [Y] [Z] ",
				"Create a Colonist with <Name> ",
				"The colonist is not linked to a settlement",
		    	"BuildUp a Hamlet with [Name], you are the owner ",
		    	"  "
		};
		requiredArgs = 4;
		position = new LocationData("", 0.0, 0.0, 0.0);
		this.name = "";
		this.owner = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				name = value;
			break;
		case 4 :
			owner = value;
		break;
		default:
			break;
		}

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
		switch (index)
		{
		case 1 :
			position.setX(value);
			break;
		case 2 :
			position.setY(value);
		break;
		case 3 :
			position.setZ(value);
		break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName(), double.class.getName(), double.class.getName(), double.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		plugin.getRealmModel().OnCommand(new McmdColonistCreate(plugin.getRealmModel(), name, position, owner));
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp())
		{
			return true;
		}
		return false;
	}

}
