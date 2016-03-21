package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKingdomGive extends RealmsCommand
{
	private int page;
	private int kingdomId;
	private int lehenId;
	private String playerName;
	
	public CmdKingdomGive()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.GIVE);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom GIVE [kingdomId] [lehenId] [ownerName] ",
		    	"The king give a lehen to his liege ",
		    	"The king give land for loyalty ",
		    	"both are bound to the loyalty ",
		    	" "
			};
			requiredArgs = 3;
			page = 0;
			kingdomId = 0;
			lehenId = 0;
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
			kingdomId = value;
			break;
		case 1:
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
		return new String[] {int.class.getName(), int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Owner  owner = plugin.getData().getOwners().getOwnerName(playerName);
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
		lehen.setOwner(owner);
		plugin.getData().writeLehen(lehen);
		msg.add(playerName+" is now the owner of lehen "+lehenId+" : "+lehen.getName());
		msg.add("the new title is "+lehen.getNobleLevel()+" of "+lehen.getName());
		owner.setNobleLevel(lehen.getNobleLevel());
		plugin.getData().writeOwner(owner);
		plugin.getMessageData().printPage(sender, msg, page);
		
		kingdomId = 0;
		lehenId = 0;
		playerName = "";
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		
		if (isOpOrAdmin(sender) == false)
		{
			Owner me = plugin.getData().getOwners().getOwnerName(player.getName());
			if (me.getNobleLevel() != NobleLevel.KING)
			{
				errorMsg.add(ChatColor.RED+"Only the king can do that !");
				return false;
			}
		}
		Owner  owner;
		owner = plugin.getData().getOwners().getOwnerName(playerName);
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"Playername is not a valid owner of Realms");
			return false;
		}
		
		if (owner.getKingdomId() != kingdomId)
		{
			errorMsg.add(ChatColor.RED+"The playerName is not a Member of your Kingdom !");
			errorMsg.add(ChatColor.YELLOW+"The playerName must make a join request !");
			errorMsg.add(ChatColor.YELLOW+"Try /kingdom join ");
			errorMsg.add(ChatColor.YELLOW+"And then /kingdom request ");
			errorMsg.add(ChatColor.YELLOW+" ");
			return false;
		}
			
		if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom not exist !");
			return false;
		}

		if (plugin.getData().getLehen().getLehen(lehenId) == null)
		{
			errorMsg.add(ChatColor.RED+"Lehen  not exist !");
			return false;
		}
		if (plugin.getData().getLehen().getLehen(lehenId).getKingdomId() != kingdomId)
		{
			errorMsg.add(ChatColor.RED+"Lehen is not in your kingdom !");
			return false;
		}
		
		return true;
	}

}
