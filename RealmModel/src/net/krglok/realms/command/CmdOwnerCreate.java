package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdOwnerCreate extends RealmsCommand
{
	private int page;
	private String playerName;
	private int npcId;
	
	public CmdOwnerCreate()
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/owner CREATE [NpcName] [NpcId] ",
		    	"Create a  Owner as NPC   ",
		    	"only for Admin and OPs",
		    	" "
			};
			requiredArgs = 2;
			page = 1;
			playerName = "";
			npcId = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch(index)
		{
		case 0:
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

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();

		Owner owner = new Owner(playerName, true);
		owner.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH0));
		owner.setCommonLevel(CommonLevel.COLONIST);
		owner.setUuid(String.valueOf(npcId));
		plugin.getData().getOwners().addOwner(owner);
		plugin.getData().writeOwner(owner);
		msg.add("Create Owner "+playerName+" as "+owner.getNobleLevel().name());
		plugin.getMessageData().printPage(sender, msg, page);
		playerName = "";
		npcId = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (isOpOrAdmin(sender) == false)
		{
			errorMsg.add(ChatColor.RED+"Only Amdmin and Ops can us this command");
			return false;
		}
		if (playerName == "")
		{
			errorMsg.add(ChatColor.RED+"PlayerName is wrong");
			return false;
		}
		Owner me = plugin.getData().getOwners().getOwnerName(playerName);
		if (me != null)
		{
			errorMsg.add(ChatColor.RED+"Owner with PlayerName exist");
			return false;
		}
		
		return true;
	}

}
