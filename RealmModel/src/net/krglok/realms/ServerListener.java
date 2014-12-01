package net.krglok.realms;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.Util;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionCondition;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.command.CmdRealmsBookList;
import net.krglok.realms.command.CmdSettleBuildingList;
import net.krglok.realms.command.CmdSettleInfo;
import net.krglok.realms.command.CmdSettleMarket;
import net.krglok.realms.command.CmdSettleNoSell;
import net.krglok.realms.command.CmdSettleProduction;
import net.krglok.realms.command.CmdSettleRequired;
import net.krglok.realms.command.CmdSettleTrader;
import net.krglok.realms.command.CmdSettleWarehouse;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SignPos;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.science.CaseBook;
import net.minecraft.server.v1_7_R1.IInventory;
import net.minecraft.server.v1_7_R1.InventoryEnderChest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Golem;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

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
	private int bookPage;
	private int bookId;
	private long lastHunt = 0;
	private long lastTame = 0; 
	
	public ServerListener(Realms plugin)
	{
		this.plugin = plugin;
		this.lastPage = 0;
		this.marketPage = 0;
		this.bookPage =0;
		this.bookId = 0;
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

	/**
	 * suppress mob spawn in a range of a settlement. 
	 * @param event
	 */
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onCreatureSpawn( CreatureSpawnEvent event )
	{
		// Check for other plugins that have cancelled the event,
		// egg spawns, spawner spawns, and neutral mobs.
		// TODO: this is a bit of a mess
		if( event.isCancelled() 
			||	( event.getSpawnReason() == SpawnReason.EGG ) 
			||	( event.getSpawnReason() == SpawnReason.SPAWNER ) 
			||	( event.getSpawnReason() == SpawnReason.SPAWNER_EGG ) 
			||	( event.getEntity() instanceof Squid ) 
			||	( event.getEntity() instanceof NPC ) 
			||	( event.getEntity() instanceof Golem ) 
//			||	( !plugin.getMobRepellentConfiguration().shouldRepelNeutralMobs() &&( event.getEntity() instanceof Animals ) ) 
			)
			return;
		
		ArrayList<EntityType> mobsToRepel = plugin.getConfigData().getMobsToRepel();
		// Now check to make sure the mob is in the list
		if( !mobsToRepel.isEmpty() )
		{
			if( !mobsToRepel.contains( event.getEntityType() ) )
				return;
		}
		
		if( findStrongholdAtLocation(plugin, event.getLocation() ) )
		{
//			System.out.print("[REALMS] Spawn suppress "+event.getEntityType().name());
			event.setCancelled( true );
		}
	}

	/**
	 * Handle explosion on settlements.
	 * Suppress destruction and reduce power instead 
	 * Work with herostronghold functions and conditions for regions
	 * @param event
	 */
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        /*if (event.isCancelled() || !(event.getEntity() instanceof Creeper || event.getEntity() instanceof EnderDragon
                || event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof Fireball)) {
            return;
        }*/
//        System.out.println("Get Explosion!");
    	
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
	
	/**
	 * send update check message to ops
	 * @param event
	 */
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
	
    /**
     * do special action on chest in settlement
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event)
    {
    	if (event.getPlayer() instanceof Player)
    	{
    		System.out.println(event.getInventory().getTitle());
    		System.out.println(event.getPlayer().getOpenInventory().getTitle());
    		checkSettleChest(event);
    	}
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryOpen(InventoryOpenEvent event)
    {
    	return;
    }
    
    /**
     * handle action on WALL_SIGN, SIGN_POST
     * BLAZEROD and Books
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
    	Block b = event.getClickedBlock();
    	if (b != null)
    	{
        	ArrayList<String> msg = new ArrayList<String>();
	    	if (b.getType() == Material.WALL_SIGN)
	    	{
	    		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
	    		{
	    	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	    	{
	    	    		cmdRegisterSign(event, b);
	    	    	} else
	    	    	{
	    	    		cmdWallSign(event, b);
	    	    	}
	    		}
	    		if (event.getAction() == Action.LEFT_CLICK_BLOCK)
	    		{
	    	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	    	{
	    	    		cmdSignUpdate(event, b);
	    	    	} else
	    	    	{
	    	    		cmdLeftWallSign(event,b);
	    	    	}
	    		}
	    		return;
	    	}
	    	if (b.getType() == Material.SIGN_POST)
	    	{
	    		Sign sign = (Sign) b.getState();
	    		String l0 = sign.getLine(0);
	    		String l1 = sign.getLine(1);
	    		if (l0.contains("[BUILD]"))
	    		{
//	    			System.out.println("SignPost");
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
		    	    		// start Build with empty hand on RightClick
			    	    	if (
			    	    		(event.getPlayer().getItemInHand().getType() == Material.AIR)
			    	    		&& (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			    	    		)
			    	    	{
				    			System.out.println("BuildAt");
				    			if (buildAt( pos, l1, event.getPlayer(), msg))
				    			{
							    	msg.add("Build startet  ");
							    	msg.add(" ");
					    			sign.setLine(0, "");
					    			sign.update();
				    			} else
				    			{
							    	msg.add("Build NOT started : "+l1);
							    	msg.add(" ");
				    			}
				    			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				    			return;
			    	    	} else
			    	    	{
						    	msg.add("Use empty hand for startup build: "+l1);
						    	msg.add(" ");
			    	    	}
			    			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
			    			return;
		    	    	}
		    		}
		    		return;
	    		}
	    		// other signpost Commands
	    		cmdSignPost(event, b);
	    		return;
	    	}

	    	if (b.getType() == Material.BOOKSHELF)
	    	{
	    		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
	    		{
	    			cmdBookList(event,b);
	    		}
	    		return;
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
    
    /**
     * do action with BLAZEROD in settlements
     * @param event
     */
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

    /**
     * unused at this time
     * @param event
     */
    private void cmdBuildPlanBook(PlayerInteractEvent event)
    {

    }
    
    /**
     * unused at this time
     * @param event
     */
    private void cmdRequiredBook(PlayerInteractEvent event)
    {
    	

    }

    
	private boolean findStrongholdAtLocation(Realms plugin,Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
	    	if (settleType != SettleType.NONE)
	    	{
	    		return true;
	    	}
	    }
	    for (Region region : plugin.stronghold.getRegionManager().getContainingRegions(position))
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return true;
	    	}
	    }
		return false;
	}

    /**
     * give first superegion at player position
     * @param plugin
     * @param player
     * @return superregion name
     */
	private String findSuperRegionAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
		SuperRegion sRegion =  findSuperRegionAtPosition( plugin,  position);
		if (sRegion != null)
		{
	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
	    	if (settleType != SettleType.NONE)
	    	{
	    		return sRegion.getName();
	    	}
	    }
		return "";
	}

	/**
	 * give first superregion at position
	 * @param plugin
	 * @param position
	 * @return superregion object  or null
	 */
	private SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
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

	/**
	 * give first building at player position
	 * @param plugin
	 * @param player
	 * @return buildungtype as string
	 */
	private String findRegionAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
		Region region = findRegionAtPosition( plugin, position);
	    if ( region != null)
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return bType.name();
	    	}
	    }
		return "";
	}

	/**
	 * give region id at player position
	 * @param plugin
	 * @param player
	 * @return region id
	 */
	private Integer findRegionIdAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
		Region region = findRegionAtPosition( plugin, position);
	    if ( region != null)
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return region.getID();
	    	}
	    }
		return -1;
	}
	
	/**
	 * give first region at position
	 * @param plugin
	 * @param position
	 * @return  region object
	 */
	private Region findRegionAtPosition(Realms plugin,Location position)
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
			    	msg.add(ChatColor.GREEN+"BUILD "+bType.name()+" in "+settle.getName()+" at "+(int)pos.getX()+":"+(int)pos.getY()+":"+(int)pos.getZ());
			    	msg.add(" ");
			    	return true;
				} else
				{
			    	msg.add(" ");
			    	msg.add(ChatColor.RED+"Above material is not available");
			    	msg.add("Give Items to warehouse ! ");
			    	msg.add(" ");
					return false;
				}
			} else
			{
		    	msg.add(ChatColor.RED+"No buildPlan set in Line 1");
		    	msg.add(" ");
		    	return false;
			}
		} else
		{
	    	msg.add("ChatColor.RED+Not in Range of a Settlement ");
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
//								if (name.equalsIgnoreCase(Material.WATER_BUCKET.name()))
//								{
//									name = Material.WATER.name();
//								}
//								if (name.equalsIgnoreCase(Material.DIRT.name()))
//								{
//									name = Material.SOIL.name();
//								}
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
		String l2 = sign.getLine(2);
		String l3 = sign.getLine(3);
		
		if (l0.contains("[TRAP]"))
		{
			cmdSignPost(event,b);
		}		
		if (l0.contains("[HUNT]"))
		{
			cmdSignPost(event,b);
		}		
		
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
				cmd.setPara(1, marketPage);
				cmd.execute(plugin, event.getPlayer());
				marketPage = cmd.getPage()+1;
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

		if (l0.contains("[NOSELL]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleNoSell  cmd = new CmdSettleNoSell();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, this.lastPage);
				cmd.execute(plugin, event.getPlayer());
				lastPage = cmd.getPage()+1;
				
			}
//			cmdRequiredBook(event);
		}

		
		if (l0.contains("[SELL]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				String itemRef = l1.toUpperCase();
				System.out.println("Sell :"+itemRef);
				if (settle.settleManager().getDontSell().containsKey(itemRef))
				{
					l3 = "NO SELL";
					sign.update();
					event.getPlayer().sendMessage("No sell of "+itemRef);
				} else
				{
					int amount = 1;
					try
					{
						amount = Integer.valueOf(l2);
						if (amount < 1 )
						{
							amount = 1;
						}
						
					} catch (Exception e)
					{
						System.out.println("Sell amount exeption! ");
						amount = 1;
					}
					double price = plugin.getData().getPriceList().getBasePrice(itemRef);
					price = price * amount;
					l3 = ConfigBasis.setStrformat2(price,7);
					sign.update();
					int stock = settle.getWarehouse().getItemList().getValue(itemRef);
					if (stock > (amount * 2))
					{
						if (Realms.economy != null)
						{
							if (Realms.economy.has(event.getPlayer().getName(),price))
							{
								ItemStack item = new ItemStack(Material.getMaterial(itemRef), amount);
								event.getPlayer().getInventory().addItem(item);
								Realms.economy.withdrawPlayer(event.getPlayer().getName(), price);
								settle.getWarehouse().withdrawItemValue(itemRef, amount);
								System.out.println("Settle SELL :"+itemRef+":"+amount+":"+price);
								event.getPlayer().sendMessage("You bought "+itemRef+":"+amount+ConfigBasis.setStrformat2(price,9));
								event.getPlayer().updateInventory();
							}
						} else
						{
							event.getPlayer().sendMessage("NO economy !");
						}
					} else
					{
						System.out.println("No Stock");
						event.getPlayer().sendMessage("No Stock of "+itemRef);
					}
				}
				
			}
//			cmdRequiredBook(event);
		}

		if (l0.contains("[BUY]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				String itemRef = l1.toUpperCase();
				System.out.println("Buy :"+itemRef);
				if ((settle.getWarehouse().getItemList().getValue(itemRef)/64) > (settle.getWarehouse().getItemMax() / 64 / 5))
				{
					l3 = "NO BUY";
					sign.update();
					event.getPlayer().sendMessage("No buy of "+itemRef);
					
				} else
				{
					int amount = 1;
					try
					{
						amount = Integer.valueOf(l2);
						if (amount < 1 )
						{
							amount = 1;
						}
						if (amount > 64 )
						{
							amount = 64;
						}
						
					} catch (Exception e)
					{
						System.out.println("Buy amount exeption! ");
						amount = 1;
					}
					double price = plugin.getData().getPriceList().getBasePrice(itemRef);
					price = price * amount;
					l3 = ConfigBasis.setStrformat2(price,7);
					sign.update();
//					int stock = settle.getWarehouse().getItemList().getValue(itemRef);
					if (Realms.economy != null)
					{
						if (settle.getBank().getKonto() > price)
						{
							ItemStack itemStack = new ItemStack(Material.getMaterial(itemRef), amount);
							if (event.getPlayer().getInventory().contains(Material.getMaterial(itemRef), amount) == true)
							{
								event.getPlayer().getInventory().removeItem(itemStack);
								Realms.economy.depositPlayer(event.getPlayer().getName(), price);
								settle.getWarehouse().depositItemValue(itemRef, amount);
								settle.getBank().withdrawKonto(price, event.getPlayer().getName(), settle.getId());
								event.getPlayer().sendMessage("You sold "+itemRef+":"+amount+ConfigBasis.setStrformat2(price,9));
								System.out.println("Settle BUY "+itemRef+":"+amount);
								event.getPlayer().updateInventory();
							} else
							{
								event.getPlayer().sendMessage("You have not enough items:"+itemRef+":"+amount);
							}
						} else
						{
							event.getPlayer().sendMessage("The settlement has not enough money");
						}
					} else
					{
						event.getPlayer().sendMessage("NO economy !");
					}
				}
				
			}
//			cmdRequiredBook(event);
		}
		
		if (l0.contains("[REQUIRE]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleRequired  cmd = new CmdSettleRequired();
				cmd.setPara(0, settle.getId());
				cmd.setPara(1, this.lastPage);
				cmd.execute(plugin, event.getPlayer());
				lastPage = cmd.getPage()+1;
				
			}
//			cmdRequiredBook(event);
		}

		if (l0.contains("[WORKSHOP]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			Integer regionId = findRegionIdAtLocation(plugin, event.getPlayer());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				if (regionId == building.getHsRegion())
				{
					sign.setLine(1, "id:"+String.valueOf(building.getId()));
					sign.update();
			    	ArrayList<String> msg = new ArrayList<String>();
					msg.add("Settlement ["+settle.getId()+"] : "+ChatColor.YELLOW+settle.getName());
					int index = 0;
					for (Item item :  building.getSlots())
					{
						if (item != null)
						{
							msg.add(ChatColor.YELLOW+"Slot"+index+": "+ChatColor.GREEN+item.ItemRef()+":"+item.value());
						}
						index++;
					}
					msg.add(" ");
					plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				}
			}
		}

		if (l0.contains("[TRAIN]"))
		{
	    	ArrayList<String> msg = new ArrayList<String>();
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			Integer regionId = findRegionIdAtLocation(plugin, event.getPlayer());
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
				if (regionId == building.getHsRegion())
				{
					if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 5 )
					{
						sign.setLine(1, String.valueOf(building.getTrainType().name()));
						sign.update();
						building.addMaxTrain(1);
						msg.add("Settlement ["+settle.getId()+"] : "
								+ChatColor.YELLOW+settle.getName()
								+ChatColor.GREEN+" Age: "+settle.getAge()
								+":"+settle.getProductionOverview().getCycleCount());
						msg.add("Building: "+building.getBuildingType().name());
						msg.add("Train   : "+ChatColor.YELLOW+building.getTrainType().name());
						msg.add("Need    : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),4)+" Cycles");
					} else
					{
						msg.add("Building: "+building.getBuildingType().name());
						msg.add("Train   : "+ChatColor.RED+"not possible !");
					}
					plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				}
			}
		}
		if (l0.contains("[BOOK]"))
		{
	    	System.out.println("Book Sign "+bookId);
	    	Region region = findRegionAtPosition(plugin, event.getPlayer().getLocation());
	    	if (event.getPlayer().isOp() == false)
	    	{
				if ((region.getType().equalsIgnoreCase(BuildPlanType.LIBRARY.name()) == false)
					&&  (region.getType().equalsIgnoreCase("BIBLIOTHEK") == false))
				{
					return;
				}
	    	}
			if (event.getPlayer().getItemInHand().getType() == Material.BOOK)
			{
		    	System.out.println("Book in Hand "+bookId);
		    	if (bookId > 0)
		    	{
					CaseBook cBook = plugin.getRealmModel().getCaseBooks().get(bookId);
	    			if (cBook.isEnabled())
	    			{
	    				event.getPlayer().sendMessage("You get a new Book");
	    	    		PlayerInventory inventory = event.getPlayer().getInventory();
	    	    		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
	    	    		book = CaseBook.writeBook(book, cBook);
	    	    		event.getPlayer().sendMessage("Create Book "+cBook.getId()+" : "+cBook.getAuthor()+" | "+cBook.getTitel());
//	    				inventory.addItem(book);
	    				event.getPlayer().setItemInHand(book);
	    			} else
	    			{
	    				event.getPlayer().sendMessage(ChatColor.RED+"The book is not Enabled");
	    			}
					
		    	} else
		    	{
					event.getPlayer().sendMessage(ChatColor.RED+"Book Id not valid !");
		    	}
			} else
			{
		    	System.out.println("Book List "+bookId);
		    	// toogle id from 1 to size 
				if ((bookId+1) <= plugin.getRealmModel().getCaseBooks().size()  )
				{
					bookId++;
				} else
				{
					bookId = 1;
				}
				CaseBook cBook = plugin.getRealmModel().getCaseBooks().get(bookId);
	    		sign.setLine(1, String.valueOf(bookId));
	    		sign.setLine(2, cBook.getTitel());
	    		sign.setLine(3, "Enable: "+cBook.isEnabled());
				sign.update(true);
			}
			
		}
		if (l0.contains("[DONATE]"))
		{
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			Integer regionId = findRegionIdAtLocation(plugin, event.getPlayer());
			System.out.println("Donate "+settle.getName()+" : "+event.getPlayer().getItemInHand().getType());
			if (settle.getBuildingList().getBuilding(regionId).getBuildingType() == BuildPlanType.HALL)
			{
				ItemStack item = event.getPlayer().getItemInHand();
				settle.getWarehouse().depositItemValue(item.getType().name(), item.getAmount());
				event.getPlayer().getInventory().remove(item);
//				event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
		}
    	
    }

    private void cmdLeftWallSign(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		String l0 = sign.getLine(0);
		String l1 = sign.getLine(1);
		String l2 = sign.getLine(2);
		String l3 = sign.getLine(3);

		if (l0.contains("[SELL]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				String itemRef = l1.toUpperCase();
				System.out.println("Check Sell :"+itemRef);
				if (settle.settleManager().getDontSell().containsKey(itemRef))
				{
					l3 = "NO SELL";
				} else
				{
					int amount = 1;
					try
					{
						amount = Integer.valueOf(l2);
						if (amount < 1 )
						{
							amount = 1;
						}
						
					} catch (Exception e)
					{
						amount = 1;
					}
					double price = plugin.getData().getPriceList().getBasePrice(itemRef);
					price = price * amount;
					l3 = ConfigBasis.setStrformat2(price,7);
				}
				sign.update();
			}
//			cmdRequiredBook(event);
		}

    }
    
    
    private void cmdSignPost(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		String l0 = sign.getLine(0);
		String l1 = sign.getLine(1);
    	
		if (l0.contains("[HUNT]"))
		{
			long actTime = plugin.getServer().getWorlds().get(0).getTime();
			if (Math.abs(actTime - lastHunt)< 1000)
			{
		    	ArrayList<String> msg = new ArrayList<String>();
				msg.add("[REALMS] Timeout hunt 1 h");
				msg.add(" ");
    			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				System.out.println("[REALMS] Timeout hunt");
				return;
			}
			lastHunt = plugin.getServer().getWorlds().get(0).getTime();
			Location pos = b.getLocation();
			// find required region
			Region region = findRegionAtPosition( plugin, pos);
			if (region == null)
			{
				System.out.println("NO Region found");
				return;
			}
			if (region.getType().equalsIgnoreCase("HUNTER") == false)
			{
				System.out.println("WRONG Region found");
				return;
			}
			for(Entity entity : pos.getWorld().getEntities()) 
			{
				EntityType eType = entity.getType(); 
                if( eType== EntityType.SPIDER) 
                {
                    double distance = entity.getLocation().distance(pos);
                    if(distance <= 100) 
                    {
                    	entity.remove();
                    	System.out.println("Spider hunt ");
                    	ItemStack itemStack = new ItemStack(Material.SPIDER_EYE,1,(short) 0);
//                    	Egg spiderEgg = 
                    	event.getPlayer().getInventory().addItem(itemStack);
						event.getPlayer().updateInventory();
}						return;
                }
                if( eType== EntityType.SKELETON) 
                {
                    double distance = entity.getLocation().distance(pos);

                    if(distance <= 100) 
                    {
                    	entity.remove();
                    	System.out.println("Skeleton hunt ");
                    	ItemStack itemStack = new ItemStack(Material.BONE,2,(short) 0);
//                    	Egg spiderEgg = 
                    	event.getPlayer().getInventory().addItem(itemStack);
						event.getPlayer().updateInventory();
						return;
                    }
                }
                if( eType== EntityType.ZOMBIE) 
                {
                    double distance = entity.getLocation().distance(pos);

                    if(distance <= 100) 
                    {
                    	entity.remove();
                    	System.out.println("ZOMBIE hunt ");
                    	ItemStack itemStack = new ItemStack(Material.ROTTEN_FLESH,2,(short) 0);
//                    	Egg spiderEgg = 
                    	event.getPlayer().getInventory().addItem(itemStack);
						event.getPlayer().updateInventory();
						return;
                    }
                }
                if( eType== EntityType.CREEPER) 
                {
                    double distance = entity.getLocation().distance(pos);

                    if(distance <= 100) 
                    {
                    	entity.remove();
                    	System.out.println("Creeper hunt ");
                    	ItemStack itemStack = new ItemStack(Material.SULPHUR,2,(short) 0);
//                    	Egg spiderEgg = 
                    	event.getPlayer().getInventory().addItem(itemStack);
						event.getPlayer().updateInventory();
						return;
                    }
                }
            }
			return;
		}
		
		if (l0.contains("[TRAP]"))
		{
			long actTime = plugin.getServer().getWorlds().get(0).getTime();
			if (Math.abs(actTime - lastTame)< 1000)
			{
		    	ArrayList<String> msg = new ArrayList<String>();
				msg.add("[REALMS] Timeout hunt 1 h");
				msg.add(" ");
    			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				System.out.println("[REALMS] Timeout hunt");
				return;
			}
			lastTame = plugin.getServer().getWorlds().get(0).getTime();
			Location pos = b.getLocation();
			// find required region
			Region region = findRegionAtPosition( plugin, pos);
			if (region == null)
			{
				System.out.println("NO Region found");
				return;
			}
			if (region.getType().equalsIgnoreCase("TAMER") == false)
			{
				System.out.println("Wrong Region found");
				return;
			}
        	Location tele = new Location(region.getLocation().getWorld(),region.getLocation().getX(),region.getLocation().getY(),region.getLocation().getZ());
        	tele.setY(tele.getY()-2);

			for(Entity entity : pos.getWorld().getEntities()) 
			{
				if (tele.distance(entity.getLocation()) > 7.5)
				{
					EntityType eType = entity.getType(); 
	                if( eType== EntityType.PIG) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Pig trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 90);
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.SHEEP) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Sheep trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 91);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.COW) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Cow trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 92);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.HORSE) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Horse trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 100);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.OCELOT) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Ocelot trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 98);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.WOLF) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Wolf trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 95);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
	                if( eType== EntityType.VILLAGER) 
	                {
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Villager trap ");
	//                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 120);
	////                    	Egg spiderEgg = 
	//                    	event.getPlayer().getInventory().addItem(itemStack);
	//						event.getPlayer().updateInventory();
							return;
	                    }
	                }
				}
			}
			return;	
		}
		if (l0.contains("[SPIDERSHED]"))
		{
//			if (plugin.getServ)
			Location pos = b.getLocation();
			// find required region
			Region region = findRegionAtPosition( plugin, pos);
			if (region.getType().equalsIgnoreCase("SPIDERSHED") == false)
			{
				return;
			}
        	Location tele = new Location(region.getLocation().getWorld(),region.getLocation().getX(),region.getLocation().getY(),region.getLocation().getZ());
        	tele.setY(tele.getY()-1);

			for(Entity entity : pos.getWorld().getEntities()) 
			{
				if (tele.distance(entity.getLocation()) > 7.5)
				{
					EntityType eType = entity.getType(); 
	                if( eType== EntityType.SPIDER) 
	                {
	                	
	                    double distance = entity.getLocation().distance(pos);
	
	                    if(distance <= 100) 
	                    {
	                    	entity.teleport(tele);
	                    	System.out.println("Spider trap ");
//	                    	ItemStack itemStack = new ItemStack(Material.MONSTER_EGG,1,(short) 52);
//	//                    	Egg spiderEgg = 
//	                    	event.getPlayer().getInventory().addItem(itemStack);
//							event.getPlayer().updateInventory();
							return;
	                    }
	                }
				}
			}
		}
    }
    
    
    private Location getSignBase(Block b)
    {
    	Location position = b.getLocation();
    	if (b.getRelative(BlockFace.NORTH).getType() != Material.AIR)
    	{
    		return b.getRelative(BlockFace.NORTH).getLocation();
    	}
    	if (b.getRelative(BlockFace.EAST).getType() != Material.AIR)
    	{
    		return b.getRelative(BlockFace.EAST).getLocation();
    	}
    	if (b.getRelative(BlockFace.SOUTH).getType() != Material.AIR)
    	{
    		return b.getRelative(BlockFace.SOUTH).getLocation();
    	}
    	if (b.getRelative(BlockFace.WEST).getType() != Material.AIR)
    	{
    		return b.getRelative(BlockFace.WEST).getLocation();
    	}
    	
    	return position;
    }

    /**
     * check a sign if registered.
     * when not registered write to sign list of the settlement
     * @param event
     * @param b 
     */
    private void cmdRegisterSign(PlayerInteractEvent event, Block b)
    {
    	Location pos = getSignBase(b);
    	Region region = findRegionAtPosition(plugin, pos );
    	if (region != null)
    	{
    		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
    		if (sRegion != null)
    		{
    			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
    			if (settle != null)
    			{
    				event.getPlayer().sendMessage("Register Sign to Settlement: "+settle.getName());
    				if (settle.getBuildingList().containRegion(region.getID()) == true)
    				{
	    				int BuildingId = settle.getBuildingList().getBuildingByRegion(region.getID()).getId();
	    				if (settle.getSignList().containsKey(BuildingId) == false)
	    				{
	    					Sign sBlock =	((Sign) b.getState());
	    					String[] text = sBlock.getLines();
	    					settle.getSignList().put
	    					(
	    						BuildingId, 
	    						new SignPos
	    						(
	    							BuildingId,
	    							new LocationData(
		    							b.getLocation().getWorld().getName(),
		    							b.getLocation().getX(),
		    							b.getLocation().getY(),
		    							b.getLocation().getZ()
	    							),
	    							text
	    						)
	    					);
	    				}
    				}
    			}
    		}
    	}
    	
    }
    
    private void cmdSignUpdate(PlayerInteractEvent event, Block b)
    {
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
				event.getPlayer().sendMessage("Update Sign on Settlement: "+settle.getName());
				plugin.doSignUpdate(settle);
			}
		}
    }
    
    private void cmdBookList(PlayerInteractEvent event, Block b)
    {
    	Region region = findRegionAtPosition(plugin, b.getLocation());
    	if (event.getPlayer().isOp() == false)
    	{
    		if ((region.getType().equalsIgnoreCase(BuildPlanType.LIBRARY.name()) == false)
    			&&  (region.getType().equalsIgnoreCase("BIBLIOTHEK") == false))
    		{
    			return;
    		}
    	}
		CmdRealmsBookList cmd = new CmdRealmsBookList();
		cmd.setPara(0, marketPage);
		cmd.execute(plugin, event.getPlayer());
		bookPage = cmd.getPage()+1;
    	
    }

	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
    private void onNPC(EntityInteractEvent event)
    {
    	System.out.println("[REALMS] NPC EntityInteractEvent");
    	event.setCancelled(true);
    }

}
