package net.krglok.realms;

import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.manager.PlanMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

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
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		String name = "NewHaven";
		@SuppressWarnings("unused")
		String worldName = "SteamHaven"; 
		String path = plugin.getDataFolder().getAbsolutePath();
//		path = "D:\\Program Files\\BuckitTest\\plugins\\Realms";
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(name);
		if (sRegion == null)
		{
			plugin.getMessageData().errorRegion(sender, this.subCommand());
			return;
		}
		int radius = 64;
		PlanMap planMap = new PlanMap(name, radius);
		byte[][] cMap = planMap.getPlan();
		Location pos = new Location(sRegion.getLocation().getWorld(), sRegion.getLocation().getX(), sRegion.getLocation().getY(), sRegion.getLocation().getZ());
//		pos = sRegion.getLocation();

		double posX = pos.getX();
		double posY = pos.getY();
		double posZ = pos.getZ();
		System.out.println("World "+pos.getWorld().getName()+" / "+pos.getX()+":"+pos.getY()+":"+pos.getZ());
		System.out.println("2D Map of "+sRegion.getName()+"  "+sRegion.getType()+ " Sektor "+sektor);
		
		switch (sektor)
		{
		case 1: // sektor NordWest
			posX = pos.getX()-PlanMap.getPlanSize(radius);
			posY = pos.getY();
			posZ = pos.getZ()-PlanMap.getPlanSize(radius);
			break;
		case 2:	// sektor NordOst
			posX = pos.getX();
			posY = pos.getY();
			posZ = pos.getZ()-PlanMap.getPlanSize(radius);
			break;
		case 3:	// sektor suedOst
			posX = pos.getX();
			posY = pos.getY()-1;
			posZ = pos.getZ();
			break;
		case 4:	// sektor SudWest
			posX = pos.getX()-PlanMap.getPlanSize(radius);
			posY = pos.getY();
			posZ = pos.getZ();
			break;
		default :
			break;
		}
		pos.setY(posY);
		System.out.println("World "+pos.getWorld().getName()+" / "+posX+":"+posY+":"+posZ);

		for (int i = 0 ; i < PlanMap.getPlanSize(radius); i++)
		{
			for (int j = 0; j < PlanMap.getPlanSize(radius) ; j++)
			{
				pos.setX(posX+j);
				pos.setZ(posZ+i);
				cMap[i][j] = getBlockIdAt(pos);
				if ((i==radius) && (j==radius))
				{
					cMap[i][j] = (byte) 255;
				}
			}
		}
//		System.out.println("World "+pos.getWorld().getName()+" / "+pos.getX()+":"+pos.getZ());
		for (int i = 0; i < PlanMap.getPlanSize(radius); i++)
		{
			System.out.print(ConfigBasis.showPlanValue(cMap[i]));
		}
		PlanMap.writePlanData(name, radius, cMap, path, sektor);
		System.out.println("File "+path+":"+name);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
