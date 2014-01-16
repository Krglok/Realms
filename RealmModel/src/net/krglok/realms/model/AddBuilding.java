package net.krglok.realms.model;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.Settlement;

public class AddBuilding implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.ADDBUILDING;
	private RealmModel rModel;
	private int settleId; 
	private int buildingId;

	public AddBuilding(RealmModel rModel, int settleId, int buildingId)
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
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		String hsRegionType = rModel.getServer().getRegionType(buildingId);
		BuildingType buildingType = rModel.getConfig().regionToBuildingType(hsRegionType);
		Building building = new Building(buildingType, buildingId , hsRegionType, true);
		if (Settlement.addBuilding(building, settle))
		{
			
		}
			

	}

	@Override
	public boolean canExecute()
	{
		if ((rModel.getSettlements().getSettlement(settleId) != null)
			&& (rModel.getServer().getRegionType(buildingId) != null))
		{
			return true;
		}
		return false;
	}

}
