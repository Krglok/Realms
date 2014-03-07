package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleWorkshop extends RealmsCommand
{
	private int settleID;
	private Integer buildingId ;
	private int slot;
	private String itemRef;

	public CmdSettleWorkshop()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.WORKSHOP);
		description = new String[] {
				ChatColor.YELLOW+"/settle WORKSHOP [SettleID] [Building] [slot] [item]",
		    	"Set the item in production slot (0..4)  ",
		    	"of the BuildingId of settlement ",
		    	"the Production is based on internal Production List"
			};
			requiredArgs = 4;
			this.settleID = 0;
			this.buildingId = 1;  
			this.slot = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 3:
			itemRef = value;
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
		case 0:
			settleID = value;
			break;
		case 1 :
				buildingId = value;
			break;
		case 2 :
			slot = value;
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
		return new String[] {int.class.getName(), int.class.getName(), int.class.getName(), String.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().getBuilding(buildingId).addSlot(slot, itemRef, plugin.getConfigData());
		msg.add("Slots are loaded with :");
		int index = 0;
		for (Item item : plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().getBuilding(buildingId).getSlots())
		{
			msg.add(index +" : "+ConfigBasis.setStrleft(item.ItemRef()+"________", 12));
			index ++;
		}
    	msg.add("");
		plugin.getMessageData().printPage(sender, msg, 1);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
				if (plugin.getRealmModel().getSettlements().getSettlement(settleID).getBuildingList().getBuilding(buildingId).getBuildingType() != BuildPlanType.WORKSHOP)
				{
					errorMsg.add("The Workshop not found ");
					errorMsg.add("The Building Id is Wrong ??");
					return false;
				}

				itemRef.toUpperCase();
				if (plugin.getServerData().getRecipe(itemRef).size() == 0)
				{
					errorMsg.add("Recipe not found !!!");
					return false;
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
