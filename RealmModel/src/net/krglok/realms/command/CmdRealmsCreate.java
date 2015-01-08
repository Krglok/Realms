package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRealmsCreate extends RealmsCommand
{

	private String name;
	private SettleType settleType;
	
	public CmdRealmsCreate()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.CREATE);
		description = new String[] {
				ChatColor.YELLOW+"/realms CREATE [SuperRegionName] ",
				"Create a Settlement from the supereregion <NAME> ",
				"You must be the an OP",
		    	"show a status report ",
		    	"  "
		};
		requiredArgs = 1;
		this.name = "";
		this.settleType = SettleType.NONE;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				name = value;
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

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName() };
	}

	private boolean cmdCreate(Realms plugin, CommandSender sender)
	{
		// /settle create [SuperRegion]
		int page = 1;
		ArrayList<String> msg = new ArrayList<String>();
//		Player player = (Player) sender;
		String superRegionName = "";
		superRegionName = this.name;

		msg.add("Settlement Create [ "+superRegionName+" ]");
		SuperRegion sRegion = plugin.stronghold.getRegionManager().getSuperRegion(superRegionName);
		if (sRegion == null)
		{
			return false;
		}
		String playerName = "";
		boolean isNPC = false;
		Owner owner; 
		if (sRegion.getOwners().size() == 0)
		{
			playerName = plugin.getRealmModel().getOwners().get(0).getPlayerName();
			isNPC = true;
			owner = plugin.getRealmModel().getOwners().getOwner(ConfigBasis.NPC_0);
		} else
		{
			for (String name : sRegion.getOwners())
			{
				if (playerName == "")
				{
					playerName = name;
				}
			}
			owner = plugin.getRealmModel().getOwners().getOwner(playerName);
    		if (owner == null)
    		{

    			owner = Owner.initDefaultOwner();
    			owner.initMayor();
    		}
		}
		Biome biome = sRegion.getLocation().getWorld().getBlockAt(sRegion.getLocation()).getBiome();
		LocationData position = new LocationData(
				sRegion.getLocation().getWorld().getName(),
				sRegion.getLocation().getX(), 
				sRegion.getLocation().getY(),
				sRegion.getLocation().getZ());
		msg.add(" Owner: "+playerName);
		Settlement settlement = new Settlement(playerName, position,  settleType, superRegionName,biome); //, plugin.getRealmModel().getLogList());
		settlement.initSettlement(plugin.getData().getPriceList());
		settlement.setOwner(owner);
		settlement.setOwnerId(owner.getPlayerName());
		plugin.getRealmModel().getSettlements().addSettlement(settlement);

		msg.add("");
		for (Region region : plugin.stronghold.getRegionManager().getContainedRegions(sRegion))
		{
//    		msg.add("  "+region.getType()+" : "+ChatColor.YELLOW+region.getID()+" : "+" Owner: "+region.getOwners());
    		
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
			BuildPlanType buildingType = plugin.getConfigData().regionToBuildingType(hsRegionType);
			Building building = new Building(
					buildingType, 
					hsRegion, 
					new LocationData(
					sRegion.getLocation().getWorld().getName(),
					sRegion.getLocation().getX(), 
					sRegion.getLocation().getY(),
					sRegion.getLocation().getZ()),
					settlement.getId()
					);
			plugin.getRealmModel().getBuildings().addBuilding(building);
			plugin.getData().writeBuilding(building);
			System.out.println("[REALMS] "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
		}
		// make not dynamic initialization
		settlement.setOwnerId(ConfigBasis.NPC_0);
		
		settlement.setBuildingList(plugin.getRealmModel().getBuildings().getSubList(settlement.getId()));
		settlement.initSettlement(plugin.getData().getPriceList());
//		settlement.setSettlerMax();
//		settlement.setWorkerNeeded();

		// minimum settler on create
		settlement.getResident().setSettlerCount(settlement.getResident().getSettlerMax()/2);
		settlement.getWarehouse().depositItemValue("WHEAT",settlement.getResident().getSettlerMax()*2 );
		settlement.getWarehouse().depositItemValue("BREAD",settlement.getResident().getSettlerMax()*2 );
		settlement.getWarehouse().depositItemValue("WOOD_HOE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD_AXE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD_PICKAXE",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("LOG",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("WOOD",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("STICK",settlement.getResident().getSettlerMax());
		settlement.getWarehouse().depositItemValue("COBBLESTONE",settlement.getResident().getSettlerMax());
		settlement.setWorkerToBuilding(settlement.getResident().getSettlerCount());

		plugin.getData().writeSettlement(settlement);
		
		msg.add("Storage : "+settlement.getWarehouse().getItemMax());
		msg.add("Max Beds: "+settlement.getResident().getSettlerMax());
		msg.add("Settlers: "+settlement.getResident().getSettlerCount());
		msg.add("Building: "+settlement.getBuildingList().size());
		msg.add("Bank    : "+settlement.getBank().getKonto());
		msg.add("Biome   : "+biome);
		plugin.getMessageData().printPage(sender, msg, page);
		return true;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		cmdCreate( plugin,  sender);
	}
	
//	private String findSuperRegionAtLocation(Realms plugin, Player player)
//	{
//		Location position = player.getLocation();
//	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
//	    {
//	    	SettleType settleType = plugin.getConfigData().superRegionToSettleType(sRegion.getType());
//	    	if (settleType != SettleType.NONE)
//	    	{
//	    		return sRegion.getName();
//	    	}
//	    }
//		return "";
//	}
	

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		boolean isReady = false;
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add(ChatColor.RED+"Realms is BUSY, try later ! ");
			return false;
		}

		if (isOpOrAdminMsg(sender) == false)
		{
			errorMsg.add(ChatColor.RED+"You are not a Admin or OP ! ");
			return false;
		}
		// fehlenden Parameter Name ersetzen
		if (this.name == "")
		{
			if (sender instanceof Player)
			{
				name = findSuperRegionAtLocation(plugin, (Player) sender);
				// pruefe leeren Namen
				if (name.equals(""))
				{
					return false;
				}
			} else
			{
				errorMsg.add("name must be given as Console operator  ");
				return false;
			}
		}
		// pruefe ob bereits ein Settlement mit dem Namen vorhanden ist
		if(plugin.getRealmModel().getSettlements().containsName(name))
		{
			errorMsg.add(ChatColor.RED+"Settlement already exist ! ");
			return false;
		}
		
		// pruefe ob SupeRegion gueltig bzw. vorhanden ist
		if (plugin.stronghold.getRegionManager().getSuperRegionNames().contains(name))
		{
			if (isSuperRegionOwner ( plugin, sender,  name ))
			{	
				isReady = true;
			} else
			{
				return false;
			}
			// pruefe settleType der SuperRegion und setze den internen wert
			this.settleType = plugin.getConfigData().superRegionToSettleType((plugin.stronghold.getRegionManager().getSuperRegion(name).getType()));
			if (settleType == SettleType.NONE)
			{
				errorMsg.add(ChatColor.RED+"Superregion has the wrong Type ");
				return false;
			}
		}
		return isReady;
		
	}

}
