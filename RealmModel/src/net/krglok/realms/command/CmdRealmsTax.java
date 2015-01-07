package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.LehenList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsTax extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsTax( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TAX);
		description = new String[] {
				ChatColor.YELLOW+"/realms TAX [page]   ",
		    	" show the tax calculation  ",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
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
			page = value;
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
		return new String[] {int.class.getName()  };
	}

	private double sumBuilding(Settlement settle)
	{
		double sum = 0;
		for (Building building : settle.getBuildingList().values())
		{
			sum = sum + building.getSales();
		}
		
		return sum;
	}
	
	private double getChildSum(Realms plugin, Lehen lehen)
	{
		double childSum = 0;
		for (Lehen child : plugin.getData().getLehen().getChildList(lehen.getId()).values())
		{
			childSum = childSum + child.getSales() * ConfigBasis.SALES_TAX / 100.0;
		}
		return childSum;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"Realms Tax Execute with OnTax() ");
    	plugin.getRealmModel().TaxTest();
//    	msg.add(ChatColor.YELLOW+"Settlements ");
//    	for (Settlement settle : plugin.getData().getSettlements().values())
//    	{
//    		double umsatz = sumBuilding(settle);
//    		double umsatzTax = umsatz * ConfigBasis.SALES_TAX / 100.0;
//    		double settlerTax = settle.getResident().getSettlerCount() * ConfigBasis.SETTLER_TAXE; 
//    		msg.add(settle.getId()
//    				+" | "+ChatColor.YELLOW+ConfigBasis.setStrleft(settle.getName(),12)
//    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(umsatzTax,9)
//    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(settlerTax,10)
//    				);
//    		Owner owner = plugin.getData().getOwners().getOwnerName(settle.getOwnerId());
//    		int lehenId = settle.getTributId();
//    		int kingdomId = 0;
//			Lehen ownerLehen = plugin.getData().getLehen().getLehen(lehenId);
//    		if (ownerLehen != null)
//    		{
//    			kingdomId = ownerLehen.getKingdomId();
//    			ownerLehen.depositSales((umsatzTax));
//	    		msg.add("> L "+ownerLehen.getId()
//	    				+" | "+ChatColor.YELLOW+ConfigBasis.setStrleft(ownerLehen.getName(),10)
//	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(umsatzTax,9)
//	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(ownerLehen.getSales(),10)
//	    				);
//    			
//    		} else if (owner != null)
//    		{
//    			kingdomId = owner.getKingdomId();
//    			owner.depositSales((umsatzTax));
//	    		msg.add("> O "+owner.getId()
//	    				+" | "+ChatColor.YELLOW+ConfigBasis.setStrleft(owner.getPlayerName(),10)
//	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(umsatzTax,9)
//	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(owner.getSales(),10)
//	    				);
//    		}
//			Lehen  kingLehen =  plugin.getData().getLehen().getKingdomRoot(kingdomId);
//			if (kingLehen != null)
//			{
//    			kingLehen.depositSales((settlerTax));
////	    		msg.add(" | K "+kingLehen.getId()
////	    				+" | "+ChatColor.YELLOW+ConfigBasis.setStrleft(kingLehen.getName(),10)
////	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(settlerTax,9)
////	    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(kingLehen.getSales(),10)
////	    				);
//			}
//    	}
//    	
//    	for (Kingdom kingdom : plugin.getData().getKingdoms().values())
//    	{
//    		if (kingdom.getId() > 0)
//    		{
//    			Lehen root = plugin.getData().getLehen().getKingdomRoot(kingdom.getId());
//    			if (root != null)
//    			{
//	    			for (Lehen lehen :plugin.getData().getLehen().getChildList(root.getId()).values())
//	    			{
//	    				for (Lehen child : plugin.getData().getLehen().getChildList(lehen.getId()).values())
//	    				{
//	    					double childsum = getChildSum(plugin,child);
//	    					child.depositSales(childsum);
//	    				}
//	    				double sum = getChildSum(plugin,lehen);
//	    				lehen.depositSales(sum);
//	    			}
//	    			double rootSum = getChildSum(plugin,root);
//	    			root.depositSales(rootSum);
//		    		msg.add("Kingdom "+root.getId()
//		    				+" | "+ChatColor.YELLOW+ConfigBasis.setStrleft(root.getName(),10)
//		    				+" | "+ChatColor.GREEN+ConfigBasis.setStrformat2(root.getSales(),10)
//		    				);
//    			}
//    		}
//    	}
    	
		plugin.getMessageData().printPage(sender, msg, page);
		page = 1;
	}

	
	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (isOpOrAdmin(sender) == false)
		{
			errorMsg.add(ChatColor.RED+"You are not OP or Admin");
			return false;
		}
		return true;
	}

}
