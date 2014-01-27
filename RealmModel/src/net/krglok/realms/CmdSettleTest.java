package net.krglok.realms;

import net.krglok.realms.builder.BuildPlanHome;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import net.krglok.realms.core.Position;

public class CmdSettleTest extends RealmsCommand
{
	LocationData position;
	
	
	public CmdSettleTest( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/settle TEST [x] [Y] [Z] ",
		    	"Build a new Building HOME  ",
		    	" Size = 7 x 7 , height Offset -1 ", 
		    	" X = East/West ",
		    	" Y = Height",
		    	" Z = Nortz/South",
		    	" "
			};
			requiredArgs = 3;
			position = new LocationData("", 0.0, 0.0, 0.0);
	}

	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{
		switch (index)
		{
		case 0 :
			position.setX(value);
			break;
		case 1 :
			position.setY(value);
		break;
		case 2 :
			position.setZ(value);
		break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {double.class.getName(), double.class.getName(), double.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		BuildPlanHome bHome = new BuildPlanHome();
		int edge = bHome.getEdge();
		Player player = (Player) sender;
		World world = player.getWorld();
		// generate Location for create the Building
		Location l = new Location(world, position.getX(), position.getY()+bHome.getOffsetY(), position.getZ());
		 
		// Iterate thru BuildingPlan
		for (int h = 0; h < edge; h++)
		{
			for (int r = 0; r < edge; r++)
			{
				for (int c = 0; c < edge; c++)
				{
					if (ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]) != Material.AIR)
					{						
//						System.out.println(ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]) );
						
						switch (ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]))
						{
						case WOOD_DOOR : 
							System.out.println("SetDoor !");
							Block bottom = world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r);
							Block top = bottom.getRelative(BlockFace.UP, 1);
							Byte data1 = (0x8); //not sure on this syntax...
							Byte data2 = (0x4); //not sure on this syntax...
							top.setTypeIdAndData(64, data1, false);
							bottom.setTypeIdAndData(64, data2, false);
							break;
						case WALL_SIGN:
							world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r).setType(ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]));
//							Block b = l.getWorld().getBlockAt(l.getBlockX()+r, l.getBlockY()+h, l.getBlockZ()+c);
//							Sign sign =  (Sign) l.getWorld().getBlockAt(l.getBlockX()+r, l.getBlockY()+h, l.getBlockZ()+c).getState();
//							sign.setLine(0, bHome.getSignText()[0].toString());
							System.out.println("Wall SIgn");
							break;
						case BED_BLOCK:
//							world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r).setType(ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]));
//							Block bedFoot = world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r);
//							Block bedHead = base.getRelative(BlockFace.SOUTH, 1);
							System.out.println("Set Bed !");
				            BlockState bedFoot = world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r).getState();
				            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
							bedFoot.setType(Material.BED_BLOCK);
							bedHead.setType(Material.BED_BLOCK);
							bedFoot.setRawData((byte) 0x0);
							bedHead.setRawData((byte) 0x8);
							bedFoot.update(true, false);
							bedHead.update(true, true);
							break;
						default :
							world.getBlockAt(l.getBlockX()+c, l.getBlockY()+h, l.getBlockZ()+r).setType(ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]));
						}
					}
				}
			}
		}
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
