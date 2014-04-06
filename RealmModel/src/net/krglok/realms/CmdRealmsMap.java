package net.krglok.realms;

import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.maps.PlanMap;
import net.krglok.realms.maps.ScanData;
import net.krglok.realms.maps.ScanResult;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class CmdRealmsMap extends RealmsCommand
{

	private int sektor;
	
	public CmdRealmsMap()
	{
		super (RealmsCommandType.REALMS, RealmsSubCommandType.BUILD);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [sektor]   ",
		    	" Make a 2D map of Settlement Region  ",
		    	" Characters are used as Block Identifyer ",
		    	" the Map is stored under the Settle Name ",
		    	" "
			};
			requiredArgs = 1;
		
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
				sektor = value;
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
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		String name = "NewHaven";
		@SuppressWarnings("unused")
		String worldName = "SteamHaven"; 
		String path = plugin.getDataFolder().getAbsolutePath();
//		path = "D:Program FilesBuckitTestpluginsRealms";
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(name);
		if (sRegion == null)
		{
			plugin.getMessageData().errorRegion(sender, this.subCommand());
			return;
		}
		PlanMap planMap = new PlanMap(name);
		ScanResult[][] cMap = planMap.getPlan();
		Location pos = new Location(sRegion.getLocation().getWorld(), sRegion.getLocation().getX(), sRegion.getLocation().getY(), sRegion.getLocation().getZ());
//		pos = sRegion.getLocation();

		double posX = pos.getX();
		double posY = pos.getY();
		double posZ = pos.getZ();
		System.out.println("World "+pos.getWorld().getName()+" / "+pos.getX()+":"+pos.getY()+":"+pos.getZ());
		System.out.println("2D Map of "+sRegion.getName()+"  "+sRegion.getType()+ " Sektor "+sektor);
		
		pos.setY(posY);
		System.out.println("World "+pos.getWorld().getName()+" / "+posX+":"+posY+":"+posZ);
		ScanResult scanResult = new ScanResult();
		ScanData data = new ScanData();
		for (int i = 0 ; i < 16; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				pos.setX(posX+j);
				pos.setZ(posZ+i);
				
				data.blockMat = pos.getBlock().getType();
				scanResult.getScanArray()[i][j] = data;
			}
		}
//		System.out.println("World "+pos.getWorld().getName()+" / "+pos.getX()+":"+pos.getZ());
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
