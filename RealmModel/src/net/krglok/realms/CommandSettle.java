package net.krglok.realms;

import java.util.ArrayList;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.model.RealmSubCommandType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSettle
{

	private Realms plugin;
//	private HashMap<String, String> ;

	public CommandSettle(Realms plugin)
	{
		this.plugin = plugin;

	}
	
	public boolean run(CommandSender sender, String[] args)
	{
		CommandArg commandArg = new CommandArg(args);
		RealmSubCommandType subCommand = RealmSubCommandType.getRealmSubCommandType(commandArg.get(0));
		commandArg.remove(0);
		switch (subCommand)
		{
		case ADD:
			if ((sender.hasPermission(RealmsPermission.SETTLE.name())) || sender.isOp() ) 
			{
				if (commandArg.size() == 0)
				{
					plugin.getMessageData().errorArgs(sender, subCommand);
				} else
				{
					cmdAdd(sender, commandArg);
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case SET :
			if ((sender.hasPermission(RealmsPermission.ADMIN.name())) || sender.isOp() ) 
			{
				if (commandArg.size() == 0)
				{
					plugin.getMessageData().errorArgs(sender, subCommand);
				} else
				{
					cmdSet(sender, commandArg);
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case DEPOSIT :
			if ((sender.hasPermission(RealmsPermission.SETTLE.name())) || sender.isOp() ) 
			{
				if (commandArg.size() == 0)
				{
					plugin.getMessageData().errorArgs(sender, subCommand);
				} else
				{
					cmdDeposit(sender, commandArg);
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case SETTLEMENT:
			if ((sender.hasPermission(RealmsPermission.SETTLE.name())) || sender.isOp() ) 
			{
				if (commandArg.size() == 0)
				{
					commandArg.add("1");
					cmdSettlement(sender, commandArg);
				} else
				{
					cmdSettlement(sender, commandArg);
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case CREATE:
			if ((sender.hasPermission(RealmsPermission.SETTLE.name())) || sender.isOp() ) 
			{
				cmdCreate(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case INFO :
			if ((sender.hasPermission(RealmsPermission.SETTLE.name())) || sender.isOp() ) 
			{
				cmdInfo(sender, commandArg);
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			break;
		case LIST :
			if ((sender.hasPermission(RealmsPermission.USER.name())) ) 
			{
				if (commandArg.size() > 0)
				{
					cmdList(sender, commandArg);
					return true;
				} else
				{
					commandArg.add("1");
					cmdList(sender, commandArg);
					return true;
				}
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
			
		case HELP:
			if ((sender.hasPermission(RealmsPermission.USER.name())) ) 
			{
				cmdHelp(sender, commandArg, subCommand);
				return true;
			} else
			{
				plugin.getMessageData().errorPermission(sender);
			}
		default :
			cmdHelp(sender, commandArg, subCommand);
			return false;
		}
		return true;
	}
	
	private boolean cmdInfo(CommandSender sender, CommandArg commandArg)
	{
		// /settle info {page} {ID}
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		int id = 0;
		if (commandArg.size()>0)
		{
			page = CommandArg.argToInt(commandArg.get(0));
		}
		if (commandArg.size()>1)
		{
			page = CommandArg.argToInt(commandArg.get(0));
			id = CommandArg.argToInt(commandArg.get(1));
		}
		sender.sendMessage("Page: "+page+ "  /   ID: "+id);
		
		
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
		{
			if (id == 0)
			{
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
					msg.add("Building : "+settle.getBuildingList().size());
					msg.add("Bank     : "+settle.getBank().getKonto());
					msg.add("Food : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
					msg.add("Required Items "+settle.getRequiredProduction().size());
					for (String itemRef : settle.getRequiredProduction().keySet())
					{
						Item item = settle.getRequiredProduction().getItem(itemRef);
						msg.add(item.ItemRef()+" : "+item.value());
					}
				}
			} else
			{
				Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(id);
				if (settle != null)
				{
					msg.add(settle.getId()+" : "+settle.getName());
					msg.add("Storage  : "+settle.getWarehouse().getItemMax());
					msg.add("Capacity : "+settle.getResident().getSettlerMax());
					msg.add("Settlers : "+settle.getResident().getSettlerCount());
					msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
					msg.add("Happiness: "+settle.getResident().getHappiness());
					msg.add("Fertility: "+settle.getResident().getFertilityCounter());
					msg.add("Deathrate: "+settle.getResident().getDeathrate());
					msg.add("Building : "+settle.getBuildingList().size());
					msg.add("Bank     : "+settle.getBank().getKonto());
					msg.add("Food : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
					msg.add("Required Items "+settle.getRequiredProduction().size());
					for (String itemRef : settle.getRequiredProduction().keySet())
					{
						Item item = settle.getRequiredProduction().getItem(itemRef);
						msg.add(item.ItemRef()+" : "+item.value());
					}
				}
			}
		} else
		{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	private boolean cmdAdd(CommandSender sender, CommandArg commandArg)
	{
//		msg.add("/settle add [SettleID] [RegionId] ");
		ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
		int page = 1;
//		int regionId = 0;
		Region region = null;
		Settlement settle = null;
		
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.ADD);
			return true;
		}

		int settleID = CommandArg.argToInt(commandArg.get(0));
		settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			plugin.getMessageData().errorSettleID(sender, RealmSubCommandType.ADD);
			return true;
		}
		if (commandArg.size() > 1)
		{
			//get region by number
			int value = CommandArg.argToInt(commandArg.get(1));
			sender.sendMessage("Region suchen : "+value);
			region = plugin.stronghold.getRegionManager().getRegionByID(value);
			if (region == null)
			{
				msg.add("/settle add [settleID] [regionID] ");
				msg.add("Stronghold region not found : [ "+value+" ]");
			}
			msg.add("Add stronghold Region [ "+value+" ]");
		} else
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.ADD);
//			// get region by location
//			Location position = player.getLocation();
//			region = plugin.stronghold.getRegionManager().getRegion(position);
//			if (region == null)
//			{
//				msg.add("/settle add [settleID] [regionID] ");
//				msg.add("NO Stronghold region at this location !");
//			}
//			msg.add("Add stronghold Region from this location");
		}
		if (region != null)
		{
			sender.sendMessage("Region found : "+region.getID());
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
			BuildingType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
			Building building = new Building(buildingType, hsRegion, hsRegionType, true);
			if (Settlement.addBuilding(building, settle))
			{
				msg.add("Settlement ["+settle.getId()+"]  "+settle.getName());
				msg.add("Storage : "+settle.getWarehouse().getItemList().getItemCount());
				msg.add("Capacity: "+settle.getResident().getSettlerMax());
				msg.add("Settlers: "+settle.getResident().getSettlerCount());
				msg.add("Building: "+settle.getBuildingList().size());
				msg.add("Bank    : "+settle.getBank().getKonto());
				msg.add("");
			} else
			{
				msg.add("/settle add [settleID] [regionID] ");
				msg.add("NO Building was added, unknown error !");
			}
		} else
		{
			plugin.getMessageData().errorRegion(sender, RealmSubCommandType.ADD);
		}
		plugin.getMessageData().printPage(sender, msg, page);
		
		return true;
	}

	
	private boolean cmdSet(CommandSender sender, CommandArg commandArg)
	{
//		msg.add("/settle set [SettleID] [SETTLER] [amount] ");
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		if (commandArg.size() < 3)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.SET);
			return true;
		}

		int settleID = CommandArg.argToInt(commandArg.get(0));
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			plugin.getMessageData().errorSettleID(sender, RealmSubCommandType.SET);
			return true;
		}

		String bName = commandArg.get(1);
		if (bName.equalsIgnoreCase("settler"))
		{
			int value = CommandArg.argToInt(commandArg.get(2));
			settle.getResident().setSettlerCount(value);
			msg.add("Settlement SET Settler to [ "+value+" ]");
		}else
		{
			plugin.getMessageData().errorArgWrong(sender, RealmSubCommandType.SET);
			return true;
		}
		msg.add("Settlement ["+settle.getId()+"]  "+settle.getName());
		msg.add("Storage : "+settle.getWarehouse().getItemList().getItemCount());
		msg.add("Capacity: "+settle.getResident().getSettlerMax());
		msg.add("Settlers: "+settle.getResident().getSettlerCount());
		msg.add("Building: "+settle.getBuildingList().size());
		msg.add("Bank    : "+settle.getBank().getKonto());
		msg.add("");
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		return true;
	}
	
	
	private boolean cmdDeposit(CommandSender sender, CommandArg commandArg)
	{
//		msg.add("/settle deposit [SettleID] [WAREHOUSE/BANK] [amount] {ItemRef} ");
		Player player = (Player) sender;
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		if (commandArg.size() < 3)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.DEPOSIT);
			return true;
		}

		int settleID = CommandArg.argToInt(commandArg.get(0));
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			plugin.getMessageData().errorSettleID(sender, RealmSubCommandType.DEPOSIT);
			return true;
		}

		String bName = commandArg.get(1);
		if (bName.equalsIgnoreCase("warehouse"))
		{
			if (commandArg.size() < 4)
			{
				plugin.getMessageData().errorArgs(sender, RealmSubCommandType.DEPOSIT);
				return true;
			}
			
			int value = CommandArg.argToInt(commandArg.get(3));
			String itemRef = commandArg.get(2);
			settle.getWarehouse().depositItemValue(itemRef, value);
			value = settle.getWarehouse().getItemList().getValue(itemRef);
			msg.add("Settlement Warehouse Deposit [ "+itemRef+":"+value+" ]");
		} else
		{
			if (bName.equalsIgnoreCase("bank"))
			{
				double value = CommandArg.argToDouble(commandArg.get(2));
				settle.getBank().depositKonto(value, player.getName());
				msg.add("Settlement Bank Deposit [ "+value+" ]");
			}else
			{
				plugin.getMessageData().errorArgWrong(sender, RealmSubCommandType.DEPOSIT);
				return true;
			}
		}
		msg.add("Storage : "+settle.getWarehouse().getItemList().getItemCount());
		msg.add("Capacity: "+settle.getResident().getSettlerMax());
		msg.add("Settlers: "+settle.getResident().getSettlerCount());
		msg.add("Building: "+settle.getBuildingList().size());
		msg.add("Bank    : "+settle.getBank().getKonto());
		msg.add("");
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		return true;
	}
	

	
	private boolean cmdCreate(CommandSender sender, CommandArg commandArg)
	{
		// /settle create [SuperRegion]
		int page = 1;
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		String superRegionName = "";
		if (commandArg.size() == 0)
		{
			plugin.getMessageData().errorArgs(sender, RealmSubCommandType.CREATE);
		}
		superRegionName = commandArg.get(0);
		msg.add("Settlement Create [ "+superRegionName+" ]");
		msg.add("Model : "+plugin.getRealmModel().getModelStatus());
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			msg.add("The model is note ready or too busy !");
			plugin.getMessageData().printPage(sender, msg, page);
			return true;
		}
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
		if (sRegion == null)
		{
	    	msg.add("No Superregions found !");
			plugin.getMessageData().printPage(sender, msg, page);
			return true;
		}
		if(plugin.getRealmModel().getSettlements().containsName(superRegionName))
		{
	    	msg.add("Superregion is already a Settlement !");
			plugin.getMessageData().printPage(sender, msg, page);
			return true;
		}
		String playerName = "";
		boolean isNPC = false;
		Owner owner; 
		if (sRegion.getOwners().size() == 0)
		{
			playerName = plugin.getRealmModel().getOwners().getOwners().get(0).getPlayerName();
			isNPC = true;
			owner = plugin.getRealmModel().getOwners().getOwners().get(0);
		} else
		{
			for (String name : sRegion.getOwners())
			{
				if (playerName == "")
				{
					playerName = name;
				}
			}
			owner = plugin.getRealmModel().getOwners().getOwner(playerName);
    		if (owner == null)
    		{
    			owner = new Owner(playerName, isNPC);
    		}
		}
		msg.add(" Owner: "+playerName);
		SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
		msg.add(" SettleType: "+settleType);
		if (settleType == SettleType.SETTLE_NONE)
		{
			msg.add("wrong stronghold Type !!");
			plugin.getMessageData().printPage(sender, msg, page);
			return true;
		}
		Settlement settlement = new Settlement(playerName, settleType, superRegionName);
		plugin.getRealmModel().getSettlements().addSettlement(settlement);
		
		msg.add("");
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
		{
//    		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
    		BuildingType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
			Building building = new Building(buildingType, hsRegion, hsRegionType, true);
			Settlement.addBuilding(building, settlement);
		}
		// make not dynamic initialization
		settlement.setSettlerMax();
		settlement.setWorkerNeeded();
		settlement.setWorkerToBuilding(settlement.getResident().getSettlerCount());
		// minimum settler on create
		settlement.getResident().setSettlerCount(settlement.getResident().getSettlerMax()/2);
		settlement.getWarehouse().depositItemValue("WHEAT",settlement.getResident().getSettlerMax()*2 );
		settlement.getWarehouse().depositItemValue("WOOD_HOE",settlement.getResident().getSettlerMax());

		msg.add("Storage : "+settlement.getWarehouse().getItemMax());
		msg.add("Capacity: "+settlement.getResident().getSettlerMax());
		msg.add("Settlers: "+settlement.getResident().getSettlerCount());
		msg.add("Building: "+settlement.getBuildingList().size());
		msg.add("Bank    : "+settlement.getBank().getKonto());
		msg.add("");
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	private boolean cmdList(CommandSender sender, CommandArg commandArg)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
	    SettlementList  rList = plugin.getRealmModel().getSettlements();
	    if (rList != null)
	    {
			msg.add("ID |Settlement | Active | Owner [ "+rList.getSettlements().size()+" ]");
		    for (Settlement settle : rList.getSettlements().values())
		    {
	    		msg.add(settle.getId()+" : "+ChatColor.YELLOW+settle.getName()+" : "+ChatColor.GOLD+settle.isEnabled()+" Owner: "+settle.getOwner());
		    }
	    } else
	    {
			msg.add("ID |Settlement | Active | Owner [  ]");
	    	msg.add("/settle list ");
	    	msg.add("NO settlements found !!");
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}

	private boolean cmdSettlement(CommandSender sender, CommandArg commandArg)
	{
		Player player = (Player) sender;
		Location position = player.getLocation();
		ArrayList<String> msg = new ArrayList<String>();
		int page = CommandArg.argToInt(commandArg.get(0));
		msg.add("Stronghold List [ "+" ]");
		
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
    		msg.add(sRegion.getType()+" : "+ChatColor.YELLOW+sRegion.getName()+" : "+" Owner: "+sRegion.getOwners());
    		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
    		{
        		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		}
    		msg.add("==");
	    }
	    if (msg.size() == 1)
	    {
	    	msg.add("No Superregions found !");
	    }
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	private boolean cmdHelp(CommandSender sender, CommandArg commandArg, RealmSubCommandType subCommand)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1; //CommandArg.argToInt(commandArg.get(0));
		if (commandArg.size() > 0)
		{
			subCommand = RealmSubCommandType.getRealmSubCommandType(commandArg.get(0));
		}

		switch (subCommand)
		{
		case SETTLEMENT:
			msg.add("[Realms] Command: Settle                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle settlement , check for settlement ");
			msg.add("at your position to create");
			msg.add("Show a list of Superregions and Regions");
			msg.add("at your position");
			msg.add("");
			msg.add("Run only if no settlement exist at the ");
			msg.add("position");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		case BUILDING :
			msg.add("[Realms] Command: Settle                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		case CREATE:
			msg.add("[Realms] Command: Realm                  ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/realm create [realmName] {ownerName} ");
			msg.add("creates a new Realm. the Realmname must be");
			msg.add("set. Ownername (the king) is optional ");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		case INFO :
			msg.add("[Realms] Command: Settle                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/realm info [realmName] ");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		case DEPOSIT :
			msg.add("[Realms] Command: Settle                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle deposit [ID] [BANK] [amount]  ");
			msg.add("add amount to bank of the settlement");
			msg.add("");
			msg.add("/settle deposit [ID] [WAREHOUSE] [ItemRef] [amount]");
			msg.add("add amount of itemRef to the warehose");
			msg.add("itemRef must be a blockname");
			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		case LIST :
			msg.add("[Realms] Command: Settle                ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle list {page} , show list of ");
			msg.add("settlements");
			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		case ADD :
			msg.add("[Realms] Command: Settle                 ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle add [ID] [RegionID]");
			msg.add("add the region to the settlement");
			msg.add("You must be owner of region and settlement");
			msg.add("or be Admin ");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			msg.add("");
			break;
		case DELETE:
			msg.add("[Realms] Command: Settle                 ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle delete [SettleID] [RegionID]");
			msg.add("remove the region from the settlement");
			msg.add("You must be owner of settlement");
			msg.add("or be Admin ");
			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		case SET :
			msg.add("[Realms] Command: Settle                 ");
			msg.add("SubCommands: "+subCommand);
			msg.add("/settle set [SettleID] [SETTLER] [amount] ");
			msg.add("set the amount of settlers to the");
			msg.add("settlement");
			msg.add("you must be Admin to use");
			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		case HELP:
//			msg.add("[Realms] Command: Settle                 ");
//			msg.add("SubCommands: "+subCommand);
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
//			break;
		
		default :
			msg.add("[Realms] Command: Settle                 ");
	    	msg.add("Parameter  [] = required  {} = optional ");
			msg.add("/settle add [ID] [RegionID]");
			msg.add("/settle create ......");
			msg.add("/settle deposit [ID] [BANK] [amount]  ");
			msg.add("/settle deposit [ID] [WAREHOUSE] [ItemRef] [amount]");
			msg.add("/settle delete  <not implemented>");
			msg.add("/settle info {page} {ID} ,show overview realm");
			msg.add("/settle list {page} , list the settlements");
			msg.add("/settle set [SettleID] [SETTLER] [amount] ");
			msg.add("/settle help {SubCommand} , show help ");
			msg.add("for SubCommand");
			msg.add("");
//			msg.add("");
//			msg.add("");
//			msg.add("");
			break;
		}

		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
}
