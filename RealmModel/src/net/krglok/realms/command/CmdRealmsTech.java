package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Owner;
import net.krglok.realms.science.KnowledgeList;
import net.krglok.realms.science.KnowledgeNode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
		msg.add("Techlevel  [ "+kList.size()+" ]"+"\n");
		
		for (String ref : kList.sortItems())
		{
			KnowledgeNode node = kList.get(ref);
    		msg.add(ChatColor.GREEN+node.getTechId()+"\n");
    		for (BuildPlanType bType : node.getBuildPermission())
    		{
    			msg.add(ChatColor.YELLOW+ConfigBasis.setStrleft(bType.name(),14)+"\n");
    		}
		}
    	if (sender instanceof Player)
		{
        	PlayerInventory inventory = ((Player) sender).getInventory();
    		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
			writeBook(book, msg, "Realm Admin","The Techlevel");
			inventory.addItem(book);
		}
		
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
