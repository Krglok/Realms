package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.core.ItemPrice;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
    
}
