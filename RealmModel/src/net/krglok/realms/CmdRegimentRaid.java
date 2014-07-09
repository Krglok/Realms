package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRegimentRaid extends RealmsCommand
{
	int regID;
	int settleID;

	public CmdRegimentRaid()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.RAID);
		description = new String[] {
				ChatColor.YELLOW+"/settle RAID [RegID] [SettleID] ",
		    	"Start a Raid of the Settlement ",
		    	"Fight with the Guards and steal ",
		    	"some Items from the Warehouse ",
		    	"After the raid the regiment go away ",
		    	" and search for a new target ",
		    	" "
			};
			requiredArgs = 2;
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
		    Settlement  settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		    if (settle != null)
		    {
		    	regiment.newRaid(settle);
				errorMsg.add("Raid startet to "+settle.getName());
				errorMsg.add("The regiment will figth 10 rounds ");
				errorMsg.add("when win the warehouse will plundered ");
				errorMsg.add("when defeat the regiment flee  ");
				errorMsg.add(" ");
		    } else
		    {
				errorMsg.add("Raid not possible ");
				errorMsg.add("The settlement not found ! ");
				errorMsg.add(" ");
		    }
	    } else
	    {
			errorMsg.add("Raid not possible ");
			errorMsg.add("The regiment not found ! ");
			errorMsg.add(" ");
	    }
	    plugin.getMessageData().printPage(sender, msg, 1);
	    
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getRegiments().get(regID) != null)
			{
				if (isOpOrAdmin(sender) == false)
				{
					if (plugin.getRealmModel().getRegiments().get(regID).getRegStatus() == RegimentStatus.IDLE)
					{
						errorMsg.add("Raid not possible ");
						errorMsg.add("The regiment is busy or hidden ! ");
						errorMsg.add(" ");
						return false;
					}
					if (isRegimentOwner(plugin, sender, regID)== false)
					{
						errorMsg.add("Raid not allowed ");
						errorMsg.add("You are not the owner of the regiment ! ");
						errorMsg.add(" ");
						return false;
					}
					if (isSettleOwner(plugin, sender, settleID)== false)
					{
						errorMsg.add("Raid not allowed ");
						errorMsg.add("You are the owner of the settlement! ");
						errorMsg.add(" ");
						return false;
					}
				}
				if (sender.isOp())
				{
					return true;
				}
			}
			errorMsg.add("Regiment not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
