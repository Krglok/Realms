package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFeudalFollow extends RealmsCommand
{

	private int kingdomId;
	private int lehenId;
	private int lordId;
	private String playerName;
	
	public CmdFeudalFollow()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.FOLLOW);
		description = new String[] {
				ChatColor.YELLOW+"/feudal FOLLOW [yourId] [lordId] {playerName} ",
		    	"The owner change is loyalty to another lord ",
		    	"This can only be done in the same kingdom ",
		    	"all your liege follow you ",
		    	"only Admins can do this for others. ",
		    	" "
			};
			requiredArgs = 2;
			kingdomId = 0;
			lehenId = 0;
			lordId = 0;
			playerName = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 2:
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
		case 1:
			lordId = value;
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
		return new String [] {int.class.getName(), int.class.getName(), String.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Owner  owner = plugin.getData().getOwners().getOwnerName(playerName);
		Lehen parent = plugin.getData().getLehen().getLehen(lordId);
		Lehen lehen =  plugin.getData().getLehen().getLehen(lehenId);
		lehen.setParentId(parent.getId());
		plugin.getData().writeLehen(lehen);
		
		msg.add(playerName+" is now a liege  of lehen "+lehenId+" : "+parent.getNobleLevel());
		
		plugin.getMessageData().printPage(sender, msg, 1);

		kingdomId = 0;
		lehenId = 0;
		playerName = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		Owner owner;
		if (isOpOrAdmin(sender) == false)
		{
			owner = plugin.getData().getOwners().getOwnerName(player.getName());
			if (owner.getNobleLevel() != NobleLevel.KING)
			{
				errorMsg.add(ChatColor.RED+"Only the king can do that !");
				return false;
			}
		} else
		{
			if (playerName == "")
			{
				errorMsg.add(ChatColor.RED+"playerName must be given !");
				return false;
			}
			owner = plugin.getData().getOwners().getOwnerName(playerName);
		}
		kingdomId = owner.getKingdomId();
		
		if (plugin.getData().getLehen().getLehen(lehenId).getOwnerId() != owner.getPlayerName())
		{
			errorMsg.add(ChatColor.RED+"You are not the owner of the Lehen !");
			return false;
		}
		
		if (plugin.getData().getLehen().getLehen(lehenId) == null)
		{
			errorMsg.add(ChatColor.RED+"Lehen  not exist !");
			return false;
		}
		
		if (plugin.getData().getLehen().getLehen(lordId) == null)
		{
			errorMsg.add(ChatColor.RED+"The Lord Lehen does not exist !");
			return false;
		}

		if (plugin.getData().getLehen().getLehen(lordId).getKingdomId() != kingdomId)
		{
			errorMsg.add(ChatColor.RED+"Lehen is not in your kingdom !");
			return false;
		}
		
		if (plugin.getData().getLehen().getLehen(lordId).getNobleLevel().getValue() <= plugin.getData().getLehen().getLehen(lehenId).getNobleLevel().getValue())
		{
			errorMsg.add(ChatColor.RED+"You can only follow a higher Rank !");
			return false;
		}

		
		return true;
	}

}
