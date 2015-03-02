package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRegimentCreate extends RealmsCommand
{
	private String name;
	private LocationData position;
	private String ownerName;


	public CmdRegimentCreate()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/regiment CREATE [Name] [X] [Y] [Z] ",
				"Create a Regiment with <Name> ",
				"The regiment is a Raider ",
		    	"The position is 0 0 0 , and the ",
		    	"regiment is hidden  ",
		    	"Move it to the rigth position  "
		};
		requiredArgs = 4;
		position = new LocationData("", 0.0, 0.0, 0.0);
		this.name = "";
		this.ownerName = "";
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
		return new String[] {String.class.getName(), double.class.getName(), double.class.getName(), double.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		String world = player.getLocation().getWorld().getName();
		position.setWorld(world); 
		LocationData center = new LocationData(world, position.getX(), position.getY(), position.getZ());
		Regiment regiment = plugin.getRealmModel().getRegiments().createRegiment(RegimentType.PRIVATEER.name(),name, 0);
		regiment.setPosition(LocationData.copyLocation(center));
		regiment.setHomePosition(LocationData.copyLocation(center));
		regiment.setTarget(center);
		Owner owner = plugin.getData().getOwners().getOwner(0);
		regiment.setOwner(owner);
		regiment.getBarrack().setUnitList(plugin.getData().getNpcs().getSubListRegiment(regiment.getId()));
		if (regiment.getBarrack().getUnitList().size() == 0)
		{
			plugin.getData().makeRaiderUnits(regiment);
			regiment.getBarrack().setUnitList(plugin.getData().getNpcs().getSubListRegiment(regiment.getId()));
		}
		plugin.getData().writeRegiment(regiment);
		BuildPlanMap buildPlan = plugin.getRealmModel().getData().readTMXBuildPlan(BuildPlanType.FORT, 7, 0);
		regiment.setBuildPlan(buildPlan);
		regiment.startMove();
		msg.add(ChatColor.GREEN+"[Realm] Raider regiment created, build a FORT at given position ");
		msg.add(ChatColor.GREEN+"position : "+center.toString());
		msg.add(ChatColor.GREEN+"has 10 MILITIA as units  ");
		msg.add(ChatColor.YELLOW+"they will travel around and plunder the settlements ! ");
		msg.add(ChatColor.YELLOW+"they do it by their own, you cannot control them ! ");
		msg.add(ChatColor.YELLOW+"they are skilled raiders ! ");
		plugin.getMessageData().printPage(sender, msg, 1);
		name = "";
		ownerName = "";
		position.setWorld("");
		position.setX(0);
		position.setY(0);
		position.setZ(0);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add(ChatColor.RED+"You are not a OP");
			return false;
		}
		if ((sender instanceof Player) == false)
		{
			errorMsg.add(ChatColor.RED+"The World can NOT be set correct !");
			errorMsg.add("The command can NOT be send from console");
			return false;
		}
		if (name.equals(""))
		{
			errorMsg.add("You must give a name for the new settlement !");
			return false;
		}
		if (position.getX() < 10)
		{
			errorMsg.add(ChatColor.RED+"Regiment cannot create below level 10!");
			errorMsg.add("Take a different place !");
			return false;
		}
		Player player = (Player) sender;
		String world = player.getLocation().getWorld().getName();
		LocationData center = new LocationData(world, position.getX(), position.getY(), position.getZ());
		Location position = plugin.makeLocation(center);
		ArrayList<Region> foundRegion = plugin.stronghold.getRegionManager().getContainingRegions(position, 15);
		if (foundRegion.size() > 0)
		{
			errorMsg.add(ChatColor.RED+"You cannot create a regiment into other regions !");
			errorMsg.add("Take a different place !");
			return false;
		}
		
		ArrayList<SuperRegion> foundSuper = plugin.stronghold.getRegionManager().getContainingSuperRegions(position);
		if (foundSuper.size() > 0)
		{
			errorMsg.add(ChatColor.RED+"You cannot create a regiment into other superRegions !");
			errorMsg.add("Take a different place !");
			return false;
		}
		if ( plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			errorMsg.add(ChatColor.RED+"A superRegion of that name always exist !");
			errorMsg.add("Take a different name !");
			return false;
		}
		if (plugin.getData().getSettlements().size() < 5)
		{
			errorMsg.add(ChatColor.RED+"The raider cannot exist without 5 settlements minimum !");
			errorMsg.add("Build some more settlements  !");
			return false;
		}
		return true;
	}

}
