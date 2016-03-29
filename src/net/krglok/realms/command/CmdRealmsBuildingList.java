package net.krglok.realms.command;

import java.io.File;
import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsBuildingList extends aRealmsCommand
{
	private int page;
	private String search ;

	public CmdRealmsBuildingList()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.BUILDINGLIST);
		description = new String[] {
			ChatColor.YELLOW+"/realms BUILDINGLIST [page] {WORD} ",
			"Show a list of BuildPlans for the BuildManager ",
			"Not all Plans may available !"
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
		case 1 :
				search = value;
			break;
		default:
			break;
		}

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
		return new String[] {int.class.getName(), String.class.getName()  };
	}

	
	private ArrayList<String> getTMXList(Realms plugin)
	{
		ArrayList<String> tmxList = new ArrayList<String>();
		String path = plugin.getDataFolder().getAbsolutePath();
        File regionFolder = new File(path, "buildplan");
        if (!regionFolder.exists()) {
        	System.out.println("BuildPlan Folder not found !"+path);
            return tmxList;
        }

        for (File TMXFile : regionFolder.listFiles()) 
        {
        	String sRegionFile = TMXFile.getName();
        	if (sRegionFile.contains("tmx"))
        	{
	        	sRegionFile = sRegionFile.replace(".tmx", "");
	        	tmxList.add(sRegionFile);
        	}
//        	System.out.println(sRegionFile);
        }
        return tmxList;
	}

	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add("BuildPlans available for Build :");
    	msg.add(" ");
    	ArrayList<String> tmxList = getTMXList(plugin);
    	msg.addAll(tmxList);
		plugin.getMessageData().printPage(sender, msg, page);
		helpPage = "";
		page = 1;
		msg.clear();

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
