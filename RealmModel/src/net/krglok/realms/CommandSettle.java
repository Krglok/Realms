package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Command handling for the command /settle
 * every subCommand is a separate instance of abstract class  RealmsCommand
 * allactive commands must be in the cmdList
 * 
 * @author Windu
 *
 */
@Deprecated
public class CommandSettle
{

	private Realms plugin;
	RealmsCommand[] cmdList ;
	CommandParser parser ;

	public CommandSettle(Realms plugin)
	{
		this.plugin = plugin;
		cmdList = makeCommandList();
		parser = new CommandParser(cmdList);

	}
	
	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
			
		};
		return commandList;
	}
	
	public boolean run(CommandSender sender, String[] args)
	{
	    	ArrayList<String> msg = new ArrayList<String>();
	    	msg.add(ChatColor.GREEN+plugin.getName()+" Vers.: "+ plugin.getConfigData().getVersion()+" ");
	    	msg.add(ChatColor.YELLOW+"Status: "+ChatColor.GREEN+" ");
	    	msg.add(ChatColor.RED+"OOPS the command is wrong ! ");
			plugin.getMessageData().printPage(sender, msg, 1);
		return true;
	}
	
	


	
//	private boolean cmdInfo(CommandSender sender, CommandArg commandArg)
//	{
//		// /settle info {page} {ID}
//		ArrayList<String> msg = new ArrayList<String>();
//		int page = 1;
//		int id = 0;
//		if (commandArg.size() < 1)
//		{
//			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.ADD);
//			return true;
//		}
//		if (commandArg.size()>1)
//		{
//			page = CommandArg.argToInt(commandArg.get(0));
//			id = CommandArg.argToInt(commandArg.get(1));
//		} else
//		{
//			page = CommandArg.argToInt(commandArg.get(0));
//		}
//		
//		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED) 
//		{
//			if (id == 0)
//			{
//				for (Settlement settle : plugin.getRealmModel().getSettlements().getSettlements().values())
//				{
//					msg.add("Settlement Info "+settle.getId()+" : "+settle.getName());
//					msg.add("Storage  : "+settle.getWarehouse().getItemMax());
//					msg.add("Residence: "+settle.getResident().getSettlerMax());
//					msg.add("Settlers  : "+settle.getResident().getSettlerCount());
//					msg.add("Needed     : "+settle.getTownhall().getWorkerNeeded());
//					msg.add("Workers  : "+settle.getTownhall().getWorkerCount());
//					msg.add("Building : "+settle.getBuildingList().size());
//					msg.add("Bank     : "+((int) settle.getBank().getKonto()));
//					msg.add("Food : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
//					msg.add("Required Items "+settle.getRequiredProduction().size());
//					msg.add("====================== ");
//				}
//			} else
//			{
//				Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(id);
//				if (settle != null)
//				{
//					msg.add("Settlement Info "+settle.getId()+" : "+settle.getName());
//					msg.add("Residence: "+ChatColor.YELLOW+settle.getResident().getSettlerMax());
//					msg.add("Settlers  : "+ChatColor.GOLD+settle.getResident().getSettlerCount());
//					msg.add("Needed     : "+ChatColor.YELLOW+settle.getTownhall().getWorkerNeeded());
//					msg.add("Workers  : "+ChatColor.GOLD+settle.getTownhall().getWorkerCount());
//					msg.add("Happiness: "+ChatColor.GOLD+settle.getResident().getHappiness());
//					msg.add("Fertility   : "+settle.getResident().getFertilityCounter());
//					msg.add("Deathrate: "+ChatColor.RED+settle.getResident().getDeathrate());
//					msg.add("Bank       : "+ChatColor.GREEN+((int) settle.getBank().getKonto()));
//					msg.add("Storage   : "+settle.getWarehouse().getItemMax());
//					msg.add("Building   : "+settle.getBuildingList().size());
//					msg.add("Food      : WHEAT "+settle.getWarehouse().getItemList().getValue("WHEAT"));
//					msg.add("====================== ");
//					msg.add(ChatColor.ITALIC+"Required Items : "+settle.getRequiredProduction().size());
//					for (String itemRef : settle.getRequiredProduction().keySet())
//					{
//						Item item = settle.getRequiredProduction().getItem(itemRef);
//						msg.add(ChatColor.ITALIC+" -"+item.ItemRef()+" : "+item.value());
//					}
//				}
//			}
//		} else
//		{
//			msg.add("[Realm Model] NOT enabled or too busy");
//			msg.add("Try later again");
//		}
//		plugin.getMessageData().printPage(sender, msg, page);
//		return true;
//	}
	

	

	
//	private boolean cmdCreate(CommandSender sender, CommandArg commandArg)
//	{
//		// /settle create [SuperRegion]
//		int page = 1;
//		ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
//		String superRegionName = "";
//		if (commandArg.size() == 0)
//		{
//			plugin.getMessageData().errorArgs(sender, RealmsSubCommandType.CREATE);
//			msg.add("");
//		}
//		superRegionName = commandArg.get(0);
//		msg.add("Settlement Create [ "+superRegionName+" ]");
//		msg.add("Model : "+plugin.getRealmModel().getModelStatus());
//		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
//		{
//			msg.add("The model is note ready or too busy !");
//			msg.add("");
//			plugin.getMessageData().printPage(sender, msg, page);
//			return true;
//		}
//		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
//		if (sRegion == null)
//		{
//	    	msg.add("No Superregions found !");
//			msg.add("");
//			plugin.getMessageData().printPage(sender, msg, page);
//			return true;
//		}
//		if(plugin.getRealmModel().getSettlements().containsName(superRegionName))
//		{
//	    	msg.add("Superregion is already a Settlement !");
//			msg.add("");
//			plugin.getMessageData().printPage(sender, msg, page);
//			return true;
//		}
//		String playerName = "";
//		boolean isNPC = false;
//		Owner owner; 
//		if (sRegion.getOwners().size() == 0)
//		{
//			playerName = plugin.getRealmModel().getOwners().getOwners().get(0).getPlayerName();
//			isNPC = true;
//			owner = plugin.getRealmModel().getOwners().getOwners().get(0);
//		} else
//		{
//			for (String name : sRegion.getOwners())
//			{
//				if (playerName == "")
//				{
//					playerName = name;
//				}
//			}
//			owner = plugin.getRealmModel().getOwners().getOwner(playerName);
//    		if (owner == null)
//    		{
//    			owner = new Owner(playerName, isNPC);
//    		}
//		}
//		msg.add(" Owner: "+playerName);
//		SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
//		msg.add(" SettleType: "+settleType);
//		if (settleType == SettleType.SETTLE_NONE)
//		{
//			msg.add("wrong stronghold Type !!");
//			plugin.getMessageData().printPage(sender, msg, page);
//			msg.add("");
//			return true;
//		}
////		Settlement settlement = new Settlement(playerName, settleType, superRegionName);
//		plugin.getRealmModel().getSettlements().addSettlement(settlement);
//
//		msg.add("");
//		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
//		{
////    		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
//    		
//			int hsRegion = region.getID();
//			String hsRegionType = region.getType();
//    		BuildingType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
//			Building building = new Building(buildingType, hsRegion, hsRegionType, true);
//			Settlement.addBuilding(building, settlement);
//		}
//		// make not dynamic initialization
//		settlement.setSettlerMax();
//		settlement.setWorkerNeeded();
//
//		// minimum settler on create
//		settlement.getResident().setSettlerCount(settlement.getResident().getSettlerMax()/2);
//		settlement.getWarehouse().depositItemValue("WHEAT",settlement.getResident().getSettlerMax()*2 );
//		settlement.getWarehouse().depositItemValue("BREAD",settlement.getResident().getSettlerMax()*2 );
//		settlement.getWarehouse().depositItemValue("WOOD_HOE",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("WOOD_AXE",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("WOOD_PICKAXE",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("LOG",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("WOOD",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("STICK",settlement.getResident().getSettlerMax());
//		settlement.getWarehouse().depositItemValue("COBBLESTONE",settlement.getResident().getSettlerMax());
//		settlement.setWorkerToBuilding(settlement.getResident().getSettlerCount());
//
//		plugin.getData().writeSettlement(settlement);
//		
//		msg.add("Storage : "+settlement.getWarehouse().getItemMax());
//		msg.add("Capacity: "+settlement.getResident().getSettlerMax());
//		msg.add("Settlers: "+settlement.getResident().getSettlerCount());
//		msg.add("Building: "+settlement.getBuildingList().size());
//		msg.add("Bank    : "+settlement.getBank().getKonto());
//		msg.add("");
//		plugin.getMessageData().printPage(sender, msg, page);
//		return true;
//	}
	

//	private boolean cmdSettlement(CommandSender sender, CommandArg commandArg)
//	{
//		Player player = (Player) sender;
//		Location position = player.getLocation();
//		ArrayList<String> msg = new ArrayList<String>();
//		int page = CommandArg.argToInt(commandArg.get(0));
//		msg.add("Stronghold List [ "+" ]");
//		
//	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
//	    {
//    		msg.add(sRegion.getType()+" : "+ChatColor.YELLOW+sRegion.getName()+" : "+" Owner: "+sRegion.getOwners());
//    		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
//    		{
//        		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
//    		}
//    		msg.add("==");
//	    }
//	    if (msg.size() == 1)
//	    {
//	    	msg.add("No Superregions found !");
//	    }
//		plugin.getMessageData().printPage(sender, msg, page);
//		return true;
//	}
	
	
}
