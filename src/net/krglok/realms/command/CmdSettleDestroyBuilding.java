package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Owner;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleDestroyBuilding extends aRealmsCommand
{
	private int settleId;
	private int buildingId;

	public CmdSettleDestroyBuilding( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.DESTROY);
		description = new String[] {
				ChatColor.YELLOW+"/settle DESTROY [SettleID] [BuildingID] ",
				"Destroy a building and region of ",
				"You must be the Owner of the Building ! ",
				"This is a instant and final destroy ! ",
		    	"  ",
		};
		this.buildingId = 0;
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
		case 1 :
			buildingId = value;
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
		Player player = (Player) sender;
		
		Building building = plugin.getData().getBuildings().getBuilding(buildingId);
		plugin.getData().removeBuilding(building);
		msg.add("Building removed from Database");
		plugin.getData().getBuildings().removeBuilding(building);
		msg.add("Building removed from List");
		if (settleId > 0)
		{
			plugin.getData().getSettlements().getSettlement(settleId).setBuildingList(plugin.getData().getBuildings().getSubList(settleId));
			msg.add("Building removed from settlement");
		}
		plugin.stronghold.getRegionManager().destroyRegion(plugin.makeLocation(building.getPosition()));
		plugin.stronghold.getRegionManager().removeRegion(plugin.makeLocation(building.getPosition()));
		msg.add("Region destroyed HeroStronghold");
		
		Integer page = 1;
		plugin.getMessageData().printPage(sender, msg, page );
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add("The Model is busy or not enaled !");
			return false;
		}
		if (settleId > 0)
		{
			if ( plugin.getRealmModel().getSettlements().getSettlement(this.settleId) == null )
			{
				errorMsg.add("Settlement not exist !");
				return false;
			}
		}
		Building building = plugin.getData().getBuildings().getBuilding(buildingId);
		if (building == null)
		{
			errorMsg.add("Building not exist !");
			return false;
		}
		
		if (isOpOrAdmin(sender) == false)
		{
			Player player = (Player) sender;
			Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
			if (owner != null)
			{
				if (building.getOwnerId() != owner.getId())
				{
					errorMsg.add("You are not the owner of the building !");
					return false;
				}
			} else
			{
				errorMsg.add("You are not a regular owner of Realms !");
				return false;
			}
			
			return true;
		}
		return true;
	}

}
