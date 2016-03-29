package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdColonyWarehouse extends aRealmsCommand
{
	private int page;
	private int colonyId;

	public CmdColonyWarehouse( )
	{
		super(RealmsCommandType.COLONIST, RealmsSubCommandType.WAREHOUSE);
		description = new String[] {
				ChatColor.YELLOW+"/settle WAREHOUSE [SettleID] [page] ",
		    	"List all Items in the Warehouse ",
		    	"  "
			};
			requiredArgs = 1;
			this.colonyId = 0;
			this.page = 1;  //default value
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
			colonyId = value;
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
	    Colony  colony = plugin.getRealmModel().getColonys().get(colonyId);
	    if (colony != null)
	    {
			msg.add("Colonist ["+colony.getId()+"] : "+colony.getName());
			msg.add(colony.getName()+" Warehouse  [ "+colony.getWarehouse().getItemList().size()+"/"+colony.getWarehouse().getItemList().getItemCount() +" ]");
		    for (Item item : colony.getWarehouse().getItemList().values())
		    {	
	    		msg.add(ConfigBasis.setStrleft(item.ItemRef(),15)+" : "+ChatColor.YELLOW+item.value());
		    }
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getColonys().get(colonyId) != null)
			{
				return true;
			}
			errorMsg.add("Colony not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			errorMsg.add("try /colonist list ");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}


}
