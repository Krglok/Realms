package net.krglok.realms.command;

import net.krglok.realms.Realms;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdRealmsSign extends RealmsCommand
{
	private int page; 
	private String text;
	
	public CmdRealmsSign( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.SIGN);
		description = new String[] {
				ChatColor.YELLOW+"/realms Sign [line] \"Text\" ",
		    	" set a Text to SIGN you look at  ",
		    	" lines form 0-3 ",
		    	" the _ is replaced with a blank ",
		    	" "
			};
			requiredArgs = 2;
			page = 0;
			text = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1 :
			text = value;
			break;
		default:
			break;
		}

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
		return new String[] {int.class.getName(), String.class.getName()  };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Look for Signs ");
		int radius = 5;
		int edge = radius * 2 -1;
		Player player = (Player) sender;
		Location pos = new Location(player.getLocation().getWorld(), player.getLocation().getX()-radius, player.getLocation().getY(), player.getLocation().getZ()-radius);
		Block lookAt =  player.getTargetBlock(null, 6);   
				//new Location(player.getLocation().getWorld(),0.0, player.getLocation().getX(),0.0);
		
		if (lookAt.getType()!= Material.AIR)
		{
			System.out.println("pos "+(int) lookAt.getX()+":"+(int) lookAt.getY()+"."+lookAt.getType());
		}
		switch (lookAt.getType())
		{
		case WALL_SIGN:  // sign on the wall
			Sign sBlock =	((Sign) lookAt.getState());
			String[] signText = sBlock.getLines();
			if ((page >= 0) && (page <= 3))
			{
				text = text.replaceAll("[_]", " ");
//						((Sign) b.getState())
				sBlock.setLine(page, text);
				sBlock.update(true);
			}
			for (int k = 0; k < signText.length; k++)
			{
				System.out.println(sBlock.getLines()[k]);
			}
			break;
			
		default:
		}
		System.out.println("Text:"+text);
		page = 0;
		text = "";
				
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender instanceof Player)
		{
			return true;
		}
		errorMsg.add("Not a console command !");
		errorMsg.add("The command must send by a Player !");
		return false;
	}

}
