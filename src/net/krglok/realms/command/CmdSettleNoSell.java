package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleNoSell extends RealmsCommand
{

	int settleID;
	Integer page ;
	String itemName;

	public CmdSettleNoSell()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.NOSELL);
		description = new String[] {
				ChatColor.YELLOW+"/settle NOSELL [SettleID] [page] {ITEM_NAME} ",
		    	"List of required Items for the settlement",
		    	"give ITEMNAME for required item by you  ",
		    	"set required amiount to 128 "
			};
			requiredArgs = 1;
			this.settleID = 0;
			this.page = 1;  //default value
			this.itemName = "";
	}


	public int getPage()
	{
		return page;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch(index)
		{
			case 2:
				itemName = value;
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
		return new String[] { int.class.getName(), int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
	    Settlement  settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
	    if (settle != null)
	    {
	    	if (itemName != "")
	    	{
	    		settle.settleManager().getDontSell().addItem(new Item(itemName, 128));
				msg.add("Set : "+itemName+": 128");
	    	}
			msg.add("Settlement ["+settle.getId()+"] : "+settle.getName());
			msg.add(settle.getName()+" No sell  [ "+settle.settleManager().getDontSell().size() +" ]");
		    for (Item item : settle.settleManager().getDontSell().values())
		    {
	    		msg.add(ConfigBasis.setStrleft(item.ItemRef()+"__________",15)+" : "+ChatColor.YELLOW+item.value());
		    }
	    }
	    if (page == 0)
	    {
	    	page = 1;
	    }
	    page = plugin.getMessageData().printPage(sender, msg, page);
		this.settleID = 0;
		this.page = 1;  //default value
		this.itemName = "";

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
		if (plugin.getRealmModel().getSettlements().getSettlement(settleID) == null)
		{
			errorMsg.add("Settlement not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		if (itemName != "")
		{
			if (ConfigBasis.isMaterial(itemName) == false)
			{
				errorMsg.add("Not a regular MATERIALNAME!");
				errorMsg.add("You can find regular Material in the pricelist!");
				return false;
			}
		}
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
	}

}
