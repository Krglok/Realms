package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdKingdomMember extends aRealmsCommand
{
	private int page;
	private int kingdomId;
	
	public CmdKingdomMember()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.MEMBER);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom MEMBER [kingdomId] [page]  ",
		    	"Show the list of member of the Kingdom  ",
		    	"There are multiple pages ! ",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
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
			kingdomId = value;
			break;
		case 1:
			page = value;
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
		Kingdom kingdom = plugin.getData().getKingdoms().getKingdom(kingdomId);
		msg.add("ID "+kingdom.getId()+" : "+kingdom.getName());
		msg.add("King "+kingdom.getOwner().getPlayerName());
		msg.add("Member:");
		for (Owner owner : plugin.getData().getOwners().getSubList(kingdom).values())
		{
    		msg.add(owner.getId()
    				+" | "+ChatColor.GOLD+owner.getNobleLevel()
    				+" | "+ChatColor.YELLOW+owner.getPlayerName()
    				);
		}
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
