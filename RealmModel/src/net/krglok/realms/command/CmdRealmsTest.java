package net.krglok.realms.command;

import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.NPCType;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.avaje.ebean.validation.Length;
import com.nisovin.shopkeepers.Shopkeeper;
import com.nisovin.shopkeepers.shopobjects.living.LivingEntityShop;
import com.nisovin.shopkeepers.shoptypes.NormalPlayerShopkeeper;

public class CmdRealmsTest extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [page]   ",
		    	" check buildings with no settlement  ",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			page = value;
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
		return new String[] {int.class.getName()  };
	}

	private void checkRegionChest(Realms plugin, ArrayList<String> msg)
	{
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			Block bs = region.getLocation().getBlock();
			if (bs.getType() != Material.CHEST)
			{
				String x = ConfigBasis.setStrformat2(region.getLocation().getX(),7);
				String y = ConfigBasis.setStrformat2(region.getLocation().getY(),7);
				String z = ConfigBasis.setStrformat2(region.getLocation().getZ(),7);
				String type = ConfigBasis.setStrleft(region.getType(), 12);
				msg.add(region.getID()+":"+ type +":"+ x + ":"+ y +":"+ z);
			}
		}
	}
	
	private static SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
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

	public static Region findRegionAtPosition(Realms plugin, Location position)
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
	
    private void cmdSignShop(Realms plugin, Block b, Player player)
    {
    	Location pos = b.getLocation();
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
    	    	System.out.println("Realms setShop");
    	    	plugin.setShopPrice(b.getLocation());
//    	    	plugin.setShop(player,b.getLocation(), settle);
//    	    	plugin.setShopPrice(pos);
    	    }
		}
    }

	
    
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Realms Test ");
    	ArrayList<String> msg = new ArrayList<String>();
		int radius = 5;
		int edge = radius * 2 -1;
		msg.add("Region without Building ");
		msg.add("ID  | Location ");
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			if (plugin.getRealmModel().getBuildings().getBuildingByRegion(region.getID()) == null)
			{

				String s = region.getID()+" :"
					+region.getType()+" :"
					+region.getLocation().getWorld().getName()+" :"
					+(int) region.getLocation().getX()+" :"
					+(int) region.getLocation().getY()+" :"
					+(int) region.getLocation().getZ()+"";
				
					System.out.println("[REALMS] region ID "+s);
					msg.add(s);
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
//		if (sender instanceof Player)
//		{
//			return true;
//		}
//		errorMsg.add("Not a console command !");
//		errorMsg.add("The command must send by a Player !");
//		return false;
	}

}
