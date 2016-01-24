package net.krglok.realms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionCondition;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.command.CmdFeudalInfo;
import net.krglok.realms.command.CmdFeudalWarehouse;
import net.krglok.realms.command.CmdRealmsBookList;
import net.krglok.realms.command.CmdRealmsPricelistInfo;
import net.krglok.realms.command.CmdSettleAddBuilding;
import net.krglok.realms.command.CmdSettleBuildingList;
import net.krglok.realms.command.CmdSettleInfo;
import net.krglok.realms.command.CmdSettleMarket;
import net.krglok.realms.command.CmdSettleNoSell;
import net.krglok.realms.command.CmdSettleProduction;
import net.krglok.realms.command.CmdSettleReputation;
import net.krglok.realms.command.CmdSettleRequired;
import net.krglok.realms.command.CmdSettleTrader;
import net.krglok.realms.command.CmdSettleWarehouse;
import net.krglok.realms.command.RealmsPermission;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SignPos;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.manager.ReputationData;
import net.krglok.realms.manager.ReputationStatus;
import net.krglok.realms.manager.ReputationType;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.unit.Regiment;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
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
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

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
	private static String sellInv = ChatColor.DARK_PURPLE+ "Settlement SELL";
	private static String buyInv = ChatColor.DARK_PURPLE+ "Settlement BUY";
	private static String donateInv= ChatColor.DARK_PURPLE+ "DONATE";

	private Realms plugin;
	private int lastPage;
	private int marketPage;
	private int bookPage;
	private int bookId;
	private long lastHunt = 0;
	private long lastTame = 0; 
//	private ArrayList<String> donatePlayer = new ArrayList<String>();
	private boolean isSell = false;
	private boolean isBuy = false;
	
	public ServerListener(Realms plugin)
	{
		this.plugin = plugin;
		this.lastPage = 0;
		this.marketPage = 0;
		this.bookPage =0;
		this.bookId = 0;
	}

	/**
	 * suppress mob spawn in a range of a settlement. 
	 * @param event
	 */
	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST )
	public void onCreatureSpawn( CreatureSpawnEvent event )
	{
		// Check for other plugins that have cancelled the event,
		// egg spawns, spawner spawns, and neutral mobs.
		if( event.isCancelled() 
			||	( event.getSpawnReason() == SpawnReason.EGG ) 
			||	( event.getSpawnReason() == SpawnReason.SPAWNER ) 
			||	( event.getSpawnReason() == SpawnReason.SPAWNER_EGG ) 
			||	( event.getEntity() instanceof Squid ) 
			||	( event.getEntity() instanceof NPC ) 
			||	( event.getEntity() instanceof Golem ) 
//			||	( !plugin.getMobRepellentConfiguration().shouldRepelNeutralMobs() &&( event.getEntity() instanceof Animals ) ) 
			)
		{
			return;
		}
		if (plugin.getConfigData() == null)
		{
			plugin.getLog().log(Level.WARNING,"[REALMS] event onCreatureSpawn, getConfig == null ");
			return;
		}
		
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
	
	public static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy::hh:mm:ss a");
          Date date = new Date();
          return dateFormat.format(date);
    }
	
    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) 
    {
		if(plugin.getData().getOwners().containUuid(event.getPlayer().getUniqueId().toString()) == true)
		{
			Owner owner = plugin.getData().getOwners().getOwner(event.getPlayer().getUniqueId().toString());
			owner.lastposition = event.getPlayer().getLocation().toString();
			owner.lastLogOff = getDateTime();
			plugin.getData().writeOwner(owner);
		}
    	
    }
	
	
	/**
	 * send update check message to ops
	 * @param event
	 */
    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) 
    {
    	//        String IP = player.getAddress().getHostString();

    	if (plugin.isEnabled() == false)
    	{
			event.getPlayer().sendMessage(ChatColor.RED+"Plugin not Enabled !");
    		return;
    	}
    	if (event.getPlayer()== null) return;    	
		if (event.getPlayer().isOp()) 
		{
			if (plugin.getConfigData() == null)
			{
				plugin.getLog().log(Level.WARNING,"[REALMS] event onPlayerJoin, getConfig == null ");
				return;
			}
			
			String msg = "[Realms] Updatecheck : "+plugin.getConfigData().getPluginName()+" Vers.: "+plugin.getConfigData().getVersion();
			plugin.getLog().log(Level.WARNING,msg);
		}
		if (plugin.getConfigData() == null)
		{
			plugin.getLog().log(Level.WARNING,"[REALMS] event onPlayerJoin, getConfig == null ");
			return;
		}

		if ((event.getPlayer().hasPermission(RealmsPermission.USER.getValue()))
			|| (event.getPlayer().hasPermission(RealmsPermission.ADMIN.getValue()))
			)
		{
			if(plugin.getData().getOwners().containUuid(event.getPlayer().getUniqueId().toString()) == false)
			{
				if(plugin.getData().getOwners().getOwnerName(event.getPlayer().getName()) == null)
				{
					Owner owner = Owner.initDefaultOwner();
					owner.setIsNPC(false);
					owner.isUser = true;
					owner.setPlayerName(event.getPlayer().getName());
					owner.setUuid(event.getPlayer().getUniqueId().toString());
					owner.setCommonLevel(CommonLevel.COLONIST);
					owner.setNobleLevel(NobleLevel.COMMONER);
					owner.firstLogin = getDateTime();
					owner.lastLogin = getDateTime();
					owner.initColonist();
					plugin.getData().getOwners().addOwner(owner);
					plugin.getData().writeOwner(owner);
					event.getPlayer().sendMessage("Owner is inilized for you !");
	//				event.getPlayer().sendMessage("use /Realms Owner for link to your existing settlements");
					plugin.getLog().log(Level.INFO,"Owner init for "+event.getPlayer().getName());
				}
			} else
			{
				Owner owner = plugin.getData().getOwners().getOwner(event.getPlayer().getUniqueId().toString());
				owner.setIsNPC(false);
				owner.isUser = true;
				owner.setPlayerName(event.getPlayer().getName());
				if (owner.firstLogin == "")
				{
					owner.firstLogin = getDateTime();
				}
				owner.lastLogin = getDateTime();
				plugin.getData().writeOwner(owner);
			}
		} else
		{
			if(plugin.getData().getOwners().containUuid(event.getPlayer().getUniqueId().toString()) == false)
			{
				if(plugin.getData().getOwners().getOwnerName(event.getPlayer().getName()) == null)
				{
					Owner owner = Owner.initDefaultOwner();
					owner.setIsNPC(false);
					owner.isUser = false;
					owner.setPlayerName(event.getPlayer().getName());
					owner.setUuid(event.getPlayer().getUniqueId().toString());
					owner.firstLogin = getDateTime();
					owner.lastLogin = getDateTime();
					plugin.getData().getOwners().addOwner(owner);
					plugin.getData().writeOwner(owner);
					event.getPlayer().sendMessage("Player is inilized for you !");
					plugin.getLog().log(Level.INFO,"Player init for "+event.getPlayer().getName());
				}
			} else
			{
				Owner owner = plugin.getData().getOwners().getOwner(event.getPlayer().getUniqueId().toString());
				owner.setIsNPC(false);
				owner.isUser = false;
				owner.setPlayerName(event.getPlayer().getName());
				owner.lastLogin = getDateTime();
				plugin.getData().writeOwner(owner);
			}
			
		}
		return; // no OP => OUT
	}

    
    private void clickInventorySell(InventoryClickEvent event)
    {
		Player player = (Player) event.getWhoClicked();
//		player.sendMessage("Slot "+":"+event.getSlot()+":"+event.getSlotType()+":"+event.getRawSlot());
		
		if (event.getRawSlot() > 26)
		{
			player.sendMessage(ChatColor.DARK_RED+"Click on item in chest inventory");
    		event.setCancelled(true);
			return;
		}
		String sRegion = findSuperRegionAtLocation(plugin, player);
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle == null)
		{
			player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
			return;
		}
		
		
		
		ItemStack itemStack  = event.getCurrentItem();
		switch(event.getAction())
		{
		case PICKUP_ALL: 
//		case PICKUP_ONE :
//		case PICKUP_SOME :
			if (itemStack.getAmount() > 0)
			{
				double cost = plugin.getData().getPriceList().getBasePrice(itemStack.getType().name());
    			int amount = 1;
    			cost = cost * amount;
    			
    			if (plugin.economy.has(player.getName(), cost) == false)
    			{
        			player.sendMessage("You have not enough money "+":"+ConfigBasis.setStrformat2(cost, 5));
    	    		event.setCancelled(true);
    			} else
    			{
        			player.sendMessage("You bought "+itemStack.getType().name()+":"+amount+":"+ConfigBasis.setStrformat2(cost, 8));
        			plugin.economy.withdrawPlayer(player.getName(), cost).toString();
        			player.getInventory().addItem(new ItemStack(itemStack.getType(),amount));
        	    	if (itemStack.getAmount() == 1)
        	    	{
        	    		event.getInventory().remove(itemStack);
//        	    		setItem(event.getRawSlot(), new ItemStack(Material.AIR,1));
        	    	} else
        	    	{
        	    		itemStack.setAmount(itemStack.getAmount()-amount);
        	    	}
        			player.updateInventory();
        			event.setCancelled(true);
       				settle.getReputations().addValue(ReputationType.TRADE, player.getName(), "SELL", ConfigBasis.TRADE_POINT);
					player.sendMessage(ChatColor.GREEN+"You get Reputation for your trade");
    			}
			}
			break;
		case PICKUP_HALF:
			if (itemStack.getAmount() > 1)
			{
    			double cost = plugin.getData().getPriceList().getBasePrice(itemStack.getType().name());
    			int amount = itemStack.getAmount() / 2;
    			cost = cost * amount;
    			if (plugin.economy.has(player.getName(), cost) == false)
    			{
        			player.sendMessage("You have not enough money "+":"+ConfigBasis.setStrformat2(cost, 5));
    	    		event.setCancelled(true);
    			} else
    			{
        			player.sendMessage("You bought "+itemStack.getItemMeta().getDisplayName()+":"+amount+":"+ConfigBasis.setStrformat2(cost, 5));
        			plugin.economy.withdrawPlayer(player.getName(), cost).toString();
        			player.getInventory().addItem(new ItemStack(itemStack.getType(),amount));
    	    		itemStack.setAmount(itemStack.getAmount()-amount);
        			player.updateInventory();
    	    		event.setCancelled(true);
    			}
			}
			break;
		default:
    		event.setCancelled(true);
			break;
		}
    }

    
    private void clickInventoryBuy(InventoryClickEvent event)
    {
		Player player = (Player) event.getWhoClicked();
//		System.out.println("RawSlot :"+event.getRawSlot());
		if (event.getRawSlot() < 27)
		{
			player.sendMessage(ChatColor.DARK_RED+"Click on item in your inventory");
			event.setCancelled(true);
			return;
		}
		String sRegion = findSuperRegionAtLocation(plugin, player);
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle == null)
		{
			player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
			return;
		}
		
		ItemStack itemStack  = event.getCurrentItem();
		switch(event.getAction())
		{
		case PICKUP_ALL: 
//		case PICKUP_ONE :
//		case PICKUP_SOME :
			if (itemStack.getAmount() > 0)
			{
				String ItemRef = ConfigBasis.checkItemRefOut(itemStack);
    			double cost = plugin.getData().getPriceList().getBasePrice(ItemRef);
    			if (cost > 0.0)
    			{
	    			int amount = 1;
	    			cost = cost * amount * ConfigBasis.SETTLE_BUY_FACTOR;
	    			if (settle.getBank().getKonto() <= cost) 
	    			{
	        			player.sendMessage("The settlement has not enough money "+":"+ConfigBasis.setStrformat2(cost, 5));
	    	    		event.setCancelled(true);
	    			} else
	    			{
	        			player.sendMessage("You sell "+itemStack.getType()+":"+amount+":"+ConfigBasis.setStrformat2(cost, 8));
	        			settle.getBank().withdrawKonto(cost, "BuyShop", settle.getId());
	        			settle.getWarehouse().depositItemValue(itemStack.getType().name(), amount);
	        			plugin.economy.depositPlayer(player.getName(), cost).toString();
	        			event.getInventory().addItem(new ItemStack(itemStack.getType(),amount));
	        	    	if (itemStack.getAmount() == 1)
	        	    	{
	        	    		player.getInventory().remove(itemStack);
	//        	    		setItem(event.getRawSlot(), new ItemStack(Material.AIR,1));
	        	    	} else
	        	    	{
	        	    		itemStack.setAmount(itemStack.getAmount()-amount);
	        	    	}
	        			player.updateInventory();
	        			event.setCancelled(true);
	       				settle.getReputations().addValue(ReputationType.TRADE, player.getName(), "BUY", ConfigBasis.TRADE_POINT);
						player.sendMessage(ChatColor.GREEN+"You get Reputation for your trade");
	    			}
    			} else
    			{
        			event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"No price set in global pricelist! "+ItemRef);
    			}
			}
			break;
		case PICKUP_HALF:
			if (itemStack.getAmount() > 1)
			{
    			double cost = plugin.getData().getPriceList().getBasePrice(itemStack.getType().name());
    			int amount = itemStack.getAmount() / 2;
    			cost = cost * amount;
    			if (cost > 0.0)
    			{
	    			if (settle.getBank().getKonto() <= cost) 
	    			{
	        			player.sendMessage("The settlement has not enough money "+":"+ConfigBasis.setStrformat2(cost, 5));
	    	    		event.setCancelled(true);
	    			} else
	    			{
	        			player.sendMessage("You sell "+itemStack.getItemMeta().getDisplayName()+":"+amount+":"+ConfigBasis.setStrformat2(cost, 5));
	        			settle.getBank().withdrawKonto(cost, "BuyShop", settle.getId());
	        			plugin.economy.depositPlayer(player.getName(), cost).toString();
	        			event.getInventory().addItem(new ItemStack(itemStack.getType(),amount));
	    	    		itemStack.setAmount(itemStack.getAmount()-amount);
	        			player.updateInventory();
	    	    		event.setCancelled(true);
	    			}
				} else
				{
	    			event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"No price set in global pricelist! "+itemStack.getType());
				}
			}
			break;
		default:
    		event.setCancelled(true);
			break;
		}
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(InventoryClickEvent event)
    {
    	if(event.getInventory().getTitle().contains(sellInv))
    	{
    		clickInventorySell(event);
		}
    	
    	if(event.getInventory().getTitle().contains(buyInv))
    	{
    		clickInventoryBuy(event);
    	}

    }
    
    
    
    /**
     * do special action on chest in settlement
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event)
    {
//    	return;
    	if (event.getPlayer() instanceof Player)
    	{
//    		System.out.println(event.getInventory().getTitle());
//    		System.out.println(event.getPlayer().getOpenInventory().getTitle());
    		if(event.getInventory().getTitle().contains(donateInv))
    		{
    			checkSettleChest(event);
    		}
    		
    		if(event.getInventory().getTitle().contains(sellInv))
    		{
    			Player player = (Player) event.getPlayer();
    			String sRegion = findSuperRegionAtLocation(plugin, player);
    			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
    			if (settle == null)
    			{
    				player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
    				return;
    			}
    			for (ItemStack itemStack : event.getInventory().getContents())
    			{
    				if (itemStack != null)
    				{
	    				if (itemStack.getType() != Material.AIR)
	    				{
	    					settle.getWarehouse().depositItemValue(itemStack.getType().name(), itemStack.getAmount());
//	    					System.out.println("Warehouse restore : "+itemStack.getType().name()+":"+itemStack.getAmount());
	    				}
    				}
    			}

    			isSell = false;
    		}
    		if(event.getInventory().getTitle().contains(buyInv))
    		{
    			Player player = (Player) event.getPlayer();
    			String sRegion = findSuperRegionAtLocation(plugin, player);
    			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
    			if (settle == null)
    			{
    				player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
    				return;
    			}
    			for (ItemStack itemStack : event.getInventory().getContents())
    			{
    				if (itemStack != null)
    				{
	    				if (itemStack.getType() != Material.AIR)
	    				{
	    					settle.getWarehouse().depositItemValue(itemStack.getType().name(), itemStack.getAmount());
//	    					System.out.println("Warehouse store : "+itemStack.getType().name()+":"+itemStack.getAmount());
	    				}
    				}
    			}

    			isBuy = false;
    		}
    	}
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryOpen(InventoryOpenEvent event)
    {
    	if (event.getInventory().getType() == InventoryType.CHEST)
    	{
    		if(event.getInventory().getTitle().contains(sellInv))
    		{
    			return;
    		}
    		if(event.getInventory().getTitle().contains(buyInv))
    		{
    			return;
    		}
    		if(event.getInventory().getTitle().contains(donateInv))
    		{
    			return;
    		}
    		Player player = (Player) event.getPlayer();
			int regionId = findRegionIdAtLocation(plugin, player);
			Region region = plugin.stronghold.getRegionManager().getRegionByID(regionId);
			if (region != null)
			{
				if (region.getOwners().contains(event.getPlayer().getName()) == false)
				{
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"You are not the owner");
				}
			}
    	}
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
    	if (event.getPlayer().isOp() == false)
    	{
			if (event.getPlayer().hasPermission(RealmsPermission.ADMIN.getValue().toLowerCase()) == false)
			{
				if (event.getPlayer().hasPermission(RealmsPermission.USER.getValue().toLowerCase()) == false)
				{
					event.getPlayer().sendMessage(ChatColor.RED+"You not have permission realms.user !");
					event.getPlayer().sendMessage(ChatColor.YELLOW+"Contact the OP or ADMIN for setup permission.");
					return ;
				}
			}
    	}
    	Block b = event.getClickedBlock();
    	if (b != null)
    	{
    		
    		/**
    		 * special for Realms Catapult
    		 */
	    	if (b.getType() == Material.IRON_BLOCK)
	    	{
				event.getPlayer().sendMessage("You click a iron block");
	    		doCatapult(event, b);
	    		return;
	    	}
	    	
	    	// Wallsign action
//	    	ArrayList<String> msg = new ArrayList<String>();
	    	if (b.getType() == Material.WALL_SIGN)
	    	{
	    		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
	    		{
	    	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	    	{
//	    	    		cmdRegisterSign(event, b);
	    	    	} else
	    	    	{
	    	    		doWallSign(event, b);
	    	    	}
	    		}
	    		if (event.getAction() == Action.LEFT_CLICK_BLOCK)
	    		{
	    	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	    	{
//	    	    		cmdSignUpdate(event, b);
	    	    	} else
	    	    	{
	    	    		doLeftWallSign(event,b);
	    	    	}
	    		}
	    		return;
	    	}
	    	
	    	// signpost action
	    	if (b.getType() == Material.SIGN_POST)
	    	{
	    		Sign sign = (Sign) b.getState();
	    		String l0 = sign.getLine(0);
//	    		String l1 = sign.getLine(1);
	    		if (l0.contains("[BUILD]"))
	    		{
	    			cmdBuildat(event, b);
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

//	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
//	    	{
////    			event.getPlayer().sendMessage("You hold a Blazerod :");
//	    		doDoortest(event, b);
//	    		return;
//	    	}
	    	
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
	    	Building building = null;
	    	Settlement settle = null;
	    	int regionId = 0;
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
		    			if (plugin.getData().getBuildings().containRegion(region.getID()))
		    			{
		    				building = plugin.getData().getBuildings().getBuildingByRegion(region.getID());
		    				msg.add(region.getID()+":"+region.getType()+" Building :"+building.getId()+":"+building.getOwnerId());
		    			} else
		    			{
		    				regionId = region.getID();
			    			if (region.getOwners().size() > 0)
			    			{
			    				msg.add(region.getID()+":"+region.getType()+":"+region.getOwners().get(0));
			    			} else
			    			{
			    				msg.add(region.getID()+":"+region.getType()+":");
			    			}
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
				if (plugin.getData().getSettlements().containsName(sRegion.getName()) == true)
				{
					settle = plugin.getData().getSettlements().findName(sRegion.getName());
					msg.add(sRegion.getName()+":"+sRegion.getType()+": ["+settle.getId()+"] :"+settle.getOwnerId());
					if (event.getPlayer().isSneaking() == true)
					{
						if (event.getPlayer().isOp())
						{
							if (sRegion.getMembers().containsKey(event.getPlayer().getName())== false)
							{
								sRegion.addMember(event.getPlayer().getName(), new ArrayList<String>());
							}
						}
					}
				} else
				{
					if (sRegion.getOwners().size() > 0)
					{
						msg.add(sRegion.getName()+":"+sRegion.getType()+":"+sRegion.getOwners().get(0));
					} else
					{
						msg.add(sRegion.getName()+":"+sRegion.getType()+":");
					}
				}
			}
			if (event.getPlayer().isSneaking() == true)
			{
				if (settle != null)
				{
					if (building == null)
					{
						if (event.getPlayer().isOp())
						{
							CmdSettleAddBuilding  cmd = new CmdSettleAddBuilding();
							cmd.setPara(0, settle.getId());
							cmd.setPara(1, regionId);
							cmd.execute(plugin, event.getPlayer());
						}
					}
				}
			}
			
			plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
		}    	
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
	    	if ((settleType == SettleType.HAMLET)
	    		|| (settleType == SettleType.TOWN)
	    		|| (settleType == SettleType.CITY)
	    		|| (settleType == SettleType.METROPOLIS)
	    		)
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
	protected SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if ((sRegion.getType().equalsIgnoreCase( SettleType.HAMLET.name()) )
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.TOWN.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.CITY.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.METROPOLIS.name()))
	    		)
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
	private int findRegionAtLocation(Realms plugin, Player player)
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
		return 0;
	}
	
	
	private BuildPlanType findBuildingTypeAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
		Region region = findRegionAtPosition( plugin, position);
	    if ( region != null)
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return bType;
	    	}
	    }
		return BuildPlanType.NONE;
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
	protected Region findRegionAtPosition(Realms plugin,Location position)
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
							if (buildAt(pos, "WHEAT", player, msg,"0"))
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
    
    private boolean buildAt(Location pos, String name, Player player, ArrayList<String> msg,String l2)
    {
    	Settlement settle = null;
    	LocationData iLoc = null;
    	int settleId = 0;
    	try
		{
        	settleId = Integer.valueOf(l2);
		} catch (Exception e)
		{
			settleId = 0;
		}
    	if (settleId > 0)
    	{
    		settle = plugin.getData().getSettlements().getSettlement(settleId);
    	} else
    	{
			String sRegion = findSuperRegionAtLocation(plugin, player); 
			settle = plugin.getRealmModel().getSettlements().findName(sRegion);
    	}
		iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY(), pos.getZ()+1);
		if (settle != null)
		{
			if (BuildPlanType.getBuildPlanType(name) != BuildPlanType.NONE)
			{
//				BuildPlanMap buildPLan = plugin.getRealmModel().getData().readTMXBuildPlan(BuildPlanType.getBuildPlanType(name), 4, -1);
				if (checkBuild(pos, name, player, msg,settle))
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
    
    private boolean checkBuild(Location pos, String name, Player player, ArrayList<String> msg, Settlement settle)
    {
//		LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY()+1, pos.getZ()+1);
//		String sRegion = findSuperRegionAtLocation(plugin, player); 
//		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
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

    private boolean checkAt(Location pos, String name, Player player, ArrayList<String> msg, String l2)
    {
//		LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX(), pos.getY()+1, pos.getZ()+1);
    	Settlement settle ;
    	int settleId = 0;
    	try
		{
        	settleId = Integer.valueOf(l2);
		} catch (Exception e)
		{
			settleId = 0;
		}
    	if (settleId > 0)
    	{
    		settle = plugin.getData().getSettlements().getSettlement(settleId);
    	} else
    	{
			String sRegion = findSuperRegionAtLocation(plugin, player); 
			settle = plugin.getRealmModel().getSettlements().findName(sRegion);
    	}
//		String sRegion = findSuperRegionAtLocation(plugin, player); 
//		settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle != null)
		{
			if (BuildPlanType.getBuildPlanType(name) != BuildPlanType.NONE)
			{
				if (checkBuild(pos, name, player, msg,settle))
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
		event.getPlayer().getLocation();
		Inventory inventory = event.getInventory();
		
		int regionId = findRegionAtLocation(plugin, player);
		Building building = plugin.getData().getBuildings().getBuildingByRegion(regionId);
		AbstractSettle aSettle = null; 
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		// get Settlement or Lehen as AbstractSettlement
		if (building.getSettleId() > 0)
		{
			aSettle = plugin.getData().getSettlements().getSettlement(building.getSettleId());
		} 
		if (building.getLehenId() > 0)
		{
			aSettle = plugin.getData().getLehen().getLehen(building.getLehenId());
		} 
			
		new ItemList();
		if (aSettle != null)
		{
			if ((building.getBuildingType() == BuildPlanType.HALL)
				|| (building.getBuildingType() == BuildPlanType.TOWNHALL)
				|| (building.getBuildingType() == BuildPlanType.KEEP)
				|| (building.getBuildingType() == BuildPlanType.CASTLE)
				|| (building.getBuildingType() == BuildPlanType.STRONGHOLD)
				|| (building.getBuildingType() == BuildPlanType.PALACE)
				)
				
			{
//				if (aSettle.getWarehouse().getFreeCapacity() < 10)
//				{
//					player.sendMessage(ChatColor.RED+"No Capacy free in Warehouse");
//					for (ItemStack itemStack :inventory.getContents())
//					{
//						if (itemStack != null)
//						{
//							player.getInventory().addItem(new ItemStack(itemStack.getType(),itemStack.getAmount()));
//						}
//					}
//					player.updateInventory();
//					inventory.clear();
//					return;
//				}
				
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
								if (aSettle.getWarehouse().depositItemValue(name, itemStack.getAmount()) == false)
								{
									System.out.println("[REALMS) DONATE Warehouse deposit FALSE");
								}
								if (ConfigBasis.initFoodMaterial().containsKey(name))
								{
									aSettle.getReputations().addValue(ReputationType.FOOD, player.getName(), name, ConfigBasis.VALUABLE_POINT);
									System.out.println(" REPUTATION VALUABLE : "+name+": 1");
									player.sendMessage(ChatColor.GREEN+"You get Reputation for your donation");
									
								} else 
								if (ConfigBasis.initValuables().containsKey(name))
								{
									aSettle.getReputations().addValue(ReputationType.VALUABLE, player.getName(), name, ConfigBasis.VALUABLE_POINT);
									System.out.println(" REPUTATION VALUABLE : "+name+": 1");
									player.sendMessage(ChatColor.GREEN+"You get Reputation for your donation");
									
								} else 
								{
									if (aSettle.getReputations().containsKey(ReputationData.getRefName(player.getName(), ReputationType.DONATION)))
									{
										aSettle.getReputations().addValue(ReputationType.DONATION, player.getName(), name, ConfigBasis.DONATION_POINT);
											System.out.println(" REPUTATION DONATION : "+name+": 1");
											player.sendMessage(ChatColor.GREEN+"You get Reputation for your donation");
									} else
									{
										aSettle.getReputations().addValue(ReputationType.DONATION, player.getName(), name, ConfigBasis.DONATION_POINT);
										System.out.println(" REPUTATION DONATION : "+name+": 1");
										player.sendMessage(ChatColor.GREEN+"You get Reputation for your donation");
									}
								}
								if (aSettle.getRequiredProduction().containsKey(name))
								{
									aSettle.getReputations().addValue(ReputationType.REQUIRED, player.getName(), name, ConfigBasis.REQUIRED_POINT);
									System.out.println("REPUTATION REQUIRED: "+name+": 1");
									player.sendMessage(ChatColor.GREEN+"You get Reputation for your donation");
									
								} 
//								System.out.println("HALL : "+itemStack.getType().name()+":"+itemStack.getAmount()+"OpenChests :"+donatePlayer.size());
							}
						}
						inventory.clear();
						switch (aSettle.getSettleType())
						{
						case HAMLET :
						case TOWN :
						case CITY :
						case METROPOLIS:
							{
								plugin.getData().writeSettlement((Settlement) aSettle);
								break;
							}
						case LEHEN_1 :
						case LEHEN_2 :
						case LEHEN_3 :
						case LEHEN_4 :
							{
								plugin.getData().writeLehen((Lehen) aSettle);
								break;
							}
						case CAMP :
							{
								plugin.getData().writeRegiment((Regiment) aSettle);
								break;
							}
						default:
							break;
						}
					}
				}
			}
		}
    }
    
    private void cmdTechBook(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		sign.getLine(0);
		String l1 = sign.getLine(1);
		sign.getLine(2);
		sign.getLine(3);
		if (l1 != "")
		{
			if (AchivementName.contains(l1))
			{
				AchivementName aName = AchivementName.valueOf(l1);
				if (aName != AchivementName.NONE)
				{
					ItemStack item = event.getPlayer().getItemInHand();
					if (item.getType() != Material.BOOK)
					{
						event.getPlayer().sendMessage(ChatColor.YELLOW+"You must hold a BOOK in your hand ");
						return;
					}
					List<String> lore = new ArrayList<String>(); //item.getItemMeta().getLore();
					lore.add(aName.name());
					lore.add("REALMS Techbook");
					item.getItemMeta().setLore(lore);
					setName(item, "Techbook", lore);				
				}
				
			} else
			{
				event.getPlayer().sendMessage(ChatColor.YELLOW+"Wrong Achivement Name ");
			}
		}
    }

    private void cmdKnowledge(PlayerInteractEvent event, Block b)
    {
    	Player player = event.getPlayer();
		BuildPlanType region = findBuildingTypeAtLocation(plugin, player);
		
		
		if (region==BuildPlanType.BIBLIOTHEK)
		{
			ItemStack item = event.getPlayer().getItemInHand();
			if (item.getType() != Material.BOOK)
			{
				event.getPlayer().sendMessage(ChatColor.YELLOW+"You must hold a BOOK in your hand ");
				return;
			}
			List<String> lore = item.getItemMeta().getLore();
			if (lore == null) { return; }
			if (lore.size() < 2) { return; }
			String l0 = lore.get(0);
			String l1 = lore.get(1);
			if (l1.contains("REALMS Techbook"))
			{
				if (AchivementName.contains(l0) == false)
				{
					return;
				}
				AchivementName aName = AchivementName.valueOf(l0);
				if (aName != AchivementName.NONE)
				{
					switch (aName)
					{
					case HALL:
					case CARPENTER:
					case CABINETMAKER:
					case BAKERY:
					case HOESHOP:
					case KNIFESHOP:
					case WORKSHOP:
					case TANNERY:
					case BLACKSMITH:
					case GUARDHOUSE:
					case TECH0 :
					case TECH1 :
					case TECH2 :
					case TECH3 :
					case TECH4 :
						Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
						if (owner != null)
						{
							owner.getAchivList().add(new Achivement(AchivementType.BOOK, aName,true));
							plugin.getData().writeOwner(owner);
							event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"You earn the achivement "+aName.name());
							lore.set(0,"used");
							lore.add(l0);
							setName(item, "Techbook", lore);				
						}
						break;
					default :
						event.getPlayer().sendMessage(ChatColor.YELLOW+"You only get Techlevel 1 -4 ");
						break;
					}
				}
			}
			item.getItemMeta().setLore(lore);
			
		}
		if (region ==BuildPlanType.LIBRARY)
		{
			ItemStack item = event.getPlayer().getItemInHand();
			if (item.getType() != Material.BOOK)
			{
				event.getPlayer().sendMessage(ChatColor.YELLOW+"You must hold a Techbook in your hand ");
				return;
			}
			List<String> lore = item.getItemMeta().getLore();
			if (lore == null) { return; }
			if (lore.size() < 2) { return; }
			String l0 = lore.get(0);
			String l1 = lore.get(1);
			if (l1.contains("REALMS Techbook"))
			{
				if (AchivementName.contains(l0) == false)
				{
					return;
				}
				AchivementName aName = AchivementName.valueOf(l0);
				if (aName != AchivementName.NONE)
				{
					switch (aName)
					{
					case TOWNHALL:
					case SMELTER:
					case BARRACK:
					case TOWER:
					case HEADQUARTER:
					case TECH5 :
					case TECH6 :
					case TECH7 :
						Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
						if (owner != null)
						{
							owner.getAchivList().add(new Achivement(AchivementType.BOOK, aName));
							plugin.getData().writeOwner(owner);
							event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"You earn the achivement "+aName.name());
							lore.add(l0);
							lore.set(0, "used Techbook");
						}
						break;
					default :
						event.getPlayer().sendMessage(ChatColor.YELLOW+"You only get Techlevel 5 -7 ");
						break;
					}
				}
			}
			item.getItemMeta().setLore(lore);
			
		}
    }
    
    private void doWallSign(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		String l0 = sign.getLine(0);
		String l1 = sign.getLine(1);
		sign.getLine(2);
		sign.getLine(3);
		if (l0.equals(""))
		{
	    	Location pos = findSignBase(b);

			Region region = findRegionAtPosition(plugin, pos);
			if (region != null)
			{
				cmdBuildingInfo(event, b, region);
			} else
			{
				event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"No region found !");
			}
			return;
		}		
		if (l0.equals("[RECIPE]"))
		{
	    	Location pos = findSignBase(b);

			Region region = findRegionAtPosition(plugin, pos);
			if (region != null)
			{
				cmdBuildingRecipe(event, b, region);
			} else
			{
				event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"No region found !");
			}
			return;
		}		

		if (l0.contains("[TRAP]"))
		{
			cmdSignPost(event,b);
			return;
		}		
		if (l0.contains("[HUNT]"))
		{
			cmdSignPost(event,b);
			return;
		}		
		if (l0.contains("[SPIDERSHED]"))
		{
			cmdSignPost(event,b);
			return;
		}		

		if (l0.contains("[ACQUIRE]"))
		{
			cmdAcquire(event, b);
			return;
		}

		if (l0.contains("[REPUTATION]"))
		{
			cmdReputation(event, b);
			return;
		}
		
		if (l0.contains("[SELL]"))
		{
			event.getPlayer().sendMessage("The settlement SELL items to you ");
			cmdSell(event, b, l1);
			return;
		}

		if (l0.contains("[BUY]"))
		{
			event.getPlayer().sendMessage("The settlement BUY items from you ");
			cmdBuy(event, b);
			return;
		}

		if (l0.contains("[TECHBOOK]"))
		{
			if (event.getPlayer().isOp())
			{
				event.getPlayer().sendMessage("An achivement book is generated ");
				cmdTechBook(event, b);
			} else
			{
				event.getPlayer().sendMessage(ChatColor.RED+"You are not an OP ");

			}
			return;
		}
		
		if (l0.contains("[KNOWLEDGE]"))
		{
			cmdKnowledge(event, b);
			return;
		}

		if (l0.contains("[PRICELIST]"))
		{
			CmdRealmsPricelistInfo cmd = new CmdRealmsPricelistInfo();
			cmd.setPara(0, 1);
			if (cmd.canExecute(plugin, event.getPlayer()))
			{
//				cmdWare.execute(plugin, event.getPlayer());
				cmd.execute(plugin, event.getPlayer());
			}
			
			return;
		}
		

		if (l0.contains("[WAREHOUSE]"))
		{
//			cmdBuildPlanBook(event);
	    	int regionId = findRegionAtLocation(plugin, event.getPlayer());
	    	Building building = plugin.getData().getBuildings().getBuildingByRegion(regionId);
			if (building.getSettleId() > 0)
			{
				Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(building.getSettleId());
				if (settle != null)
				{
					CmdSettleWarehouse cmdWare = new CmdSettleWarehouse();
					cmdWare.setPara(0, settle.getId());
					cmdWare.setPara(1, this.lastPage);
					if (cmdWare.canExecute(plugin, event.getPlayer()))
					{
	//					cmdWare.execute(plugin, event.getPlayer());
						cmdWare.execute(plugin, event.getPlayer());
						lastPage = cmdWare.getPage()+1;
					}
				}
			} else
			{
				Lehen lehen = plugin.getRealmModel().getData().getLehen().getLehen(building.getLehenId());
				if (lehen != null)
				{
					CmdFeudalWarehouse cmdWare = new CmdFeudalWarehouse();
					cmdWare.setPara(0, lehen.getId());
					cmdWare.setPara(1, this.lastPage);
					if (cmdWare.canExecute(plugin, event.getPlayer()))
					{
	//					cmdWare.execute(plugin, event.getPlayer());
						cmdWare.execute(plugin, event.getPlayer());
						lastPage = cmdWare.getPage()+1;
					}
				}
			}
			return;
		}
		if (l0.contains("[INFO]"))
		{
			cmdInfo(event);
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
				if (cmd.canExecute(plugin, event.getPlayer()))
				{
					cmd.execute(plugin, event.getPlayer());
				}
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
				if (cmd.canExecute(plugin, event.getPlayer()))
				{
					cmd.execute(plugin, event.getPlayer());
					marketPage = cmd.getPage()+1;
				}
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
				if (cmd.canExecute(plugin, event.getPlayer()))
				{
					cmd.execute(plugin, event.getPlayer());
					marketPage = cmd.getPage()+1;
				}
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
				if (cmd.canExecute(plugin, event.getPlayer()))
				{
					cmd.execute(plugin, event.getPlayer());
					marketPage = cmd.getPage()+1;
				}
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
			return;
		}

		
		
		if (l0.contains("[REQUIRE]"))
		{
//			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
			String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
			if (settle != null)
			{
				CmdSettleRequired  cmd = new CmdSettleRequired();
				if (cmd.canExecute(plugin, event.getPlayer()))
				{
					cmd.setPara(0, settle.getId());
					cmd.setPara(1, 1);  // first page only
					cmd.execute(plugin, event.getPlayer());
//					lastPage = cmd.getPage()+1;
				} else
				{
					event.getPlayer().sendMessage(ChatColor.RED+"Can't  execute command!");
				}
			}
//			cmdRequiredBook(event);
		}

		if (l0.contains("[WORKSHOP]"))
		{
			cmdWorkshop(event, b);
			return;
		}

		if (l0.contains("[TRAIN]"))
		{
	    	ArrayList<String> msg = new ArrayList<String>();
	    	int regionId = findRegionAtLocation(plugin, event.getPlayer());
	    	Building building = plugin.getData().getBuildings().getBuildingByRegion(regionId);
			sign.setLine(2, String.valueOf(""));
			sign.update();
	    	
			if (building.getSettleId() > 0)
			{
				Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(building.getSettleId());
				if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500 )
				{
					sign.setLine(1, String.valueOf(building.getTrainType().name()));
					sign.update();
					// incresase maxTrain start training process
					building.addMaxTrain(1);
					plugin.getData().writeBuilding(building);
					msg.add("Settlement ["+settle.getId()+"] : "
							+ChatColor.YELLOW+settle.getName()
							+ChatColor.GREEN+" Age: "+settle.getAge()
							+":"+settle.getProductionOverview().getCycleCount());
					msg.add("Building: "+building.getBuildingType().name());
					msg.add("Train   : "+ChatColor.YELLOW+building.getTrainType().name());
					msg.add("Need    : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),4)+" Cycles");
					sign.setLine(2, String.valueOf("count: "+building.getMaxTrain()));
					sign.update();
				} else
				{
					msg.add("Building: "+building.getBuildingType().name());
					msg.add("Train   : "+ChatColor.RED+"not possible !");
				}
				plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
			}
			if (building.getLehenId() > 0)
			{
				Lehen lehen = plugin.getData().getLehen().getLehen(building.getLehenId());
				if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500 )
				{
					sign.setLine(1, String.valueOf(building.getTrainType().name()));
					sign.update();
					// incresase maxTrain start training process
					building.addMaxTrain(1);
					plugin.getData().writeBuilding(building);
					msg.add("Lehen ["+lehen.getId()+"] : "
							+ChatColor.YELLOW+lehen.getName()
							);
					msg.add("Building: "+building.getBuildingType().name());
					msg.add("Train   : "+ChatColor.YELLOW+building.getTrainType().name());
					msg.add("Need    : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),4)+" Cycles");
					sign.setLine(2, String.valueOf("count: "+building.getMaxTrain()));
					sign.update();
				} else
				{
					msg.add("Building: "+building.getBuildingType().name());
					msg.add("Train   : "+ChatColor.RED+"not possible !");
				}
				plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
			}
			return;
		}
		if (l0.contains("[BOOK]"))
		{
	    	System.out.println("Book Sign "+bookId);
	    	Region region = findRegionAtPosition(plugin, event.getPlayer().getLocation());
	    	if (event.getPlayer().isOp() == false)
	    	{
				if ((region.getType().equalsIgnoreCase(BuildPlanType.LIBRARY.name()) == false)
					&&  (region.getType().equalsIgnoreCase("BIBLIOTHEK") == false)
					&&  (region.getType().equalsIgnoreCase("HALL") == false)
					)
				{
					event.getPlayer().sendMessage(ChatColor.GREEN+"The Booklist is available in BIBLIOTHEK or LIBRARY");
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
	    	    		event.getPlayer().getInventory();
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
			return;
		}
		if (l0.contains("[DONATE]"))
		{
			event.getPlayer().sendMessage(ChatColor.GREEN+"Put items in chest for donation !");
			event.getPlayer().sendMessage(ChatColor.GREEN+"You will earn some reputation");
			cmdDonate(event, b);
			return;
		}
    	
    }

    private void doLeftWallSign(PlayerInteractEvent event, Block b)
    {
		Sign sign = (Sign) b.getState();
		String l0 = sign.getLine(0);
		String l1 = sign.getLine(1);
		String l2 = sign.getLine(2);
		sign.getLine(3);

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
					ConfigBasis.setStrformat2(price,7);
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
		sign.getLine(1);
    	
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
    
    
    private Location findSignBase(Block b)
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
    	Location pos = findSignBase(b);
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
//    	System.out.println("[REALMS] NPC EntityInteractEvent");
    	event.setCancelled(true);
    }

	private void shotArrow(Block b)
	{
//		Location eyelocation = player.getEyeLocation();
//		Vector vec = player.getLocation().getDirection();
//		Location frontlocation = eyelocation.add(vec);
//
//Entity bullet = player.getWorld().spawnEntity(frontLocation, EntityType.SNOWBALL);
////Spawns a snowball at front location
//bullet.setVelocity(frontlocation.getDirection().multiply(4.0));
////Sets velocity of bullet to the direction that the player is facing.

        //Calculate trajectory of the arrow
        Location loc = b.getRelative(BlockFace.UP, 2).getLocation();
//        Location playerLoc = player.getLocation();
        Vector direction = new Vector(loc.getX()-30, loc.getY()+30, loc.getZ()-30);
//        
//        //Spawn and set velocity of the arrow
        double speed = 3.0;
//        Entity tnt = b.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
//        tnt.setVelocity(vel.multiply(speed));
//        tnt.setFireTicks(150);
        Arrow arrow = b.getWorld().spawnArrow(loc, direction, (float) (1.0 * speed), 12);
        arrow.setFireTicks(250);
//        arrow.setVelocity(vel.multiply(speed));
        
	}

	private void cmdBuildat(PlayerInteractEvent event, Block b)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		Sign sign = (Sign) b.getState();
		sign.getLine(0);
		String l1 = sign.getLine(1);
		String l2 = sign.getLine(2);
//			System.out.println("SignPost");
		if (l1 != "")
		{
			if (l2 == "") {	l2 = "0";	}
			Location pos = b.getLocation();
	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK)
	    	{
    			System.out.println("Check");
	    		checkAt(pos, l1, event.getPlayer(), msg,l2);
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
	    			if (buildAt( pos, l1, event.getPlayer(), msg,l2))
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
	}
	
	/**
	 * out of order 
	 * 
	 * @param event
	 * @param b
	 */
	private void cmdWorkshop(PlayerInteractEvent event, Block b)
	{
		Owner owner = plugin.getData().getOwners().getOwner(event.getPlayer().getUniqueId().toString());
		if (owner == null)
		{
			event.getPlayer().sendMessage(ChatColor.RED+"You are not a regular Owner !");
			return;
		}
		String sRegion = findSuperRegionAtLocation(plugin, event.getPlayer()); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		Integer regionId = findRegionIdAtLocation(plugin, event.getPlayer());
		Building building = settle.getBuildingList().getBuildingByRegion(regionId);
    	ArrayList<String> msg = new ArrayList<String>();

    	if (event.getPlayer().isOp() == false)
    	{
	    	if (building.getOwnerId() != owner.getId())
			{
				msg.add("You are not the owner");
				plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
				
			}
    	}
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
	
	
	private void cmdBuildingInfo(PlayerInteractEvent event, Block b, Region region)
	{
		Building building = plugin.getData().getBuildings().getBuildingByRegion(region.getID());
		if (building != null)
		{
			b.getState();
			String l0 = "";
			String l1 = "";
			String l2 = "";
			String l3 = "";

			Sign sBlock =	((Sign) b.getState());

			l0 = building.getHsRegionType();
			
			if(BuildPlanType.getBuildGroup(building.getBuildingType())== ConfigBasis.BUILDPLAN_GROUP_HOME)
			{
				Iterator<NpcData> npcs = plugin.getData().getNpcs().getBuildingNpc(building.getId()).values().iterator();
				
				if (npcs.hasNext())
				{
					l1 = npcs.next().getName();
				}
				if (npcs.hasNext())
				{
					l2 = npcs.next().getName();
				}
				if (npcs.hasNext())
				{
					l3 = npcs.next().getName();
				}
			} else
			{
				l1 = "Worker:"+building.getWorkerInstalled();
				ItemArray products = building.produce(plugin.getServerData());
				l2 = "Prod:";
				if (products.size() > 0)
				{
					l2 = l2 + products.get(0).ItemRef()+":"+products.get(0).value();
				}
				l3 = "Prod:";
				if (products.size() > 1)
				{
					l3 = l3 + products.get(1).ItemRef()+":"+products.get(1).value();
				}
			}
			sBlock.setLine(0, l0);
			sBlock.setLine(1, l1);
			sBlock.setLine(2, l2);
			sBlock.setLine(3, l3);
			sBlock.update(true);
		} else
		{
			event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"No building found !");
		}
	}

	
	private void cmdBuildingRecipe(PlayerInteractEvent event, Block b, Region region)
	{
		Building building = plugin.getData().getBuildings().getBuildingByRegion(region.getID());
		if (building != null)
		{
			b.getState();
			String l0 = "[RECIPE]";
			String l1 = "1:";
			String l2 = "2:";
			String l3 = "3:";

			Sign sBlock =	((Sign) b.getState());

			l0 = building.getHsRegionType();
			
			if(BuildPlanType.getBuildGroup(building.getBuildingType())== ConfigBasis.BUILDPLAN_GROUP_PRODUCTION)
			{
				ItemArray products = building.produce(plugin.getServerData());
				if (products.size() > 0)
				{
					ItemList ingedients = plugin.getServerData().getRecipeProd(products.get(0).ItemRef(),building.getHsRegionType());
					Iterator<Item> items = ingedients.values().iterator();
					if (items.hasNext() )
					{
						Item item = items.next();
						l1 = l1 + item.ItemRef()+":"+item.value();
					}
					if (items.hasNext() )
					{
						Item item = items.next();
						l2 = l2 + item.ItemRef()+":"+item.value();
					}
					if (items.hasNext() )
					{
						Item item = items.next();
						l3 = l3 + item.ItemRef()+":"+item.value();
					}
				}
			}
			sBlock.setLine(0, l0);
			sBlock.setLine(1, l1);
			sBlock.setLine(2, l2);
			sBlock.setLine(3, l3);
			sBlock.update(true);
		} else
		{
			event.getPlayer().sendMessage(ChatColor.DARK_GRAY+"No building found !");
		}
	}

	
	private void cmdInfo(PlayerInteractEvent event)
	{
    	int regionId = findRegionAtLocation(plugin, event.getPlayer());
    	Building building = plugin.getData().getBuildings().getBuildingByRegion(regionId);
		if (building != null)
		{
			if (building.getLehenId() > 0)
			{
				Lehen lehen = plugin.getData().getLehen().getLehen(building.getLehenId());
				if (lehen != null)
				{
					CmdFeudalInfo cmd = new CmdFeudalInfo();
					cmd.setPara(0, building.getLehenId()); // lehen Id
					cmd.setPara(1, 1); // page
					if (cmd.canExecute(plugin, event.getPlayer()))
					{
						cmd.execute(plugin, event.getPlayer());
					} else
					{
						System.out.println("Cant execute ");
					}
				} else
				{
					System.out.println("Lehen not found ");
				}
			} else
			{
				Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(building.getSettleId());
				if (settle != null)
				{
					CmdSettleInfo cmdInfo = new CmdSettleInfo();
					cmdInfo.setPara(0, settle.getId());
					cmdInfo.setPara(1, 1);
					if (cmdInfo.canExecute(plugin, event.getPlayer()))
					{
						cmdInfo.execute(plugin, event.getPlayer());
					}
				} else
				{
					System.out.println("Settlement not found ");
				}
			}
		}
		
	}

	
	private void cmdReputation(PlayerInteractEvent event, Block b)
	{
    	Player player = (Player) event.getPlayer();
		player.getLocation();
		String sRegion = findSuperRegionAtLocation(plugin, player); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle != null)
		{
			CmdSettleReputation cmd = new CmdSettleReputation();
			cmd.setPara(0, settle.getId());
			if (cmd.canExecute(plugin, player))
			{
				cmd.execute(plugin, player);
			}
			
		}
	}
	
	
	private void doDoortest(PlayerInteractEvent event, Block b)
	{
		System.out.println(" door check ");
		BlockFace[] blockFaces = {BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};
		Block base = b.getLocation().getBlock().getRelative(BlockFace.UP);
		for(BlockFace bf : blockFaces) 
		{
		    Block bu = base.getRelative(bf);
		    if((bu.getType() == Material.WOODEN_DOOR)) 
		    {
		    	byte doorData = (byte) (bu.getData());
				System.out.println(" door found "+ (doorData & 0x4));
				if ((doorData & 0x4) == 0x4)
				{
					doorData = (byte) (doorData & 0x3);
				} else
				{
					doorData = (byte) (doorData | 0x7);
					
				}
//		    	doorData = (byte) (doorData | openData);
				System.out.println(" door open "+doorData);
		    	bu.setData(doorData);
//		    	bu.setData(openData);
//		    	BlockState state =  bu.getState();
//		    	Openable o = (Openable) state.getData();
//		    	o.setOpen(true); 
//		    	state.setData((MaterialData) o);
//		    	state.update();w
		    	
			    if((bu.getRelative(BlockFace.UP).getType() == Material.WOODEN_DOOR)) 
			    {
			    	doorData = (byte) (bu.getRelative(BlockFace.UP).getData());
			    	if ((doorData & 0x1) == 1)
			    	{
			    		doorData = 0x9;
			    	} else
			    	{
			    		doorData = 0x8;
			    	}
					System.out.println(" door hing "+doorData);
			    	bu.getRelative(BlockFace.UP).setData(doorData);
			    }
				System.out.println(" set door state ");
		    }
		}

	}
	
	
	private void doCatapult(PlayerInteractEvent event, Block b)
	{
		Chest chest = null;
		Block redstone = null;
		if (b.getRelative(BlockFace.UP).getType() == Material.CHEST)
		{
			chest = (Chest) b.getRelative(BlockFace.UP).getState();
		}
		if (b.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK)
		{
			redstone = b.getRelative(BlockFace.DOWN);
		}
		if (event.getPlayer().getItemInHand().getType() == Material.STICK)
		{
			if((chest != null) && (redstone != null))
			{
				event.getPlayer().sendMessage("You triggerd a Catapult");
				float loud = (float) 20.0;
				float pitch = (float) 90.0;
				event.getPlayer().getWorld().playSound(b.getLocation(), Sound.FIREWORK_BLAST, loud, pitch);
				shotArrow(b);
			}
		} else
		{
			event.getPlayer().sendMessage("You need a stick");
		}
		
	}
	
	
	private void cmdDonate(PlayerInteractEvent event, Block b)
	{
    	Player player = (Player) event.getPlayer();
		player.getLocation();
		int regionId = findRegionAtLocation(plugin, player);
		Building building = plugin.getData().getBuildings().getBuildingByRegion(regionId);
		// do Donation for Settlement
		if (building.getSettleId() > 0)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(building.getSettleId());
			if (settle != null)
			{
				if ((building.getBuildingType() == BuildPlanType.HALL)
					|| (building.getBuildingType() == BuildPlanType.TOWNHALL))
				{
					Inventory chest = player.getServer().createInventory(null, 3 * 9, donateInv);
					player.openInventory(chest);
				}
			}
			return;
		}
		// do Donation for Lehen
		if (building.getLehenId() > 0)
		{
			Lehen lehen = plugin.getData().getLehen().getLehen(building.getLehenId());
			if (lehen != null)
			{
				if ((building.getBuildingType() == BuildPlanType.KEEP)
					|| (building.getBuildingType() == BuildPlanType.CASTLE)
					|| (building.getBuildingType() == BuildPlanType.STRONGHOLD)
					|| (building.getBuildingType() == BuildPlanType.PALACE)
					)
					{
						Inventory chest = player.getServer().createInventory(null, 3 * 9, donateInv);
						player.openInventory(chest);
					}
			} else
			{
				System.out.println("No Lehen found "+building.getLehenId());
			}
		}

	}
	
	
	public BlockFace determineDataOfDirection(BlockFace bf)
	{
	     if(bf.equals(BlockFace.NORTH))
	            return BlockFace.SOUTH;
//	            return (byte)2;
	     else if(bf.equals(BlockFace.SOUTH))
	            return BlockFace.NORTH;
//	             return (byte)3;
	     else if(bf.equals(BlockFace.WEST))
	            return BlockFace.EAST;
//	            return (byte)4;
	     else if(bf.equals(BlockFace.EAST))
	            return BlockFace.WEST;
//	            return (byte)5;
	     return BlockFace.NORTH;
	}
	
	public BlockFace getFaceFromData(byte value)
	{
		switch(value)
		{
		case 2 : return BlockFace.NORTH;
		case 3 : return BlockFace.SOUTH;
		case 4 : return BlockFace.WEST;
		case 5 : return BlockFace.EAST;
		default : 
		  return BlockFace.NORTH;
		}
	}

	
	/**
	 * acquire a building for player
	 * check the reputation
	 * 
	 * @param event
	 * @param b
	 */
	private void cmdAcquire(PlayerInteractEvent event, Block b)
	{
//		byte signData = event.getClickedBlock().getData();
		BlockFace baseFace = determineDataOfDirection(getFaceFromData(event.getClickedBlock().getData()));
//		System.out.println("[REALMS] Acquire face  :"+baseFace);
		Location signBase = event.getClickedBlock().getRelative(baseFace).getLocation();
		String sRegion = findSuperRegionAtPosition(plugin, signBase).getName(); 
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		Owner owner = plugin.getData().getOwners().getOwner(event.getPlayer().getUniqueId().toString());
		if (owner == null)
		{
			event.getPlayer().sendMessage(ChatColor.RED+"You are not a regular Owner !");
			return;
		}

		// ohne settlement können auch gebäude erworben werden
		// aber wenn in einem settlement , dann muss reputation vorhanden sein
		if (settle != null)
		{
			// der owner braucht keinen Nachweis
//			System.out.println("ACQUIRE Settlement "+settle.getId()+":"+settle.getName());
			if (settle.getOwnerId() == owner.getId())
			{
//				System.out.println("ACQUIRE Reputation "+settle.getId()+":"+settle.getReputations().getReputation(event.getPlayer().getName()));
				// ein fremder muss genug reputation haben
				if (settle.getReputations().getReputation(event.getPlayer().getName()) < ReputationStatus.CITIZEN.getValue())
				{
					event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"You need more Reputation in this settlement !");
					event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"You need more than "+ReputationStatus.CITIZEN.getValue());
					return;
				}
			}
		} else
		{
			event.getPlayer().sendMessage(ChatColor.RED+"Settlement not found !");
		}
		Region region = findRegionAtPosition(plugin, signBase);
		if (region != null)
		{
			double cost = plugin.getServerData().getRegionTypeCost(region.getType());
			cost = cost * 2;
			if (plugin.economy.has(event.getPlayer().getName(), cost))
			{
				if (event.getPlayer().getInventory().getItemInHand().getType() == Material.AIR)
				{
					plugin.economy.withdrawPlayer(event.getPlayer().getName(), cost);
					Building building = plugin.getRealmModel().getBuildings().getBuildingByRegion(region.getID());
					if (building != null)
					{
						building.setOwnerId(owner.getId());
						SuperRegion superRegion = plugin.getServerData().getSuperRegion(sRegion);
						List<String> perms = new ArrayList<String>();
						superRegion.addMember(event.getPlayer().getName(), perms );
						plugin.getData().writeBuilding(building);
						
						event.getPlayer().sendMessage(ChatColor.GREEN+"You are now owner of this building");
						event.getPlayer().sendMessage(ChatColor.YELLOW+"Remember to remove the AQUIRE sign !");
						// ohne settlement können auch gebäude erworben werden
						// aber dann gibt es auch keine reputation!
						if (settle != null)
						{
							settle.getReputations().addValue(ReputationType.MEMBER, event.getPlayer().getName(), region.getType(), ConfigBasis.VALUABLE_POINT);
//							System.out.println(" REPUTATION MEMBER : "+region.getType()+": 1");
							event.getPlayer().sendMessage(ChatColor.GREEN+"You gain reputation as citizen of the settlement ");
						}
					} else
					{
						event.getPlayer().sendMessage(ChatColor.RED+"Region not found ! region "+region.getID());
						
					}
				} else
				{
					event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Your hand must be empty !");
				}
				
			} else
			{
				event.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"You have need "+ConfigBasis.format2(cost)+plugin.economy.currencyNamePlural());
			}
		} else
		{
//			System.out.println("[REALMS] Acquire location :"+signBase);
			event.getPlayer().sendMessage(ChatColor.RED+"Region not found !");
		}


	}
	
	
	private ItemStack setName(ItemStack is, String name, List<String> lore){
		ItemMeta IM = is.getItemMeta();
		if (name != null) {
			IM.setDisplayName(name);
		}
		if (lore != null) {
			IM.setLore(lore);
		}
		is.setItemMeta(IM);
		return is;
	}

	
	private void setupShopRow(int[] row, ItemArray stockList, Settlement settle, Player player, Inventory chest, int minAmount)
	{
		ArrayList<String> loreString = new ArrayList<String>(); // attribute liste
		int index = 0;
		for (int slot : row)
		{
			if (index >= stockList.size())
			{
				return;
			}
			Item item = stockList.get(index);
			if (item != null)
			{
				if (item.ItemRef().equalsIgnoreCase("SOIL") == false)
				{
					String itemRef = item.ItemRef();
					int amount = item.value();
					double price = plugin.getData().getPriceList().getBasePrice(itemRef);
					if ((price > 0.0))
					{
						int stock = settle.getWarehouse().getItemList().getValue(itemRef);
						if ((stock+amount > minAmount))
						{
							settle.getWarehouse().getItemList().withdrawItem(itemRef, amount);
		//					player.sendMessage("Store item :"+itemRef);
							if (itemRef.equalsIgnoreCase("AIR") == false)
							{
								loreString.clear();
								ItemStack itemStack = new ItemStack(Material.valueOf(itemRef), amount);
								loreString.add("Price : "+ConfigBasis.setStrformat2(price,5));
								loreString.add("Amount: "+amount);
								loreString.add("Cost  : "+ConfigBasis.setStrformat2(price*amount,6));
		//						System.out.println("Slot : "+slot);
								if (itemStack != null)
								{
									chest.setItem(slot - 1, setName(itemStack, itemStack.getItemMeta().getDisplayName(), loreString));
								}
								stockList.remove(index);
								if (stockList.size() == 0)
								{
									// end the list
									return;
								}
							}
						} else
						{
						}
					} else
					{
						System.out.println("[REALMS No price for  :"+itemRef);
					}
				}
			}
		}
		
	}
	
	
	private void setupShop(ItemArray stockList, Settlement settle, Player player, Inventory chest, int minAmount)
	{
		int[] row1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] row2 = { 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] row3 = { 19, 20, 21, 23, 24, 25, 26, 27, 28 };
		new ArrayList<String>();
		setupShopRow(row1, stockList, settle, player, chest, minAmount);
		setupShopRow(row2, stockList, settle, player, chest, minAmount);
		setupShopRow(row3, stockList, settle, player, chest, minAmount);
		
		
	}
	
	private ItemArray shopBuildMaterial(int settleId)
	{
		ItemArray stockList = plugin.getData().getSettlements().getSettlement(settleId).getWarehouse().searchItemsInWarehouse(ConfigBasis.initBuildMaterial()).asItemArray();
		stockList.addAll(plugin.getData().getSettlements().getSettlement(settleId).getWarehouse().searchItemsInWarehouse(ConfigBasis.initRawMaterial()).asItemArray());
		return stockList;
	}

	
	private ItemArray shopMaterial(int settleId)
	{
		ItemArray stockList = plugin.getData().getSettlements().getSettlement(settleId).getWarehouse().searchItemsInWarehouse(ConfigBasis.initMaterial()).asItemArray();
		stockList.addAll(plugin.getData().getSettlements().getSettlement(settleId).getWarehouse().searchItemsInWarehouse(ConfigBasis.initTool()).asItemArray());
		return stockList;
	}
	
	
	private void cmdSell(PlayerInteractEvent event, Block b, String l1)
	{
		Player player = event.getPlayer();
		if (isSell)
		{
			player.sendMessage(ChatColor.DARK_RED+"Sorry, the shop is busy");
			return;
		}
		String sRegion = findSuperRegionAtLocation(plugin, player);
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle == null)
		{
			player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
			return;
		}
		
		isSell = true;
		// create inventory
		Inventory chest = player.getServer().createInventory(null, 3 * 9, sellInv);

		ItemArray stockList;
		if (l1.equalsIgnoreCase("Material"))
		{
			stockList = shopMaterial(settle.getId());
			
		} else
		{
			stockList = shopBuildMaterial(settle.getId());
		}
		setupShop(stockList, settle, player, chest, 1);
		
		player.openInventory(chest);

	}
	

	private void cmdBuy(PlayerInteractEvent event, Block b)
	{
		Sign sign = (Sign) b.getState();
		sign.getLine(0);
		String l1 = sign.getLine(1);
		sign.getLine(2);
		sign.getLine(3);

		Player player = event.getPlayer();
		if (isBuy)
		{
			player.sendMessage(ChatColor.DARK_RED+"Sorry, the shop is busy");
			return;
		}
		String sRegion = findSuperRegionAtLocation(plugin, player);
		Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
		if (settle == null)
		{
			player.sendMessage(ChatColor.DARK_RED+"You standing not in a settlement");
			return;
		}
		
		isBuy = true;
		
		if (settle != null)
		{
			l1.toUpperCase();
//			System.out.println("Buy :"+itemRef);
			// create inventory
			Inventory chest = player.getServer().createInventory(null, 3 * 9, buyInv);

//			ItemArray stockList;
//			if (l1.equalsIgnoreCase("Material"))
//			{
//				stockList = shopMaterial(settle.getId());
//				
//			} else
//			{
//				stockList = shopBuildMaterial(settle.getId());
//			}
//			setupShop(stockList, settle, player, chest, 1);
			
			player.openInventory(chest);
			
		}
		
	}

}
