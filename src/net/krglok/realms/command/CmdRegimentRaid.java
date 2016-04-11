package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRegimentRaid extends aRealmsCommand
{
	int regID;
	int settleID;

	public CmdRegimentRaid()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.RAID);
		description = new String[] {
				ChatColor.YELLOW+"/settle RAID [RegID] ",
		    	"Start a Raid of the Settlement nearby",
		    	"Fight with the Guards and steal ",
		    	"some Items from the Warehouse ",
		    	"After the raid the regiment go away ",
		    	" and search for a new target ",
		    	" "
			};
			requiredArgs = 1;
			this.regID =0;
			this.settleID = 0;
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
			regID = value;
		break;
		case 1 :
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
		return new String[] { int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
	    Regiment regiment = plugin.getRealmModel().getRegiments().get(regID);
	    if (regiment != null)
	    {
		    Settlement  settle = plugin.getRealmModel().getSettlements().getSettlement(regiment.getSettleId());
		    if (settle != null)
		    {
		    	regiment.startRaid(settle);
				msg.add("Raid startet to "+settle.getName());
				msg.add("The regiment will figth 10 rounds ");
				msg.add("when win the warehouse will plundered ");
				msg.add("when defeat the regiment flee  ");
		    } else
		    {
				msg.add("Raid not possible ");
				msg.add("The settlement not found ! ");
				msg.add(" ");
		    }
	    } else
	    {
			msg.add("Raid not possible ");
			msg.add("The regiment not found ! ");
			msg.add(" ");
	    }
	    plugin.getMessageData().printPage(sender, msg, 1);
	    
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add(ChatColor.RED+"[Realm Model] NOT enabled or too busy");
			errorMsg.add("Try later again");
			return false;
		}
		Regiment regiment = plugin.getData().getRegiments().getRegiment(regID);
		settleID = regiment.getSettleId();
		Settlement settle = plugin.getData().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			errorMsg.add(ChatColor.RED+"Settlement not found !!!");
			errorMsg.add("Has the regiment a camp position ?");
			return false;
		}
		if (regiment == null)
		{
			errorMsg.add(ChatColor.RED+"Regiment not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		if (isOpOrAdmin(sender) == false)
		{
			if (regiment.getRegStatus() != RegimentStatus.IDLE)
			{
				errorMsg.add(ChatColor.RED+"Raid not possible ");
				errorMsg.add("The regiment is busy or hidden ! ");
				return false;
			}
			if (isRegimentOwner(plugin, sender, regID)== false)
			{
				errorMsg.add(ChatColor.RED+"Raid not allowed ");
				errorMsg.add("You are not the owner of the regiment ! ");
				return false;
			}
			if (isSettleOwner(plugin, sender, settleID)== false)
			{
				errorMsg.add(ChatColor.RED+"Raid not allowed ");
				errorMsg.add("You are the owner of the settlement! ");
				return false;
			}
		}
		if (regiment.getPosition().distance2D(settle.getPosition()) > 265.0)
		{
			errorMsg.add(ChatColor.DARK_PURPLE+"Distance to great for a Raid ! ");
			errorMsg.add(ChatColor.YELLOW+"Try /regiment move  ");
		}
		return true;
	}

}
