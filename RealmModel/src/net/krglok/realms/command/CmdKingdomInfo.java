package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdKingdomInfo extends RealmsCommand
{
	private int page;
	private int kingdomId;
	
	public CmdKingdomInfo()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.INFO);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom Info [kingdomId] [page]  ",
		    	"Show the data of the Kingdom  ",
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
		msg.add("Settlements:");
		for (Settlement settle : plugin.getData().getSettlements().getSubList(kingdom).values())
		{
    		msg.add(settle.getId()
    				+" | "+ChatColor.GOLD+settle.getName()
    				+" | "+ChatColor.YELLOW+settle.getOwnerId()
    				+" |Settler: "+settle.getResident().getSettlerCount()
    				);
		}
		msg.add("Lehen:");
		for (Lehen lehen : plugin.getData().getLehen().getSubList(kingdom.getId()).values())
		{
    		msg.add(lehen.getId()
    				+" | "+ChatColor.GOLD+lehen.getName()
    				+" | "+ChatColor.YELLOW+lehen.getOwnerId()
    				+" |Settler: "+lehen.getNobleLevel().name()
    				);
			
		}
		plugin.getMessageData().printPage(sender, msg, page);
		kingdomId = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom not exist !");
			return false;
		}

		return true;
	}

}
