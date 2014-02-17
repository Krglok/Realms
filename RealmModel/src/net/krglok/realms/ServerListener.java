package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ServerListener implements Listener
{
	private Realms plugin;
	
	public ServerListener(Realms plugin)
	{
		this.plugin = plugin;
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

    @EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) 
    {
		if (event.getPlayer().isOp()) 
		{
			
			String msg = "[Realms] Updatecheck : "+plugin.getConfigData().getPluginName()+" Vers.: "+plugin.getConfigData().getVersion();
			Update.message(event.getPlayer(),msg);
		}
		return; // no OP => OUT
	}
	
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event)
    {
    	Location pos = event.getPlayer().getLocation();
    	
    	event.getInventory();
    }
    
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
    	Block b = event.getClickedBlock();
    	if (b != null)
    	{
	    	if (b.getType() == Material.WALL_SIGN)
	    	{
	    		Sign sign = (Sign) b.getState();
	    		String l0 = sign.getLine(0);
	    		String l1 = sign.getLine(1);
	    		if (l0.contains("[BuildPlan]"))
	    		{
	    			event.getPlayer().sendMessage("You will get a BuildPLan Book of :"+l1);
	    			cmdBuildPlanBook(event);
	    		}
	    		if (l0.contains("[Required]"))
	    		{
	    			event.getPlayer().sendMessage("You will get a Book with required Items for the Settlement :"+l1);
	    			cmdRequiredBook(event);
	    		}
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD)
	    	{
//    			event.getPlayer().sendMessage("You hold a Blazerod :");
	    		cmdBlazeRod(event);
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK)
	    	{
	    		cmdBuildBook(event);
	    	}
	    	if (event.getPlayer().getItemInHand().getType() == Material.BOOK_AND_QUILL)
	    	{
	    		cmdBuildBook(event);
	    		System.out.println("BookEdit");
	    	}

    	}
    }
    
    private void cmdBlazeRod(PlayerInteractEvent event)
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
	    				msg.add(region.getID()+":"+region.getType()+":"+region.getOwners().get(0));
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
		plugin.getMessageData().printPage(event.getPlayer(), msg, 1);
    	
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
	    	if (settleType != SettleType.SETTLE_NONE)
	    	{
	    		return sRegion.getName();
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
    	ArrayList<String> msg = new ArrayList<String>();
    	if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
    	{
//    		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
    		{
		    	Block target = ((PlayerInteractEvent) event).getClickedBlock();
		    	Location pos = target.getLocation();
				LocationData iLoc = new LocationData(pos.getWorld().getName(), pos.getX()+1, pos.getY()+1, pos.getZ()+1);
				String sRegion = findSuperRegionAtLocation(plugin, player); 
				Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion);
				if (settle != null)
				{
					ItemStack book = event.getPlayer().getItemInHand();
		//			final BookMeta bm = (BookMeta) book.getItemMeta();
		//			if (bm.getTitle().equalsIgnoreCase("[WHEAT]"))
					if (book.getItemMeta().getDisplayName().equalsIgnoreCase("[WHEAT]"))
					{
						BuildPlanType bType = BuildPlanType.WHEAT;
						McmdBuilder modelCommand = new McmdBuilder(plugin.getRealmModel(), settle.getId(), bType, iLoc);
						plugin.getRealmModel().OnCommand(modelCommand);
				    	msg.add("BUILD "+bType.name()+" in "+settle.getName()+" at "+(int)pos.getX()+":"+(int)pos.getY()+":"+(int)pos.getZ());
				    	msg.add(" ");
					}
				} else
				{
					msg.add("No settlement found at this position !!");
					msg.add("Build process is canceled!");
				}
    		}
    	} else
    	{
			msg.add("[Realm Model] NOT enabled or too busy");
			msg.add("Try later again");
    	}
		plugin.getMessageData().printPage(player, msg, 1);
}
    
}
