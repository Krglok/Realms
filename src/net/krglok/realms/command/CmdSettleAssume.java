package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.List;

import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSettleAssume extends aRealmsCommand
{
	private int settleId;
	
	public CmdSettleAssume()
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.ASSUME);
		description = new String[] {
				ChatColor.YELLOW+"/settle ASSUME [settleID] ",
				"Assume the HAMLET from the NPC Owner ",
				"You must have reputation and money",
		    	"You will be owner of all buildings without ",
		    	" except HOME, HOUSE, MANSION  ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleId = 0;
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

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		Player player = (Player) sender;
    	String playername = player.getName();
		Owner owner = plugin.getRealmModel().getOwners().getOwner(player.getUniqueId().toString());
		if (owner == null)
		{
			msg.add(ChatColor.RED+"You are not a regular owner in REALMS !");
	    	msg.add(" ");
			plugin.getMessageData().printPage(sender, msg, 1);
			return;
		} 
		double cost = plugin.getServerData().getSuperTypeCost(SettleType.HAMLET.name());
		if (plugin.economy.has(player.getName(), cost) == false)
		{
			errorMsg.add(ChatColor.YELLOW+"You not have enough money !");
			errorMsg.add(ChatColor.YELLOW+"You ndeed :"+ConfigBasis.setStrright(cost, 10));
			return;
		}
		
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
		if ( settle != null)
		{
			Owner oldOwner = plugin.getData().getOwners().getOwner(settle.getOwnerId());
			String oldOwnerName = oldOwner.getPlayerName();
			plugin.economy.withdrawPlayer(player.getName(), cost);
			Kingdom kingdom = plugin.getData().getKingdoms().getKingdom(settle.getOwner().getKingdomId());
			if (kingdom != null)
			{
				plugin.economy.bankDeposit(kingdom.getName(), cost);
			} else
			{
				plugin.economy.bankDeposit(oldOwnerName, cost);
			}
			SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(settle.getName());
			if (sRegion != null)
			{
				List<String> perms = null;
				List<String> members = new ArrayList<String>();
				members.add(playername);
				plugin.stronghold.getRegionManager().setMember(sRegion, settle.getName(), members);
				plugin.stronghold.getRegionManager().setOwner(sRegion, playername);
				settle.setOwner(owner);
//				sRegion.addMember(playername, perms );
				for (Building building : settle.getBuildingList().values())
				{
					if ((building.getBuildingType() != BuildPlanType.HOME) 
							&& (building.getBuildingType() != BuildPlanType.HOUSE)
							&& (building.getBuildingType() != BuildPlanType.MANSION)
							&& (building.getBuildingType() != BuildPlanType.KEEP)
							&& (building.getBuildingType() != BuildPlanType.CASTLE)
							&& (building.getBuildingType() != BuildPlanType.STRONGHOLD)
							&& (building.getBuildingType() != BuildPlanType.PALACE)
							)
					{
						if (building.getOwnerId()==oldOwner.getId())
						{
							plugin.stronghold.getRegionManager().setMember(plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion()), playername);
							plugin.stronghold.getRegionManager().setOwner(plugin.stronghold.getRegionManager().getRegionByID(building.getHsRegion()), playername);
							System.out.println(building.getBuildingType().name()+":"+building.getHsRegion()+":"+playername );
							building.setOwnerId(owner.getId());
							plugin.getData().writeBuilding(building);
				    		AchivementName aName = AchivementName.valueOf(building.getBuildingType().name());
			    			if (owner.getAchivList().contains(aName) == false)
			    			{
				    			owner.getAchivList().add(new Achivement(AchivementType.BUILD, aName));
				    			plugin.getData().writeOwner(owner);
				    	    	msg.add(ChatColor.GOLD+"You earn Achivement "+aName.name()+" for building ");
			    			}
						}
					}

				}
				plugin.getData().writeSettlement(settle);
				msg.add("Owner added to "+settle.getName());
				msg.add(ChatColor.GOLD+"Player "+playername+" now owner of the settlement");
				msg.add(ChatColor.GOLD+"Player "+playername+" can use every building in the Settlement");
				msg.add(ChatColor.GOLD+"The settlers  now look at you. Dont disappoint them !");
				msg.add(ChatColor.YELLOW+"Notice you are not a member of the houses !");
				msg.add("");
				if (plugin.getServer().getPlayer(playername) != null)
				{
					for (String s : msg)
					{
						plugin.getServer().getPlayer(playername).sendMessage(s);
					}
				} 
				for (String s : msg)
				{
					sender.sendMessage(s);
				}
				AchivementName aName = AchivementName.TECH1;
    			if (owner.getAchivList().contains(aName) == false)
    			{
	    			owner.getAchivList().add(new Achivement(AchivementType.BUILD, aName));
	    			plugin.getData().writeOwner(owner);
	    	    	msg.add(ChatColor.GOLD+"You earn Achivement "+aName.name()+" for building ");
    			}
    			AchivementName nextTech = plugin.getRealmModel().getKnowledgeData().getKnowledgeList().checkNextRank(owner.getAchivList());
    			if (nextTech != AchivementName.NONE)
    			{
	    			owner.getAchivList().add(new Achivement(AchivementType.BUILD, nextTech));
	    			plugin.getData().writeOwner(owner);
	    	    	msg.add(ChatColor.GOLD+"You earn Techlevel "+nextTech.name()+" for building ");
    			}

			} else
			{
				msg.add("Superregion not found !");
				msg.add("");
			}
		} else
		{
			msg.add("Settlement not found !");
			msg.add("");
		}
    	
		plugin.getMessageData().printPage(sender, msg, 1);
}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().containsID(settleId) == false)
		{
			errorMsg.add(ChatColor.RED+"Wrong Settlement ID !");
			errorMsg.add(getDescription()[0]);
			return false;
		}
		if (isSettleOwner(plugin, sender, settleId) == true)
		{
			if (sender.isOp())
			{
				errorMsg.add(ChatColor.RED+"You are an OP !");
				
			} else
			{
				errorMsg.add(ChatColor.RED+"You are always the Owner !");
				errorMsg.add(" ");
				return false;
			}
		}
		Player player = (Player) sender;
		// check for realms achivement
		Owner owner = plugin.getRealmModel().getOwners().getOwner(player.getUniqueId().toString());
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"You are not a regular owner in REALMS !");
			return false;
		} else
		{
			Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
			if (settle.getOwner().isNPC() == false)
			{
				errorMsg.add(ChatColor.RED+"This is not a NPC settlement !");
				return false;
			}
			if (settle.getSettleType() != SettleType.HAMLET)
			{
				errorMsg.add(ChatColor.RED+"You can only assume HAMLET with reputation !");
				return false;
			}
			
			if (settle.getReputations().getReputation(player.getName()) < ConfigBasis.REPUTATION_GOAL_OWNER)
			{
				errorMsg.add(ChatColor.RED+"Your reputation is not high enough !");
				errorMsg.add(ChatColor.RED+"You need "+ConfigBasis.REPUTATION_GOAL_OWNER);
				errorMsg.add(ChatColor.YELLOW+"Try /settle reputation for your value");
				return false;
			}
			double cost = plugin.getServerData().getSuperTypeCost(SettleType.HAMLET.name());
			if (plugin.economy.has(player.getName(), cost)== false)
			{
				errorMsg.add(ChatColor.RED+"You not have enough money !");
				errorMsg.add(ChatColor.YELLOW+"You ndeed :"+ConfigBasis.setStrright(cost, 10));
				return false;
			}
			
		}
		return true;
	}

}
