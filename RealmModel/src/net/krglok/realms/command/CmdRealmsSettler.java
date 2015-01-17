package net.krglok.realms.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;

public class CmdRealmsSettler extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsSettler( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.SETTLER);
		description = new String[] {
				ChatColor.YELLOW+"/realms SETTLER   ",
		    	" Create a Citizen NPC at the  ",
		    	" you look at. ",
		    	" The settlerType is chosen by regionType ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
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
		return new String[] {int.class.getName()  };
	}

	private void checkRegionChest(Realms plugin, ArrayList<String> msg)
	{
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			Block bs = region.getLocation().getBlock();
			if (bs.getType() != Material.CHEST)
			{
				String x = ConfigBasis.setStrformat2(region.getLocation().getX(),7);
				String y = ConfigBasis.setStrformat2(region.getLocation().getY(),7);
				String z = ConfigBasis.setStrformat2(region.getLocation().getZ(),7);
				String type = ConfigBasis.setStrleft(region.getType(), 12);
				msg.add(region.getID()+":"+ type +":"+ x + ":"+ y +":"+ z);
			}
		}
	}
	
	
    private void cmdSignShop(Realms plugin, Block b, Player player)
    {
    	Location pos = b.getLocation();
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
    	    	System.out.println("Realms setShop");
    	    	plugin.setShopPrice(b.getLocation());
//    	    	plugin.setShop(player,b.getLocation(), settle);
//    	    	plugin.setShopPrice(pos);
    	    }
		}
    }

    private boolean checkFullSize(Location pos)
    {
    	if (pos.getBlock().getType() == Material.AIR)
    	{
	    	if (pos.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
	    	{
	    		return true;
	    	}
    	}
    	return false;
    }
    
    private Location getNearFree(Location pos)
    {
    	Location spawnPos = new Location(pos.getWorld(),pos.getX(),pos.getY(),pos.getZ());
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.NORTH).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.NORTH).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.EAST).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.EAST).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.SOUTH).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.SOUTH).getLocation();
    	}
    	if (checkFullSize(pos.getBlock().getRelative(BlockFace.WEST).getLocation()))
    	{
    		return pos.getBlock().getRelative(BlockFace.WEST).getLocation();
    	}

    	return spawnPos;
    }

    
	private Location getFreeTarget(Location pos, Location eyelocation)
	{
		Vector vec = pos.getDirection();
		Location frontlocation = eyelocation.add(vec);
		return frontlocation.getWorld().getHighestBlockAt(frontlocation).getLocation(); //.getRelative(BlockFace.UP).getLocation();
	}
    
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Spawn Settler ");
    	ArrayList<String> msg = new ArrayList<String>();
		int radius = 5;
		int edge = radius * 2 -1;
		Player player = (Player) sender;
		Entity npc;
//		Location pos = new Location(player.getLocation().getWorld(), player.getLocation().getX()-radius, player.getLocation().getY(), player.getLocation().getZ()-radius);
		Block lookAt =  player.getTargetBlock(null, 6);  
		NpcData settleNpc = null;
		
		// location where you look at
		Location spawnPos;
//		Location pos = player.getTargetBlock(null, 6).getLocation();
//		pos.setY(pos.getY()+1);
//		if (pos.getBlock().getType() != Material.AIR)
//		{
//		spawnPos = getFreeTarget(player.getLocation(),player.getEyeLocation());
		spawnPos = getNearFree(player.getLocation());
			
		if (spawnPos.getBlock().getType() != Material.AIR)
		{
	    	msg.add(ChatColor.RED+"Sorry there is no free space for spawn ");
	    	return;
		} else
		{
			// in die mitte des Blocks verschieben
			spawnPos.setX(spawnPos.getX()+0.5);
			spawnPos.setZ(spawnPos.getZ()+0.5);
		}
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, player.getTargetBlock(null, 6).getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			Region region = findRegionAtPosition(plugin, player.getTargetBlock(null, 6).getLocation());
			if (region != null)
			{
				Building building = settle.getBuildingList().getBuildingByRegion(region.getID());
				if (building != null)
				{
					switch (region.getType())
					{
					case "HALL":
						settleNpc = settle.getResident().getNpcList().getNpcType(NPCType.MANAGER);
						if (settleNpc != null)
						{
							plugin.npcManager.createNPC(settleNpc, new LocationData(spawnPos.getWorld().getName(),spawnPos.getX(),spawnPos.getY(),spawnPos.getZ()));
					    	msg.add(ChatColor.GREEN+"NPC Citizen");
						} else
						{
					    	msg.add(ChatColor.RED+"NPC not found in Settlement "+settle.getName());
						}
						break;
					case "HOME":
						settleNpc = settle.getResident().getNpcList().getBuildingNpc(building.getId()).values().iterator().next();
						if (settleNpc != null)
						{
							plugin.npcManager.createNPC(settleNpc, new LocationData(spawnPos.getWorld().getName(),spawnPos.getX(),spawnPos.getY(),spawnPos.getZ()));
					    	msg.add(ChatColor.GREEN+"NPC Citizen");
						} else
						{
					    	msg.add(ChatColor.RED+"NPC not found in Settlement "+settle.getName());
						}
						break;
					case "HOUSE":
						settleNpc = settle.getResident().getNpcList().getBuildingNpc(building.getId()).values().iterator().next();
						if (settleNpc != null)
						{
							plugin.npcManager.createNPC(settleNpc, new LocationData(spawnPos.getWorld().getName(),spawnPos.getX(),spawnPos.getY(),spawnPos.getZ()));
					    	msg.add(ChatColor.GREEN+"NPC Citizen");
						} else
						{
					    	msg.add(ChatColor.RED+"NPC not found in Settlement "+settle.getName());
						}
						break;
					case "FARMHOUSE":
						settleNpc = settle.getResident().getNpcList().getBuildingNpc(building.getId()).values().iterator().next();
						if (settleNpc != null)
						{
							plugin.npcManager.createNPC(settleNpc, new LocationData(spawnPos.getWorld().getName(),spawnPos.getX(),spawnPos.getY(),spawnPos.getZ()));
					    	msg.add(ChatColor.GREEN+"NPC Citizen");
						} else
						{
					    	msg.add(ChatColor.RED+"NPC not found in Settlement "+settle.getName());
						}
						break;
					default:
						break;
					}
				} else
				{
					settleNpc = settle.getResident().getNpcList().getNoHomeNpc().values().iterator().next();
					if (settleNpc != null)
					{
						plugin.npcManager.createNPC(settleNpc, new LocationData(spawnPos.getWorld().getName(),spawnPos.getX(),spawnPos.getY(),spawnPos.getZ()));
				    	msg.add(ChatColor.GREEN+"NPC Citizen");
					} else
					{
				    	msg.add(ChatColor.RED+"BEGGAR not found in Settlement "+settle.getName());
					}
				}
			}
		} else
		{
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			return false;
		}
		return true;
	}


}
