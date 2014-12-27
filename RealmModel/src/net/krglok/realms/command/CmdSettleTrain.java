package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleTrain extends RealmsCommand
{
	private int settleID;
	private int buildingId;
	private int amount;
	private int page;

	public CmdSettleTrain()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.TRAIN);
		description = new String[] {
				ChatColor.YELLOW+"/settle TRAIN [SettleID] [BuildingId] [amount]",
				"Start training of units for the building ",
				"train up to amount number of units",
		    	"  "
		};
		requiredArgs = 2;
		this.settleID = 0;
		this.buildingId = 0;
		this.amount = 1;
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
			buildingId = value;
			break;
		case 2 :
			amount = value;
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
		return new String[] { int.class.getName(), int.class.getName(), int.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			Building building = plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().getBuildingByRegion(buildingId);
			building.setIsEnabled(true);
			building.addMaxTrain(amount);
			msg.add("Settlement ["+settle.getId()+"] : "
					+ChatColor.YELLOW+settle.getName()
					+ChatColor.GREEN+" Age: "+settle.getAge()
					+":"+settle.getProductionOverview().getCycleCount());
			msg.add("Building: "+building.getBuildingType().name());
			msg.add("Train   : "+ChatColor.YELLOW+building.getTrainType().name());
			msg.add("Need    : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),4)+" Cycles");
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
		amount = 1;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().getBuildingByRegion(buildingId)  != null)
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
				} else
				{
					errorMsg.add("Building not found !!!");
					errorMsg.add("The ID is wrong or not a number ?");
					return false;
				}
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
