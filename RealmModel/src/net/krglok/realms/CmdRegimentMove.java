package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRegimentMove extends RealmsCommand
{
	private int regimentId;
	LocationData position;

	public CmdRegimentMove()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.MOVE);
		description = new String[] {
				ChatColor.YELLOW+"/regiment MOVE [ID] [X] [Y] [Z] ",
				"Move a Colonist with <ID> to the position ",
		    	"positioning a sign at the position ",
		    	" You cannot change the World !!! "
		};
		requiredArgs = 4;
		position = new LocationData("", 0.0, 0.0, 0.0);
		this.regimentId = 0;
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
			regimentId = value;
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
		return new String[] {int.class.getName(),  double.class.getName(), double.class.getName(), double.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		World worldMap = player.getLocation().getWorld();
		Regiment regiment = plugin.getRealmModel().getRegiments().get(regimentId);
		LocationData center = regiment.getTarget();
		// set new position
		center.setWorld(worldMap.getName());
		center.setX(position.getX()-1);
		center.setY(position.getY());
		center.setZ(position.getZ());
		regiment.startMove();

		String[] signText = new String[] {"REGIMENT", regiment.getName(), regiment.getOwner(), "[MOVED]" };
		plugin.setSign(worldMap, new ItemLocation(Material.SIGN_POST,center), signText);
		msg.add("[Realm] Regiment move at "+(int)center.getX()+":"+(int)center.getY()+":"+(int)center.getZ());
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);

		position.setX(0);
		position.setY(0);
		position.setZ(0);
		regimentId = 0;
		msg.add("The Colony moved "+regimentId);
    	msg.add("Set new Sign  ");
    	plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if ((sender instanceof Player) == false)
		{
			errorMsg.add("The World can NOT be set correct !");
			errorMsg.add("The command can NOT be send from console");
			return false;
		}
		Regiment regiment = plugin.getRealmModel().getRegiments().get(regimentId);
		if (regiment == null)
		{
			errorMsg.add("Colony ID not found !");
			errorMsg.add(" ");
			return false;
		}
		if (sender.isOp())
		{
			return true;
		} else
		{
			if (sender.getName().equalsIgnoreCase(regiment.getOwner()))
			{
				return true;
			}
		}
		errorMsg.add("You are not the Owner");
		return false;
	}

}
