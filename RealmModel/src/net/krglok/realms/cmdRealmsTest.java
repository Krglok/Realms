package net.krglok.realms;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdRealmsTest extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [page]   ",
		    	" set a Text to SIGN you look at  ",
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

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Look for Signs ");
//		String path = plugin.getDataFolder().getAbsolutePath();
//		path = "D:\\Program Files\\BuckitTest\\plugins\\Realms";
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
					if (signText[0].equals("[REQUIRE"+page+"]"))
					{
//						((Sign) b.getState())
						sBlock.setLine(1, Material.WOOD_AXE+": "+5);
						sBlock.setLine(2, Material.WHEAT+": "+105);
						sBlock.update(true);
					}
					for (int k = 0; k < signText.length; k++)
					{
						System.out.println(sBlock.getLines()[k]);
					}
					break;
					
				default:
				}
				
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
