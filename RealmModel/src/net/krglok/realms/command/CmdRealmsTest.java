package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

public class CmdRealmsTest extends RealmsCommand
{
    private final String MARKER_SET = "markers";
	private int page; 
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [page]   ",
		    	" show dynmap label  ",
		    	" "
			};
			requiredArgs = 1;
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
	
	private static SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if (sRegion != null)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}

	public static Region findRegionAtPosition(Realms plugin, Location position)
	{
	    for (Region region : plugin.stronghold.getRegionManager().getContainingRegions(position))
	    {
	    	if (region != null)
	    	{
	    		return region;
	    	}
	    }
		return null;
	}
	
//    private void cmdSignShop(Realms plugin, Block b, Player player)
//    {
//    	Location pos = b.getLocation();
//		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
//		if (sRegion != null)
//		{
//			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
//			if (settle != null)
//			{
//    	    	System.out.println("Realms setShop");
//    	    	plugin.setShopPrice(b.getLocation());
////    	    	plugin.setShop(player,b.getLocation(), settle);
////    	    	plugin.setShopPrice(pos);
//    	    }
//		}
//    }


    public void checkRegionBuilding(Realms plugin, CommandSender sender)
    {
		System.out.println("Realms Test ");
    	ArrayList<String> msg = new ArrayList<String>();
		int radius = 5;
		int edge = radius * 2 -1;
		msg.add("Region without Building ");
		msg.add("ID  | Location ");
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			if (plugin.getRealmModel().getBuildings().getBuildingByRegion(region.getID()) == null)
			{

				String s = region.getID()+" :"
					+region.getType()+" :"
					+region.getLocation().getWorld().getName()+" :"
					+(int) region.getLocation().getX()+" :"
					+(int) region.getLocation().getY()+" :"
					+(int) region.getLocation().getZ()+"";
				
					System.out.println("[REALMS] region ID "+s);
					msg.add(s);
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
    	
    }
    
    private void initMarkerSettle(Realms plugin, MarkerSet markerSet, Settlement settle)
    {
		String id  = "settle"+String.valueOf(settle.getId());
		String label = settle.getName();
		String world = settle.getPosition().getWorld();
		double getX  = settle.getPosition().getX();
		double getY  = settle.getPosition().getY()+20;
		double getZ  = settle.getPosition().getZ();
		MarkerIcon icon = plugin.dynmap.getMarkerAPI().getMarkerIcon("house"); 
		// init new marker 
		markerSet.createMarker(id, label, true, world, getX, getY, getZ, icon , false);
		
//		markerSet.createAreaMarker(id, label, true, world, arg4, icon, false);
    }

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		if (plugin.dynmap == null)
		{
			msg.add(ChatColor.RED+"Dynmap not found ! ");
			plugin.getMessageData().printPage(sender, msg, page);
			return;
		}

		msg.add(ChatColor.RED+"Realms Test ");

		msg.add("Dynmap Marker ");

		MarkerSet markerSet = plugin.dynmap.getMarkerAPI().getMarkerSet(MARKER_SET);
		{
			if (markerSet.getMarkerSetID().equalsIgnoreCase("markers"))
			{
				for (Settlement settle : plugin.getData().getSettlements().values())
				{
					String id  = "settle"+String.valueOf(settle.getId());
	                Marker m = null;
	                for (Marker exist : markerSet.getMarkers()) //markers.remove(id);
	                {	
	                	if (exist.getLabel().equalsIgnoreCase(settle.getName()))
	                	{
	                		m = exist;
	                	}
	                }
	                if (m == null)
	                {
	                	initMarkerSettle(plugin, markerSet, settle);
	                	msg.add("Label set "+settle.getName());
	                }
	                else
	                {
	                	msg.add("Label exist ");
	                }
	
//	     			msg.add(markerSet.getMarkerSetLabel()
//						+" :"+markerSet.getMarkers().size()
//						+" :"+markerSet.getMarkerSetID()
//						);
//					for (Marker marker : markerSet.getMarkers())
//					{
//						msg.add("+"+marker.getLabel()
//								+":"+marker.getMarkerIcon().getMarkerIconID()
//								+":"+marker.getWorld()
//								);
//					}
				}
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
//		if (sender instanceof Player)
//		{
//			return true;
//		}
//		errorMsg.add("Not a console command !");
//		errorMsg.add("The command must send by a Player !");
//		return false;
	}

}
