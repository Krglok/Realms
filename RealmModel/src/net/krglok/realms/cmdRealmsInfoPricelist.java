package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ItemPrice;

import org.bukkit.command.CommandSender;

public class cmdRealmsInfoPricelist extends RealmsCommand
{
	private int page;

	public cmdRealmsInfoPricelist()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.PRICELIST);
		description = new String[] {
				"help for you ",
		    	"/realms PRICELIST {page}, show the pricelist  ",
		    	"All items are listed in the central pricelist  ",
		    	"The list has many pages. ",
		    	" "
			};
			requiredArgs = 1;
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
		return new String[] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		msg.add("== Pricelist size :"+plugin.getData().getPriceList().size());
		for (ItemPrice item : plugin.getData().getPriceList().values())
		{
			msg.add(item.ItemRef()+ " : "+item.getFormatedBasePrice());
		}
		if (msg.size() < 2)
		{
			msg.add("== ");
		}
		plugin.getMessageData().printPage(sender, msg, page);
		
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
	}

}
