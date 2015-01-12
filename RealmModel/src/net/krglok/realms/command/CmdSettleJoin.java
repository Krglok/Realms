package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.Request;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleJoin extends RealmsCommand
{
	private int page;
	private int kingdomId;
	private int settleId;
	private int tributId;
	Owner owner ;
	
	public CmdSettleJoin()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.JOIN);
		description = new String[] {
				ChatColor.YELLOW+"/settle JOIN [settleId] [kingdomId] [tributId] ",
		    	"Send request for join to kingdom to the king  ",
		    	"this is a loyalty oath ",
		    	"The settlement going to the kingdom and accept pay tribut",
		    	"to the Lehen of [tributId] ",
		    	" if tributId = 0 then tribut go direct to the king ",
		    	" "
			};
			requiredArgs = 2;
			page = 0;
			settleId = 0;
			kingdomId = 0;
			tributId = 0;
			owner = null;
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
			settleId = value;
			break;
		case 1:
			kingdomId = value;
			break;
		case 2:
			tributId = value;
			break;
		default :
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
		return new String[] {int.class.getName(), int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();

		Settlement settle = plugin.getData().getSettlements().getSettlement(settleId);
		settle.setKingdomId(kingdomId);
		settle.setTributId(tributId);
		plugin.getData().writeSettlement(settle);
		
		msg.add("Your settlement join to kingdom "+kingdomId);
		if (tributId > 0)
		{
			msg.add("Your settlement pay tribut/tax to "+tributId);
		} else
		{
			msg.add("Your settlement pay tribut/tax to the King");
		}
		
		plugin.getMessageData().printPage(sender, msg, page);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		if (plugin.getData().getSettlements().getSettlement(settleId) == null)
		{
			errorMsg.add(ChatColor.RED+"Settlement not exist !");
			return false;
		}

		if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
		{
			errorMsg.add(ChatColor.RED+"Kingdom not exist");
			return false;
		}

		if (tributId > 0)
		{
			Lehen lehen = plugin.getData().getLehen().getLehen(tributId);
			if (lehen == null)
			{
				errorMsg.add(ChatColor.RED+"The tribut Lehen not exist");
				return false;
			}
			if (lehen.getKingdomId() != kingdomId)
			{
				errorMsg.add(ChatColor.RED+"The tribut Lehen is not a member of Kingdom !");
				return false;
			}
		}
		
		if (isOpOrAdmin(sender) == false)
		{
			owner = plugin.getData().getOwners().getOwnerName(player.getName());
			if (owner == null)
			{
				errorMsg.add(ChatColor.RED+"Only Admins can Requst for other");
				return false;
			}
			if (plugin.getData().getSettlements().getSettlement(settleId).getOwnerId() == owner.getId()==false)
			{
				errorMsg.add(ChatColor.RED+"You are not the owner of the settlement ! ");
				return false;
			}

			return false;
		}
		return true;
	}

}
