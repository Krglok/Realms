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

	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST {page}   ",
		    	" Make a analysis of sings in range of 10 blocks  ",
		    	"  ",
		    	"  ",
		    	" "
			};
			requiredArgs = 1;
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
		Location lookAt = new Location(player.getLocation().getWorld(),0.0, player.getLocation().getX(),0.0);
		for (int i = 0; i < edge; i++)
		{
			for (int j = 0; j < edge ; j++)
			{
				lookAt.setX(i+pos.getX());
				lookAt.setY(pos.getY());
				lookAt.setZ(j+pos.getZ());
				Block b = lookAt.getWorld().getBlockAt(lookAt);
				if (b.getType()!= Material.AIR)
				{
					System.out.println("pos "+(int) lookAt.getX()+":"+(int) lookAt.getY()+b.getType());
				}
				switch (b.getType())
				{
				case WALL_SIGN:  // sign on the wall
				case SIGN_POST: // Sign on the ground
					Sign sBlock =	((Sign) b.getState());
					String[] signText = sBlock.getLines();
					if (signText[0].equals("[REQUIRE]"))
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
				case SIGN :		// Material to build signs
					System.out.println("Sign ");
					break;
				case CHEST:
					Chest chest = ((Chest) b.getState());
					System.out.println("Deposit to Warehouse;");
					if (chest.getInventory().getContents() != null)
					{
						ItemStack[] items = chest.getInventory().getContents();
						for (ItemStack item : items)
						{
							System.out.println(item.getType());
						}
					}
					break;
				default:
				}
				
			}
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
