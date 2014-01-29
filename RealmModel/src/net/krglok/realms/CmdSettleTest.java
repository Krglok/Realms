package net.krglok.realms;

import net.krglok.realms.builder.BuildPlanHome;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.McmdBuilder;

import org.bukkit.ChatColor;
import org.bukkit.World;
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
//		BuildPlanHome bHome = new BuildPlanHome();
//		int edge = bHome.getEdge();
		Player player = (Player) sender;
		World world = player.getWorld();
		// generate Location for create the Building
//		Location l = new Location(world, , +bHome.getOffsetY(), );
		LocationData iLoc = new LocationData(world.getName(), position.getX(), position.getY(), position.getZ());
		McmdBuilder modelCommand = new McmdBuilder(plugin.getRealmModel(), 3, BuildPlanType.WHEAT, iLoc);
		plugin.getRealmModel().OnCommand(modelCommand);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
