package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.McmdBuilder;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import net.krglok.realms.core.Position;

public class CmdSettleBuild extends RealmsCommand
{
	LocationData position;
	String buildName;
	BuildPlanType bType;
	int settleId ;
	
	public CmdSettleBuild( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BUILD);
		description = new String[] {
				ChatColor.YELLOW+"/settle BUILD [ID] [x] [Y] [Z] {BUILDING}",
		    	"Build a new Building {BUILDING_TYPE}  ",
		    	" Size = 7 x 7 , height Offset -1 ", 
		    	" X = East/West ",
		    	" Y = Height",
		    	" Z = North/South",
		    	" "
			};
			requiredArgs = 4;
			position = new LocationData("", 0.0, 0.0, 0.0);
			buildName = "";
			bType = BuildPlanType.NONE;
			settleId = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 4 :
			buildName = value;
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
			settleId = value;
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
		switch (index)
		{
		case 1 :
			position.setX(value);
			break;
		case 2 :
			position.setY(value);
		break;
		case 3 :
			position.setZ(value);
		break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName(), double.class.getName(), double.class.getName(), double.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
		World world = player.getWorld();
		LocationData iLoc = new LocationData(world.getName(), position.getX(), position.getY(), position.getZ());
		System.out.println("SettleCommand : Builder");
		McmdBuilder modelCommand = new McmdBuilder(plugin.getRealmModel(), settleId, bType, iLoc,player);
		plugin.getRealmModel().OnCommand(modelCommand);
    	msg.add("BUILD "+bType.name()+" at "+(int)position.getX()+":"+(int)position.getY()+":"+(int)position.getZ());
    	msg.add(" ");
		plugin.getMessageData().printPage(sender, msg, 1);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
		{
			errorMsg.add("Wrong Settlement ID !");
			errorMsg.add(getDescription()[0]);
			return false;
		}
		if (buildName == "")
		{
			buildName = "WHEAT";
		}
		bType = BuildPlanType.getBuildPlanType(buildName);
		if (bType != BuildPlanType.NONE)
		{
			return true;
		}
		errorMsg.add("Wrong BuildPlanType !");
		errorMsg.add("NoName Default = WHEAT ");
		errorMsg.add(getDescription()[0]);
		return false;
	}

}
