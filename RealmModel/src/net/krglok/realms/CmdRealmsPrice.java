package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsPrice extends RealmsCommand
{
	private String itemRef;
	private double price;

	public CmdRealmsPrice()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.PRICE);
		description = new String[] {
				ChatColor.YELLOW+"/realms PRICE [ITEM] [PRICE],   ",
		    	"Set teh prive of <Item> in the central pricelist ",
		    	"Item must be uppercase, MATERIAL.NAME ",
		    	"price <0.00> ",
		    	" "
			};
			requiredArgs = 2;
			this.itemRef = "";
			this.price = 0.0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				itemRef = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{
		switch (index)
		{
		case 0 :
				price = value;
			break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName(), double.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		msg.add(""+ itemRef);
		itemRef = itemRef.toUpperCase();
		String sPrice = ConfigBasis.setStrformat2(plugin.getData().getPriceList().getBasePrice(itemRef),7);
		msg.add("Old Price : "+sPrice);
		plugin.getData().getPriceList().add(itemRef, price);
		String newPrice = ConfigBasis.setStrformat2(price,7);
		msg.add("New Price : "+newPrice);
		msg.add(" ");
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp())
		{
			if (itemRef == "")
			{
				errorMsg.add("ItemName not given");
				errorMsg.add("Give a valid Material Name in uppercase");
			}
			if (price <= 0.0)
			{
				errorMsg.add("[Realm Model] NOT enabled or too busy");
				errorMsg.add("Try later again");
			}
			
		} else
		{
			errorMsg.add("You are not a OP ");
			errorMsg.add("");
			
		}
		return false;
	}

}
