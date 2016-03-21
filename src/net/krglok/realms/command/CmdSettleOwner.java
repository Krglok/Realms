package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleOwner extends RealmsCommand
{
	private int ownerId;
	private int settleId ;

	public CmdSettleOwner()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/settle OWNER [ID] [ownerId]",
				"Set the owner to the settlement ",
				"Set the playername as owner to every building ",
		    	"expect the HOME, HOUSE, MANSION ",
		    	"  "
		};
		requiredArgs = 2;
		this.ownerId = -1;
		this.settleId = 0;
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
			settleId = value;
			break;
		case 1:
			ownerId = value;
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
		return new String[] {int.class.getName(), int.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player;
		Owner owner = null;
		owner = plugin.getData().getOwners().getOwner(ownerId);
		if (owner == null)
		{
			msg.add("No owner is given !");
			plugin.getMessageData().printPage(sender, msg, 1);
			return;
		}
		String playername = owner.getPlayerName();

		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
		if ( settle == null)
		{
			msg.add("Settlement not found !");
		}
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(settle.getName());
		if (sRegion == null)
		{
			msg.add("Superregion not found !");
		}
		settle.setOwner(owner);
		if (owner.isNPC() == false)
		{
			List<String> members = new ArrayList<String>();
			members.add(playername);
			// check for playername as member of superregion
			// set it again to delete the member entry
			if (sRegion.getMembers().containsKey(playername))
			{
				plugin.stronghold.getRegionManager().setMember(sRegion, settle.getName(), members);
			}
			// set owner of superregion in addition to existing
			if (sRegion.getOwners().contains(playername) == false)
			{
				sRegion.addOwner(playername);
			}
//					sRegion.addMember(playername, perms );
			for (Building building : settle.getBuildingList().values())
			{
				if ((building.getBuildingType() != BuildPlanType.HOME) 
						&& (building.getBuildingType() != BuildPlanType.HOUSE)
						&& (building.getBuildingType() != BuildPlanType.MANSION)
						)
				{
					Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
					if (region.getOwners().contains(playername) == false)
					{
						region.addOwner(playername);
					}
					System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+playername );
					building.setOwnerId(owner.getId());
					plugin.getData().writeBuilding(building);
				}
			}
		}
		plugin.getData().writeSettlement(settle);
		msg.add("Owner added to "+settle.getName());
		msg.add(ChatColor.GOLD+"Player "+playername+" now owner of the settlement");
		msg.add(ChatColor.GOLD+"Player "+playername+" can use every building in the Settlement");
		msg.add(ChatColor.GOLD+"The settlers  now look at you. Dont disappoint them !");
		msg.add(ChatColor.YELLOW+"Notice you are not a member of the houses !");
		msg.add("");
		if (plugin.getServer().getPlayer(playername) != null)
		{
			for (String s : msg)
			{
				plugin.getServer().getPlayer(playername).sendMessage(s);
			}
		} 
//		for (String s : msg)
//		{
//			sender.sendMessage(s);
//		}

		plugin.getMessageData().printPage(sender, msg, 1);
		this.ownerId = -1;
		this.settleId = 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().getSettlement(settleId) == null)
		{
			errorMsg.add("Settlement not found ! ");
			return false;
			
		}
		if (plugin.getData().getOwners().getOwner(ownerId) == null)
		{
				errorMsg.add("Not a regulat Owner ! "+ownerId);
				return false;
		}
		if (isOpOrAdmin(sender) == false)
		{
			if (isSettleOwner(plugin, sender, settleId)== false)
			{
				errorMsg.add("You are not the owner ! ");
				return false;
				
			}
		} 
		return true;
			
	}

}
