package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdWallSign extends aRealmsCommand
{
	public CmdWallSign()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.WALLSIGN);
		description = new String[] {
				ChatColor.YELLOW+"/realms WALLSIGN show the list of Wallsigns  ",
		    	"All Wallsign entrys are listed  ",
		    	"with a short description ",
		    	" "
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

	}

	@Override
	public void setPara(int index, boolean value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		msg.add(ChatColor.GREEN+"== List of WALLSIGN commands"+ChatColor.GREEN+" <RightClick>");
		msg.add(ChatColor.DARK_PURPLE+"the must in line 0  ");
//		msg.add("---------+---------+---------+---------+---------+-------- ");
		msg.add(ChatColor.YELLOW+"[TRAP], catch a animal nearby for a TAMER building ");
		msg.add(ChatColor.YELLOW+"[HUNT], kill a monster nearby for a HUNTER builging ");
		msg.add(ChatColor.YELLOW+"[WAREHOUSE] show list of items for settlement ");
		msg.add(ChatColor.YELLOW+"[INFO] , show the settlement status  ");
		msg.add(ChatColor.YELLOW+"[TRADER], show the trader aktivity for a settlement ");
		msg.add(ChatColor.YELLOW+"[BUILDINGS], show buildings with counter for settlement");
		msg.add(ChatColor.YELLOW+"[PRODUCTION], show produced items with statistic ");
		msg.add(ChatColor.YELLOW+"[MARKET], show the global sellorders ");
		msg.add(ChatColor.YELLOW+"[NOSELL], show the items with nosell status for settlement ");
		msg.add(ChatColor.YELLOW+"[REQUIRE], show list of required items for settlement ");
		msg.add(ChatColor.YELLOW+"[TRAIN], start train in military building ");
		msg.add(ChatColor.YELLOW+"[BOOK], get book from the booklist ");
		msg.add(ChatColor.YELLOW+"[DONATE), give item iun hand to warehouse of settlement ");
		msg.add(ChatColor.YELLOW+"[SELL], sell item to player, line1: <Material.name> ");
		msg.add(" ");

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use
		return true;
	}

}
