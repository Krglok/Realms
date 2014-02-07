package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.colonist.ColonyList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdColonistList extends RealmsCommand
{
	private int page;
	
	public CmdColonistList( )
	{
		super(RealmsCommandType.COLONIST, RealmsSubCommandType.LIST);
		description = new String[] {
				ChatColor.YELLOW+"/colonist LIST [page] ",
				"List all Colonists ",
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
		return new String [] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		ColonyList  cList = plugin.getRealmModel().getColonys();
	    if (cList != null)
	    {
			msg.add("ID |Colonist | Active | Owner [ "+cList.size()+" ]");
		    for (Colony colony : cList.values())
		    {
	    		msg.add(colony.getId()
	    				+" : "+ChatColor.YELLOW+colony.getName()
	    				+" : "+ChatColor.GOLD+colony.getPosition().getX()
	    				+" : "+ChatColor.GOLD+colony.getPosition().getY()
	    				+" : "+ChatColor.GOLD+colony.getPosition().getZ()
	    				+" in "+colony.getPosition().getWorld());
		    }
	    } else
	    {
			msg.add("ID |Colonist | Active | Owner [ null ]");
	    	msg.add("/colonist LIST [page] ");
	    	msg.add("NO Colonists found !!");
	    }
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
