package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdKingdomStructure extends RealmsCommand
{
	private int page;
	private int kingdomId;
	
	public CmdKingdomStructure()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.STRUCTURE);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom STRUCTURE [kingdomId] [page]  ",
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

	private String printNode(Lehen lehen, int ebene)
	{
		String versatz="";
		switch(ebene)
		{
		case 1: versatz = "   +";
		break;
		case 2: versatz = "  +";
		break;
		case 3: versatz = " +";
		break;
		case 4: versatz = " + ";
		break;
		default :
			break;
		}
		String line = versatz + lehen.getId()
				+" | "+ChatColor.GOLD+lehen.getName()
				+" | "+ChatColor.YELLOW+lehen.getOwnerId()
				+" | "+lehen.getNobleLevel().name()
				;
		return line;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Kingdom kingdom = plugin.getData().getKingdoms().getKingdom(kingdomId);
		msg.add("ID "+kingdom.getId()+" : "+kingdom.getName());
		msg.add("King "+kingdom.getOwner().getPlayerName());
		msg.add("Settlements:");
		msg.add("Lehen:");
		Lehen root = plugin.getData().getLehen().getKingdomRoot(kingdomId);
		msg.add(printNode(root,4));
		for (Lehen lehen : plugin.getData().getLehen().getParentList(root.getId()).values())
		{
			msg.add(printNode(lehen,3));
			for (Lehen child1 : plugin.getData().getLehen().getParentList(lehen.getId()).values())
			{
				msg.add(printNode(child1,2));
				for (Lehen child2 : plugin.getData().getLehen().getParentList(child1.getId()).values())
				{
					msg.add(printNode(child2,2));
				}
							
			}
			
		}
		plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (kingdomId ==  0)
		{
			errorMsg.add(ChatColor.RED+"Kingdom 0 has no structure  !");
			return false;
		}
		return true;
	}

}
