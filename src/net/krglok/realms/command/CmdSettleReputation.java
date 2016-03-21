package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.manager.ReputationData;
import net.krglok.realms.manager.ReputationStatus;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleReputation extends RealmsCommand
{

	private int settleID;

	public CmdSettleReputation( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.REPUTATION);
		description = new String[] {
				ChatColor.YELLOW+"/setlle REPUTATION [SettleID]  ",
				"Get the amount of item from the warehouse ",
		    	"of the Settlement  ",
		    	"Put the item in your inventory ",
		    	"  "
		};
		requiredArgs = 1;
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
		case 0:
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
		return new String[] { int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		
		Player player = (Player) sender;
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		int reputation = settle.getReputations().getReputation(player.getName());
		msg.add(ChatColor.GREEN+"Your Reputation in Settlement ["+settle.getId()+"] : "+ChatColor.YELLOW+settle.getName());
		msg.add(ChatColor.GREEN+ReputationStatus.ReputationStatusMessage(reputation)+" ["+reputation+"]");
		if (reputation > ConfigBasis.REPUTATION_GOAL_OWNER)
		{
			msg.add(ChatColor.DARK_PURPLE+"You can get Ownership of the settlelemnt");
			msg.add(ChatColor.DARK_PURPLE+"Use /settle ASSUME for assume control ");
			
		} else
		if (reputation > ConfigBasis.REPUTATION_GOAL_TRADER)
		{
			msg.add(ChatColor.DARK_PURPLE+"You can make routes to this settlement");
			msg.add(ChatColor.DARK_PURPLE+"All /settle ROUTE to this settlement are accepted ");
		} else
		if (reputation > ConfigBasis.REPUTATION_GOAL_ACQUIRE)
		{
			msg.add(ChatColor.DARK_PURPLE+"You can get buildings in settlement");
			msg.add(ChatColor.DARK_PURPLE+"Use the sign [ACQUIRE] to get a building ");
		}

		
		for (ReputationData repData : settle.getReputations().values())
		{
	    	msg.add(ChatColor.YELLOW+"Source: "+ChatColor.GREEN+repData.getRepTyp().name()+":"+repData.getValue());
	    	for (String itemRef : repData.getItemValues().keySet())
	    	{
	    		msg.add(ChatColor.WHITE+" + "+itemRef+":"+repData.getItemValues().get(itemRef));
	    	}
			
		}
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleID))
			{
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
