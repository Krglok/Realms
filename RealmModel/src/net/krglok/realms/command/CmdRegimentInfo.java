package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.UnitType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRegimentInfo extends RealmsCommand
{
	int regID;
	Integer page ;


	public CmdRegimentInfo()
	{
		super(RealmsCommandType.REGIMENT, RealmsSubCommandType.INFO);
		description = new String[] {
				ChatColor.YELLOW+"/regiment INFO [RegD] [page] ",
		    	"Show information of the regiment ",
		    	"only the owner or op can use the command  ",
		    	"  "
			};
			requiredArgs = 1;
			this.regID = 0;
			this.page = 0;  //default value
	}

	public int getPage()
	{
		return page;
	}
	
	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			regID = value;
			break;
		case 1 :
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
		return new String[] { int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
	    Regiment regiment = plugin.getRealmModel().getRegiments().get(regID);
	    if (regiment != null)
	    {
			msg.add("Regiment ["+regiment.getId()+"] : "+regiment.getName()+" [ "+regiment.getRegStatus().name()+" ]");
			msg.add("Position : "+(int)regiment.getPosition().getX()+":"+(int)regiment.getPosition().getY()+":"+(int)regiment.getPosition().getZ());
			msg.add("Barracks  : "+ChatColor.YELLOW+(regiment.getBarrack().getUnitList().size())+"/"+regiment.getBarrack().getUnitMax());
			msg.add("Bank       : "+ChatColor.GREEN+((int) regiment.getBank().getKonto()));
			msg.add("Storage   : "+regiment.getWarehouse().getItemMax());
			msg.add("Food      : WHEAT "+regiment.getWarehouse().getItemList().getValue("WHEAT"));
			msg.add("Settler  : "+ChatColor.YELLOW+(regiment.getBarrack().getUnitList().getUnitTypeList(UnitType.SETTLER).size()));
			msg.add("Militia  : "+ChatColor.YELLOW+(regiment.getBarrack().getUnitList().getUnitTypeList(UnitType.MILITIA).size()));
			msg.add("Archer    : "+ChatColor.YELLOW+(regiment.getBarrack().getUnitList().getUnitTypeList(UnitType.ARCHER).size()));
	    }
	    if (page == 0)
	    {
	    	page = 1;
	    }
	    page = plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getRegiments().get(regID) != null)
			{
				if (isOpOrAdmin(sender) == false)
				{
					if (isRegimentOwner(plugin, sender, regID)== false)
					{
						errorMsg.add("You are not the owner ! ");
						errorMsg.add(" ");
						return false;
						
					}
				}
				if (sender.isOp())
				{
					return true;
				}
			}
			errorMsg.add("Regiment not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;
	}
}
