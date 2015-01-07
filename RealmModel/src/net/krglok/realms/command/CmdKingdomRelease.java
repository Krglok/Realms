package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class CmdKingdomRelease extends RealmsCommand
{
	private int page;
	private int kingdomId;
	private String playerName;
	Owner owner ;
	
	public CmdKingdomRelease()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.RELEASE);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom RELEASE {playerName}  ",
		    	"You release the kingdom and go to Kingdom 0  ",
		    	"Now you are an enemy to the prior Kingdom ",
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
		case 0:
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
		return new String[] {String.class.getName()  };
	}

	/**
	 * setzt alle Parent lehen, die nicht dem owner gehören auf 0
	 * Dadurch werden diese beim Join neu verlinkt
	 * 
	 * @param plugin
	 * @param owner
	 */
	private void checkParents(Realms plugin, Owner owner)
	{
		for (Lehen lehen : plugin.getData().getLehen().getSubList(owner).values())
		{
			Lehen parent = plugin.getData().getLehen().getLehen(lehen.getParentId());
			if (parent != null)
			{
				if (parent.getOwnerId() != lehen.getOwnerId())
				{
					lehen.setParentId(0);
				}
			}
			lehen.setKingdomId(0);
			plugin.getData().writeLehen(lehen);
		}
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);
		int oldKingdom = owner.getKingdomId();
		checkParents(plugin, owner);
		msg.add(ChatColor.YELLOW+"Your Lehen  set to kingdom 0");

		owner.setKingdomId(0);
		plugin.getData().writeOwner(owner);
		msg.add(ChatColor.YELLOW+"Your Kingdom is now 0 , You an enemy to "+oldKingdom);

		plugin.getMessageData().printPage(sender, msg, page);
		playerName = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		if (playerName != "")
		{
			if (isOpOrAdmin(sender) == false)
			{
				errorMsg.add(ChatColor.RED+"Only Admins can Release for other");
				return false;
			}
			owner = plugin.getData().getOwners().getOwnerName(playerName);
		} else
		{
			playerName = player.getName();
			owner = plugin.getData().getOwners().getOwnerName(playerName);
		}
		
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"Owner  not exist !");
			return false;
		}

		return true;
	}

}
