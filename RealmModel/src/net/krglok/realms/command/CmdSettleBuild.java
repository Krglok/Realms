package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.RegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
//import net.krglok.realms.core.Position;

public class CmdSettleBuild extends RealmsCommand
{
	LocationData position;
	String buildName;
	BuildPlanType bType;
	int settleId ;
	
	public CmdSettleBuild( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUILD);
		description = new String[] {
				ChatColor.YELLOW+"/settle BUILD [ID] [BUILDING_TYPE]",
		    	"Create a new Building {BUILDING_TYPE}  ",
		    	"at your current position " +
		    	"the ID is the Settlement Id (0 = no settlement) ",
		    	"the command create the HeroStronghold region ",
		    	"and a building for realms ",
		    	"You must fullfil the requirements ",
		    	"You must pay the building cost ",
		    	" "
			};
			requiredArgs = 2;
			position = new LocationData("", 0.0, 0.0, 0.0);
			buildName = "";
			bType = BuildPlanType.NONE;
			settleId = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1 :
			buildName = value;
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
			settleId = value;
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
		return new String[] {int.class.getName(), String.class.getName() };
	}
	
	

	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		World world = player.getWorld();
		LocationData iLoc = new LocationData(world.getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		Location loc = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		String ownerName = player.getName();
		Owner owner = plugin.getData().getOwners().getOwnerName(ownerName);
		if (owner == null)
		{
	    	msg.add("Owner not exist ");
			plugin.getMessageData().printPage(sender, msg, 1);
			return;
		}
		System.out.println("SettleCommand : Builder");
		ArrayList<String> owners = new ArrayList<String>();
		owners.add(ownerName);
		bType = BuildPlanType.valueOf(buildName);
		String regionType = bType.name();
		
		if (plugin.stronghold.getRegionManager().canBuildHere(player, loc) == false)
		{
	    	msg.add("You cant build here ");
	    	msg.add("not a owner , nor a member ");
	    	msg.add(" ");
			plugin.getMessageData().printPage(sender, msg, 1);
	    	return;
		}
		
		if (plugin.stronghold.getRegionManager().getContainingBuildRegions(loc).size() != 0)
		{
	    	msg.add("You overlaped a region ");
	    	msg.add("Have a safe distanc of +1 Block each side ");
	    	msg.add(" ");
			plugin.getMessageData().printPage(sender, msg, 1);
	    	return;
		}
		
		Map<Integer, Integer> reqMap = checkRegionRequirements(plugin, loc, regionType);
		if ((reqMap != null) && (reqMap.isEmpty() == false))
		{
			ArrayList<String> reqMsg = faultList(reqMap);
			msg.addAll(reqMsg);
	    	msg.add(" ");
			plugin.getMessageData().printPage(sender, msg, 1);
			return;
		}
		RegionLocation rLoc = new RegionLocation(bType.name(), iLoc, ownerName, "");
		plugin.doRegionRequest( world, rLoc );
//		plugin.stronghold.getRegionManager().addRegion(loc, regionType, owners);
		Region region = plugin.stronghold.getRegionManager().getRegion(loc);
		if (region == null)
		{
	    	msg.add("Created region not found ");
	    	msg.add(" ");
			plugin.getMessageData().printPage(sender, msg, 1);
	    	return;
		}
		double cost = plugin.getServerData().getRegionTypeCost(buildName);
		plugin.economy.withdrawPlayer(ownerName, cost);
		Building building = new Building(bType, region.getID(), iLoc, settleId);
		plugin.getRealmModel().getBuildings().addBuilding(building);
		plugin.getRealmModel().getData().writeBuilding(building);
		
		if (settleId != 0)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
			settle.setBuildingList(plugin.getRealmModel().getBuildings().getSubList(settleId));
			settle.initSettlement(plugin.getData().getPriceList());
		}

		RegionType rConfig = plugin.stronghold.getRegionManager().getRegionType(regionType);
		ArrayList<String> reagents = new ArrayList<String>();
		for (ItemStack item : rConfig.getReagents())
		{
			reagents.add(item.getType().name()+":"+item.getAmount());
		}
    	msg.add("BUILD "+bType.name()+" at "+(int)position.getX()+":"+(int)position.getY()+":"+(int)position.getZ());
    	msg.add("Building: "+building.getId() +"for owner "+ownerName);
    	msg.add("remember to give reagents to chest: ");
    	for (String s : reagents)
    	{
    		msg.add(s);
    	}
    	msg.add(" ");
    	
    	if (AchivementName.contains(regionType))
    	{
    		AchivementName aName = AchivementName.valueOf(regionType);
    		if (owner != null)
    		{
    			if (owner.getAchivList().contains(aName) == false)
    			{
	    			owner.getAchivList().add(new Achivement(AchivementType.BUILD, aName, true));
	    			plugin.getData().writeOwner(owner);
	    	    	msg.add(ChatColor.GOLD+"You earn Achivement "+regionType+" for building ");
    			}
    			AchivementName nextTech = plugin.getRealmModel().getKnowledgeData().getKnowledgeList().checkNextRank(owner.getAchivList());
    			if (nextTech != AchivementName.NONE)
    			{
	    			owner.getAchivList().add(new Achivement(AchivementType.BUILD, nextTech, true));
	    			plugin.getData().writeOwner(owner);
	    	    	msg.add(ChatColor.GOLD+"You earn Techlevel "+nextTech.name()+" for building ");
    			}
    		}
    	}
    	
		plugin.getMessageData().printPage(sender, msg, 1);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (settleId > 0)
		{
			if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
			{
				errorMsg.add(ChatColor.RED+"Wrong Settlement ID !");
				errorMsg.add(getDescription()[0]);
				return false;
			}
			if (isSettleOwner(plugin, sender, settleId) == false)
			{
				errorMsg.add("ChatColor.RED+You are not the Owner !");
				errorMsg.add(" ");
				return false;
			}
		}
		

		if (buildName == "")
		{
			errorMsg.add(ChatColor.RED+"Wrong BuildingType !");
			return false;
		}
		bType = BuildPlanType.getBuildPlanType(buildName);
		if (bType == BuildPlanType.NONE)
		{
			errorMsg.add(ChatColor.RED+"Wrong BuildPlanType !");
			errorMsg.add(ChatColor.RED+"NoName Default = WHEAT ");
			errorMsg.add(getDescription()[0]);
			return false;
		}
		// check for feudal buildings
		if (settleId > 0)
		{
			if ( BuildPlanType.getBuildGroup(bType) == 900)
			{
				errorMsg.add(ChatColor.RED+"Wrong BuildPlanType !");
				errorMsg.add(ChatColor.RED+"Lehen buildings not allowed ");
				errorMsg.add(getDescription()[0]);
				return false;
			}
		}
		Player player = (Player) sender;
		// check for realms achivement
		Owner owner = plugin.getRealmModel().getOwners().getOwner(player.getUniqueId().toString());
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"You are not a regular owner in REALMS !");
			return false;
		} else
		{
//			AchivementName aName = AchivementName.valueOf(bType.name());
			ArrayList<BuildPlanType> aList = new ArrayList<BuildPlanType>();
//			aList.add(owner.getAchivList());
			aList.addAll(plugin.getRealmModel().getKnowledgeData().getKnowledgeList().getPermissions(owner.getAchivList()));
			if (aList.contains(bType) == false)
			{
				errorMsg.add(ChatColor.RED+"You not have the required permission !");
				errorMsg.add(ChatColor.YELLOW+"Try /owner INFO  for check your achivements");
				errorMsg.add(ChatColor.YELLOW+"You need "+bType.name());
				return false;
			}
		}
		double cost = plugin.getServerData().getRegionTypeCost(buildName);
		if (plugin.economy.has(owner.getPlayerName(), cost) == false)
		{
			errorMsg.add(ChatColor.RED+"You not have the required money :"+cost);
			return false;
		}
//		System.out.println("Can Execute  TRUE");
		return true;
	}

}
