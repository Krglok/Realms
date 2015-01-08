package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
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
	private String playerName;
	
	public CmdFeudalOwner()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/feudal OWNER [lehenId] [playerName] ",
		    	"Set playerName as owner ",
		    	"The Lehen get the kingdom of the creator ",
		    	"Only the Owner and the King can set the owner ",
		    	" "
			};
			requiredArgs = 2;
			lehenId = 0;
			page = 1;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			playerName = value;
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
			lehenId = value;
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
		return new String[] {int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);

		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
		lehen.setOwner(owner);
		lehen.setKingdomId(owner.getKingdomId());
		plugin.getData().writeLehen(lehen);
		
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
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);
		if (owner == null)
		{
			errorMsg.add("New Owner  not exist, wrong name");
			return false;
		}

		return true;
	}

}