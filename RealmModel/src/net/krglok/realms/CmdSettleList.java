package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleList extends RealmsCommand
{
	private int page;
	
	public CmdSettleList( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/settle LIST [page] ",
				"List all Settlements ",
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
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		SettlementList  rList = plugin.getRealmModel().getSettlements();
	    if (rList != null)
	    {
			msg.add("ID |Settlement | Active | Owner [ "+rList.getSettlements().size()+" ]");
		    for (Settlement settle : rList.getSettlements().values())
		    {
	    		msg.add(settle.getId()
	    				+" : "+ChatColor.YELLOW+settle.getName()
	    				+" : "+ChatColor.GOLD+settle.isEnabled()
	    				+" Owner: "+settle.getOwner()
	    				+" in "+settle.getPosition().getWorld());
		    }
	    } else
	    {
			msg.add("ID |Settlement | Active | Owner [ null ]");
	    	msg.add("/settle LIST [page] ");
	    	msg.add("NO settlements found !!");
	    }
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
