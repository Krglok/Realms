package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleRequired extends aRealmsCommand
{
	int settleID;
	Integer page ;

	public CmdSettleRequired()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.REQUIRE);
		description = new String[] {
				ChatColor.YELLOW+"/settle REQUIRE [SettleID] [page] ",
		    	"List of required Items for the settlement",
		    	"  "
			};
			requiredArgs = 0;
			requiredArgs = 1;
			this.settleID = 0;
			this.page = 0;  //default value
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
		return new String[] { int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
	    Settlement  settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
	    if (settle != null)
	    {
			msg.add("Settlement ["+settle.getId()+"] : "+settle.getName());
			msg.add(settle.getName()+" Required  [ "+settle.getRequiredProduction().size() +" ]");
		    for (Item item : settle.getRequiredProduction().values())
		    {
	    		msg.add(ConfigBasis.setStrleft(item.ItemRef()+"__________",15)+" : "+ChatColor.YELLOW+item.value());
		    }
	    }
	    if (page == 0)
	    {
	    	page = 1;
	    }
	    page = plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().getSettlement(settleID) != null)
			{
				if (isSettleOwner(plugin, sender, settleID) == false)
				{
					errorMsg.add("You are not the Owner !");
					errorMsg.add(" ");
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
