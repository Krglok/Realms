package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdKingdomCreate extends RealmsCommand
{
	private int page;
	private String name;
	private String playerName;
	private boolean isNPC;
	
	public CmdKingdomCreate()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom CREATE [Name] [isNPC] [PlayerName]  ",
		    	"Create a new Kingdom with name   ",
		    	"only Admins can create Kingdoms ",
		    	" "
			};
			requiredArgs = 2;
			page = 0;
			name = "";
			playerName = "";
			isNPC = true;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch(index)
		{
		case 0 :
			name = value;
			break;
		case 2 :
			playerName = value;
			break;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, int value)
	{

	}

	@Override
	public void setPara(int index, boolean value)
	{
		switch(index)
		{
		case 1 :
			isNPC = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { String.class.getName(), boolean.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Create Kingdom  "+name+ " with king "+playerName);
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);
		
		Kingdom kingdom = new Kingdom(1, name, owner, isNPC );
		plugin.getData().getKingdoms().addKingdom(kingdom);
		plugin.getData().writeKingdom(kingdom);
		
		int kingdomId = kingdom.getId();
		owner.setKingdomId(kingdomId);
		plugin.getData().writeOwner(owner);
		msg.add("Owner set to Kingdom"+kingdomId);
		
		for (Lehen lehen :plugin.getData().getLehen().getSubList(playerName).values())
		{
			lehen.setKingdomId(kingdomId);
			plugin.getData().writeLehen(lehen);
			msg.add("Lehen "+lehen.getId()+" set to Kingdom"+kingdomId);
		}

		
		plugin.getMessageData().printPage(sender, msg, page);
		name = "";
		playerName = "";
		isNPC = true;
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (isOpOrAdmin(sender) == false)
		{
			errorMsg.add(ChatColor.RED+"Only Amdmin and Ops can us this command");
			return false;
		}
		if (name == "")
		{
			errorMsg.add(ChatColor.RED+"Kingdom name is wrong");
			return false;
		}
		if (plugin.getData().getKingdoms().findKingdom(name) != null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom "+name+" always exist ! ");
			return false;
		}
		if (playerName == "")
		{
			playerName = sender.getName();
		}
		if (plugin.getData().getOwners().getOwnerName(playerName) == null)
		{
			errorMsg.add(ChatColor.RED+"Owner with "+playerName+" not exist ! ");
			return false;
		}
		if (playerName.equalsIgnoreCase(sender.getName())== false)
		{
			boolean isValid = false;
			for (Lehen lehen :plugin.getData().getLehen().getSubList(playerName).values())
			{
				if (lehen.getSettleType() == SettleType.LEHEN_4)
				{
					isValid = true;
				}
			}
			if (isValid == false)
			{
				errorMsg.add(ChatColor.RED+"The player owns no LEHEN_4 , and can not be a king");
				return false;
			}
		}
		
		return true;
	}

}
