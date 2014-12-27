package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.logging.Level;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.KnowledgeNode;

import org.bukkit.ChatColor;
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
			ownerId = 0;
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
			}
			
		}
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
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
			msg.add("Achivements  [ "+owner.getAchivList().size()+" ]");
			for (Achivement achive  : owner.getAchivList().values())
			{
				msg.add(ChatColor.GOLD+achive.getName()
						+" | "+ChatColor.YELLOW+achive.getTypeName()
						);
				KnowledgeNode kNode = plugin.getRealmModel().getKnowledgeData().getKnowledgeList().get(achive.getAchiveName());
				if (kNode != null)
				{
					msg.add("Knowledge  [ "+kNode.getBuildPermission().size()+"/"+kNode.getSettlePermission().size()+" ]");
					for (BuildPlanType bType : kNode.getBuildPermission())
					{
						msg.add(" | "+ChatColor.GREEN+bType.name());
					}
					for (SettleType sType : kNode.getSettlePermission())
					{
						msg.add(" | "+ChatColor.GREEN+sType.name());
					}
				}
			}
		}
		plugin.getMessageData().printPage(sender, msg, page);
		page = 0;
		ownerId = 0; 
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use this command
		return true;
	}

}
