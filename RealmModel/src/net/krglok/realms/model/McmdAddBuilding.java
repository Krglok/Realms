package net.krglok.realms.model;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;

/**
 * Add region as Building to the Settlement
 *  
 * @author Windu
 *
 */
public class McmdAddBuilding implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.ADDBUILDING;
	private RealmModel rModel;
	private int settleId; 
	private int buildingId;

	public McmdAddBuilding(RealmModel rModel, int settleId, int buildingId)
	{
		this.rModel = rModel;
		this.settleId = settleId;
		this.buildingId = buildingId;
	}
	
	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName(), int.class.getName(), int.class.getName() };
	}

	@Override
	public void execute()
	{
//		Settlement settle = rModel.getSettlements().getSettlement(settleId);
//		String hsRegionType = rModel.getServer().getRegionType(buildingId);
//		Region region = rModel.getServer().getR
//		BuildPlanType buildingType = rModel.getConfig().regionToBuildingType(hsRegionType);
//		Building building = new Building(
//				buildingType, 
//				buildingId , 
//				hsRegionType, 
//				true,
//				new LocationData(
//						region.getLocation().getWorld().getName(),
//						region.getLocation().getX(), 
//						region.getLocation().getY(),
//						region.getLocation().getZ())
//				);
//		if (Settlement.addBuilding(building, settle))
//		{
//			
//		}
		System.out.println("Command not implemented !!!");

	}

	@Override
	public boolean canExecute()
	{
//		if ((rModel.getSettlements().getSettlement(settleId) != null)
//			&& (rModel.getServer().getRegionType(buildingId) != null))
//		{
//			return true;
//		}
		System.out.println("Command not implemented !!!");
		return false;
	}

}
