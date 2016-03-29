package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.stronghold.region.RegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class CmdRealmsProduce extends aRealmsCommand
{
	private String search;
	private int page;

	public CmdRealmsProduce( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.PRODUCTION);
		description = new String[] {
				ChatColor.YELLOW+"/realms PRODUCTION {BUILDINGTYPE} ",
				"Show production details of BuildingType ",
				"If no BuildingType a List of Buildings shown",
		    	"  "
			};
		requiredArgs = 0;
		page = 1;
		search = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				search = value;
			break;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, int value)
	{

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
		return new String[] { String.class.getName() };
	}

	private String toLine(String s, String value)
	{
		s = s + " "+ConfigBasis.setStrleft(value+"________", 12);
		return s;
	}

	private ArrayList<String> BuildingSection(int von, int bis, ChatColor color)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	int index = 0; 
    	String line = "";
    	for (BuildPlanType bType : BuildPlanType.values())
    	{
    		if ((bType.getValue() > von) && (bType.getValue() < bis) )
    		{
    			if (index >= 4)
    			{
    				msg.add(color+line);
    				index = 0;
    				line = "";
    			}
    			index++;
    			line = toLine(line,bType.name());
    		}
    	}
		if (line.length() > 0)
		{
			msg.add(color+line);
			index = 0;
			line = "";
		}

    	return msg;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	if (search == "")
    	{
        	msg.addAll(BuildingSection(200, 300,ChatColor.GREEN));
        	msg.addAll(BuildingSection(300, 400,ChatColor.LIGHT_PURPLE));
    		
    	} else
    	{
        	msg.add(ChatColor.GREEN+"BuildPlan production requirements "+ChatColor.YELLOW+search);
        	RegionType region = plugin.stronghold.getRegionManager().getRegionType(search);
        	if (region != null)
        	{
            	msg.add(ChatColor.GREEN+"Required Input :");
            	int index = 0; 
        		String line = "";
            	for (ItemStack item : region.getUpkeep())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item.getType().name()+" : "+item.getAmount()+" | ";
            		} else
            		{
            			msg.add(ChatColor.RED+line);
            			line = item.getType().name()+" : "+item.getAmount()+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.YELLOW+line);
        		}
            	msg.add(" ");
            	msg.add(ChatColor.GREEN+"Output Resources:");
            	index = 0; 
        		line = "";
            	for (ItemStack item : region.getOutput())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item.getType().name()+" : "+item.getAmount()+" | ";
            		} else
            		{
            			msg.add(ChatColor.LIGHT_PURPLE+line);
            			line = item.getType().name()+" : "+item.getAmount()+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.YELLOW+line);
        		}
            	msg.add(" ");
        	} else
        	{
            	msg.add(ChatColor.RED+"Building not found or not for production");
            	msg.add(ChatColor.GREEN+"Try command /realms BUILDING (without parameter)");
        		
        	}
        			
//    		ItemList required = plugin.getServerData().getRegionUpkeep(search);
    	}
		plugin.getMessageData().printPage(sender, msg, page);
		helpPage = "";
		page = 1;
		msg.clear();
		search = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
