package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRegimentHome extends RealmsCommand
{
	private int regimentId;
//	LocationData position;

	public CmdRegimentHome()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.HOME);
		description = new String[] {
				ChatColor.YELLOW+"/regiment HOME [ID] ",
				"Move a Regiment with <ID> to the home position",
		    	"this is the positioning given during creating ",
		    	"the regiment, the position is hidden stored "
		};
		requiredArgs = 1;
//		position = new LocationData("", 0.0, 0.0, 0.0);
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
//		switch (index)
//		{
//		case 1 :
//			position.setX(value);
//			break;
//		case 2 :
//			position.setY(value);
//		break;
//		case 3 :
//			position.setZ(value);
//		break;
//		default:
//			break;
//		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		World worldMap = player.getLocation().getWorld();
		Regiment regiment = plugin.getRealmModel().getRegiments().get(regimentId);
		// das Target wird überschrieben
		regiment.setTarget(LocationData.copyLocation(regiment.getHomePosition()));
		regiment.setSettleId(0);
		LocationData center = regiment.getHomePosition();
		// set new position
		center.setWorld(worldMap.getName());
		center.setX(center.getX());
		BuildPlanMap buildPlan = plugin.getRealmModel().getData().readTMXBuildPlan(BuildPlanType.FORT, 7, 0);
		regiment.setBuildPlan(buildPlan);
		regiment.startMove();

//		String[] signText = new String[] {"REGIMENT", regiment.getName(), regiment.getOwner().getPlayerName(), "[MOVED]" };
//		plugin.setSign(worldMap, new ItemLocation(Material.SIGN_POST,center), signText);
		msg.add("[Realm] Regiment move to "+(int)center.getX()+":"+(int)center.getY()+":"+(int)center.getZ());
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);

//		position.setX(0);
//		position.setY(0);
//		position.setZ(0);
		regimentId = 0;
		msg.add("The Regiment moved "+regimentId);
    	msg.add("Build new FORT  ");
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
		if (sender.isOp() == false)
		{
			if (isRegimentOwner(plugin, sender, regimentId)== false)
			{
				errorMsg.add("You are not the Owner");
				return false;
			}
		}
		if (regiment.getHomePosition().getY() < 2.0)
		{
			errorMsg.add(ChatColor.RED+"The home position is near height 0 , this is not valid !");
			errorMsg.add(ChatColor.WHITE+"Shut down the the server and set a correct home position");
			errorMsg.add(ChatColor.WHITE+"in the regiment.yml or destroy the regiment and recreate");
			return false;
		}
		return true;
	}

}
