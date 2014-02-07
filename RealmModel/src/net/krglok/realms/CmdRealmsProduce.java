package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsProduce extends RealmsCommand
{

	public CmdRealmsProduce( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.PRODUCTION);
		description = new String[] {
				ChatColor.YELLOW+"/realms PRODUCTION ",
		    	"Set a production in the command queue  ",
		    	"Do it only for test case !!  ",
		    	"Only for Admins and OPs  ",
		    	"  "
			};
			requiredArgs = 0;
	}

	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{

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
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		
		if (TickTask.isProduction())
		{
			TickTask.setIsProduction(false);
			msg.add("Production set FALSE ");
		} else
		{
			TickTask.setIsProduction(true);
			msg.add("Production set TRUE ");
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		//  || (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED))
		{
			plugin.getRealmModel().OnProduction();
			msg.add("[Realm Model] Production");
			for (Settlement settle : plugin.getRealmModel().getSettlements().getSettlements().values())
			{
				msg.add(settle.getId()+" : "+settle.getName());
				msg.add("Storage  : "+settle.getWarehouse().getItemMax());
				msg.add("Capacity : "+settle.getResident().getSettlerMax());
				msg.add("Settlers : "+settle.getResident().getSettlerCount());
				msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
				msg.add("Happiness: "+settle.getResident().getHappiness());
				msg.add("Fertility: "+settle.getResident().getFertilityCounter());
				msg.add("Deathrate: "+settle.getResident().getDeathrate());
				msg.add("Required Items "+settle.getRequiredProduction().size());
				for (String itemRef : settle.getRequiredProduction().keySet())
				{
					Item item = settle.getRequiredProduction().getItem(itemRef);
					msg.add(item.ItemRef()+" : "+item.value());
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		return true;
	}

}
