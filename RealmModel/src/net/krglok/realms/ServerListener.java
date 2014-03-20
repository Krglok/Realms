package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.Util;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionCondition;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * <pre>
 * This is the implementation of the EventHandlers 
 * the Event will catched , selected 
 * and transfered to the special command Handler
 * 
 * @author oduda
 *
 *</pre>
 */
public class ServerListener implements Listener
{
	private Realms plugin;
	private int lastPage;
	private int marketPage;
	
	public ServerListener(Realms plugin)
	{
		this.plugin = plugin;
		this.lastPage = 0;
		this.marketPage = 0;
	}

//    @EventHandler(priority = EventPriority.NORMAL)
//    public void Craft(CraftItemEvent event)
//    {
////    	Recipe recipe = event.getRecipe();
////    	ItemStack itemStack = recipe.getResult();
//    }
//    
//    //PrepareCraftItemEvent
//    @EventHandler(priority = EventPriority.NORMAL)
//    public void prepareCraft(PrepareItemCraftEvent event)
//    {
////    	Recipe recipe = event.getRecipe();
////    	ItemStack itemStack = recipe.getResult();
////    	Container container = null;
////		InventoryCrafting inventorycrafting = new InventoryCrafting(container, 1, 1);
////		inventorycrafting.b(i, itemstack)
//    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        /*if (event.isCancelled() || !(event.getEntity() instanceof Creeper || event.getEntity() instanceof EnderDragon
                || event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof Fireball)) {
            return;
        }*/
        System.out.println("Get Explosion!");
    	
        ArrayList<RegionCondition> conditions = new ArrayList<RegionCondition>();
        conditions.add(new RegionCondition("denyexplosion", true, 4));
        conditions.add(new RegionCondition("denyexplosionnoreagent", false, 4));
        conditions.add(new RegionCondition("powershield", true, 0));
        if (event.getEntity() == null) 
        {
            
        } else if (event.getEntity().getClass().equals(Creeper.class)) 
        {
            conditions.add(new RegionCondition("denycreeperexplosion", true, 4));
            conditions.add(new RegionCondition("denycreeperexplosionnoreagent", false, 4));
        } else if (event.getEntity().getClass().equals(TNTPrimed.class)) 
        {
            conditions.add(new RegionCondition("denytntexplosion", true, 4));
            conditions.add(new RegionCondition("denytntexplosionnoreagent", false, 4));
        } else if (event.getEntity().getClass().equals(Fireball.class)) 
        {
            conditions.add(new RegionCondition("denyghastexplosion", true, 4));
            conditions.add(new RegionCondition("denyghastexplosionnoreagent", false, 4));
        }
        if (plugin.stronghold.getRegionManager().shouldTakeAction(event.getLocation(), null, conditions)) 
        {
            for (SuperRegion sr : plugin.stronghold.getRegionManager().getContainingSuperRegions(event.getLocation())) 
            {
                if (sr.getPower() > 0 ) 
                {
//                	plugin.stronghold.getRegionManager().reduceRegion(sr);
                  System.out.println("DenyShield active!");
                }
                event.setCancelled(true);
            }
            return;
        }
        
    }
	
    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) 
    {
		if (event.getPlayer().isOp()) 
		{
			
			String msg = "[Realms] Updatecheck : "+plugin.getConfigData().getPluginName()+" Vers.: "+plugin.getConfigData().getVersion();
//			UpdateOld.message(event.getPlayer(),msg);
		}
		return; // no OP => OUT
	}
	
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event)
    {
    	if (event.getPlayer() instanceof Player)
    	{
    		checkSettleChest(event);
    	}
    }
    
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
    	Block b = event.getClickedBlock();
    	if (b != null)
    	{
        	ArrayList<String> msg = new ArrayList<String>();
	    	if (b.getType() == Material.WALL_SIGN)
	    	{
	    		cmdWallSign(event, b);
	    	}
	    	if (b.getType() == Material.SIGN_POST)
	    	{
	    		Sign sign = (Sign) b.getState();
	    		String l0 = sign.getLine(0);
	    		String l1 = sign.getLine(1);
	    		if (l0.contains("[BUILD]"))
	    		{
	    			System.out.println("SignPost");
		    		if (l1 != "")
		    		{
		    			Location pos = b.getLocation();
		    	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK)
		    	    	{
			    			System.out.println("Check");
		    	    		checkAt(pos, l1, event.getPlayer(), msg);
		    	    		plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
		    	    		return;
		    	    	} else
		    	    	{
			    			System.out.println("BuildAt");
			    			sign.setLine(0, "");
			    			sign.update();
			    			if (buildAt( pos, l1, event.getPlayer(), msg))
			    			{
						    	msg.add("Build startet  ");
						    	msg.add(" ");
			    			} else
			    			{
						    	msg.add("Building NOT Build : "+l1);
						    	msg.add(" ");
			    			}
			    			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
			    			return;
		    	    	}
		    		}
	    		}
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	{
//    			event.getPlayer().sendMessage("You hold a Blazerod :");
	    		cmdBlazeRod(event);
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK)
	    	{
//    			System.out.println("BOOK");
//	    		cmdBuildBook(event);
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK_AND_QUILL)
	    	{
	    		cmdBuildBook(event);
//	    		System.out.println("BookEdit");
	    	}

    	}
    }
    
    private void cmdBlazeRod(PlayerInteractEvent event)
    {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
	    	Block target = event.getClickedBlock();
	    	Location pos = target.getLocation();
			ArrayList<Region> targets =  plugin.stronghold.getRegionManager().getContainingRegions(pos);
	    	ArrayList<String> msg = new ArrayList<String>();
			msg.add("== HeroStronghold : Region Info");
			if (targets != null)
			{
				if (targets.size() > 0)
				{
		    		for (Region region : targets)
		    		{
		    			if (region.getOwners().size() > 0)
		    			{
		    				msg.add(region.getID()+":"+region.getType()+":"+region.getOwners().get(0));
		    			} else
		    			{
		    				msg.add(region.getID()+":"+region.getType()+":");
		    			}
		    			
		    		}
				} else
				{
		    		msg.add("No Region found ! ");
				}
	
			} else
			{
	    		msg.add("No Region found ! ");
			}
			for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(pos))
			{
				if (sRegion.getOwners().size() > 0)
				{
					msg.add(sRegion.getName()+":"+sRegion.getType()+":"+sRegion.getOwners().get(0));
				} else
				{
					msg.add(sRegion.getName()+":"+sRegion.getType()+":");
				}
				
			}
			
			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
		}    	
    }

    private void cmdBuildPlanBook(PlayerInteractEvent event)
    {

    }
    
    private void cmdRequiredBook(PlayerInteractEvent event)
    {

    }
    
	private String findSuperRegionAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
	    	if (settleType != SettleType.NONE)
	    	{
	    		return sRegion.getName();
	    	}
	    }
		return "";
	}

	private String findRegionAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
	    for (Region region : plugin.stronghold.getRegionManager().getContainingRegions(position))
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return bType.name();
	    	}
	    }
		return "";
	}
	
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerPlayerEditBookEvent(PlayerEditBookEvent event)
    {
    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK_AND_QUILL)
    	{
    		//cmdBuildBook(event);
    		System.out.println("BookEdit");
    	}
    	
    }

    private void cmdBuildBook(PlayerInteractEvent event)
    {
    	Player player = event.getPlayer();
    	ItemStack handItem = event.getPlayer().getItemInHand();
    	ArrayList<String> msg = new ArrayList<String>();
    	if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
    	{
    		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
    		{
		    	Block target = ((PlayerInteractEvent) event).getClickedBlock();
		    	Location pos = target.getLocation();
				if (handItem != null)
				{
					ItemMeta meta = handItem.getItemMeta();
					if (meta != null)
					{
		    			System.out.println("CheckMeta");
						if (meta.getDisplayName() == "[WHEAT]")
						{
							if (buildAt(pos, "WHEAT", player, msg))
							{
						    	msg.add(":"+meta.getDisplayName()+":");
								
							} else
							{
						    	msg.add("Not Build  WHEAT !");
						    	msg.add(":"+meta.getDisplayName()+":");
							}
						} else
						{
					    	msg.add("Not a BuildPlan name WHEAT!");
					    	msg.add(":"+meta.getDisplayName()+":");
						}
					} else
					{
				    	msg.add("No Meta in Hand !");
				    	msg.add(" ");
					}
						
				}
    		}
    	} else
    	{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
    	}
		plugin.getMessageData().printPage(player, msg, 1);
    }
    
    private boolean buildAt(Location pos, String name, Player player, ArrayList<String> msg)
    {
		LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY(), pos.getZ()+1);
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle != null)
		{
			if (BuildPlanType.getBuildPlanType(name) != BuildPlanType.NONE)
			{
				BuildPlanMap buildPLan = plugin.getRealmModel().getData().readTMXBuildPlan(BuildPlanType.getBuildPlanType(name), 4, -1);
				
				if (checkBuild(pos, name, player, msg))
				{
					BuildPlanType bType = BuildPlanType.getBuildPlanType(name);
					McmdBuilder modelCommand = new McmdBuilder(plugin.getRealmModel(), settle.getId(), bType, iLoc,player);
					plugin.getRealmModel().OnCommand(modelCommand);
			    	msg.add("BUILD "+bType.name()+" in "+settle.getName()+" at "+(int)pos.getX()+":"+(int)pos.getY()+":"+(int)pos.getZ());
			    	msg.add(" ");
			    	return true;
				} else
				{
			    	msg.add("Some Material is not available");
			    	msg.add("Give Items to warehouse ! ");
					return false;
				}
			} else
			{
		    	msg.add("No BuildPlan set in Line 1");
		    	msg.add(" ");
		    	return false;
			}
		} else
		{
	    	msg.add("Not in Range of a Settlement ");
	    	msg.add(" ");
	    	return false;
		}
    }
    
    private boolean checkBuild(Location pos, String name, Player player, ArrayList<String> msg)
    {
		LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY()+1, pos.getZ()+1);
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		ItemList needMat = new ItemList();
		if (settle != null)
		{
			if (BuildPlanType.getBuildPlanType(name) != BuildPlanType.NONE)
			{
				BuildPlanType bType = BuildPlanType.getBuildPlanType(name);
				needMat = settle.settleManager().checkBuildingMaterials(plugin.getRealmModel(), settle, bType);
				if (needMat.isEmpty())
				{
					return true;
				} else
				{
					for (Item item : needMat.values())
					{
						msg.add(item.ItemRef()+":"+item.value());
					}
			    	return false;
				}
			} else
			{
		    	msg.add("No BuildPlan set in Line 1");
		    	msg.add(" ");
		    	return false;
			}
		} else
		{
	    	msg.add("Not in Range of a Settlement ");
	    	msg.add(" ");
	    	return false;
		}
    }

    private boolean checkAt(Location pos, String name, Player player, ArrayList<String> msg)
    {
		LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY()+1, pos.getZ()+1);
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle != null)
		{
			if (BuildPlanType.getBuildPlanType(name) != BuildPlanType.NONE)
			{
				if (checkBuild(pos, name, player, msg))
				{
					BuildPlanType bType = BuildPlanType.getBuildPlanType(name);
			    	msg.add("Ready to BUILD "+bType.name()+" in "+settle.getName()+" at "+(int)pos.getX()+":"+(int)pos.getY()+":"+(int)pos.getZ());
			    	msg.add(" ");
			    	return true;
				} else
				{
			    	msg.add("Some Material not available");
			    	msg.add("Give Items to warehouse ! ");
					return false;
				}
			} else
			{
		    	msg.add("No BuildPlan set in Line 1");
		    	msg.add(" ");
		    	return false;
			}
		} else
		{
	    	msg.add("Not in Range of a Settlement ");
	    	msg.add(" ");
	    	return false;
		}
    }

    private void  checkSettleChest(InventoryCloseEvent event)
    {
    	Player player = (Player) event.getPlayer();
		Location pos = event.getPlayer().getLocation();
		Inventory inventory = event.getInventory();
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		ItemList needMat = new ItemList();
		if (settle != null)
		{
			String region = findRegionAtLocation(plugin, player);
//			if (region.equalsIgnoreCase(BuildPlanType.WAREHOUSE.name()))
//			{
//				if (event.getView().getType() == InventoryType.CHEST)
//				{
//					System.out.println("You are in a WAREHOUSE closed a Chest");
//					if (inventory.getSize() > 0)
//					{
//						for (ItemStack itemStack :inventory.getContents())
//						{
//							settle.getWarehouse().depositItemValue(itemStack.getType().name(), itemStack.getAmount());
//						}
//						inventory.clear();
//					}
//				}
//			}
			if (region.equalsIgnoreCase(BuildPlanType.HALL.name()))
			{
				if (event.getView().getType() == InventoryType.CHEST)
				{
//					System.out.println("You are in a HALL closed a Chest");
					if (inventory.getSize() > 0)
					{
						for (ItemStack itemStack :inventory.getContents())
						{
							if (itemStack != null)
							{
								String name = itemStack.getType().name();
								if (name.equalsIgnoreCase(Material.WATER_BUCKET.name()))
								{
									name = Material.WATER.name();
								}
								if (name.equalsIgnoreCase(Material.DIRT.name()))
								{
									name = Material.SOIL.name();
								}
								settle.getWarehouse().depositItemValue(name, itemStack.getAmount());
								System.out.println("Warehouse : "+itemStack.getType().name()+":"+itemStack.getAmount());
							}
						}
						inventory.clear();
					}
				}
			}
		}
    }

    private void cmdWallSign(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		String l0 = sign.getLine(0);
		String l1 = sign.getLine(1);
		if (l0.contains("[WAREHOUSE]"))
		{
//			cmdBuildPlanBook(event);
			CmdSettleWarehouse cmdWare = new CmdSettleWarehouse();
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				cmdWare.execute(plugin, event.getPlayer());
				cmdWare.setPara(0, settle.getId());
				cmdWare.setPara(1, this.lastPage);
				cmdWare.execute(plugin, event.getPlayer());
				lastPage = cmdWare.getPage()+1;
				
			}
			return;
		}
		if (l0.contains("[INFO]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleInfo cmdInfo = new CmdSettleInfo();
				cmdInfo.setPara(0, settle.getId());
				cmdInfo.setPara(1, 1);
				cmdInfo.execute(plugin, event.getPlayer());
			}
			return;
		}
		if (l0.contains("[TRADER]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleTrader cmd = new CmdSettleTrader();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, 1);
				cmd.execute(plugin, event.getPlayer());
			}
			return;
		}
		if (l0.contains("[BUILDINGS]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleBuildingList cmd = new CmdSettleBuildingList();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, 1);
				cmd.execute(plugin, event.getPlayer());
			}
			return;
		}
		if (l0.contains("[PRODUCTION]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleProduction cmd = new CmdSettleProduction();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, marketPage);
				cmd.execute(plugin, event.getPlayer());
				marketPage = cmd.getPage()+1;
			}
			return;
		}

		if (l0.contains("[MARKET]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleMarket cmd = new CmdSettleMarket();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, marketPage);
				cmd.execute(plugin, event.getPlayer());
				marketPage = cmd.getPage()+1;
			}
			return;
		}
		
		if (l0.contains("[Required]"))
		{
			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			cmdRequiredBook(event);
		}
    	
    }
    
}
