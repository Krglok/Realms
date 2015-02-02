package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleDelete extends RealmsCommand {
	
	private int settleId;
	private String name;


	public CmdSettleDelete() {
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.DELETE);
		description = new String[] {
				ChatColor.YELLOW+"/settle DELETE [SettleID] [Name] ",
				"Delete the Settlement  with ID and Name",
				"You must be an OP/Admin  ",
				"The building NOT destroyed and the settler are die!",
				"The sell- and transportorder deleted !",
				"This is a instant and final destroy ! "
		};
		this.settleId = 0;
		requiredArgs = 1;
	}

	@Override
	public void setPara(int index, String value) {
		switch (index)
		{
		case 1 :
			name = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value) {
		switch (index)
		{
		case 0 :
			settleId = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value) {

	}

	@Override
	public void setPara(int index, double value) {

	}

	@Override
	public String[] getParaTypes() {
		return new String[] {int.class.getName(), String.class.getName() };
	}

	private void deleteOrder(Realms plugin, Settlement settle)
	{
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		for (Integer key :plugin.getRealmModel().getTradeMarket().keySet())
		{
			TradeMarketOrder order = plugin.getRealmModel().getTradeMarket().get(key);
			if (order.getSettleID() == settle.getId())
			{
				if (removeList.contains(key) == false)
				{
					removeList.add(key);
				}
			}
			if (order.getTargetId() == settle.getId())
			{
				if (removeList.contains(key) == false)
				{
					removeList.add(key);
				}
			}
		}
		for (Integer key : removeList)
		{
			plugin.getRealmModel().getTradeMarket().remove(key);
			System.out.println("Delete TradeOrder :"+key);
		}
		removeList.clear();
		for (Integer key : plugin.getRealmModel().getTradeTransport().keySet())
		{
			TradeMarketOrder order = plugin.getRealmModel().getTradeTransport().get(key);
			if (order.getSettleID() == settle.getId())
			{
				if (removeList.contains(key) == false)
				{
					removeList.add(key);
				}
			}
			if (order.getTargetId() == settle.getId())
			{
				if (removeList.contains(key) == false)
				{
					removeList.add(key);
				}
			}
		}
		for (Integer key : removeList)
		{
			plugin.getRealmModel().getTradeMarket().remove(key);
			System.out.println("Delete TransportOrder :"+key);
		}

	}
	
	private void deleteNPCRef(Realms plugin, Settlement settle)
	{
		for (NpcData npc : plugin.getData().getNpcs().values())
		{
			if (npc.getSettleId() == settle.getId())
			{
				npc.setSettleId(0);
				npc.setWorkBuilding(0);
				npc.setAlive(false);
				npc.setNpcType(NPCType.BEGGAR);
				plugin.getData().writeNpc(npc);
			}
		}
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender) 
	{
		ArrayList<String> msg = new ArrayList<String>();
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			msg.add("The Model is busy or not enaled !");
		} else
		{
			Settlement settle = plugin.getData().getSettlements().getSettlement(settleId);
			deleteOrder(plugin, settle);
			deleteNPCRef(plugin, settle);
			
			plugin.getData().removeSettlement(settle);

			plugin.stronghold.getRegionManager().destroySuperRegion(settle.getName(),false);
//			plugin.stronghold.getRegionManager().remove Region(plugin.makeLocation(settle.getPosition()));
			msg.add("SuperRegion destroyed in HeroStronghold");

		}
		Integer page = 1;
		plugin.getMessageData().printPage(sender, msg, page );
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender) 
	{

		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add(ChatColor.RED+"The Model is busy or not enaled !");
			return false;
		}
		if (plugin.getRealmModel().getSettlements().getSettlement(this.settleId) == null )
		{
			errorMsg.add(ChatColor.RED+"Settlement not exist !");
			return false;
		}
		if (plugin.getRealmModel().getSettlements().getSettlement(this.settleId).getName().equals(name)==false)
		{
			errorMsg.add(ChatColor.RED+"Wrong name of settlement !");
			return false;
		}
		
		if (isOpOrAdmin(sender) == false)
		{
			errorMsg.add(ChatColor.RED+"You are not a Admin !");
			return false;
		}

		return true;
	}

}
