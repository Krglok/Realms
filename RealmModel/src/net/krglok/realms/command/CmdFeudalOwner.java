package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFeudalOwner extends RealmsCommand
{
	int page;
	private int lehenId;
	private int ownerId;
	
	public CmdFeudalOwner()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/feudal OWNER [lehenId] [ownerId] ",
		    	"Set owner for lehen",
		    	"The Lehen get the kingdom of the creator ",
		    	"Only the Owner and the King can set the owner ",
		    	" "
			};
			requiredArgs = 2;
			lehenId = 0;
			ownerId = 0;
			page = 1;
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
			lehenId = value;
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
		return new String[] {int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
		if (owner == null)
		{
			return;
		}
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
		if (lehen == null)
		{
			return;
		}
		String playerName = owner.getPlayerName();
		lehen.setOwner(owner);
		lehen.setKingdomId(owner.getKingdomId());
		plugin.getData().writeLehen(lehen);
		owner.setNobleLevel(lehen.getNobleLevel());
		plugin.getData().writeOwner(owner);
		// check for playername as member of superregion
		// set it again to delete the member entry
		List<String> members = new ArrayList<String>();
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(lehen.getName());
		if (sRegion.getMembers().containsKey(playerName))
		{
			plugin.stronghold.getRegionManager().setMember(sRegion, lehen.getName(), members);
		}
		// set owner of superregion in addition to existing
		if (sRegion.getOwners().contains(playerName) == false)
		{
			sRegion.addOwner(playerName);
		}
		for (Building building : lehen.getBuildings().values())
		{
			if (building.getLehenId() == lehen.getId())
			{
				Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
				if (region.getOwners().contains(playerName) == false)
				{
					region.addOwner(playerName);
				}
				System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+playerName );
				building.setOwnerId(owner.getId());
				plugin.getData().writeBuilding(building);
			}
		}
	
		msg.add(ChatColor.YELLOW+playerName+" is now the new owner of "+lehen.getId()+":"+lehen.getName());
		plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		Owner me = plugin.getData().getOwners().getOwnerName(player.getName());
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);

		if (isOpOrAdmin(player) == false)
		{
			if (lehen != null)
			{
				if (me.getKingdomId() == lehen.getKingdomId())
				{
					errorMsg.add("Lehen and You are not the in the same Kingdom !");
					return false;
					
				}
				if (lehen.getOwner().getPlayerName().equalsIgnoreCase(me.getPlayerName())==false)
				{
					if (me.getNobleLevel() != NobleLevel.KING)
					{
						errorMsg.add("You are not the owner of the Lehen !");
						errorMsg.add("You are not the a King !");
						return false;
					}
				}
			}
		}
		if (lehen == null)
		{
			errorMsg.add("Lehen not exist, wrong id !");
			return false;
		}
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
		if (owner == null)
		{
			errorMsg.add("New Owner  not exist, wrong name");
			return false;
		}

		return true;
	}

}
