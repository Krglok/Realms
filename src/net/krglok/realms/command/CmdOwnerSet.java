package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.Owner;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdOwnerSet extends aRealmsCommand
{
	private int page;
	private int ownerId;
	private String achivementName; 
	
	public CmdOwnerSet()
	{
		super(RealmsCommandType.OWNER, RealmsSubCommandType.SET);
		description = new String[] {
				ChatColor.YELLOW+"/owner SET [ownerId] [ACHIVEMENTNAME]  ",
		    	"Set the Owner the AchivementName   ",
		    	"Achivement give Building permissions ",
		    	"and Settlement permissions ",
		    	" "
			};
			requiredArgs = 2;
			page = 1;
			ownerId = 0;
			achivementName = "";
}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			achivementName = value;
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
		case 0:
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
		return new String[] { int.class.getName(), String.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		AchivementName aName = AchivementName.valueOf(achivementName);
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
		Achivement achive = new Achivement(AchivementType.BOOK, aName,true);
		owner.getAchivList().add(achive);
		plugin.getData().writeOwner(owner);

		msg.add(ChatColor.GREEN+"Achivement "+aName.name()+" set for "+owner.getPlayerName());
		msg.add("");
		for (Achivement achiv  : owner.getAchivList().values())
		{
			msg.add(ChatColor.GOLD+achiv.getName()
					+" | "+ChatColor.YELLOW+achiv.getTypeName()
					);
		}
		msg.add("");
		plugin.getMessageData().printPage(sender, msg, page);
		ownerId = -1;
		achivementName = "";
		page = 1;
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (achivementName == "")
		{
			return false;
		}
		if (AchivementName.contains(achivementName) == false)
		{
			errorMsg.add(ChatColor.RED+"AchivementName is wrong");
			return false;
		}
		AchivementName aName = AchivementName.valueOf(achivementName);
		if (aName == null)
		{
			errorMsg.add(ChatColor.RED+"AchivementName is wrong");
			return false;
		}
		Owner owner = plugin.getData().getOwners().getOwner(ownerId);
		if (owner == null)
		{
			errorMsg.add(ChatColor.RED+"owner is wrong");
			return false;
		}
		boolean found = false;
		for (Achivement achive  : owner.getAchivList().values())
		{
			if (achive.getAchiveName() == aName)
			{
				found = true;
			}
		}
		if (found == true)
		{
			errorMsg.add(ChatColor.RED+"owner always has the achivement");
			return false;
		}
		// only for OPs or Admin
		if (isOpOrAdminMsg(sender))
		{
			return true;
		}
		return false;
	}

}
