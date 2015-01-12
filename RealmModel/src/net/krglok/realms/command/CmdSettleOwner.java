package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleOwner extends RealmsCommand
{
	public CmdSettleOwner()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/settle OWNER [ID] [playername]",
				"Set the player as owner to settlement ",
				"Set the playername as owner to every building ",
		    	"expect the HOME, HOUSE, MANSION ",
		    	"  "
		};
		requiredArgs = 2;
		this.playername = "";
		this.settleId = 0;
	}

	private String playername;
	private int settleId ;

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			playername = value;
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
		case 0:
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
		return new String[] {int.class.getName()
				, String.class.getName()
				};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player;
		Owner owner = null;
		if (sender instanceof Player)
		{
			player = (Player) sender;
			if (playername == "")
			{
				playername = player.getPlayerListName();
			}
		}
		owner = plugin.getData().getOwners().getOwnerName(playername);
		if (owner == null)
		{
			msg.add("No playername is given !");
		}
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
		settle.setOwner(owner);
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
		for (String s : msg)
		{
			sender.sendMessage(s);
		}

		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().containsID(settleId))
		{
			if (isOpOrAdmin(sender) == false)
			{
				if (isSettleOwner(plugin, sender, settleId)== false)
				{
					errorMsg.add("You are not the owner ! ");
					return false;
					
				}
			} 
			if (plugin.getData().getOwners().getOwnerName(playername) == null)
			{
					errorMsg.add("Not a regulat Owner ! ");
					return false;
			}
			return true;
			
		} else
		{
			errorMsg.add("Settlement not found ! ");
			return false;
			
		}
	}

}
