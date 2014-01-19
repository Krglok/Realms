package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsHelp extends RealmsCommand
{

	private int page;
	
	public CmdRealmsHelp()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.HELP);
		description = new String[] {
			"command not found this will help you ",
	    	"/realms INFO PRICE [item], show the production price",
	    	"/realms INFO PRICELIST {page}, show the pricelist  ",
	    	"/realms SET PRICE [item] [#.#], set the price  ",
	    	"/realms WRITE [#], write the settlement to file ",
	    	"/realms READ [#], read the the settlement from file ",
	    	"/realms DEBUG[true/false], set/unset Debug Mode ",
	    	"/realms ACTIV , activate the Model and make init ",
	    	"/realms DEACTIV , deactivate the model ",
	    	"/realms SET AUTO [true/false], start/stop tick processing",
	    	"/realms SET COUNTER [#] , set the production Counter ",
	    	"/realms LIST BUILDING, list the Stronghold Regions ",
	    	"/realms LIST SETTLE, list the Stronghold Superegions ",
	    	"/realms LIST  ",
	    	"/realms LIST  ",
	    	"/realms LIST  ",
	    	"/realms LIST  ",
	    	"/realms LIST  "
		};
		requiredArgs = 1;
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
		return new String[] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+ConfigBasis.setStrleft(ConfigBasis.LINE,30));
    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+" ");
		msg.addAll(getDescriptionString());
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
