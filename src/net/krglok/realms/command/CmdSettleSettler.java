package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NpcData;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CmdSettleSettler extends aRealmsCommand
{
	private int page;
	private int settleId;

	public CmdSettleSettler( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.SETTLER);
		description = new String[] {
				ChatColor.YELLOW+"/settle SETTLER [page] ",
				"List Settler  Overview of the settlement ",
		    	"only the NPC   ",
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
		msg.add(ChatColor.YELLOW+"Settler|Job|");
		for (NpcData npc : plugin.getData().getSettlements().getSettlement(settleId).getResident().getNpcList().values())
		{
			String s = ConfigBasis.setStrright(npc.getId(),4)
					+"|"+ChatColor.YELLOW+ConfigBasis.setStrleft(npc.getName()+"____________",11)
					+"|"+ChatColor.WHITE+ConfigBasis.setStrleft(npc.getNpcType().name(), 7)
					+ "|"+ChatColor.YELLOW+ConfigBasis.setStrright(npc.getHomeBuilding(),4)
					+ "|"+ChatColor.GREEN+ConfigBasis.setStrright(npc.getWorkBuilding(),4)
					+ "|"+ChatColor.WHITE+ConfigBasis.setStrformat2(npc.getMoney(),8)
					;
			msg.add(s);
			
		}
    	if (sender instanceof Player)
		{
    		Player player = ((Player) sender);
        	PlayerInventory inventory = player.getInventory();
        	ItemStack holdItem = player.getItemInHand();
        	if (holdItem.getData().getItemType() == Material.WRITTEN_BOOK)
        	{
    			writeBook(holdItem, msg, "Realms","SettlementList");
    			((Player) sender).updateInventory();
        	}
		}

		page = plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
		settleId = 0;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add("[Realm Model] NOT enabled or too busy");
			errorMsg.add("Try later again");
			return false;
		}
		if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
		{
			errorMsg.add("Settlement not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		return true;
	}

}
