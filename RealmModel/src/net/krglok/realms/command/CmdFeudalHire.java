package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.model.McmdHireSettler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdFeudalHire extends RealmsCommand
{

	private int lehenId;
	private int settleId;
	private double price = 1000.0;
	private int page = 1;
	
	public CmdFeudalHire( )
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.HIRE);
		description = new String[] {
				ChatColor.YELLOW+"/feudal HIRE [lehenId] [settleId]  ",
				"Hire a new Setttler for lehenId",
		    	"form the Settlement settleId",
		    	"The Settlement must your or in tributlist",
		    	"The cost are 1000 Thaler, given half to NPC ",
		    	"and Settlement ",
		    	" "
			};
		lehenId = 0;
		settleId= 0;
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
			lehenId = value;
			break;
		case 1:
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
		return new String[] { int.class.getName(), int.class.getName() };	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		
		McmdHireSettler mCmd = new McmdHireSettler(plugin.getRealmModel(),settleId,lehenId,price);
		plugin.getRealmModel().getcommandQueue().add(mCmd);
		msg.add("Command send to Settlement, wait a moment for execution");
		msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, page);
		lehenId = 0;
		settleId= 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getData().getSettlements().getSettlement(settleId) == null)
		{
			errorMsg.add(ChatColor.RED+"The settleId is wrong !");
			return false;
		}
		if (plugin.getData().getLehen().getLehen(lehenId) == null)
		{
			errorMsg.add(ChatColor.RED+"The lehenId is wrong !");
			return false;
		}
		if (plugin.getData().getLehen().getLehen(lehenId).getBank().getKonto() < price)
		{
			errorMsg.add(ChatColor.RED+"Not enough money in lehen bank !");
			return false;
		}
		if (plugin.getData().getSettlements().getSettlement(settleId).getResident().findRecrute() == null)
		{
			errorMsg.add(ChatColor.RED+"NO Recrute found in Settlement !");
			return false;
		}
		
		return true;
	}

}
