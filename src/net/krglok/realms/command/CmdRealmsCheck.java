package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRealmsCheck extends aRealmsCommand
{

	private String worldName;
	private int page;

	public CmdRealmsCheck( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.CHECK);
		description = new String[] {
				ChatColor.YELLOW+"/realms CHECK [page]",
		    	"Check region for Building reference ",
		    	"  "
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
		return new String[] { int.class.getName() };
	}

	

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		
		msg.add("RegionTypen in  "+worldName);
    	HashMap<String,Integer> buildingStat = new HashMap<String,Integer>();
        for (Region sData : plugin.stronghold.getRegionManager().getRegions().values())
        {
        	if (sData.getLocation().getWorld().getName().equalsIgnoreCase(worldName))
        	{
//				String bType = sData.getType();
//				if (buildingStat.containsKey(bType))
//				{
//					buildingStat.put(bType, (buildingStat.get(bType)+1));
//				}else
//				{
//					buildingStat.put(bType, 1);
//				}
        		if (plugin.getData().getBuildings().getBuildingByRegion(sData.getID()) == null)
        		{
        			System.out.println("region :"+sData.getID()+":"+sData.getType()+":"+sData.getLocation().toString());
        		}
        	}
        }
//        for (String key : buildingStat.keySet())
//        {
//	    	String sName = ConfigBasis.setStrleft(key, 20);
//	    	msg.add(sName+":"+ChatColor.YELLOW+buildingStat.get(key));
//        }
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		if (sender instanceof Player)
		{
			worldName = sender.getServer().getPlayer(sender.getName()).getLocation().getWorld().getName();
			return true;
		} else
		{
			worldName = sender.getServer().getWorlds().get(0).getName();
			if (worldName == null)
			{
				errorMsg.add("Default World not found !!");
				return false;
			}
			return true;
		}
		
	}

}
