package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleAddBuilding extends RealmsCommand
{
	private int settleId;
	private int regionId;

	public CmdSettleAddBuilding()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.ADD);
		description = new String[] {
				ChatColor.YELLOW+"/settle ADD [SettleID] [RegionID] ",
				"Add a region to a Settlement  ",
				"You must NOT be the Owner of the region ! ",
				"You must  be the Owner of the Settlement ! ",
				"The region must exist",
		    	"  ",
		};
		this.regionId = 0;
		this.settleId = 0;
		requiredArgs = 2;
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
		case 1:
			regionId = value;
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
		int page = 1;
		Region region = null;
		Settlement settle = null;

		settle = plugin.getRealmModel().getSettlements().getSettlement(this.settleId);
		if (settle == null)
		{
			msg.add("Settlemnet not found ! ID: "+this.settleId);
			msg.add("NO Building was added, unknown error !");
			plugin.getMessageData().printPage(sender, msg, page);
			return;
		} else
		region = plugin.stronghold.getRegionManager().getRegionByID(this.regionId);
		if (region == null)
		{
			msg.add("Region not found ! ID: "+this.regionId);
			msg.add("NO Building was added, unknown error !");
			plugin.getMessageData().printPage(sender, msg, page);
			return;
		}
		String hsRegionType = region.getType();
		BuildPlanType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
		Building building = new Building(
				buildingType, 
				this.regionId, 
				hsRegionType, 
				true,
				new LocationData(
						region.getLocation().getWorld().getName(),
						region.getLocation().getX(), 
						region.getLocation().getY(),
						region.getLocation().getZ()));
		if (Settlement.addBuilding(building, settle))
		{
			msg.add("Settlement ["+settle.getId()+"]  "+settle.getName());
			msg.add("Storage : "+settle.getWarehouse().getItemList().getItemCount());
			msg.add("Capacity: "+settle.getResident().getSettlerMax());
			msg.add("Settlers: "+settle.getResident().getSettlerCount());
			msg.add("Building: "+settle.getBuildingList().size());
			msg.add("Bank    : "+settle.getBank().getKonto());
			msg.add("new "+building.getBuildingType()+" to Settlement added");
		} else
		{
			msg.add("/settle add [settleID] [regionID] ");
			msg.add("NO Building was added, unknown error !");
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
		return ;

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add("The Model is busy or not enaled !");
			errorMsg.add("");
			return false;
		}
		if ( plugin.getRealmModel().getSettlements().getSettlement(this.settleId).getBuildingList().containRegion(regionId) )
		{
			errorMsg.add("The region is always in the settlement !");
			errorMsg.add("");
			return false;
		}
		if (sender.isOp())
		{
			return true;
		}

		return isSettleOwner(plugin, sender, settleId);
	}
}
