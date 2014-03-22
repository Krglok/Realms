package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.model.McmdBuilder;
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
		msg.add(ChatColor.YELLOW+"Item           |Region    |");
		for (BuildPlanType bItem : plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList().keySet())
		{
			String name = ConfigBasis.setStrleft(bItem.name()+"__________", 15);
			String settler = ConfigBasis.setStrright(String.valueOf(plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList().get(bItem)), 3);
			String product = "";
			msg.add(name +"|"+settler+" |");
		}
		
		msg.add(ChatColor.YELLOW+"BuildManager : ");
		String sBuilding = "";
		String status = "";
		if (plugin.getRealmModel().getSettlements().getSettlement(settleId).buildManager().getActualBuild() != null)
		{
			sBuilding = ChatColor.GREEN+plugin.getRealmModel().getSettlements().getSettlement(settleId).buildManager().getActualBuild().getBuildingType().name();
			status = plugin.getRealmModel().getSettlements().getSettlement(settleId).buildManager().getStatus().name();
		} else
		{
			sBuilding = ChatColor.YELLOW+"NO Build";
			status = plugin.getRealmModel().getSettlements().getSettlement(settleId).buildManager().getStatus().name();
		}
		msg.add(sBuilding +"|"+status+" |");
		
		msg.add("Cmd          |Position        |");
		msg.add(ChatColor.YELLOW+"Build command in queue : ");
		for (McmdBuilder buildOrder : plugin.getRealmModel().getSettlements().getSettlement(settleId).settleManager().getCmdBuilder())
		{
			String name = ConfigBasis.setStrleft(buildOrder.getbType().name(), 15);
			String product = ""+ConfigBasis.setStrright(buildOrder.getPosition().getX(), 7);
			product = product+":"+ConfigBasis.setStrright(buildOrder.getPosition().getY(), 5);
			product = product+":"+ConfigBasis.setStrright(buildOrder.getPosition().getZ(), 7);
			msg.add(name +"|"+product+" |");
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
