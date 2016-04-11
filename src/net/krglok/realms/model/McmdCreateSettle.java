package net.krglok.realms.model;

import org.bukkit.block.Biome;

import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import multitallented.redcastlemedia.bukkit.stronghold.region.SuperRegion;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
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
//		playerName = "";
		boolean isNPC = false;
		if ((playerName == "") )
		{
			playerName = ConfigBasis.NPC_0;
			isNPC = true;
		}
		Owner owner = rModel.getOwners().getOwnerName(playerName);
		if (owner == null)
		{
			Owner fOwner = rModel.getOwners().getOwner(0);
			if (fOwner == null)
			{
				owner = Owner.initDefaultOwner();
			} else
			{
				owner = fOwner;
			}
		}
		LocationData position;
		SuperRegion sRegion =  rModel.getServer().getSuperRegion(superRegionName);
		if (sRegion == null)
		{
			System.out.println("[REALMS]  SuperRegion not found on create settlement !" );
			return;
		}
		sRegion.setBalance(10000.0);
		position = new LocationData(
				sRegion.getLocation().getWorld().getName(),
				sRegion.getLocation().getX(), 
				sRegion.getLocation().getY(),
				sRegion.getLocation().getZ());

		Settlement settlement = new Settlement(owner.getId(), position, settleType, superRegionName, biome); //, rModel.getLogList());
		rModel.getSettlements().addSettlement(settlement);
//		System.out.println(superRegionName+" : "+settlement.getId());
		for (Region region : rModel.getServer().getRegionInSuperRegion(superRegionName))
		{
			int regionId = region.getID();
			String hsRegionType = region.getType();
    		BuildPlanType buildingType = rModel.getConfig().regionToBuildingType(hsRegionType);
//			Building building = new Building(
//					buildingType, 
//					hsRegion, 
//					new LocationData(
//					region.getLocation().getWorld().getName(),
//					region.getLocation().getX(), 
//					region.getLocation().getY(),
//					region.getLocation().getZ()),
//					settlement.getId()
//					);
//			rModel.getBuildings().addBuilding(building);
//			rModel.getData().writeBuilding(building);
			if ((BuildPlanType.getBuildGroup(buildingType) < 900)
					&& (BuildPlanType.getBuildGroup(buildingType) >= 10)
					)
				{
					if (rModel.getData().getBuildings().containRegion(regionId) == false)
					{
						Building building = new Building(
							buildingType, 
							regionId, 
							new LocationData(
									region.getLocation().getWorld().getName(),
									region.getLocation().getX(), 
									region.getLocation().getY(),
									region.getLocation().getZ()),
									settlement.getId()
							);
						rModel.getBuildings().addBuilding(building);
						rModel.getData().writeBuilding(building);
						System.out.println("[REALMS] Modell Settle "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
					} else
					{
						Building building = rModel.getData().getBuildings().getBuildingByRegion(regionId);
						if ((building.getSettleId() == 0) && (building.getLehenId() == 0))
						{
							building.setSettleId(settlement.getId());
							rModel.getData().writeBuilding(building);
							System.out.println("[REALMS] Model Settle "+building.getBuildingType()+":"+building.getId()+":"+building.getHsRegion());
						}
					}
				} else
				{
					System.out.println("[REALMS] Model Settle wrong Building Group"+BuildPlanType.getBuildGroup(buildingType));
				}
		}
		// make not dynamic initialization
		
		settlement.setBuildingList(rModel.getBuildings().getSubList(settlement.getId()));
		settlement.initSettlement(rModel.getData().getPriceList());

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
		settlement.getWarehouse().depositItemValue("SOIL",64);
		settlement.getWarehouse().depositItemValue("WATER",settlement.getResident().getSettlerMax());
		settlement.getBank().depositKonto((double) (settlement.getResident().getSettlerCount()*10) , "CREATE",settlement.getId());
		System.out.println("[REALMS] Model Write Settlement: "+settlement.getName()+" Activ:"+settlement.isActive()+" Enable:"+settlement.isEnabled());
		rModel.getData().writeSettlement(settlement);
	}

	@Override
	public boolean canExecute()
	{
			return true;
	}

}
