package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.logging.Level;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.RealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.KnowledgeNode;

import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdOwnerInfo extends RealmsCommand
{
	private int page;
	private int ownerId;
	
	public CmdOwnerInfo()
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.INFO);
		description = new String[] {
				ChatColor.YELLOW+"/owner INFO {page} {ID}  ",
		    	"Show the Owner Data   ",
		    	"in detail you can only see your own data ",
		    	"for others only the common data shown",
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
		Owner me = plugin.getData().getOwners().getOwner(player.getUniqueId().toString());
		if (me == null)
		{
			if(plugin.getData().getOwners().getOwnerName(player.getName()) == null)
			{
				Owner owner = Owner.initDefaultOwner();
				owner.setPlayerName(player.getName());
				owner.setUuid(player.getUniqueId().toString());
				owner.setCommonLevel(CommonLevel.COLONIST);
				owner.setNobleLevel(NobleLevel.COMMONER);
				plugin.getData().getOwners().addOwner(owner);
				plugin.getData().writeOwner(owner);
				player.sendMessage("Owner is inilized for you !");
				player.sendMessage("use /Realms Owner for link to your existing settlements");
				plugin.getLog().log(Level.INFO,"Owner init for "+player.getName());
				ownerId = owner.getId();
			}
		} else
		{
			ownerId = me.getId();
		}
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
		for (Achivement achive : owner.getAchivList().values())
		{
			switch (achive.getAchiveName())
			{
			case TECH0 :
				owner.setCommonLevel(CommonLevel.COLONIST);
				break;
			case TECH1 :
				owner.setCommonLevel(CommonLevel.SETTLER);
				break;
			case TECH4 :
				owner.setCommonLevel(CommonLevel.MAYOR);
				break;
			case TECH5 :
				owner.setCommonLevel(CommonLevel.COUNCILOR);
				break;
			case TECH7 :
				owner.setCommonLevel(CommonLevel.SENATOR);
				break;
			default :
				break;
			}
		}
		
		msg.add("ID |Nobility  |Commonlevel  |   | Player      |  [ "+plugin.getData().getOwners().size()+" ]");
		msg.add(owner.getId()
				+" | "+ChatColor.GOLD+owner.getNobleLevel()
				+" | "+ChatColor.YELLOW+owner.getCommonLevel()
				+" | "+owner.getKingdomId()
				+" | "+owner.getPlayerName()
				);
		msg.add(" ");
		if ((me.getId() == owner.getId()) || player.isOp())
		{
			ArrayList<BuildPlanType> aList = new ArrayList<BuildPlanType>();
			ArrayList<SettleType> sList = new ArrayList<SettleType>();
//			aList.add(owner.getAchivList());
			sList.addAll(plugin.getRealmModel().getKnowledgeData().getKnowledgeList().getSettlePermission(owner.getAchivList()));
			aList.addAll(plugin.getRealmModel().getKnowledgeData().getKnowledgeList().getPermissions(owner.getAchivList()));
			for (Achivement achiv : owner.getAchivList().values())
			{
				if (achiv.isEnaled())
				{
					msg.add(ChatColor.GOLD+achiv.getName()+" "+achiv.isEnaled());
				} else
				{
					msg.add(ChatColor.GOLD+achiv.getName()+" "+ChatColor.RED+achiv.isEnaled());
				}
			}
			
			msg.add("Build Permissions  [ "+aList.size()+" ]");
			int column = 0;
			String line = "";
			for (BuildPlanType bPerm  : aList)
			{
				column++;
				if (column > 3)
				{
					msg.add(ChatColor.GOLD+line);
					line =  " | "+bPerm.name();
					column = 1;
				} else
				{
					line = line + " | "+bPerm.name();
				}
			}
			msg.add(ChatColor.GOLD+line);
			column = 0;
			line = "";
			msg.add("Settlement Permissions  [ "+sList.size()+" ]");
			for (SettleType bPerm  : sList)
			{
				column++;
				if (column > 3)
				{
					msg.add(ChatColor.GOLD+line);
					line =  " | "+bPerm.name();
					column = 1;
				} else
				{
					line = line + " | "+bPerm.name();
				}
			}
			msg.add(ChatColor.GOLD+line);
			msg.add("Minecraft Achievements ");
			for (Achievement mcAchiv : Achievement.values())
			{
				if (player.hasAchievement(mcAchiv)) 
				{
					msg.add(ChatColor.GREEN+mcAchiv.name());
				}
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
		return true;
	}

}
