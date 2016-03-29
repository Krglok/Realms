package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.BookStringList;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdSettleProduction extends aRealmsCommand
{
	private int page;
	private int settleId;

	public CmdSettleProduction( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.PRODUCTION);
		description = new String[] {
				ChatColor.YELLOW+"/settle PRODUCTION [page] ",
				"List Production Overview of the settlelment ",
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
		ArrayList<String> msg = new ArrayList<String>();
		BookStringList msg1 = new BookStringList();
		msg.add("Settlement ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getId()
				+"] : "+ChatColor.YELLOW+plugin.getRealmModel().getSettlements().getSettlement(settleId).getName());
		msg.add("Item              |    Day  |    Month |  Store ["+plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().getPeriodCount()+"]");
		for (String ref : plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().sortItems())
		{
			BoardItem bItem = plugin.getRealmModel().getSettlements().getSettlement(settleId).getProductionOverview().get(ref);
			String name = ConfigBasis.setStrleft(bItem.getName()+"__________", 12);
			String last = ConfigBasis.setStrright(String.valueOf((int)bItem.getInputValue()), 9);
			String cycle = ConfigBasis.setStrright(String.valueOf((int)bItem.getInputSum()), 9);
			String period = ConfigBasis.setStrright(String.valueOf((int)  plugin.getRealmModel().getSettlements().getSettlement(settleId).getWarehouse().getItemList().getValue(bItem.getName())), 6);
			msg.add(name +"|"+last+"|"+cycle+"|"+period+"|");
			msg1.add(name+last+period);
		}
    	if (sender instanceof Player)
		{
    		Player player = ((Player) sender);
        	PlayerInventory inventory = player.getInventory();
        	ItemStack holdItem = player.getItemInHand();
        	if (holdItem.getData().getItemType() != Material.BOOK_AND_QUILL)
        	{
    		 holdItem  = new ItemStack(Material.WRITTEN_BOOK, 1);
        	}
			writeBook(holdItem, msg1, plugin.getRealmModel().getSettlements().getSettlement(settleId).getName(),"Production");
//			inventory.addItem(holdItem);
			((Player) sender).updateInventory();
		}
		msg.add("");
		page = plugin.getMessageData().printPage(sender, msg, page);
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
			if (isSettleOwner(plugin, sender, settleId) == false)
			{
				errorMsg.add("You are not the Owner !");
				errorMsg.add(" ");
				return false;
			}
			return true;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
