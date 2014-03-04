package net.krglok.realms;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleAddMember extends RealmsCommand
{
	private String playername;
	private int settleId ;

	public CmdSettleAddMember( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.MEMBER);
		description = new String[] {
				ChatColor.YELLOW+"/settle SELL [ID] [item] [amount] [prive] [days]",
				"Set an buy order for te amount of item to the trader ",
		    	"of Settlement <ID>, the decline after day(s) ",
		    	"  "
		};
		requiredArgs = 2;
		this.playername = "";
		this.settleId = 0;
	}

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
		if (sender instanceof Player)
		{
			player = (Player) sender;
			if (playername == "")
			{
				playername = player.getPlayerListName();
			}
		}
		if (playername != "")
		{
			Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
			if ( settle != null)
			{
				SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(settle.getName());
				if (sRegion != null)
				{
					List<String> perms = null;
					List<String> members = new ArrayList<String>();
					members.add(playername);
					plugin.stronghold.getRegionManager().setMember(sRegion, playername, members);
//					sRegion.addMember(playername, perms );
					for (Building building : settle.getBuildingList().getBuildingList().values())
					{
//						plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion()).addMember(playername);
						plugin.stronghold.getRegionManager().setMember(plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion()), playername);
					}
					msg.add("Member added to "+settle.getName());
					msg.add("Player "+playername+" now can use every Building in the Area");
					msg.add("Player "+playername+" now can rearrange the settlement area");
					msg.add("They now look at you. Dont disappoint them !");
					msg.add("");
					for (String s : msg)
					{
						plugin.getServer().getPlayer(playername).sendMessage(s);
					}
				} else
				{
					msg.add("Superregion not found !");
					msg.add("");
				}
			} else
			{
				msg.add("Settlement not found !");
				msg.add("");
			}
		} else
		{
			msg.add("No playername is given !");
			msg.add("");
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
					errorMsg.add(" ");
					return false;
					
				}
			} else
			{
				return true;
			}
			
		} else
		{
			errorMsg.add("You are not the owner ! ");
			errorMsg.add(" ");
			return false;
			
		}
			
		return false;
	}

}
