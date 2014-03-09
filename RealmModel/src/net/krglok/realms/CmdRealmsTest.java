package net.krglok.realms;

import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;

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
import org.bukkit.entity.Player;
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

public class CmdRealmsTest extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [page]   ",
		    	" test all region for center chest  ",
		    	"  ",
		    	"  ",
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
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
		checkRegionChest(plugin, msg);
		plugin.getMessageData().printPage(sender, msg, page);

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
