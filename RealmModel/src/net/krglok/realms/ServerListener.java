package net.krglok.realms;

import net.minecraft.server.v1_7_R1.Container;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.InventoryCrafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class ServerListener implements Listener
{
	private Realms plugin;
	
	public ServerListener(Realms plugin)
	{
		this.plugin = plugin;
	}

    @EventHandler(priority = EventPriority.NORMAL)
    public void Craft(CraftItemEvent event)
    {
    	Recipe recipe = event.getRecipe();
    	ItemStack itemStack = recipe.getResult();
    }
    
    //PrepareCraftItemEvent
    @EventHandler(priority = EventPriority.NORMAL)
    public void prepareCraft(PrepareItemCraftEvent event)
    {
    	Recipe recipe = event.getRecipe();
    	ItemStack itemStack = recipe.getResult();
    	Container container = null;
		InventoryCrafting inventorycrafting = new InventoryCrafting(container, 1, 1);
		inventorycrafting.b(i, itemstack)
    }

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
	
}
