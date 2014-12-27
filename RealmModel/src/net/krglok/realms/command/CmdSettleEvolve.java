package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.Map.Entry;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class CmdSettleEvolve extends RealmsCommand
{

	private int settleId;
	private String newType;
	SettleType settleType = null;
	
	public CmdSettleEvolve()
	{
		
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.EVOLVE);
		description = new String[] {
				ChatColor.YELLOW+"/settle EVOLVE [ID] [NewSettleType]",
				"Make the settlement evolve to the new SettleType ",
				"The requirements of the new settletype are checked ",
		    	"also the required money are taken from the bank",
		    	"the position are not changed, the radius are expanded",
		    	"  "
		};
		requiredArgs = 2;
		this.newType = "";
		this.settleId = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 1:
			newType = value;
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
		return new String[] {int.class.getName()
				, String.class.getName()
				};
	}

	private boolean checkRequiredBuilding(Realms plugin, Settlement settle)
	{
		boolean foundall = true;
		for (Entry<String, Integer> entry : plugin.getServerData().getSuperTypeRequirements(settleType.name()).entrySet())
		{
			System.out.println("Require: "+entry.getKey()+":"+entry.getValue());
			boolean found = false;
			if (plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList().containsKey(entry.getKey()))
			{
				System.out.println("Found: "+entry.getKey());
				if (plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList().get(entry.getKey()) >= entry.getValue())
				{
					System.out.println("Found: "+entry.getKey()+":"+plugin.getRealmModel().getSettlements().getSettlement(settleId).getBuildingList().getBuildTypeList().get(entry.getKey()));
					found = true;
				}
			}
			if (found == false)
			{
				foundall = false;
			}
		}
		return foundall;
	}
	
	private boolean checkRequiredCost(Realms plugin, Settlement settle)
	{
		double requiredCost = plugin.getServerData().getSuperTypeCost(settleType.name());
		if (settle.getBank().getKonto() >= requiredCost)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		int page = 1;
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(this.settleId);
		if (settle == null)
		{
			errorMsg.add("Settlement not found !");
			errorMsg.add("See /settle LIST  for ID");
			errorMsg.add("");
			plugin.getMessageData().printPage(sender, msg, page);
			return ;
		}
		String name = settle.getName();
		LocationData position = new LocationData(
				settle.getPosition().getWorld(), 
				settle.getPosition().getX(), 
				settle.getPosition().getY(), 
				settle.getPosition().getZ()-1
				);
		String owner = settle.getOwnerId();
		RegionLocation rLoc = new RegionLocation(newType, position, owner,name);
		World world = plugin.getServer().getWorld(settle.getPosition().getWorld());
		
		plugin.getServerData().destroySuperRegion(name);
		plugin.getServerData().createSuperRegion(world, rLoc);
		errorMsg.add("Settlement "+settle.getSettleType().name()+" are destroyed !");
		errorMsg.add("Settlement "+settleType+ " are created !");
		settle.setSettleType(settleType);
		plugin.getData().writeSettlement(settle);
		errorMsg.add("New Settlement are stored in File");
		errorMsg.add("");
		
		plugin.getMessageData().printPage(sender, msg, page);
		return ;
	}
	

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED+"Not implemented yet !!");
		return false;
		
//		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
//		{
//			errorMsg.add("The Model is busy or not enaled !");
//			errorMsg.add("");
//			return false;
//		}
//		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(this.settleId);
//		if (settle == null)
//		{
//			errorMsg.add("Settlement not found !");
//			errorMsg.add("See /settle LIST  for ID");
//			errorMsg.add("");
//			return false;
//		}
//		// check for ownership
//		if (sender.isOp() == false)
//		{
//			if(isSettleOwner(plugin, sender, settleId) == false)
//			{
//				errorMsg.add("You are not the owner !");
//				errorMsg.add("");
//				return false;
//			}
//		}
//
//		// check for regular types
//		settleType = SettleType.getSettleType(newType);
//		if ((settleType == SettleType.NONE)
//			|| (settleType == SettleType.CAMP)
//			|| (settleType == SettleType.FORTRESS)
//			|| (settleType == SettleType.LEHEN_1)
//			|| (settleType == SettleType.LEHEN_2)
//			|| (settleType == SettleType.LEHEN_3)
//			|| (settleType == SettleType.LEHEN_4)
//			)
//		{
//			errorMsg.add("The settleType is wrong or not allowe!");
//			errorMsg.add("get (CLAIM,HAMLET,TOWN, CITY) ");
//			errorMsg.add("");
//			return false;
//		}
//		// check for buildings
//		if ( checkRequiredBuilding(plugin, settle) )
//		{
//			errorMsg.add("Not all required building in the settlement !");
//			errorMsg.add("");
//			return false;
//		}
//		// check for money
//		if ( checkRequiredCost(plugin, settle) )
//		{
//			errorMsg.add("Not enough money in the settlement bank !");
//			errorMsg.add("");
//			return false;
//		}
//		
//		return true;
	}

}
