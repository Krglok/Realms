package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleBuildingList extends RealmsCommand
{
	
	private int page;
	private int settleId;

	public CmdSettleBuildingList( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUILDINGLIST);
		description = new String[] {
				ChatColor.YELLOW+"/settle BUILDINGLIST [page] ",
				"List Production Overview of the settlelment ",
		    	"  ",
		};
		requiredArgs = 1;
		page = 1;
		settleId = 0;
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
			settleId = value;
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
		return new String[] {int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getId()
				+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleId).getName());
		msg.add("Item           |Region    |Beds|Product");
		for (Building bItem : plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildingList().values())
		{
			String name = ConfigBasis.setStrleft(bItem.getBuildingType().name(), 15);
			String region = ConfigBasis.setStrleft(bItem.getHsRegionType(), 10);
			String settler = ConfigBasis.setStrright(String.valueOf(bItem.getSettler()), 2);
			String slots = ConfigBasis.setStrright(String.valueOf(bItem.getSlot1().size()), 5);
			ItemList output = plugin.getServerData().getRegionOutput(region);
			String product = "";
			for (String item : output.keySet())
			{
				product = product + item + "|";
			}
			msg.add(name +"|"+region+"| "+settler+" |"+product);
		}
		msg.add("");
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
			{
				errorMsg.add("Settlement not found !!!");
				errorMsg.add("The ID is wrong or not a number ?");
				return false;
			}
			return true;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}