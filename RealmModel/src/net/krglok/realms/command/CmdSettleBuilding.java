package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleBuilding extends RealmsCommand
{

	int buildingId;
	boolean setEnable;
	
	
	public CmdSettleBuilding( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUILDING);
		description = new String[] {
				ChatColor.YELLOW+"/settle BUILDING [buildungId] {false} ",
		    	"Show info of building Id  ",
		    	"and set Enable to false " +
		    	"the ID is the Settlement Id (0 = no settlement) ",
		    	"the command create the HeroStronghold region ",
		    	"and a building for realms ",
		    	"You must place the fullfil the requirements ",
		    	"You must pay the building cost ",
		    	" "
			};
			requiredArgs = 1;
			buildingId = 0;
			setEnable = true;
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
			buildingId = value;
		break;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, boolean value)
	{
		switch (index)
		{
		case 1 :
			setEnable = value;
			break;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName(), boolean.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Building ");
		Building building = plugin.getData().getBuildings().getBuilding(buildingId);
		
		msg.add(ChatColor.GREEN+building.getBuildingType().name() +" | "+building.getId()+" | "+building.getHsRegion());
		msg.add(ChatColor.YELLOW+"Settlement : "+building.getSettleId()+ChatColor.WHITE+"  Position "+building.getPosition().toString());
		msg.add(ChatColor.YELLOW+"Settler: "+building.getSettler()+"| Need: "+building.getWorkerNeeded()+"| Work: "+building.getWorkerInstalled());
		msg.add(ChatColor.YELLOW+"Train  : "+building.getTrainType()+"| Time: "+building.getTrainTime()+"| Count: "+building.getTrainCounter());
		msg.add(ChatColor.YELLOW+"Active : "+building.isActive()+"| Enable: "+building.isEnabled());
		if (BuildPlanType.getBuildGroup(building.getBuildingType())== 200)
		{
			for (Item item : building.produce(plugin.getServerData()))
			{
				msg.add(item.ItemRef()+":"+item.value());
			}
		}
		if (BuildPlanType.getBuildGroup(building.getBuildingType())== 300)
		{
			for (Item item : building.produce(plugin.getServerData()))
			{
				msg.add(item.ItemRef()+":"+item.value());
			}
		}
		if (BuildPlanType.getBuildGroup(building.getBuildingType())== 500)
		{
			for (Item item : building.militaryProduction().values())
			{
				msg.add(item.ItemRef()+":"+item.value());
			}
		}
		if (building.isEnabled() != setEnable)
		{
			building.setIsEnabled(setEnable);
		}
		int page = 1;
		plugin.getMessageData().printPage(sender, msg, page);
		buildingId = 0;
		setEnable = true;

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
		if (plugin.getRealmModel().getBuildings().getBuilding(buildingId) == null)
		{
			errorMsg.add("Building not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		return true;
	}

}
