package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.science.KnowledgeList;
import net.krglok.realms.science.KnowledgeNode;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsTech extends RealmsCommand
{

	private int page;
	
	public CmdRealmsTech()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TECH);
		description = new String[] {
				ChatColor.YELLOW+"/realms TECH {page}  ",
		    	"List the TechLevel list or data  ",
		    	"None = List of Techlevels ",
		    	"0 = Tech 0 description ",
		    	": upto ",
		    	"7 = Tech 7 description ",
		    	" "
			};
			requiredArgs = 0;
			page = -1;
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
		KnowledgeList kList = plugin.getRealmModel().getKnowledgeData().getKnowledgeList();
		msg.add("ID |     |  [ "+kList.size()+" ]");
		
		for (String ref : kList.sortItems())
		{
			KnowledgeNode node = kList.get(ref);
    		msg.add(node.getTechId()
    				+" | "+ChatColor.GOLD+node.getAchievName()
    				+" | "+node.getDescription()
    				);
		}
		msg.add(" ");
		
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use this 
		return true;
	}

}
