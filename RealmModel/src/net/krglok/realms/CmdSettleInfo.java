package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleInfo extends RealmsCommand
{
	int settleID;
	int page ;

	public CmdSettleInfo( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.INFO);
		description = new String[] {
		    	"/settle INFO [SettleID] [page] ",
				"Show Infomation about the Settlement ",
		    	"and show the analysis report ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleID = 0;
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
		case 0:
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
		return new String[] { int.class.getName(), int.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			if (settle != null)
			{
				msg.add("Settlement ["+settle.getId()+"] : "+settle.getName());
				msg.add("Residence: "+ChatColor.YELLOW+settle.getResident().getSettlerMax());
				msg.add("Settlers  : "+ChatColor.GOLD+settle.getResident().getSettlerCount());
				msg.add("Needed     : "+ChatColor.YELLOW+settle.getTownhall().getWorkerNeeded());
				msg.add("Workers  : "+ChatColor.GOLD+settle.getTownhall().getWorkerCount());
				msg.add("Happiness: "+ChatColor.GOLD+settle.getResident().getHappiness());
				msg.add("Fertility   : "+settle.getResident().getFertilityCounter());
				msg.add("Deathrate: "+ChatColor.RED+settle.getResident().getDeathrate());
				msg.add("Bank       : "+ChatColor.GREEN+((int) settle.getBank().getKonto()));
				msg.add("Storage   : "+settle.getWarehouse().getItemMax());
				msg.add("Building   : "+settle.getBuildingList().size());
				msg.add("Food      : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
				msg.add("====================== ");
				msg.add(ChatColor.ITALIC+"Required Items : "+settle.getRequiredProduction().size());
				for (String itemRef : settle.getRequiredProduction().keySet())
				{
					Item item = settle.getRequiredProduction().getItem(itemRef);
					msg.add(ChatColor.ITALIC+" -"+item.ItemRef()+" : "+item.value());
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, page);
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
