package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.Request;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKingdomRequest extends RealmsCommand
{
	private int page;
	private int kingdomId;
	private int requestId;
	private String accept;
	
	public CmdKingdomRequest()
	{
		super(RealmsCommandType.KINGDOM, RealmsSubCommandType.REQUEST);
		description = new String[] {
				ChatColor.YELLOW+"/kingdom REQUEST [kingdomId] [page] {requestId} {ACCEPT/REJECT} ",
		    	"Only kingdomId given : Show request list ",
		    	"with [page] you can show a long list ",
		    	"with {requestId} the status will set",
		    	"use the correct wording for accept/reject",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
			kingdomId = 0;
			requestId = 0;
			accept = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 3:
			accept = value;
			break;
		default :
			break;
		}
	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0:
			kingdomId = value;
			break;
		case 1:
			page = value;
			break;
		case 2:
			requestId = value;
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
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName(), int.class.getName(), int.class.getName(), String.class.getName()  };
	}
	
	/**
	 * do the join and set all lehen to the new Kingdom
	 * @param plugin
	 * @return
	 */
	private ArrayList<String> joinOwnerToKingdom(Realms plugin)
	{
		ArrayList<String> msg = new ArrayList<String>();
		Request request = plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().get(requestId);
		Owner owner = plugin.getData().getOwners().getOwner(request.getOwnerId());
		owner.setKingdomId(kingdomId);
		plugin.getData().writeOwner(owner);
		msg.add("Owner set to Kingdom"+kingdomId);
		// root of new kingdom
		Lehen parent = plugin.getData().getLehen().getKingdomRoot(kingdomId);
		for (Lehen lehen :plugin.getData().getLehen().getSubList(owner.getPlayerName()).values())
		{
			lehen.setKingdomId(kingdomId);
			if (lehen.getParentId() == 0)
			{
				lehen.setParentId(parent.getId());
			}
			plugin.getData().writeLehen(lehen);
			msg.add("Lehen "+lehen.getId()+" set to Kingdom"+kingdomId);
		}
		return msg;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		if (page == 0) { page =1; }
		
		ArrayList<String> msg = new ArrayList<String>();
		if (requestId == 0)
		{
			msg.add("Join Request list  [ "+plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().size()+" ]");
			for (Request request: plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().values())
			{
	    		msg.add(request.getId()
	    				+" | "+ChatColor.GOLD+plugin.getData().getOwners().getOwner(request.getOwnerId()).getPlayerName()
	    				+" | "+ChatColor.YELLOW+plugin.getData().getOwners().getOwner(request.getOwnerId()).getNobleLevel().name()
	    				);
			}
		} else
		{
			if (accept != "")
			{
				if (accept.equalsIgnoreCase("accept"))
				{
					plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().get(requestId).setStatus(Request.REQUEST_STATUS_ACCEPT);
					msg.addAll(joinOwnerToKingdom(plugin));
					msg.add(ChatColor.GREEN+"Request accepted: "+requestId);
				}
				if (accept.equalsIgnoreCase("REJECT"))
				{
					plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().get(requestId).setStatus(Request.REQUEST_STATUS_REJECT);
					msg.add(ChatColor.RED+"Request rejected: "+requestId);
				}
			} else
			{
				msg.add(ChatColor.RED+"Owner INfo "+requestId);
				int ownerId = plugin.getData().getKingdoms().getKingdom(kingdomId).getJoinRequests().get(requestId).getOwnerId();
				CmdOwnerInfo cmd = new CmdOwnerInfo();
				cmd.setPara(0, 1); // page
				cmd.setPara(1, ownerId);
				cmd.execute(plugin, sender);
			}
		}
		
		
		plugin.getMessageData().printPage(sender, msg, page);
		
		
		kingdomId = 0;
		requestId = 0;
		accept = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		Player player = (Player) sender;
		Owner  owner;
		if (isOpOrAdmin(sender) == false)
		{
			owner = plugin.getData().getOwners().getOwnerName(player.getName());
			if (owner == null)
			{
				errorMsg.add(ChatColor.RED+"You are not a valid owner of Realms");
				return false;
			}
			
			if (plugin.getData().getKingdoms().getKingdom(kingdomId) == null)
			{
				errorMsg.add(ChatColor.RED+"Kingdom not exist !");
				return false;
			}
			
			
			if (owner.getNobleLevel() != NobleLevel.KING)
			{
				errorMsg.add(ChatColor.RED+"Only the king can answer to requests !");
				return false;
			}
		}

		if (requestId > 0)
		{
			Kingdom kingdom =  plugin.getData().getKingdoms().getKingdom(kingdomId);
			if (kingdom.getJoinRequests().get(requestId)== null)
			{
				errorMsg.add(ChatColor.RED+"request do not exist !");
				return false;
			}
			if (kingdom.getJoinRequests().get(requestId).getStatus() != Request.REQUEST_STATUS_OPEN)
			{
				errorMsg.add(ChatColor.RED+"request is not open !");
				return false;
			}
		}
		
		if (accept.equalsIgnoreCase("accept"))
		{
			if (accept.equalsIgnoreCase("REJECT"))
			{
				accept = "";
			}
		}
		return true;
	}


}
