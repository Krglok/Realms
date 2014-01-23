package net.krglok.realms;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.PlanMap;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.data.StrongholdTools;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleCheck extends RealmsCommand
{
	private String name;
	private int page;
	
	public CmdSettleCheck()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.CHECK);
		description = new String[] {
		    	"/settle CHECK [SuperRegionName] [page] ",
				"Check to create a Settlement from the supereregion <NAME> ",
		    	"and show the analysis report ",
		    	"  ",
		};
		requiredArgs = 0;
		this.name = "";
		this.page = 1;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				name = value;
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
		return new String[] {String.class.getName() , int.class.getName() };
	}

	private String findSuperRegionAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
	    	if (settleType != SettleType.SETTLE_NONE)
	    	{
	    		return sRegion.getName();
	    	}
	    }
		return "";
	}
	
	private boolean cmdSettlement(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Building in Superregion [ "+" ]");
		if (plugin.getRealmModel().getSettlements().containsName(name))
		{
			msg.add("see Settlement :"+ name
					+" as :"+plugin.getRealmModel().getSettlements().findName(name).getId()
//					+":"+plugin.getRealmModel().getSettlements().findName(name).getSettleType()
					);
		}
//		msg.add(name);
		if ( plugin.stronghold.getRegionManager().getSuperRegion(name) == null)
		{
			this.addErrorMsg(this.command()+":"+this.subCommand()+ "  No SuperRegion (null)");
			return false;
		}
	    SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(name);
		LocationData position = new LocationData(
				sRegion.getLocation().getWorld().getName(),
				sRegion.getLocation().getX(), 
				sRegion.getLocation().getY(),
				sRegion.getLocation().getZ());
		msg.add(sRegion.getType()+" : "+ChatColor.YELLOW+sRegion.getName()+" : "+" Owner: "+sRegion.getOwners());
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
		{
	    	String sName = StrongholdTools.setStrleft(region.getType(), 20);
    		msg.add("  "+sName+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
		}
		msg.add("== Region List");
		String sName = "";
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			LocationData loc = new LocationData(
					region.getLocation().getWorld().getName(),
					region.getLocation().getX(), 
					region.getLocation().getY(),
					region.getLocation().getZ());
			
			double d =  Math.round(position.distance(loc));
			
			if (d > 70)
			{
				sName = StrongholdTools.setStrleft(region.getType(), 15);
	    		msg.add("  "+sName+" : "+ChatColor.YELLOW+region.getID()+"  at : "+d);
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		if (page < 1)
		{
			page = 1;
		}
		cmdSettlement(plugin, sender);
	}

	/**
	 * prueft ob ein Name vorhanden ist und ersetzt ihn gegenbenenfalls 
	 * aufgrund der Locatiopn des Players.
	 * Wird kein Name gefunden, ist der Befehl ungueltig
	 * Die Console kann den Befehl ausfuehren muss aber einen Namen angeben
	 * 
	 * @param plugin
	 * @param sender
	 */
	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		boolean isReady = false;
		if (sender.isOp() == false)
		{
			if (sender instanceof Player)
			{
				if (sender.hasPermission("") == false)
				{
					this.addErrorMsg(this.command()+":"+this.subCommand()+ "  No Permissions");
					return false;
				}
			}
		}
		// fehlenden Parameter Name ersetzen
		if (this.name == "")
		{
			if (sender instanceof Player)
			{
				name = findSuperRegionAtLocation(plugin, (Player) sender);
				// pruefe leeren Namen
				if (name.equals(""))
				{
					this.addErrorMsg(this.command()+":"+this.subCommand()+ "  No SuperRegionName");
					return false;
				}
			} else
			{
				this.addErrorMsg(this.command()+":"+this.subCommand()+ "  Console must use the Name Parameter");
				return false;
			}
		}
		// pruefe ob Superegion gueltig bzw. vorhanden ist
		if (plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			if (sender.isOp() == false)
			{
				// pruefe ob der Player der Owner ist
				if (plugin.stronghold.getRegionManager().getSuperRegion(name).getOwners().isEmpty() == false)
				{
					isReady = sender.getName().equalsIgnoreCase(plugin.stronghold.getRegionManager().getSuperRegion(name).getOwners().get(0));
					this.addErrorMsg(this.command()+":"+this.subCommand()+ "  You are not the Owner !");
				} else
				{
					this.addErrorMsg(this.command()+":"+this.subCommand()+ " Superregion & Owner found !");
					isReady = true;
				}
			} else
			{
				this.addErrorMsg(this.command()+":"+this.subCommand()+ " Superregion found as OP !");
				isReady = true;
			}
		}
		return isReady;
	}

}
