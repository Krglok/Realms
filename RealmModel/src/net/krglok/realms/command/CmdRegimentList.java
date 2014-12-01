package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRegimentList extends RealmsCommand
{

	private int page;
	
	public CmdRegimentList()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/regiment LIST [page] ",
				"List all regiments ",
		    	"  ",
		};
		requiredArgs = 0;
		page = 1;
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
		case 0 :
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
		return new String[] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		RegimentList  rList = plugin.getRealmModel().getRegiments();
	    if (rList.size() > 0)
	    {
			msg.add("ID |Regiment | Status | Owner [ "+rList.size()+" ]");
		    for (Regiment regiment : rList.values())
		    {
	    		msg.add(regiment.getId()
	    				+" : "+ChatColor.YELLOW+regiment.getName()
	    				+" : "+ChatColor.GOLD+regiment.getRegStatus().name()
	    				+" Owner: "+regiment.getOwner()
	    				+" in "+regiment.getPosition().getWorld());
		    }
	    } else
	    {
			msg.add("ID |Settlement | Active | Owner [ null ]");
	    	msg.add("/regiment LIST [page] ");
	    	msg.add("NO regiments found !!");
	    }
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
