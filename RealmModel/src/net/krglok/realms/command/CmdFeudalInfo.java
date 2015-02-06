package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFeudalInfo extends RealmsCommand
{

	private int page;
	private int lehenId;
	
	public CmdFeudalInfo()
	{
		super(RealmsCommandType.FEUDAL, RealmsSubCommandType.INFO);
		description = new String[] {
				ChatColor.YELLOW+"/feudal INFO [lehenId] [page]  ",
		    	"List Lehen data  ",
		    	"and the vasale references",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
			lehenId = 0;
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
		case 0:
			lehenId = value;
			break;
		case 1:
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
		Lehen lehen = plugin.getData().getLehen().getLehen(lehenId);
		Lehen parent = plugin.getData().getLehen().getLehen(lehen.getParentId());
		Kingdom kingdom = plugin.getData().getKingdoms().getKingdom(lehen.getKingdomId());
		msg.add(ChatColor.GREEN+"Lehen "+ChatColor.YELLOW+lehen.getId()+" : "+lehen.getName()+ChatColor.GREEN+" Kingdom "+ChatColor.YELLOW+kingdom.getId()+" : "+kingdom.getName());
		msg.add(ChatColor.GREEN+"Your Title is "+ChatColor.DARK_PURPLE+lehen.getNobleLevel()+" ");
		msg.add(ChatColor.GREEN+"Your bank is "+ChatColor.YELLOW+ConfigBasis.setStrformat2(lehen.getBank().getKonto(),11));
		
		if (parent != null)
		{
			msg.add(ChatColor.GREEN+"Your Lord is "+ChatColor.YELLOW+parent.getOwner().getNobleLevel()+" "+parent.getOwner().getPlayerName()+" from "+parent.getName());
		}
		msg.add(ChatColor.GREEN+"Your Building are :");
		for (Building building : lehen.getBuildings().values())
		{
    		msg.add(ChatColor.YELLOW+"+"+building.getId()
    				+" | "+ChatColor.YELLOW+building.getBuildingType()
    				+" | "+ChatColor.GOLD+building.getSettler()
    				+" Train: "+building.getTrainType()
    				);
		}
		
		msg.add(ChatColor.GREEN+"Your settlement are :");
	    for (Settlement settle : plugin.getData().getSettlements().values())
	    {
	    	
	    	if (settle.getTributId() == lehenId)
	    	{
	    		msg.add(ChatColor.YELLOW+"+"+settle.getId()
	    				+" | "+ChatColor.YELLOW+settle.getName()
	    				+" | "+ChatColor.GOLD+settle.getSettleType().name()
	    				+" Owner: "+settle.getOwnerId()
	    				+" in "+settle.getPosition().getWorld());
	    	}
	    }

		msg.add(ChatColor.GREEN+"Your liege are :");
		for (Lehen vasal : plugin.getData().getLehen().values())
		{
			if (vasal.getParentId() == lehen.getId())
			{
	    		msg.add(ChatColor.YELLOW+" "+vasal.getId()
	    				+" | "+ChatColor.YELLOW+vasal.getName()
	    				+" | "+ChatColor.DARK_PURPLE+vasal.getNobleLevel()
	    				+" | "+ChatColor.YELLOW+vasal.getOwnerId()
	    				+" | "+ChatColor.GOLD+vasal.getKingdomId()
	    				);
			}
		}
		
		
		msg.add(" ");
		
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getData().getLehen().getLehen(lehenId) == null)
		{
			errorMsg.add(ChatColor.RED+"The lehenId is wrong !");
			return false;
		}
		Player player = (Player) sender;
		if (isOpOrAdmin(sender) == false)
		{
			if (plugin.getData().getLehen().getLehen(lehenId).getOwnerId().equalsIgnoreCase(player.getName()))
			{
				errorMsg.add(ChatColor.RED+"You are not the owner !");
				return false;
			}
		}
			
		return true;
	}

}
