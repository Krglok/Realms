package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleWorkshop extends RealmsCommand
{
	private int settleID;
//	private Integer buildingId ;
	private int slot;
	private String itemRef;

	public CmdSettleWorkshop()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.WORKSHOP);
		description = new String[] {
				ChatColor.YELLOW+"/settle WORKSHOP [slot] [item]",
		    	"Set the item in production slot (0..4)  ",
		    	"You must stay in a WORKSHOP building/area ",
		    	"the Production is based on internal Production List",
		    	" use /realms RECIPES  "
			};
			requiredArgs = 2;
			this.settleID = 0;
//			this.buildingId = 1;
			this.itemRef = "";
			this.slot = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			itemRef = value;
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
			slot = value;
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
		return new String[] {int.class.getName(), String.class.getName()};
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	Player player = (Player) sender;
    	Location loc = player.getLocation();
    	Region region = findRegionAtPosition(plugin, loc);
    	Building building = plugin.getData().getBuildings().getBuildingByRegion(region.getID());
		building.addSlot(slot, itemRef, plugin.getConfigData());
		plugin.getData().writeBuilding(building);
		msg.add("WORKSHOP Slots are loaded with :");
		int index = 0;
		for (Item item : building.getSlots())
		{
			if (item != null)
			{
				msg.add(index +" : "+ConfigBasis.setStrleft(item.ItemRef()+"________", 12));
				index ++;
			}
		}
    	msg.add("");
		plugin.getMessageData().printPage(sender, msg, 1);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
	    	Player player = (Player) sender;
	    	Location loc = player.getLocation();

	    	String settleName = findSettlementAtLocation(plugin, player);
			
			if (plugin.getRealmModel().getSettlements().containsName(settleName)== false)
			{
				errorMsg.add("Settlement not found !!!");
				errorMsg.add("The name is wrong  ?");
				return false;
			}
				
			settleID = plugin.getRealmModel().getSettlements().findName(settleName).getId();
			if (isOpOrAdmin(sender) == false)
			{
				if (isSettleOwner(plugin, sender, settleID) == false)
				{
					errorMsg.add("You are not the Owner !");
					return false;
				}
				
			}
			if (slot < 0 )
			{
				errorMsg.add(ChatColor.RED+"Slot number must positive ");
				errorMsg.add("Numbers valid 0..4 ");
				return false;
			}
			if (slot > 4 )
			{
				errorMsg.add(ChatColor.RED+"Slot number to high ");
				errorMsg.add("Numbers valid 0..4 ");
				return false;
			}
	    	ArrayList<Region>  regions = plugin.stronghold.getRegionManager().getContainingRegions(loc);
	    	if (regions.size() == 0)
			{
				errorMsg.add("The region not found ");
				errorMsg.add("Stay in a WORKSHOP building ");
				return false;
			}
			if (regions.iterator().next().getType().equals(BuildPlanType.WORKSHOP.name()) == false)
			{
				errorMsg.add("The Workshop not found ");
				errorMsg.add("Stay in a WORKSHOP building ");
				return false;
			}

			
			itemRef.toUpperCase();
			if (ConfigBasis.isMaterial(itemRef) == false)
			{
				errorMsg.add("Not a valid MatrialName !!!");
				return false;
			}
			if (plugin.getServerData().getRecipe(itemRef).size() == 0)
			{
				errorMsg.add("Recipe not found !!!");
				return false;
			}
			return true;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}

}
