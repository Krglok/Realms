package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdRealmsSettlement extends RealmsCommand
{
	private int page;
	private String search ;

	public CmdRealmsSettlement()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.SETTLEMENT);
		description = new String[] {
			ChatColor.YELLOW+"/realms SETTLEMENT {SettlementType} ",
			"Show construction details of BuildingType ",
			"If no BuildingType a List of Buildings shown",
			" "
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

	private ArrayList<String> SettlementSection(int von, int bis, ChatColor color)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	int index = 0; 
    	String line = "";
    	for (SettleType bType : SettleType.values())
    	{
    		if ((bType.getValue() >= von) && (bType.getValue() <= bis) )
    		{
//    			if (index >= 4)
//    			{
//    				msg.add(color+line+"\n");
//    				index = 0;
//    				line = "";
//    			}
    			index++;
//    			line = toLine(line,bType.name());
    			msg.add(color+bType.name()+"\n");
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
    		msg.add(ChatColor.GOLD+"protected Areas"+"\n");
        	msg.addAll(SettlementSection(10, 99,ChatColor.GOLD));
    		msg.add(ChatColor.GREEN+"Commoner Areas"+"\n");
			msg.add(ChatColor.GREEN+SettleType.BERTH.name()+"\n");
			msg.add(ChatColor.GREEN+SettleType.CLAIM.name()+"\n");
        	msg.addAll(SettlementSection(100, 399,ChatColor.GREEN));
    		msg.add(ChatColor.LIGHT_PURPLE+"Noble Areas"+"\n");
        	msg.addAll(SettlementSection(500, 600,ChatColor.LIGHT_PURPLE));


    		if (sender instanceof Player)
    		{
            	PlayerInventory inventory = ((Player) sender).getInventory();
        		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
    			writeBook(book, msg, "Realm Admin","The SettleTypes");
    			inventory.addItem(book);
    		}
    		
    	} else
    	{
        	msg.add(ChatColor.GREEN+"Minimum requirements for founding a  "+ChatColor.YELLOW+search);
//        	String pathName = plugin.stronghold.getDataFolder().getPath();
//            File regionFolder = new File(pathName, "RegionConfig");
//        	RegionConfig region = StrongholdTools.getRegionConfig(regionFolder.getAbsolutePath(), search+".yml");
        	SuperRegionType region = plugin.stronghold.getRegionManager().getSuperRegionType((search));
        	if (region != null)
        	{
            	msg.add(ChatColor.GREEN+"Radius    : "+ChatColor.YELLOW+region.getRawRadius());
            	msg.add(ChatColor.GREEN+"MaxPower  : "+ChatColor.YELLOW+region.getMaxPower());
            	msg.add(ChatColor.GREEN+"DailyPower: "+ChatColor.YELLOW+region.getDailyPower());
            	msg.add(ChatColor.GREEN+"Required Building :");
            	int index = 0; 
        		String line = "";
            	for (String item : region.getRequirements().keySet())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item+" : "+region.getRequirements().get(item)+" | ";
            		} else
            		{
            			msg.add(ChatColor.YELLOW+line);
            			line = item+" : "+region.getRequirements().get(item)+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.YELLOW+line);
        		}
            	msg.add(" ");
            	msg.add(ChatColor.GREEN+"Allowed Settlement in place :");
            	index = 0; 
        		line = "";
            	for (String item : region.getChildren())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item+" | ";
            		} else
            		{
            			msg.add(ChatColor.LIGHT_PURPLE+line);
            			line = item+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.LIGHT_PURPLE+line);
        		}
            	msg.add(" ");
            	msg.add(ChatColor.GREEN+"needed for ASSUME in Realms:");
            	msg.add(ChatColor.GREEN+"MoneyCost: "+ChatColor.YELLOW+region.getMoneyRequirement());
        	} else
        	{
            	msg.add(ChatColor.RED+"Building not found");
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
