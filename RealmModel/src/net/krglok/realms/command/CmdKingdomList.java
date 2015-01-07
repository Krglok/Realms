package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdKingdomList extends RealmsCommand
{
	
	private int page;
	
	public CmdKingdomList()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom LIST [page]  ",
		    	"List the registered Kingdoms   ",
		    	"only Admins can create Kingdoms ",
		    	" "
			};
			requiredArgs = 0;
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
		return new String[] { int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("ID |Name            |Owner     |Member|  [ "+plugin.getData().getKingdoms().size()+" ]");
		for (Kingdom kingdom : plugin.getData().getKingdoms().values())
		{
    		msg.add(kingdom.getId()
    				+" | "+ChatColor.GOLD+kingdom.getName()
    				+" | "+ChatColor.YELLOW+plugin.getData().getOwners().getOwner(kingdom.getOwnerId()).getPlayerName()
    				+" | "+kingdom.getMembers().size()
    				);
		}
		
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use the command
		return true;
	}

}
