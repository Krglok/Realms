package net.krglok.realms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.minecraft.server.v1_7_R1.Facing;

/**
 * <pre>
 * realize the livecycle of the RealmModel
 * normally the tick is 1 second
 * 
 * @author oduda
 *</pre>
 */
public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
    private static long counter = 0;
    private static boolean isProduction = false;
    private static int prodLimit = (int) ConfigBasis.GameDay;
    private static int taxCounter = 0;
    private static int taxLimit = prodLimit * 10;
    private static int buildMin = prodLimit * 4 / 10;
    private static int buildMax = prodLimit * 7 / 10;

	private static final long DAYTIME = 0;
	private static final long SUNSET = 12000;
	private static final long NIGHTTIME = 14000;
	private static final long SUNRISE = 22000;
	private static final long TOMORROW = 24000;
    
	private boolean isGateClose = false;
	private boolean isGateOpen = false;
	
    public static int getProdCounter()
	{
		return prodLimit;
	}

	public TickTask(Realms plugin)
	{
		this.plugin = plugin;
		counter = 0;
	}
    
	public static void setIsProduction(boolean value)
	{
		TickTask.isProduction = value;
	}
	
	public static long getCounter()
	{
		return counter;
	}
	
	public static void setCounter(long l)
	{
		counter = l;
	}
	
	public static void setProdLimit(int value)
	{
		TickTask.prodLimit = value;
	}
	
	public static boolean isProduction()
	{
		return TickTask.isProduction;
	}
	
	
	@Override
	public void run()
	{
		counter++;
		taxCounter++;
		// starte speichern der Settlement vor onTick
		if (counter > prodLimit)
		{
			counter = 0;
		}
		
		plugin.getRealmModel().OnTick();
//		System.out.println("[Realms] Tick "+counter);
//		if ((counter > buildMin) && (counter < buildMax))
//		{
//			System.out.println("BuildRequest");
			plugin.onBuildRequest();
			plugin.onCleanRequest();
//		}
			plugin.onSignRequest();
		// startt night Event
		if( (plugin.getServer().getWorlds().get(0).getTime() >= 13000 )
			&& (plugin.getServer().getWorlds().get(0).getTime() < 17000 ))
		{
//			System.out.print("Night:"+plugin.getServer().getWorlds().get(0).getTime());
			if (isGateClose == false)
			{
				plugin.getLog().info("[Realms] Gate Night Event");
				doGateClose();
				isGateClose = true;
				isGateOpen = false;
			}
			plugin.getRealmModel().OnNight(plugin.getServer().getWorlds().get(0).getTime());
			
		} else
		{
			isGateClose = false;
		}
		// start Day Event
		if( (plugin.getServer().getWorlds().get(0).getTime() >= 23000))
		{
//			System.out.print("Day:"+plugin.getServer().getWorlds().get(0).getTime());
			if (isGateOpen == false)
			{
				plugin.getLog().info("[Realms] Gate DAY Event");
				doGateOpen();
				isGateOpen = true;
				isGateClose = false;
			}
			plugin.getRealmModel().OnDay(plugin.getServer().getWorlds().get(0).getTime());
		} else
		{
			isGateOpen = false;
		}
			
		// trader run
		if (counter == (prodLimit/2))
		{
			if (isProduction)
			{
				plugin.getRealmModel().OnTrade();
				plugin.getLog().info("[Realms] Trader calculation");
			}

		}
		
		//production run
		if (counter == prodLimit)
		{
			counter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnProduction();
				plugin.getLog().info("[Realms] production calculation");
//				System.out.println("[Realms] Production");
//				plugin.getLog().info("Tax counter "+taxCounter);
			} else
			{
				System.out.println("[Realms] Production "+isProduction);
				
			}
			
		} 
		
		//Tax run
		if(taxCounter >= taxLimit)
		{
			taxCounter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnTax();
				plugin.getLog().info("[Realms] Tax calculation");
				System.out.println("[Realms] Tax");
			}
		}
	}

	/**
	 * not in use now 
	 */
	private void doGateClose()
	{
		plugin.getServer().broadcastMessage("Gates now Closed");
		for (Settlement settle : plugin.getRealmModel().getSettlements().getSettlements().values())
		{
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				if (building.getBuildingType() == BuildPlanType.GATE)
				{
					Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
					if (region != null)
					{
						System.out.println("[REALMS] Close gate");
						setGateFrame(region.getLocation(), Material.FENCE);
					}
				}
			}
		}
	}

	private void setGateFrame(Location pos, Material gateMat)
	{
		Block base = pos.getBlock();
		Block gate;
		gate = base.getRelative(BlockFace.DOWN, 1);
		gate.setType(gateMat);
		gate.getRelative(BlockFace.EAST, 1).setType(gateMat);
		gate.getRelative(BlockFace.WEST , 1).setType(gateMat);
		gate.getRelative(BlockFace.NORTH, 1).setType(gateMat);
		gate.getRelative(BlockFace.SOUTH, 1).setType(gateMat);
		gate = base.getRelative(BlockFace.DOWN, 2);
		gate.setType(gateMat);
		gate.getRelative(BlockFace.EAST, 1).setType(gateMat);
		gate.getRelative(BlockFace.WEST, 1).setType(gateMat);
		gate.getRelative(BlockFace.NORTH, 1).setType(gateMat);
		gate.getRelative(BlockFace.SOUTH, 1).setType(gateMat);
		
	}
	
	
	/**
	 * not in use now 
	 */
	private void doGateOpen()
	{
		plugin.getServer().broadcastMessage("Gates now Open");
		for (Settlement settle : plugin.getRealmModel().getSettlements().getSettlements().values())
		{
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				if (building.getBuildingType() == BuildPlanType.GATE)
				{
					Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
					if (region != null)
					{
						System.out.println("[REALMS] Open gate");
						setGateFrame(region.getLocation(), Material.AIR);
					}
				}
			}
		}
	}
	
}
