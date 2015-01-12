package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleCreate extends RealmsCommand
{
	private String name;
	private String sType;
	private SettleType settleType;
	private String playerName;
	
	public CmdSettleCreate()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.FOUNDING);
		description = new String[] {
				ChatColor.YELLOW+"/settle FOUNDING [settleType] [Name] ",
				"Create a Settlement from <settleType> with <NAME> ",
				"You must has the TechLevel for the settleType",
				"You MUST stay at the center of new Settlement!",
		    	"show a status report ",
		    	"  "
		};
		requiredArgs = 2;
		this.name = "";
		this.sType = "";
		this.settleType = SettleType.NONE;
		playerName = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
			sType = value;
		break;
		case 1 :
				name = value;
			break;
		default:
			break;
		}

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
		return new String[] {String.class.getName(), String.class.getName() };
	}
	
	
	private ArrayList<String> createSuperRegion(Realms plugin, Player player, String superRegionName,String regionTypeName, Location currentLocation)
	{
		ArrayList<String> msg = new ArrayList<String>();
		
		boolean isValid = true;
        SuperRegionType currentRegionType = plugin.stronghold.getRegionManager().getSuperRegionType(regionTypeName);
        if (currentRegionType != null)
        {
            double cost = currentRegionType.getMoneyRequirement();
            if (plugin.economy.has(player.getName(), cost)== false)
            {
            	msg.add(ChatColor.RED+"You have not enough money ");
            	msg.add(ChatColor.YELLOW+"You need "+ConfigBasis.setStrformat2(cost, 10));
            	isValid = false;
            }
        	
            Map<String, Integer> requirements = currentRegionType.getRequirements();
            HashMap<String, Integer> req = new HashMap<String, Integer>();
            for (String s : currentRegionType.getRequirements().keySet()) {
                req.put(new String(s), new Integer(requirements.get(s)));
            }
            
            //Check for required regions
            List<String> children = currentRegionType.getChildren();
            if (children != null) {
                for (String s : children) {
                    if (!req.containsKey(s))
                        req.put(new String(s), 1);
                }
            }
            int radius = (int) currentRegionType.getRawRadius();
            for (SuperRegion sr : plugin.stronghold.getRegionManager().getSortedSuperRegions()) 
            {
                try 
                {
                    if (sr.getLocation().distance(currentLocation) < radius + plugin.stronghold.getRegionManager().getSuperRegionType(sr.getType()).getRawRadius())
                    {
                    	if (children.contains(sr.getType())==false) 
                    	{
                    		msg.add(ChatColor.RED + "[HeroStronghold] " + sr.getName() + " is already here.");
                        	isValid = false;
                    	}
                    }
                } catch (IllegalArgumentException iae) {
                    
                }
            }
            Location loc = player.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            int radius1 = currentRegionType.getRawRadius();
            for (Region r : plugin.stronghold.getRegionManager().getSortedRegions()) 
            {	  	
                Location l = r.getLocation();
                if (l.getX() + radius1 < x) 
                {
                    break;
                }

                if (l.getX() - radius1 < x 
                		&& l.getY() + radius1 > y 
                		&& l.getY() - radius1 < y 
                		&& l.getZ() + radius1 > z 
                		&& l.getZ() - radius1 < z 
                		&& l.getWorld().equals(loc.getWorld()) 
                		&& req.containsKey(r.getType())) 
                {
                    if (req.get(r.getType()) < 2) {
                        req.remove(r.getType());
                    } else {
                        req.put(r.getType(), req.get(r.getType()) - 1);
                    }
                }
            }
            if (!req.isEmpty()) 
            {
                for (Region r : plugin.stronghold.getRegionManager().getContainingRegions(currentLocation)) 
                {
                    String rType = plugin.stronghold.getRegionManager().getRegion(r.getLocation()).getType();
                    if (req.containsKey(rType)) 
                    {
                        int amount = req.get(rType);
                        if (amount <= 1) 
                        {
                            req.remove(rType);
                        } else {
                            req.put(rType, amount - 1);
                        }
                    }
                }
                
            }
            if (!req.isEmpty()) 
            {
                msg.add(ChatColor.RED + "[HeroStronghold] This area doesnt have all of the required regions.");
                int j=0;
                for (String s : req.keySet()) 
                {
                	msg.add(ChatColor.YELLOW +s );
                }
            	isValid = false;
            }

            if (isValid)
            {
                Map<String, List<String>> members = new HashMap<String, List<String>>();
                List<String> owners = new ArrayList<String>();
                owners.add(playerName);
            	double balance = 1000.0;
				plugin.stronghold.getRegionManager().addSuperRegion(superRegionName, currentLocation, regionTypeName, owners, members, currentRegionType.getDailyPower(), balance );
            }
        }
		return msg;
	}

	private boolean cmdCreate(Realms plugin, CommandSender sender)
	{
		// /settle create [SuperRegion]
		int page = 1;
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		String superRegionName = "";
		superRegionName = this.name;
		Location centerPos = player.getLocation();
		

		msg.add("Settlement Create [ "+superRegionName+" ]");
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
		if (sRegion == null)
		{
			playerName = player.getName();
			msg.addAll(createSuperRegion(plugin, player, superRegionName, settleType.name(), centerPos));
			sRegion = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
			if (sRegion == null)
			{
				msg.add(ChatColor.GOLD+"[HeroStronghold} NOT find Superregion "+superRegionName+" : "+settleType.name()+ "");
				plugin.getMessageData().printPage(sender, msg, page);
				return false;
			} else
			{
				msg.add(ChatColor.GOLD+"[HeroStronghold} Superregion "+superRegionName+" : "+settleType.name()+ "succesful created");
			}
		} else
		{
			// hole owner aus Superegion
			playerName = sRegion.getOwners().iterator().next();
			if (playerName == "")
			{
				playerName = player.getName();
			}
			if (plugin.getData().getOwners().containsKey(playerName) == false)
			{
				playerName = ConfigBasis.NPC_0;
			}
		}
		boolean isNPC = false;
		Owner owner = plugin.getRealmModel().getOwners().getOwnerName(playerName);
		if (owner == null)
		{
			msg.add(ChatColor.RED+"Owner not found ");
			plugin.getMessageData().printPage(sender, msg, page);
			return false;
		}
		Biome biome = sRegion.getLocation().getWorld().getBlockAt(sRegion.getLocation()).getBiome();
		LocationData position = new LocationData(
				sRegion.getLocation().getWorld().getName(),
				sRegion.getLocation().getX(), 
				sRegion.getLocation().getY(),
				sRegion.getLocation().getZ());
		msg.add(ChatColor.GOLD+"SettlementOwner: "+playerName);
		Settlement settlement = new Settlement(owner.getId(), position,  settleType, superRegionName,biome); //, plugin.getRealmModel().getLogList());
		settlement.setOwner(owner);
		plugin.getRealmModel().getSettlements().addSettlement(settlement);

		msg.add("");
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
		{
//    		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
			BuildPlanType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
			if ((BuildPlanType.getBuildGroup(buildingType) < 900)
				&& (BuildPlanType.getBuildGroup(buildingType) >= 10)
				)
			{
				if (plugin.getData().getBuildings().containRegion(hsRegion) == false)
				{
					Building building = new Building(
						buildingType, 
						hsRegion, 
						new LocationData(
						sRegion.getLocation().getWorld().getName(),
						sRegion.getLocation().getX(), 
						sRegion.getLocation().getY(),
						sRegion.getLocation().getZ()),
						settlement.getId()
						);
					plugin.getRealmModel().getBuildings().addBuilding(building);
					plugin.getData().writeBuilding(building);
					System.out.println("[REALMS] Settle "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
				} else
				{
					Building building = plugin.getData().getBuildings().getBuildingByRegion(hsRegion);
					if ((building.getSettleId() == 0) && (building.getLehenId() == 0))
					{
						building.setSettleId(settlement.getId());
						plugin.getData().writeBuilding(building);
						System.out.println("[REALMS] Settle "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
					}
				}
			}
		}
		
		// minimum Warehouse on
		settlement.getWarehouse().depositItemValue("WHEAT",256 );
		settlement.getWarehouse().depositItemValue("BREAD",64 );
		settlement.getWarehouse().depositItemValue("WOOD_HOE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD_AXE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD_PICKAXE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("LOG",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("STICK",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("COBBLESTONE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue(Material.WOOD_DOOR.name(),3);
		settlement.getWarehouse().depositItemValue(Material.CHEST.name(),6);
		settlement.getWarehouse().depositItemValue(Material.WORKBENCH.name(),3);
		settlement.getWarehouse().depositItemValue(Material.FURNACE.name(),3);
		settlement.getWarehouse().depositItemValue(Material.WALL_SIGN.name(),3);
		settlement.getWarehouse().depositItemValue(Material.GOLD_NUGGET.name(),3);
		
		// initial setUp of the settlement
		settlement.getResident().setDefaultSettlerCount(settleType);
		settlement.getBank().depositKonto(1000.0, "ADMIN", settlement.getId());
		settlement.setBuildingList(plugin.getRealmModel().getBuildings().getSubList(settlement.getId()));
		settlement.initSettlement(plugin.getData().getPriceList());
		settlement.setWorkerToBuilding(settlement.getResident().getSettlerCount());


		plugin.getData().writeSettlement(settlement);
		
		msg.add("Owner   : "+settlement.getOwnerId());
		msg.add("Storage : "+settlement.getWarehouse().getItemMax());
		msg.add("Max Beds: "+settlement.getResident().getSettlerMax());
		msg.add("Settlers: "+settlement.getResident().getSettlerCount());
		msg.add("Building: "+settlement.getBuildingList().size());
		msg.add("Bank    : "+settlement.getBank().getKonto());
		msg.add("Biome   : "+biome);
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		cmdCreate( plugin,  sender);
	}
	
//	private String findSuperRegionAtLocation(Realms plugin, Player player)
//	{
//		Location position = player.getLocation();
//	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
//	    {
//	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
//	    	if (settleType != SettleType.NONE)
//	    	{
//	    		return sRegion.getName();
//	    	}
//	    }
//		return "";
//	}
	

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		boolean isReady = true;
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add(ChatColor.RED+"The Model is busy, try later  ");
			return false;
		}
		if (sType == "")
		{
			sType = "NONE";
		}
		settleType = SettleType.valueOf(sType);
		if (settleType == SettleType.NONE)
		{
			errorMsg.add(ChatColor.RED+"This is not a regular settleType!");
			errorMsg.add(ChatColor.YELLOW+"Try HAMLET, TOWN, CITY, METROPOLIS ");
			return false;
			
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
			ArrayList<SettleType> aList = new ArrayList<SettleType>();
			aList.addAll(plugin.getRealmModel().getKnowledgeData().getKnowledgeList().getSettlePermission(owner.getAchivList()));
			if (aList.contains(settleType) == false)
			{
				errorMsg.add(ChatColor.RED+"You not have the required Techlevel !");
				errorMsg.add(ChatColor.YELLOW+"Try /owner INFO  for check your achivements");
				return false;
			}
		}
		playerName = owner.getPlayerName(); 
		// fehlenden Parameter Name ersetzen
		if (this.name == "")
		{
			errorMsg.add(ChatColor.RED+"Name must be given !  ");
			return false;
		}
		// pruefe ob bereits ein Settlement mit dem Namen vorhanden ist
		if(plugin.getRealmModel().getSettlements().containsName(name))
		{
			errorMsg.add(ChatColor.RED+"Settlement already exist !"+ChatColor.YELLOW+" (see /settle LIST) ");
			errorMsg.add(ChatColor.RED+"Settlement ID "+plugin.getRealmModel().getSettlements().findName(name));
			return false;
		}
		
		// pruefe ob SupeRegion gueltig bzw. vorhanden ist
		if (plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			if (isSuperRegionOwner ( plugin, sender,  name ))
			{	
				isReady = true;
			} else
			{
				if (sender.isOp() == false)
				{
					errorMsg.add(ChatColor.RED+"You are not owner of existing Superregion!");
					return false;
				}
			}
			// pruefe settleType der SuperRegion und setze den internen wert
			this.settleType = plugin.getConfigData().superRegionToSettleType((plugin.stronghold.getRegionManager().getSuperRegion(name).getType()));
			if (settleType == SettleType.NONE)
			{
				errorMsg.add(ChatColor.RED+"Superregion already exist with different settlementType !");
				return false;
			}
		}
		return isReady;
		
	}

}
