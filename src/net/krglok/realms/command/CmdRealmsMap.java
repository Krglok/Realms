package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.Set;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.impl.MarkerAPIImpl.AreaMarkerUpdated;

public class CmdRealmsMap extends RealmsCommand
{

	private int sektor;
    private final String MARKER_SET = "markers";
    private final String AREA_SET = "areas";
    private final String FORMAT = "Settlement :";
	private int page; 
	
	public CmdRealmsMap()
	{
		super (RealmsCommandType.REALMS, RealmsSubCommandType.MAP);
		description = new String[] {
				ChatColor.YELLOW+"/realms MAP [page]   ",
		    	" Init the dynmap label for realms  ",
		    	" Settlements, Lehen  ",
		    	"  ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
		
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
		return new String[] {int.class.getName() };
	}

	
	
	private byte getBlockIdAt(Location pos)
	{
//		Location pos = new Location(plugin.getServer().getWorld(world), posX, posY, posZ);
		if (pos == null)
		{
			System.out.println("Wrong Position");
		}
		if (pos.getWorld().getName() == null)
		{
			System.out.println("Wrong World");
		}
		
		Block b = pos.getWorld().getBlockAt(pos);
		
		return  ConfigBasis.getBlockId(b); 
	}
	
	private void makeMap()
	{
		ItemStack itemStack = new ItemStack(Material.EMPTY_MAP);
		// need a maprenderer
//		Map 
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
    }

    private void initMarkerLehen(Realms plugin, MarkerSet markerSet, Lehen lehen)
    {
		String id  = "lehen"+String.valueOf(lehen.getId());
		String label = lehen.getName();
		String world = lehen.getPosition().getWorld();
		double getX  = lehen.getPosition().getX();
		double getY  = lehen.getPosition().getY()+20;
		double getZ  = lehen.getPosition().getZ();
		MarkerIcon icon = plugin.dynmap.getMarkerAPI().getMarkerIcon("tower"); 
		// init new marker 
		markerSet.createMarker(id, label, true, world, getX, getY, getZ, icon , false);
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
			boolean isFind = false;
			for (Settlement settle : plugin.getData().getSettlements().values())
			{
				isFind = false;
				for (AreaMarker area : markerSet.getAreaMarkers())
				{
					String id  = "settle"+String.valueOf(settle.getId());
					if (area.getLabel().equalsIgnoreCase(id))
					{
						isFind = true;
					}
				}
				if (isFind == false)
				{
//createAreaMarker(String id, String lbl, boolean markup, String world, double x[], double z[], boolean persistent);
					String id  = "settle"+String.valueOf(settle.getId());
					String arg1= settle.getName();
					boolean arg2= false;
					String arg3 = settle.getPosition().getWorld();
					double p1x = settle.getPosition().getX()+40.0; 
					double p1y = settle.getPosition().getZ()+40.0; 
					double p2x = settle.getPosition().getX()-40.0; 
					double p2y = settle.getPosition().getZ()-40.0; 
					double[] arg4 = new double[] {p1x,p2x};
					double[] arg5 = new double[] {p1y,p2y};
					boolean arg6 = false;
					AreaMarker area = markerSet.createAreaMarker(id, arg1, arg2, arg3, arg4, arg5, arg6);
					//setLineStyle(int weight, double opacity, int color);
					area.setLineStyle(2, 0.8, 0x00FF00);  //gruen
					area.setFillStyle(0.1, 0x00FF00);
				}
			}
			
			
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
	
				}

				for (Lehen lehen : plugin.getData().getLehen().values())
				{
					isFind = false;
					for (AreaMarker area : markerSet.getAreaMarkers())
					{
						String id  = "lehen"+String.valueOf(lehen.getId());
						if (area.getLabel().equalsIgnoreCase(id))
						{
							isFind = true;
						}
					}
					if (isFind == false)
					{
	//createAreaMarker(String id, String lbl, boolean markup, String world, double x[], double z[], boolean persistent);
						String id  = "lehen"+String.valueOf(lehen.getId());
						String arg1= lehen.getName();
						boolean arg2= false;
						String arg3 = lehen.getPosition().getWorld();
						double p1x = lehen.getPosition().getX()+200.0; 
						double p1y = lehen.getPosition().getZ()+200.0; 
						double p2x = lehen.getPosition().getX()-200.0; 
						double p2y = lehen.getPosition().getZ()-200.0; 
						double[] arg4 = new double[] {p1x,p2x};
						double[] arg5 = new double[] {p1y,p2y};
						boolean arg6 = false;
						AreaMarker area = markerSet.createAreaMarker(id, arg1, arg2, arg3, arg4, arg5, arg6);
						//setLineStyle(int weight, double opacity, int color);
						area.setLineStyle(2, 0.8, 0xFFFF00); //gelb
						area.setFillStyle(0.1, 0xFFFF00);
}
				}
				
				for (Lehen lehen : plugin.getData().getLehen().values())
				{
					String id  = "lehen"+String.valueOf(lehen.getId());
	                Marker m = null;
	                for (Marker exist : markerSet.getMarkers()) //markers.remove(id);
	                {	
	                	if (exist.getLabel().equalsIgnoreCase(lehen.getName()))
	                	{
	                		m = exist;
	                	}
	                }
	                if (m == null)
	                {
	                	initMarkerLehen(plugin, markerSet, lehen);
	                	msg.add("Label set "+lehen.getName());
	                }
	                else
	                {
	                	msg.add("Label exist ");
	                }
					
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
	}

}
