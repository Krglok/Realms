package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.RegionType;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.Request;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * <pre>
 * base class for player command executed in the plugin.
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


	/**
	 * <pre>
	 * give a list of classnames as String array
	 * set for every parameter of the command the type like <class>.class.getName()
	 * for a int , string it shows 
	 * new String[] { int.class.getName(), String.class.getName() }
	 * 
	 * @return array of class names
	 * </pre>
	 */
	@Override
	public abstract String[] getParaTypes();
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * write here the code for command execution
	 */
	@Override
	public abstract void execute(Realms plugin, CommandSender sender);
//	{
//		// TODO Auto-generated method stub
//		
//	}

	/**
	 * write her code for checking permissions and other conditions for the command
	 */
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

	/**
	 * check if sender is an op or has realms.admin permission 
	 * set a errorMessage in the global message storage.
	 * @param sender
	 * @return
	 */
	public boolean isOpOrAdminMsg(CommandSender sender)
	{
		if (isOpOrAdmin(sender) == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		return true;
	}
	
	/**
	 * check if sender is an op or has realms.admin permission
	 * silent check without message generation 
	 * 
	 * @param sender
	 * @return
	 */
	public boolean isOpOrAdmin(CommandSender sender)
	{
		if (sender.isOp() == true)
		{
			return true;
		}
		if (sender instanceof Player)
		{
			if (sender.hasPermission(RealmsPermission.ADMIN.getValue()) == false)
			{
//				errorMsg.add("You are not an Admins !  ");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * check for an settlement with  the given settleId
	 * 
	 * @param plugin
	 * @param settleID
	 * @return
	 */
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
	
	/**
	 * check for superregion (name) has the sender as owner
	 * direct access to Herostronghold plugin
	 * 
	 * @param plugin
	 * @param sender
	 * @param name
	 * @return
	 */
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
	
	/**
	 * give first region at position
	 * @param plugin
	 * @param position
	 * @return  region object
	 */
	protected Region findRegionAtPosition(Realms plugin,Location position)
	{
	    for (Region region : plugin.stronghold.getRegionManager().getContainingRegions(position))
	    {
//	    	System.out.println(region.getType());
	    	if (region != null)
	    	{
	    		return region;
	    	}
	    }
		return null;
	}


	/**
	 * give region id at player position
	 * @param plugin
	 * @param playert/
	 * @return region id
	 */
	protected Integer findRegionIdAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
		Region region = findRegionAtPosition( plugin, position);
	    if ( region != null)
	    {
	    	BuildPlanType bType = plugin.getConfigData().regionToBuildingType(region.getType());
	    	if (bType != BuildPlanType.NONE)
	    	{
	    		return region.getID();
	    	}
	    }
		return -1;
	}
	
    /**
     * give first superegion at player position
     * @param plugin
     * @param player
     * @return superregion name
     */
	protected String findSettlementAtLocation(Realms plugin, Player player)
	{
		Location position = player.getLocation();
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if ((sRegion.getType().equalsIgnoreCase( SettleType.HAMLET.name()) )
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.TOWN.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.CITY.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.METROPOLIS.name()))
	    		)
	    	{
	    		return sRegion.getName();
	    	}
	    }
		return "";
	}

	/**
	 * give first superregion at position
	 * @param plugin
	 * @param position
	 * @return superregion object  or null
	 */
	protected SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if ((sRegion.getType().equalsIgnoreCase( SettleType.HAMLET.name()) )
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.TOWN.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.CITY.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.METROPOLIS.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_1.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_2.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_3.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_4.name()))
	    		)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}

	protected SuperRegion findSettlementAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if ((sRegion.getType().equalsIgnoreCase( SettleType.HAMLET.name()) )
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.TOWN.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.CITY.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.METROPOLIS.name()))
	    		)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}

	protected SuperRegion findLehenAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if ((sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_1.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_2.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_3.name()))
	    		|| (sRegion.getType().equalsIgnoreCase( SettleType.LEHEN_4.name()))
	    		)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}
	
	public boolean isSettleOwner(Realms plugin, CommandSender sender, int settleID)
	{
		if (isOpOrAdmin(sender))
		{
			return true;
		}
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleID);
		if (settle == null)
		{
			errorMsg.add("Settlement NOT not found:"+settleID);
			return false;
		}
//		if (settle.getOwnerId() == "")
//		{
//			return true;
//		}
		Player player = (Player) sender;
		Owner owner = plugin.getData().getOwners().getOwner(player.getUniqueId().toString()); 
		if (owner.getId() != settle.getOwnerId())
		{
			errorMsg.add("You are NOT the owner of the Settlement !");
			return false;
		}
		
		return true;
	}

	protected boolean isRegimentOwner(Realms plugin, CommandSender sender, int regID)
	{
		if (isOpOrAdmin(sender))
		{
			return true;
		}
		Regiment regiment = plugin.getRealmModel().getRegiments().get(regID);
		if (regiment.getOwnerId() == 0)
		{
			return true;
		}
//		if (sender.getName().equalsIgnoreCase(regiment.getOwner()) == false)
		Player player = (Player) sender;
	    if (player.getUniqueId().toString().equalsIgnoreCase(regiment.getOwner().getUuid()) == false)
		{
			errorMsg.add("You are NOT the owner of the Regiment !");
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
		if (plugin.economy != null)
		{
			errorMsg.add("NO economy is installed !");
			return false;
		}
		Player player = (Player) sender;
		
		if (plugin.economy.has(player.getName(),  amount) == false)
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
//		msg.add(ChatColor.RED+"Nothig found for "+helpPage );
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
    		
//    		RealmsSubCommandType subCommandType = RealmsSubCommandType.searchRealmSubCommandType(search);
    		String name = search.toUpperCase();
    		for (RealmsSubCommandType rsc : RealmsSubCommandType.values())
    		{
    			if (rsc.name().contains(name))
    			{
//    				return rsc;
    				msg.addAll(getCommandDescription(cmdList,  this.command(), rsc));
    			}
    		}

//			msg.addAll(getCommandDescription(cmdList,  this.command(), subCommandType));
    	}
		return msg;

	}
	
	/**
	 * overwrite the book with the new content
	 * 
	 * @param book
	 * @param msg
	 * @param author
	 * @param title
	 * @return
	 */
	public ItemStack writeBook(ItemStack book, ArrayList<String> msg, String author, String title)
	{
		final BookMeta bm = (BookMeta) book.getItemMeta();
		if (bm.hasPages())
		{
			// Pages alle loeschen
			ArrayList<String> newPages = new ArrayList<String>();
			bm.setPages(newPages);
		}
		String sPage = "";
		int line = 0;
		int bookPage = 0;
		for (int i=0; i < msg.size(); i++)
		{
			line++;
			sPage = sPage+msg.get(i);
			if ((line > 11) && (bookPage < 50))
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
	
	/**
	 * do the join and set all lehen to the new Kingdom
	 * @param plugin
	 * @return
	 */
	public ArrayList<String> joinOwnerToKingdom(Realms plugin, int kingdomId, Owner owner)
	{
		ArrayList<String> msg = new ArrayList<String>();
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
				if (lehen.getNobleLevel() != NobleLevel.KING)
				{
				  lehen.setParentId(parent.getId());
				} else
				{
					lehen.setParentId(0);
				}
			}
			plugin.getData().writeLehen(lehen);
			plugin.getData().writeOwner(owner);
			msg.add("Lehen "+lehen.getId()+" set to Kingdom"+kingdomId);
		}
		return msg;
	}

	public Map<Integer, Integer> checkRegionRequirements(Realms plugin, Location currentLocation, String regionType)
	{

		// Prepare a requirements checklist
		RegionType currentRegionType =	plugin.stronghold.getRegionManager().getRegionType(regionType);
		ArrayList<ItemStack> requirements = currentRegionType.getRequirements();
		Map<Integer, Integer> reqMap = null;
		if (!requirements.isEmpty())
		{
			reqMap = new HashMap<Integer, Integer>();
			for (ItemStack currentIS : requirements)
			{
				reqMap.put(new Integer(currentIS.getTypeId()), new Integer(currentIS.getAmount()));
			}

			// Check the area for required blocks
			int radius = (int) Math.sqrt(currentRegionType.getBuildRadius());

			int lowerLeftX = (int) currentLocation.getX() - radius;
			int lowerLeftY = (int) currentLocation.getY() - radius;
			lowerLeftY = lowerLeftY < 0 ? 0 : lowerLeftY;
			int lowerLeftZ = (int) currentLocation.getZ() - radius;

			int upperRightX = (int) currentLocation.getX() + radius;
			int upperRightY = (int) currentLocation.getY() + radius;
			upperRightY = upperRightY > 255 ? 255 : upperRightY;
			int upperRightZ = (int) currentLocation.getZ() + radius;

			World world = currentLocation.getWorld();

			outer: for (int x = lowerLeftX; x < upperRightX; x++)
			{

				for (int z = lowerLeftZ; z < upperRightZ; z++)
				{

					for (int y = lowerLeftY; y < upperRightY; y++)
					{

						int type = world.getBlockTypeIdAt(x, y, z);
						if (type != 0 && reqMap.containsKey(type))
						{
							if (reqMap.get(type) < 2)
							{
								reqMap.remove(type);
								if (reqMap.isEmpty())
								{
									break outer;
								}
							} else
							{
								reqMap.put(type, reqMap.get(type) - 1);
							}
						}
					}

				}

			}
		}
		return reqMap;
	}

	public ArrayList<String> faultList(Map<Integer,Integer> reqMap)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add(ChatColor.RED+"You don't have all of the required blocks in this structure.");
		int j = 0;
		for (int type : reqMap.keySet())
		{
			int reqAmount = reqMap.get(type);
			String reqType = Material.getMaterial(type).name();
			msg.add(ChatColor.GOLD + reqType+":"+reqAmount);
		}
		return msg;
		
	}
	
}
