package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.McmdColonistCreate;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRegimentCreate extends RealmsCommand
{
	private String name;
	private LocationData position;
	private String owner;


	public CmdRegimentCreate()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/regiment CREATE [Name]  ",
				"Create a Regiment with <Name> ",
				"The regiment is a Raider ",
		    	"The position is 0 0 0 , and the ",
		    	"regiment is hidden  ",
		    	"Move it to the rogth position  "
		};
		requiredArgs = 1;
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
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getParaTypes()
	{
		// TODO Auto-generated method stub
		return new String[] {String.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		String world = player.getLocation().getWorld().getName();
		position.setWorld(world); 
		LocationData center = new LocationData(world, position.getX(), position.getY(), position.getZ());
		plugin.getRealmModel().getRegiments().createRegiment(RegimentType.PRIVATEER.name(),name, 0, plugin.getLogList());
		
		msg.add("[Realm] Regiment created ");
		msg.add("position 0 0 0  ");
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);
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
