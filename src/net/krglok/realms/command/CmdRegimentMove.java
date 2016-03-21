package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.PositionFace;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRegimentMove extends RealmsCommand
{
	private int regimentId;
	private int settleId;
	private String faceName;
//	LocationData position;

	public CmdRegimentMove()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.MOVE);
		description = new String[] 
				{
				ChatColor.YELLOW+"/regiment MOVE [ID] [settleID] [Face] ",
				"Move a Regiment with <ID> to the settlement position",
		    	"Face = NORTH,EAST,SOUTH,WEST ",
		    	"the regiment positioned ",
		    	"NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST "
				};
		requiredArgs = 2;
//		position = new LocationData("", 0.0, 0.0, 0.0);
		this.regimentId = 0;
		this.settleId = 0;
		this.faceName = "";
	}


	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 2 :
			faceName = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			regimentId = value;
			break;
		case 1 :
			settleId = value;
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
		return new String[] {int.class.getName(), int.class.getName(), String.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		Regiment regiment = plugin.getRealmModel().getRegiments().get(regimentId);
		// das Target wird überschrieben
		PositionFace face = PositionFace.valueOf(faceName);
		CampPosition campPos = plugin.getData().getCampList().getCampPosition(settleId, face);
		
		regiment.setTarget(LocationData.copyLocation(campPos.getPosition()));
		regiment.setSettleId(settleId);
		LocationData center = LocationData.copyLocation(campPos.getPosition());
		// set new position
		BuildPlanMap buildPlan = plugin.getRealmModel().getData().readTMXBuildPlan(BuildPlanType.FORT, 7, 0);
		regiment.setBuildPlan(buildPlan);
		regiment.startMove();

		msg.add("[Realm] Regiment move to "+(int)center.getX()+":"+(int)center.getY()+":"+(int)center.getZ());
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);

		regimentId = 0;
		msg.add("The Regiment moved "+regimentId);
    	msg.add("Build new FORT  ");
    	plugin.getMessageData().printPage(sender, msg, 1);
		this.regimentId = 0;
		this.settleId = 0;
		this.faceName = "";

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
		Location position = plugin.makeLocation(regiment.getHomePosition());
		ArrayList<Region> foundRegion = plugin.stronghold.getRegionManager().getContainingRegions(position, 15);
		if (foundRegion.size() > 0)
		{
			errorMsg.add(ChatColor.RED+"You cannot move a regiment into other regions !");
			errorMsg.add("Take a different place !");
			return false;
		}
		if (plugin.getData().getSettlements().getSettlement(settleId) == null)
		{
			errorMsg.add(ChatColor.RED+"Settlement not found !");
			errorMsg.add("See  /settle list ");
			return false;
		}
		if (plugin.getData().getSettlements().getSettlement(settleId).getPosition().getWorld().equalsIgnoreCase(regiment.getPosition().getWorld()) == false)
		{
			errorMsg.add(ChatColor.RED+"Regiments can not go to other worlds "+plugin.getData().getSettlements().getSettlement(settleId).getPosition().getWorld()+":"+regiment.getPosition().getWorld());
			errorMsg.add("See  /settle list ");
			return false;
		}
		if (PositionFace.contain(faceName)== false)
		{
			errorMsg.add(ChatColor.RED+"Wrong face !");
			errorMsg.add("Try :");
			for (CampPosition campPos : plugin.getData().getCampList().getSubList(settleId).values())
			{
				errorMsg.add("Camp "+settleId+" : "+campPos.getFace().name()+" : "+campPos.isValid());
			}
			return false;
		}
		PositionFace face = PositionFace.valueOf(faceName);
		if (plugin.getData().getCampList().getCampPosition(settleId, face) == null)
		{
			errorMsg.add(ChatColor.RED+"That is not a valid camp position !");
			errorMsg.add("Try other face ");
			return false;
		}
		
		return true;
	}

}
