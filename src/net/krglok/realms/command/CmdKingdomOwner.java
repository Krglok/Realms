package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKingdomOwner extends aRealmsCommand
{
	private int page;
	private int kingdomId;
	private String playerName;
	
	public CmdKingdomOwner()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.OWNER);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom OWNER [kingdomId] [playerName] ",
		    	"The king set to the playername ",
		    	"The player set also as owner of the king lehen ",
		    	" "
			};
			requiredArgs = 2;
			page = 0;
			kingdomId = 0;
			playerName = "";
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
			kingdomId = value;
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
		Owner  owner = plugin.getData().getOwners().getOwnerName(playerName);
		owner.setNobleLevel(NobleLevel.KING);
		plugin.getData().writeOwner(owner);
		
		Kingdom kingdom = plugin.getData().getKingdoms().getKingdom(kingdomId);
		kingdom.setOwner(owner);
		plugin.getData().writeKingdom(kingdom);
		msg.add(playerName+" is now the owner of Kingdom "+kingdomId+" : "+kingdom.getName());
		msg.add("the new title is "+NobleLevel.KING+" of "+kingdom.getName());
		Lehen lehen = plugin.getData().getLehen().getKingdomRoot(kingdomId);
		if (lehen != null)
		{
			lehen.setOwner(owner);
			plugin.getData().writeLehen(lehen);
			msg.add(playerName+" is now the owner of lehen "+lehen.getId()+" : "+kingdom.getName());
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		kingdomId = 0;
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
		
			
		if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom not exist !");
			return false;
		}

		return true;
	}

}
