package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleTraining extends RealmsCommand
{

	int settleID;
	int page;

	public CmdSettleTraining()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.TRAINING);
		description = new String[] {
				ChatColor.YELLOW+"/settle TRAINING [SettleID] [page] ",
				"Show Information about Training Buildings ",
		    	"of the Settlement  ",
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
		case 1 :
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
		return new String[] { int.class.getName(), int.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			msg.add("Settlement ["+settle.getId()+"] : "
					+ChatColor.YELLOW+settle.getName()
					+ChatColor.GREEN+" Age: "+settle.getAge()
					+":"+settle.getProductionOverview().getCycleCount());
			for (Building building : plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().values())
			{
				
				msg.add(" "+ConfigBasis.setStrright(building.getId(),3)
				+" : "+ConfigBasis.setStrleft(building.getBuildingType().name(),12)
				+" : "+ConfigBasis.setStrleft(ChatColor.YELLOW+building.getTrainType().name(),10)
				+" : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainCounter(),3)
				+" : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),3)+" Cycles"
				);
			}
			msg.add(" ");
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (isOpOrAdmin(sender) == false)
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
			errorMsg.add("Settlement not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
