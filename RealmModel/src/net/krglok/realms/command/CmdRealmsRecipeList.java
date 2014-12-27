package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsRecipeList extends RealmsCommand
{
	
	Integer page ;
	Integer group;

	public CmdRealmsRecipeList()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.RECIPES);
		description = new String[] {
				ChatColor.YELLOW+"/realms RECIPES [group {page}",
		    	"Show the list of recipes for WORKSHOP ",
		    	" 1 = tools",
		    	" 2 = weapons ",
		    	" 3 = armors ",
		    	" 4 = materials ",
		    	" 5 = builds ",
		    	" 6 = ores ",
		    	" 7 = valuables ",
		    	" 8 = raws ",
		    	" 9 = foods ",
		    	"  ",
			};
			requiredArgs = 1;
			page = 0;
			group = 0;
	}

	public int getPage()
	{
		return page;
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
			group = value;
		break;
		case 1 :
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
		return new String[] { int.class.getName(), int.class.getName() };
	}

	private ArrayList<String> makeList(ItemList subList)
	{
		ArrayList<String> msg = new ArrayList<String>();
		for (String item : subList.sortItems())
		{
			msg.add(item);
		}
		return msg;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();

		switch (group)
		{
		case 1 :
			msg.add(ChatColor.GREEN+"Tool Items:");
			msg.addAll(makeList(plugin.getConfigData().getToolItems()));
			break;
		case 2 :
			msg.add(ChatColor.GREEN+"Weapon Items:");
			msg.addAll(makeList(plugin.getConfigData().getWeaponItems()));
			break;
		case 3 :
			msg.add(ChatColor.GREEN+"Armour Items:");
			msg.addAll(makeList(plugin.getConfigData().getArmorItems()));
			break;
		case 4 :
			msg.add(ChatColor.GREEN+"Material tems:");
			msg.addAll(makeList(plugin.getConfigData().getMaterialItems()));
			break;
		case 5 :
			msg.add(ChatColor.YELLOW+"Build Material Items:");
			msg.addAll(makeList(plugin.getConfigData().getBuildMaterialItems()));
			break;
		case 6 :
			msg.add(ChatColor.YELLOW+"Ore Items:");
			msg.addAll(makeList(plugin.getConfigData().getOreItems()));
			break;
		case 7 :
			msg.add(ChatColor.YELLOW+"Valuable Items:");
			msg.addAll(makeList(plugin.getConfigData().getValuables()));
			break;
		case 8 :
			msg.add(ChatColor.YELLOW+"Raw Items:");
			msg.addAll(makeList(plugin.getConfigData().getRawItems()));
			break;
		case 9 :
			msg.add(ChatColor.YELLOW+"Food Items:");
			msg.addAll(makeList(plugin.getConfigData().getToolItems()));
			break;
		default:
			msg.addAll(getDescriptionString());
		}
		
	    if (page == 0)
	    {
	    	page = 1;
	    }
	    page = plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (group < 1)
		{
			return false;
		}
		return true;
	}

}
