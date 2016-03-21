package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFeudalCreate extends RealmsCommand
{
	int page;
	private int level;
	private String name;
	private String playerName;
	private int parentId;
	
	public CmdFeudalCreate()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/feudal CREATE [Level] [lehenName] [playerName] [parentId]",
		    	"create a Lehen  of Level (1-4) ",
		    	"playerName = Owner ",
		    	"parentId = lehenId of the landlord ",
		    	"the Lehen get the kingdom of the creator ",
		    	ChatColor.YELLOW+"Player can only create in Kingdom 0 ",
		    	ChatColor.DARK_PURPLE+"The King can create in his own kingdom ",
		    	ChatColor.DARK_PURPLE+"and give the Lehen to one of his favorite "
			};
			requiredArgs = 3;
			level = 0;
			name = "";
			playerName = "";
			page = 1;
			parentId =0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			name = value;
			break;
		case 2:
			playerName = value;
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
		case 0:
			level = value;
			break;
		case 3:
			parentId = value;
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
		return new String[] {int.class.getName(), String.class.getName(), String.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);
		Kingdom kingdom =  plugin.getData().getKingdoms().getKingdom(owner.getKingdomId()); 

		String regionType = "LEHEN_"+String.valueOf(level);

		msg.add(ChatColor.YELLOW+"Create Lehen "+ChatColor.GREEN+regionType+ChatColor.YELLOW+"with name "+ChatColor.GREEN+ name);

		double cost = plugin.getServerData().getSuperTypeCost(regionType);
		plugin.economy.withdrawPlayer(player.getName(), cost);
		if (plugin.getData().getLehen().getKingdomRoot(kingdom.getId()) != null)
		{
			plugin.getData().getLehen().getKingdomRoot(kingdom.getId()).getBank().depositKonto(cost, "Create", 0);
			msg.add(ChatColor.GREEN+"The money is given to the king ");
		}

		double needExp = plugin.getServerData().getSuperTypeExp(regionType);
		int costExp = (int) (needExp * -1);
		player.giveExp(costExp);
		int exp = player.getTotalExperience();
		msg.add(ChatColor.YELLOW+"You payed experience "+ChatColor.GREEN+costExp +" rest "+exp);
		
		SettleType sType = SettleType.getSettleType(regionType);
        Map<String, List<String>> members = new HashMap<String, List<String>>();
        List<String> owners = new ArrayList<String>();
        owners.add(player.getName());
    	double balance = 1000.0;
        
    	SuperRegionType currentRegionType = plugin.stronghold.getRegionManager().getSuperRegionType(regionType);
        Location currentLocation = player.getLocation();
		if (plugin.stronghold.getRegionManager().addSuperRegion(name, currentLocation , regionType, owners, members, currentRegionType.getDailyPower(), balance ) == false)
		{
			msg.add(ChatColor.RED+"Superregion not created : "+name);
			System.out.println("SuperRegion not created ");
			System.out.println("Name : "+name);
			System.out.println("currentLocation : "+currentLocation.toString());
			System.out.println("regionType : "+regionType);
			System.out.println("owners : "+owners);
			System.out.println("members : "+members);
			System.out.println("Power : "+currentRegionType.getDailyPower());
			System.out.println("balance : "+balance);
			return;
		}

		NobleLevel nobleLevel = NobleLevel.valueOf(level);
		if (parentId == 0)
		{
			if (owner.getKingdomId() != 0)
			{
				Lehen parent = plugin.getData().getLehen().getKingdomRoot(owner.getKingdomId());
				if (parent != null)
				{
					parentId = parent.getId();
				}
			}
		}
		LocationData position = new LocationData(currentLocation.getWorld().getName(), currentLocation.getX(), currentLocation.getY(), currentLocation.getZ());
		Lehen lehen = new Lehen(owner.getKingdomId(), name, nobleLevel, sType, owner, parentId,position);
		plugin.getData().getLehen().addLehen(lehen);
		plugin.getData().writeLehen(lehen);
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(name);
		msg.add("");
		
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
		{
    		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
			BuildPlanType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
			if (BuildPlanType.getBuildGroup(buildingType) == 900)
			{
				Building building;
				// is region not in the building list , then create a building in buildinglist 
				if (plugin.getData().getBuildings().containRegion(hsRegion) == false)
				{
					building = new Building(
						buildingType, 
						hsRegion, 
						new LocationData(
						sRegion.getLocation().getWorld().getName(),
						sRegion.getLocation().getX(), 
						sRegion.getLocation().getY(),
						sRegion.getLocation().getZ()),
						0
						);
					building.setLehenId(lehen.getId());
					plugin.getRealmModel().getBuildings().addBuilding(building);
					lehen.getBuildingList().addBuilding(building);
					plugin.getData().writeBuilding(building);
					System.out.println("[REALMS] Lehen "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
				} else
				{
					building = plugin.getData().getBuildings().getBuildingByRegion(hsRegion);
					if (building.getLehenId() == 0)
					{
						building.setLehenId(lehen.getId());
						lehen.getBuildingList().addBuilding(building);
						plugin.getData().writeBuilding(building);
						System.out.println("[REALMS] Lehen "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
					}
				}
				// create noblefamily without children
				plugin.getData().makeNobleFamily(building, plugin.getData().getNpcName(), nobleLevel);
			}
		}

		plugin.getMessageData().printPage(sender, msg, page);

		level = 0;
		name = "";
		playerName = "";
		page = 1;
		parentId =0;

	}
	
	public boolean checkSuperChildren(Realms plugin, String regionTypeName, Location currentLocation)
	{
		SuperRegionType currentRegionType = plugin.stronghold.getRegionManager().getSuperRegionType(regionTypeName);
        if (currentRegionType != null)
        {
	        Map<String, Integer> requirements = currentRegionType.getRequirements();
	        HashMap<String, Integer> req = new HashMap<String, Integer>();
	        for (String s : currentRegionType.getRequirements().keySet()) 
	        {
	            req.put(new String(s), new Integer(requirements.get(s)));
	        }
	        
	        //Check for allowed childs
	        List<String> children = currentRegionType.getChildren();
	        if (children != null) 
	        {
	        	System.out.println("ChildrenList :"+regionTypeName);
	        	for (String s : children)
	        	{
	        		System.out.println(regionTypeName+":"+s);
	        	}
	        	int radius = (int) currentRegionType.getRawRadius();
		        for (SuperRegion sr : plugin.stronghold.getRegionManager().getSortedSuperRegions()) 
		        {
		            try 
		            {
	//                	System.out.println("distance children : "+sr.getLocation().distance(currentLocation));
		                if (sr.getLocation().distance(currentLocation) < radius + plugin.stronghold.getRegionManager().getSuperRegionType(sr.getType()).getRawRadius())
		                {
		                	System.out.println("distance : "+sr.getType()+":"+sr.getLocation().distance(currentLocation));
		                	if (children.contains(sr.getType()) == false) 
		                	{
			                	System.out.println("contain children : "+sr.getType()+":"+"false");
		                		errorMsg.add(ChatColor.RED + "[HeroStronghold] " + sr.getName() + " is already here.");
		                    	return false;
		                	}
		                }
		            } catch (IllegalArgumentException iae) {
		                
		            }
		        }
	        }
	        //Check for required regions
//            Location loc = player.getLocation();
            double x = currentLocation.getX();
            double y = currentLocation.getY();
            double z = currentLocation.getZ();
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
                		&& l.getWorld().equals(currentLocation.getWorld()) 
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
                errorMsg.add(ChatColor.RED + "[HeroStronghold] Some required regions not found:");
//                int j=0;
                for (String s : req.keySet()) 
                {
                	errorMsg.add(ChatColor.YELLOW +s );
                }
            	return false;
            }

        }
        return true;
	}

	
	public boolean checkOwnerPower(Realms plugin, Owner owner, int power)
	{
		int myPower = 0;
		for (Settlement settle : plugin.getData().getSettlements().getSubList(owner).values())
		{
			myPower = myPower + plugin.getServerData().getSuperRegionPower(settle.getName());
		}
		if (myPower < power)
		{
			return false;
		}
		
		return true;
	}
	
	public void checkForVasall(Lehen lehen)
	{
		
	}
	

	public void checkForSettlement(Realms plugin, Lehen lehen)
	{
		SuperRegionType currentRegionType = plugin.stronghold.getRegionManager().getSuperRegionType(lehen.getName());
        double x = lehen.getPosition().getX();
        double y = lehen.getPosition().getY();
        double z = lehen.getPosition().getZ();
        int radius1 = currentRegionType.getRawRadius();
        for (SuperRegion r : plugin.stronghold.getRegionManager().getSortedSuperRegions()) 
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
            		&& l.getWorld().getName().equals(lehen.getPosition().getWorld()
            				))  
            {
    			if (r.getType() == SettleType.HAMLET.name()
    				|| (r.getType() == SettleType.TOWN.name())
    				|| (r.getType() == SettleType.CITY.name())
    				|| (r.getType() == SettleType.METROPOLIS.name())	    				)
    				
    			{
    				String settleName = r.getName();
    				Settlement settle = plugin.getData().getSettlements().findName(settleName);
    				if (settle != null)
    				{
    					if (settle.getOwner().getId() == 0)
						{
							settle.setTributId(lehen.getId());
						}
    				}
    			}
            }
        }
		
	}
	
	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		if (playerName == "")
		{
			playerName = player.getName();
		}
		
		Owner owner = plugin.getData().getOwners().getOwnerName(playerName);
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"playerName is not a regular owner of REALMS");
			return false;
		}
		if (isOpOrAdmin(sender)  == false)
		{
//			if (owner.getLevel() != NobleLevel.KING)
//			{
//				if (owner.getKingdomId() > 0)
//				{
//					if (plugin.getData().getKingdoms().getKingdom(owner.getKingdomId()).isNPCkingdom() == false)
//					{
//						errorMsg.add(ChatColor.RED+"You are not a king and not in kindom_0");
//						return false;
//					}
//				}
//			}
		}
		if (name == "")
		{
			errorMsg.add(ChatColor.RED+"Wrong name ");
			return false;
			
		}
		String regionType = "LEHEN_"+String.valueOf(level);
		SettleType sType = SettleType.getSettleType(regionType);
		if (sType == SettleType.NONE)
		{
			errorMsg.add(ChatColor.RED+"Wrong level ");
			errorMsg.add(ChatColor.YELLOW+"Level from (1-4) valid");
			return false;
		}
		double exp = player.getTotalExperience();
		double needExp = plugin.getServerData().getSuperTypeExp(regionType); 
		if ( needExp> exp)
		{
			errorMsg.add(ChatColor.RED+"You not have enough xp ");
			errorMsg.add(ChatColor.YELLOW+"You need "+needExp + " > "+exp+" xp");
			return false;
		}
		double cost = plugin.getServerData().getSuperTypeCost(regionType);
		if (plugin.economy.has(player.getName(), cost) == false)
		{
			errorMsg.add(ChatColor.RED+"You not have enough money ");
			errorMsg.add(ChatColor.YELLOW+"You need "+cost );
			return false;
		}
		// pruefe ob bereits ein Settlement mit dem Namen vorhanden ist
		if(plugin.getData().getLehen().containsName(name))
		{
			errorMsg.add(ChatColor.RED+"Lehen already exist !"+ChatColor.YELLOW+" (see /feudal LIST) ");
			errorMsg.add(ChatColor.RED+"Lehen ID "+plugin.getData().getLehen().getLehen(name).getId()+" Kingdom:"+plugin.getData().getLehen().getLehen(name).getKingdomId());
			return false;
		}
		player.sendMessage("check contain superregion");
		// pruefe ob SupeRegion gueltig bzw. vorhanden ist
		if (plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			errorMsg.add(ChatColor.RED+"[HeroStronghold] Superregion already exist !");;
			return false;
		}
		player.sendMessage("check contain illegal children for "+regionType);
		if (checkSuperChildren(plugin, regionType, player.getLocation()) == false)
		{
			return false;
		}
		if (isOpOrAdmin(sender) == false)
		{
			int power = ConfigBasis.getCreateMinPower(sType);
			
			if (checkOwnerPower(plugin, owner, power))
			{
				errorMsg.add(ChatColor.RED+"[HeroStronghold] You need more Power !");;
				errorMsg.add(ChatColor.YELLOW+"Wait for regeneration or build new settlements !");;
				return false;
			}
		}
		return true;
	}

}
