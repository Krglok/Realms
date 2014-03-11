package net.krglok.realms;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;

public class CmdSettleBiome extends RealmsCommand
{

	private int settleId ;
	private int page;

	public CmdSettleBiome( )
	{
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.BIOME);
		description = new String[] {
				ChatColor.YELLOW+"/settle BIOME [ID] [page]",
				"Show list of Biome Material Factors ",
		    	"only the modified Materials are listed ",
		    	"  "
		};
		requiredArgs = 1;
		this.settleId = 0;
		this.page = 0;
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
			settleId = value;
			break;
		case 1 :
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
		return new String[] {int.class.getName(), int.class.getName()  };
	}

	private ArrayList<String> makeBiomeItemList(Realms plugin,ItemList items, Biome biome, ArrayList<String> msg)
	{
		int factor;
		for (Item item : items.values() )
		{
			factor = plugin.getServerData().getBioneFactor(biome, Material.getMaterial(item.ItemRef()));
			if (factor != 0)
			{
				String name = ConfigBasis.setStrleft(item.ItemRef()+"__________", 12);
				String sFactor ;
				if (factor < 0)
				{
					sFactor = ChatColor.RED+ConfigBasis.setStrright(factor, 4);
				} else
				{
					sFactor = ChatColor.GREEN+ConfigBasis.setStrright(factor, 4);
					
				}
				msg.add(name +":"+sFactor+" %" );
			}
		}
		return msg;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		ItemList items = new ItemList();
		Settlement settle = plugin.getRealmModel().getSettlements().getSettlement(settleId);
		if ( settle != null)
		{
			msg.add(settle.getId()+":"+settle.getName()+"  : "+ChatColor.YELLOW+settle.getBiome());
			items = ConfigBasis.initFoodMaterial();
			msg.add(ChatColor.YELLOW+"Food");
			msg = makeBiomeItemList(plugin, items, settle.getBiome(), msg);
			items = ConfigBasis.initBuildMaterial();
			msg.add(ChatColor.YELLOW+"BuildMaterial");
			msg = makeBiomeItemList(plugin, items, settle.getBiome(), msg);
			items = ConfigBasis.initOre();
			msg.add(ChatColor.YELLOW+"Ore");
			msg = makeBiomeItemList(plugin, items, settle.getBiome(), msg);
			items = ConfigBasis.initValuables();
			msg.add(ChatColor.YELLOW+"Valuables");
			msg = makeBiomeItemList(plugin, items, settle.getBiome(), msg);
			items = ConfigBasis.initMaterial();
			msg.add(ChatColor.YELLOW+"Other");
			msg = makeBiomeItemList(plugin, items, settle.getBiome(), msg);
		}
		plugin.getMessageData().printPage(sender, msg, page);
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getRealmModel().getSettlements().containsID(settleId))
		{
			return true;
		} else
		{
			errorMsg.add("Settlement not found ! ");
			errorMsg.add(" ");
			return false;
		}
	}

}
