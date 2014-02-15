package net.krglok.realms.model;

import org.bukkit.block.Biome;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;

public class McmdCreateSettle implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.CREATESETTLEMENT;
	private RealmModel rModel;
	private String playerName;
	private SettleType settleType;
	private String superRegionName;
	private Biome biome;
	
	public McmdCreateSettle(RealmModel rModel, String superRegionName, String playerName, SettleType settleType, Biome biome)
	{
		this.rModel = rModel;
		this.superRegionName = superRegionName;
		this.playerName = playerName;
		this.settleType = settleType;
		this.biome = biome;
	}
	
	@Override
	public ModelCommandType command()
	{
		return commandType;
	}
	

	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName(), String.class.getName(), String.class.getName(), SettleType.class.getName()};
	}

	@Override
	public void execute()
	{
		playerName = "";
		boolean isNPC = false;
		Owner owner = null; 
		if ((playerName == "") )
		{
			playerName = "NPC1";
			isNPC = true;
		} else
		{
			for (Owner fOwner : rModel.getOwners().getOwners().values())
			{
				if (playerName.equalsIgnoreCase(fOwner.getPlayerName()))
				{
					owner = fOwner;
				}
			}
    		if (owner == null)
    		{
    			owner = new Owner(playerName, isNPC);
    		}
		}
		LocationData position;
		SuperRegion sRegion =  rModel.getServer().getSuperRegion(superRegionName);
		if (sRegion == null)
		{
			position = new LocationData("", 0.0, 0.0, 0.0);
			return;
		}
		position = new LocationData(
				sRegion.getLocation().getWorld().getName(),
				sRegion.getLocation().getX(), 
				sRegion.getLocation().getY(),
				sRegion.getLocation().getZ());

		Settlement settlement = new Settlement(playerName, position, settleType, superRegionName, biome);
		rModel.getSettlements().addSettlement(settlement);
//		System.out.println(superRegionName+" : "+settlement.getId());
		for (Region region : rModel.getServer().getRegionInSuperRegion(superRegionName))
		{
			int hsRegion = region.getID();
			String hsRegionType = region.getType();
    		BuildPlanType buildingType = rModel.getConfig().regionToBuildingType(hsRegionType);
			Building building = new Building(
					buildingType, 
					hsRegion, 
					hsRegionType, 
					true,
					new LocationData(
							region.getLocation().getWorld().getName(),
							region.getLocation().getX(), 
							region.getLocation().getY(),
							region.getLocation().getZ())
					);
			Settlement.addBuilding(building, settlement);
		}
		// make not dynamic initialization
		settlement.setSettlerMax();
		settlement.setWorkerNeeded();

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
		settlement.getBank().depositKonto((double) (settlement.getResident().getSettlerCount()*10) , "CREATE");
		System.out.println("Write Settlement to Storage"+settlement.getName()+" Activ:"+settlement.isActive()+" Enable:"+settlement.isEnabled());
		rModel.getData().writeSettlement(settlement);
	}

	@Override
	public boolean canExecute()
	{
			return true;
	}

}
