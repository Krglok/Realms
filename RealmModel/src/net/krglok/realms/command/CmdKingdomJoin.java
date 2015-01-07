package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Request;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKingdomJoin extends RealmsCommand
{
	private int page;
	private int kingdomId;
	private String playerName;
	Owner owner ;
	
	public CmdKingdomJoin()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.JOIN);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom JOIN [kingdomId] {playerName}  ",
		    	"Send request for join to kingdom to the king  ",
		    	"this is a loyalty oath ",
		    	"All your Lehen and Settlement going to the kingdom. ",
		    	"The king must accet your request and loyalty oath ",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
			playerName = "";
			owner = null;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			playerName = value;
			break;
		default :
			break;
		}
	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0:
			kingdomId = value;
			break;
		default :
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
		return new String[] {int.class.getName(), String.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();

		Request request = new Request();
		request.setOwnerId(owner.getId());
		request.setStatus(Request.REQUEST_STATUS_OPEN);
		
		plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().addRequest(request);
		
		msg.add("You send a join request to kingdom "+kingdomId);
		
		plugin.getMessageData().printPage(sender, msg, page);
		owner = null;
		kingdomId = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		if (playerName != "")
		{
			if (isOpOrAdmin(sender) == false)
			{
				errorMsg.add(ChatColor.RED+"Only Admins can Requst for other");
				return false;
			}
			owner = plugin.getData().getOwners().getOwnerName(playerName);
		} else
		{
			owner = plugin.getData().getOwners().getOwnerName(player.getName());
		}
		
		if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom not exist !");
			return false;
		}
		
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"Owner  not exist !");
			return false;
		}
		if (owner.getKingdomId() == plugin.getData().getKingdoms().getKingdom(kingdomId).getId())
		{
			errorMsg.add(ChatColor.RED+"Owner is always a member of the kingdom !");
			return false;
		}
		if (owner.getKingdomId() != 0)
		{
			errorMsg.add(ChatColor.RED+"You must be in Kingdom 0 for join !");
			return false;
		}
		return true;
	}

}
