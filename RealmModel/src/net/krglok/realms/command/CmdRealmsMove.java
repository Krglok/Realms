package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRealmsMove extends RealmsCommand
{
	int settleID;
	int page ;

	public CmdRealmsMove( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.MOVE);
		description = new String[] {
				ChatColor.YELLOW+"/realms MOVE [SettleID] ",
				"Move the Player to the center of the settlement ",
		    	"only for OPs and Admin  ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleID = 0;
		this.page = 1;  //default value
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
			settleID = value;
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
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			msg.add(ChatColor.RED+"Settlement not exist!");
			msg.add(" ");
	    	plugin.getMessageData().printPage(sender, msg, page);
			return;
		}
		World world = plugin.getServer().getWorld(settle.getPosition().getWorld());
		Location newPos = new Location(world,settle.getPosition().getX(),settle.getPosition().getY(),settle.getPosition().getZ());
		Player player = (Player) sender;
		player.teleport(newPos);
		msg.add(ChatColor.RED+"Settlement not exist!");
		msg.add(" ");
				
    	plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if(isOpOrAdmin(sender) == false)
		{
			if (isSettleOwner(plugin, sender, settleID) == false)
			{
				errorMsg.add("You are not the Owner !");
				errorMsg.add(" ");
				return false;
			}
		}
		return true;
	}

}
