package net.krglok.realms;

import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
//    private static long counter = 0;
    private static boolean isProduction = false;
    private static int prodLimit = (int) ConfigBasis.GameDay;
    private static int taxCounter = 0;
    private static int taxLimit = 30;
    private static int buildMin = prodLimit * 4 / 10;
    private static int buildMax = prodLimit * 7 / 10;

	private static final long DAYTIME = 0;
	private static final long SUNSET = 12000;
	private static final long NIGHTTIME = 14000;
	private static final long SUNRISE = 22000;
	private static final long TOMORROW = 24000;
    
//	private boolean isGateClose = false;
//	private boolean isGateOpen = false;
	
//	private HashMap<String,Integer> taxCounter = new  HashMap<String,Integer>();
	private HashMap<String,Boolean> isGateClose = new  HashMap<String,Boolean>();
	private HashMap<String,Boolean> isGateOpen = new  HashMap<String,Boolean>();
	
    public static int getProdCounter()
	{
		return prodLimit;
	}

	public TickTask(Realms plugin)
	{
		this.plugin = plugin;
		// init world list
		for (World world :plugin.getServer().getWorlds())
		{
			isGateClose.put(world.getName(), false);
			isGateOpen.put(world.getName(), false);
//			taxCounter.put(world.getName(), 0);
//			System.out.println("[REALMS] world "+world.getName());
		}
	}
    
	public static void setIsProduction(boolean value)
	{
		TickTask.isProduction = value;
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
		doDynMapUpdate();
		
		// dont run until init done
		if (plugin.getRealmModel().isInit() == false)
		{
			return;
		} else
		{
			if (plugin.npcManager.isSpawn() == false) return;
			if (plugin.unitManager.isSpawn() == false) return;
			if (plugin.nobleManager.isSpawn() == false) return;
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			return;
		}
		
		
		plugin.getRealmModel().OnTick();
		plugin.doBuildRequest(); // colonist build
		plugin.doCleanRequest();
		plugin.doSignRequest();
		plugin.doScanRequest();
		// start night Event
			
		for (World world : plugin.getServer().getWorlds())
		{
			if (world.getEnvironment() != Environment.THE_END)
			{
				if( (world.getTime() >= 13000 )
					&& (world.getTime() < 17000 ))
				{
		//			System.out.print("Night:"+plugin.getServer().getWorlds().get(0).getTime());
					if (isGateClose.containsKey(world.getName()) == true)
					{
						if (isGateClose.get(world.getName()) == false)
						{
							plugin.getLog().info("[Realms] Gate Night Event "+world.getName());
							doGateClose(world);
							isGateClose.put(world.getName(),true);
							isGateOpen.put(world.getName(),false);
							if (isProduction)
							{
								if (plugin.getServer().getWorlds().get(0).getName().equalsIgnoreCase(world.getName()))
								{
									taxCounter++;
								}
								if (plugin.getRealmModel().getSettlements().size() > 0)
								{
									plugin.getRealmModel().OnProduction(world.getName());
	//								plugin.getLog().info("[Realms] production calculation : "+world.getName());
									plugin.getServer().broadcastMessage(ChatColor.BLUE+"[Realms] production calculation on "+world.getName());
								}
							} else
							{
								System.out.println("[Realms] Production "+isProduction+" on "+world.getName());
							}
						}
					}
					plugin.getRealmModel().OnNight(plugin.getServer().getWorlds().get(0).getTime());
					
				} else
				{
					isGateClose.put(world.getName(),false);
				}
				// start Day Event
				if( (world.getTime() >= 0)
					&& (world.getTime() <= 1999)
						)
				{
		//			System.out.print("Day:"+plugin.getServer().getWorlds().get(0).getTime());
					if (isGateOpen.containsKey(world.getName()) == true)
					{
						if (isGateOpen.get(world.getName()) == false)
						{
							plugin.getLog().info("[Realms] Gate DAY Event : "+world.getName());
							doGateOpen(world);
							isGateOpen.put(world.getName(), true);
							isGateClose.put(world.getName(), false);
							for (Regiment regiment : plugin.getData().getRegiments().values())
							{	
								if (regiment.getPosition().getWorld().equalsIgnoreCase(world.getName()))
								{
									System.out.println("[REALMS] Regiment next day ");
									regiment.doNextDay();
								}
							}
							if (isProduction)
							{
								plugin.getRealmModel().OnTrade(world.getName()); 
								plugin.getLog().info("[Realms] Trader calculation :"+world.getName());
							}
							
						}
					}
					plugin.getRealmModel().OnDay(world.getTime());
				} else
				{
					isGateOpen.put(world.getName(), false);
				}
				
			
				//Tax run
				if(taxCounter >= taxLimit)
				{
					taxCounter = 0;
					if (isProduction)
					{
						plugin.getRealmModel().OnTax();
						plugin.getLog().info("[Realms] Tax calculation");
					}
				}
			}
		}
	}

	/**
	 * not in use now 
	 */
	private void doGateClose(World world)
	{
//		plugin.getServer().broadcastMessage("Gates now Closed "+world.getName());
		for (Settlement settle : plugin.getRealmModel().getSettlements().getSubList(world.getName()).values())
		{
			for (Building building : settle.getBuildingList().values())
			{
				if (building.getBuildingType() == BuildPlanType.GATE)
				{
					Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
					if (region != null)
					{
//						System.out.println("[REALMS] Close gate");
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
	 * open the gate of a settlement 
	 */
	private void doGateOpen(World world)
	{
//		plugin.getServer().broadcastMessage("Gates now Open "+world.getName());
		for (Settlement settle : plugin.getRealmModel().getSettlements().getSubList(world.getName()).values())
		{
			for (Building building : settle.getBuildingList().values())
			{
				if (building.getBuildingType() == BuildPlanType.GATE)
				{
					Region region = plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion());
					if (region != null)
					{
//						System.out.println("[REALMS] Open gate");
						setGateFrame(region.getLocation(), Material.AIR);
					}
				}
			}
		}
	}

	private void doDynMapUpdate()
	{
//		plugin.dynmap.getMarkerAPI().getMarkerSets()getClass();
		
	}
	
}
