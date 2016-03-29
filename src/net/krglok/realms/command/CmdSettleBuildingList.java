package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.BookStringList;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdSettleBuildingList extends aRealmsCommand
{
	
	private int page;
	private int settleId;

	public CmdSettleBuildingList( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUILDINGLIST);
		description = new String[] {
				ChatColor.YELLOW+"/settle BUILDINGLIST [page] ",
				"List Buildings Overview of the settlelment ",
		    	"  ",
		};
		requiredArgs = 1;
		page = 1;
		settleId = 0;
	}

	public int getPage()
	{
		return page;
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
		BookStringList msg = new BookStringList();
		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getId()
				+"] ");
		msg.add(plugin.getRealmModel().getSettlements().getSettlement(settleId).getName());
		msg.add("Building    |Count");
		HashMap <String,Integer> builpTypList = plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList();
		for (String ref : plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().sortStringList(builpTypList.keySet()))
		{
			String name = ConfigBasis.setStrleft(ref+"__________", 12);
			String settler = ConfigBasis.setStrright(String.valueOf(builpTypList.get(ref)), 3);
			msg.add(name +": "+settler);
		}
    	if (sender instanceof Player)
		{
    		Player player = ((Player) sender);
        	PlayerInventory inventory = player.getInventory();
        	ItemStack holdItem = player.getItemInHand();
        	if (holdItem.getData().getItemType() != Material.WRITTEN_BOOK)
        	{
        		holdItem  = new ItemStack(Material.WRITTEN_BOOK, 1);
   				inventory.addItem(holdItem);
        	}
			writeBook(holdItem, msg, plugin.getRealmModel().getSettlements().getSettlement(settleId).getName(),"Building Info");
			player.updateInventory();
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
			sBuilding = "NO Build";
			status = plugin.getRealmModel().getSettlements().getSettlement(settleId).buildManager().getStatus().name();
		}
		msg.add(sBuilding +"|"+status+" |");
		
		msg.add("Build command in queue : ");
		msg.add("Cmd BuildType");
		for (McmdBuilder buildOrder : plugin.getRealmModel().getSettlements().getSettlement(settleId).settleManager().getCmdBuilder())
		{
			String name = ConfigBasis.setStrleft(buildOrder.getbType().name(), 15);
//			String product = ""+ConfigBasis.setStrright(buildOrder.getPosition().getX(), 7);
//			product = product+":"+ConfigBasis.setStrright(buildOrder.getPosition().getY(), 5);
//			product = product+":"+ConfigBasis.setStrright(buildOrder.getPosition().getZ(), 7);
			msg.add(name );
		}
		page = plugin.getMessageData().printPage(sender, msg, page);
//		page = 1;

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
