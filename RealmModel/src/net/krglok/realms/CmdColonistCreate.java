package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.McmdColonistCreate;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author oduda
 *
 */
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
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		String world = player.getLocation().getWorld().getName();
		position.setWorld(world); 
		LocationData center = new LocationData(world, position.getX(), position.getY(), position.getZ());
		plugin.getRealmModel().OnCommand(new McmdColonistCreate(plugin.getRealmModel(), name, center, owner));
		World worldMap = player.getLocation().getWorld();
		
		String[] signText = new String[] {"COLONIST", name, owner, "[NEW]" };
		plugin.setSign(worldMap, new ItemLocation(Material.SIGN_POST,center), signText);
		msg.add("[Realm] Colony created at "+(int)center.getX()+":"+(int)center.getY()+":"+(int)center.getZ());
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);
		position.setX(0);
		position.setY(0);
		position.setZ(0);
		name = "";
		owner = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp())
		{
			if (sender instanceof Player)
			{
				if (name.equals(""))
				{
					errorMsg.add("You must give a name for the new settlement !");
					errorMsg.add(" ");
					return false;
				}
				return true;
			}
			errorMsg.add("The World can NOT be set correct !");
			errorMsg.add("The command can NOT be send from console");
		} else
		{
			errorMsg.add("You are not a OP");
		}
		return false;
	}

}
