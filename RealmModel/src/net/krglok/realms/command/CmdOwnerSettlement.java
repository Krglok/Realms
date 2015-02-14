package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.logging.Level;

import net.krglok.realms.Realms;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdOwnerSettlement extends RealmsCommand
{
	private int page;
	private int ownerId;
	
	public CmdOwnerSettlement()
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.SETTLEMENT);
		description = new String[] {
				ChatColor.YELLOW+"/owner SETTLEMENT {page} {ID}  ",
		    	"Show the Settlements of a Owner    ",
		    	"you can only see your on list ",
		    	"only OP or Admin can see for ozhers",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
			ownerId = -1;
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
			page = value;
			break;
		case 1:
			ownerId = value;
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
		Player player = (Player) sender;
		Owner owner;
		if (ownerId > 0)
		{
			owner = plugin.getData().getOwners().getOwner(ownerId);
		} else
		{
			owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
		}
		if (owner == null)
		{
			msg.add(ChatColor.RED+"Not a regular owner of Realms !");
		}
		msg.add("ID |Nobility  |Commonlevel  |   | Player      |  [ "+plugin.getData().getOwners().size()+" ]");
		msg.add(owner.getId()
				+" | "+ChatColor.GOLD+owner.getNobleLevel()
				+" | "+ChatColor.YELLOW+owner.getCommonLevel()
				+" | "+owner.getKingdomId()
				+" | "+owner.getPlayerName()
				);
		msg.add(" ");
		
		msg.add("ID |Type     |Settlement   |");
		for (Settlement settle : plugin.getData().getSettlements().values())
		{
			if (settle.getOwnerId() == owner.getId())
			{
				msg.add(settle.getId()
					+" | "+ChatColor.YELLOW+settle.getSettleType()
					+" | "+ChatColor.GREEN+settle.getName()
					);
			}
		}
		msg.add("ID |Kingdom   ");
		for (Kingdom kingdom : plugin.getData().getKingdoms().values())
		{
			if (kingdom.getOwnerId() == owner.getId())
			{
				msg.add(kingdom.getId()
						+" | "+ChatColor.YELLOW+kingdom.getName()
						);
			}
		}

		msg.add("ID |Lehen   ");
		for (Lehen lehen : plugin.getData().getLehen().values())
		{
			if (lehen.getOwnerId() == owner.getId())
			{
				msg.add(lehen.getId()
						+" | "+ChatColor.YELLOW+lehen.getNobleLevel()
						+" | "+ChatColor.GREEN+lehen.getName()
						);
			}
		}
		
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;
		ownerId = -1; 
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use this command
		if (ownerId > 0)
		{
			if (isOpOrAdmin(sender) == false)
			{
				errorMsg.add(ChatColor.RED+"You are not OP or Admin !");
				errorMsg.add(ChatColor.RED+"You can only see your own ownership");
			}
		}
		return true;
	}

}
