package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * <pre>
 * base class for idividual command executed in the plugin.
 * the command is specified by 
 * - RealmsCommandType, this is the registered commandname
 * - RealmsSubCommandType, this is the 1th parameter
 * the first parameter is always the subcommand
 * 
 * @author oduda
 *
 *</pre>
 */
public abstract class RealmsCommand implements iRealmsCommand
{
	private RealmsCommandType command;
	private RealmsSubCommandType subCommand;
	protected String[] description;
	protected int requiredArgs;
	protected ArrayList<String> errorMsg;
	protected boolean isParserError; 
	protected String helpPage;
	
	
	public RealmsCommand(RealmsCommandType command, RealmsSubCommandType subCommand)
	{
		this.command = command; 
		this.subCommand = subCommand;
		this.description = null;
		this.requiredArgs = 0;
		this.errorMsg = new ArrayList<String>();
		this.isParserError = false;
		this.helpPage = "";

	}


	public boolean isParserError()
	{
		return isParserError;
	}


	public void setParserError(boolean isParserError)
	{
		this.isParserError = isParserError;
	}


	@Override
	public abstract String[] getParaTypes();
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public abstract void execute(Realms plugin, CommandSender sender);
//	{
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public abstract boolean canExecute(Realms plugin, CommandSender sender);
//	{
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public String[] getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(String[] newDescription)
	{
		this.description = newDescription;
	}

	
	@Override
	public RealmsCommandType command()
	{
		return this.command;
	}
	
	@Override
	public RealmsSubCommandType subCommand()
	{
		return this.subCommand;
	}


	@Override
	public ArrayList<String> getDescriptionString()
	{
		ArrayList<String> msg = new ArrayList<String>();
		for (int i = 0; i < description.length; i++)
		{
			msg.add(description[i].toString());
		}
		return msg;
	}
	
	@Override
	public int getRequiredArgs()
	{
		return requiredArgs;
	}

	@Override
	public  ArrayList<String> getErrorMsg()
	{
		return this.errorMsg;
	}
	
	public void addErrorMsg (String s)
	{
		this.errorMsg.add(s);
	}

	public boolean isOpOrAdmin(CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		if (sender instanceof Player)
		{
			if (sender.hasPermission(RealmsPermission.ADMIN.getValue()) == false)
			{
				errorMsg.add("You are not an Admins !  ");
				return false;
			}
		}
		return true;
	}
	
	public boolean existSettlement (Realms plugin, int settleID)
	{
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			if (plugin.getRealmModel().getSettlements().getSettlement(settleID) != null)
			{
				return true;
			}
			errorMsg.add("Settlement not found !!!");
			errorMsg.add("The ID is wrong or not a number ?");
			return false;
		}
		errorMsg.add("[Realm Model] NOT enabled or too busy");
		errorMsg.add("Try later again");
		return false;

	}
	
	public boolean isSuperRegionOwner (Realms plugin,CommandSender sender, String name )
	{
		// pruefe ob Superegion gueltig bzw. vorhanden ist
		if (plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			if (sender.isOp() == false)
			{
				// pruefe ob der Player der Owner ist
				if (plugin.stronghold.getRegionManager().getSuperRegion(name).getOwners().isEmpty() == false)
				{
					if( sender.getName().equalsIgnoreCase(plugin.stronghold.getRegionManager().getSuperRegion(name).getOwners().get(0)) == false)
					{
						return false;
					}
				}
			}
			SettleType settleType = plugin.getConfigData().superRegionToSettleType((plugin.stronghold.getRegionManager().getSuperRegion(name).getType()));
			if (settleType == SettleType.NONE)
			{
				return false;
			}
		}
		return true;
		
	}
	
	public boolean isSettleOwner(Realms plugin, CommandSender sender, int settleID)
	{
		if (isOpOrAdmin(sender))
		{
			return true;
		}
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle.getOwner() == "")
		{
			return true;
		}
		if (sender.getName().equalsIgnoreCase(settle.getOwner()) == false)
		{
			errorMsg.add("You are NOT the owner of the Settlement !");
			return false;
		}
		
		return true;
	}
	
	public boolean hasItem( CommandSender sender, String itemRef, int amount)
	{
		if ((sender instanceof Player) == false)
		{
			errorMsg.add("You are NOT a Player !");
			return false;
		}
		Player player = (Player) sender;
		
		if (player.getInventory().contains(Material.getMaterial(itemRef), amount) == false)
		{
			errorMsg.add("You have NOT enough items !");
			return false;
		}
		return true;
	}

	public boolean hasMoney(Realms plugin, CommandSender sender,  double amount)
	{
		if ((sender instanceof Player) == false)
		{
			errorMsg.add("You are NOT a Player !");
			return false;
		}
		if (Realms.economy != null)
		{
			errorMsg.add("NO economy is installed !");
			return false;
		}
		Player player = (Player) sender;
		
		if (Realms.economy.has(player.getName(),  amount) == false)
		{
			errorMsg.add("You have NOT enough money !");
			return false;
		}
		return false;
	}
	
	public ArrayList<String> getCommandDescription(RealmsCommand[] cmdList
			, RealmsCommandType commandType
			, RealmsSubCommandType subCommandType)
	{
		for (iRealmsCommand cmd : cmdList)
		{
			if ((cmd.command() == commandType) 
				&& (cmd.subCommand() == subCommandType)
				) 
			{
				return cmd.getDescriptionString();
			}
		}
		ArrayList<String> msg = new ArrayList<String>();
		msg.add(ChatColor.RED+"Nothig found for "+helpPage );
		return msg;
	}


	public ArrayList<String> makeHelpPage(RealmsCommand[] cmdList, ArrayList<String> msg, String search )
	{
		System.out.println("Word: "+search);
    	if (search == "")
    	{
	    	msg.add(ChatColor.GREEN+"{REALMS]   Help Page");
			msg.addAll(getDescriptionString());
			if (this.subCommand() != RealmsSubCommandType.HELP)
			{
				for (iRealmsCommand cmd : cmdList)
				{
					if ((cmd.subCommand() != RealmsSubCommandType.NONE) 
						&& (this.command() == cmd.command())
						)
					{
						String line = cmd.getDescription()[0];
						msg.add(line);
					}
				}
				
			} else
			{
				for (iRealmsCommand cmd : cmdList)
				{
					if ((cmd.subCommand() != RealmsSubCommandType.NONE) 
						&& (this.command() == cmd.command())
						)
					{
						msg.addAll(cmd.getDescriptionString());
					}
				}
			}
    	} else
    	{
    		
    		RealmsSubCommandType subCommandType = RealmsSubCommandType.getRealmSubCommandType(search);
			msg.addAll(getCommandDescription(cmdList,  this.command(), subCommandType));
    	}
		return msg;

	}
	
	public ItemStack writeBook(ItemStack book, ArrayList<String> msg, String author, String title)
	{
		final BookMeta bm = (BookMeta) book.getItemMeta();
		String sPage = "";
		int line = 0;
		int bookPage = 0;
		for (int i=0; i < msg.size(); i++)
		{
			line++;
			sPage = sPage+msg.get(i);
			if ((line > 12) && (bookPage < 50))
			{
				bm.addPage(sPage);
				sPage = "";
				line = 0;
				bookPage++;
			}
		}
		if ((sPage != "") && (bookPage < 50))
		{
			bm.addPage(sPage);
		}
		bm.setAuthor(author);
		bm.setTitle(title);
		book.setItemMeta(bm);

		return book;
	}
}
